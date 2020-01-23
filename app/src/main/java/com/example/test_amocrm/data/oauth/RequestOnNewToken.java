package com.example.test_amocrm.data.oauth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestOnNewToken {
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("client_secret")
    @Expose
    private String clientSecret;
    @SerializedName("grant_type")
    @Expose
    private String grantType;
    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;
    @SerializedName("redirect_uri")
    @Expose
    private String redirectUri;

    public RequestOnNewToken(String clientId, String clientSecret, String grantType, String refreshToken, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.refreshToken = refreshToken;
        this.redirectUri = redirectUri;
    }
}
