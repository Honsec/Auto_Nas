package genius.baselib.frame.auto;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Build;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import genius.baselib.PreferenceUtil;
import genius.baselib.frame.center.CStatic;
import genius.baselib.frame.inter.CheckLoginResult;
import genius.baselib.frame.inter.GoogleUserInfo;
import genius.baselib.frame.util.CTools;
import genius.utils.UtilsLog;
import genius.utils.UtilsSP;


/**
 * Check Google Login State
 * Created by Hongsec on 2016-08-31.
 */
public class AutoAccount {


    private static WebView webview;
    private static MyGoogleUserInfoInterface result_interface;

    /**
     *  로그인 체크가 안되는 폰을 위해서 이거 쓰기로 했으
     * @param activity
     * @param checkLoginResult
     */
    public static void checklogin(Activity activity,CheckLoginResult checkLoginResult){

        UtilsSP instance = PreferenceUtil.getInstance(activity.getApplicationContext());
        if(instance.getValue(CStatic.SP_LOGINSTATE,false)){
            if(checkLoginResult!=null){
                checkLoginResult.result(true);
            }

        }else{
            if(checkLoginResult!=null){
                checkLoginResult.result(false);
            }
        }

    }
/*

    public static void checklogin(Activity act, final CheckLoginResult checkLoginResult) {

        final WebView webview = new WebView(act);
        webview.setWebChromeClient(new WebChromeClient() {
        });
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                UtilsLog.v("demo", "PageFinished:" + url);
                webview.loadUrl("javascript:{ " +
                        "var value = document.getElementsByClassName('profile-name');   " +
                        " if(value.length <= 0){window.android.getLoginId('');} " +
                        "      else{ window.android.getLoginId(value[0].innerHTML);}" +
                        "};");

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                UtilsLog.v("demo", "onPageStarted:" + url);
            }


        });
        WebSettings set = webview.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(false);
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        set.setSupportMultipleWindows(true);
        set.setDatabaseEnabled(true);
        set.setDomStorageEnabled(true);
        webview.addJavascriptInterface(new MyGoogleLoginInterface(checkLoginResult), "android");
        set.setUseWideViewPort(true);
        set.setLoadWithOverviewMode(true);
        set.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        set.setSavePassword(true);
        set.setRenderPriority(WebSettings.RenderPriority.HIGH);
        set.setLoadsImagesAutomatically(false);
        set.setBlockNetworkImage(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 0x15) {
            set.setMixedContentMode(0x0);
            cookieManager.setAcceptThirdPartyCookies(webview, true);
        }
        if (Build.VERSION.SDK_INT >= 0x10) {
            set.setAllowUniversalAccessFromFileURLs(true);
        }
        webview.loadUrl("https://play.google.com/store/account");

    }
*/
    public static void getUserInfo(final Activity act, final GoogleUserInfo checkLoginResult) {

        webview = new WebView(act);
        webview.setWebChromeClient(new WebChromeClient() {
        });
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                UtilsLog.v("demo", "PageFinished:" + url);
                if(url.contains("javascript:")){

                    result_interface.getUserInfo(CTools.getIMEI(act.getApplicationContext()),CTools.getIMEI(act.getApplicationContext()));
                    return;
                }
                webview.loadUrl(" javascript:\n" +
                        "\n" +
                        "            {\n" +
                        "                var account_ele = document.getElementsByClassName('account-email');\n" +
                        "                var account = '';\n" +
                        "                if (account_ele) {\n" +
                        "                    account = account_ele[0].innerHTML.trim();\n" +
                        "                }\n" +
                        "                var name_ele = document.getElementsByClassName('account-name');\n" +
                        "                var name = '';\n" +
                        "                if (name_ele) {\n" +
                        "                    name = name_ele[0].innerHTML.trim();\n" +
                        "                }\n" +
                        "                window.android.getUserInfo(name, account);\n" +
                        "            };");

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                UtilsLog.v("demo", "onPageStarted:" + url);
            }


        });
        WebSettings set = webview.getSettings();
        set.setJavaScriptEnabled(true);
        set.setBuiltInZoomControls(false);
        set.setJavaScriptCanOpenWindowsAutomatically(true);
        set.setSupportMultipleWindows(true);
        set.setDatabaseEnabled(true);
        set.setDomStorageEnabled(true);
        webview.addJavascriptInterface(result_interface = new MyGoogleUserInfoInterface(checkLoginResult), "android");
        set.setUseWideViewPort(true);
        set.setLoadWithOverviewMode(true);
        set.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        set.setSavePassword(true);
        set.setRenderPriority(WebSettings.RenderPriority.HIGH);
        set.setLoadsImagesAutomatically(false);
        set.setBlockNetworkImage(true);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 0x15) {
            set.setMixedContentMode(0x0);
            cookieManager.setAcceptThirdPartyCookies(webview, true);
        }
        if (Build.VERSION.SDK_INT >= 0x10) {
            set.setAllowUniversalAccessFromFileURLs(true);
        }
        webview.loadUrl("https://accounts.google.com/SignOutOptions?service=googleplay&continue=https%3A%2F%2Fplay.google.com%2Fstore%2Fpeople%2Fdetails%3Fid%3D115420726289778456716\n");

    }


    private static class MyGoogleLoginInterface {

        private CheckLoginResult checkLoginResult;

        public MyGoogleLoginInterface(CheckLoginResult checkLoginResult) {
            this.checkLoginResult = checkLoginResult;
        }

        @JavascriptInterface
        public void getLoginId(String loginid) {

            if (checkLoginResult != null) {
                if (TextUtils.isEmpty(loginid)) {
                    checkLoginResult.result(false);
                } else {
                    checkLoginResult.result(true);

                }
            }
            UtilsLog.d("logiID:" + loginid);
        }



    }


    private static class MyGoogleUserInfoInterface {

        private GoogleUserInfo googleUserInfo;

        public MyGoogleUserInfoInterface(GoogleUserInfo googleUserInfo) {
            this.googleUserInfo = googleUserInfo;
        }

        @JavascriptInterface
        public void getUserInfo(String nickname,String account) {

            if (googleUserInfo != null) {
                googleUserInfo.result(nickname,account);
            }
            if(webview!=null){
                webview.stopLoading();
                webview.setWebChromeClient(null);
                webview.setWebViewClient(null);
                webview.destroy();
                webview = null;
            }
            UtilsLog.d("userinfo:" +nickname+",account:"+account);
        }
    }
}
