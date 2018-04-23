package com.teeny.wms.page.picking.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.PickingService;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.OutputPickingEntity;
import com.teeny.wms.model.OutputPickingItemEntity;
import com.teeny.wms.model.OutputPickingOrderEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.OutputPickingRequestEntity;
import com.teeny.wms.page.allot.AllotOrderAddActivity;
import com.teeny.wms.page.barcode.BarcodeCollectActivity;
import com.teeny.wms.page.common.adapter.AllocationAdapter;
import com.teeny.wms.page.common.adapter.SimpleAdapter;
import com.teeny.wms.page.picking.adapter.OutputPickingAdapter;
import com.teeny.wms.page.picking.helper.OutputPickingHelper;
import com.teeny.wms.page.sku.SKUCheckActivity;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Converter;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
import com.teeny.wms.util.log.Logger;
import com.teeny.wms.widget.KeyValueTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutputPickingActivity
 * @since 2018/1/20
 */

public class OutputPickingActivity extends ToolbarActivity implements DialogInterface.OnClickListener {

    public static void startActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, OutputPickingActivity.class);
        context.startActivity(intent);
    }

    private static final int INVALID_POSITION = -1;

    private KeyValueTextView mDocumentNoView;

    private KeyValueTextView mLocationView;
    private KeyValueTextView mDeskNameView;
    private TextView mClientNameView;
    private KeyValueTextView mOrderRemark;
    private KeyValueTextView mOriginalType;
    private KeyValueTextView mProgressView;
    private KeyValueTextView mNextLocationView;
    private KeyValueTextView mWarehouseView;
    private KeyValueTextView mClerkView;         //业务员
    private KeyValueTextView mShopView;
    private KeyValueTextView mTotalMoneyView;

    private KeyValueTextView mNameView;
    private KeyValueTextView mSpecificationView;
    private KeyValueTextView mManufacturerView;
    private KeyValueTextView mUnitView;
    private KeyValueTextView mLotView;
    private KeyValueTextView mValidityView;
    private KeyValueTextView mProductionDateView;
    private KeyValueTextView mBarcodeView;
    private KeyValueTextView mUnitPriceView;
    private KeyValueTextView mMoneyView;
    private KeyValueTextView mOrderCountView;
    private EditText mPickCountView;
    private KeyValueTextView mRemarkView;

    private OutputPickingAdapter mAdapter;

    private int mSelectPosition = INVALID_POSITION;
    private int mDeletePosition = INVALID_POSITION;

    private AlertDialog mDeleteDialog;

    private PickingService mService;

    private OutputPickingHelper mHelper;

    private AlertDialog mSubmitDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_picking_layout);
        showNavigationIcon(true);
        initView();
        registerEventBus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
        mHelper.clear();
    }

    private void initView() {
        setNavigationOnClickListener(v -> {
            if (mHelper.hasPrev()) {
                mHelper.prev();
            } else {
                Toaster.showToast("前面没有更多了.");
            }
        });
        mDocumentNoView = findViewById(R.id.output_picking_document_no);
        mDocumentNoView.setOnClickListener(v -> {
            if (Validator.isEmpty(mDocumentNoView.getValue())) {
                initialize();
            }
        });

        mSubmitDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_complete_current), this::onSubmit);

        mLocationView = findViewById(R.id.output_picking_location);
        mDeskNameView = findViewById(R.id.output_picking_desk_name);
        mClientNameView = findViewById(R.id.output_picking_client_name);
        mOrderRemark = findViewById(R.id.output_picking_order_remark);
        mOriginalType = findViewById(R.id.output_picking_original_type);
        mProgressView = findViewById(R.id.output_picking_progress);
        mNextLocationView = findViewById(R.id.output_picking_next_location);
        mWarehouseView = findViewById(R.id.output_picking_warehouse);
        mClerkView = findViewById(R.id.output_picking_clerk);
        mShopView = findViewById(R.id.output_picking_shop);
        mTotalMoneyView = findViewById(R.id.output_picking_total_money);

        mNameView = findViewById(R.id.output_picking_name);
        mSpecificationView = findViewById(R.id.output_picking_specification);
        mManufacturerView = findViewById(R.id.output_picking_manufacturer);
        //TODO
        mUnitView = findViewById(R.id.output_picking_unit);
        mLotView = findViewById(R.id.output_picking_lot);
        mValidityView = findViewById(R.id.output_picking_validity);
        mProductionDateView = findViewById(R.id.output_picking_production_date);
        mBarcodeView = findViewById(R.id.output_picking_barcode);
        mUnitPriceView = findViewById(R.id.output_picking_unit_price);
        mMoneyView = findViewById(R.id.output_picking_money);
        mOrderCountView = findViewById(R.id.output_picking_order_count);
        mPickCountView = findViewById(R.id.output_picking_pick_count);
        mRemarkView = findViewById(R.id.output_picking_remark);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new OutputPickingAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        decoration.setNeedDraw(false);
        recyclerView.addItemDecoration(decoration);
        RecyclerViewTouchListener listener = new RecyclerViewTouchListener(this, this::onItemClick);
        listener.setOnItemLongClickListener(this::onItemLongClick);
        recyclerView.addOnItemTouchListener(listener);

        mDeleteDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_complete_current), this);
        mDeleteDialog.setOnDismissListener(dialog -> mDeletePosition = INVALID_POSITION);

        mService = NetServiceManager.getInstance().getService(PickingService.class);
        mHelper = OutputPickingHelper.getInstance();
        initialize();
    }

    private void onSubmit(DialogInterface dialog, int which) {
        complete();
    }

    private void initialize() {
        Flowable<ResponseEntity<OutputPickingOrderEntity>> flowable = mService.initialize();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<OutputPickingOrderEntity>(this) {
            @Override
            public void doNext(OutputPickingOrderEntity data) {
                mHelper.setData(data);
            }

            @Override
            public void doComplete() {

            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChanged(OutputPickingHelper helper) {
        OutputPickingOrderEntity entity = helper.getData();
        mDocumentNoView.setValue(entity.getNumber());
        mDeskNameView.setValue(entity.getDeskName());
        mClientNameView.setText(entity.getClientName());
        mOrderRemark.setValue(entity.getOrderRemark());
        mOriginalType.setValue(entity.getOriginalType());
        mWarehouseView.setValue(entity.getWarehouse());
        mClerkView.setValue(entity.getClerk());
        mShopView.setValue(entity.getShopName());
        mTotalMoneyView.setValue(String.valueOf(entity.getTotalMoney()));
        mAdapter.setItems(entity.getTurnoverList());

        setData(helper.getCurrent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setData(OutputPickingItemEntity entity) {
        mNameView.setValue(entity.getGoodsName());
        mSpecificationView.setValue(entity.getSpecification());
        mManufacturerView.setValue(entity.getManufacturer());
        mUnitView.setValue(entity.getUnit());
        mLotView.setValue(entity.getLot());
        mValidityView.setValue(entity.getValidate());
        mProductionDateView.setValue(entity.getProductionDate());
        mBarcodeView.setValue(entity.getGoodsBarcode());
        mUnitPriceView.setValue(String.valueOf(entity.getUnitPrice()));
        mMoneyView.setValue(String.valueOf(entity.getMoney()));
        mOrderCountView.setValue(String.valueOf(entity.getOrderCount()));
        mPickCountView.setText(String.valueOf(entity.getPickCount()));
        mLocationView.setValue(entity.getLocation());
        mNextLocationView.setValue(mHelper.getNextLocation());
        mRemarkView.setValue(entity.getRemark());

        mProgressView.setValue(mHelper.getData().getProgress());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_output_picking, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.work_task) {
            TaskActivity.startActivity(this);
            return true;
        }
        if (mHelper.getCurrent() == null) {
            Toaster.showToast("没有单据.");
            return true;
        }
        switch (id) {
            case R.id.add_turnover_box:
                OutputPickingAddActivity.startActivity(this);
                return true;
            case R.id.goods_allot:
                AllotOrderAddActivity.startActivity(this, mHelper.getCurrent().getGoodsName());
                return true;
            case R.id.inventory_detail:
                SKUCheckActivity.startActivity(this, mHelper.getCurrent().getGoodsName());
                return true;
            case R.id.barcode_collect:
                BarcodeCollectActivity.startActivity(this, mHelper.getCurrent().getGoodsName());
                return true;
            case R.id.document_detail:
                OrderDetailActivity.startActivity(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        OutputPickingItemEntity entity = mHelper.getCurrent();
        if (entity == null) {
            return;
        }
        if (entity.getStatus() == 1) {
            if (mHelper.hasNext()) {
                mHelper.next();
                return;
            }
        }
        mSubmitDialog.show();
    }

    private void complete() {
        OutputPickingItemEntity entity = mHelper.getCurrent();
        OutputPickingRequestEntity requestEntity = new OutputPickingRequestEntity();
        List<OutputPickingEntity> list = mAdapter.getItems();
        requestEntity.setList(list);
        if (mHelper.isLast()) {
            if (Validator.isEmpty(list)) {
                Toaster.showToast("请添加周转箱.");
                return;
            }
        }
        requestEntity.setId(mHelper.getData().getId());
        requestEntity.setDetailId(entity.getId());
        float number = Converter.toFloat(mPickCountView.getText().toString());
        if (number > entity.getOrderCount()) {
            Toaster.showToast("拣货数量不能大于订单数量.");
            return;
        }
        requestEntity.setNumber(number);
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.complete(requestEntity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                mHelper.addCount();
                mHelper.getCurrent().setStatus(1);
                if (mHelper.hasNext()) {
                    mHelper.next();
                } else {
                    clear();
                    mHelper.clear();
                    initialize();
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }

    public void clear() {
        mDocumentNoView.setValue("");
        mDeskNameView.setValue("");
        mClientNameView.setText("");
        mOrderRemark.setValue("");
        mOriginalType.setValue("");
        mProgressView.setValue("");
        mWarehouseView.setValue("");
        mClerkView.setValue("");
        mShopView.setValue("");
        mTotalMoneyView.setValue("");

        mNameView.setValue("");
        mSpecificationView.setValue("");
        mManufacturerView.setValue("");
        mUnitView.setValue("");
        mLotView.setValue("");
        mValidityView.setValue("");
        mProductionDateView.setValue("");
        mBarcodeView.setValue("");
        mUnitPriceView.setValue("");
        mMoneyView.setValue("");
        mOrderCountView.setValue("");
        mPickCountView.setText("");
        mLocationView.setValue("");
        mNextLocationView.setValue("");
        mRemarkView.setValue("");
        mAdapter.replaces(new ArrayList<>());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationAdd(OutputPickingEntity entity) {
        if (mSelectPosition != INVALID_POSITION) {
            mAdapter.update(entity, mSelectPosition);
        } else {
            mAdapter.append(entity);
        }
    }

    private void onItemClick(View view, int position) {
        mSelectPosition = position;
        OutputPickingAddActivity.startActivity(this, mAdapter.getItem(position));
    }

    public boolean onItemLongClick(View view, int position) {
        mDeletePosition = position;
        mDeleteDialog.show();
        return true;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (mDeletePosition != INVALID_POSITION) {
            mAdapter.remove(mDeletePosition);
        }
    }
}
