package com.example.test_amocrm.data.retrofit;

import com.example.test_amocrm.data.deal.Deals;
import com.example.test_amocrm.data.pipeline.Pipelines;
import com.example.test_amocrm.data.oauth.OAuthToken;
import com.example.test_amocrm.data.oauth.RequestOnNewToken;
import com.example.test_amocrm.data.oauth.RequestOnToken;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OAuthServer {

    @POST("/oauth2/access_token")
    Single<OAuthToken> requestTokenToServer(@Body RequestOnToken requestOnToken);

    @POST("/oauth2/access_token")
    Single<OAuthToken> requestNewTokenToServer(@Body RequestOnNewToken requestOnNewToken);

    @GET("/api/v2/leads")
    Single<Deals>  getDealsFromServer(@Header("Authorization") String authorizationToken);

    @GET("/api/v2/account?with=pipelines")
    Single<Pipelines> getPipelinesFromServer(@Header("Authorization") String authorizationToken);

}
