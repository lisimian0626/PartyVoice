package pl.sugl.common.widget.smscode;

/**
 * author: Hanson
 * date:   2018/2/27
 * describe:
 */

public interface ISmsCode {
    void sendSmsCode(String mobile);

    void cancelSendSmsCode();
}
