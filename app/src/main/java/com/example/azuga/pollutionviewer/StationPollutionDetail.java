package com.example.azuga.pollutionviewer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 09-01-2016.
 */
public class StationPollutionDetail implements Parcelable {
    public static final Parcelable.Creator<StationPollutionDetail> CREATOR = new Parcelable.Creator<StationPollutionDetail>() {
        public StationPollutionDetail createFromParcel(Parcel in) {
            return new StationPollutionDetail(in);
        }

        public StationPollutionDetail[] newArray(int size) {
            return new StationPollutionDetail[size];
        }
    };
    private String stateId;
    private String cityId;
    private String station;
    private String timestamp;
    @SerializedName("PM25")
    private String pollutionLevel;
    @SerializedName("CarbonMonoxide")
    private String carbonMonoxide;
    @SerializedName("NitricOxide")
    private String nitricOxide;
    @SerializedName("SulfurDioxide")
    private String sulphurDioxide;
    @SerializedName("Benzene")
    private String benzene;
    @SerializedName("Toluene")
    private String toulene;

    private StationPollutionDetail(Parcel in) {
        pollutionLevel = in.readString();
        carbonMonoxide = in.readString();
        nitricOxide = in.readString();
        sulphurDioxide = in.readString();
        benzene = in.readString();
        toulene = in.readString();
        timestamp = in.readString();
        stateId = in.readString();
        cityId = in.readString();
        station = in.readString();
    }

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

    public String getNitricOxide() {
        return nitricOxide;
    }

    public void setNitricOxide(String nitricOxide) {
        this.nitricOxide = nitricOxide;
    }

    public String getSulphurDioxide() {
        return sulphurDioxide;
    }

    public void setSulphurDioxide(String sulphurDioxide) {
        this.sulphurDioxide = sulphurDioxide;
    }

    public String getBenzene() {
        return benzene;
    }

    public void setBenzene(String benzene) {
        this.benzene = benzene;
    }

    public String getToulene() {
        return toulene;
    }

    public void setToulene(String toulene) {
        this.toulene = toulene;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pollutionLevel);
        parcel.writeString(carbonMonoxide);
        parcel.writeString(nitricOxide);
        parcel.writeString(sulphurDioxide);
        parcel.writeString(benzene);
        parcel.writeString(toulene);
        parcel.writeString(timestamp);
        parcel.writeString(stateId);
        parcel.writeString(cityId);
        parcel.writeString(station);
    }

}
