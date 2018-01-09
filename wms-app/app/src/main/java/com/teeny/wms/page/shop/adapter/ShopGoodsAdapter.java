package com.teeny.wms.page.shop.adapter;

import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.InventoryGoodsEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopGoodsAdapter
 * @since 2017/8/20
 */

public class ShopGoodsAdapter extends RecyclerAdapter<InventoryGoodsEntity> {

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public ShopGoodsAdapter(@Nullable List<InventoryGoodsEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_shop_goods_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, InventoryGoodsEntity item) {
        KeyValueTextView name = holder.get(R.id.shop_goods_name);
        KeyValueTextView allocation = holder.get(R.id.shop_goods_allocation);
        KeyValueTextView inventoryAmount = holder.get(R.id.shop_goods_inventory_amount);
        KeyValueTextView paperAmount = holder.get(R.id.shop_goods_paper_amount);
        KeyValueTextView unit = holder.get(R.id.shop_goods_unit);
        KeyValueTextView specification = holder.get(R.id.shop_goods_specification);
        KeyValueTextView manufacturer = holder.get(R.id.shop_goods_manufacturer);

        if (Validator.isNotNull(item)) {
            name.setValue(item.getGoodsName());
            allocation.setValue(item.getLocation());
            inventoryAmount.setValue(String.valueOf(item.getInventoryCount()));
            paperAmount.setValue(String.valueOf(item.getCountInBill()));
            unit.setValue(item.getUnit());
            specification.setValue(item.getSpecification());
            manufacturer.setValue(item.getManufacturer());
        }
    }
}
