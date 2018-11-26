package com.beidousat.libpartyvoice.dagger;


import com.beidousat.libpartyvoice.ISHomeActivity;
import com.beidousat.libpartyvoice.dagger.scope.ActivityScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * author: Hanson
 * date:   2018/1/18
 * describe:
 */

@Module(subcomponents = {BaseActivityComponent.class})
public abstract class AllActivitiesModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract ISHomeActivity contributeHomeActivityInjector();

}
