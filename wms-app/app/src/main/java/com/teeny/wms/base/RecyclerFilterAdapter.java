package com.teeny.wms.base;

import android.support.annotation.Nullable;
import android.widget.Filter;
import android.widget.Filterable;

import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see RecyclerFilterAdapter
 * @since 2018/1/21
 */

public abstract class RecyclerFilterAdapter<T> extends RecyclerAdapter<T> implements Filterable {

    private final Object mLock = new Object();

    private Filter mFilter;

    private List<T> mOriginalValues;

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public RecyclerFilterAdapter(@Nullable List<T> items) {
        super(items);
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
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(getItems());
                }
            }

            if (Validator.isEmpty(constraint)) {
                final ArrayList<T> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                final String constraintString = constraint.toString().toLowerCase();
                final ArrayList<T> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }
                final int count = values.size();
                final ArrayList<T> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final T value = values.get(i);
                    final String valueText = getConstraintString(value);
                    if (Validator.isNotEmpty(valueText)) {
                        if (valueText.toLowerCase().contains(constraintString)) {
                            newValues.add(value);
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

        }
    }

    protected abstract String getConstraintString(T t);
}
