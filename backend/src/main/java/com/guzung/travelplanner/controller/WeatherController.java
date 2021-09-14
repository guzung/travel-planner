package com.guzung.travelplanner.controller;

import com.guzung.travelplanner.model.Weather;
import com.guzung.travelplanner.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Set;

@RestController()
@RequestMapping("/weather/api/v1")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;

    @GetMapping()
    public Set<Weather> getCityWeather(@RequestParam("city") String city,
                                       @RequestParam("timestamp")
                                       @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date date) {
        return weatherService.getCityWeather(city, date);
    }
}
