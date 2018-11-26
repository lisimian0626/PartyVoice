package pl.sugl.common.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import pl.sugl.common.annotation.HolderViewStateHelper;
import pl.sugl.common.annotation.StateEntity;
import pl.sugl.common.annotation.ViewState;
import pl.sugl.common.event.NetworkEventSubscriber;
import pl.sugl.common.intr.ILoadingView;
import pl.sugl.common.intr.INetworkListener;
import pl.sugl.common.widget.CommonStateView;

/**
 * author: Hanson
 * date:   2018/1/12
 * describe:
 */

public abstract class BaseFragment extends Fragment implements ILoadingView, INetworkListener {
    protected View mRootView;
    protected BaseActivity mAttachedAct;
    protected NetworkEventSubscriber mEventSubscriber;

    private StateEntity mStateEntity;
    private CommonStateView mCommonView;
    private boolean isCommonStateViewAttached;
    private boolean isFirstLoading = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        adapterStatusBar();
        if (mRootView == null) {
            mRootView = inflater.inflate(getContentView(), container, false);
            ButterKnife.bind(this, mRootView);

            initViews();
            setListeners();
            initData();
        }

        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        onInit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAttachedAct = (BaseActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAttachedAct = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDataWhenOnResume();
        mEventSubscriber.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        cancelRequestWhenOnPause();
        mEventSubscriber.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mEventSubscriber.onDestroy();
    }

    private void onInit() {
        onInitNetworkSubscribe();
        mEventSubscriber.onCreate();

        initCommonStateView();
    }

    private void initCommonStateView() {
        try {
            mStateEntity = HolderViewStateHelper.parseHolderViewState(getContext(), (ViewGroup) mRootView, getClass());
            if (mStateEntity.getHolderId() != View.NO_ID) {
                mCommonView = new CommonStateView(getContext());
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
                mRootView.findViewById(id).setVisibility(gone ? View.GONE : View.VISIBLE);
            }
        }
    }

    public void onInitNetworkSubscribe() {
        mEventSubscriber = new NetworkEventSubscriber(false, this);
    }

    @Override
    public void showLoading(String msg) {
        if (mStateEntity.getHolderView() != null) {
            mCommonView.showLoading();
            if (isFirstLoading) {
                if (mCommonView.getParent() == null) {
                    ((ViewGroup) mStateEntity.getHolderView().getParent()).addView(mCommonView, mStateEntity.getHolderViewIndex(),
                            mStateEntity.getHolderLayoutParams());
                }
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
            mCommonView.hideLoading();

            if (mStateEntity.getHolderView().getParent() == null && mCommonView.hasNoneState()) {
                ((ViewGroup) mCommonView.getParent()).addView(mStateEntity.getHolderView(), mStateEntity.getHolderViewIndex(),
                        mStateEntity.getHolderLayoutParams());
            }
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
//        if (this.getClass().getSimpleName().equals("FmMsgComment")) {
//            Log.d("commonstateview", "isNetworkOk=" + isNetworkOk);
//        }
        if (!isNetworkOk && mStateEntity.getHolderView() != null) {
            setGoneViews(ViewState.NETWORK_ERROR, true);
            mCommonView.setupNetworkView(isNetworkOk, code);

            if (mCommonView.getParent() == null) {
                ((ViewGroup) mStateEntity.getHolderView().getParent()).addView(mCommonView, mStateEntity.getHolderViewIndex(),
                        mStateEntity.getHolderLayoutParams());
            }

            if (mStateEntity.getHolderView().getParent() != null) {
                ((ViewGroup) mStateEntity.getHolderView().getParent()).removeView(mStateEntity.getHolderView());
            }
        } else if (isNetworkOk && mStateEntity.getHolderView() != null) {
            setGoneViews(ViewState.NETWORK_ERROR, false);
            mCommonView.setupNetworkView(isNetworkOk, code);
            if (mStateEntity.getHolderView().getParent() == null) {
                ((ViewGroup) mCommonView.getParent()).addView(mStateEntity.getHolderView(), mStateEntity.getHolderViewIndex(),
                        mStateEntity.getHolderLayoutParams());
            }
            if (mCommonView.getParent() != null && mCommonView.hasNoneState()) {
                ((ViewGroup) mCommonView.getParent()).removeView(mCommonView);
            }
        }
    }

    @Override
    public void onNetWorkRetry() {

    }

    public View getRootView() {
        return mRootView;
    }

    /**
     * 获取布局文件id
     *
     * @return id
     */
    public abstract @LayoutRes
    int getContentView();

    /**
     * 初始化视图
     */
    public abstract void initViews();

    /**
     * 设置监听器
     */
    public abstract void setListeners();

    /**
     * 初始化数据
     */
    public abstract void initData();

    /**
     * 加载数据(一般用于加载网络数据)
     */
    public abstract void loadDataWhenOnResume();


    @Override
    public void cancelLoading(boolean cancelRequest) {
        hideLoading();
        if (cancelRequest) {
            cancelRequestWhenOnPause();
        }
    }

    public void adapterStatusBar() {

    }

    /**
     * 取消loading时的操作
     */
    public abstract void cancelRequestWhenOnPause();
}
