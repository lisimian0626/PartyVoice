package com.beidousat.libpartyvoice.dagger;



import com.beidousat.libpartyvoice.dagger.scope.ActivityScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import pl.sugl.common.base.BaseActivity;

/**
 * author: Hanson
 * date:   2018/1/18
 * describe:
 */

@ActivityScope
@Subcomponent(modules = {AndroidInjectionModule.class})
public interface BaseActivityComponent extends AndroidInjector<BaseActivity> {
    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseActivity> {

    }
}
