package com.teeny.wms.page.review;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.ReviewService;
import com.teeny.wms.model.DocumentEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ExWarehouseReviewEntity;
import com.teeny.wms.model.KeyValueEntity;
import com.teeny.wms.model.RecipientEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.ExWarehouseReviewRequestEntity;
import com.teeny.wms.page.common.adapter.KeyValueListAdapter;
import com.teeny.wms.page.document.controller.DocumentHelper;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
import com.teeny.wms.widget.KeyValueTextView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:出库复核
 *
 * @author zp
 * @version 1.0
 * @see ExWarehouseReviewActivity
 * @since 2017/7/16
 */

public class ExWarehouseReviewActivity extends ToolbarActivity {
    private static final String KEY_ENTITY = "entity";

    public static void startActivity(Context context, DocumentEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, ExWarehouseReviewActivity.class);
        if (Validator.isNotNull(entity)) {
            intent.putExtra(KEY_ENTITY, entity);
        }
        context.startActivity(intent);
    }

    private static final int INVALID_ID = -1;

    private AutoCompleteTextView mScanView;
    private KeyValueListAdapter mOrderAdapter;
    private int mSelectOrderId = INVALID_ID;

    private KeyValueTextView mBillView;
    private KeyValueTextView mPriorityView;
    private KeyValueTextView mStagingAreaView;
    private KeyValueTextView mCustomerView;
    private KeyValueTextView mStatusView;
    private KeyValueTextView mDocumentStatusView;
    private KeyValueTextView mDistributionRoadView;
    private KeyValueTextView mDocumentRemarkView;
    private KeyValueTextView mReplenishmentOrderView;
    private KeyValueTextView mIntactGoodsNumberView;
    private KeyValueTextView mLCLView;
    private KeyValueTextView mPackageView;

    private AlertDialog mOptionDialog;
    private TextView mRemarkTextView;
    private TextView mRecipientTextView;
    private RecipientEntity mRecipient;

    private ReviewService mService;

    private DocumentEntity mDocumentEntity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ex_warehouse_review_layout);

        mScannerHelper.openScanner(this, this::handleResult);
        registerEventBus();
        initView();
    }

    private void handleResult(String msg) {
        mScanView.setText(msg);
        obtainData(msg);
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        unregisterEventBus();
        super.onDestroy();
    }

    private void initView() {
        mScanView = (AutoCompleteTextView) findViewById(R.id.ex_warehouse_review_scan_click);
        mScanView.setOnItemClickListener(this::onOrderNoClick);
        mOrderAdapter = new KeyValueListAdapter(null);
        mScanView.setAdapter(mOrderAdapter);
        mScanView.setOnClickListener(v -> {
            if (!mScanView.isPopupShowing()) {
                mScanView.showDropDown();
            }
        });
        mScanView.setOnFocusChangeListener(this::onFocusChange);

        mBillView = findViewById(R.id.ex_warehouse_review_sale_order_number);
        mPriorityView = findViewById(R.id.ex_warehouse_review_priority);
        mStagingAreaView = findViewById(R.id.ex_warehouse_review_staging_area);
        mCustomerView = findViewById(R.id.ex_warehouse_review_customer);
        mStatusView = findViewById(R.id.ex_warehouse_review_status);
        mDocumentStatusView = findViewById(R.id.ex_warehouse_review_document_status);
        mDistributionRoadView = findViewById(R.id.ex_warehouse_review_distribution_road);
        mDocumentRemarkView = findViewById(R.id.ex_warehouse_review_document_remark);
        mReplenishmentOrderView = findViewById(R.id.ex_warehouse_review_replenishment_order);
        mIntactGoodsNumberView = findViewById(R.id.ex_warehouse_review_intact_goods_number);
        mLCLView = findViewById(R.id.ex_warehouse_review_lcl_count);
        mPackageView = findViewById(R.id.ex_warehouse_review_package_count);

        mRemarkTextView = findViewById(R.id.ex_warehouse_review_diff_remark);
        mRecipientTextView = findViewById(R.id.ex_warehouse_review_recipient);

        mService = NetServiceManager.getInstance().getService(ReviewService.class);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_ENTITY)) {
            mDocumentEntity = getIntent().getParcelableExtra(KEY_ENTITY);
        }

        obtainReplenishmentCount();
    }

    private void obtainReplenishmentCount() {
        Flowable<ResponseEntity<Integer>> flowable = mService.getReplenishmentCount();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<Integer>(this) {
            @Override
            public void doNext(Integer data) {
                if (data != null) {
                    mReplenishmentOrderView.setValue(String.valueOf(data));
                }
            }

            @Override
            public void doComplete() {
                obtainBills();
            }
        });
    }

    private void onOrderNoClick(AdapterView<?> parent, View view, int position, long id) {
        KeyValueEntity entity = mOrderAdapter.getItem(position);
        if (mSelectOrderId != entity.getKey()) {
            mSelectOrderId = entity.getKey();
            mScanView.setSelection(entity.getValue().length());
            obtainData(entity.getValue());
            WindowUtils.hideInputSoft(view);
        }
    }

    private void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            mScanView.dismissDropDown();
            return;
        }
        mScanView.showDropDown();
    }

    private void obtainBills() {
        Flowable<ResponseEntity<List<KeyValueEntity>>> flowable = mService.getBillList();
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<KeyValueEntity>>(this) {
            @Override
            public void doNext(List<KeyValueEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("没有未完成的复核单.");
                }
                mOrderAdapter.setItems(data);
            }

            @Override
            public void doComplete() {
                if (mDocumentEntity != null) {
                    mScanView.setText(mDocumentEntity.getNumber());
                    obtainData(mDocumentEntity.getNumber());
                }
            }
        });
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.ex_warehouse_review_recipient_click:
                RecipientActivity.startActivity(this);
                break;
            case R.id.ex_warehouse_review_diff_remark_click:
                onRemarkClick();
                break;
            case R.id.ex_warehouse_review_complete:
                complete();
                break;
        }
    }

    private void obtainData(String code) {
        Flowable<ResponseEntity<ExWarehouseReviewEntity>> flowable = mService.detail(code);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<ExWarehouseReviewEntity>(this) {
            @Override
            public void doNext(ExWarehouseReviewEntity data) {
                mBillView.setValue(data.getBillNo());
                mPriorityView.setValue(data.getPriority());
                mStagingAreaView.setValue(data.getTempArea());
                mCustomerView.setValue(data.getCustomer());
                mStatusView.setValue(data.getStatusString());
                mDocumentStatusView.setValue(String.valueOf(data.getDocumentStatus()));
                mDistributionRoadView.setValue(data.getDeliveryLine());
                mDocumentRemarkView.setValue(data.getBillRemark());

                mIntactGoodsNumberView.setKey(String.valueOf(data.getZhQuantity()));
                mIntactGoodsNumberView.setValue(String.valueOf(data.getZhQuantityTotal()));
                if (data.getZhQuantity() == data.getZhQuantityTotal()) {
                    mIntactGoodsNumberView.setKeyColor(ResourcesCompat.getColor(getResources(), R.color.text_value, null));
                } else {
                    mIntactGoodsNumberView.setKeyColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                }
                mLCLView.setKey(String.valueOf(data.getPxQuantity()));
                mLCLView.setValue(String.valueOf(data.getPxQuantityTotal()));
                if (data.getPxQuantity() == data.getPxQuantityTotal()) {
                    mLCLView.setKeyColor(ResourcesCompat.getColor(getResources(), R.color.text_value, null));
                } else {
                    mLCLView.setKeyColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                }
                mPackageView.setKey(String.valueOf(data.getDbQuantity()));
                mPackageView.setValue(String.valueOf(data.getDbQuantityTotal()));
                if (data.getDbQuantity() == data.getDbQuantityTotal()) {
                    mPackageView.setKeyColor(ResourcesCompat.getColor(getResources(), R.color.text_value, null));
                } else {
                    mPackageView.setKeyColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimaryDark, null));
                }
            }

            @Override
            public void doComplete() {
                obtainBills();
            }
        });
    }

    private void onRemarkClick() {
        if (mOptionDialog == null) {
            mOptionDialog = DialogFactory.createSingleChoiceDialog(this, getString(R.string.text_select_diff), getResources().getStringArray(R.array.option_array_1), (dialog, which) -> {
                mRemarkTextView.setText(mOptionDialog.getListView().getAdapter().getItem(which).toString());
                dialog.dismiss();
            });
        }
        mOptionDialog.show();
    }

    private void complete() {
        String billId = mBillView.getValue();
        if (Validator.isEmpty(billId)) {
            Toaster.showToast("请先扫描单号.");
            return;
        }
        if (Validator.isNull(mRecipient)) {
            Toaster.showToast("请先选择接收人.");
            return;
        }
        String remark = mRemarkTextView.getText().toString();
        if (Validator.isEmpty(remark)) {
            Toaster.showToast("请先选择差异.");
            return;
        }
        ExWarehouseReviewRequestEntity entity = new ExWarehouseReviewRequestEntity();
        entity.setBillNo(billId);
        entity.setRecipientId(mRecipient.getId());
        entity.setRemark(remark);

        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.complete(entity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
            }

            @Override
            public void doComplete() {
                DocumentHelper.getInstance().notifyDocumentChanged();
                clear();
                obtainReplenishmentCount();
            }
        });
    }

    private void clear() {
        mBillView.setValue("");
        mPriorityView.setValue("");
        mStagingAreaView.setValue("");
        mCustomerView.setValue("");
        mStatusView.setValue("");
        mDocumentStatusView.setValue("");
        mDistributionRoadView.setValue("");
        mDocumentRemarkView.setValue("");

        mIntactGoodsNumberView.setKey("");
        mIntactGoodsNumberView.setValue("");
        mLCLView.setKey("");
        mLCLView.setValue("");
        mPackageView.setKey("");
        mPackageView.setValue("");
        mScanView.setText("");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRecipiSelected(RecipientEntity entity) {
        mRecipient = entity;
        mRecipientTextView.setText(mRecipient.getName());
    }
}
