create memory table if not exists weather
(
    ID           int PRIMARY KEY AUTO_INCREMENT,
    city_name    VARCHAR_IGNORECASE(255),
    description  VARCHAR_IGNORECASE(255),
    country_code bigint,
    temperature  decimal(10, 3),
    clouds       int,
    date         timestamp,
    created_at   timestamp
);