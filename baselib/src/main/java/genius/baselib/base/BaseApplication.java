package genius.baselib.base;

import android.app.Application;

import genius.baselib.PreferenceUtil;
import genius.baselib.receiver.NetworkReceiver;
import genius.utils.UtilsNetwork;

/**
 * Created by Hongsec on 2016-07-21.
 */
public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();


//        Config.init(true,"I will be back",true);TODO

        updateNetworkStatus();
    }


    /**
     * 更新网络状态并保存
     */
    private void updateNetworkStatus() {
        UtilsNetwork.TYPE connectivityStatus = UtilsNetwork.getConnectivityStatus(this);
        if(connectivityStatus == UtilsNetwork.TYPE.NOT_CONNECTED){
            //no network
            PreferenceUtil.setValue(this, NetworkReceiver.NetworkStatusKey,0);
        }else if(connectivityStatus == UtilsNetwork.TYPE.WIFI){
            //wifi
            PreferenceUtil.setValue(this, NetworkReceiver.NetworkStatusKey,1);
        }else if(connectivityStatus == UtilsNetwork.TYPE.MOBILE){
            //mobile
            PreferenceUtil.setValue(this, NetworkReceiver.NetworkStatusKey,2);
        }
    }
}
