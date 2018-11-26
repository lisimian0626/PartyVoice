package pl.sugl.common.annotation;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

public class StateEntity {
    private int mHolderId;
    private int mHolderViewIndex;
    private Map<ViewState, int[]> mGoneViewMap = new HashMap<>();

    private View mHolderView;
    private View mStateView;
    private ViewGroup.LayoutParams mHolderLayoutParams;

    public StateEntity() {
        mHolderId = View.NO_ID;
    }

    public int getHolderId() {
        return mHolderId;
    }

    public void setHolderId(int holderId) {
        mHolderId = holderId;
    }

    public int getHolderViewIndex() {
        return mHolderViewIndex;
    }

    public void setHolderViewIndex(int holderViewIndex) {
        mHolderViewIndex = holderViewIndex;
    }

    public Map<ViewState, int[]> getGoneViewMap() {
        return mGoneViewMap;
    }

    public void setGoneViewMap(Map<ViewState, int[]> goneViewMap) {
        mGoneViewMap = goneViewMap;
    }

    public View getHolderView() {
        return mHolderView;
    }

    public void setHolderView(View holderView) {
        mHolderView = holderView;
    }

    public View getStateView() {
        return mStateView;
    }

    public void setStateView(View stateView) {
        mStateView = stateView;
    }

    public ViewGroup.LayoutParams getHolderLayoutParams() {
        return mHolderLayoutParams;
    }

    public void setHolderLayoutParams(ViewGroup.LayoutParams holderLayoutParams) {
        mHolderLayoutParams = holderLayoutParams;
    }
}
