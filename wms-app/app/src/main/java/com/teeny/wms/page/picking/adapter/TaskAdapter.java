package com.teeny.wms.page.picking.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.Filter;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerFilterAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.OutPickingTaskEntity;
import com.teeny.wms.util.ObjectUtils;
import com.teeny.wms.widget.KeyValueTextView;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TaskAdapter
 * @since 2018/1/21
 */

public class TaskAdapter extends RecyclerFilterAdapter<OutPickingTaskEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public TaskAdapter(@Nullable List<OutPickingTaskEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_output_picking_task_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, OutPickingTaskEntity item) {
        KeyValueTextView document = holder.get(R.id.output_picking_task_document);
        KeyValueTextView username = holder.get(R.id.output_picking_task_username);
        KeyValueTextView row = holder.get(R.id.output_picking_task_detail_row);
        KeyValueTextView money = holder.get(R.id.output_picking_task_money);
        KeyValueTextView status = holder.get(R.id.output_picking_task_status);

        if (item != null) {
            document.setValue(item.getDocumentNo());
            username.setValue(item.getUsername());
            row.setValue(item.getDetailRow());
            money.setValue(item.getTotalMoney());
            if (item.isUntreated()) {
                status.setValue("未拣");
                status.setValueColor(ResourcesCompat.getColor(holder.getItemView().getResources(), R.color.red_600, null));
            } else {
                status.setValue("已拣");
                status.setValueColor(ResourcesCompat.getColor(holder.getItemView().getResources(), R.color.teal_a400, null));
            }
        }
    }

    @Override
    protected String getConstraintString(OutPickingTaskEntity entity) {
        String status = entity.isUntreated() ? "未拣" : "已拣";
        return ObjectUtils.concat(entity.getDocumentNo(), entity.getUsername(), status);
    }
}
