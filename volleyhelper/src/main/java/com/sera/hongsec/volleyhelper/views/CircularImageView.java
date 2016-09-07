package com.sera.hongsec.volleyhelper.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class CircularImageView extends LocalNetworkImageView {

	/**
	 * 整体框
	 */
	private RectF mRoundRect=new RectF();

	private Path mPath = new Path();

	/**
	 * 设置图形、图片的抗锯齿。可用于线条等。按位或.
	 */
	private PaintFlagsDrawFilter pfd=new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
	
	public CircularImageView(Context context) {
		super(context);
		initObjectAttribute();
	}

	public CircularImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CircularImageView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		
		initObjectAttribute();		
	}

	private void initObjectAttribute()
	{
//		if(getScaleType() != ScaleType.CENTER_CROP)
//		{
//			setScaleType(ScaleType.CENTER_CROP);
//		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//测量框的大小
		mRoundRect.set(0,0,getMeasuredWidth(),getMeasuredHeight());
	}

	/**
	 * http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2014/1120/2019.html
	 * 在此方法中重新划图片
	 * @param canvas
     */
	@Override
	protected void onDraw(Canvas canvas) { //콤파스~~
		//开始工作~~~~~~~~~~（先做工作室准备，清除画板，检查我的笔，我的素材）

		//获取已画出的图片
		Drawable mDrawable = getDrawable();
		if (mDrawable == null)
		{
			return;
		}
        //如果是空的就不画了
        if (mDrawable.getIntrinsicWidth() == 0 || mDrawable.getIntrinsicHeight() == 0) {
            return;     // nothing to draw (empty bounds)
        }

		//设置锯齿
        canvas.setDrawFilter(pfd);


        canvas.save();

		//初始化我的铅笔
        mPath.reset();
        canvas.clipPath(mPath); // makes the clip empty

        mPath.addCircle(getWidth()/2, getHeight()/2, Math.min(getWidth()/2, getHeight()/2), Path.Direction.CCW);
        canvas.clipPath(mPath, Region.Op.REPLACE);

        
        Matrix mDrawMatrix = getImageMatrix();
        if (mDrawMatrix != null) {
            canvas.concat(mDrawMatrix);
        }
        mDrawable.draw(canvas);
        
        canvas.restore();
		//结束工作
	}
}