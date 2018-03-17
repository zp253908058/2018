package com.teeny.wms.page.barcode.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.LinearLayout;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.BarcodeGoodsEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeGoodsAdapter
 * @since 2018/1/4
 */

public class BarcodeGoodsAdapter extends RecyclerAdapter<BarcodeGoodsEntity> {

    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public BarcodeGoodsAdapter(@Nullable List<BarcodeGoodsEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_barcode_collect_goods_adapter;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, BarcodeGoodsEntity item) {

        LinearLayout container = holder.get(R.id.barcode_collect_container);
        KeyValueTextView name = holder.get(R.id.barcode_collect_goods_name);
        KeyValueTextView barcode = holder.get(R.id.barcode_collect_goods_barcode);
        KeyValueTextView newBarcode = holder.get(R.id.barcode_collect_goods_new_barcode);
        KeyValueTextView specification = holder.get(R.id.barcode_collect_goods_specification);
        KeyValueTextView unit = holder.get(R.id.barcode_collect_goods_unit);
        KeyValueTextView manufacturer = holder.get(R.id.barcode_collect_goods_manufacturer);
        //剂型
        KeyValueTextView dosageForm = holder.get(R.id.barcode_collect_goods_dosage_form);


        if (Validator.isNotNull(item)) {
            name.setValue(item.getGoodsName());
            barcode.setValue(item.getBarcode());
            newBarcode.setValue(item.getNewBarcode());
            specification.setValue(item.getSpecification());
            unit.setValue(item.getUnit());
            manufacturer.setValue(item.getManufacturer());
            dosageForm.setValue(item.getDosageForm());
            if (Validator.isEmpty(item.getBarcode())) {
                container.setBackgroundColor(ResourcesCompat.getColor(holder.getItemView().getResources(), R.color.orange_300, null));
            } else if (Validator.isEmpty(item.getNewBarcode())) {
                container.setBackgroundColor(ResourcesCompat.getColor(holder.getItemView().getResources(), R.color.green_300, null));
            } else {
                container.setBackgroundColor(ResourcesCompat.getColor(holder.getItemView().getResources(), R.color.white, null));
            }
        }
    }
}
