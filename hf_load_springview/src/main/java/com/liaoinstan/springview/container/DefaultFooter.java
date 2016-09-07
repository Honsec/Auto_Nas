package com.liaoinstan.springview.container;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liaoinstan.springview.R;

/**
 * Created by Administrator on 2016/3/21.
 */
public class DefaultFooter extends BaseFooter {
    private Context context;
    private int rotationSrc;
    private TextView footerTitle;
    private View footerProgressbar;

    public DefaultFooter(Context context){
        this(context,R.drawable.progress_small);
    }

    public DefaultFooter(Context context,int rotationSrc){
        this.context = context;
        this.rotationSrc = rotationSrc;
    }

    @Override
    public View getView(LayoutInflater inflater, ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.default_footer, viewGroup, true);
        footerTitle = (TextView) view.findViewById(R.id.default_footer_title);
        footerProgressbar =  view.findViewById(R.id.default_footer_progressbar);
//        footerProgressbar.setIndeterminateDrawable(ContextCompat.getDrawable(context,rotationSrc));
        return view;
    }

    @Override
    public void onPreDrag(View rootView) {
    }

    @Override
    public void onDropAnim(View rootView, int dy) {
    }

    @Override
    public void onLimitDes(View rootView, boolean upORdown) {
        if (upORdown) {
            footerTitle.setText(R.string.default_loadrelease);
        } else {
            footerTitle.setText(R.string.default_loadmore);
        }
    }

    @Override
    public void onStartAnim() {
        footerTitle.setVisibility(View.INVISIBLE);
        footerProgressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFinishAnim() {
        footerTitle.setText(R.string.default_loadmore);
        footerTitle.setVisibility(View.VISIBLE);
        footerProgressbar.setVisibility(View.INVISIBLE);
    }
}