package genius.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;

import java.lang.reflect.Field;

/**
 * Created by Hongsec on 2016-07-21.
 */
public class UtilsDensity {

    //转换dp为px
    public static int dp2px(Context context, int dip) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f * (dip >= 0 ? 1 : -1));
    }

    //转换px为dp
    public static int px2dp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f * (px >= 0 ? 1 : -1));
    }

    //转换sp为px
    public static int sp2px(Context context, float spValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    //转换px为sp
    public static int px2sp(Context context, float pxValue) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return displayMetrics.heightPixels;
    }


    /**
     * 以防万一，推荐使用 Activity的
     * http://blog.csdn.net/xinhai657/article/details/16863679
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Context context){

        int result = 0;
        try {
            result = 0;
            int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                result =context.getResources().getDimensionPixelSize(resourceId);
            }
            if(result >0){
                return result;
            }
        } catch (Resources.NotFoundException e) {

        }

        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = context.getResources().getDimensionPixelSize(x);
            if(result >0){
                return sbar;
            }
        } catch(Exception e1) {

            e1.printStackTrace();
        }

        return 0;
    }

    /**
     * 推荐使用
     * http://blog.csdn.net/xinhai657/article/details/16863679
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Activity context){
        int statusBarHeight= 0;
        try {
            Rect rectangle= new Rect();
            Window window= context.getWindow();
            window.getDecorView().getWindowVisibleDisplayFrame(rectangle);
            statusBarHeight = rectangle.top;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(statusBarHeight>0){
            return statusBarHeight;
        }

        return getStatusBarHeight((Context) context);
    }
}
