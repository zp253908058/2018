package com.teeny.wms.page.document.adapter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.DocumentEntity;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see DocumentAdapter
 * @since 2017/7/18
 */

public class DocumentAdapter extends RecyclerAdapter<DocumentEntity> implements Filterable {

    private final Object mLock = new Object();

    private Filter mFilter;

    private List<DocumentEntity> mOriginalValues;

    private CharSequence mStatus;

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public DocumentAdapter(@Nullable List<DocumentEntity> items) {
        super(items);
    }

    @Override
    public void setItems(List<DocumentEntity> items) {
        super.setItems(items);
        mOriginalValues = null;
    }

    public CharSequence getStatus() {
        return mStatus;
    }

    public void setStatus(CharSequence status) {
        mStatus = status;
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_document_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, DocumentEntity item) {
        TextView type = holder.get(R.id.document_type_text_view);
        TextView status = holder.get(R.id.document_status_text_view);
        TextView number = holder.get(R.id.document_number_text_view);
        TextView date = holder.get(R.id.document_date_text_view);

        if (Validator.isNotNull(item)) {
            type.setText(item.getTypeDescription());
            status.setText(item.getStatus());
            number.setText(item.getNumber());
            date.setText(item.getDate());
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

            if (Validator.isEmpty(prefix) && Validator.isEmpty(mStatus)) {
                final ArrayList<DocumentEntity> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = "";
                if (Validator.isNotEmpty(prefix)) {
                    prefixString = prefix.toString().toLowerCase();
                }

                final ArrayList<DocumentEntity> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<DocumentEntity> newValues = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    final DocumentEntity value = values.get(i);
                    final String valueText = value.getNumber().toLowerCase();
                    if (valueText.contains(prefixString)) {
                        if (Validator.isNotEmpty(mStatus) && !ObjectUtils.equals(mStatus, value.getStatus())) {
                            continue;
                        }
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
            replaces((List<DocumentEntity>) results.values);
        }
    }
}
