package com.teeny.wms.base;

import android.widget.TextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see MultipleChoiceAdapter
 * @since 2017/8/28
 */

public class MultipleChoiceAdapter<T> extends ListViewAdapter<T> {

    public MultipleChoiceAdapter(List<T> items) {
        super(items);
    }

    @Override
    protected int getLayout() {
        return android.R.layout.simple_list_item_multiple_choice;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, T item) {
        TextView view = holder.get(android.R.id.text1);
        if (item != null) {
            view.setText(item.toString());
        }
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
