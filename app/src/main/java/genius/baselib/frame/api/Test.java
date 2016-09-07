package genius.baselib.frame.api;

import android.content.Context;

import org.json.JSONObject;

import genius.baselib.frame.base.BaseJsonApi;

/**
 * Created by Hongsec on 2016-09-05.
 */
public class Test  extends BaseJsonApi<Test>{

    public Test(Context context) {
        super(context);
    }

    @Override
    public int getRequestType() {
        return 0;
    }

    @Override
    public String getRequestUrl() {
        return null;
    }

    @Override
    public JSONObject getHeaders() {
        return null;
    }

    @Override
    public void setResponseData(Context context, JSONObject response) {

    }
}
