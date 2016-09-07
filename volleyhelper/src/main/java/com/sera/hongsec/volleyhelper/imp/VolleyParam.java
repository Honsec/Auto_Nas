package com.sera.hongsec.volleyhelper.imp;

import android.content.Context;

import org.json.JSONObject;

/**
 * Created by Hongsec on 2016-08-21.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public interface VolleyParam<T>{
    public int getRequestType();
    public String getRequestUrl();
    public JSONObject getHeaders();
    public void setResponseData(Context context,JSONObject response);

}
