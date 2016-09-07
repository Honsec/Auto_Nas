package genius.baselib.frame.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import genius.baselib.base.BaseActivity;
import genius.baselib.frame.view.widget.ProgressDialog;

/**
 * Created by Hongsec on 2016-09-05.
 */
public abstract class BaseAct extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean check_device() {
        boolean result = false;
        result |= Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        // in xiaomi should return true must
        result |= "xiaomi".equalsIgnoreCase(Build.MANUFACTURER.toLowerCase());
        return result;
    }


    protected ProgressDialog progressDialog ;

    public void showLoading(){
        if(progressDialog==null){
            progressDialog = new ProgressDialog(this);
        }
        if(!progressDialog.isShowing()){
            progressDialog.show();
        }

    }
    public void cancleLoading(){
        if(progressDialog==null){
            progressDialog = new ProgressDialog(this);
        }
        if(progressDialog.isShowing()){
            progressDialog.cancel();
        }

    }

}
