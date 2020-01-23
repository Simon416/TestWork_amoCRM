package com.example.test_amocrm.ui.main.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.test_amocrm.ui.main.login.LoginActivity;
import com.example.test_amocrm.R;
import com.example.test_amocrm.data.StoreKeys;
import com.example.test_amocrm.di.AppGraph;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import okhttp3.HttpUrl;

import android.view.View;

import javax.inject.Inject;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    private final static String URL_AMO_CRM = "url";
    private final static int REQUEST_COD = 555;

    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout errorLayout;
    private LinearLayout authorizationLayout;

    @InjectPresenter
    MainPresenter presenter;

    @ProvidePresenter
    MainPresenter providePresenter() {
        return AppGraph.getInstance().getMainPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        swipeRefresh.setVisibility(View.GONE);
    }

    @Override
    public void showDeals() {
        authorizationLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorLoadData() {
        errorLayout.setVisibility(View.VISIBLE);
        swipeRefresh.setVisibility(View.GONE);
    }

    @Override
    public void showMainScreen() {
        authorizationLayout.setVisibility(View.GONE);
        swipeRefresh.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgress() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void startPageOnBrowser(HttpUrl authorizeUrl) {
        Intent intent = LoginActivity.getStartIntent(this);
        intent.putExtra(URL_AMO_CRM, authorizeUrl.toString());
        startActivityForResult(intent, REQUEST_COD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((resultCode == RESULT_OK) && (requestCode == REQUEST_COD) && (data != null)) {
            presenter.returnAuthorizationKeyFromServer(Uri.parse(
                    data.getStringExtra(LoginActivity.getRedirectUrl())
            ));
        }
    }

    @Override
    public void showErrorAuthorization() {
        swipeRefresh.setVisibility(View.GONE);
        authorizationLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void setDeals(List<DealUi> listDealUi) {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        DealAdapter dealAdapter = new DealAdapter(listDealUi);
        recyclerView.setAdapter(dealAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public static String getUrlAmoCrm() {
        return URL_AMO_CRM;
    }

    private void init() {
        swipeRefresh = findViewById(R.id.swipeRefreshView);
        errorLayout = findViewById(R.id.errorLayout);
        authorizationLayout = findViewById(R.id.authorizationLayout);

        Button btnRepeatAuthorizationView = findViewById(R.id.btnRepeatAuthorizationView);
        Button btnRepeatView = findViewById(R.id.btnRepeatView);

        btnRepeatView.setOnClickListener(v -> presenter.updateState());
        btnRepeatAuthorizationView.setOnClickListener(v -> presenter.onBtnRepeatAuthorizationClick());

        swipeRefresh.setOnRefreshListener(() -> presenter.updateState());
    }

}
