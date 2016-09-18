package genius.baselib.frame.auto;

/**
 * Created by Hongsec on 2016-08-22.
 */
public interface MissionCallback {

    /**
     * 미션 다운로드 완료
     * @param pkg
     */
    public void onInstalled(String pkg);


    public void onPrepare(String data);

    public void stopall();
}
