package com.teeny.wms.page.delivery.adapter;

import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.ShopDeliveryGoodsEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryGoodsAdapter
 * @since 2018/1/18
 */

public class ShopDeliveryGoodsAdapter extends RecyclerAdapter<ShopDeliveryGoodsEntity> {

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public ShopDeliveryGoodsAdapter(@Nullable List<ShopDeliveryGoodsEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_shop_delivery_goods_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, ShopDeliveryGoodsEntity item) {
        KeyValueTextView name = holder.get(R.id.shop_delivery_goods_name);
        KeyValueTextView specification = holder.get(R.id.shop_delivery_goods_specification);
        KeyValueTextView unit = holder.get(R.id.shop_delivery_goods_unit);
        KeyValueTextView receiving = holder.get(R.id.shop_delivery_goods_receiving_number);
        KeyValueTextView delivery = holder.get(R.id.shop_delivery_goods_delivery_number);
        KeyValueTextView lot = holder.get(R.id.shop_delivery_goods_lot);
        KeyValueTextView validateDate = holder.get(R.id.shop_delivery_goods_validate_date);
        KeyValueTextView manufacturer = holder.get(R.id.shop_delivery_goods_manufacturer);

        if (Validator.isNotNull(item)) {
            name.setValue(item.getGoodsName());
            specification.setValue(item.getSpecification());
            unit.setValue(item.getUnit());
            receiving.setValue(String.valueOf(item.getReceivingNumber()));
            delivery.setValue(String.valueOf(item.getDeliveryNumber()));
            lot.setValue(item.getLotNumber());
            validateDate.setValue(item.getValidateDate());
            manufacturer.setValue(item.getManufacturer());
        }
    }
}