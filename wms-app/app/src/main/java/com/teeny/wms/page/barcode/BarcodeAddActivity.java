package com.teeny.wms.page.barcode;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.SKUGoodsDetailEntity;
import com.teeny.wms.model.request.BarcodeAddRequestEntity;
import com.teeny.wms.page.common.adapter.GoodsChoiceAdapter;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Validator;
import com.teeny.wms.util.WindowUtils;
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

    public static void startActivity(Context context) {
        startActivity(context, 0);
    }

    public static void startActivity(Context context, int id) {
        Intent intent = new Intent();
        intent.setClass(context, BarcodeAddActivity.class);
        if (id > 0) {
            intent.putExtra(KEY_ID, id);
        }
        context.startActivity(intent);
    }

    private AppCompatEditText mBarcodeView;
    private AppCompatEditText mGoodsView;

    private KeyValueEditView mGoodsNameView;
    private KeyValueEditView mManufacturerView;
    private KeyValueEditView mMakeAreaView;
    private KeyValueEditView mDosageFormView;
    private KeyValueEditView mUnitView;
    private KeyValueEditView mApprovalNumberView;

    private int mId;
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
        mId = getIntent().getIntExtra(KEY_ID, 0);

        mBarcodeView = (AppCompatEditText) findViewById(R.id.barcode_add_barcode);
        mGoodsView = (AppCompatEditText) findViewById(R.id.barcode_add_goods);
        mGoodsView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                WindowUtils.hideInputSoft(v);
                search();
                return true;
            }
            return false;
        });

        mGoodsNameView = (KeyValueEditView) findViewById(R.id.barcode_add_goods_name);
        mManufacturerView = (KeyValueEditView) findViewById(R.id.barcode_add_manufacturer);
        mMakeAreaView = (KeyValueEditView) findViewById(R.id.barcode_add_make_area);
        mDosageFormView = (KeyValueEditView) findViewById(R.id.barcode_add_dosage_form);
        mUnitView = (KeyValueEditView) findViewById(R.id.barcode_add_unit);
        mApprovalNumberView = (KeyValueEditView) findViewById(R.id.barcode_add_approval_number);

        mService = NetServiceManager.getInstance().getService(BarcodeService.class);

        if (mId > 0) {
            obtainData();
        }
    }

    public void onClick(View view) {
        complete();
    }

    private void complete() {
        BarcodeAddRequestEntity entity = new BarcodeAddRequestEntity();
//        entity.setId(mId);
//        entity.setAmount(Converter.toInt(amount));
//        entity.setOriginalAmount(Converter.toInt(amount));
//        entity.setLocationCode(allocation);
//        entity.setValidateDate(date);
//        entity.setLotNo(lot);
//        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.add(entity);
//        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
//            @Override
//            public void doNext(EmptyEntity data) {
//                getEventBus().post(new SKUCheckActivity.DataAddedFlag());
//            }
//
//            @Override
//            public void doComplete() {
//                finish();
//            }
//        });
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
                    Toaster.showToast("该商品码不存在.");
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
                if (Validator.isNotNull(data)) {
                    if (data.size() > 1) {
                        final GoodsChoiceAdapter adapter = new GoodsChoiceAdapter(data);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle_NoTitle);
                        ListView listView = (ListView) LayoutInflater.from(getContext()).inflate(R.layout.common_list_view, null);
                        listView.setAdapter(adapter);
                        builder.setView(listView);
                        AlertDialog dialog = builder.create();
                        listView.setOnItemClickListener((parent, view, position, id) -> {
                            setData((SKUGoodsDetailEntity) adapter.getItem(position));
                            dialog.dismiss();
                        });
                        dialog.show();
                        return;
                    }
                    setData(data.get(0));
                } else {
                    Toaster.showToast("该商品码不存在.");
                }
            }

            @Override
            public void doComplete() {

            }
        });
//        if (data.size() > 1) {
//            final GoodsChoiceAdapter adapter = new GoodsChoiceAdapter(data);
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogStyle_NoTitle);
//            ListView listView = (ListView) LayoutInflater.from(getContext()).inflate(R.layout.common_list_view, null);
//            listView.setAdapter(adapter);
//            builder.setView(listView);
//            AlertDialog dialog = builder.create();
//            listView.setOnItemClickListener((parent, view, position, id) -> {
//                setData((SKUGoodsDetailEntity) adapter.getItem(position));
//                dialog.dismiss();
//            });
//            dialog.show();
//            return;
//        }
//        setData(data.get(0));
    }

    private void setData(BarcodeGoodsEntity data) {
//        mNameView.setValue(data.getGoodsName());
//        mNumberView.setValue(data.getNumber());
//        mSpecificationView.setValue(data.getStandard());
//        mUnitView.setValue(data.getUnit());
//        mManufacturerView.setValue(data.getManufacturers());
//        mId = data.getpId();
    }
}
