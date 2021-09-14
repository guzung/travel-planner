package com.guzung.travelplanner.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guzung.travelplanner.exception.DataNotFoundException;
import com.guzung.travelplanner.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.guzung.travelplanner.utils.TimeUtils.getDate;
import static com.guzung.travelplanner.utils.TimeUtils.toTimestamp;

@Service
public class OpenWeatherMapService {
    private static final String WEATHER_URL = "http://api.openweathermap.org/data/2.5/forecast?q={city}&units=metric&appid={key}";

    @Value("${openweathermap.apikey}")
    private String apiKey;

    @Value("${openweathermap.start-time}")
    private String startTime;

    @Value("${openweathermap.end-time}")
    private String endTime;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper mapper;

    public static String unaccent(String src) {
        return Normalizer
                .normalize(src, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "");
    }

    public Set<Weather> get5daysForecast(String city) {
        URI url = new UriTemplate(WEATHER_URL).expand(city, apiKey);
        try {
            var response = restTemplate.getForEntity(url, String.class);
            return convert(response);
        } catch (HttpClientErrorException e) {
            throw new DataNotFoundException();
        }
    }

    private Set<Weather> convert(ResponseEntity<String> response) {
        try {
            Set<Weather> allDays = new HashSet<>();
            JsonNode root = mapper.readTree(response.getBody());

            String city = unaccent(root.path("city").path("name").asText());
            Long code = root.path("city").path("id").asLong();

            root.path("list").elements().forEachRemaining(node -> {
                if (isBetweenRequiredHours(node)) {
                    Weather weather = new Weather();
                    weather.setCityName(city);
                    weather.setCountryCode(code);
                    weather.setDescription(node.path("weather").get(0).path("description").asText());
                    weather.setTemperature(BigDecimal.valueOf(node.path("main").path("temp").asLong()));
                    weather.setClouds(node.path("clouds").path("all").asInt());
                    weather.setDate(toTimestamp(node.path("dt_txt").asText()));
                    allDays.add(weather);
                }
            });
            return allDays;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing JSON", e);
        }
    }

    Boolean isBetweenRequiredHours(JsonNode node) {
        try {

            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(node.path("dt_txt").asText());
            Calendar ourDate = getDate(new SimpleDateFormat("HH:mm:ss").format(date), "HH:mm:ss");
            return ourDate.after(startDate()) && ourDate.before(endDate());

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    Calendar startDate() throws ParseException {
        return getDate(startTime, "HH:mm:ss");
    }

    Calendar endDate() throws ParseException {
        return getDate(endTime, "HH:mm:ss");
    }
}
