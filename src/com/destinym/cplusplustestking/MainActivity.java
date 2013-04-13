package com.destinym.cplusplustestking;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import ColorPickerDialog.ColorPickerDialog;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity  {
	public static final int REFRESH = 1;
	/** Called when the activity is first created. */
	private PageWidget mPageWidget;
	Bitmap mCurPageBitmap, mNextPageBitmap;
	Canvas mCurPageCanvas, mNextPageCanvas;
	BookPageFactory pagefactory;
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
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		mPageWidget = new PageWidget(this);
		setContentView(mPageWidget);

		mCurPageBitmap = Bitmap.createBitmap(480, 800, Bitmap.Config.ARGB_8888);
		mNextPageBitmap = Bitmap
				.createBitmap(480, 800, Bitmap.Config.ARGB_8888);

		mCurPageCanvas = new Canvas(mCurPageBitmap);
		mNextPageCanvas = new Canvas(mNextPageBitmap);
		pagefactory = new BookPageFactory(480, 800);

		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.shelf_bkg));

		try {
			InputStream inputStream = getAssets().open("test.txt");
			FileOutputStream fos=new FileOutputStream("/sdcard/z8806c.txt");
			int data=inputStream.read();
			while(data!=-1){
				fos.write(data);
				data=inputStream.read();
			}
			fos.close();

			pagefactory.openbook("/sdcard/z8806c.txt");
			pagefactory.onDraw(mCurPageCanvas);
		} catch (IOException e1) {
			Toast.makeText(this, "�����鲻����,�뽫��z8806c.txt������SD����Ŀ¼��,���Գ���100M����",
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
						mPageWidget.setBitmaps(mCurPageBitmap, mNextPageBitmap);
					}

					ret = mPageWidget.doTouchEvent(e);
					return ret;
				}

				return false;
			}

		});
		
		new Thread(new GameThread()).start();

	}

	public boolean onCreateOptionsMenu(Menu menu)
	{
		menu.add(0,0,0, "��һҳ");
		menu.add(0,1,0, "����");
		menu.add(0,2,0, "��ɫ");
		menu.add(0,3,0, "����");
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
			break;
		case 4:
			new AlertDialog.Builder(this)
			.setTitle("����")
			.setMessage("destinym")
			.setPositiveButton("ȷ��", null)
			.show();
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
			}  
		})   ;

		dialog.show(); 

	}

	private void gotoFirstPage()
	{
		pagefactory.getoFirstPage();
		pagefactory.onDraw(mCurPageCanvas);
		
	}

	private void loadBgImage()
	{
		pagefactory.setBgBitmap(BitmapFactory.decodeResource(
				this.getResources(), R.drawable.shelf_bkg));

	}

	private void exitProgram()
	{

	}


}