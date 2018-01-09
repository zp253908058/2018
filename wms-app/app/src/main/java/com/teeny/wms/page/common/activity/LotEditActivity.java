package com.teeny.wms.page.common.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.teeny.wms.R;
import com.teeny.wms.base.ToolbarActivity;
import com.teeny.wms.model.LotEntity;
import com.teeny.wms.pop.DialogFactory;
import com.teeny.wms.pop.Toaster;
import com.teeny.wms.util.Converter;
import com.teeny.wms.util.Validator;

import java.util.Locale;

/**
 * Class description: 批次修改 或 增加页面
 *
 * @author zp
 * @version 1.0
 * @see LotEditActivity
 * @since 2017/8/23
 */

public class LotEditActivity extends ToolbarActivity implements DatePickerDialog.OnDateSetListener {

    public static final String KEY_DATA = "data";

    public static void startActivityForResult(Activity context, int requestCode) {
        startActivityForResult(context, requestCode, null);
    }

    public static void startActivityForResult(Activity context, int requestCode, LotEntity entity) {
        Intent intent = new Intent();
        intent.setClass(context, LotEditActivity.class);
        if (Validator.isNotNull(entity)) {
            intent.putExtra(KEY_DATA, entity);
        }
        context.startActivityForResult(intent, requestCode);
    }

    private static final String DATE_FORMAT = "%1$d-%2$d-%3$d";

    private EditText mLotNoEdit;
    private EditText mAmountEdit;
    private LotEntity mEntity;
    private TextView mDateTextView;
    private DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lot_add_layout);

        initView();
    }

    private void initView() {
        mLotNoEdit = (EditText) findViewById(R.id.lot_add_number);
        mAmountEdit = (EditText) findViewById(R.id.lot_add_amount);
        mDateTextView = (TextView) findViewById(R.id.lot_validity_date);

        Intent intent = getIntent();
        if (intent.hasExtra(KEY_DATA)) {
            mEntity = intent.getParcelableExtra(KEY_DATA);
            mLotNoEdit.setText(mEntity.getLotNo());
            mAmountEdit.setText(String.valueOf(mEntity.getCount()));
            mDateTextView.setText(mEntity.getValidateDate());
        } else {
            mEntity = new LotEntity();
        }

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
        String amount = mAmountEdit.getText().toString();
        if (Validator.isEmpty(amount)) {
            Toaster.showToast("请输入数量.");
            mAmountEdit.requestFocus();
            return;
        }
        String date = mDateTextView.getText().toString();
        if (Validator.isEmpty(date)) {
            showDateDialog();
            return;
        }
        mEntity.setLotNo(number);
        mEntity.setCount(Converter.toFloat(amount));
        mEntity.setValidateDate(date);
        Intent intent = new Intent();
        intent.putExtra(KEY_DATA, mEntity);
        setResult(RESULT_OK, intent);
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
