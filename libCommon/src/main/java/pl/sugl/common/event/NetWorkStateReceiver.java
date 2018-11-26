package pl.sugl.common.event;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

public class NetWorkStateReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            System.out.println("网络状态发生变化");
            boolean connected = false;
            //检测API是不是小于21，因为到了API21之后getNetworkInfo(int networkType)方法被弃用
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                //获取ConnectivityManager对象对应的NetworkInfo对象
                //获取WIFI连接的信息
                NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                //获取移动数据连接的信息
                NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

                if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    connected = true;
                    EventBus.getDefault().post(new NetworkEvent(true));
                } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                    connected = true;
                    EventBus.getDefault().post(new NetworkEvent(true));
                } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                    connected = true;
                    EventBus.getDefault().post(new NetworkEvent(true));
                } else {
                    connected = false;
                    EventBus.getDefault().post(new NetworkEvent(false));
                }
            } else {
                //这里的就不写了，前面有写，大同小异
                System.out.println("API level 大于21");
                //获得ConnectivityManager对象
                ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

                //获取所有网络连接的信息
                Network[] networks = connMgr.getAllNetworks();
                //用于存放网络连接信息
                boolean flag = false;
                //通过循环将网络信息逐个取出来
                for (int i = 0; i < networks.length; i++) {
                    //获取ConnectivityManager对象对应的NetworkInfo对象
                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                    if (networkInfo != null && networkInfo.isConnected()) {
                        flag = true;
                        break;
                    } else {
                        flag = false;
                    }
                }
                connected = flag;
                EventBus.getDefault().post(new NetworkEvent(flag));
            }

            if (!connected) {
                String str = "网络连接失败，请检查网络设置";
                Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e("NetWorkStateReceiver", e.toString());
        }
    }
}