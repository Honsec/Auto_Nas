package genius.baselib.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import de.greenrobot.event.EventBus;
import genius.baselib.bus.BusMessage;
import genius.baselib.bus.BusTool;
import genius.baselib.receiver.NetworkReceiver;
import genius.utils.UtilsNetwork;

/**
 * Created by Hongsec on 2016-07-21.
 */
public abstract  class BaseActivity extends BaseAbstractActivity {

    protected BaseActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RegisterBus();
        mContext = this;

        NetworkReceiver.checkNetWork(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        UnRegisterBus();
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


    private void RegisterBus(){
        //이벤트 버스 등록
        try {
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 자식에서 쓸필요 없음
     *
     * @param myBus
     */
    public void onEvent(BusMessage myBus) {
        if (BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEvent, this.getClass().getSimpleName())) {
            return;
        }
        Bus_onEvent(myBus);
    }

    ;

    /**
     * 자식에서 쓸필요 없음
     *
     * @param myBus
     */
    public void onEventMainThread(BusMessage myBus) {
        if (BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEventMainThread, this.getClass().getSimpleName())) {
            return;
        }
        Bus_onEventMainThread(myBus);
    }

    ;

    /**
     * 자식에서 쓸필요 없음
     *
     * @param myBus
     */
    public void onEventBackgroundThread(BusMessage myBus) {
        if (BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEventBackgroundThread, this.getClass().getSimpleName())) {
            return;
        }
        Bus_onEventBackgroundThread(myBus);
    }

    ;

    /**
     * 자식에서 쓸필요 없음
     *
     * @param myBus
     */
    public void onEventAsync(BusMessage myBus) {
        if (BusTool.onEventBusFilter(myBus, BusMessage.BUSTYPE.onEventAsync, this.getClass().getSimpleName())) {
            return;
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

    }




    /**
     * 如果使用onEvent作为订阅函数，那么该事件在哪个线程发布出来的，
     onEvent就会在这个线程中运行，也就是说发布事件和接收事件线程在同一个线程。
     使用这个方法时，在onEvent方法中不能执行耗时操作
     ，如果执行耗时操作容易导致事件分发延迟。
     * @param myBus
     */
    protected   void Bus_onEvent(BusMessage myBus){};

    /**
     *   如果使用onEventMainThread作为订阅函数，
     那么不论事件是在哪个线程中发布出来的，onEventMainThread都会在UI线程中执行
     ，接收事件就会在UI线程中运行，这个在Android中是非常有用的，
     因为在Android中只能在UI线程中跟新UI，所以在onEvnetMainThread方法中是不能执行耗时操作的。
     * @param myBus
     */
    protected   void Bus_onEventMainThread(BusMessage myBus){};

    /**
     *  如果使用onEventBackgrond作为订阅函数，那么如果事件是在UI线程中发布出来的，那么
     onEventBackground就会在子线程中运行，
     如果事件本来就是子线程中发布出来的，那么onEventBackground函数直接在该子线程中执行。
     * @param myBus
     */
    protected   void Bus_onEventBackgroundThread(BusMessage myBus){};


    /**
     *   使用这个函数作为订阅函数，
     那么无论事件在哪个线程发布，都会创建新的子线程在执行onEventAsync.
     * @param myBus
     */
    protected  void Bus_onEventAsync(BusMessage myBus){};
    protected   void onNetNOT_CONNECTED(){};
    protected   void onNetWIFI_CONNECTED(){};
    protected   void onNetMOBILE_CONNECTED(){};


}
