package com.example.aqiserver.Service;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.QueryApi;
import com.influxdb.query.FluxRecord;
import com.influxdb.query.FluxTable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InfluxDBService {

    private final InfluxDBClient influxDBClient;

    public InfluxDBService(InfluxDBClient influxDBClient) {
        this.influxDBClient = influxDBClient;
    }

    public List<String> queryData(String timeRangeStart, String timeRangeStop, String windowPeriod) {
        QueryApi queryApi = influxDBClient.getQueryApi();

        String fluxQuery = String.format(
                "from(bucket: \"AQIMetrics\") " +
                        "|> range(start: %s, stop: %s) " +
                        "|> filter(fn: (r) => r[\"_measurement\"] == \"air_quality\") " +
                        "|> filter(fn: (r) => r[\"_field\"] == \"PM1\" or r[\"_field\"] == \"PM10\" or r[\"_field\"] == \"PM2.5\") " +
                        "|> filter(fn: (r) => r[\"location\"] == \"local\") " +
                        "|> aggregateWindow(every: %s, fn: mean, createEmpty: false) " +
                        "|> yield(name: \"mean\")",
                timeRangeStart, timeRangeStop, windowPeriod);

        List<FluxTable> tables = queryApi.query(fluxQuery);

        List<String> results = new ArrayList<>();
        for (FluxTable table : tables) {
            for (FluxRecord record : table.getRecords()) {
                results.add(record.getValueByKey("_value").toString());
            }
        }

        return results;
    }
}
