package genius.baselib.frame.util;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Hongsec on 2016-09-06.
 */
public class AutoSwipeListener extends SwipeRefreshLayout {
    public AutoSwipeListener(Context context) {
        super(context);
    }

    public AutoSwipeListener(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 自动刷新
     */
    public void preformRefresh() {
        try {
            Field mCircleView = SwipeRefreshLayout.class.getDeclaredField("mCircleView");
            mCircleView.setAccessible(true);
            View progress = (View) mCircleView.get(this);
            progress.setVisibility(VISIBLE);

            Method setRefreshing = SwipeRefreshLayout.class.getDeclaredMethod("setRefreshing", boolean.class, boolean.class);
            setRefreshing.setAccessible(true);
            setRefreshing.invoke(this, true, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
