package com.beidousat.libpartyvoice.dagger;



import com.beidousat.libpartyvoice.dagger.scope.FragmentScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import pl.sugl.common.base.BaseFragment;

/**
 * author: Hanson
 * date:   2018/1/18
 * describe:
 */
@FragmentScope
@Subcomponent(modules = {AndroidSupportInjectionModule.class})
public interface BaseFragmentComponent extends AndroidInjector<BaseFragment> {

    @Subcomponent.Builder
    abstract class Builder extends AndroidInjector.Builder<BaseFragment> {

    }
}
