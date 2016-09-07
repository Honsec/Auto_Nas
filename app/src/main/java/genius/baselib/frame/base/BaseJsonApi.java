package genius.baselib.frame.base;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.sera.hongsec.volleyhelper.JsonRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import genius.baselib.PreferenceUtil;
import genius.baselib.frame.center.CConfig;
import genius.baselib.frame.center.CStatic;
import genius.baselib.frame.util.CTools;
import genius.utils.Utils_Alert;

/**
 * Created by Hongsec on 2016-09-05.
 */
public abstract class BaseJsonApi<T> extends JsonRequest<T> {

    private Context mContext;
    private String reg_id = "";
    private int em = -1;
    private String ses = "";
    private int version = 0;

    public BaseJsonApi(Context context) {

        if(CConfig.is_debug){
            CConfig.BASE_URL = CStatic.DEV_URL;
        }else{
            CConfig.BASE_URL = CStatic.REAL_URL;
        }

        this.mContext = context;
        //버전
        version = PreferenceUtil.getInstance(context.getApplicationContext()).getValue(CStatic.SP_VERSION_CODE, 0);
        reg_id = PreferenceUtil.getInstance(context.getApplicationContext()).getValue(CStatic.SP_REGID, "");

    }

    @Override
    public String baseUrl() {

        return CConfig.BASE_URL;
    }

    @Override
    public String getCommonParams() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append("&");
            stringBuilder.append("ve=" + version);
            stringBuilder.append("&");
            stringBuilder.append("mo=" + URLEncoder.encode(Build.MODEL, "utf8"));
            stringBuilder.append("&");
            stringBuilder.append("ov=" + URLEncoder.encode(Build.VERSION.RELEASE, "utf8"));

            if (!TextUtils.isEmpty(ses)) {
                stringBuilder.append("&");
                stringBuilder.append("se=" + ses);
            }
            if (em!=-1) {
                stringBuilder.append("&");
                stringBuilder.append("em=" + URLEncoder.encode(CTools.Encode(em+""), "utf8"));
            }
            if (!TextUtils.isEmpty(reg_id)) {
                stringBuilder.append("&");
                stringBuilder.append("re=" + reg_id);
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    @Override
    public void ErrorStatusProcess(int statusCode) {

        switch (statusCode) {

            case 401://logout

                CTools.logout(mContext);

                break;
            case 409:

                if(!TextUtils.isEmpty(result.msg)){
                    Utils_Alert.showAlertDialog(mContext,0,result.msg,false,android.R.string.ok,null,0,null,null);
                }


                break;

        }

    }
}
