package com.teeny.wms.page.allot;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.model.AllotLocationEntity;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Converter;
import com.teeny.wms.util.Validator;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AllotOrderLocationAddActivity
 * @since 2018/1/18
 */

public class AllotOrderLocationAddActivity extends ToolbarActivity {

    private static final String KEY_ENTITY = "entity";

    public static void startActivity(Context context) {
        startActivity(context, null);
    }

    public static void startActivity(Context context, AllotLocationEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, AllotOrderLocationAddActivity.class);
        if (entity != null) {
            intent.putExtra(KEY_ENTITY, entity);
        }
        context.startActivity(intent);
    }

    private EditText mLocationText;
    private EditText mAmountEdit;
    private AllotLocationEntity mEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allot_order_location_add_layout);
        mScannerHelper.openScanner(this, this::handleResult);
        initView();
    }

    private void handleResult(String msg) {
        mLocationText.setText(msg);
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        super.onDestroy();
    }


    private void initView() {
        mLocationText = (EditText) findViewById(R.id.allot_order_location_add_name);
        mAmountEdit = (EditText) findViewById(R.id.allot_order_location_add_amount);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_ENTITY)) {
            mEntity = intent.getParcelableExtra(KEY_ENTITY);
            mLocationText.setText(mEntity.getLocationCode());
            mAmountEdit.setText(String.valueOf(mEntity.getAmount()));
        } else {
            mEntity = new AllotLocationEntity();
        }
    }

    public void onClick(View view) {
        onComplete();
    }

    private void onComplete() {
        String locationCode = mLocationText.getText().toString();
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
