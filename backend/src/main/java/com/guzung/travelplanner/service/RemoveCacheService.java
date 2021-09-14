package com.guzung.travelplanner.service;

import com.guzung.travelplanner.repository.WeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveCacheService {
    @Autowired
    private WeatherRepository weatherRepository;

    @Transactional
    @Scheduled(fixedRate = 1000 * 60 * 10)
    public void cronJobRemoveOldData() {
        weatherRepository.removeOldRecords();
    }
}
