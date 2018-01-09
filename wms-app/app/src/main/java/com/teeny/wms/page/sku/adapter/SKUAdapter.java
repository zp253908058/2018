package com.teeny.wms.page.sku.adapter;

import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.SKUEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SKUAdapter
 * @since 2017/7/31
 */

public class SKUAdapter extends RecyclerAdapter<SKUEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public SKUAdapter(@Nullable List<SKUEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_sku_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, SKUEntity item) {

        KeyValueTextView name = holder.get(R.id.sku_check_name);
        KeyValueTextView allocation = holder.get(R.id.sku_check_allocation);
        KeyValueTextView lot = holder.get(R.id.sku_check_lot_number);
        KeyValueTextView manufacturer = holder.get(R.id.sku_check_manufacturer);
        KeyValueTextView amount = holder.get(R.id.sku_check_amount);
        KeyValueTextView date = holder.get(R.id.sku_check_validity_date);
        KeyValueTextView specification = holder.get(R.id.sku_check_specification);

        if (Validator.isNotNull(item)) {
            name.setValue(item.getGoodsName());
            allocation.setValue(item.getLocationName());
            lot.setValue(item.getLotNo());
            manufacturer.setValue(item.getManufacturer());
            amount.setValue(String.valueOf(item.getQuantity()));
            date.setValue(item.getValidateDate());
            specification.setValue(item.getSpecification());
        }

    }
}
