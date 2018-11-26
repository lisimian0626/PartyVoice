package pl.sugl.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringRes;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import com.beidousat.libcommon.R;

import pl.sugl.common.utils.DensityUtil;


public class ClearEditText extends AppCompatEditText implements
        OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawableH;

    private Drawable mClearDrawableN;

    private Drawable mOkDrawable;

    private DrawableType mDrawableType = DrawableType.CLEAR_DRAWABLE_N;

    private OnFocusChangeListener mParentFocusChangeListener;
    /**
     * 控件是否有焦点
     */
    private boolean checkEqual = true;
    private boolean hasFoucs;
    private int mMinLetterLen = 0;
    private int mMaxLetterLen = 18;
    private Paint mErrorPaint;

    private TextValidateRule mRule;

    private int mErrorTextBaseline;
    private int mErrorTextPadding = 10;
    private String mErrorMsg = "";
    private boolean mShowErrorInfo;
    private int errorTextSize;

    public enum DrawableType {
        OK_DRAWABLE,
        CLEAR_DRAWABLE_H,
        CLEAR_DRAWABLE_N
    }

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ClearEditText);
        mMinLetterLen = ta.getInteger(R.styleable.ClearEditText_minLetterLen, mMinLetterLen);
        mMaxLetterLen = ta.getInteger(R.styleable.ClearEditText_maxLetterLen, mMaxLetterLen);
        errorTextSize = ta.getDimensionPixelOffset(R.styleable.ClearEditText_errorTextSize, (int) getPaint().getTextSize());
        ta.recycle();

        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawableH = getResources().getDrawable(R.drawable.wrong_btn);
        mClearDrawableH.setBounds(0, 0, mClearDrawableH.getIntrinsicWidth(), mClearDrawableH.getIntrinsicHeight());
        mClearDrawableN = getResources().getDrawable(R.drawable.wrong_btn_gray);
        mClearDrawableN.setBounds(0, 0, mClearDrawableN.getIntrinsicWidth(), mClearDrawableN.getIntrinsicHeight());
        mOkDrawable = getResources().getDrawable(R.drawable.right_btn);
        mOkDrawable.setBounds(0, 0, mOkDrawable.getIntrinsicWidth(), mOkDrawable.getIntrinsicHeight());
        setCompoundDrawablePadding(DensityUtil.dip2px(getContext(), 10));

        //默认设置隐藏图标
        setRightIconVisible(false);
        //设置焦点改变的监听
        super.setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);

        initErrorInfoPaint(context);
    }

    private void initErrorInfoPaint(Context context) {
        mErrorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mErrorPaint.setColor(Color.parseColor("#EC3661"));
        mErrorPaint.setTextSize(errorTextSize);
        mErrorPaint.setTextAlign(Paint.Align.LEFT);

        mErrorTextPadding = DensityUtil.dip2px(context, 10);
    }

    private Drawable getTypeDrawable(DrawableType drawableType) {
        Drawable drawable = null;

        if (drawableType != null) {
            switch (drawableType) {
                case OK_DRAWABLE:
                    drawable = mOkDrawable;
                    break;
                case CLEAR_DRAWABLE_H:
                    drawable = mClearDrawableH;
                    break;
                case CLEAR_DRAWABLE_N:
                default:
                    drawable = mClearDrawableN;
                    break;
            }
        }

        return drawable;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Paint.FontMetricsInt fontMetrics = getPaint().getFontMetricsInt();
        mErrorTextBaseline = (getHeight() - fontMetrics.bottom - fontMetrics.top) / 2;
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null &&
                    (mDrawableType == DrawableType.CLEAR_DRAWABLE_H ||
                            mDrawableType == DrawableType.CLEAR_DRAWABLE_N)) {

                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                    this.setSelected(false);
                    mErrorMsg = "";
                    mShowErrorInfo = false;
                }
            }
        }

        return super.onTouchEvent(event);
    }

    public void setValidationRule(TextValidateRule rule) {
        mRule = rule;
    }

    public void setCheckEqual(boolean checkEqual) {
        this.checkEqual = checkEqual;
    }

    public int getMaxLetterLen() {
        return mMaxLetterLen;
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (mParentFocusChangeListener != null) {
            mParentFocusChangeListener.onFocusChange(v, hasFocus);
        }

        this.hasFoucs = hasFocus;
        updateDrawable();
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener l) {
        mParentFocusChangeListener = l;
    }

    private void updateDrawable() {

        if (mRule == null) {
            mDrawableType = null;
            Log.d("ClearEditText", "未设置TextValidation！");
        } else {
            if (mRule.isAvailable(getText().toString().trim()) && checkEqual) {
                mDrawableType = DrawableType.OK_DRAWABLE;
            } else {
                if (!checkEqual || checkLenAvailable(getText().toString())) {
                    mDrawableType = DrawableType.CLEAR_DRAWABLE_N;
                } else {
                    mDrawableType = DrawableType.CLEAR_DRAWABLE_H;
                }
            }
        }
    }

    private boolean checkLenAvailable(String text) {
        if (TextUtils.isEmpty(text)) {
            return false;
        }

        return text.length() > mMinLetterLen && text.length() < mMaxLetterLen;
    }

    public boolean isLengthError() {
        if (mRule == null) {
            return false;
        }

        return getText().length() == mMaxLetterLen && !mRule.isAvailable(getText().toString());
    }

    /**
     * 设置图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    protected void setRightIconVisible(boolean visible) {
        if (visible) {
            setDrawableType(mDrawableType);
        } else {
            setDrawableType(null);

        }
    }

    public void setDrawableType(DrawableType type) {
        if (type == DrawableType.CLEAR_DRAWABLE_H) {
            setSelected(true);
        } else {
            setSelected(false);
        }
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1],
                getTypeDrawable(type), getCompoundDrawables()[3]);
    }


    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (hasFoucs) {
            updateDrawable();
            setRightIconVisible(s.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (mRule != null) {
            mRule.afterCheckAvailable(mRule.isAvailable(s.toString()));
        }
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }


    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public interface TextValidateRule {
        boolean isAvailable(String text);
        void afterCheckAvailable(boolean available);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!TextUtils.isEmpty(mErrorMsg) && mShowErrorInfo) {
            float width = mErrorPaint.measureText(mErrorMsg);
            int mErrorTextRight = getWidth() - getTotalPaddingRight() - mErrorTextPadding;
            canvas.drawText(mErrorMsg, mErrorTextRight - width,
                    mErrorTextBaseline, mErrorPaint);
        }
    }

    public void setErrorInfo(String errorMsg) {
        mErrorMsg = errorMsg;
    }

    public void showErrorInfo(boolean show) {
        mShowErrorInfo = show;
    }

    public void setErrorInfo(@StringRes int errorMsg) {
        mErrorMsg = getContext().getString(errorMsg);
    }
}