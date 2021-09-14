package com.guzung.travelplanner.repository;

import com.guzung.travelplanner.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer>, JpaSpecificationExecutor<Weather> {
    Set<Weather> findAllByCityNameLikeAndDateBetween(String city,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date dateStart,
                                                     @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date dateEnd);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(nativeQuery = true,
            value = "DELETE FROM weather where " +
                    "TIMESTAMPDIFF(MINUTE,PARSEDATETIME(created_at,'yyyy-MM-dd HH:mm:ss'), CURRENT_TIMESTAMP()) >= 60")
    void removeOldRecords();
}
