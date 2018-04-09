package com.teeny.wms.page.shelve.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.ShelveEntity;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShelveAdapter
 * @since 2017/7/25
 */

public class ShelveAdapter extends RecyclerAdapter<ShelveEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public ShelveAdapter(@Nullable List<ShelveEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_shelve_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, ShelveEntity item) {

        KeyValueTextView no = holder.get(R.id.shelve_article_no_view);
        TextView status = holder.get(R.id.shelve_status_view);
        KeyValueTextView name = holder.get(R.id.shelve_goods_name_view);
        KeyValueTextView lot = holder.get(R.id.shelve_lot_number_view);
        KeyValueTextView specification = holder.get(R.id.shelve_specification_view);
        KeyValueTextView date = holder.get(R.id.shelve_production_date_view);
        KeyValueTextView unit = holder.get(R.id.shelve_unit_view);
        KeyValueTextView amount = holder.get(R.id.shelve_amount_view);
        KeyValueTextView manufacturer = holder.get(R.id.shelve_manufacturer_view);
        KeyValueTextView barcode = holder.get(R.id.shelve_barcode);
        KeyValueTextView remark = holder.get(R.id.shelve_remark);

        if (item != null) {
            no.setValue(item.getLocationCode());
            status.setText(item.getStatus() == 0 ? "待上架" : "已上架");
            name.setValue(item.getGoodsName());
            lot.setValue(item.getLotNo());
            specification.setValue(item.getSpecification());
            date.setValue(item.getProductionDate());
            unit.setValue(item.getUnit());
            amount.setValue(item.getAmount());
            manufacturer.setValue(item.getManufacturer());
            barcode.setValue(item.getGoodsCode());
            remark.setValue(item.getRemark());
        }

    }
}
