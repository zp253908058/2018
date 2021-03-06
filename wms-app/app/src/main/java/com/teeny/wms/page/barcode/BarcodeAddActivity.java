package com.teeny.wms.page.barcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ListView;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.BarcodeService;
import com.teeny.wms.model.BarcodeGoodsEntity;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.request.BarcodeAddRequestEntity;
import com.teeny.wms.page.common.adapter.GoodsChoiceAdapter;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
import com.teeny.wms.util.log.Logger;
import com.teeny.wms.widget.KeyValueEditView;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BarcodeAddActivity
 * @since 2018/1/6
 */

public class BarcodeAddActivity extends ToolbarActivity {

    private static final String KEY_ID = "id";
    private static final String KEY_OLD_BARCODE = "old_barcode";

    public static void startActivity(Context context) {
        startActivity(context, 0, null);
    }

    public static void startActivity(Context context, int id, String oldBarcode) {
        Intent intent = new Intent();
        intent.setClass(context, BarcodeAddActivity.class);
        if (id > 0) {
            intent.putExtra(KEY_ID, id);
        }
        if (Validator.isNotEmpty(oldBarcode)) {
            intent.putExtra(KEY_OLD_BARCODE, oldBarcode);
        }
        context.startActivity(intent);
    }

    private AppCompatEditText mBarcodeView;
    private AppCompatEditText mGoodsView;

    private KeyValueEditView mGoodsNameView;
    private KeyValueEditView mSpecificationView;
    private KeyValueEditView mManufacturerView;
    private KeyValueEditView mMakeAreaView;
    private KeyValueEditView mDosageFormView;
    private KeyValueEditView mUnitView;
    private KeyValueEditView mApprovalNumberView;

    private int mId;
    private String mOldBarcode;
    private BarcodeService mService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_add_layout);
        mScannerHelper.openScanner(this, this::handleResult);
        initView();
    }

    private void handleResult(String msg) {
        mBarcodeView.setText(msg);
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        super.onDestroy();
    }

    private void initView() {
        Intent intent = getIntent();
        mId = intent.getIntExtra(KEY_ID, 0);

        mOldBarcode = intent.getStringExtra(KEY_OLD_BARCODE);

        mBarcodeView = findViewById(R.id.barcode_add_barcode);
        mBarcodeView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                WindowUtils.hideInputSoft(v);
                complete();
                return true;
            }
            return false;
        });
        mGoodsView = findViewById(R.id.barcode_add_goods);
        mGoodsView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                WindowUtils.hideInputSoft(v);
                search();
                return true;
            }
            return false;
        });

        mGoodsNameView = findViewById(R.id.barcode_add_goods_name);
        mSpecificationView = findViewById(R.id.barcode_add_specification);
        mManufacturerView = findViewById(R.id.barcode_add_manufacturer);
        mMakeAreaView = findViewById(R.id.barcode_add_make_area);
        mDosageFormView = findViewById(R.id.barcode_add_dosage_form);
        mUnitView = findViewById(R.id.barcode_add_unit);
        mApprovalNumberView = findViewById(R.id.barcode_add_approval_number);

        mService = NetServiceManager.getInstance().getService(BarcodeService.class);


        if (mId > 0) {
            obtainData();
        }
    }

    public void onClick(View view) {
        complete();
    }

    private void complete() {
        if (mId <= 0) {
            Toaster.showToast("商品id错误,请重新添加.");
            return;
        }
        String barcode = mBarcodeView.getText().toString();
        if (Validator.isEmpty(barcode)) {
            Toaster.showToast("请输入条码.");
            return;
        }
        BarcodeAddRequestEntity entity = new BarcodeAddRequestEntity();
        entity.setId(mId);
        entity.setOldBarcode(mOldBarcode);
        entity.setNewBarcode(barcode);
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.add(entity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                Toaster.showToast("添加成功.");
            }

            @Override
            public void doComplete() {
                finish();
            }
        });
    }

    private void obtainData() {
        if (mId <= 0) {
            return;
        }
        Flowable<ResponseEntity<BarcodeGoodsEntity>> flowable = mService.getGoodsById(mId);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<BarcodeGoodsEntity>(this) {
            @Override
            public void doNext(BarcodeGoodsEntity data) {
                if (Validator.isNotNull(data)) {
                    setData(data);
                } else {
                    Toaster.showToast("该商品id无效.");
                }
            }

            @Override
            public void doComplete() {

            }
        });
    }

    private void search() {
        String goods = mGoodsView.getText().toString();
        if (Validator.isEmpty(goods)) {
            WindowUtils.hideInputSoft(mGoodsView);
            return;
        }
        Flowable<ResponseEntity<List<BarcodeGoodsEntity>>> flowable = mService.getGoodsList(goods);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<List<BarcodeGoodsEntity>>(this) {
            @Override
            @SuppressWarnings("unchecked")
            public void doNext(List<BarcodeGoodsEntity> data) {
                if (Validator.isEmpty(data)) {
                    Toaster.showToast("搜索内容不存在.");
                    return;
                }
                if (data.size() > 1) {
                    final GoodsChoiceAdapter adapter = new GoodsChoiceAdapter(data);
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle_NoTitle);
                    ListView listView = (ListView) LayoutInflater.from(getContext()).inflate(R.layout.common_list_view, null);
                    listView.setAdapter(adapter);
                    builder.setView(listView);
                    AlertDialog dialog = builder.create();
                    listView.setOnItemClickListener((parent, view, position, id) -> {
                        setData((BarcodeGoodsEntity) adapter.getItem(position));
                        dialog.dismiss();
                    });
                    dialog.show();
                    return;
                }
                setData(data.get(0));
            }

            @Override
            public void doComplete() {

            }
        });
    }

    private void setData(BarcodeGoodsEntity data) {
        mId = data.getId();
        mOldBarcode = data.getBarcode();
        mGoodsNameView.setValue(data.getGoodsName());
        mSpecificationView.setValue(data.getSpecification());
        mMakeAreaView.setValue(data.getMakeArea());
        mDosageFormView.setValue(data.getDosageForm());
        mApprovalNumberView.setValue(data.getApprovalNumber());
        mUnitView.setValue(data.getUnit());
        mManufacturerView.setValue(data.getManufacturers());

    }
}
