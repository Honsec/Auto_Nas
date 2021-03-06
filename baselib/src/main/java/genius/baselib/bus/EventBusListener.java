package genius.baselib.bus;

/**
 * Created by Hongsec on 2016-04-17.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public interface EventBusListener {


    /**
     * 如果使用onEvent作为订阅函数，那么该事件在哪个线程发布出来的，
     onEvent就会在这个线程中运行，也就是说发布事件和接收事件线程在同一个线程。
     使用这个方法时，在onEvent方法中不能执行耗时操作
     ，如果执行耗时操作容易导致事件分发延迟。
     * @param myBus
     */
    public void onEvent(BusMessage myBus);

    /**
     *   如果使用onEventMainThread作为订阅函数，
     那么不论事件是在哪个线程中发布出来的，onEventMainThread都会在UI线程中执行
     ，接收事件就会在UI线程中运行，这个在Android中是非常有用的，
     因为在Android中只能在UI线程中跟新UI，所以在onEvnetMainThread方法中是不能执行耗时操作的。
     * @param myBus
     */
    public void onEventMainThread(BusMessage myBus);

    /**
     *  如果使用onEventBackgrond作为订阅函数，那么如果事件是在UI线程中发布出来的，那么
     onEventBackground就会在子线程中运行，
     如果事件本来就是子线程中发布出来的，那么onEventBackground函数直接在该子线程中执行。
     * @param myBus
     */
    public void onEventBackgroundThread(BusMessage myBus);


    /**
     *   使用这个函数作为订阅函数，
     那么无论事件在哪个线程发布，都会创建新的子线程在执行onEventAsync.
     * @param myBus
     */
    public void onEventAsync(BusMessage myBus);
}
