package pl.sugl.common.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beidousat.libcommon.R;
import com.jaeger.library.StatusBarUtil;
import com.jaeger.library.ToolbarUtil;

import pl.sugl.common.base.BaseFragment;

/**
 * author: Hanson
 * date:   2018/1/19
 * describe:
 */

public class ToolbarInstaller {
    private AppCompatActivity mActivity;
    private Toolbar mToolbar;
    private SparseArray<View> mViewCache;
    private GestureDetector mGestureDetector;
    private OnDoubleTapListener mOnDoubleTapListener;
    private int mToolbarId = com.jaeger.library.R.id.toolbar;
    private int mToolbarBack = com.jaeger.library.R.id.toolbar_back;
    private int mToolbarTitle = com.jaeger.library.R.id.toolbar_title;
    private int mToolbarRight = com.jaeger.library.R.id.toolbar_right;


    private static final int DRAW_PADDING = 20;

    public ToolbarInstaller(@NonNull AppCompatActivity activity, @IdRes int toolbarRes) throws IllegalArgumentException {
        mActivity = activity;
        mToolbar = (Toolbar) mActivity.findViewById(toolbarRes);
        mViewCache = new SparseArray<>();

        if (mToolbar == null) {
            throw new IllegalArgumentException("cannot find toolbar in " + activity.getClass().getSimpleName());
        }

        setupDoubleTapHandler();
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public ToolbarInstaller(@NonNull AppCompatActivity activity) throws IllegalArgumentException {
        mActivity = activity;
        mToolbar = (Toolbar) mActivity.findViewById(mToolbarId);
        mViewCache = new SparseArray<>();

        if (mToolbar == null) {
            throw new IllegalArgumentException("cannot find toolbar in " + activity.getClass().getSimpleName());
        }

        setupDoubleTapHandler();
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public ToolbarInstaller(@NonNull BaseFragment fragment, @IdRes int toolbarRes) throws IllegalArgumentException {
        mActivity = (AppCompatActivity) fragment.getActivity();
        mToolbar = (Toolbar) fragment.getRootView().findViewById(toolbarRes);
        mViewCache = new SparseArray<>();

        if (mToolbar == null) {
            throw new IllegalArgumentException("cannot find toolbar in " + fragment.getClass().getSimpleName());
        }

        setupDoubleTapHandler();
        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void setupDoubleTapHandler() {
        mToolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mGestureDetector.onTouchEvent(event);
            }
        });
        mGestureDetector = new GestureDetector(mActivity, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mOnDoubleTapListener != null) {
                    mOnDoubleTapListener.onDoubleTap(e);
                    return true;
                }
                return super.onDoubleTap(e);
            }
        });
    }

    public ToolbarInstaller setTitle(String text) {
        return setText(mToolbarTitle, text);
    }

    public ToolbarInstaller setTitle(@StringRes int text) {
        return setText(mToolbarTitle, text);
    }

    public ToolbarInstaller setBackDrawable(@DrawableRes int drawableRes) {
        return setTextViewDrawable(mToolbarBack, drawableRes);
    }


    public ToolbarInstaller setBackDrawable(@DrawableRes int drawableRes, DrawableType type) {
        return setTextViewDrawable(mToolbarBack, drawableRes, type, DRAW_PADDING);
    }

    public ToolbarInstaller setText(@IdRes int id, String text) {
        assert (text != null);
        TextView textView = (TextView) getViewById(id);
        if (textView != null) {
            textView.setText(text);
        }

        return this;
    }

    public ToolbarInstaller setText(@IdRes int id, @StringRes int text) {
        TextView textView = (TextView) getViewById(id);
        textView.setText(text);

        return this;
    }

    public ToolbarInstaller setTextViewDrawable(@IdRes int res, @DrawableRes int drawableRes) {
        return setTextViewDrawable(res, drawableRes, DrawableType.LEFT, DRAW_PADDING);
    }

    public ToolbarInstaller setTextViewDrawable(@IdRes int id, @DrawableRes int drawableRes, DrawableType type, int pad) {
        TextView textView = (TextView) getViewById(id);
        textView.setCompoundDrawablePadding(pad);
        Drawable drawable = mActivity.getResources().getDrawable(drawableRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        switch (type) {
            case TOP:
                textView.setCompoundDrawables(null, drawable, null, null);
                break;
            case LEFT:
                textView.setCompoundDrawables(drawable, null, null, null);
                break;
            case RIGHT:
                textView.setCompoundDrawables(null, null, drawable, null);
                break;
            case BOTTOM:
                textView.setCompoundDrawables(null, null, null, drawable);
                break;
            default:
                break;
        }

        return this;
    }

    public ToolbarInstaller setImage(@IdRes int id, @DrawableRes int drawableRes) {
        ImageView imageView = (ImageView) getViewById(id);
        imageView.setImageResource(drawableRes);

        return this;
    }

    public ToolbarInstaller setTextViewBackground(@IdRes int id, @DrawableRes int drawableRes) {
        TextView textView = (TextView) getViewById(id);
        textView.setBackgroundResource(drawableRes);

        return this;
    }

    public ToolbarInstaller setDefaultBackListener(@IdRes int id) {
        View view = getViewById(mActivity, id);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

        return this;
    }

    public ToolbarInstaller setDefaultBackListener() {
        View view = getViewById(mActivity, mToolbarBack);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });

        return this;
    }

    public ToolbarInstaller setOnClickListener(@IdRes int id, View.OnClickListener listener) {
        assert (listener != null);
        View view = getViewById(id);
        view.setOnClickListener(listener);

        return this;
    }

    public ToolbarInstaller setToolbarBackgroundResource(@ColorRes int color) {
        mToolbar.setBackgroundResource(color);

        return this;
    }

    public ToolbarInstaller setBackgroundResource(@IdRes int id, @DrawableRes int color) {
        assert (id != View.NO_ID);
        View view = getViewById(id);
        view.setBackgroundResource(color);
        return this;
    }

    public ToolbarInstaller setOnDoubleTapListener(OnDoubleTapListener listener) {
        mOnDoubleTapListener = listener;

        return this;
    }

    public View getViewById(@IdRes int id) {
        View view = mViewCache.get(id);
        if (view == null) {
            view = mToolbar.findViewById(id);
            assert (view != null);
            mViewCache.put(id, view);
        }

        return view;
    }

    /**
     * 如果使用mCollapsingToolbar必须使用这个方法查找view
     *
     * @param activity
     * @param id
     * @return
     */
    public View getViewById(Activity activity, @IdRes int id) {
        View view = mViewCache.get(id);
        if (view == null) {
            view = mActivity.findViewById(id);
            assert (view != null);
            mViewCache.put(id, view);
        }

        return view;
    }

    public ToolbarInstaller setCollapsedImage(@IdRes int imageId, Drawable drawable) {
        ImageView imageView = (ImageView) getViewById(mActivity, imageId);
        imageView.setImageDrawable(drawable);

        return this;
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public ToolbarInstaller setCollapsedZoomModel(@IdRes int collapToolbarId,
                                                  @IdRes int appBarId,
                                                  @IdRes int imageId,
                                                  final String title) {
        final CollapsingToolbarLayout mCollapsingToolbar = (CollapsingToolbarLayout) getViewById(mActivity, collapToolbarId);
        final AppBarLayout mAppBar = (AppBarLayout) getViewById(mActivity, appBarId);
        final ImageView imageView = (ImageView) getViewById(mActivity, imageId);
        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int throttle = 100;
                if (Math.abs(verticalOffset) > appBarLayout.getTotalScrollRange() - throttle) {
                    mCollapsingToolbar.setTitle(title);
                } else {
                    mCollapsingToolbar.setTitle("");
                }
                int statusHeight = ToolbarUtil.getStatusBarHeight(mActivity);
                float total = (imageView.getHeight() - mToolbar.getHeight()) - statusHeight;
                float v = Float.valueOf(Math.abs(verticalOffset)) / total;
                float percent = v > 1 ? 1 : v;
                mToolbar.setBackgroundColor(Color.argb((int) (255 * percent), 0xff, 0xae, 00));
                StatusBarUtil.setTranslucentForCoordinatorLayout(mActivity, (int) (255 * percent), Color.rgb(0xff, 0xae, 00));
            }
        });

        return this;
    }

    public void onDestroy() {
        mActivity = null;
        mToolbar = null;
        mViewCache.clear();
    }

    public enum DrawableType {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    public interface OnDoubleTapListener {
        void onDoubleTap(MotionEvent e);
    }
}
