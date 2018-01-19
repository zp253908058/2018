package com.teeny.wms.page.allot.adapter;

import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.AllotLocationEntity;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotLocationAdapter
 * @since 2018/1/18
 */

public class AllotLocationAdapter extends RecyclerAdapter<AllotLocationEntity> {

    public AllotLocationAdapter(List<AllotLocationEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_allot_location_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, AllotLocationEntity item) {
        TextView name = holder.get(R.id.allot_location_name);
        TextView amount = holder.get(R.id.allot_location_amount);

        if (item != null) {
            name.setText(item.getLocationCode());
            amount.setText(String.valueOf(item.getAmount()));
        }
    }
}
