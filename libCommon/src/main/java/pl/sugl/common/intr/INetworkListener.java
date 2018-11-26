package pl.sugl.common.intr;

/**
 * author: Hanson
 * date:   2018/1/12
 * describe:
 */

public interface INetworkListener {
    /**
     * 显示网络状态页面
     * @param isNetworkOk 网络是否畅通
     * @param code 网络请求code,对应 http status code
     */
    void showNetWorkError(boolean isNetworkOk, int code);
    /**
     * 网络重试回调
     */
    void onNetWorkRetry();
}
