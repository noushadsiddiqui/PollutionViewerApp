package com.example.azuga.pollutionviewer;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 09-01-2016.
 */
public class StationPollutionDetail {
    private String stateId;
    private String cityId;
    private String station;
    private String timestamp;
    @SerializedName("PM25")
    private String pollutionLevel;
    @SerializedName("CarbonMonoxide")
    private String carbonMonoxide;

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {

        this.timestamp = timestamp;
    }

    public String getPollutionLevel() {
        return pollutionLevel;
    }

    public void setPollutionLevel(String pollutionLevel) {
        this.pollutionLevel = pollutionLevel;
    }

    public String getCarbonMonoxide() {
        return carbonMonoxide;
    }

    public void setCarbonMonoxide(String carbonMonoxide) {
        this.carbonMonoxide = carbonMonoxide;
    }
}
