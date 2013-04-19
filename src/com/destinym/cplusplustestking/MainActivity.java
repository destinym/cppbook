package com.destinym.cplusplustestking;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


//import net.youmi.android.YoumiAdManager;
//import net.youmi.android.banner.AdSize;
//import net.youmi.android.banner.AdView;
//import net.youmi.android.banner.AdViewLinstener;

import com.adchina.android.ads.AdManager;
import com.adchina.android.ads.views.AdView;
import com.kyview.AdViewInterface;
import com.kyview.AdViewLayout;
import com.kyview.AdViewTargeting;
import com.kyview.AdViewTargeting.RunMode;
import com.kyview.AdViewTargeting.UpdateMode;


import ColorPickerDialog.ColorPickerDialog;
import android.R.string;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.FloatMath;
import android.util.Log;
import android.view.Gravity;
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
import android.widget.Toast;

public class MainActivity extends Activity implements AdViewInterface {
	public static final int REFRESH = 1;
	/** Called when the activity is first created. */
	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;
	LinearLayout layout;
	AdViewLayout adViewLayout;
	TextView text;

	public static List<Activity> activityList = new ArrayList<Activity>();
	// ʵ����һ��handler
	Handler myHandler   = new Handler(){
		//���յ���Ϣ����
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case MainActivity.REFRESH:
				mPageWidget.invalidate();        //ˢ�½���
				break;
			}
			super.handleMessage(msg);
		}
	};

	class GameThread implements Runnable {
		public void run() {
			// TODO Auto-generated method stub
			while (!Thread.currentThread().isInterrupted()) 
			{ 
				Message message = new Message(); 
				message.what = MainActivity.REFRESH; // ����
				MainActivity.this.myHandler.sendMessage(message);
				try 
				{ 
					Thread.sleep(100); 
				} 
				catch (InterruptedException e)
				{
					Thread.currentThread().interrupt(); 
				}  
			}
		}
	};


	@Override
	public void onCreate(Bundle savedInstanceState) {


		activityList.add(this);
		 DisplayMetrics dm = new DisplayMetrics();   
	     getWindowManager().getDefaultDisplay().getMetrics(dm);   
		//wooboo
		//WoobooAdView adView = new WoobooAdView(this, 0xFF000000, 0xFFFFFFFF, 40);
		//adchaina"83587"
		//AdView adView = new AdView(getApplicationContext(), "83587", true, true);

		//AdManager.setRefershinterval(40);

		//adView.setaHeight(480);
		//adView.setaHeight(72);	
		//AdManager.setmVideoPlayer(true);
		//AdManager.setRelateScreenRotate(this,true);

		//youmi
		//YoumiAdManager.getInstance(this).init("b2113d5f09dbaa77", "e0aadc51451db4f5", false);

		//��ʼ�������ͼ������ʹ�������Ĺ��캯�����ù����ͼ�ı���ɫ��͸���ȼ�������ɫ
		//AdView adView = new AdView(this, AdSize.SIZE_320x50);
		//domob
		//DomobAdView adView = new DomobAdView(this,"56OJzqIIuNV6g13yFG", DomobAdView.INLINE_SIZE_320X50);
		//adView.set

		/*��������ֻ���ڲ���,��ɺ�һ��Ҫȥ��,�ο��ĵ�˵��*/
		//AdViewTargeting.setUpdateMode(UpdateMode.EVERYTIME); //��֤ÿ�ζ��ӷ�����ȡ����
		//AdViewTargeting.setRunMode(RunMode.TEST); //��֤����ѡ�еĹ�湫˾��Ϊ����״̬
		/*������䷽�㿪���߽��з�������ͳ��,��ϸ���ÿ��Բο�java doc */
		//AdViewTargeting.setChannel(Channel.GOOGLEMARKET);
		adViewLayout = new AdViewLayout(this, "SDK20132313110429qjaqgbtumcnle0x");
		adViewLayout.setAdViewInterface(this);

		FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, 
				FrameLayout.LayoutParams.WRAP_CONTENT);	 
		//���ù����ֵ�λ��(��������Ļ���½�)		 
		params.gravity=Gravity.TOP; 
		adViewLayout.setLayoutParams(params);
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		mPageWidget = new PageWidget(this);

		int textLength = 75;
		TextView text = new TextView(this);
		
		text.setBackgroundColor(Color.rgb(0xEE,0xE8,0xAA));
		text.setText("cpp����֮���");
		text.setHeight(textLength);
		text.setGravity(Gravity.CENTER);
		text.setTextSize(24);

//		LinearLayout lineLayout = new LinearLayout(this);
//		LinearLayout.LayoutParams lineparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 
//				textLength);
//		//lineLayout.addView(text);
//		lineLayout.setLayoutParams(lineparams);
//		//lineLayout.addView(adView);
//		// lineLayout.setBackground( this.getResources().getDrawable(R.drawable.title));
//		text.setLayoutParams(params);
		layout = (LinearLayout)findViewById(R.id.main_linear);

		addContentView(adViewLayout, params); 


		//mPageWidget.setLayoutParams(params1);

		layout.addView(text);
		//layout.addView(adViewLayout);
		//this.addContentView(adViewLayout, params);
		//layout.addView(adViewLayout);
		layout.addView(mPageWidget);



		mPageWidget.setScreen(dm.widthPixels, dm.heightPixels);
		mPageWidget.setInitXY(0, textLength);

		//mPageWidget
		Log.i("size","size "+mPageWidget.getWidth()+" " + mPageWidget.getHeight() );
		mCurPageBitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels- textLength, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap.createBitmap(dm.widthPixels, dm.heightPixels- textLength, Bitmap.Config.ARGB_8888);

		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagefactory = new BookPageFactory(dm.widthPixels, dm.heightPixels- textLength);

		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.shelf_bkg));



		try {
			InputStream inputStream = getAssets().open("bkg4.png");
			FileOutputStream fos=new FileOutputStream("/sdcard/test.png");
			int data=inputStream.read();
			while(data!=-1){
				fos.write(data);
				data=inputStream.read();
			}
			fos.close();

			pagefactory.openbook("/sdcard/test.png");
			pagefactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			Toast.makeText(this, "�����鲻����",
					Toast.LENGTH_LONG).show();
		}

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
		menu.add(0,0,0, "��һҳ");
		menu.add(0,1,0, "����");
		menu.add(0,2,0, "��ɫ");
		//menu.add(0,3,0, "����");
		menu.add(0,4,0, "����");
		menu.add(0,5,0, "�˳�");
		return true;
	}


	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);
		switch(item.getItemId())//�õ��������item��itemId
		{
		case 0: //��Ӧ��ID������add���������趨��Id
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
			.setTitle("����")
			.setMessage("��Ȩ����destiny_m")
			.setPositiveButton("ȷ��", null)
			.show();
			break;
		case 5:
			exitProgram();
			break;
		}
		return true;
	}


	private void changeColor(Context context)
	{
		ColorPickerDialog dialog = new ColorPickerDialog(context,pagefactory.getColor(),"ѡ����ɫ", 
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
		dialog.setTitle("�����С")  ;
		dialog.setItems(new String[] {"��","��","С"}, new DialogInterface.OnClickListener() {  
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

	private void loadBgImage()
	{
		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.shelf_bkg));

	}

	private void exitProgram()
	{

		Builder dialog = new AlertDialog.Builder(this);  
		dialog.setTitle("�Ƿ��˳�");
		dialog.setMessage("�Ƿ�Ҫ�˳�");
		dialog.setPositiveButton("��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				Log.d("exit", "----- exitClient -----");         // �ر�����Activity          
				for (int i = 0; i < activityList.size(); i++)        
				{              
					if (null != activityList.get(i))             
					{                  
						activityList.get(i).finish();             
					}         
				}
				//ActivityManager activityManager = (ActivityManager) MainActivity.getSystemService(Context.ACTIVITY_SERVICE);        
				//activityManager.restartPackage("��·��");          
				System.exit(0);
				//Android�ĳ���ֻ����Activity finish()��,��������finish��,�˳�������ȫ
			}});
		dialog.setNegativeButton("��", null);
		dialog.show();
	}



	//@Override
	public void onClickAd() {
		// TODO Auto-generated method stub
		Log.i("AdViewSample", "onClickAd");

	}

	//@Override
	public void onDisplayAd() {
		// TODO Auto-generated method stub		
		Log.i("AdViewSample", "onDisplayAd");
	}


}