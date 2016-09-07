package genius.baselib.base;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import genius.utils.UtilsActivity;

/**
 * Created by Hongsec on 2016-07-21.
 */
public abstract class BaseAbstractActivity extends AppCompatActivity {


    protected  String TAG = this.getClass().getSimpleName();

    /**
     * 앱티비티가 로드 완료되였는지 표기하는 필더 onWindowFocusChanged 참고
     */
    private boolean loaded = false;

    /**
     * ContentView ResourceID
     *
     * @return
     */
    protected abstract int setContentLayoutResID();

    /**
     * 액티비티 뷰가 완전히 로드되였을때 호출됨 <br>
     * (액티비티가 완전히 로드되였는지는 onWindowsFocused가 호출될때 완료되였다고 판단함 )
     */
    protected abstract void viewLoadFinished();



    /**
     * 뷰초기화  in onCreate
     */
    protected abstract void initViews();





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(setContentLayoutResID()!=0){
            setContentView(setContentLayoutResID());
        }
        initViews();
        //activity관리를 위해 register 하여 저장
        UtilsActivity.getInstance().registerActivity(this, getClass().getSimpleName());

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {

            if (!loaded) {
                //로드완료
                loaded = true;

                viewLoadFinished();
            }


        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loaded = false;
        //activity 관리자에서 제거
        UtilsActivity.getInstance().unregisterActivity(this, getClass().getSimpleName());

    }



    /**
     * findViewById를 다시 만듬
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewBId(@IdRes int id) {
        return (T) super.findViewById(id);
    }


    /**
     * 권한체크여부  쇼미는 무조건 권한체크함. 기타 6.0이상만 함<br/>
     * boolean result = false;<br/>
     * result |= Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;<br/>
     * // in xiaomi  should return true must<br/>
     * result |= "xiaomi".equalsIgnoreCase(Build.MANUFACTURER.toLowerCase());<br/>
     * return result;<br/>
     *
     * @return
     */
    public abstract boolean check_device();

    /**
     * Only  23API ,권한체크 <br>
     * 원래는 6.0 이상만 체크하였지만 쇼미때문에 모두 체크하게됨
     *
     * @param manifests
     * @param requestCode
     * @return true means  need request permission
     */
    public boolean CheckPermission_request(String[] manifests, int requestCode) {

        if (!check_device()) {
            return false;
        }

        String[] requestmanifests = null;
        ArrayList<String> requestmanifests_list = new ArrayList<String>();

        boolean result = false;
        boolean temp = true;
        for (String manifest : manifests) {
            temp = isPermissioned(manifest);

            if (!temp) {
                requestmanifests_list.add(manifest);
                result = true;
            }
        }

        if (result) {

            requestmanifests = new String[requestmanifests_list.size()];
            for (int i = 0; i < requestmanifests_list.size(); i++) {
                requestmanifests[i] = requestmanifests_list.get(i);
            }

            ActivityCompat.requestPermissions(this, requestmanifests, requestCode);
        }

        return result;
    }


    /**
     * 권한부여 여부
     *
     * @param manifest_permission
     * @return
     */
    public boolean isPermissioned(String manifest_permission) {

        try {
            return ActivityCompat.checkSelfPermission(this, manifest_permission) == PackageManager.PERMISSION_GRANTED;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  true;
    }


}
