package com.teeny.wms.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see WindowUtils
 * @since 2017/7/8
 */

public class WindowUtils {

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }


    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static void hideInputSoft(View view){
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }
}
