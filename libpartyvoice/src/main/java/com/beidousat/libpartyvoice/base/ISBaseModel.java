package com.beidousat.libpartyvoice.base;

import java.io.Serializable;

/**
 * author: Lism
 * date:   2018/11/12
 * describe:
 */

public class ISBaseModel<T> implements Serializable {
    private String msg;
    private String url;
    private String tips;
    private int code;
    private ISDataModel<T> data;

    public T getEntity() {
        return data != null ? data.data : null;
    }

    public String getTips() {
        return tips;
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
