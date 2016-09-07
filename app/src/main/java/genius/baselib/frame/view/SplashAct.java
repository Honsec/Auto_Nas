package genius.baselib.frame.view;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.genius.hongsec.nasa.R;

import genius.baselib.frame.auto.AutoAccount;
import genius.baselib.frame.base.BaseAct;
import genius.baselib.frame.inter.CheckLoginResult;
import genius.baselib.inter.ClickFilter;

/**
 * Created by Hongsec on 2016-09-06.
 */
public class SplashAct extends BaseAct {
    @Override
    protected int setContentLayoutResID() {
        return R.layout.act_splash;
    }

    @Override
    protected void viewLoadFinished() {
        if (CheckPermission_request(permissions, 1)) return;
        doActions();
    }

    private void doActions() {

        showLoading();
        AutoAccount.checklogin(this, new CheckLoginResult() {
            @Override
            public void result(boolean islogined) {
                cancleLoading();
                if(islogined){
                    Intent intent = new Intent(SplashAct.this,MainAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashAct.this,GoogleLoginAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    startActivity(intent);
                }
                SplashAct.this.finish();
            }
        });


    }

    String[] permissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ,
//            Manifest.permission.GET_ACCOUNTS
    };

    @Override
    protected void initViews() {

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case 1:
                boolean flag = false;
                //권한 신청이 거부되였을 경우 앱을 끕니다.
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        flag = true;
                    }
                }

                if(!flag){
                    doActions();
                }

                break;
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
            Toast.makeText(SplashAct.this, R.string.close_app, Toast.LENGTH_SHORT).show();
        }

    }

}






