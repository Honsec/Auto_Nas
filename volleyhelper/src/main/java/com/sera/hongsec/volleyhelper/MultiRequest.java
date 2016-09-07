package com.sera.hongsec.volleyhelper;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.sera.hongsec.volleyhelper.base.BaseRequest;
import com.sera.hongsec.volleyhelper.base.MultipartRequest;
import com.sera.hongsec.volleyhelper.base.MultipartRequestParams;
import com.sera.hongsec.volleyhelper.imp.StringCallBackListener;

import org.json.JSONException;
import org.json.JSONObject;

import genius.utils.UtilsLog;

/**
 * Can't do get method
 * Created by Hongsec on 2016-08-21.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public abstract class MultiRequest<T> extends BaseRequest<T> {

    public abstract MultipartRequestParams getMultiParamHeaders();

    @Override
    public JSONObject getHeaders() {
        return null;
    }

    @Override
    public void request(final Context context, final StringCallBackListener<T> tCallBackListener) {
        super.request(context, tCallBackListener);
        UtilsLog.i("request Url:"+getRealUrl());
        tCallBackListener.showloadingUI();
        MultipartRequest multipartRequest = new MultipartRequest(getRequestType(),getMultiParamHeaders(), getRealUrl(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                tCallBackListener.cancleloadingUI();
                JSONObject jsonObject= getJJsonObject(response,new JSONObject());

                setResult(jsonObject);

                if(result.status){

                    setResponseData(context,jsonObject);
                    if(tCallBackListener!=null){
                        tCallBackListener.onResponseString((T)MultiRequest.this,response);
                        tCallBackListener.onResponse((T)MultiRequest.this);
                    }
                }else{

                    if(tCallBackListener!=null){
                        tCallBackListener.onErrorResponse((T)MultiRequest.this);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tCallBackListener.cancleloadingUI();
                NetworkResponse response = error.networkResponse;
                if (response != null && response.data != null) {

                    try {
                        JSONObject jsonObject = new JSONObject(new String(response.data));
                        setResult(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(tCallBackListener!=null){
                        tCallBackListener.onResponseString((T)MultiRequest.this,new String(response.data));
                    }
                    if(tCallBackListener!=null){
                        tCallBackListener.onErrorResponse((T) MultiRequest.this);
                    }
                    ErrorStatusProcess(response.statusCode);


                }else{

                    if(tCallBackListener!=null){
                        tCallBackListener.onErrorResponse((T) MultiRequest.this);
                    }
                }

            }
        });

        VolleyHelper.getRequestqueque(context).add(multipartRequest);
    }
}
