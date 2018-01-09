package com.teeny.wms.page.receiving;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.model.ReceivingLotEntity;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Converter;
import com.teeny.wms.util.Validator;
import com.teeny.wms.widget.KeyValueEditView;

import java.util.Locale;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see AcceptanceLotAddActivity
 * @since 2017/8/16
 */

public class AcceptanceLotAddActivity extends ToolbarActivity implements DatePickerDialog.OnDateSetListener {

    public static final String KEY_DATA = "data";
    public static final String KEY_FLAG = "flag";

    public static void startActivity(Context context, float rate, String zhUnit, String lhUnit) {
        ReceivingLotEntity entity = new ReceivingLotEntity();
        entity.setRate(rate);
        entity.setZhUnit(zhUnit);
        entity.setLhUnit(lhUnit);
        startActivity(context, entity, false);
    }

    public static void startActivity(Context context, ReceivingLotEntity entity) {
        startActivity(context, entity, true);
    }

    private static void startActivity(Context context, ReceivingLotEntity entity, boolean init) {
        Intent intent = new Intent();
        intent.setClass(context, AcceptanceLotAddActivity.class);
        intent.putExtra(KEY_DATA, entity);
        intent.putExtra(KEY_FLAG, init);
        context.startActivity(intent);
    }

    private static final String DATE_FORMAT = "%1$d-%2$d-%3$d";

    private EditText mLotNoEdit;
    private EditText mSerialNoEdit;
    private EditText mPriceEdit;
    private KeyValueEditView mAmountEdit;

    private TextInputLayout mLHLayout;
    private TextInputEditText mLHEdit;
    private TextInputLayout mZHLayout;
    private TextInputEditText mZHEdit;

    private ReceivingLotEntity mEntity;
    private TextView mDateTextView;
    private DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceptance_lot_add_layout);

        initView();
    }

    private void initView() {
        mLotNoEdit = (EditText) findViewById(R.id.lot_add_number);
        mSerialNoEdit = (EditText) findViewById(R.id.lot_add_serial_number);
        mPriceEdit = (EditText) findViewById(R.id.lot_add_price);
        mAmountEdit = (KeyValueEditView) findViewById(R.id.lot_add_amount);
        mDateTextView = (TextView) findViewById(R.id.lot_validity_date);

        mLHLayout = (TextInputLayout) findViewById(R.id.lot_add_lh_layout);
        mLHEdit = (TextInputEditText) findViewById(R.id.lot_add_lh_amount);
        mZHLayout = (TextInputLayout) findViewById(R.id.lot_add_zh_layout);
        mZHEdit = (TextInputEditText) findViewById(R.id.lot_add_zh_amount);

        Intent intent = getIntent();
        mEntity = intent.getParcelableExtra(KEY_DATA);
        if (intent.getBooleanExtra(KEY_FLAG, false)) {
            mLotNoEdit.setText(mEntity.getLotNo());
            mSerialNoEdit.setText(String.valueOf(mEntity.getSerialNo()));
            mPriceEdit.setText(String.valueOf(mEntity.getPrice()));
            mLHEdit.setText(String.valueOf(mEntity.getLhAmount()));
            mZHEdit.setText(String.valueOf(mEntity.getZhAmount()));
            mAmountEdit.setValue(String.valueOf(mEntity.getAmount()));
            mDateTextView.setText(mEntity.getValidityDate());
        }

        String lh = getResources().getString(R.string.text_lh_format);
        mLHLayout.setHint(String.format(lh, mEntity.getLhUnit()));
        String zh = getResources().getString(R.string.text_zh_format);
        mZHLayout.setHint(String.format(zh, mEntity.getZhUnit()));

        mLHEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float result;
                if (s.length() <= 0) {
                    result = 0;
                } else {
                    result = Converter.toFloat(s.toString());
                }
                mEntity.setLhAmount(result);
                mAmountEdit.setValue(String.valueOf(mEntity.getAmount()));
            }
        });

        mZHEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float result;
                if (s.length() <= 0) {
                    result = 0;
                } else {
                    result = Converter.toFloat(s.toString());
                }
                mEntity.setZhAmount(result);
                mAmountEdit.setValue(String.valueOf(mEntity.getAmount()));
            }
        });

        mDatePickerDialog = DialogFactory.createDatePickerDialog(this, this);
    }

    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.lot_select_date:
                showDateDialog();
                break;
            case R.id.lot_add_complete:
                onComplete();
                break;
        }
    }

    private void onComplete() {
        String number = mLotNoEdit.getText().toString();
        if (Validator.isEmpty(number)) {
            Toaster.showToast("请输入批号.");
            mLotNoEdit.requestFocus();
            return;
        }
        String serial = mSerialNoEdit.getText().toString();
        if (Validator.isEmpty(serial)) {
            Toaster.showToast("请输入序号.");
            mSerialNoEdit.requestFocus();
            return;
        }
        String price = mPriceEdit.getText().toString();
        if (mEntity.getAmount() <= 0) {
            Toaster.showToast("请输入整货数量或者零货数量.");
            mZHEdit.requestFocus();
            return;
        }
        String date = mDateTextView.getText().toString();
        if (mEntity == null) {
            mEntity = new ReceivingLotEntity();
        }
        mEntity.setLotNo(number);
        mEntity.setSerialNo(Converter.toInt(serial));
        mEntity.setPrice(Converter.toFloat(price));
        mEntity.setValidityDate(date);
        getEventBus().post(mEntity);
        finish();
    }

    private void showDateDialog() {
        mDatePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = String.format(Locale.getDefault(), DATE_FORMAT, year, month + 1, dayOfMonth);
        mDateTextView.setText(date);
    }
}
