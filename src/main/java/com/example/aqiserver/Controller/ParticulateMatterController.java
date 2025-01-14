package com.example.aqiserver.Controller;
import com.example.aqiserver.Service.InfluxDBService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/particulate-matter")
public class ParticulateMatterController {

    private final InfluxDBService influxDBService;

    public ParticulateMatterController(InfluxDBService influxDBService) {
        this.influxDBService = influxDBService;
    }

    @GetMapping("/get-PM-Data")
    public String getPMData() {
        String timeRangeStart = "-15s";
        String timeRangeStop = "now()";
        String windowPeriod = "1h";
        List<String> pm_data = influxDBService.queryData(timeRangeStart, timeRangeStop, windowPeriod);
        return pm_data.toString();
    }
}
