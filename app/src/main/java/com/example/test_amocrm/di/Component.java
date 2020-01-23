package com.example.test_amocrm.di;

import com.example.test_amocrm.ui.main.main.MainPresenter;
import javax.inject.Singleton;

@Singleton
@dagger.Component(modules = {ContextModule.class, DealsModule.class})
public interface Component {

    MainPresenter getMainPresenter();
}
