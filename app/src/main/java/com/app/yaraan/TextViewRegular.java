package com.app.yaraan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by root on 19/5/18.
 */

public class TextViewRegular extends AppCompatTextView {
    public TextViewRegular(Context context) {
        super(context);
        Typeface myFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        setTypeface(myFont);
    }

    public TextViewRegular(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface myFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        setTypeface(myFont);
    }

    public TextViewRegular(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface myFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
        setTypeface(myFont);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
