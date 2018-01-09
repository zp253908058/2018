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
 * @see VerticalDecoration
 * @since 2017/5/3
 */
public class VerticalDecoration extends AbstractDecoration {
    private static final String TAG = VerticalDecoration.class.getSimpleName();

    private boolean mIsDrawLast;

    public VerticalDecoration(Context context) {
        super(context);
        getBounds().bottom = 1;
        setNeedDraw(true);
    }

    public int getHeight() {
        return getBounds().bottom;
    }

    public void setHeight(int height) {
        getBounds().bottom = height;
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
            outRect.set(0, 0, 0, position + 1 == state.getItemCount() ? 0 : getHeight());
        }
    }
}
