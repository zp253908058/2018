package com.teeny.wms.page.review;

import android.support.annotation.Nullable;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.RecipientEntity;
import com.teeny.wms.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RecipientAdapter
 * @since 2017/9/21
 */

public class RecipientAdapter extends RecyclerAdapter<RecipientEntity> implements Filterable {

    private final Object mLock = new Object();

    private Filter mFilter;

    private List<RecipientEntity> mOriginalValues;

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public RecipientAdapter(@Nullable List<RecipientEntity> items) {
        super(items);
    }

    @Override
    public void setItems(List<RecipientEntity> items) {
        super.setItems(items);
        mOriginalValues = null;
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.common_text_view_2;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, RecipientEntity item) {
        TextView view = holder.get(R.id.text_view);

        if (Validator.isNotNull(item)) {
            view.setText(item.getName());
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
                final ArrayList<RecipientEntity> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String prefixString = prefix.toString().toLowerCase();

                final ArrayList<RecipientEntity> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<RecipientEntity> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final RecipientEntity value = values.get(i);
                    String valueText = value.getName().toLowerCase();
                    if (valueText.contains(prefixString)) {
                        newValues.add(value);
                        continue;
                    }
                    valueText = value.getSerialNumber().toLowerCase();
                    if (valueText.contains(prefixString)) {
                        newValues.add(value);
                        continue;
                    }
                    valueText = value.getPinyin().toLowerCase();
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
            replaces((List<RecipientEntity>) results.values);
        }
    }
}
