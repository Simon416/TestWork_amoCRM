package com.example.test_amocrm.di;

import android.content.Context;
import com.example.test_amocrm.data.ServerRepository;
import com.example.test_amocrm.data.ServerRepositoryImpl;
import com.example.test_amocrm.utils.StringProvider;
import com.example.test_amocrm.utils.StringProviderImpl;


import dagger.Module;
import dagger.Provides;

@Module
class DealsModule {

    @Provides
    ServerRepository provideRepository(){
        return new ServerRepositoryImpl();

    }

    @Provides
    StringProvider provideStringProvider(Context context){
        return new StringProviderImpl(context);
    }
}
