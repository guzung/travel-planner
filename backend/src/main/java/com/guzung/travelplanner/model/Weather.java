package com.guzung.travelplanner.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Table(name = "weather")
public class Weather {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name ="description")
    private String description;

    @Column(name ="city_name")
    private String cityName;

    @Column(name ="country_code")
    private Long countryCode;

    @Column(name ="temperature")
    private BigDecimal temperature;

    @Column(name ="clouds")
    private Integer clouds;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "Timestamp")
    private Date createdAt;

    @PrePersist
    public void onCreate() {
        setCreatedAt(new Timestamp(System.currentTimeMillis()));
    }
}
