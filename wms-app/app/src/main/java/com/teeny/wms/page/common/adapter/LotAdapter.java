package com.teeny.wms.page.common.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.LotEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see LotAdapter
 * @since 2017/8/23
 */

public class LotAdapter extends RecyclerAdapter<LotEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public LotAdapter(@Nullable List<LotEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_lot_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, LotEntity item) {
        TextView lot = holder.get(R.id.lot_number);
        TextView amount = holder.get(R.id.lot_amount);
        TextView date = holder.get(R.id.lot_validate_date);

        if (item != null) {
            lot.setText(item.getLotNo());
            amount.setText(String.valueOf(item.getCount()));
            date.setText(item.getValidateDate());
        }
    }
}
