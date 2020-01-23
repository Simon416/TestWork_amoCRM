package com.example.test_amocrm.di;


import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
class ContextModule {

    private Application application;

    ContextModule(Application application){
        this.application = application;
    }

    @Provides
    Application getApplication(){
        return application;
    }

    @Provides
    Context provideContext(){
        return application;
    }


}
