package pl.sugl.common.contracts;

/**
 * author: Hanson
 * date:   2018/1/17
 * describe:
 */

public interface BaseView {
    /**
     * 显示Loading视图
     * @param msg
     */
    void showLoading(String msg);

    /**
     * 隐藏Loading视图
     */
    void hideLoading();

    /**
     * 信息反馈
     * @param succeed 成功或者失败
     * @param key 标识请求的key ApiService.class
     * @param msg 消息
     */
    void onFeedBack(boolean succeed, String key, String msg);
}
