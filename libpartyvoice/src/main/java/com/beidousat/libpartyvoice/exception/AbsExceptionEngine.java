package com.beidousat.libpartyvoice.exception;

import io.reactivex.functions.Consumer;

/**
 * author: Lism
 * date:   2018/10/12
 * describe:
 */

public abstract class AbsExceptionEngine implements Consumer<Throwable> {
    /**
     * 处理出错信息
     * @param message 出错信息
     */
    public abstract void handMessage(String message);

    @Override
    public void accept(Throwable throwable) throws Exception {
        handMessage(ExceptionHandler.handle(throwable));
    }
}
