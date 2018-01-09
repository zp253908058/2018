package com.teeny.wms.base.decoration;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Class description
 *
 * @author zp
 * @version 1.0
 * @see AbstractDecoration
 * @since 2017/5/3
 */
public abstract class AbstractDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = AbstractDecoration.class.getSimpleName();
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};

    private Drawable mDivider;
    private boolean mIsNeedDraw;

    private final Rect mBounds = new Rect();
    private final Rect mDecoratedBounds = new Rect();
    private Resources mResources;

    AbstractDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        mResources = context.getResources();
    }

    /**
     * Sets the {@link Drawable} for this divider.
     *
     * @param divider Drawable that should be used as a divider.
     */
    public void setDivider(Drawable divider) {
        if (divider == null) {
            throw new IllegalArgumentException("Drawable cannot be null.");
        }
        mDivider = divider;
    }

    public void setColor(@ColorRes int color) {
        mDivider = new ColorDrawable(ResourcesCompat.getColor(mResources, color, null));
    }

    public boolean isNeedDraw() {
        return mIsNeedDraw;
    }

    public void setNeedDraw(boolean needDraw) {
        mIsNeedDraw = needDraw;
    }

    public void setBounds(Rect bounds) {
        mBounds.set(bounds);
    }

    public void setBounds(int left, int top, int right, int bottom) {
        mBounds.set(left, top, right, bottom);
    }

    Rect getBounds() {
        return mBounds;
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        if (!mIsNeedDraw) {
            return;
        }
        if (parent.getLayoutManager() == null) {
            return;
        }
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP && parent.getClipToPadding()) {
            final int parentLeft = parent.getPaddingLeft();
            final int parentTop = parent.getPaddingTop();
            final int parentRight = parent.getWidth() - parent.getPaddingRight();
            final int parentBottom = parent.getHeight() - parent.getPaddingBottom();
            canvas.save();
            canvas.clipRect(parentLeft, parentTop, parentRight, parentBottom);
            canvas.restore();
        }
        drawVertical(canvas, parent);
        drawHorizontal(canvas, parent);
    }

    @SuppressLint("NewApi")
    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mDecoratedBounds);
            final int left = mDecoratedBounds.left + Math.round(ViewCompat.getTranslationX(child));
            final int right = mDecoratedBounds.right + Math.round(ViewCompat.getTranslationX(child));

            if (mBounds.top > 0) {
                final int topTop = mDecoratedBounds.top + Math.round(ViewCompat.getTranslationY(child));
                final int topBottom = topTop - mBounds.top;
                mDivider.setBounds(left, topTop, right, topBottom);
                mDivider.draw(canvas);
            }

            if (mBounds.bottom > 0) {
                final int bottomBottom = mDecoratedBounds.bottom + Math.round(ViewCompat.getTranslationY(child));
                final int bottomTop = bottomBottom - mBounds.bottom;
                mDivider.setBounds(left, bottomTop, right, bottomBottom);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    private void drawHorizontal(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getLayoutManager().getDecoratedBoundsWithMargins(child, mDecoratedBounds);
            final int top = mDecoratedBounds.top + Math.round(ViewCompat.getTranslationY(child));
            final int bottom = mDecoratedBounds.bottom + Math.round(ViewCompat.getTranslationY(child));

            if (mBounds.left > 0) {
                final int leftLeft = mDecoratedBounds.left + Math.round(ViewCompat.getTranslationX(child));
                final int leftRight = leftLeft - mBounds.left;
                mDivider.setBounds(leftLeft, top, leftRight, bottom);
                mDivider.draw(canvas);
            }

            if (mBounds.right > 0) {
                final int rightRight = mDecoratedBounds.right + Math.round(ViewCompat.getTranslationX(child));
                final int rightLeft = rightRight - mBounds.right;
                mDivider.setBounds(rightLeft, top, rightRight, bottom);
                mDivider.draw(canvas);
            }
        }
        canvas.restore();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mBounds);
    }
}
