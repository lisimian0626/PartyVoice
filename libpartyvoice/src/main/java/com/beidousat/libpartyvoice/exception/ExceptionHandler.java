package com.beidousat.libpartyvoice.exception;

import com.beidousat.libpartyvoice.base.ISBaseModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import pl.sugl.common.event.NetworkEvent;
import retrofit2.HttpException;

/**
 * author: Lism
 * date:   2018/11/12
 * describe:
 */

public class ExceptionHandler {
    public static String handle(Throwable throwable) {
        String msg = "";
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;
            msg = processHttpError(httpException);
        } else if (throwable instanceof SocketTimeoutException) {
            msg = "网络超时，请重试...";
        } else if (throwable instanceof SocketException || throwable instanceof UnknownHostException) {
            msg = "未开启网络";
            EventBus.getDefault().post(new NetworkEvent("网络连接错误"));
        } else {
            msg = "未知错误";
        }
        return msg;
    }

    private static String processHttpError(HttpException httpException) {
        String msg = "";
        switch (httpException.code()) {
            case 400:
            case 404:
                try {
                    String string = httpException.response().errorBody().string();
                    Gson gson = new Gson();
                    ISBaseModel<String> errBody = gson.fromJson(string, new TypeToken<ISBaseModel<String>>() {
                    }.getType());
                    msg = errBody.getTips();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case 401:
            case 402:
                msg = "用户未认证";
                break;
        }

        return msg;
    }
}
