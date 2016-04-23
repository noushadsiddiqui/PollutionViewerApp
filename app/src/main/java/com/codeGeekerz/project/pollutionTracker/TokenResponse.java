package com.codeGeekerz.project.pollutionTracker;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 15-03-2016.
 */
public class TokenResponse {
    @SerializedName("message")
    private String message;
    @SerializedName("token")
    private String token;
    @SerializedName("success")
    private boolean success;

    public TokenResponse() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
