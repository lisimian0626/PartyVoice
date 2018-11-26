package com.beidousat.libpartyvoice.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.android.voicedemo.control.MyRecognizer;
import com.baidu.android.voicedemo.recognization.IRecogListener;
import com.baidu.android.voicedemo.recognization.IStatus;
import com.baidu.android.voicedemo.recognization.MessageStatusRecogListener;
import com.baidu.speech.asr.SpeechConstant;
import com.beidousat.libpartyvoice.R;
import com.beidousat.libpartyvoice.bussiness.UiJumpHelper;
import com.lehu.mystyle.voiceassistant.manager.BlueToothManager;
import com.lehu.mystyle.voiceassistant.util.BlueToothUtil;
import com.lehu.mystyle.voiceassistant.util.MainHandlerUtil;
import com.lehu.mystyle.voiceassistant.util.ToastUtil;
import com.lehu.mystyle.voiceassistant.util.speex.SpeexStreamHandler;

import java.util.HashMap;
import java.util.Map;

import pl.sugl.common.base.BaseActivity;
import pl.sugl.common.utils.ToolbarInstaller;

public class BlueToothActivity extends BaseActivity {

    private static final String TAG = "BlueTooth";
    private LinearLayout layout_noVip,layout_Vip;
    private TextView tv_band_device,tv_search;
    private ImageView iv_close;
    public BlueToothManager blueToothManager;
    private MyRecognizer myRecognizer;
    public static Map<String, Object> params;

    private LocalBroadcastManager localBroadcastManager;

    private MyBroadCastReceiver broadCastReceiver;

    @Override
    public void setupToolbar() {
        super.setupToolbar();
        mToolbarInstaller = new ToolbarInstaller(this);
        mToolbarInstaller.setTitle(R.string.blubtooth_title)
                .setDefaultBackListener();
    }

    @Override
    public int getContentView() {
        return R.layout.bluetooth;
    }

    @Override
    public void initViews() {
//    tv_band_device=findViewById(R.id.bt_tv_bang_device);
//    tv_search=findViewById(R.id.bt_tv_search);
//    btn_close=findViewById(R.id.bt_btn_close);
    }

    @Override
    public void setListener() {
//    tv_search.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            clickBluetoothView();
//        }
//    });
//    btn_close.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            finish();
//        }
//    });
    }

    @Override
    public void initData() {

    }

    @Override
    public void loadDataWhenOnResume() {

    }

    @Override
    public void cancelLoadingRequest() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadCast();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unRegisterBroadCast();
    }

    //监听蓝牙变化显示蓝牙当前状态
    private void registerBroadCast() {
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        broadCastReceiver = new MyBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BlueToothManager.BLUETOOTHSTATECHANGE);
        localBroadcastManager.registerReceiver(broadCastReceiver, intentFilter);
    }

    private void unRegisterBroadCast() {
        if (broadCastReceiver != null) {
            localBroadcastManager.unregisterReceiver(broadCastReceiver);
            broadCastReceiver = null;
        }
    }
    public void clickBluetoothView() {
        String state = BlueToothUtil.ConnectedState;
        switch (state) {
            case BlueToothUtil.BLUETOOTH_NO:
                ToastUtil.showErrorToast("无蓝牙模块");
                break;
            case BlueToothUtil.BLUETOOTH_NO_ENABLE:
            case BlueToothUtil.BLUETOOTH_ON:
                BlueToothUtil.openBlueToothSetting(this);
                break;
            case BlueToothUtil.BLUETOOTH_CONNECTED:
                if (blueToothManager != null) {
                    blueToothManager.getConnectedDevices();
                }
                break;
        }
    }
    public class MyBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BlueToothManager.BLUETOOTHSTATECHANGE)) {
                tv_band_device.setText(getBluetoothState());
            }
        }
    }
    private String getBluetoothState() {
        String state = BlueToothUtil.ConnectedState;
        String str = "未开启";
        switch (state) {
            case BlueToothUtil.BLUETOOTH_NO:
                str = "无蓝牙模块";
                break;
            case BlueToothUtil.BLUETOOTH_NO_ENABLE:
                str = "未开启";
                break;
            case BlueToothUtil.BLUETOOTH_ON:
                str = "未连接";
                break;
            case BlueToothUtil.BLUETOOTH_CONNECTED:
                str = "点击重试";
                break;
            case BlueToothUtil.BLUETOOTH_UN_AUTH:
                str = "未授权的设备";
                break;
            case BlueToothUtil.BLUETOOTH_CONNECTING_SPP:
                str = "连接中...";
                break;
            case BlueToothUtil.BLUETOOTH_CONNECTED_SPP:
                if (BlueToothUtil.device == null) {
                    str = "已连接";
                } else {
                    str = BlueToothUtil.getDevicesName();
                }
                break;
        }

        return str;
    }
}
