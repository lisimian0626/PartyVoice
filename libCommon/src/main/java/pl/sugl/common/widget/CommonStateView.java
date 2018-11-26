package pl.sugl.common.widget;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beidousat.libcommon.R;

/**
 * author: Hanson
 * date:   2018/3/15
 * describe:
 */

public class CommonStateView extends ConstraintLayout {
    private View mRootView;

    ConstraintLayout mViewNetwork;
    ImageView mIvImage;
    TextView mTvMessage;
    TextView mTvRetry;
    LinearLayout mViewLoading;

    private int mCurrentState = STATE_NONE;

    private static final int STATE_NONE = 0x0;
    private static final int STATE_NETWORK = 0x01;
    private static final int STATE_LOADING = 0x02;

    public CommonStateView(Context context) {
        this(context, null);
    }

    public CommonStateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CommonStateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mRootView = View.inflate(context, R.layout.layout_network_state_view, this);
        mViewNetwork=mRootView.findViewById(R.id.view_network);
        mIvImage=mRootView.findViewById(R.id.tv_message);
        mTvMessage=mRootView.findViewById(R.id.tv_message);
        mTvRetry=mRootView.findViewById(R.id.tv_retry);
        mViewLoading=mRootView.findViewById(R.id.view_loading);
    }

    public void setupNetworkView(String msg) {
        mCurrentState |= STATE_NETWORK;
        mViewNetwork.setVisibility(VISIBLE);
        mViewLoading.setVisibility(GONE);
        mTvMessage.setText(TextUtils.isEmpty(msg) ? "" : msg);
    }

    public void setupNetworkView(boolean isNetworkOk, int code) {
        if (isNetworkOk) {
            mCurrentState ^= STATE_NETWORK;
            mViewNetwork.setVisibility(GONE);
        } else {
            mCurrentState |= STATE_NETWORK;
            mViewNetwork.setVisibility(VISIBLE);
        }
//        Log.i("commonstateview", mCurrentState+"");
        mViewNetwork.setVisibility(VISIBLE);
        mViewLoading.setVisibility(GONE);
    }

    public void setOnRetryListener(View.OnClickListener listener) {
        mTvRetry.setOnClickListener(listener);
    }

    public void showLoading() {
        mCurrentState |= STATE_LOADING;
        mViewNetwork.setVisibility(GONE);
        mViewLoading.setVisibility(VISIBLE);
//        Log.i("commonstateview", mCurrentState+"");
    }

    public void hideLoading() {
        mCurrentState ^= STATE_LOADING;
        mViewLoading.setVisibility(GONE);
//        Log.i("commonstateview", mCurrentState+"");
    }

    public boolean hasNoneState() {
        return mCurrentState == STATE_NONE;
    }
}
