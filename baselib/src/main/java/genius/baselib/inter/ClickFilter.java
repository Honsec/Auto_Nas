package genius.baselib.inter;

/**
 * Created by Hongsec on 2016-08-04.
 */
public class ClickFilter  {

    private long lasttime = 0;

    private long check_time = 500l;

    public ClickFilter() {
    }

    public ClickFilter(long check_time) {
        this.check_time = check_time;
    }

    public boolean isClicked(){

        long l = System.currentTimeMillis();
        if(l - lasttime > check_time){
            lasttime = l;
            return  false;
        }

        return  true;
    }

    public long getCheck_time() {
        return check_time;
    }

    public void setCheck_time(long check_time) {
        this.check_time = check_time;
    }
}
