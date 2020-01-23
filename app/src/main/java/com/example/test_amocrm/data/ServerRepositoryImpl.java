package com.example.test_amocrm.data;

import com.example.test_amocrm.data.deal.DealJsonDeserializer;
import com.example.test_amocrm.data.deal.Deals;
import com.example.test_amocrm.data.pipeline.Pipelines;
import com.example.test_amocrm.data.pipeline.PipelinesJsonDeserializer;
import com.example.test_amocrm.data.oauth.OAuthToken;
import com.example.test_amocrm.data.oauth.RequestOnNewToken;
import com.example.test_amocrm.data.oauth.RequestOnToken;
import com.example.test_amocrm.data.retrofit.OAuthServer;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerRepositoryImpl implements ServerRepository {
    private OAuthServer oAuthServer;
    private final String BASE_URL = "https://simonhoolegan.amocrm.ru";
    private final String TYPE_TOKEN = "Bearer ";

    private PipelinesJsonDeserializer pipelinesJsonDeserializer;


    @Inject
    public ServerRepositoryImpl() {
        //Логирование
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //OkHttpClient
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        pipelinesJsonDeserializer = new PipelinesJsonDeserializer();

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Deals.class, new DealJsonDeserializer());
        gsonBuilder.registerTypeAdapter(Pipelines.class, pipelinesJsonDeserializer);
        Gson gson = gsonBuilder.create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        oAuthServer = retrofit.create(OAuthServer.class);
    }

    @Override
    public Single<OAuthToken> requestToken(RequestOnToken requestOnToken) {
        return oAuthServer.requestTokenToServer(requestOnToken);
    }

    @Override
    public Single<OAuthToken> requestNewToken(RequestOnNewToken requestOnNewToken) {
        return oAuthServer.requestNewTokenToServer(requestOnNewToken);
    }

    @Override
    public Single<Deals>  getDeals(String token) {
        return oAuthServer.getDealsFromServer(TYPE_TOKEN + token);
    }

    @Override
    public Single<Pipelines> getPipelines(String token) {
        return oAuthServer.getPipelinesFromServer(TYPE_TOKEN + token);
    }
}
