package com.teeny.wms.page.delivery.activity;

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
import com.teeny.wms.datasouce.net.service.ShopDeliveryService;
import com.teeny.wms.model.EmptyEntity;
import com.teeny.wms.model.ResponseEntity;
import com.teeny.wms.model.ShopDeliveryGoodsEntity;
import com.teeny.wms.page.allot.fragment.AllotGoodsSelectedFragment;
import com.teeny.wms.page.delivery.fragment.ShopDeliveryHeaderFragment;
import com.teeny.wms.page.delivery.helper.ShopDeliveryHelper;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Converter;
import com.teeny.wms.util.Validator;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ShopDeliveryEditActivity
 * @since 2018/1/18
 */

public class ShopDeliveryEditActivity extends ToolbarActivity {

    private static final String KEY_ENTITY = "entity";

    public static void startActivity(Context context, ShopDeliveryGoodsEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, ShopDeliveryEditActivity.class);
        intent.putExtra(KEY_ENTITY, entity);
        context.startActivity(intent);
    }

    private ShopDeliveryGoodsEntity mEntity;

    private EditText mAmountTextView;
    private EditText mRemarkTextView;

    private ShopDeliveryService mService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_delivery_edit_layout);

        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        mEntity = intent.getParcelableExtra(KEY_ENTITY);

        TextView name = (TextView) findViewById(R.id.shop_delivery_edit_name);
        TextView specification = (TextView) findViewById(R.id.shop_delivery_edit_specification);
        TextView manufacturer = (TextView) findViewById(R.id.shop_delivery_edit_manufacturer);
        TextView unit = (TextView) findViewById(R.id.shop_delivery_edit_unit);
        TextView lot = (TextView) findViewById(R.id.shop_delivery_edit_lot);
        TextView validity = (TextView) findViewById(R.id.shop_delivery_edit_validity);
        TextView deliveryNumber = (TextView) findViewById(R.id.shop_delivery_edit_delivery_number);

        name.setText(mEntity.getGoodsName());
        specification.setText(mEntity.getSpecification());
        manufacturer.setText(mEntity.getManufacturer());
        unit.setText(mEntity.getUnit());
        lot.setText(mEntity.getLotNumber());
        validity.setText(mEntity.getValidateDate());
        deliveryNumber.setText(String.valueOf(mEntity.getDeliveryNumber()));

        mAmountTextView = (EditText) findViewById(R.id.shop_delivery_edit_receiving_number);
        mAmountTextView.setText(String.valueOf(mEntity.getReceivingNumber()));
        mRemarkTextView = (EditText) findViewById(R.id.shop_delivery_edit_remark);

        mService = NetServiceManager.getInstance().getService(ShopDeliveryService.class);
    }

    public void onClick(View view) {
        complete();
    }

    private void complete() {
        if (mEntity == null) {
            return;
        }
        float amount = Converter.toFloat(mAmountTextView.getText().toString());
        if (amount > mEntity.getDeliveryNumber()) {
            Toaster.showToast("收货数量不能大于配送数量.");
            return;
        }
        String remark = null;
        if (amount != mEntity.getDeliveryNumber()) {
            remark = mRemarkTextView.getText().toString();
            if (Validator.isEmpty(remark)) {
                Toaster.showToast("数量不一致,请填写差异备注.");
            }
        }
        Flowable<ResponseEntity<EmptyEntity>> flowable = mService.single(mEntity.getId(), amount, remark, mEntity.getBillId());
        flowable.observeOn(AndroidSchedulers.mainThread()).subscribe(new ResponseSubscriber<EmptyEntity>(this) {
            @Override
            public void doNext(EmptyEntity data) {
                Toaster.showToast("已完成.");
            }

            @Override
            public void doComplete() {
                getEventBus().post(new ShopDeliveryHeaderFragment.DataChangedObserver());
                finish();
            }
        });
    }
}
