package com.example.test_amocrm.ui.main.main;

import android.net.Uri;
import android.text.TextUtils;

import com.example.test_amocrm.BuildConfig;
import com.example.test_amocrm.data.StoreKeys;
import com.example.test_amocrm.data.deal.Deal;
import com.example.test_amocrm.data.pipeline.Pipeline;
import com.example.test_amocrm.data.pipeline.Pipelines;
import com.example.test_amocrm.data.pipeline.Status;
import com.example.test_amocrm.utils.ClientParams;
import com.example.test_amocrm.utils.CodeType;
import com.example.test_amocrm.data.deal.Deals;
import com.example.test_amocrm.data.ServerRepository;
import com.example.test_amocrm.data.oauth.RequestOnNewToken;
import com.example.test_amocrm.data.oauth.RequestOnToken;
import com.example.test_amocrm.utils.StringProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import moxy.InjectViewState;
import moxy.MvpPresenter;
import okhttp3.HttpUrl;
import retrofit2.HttpException;


@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    private final static String HOST = "takeumbrella.ru";
    private final static String Scheme = "http";
    private final static String CODE = "code";
    private static final String ERROR_CODE = "error";

    private static final String URL_AMO_CRM = "https://www.amocrm.ru/oauth/";
    private static final String STATE = "state";
    private static final String MODE = "mode";
    private static final String CLIENT_ID = "client_id";

    private ServerRepository serverRepository;
    private StoreKeys storeKeys;
    private CompositeDisposable disposable = new CompositeDisposable();
    private StringProvider stringProvider;

    @Inject
    MainPresenter(StoreKeys storeKeys, ServerRepository serverRepository, StringProvider stringProvider) {
        this.stringProvider = stringProvider;
        this.storeKeys = storeKeys;
        this.serverRepository = serverRepository;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        updateState();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    void updateState() {
        if (storeKeys.getAuthorizationCode().equals(StoreKeys.DEF_VALUE)) {
            getViewState().startPageOnBrowser(buildUrl());
        } else if (storeKeys.getAccessToken().equals(StoreKeys.DEF_VALUE)) {
            getTokenKey();
        } else {
            loadData(storeKeys.getAccessToken());
        }
    }

    void returnAuthorizationKeyFromServer(Uri data) {
        if (!TextUtils.isEmpty(data.getScheme())) {
            if (HOST.equals(data.getHost()) && (Scheme.equals(data.getScheme()))) {
                String code = data.getQueryParameter(CODE);
                String errorCode = data.getQueryParameter(ERROR_CODE);
                String state = data.getQueryParameter(STATE);
                if (!TextUtils.isEmpty(errorCode)) {
                    CodeType codeType = CodeType.getCodeType(errorCode);
                    switch (codeType) {
                        case CODE_110:
                            getViewState().showError(stringProvider.getMessageErrorCode110());
                            break;
                        case CODE_111:
                            getViewState().showError(stringProvider.getMessageErrorCode111());
                            break;
                        case CODE_112:
                            getViewState().showError(stringProvider.getMessageErrorCode112());
                            break;
                        case CODE_113:
                            getViewState().showError(stringProvider.getMessageErrorCode113());
                            break;
                        case CODE_101:
                            getViewState().showError(stringProvider.getMessageErrorCode101());
                            break;
                        default:
                            throw new IllegalArgumentException();
                    }
                    return;
                }
                assert state != null;
                if (!TextUtils.isEmpty(state) && !TextUtils.isEmpty(code) && state.equals(ClientParams.STATE)) {
                    getViewState().showMainScreen();
                    storeKeys.saveAuthorizationKey(code);
                    updateState();
                } else
                    getViewState().showError(stringProvider.getMessageErrorServer());
            }
        }
    }

    void onBtnRepeatAuthorizationClick() {
        getViewState().startPageOnBrowser(buildUrl());
    }

    private void getTokenKey() {

        RequestOnToken requestOnToken = new RequestOnToken(
                BuildConfig.ClIENT_ID,
                BuildConfig.CLIENT_SECRET,
                ClientParams.AUTHORIZATION_TYPE,
                storeKeys.getAuthorizationCode(),
                ClientParams.REDIRECT_URI
        );
        disposable.add(serverRepository.requestToken(requestOnToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((__) -> getViewState().showProgress())
                .doFinally(() -> getViewState().hideProgress())
                .subscribe(oAuthToken -> {
                            updateTokens(oAuthToken.getAccessToken(), oAuthToken.getRefreshToken());
                            loadData(oAuthToken.getAccessToken());
                        },
                        throwable -> getViewState().showError(onErrorProcess(throwable)))
        );
    }

    private void getUpdateTokens() {

        RequestOnNewToken requestOnNewToken = new RequestOnNewToken(
                BuildConfig.ClIENT_ID,
                BuildConfig.CLIENT_SECRET,
                ClientParams.REFRESH_TOKEN_TYPE,
                storeKeys.getRefreshToken(),
                ClientParams.REDIRECT_URI

        );
        disposable.add(serverRepository.requestNewToken(requestOnNewToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(oAuthToken -> updateTokens(oAuthToken.getAccessToken(), oAuthToken.getRefreshToken()),
                        throwable -> getViewState().showError(onErrorProcess(throwable)))
        );
    }

    private void updateTokens(String accessToken, String refreshToken) {
        storeKeys.saveAccessToken(accessToken);
        storeKeys.saveRefreshToken(refreshToken);
    }

    private void loadData(String token) {
        getViewState().showDeals();
        disposable.add(Single.zip(serverRepository.getDeals(token).subscribeOn(Schedulers.io()),
                serverRepository.getPipelines(token).subscribeOn(Schedulers.io()), this::getListDealUi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((__) -> getViewState().showProgress())
                .doFinally(() -> getViewState().hideProgress())
                .subscribe(listDealUi -> getViewState().setDeals(listDealUi),
                        throwable -> getViewState().showError(onErrorProcess(throwable))
                )
        );
    }

    private List<DealUi> getListDealUi(Deals deals, Pipelines pipelines) {

        List<DealUi> listDealUi = new ArrayList<>();
        for (Deal deal : deals.getDeals()) {
            for (Pipeline pipeline : pipelines.getListPipeline()) {
                if (deal.getPipelineId() == pipeline.getId()) {
                    for (Status status : pipeline.getListStatuses()) {
                        if (deal.getStatusId() == status.getId()) {
                            listDealUi.add(
                                    new DealUi(
                                            deal.getName(),
                                            deal.getSale(),
                                            deal.getDataCreate(),
                                            status.getStatusName()
                                    )
                            );
                        }
                    }
                }
            }
        }
        return listDealUi;
    }

    private HttpUrl buildUrl() {
        return Objects.requireNonNull(HttpUrl.parse(URL_AMO_CRM))
                .newBuilder()
                .addQueryParameter(CLIENT_ID, BuildConfig.ClIENT_ID)
                .addQueryParameter(STATE, ClientParams.STATE)
                .addQueryParameter(MODE, ClientParams.MODE)
                .build();
    }

    private String onErrorProcess(Throwable e) {
        if (e instanceof HttpException) {
            HttpException exception = (HttpException) e;
            int errorCode = exception.code();
            if ((errorCode >= 400) && (errorCode <= 404) ||
                    (errorCode >= 500) && (errorCode) <= 503) {
                CodeType codeType = CodeType.getCodeType(String.valueOf(errorCode));
                switch (codeType) {
                    case CODE_400:
                        getViewState().showErrorAuthorization();
                        return stringProvider.getMessageErrorCode() + errorCode + stringProvider.getMessageErrorCode400();
                    case CODE_401:
                        getUpdateTokens();
                        return stringProvider.getMessageErrorCode() + errorCode + stringProvider.getMessageErrorCode401();
                    case CODE_403:
                        return stringProvider.getMessageErrorCode() + errorCode + stringProvider.getMessageErrorCode403();
                    case CODE_404:
                        return stringProvider.getMessageErrorCode() + errorCode + stringProvider.getMessageErrorCode404();
                    case CODE_500:
                        return stringProvider.getMessageErrorCode() + errorCode + stringProvider.getMessageErrorCode500();
                    case CODE_502:
                        return stringProvider.getMessageErrorCode() + errorCode + stringProvider.getMessageErrorCode502();
                    case CODE_503:
                        return stringProvider.getMessageErrorCode() + errorCode + stringProvider.getMessageErrorCode503();
                    default:
                        throw new IllegalArgumentException();
                }
            }
            if (errorCode < 200 || errorCode > 204) {
                return stringProvider.getMessageErrorCode() + errorCode + stringProvider.getMessageUndefinedError();
            } else {
                return exception.getLocalizedMessage();
            }
        } else {
            getViewState().showErrorLoadData();
            return e.getLocalizedMessage();
        }
    }
}
