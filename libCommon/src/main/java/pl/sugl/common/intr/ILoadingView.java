package pl.sugl.common.intr;

/**
 * author: Hanson
 * date:   2018/1/12
 * describe:
 */

public interface ILoadingView {
    /**
     * 显示loading
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 隐藏loading
     */
    void hideLoading();

    /**
     * 取消loading
     * @param cancelRequest 是否取消请求
     */
    void cancelLoading(boolean cancelRequest);
}
