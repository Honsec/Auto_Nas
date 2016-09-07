package com.liaoinstan.springview.container;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.liaoinstan.springview.R;
import com.liaoinstan.springview.UtilsSP;


/**
 * Created by Administrator on 2016/3/21.
 */
public class DefaultHeader extends BaseHeader {
    private UtilsSP utilsSP;
    private Context context;
    private int rotationSrc;
    private int arrowSrc;


    private final int ROTATE_ANIM_DURATION = 180;
    private RotateAnimation mRotateUpAnim;
    private RotateAnimation mRotateDownAnim;

    private TextView headerTitle;
    private TextView headerTime;
    private ImageView headerArrow;
    private View headerProgressbar;

    public DefaultHeader(Context context){
        this(context, R.drawable.progress_small,R.drawable.arrow);
    }

    public DefaultHeader(Context context,int rotationSrc,int arrowSrc){
        this.context = context;
        this.rotationSrc = rotationSrc;
        this.arrowSrc = arrowSrc;

        mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateUpAnim.setFillAfter(true);
        mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
        mRotateDownAnim.setFillAfter(true);
        utilsSP = new UtilsSP(context);
    }

    @Override
    public View getView(LayoutInflater inflater,ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.default_header, viewGroup, true);
        headerTitle = (TextView) view.findViewById(R.id.default_header_title);
        headerTime = (TextView) view.findViewById(R.id.default_header_time);
        headerArrow = (ImageView) view.findViewById(R.id.default_header_arrow);
        headerProgressbar =   view.findViewById(R.id.default_header_progressbar);
//        headerProgressbar.setIndeterminateDrawable(ContextCompat.getDrawable(context, rotationSrc));
        headerArrow.setImageResource(arrowSrc);
        return view;
    }

    @Override
    public void onPreDrag(View rootView) {
            int m = (int) ((System.currentTimeMillis()-utilsSP.getValue("freshtimes",0L))/1000/60);
            if(m>=1 && m<60){
                headerTime.setText( m +rootView.getResources().getString(R.string.before_minute));
            }else if(m>60*24){
                int d = m/(60*24);
                if(d>365){
                    headerTime.setText(R.string.before_first);
                }else{
                    headerTime.setText( d +rootView.getResources().getString(R.string.before_day));
                }

            }else if (m>=60){
                int h = m/60;
                headerTime.setText( h +rootView.getResources().getString(R.string.before_hour));
            }else {
                headerTime.setText(rootView.getResources().getString(R.string.before_now));
            }
          utilsSP.setValue("freshtimes",System.currentTimeMillis());
    }

    @Override
    public void onDropAnim(View rootView, int dy) {
    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        if (!upORdown){
            headerTitle.setText(R.string.default_loadrelease);
            if (headerArrow.getVisibility()==View.VISIBLE)
                headerArrow.startAnimation(mRotateUpAnim);
        }
        else {
            headerTitle.setText(R.string.default_load_pull);//"下拉刷新"
            if (headerArrow.getVisibility()==View.VISIBLE)
                headerArrow.startAnimation(mRotateDownAnim);
        }
    }

    @Override
    public void onStartAnim() {
        headerTitle.setText(R.string.default_loading);
        headerArrow.setVisibility(View.INVISIBLE);
        headerArrow.clearAnimation();
        headerProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishAnim() {
        headerArrow.setVisibility(View.VISIBLE);
        headerProgressbar.setVisibility(View.INVISIBLE);
    }
}