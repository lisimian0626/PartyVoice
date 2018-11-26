package pl.sugl.common.event;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import pl.sugl.common.intr.INetworkListener;

/**
 * author: Hanson
 * date:   2018/1/12
 * describe:
 */

public class NetworkEventSubscriber {
    private SUBSCRIBER_TYPE mSubType;
    private INetworkListener mNetworkListener;

    public enum SUBSCRIBER_TYPE {
        ONCREATE,
        ONRESUME,
    }

    public NetworkEventSubscriber(SUBSCRIBER_TYPE subType, INetworkListener networkListener) {
        mSubType = subType;
        mNetworkListener = networkListener;
    }

    public NetworkEventSubscriber(boolean subOnResume, INetworkListener networkListener) {
        mSubType = subOnResume ? SUBSCRIBER_TYPE.ONRESUME : SUBSCRIBER_TYPE.ONCREATE;
        mNetworkListener = networkListener;
    }

    public NetworkEventSubscriber(INetworkListener networkListener) {
        mSubType = SUBSCRIBER_TYPE.ONCREATE;
        mNetworkListener = networkListener;
    }

    public void onCreate() {
        if (mSubType == SUBSCRIBER_TYPE.ONCREATE) {
            EventBus.getDefault().register(this);
        }
    }

    public void onDestroy() {
        if (mSubType == SUBSCRIBER_TYPE.ONCREATE) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onResume() {
        if (mSubType == SUBSCRIBER_TYPE.ONRESUME) {
            EventBus.getDefault().register(this);
        }
    }

    public void onPause() {
        if (mSubType == SUBSCRIBER_TYPE.ONRESUME) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNetWorkEvent(NetworkEvent event) {
        mNetworkListener.showNetWorkError(event.isNetworkOk(), event.getCode());
    }
}
