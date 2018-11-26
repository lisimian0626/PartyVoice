package com.beidousat.libpartyvoice.dagger;

import android.app.Service;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

/**
 * author: Hanson
 * date:   2018/1/18
 * describe:
 */

@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseServiceComponent extends AndroidInjector<Service> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<Service> {

    }
}
