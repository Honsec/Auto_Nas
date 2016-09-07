package com.sera.hongsec.volleyhelper.base;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hongsec on 2016-08-21.
 * email : piaohongshi0506@gmail.com
 * QQ: 251520264
 */
public abstract class JsonParseUtil {

    public int getJInteger(JSONObject jsonObject, String name, int defaultValue) {
        if (jsonObject.has(name)) {
            try {
                return jsonObject.getInt(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return defaultValue;

    }


    public boolean getJBoolean(JSONObject jsonObject, String name, boolean defaultValue) {

        if (jsonObject.has(name)) {
            try {
                return jsonObject.getBoolean(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return defaultValue;
    }
    public JSONObject getJJsonObject(JSONObject jsonObject, String name, JSONObject defaultValue) {

        if (jsonObject.has(name)) {
            try {
                return jsonObject.getJSONObject(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return defaultValue;
    }

    public JSONObject getJJsonObject(String str,JSONObject defaultValue){

        try {
            JSONObject jsonObject = new JSONObject(str);
            if(jsonObject!=null ){
                return  jsonObject;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    public String getJString(JSONObject jsonObject, String name, String defaultValue) {

        if (jsonObject.has(name)) {
            try {
                return jsonObject.getString(name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return defaultValue;
    }

}
