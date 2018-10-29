package com.app.yaraan.fontsetting.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewChangeBold extends AppCompatTextView {
    public TextViewChangeBold(Context context) {
        super(context);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/NotoSansArabic-Bold.ttf");
        setTypeface(font);
    }

    public TextViewChangeBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/NotoSansArabic-Bold.ttf");
        setTypeface(font);
    }

    public TextViewChangeBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/NotoSansArabic-Bold.ttf");
        setTypeface(font);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void getFont(){
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/NotoSansArabic-Bold.ttf");

    }
}


