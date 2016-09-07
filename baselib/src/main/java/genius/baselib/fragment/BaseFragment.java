package genius.baselib.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.greenrobot.event.EventBus;
import genius.baselib.base.BaseActivity;
import genius.baselib.base.BaseApplication;
import genius.baselib.bus.BusMessage;
import genius.baselib.bus.BusTool;
import genius.baselib.receiver.NetworkReceiver;
import genius.utils.UtilsNetwork;

/**
 * Created by Hongsec on 2016-07-21.
 */
public abstract class BaseFragment extends BaseAbstractFragment {


    protected   void onNetNOT_CONNECTED(){};
    protected   void onNetWIFI_CONNECTED(){};
    protected   void onNetMOBILE_CONNECTED(){};
    protected  void Bus_onEvent(BusMessage myBus){};
    protected  void Bus_onEventMainThread(BusMessage myBus){};
    protected  void Bus_onEventBackgroundThread(BusMessage myBus){};
    protected  void Bus_onEventAsync(BusMessage myBus){};


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RegisterBus();

        NetworkReceiver.checkNetWork(getBaseParent());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        UnRegisterBus();
    }


    protected BaseActivity getBaseParent(){
        return getActivity()==null?null:(BaseActivity) getActivity();
    }

    protected BaseApplication getBaseApp(){
        return getBaseParent()==null?null:(getBaseParent().getApplication()==null?null:(BaseApplication) getBaseParent().getApplication());
    }


    private void UnRegisterBus() {
        try {
            //이벤트 버스 해제
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void RegisterBus() {
        try {
            //이벤트 버스 해제
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 자식에서 쓸필요 없음
     * @param myBus
     */
    public void onEvent(BusMessage myBus){
        if(BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEvent,this.getClass().getSimpleName())){
            return ;
        }
        Bus_onEvent(myBus);
    };

    /**
     * 자식에서 쓸필요 없음
     * @param myBus
     */
    public void onEventMainThread(BusMessage myBus){
        if(BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEventMainThread,this.getClass().getSimpleName())){
            return ;
        }
        Bus_onEventMainThread(myBus);
    };

    /**
     * 자식에서 쓸필요 없음
     * @param myBus
     */
    public void onEventBackgroundThread(BusMessage myBus){
        if(BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEventBackgroundThread,this.getClass().getSimpleName())){
            return ;
        }
        Bus_onEventBackgroundThread(myBus);
    };

    /**
     * 자식에서 쓸필요 없음
     * @param myBus
     */
    public void onEventAsync(BusMessage myBus){
        if(BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEventAsync,this.getClass().getSimpleName())){
            return ;
        }
        Bus_onEventAsync(myBus);

        if(myBus.getAction_code() == NetworkReceiver.UPDATE_NET){
            //인터넷 체크하여
            UtilsNetwork.TYPE connectivityStatus = (UtilsNetwork.TYPE) myBus.getObject();
            if(connectivityStatus == UtilsNetwork.TYPE.NOT_CONNECTED){
                onNetNOT_CONNECTED();
            }else if(connectivityStatus == UtilsNetwork.TYPE.WIFI){
                onNetWIFI_CONNECTED();
            }else if(connectivityStatus == UtilsNetwork.TYPE.MOBILE){
                onNetMOBILE_CONNECTED();
            }
        }

    };


}
