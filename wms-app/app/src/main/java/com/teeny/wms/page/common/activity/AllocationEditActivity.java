package com.teeny.wms.page.common.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.CommonService;
import com.teeny.wms.model.AllocationEntity;
import com.teeny.wms.model.GoodsAllocationEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Converter;
import com.teeny.wms.util.Validator;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllocationEditActivity
 * @since 2017/8/25
 */

public class AllocationEditActivity extends ToolbarActivity implements DialogInterface.OnClickListener {

    public static final String KEY_DATA = "data";
    public static final String KEY_GOODS_ID = "goods_id";

    public static void startActivity(Context context, int goodsId) {
        startActivity(context, goodsId, null);
    }

    public static void startActivity(Context context, int goodsId, AllocationEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, AllocationEditActivity.class);
        intent.putExtra(KEY_GOODS_ID, goodsId);
        if (Validator.isNotNull(entity)) {
            intent.putExtra(KEY_DATA, entity);
        }
        context.startActivity(intent);
    }

    private EditText mAllocationText;
    private EditText mAmountEdit;
    private AllocationEntity mEntity;
    private int mGoodsId;

    private CommonService mService;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation_edit_layout);
        mScannerHelper.openScanner(this, this::handleResult);
        initView();
    }

    private void handleResult(String msg) {
        mAllocationText.setText(msg);
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_history_allocation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.history_allocation:
                showHistoryAllocation();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHistoryAllocation() {
        if (mAlertDialog != null) {
            mAlertDialog.show();
            return;
        }

        Flowable<ResponseEntity<List<GoodsAllocationEntity>>> flowable = mService.getHistoryAllocation(mGoodsId);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<GoodsAllocationEntity>>() {
            @Override
            public void doNext(List<GoodsAllocationEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("未获取到历史货位.");
                    data = new ArrayList<>();
                }
                mAlertDialog = DialogFactory.createSingleChoiceDialog(getContext(), getString(R.string.text_history_allocation), data.toArray(), AllocationEditActivity.this);
            }

            @Override
            public void doComplete() {
                mAlertDialog.show();
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        GoodsAllocationEntity entity = (GoodsAllocationEntity) mAlertDialog.getListView().getAdapter().getItem(which);
        mAllocationText.setText(entity.getCode());
        dialog.dismiss();
    }

    private void initView() {
        mAllocationText = (EditText) findViewById(R.id.allocation_edit_name);
        mAmountEdit = (EditText) findViewById(R.id.allocation_edit_amount);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_DATA)) {
            mEntity = intent.getParcelableExtra(KEY_DATA);
            mAllocationText.setText(mEntity.getLocationCode());
            mAmountEdit.setText(String.valueOf(mEntity.getAmount()));
        } else {
            mEntity = new AllocationEntity();
        }
        mGoodsId = intent.getIntExtra(KEY_GOODS_ID, 0);
        mService = NetServiceManager.getInstance().getService(CommonService.class);
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.allocation_edit_complete:
                onComplete();
                break;
        }
    }

    private void onComplete() {
        String locationCode = mAllocationText.getText().toString();
        if (Validator.isEmpty(locationCode)) {
            Toaster.showToast("请扫描或输入货位.");
            return;
        }
        String amount = mAmountEdit.getText().toString();
        if (Validator.isEmpty(amount)) {
            Toaster.showToast("请输入数量.");
            mAmountEdit.requestFocus();
            return;
        }
        mEntity.setLocationCode(locationCode);
        mEntity.setAmount(Converter.toFloat(amount));
        getEventBus().post(mEntity);
        finish();
    }
}
