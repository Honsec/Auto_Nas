package genius.baselib.frame.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Hongsec on 2016-09-06.
 */
public class InstallListener extends BroadcastReceiver {
    private String substring;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {
            Log.v("demo", "pkg_received");

            if ("android.intent.action.PACKAGE_ADDED".equalsIgnoreCase(intent.getAction())) {
                substring = intent.getData().getSchemeSpecificPart();
                Log.v("demo", "pkg_added:" + substring);

            }
        }

    }

}
