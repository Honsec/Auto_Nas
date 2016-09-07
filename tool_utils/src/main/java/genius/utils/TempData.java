package genius.utils;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by Hongsec on 2016-07-21.
 */
public class TempData  {


    public static <T extends Object> T getWeak(T  obj){
        WeakReference<T> objectWeakReference = new WeakReference<T>(obj);
        return  objectWeakReference.get();
    }

    public static <T extends Object> T getSoft(T  obj){
        SoftReference<T> objectWeakReference = new SoftReference<T>(obj);
        return  objectWeakReference.get();
    }

}
