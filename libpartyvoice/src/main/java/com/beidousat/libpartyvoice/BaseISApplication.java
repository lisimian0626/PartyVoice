package com.beidousat.libpartyvoice;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.beidousat.libpartyvoice.bussiness.ApiService;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.facebook.stetho.Stetho;
import com.liulishuo.filedownloader.FileDownloader;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.HasServiceInjector;
import pl.sugl.common.base.ApplicationInjector;
import pl.sugl.common.utils.ISingContextHolder;
import pl.sugl.common.utils.MyActivityManager;

/**
 * author: Lism
 * date:   2018/11/12
 * describe:
 */

public class BaseISApplication extends MultiDexApplication implements HasActivityInjector, HasServiceInjector{

    @Inject
    DispatchingAndroidInjector<Activity> mActivityInjector;
    @Inject
    DispatchingAndroidInjector<Service> mServiceInjector;
    @Inject
    ApiService mApiService;
    private int mActCount;
    private static boolean isRunInBackground = false;

//    AsyncTask mCopyAnimTask = new AsyncTask() {
//        @Override
//        protected Object doInBackground(Object[] objects) {
//            FileUtils.copyAssets(BaseISApplication.this, "anim", Constant.File.getAnimDir());
//            return null;
//        }
//    };

    @Override
    public void onCreate() {
        super.onCreate();
        ISingContextHolder.setContext(this);

//        DaggerISComponent.builder()
////                .databaseModule(new DatabaseModule(this))
////                .thirdPartyLoginModule(new ThirdPartyLoginModule(this))
//                .build()
//                .inject(this);
        //初始化Stetho调试工具
        Stetho.initializeWithDefaults(this);
        //dagger2 初始化设置
        handleLifeCycle();
        //设置主页面类
        MyActivityManager.getInstance().setHomeClass(ISHomeActivity.class);
        FileDownloader.setup(this);

//        initRongYun();
//
//        initWb();
//
//        initMapSdk();

//        initDefaultAnim();

        initBugly();
    }

    private void handleLifeCycle() {
        registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//                ApplicationInjector.handleInjectActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActCount++;
                if (isRunInBackground) {
                    isRunInBackground = false;
//                    EventBusHelper.sendRunStateEvent(isRunInBackground);
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                mActCount--;
                if (mActCount == 0) {
                    isRunInBackground = true;
//                    EventBusHelper.sendRunStateEvent(isRunInBackground);
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void initBugly() {
//        CrashReport.initCrashReport(getApplicationContext(), "3fbc86a164", false);
//        CrashReport.initCrashReport(this);
//        CrashReport.testNativeCrash();
    }

//    private void initDefaultAnim() {
//        mCopyAnimTask.execute();
//    }


//    //初始化微博
//    private void initWb() {
//        AuthInfo authInfo = new AuthInfo(ISingContextHolder.getContext(), ThirdPartyConstant.WB_APP_ID,
//                ThirdPartyConstant.WB_REDIRECT_URL, ThirdPartyConstant.WB_SCOPE);
//        WbSdk.install(ISingContextHolder.getContext(), authInfo);
//    }

//    /**
//     * 初始化融云服务
//     */
//    public void initRongYun() {
//        RongIM.init(this);
//        //todo 位置、用户信息提供者
//        RongIM.setUserInfoProvider(new UserInfoEngine(mApiService), true);
//        ISUser user = MyUserInfo.getInstance().getMyself();
//        if (user != null) {
//            mIMUtils.initRongIM(this);
//        }
//    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mActivityInjector;
    }

//    @Override
//    public void initMapSdk() {
//        //初始化百度地图
//        SDKInitializer.initialize(this);
//    }

    public static boolean isRunInBackground() {
        return isRunInBackground;
    }

    @Override
    public AndroidInjector<Service> serviceInjector() {
        return mServiceInjector;
    }
}
