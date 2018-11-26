package pl.sugl.common.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import pl.sugl.common.annotation.HolderViewState;
import pl.sugl.common.annotation.HolderViewStateHelper;
import pl.sugl.common.annotation.StateEntity;
import pl.sugl.common.widget.CommonStateView;

public abstract class BaseDialogFragment extends DialogFragment {
    protected View mRootView;

    private CommonStateView mCommonView;
    private boolean isFirstLoading = true;
    private boolean isCommonStateViewAttached;
    private StateEntity mStateEntity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentView(), container, false);
        ButterKnife.bind(this, mRootView);
        initCommonStateView();
        initData();
        initViews();
        setListeners();

        return mRootView;
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
            }
        } catch (Exception e) {
            Log.e("HolderViewState", e.toString());
        }
    }

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


    public void cancelLoading(boolean cancelRequest) {
        hideLoading();
        if (cancelRequest) {
            cancelLoadingRequest();
        }
    }

    public abstract @LayoutRes int getContentView();

    protected abstract void initData();

    protected abstract void setListeners();

    protected abstract void initViews();


    /**
     * 取消loading时的操作
     */
    public abstract void cancelLoadingRequest();
}
