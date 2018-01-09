package com.teeny.wms.page.receiving.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.ReceivingItemEntity;
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
 * @see ReceivingAcceptanceAdapter
 * @since 2017/7/20
 */

public class ReceivingAcceptanceAdapter extends RecyclerAdapter<ReceivingItemEntity> implements Filterable {

    private final Object mLock = new Object();

    private Filter mFilter;

    private List<ReceivingItemEntity> mOriginalValues;

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public ReceivingAcceptanceAdapter(@Nullable List<ReceivingItemEntity> items) {
        super(items);
    }

    @Override
    public void setItems(List<ReceivingItemEntity> items) {
        mOriginalValues = null;
        super.setItems(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_receiving_acceptance_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, ReceivingItemEntity item) {
        TextView name = holder.get(R.id.receiving_acceptance_goods_name);
        KeyValueTextView billNo = holder.get(R.id.receiving_acceptance_bill_number);
        KeyValueTextView lotNo = holder.get(R.id.receiving_acceptance_lot_number);
        KeyValueTextView specification = holder.get(R.id.receiving_acceptance_specification);
        KeyValueTextView validity = holder.get(R.id.receiving_acceptance_validity);
        KeyValueTextView retailPrice = holder.get(R.id.receiving_acceptance_retail_price);
        KeyValueTextView receivingQuantity = holder.get(R.id.receiving_acceptance_receiving_quantity);
        KeyValueTextView amount = holder.get(R.id.receiving_acceptance_amount);
        KeyValueTextView manufacturer = holder.get(R.id.receiving_acceptance_manufacturer);

        if (Validator.isNotNull(item)) {
            name.setText(item.getGoodsName());
            billNo.setValue(item.getBillNo());
            lotNo.setValue(item.getLotNo());
            specification.setValue(item.getSpecification());
            validity.setValue(item.getValidityDate());
            retailPrice.setValue(item.getRetailPrice());
            amount.setValue(item.getAmount());
            receivingQuantity.setValue(item.getQuantity());
            manufacturer.setValue(item.getManufacturer());
        }
    }


    @Override
    @NonNull
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new FilterImpl();
        }
        return mFilter;
    }

    /**
     * <p>A filter impl constrains the content of the array adapter with
     * a prefix. Each item that does not start with the supplied prefix
     * is removed from the list.</p>
     */
    private class FilterImpl extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            final FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(getItems());
                }
            }

            if (Validator.isEmpty(prefix)) {
                final ArrayList<ReceivingItemEntity> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<ReceivingItemEntity> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<ReceivingItemEntity> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final ReceivingItemEntity value = values.get(i);
                    final String valueText = ObjectUtils.getString(value.getBarcode()).toLowerCase() + ObjectUtils.getString(value.getPinyin()).toLowerCase();
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
            replaces((List<ReceivingItemEntity>) results.values);
        }
    }
}
