package com.teeny.wms.page.second.adapter;

import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.InventoryGoodsEntity;
import com.teeny.wms.model.SecondInventoryGoodsEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SecondInventoryGoodsAdapter
 * @since 2017/8/20
 */

public class SecondInventoryGoodsAdapter extends RecyclerAdapter<InventoryGoodsEntity> {

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public SecondInventoryGoodsAdapter(@Nullable List<InventoryGoodsEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_second_inventory_goods_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, InventoryGoodsEntity item) {
        KeyValueTextView name = holder.get(R.id.second_inventory_goods_name);
        KeyValueTextView allocation = holder.get(R.id.second_inventory_goods_allocation);
        KeyValueTextView lot = holder.get(R.id.second_inventory_goods_lot_number);
        KeyValueTextView amount = holder.get(R.id.second_inventory_goods_amount);
        KeyValueTextView paperAmount = holder.get(R.id.second_inventory_goods_paper_amount);
        KeyValueTextView productionDate = holder.get(R.id.second_inventory_goods_production_date);
        KeyValueTextView validateDate = holder.get(R.id.second_inventory_goods_validate_date);
        KeyValueTextView specification = holder.get(R.id.second_inventory_goods_specification);
        KeyValueTextView unit = holder.get(R.id.second_inventory_goods_unit);
        KeyValueTextView manufacturer = holder.get(R.id.second_inventory_goods_manufacturer);

        if (Validator.isNotNull(item)) {
            name.setValue(item.getGoodsName());
            allocation.setValue(item.getLocation());
            lot.setValue(item.getLotNo());
            amount.setValue(String.valueOf(item.getInventoryCount()));
            paperAmount.setValue(String.valueOf(item.getCountInBill()));
            productionDate.setValue(item.getProductionDate());
            validateDate.setValue(item.getValidateDate());
            unit.setValue(item.getUnit());
            specification.setValue(item.getSpecification());
            manufacturer.setValue(item.getManufacturer());
        }
    }
}
