package com.example.test_amocrm.data;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StoreKeys  {

    private final String ACCESS_TOKEN = "Access token";
    private final String REFRESH_TOKEN = "Refresh token";
    private final String AUTHORIZATION_KEY = "Authorization key";
    public static final String DEF_VALUE = "key is empty";
    private SharedPreferences sharedPreferences ;

    @Inject
    StoreKeys(Context context){
        String KEYS_FROM_SERVER = "Keys from Server";
        this.sharedPreferences = context.getSharedPreferences(KEYS_FROM_SERVER, Context.MODE_PRIVATE);
    }

    public void saveAuthorizationKey(String authKey){
        sharedPreferences.edit().putString(AUTHORIZATION_KEY,authKey).apply();
    }

    public void saveAccessToken(String token){
        sharedPreferences.edit().putString(ACCESS_TOKEN, token).apply();
    }

    public void saveRefreshToken(String token){
        sharedPreferences.edit().putString(REFRESH_TOKEN,token).apply();
    }

    public String getAuthorizationCode(){
        return sharedPreferences.getString(AUTHORIZATION_KEY, DEF_VALUE);
    }
    public String getAccessToken(){
        return sharedPreferences.getString(ACCESS_TOKEN, DEF_VALUE);
    }


    public String getRefreshToken(){
        return sharedPreferences.getString(REFRESH_TOKEN,DEF_VALUE);
    }
}
