package com.esp.sharing.Helper;

import android.content.Context;
import android.util.TypedValue;

public class DimensionHelper {

    public static int convertDip2Pixels(Context context, int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, context.getResources().getDisplayMetrics());
    }

}
