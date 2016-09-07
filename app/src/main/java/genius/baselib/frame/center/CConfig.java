package genius.baselib.frame.center;

import android.content.Context;

import genius.baselib.center.Config;

/**
 * Created by Hongsec on 2016-09-05.
 */
public class CConfig {

    public static boolean is_debug = false;
    public static boolean is_log = true;

    public static String BASE_URL = "";

    public static void init(Context context){

        Config.init(is_log,"I will be back",false);
//        if(is_debug){
//            BASE_URL = CStatic.DEV_URL;
//        }else{
//            BASE_URL = CStatic.REAL_URL;
//        }

    }

}
