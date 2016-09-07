package genius.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

public class UtilsNetwork {
     

    public enum TYPE{ WIFI, MOBILE, NOT_CONNECTED};

    /**
     *
     * @param context
     * @return   TYPE_WIFI 1 <br/> TYPE_MOBILE 2  <br/> TYPE_NOT_CONNECTED = 0
     */
    public static TYPE getConnectivityStatus(Context context) {

        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE.WIFI;

                if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE.MOBILE;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return TYPE.NOT_CONNECTED;
    }

    public static void goWifiSetting(Context context){
        context.startActivity(new Intent(Settings.ACTION_WIRELESS_SETTINGS));
    }

}