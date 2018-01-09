package com.teeny.wms.page.receiving.adapter;

import android.support.annotation.Nullable;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.ReceivingLotEntity;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AcceptanceLotAdapter
 * @since 2017/8/15
 */

public class AcceptanceLotAdapter extends RecyclerAdapter<ReceivingLotEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public AcceptanceLotAdapter(@Nullable List<ReceivingLotEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_acceptance_lot_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, ReceivingLotEntity item) {
        KeyValueTextView lot = holder.get(R.id.lot_number);
        KeyValueTextView serial = holder.get(R.id.lot_serial_number);
        KeyValueTextView validityDate = holder.get(R.id.lot_validity_date);
        KeyValueTextView price = holder.get(R.id.lot_price);
        KeyValueTextView zh = holder.get(R.id.lot_zh_amount);
        KeyValueTextView lh = holder.get(R.id.lot_lh_amount);

        if (Validator.isNotNull(item)) {
            lot.setValue(item.getLotNo());
            serial.setValue(String.valueOf(item.getSerialNo()));
            validityDate.setValue(item.getValidityDate());
            price.setValue(String.valueOf(item.getPrice()));
            zh.setValue(String.valueOf(item.getZhAmount()));
            lh.setValue(String.valueOf(item.getLhAmount()));
        }
    }
}
