package com.teeny.wms.page.history.activity;

import android.content.Context;
import android.content.Intent;

import com.teeny.wms.base.FilterRecyclerAdapter;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.CommonService;
import com.teeny.wms.datasouce.net.service.HomeService;
import com.teeny.wms.model.HistoryGoodsEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.page.common.activity.SearchActivity;
import com.teeny.wms.page.history.adapter.HistoryGoodsAdapter;
import com.teeny.wms.page.picking.activity.OutputPickingActivity;
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
 * @see HistoryGoodsActivity
 * @since 2018/4/22
 */
public class HistoryGoodsActivity extends SearchActivity<HistoryGoodsEntity> {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, HistoryGoodsActivity.class);
        context.startActivity(intent);
    }

    private HistoryGoodsAdapter mAdapter = new HistoryGoodsAdapter(null);

    private CommonService mService = NetServiceManager.getInstance().getService(CommonService.class);

    @Override
    protected FilterRecyclerAdapter<HistoryGoodsEntity> getAdapter() {
        return mAdapter;
    }

    @Override
    protected int getType() {
        return 2;
    }

    @Override
    protected void onSearch(String condition) {
        if (Validator.isEmpty(condition)) {
            Toaster.showToast("输入信息为空.");
            return;
        }
        Flowable<ResponseEntity<List<HistoryGoodsEntity>>> flowable = mService.getHistoryGoods(condition);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<HistoryGoodsEntity>>(this) {
            @Override
            public void doNext(List<HistoryGoodsEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("没有为你查询到相关信息.");
                }
                mAdapter.setItems(data);
            }

            @Override
            public void doComplete() {

            }
        });
    }
}
