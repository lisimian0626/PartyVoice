package com.beidousat.libpartyvoice.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.beidousat.libpartyvoice.R;


/**
 * author: Hanson
 * date:   2018/1/16
 * describe:
 */

public class BezierHolderView extends View {
    private int mBezierCtrlOffset;
    private int mFrontBgColor = Color.WHITE;
    private Paint mPaint;
    private Path mPath;

    public BezierHolderView(Context context) {
        this(context, null);
    }

    public BezierHolderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BezierHolderView);
        mBezierCtrlOffset = ta.getDimensionPixelSize(R.styleable.BezierHolderView_bezierCtrlOffset, mBezierCtrlOffset);
        mFrontBgColor = ta.getColor(R.styleable.BezierHolderView_frontBgColor, mFrontBgColor);
        ta.recycle();

        if (mBezierCtrlOffset != 0) {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setColor(mFrontBgColor);
            mPaint.setStyle(Paint.Style.FILL);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (getWidth() != 0) {
            initPath();
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOval(canvas);
    }

    private void initPath() {
        mPath = new Path();
        mPath.moveTo(0, 0);
        mPath.lineTo(getWidth(), 0);
        mPath.lineTo(getWidth(), getHeight() - mBezierCtrlOffset);
        mPath.quadTo(getWidth() / 2, getHeight() + mBezierCtrlOffset, 0,
                getHeight() - mBezierCtrlOffset);
        mPath.lineTo(0, 0);
        mPath.close();
    }

    private void drawOval(Canvas canvas) {
        if (mPath != null && mPaint != null) {
            canvas.save();
            canvas.drawPath(mPath, mPaint);
        }
    }
}
