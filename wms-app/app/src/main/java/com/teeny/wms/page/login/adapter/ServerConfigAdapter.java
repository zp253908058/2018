package com.teeny.wms.page.login.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerAdapter;
import com.teeny.wms.base.RecyclerViewHolder;
import com.teeny.wms.model.ServerConfigEntity;
import com.teeny.wms.util.Validator;

import java.util.List;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ServerConfigAdapter
 * @since 2017/7/14
 */

public class ServerConfigAdapter extends RecyclerAdapter<ServerConfigEntity> {
    /**
     * the constructor of this class.
     *
     * @param items the data source.
     */
    public ServerConfigAdapter(@Nullable List<ServerConfigEntity> items) {
        super(items);
    }

    @Override
    protected int getLayoutByViewType(int viewType) {
        return R.layout.item_system_config_layout;
    }

    @Override
    protected void onBindViewHolder(RecyclerViewHolder holder, int position, ServerConfigEntity item) {
        TextView name = holder.get(R.id.system_config_service_name);
        TextView address = holder.get(R.id.system_config_service_address);
        TextView port = holder.get(R.id.system_config_port);

        if (Validator.isNotNull(item)) {
            name.setText(item.getServerName());
            address.setText(item.getServerAddress());
            port.setText(item.getPort());
        }
    }
}
