package com.sera.hongsec.volleyhelper.imp;

/**
 * Created by Hongsec on 2016-08-21.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public interface CallBackListener<T> {
    public void showloadingUI();
    public void cancleloadingUI();
    public void onResponse(T t);
    public void onErrorResponse(T t);
}
