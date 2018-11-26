package pl.sugl.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.beidousat.libcommon.R;

/**
 * 自定义加载进度对话框
 * Created by Administrator on 2016-10-28.
 */

public class DlgLoading extends Dialog {
    private TextView tv_text;
    private boolean mInterceptBackEvent;

    public DlgLoading(Context context) {
        super(context);
        /**设置对话框背景透明*/
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dlg_loading);
        tv_text = (TextView) findViewById(R.id.tv_text);
        setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mInterceptBackEvent) {
            getWindow().getDecorView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int i, KeyEvent keyEvent) {
                    if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                    return false;
                }
            });
        }
    }

    /**
     * 为加载进度个对话框设置不同的提示消息
     *
     * @param message 给用户展示的提示信息
     * @return build模式设计，可以链式调用
     */
    public DlgLoading setMessage(String message) {
        tv_text.setText(message);
        return this;
    }

    public void setInterceptBackEvent(boolean interceptBackEvent) {
        mInterceptBackEvent = interceptBackEvent;
    }
}