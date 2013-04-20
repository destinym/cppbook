package com.destinym.cplusplustestking;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


//import net.youmi.android.YoumiAdManager;
//import net.youmi.android.banner.AdSize;
//import net.youmi.android.banner.AdView;
//import net.youmi.android.banner.AdViewLinstener;


import com.destinym.cplusplustestking.R;
import com.destinym.utils.BookPageFactory;
import com.kyview.AdViewLayout;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewTargeting.Channel;


import ColorPickerDialog.ColorPickerDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint({ "SdCardPath", "HandlerLeak", "HandlerLeak" })
public class MainActivity extends Activity  {
	public static final int REFRESH = 1;
	/** Called when the activity is first created. */
	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;
	LinearLayout layout;
	AdViewLayout adViewLayout;
	TextView text;

	private SharedPreferences settings;
	public static List<Activity> activityList = new ArrayList<Activity>();
	public static final String PREFS_NAME = "MyPrefsFile"; 


	@Override
	public void onCreate(Bundle savedInstanceState) {

		// Restore preferences  
		settings = getSharedPreferences(PREFS_NAME, 0);  

		activityList.add(this);
		DisplayMetrics dm = new DisplayMetrics();   
		getWindowManager().getDefaultDisplay().getMetrics(dm);   

		//domob
		//DomobAdView adView = new DomobAdView(this,"56OJzqIIuNV6g13yFG", DomobAdView.INLINE_SIZE_320X50);
		
		AdViewTargeting.setChannel(Channel.HIAPK);
		adViewLayout = new AdViewLayout(this, "SDK20132313110429qjaqgbtumcnle0x");


		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 
				FrameLayout.LayoutParams.WRAP_CONTENT);	 
		//设置广告出现的位置(悬浮于屏幕右下角)		 
		params.gravity=Gravity.TOP; 

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		mPageWidget = new PageWidget(this);

		int textLength = 75;
		TextView text = new TextView(this);

		text.setBackgroundColor(Color.rgb(0xEE,0xE8,0xAA));
		text.setText("cpp面试之面霸");
		text.setHeight(textLength);
		text.setGravity(Gravity.CENTER);
		text.setTextSize(24);

		layout = (LinearLayout)findViewById(R.id.main_linear);

		addContentView(adViewLayout, params); 


		//mPageWidget.setLayoutParams(params1);

		layout.addView(text);
		//layout.addView(adViewLayout);
		//this.addContentView(adViewLayout, params);
		//layout.addView(adViewLayout);
		layout.addView(mPageWidget);



		mPageWidget.setScreen(dm.widthPixels, dm.heightPixels);

		//mPageWidget
		Log.i("size","size "+mPageWidget.getWidth()+" " + mPageWidget.getHeight() );
		mCurPageBitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels- textLength, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels- textLength, Bitmap.Config.ARGB_8888);

		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagefactory = new BookPageFactory(dm.widthPixels, dm.heightPixels- textLength);

		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.shelf_bkg));


		File f= new File("/sdcard/cpptestking.png");
		if (!f.exists() || settings.getInt("mbBufLen", 0) == 0 ){
			try {
				InputStream inputStream = getAssets().open("bkg4.png");
				FileOutputStream fos=new FileOutputStream("/sdcard/cpptestking.png");
				int data=inputStream.read();
				while(data!=-1){
					fos.write(data);
					data=inputStream.read();
				}
				fos.close();
				pagefactory.openbook("/sdcard/cpptestking.png");
			}
			catch(IOException e1 )
			{
				e1.printStackTrace();
			}
		}
		else
		{
			//pagefactory;
			try {
				pagefactory.openbook("/sdcard/cpptestking.png");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pagefactory.getRestore(settings);


		}


		pagefactory.onDraw(mCurPageCanvas);

		mPageWidget.setBitmaps(mCurPageBitmap, mCurPageBitmap);

		mPageWidget.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent e) {
				// TODO Auto-generated method stub

				boolean ret = false;
				if (v == mPageWidget) {
					if (e.getAction() == MotionEvent.ACTION_DOWN) {
						mPageWidget.abortAnimation();
						mPageWidget.calcCornerXY(e.getX(), e.getY());

						pagefactory.onDraw(mCurPageCanvas);
						if (mPageWidget.DragToRight()) {
							try {
								pagefactory.prePage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (pagefactory.isfirstPage())
								return false;
							pagefactory.onDraw(mNextPageCanvas);
						} else {
							try {
								pagefactory.nextPage();
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							if (pagefactory.islastPage()) {
								return false;
							}
							pagefactory.onDraw(mNextPageCanvas);
						}
						mPageWidget.setBitmaps(mCurPageBitmap,mNextPageBitmap);
					}

					ret = mPageWidget.doTouchEvent(e);
					return ret;
				}

				return false;
			}

		});

		//new Thread(new GameThread()).start();

	}


	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(0,0,0, "第一页");
		menu.add(0,1,0, "字体");
		menu.add(0,2,0, "颜色");
		//menu.add(0,3,0, "背景");
		menu.add(0,4,0, "关于");
		menu.add(0,5,0, "退出");
		return true;
	}


	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())//得到被点击的item的itemId
		{
		case 0: //对应的ID就是在add方法中所设定的Id
			gotoFirstPage();
			break;
		case 1:
			changeFontSize(this);
			break;
		case 2:
			changeColor(this);
			break;
		case 3:
			//exitProgram();
			break;
		case 4:
			new AlertDialog.Builder(this)
			.setTitle("关于")
			.setMessage("版权所有destiny_m")
			.setPositiveButton("确定", null)
			.show();
			break;
		case 5:
			exitDialog();
			break;
		}
		return true;
	}


	private void changeColor(Context context)
	{
		ColorPickerDialog dialog = new ColorPickerDialog(context,pagefactory.getColor(),"选择颜色", 
				new ColorPickerDialog.OnColorChangedListener() {

			public void colorChanged(int color) {
				pagefactory.setColor(color);
				pagefactory.onDraw(mCurPageCanvas);	
				mPageWidget.invalidate();
			}
		});
		dialog.show();
	}

	private void changeFontSize(Context context)
	{
		Builder dialog = new AlertDialog.Builder(this);  
		dialog.setTitle("字体大小")  ;
		dialog.setItems(new String[] {"大","中","小"}, new DialogInterface.OnClickListener() {  
			public void onClick(DialogInterface dialog, int which) {
				int textSize = 28;
				switch (which)
				{
				case 0:
					textSize = 32;
					break;
				case 1:
					textSize = 28;
					break;
				case 2:
					textSize = 24;
					break;
				}
				pagefactory.setTextSize(textSize);
				pagefactory.onDraw(mCurPageCanvas);
				mPageWidget.invalidate();
			}  
		})   ;

		dialog.show(); 

	}

	private void gotoFirstPage()
	{		
		pagefactory.getoFirstPage();
		pagefactory.onDraw(mCurPageCanvas);
		mPageWidget.invalidate();
		mPageWidget.setBitmaps(mCurPageBitmap,mNextPageBitmap);


	}

	@SuppressWarnings("unused")
	private void loadBgImage()
	{
		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.shelf_bkg));

	}


	void exitDialog()
	{
		AlertDialog.Builder builder = new Builder(this); 
		builder.setMessage("确定要退出吗?"); 
		builder.setTitle("提示"); 
		builder.setPositiveButton("确认", 
				new DialogInterface.OnClickListener() { 
			//@Override 
			public void onClick(DialogInterface dialog, int which) { 
				pagefactory.setRestore(settings);
				dialog.dismiss(); 
				MainActivity.this.finish(); 
			} 
		}); 
		builder.setNegativeButton("取消", 
				new android.content.DialogInterface.OnClickListener() { 
			@Override 
			public void onClick(DialogInterface dialog, int which) { 
				dialog.dismiss(); 
			} 
		}); 
		builder.create().show(); 
	} 
	@Override 
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
			exitDialog(); 
			return false; 
		} 
		return false; 
	}


}