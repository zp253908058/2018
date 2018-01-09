package com.teeny.wms.page.warehouse.adapter;

import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.InventoryGoodsEntity;
import com.teeny.wms.model.WarehouseGoodsEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see WarehouseGoodsAdapter
 * @since 2017/8/23
 */

public class WarehouseGoodsAdapter extends RecyclerAdapter<InventoryGoodsEntity> {

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public WarehouseGoodsAdapter(@Nullable List<InventoryGoodsEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_warehouse_goods_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, InventoryGoodsEntity item) {
        KeyValueTextView name = holder.get(R.id.warehouse_goods_name);
        KeyValueTextView allocation = holder.get(R.id.warehouse_goods_allocation);
        KeyValueTextView lot = holder.get(R.id.warehouse_goods_lot_number);
        KeyValueTextView amount = holder.get(R.id.warehouse_goods_amount);
        KeyValueTextView paperAmount = holder.get(R.id.warehouse_goods_paper_amount);
        KeyValueTextView productionDate = holder.get(R.id.warehouse_goods_production_date);
        KeyValueTextView validateDate = holder.get(R.id.warehouse_goods_validate_date);
        KeyValueTextView specification = holder.get(R.id.warehouse_goods_specification);
        KeyValueTextView unit = holder.get(R.id.warehouse_goods_unit);
        KeyValueTextView manufacturer = holder.get(R.id.warehouse_goods_manufacturer);

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
