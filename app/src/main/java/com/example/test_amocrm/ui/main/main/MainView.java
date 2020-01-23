package com.example.test_amocrm.ui.main.main;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import okhttp3.HttpUrl;

@StateStrategyType(OneExecutionStateStrategy.class)
public interface MainView extends MvpView {

    void showMainScreen();
    void showError(String error);
    void showProgress();
    void hideProgress();
    void startPageOnBrowser(HttpUrl authorizeUrl);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showErrorAuthorization();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showDeals();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showErrorLoadData();

    @StateStrategyType(AddToEndSingleStrategy.class)
    void setDeals(List<DealUi> listDealUi);


}
