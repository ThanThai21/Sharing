package com.esp.sharing.CustomView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

public class CustomTextView extends TextView {
    public CustomTextView(Context context) {
        this(context, (AttributeSet)null);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!this.isInEditMode()) {
            String fontAsset = "font.ttf";
            if (fontAsset != null && !fontAsset.equals("")) {
                FontManager.init(context.getAssets());
                Typeface tf = FontManager.getInstance().getFont(fontAsset);
                int style = 0;
                float size = this.getTextSize();
                if (this.getTypeface() != null) {
                    style = this.getTypeface().getStyle();
                }

                if (tf != null) {
                    this.setTypeface(tf, style);
                    Log.d("FontText", tf.getStyle() + "");
                } else {
                    Log.d("FontText", String.format("Could not create a font from asset: %s", new Object[]{fontAsset}));
                }
            }
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
