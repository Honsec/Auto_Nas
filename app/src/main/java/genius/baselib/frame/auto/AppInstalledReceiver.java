package genius.baselib.frame.auto;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by Hongsec on 2016-08-22.
 */
public class AppInstalledReceiver extends BroadcastReceiver {

    private String substring;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {
            Log.v("demo", "pkg_received");

            if ("android.intent.action.PACKAGE_ADDED".equalsIgnoreCase(intent.getAction())) {
                substring = intent.getData().getSchemeSpecificPart();
                Log.v("demo", "pkg_added:" + substring);

                if (AutoInstallHelper.getInstance().missionCallback != null) {
                    Log.v("demo", "response result");
                    if (AutoInstallHelper.getInstance().oldMissionData.contains(substring)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                AutoInstallHelper.getInstance().missionCallback.onInstalled(substring);
                            }
                        }).start();
                    }
                }

            }
        }
    }


}
