package pl.sugl.common.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beidousat.libcommon.R;

import butterknife.ButterKnife;

/**
 * author: Hanson
 * date:   2018/1/12
 * describe:
 */

public class TopNoticeView extends RelativeLayout {
    private static final long ANIM_DURATION = 500;
    private static final long ANIM_HOLD_TIME = 2000;
    private View mRootView;
    private AnimatorSet mAnims;
    private AppCompatActivity mActivity;
    TextView mTextView;

    public TopNoticeView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.widget_top_notice, this, true);
        mTextView=mRootView.findViewById(R.id.tv_content);
        measure(0, 0);
    }

    public TopNoticeView setViewColor(int color) {
        mRootView.setBackgroundColor(color);

        return this;
    }

    public TopNoticeView setText(String str) {
        if (!TextUtils.isEmpty(str)) {
            mTextView.setText(str);
        }

        return this;
    }

    public void attach(AppCompatActivity activity) {
        mActivity = activity;
        if (activity != null) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView()
                    .findViewById(android.R.id.content).getParent().getParent();
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, getMeasuredHeight());
            if (this.getParent() != null) {
                ((ViewGroup) this.getParent()).removeView(this);
            }
            decorView.addView(this, params);

            startPopAnimation();
        }
    }

    public void detach() {
        if (mActivity != null) {
            ViewGroup decorView = (ViewGroup) mActivity.getWindow().getDecorView();
            decorView.removeView(this);
            mActivity = null;
        }
    }

    public void startPopAnimation() {
        if (mAnims != null && mAnims.isRunning()) {
            mAnims.cancel();
        }

        mAnims = new AnimatorSet();
        ObjectAnimator openAnim = ObjectAnimator.ofFloat(this, "translationY", -getMeasuredHeight(), 0);
        openAnim.setDuration(ANIM_DURATION);
        ObjectAnimator closeAnim = ObjectAnimator.ofFloat(this, "translationY", 0, -getMeasuredHeight());
        closeAnim.setStartDelay(ANIM_HOLD_TIME);

        mAnims.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                detach();
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
                detach();
            }
        });
        mAnims.play(openAnim).before(closeAnim);
        mAnims.start();
    }
}
