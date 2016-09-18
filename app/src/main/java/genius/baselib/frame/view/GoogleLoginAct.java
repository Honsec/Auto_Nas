package genius.baselib.frame.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.CookieManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.genius.hongsec.nasa.R;

import genius.baselib.PreferenceUtil;
import genius.baselib.frame.base.BaseAct;
import genius.baselib.frame.center.CStatic;
import genius.baselib.inter.ClickFilter;
import genius.utils.UtilsActivity;
import genius.utils.UtilsLog;
import genius.utils.UtilsSP;

/**
 * Created by Hongsec on 2016-09-07.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public class GoogleLoginAct extends BaseAct {


    private WebView id_google_webview;
    /**
     * 처음 열떄 로딩
     */
    private boolean first = true;

    @Override
    protected int setContentLayoutResID() {
        return R.layout.act_googleloginact;
    }

    @Override
    protected void viewLoadFinished() {
        UtilsActivity.getInstance().killAllActivities(GoogleLoginAct.class);
        id_google_webview.loadUrl("https://accounts.google.com/AddSession?sacu=1&continue=https%3A%2F%2Fplay.google.com%2Fstore%2F&hl=ko&service=googleplay#identifier");
    }

    @Override
    protected void initViews() {
        id_google_webview = findViewBId(R.id.id_google_webview);
        Setting_webview();
        id_google_webview.setWebChromeClient(new WebChromeClient());
        id_google_webview.setWebViewClient(new WebViewClientCustom());
    }

    private void Setting_webview() {
        WebSettings settings = id_google_webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setSavePassword(true);
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if (Build.VERSION.SDK_INT >= 0x15) {
            settings.setMixedContentMode(0x0);
            cookieManager.setAcceptThirdPartyCookies(id_google_webview, true);
        }
        if (Build.VERSION.SDK_INT >= 0x10) {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
    }

    private class WebViewClientCustom extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            UtilsLog.i("onPageFinished url:" + url);
            GoogleLoginAct.this.cancleLoading();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            UtilsLog.i("onPageStarted url:" + url);

            if (url.startsWith("https://play.google.com/store")) {

                UtilsSP instance = PreferenceUtil.getInstance(getApplicationContext());
                instance.setValue(CStatic.SP_LOGINSTATE,true);

                // 로그인 성공
                Intent intent = new Intent(GoogleLoginAct.this, MainAct.class);
                intent.setFlags( Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                GoogleLoginAct.this.finish();

            } else {
                if (first) {
                    first = false;
                    GoogleLoginAct.this.showLoading();
                }
            }

        }
    }

    /**
     * 앱끄기 필터
     */
    ClickFilter closeApp = new ClickFilter(2000L);

    @Override
    public void onBackPressed() {
        if(closeApp.isClicked()){
            super.onBackPressed();
        }else{
            Toast.makeText(GoogleLoginAct.this, R.string.close_app, Toast.LENGTH_SHORT).show();
        }

    }
}
