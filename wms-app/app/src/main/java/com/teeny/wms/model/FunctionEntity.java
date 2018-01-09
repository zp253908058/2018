package com.teeny.wms.model;

import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see FunctionEntity
 * @since 2017/7/15
 */

public class FunctionEntity {

    private String title;
    @DrawableRes
    private int icon;

    public FunctionEntity() {
    }

    public FunctionEntity(String title, @DrawableRes int icon, @ColorInt int color) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @DrawableRes
    public int getIcon() {
        return icon;
    }

    public void setIcon(@DrawableRes int icon) {
        this.icon = icon;
    }
}
