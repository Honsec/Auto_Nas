package genius.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;

/**
 * Created by Hongsec on 2016-07-21.
 */
public class UtilsVersionMC {

    public static int getColor(Resources resources  , int colorId){

        if(resources == null) return 0;
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M){
            try {
                return resources.getColor(colorId,null);
            } catch (Exception e) {
                e.printStackTrace();
                return resources.getColor(colorId);
            }
        }else{
            return resources.getColor(colorId);
        }

    }


    public static Drawable getDrawable(Resources resources, int drawableId){
        if(resources == null) return null;

        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1){
            try {
                return resources.getDrawable(drawableId,null);
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
                return resources.getDrawable(drawableId);
            }
        }else{

            return resources.getDrawable(drawableId);
        }


    }


}
