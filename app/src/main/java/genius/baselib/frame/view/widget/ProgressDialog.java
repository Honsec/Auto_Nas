package genius.baselib.frame.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.genius.hongsec.nasa.R;


/**
 * @author kim seok woon
 * 
 *  - 베이스 프로그래스바  
 */

public class ProgressDialog extends Dialog {
//	private ProgressBar mProgressBar;

//	private RotateLoading rotateLoading;

	private ProgressBar progressBar;


	public ProgressDialog(Context context) {
//		super(context, android.R.style.Widget_Material_Light_ProgressBar_Horizontal);
		super(context, R.style.AppTheme_NewDialog);
	}


	public ProgressDialog(Context context, int theme) {
		super(context, theme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
 
		final Context context = getContext();
//		rotateLoading = new RotateLoading(context);
//		rotateLoading.setLayoutParams(new LayoutParams(80,80));

		progressBar=new ProgressBar(context);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

		RelativeLayout layout = new RelativeLayout(context);
		layout.addView(progressBar, params);
		layout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View view, MotionEvent motionevent) {
				cancel();
				return false;
			}
		});
		setContentView(layout);
	}
	@Override
	public void show() {
		try {
			super.show();
		} catch (Exception e) { }
	}

	@Override
	public void dismiss() {
		try {
			super.dismiss();
		} catch (Exception e) { }
	}

	@Override
	public void onBackPressed() {
		
		dismiss();
		super.onBackPressed();
	}
	
	
}
