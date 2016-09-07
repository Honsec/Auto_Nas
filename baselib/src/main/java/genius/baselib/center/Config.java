package genius.baselib.center;

import genius.baselib.receiver.NetworkReceiver;
import genius.utils.UtilsLog;

/**
 * Created by Hongsec on 2016-08-01.
 */
public class Config {


    /**
     *
     * @param isshowlog 是否显示log
     * @param default_logmsg 默认的logmessage
     * @param ischeckNet 是否检测网络状态，在每个界面
     */
    public static  void init(boolean isshowlog,String default_logmsg,boolean ischeckNet){

        //是否在每个界面检测网路状态
        NetworkReceiver.checkEnable = ischeckNet;
        UtilsLog.init(isshowlog,default_logmsg);

    }


}
