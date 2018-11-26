package pl.sugl.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.beidousat.libcommon.R;

/**
 * author: Hanson
 * date:   2018/3/15
 * describe:
 */

public class LoadingView extends LinearLayout {

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        View.inflate(context, R.layout.vstb_loading_view, this);
    }
}