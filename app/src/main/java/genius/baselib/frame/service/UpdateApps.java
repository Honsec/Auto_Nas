package genius.baselib.frame.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

import genius.baselib.frame.db.DB_Install;

/**
 * 설치된 앱 저장하기
 * Created by Hongsec on 2016-09-06.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public class UpdateApps extends Service {

    public static String UPLOAD = "upload_apps";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null) {

            if (UPLOAD.equalsIgnoreCase(intent.getAction())) {

                DB_Install db_install = new DB_Install(getApplicationContext(), null);
                db_install.openDB();

                PackageManager packageManager = UpdateApps.this.getPackageManager();
                List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
                for (PackageInfo packageInfo : installedPackages) {

                    if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                        //그냥앱
                        db_install.insertOrupdate(packageInfo.packageName, "");
                    } else {
                        //시스템 앱
                    }

                }

                db_install.closeDB();


            }


        }

        return super.onStartCommand(intent, flags, startId);
    }
}
