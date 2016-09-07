package genius.baselib.frame.base;

import java.io.IOException;

import genius.baselib.PreferenceUtil;
import genius.baselib.base.BaseApplication;
import genius.baselib.frame.center.CConfig;
import genius.baselib.frame.center.CStatic;
import genius.baselib.frame.util.AdvertisingIdClient;
import genius.baselib.frame.util.CTools;

/**
 * Created by Hongsec on 2016-09-05.
 */
public class BaseApp extends BaseApplication {

    @Override
    public void onCreate() {


        CConfig.init(getApplicationContext());

        super.onCreate();

//        UpdateDB();


        getGoogleAdId();

        CTools.updateVersion(getApplicationContext());

    }


    private void getGoogleAdId() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {

                    AdvertisingIdClient.AdInfo advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(getApplicationContext());
                    if (advertisingIdInfo != null) {
                        PreferenceUtil.getInstance(getApplicationContext()).setValue(CStatic.SP_GID, advertisingIdInfo.getId() + "");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception ee) {
                    ee.printStackTrace();
                }

            }
        }).start();
    }


}
