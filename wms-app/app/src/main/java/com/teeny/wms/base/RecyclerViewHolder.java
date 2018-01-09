package com.teeny.wms.base;

import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RecyclerViewHolder
 * @since 2017/7/8
 */

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private ViewHolder mViewHolder;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        mViewHolder = new ViewHolder(itemView);
    }

    public <T extends View> T get(@IdRes int resId) {
        return mViewHolder.get(resId);
    }

    public View getItemView() {
        return mViewHolder.getItemView();
    }
}
