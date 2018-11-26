package pl.sugl.common.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * author: Hanson
 * date:   2017/6/27
 * describe:
 */

public class ColorfulClickableSpan extends ClickableSpan {
    private View.OnClickListener mClickListener;
    private int mColor;

    public ColorfulClickableSpan(int color) {
        mColor = color;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(mColor);
    }

    @Override
    public void onClick(final View widget) {
        if (mClickListener != null) {
            mClickListener.onClick(widget);
        }
    }

    public void setClickListener(View.OnClickListener clickListener) {
        mClickListener = clickListener;
    }
}
