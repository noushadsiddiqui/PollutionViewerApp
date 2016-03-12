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
    private String pM25;
    @SerializedName("PM10")
    private String pM10;
    @SerializedName("CarbonMonoxide")
    private String carbonMonoxide;
    @SerializedName("NitricOxide")
    private String nitricOxide;
    @SerializedName("NitrogenDioxide")
    private String nitrogenDioxide;
    @SerializedName("OxidesofNitrogen")
    private String oxidesOfNitrogen;
    @SerializedName("SulfurDioxide")
    private String sulphurDioxide;
    @SerializedName("Benzene")
    private String benzene;
    @SerializedName("Toluene")
    private String toulene;
    @SerializedName("EthylBenzene")
    private String ethylBenzene;
    @SerializedName("Ammonia")
    private String ammonia;
    @SerializedName("NonMethaneHydrocarbon")
    private String nonMethaneHydrocarbon;
    @SerializedName("PXylene")
    private String PXylene;
    @SerializedName("Ozone")
    private String ozone;
    @SerializedName("Temperature")
    private String temperature;
    @SerializedName("RelativeHumidity")
    private String relativeHumidity;
    @SerializedName("BarPressure")
    private String barPressure;
    @SerializedName("WindSpeed")
    private String windSpeed;
    @SerializedName("WindDirection")
    private String windDirection;
    @SerializedName("SolarRadiation")
    private String solarRadiation;
    @SerializedName("PM25_avg")
    private String pM25_avg;
    @SerializedName("PM10_avg")
    private String pM10_avg;
    @SerializedName("NitrogenDioxide_avg")
    private String nitrogenDioxide_avg;
    @SerializedName("SulfurDioxide_avg")
    private String sulfurDioxide_avg;
    @SerializedName("CarbonMonoxide_avg")
    private String carbonMonoxide_avg;
    @SerializedName("Ammonia_avg")
    private String ammonia_avg;
    @SerializedName("Ozone_avg")
    private String ozone_avg;
    @SerializedName("pm25AQI")
    private String pm25AQI;
    @SerializedName("pm10AQI")
    private String pm10AQI;
    @SerializedName("nitrogenDioxideAQI")
    private String nitrogenDioxideAQI;
    @SerializedName("sulfurDioxideAQI")
    private String sulfurDioxideAQI;
    @SerializedName("carbonMonoxideAQI")
    private String carbonMonoxideAQI;
    @SerializedName("ammoniaAQI")
    private String ammoniaAQI;
    @SerializedName("ozoneAQI")
    private String ozoneAQI;
    @SerializedName("aqi")
    private String aqi;

    private StationPollutionDetail(Parcel in) {
        pM25 = in.readString();
        pM10 = in.readString();
        carbonMonoxide = in.readString();
        nitricOxide = in.readString();
        nitrogenDioxide = in.readString();
        oxidesOfNitrogen = in.readString();
        sulphurDioxide = in.readString();
        benzene = in.readString();
        toulene = in.readString();
        ethylBenzene = in.readString();
        ammonia = in.readString();
        nonMethaneHydrocarbon = in.readString();
        PXylene = in.readString();
        ozone = in.readString();
        temperature = in.readString();
        relativeHumidity = in.readString();
        barPressure = in.readString();
        windSpeed = in.readString();
        windDirection = in.readString();
        solarRadiation = in.readString();
        pM25_avg = in.readString();
        pM10_avg = in.readString();
        nitrogenDioxide_avg = in.readString();
        sulfurDioxide_avg = in.readString();
        carbonMonoxide_avg = in.readString();
        ammonia_avg = in.readString();
        ozone_avg = in.readString();
        pm25AQI = in.readString();
        pm10AQI = in.readString();
        nitrogenDioxideAQI = in.readString();
        sulfurDioxideAQI = in.readString();
        carbonMonoxideAQI = in.readString();
        ammoniaAQI = in.readString();
        ozoneAQI = in.readString();
        aqi = in.readString();
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

    public String getpM25() {
        return pM25;
    }

    public void setpM25(String pM25) {
        this.pM25 = pM25;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getpM10() {
        return pM10;
    }

    public void setpM10(String pM10) {
        this.pM10 = pM10;
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

    public String getNitrogenDioxide() {
        return nitrogenDioxide;
    }

    public void setNitrogenDioxide(String nitrogenDioxide) {
        this.nitrogenDioxide = nitrogenDioxide;
    }

    public String getOxidesOfNitrogen() {
        return oxidesOfNitrogen;
    }

    public void setOxidesOfNitrogen(String oxidesOfNitrogen) {
        this.oxidesOfNitrogen = oxidesOfNitrogen;
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

    public String getEthylBenzene() {
        return ethylBenzene;
    }

    public void setEthylBenzene(String ethylBenzene) {
        this.ethylBenzene = ethylBenzene;
    }

    public String getAmmonia() {
        return ammonia;
    }

    public void setAmmonia(String ammonia) {
        this.ammonia = ammonia;
    }

    public String getNonMethaneHydrocarbon() {
        return nonMethaneHydrocarbon;
    }

    public void setNonMethaneHydrocarbon(String nonMethaneHydrocarbon) {
        this.nonMethaneHydrocarbon = nonMethaneHydrocarbon;
    }

    public String getPXylene() {
        return PXylene;
    }

    public void setPXylene(String PXylene) {
        this.PXylene = PXylene;
    }

    public String getOzone() {
        return ozone;
    }

    public void setOzone(String ozone) {
        this.ozone = ozone;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(String relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public String getBarPressure() {
        return barPressure;
    }

    public void setBarPressure(String barPressure) {
        this.barPressure = barPressure;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(String windDirection) {
        this.windDirection = windDirection;
    }

    public String getSolarRadiation() {
        return solarRadiation;
    }

    public void setSolarRadiation(String solarRadiation) {
        this.solarRadiation = solarRadiation;
    }

    public String getpM25_avg() {
        return pM25_avg;
    }

    public void setpM25_avg(String pM25_avg) {
        this.pM25_avg = pM25_avg;
    }

    public String getpM10_avg() {
        return pM10_avg;
    }

    public void setpM10_avg(String pM10_avg) {
        this.pM10_avg = pM10_avg;
    }

    public String getNitrogenDioxide_avg() {
        return nitrogenDioxide_avg;
    }

    public void setNitrogenDioxide_avg(String nitrogenDioxide_avg) {
        this.nitrogenDioxide_avg = nitrogenDioxide_avg;
    }

    public String getSulfurDioxide_avg() {
        return sulfurDioxide_avg;
    }

    public void setSulfurDioxide_avg(String sulfurDioxide_avg) {
        this.sulfurDioxide_avg = sulfurDioxide_avg;
    }

    public String getCarbonMonoxide_avg() {
        return carbonMonoxide_avg;
    }

    public void setCarbonMonoxide_avg(String carbonMonoxide_avg) {
        this.carbonMonoxide_avg = carbonMonoxide_avg;
    }

    public String getAmmonia_avg() {
        return ammonia_avg;
    }

    public void setAmmonia_avg(String ammonia_avg) {
        this.ammonia_avg = ammonia_avg;
    }

    public String getOzone_avg() {
        return ozone_avg;
    }

    public void setOzone_avg(String ozone_avg) {
        this.ozone_avg = ozone_avg;
    }

    public String getPm25AQI() {
        return pm25AQI;
    }

    public void setPm25AQI(String pm25AQI) {
        this.pm25AQI = pm25AQI;
    }

    public String getPm10AQI() {
        return pm10AQI;
    }

    public void setPm10AQI(String pm10AQI) {
        this.pm10AQI = pm10AQI;
    }

    public String getNitrogenDioxideAQI() {
        return nitrogenDioxideAQI;
    }

    public void setNitrogenDioxideAQI(String nitrogenDioxideAQI) {
        this.nitrogenDioxideAQI = nitrogenDioxideAQI;
    }

    public String getSulfurDioxideAQI() {
        return sulfurDioxideAQI;
    }

    public void setSulfurDioxideAQI(String sulfurDioxideAQI) {
        this.sulfurDioxideAQI = sulfurDioxideAQI;
    }

    public String getCarbonMonoxideAQI() {
        return carbonMonoxideAQI;
    }

    public void setCarbonMonoxideAQI(String carbonMonoxideAQI) {
        this.carbonMonoxideAQI = carbonMonoxideAQI;
    }

    public String getAmmoniaAQI() {
        return ammoniaAQI;
    }

    public void setAmmoniaAQI(String ammoniaAQI) {
        this.ammoniaAQI = ammoniaAQI;
    }

    public String getOzoneAQI() {
        return ozoneAQI;
    }

    public void setOzoneAQI(String ozoneAQI) {
        this.ozoneAQI = ozoneAQI;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pM25);
        parcel.writeString(pM10);
        parcel.writeString(carbonMonoxide);
        parcel.writeString(nitricOxide);
        parcel.writeString(nitrogenDioxide);
        parcel.writeString(oxidesOfNitrogen);
        parcel.writeString(sulphurDioxide);
        parcel.writeString(benzene);
        parcel.writeString(toulene);
        parcel.writeString(ethylBenzene);
        parcel.writeString(ammonia);
        parcel.writeString(nonMethaneHydrocarbon);
        parcel.writeString(PXylene);
        parcel.writeString(ozone);
        parcel.writeString(temperature);
        parcel.writeString(relativeHumidity);
        parcel.writeString(barPressure);
        parcel.writeString(windSpeed);
        parcel.writeString(windDirection);
        parcel.writeString(solarRadiation);
        parcel.writeString(pM25_avg);
        parcel.writeString(pM10_avg);
        parcel.writeString(nitrogenDioxide_avg);
        parcel.writeString(sulfurDioxide_avg);
        parcel.writeString(carbonMonoxide_avg);
        parcel.writeString(ammonia_avg);
        parcel.writeString(ozone_avg);
        parcel.writeString(pm25AQI);
        parcel.writeString(pm10AQI);
        parcel.writeString(nitrogenDioxideAQI);
        parcel.writeString(sulfurDioxideAQI);
        parcel.writeString(carbonMonoxideAQI);
        parcel.writeString(ammoniaAQI);
        parcel.writeString(ozoneAQI);
        parcel.writeString(aqi);
        parcel.writeString(timestamp);
        parcel.writeString(stateId);
        parcel.writeString(cityId);
        parcel.writeString(station);
    }

}
