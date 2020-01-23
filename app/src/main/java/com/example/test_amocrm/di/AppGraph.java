package com.example.test_amocrm.di;

import android.app.Application;


public class AppGraph {

    private static Component component;

    public static Component getInstance() {
        return component;
    }

    public static void init(Application app) {
        component = DaggerComponent
                .builder()
                .contextModule(new ContextModule(app))
                .build();
    }
}

