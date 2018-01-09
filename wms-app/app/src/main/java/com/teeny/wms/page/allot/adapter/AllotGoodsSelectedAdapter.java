package com.teeny.wms.page.allot.adapter;

import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.AllotGoodsEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotGoodsSelectedAdapter
 * @since 2018/1/9
 */

public class AllotGoodsSelectedAdapter extends RecyclerAdapter<AllotGoodsEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public AllotGoodsSelectedAdapter(@Nullable List<AllotGoodsEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_allot_goods_selected_layout;
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, AllotGoodsEntity item) {

        KeyValueTextView export = holder.get(R.id.allot_goods_selected_export_location);
        KeyValueTextView importLocation = holder.get(R.id.allot_goods_selected_import_location);
        KeyValueTextView name = holder.get(R.id.allot_goods_selected_name);
        KeyValueTextView lot = holder.get(R.id.allot_goods_selected_lot_number);
        KeyValueTextView specification = holder.get(R.id.allot_goods_selected_specification);
        KeyValueTextView productionDate = holder.get(R.id.allot_goods_selected_production_date);
        KeyValueTextView validityDate = holder.get(R.id.allot_goods_selected_validity_date);
        KeyValueTextView amount = holder.get(R.id.allot_goods_selected_amount);
        KeyValueTextView manufacturer = holder.get(R.id.allot_goods_selected_manufacturer);

        if (Validator.isNotNull(item)) {
//            export.setValue(item.getExportName());
//            importLocation.setValue(item.getImportName());
//            name.setValue(item.getGoodsName());
//            lot.setValue(item.getLotNo());
//            specification.setValue(item.getSpecification());
//            productionDate.setValue(item.getProductDate());
//            validityDate.setValue(item.getValidateDate());
//            amount.setValue(String.valueOf(item.getAmount()));
//            manufacturer.setValue(item.getManufacturer());
        }
    }
}
