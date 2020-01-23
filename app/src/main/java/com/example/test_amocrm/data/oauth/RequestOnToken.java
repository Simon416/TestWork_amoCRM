package com.example.test_amocrm.data.oauth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestOnToken {
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("client_secret")
    @Expose
    private String clientSecret;
    @SerializedName("grant_type")
    @Expose
    private String grantType;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("redirect_uri")
    @Expose
    private String redirectUri;

    public RequestOnToken(String clientId, String clientSecret, String grantType, String code, String redirectUri) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.grantType = grantType;
        this.code = code;
        this.redirectUri = redirectUri;
    }
}
