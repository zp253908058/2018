package com.teeny.wms.page.main.adapter;

import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.page.common.adapter.KeyValueRecyclerAdapter;
import com.teeny.wms.util.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see WarehouseAdapter
 * @since 2017/7/19
 */

public class WarehouseAdapter extends KeyValueRecyclerAdapter {
    /**
     * the constructor of this class.
     */
    public WarehouseAdapter() {
        this(null);
        List<KeyValueEntity> list = new ArrayList<>();
        list.add(new KeyValueEntity(0, "所有仓库"));
        setItems(list);
    }

    private WarehouseAdapter(List<KeyValueEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.common_text_view;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, KeyValueEntity item) {
        TextView textView = holder.get(R.id.text_view);
        if (Validator.isNotNull(item)) {
            textView.setText(item.getValue());
        }
    }
}
