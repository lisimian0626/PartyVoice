package com.beidousat.libpartyvoice.bussiness;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.TextUtils;


import com.beidousat.libpartyvoice.BuildConfig;
import com.beidousat.libpartyvoice.ISHomeActivity;
import com.beidousat.libpartyvoice.activity.BlueToothActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.sugl.common.utils.MyActivityManager;

/**
 * author: Hanson
 * date:   2018/1/18
 * describe:
 */

public class UiJumpHelper {
    public static void startISHomeActivity(Context context) {
        Intent intent = new Intent(context, ISHomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }
    public static void startBluetoothActivity(Context context) {
        Intent intent = new Intent(context, BlueToothActivity.class);
        context.startActivity(intent);
    }
    public static void startSystemPermissionActivity(Activity context, int requestCode) {
        Intent intent = null;
        do {
            if ((intent = getMiuiPermissionIntent(context)) != null) {
                break;
            }
            if ((intent = getMeizuPermissionIntent(context)) != null) {
                break;
            }
            if ((intent = getHuaweiPermissionIntent(context)) != null) {
                break;
            }
            intent = getAppDetailSettingIntent(context);
        } while (false);
        context.startActivityForResult(intent, requestCode);
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        for (ActivityManager.RunningTaskInfo taskInfo : list) {
            if (taskInfo.topActivity.getShortClassName().contains(className)) { // 说明它已经启动了
                return true;
            }
        }
        return false;
    }

    /**
     * 获取应用详情页面intent
     */
    private static Intent getAppDetailSettingIntent(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        return intent;
    }

    /**
     * 华为的权限管理页面
     */
    private static Intent getHuaweiPermissionIntent(Context context) {
        Intent intent = new Intent();
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
        intent.setComponent(comp);

        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_ALL) != null) {
            return intent;
        }

        return null;
    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private static Intent getMeizuPermissionIntent(Context context) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.putExtra("packageName", BuildConfig.APPLICATION_ID);

        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_ALL) != null) {
            return intent;
        }

        return null;
    }

    /**
     * 跳转到miui的权限管理页面
     */
    private static Intent getMiuiPermissionIntent(Context context) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        intent.setComponent(componentName);
        intent.putExtra("extra_pkgname", context.getPackageName());
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_ALL) != null) {
            return intent;
        }

        return null;
    }

}
