package pl.sugl.common.utils;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.util.Stack;

public class MyActivityManager {

    private static MyActivityManager sInstance = new MyActivityManager();
    private Stack<Activity> activityStack = new Stack<Activity>();
    private Class mHomeClass;
    private Class mWxEntryClass;

    private MyActivityManager() {

    }

    public static MyActivityManager getInstance() {
        return sInstance;
    }

    public Activity getTopActivity() {
        return activityStack.peek();
    }

    public void setHomeClass(Class<?> clazz) {
        mHomeClass = clazz;
    }

    public void setWxEntryClass(Class<?> clazz) {
        mWxEntryClass = clazz;
    }

    /**
     * 获取指定的Activity
     */
    public Activity getActivity(Class<?> cls) {
        if (activityStack != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        }
        return null;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        activityStack.push(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.peek();
    }

    /**
     * 获取当前Activity（堆栈中第一个压入的）
     */
    public Activity topActivity() {
        if (activityStack.isEmpty()) {
            return null;
        }
        return activityStack.firstElement();
    }

    public boolean activityTopStackIsEmpty() {
        return activityStack.isEmpty();
    }

    public boolean activityTopStackIsNotEmpty() {
        return !activityStack.isEmpty();
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.peek();
        finishAndRemoveActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishAndRemoveActivity(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    public void finishActivity(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activity.finish();
        }
    }

    /**
     * 结束指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null && activityStack.contains(activity)) {
            activityStack.remove(activity);
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishAndRemoveActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        int size = activityStack.size();
        //FIFO
        for (int i = size - 1; i >= 0; i--) {
            if (null != activityStack.get(i)) {
                finishActivity(activityStack.get(i));
            }
        }
        activityStack.clear();
    }

    public void clearTopActivity(Class<?> clazz) {
        Activity activity = null;
        while ((activity = activityStack.peek()) != null) {
            if (activity.getClass().equals(clazz)) {
                break;
            }
            activityStack.pop();
            activity.finish();
        }
    }

    public void toHomeActivity() {
        if (mHomeClass != null) {
            clearTopActivity(mHomeClass);
        }
    }

    /**
     * 登录调用此方法；因为在部分机型（华为）测试发现调用微信登录的时候
     * 关闭页面在登录成功之后才会关闭WxEntryActivity;
     * ps:很奇怪的问题，明明先调用的finish再去请求网络的；不知道为什么会出现在这种情况
     */
    public void closeWhenLogin() {
        if (mWxEntryClass != null) {
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(mWxEntryClass)) {
                    activityStack.remove(activity);
                    activity.finish();
                }
            }
        }
        finishActivity();
    }

    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}  