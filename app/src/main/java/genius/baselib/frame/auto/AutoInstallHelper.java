package genius.baselib.frame.auto;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import genius.utils.UtilsLog;

/**
 * Created by Hongsec on 2016-08-22.
 */
public class AutoInstallHelper {


    public static final String AUTO_MINUTE = "minute";
    public static final String AUTO_HOUR = "hour";
    private static AutoInstallHelper instance;
    private Context mContext;

    public static AutoInstallHelper getInstance() {
        if (instance == null) {
            instance = new AutoInstallHelper();
        }
        return instance;
    }

    private WebView mWebview;

    private String installBtnClick_Script = "javascript: {var btn = document.getElementsByClassName(\'price buy id-track-click id-track-impression\');btn[0].click();};";
    private String popupInstallBtnClick_Script = "javascript: {var btn = document.getElementsByClassName(\'play-button apps loonie-ok-button\');btn[0].click();};";

    private final String basicMissionUrl = "https://play.google.com/store/apps/details?id=";
    private String pkg = "";


    private ArrayList<String> missionDatas = new ArrayList<>();


    enum STATUS {
        GETDEVICES,
        MISSION,
        NONE
    }

    STATUS status = STATUS.NONE;

    /**
     * 정지시키기 위한 변수
     */
    public boolean stopFlag = false;

    public void cancleAll() {
        status = STATUS.NONE;
//        missionDatas.clear();
        handler.removeMessages(1);
        handler.removeMessages(2);
        handler.removeMessages(3);
        if (missionCallback != null) {
            missionCallback.stopall();
        }

        if (mWebview != null) {
            mWebview.stopLoading();
        }
        stopFlag = true;

         
        mWebview.pauseTimers();
    }

    public boolean isCancled() {
        return status == STATUS.NONE;
    }


    public void addMission(String missionData) {
        if (!missionDatas.contains(missionData)) {
            missionDatas.add(missionData);
        }
    }

    public void closeAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        alarmManager.cancel(pIntent);
    }


    /**
     * @param context
     * @param custom_hour   -1 이면 없는값
     * @param custom_minute
     */
    public void openAlarm(Context context, int custom_hour, int custom_minute) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        alarmManager.cancel(pIntent);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if (hour > custom_hour || (hour == custom_hour && minute >= custom_minute)) { //내일
            calendar.setTimeInMillis(System.currentTimeMillis() + 1000 * 3600 * 24);
            if (custom_hour != -1) {
                calendar.set(Calendar.HOUR_OF_DAY, custom_hour);
            }
            if (custom_minute != -1) {
                calendar.set(Calendar.MINUTE, custom_minute);
            }
            calendar.set(Calendar.SECOND, 0);
        } else {//오늘
            if (custom_hour != -1) {
                calendar.set(Calendar.HOUR_OF_DAY, custom_hour);
            }
            if (custom_minute != -1) {
                calendar.set(Calendar.MINUTE, custom_minute);
            }
            calendar.set(Calendar.SECOND, 0);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        String format = simpleDateFormat.format(calendar.getTime());
        UtilsLog.v("demo", "알람 시작시간 :" + format);


        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 3600 * 24, pIntent);
    }


    public void init(Context context, WebView XWalkView) {
        this.mContext = context;
        this.stopFlag = false;
        initXWalkView(context, XWalkView);
    }

    public boolean isUtilsLogined() {
        return false;
    }

    public String getGoogleAccount() {
        return "";
    }


    /**
     * 미션 시작
     *
     * @param pkg
     */

    public MissionCallback missionCallback;


    /**
     * 설치됐었던지 체그 , 미션대기 리스트에서 하나씩불러와서 callback호출
     * 미션시작여뷰를 알림 시작해도 되면 start호출
     *
     * @param missionCallback
     */
    public void prepareMession(MissionCallback missionCallback) {
        if (status != STATUS.NONE || stopFlag) return;
        this.missionCallback = missionCallback;
        if (missionDatas.size() > 0) {
            UtilsLog.v("demo", "총 " + missionDatas.size() + "개 미션 대기중_____________________________________________________________________________");
            UtilsLog.v("demo", "Mission Prepare!!!!" + ", pkg:" + missionDatas.get(0));
                if (this.missionCallback != null) {
                    AutoInstallHelper.getInstance().missionCallback.onPrepare(this.missionDatas.get(0));
                }
        } else {
            status = STATUS.NONE;
            UtilsLog.v("demo", "Mission Clear!");
            try {
                cancleAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * After prepareMession
     */
    public void start() {
        if (stopFlag) return;
        try {
            UtilsLog.v("demo", "Mission Start!!!!" + ", pkg:" + missionDatas.get(0));
            status = STATUS.MISSION;
            mWebview.loadUrl(basicMissionUrl + missionDatas.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * After prepareMession
     */
    public void start(String url ) {
        if (stopFlag) return;
        try {
            UtilsLog.v("demo", "Mission Start!!!!" + ", pkg:" + missionDatas.get(0));
            status = STATUS.MISSION;
            mWebview.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void next() {
        if (stopFlag) return;
        UtilsLog.v("demo", "nextMission");
        if (missionDatas.size() > 0) {
            missionDatas.remove(0);
        }
        status = STATUS.NONE;
        prepareMession(this.missionCallback);
    }


    class JavaScriptInterface {

        @JavascriptInterface
        public void getHTML(String url, final String html) {
            if (!TextUtils.isEmpty(html)) {
                onDataParse(url, html);
            }
        }

    }


    private void initXWalkView(Context context, WebView myview) {
        if (myview != null) {
            this.mWebview = myview;
        } else {
            this.mWebview = new WebView(context);
        }
        WebSettings set = this.mWebview.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(false);
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        set.setSupportMultipleWindows(true);
        set.setDatabaseEnabled(true);
        set.setDomStorageEnabled(true);
        set.setUseWideViewPort(true);
        set.setLoadWithOverviewMode(true);
        set.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
//        mWebview.addJavascriptInterface(new JavaScriptInterface(), "android");
        set.setRenderPriority(WebSettings.RenderPriority.HIGH);
        set.setLoadsImagesAutomatically(false);
        set.setBlockNetworkImage(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 0x15) {
            set.setMixedContentMode(0x0);
            cookieManager.setAcceptThirdPartyCookies(mWebview, true);
        }
        if (Build.VERSION.SDK_INT >= 0x10) {
            set.setAllowUniversalAccessFromFileURLs(true);
        }

        mWebview.setWebChromeClient(new WebChromeClient() {
        });
        mWebview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                UtilsLog.v("demo", "PageFinished:" + url);
                if (AutoInstallHelper.this.status == STATUS.MISSION) {

                    //step 0:
                    if (url.contains("https://accounts.google.com")) {
                        //로그인 안됨
                        cancleAll();
                        return;
                    }

                    //step 1:
                    if (url.contains(basicMissionUrl)) {
                        handler.sendEmptyMessageDelayed(0, 15000L);
                        handler.sendEmptyMessageDelayed(1, 30000L);
                        handler.sendEmptyMessageDelayed(2, 120000L);
                    }


                }

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                UtilsLog.v("demo", "PageStarted:" + url);
            }
        });
    }


    public Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (stopFlag) return;
            switch (msg.what) {

                case 0:
                    if (status == STATUS.MISSION) {
                        UtilsLog.v("demo", "installButton");
                        mWebview.resumeTimers();
                        mWebview.loadUrl(installBtnClick_Script);//
                    }
                    break;
                case 1:
                    if (status == STATUS.MISSION) {
                        UtilsLog.v("demo", "popupInstallButton");
                        mWebview.resumeTimers();
                        mWebview.loadUrl(popupInstallBtnClick_Script);//
                        if (missionDatas.size() > 0) {
                            oldMissionData.add(missionDatas.get(0));
                        }
                    }
                    break;
                case 2:
                    next();
                    break;
            }

        }
    };


    public WebView getmWebview() {
        return mWebview;
    }

    public ArrayList<String> oldMissionData = new ArrayList<>();

  /*  private XWalkViewClient XWalkViewClient = new XWalkViewClient() {

        @Override
        public void onPageStarted(XWalkView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            UtilsLog.v("demo", "PageStart:" + url);


        }

        @Override
        public void onPageFinished(XWalkView view, String url) {
            super.onPageFinished(view, url);
            UtilsLog.v("demo", "PageFinished:" + url);
            UtilsLog.v("demo", "PageFinished:" + view.toString());


            if (status == STATUS.MISSION) {

                //step 0:
                if (url.contains("https://accounts.google.com")) {
                    //로그인 안됨
                    cancleAll();
                    return;
                }

                //step 1:
                if (url.contains(basicMissionUrl)) {
                    handler.sendEmptyMessageDelayed(0,15000L);
                    handler.sendEmptyMessageDelayed(1,30000L);
                    handler.sendEmptyMessageDelayed(2,120000L);
                }


            }
            mWebview.load("javascript:window.android.getHTML('" + url + "','<html>'+document.body.innerHTML+'</html>');","");

        }

    };*/

//    DeviceInfoCallback deviceInfoCallback = null;
//    private final String devicegetUrl = "https://play.google.com/store/apps/details?id=cq.game.fivechess";
//
//    public void getDevices(DeviceInfoCallback deviceInfoCallback) {
//        status = STATUS.GETDEVICES;
//        this.deviceInfoCallback = deviceInfoCallback;
//        mWebview.loadUrl(devicegetUrl);
//
//    }

    private void onDataParse(String url, String html) {
/*

        Document document = Jsoup.parse(html);
        if (url.contains("https://accounts.google.com")) {
            //로그인 안됨


            return;
        }



        //기기정보 가져옴무니다
        if (url.contains("/store/apps/details/diaUtilsLog/app_diaUtilsLog_permission_buckets")) {


            ArrayList<DeviceInfo> devices = new ArrayList<>();//id-displayed-device
            Elements selected_device = document.getElementsByClass("id-displayed-device");
            DeviceInfo selected_deviceInfo = new DeviceInfo();
            if (selected_device != null && selected_device.size() > 0) {
                selected_deviceInfo.deviceName = selected_device.get(0).childNode(0).toString();
                selected_deviceInfo.isSelected = 1;
                devices.add(selected_deviceInfo);
            }

            Elements unselected_devices = document.getElementsByClass("device-selector-dropdown-child");

            boolean existActiveDevice= false; //실제 사용가능 혹은 사용가능 디바이스가 있을경우.

            if (unselected_devices != null) {
                for (Element unselected_device : unselected_devices) {
                    DeviceInfo deviceInfo = new DeviceInfo();
                    Elements device_title = unselected_device.getElementsByClass("device-title");
                    if (device_title != null) {
                        deviceInfo.deviceName = device_title.get(0).childNode(0).toString();
                    }
                    deviceInfo.isSelected = 0;
                    if (selected_deviceInfo.deviceName.equalsIgnoreCase(deviceInfo.deviceName)) {
                        existActiveDevice = true;
                    }
                    devices.add(deviceInfo);
                }
            }

            if(existActiveDevice){
                devices.remove(0);
            }

            if(status == STATUS.GETDEVICES){
                if(deviceInfoCallback!=null){
                    deviceInfoCallback.onResponse(true,devices);
                }
            }

        }
*/


        //미션 시작
//        if(url.equalsIgnoreCase("")){
//            //// STOPSHIP: 2016-08-22
//            if(status == STATUS.MISSION){
//                if(existActiveDevice){
//
//                    mWebView.loadUrl(popupInstallBtnClick_Script);//
//                }else{
//                    if(missionCallback!=null){
//                        missionCallback.onResponse(false,pkg);
//                    }
//                }
//            }
//
//
//        }


    }


}
