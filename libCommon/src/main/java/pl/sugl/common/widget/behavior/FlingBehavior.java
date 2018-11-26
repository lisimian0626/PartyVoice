package pl.sugl.common.widget.behavior;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public final class FlingBehavior extends AppBarLayout.Behavior {
    private int mStartedScrollType = -1;
    private boolean mSkipNextStop;

    private static final String TAG = FlingBehavior.class.getSimpleName();

    public FlingBehavior() {

    }

    public FlingBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    public boolean onStartNestedScroll(CoordinatorLayout parent, AppBarLayout child, View directTargetChild, View target, int nestedScrollAxes, int type) {
//        if (mStartedScrollType != -1) {
//            onStopNestedScroll(parent, child, target, mStartedScrollType);
//            mSkipNextStop = true;
//        }
//        mStartedScrollType = type;
//        return super.onStartNestedScroll(parent, child, directTargetChild, target, nestedScrollAxes, type);
//    }
//
//    @Override
//    public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout abl, View target, int type) {
//        if (mSkipNextStop) {
//            mSkipNextStop = false;
//            return;
//        }
//        if (mStartedScrollType == -1) {
//            return;
//        }
//        mStartedScrollType = -1;
//        // Always pass TYPE_TOUCH, because want to snap even after fling
//        super.onStopNestedScroll(coordinatorLayout, abl, target, ViewCompat.TYPE_TOUCH);
//    }
}