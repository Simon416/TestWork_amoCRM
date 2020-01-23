package com.example.test_amocrm.data;

import com.example.test_amocrm.data.deal.Deals;
import com.example.test_amocrm.data.pipeline.Pipelines;
import com.example.test_amocrm.data.oauth.OAuthToken;
import com.example.test_amocrm.data.oauth.RequestOnNewToken;
import com.example.test_amocrm.data.oauth.RequestOnToken;

import io.reactivex.Single;

public interface ServerRepository {

    Single<OAuthToken> requestToken(RequestOnToken requestOnToken);
    Single<OAuthToken> requestNewToken(RequestOnNewToken requestOnNewToken);
    Single<Deals> getDeals(String token);
    Single<Pipelines> getPipelines(String token);

}
