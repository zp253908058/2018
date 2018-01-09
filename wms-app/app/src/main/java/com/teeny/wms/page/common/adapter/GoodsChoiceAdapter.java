package com.teeny.wms.page.common.adapter;

import com.teeny.wms.R;
import com.teeny.wms.base.ListViewAdapter;
import com.teeny.wms.base.ViewHolder;
import com.teeny.wms.model.GoodsChoiceEntity;
import com.teeny.wms.model.SKUGoodsDetailEntity;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see GoodsChoiceAdapter
 * @since 2017/12/29
 */

public class GoodsChoiceAdapter<T extends GoodsChoiceEntity> extends ListViewAdapter<T> {

    public GoodsChoiceAdapter(List<T> items) {
        super(items);
    }

    @Override
    protected int getLayout() {
        return R.layout.item_goods_choice_layout;
    }

    @Override
    protected void onBindViewHolder(ViewHolder holder, int position, T item) {
        KeyValueTextView name = holder.get(R.id.goods_choice_name);
        KeyValueTextView specification = holder.get(R.id.goods_choice_specification);
        KeyValueTextView manufacturer = holder.get(R.id.goods_choice_manufacturer);

        if (item != null) {
            name.setValue(item.getGoodsName());
            specification.setValue(item.getSpecification());
            manufacturer.setValue(item.getManufacturers());
        }
    }
}
