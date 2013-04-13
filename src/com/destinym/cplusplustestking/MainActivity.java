package com.destinym.cplusplustestking;


//Android大TXT文本文档读取
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ScrollView;

public class MainActivity extends Activity implements
     SwanTextView.OnPreDrawListener {

 private static final String LOG_TAG = "BigTxtReader";
 private static final int BUF_SIZE = 1024 * 2;
 private static final int BUF_SHOW = 3;
 
 private static final int ARROW_UP = 1;
 private static final int ARROW_DOWN = 2;

 private static String ENCODING = "GB2312";
 
 private InputStreamReader mIsReader = null;
 private Uri mUri = null;
 private SwanTextView mTextShow;
 private ScrollView mScrollView;
 private String mStringShow = null;
 private StringBuilder mStringBuilder = null;

 private boolean mReadNext    = true;
 private boolean mReadBack    = false;
 private boolean mStopThread  = false;
 
 private int mPreBottom  = -1;
 private int mCurBottom  = -1;
 private int mReadBufNum = 0;
 private int mBuffHeight = -1;
 private int mPreScrollY = -1;

 private final Handler mHandler = new Handler() {
     @Override
     public void handleMessage(Message msg) {
         switch (msg.what) {
         case ARROW_DOWN:
             mTextShow.setText((CharBuffer) msg.obj);
             break;
         case ARROW_UP:
             mTextShow.setText((CharBuffer) msg.obj);
             mScrollView.scrollTo(0, mBuffHeight);
             break;
         default:
             super.handleMessage(msg);
         }
     }
 };

 public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_main);

     mUri = getIntent().getData();
     
     mScrollView = (ScrollView) findViewById(R.id.text_show_scroll);

     mTextShow = (SwanTextView) findViewById(R.id.text_show);    
     mTextShow.setOnPreDrawListener(this);

     new TextShowTask().execute(mUri);
 }

 private void showText(Uri uri) throws IOException, InterruptedException {
     
     mIsReader = new InputStreamReader(new FileInputStream(
             uri.getPath()), ENCODING);
     
     mStringBuilder = new StringBuilder();
     int initBufSize = BUF_SIZE * (BUF_SHOW - 1);
     char[] buf = new char[BUF_SIZE];
     
     while (!mStopThread) {
         int scrollY = mScrollView.getScrollY();
         if (mCurBottom == scrollY && mPreScrollY < scrollY) {
             mReadNext = true;
             mReadBack = false;
         } else if (mReadBufNum > BUF_SHOW && 0 == scrollY && mPreScrollY != scrollY) {
             mReadNext = false;
             mReadBack = true;
         }
         
         mPreScrollY = scrollY;

         int len = -1;
         if (mReadNext && (len = mIsReader.read(buf)) > 0) {
             mReadNext = false;
             mReadBufNum++;

             if (mStringBuilder.length() > initBufSize) {
                 mStringBuilder.delete(0, BUF_SIZE);
                 mPreBottom = mCurBottom;

                 Message msg = mHandler.obtainMessage(ARROW_DOWN);                
                 msg.obj = CharBuffer.wrap(mStringBuilder.toString());
                 mHandler.sendMessage(msg);

                 mStringShow = mStringBuilder.append(buf, 0, len).toString();
             } else {
                 while (mStringBuilder.length() < initBufSize) {
                     mStringBuilder.append(buf);
                     mIsReader.read(buf);
                     mReadBufNum++;
                 }

                 mStringBuilder.append(buf);
                 Message msg = mHandler.obtainMessage(ARROW_DOWN);
                 msg.obj = CharBuffer.wrap(mStringBuilder.toString());
                 mHandler.sendMessage(msg);
             }
         } else if (mReadBack && mReadBufNum > BUF_SHOW) {
             Log.d(LOG_TAG, "Prepare to read back");
             mReadBack = false;
             mIsReader.close();
             new BackBufReadThread(mStringBuilder).start();
         }
     }
 }

 private class TextShowTask extends AsyncTask<Object, Object, Object> {
     @Override
     protected void onPostExecute(Object param) {
         Log.d(LOG_TAG, "Send broadcast");
     }

     @Override
     protected Object doInBackground(Object... params) {
         Uri uri = (Uri) params[0];
         uri = Uri.parse("/sdcard/books/aaa.txt");

         try {
             showText(uri);
         } catch (Exception e) {
             Log.d(LOG_TAG, "Exception", e);
         }

         return null;
     }
 }
 
 private class BackBufReadThread extends Thread {
     StringBuilder mSbPre = null;
     
     public BackBufReadThread(StringBuilder sb) {
         mSbPre = sb.delete(0, sb.length());
     }
     
     @Override
     public void run() {
         try {
             mIsReader = new InputStreamReader(new FileInputStream(
                     mUri.getPath()), ENCODING);
             
             char[] buf = new char[BUF_SIZE];
             int i = 0;
             while((mReadBufNum - BUF_SHOW) > ++i && mIsReader.read(buf) > 0) {
                 // Just to skip the inputstream. Any better methods?
             }
             mReadBufNum--;
             
             for (i = 0; i < BUF_SHOW; i++) {
                 mIsReader.read(buf);                    
                 mSbPre.append(buf);
             }
             
             
//             mSbPre.delete(mSbPre.length() - BUF_SIZE, mSbPre.length()).insert(0, buf);
             Message msg = mHandler.obtainMessage(ARROW_UP);
             msg.obj = CharBuffer.wrap(mSbPre.toString());
             mHandler.sendMessage(msg);
         } catch (Exception e) {
             Log.d(LOG_TAG, "Exception", e);
         }
     }
 }

 public void onPreDraw(int bottom) {
     mCurBottom = bottom - mScrollView.getHeight();
     
     if (!TextUtils.isEmpty(mStringShow)) {
         // Use the last deleted buff to evaluate the height
         mBuffHeight = mPreBottom - mScrollView.getScrollY();
         
         // Set the text to add new content without flash the view
         Message msg = mHandler.obtainMessage(ARROW_DOWN);
         msg.obj = CharBuffer.wrap(mStringShow);            
         mHandler.sendMessage(msg);
         
         mStringShow = null;
     }
 }
 
 @Override
 public void finish() {
     mStopThread = true;
     super.finish();
 }
}