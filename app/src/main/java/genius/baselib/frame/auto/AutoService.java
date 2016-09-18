package genius.baselib.frame.auto;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.nextapps.naswall.NASWall;
import com.nextapps.naswall.NASWallAdInfo;

import java.util.ArrayList;

import genius.baselib.PreferenceUtil;
import genius.baselib.frame.center.CStatic;
import genius.utils.UtilsLog;

/**
 * 수동으로 자동설치 서비스를 시작
 * Created by Hongsec on 2016-08-23.
 */
public class AutoService extends Service {

    public static final String STOP = "stop";
    public static final String START = "start";
    public static final String AUTO = "auto";

    /**
     * 알람 자동적립
     */
    private boolean auto = true;
    /**
     * 처음 시작하는지
     */
    private boolean first = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (intent != null && intent.getAction().equalsIgnoreCase(STOP)) {
            stopSelf();
            return super.onStartCommand(intent, flags, startId);
        }


        if (intent != null && intent.getAction().equalsIgnoreCase(START) && first) {
            UtilsLog.v("demo", "service start");
            first = false;

            final AutoInstallHelper autoInstallHelper = AutoInstallHelper.getInstance();
            autoInstallHelper.init(this, null);


            //call nas ad list
            NASWall.getAdList(AutoService.this, PreferenceUtil.getInstance(getApplicationContext()).getValue(CStatic.SP_ACCOUNT, ""), new NASWall.OnAdListListener() {
                @Override
                public void OnSuccess(ArrayList<NASWallAdInfo> arrayList) {
                    if (autoInstallHelper.stopFlag) return;

                    ArrayList<NASWallAdInfo> nasWallAdInfos = new ArrayList<NASWallAdInfo>();
                    for (NASWallAdInfo nasWallAdInfo : arrayList) {

                        if ("설치형".equalsIgnoreCase(nasWallAdInfo.getMissionText())) {
                            //can do
                            autoInstallHelper.addMission(nasWallAdInfo.getPackageName());
                            nasWallAdInfos.add(nasWallAdInfo);
                        }

                    }
                    prepare(autoInstallHelper, nasWallAdInfos);

                }

                @Override
                public void OnError(int i) {

                }
            });

            return super.onStartCommand(intent, flags, startId);
        }


        return super.onStartCommand(intent, flags, startId);
    }

    private void prepare(final AutoInstallHelper autoInstallHelper, final ArrayList<NASWallAdInfo> adInfos) {
        autoInstallHelper.prepareMession(new MissionCallback() {
            @Override
            public void onInstalled(final String pkg) {
                //TODO 오토설치리스트에 해당하는 앱이  설치되였을때  서버로 설치완료 api호출
                UtilsLog.v("demo", "service 설치완료 :" + pkg);


            }


            public NASWallAdInfo getInsMission(String pkg) {

                for (NASWallAdInfo nasWallAdInfo : adInfos) {
                    if (nasWallAdInfo.getPackageName().equalsIgnoreCase(pkg)) {

                        return nasWallAdInfo;

                    }
                }

                return null;
            }


            @Override
            public void onPrepare(String pkg) {
                //call api and check
                NASWallAdInfo nasWallAdInfo = getInsMission(pkg);
                if (nasWallAdInfo != null) {

                    NASWall.joinAd(AutoService.this, nasWallAdInfo, new NASWall.OnJoinAdListener() {
                        @Override
                        public void OnSuccess(NASWallAdInfo nasWallAdInfo, String url) {
                            if (url.startsWith("http")) {
                                autoInstallHelper.start(url);
                            } else {
                                autoInstallHelper.start();
                            }
                        }

                        @Override
                        public void OnError(NASWallAdInfo nasWallAdInfo, int i) {
                            autoInstallHelper.next();
                        }

                        @Override
                        public void OnComplete(NASWallAdInfo nasWallAdInfo) {

                        }
                    });
                } else {
                    autoInstallHelper.next();
                }


            }

            @Override
            public void stopall() {
                AutoService.this.stopSelf();
            }


        });
    }
}
