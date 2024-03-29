package com.destinym.cplusplustestking;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;



public class SwanTextView extends TextView {

    private int mPreBottom = -1;
    private OnPreDrawListener preDrawListener = null;
    
    public SwanTextView(Context context) {
        super(context);
    }

    public SwanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwanTextView(Context context, AttributeSet attrs,
            int defStyle) {
        super(context, attrs, defStyle);
    }
    
@Override
protected void onDraw(Canvas canvas) {
    
    if (mPreBottom != getBottom()) {
        mPreBottom = getBottom();

        if (preDrawListener != null)
            preDrawListener.onPreDraw(mPreBottom);
    }

    super.onDraw(canvas);
}

public static interface OnPreDrawListener {
    public void onPreDraw(int bottom);
}

public void setOnPreDrawListener(OnPreDrawListener listener) {
    preDrawListener = listener;
}
}

