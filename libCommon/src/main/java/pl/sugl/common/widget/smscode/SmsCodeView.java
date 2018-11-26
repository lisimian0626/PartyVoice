package pl.sugl.common.widget.smscode;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beidousat.libcommon.R;

import pl.sugl.common.utils.PhoneCheckUtil;
import pl.sugl.common.widget.ClearEditText;

/**
 * author: Hanson
 * date:   2018/2/27
 * describe:
 */

public class SmsCodeView extends RelativeLayout implements View.OnClickListener {
    private static final long INTERVAL = 1000;

    View mRootView;
    TextView mTvSmsCode;
    ClearEditText mEtSmsCode;

    ISmsCode mSmsCode;
    String mMobile;
    long mCountDownMls = 60*1000;
    CountDownTimer mCountDownTimer;
    boolean isNotSend;

    public SmsCodeView(Context context) {
        this(context, null);
    }

    public SmsCodeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmsCodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context) {
        mRootView = View.inflate(context, R.layout.widget_smscode, this);
        mTvSmsCode = (TextView) mRootView.findViewById(R.id.tv_send_smscode);
        mEtSmsCode = (ClearEditText) mRootView.findViewById(R.id.et_verify_code);
        mTvSmsCode.setOnClickListener(this);

        mEtSmsCode.setValidationRule(new ClearEditText.TextValidateRule() {
            @Override
            public boolean isAvailable(String text) {
                return PhoneCheckUtil.checkVerifyCode(text);
            }

            @Override
            public void afterCheckAvailable(boolean available) {

            }
        });
    }

    private void initCountDownTimer() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }

        mCountDownTimer = new CountDownTimer(mCountDownMls, INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                isNotSend = false;
                mTvSmsCode.setEnabled(false);

                int remind = (int)millisUntilFinished/1000;
                String text = getContext().getResources().getString(R.string.smscode_delay);
                SpannableStringBuilder spanSb = new SpannableStringBuilder();
                spanSb.append(String.valueOf(remind));
                spanSb.append("s ");
                spanSb.setSpan(new ForegroundColorSpan(Color.parseColor("#FFAE00")),
                        0, spanSb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                spanSb.append(text);
                mTvSmsCode.setTextColor(Color.parseColor("#CCCCCC"));
                mTvSmsCode.setText(spanSb);
            }

            @Override
            public void onFinish() {
                isNotSend = true;
                mTvSmsCode.setEnabled(true);
                mTvSmsCode.setTextColor(Color.WHITE);
                mTvSmsCode.setText(R.string.smscode_get);
            }
        };
        mCountDownTimer.start();
    }

    public void setSmsCode(ISmsCode smsCode) {
        mSmsCode = smsCode;
    }

    public void setMobile(String mobile) {
        mMobile = mobile;
    }

    @Override
    public void onClick(View v) {
        if (TextUtils.isEmpty(mMobile)) {
            Toast.makeText(getContext(), "手机号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mSmsCode != null) {
            mSmsCode.sendSmsCode(mMobile);

            initCountDownTimer();
        }
    }

    public void reset() {
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
        }
        isNotSend = true;
        mTvSmsCode.setText(R.string.smscode_get);
        mTvSmsCode.setTextColor(Color.WHITE);
        mTvSmsCode.setEnabled(true);
    }

    public String getSmsCode() {
        return mEtSmsCode.getText().toString();
    }
}
