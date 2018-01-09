package com.teeny.wms.base.decoration;

import android.content.Context;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AbstractDecoration
 * @since 2017/5/3
 */
public class GridDecoration extends AbstractDecoration {
    private static final String TAG = GridDecoration.class.getSimpleName();

    public GridDecoration(Context context) {
        this(context, 1);
    }

    public GridDecoration(Context context, int space) {
        super(context);
        getBounds().set(space, space, space, space);
        setNeedDraw(true);
    }
}
