package genius.baselib.frame.auto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import genius.baselib.PreferenceUtil;
import genius.baselib.frame.center.CStatic;
import genius.baselib.frame.util.CTools;
import genius.utils.UtilsLog;
import genius.utils.UtilsNetwork;
import genius.utils.UtilsSP;


/**
 * Created by Hongsec on 2016-08-23.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        AutoInstallHelper autoInstallHelper = AutoInstallHelper.getInstance();
        UtilsSP sqUtils= PreferenceUtil.getInstance(context);
        autoInstallHelper.openAlarm(context,sqUtils.getValue(AutoInstallHelper.AUTO_HOUR,-1),sqUtils.getValue(AutoInstallHelper.AUTO_MINUTE,-1));


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if(hour == sqUtils.getValue(AutoInstallHelper.AUTO_HOUR,-1) && minute == sqUtils.getValue(AutoInstallHelper.AUTO_MINUTE,-1)){
        }else{

            UtilsLog.v("alarm boot on wrong time!!");
            return;
        }



        if(UtilsNetwork.getConnectivityStatus(context) != UtilsNetwork.TYPE.WIFI){
            if(sqUtils.getValue(CStatic.SP_KEY_ONLYWIFI,true)){
                UtilsLog.v("work only wifi ");
                return;
            }else{
                UtilsLog.v("start work on 3g ");
            }
        }


        CTools.startAuto(context);
        UtilsLog.v("start service ");
    }


}
