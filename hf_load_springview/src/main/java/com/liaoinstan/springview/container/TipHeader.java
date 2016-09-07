package com.liaoinstan.springview.container;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.liaoinstan.springview.R;


/**
 * Created by Administrator on 2016/3/21.
 */
public class TipHeader extends BaseHeader {


    @Override
    public View getView(LayoutInflater inflater,ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.home_default_header, viewGroup, true);

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

    }


    @Override
    public void onStartAnim() {
    }

    @Override
    public void onFinishAnim() {

    }
}