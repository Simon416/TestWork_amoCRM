package com.example.test_amocrm;

import android.app.Application;
import com.example.test_amocrm.di.AppGraph;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppGraph.init(this);
    }
}
