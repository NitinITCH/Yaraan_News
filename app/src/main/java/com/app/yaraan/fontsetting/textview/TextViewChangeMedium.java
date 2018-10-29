package com.app.yaraan.fontsetting.textview;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class TextViewChangeMedium extends AppCompatTextView {
    public TextViewChangeMedium(Context context) {
        super(context);
        Typeface font = Typeface.createFromAsset(
                getContext().getAssets(),
                "fonts/NotoSansArabic-Medium.ttf");
        setTypeface(font);
    }

    public TextViewChangeMedium(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface font = Typeface.createFromAsset(
                getContext().getAssets(),
                "fonts/NotoSansArabic-Medium.ttf");
        setTypeface(font);
    }

    public TextViewChangeMedium(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface font = Typeface.createFromAsset(
                getContext().getAssets(),
                "fonts/NotoSansArabic-Medium.ttf");
        setTypeface(font);
    }
    public void getFont(){
        Typeface font = Typeface.createFromAsset(
                getContext().getAssets(),
                "fonts/NotoSansArabic-Medium.ttf");

    }
}
