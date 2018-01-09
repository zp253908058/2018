package com.teeny.wms.base.decoration;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Class description
 *
 * @author zp
 * @version 1.0
 * @see HorizontalDecoration
 * @since 2017/5/3
 */
public class HorizontalDecoration extends AbstractDecoration {
    private static final String TAG = HorizontalDecoration.class.getSimpleName();

    private boolean mIsDrawLast;

    public HorizontalDecoration(Context context) {
        super(context);
        getBounds().right = 1;
        setNeedDraw(true);
    }

    public int getWidth() {
        return getBounds().right;
    }

    public void setWidth(int width) {
        getBounds().right = width;
    }

    public boolean isDrawLast() {
        return mIsDrawLast;
    }

    public void setDrawLast(boolean drawLast) {
        mIsDrawLast = drawLast;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mIsDrawLast) {
            super.getItemOffsets(outRect, view, parent, state);
        } else {
            int position = parent.getChildLayoutPosition(view);
            outRect.set(0, 0, position + 1 == state.getItemCount() ? 0 : getWidth(), 0);
        }
    }
}
