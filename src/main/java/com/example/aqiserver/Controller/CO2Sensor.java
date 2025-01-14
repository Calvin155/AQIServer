package com.example.aqiserver.Controller;

import com.example.aqiserver.Service.InfluxDBService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/co2-sensor")
public class CO2Sensor {
    private final InfluxDBService influxDBService;

    public CO2Sensor(InfluxDBService influxDBService) {
        this.influxDBService = influxDBService;
    }
}
