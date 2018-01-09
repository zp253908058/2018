package com.teeny.wms.page.allot.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.AllotGoodsEntity;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotGoodsQueryAdapter
 * @since 2018/1/9
 */

public class AllotGoodsQueryAdapter extends RecyclerAdapter<AllotGoodsEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public AllotGoodsQueryAdapter(@Nullable List<AllotGoodsEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_allot_goods_query_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, AllotGoodsEntity item) {
        LinearLayout container = holder.get(R.id.allot_goods_query_container);
        KeyValueTextView location = holder.get(R.id.allot_goods_query_location);
        KeyValueTextView name = holder.get(R.id.allot_goods_query_name);
        KeyValueTextView lot = holder.get(R.id.allot_goods_query_lot);
        KeyValueTextView specification = holder.get(R.id.allot_goods_query_specification);
        KeyValueTextView validity = holder.get(R.id.allot_goods_query_validity);
        KeyValueTextView date = holder.get(R.id.allot_goods_query_production_date);
        KeyValueTextView unit = holder.get(R.id.allot_goods_query_unit);
        KeyValueTextView amount = holder.get(R.id.allot_goods_query_amount);
        KeyValueTextView manufacturer = holder.get(R.id.allot_goods_query_manufacturer);

        if (item != null) {
            if (item.isSelected()){
                container.setBackgroundColor(ResourcesCompat.getColor(holder.getItemView().getResources(), R.color.orange_300, null));
            }
            location.setValue(item.getLocationCode());
            name.setValue(item.getGoodsName());
            lot.setValue(item.getLotNo());
            specification.setValue(item.getSpecification());
            validity.setValue(item.getValidityDate());
            date.setValue(item.getProductionDate());
            unit.setValue(item.getUnit());
            amount.setValue(String.valueOf(item.getAmount()));
            manufacturer.setValue(item.getManufacturer());
        }

    }
}
