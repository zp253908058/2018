package com.teeny.wms.page.picking.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.OutputPickingEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutputPickingAdapter
 * @since 2018/1/20
 */

public class OutputPickingAdapter extends RecyclerAdapter<OutputPickingEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public OutputPickingAdapter(@Nullable List<OutputPickingEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_output_picking_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, OutputPickingEntity item) {
        TextView turnover = holder.get(R.id.output_picking_turnover);

        if (item != null) {
            turnover.setText(item.getTurnover());
        }
    }
}
