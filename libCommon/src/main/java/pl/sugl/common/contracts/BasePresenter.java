package pl.sugl.common.contracts;

/**
 * author: Hanson
 * date:   2018/1/17
 * describe:
 */

public interface BasePresenter {
    void cancel();
    boolean isNoMoreData();
    void setNoMoreData(boolean isNoMore);
}
