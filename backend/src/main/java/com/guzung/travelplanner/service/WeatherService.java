package com.guzung.travelplanner.service;

import com.guzung.travelplanner.model.Weather;
import com.guzung.travelplanner.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import static com.guzung.travelplanner.utils.TimeUtils.dateWithTime;

@Service
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    public Set<Weather> getCityWeather(String city, Date date) {
        String selectedDay = new SimpleDateFormat("yyyy-MM-dd").format(date);

        Set<Weather> forecast = weatherRepository
                .findAllByCityNameLikeAndDateBetween(city, dateWithTime(date, 0), dateWithTime(date, 23));
        if (forecast.isEmpty()) {
            return openWeatherMapService
                    .get5daysForecast(city)
                    .stream()
                    .map(weatherRepository::save)
                    .filter(weather -> new SimpleDateFormat("yyyy-MM-dd").format(weather.getDate()).equals(selectedDay))
                    .collect(Collectors.toSet());
        }
        return forecast;
    }

}
