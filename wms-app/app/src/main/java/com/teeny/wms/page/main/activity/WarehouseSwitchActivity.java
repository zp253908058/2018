package com.teeny.wms.page.main.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.teeny.wms.base.FilterRecyclerAdapter;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.HomeService;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.common.activity.SearchActivity;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.page.main.adapter.WarehouseAdapter;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:仓库切换页面
 *
 * @author zp
 * @version 1.0
 * @see WarehouseSwitchActivity
 * @since 2018/4/21
 */
public class WarehouseSwitchActivity extends SearchActivity<KeyValueEntity> {

    private WarehouseAdapter mAdapter = new WarehouseAdapter();

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, WarehouseSwitchActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected FilterRecyclerAdapter<KeyValueEntity> getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getType() {
        return 1;
    }

    @Override
    protected void onObtainData() {
        HomeService homeService = NetServiceManager.getInstance().getService(HomeService.class);
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = homeService.getWarehouseList();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                mAdapter.appends(data);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    @Override
    protected void onItemClick(View view, int i) {
        KeyValueEntity entity = mAdapter.getItem(i);
        NetServiceManager manager = NetServiceManager.getInstance();
        if (entity.getKey() != manager.getWarehouseId()) {
            manager.setWarehouseId(entity.getKey());
            DocumentHelper.getInstance().notifyDocumentChanged();
            getEventBus().post(new MainActivity.WarehouseFlag(entity.getValue()));
        }
        finish();
    }
}
