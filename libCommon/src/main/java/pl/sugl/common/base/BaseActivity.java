package pl.sugl.common.base;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.jaeger.library.LightStatusBarUtils;
import com.jaeger.library.ToolbarUtil;

import javax.inject.Inject;

import butterknife.ButterKnife;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import pl.sugl.common.annotation.HolderViewStateHelper;
import pl.sugl.common.annotation.StateEntity;
import pl.sugl.common.annotation.ViewState;
import pl.sugl.common.event.NetworkEventSubscriber;
import pl.sugl.common.intr.ILoadingView;
import pl.sugl.common.intr.INetworkListener;
import pl.sugl.common.utils.MyActivityManager;
import pl.sugl.common.utils.ToolbarInstaller;
import pl.sugl.common.widget.CommonStateView;
import pl.sugl.common.widget.TopNoticeView;

/**
 * author: Hanson
 * date:   2018/1/12
 * describe:
 */

public abstract class BaseActivity extends AppCompatActivity implements INetworkListener,
        ILoadingView{
    private TopNoticeView mTopNoticeView;
    protected NetworkEventSubscriber mEventSubscriber;
    protected ToolbarInstaller mToolbarInstaller;

    private StateEntity mStateEntity;
    private CommonStateView mCommonView;
    private boolean isCommonStateViewAttached;
    private boolean isFirstLoading = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adaptTheme();
        if (getContentView() != View.NO_ID) {
            setContentView(getContentView());
        }
        adaptStatusBar();
        MyActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);

        initViews();
        setupToolbar();
        setListener();
        initData();

        onInit();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected void adaptTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void adaptStatusBar() {
        ToolbarUtil.setColorNoTranslucent(this, Color.parseColor("#FFAE00"));
        LightStatusBarUtils.setLightStatusBar(this, false);
    }

    private void onInit() {
        onInitNetworkSubscribe();
        mEventSubscriber.onCreate();
        mTopNoticeView = new TopNoticeView(this);

        initCommonStateView();
    }

    /**
     * 子类可复写改变订阅时间
     */
    public void onInitNetworkSubscribe() {
        mEventSubscriber = new NetworkEventSubscriber(false, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEventSubscriber.onResume();
        loadDataWhenOnResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cancelLoadingRequest();
        mEventSubscriber.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mToolbarInstaller != null) {
            mToolbarInstaller.onDestroy();
        }
        mEventSubscriber.onDestroy();
        MyActivityManager.getInstance().removeActivity(this);
    }

    /**
     * 网络重试回调
     */
    @Override
    public void onNetWorkRetry() {

    }

    @Override
    public void showLoading(String msg) {
        if (isFirstLoading) {
            if (mStateEntity.getHolderView() != null) {
                if (mCommonView.getParent() == null) {
                    ((ViewGroup) mStateEntity.getHolderView().getParent()).addView(mCommonView, mStateEntity.getHolderViewIndex(),
                            mStateEntity.getHolderLayoutParams());
                }
                mCommonView.showLoading();
                if (mStateEntity.getHolderView().getParent() != null) {
                    ((ViewGroup) mStateEntity.getHolderView().getParent()).removeView(mStateEntity.getHolderView());
                }
            }
        }
        isFirstLoading = false;
    }

    @Override
    public void hideLoading() {
        if (mStateEntity.getHolderView() != null) {
            if (mStateEntity.getHolderView().getParent() == null) {
                ((ViewGroup) mCommonView.getParent()).addView(mStateEntity.getHolderView(), mStateEntity.getHolderViewIndex(),
                        mStateEntity.getHolderLayoutParams());
            }
            mCommonView.hideLoading();
            if (mCommonView.getParent() != null && mCommonView.hasNoneState()) {
                ((ViewGroup) mCommonView.getParent()).removeView(mCommonView);
            }
        }
    }

    /**
     * 网络错误提示操作
     */
    @Override
    public void showNetWorkError(boolean isNetworkOk, int code) {
        if (!isNetworkOk && mStateEntity.getHolderView() != null) {
            if (mCommonView.getParent() == null) {
                ((ViewGroup) mStateEntity.getHolderView().getParent()).addView(mCommonView,  mStateEntity.getHolderViewIndex(),
                        mStateEntity.getHolderLayoutParams());
            }
            setGoneViews(ViewState.NETWORK_ERROR, true);
            mCommonView.setupNetworkView(isNetworkOk, code);
            if (mStateEntity.getHolderView().getParent() != null) {
                ((ViewGroup) mStateEntity.getHolderView().getParent()).removeView(mStateEntity.getHolderView());
            }
        } else if (isNetworkOk && mStateEntity.getHolderView() != null) {
            if (mStateEntity.getHolderView().getParent() == null) {
                ((ViewGroup) mCommonView.getParent()).addView(mStateEntity.getHolderView(), mStateEntity.getHolderViewIndex(),
                        mStateEntity.getHolderLayoutParams());
            }
            setGoneViews(ViewState.NETWORK_ERROR, false);
            mCommonView.setupNetworkView(isNetworkOk, code);
            if (mCommonView.getParent() != null && mCommonView.hasNoneState()) {
                ((ViewGroup) mCommonView.getParent()).removeView(mCommonView);
            }
        }
    }

    @Override
    public void cancelLoading(boolean cancelRequest) {
        hideLoading();
        if (cancelRequest) {
            cancelLoadingRequest();
        }
    }

    /**
     * 获取布局文件id
     *
     * @return id
     */

    public ToolbarInstaller getToolbarInstaller() {
        return mToolbarInstaller;
    }

    public void setupToolbarInstaller(ToolbarInstaller toolbarInstaller) {
        mToolbarInstaller = toolbarInstaller;
    }

    private void initCommonStateView() {
        try {
            mStateEntity = HolderViewStateHelper.parseHolderViewState(this, (ViewGroup) getWindow().getDecorView(), getClass());
            if (mStateEntity.getHolderId() != View.NO_ID) {
                mCommonView = new CommonStateView(this);
                mStateEntity.setStateView(mCommonView);
                mCommonView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
                    @Override
                    public void onViewAttachedToWindow(View v) {
                        isCommonStateViewAttached = true;
                    }

                    @Override
                    public void onViewDetachedFromWindow(View v) {
                        isCommonStateViewAttached = false;
                    }
                });
                mCommonView.setOnRetryListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onNetWorkRetry();
                    }
                });
            }
        } catch (Exception e) {
            Log.e("HolderViewState", e.toString());
        }
    }

    private void setGoneViews(ViewState state, boolean gone) {
        if (mStateEntity.getGoneViewMap().containsKey(state)) {
            for (int id : mStateEntity.getGoneViewMap().get(state)) {
                findViewById(id).setVisibility(gone ? View.GONE : View.VISIBLE);
            }
        }
    }

    public abstract @LayoutRes
    int getContentView();

    /**
     * 初始化视图
     */
    public abstract void initViews();

    /**
     * 设置监听器
     */
    public abstract void setListener();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 加载数据(一般用于加载网络数据)
     */
    public abstract void loadDataWhenOnResume();

    /**
     * 取消loading时的操作
     */
    public abstract void cancelLoadingRequest();

    public void setupToolbar() {

    }
}
