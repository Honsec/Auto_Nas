package genius.baselib.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import genius.baselib.PreferenceUtil;
import genius.baselib.bus.BusMessage;
import genius.baselib.bus.BusTool;
import genius.utils.TempData;
import genius.utils.UtilsNetwork;

/*  <receiver android:name=".receiver.NetworkReceiver">

            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE"/>
            </intent-filter>

        </receiver>
*
* */

/**
 * Created by Hongsec on 2016-07-21.
 */
public class NetworkReceiver extends BroadcastReceiver {
    public static final int UPDATE_NET = 1000;

    public static final String NetworkStatusKey= "network_key" ;

    /**
     * 是否在每个界面检测网络状态并提示
     */
    public static  boolean checkEnable = false;



    @Override
    public void onReceive(Context context, Intent intent) {

        noSetCheck(context);

    }

    /**
     * 인터넷 상태 검증밑 스위치에따라 UI전환시키기
     * @param context
     */
    public static void checkNetWork(Context context){
        if(genius.baselib.receiver.NetworkReceiver.checkEnable) return;

        switch (TempData.getWeak(PreferenceUtil.getValue(context, genius.baselib.receiver.NetworkReceiver.NetworkStatusKey,-1))){

            case -1://noset

                noSetCheck(context);

                break;
            case 0:

                if(genius.baselib.receiver.NetworkReceiver.checkEnable){
                    BusTool.sendBus(new BusMessage(BusMessage.BUSTYPE.onEventMainThread,true, UtilsNetwork.TYPE.NOT_CONNECTED, genius.baselib.receiver.NetworkReceiver.UPDATE_NET));
                }

                break;
            case 1:
                if(genius.baselib.receiver.NetworkReceiver.checkEnable){
                    BusTool.sendBus(new BusMessage(BusMessage.BUSTYPE.onEventMainThread,true, UtilsNetwork.TYPE.WIFI, genius.baselib.receiver.NetworkReceiver.UPDATE_NET));
                }
                break;
            case 2:
                if(genius.baselib.receiver.NetworkReceiver.checkEnable){
                    BusTool.sendBus(new BusMessage(BusMessage.BUSTYPE.onEventMainThread,true, UtilsNetwork.TYPE.MOBILE, genius.baselib.receiver.NetworkReceiver.UPDATE_NET));
                }
                break;
        }
    }

    private static void noSetCheck(Context context) {
        UtilsNetwork.TYPE connectivityStatus = UtilsNetwork.getConnectivityStatus(context);

        if(connectivityStatus == UtilsNetwork.TYPE.NOT_CONNECTED){
            //no network
            PreferenceUtil.setValue(context, genius.baselib.receiver.NetworkReceiver.NetworkStatusKey,0);
        }else if(connectivityStatus == UtilsNetwork.TYPE.WIFI){
            //wifi
            PreferenceUtil.setValue(context, genius.baselib.receiver.NetworkReceiver.NetworkStatusKey,1);
        }else if(connectivityStatus == UtilsNetwork.TYPE.MOBILE){
            //mobile
            PreferenceUtil.setValue(context, genius.baselib.receiver.NetworkReceiver.NetworkStatusKey,2);
        }

        if(genius.baselib.receiver.NetworkReceiver.checkEnable){
            BusTool.sendBus(new BusMessage(BusMessage.BUSTYPE.onEventMainThread,true,connectivityStatus, genius.baselib.receiver.NetworkReceiver.UPDATE_NET));
        }
    }


}
