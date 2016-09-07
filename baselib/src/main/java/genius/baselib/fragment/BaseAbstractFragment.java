package genius.baselib.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by Hongsec on 2016-07-21.
 */
public abstract  class BaseAbstractFragment extends Fragment {

    /**
     * 子文件中可以用次来做特殊处理。 layout inflate 正常做,在这里必须要做setContentView
     * @param savedInstanceState
     */
    protected  void onCreateView(Bundle savedInstanceState){};




    protected LayoutInflater inflater;
    private View contentView;
    protected BaseAbstractFragment mContext;
    private ViewGroup container;

    protected  String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext =this;
        if (savedInstanceState == null) {

        } else {
            //remove state fragments
            savedInstanceState.remove("android.support.fragments");
        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (contentView == null)
            return super.onCreateView(inflater, container, savedInstanceState);
        return contentView;
    }



    public void setContentView(int layoutResID) {
        setContentView((ViewGroup) inflater.inflate(layoutResID, container, false));
    }

    public void setContentView(View view) {
        contentView = view;
    }
    public View getContentView() {
        return contentView;
    }

    /**
     * findViewById를 다시 만듬
     *
     * @param id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewBId(@IdRes int id) {
        return (T) contentView.findViewById(id);
    }




    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }



}
