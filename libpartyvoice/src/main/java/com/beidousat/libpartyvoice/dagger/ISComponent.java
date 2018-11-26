package com.beidousat.libpartyvoice.dagger;


import com.beidousat.libpartyvoice.BaseISApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * author: Hanson
 * date:   2018/1/18
 * describe:
 */
@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AndroidSupportInjectionModule.class,
        AllActivitiesModule.class,
        AllFragmentsModule.class,
        AllServiceModule.class,
        NetworkModule.class,
        DatabaseModule.class
})
public interface ISComponent {
    void inject(BaseISApplication application);
}
