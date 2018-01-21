package com.teeny.wms.page.picking.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.RecyclerViewTouchListener;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.base.decoration.VerticalDecoration;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.PickingService;
import com.teeny.wms.model.AllotLocationEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.OutputPickingEntity;
import com.teeny.wms.model.OutputPickingItemEntity;
import com.teeny.wms.model.OutputPickingOrderEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.OutputPickingRequestEntity;
import com.teeny.wms.page.allot.AllotOrderAddActivity;
import com.teeny.wms.page.barcode.BarcodeCollectActivity;
import com.teeny.wms.page.common.adapter.SimpleAdapter;
import com.teeny.wms.page.picking.adapter.OutputPickingAdapter;
import com.teeny.wms.page.picking.helper.OutputPickingHelper;
import com.teeny.wms.page.sku.SKUCheckActivity;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
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

    private AutoCompleteTextView mAutoCompleteTextView;
    private SimpleAdapter<KeyValueEntity> mOrderAdapter;
    private KeyValueEntity mSelectedOrder;

    private KeyValueTextView mLocationView;
    private KeyValueTextView mProgressView;
    private KeyValueTextView mNextLocationView;
    private KeyValueTextView mWarehouseView;
    private KeyValueTextView mClerkView;         //业务员
    private KeyValueTextView mShopView;
    private KeyValueTextView mTotalMoneyView;

    private TextView mNameView;
    private TextView mSpecificationView;
    private TextView mManufacturerView;
    private TextView mUnitView;
    private TextView mLotView;
    private TextView mValidityView;
    private TextView mProductionDateView;
    private TextView mBarcodeView;
    private TextView mUnitPriceView;
    private TextView mMoneyView;
    private TextView mOrderCountView;

    private OutputPickingAdapter mAdapter;

    private int mSelectPosition = INVALID_POSITION;
    private int mDeletePosition = INVALID_POSITION;

    private AlertDialog mDeleteDialog;

    private PickingService mService;

    private OutputPickingHelper mHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_picking_layout);
        showNavigationIcon(true);
        initView();
        registerEventBus();
        mScannerHelper.openScanner(this, this::handleResult);
    }

    private void handleResult(String result) {
        mAutoCompleteTextView.setText(result);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterEventBus();
        mScannerHelper.unregisterReceiver(this);
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
        mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.output_picking_scan);
        mAutoCompleteTextView.setOnItemClickListener((parent, view, position, id) -> {
            KeyValueEntity entity = mOrderAdapter.getItem(position);
            if (mSelectedOrder == entity) {
                return;
            }
            mSelectedOrder = entity;
            if (mSelectedOrder == null) {
                return;
            }
            obtainData();
        });
        mOrderAdapter = new SimpleAdapter<>(getContext());
        mAutoCompleteTextView.setAdapter(mOrderAdapter);
        mAutoCompleteTextView.setOnClickListener(v -> {
            if (!mAutoCompleteTextView.isPopupShowing()) {
                mAutoCompleteTextView.showDropDown();
            }
        });

        mLocationView = (KeyValueTextView) findViewById(R.id.output_picking_location);
        mProgressView = (KeyValueTextView) findViewById(R.id.output_picking_progress);
        mNextLocationView = (KeyValueTextView) findViewById(R.id.output_picking_next_location);
        mWarehouseView = (KeyValueTextView) findViewById(R.id.output_picking_warehouse);
        mClerkView = (KeyValueTextView) findViewById(R.id.output_picking_clerk);
        mShopView = (KeyValueTextView) findViewById(R.id.output_picking_shop);
        mTotalMoneyView = (KeyValueTextView) findViewById(R.id.output_picking_total_money);

        mNameView = (TextView) findViewById(R.id.output_picking_name);
        mSpecificationView = (TextView) findViewById(R.id.output_picking_specification);
        mManufacturerView = (TextView) findViewById(R.id.output_picking_manufacturer);
        mUnitView = (TextView) findViewById(R.id.output_picking_unit);
        mLotView = (TextView) findViewById(R.id.output_picking_lot);
        mValidityView = (TextView) findViewById(R.id.output_picking_validity);
        mProductionDateView = (TextView) findViewById(R.id.output_picking_production_date);
        mBarcodeView = (TextView) findViewById(R.id.output_picking_barcode);
        mUnitPriceView = (TextView) findViewById(R.id.output_picking_unit_price);
        mMoneyView = (TextView) findViewById(R.id.output_picking_money);
        mOrderCountView = (TextView) findViewById(R.id.output_picking_order_count);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new OutputPickingAdapter(new ArrayList<>());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this.getContext());
        manager.setSmoothScrollbarEnabled(true);
        manager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(manager);
        VerticalDecoration decoration = new VerticalDecoration(this.getContext());
        decoration.setHeight(this.getContext().getResources().getDimensionPixelSize(R.dimen.dp_16));
        decoration.setNeedDraw(false);
        recyclerView.addItemDecoration(decoration);
        RecyclerViewTouchListener listener = new RecyclerViewTouchListener(this, this::onItemClick);
        listener.setOnItemLongClickListener(this::onItemLongClick);
        recyclerView.addOnItemTouchListener(listener);

        mDeleteDialog = DialogFactory.createAlertDialog(this, getString(R.string.prompt_delete_confirm), this);
        mDeleteDialog.setOnDismissListener(dialog -> mDeletePosition = INVALID_POSITION);

        mService = NetServiceManager.getInstance().getService(PickingService.class);
        mHelper = OutputPickingHelper.getInstance();
        obtainOrders();
    }

    private void obtainOrders() {
        if (mSelectedOrder == null) {
            Toaster.showToast("请选择订单.");
            return;
        }
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getOrderList();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("未获取到仓库.");
                } else {
                    mOrderAdapter.clear();
                    mOrderAdapter.addAll(data);
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }

    private void obtainData() {
        Flowable<ResponseEntity<OutputPickingOrderEntity>> flowable = mService.getList(mSelectedOrder.getKey());
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
        mProgressView.setValue(entity.getProgress());
        mWarehouseView.setValue(entity.getWarehouse());
        mClerkView.setValue(entity.getClerk());
        mShopView.setValue(entity.getShopName());
        mTotalMoneyView.setValue(String.valueOf(entity.getTotalMoney()));
        if (mHelper.hasNext()) {
            mHelper.next();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void get(OutputPickingItemEntity entity) {
        mNameView.setText(entity.getGoodsName());
        mSpecificationView.setText(entity.getSpecification());
        mManufacturerView.setText(entity.getManufacturer());
        mUnitView.setText(entity.getUnit());
        mLotView.setText(entity.getLot());
        mValidityView.setText(entity.getValidate());
        mProductionDateView.setText(entity.getProductionDate());
        mBarcodeView.setText(entity.getGoodsBarcode());
        mUnitPriceView.setText(String.valueOf(entity.getUnitPrice()));
        mMoneyView.setText(String.valueOf(entity.getMoney()));
        mOrderCountView.setText(String.valueOf(entity.getOrderCount()));
        mLocationView.setValue(entity.getLocation());
        mNextLocationView.setValue(mHelper.getNextLocation());
        mAdapter.setItems(entity.getList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_output_picking, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
            case R.id.work_task:
                TaskActivity.startActivity(this);
                return true;
            case R.id.document_detail:
                OrderDetailActivity.startActivity(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        complete();
    }

    private void complete() {
        OutputPickingItemEntity entity = mHelper.getCurrent();
        if (entity == null) {
            return;
        }
        List<OutputPickingEntity> list = mAdapter.getItems();
        if (Validator.isEmpty(list)) {
            Toaster.showToast("请添加周转箱和数量.");
            return;
        }
        int count = 0;
        for (OutputPickingEntity item : list) {
            count += item.getNumber();
        }
        if (count > entity.getOrderCount()) {
            Toaster.showToast("拣货总数不能大于订单数量.");
            return;
        }
        OutputPickingRequestEntity requestEntity = new OutputPickingRequestEntity();
        requestEntity.setId(entity.getId());
        requestEntity.setList(list);
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.complete(requestEntity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                OutputPickingItemEntity entity = mHelper.getCurrent();
                List<OutputPickingEntity> list = mAdapter.getItems();
                entity.setList(list);
                mHelper.addCount();
                if (mHelper.hasNext()) {
                    mHelper.next();
                } else {
                    mOrderAdapter.remove(mSelectedOrder);
                    mSelectedOrder = null;
                    clear();
                    mHelper.clear();
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }

    public void clear() {
        mProgressView.setValue("/");
        mWarehouseView.setValue("");
        mClerkView.setValue("");
        mShopView.setValue("");
        mTotalMoneyView.setValue("");

        mNameView.setText("");
        mSpecificationView.setText("");
        mManufacturerView.setText("");
        mUnitView.setText("");
        mLotView.setText("");
        mValidityView.setText("");
        mProductionDateView.setText("");
        mBarcodeView.setText("");
        mUnitPriceView.setText("");
        mMoneyView.setText("");
        mOrderCountView.setText("");
        mLocationView.setValue("");
        mNextLocationView.setValue("");
        mAdapter.removes();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocationAdd(OutputPickingEntity entity) {
        if (mSelectPosition != INVALID_POSITION) {
            mAdapter.update(entity, mSelectPosition);
        } else {
            mAdapter.append(entity);
        }
        mAdapter.notifyDataSetChanged();
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
