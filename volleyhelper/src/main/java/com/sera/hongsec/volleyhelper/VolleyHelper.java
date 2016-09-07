package com.sera.hongsec.volleyhelper;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Hongsec on 2016-08-21.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public class VolleyHelper {


    private static RequestQueue requestQueue;

    /**
     *
     * @param applicationContext
     */
    public static RequestQueue getRequestqueque(Context applicationContext){
        if(requestQueue==null){
            requestQueue = Volley.newRequestQueue(applicationContext.getApplicationContext());
        }
        return requestQueue;
    }


    private static com.android.volley.toolbox.ImageLoader imageLoader =null;

    /**
     * @param context
     * @return
     */
    public static com.android.volley.toolbox.ImageLoader getImageLoader(Context context) {
        if(imageLoader==null){
            imageLoader = new com.android.volley.toolbox.ImageLoader(getRequestqueque(context),getBitmapCa());
        }
        return imageLoader;
    }


    private static LruBitmapCache bitmapCache;

    public static LruBitmapCache getBitmapCa(){
        if(bitmapCache ==null){
            bitmapCache = new LruBitmapCache();
        }
        return bitmapCache;
    }

}
