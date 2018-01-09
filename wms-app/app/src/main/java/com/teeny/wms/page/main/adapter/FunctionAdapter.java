package com.teeny.wms.page.main.adapter;

import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.FunctionEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.NavigationItemView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see FunctionAdapter
 * @since 2017/7/15
 */

public class FunctionAdapter extends RecyclerAdapter<FunctionEntity> {
    private static final String TAG = "FunctionAdapter";

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public FunctionAdapter(@Nullable List<FunctionEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_function_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, FunctionEntity item) {
        NavigationItemView view = holder.get(R.id.function_navigation_item_view);
        if(Validator.isNotNull(item)){
            view.setText(item.getTitle());
            view.setIcon(item.getIcon());
        }
    }
}
