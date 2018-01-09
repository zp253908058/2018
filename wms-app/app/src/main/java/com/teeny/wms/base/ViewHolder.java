package com.teeny.wms.base;

import android.support.annotation.IdRes;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;

import com.teeny.wms.util.Validator;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ViewHolder
 * @since 2017/7/8
 */

public class ViewHolder {
    private static final String TAG = ViewHolder.class.getSimpleName();
    private SparseArrayCompat<View> mHolder = null;
    private View mItemView;

    public ViewHolder() {
        this(null);
    }

    public ViewHolder(View itemView) {
        this.mItemView = itemView;
        mHolder = new SparseArrayCompat<>();
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T get(@IdRes int resId) {
        View view = mHolder.get(resId);
        if (Validator.isNull(view)) {
            view = mItemView.findViewById(resId);
            mHolder.put(resId, view);
        }
        return (T) view;
    }

    public View getItemView() {
        return mItemView;
    }

    public void setItemView(View itemView) {
        this.mItemView = itemView;
    }

    public void clear() {
        mItemView = null;
        mHolder.clear();
    }
}
