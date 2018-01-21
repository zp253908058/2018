package com.teeny.wms.page.picking.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.teeny.wms.base.FilterActivity;
import com.teeny.wms.base.RecyclerFilterAdapter;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.PickingService;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.OutPickingTaskEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.picking.adapter.TaskAdapter;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see TaskActivity
 * @since 2018/1/21
 */

public class TaskActivity extends FilterActivity<OutPickingTaskEntity> {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, TaskActivity.class);
        context.startActivity(intent);
    }

    private TaskAdapter mAdapter;

    private PickingService mService;

    @Override
    protected RecyclerFilterAdapter<OutPickingTaskEntity> getAdapter() {
        if (mAdapter == null) {
            mAdapter = new TaskAdapter(null);
        }
        return mAdapter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHint("单号|业务员|状态");

        mService = NetServiceManager.getInstance().getService(PickingService.class);
        obtainData();
    }

    private void obtainData() {
        Flowable<ResponseEntity<List<OutPickingTaskEntity>>> flowable = mService.getTaskList();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<OutPickingTaskEntity>>(this) {
            @Override
            public void doNext(List<OutPickingTaskEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("未获取到工作任务.");
                } else {
                    getAdapter().setItems(data);
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }
}
