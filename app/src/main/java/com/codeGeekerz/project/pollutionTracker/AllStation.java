package com.codeGeekerz.project.pollutionTracker;

/**
 * Created by User on 21-01-2016.
 */
public class AllStation {
    private String cityId;
    private String stationName;
    private String stateName;
    private String stateId;
    private String fullStationName;
    private String cityName;

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getFullStationName() {
        return fullStationName;
    }

    public void setFullStationName(String fullStationName) {
        this.fullStationName = fullStationName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
