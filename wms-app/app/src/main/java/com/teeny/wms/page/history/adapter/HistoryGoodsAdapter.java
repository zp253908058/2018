package com.teeny.wms.page.history.adapter;

import android.support.annotation.Nullable;
import android.widget.Filter;

import com.teeny.wms.R;
import com.teeny.wms.base.FilterRecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.HistoryGoodsEntity;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see HistoryGoodsAdapter
 * @since 2018/4/22
 */
public class HistoryGoodsAdapter extends FilterRecyclerAdapter<HistoryGoodsEntity> {

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public HistoryGoodsAdapter(@Nullable List<HistoryGoodsEntity> items) {
        super(items);
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_history_goods_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, HistoryGoodsEntity item) {
        KeyValueTextView name = holder.get(R.id.history_goods_name_view);
        KeyValueTextView code = holder.get(R.id.history_goods_code_view);
        KeyValueTextView specification = holder.get(R.id.history_goods_specification_view);
        KeyValueTextView manufacturer = holder.get(R.id.history_goods_manufacturer_view);

        if (item != null) {
            name.setValue(item.getGoodsName());
            code.setValue(item.getGoodsEncode());
            specification.setValue(item.getSpecification());
            manufacturer.setValue(item.getManufacturer());
        }
    }
}
