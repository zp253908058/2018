package com.teeny.wms.page.sku;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.datasouce.net.NetServiceManager;
import com.teeny.wms.datasouce.net.ResponseSubscriber;
import com.teeny.wms.datasouce.net.service.SKUService;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.SKUEntity;
import com.teeny.wms.model.request.SKUAddRequestEntity;
import com.teeny.wms.util.Converter;
import com.teeny.wms.widget.KeyValueEditView;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see SKUDetailActivity
 * @since 2017/7/31
 */

public class SKUDetailActivity extends ToolbarActivity {

    private static final String KEY_DATA = "data";

    public static void startActivity(Context context, SKUEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, SKUDetailActivity.class);
        intent.putExtra(KEY_DATA, entity);
        context.startActivity(intent);
    }

    private SKUEntity mEntity;
    private EditText mQuantityView;
    private SKUCheckActivity.DataChangedFlag mChangedFlag = new SKUCheckActivity.DataChangedFlag();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sku_detail_layout);
        initView();
    }

    private void initView() {
        mEntity = getIntent().getParcelableExtra(KEY_DATA);

        KeyValueEditView name = (KeyValueEditView) findViewById(R.id.sku_detail_name);
        name.setValue(mEntity.getGoodsName());
        KeyValueEditView number = (KeyValueEditView) findViewById(R.id.sku_detail_number);
        number.setValue(mEntity.getNumber());
        KeyValueEditView barcode = (KeyValueEditView) findViewById(R.id.sku_detail_barcode);
        barcode.setValue(mEntity.getBarcode());
        KeyValueEditView costPrice = (KeyValueEditView) findViewById(R.id.sku_detail_cost_price);
        costPrice.setValue(String.valueOf(mEntity.getCostPrice()));
        KeyValueEditView costTotal = (KeyValueEditView) findViewById(R.id.sku_detail_cost_total);
        costTotal.setValue(String.valueOf(mEntity.getCostTotal()));
        KeyValueEditView lot = (KeyValueEditView) findViewById(R.id.sku_detail_lot_number);
        lot.setValue(mEntity.getLotNo());
        KeyValueEditView allocation = (KeyValueEditView) findViewById(R.id.sku_detail_allocation);
        allocation.setValue(mEntity.getLocationName());
        TextView repertoryQuantity = (TextView) findViewById(R.id.sku_detail_repertory_quantity);
        repertoryQuantity.setText(String.valueOf(mEntity.getQuantity()));
        KeyValueEditView specification = (KeyValueEditView) findViewById(R.id.sku_detail_specification);
        specification.setValue(mEntity.getSpecification());
        KeyValueEditView validityDate = (KeyValueEditView) findViewById(R.id.sku_detail_validity_date);
        validityDate.setValue(mEntity.getValidateDate());
        KeyValueEditView unit = (KeyValueEditView) findViewById(R.id.sku_detail_unit);
        unit.setValue(mEntity.getUnit());
        KeyValueEditView manufacturer = (KeyValueEditView) findViewById(R.id.sku_detail_manufacturer);
        manufacturer.setValue(mEntity.getManufacturer());
        KeyValueEditView productionDate = (KeyValueEditView) findViewById(R.id.sku_detail_production_date);
        productionDate.setValue(mEntity.getProductDate());
        KeyValueEditView productionPlace = (KeyValueEditView) findViewById(R.id.sku_detail_production_place);
        productionPlace.setValue(mEntity.getProductionPlace());
        mQuantityView = (EditText) findViewById(R.id.sku_detail_real_quantity);
        mQuantityView.setText(String.valueOf(mEntity.getQuantity()));
    }

    public void onClick(View view) {
        int quantity = Converter.toInt(mQuantityView.getText().toString(), -1);
        if (quantity < 0 || quantity == mEntity.getQuantity()) {
            finish();
            return;
        }
        mChangedFlag.amount = quantity;
        SKUService service = NetServiceManager.getInstance().getService(SKUService.class);
        SKUAddRequestEntity entity = new SKUAddRequestEntity();
        entity.setId(mEntity.getId());
        entity.setpId(mEntity.getGoodsId());
        entity.setAmount(quantity);
        entity.setOriginalAmount(mEntity.getQuantity());
        entity.setLocationId(mEntity.getLocationId());
        entity.setValidateDate(mEntity.getValidateDate());
        entity.setLotNo(mEntity.getLotNo());
        Flowable<ResponseEntity<EmptyEntity>> flowable = service.save(entity);
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                getEventBus().post(mChangedFlag);
            }

            @Override
            public void doComplete() {
                finish();
            }
        });
    }
}
