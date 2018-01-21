package com.teeny.wms.page.picking.adapter;

import android.support.annotation.Nullable;
import android.widget.Filter;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerFilterAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.OutputPickingItemEntity;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OrderDetailAdapter
 * @since 2018/1/21
 */

public class OrderDetailAdapter extends RecyclerFilterAdapter<OutputPickingItemEntity> {

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public OrderDetailAdapter(@Nullable List<OutputPickingItemEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_output_picking_detail_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, OutputPickingItemEntity item) {
        KeyValueTextView name = holder.get(R.id.output_picking_detail_goods_name);
        KeyValueTextView specification = holder.get(R.id.output_picking_detail_specification);
        KeyValueTextView unit = holder.get(R.id.output_picking_detail_unit);
        KeyValueTextView location = holder.get(R.id.output_picking_detail_location);
        KeyValueTextView count = holder.get(R.id.output_picking_detail_order_count);
        KeyValueTextView lot = holder.get(R.id.output_picking_detail_lot);
        KeyValueTextView validateDate = holder.get(R.id.output_picking_detail_validate_date);
        KeyValueTextView manufacturer = holder.get(R.id.output_picking_detail_manufacturer);

        if (Validator.isNotNull(item)) {
            name.setValue(item.getGoodsName());
            specification.setValue(item.getSpecification());
            unit.setValue(item.getUnit());
            location.setValue(item.getLocation());
            count.setValue(String.valueOf(item.getOrderCount()));
            lot.setValue(item.getLot());
            validateDate.setValue(item.getValidate());
            manufacturer.setValue(item.getManufacturer());
        }
    }

    @Override
    protected String getConstraintString(OutputPickingItemEntity entity) {
        return ObjectUtils.concat(entity.getGoodsName(), entity.getPinyin(), entity.getGoodsBarcode());
    }
}
