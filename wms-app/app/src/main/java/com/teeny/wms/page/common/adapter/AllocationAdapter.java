package com.teeny.wms.page.common.adapter;

import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.AllocationEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllocationAdapter
 * @since 2017/8/25
 */

public class AllocationAdapter extends RecyclerAdapter<AllocationEntity> {

    public AllocationAdapter(List<AllocationEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_allocation_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, AllocationEntity item) {
        TextView name = holder.get(R.id.allocation_name);
        TextView amount = holder.get(R.id.allocation_amount);

        if (item != null) {
            name.setText(item.getLocationCode());
            amount.setText(String.valueOf(item.getAmount()));
        }
    }
}
