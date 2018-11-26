package pl.sugl.common.utils;

import android.content.Context;

/**
 * author: Hanson
 * date:   2018/1/16
 * describe:
 */

public class ISingContextHolder {
    private static Context sContext;

    public static void setContext(Context context) {
        sContext = context;
    }

    public static Context getContext() {
        return sContext;
    }
}
