package com.teeny.wms.page.picking.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teeny.wms.base.FilterActivity;
import com.teeny.wms.base.RecyclerFilterAdapter;
import com.teeny.wms.model.OutputPickingItemEntity;
import com.teeny.wms.page.picking.adapter.OrderDetailAdapter;
import com.teeny.wms.page.picking.helper.OutputPickingHelper;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OrderDetailActivity
 * @since 2018/1/21
 */

public class OrderDetailActivity extends FilterActivity<OutputPickingItemEntity> {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, OrderDetailActivity.class);
        context.startActivity(intent);
    }

    private OrderDetailAdapter mAdapter;

    @Override
    protected RecyclerFilterAdapter<OutputPickingItemEntity> getAdapter() {
        if (mAdapter == null) {
            mAdapter = new OrderDetailAdapter(null);
        }
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHint("品名|拼音|条码");

        getAdapter().setItems(OutputPickingHelper.getInstance().getData().getDataList());
    }
}
