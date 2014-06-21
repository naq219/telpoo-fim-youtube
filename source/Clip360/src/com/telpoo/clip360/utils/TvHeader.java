package com.telpoo.clip360.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class TvHeader extends TextView {

/*
 * Caches typefaces based on their file path and name, so that they don't have to be created every time when they are referenced.
 */
private static Typeface mTypeface;

public TvHeader(final Context context) {
    this(context, null);
}

public TvHeader(final Context context, final AttributeSet attrs) {
    this(context, attrs, 0);
}

public TvHeader(final Context context, final AttributeSet attrs, final int defStyle) {
    super(context, attrs, defStyle);

     if (mTypeface == null) {
         mTypeface = Typeface.createFromAsset(context.getAssets(), "utmavio.ttf");
     }
     setTypeface(mTypeface);
}

}