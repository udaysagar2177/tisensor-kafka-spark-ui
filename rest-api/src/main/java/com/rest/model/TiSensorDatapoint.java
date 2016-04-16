package com.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by uday on 3/21/16.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class TiSensorDatapoint {
    private double temperature;
    private double light;
    private long timestamp;
    private String tiSensorId;

    public double getLight() {
        return light;
    }

    public void setLight(double light) {
        this.light = light;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTiSensorId() {
        return tiSensorId;
    }

    public void setTiSensorId(String tiSensorId) {
        this.tiSensorId = tiSensorId;
    }
}
