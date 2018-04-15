package com.teeny.wms.page.second.adapter;

import android.support.annotation.Nullable;
import android.widget.Filter;
import android.widget.Filterable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.InventoryGoodsEntity;
import com.teeny.wms.model.RecipientEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SecondInventoryGoodsAdapter
 * @since 2017/8/20
 */

public class SecondInventoryGoodsAdapter extends RecyclerAdapter<InventoryGoodsEntity> implements Filterable {

    private final Object mLock = new Object();

    private Filter mFilter;

    private List<InventoryGoodsEntity> mOriginalValues;

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

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new FilterImpl();
        }
        return mFilter;
    }

    private class FilterImpl extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(getItems());
                }
            }

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<InventoryGoodsEntity> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<InventoryGoodsEntity> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<InventoryGoodsEntity> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final InventoryGoodsEntity value = values.get(i);
                    String valueText = value.getLocationCode().toLowerCase();
                    if (valueText.contains(prefixString)) {
                        newValues.add(value);
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            replaces((List<InventoryGoodsEntity>) results.values);
        }
    }
}
