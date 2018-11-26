package com.beidousat.libpartyvoice.net;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author: Lism
 * date:   2018/11/12
 * describe:
 */

public class CommonHeaderInterceptor implements Interceptor {
    private static final String TOKEN = "jwt";
//    private static final String DEVICE_TYPE = "device_type";
    private static final String USER_ID = "user_id";
    private static final String JPUSH_REGISTRATION_ID = "registration_id";
    private static final String MOBILE_NAME = "mobile_name";
    private static final String DEVICE_NAME = "device_name";
    private static final String CONNECTION = "connection";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        Log.d("intercept", "url="+request.url() + ";\tjwt="+MyUserInfo.getInstance().getJwtToken());
//        if (!TextUtils.isEmpty(MyUserInfo.getInstance().getJwtToken())) {
//            request = request.newBuilder()
//                    .addHeader(TOKEN, MyUserInfo.getInstance().getJwtToken())
//                    .build();
//        }
//        request = request.newBuilder().addHeader(CONNECTION, "close").build();
        return chain.proceed(request);
    }

}
