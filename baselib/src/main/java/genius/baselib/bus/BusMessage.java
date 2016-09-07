package genius.baselib.bus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hongsec on 2016-04-17.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public class BusMessage {

    public enum BUSTYPE{ onEvent ,onEventMainThread,onEventBackgroundThread,onEventAsync};

    private BUSTYPE bustype;

    /**
     * Target class Name <br/>
     */
    private List<String> target_name=new ArrayList<>();

    /**
     * Target to all
     */
    private boolean target_all=false;


    /**
     * Can save anything
     */
    private Object object = null;

    private int action_code = 0;





    public BUSTYPE getBustype() {
        return bustype;
    }

    public List<String> getTarget_name() {
        return target_name;
    }

    public boolean isTarget_all() {
        return target_all;
    }

    public Object getObject() {
        return object;
    }

    public int getAction_code() {
        return action_code;
    }

    public BusMessage setBustype(BUSTYPE bustype) {
        this.bustype = bustype;
        return  this;
    }

    public BusMessage setTarget_name(List<String> target_name) {
        this.target_name = target_name;
        return this;
    }

    public BusMessage setTarget_all(boolean target_all) {
        this.target_all = target_all;
        return  this;
    }

    public BusMessage add_targetName(String target_name) {
        this.target_name.add(target_name);
        return  this;
    }

    public BusMessage setObject(Object object) {
        this.object = object;
        return  this;
    }

    public BusMessage setAction_code(int action_code) {
        this.action_code = action_code;
        return  this;
    }

    public BusMessage() {
    }

    public BusMessage(BUSTYPE bustype, boolean target_all, Object object, int action_code) {
        this.bustype = bustype;
        this.target_all = target_all;
        this.object = object;
        this.action_code =action_code;
    }
    public BusMessage(BUSTYPE bustype, boolean target_all, Object object, int action_code, String... name) {
        this.bustype = bustype;
        this.target_all = target_all;
        this.object = object;
        this.action_code =action_code;
        if(name!=null ){
            for(String str: name){
                target_name.add(str);
            }
        }
    }

    public BusMessage(BUSTYPE bustype, boolean target_all, Object object , int action_code, List<String> target_name) {
        this.bustype = bustype;
        this.target_name = target_name;
        this.target_all = target_all;
        this.action_code= action_code;
        this.object = object;
    }
}
