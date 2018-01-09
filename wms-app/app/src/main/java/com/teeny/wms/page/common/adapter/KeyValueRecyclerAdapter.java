package com.teeny.wms.page.common.adapter;

import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValueRecyclerAdapter
 * @since 2017/8/16
 */

public class KeyValueRecyclerAdapter extends RecyclerAdapter<KeyValueEntity> implements Filterable {
    private final Object mLock = new Object();

    private Filter mFilter;

    private List<KeyValueEntity> mOriginalValues;

    public KeyValueRecyclerAdapter(List<KeyValueEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void setItems(List<KeyValueEntity> items) {
        super.setItems(items);
        mOriginalValues = null;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, KeyValueEntity item) {
        TextView textView = holder.get(android.R.id.text1);
        if (Validator.isNotNull(item)) {
            textView.setText(item.getValue());
        }
    }

    @Override
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

            if (prefix == null || prefix.length() == 0) {
                final ArrayList<KeyValueEntity> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<KeyValueEntity> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<KeyValueEntity> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final KeyValueEntity value = values.get(i);
                    final String valueText = value.getValue().toLowerCase();
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
            replaces((List<KeyValueEntity>) results.values);
        }
    }
}
