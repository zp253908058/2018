package com.teeny.wms.page.allot.adapter;

import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.AllotListEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotListAdapter
 * @since 2017/7/18
 */

public class AllotListAdapter extends RecyclerAdapter<AllotListEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public AllotListAdapter(@Nullable List<AllotListEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_allot_list_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, AllotListEntity item) {

        KeyValueTextView export = holder.get(R.id.allot_list_export_location);
        KeyValueTextView importLocation = holder.get(R.id.allot_list_import_location);
        KeyValueTextView name = holder.get(R.id.allot_list_name);
        KeyValueTextView document = holder.get(R.id.allot_document_number);
        KeyValueTextView lot = holder.get(R.id.allot_list_lot_number);
        KeyValueTextView specification = holder.get(R.id.allot_list_specification);
        KeyValueTextView productionDate = holder.get(R.id.allot_list_production_date);
        KeyValueTextView validityDate = holder.get(R.id.allot_list_validity_date);
        KeyValueTextView amount = holder.get(R.id.allot_list_amount);
        KeyValueTextView manufacturer = holder.get(R.id.allot_list_manufacturer);

        if (Validator.isNotNull(item)) {
            export.setValue(item.getExportName());
            importLocation.setValue(item.getImportName());
            name.setValue(item.getGoodsName());
            document.setValue(item.getBillNo());
            lot.setValue(item.getLotNo());
            specification.setValue(item.getSpecification());
            productionDate.setValue(item.getProductDate());
            validityDate.setValue(item.getValidateDate());
            amount.setValue(String.valueOf(item.getAmount()));
            manufacturer.setValue(item.getManufacturer());
        }

    }
}
