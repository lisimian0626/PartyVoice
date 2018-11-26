package pl.sugl.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

/**
 * author: Hanson
 * date:   2018/3/13
 * describe:
 */

public class MyKeyboardUtil {
    private static final Map<Class, ViewTreeObserver.OnGlobalLayoutListener> mListeners = new HashMap<>();


    /**
     * 打开软键盘
     *
     * @param mEditText
     * @param mContext
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText 输入框
     * @param mContext 上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 判断当前软键盘是否打开
     *
     * @param activity
     * @return
     */
    public static boolean isSoftInputShow(Activity activity) {

        // 虚拟键盘隐藏 判断view是否为空
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            // 隐藏虚拟键盘
            InputMethodManager inputmanger = (InputMethodManager) activity
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
//       inputmanger.hideSoftInputFromWindow(view.getWindowToken(),0);

            return inputmanger.isActive() && activity.getWindow().getCurrentFocus() != null;
        }
        return false;
    }


    public static void registerSoftKeyboardObserve(Activity activity, final OnSoftKeyboardChangeListener listener) {
        final View decorView = activity.getWindow().getDecorView();
        final Class clazz = activity.getClass();
        final Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);

        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen","android");
        final int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

        if (!mListeners.containsKey(clazz)) {
            decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                private boolean mIsSoftKeyboardShowing;

                @Override
                public void onGlobalLayout() {
                    mListeners.put(clazz, this);

                    Rect r = new Rect();
                    decorView.getWindowVisibleDisplayFrame(r);
                    //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
                    int heightDifference = size.y - (r.bottom - r.top);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        heightDifference -= statusBarHeight;
                    }

                    boolean isKeyboardShowing = heightDifference > size.y/4;

                    //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
                    if ((mIsSoftKeyboardShowing && !isKeyboardShowing) || (!mIsSoftKeyboardShowing && isKeyboardShowing)) {
                        mIsSoftKeyboardShowing = isKeyboardShowing;
                        listener.onSoftKeyBoardChange(heightDifference, mIsSoftKeyboardShowing);
                    }

                }
            });
        }
    }

    public static void unregisterSoftKeyboardObserve(Activity activity) {
        final View decorView = activity.getWindow().getDecorView();
        decorView.getViewTreeObserver().removeOnGlobalLayoutListener(mListeners.get(activity.getClass()));
        mListeners.remove(activity.getClass());
    }

    public interface OnSoftKeyboardChangeListener {
        void onSoftKeyBoardChange(int softKeyboardHeight, boolean visible);
    }
}
