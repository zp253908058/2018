package com.teeny.wms.page.picking.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.model.AllotLocationEntity;
import com.teeny.wms.model.OutputPickingEntity;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Converter;
import com.teeny.wms.util.Validator;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see OutputPickingAddActivity
 * @since 2018/1/20
 */

public class OutputPickingAddActivity extends ToolbarActivity {

    private static final String KEY_ENTITY = "entity";

    public static void startActivity(Context context) {
        startActivity(context, null);
    }

    public static void startActivity(Context context, OutputPickingEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, OutputPickingAddActivity.class);
        if (entity != null) {
            intent.putExtra(KEY_ENTITY, entity);
        }
        context.startActivity(intent);
    }

    private EditText mTurnoverBoxText;
    private EditText mAmountText;
    private OutputPickingEntity mEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output_picking_add_layout);

        mScannerHelper.openScanner(this, this::handleResult);
        initView();
    }

    private void handleResult(String msg) {
        mTurnoverBoxText.setText(msg);
    }

    @Override
    protected void onDestroy() {
        mScannerHelper.unregisterReceiver(this);
        super.onDestroy();
    }

    private void initView() {
        mTurnoverBoxText = (EditText) findViewById(R.id.output_picking_add_turnover_box);
        mAmountText = (EditText) findViewById(R.id.output_picking_add_picking_number);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_ENTITY)) {
            mEntity = intent.getParcelableExtra(KEY_ENTITY);
            mTurnoverBoxText.setText(mEntity.getTurnover());
            mAmountText.setText(String.valueOf(mEntity.getNumber()));
        } else {
            mEntity = new OutputPickingEntity();
        }
    }

    public void onClick(View view) {
        onComplete();
    }

    private void onComplete() {
        String turnover = mTurnoverBoxText.getText().toString();
        if (Validator.isEmpty(turnover)) {
            Toaster.showToast("请扫描或输入周转箱.");
            return;
        }
        String amount = mAmountText.getText().toString();
        if (Validator.isEmpty(amount)) {
            Toaster.showToast("请输入数量.");
            mAmountText.requestFocus();
            return;
        }
        mEntity.setTurnover(turnover);
        mEntity.setNumber(Converter.toInt(amount));
        getEventBus().post(mEntity);
        finish();
    }
}
