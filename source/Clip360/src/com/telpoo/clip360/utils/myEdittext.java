package com.telpoo.clip360.utils;

import com.telpoo.frame.delegate.Idelegate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;

public class myEdittext extends EditText {
    private Idelegate mOnImeBack;

    public myEdittext(Context context) {
        super(context);
    }

    public myEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public myEdittext(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            if (mOnImeBack != null) mOnImeBack.callBack(0, 0);
        }
        return super.dispatchKeyEvent(event);
    }

    public void setOnEditTextImeBackListener(Idelegate listener) {
        mOnImeBack = listener;
    }

}
