package com.beidousat.libpartyvoice.dagger;

import android.os.Handler;
import android.os.Message;


import com.youth.banner.WeakHandler;

import pl.sugl.common.network.CountingRequestBody;

public class UploadProgressHandler implements CountingRequestBody.Listener {
    private OnUploadProgressListener mOnUploadProgressListener;
    private WeakHandler mHandler = new WeakHandler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (mOnUploadProgressListener != null) {
                mOnUploadProgressListener.onUploadProgress((Float) message.obj);
            }
            return true;
        }
    });

    public void attachOnUploadProgressListener(OnUploadProgressListener listener) {
        mOnUploadProgressListener = listener;
    }

    public void detachOnUploadProgressListener() {
        mOnUploadProgressListener = null;
    }

    @Override
    public void onRequestProgress(long bytesWritten, long contentLength) {
        Message message = new Message();
        message.obj = (float) bytesWritten / contentLength * 100;
        mHandler.sendMessage(message);
    }

    public interface OnUploadProgressListener {
        void onUploadProgress(float percent);
    }
}
