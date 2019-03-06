package com.austinhickey.lis4331.currencyconverterapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

public class CurrencyView extends View {
    private String mCurrencyString;
    private float mAmount;
    private int mFontSize;

    public CurrencyView(Context context) {
        super(context);
        init(null, 0);
    }

    public CurrencyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CurrencyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CurrencyView, defStyle, 0);

        mCurrencyString = a.getString(R.styleable.CurrencyView_currency);
        mAmount = a.getFloat(R.styleable.CurrencyView_moneyAmount, mAmount);
        mFontSize = a.getInt(R.styleable.CurrencyView_fontSize, mFontSize);

        a.recycle();
    }

    public String getCurrency() {
        return mCurrencyString;
    }

    public void setCurrency(String s) {
        mCurrencyString = s;
    }

    public float getAmount() {
        return mAmount;
    }

    public void setAmount(float a) {
        mAmount = a;
    }
}
