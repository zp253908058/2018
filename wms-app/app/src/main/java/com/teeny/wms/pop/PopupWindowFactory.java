package com.teeny.wms.pop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.PopupWindowCompat;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see PopupWindowFactory
 * @since 2017/7/19
 */

public class PopupWindowFactory {

    public static PopupWindow create(View contentView) {
        return create(contentView, 0, 0);
    }

    public static PopupWindow create(View contentView, int width, int height) {
        return create(contentView, width, height, false);
    }

    public static PopupWindow create(int width, int height) {
        return create(null, width, height);
    }

    public static PopupWindow create(View contentView, int width, int height, boolean focusable) {
        return new PopupWindow(contentView, width, height, focusable);
    }

    public static void setOutsideTouchable(PopupWindow popup, Context context) {
        popup.setTouchable(true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new BitmapDrawable(context.getResources(), (Bitmap) null));
    }

    public static void setAnimationStyle(PopupWindow popup, int style) {
        popup.setAnimationStyle(style);
    }

    public static void show(PopupWindow popup, View anchor, int xoff, int yoff, int gravity) {
        PopupWindowCompat.showAsDropDown(popup, anchor, xoff, yoff, gravity);
    }
}
