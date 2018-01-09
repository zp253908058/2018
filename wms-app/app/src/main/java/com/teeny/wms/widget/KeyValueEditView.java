package com.teeny.wms.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teeny.wms.R;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValueEditView
 * @since 2017/7/31
 */

public class KeyValueEditView extends LinearLayout {

    private TextView mKeyView;
    private EditText mValueView;

    public KeyValueEditView(Context context) {
        this(context, null);
    }

    public KeyValueEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyValueEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyValueEditView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super.setOrientation(HORIZONTAL);
        Resources resources = context.getResources();

        mKeyView = new TextView(context);
        mKeyView.setLines(1);
        mKeyView.setSingleLine();
        mKeyView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        mKeyView.setGravity(Gravity.CENTER_VERTICAL);
        addView(mKeyView);

        mValueView = new EditText(context);
        mValueView.setLines(1);
        mValueView.setSingleLine();
        mValueView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mValueView.setGravity(Gravity.CENTER_VERTICAL | GravityCompat.END);
        mValueView.setBackground(null);
        mValueView.setEllipsize(TextUtils.TruncateAt.END);
        mValueView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        addView(mValueView);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyValueEditView, defStyleAttr, 0);
        mKeyView.setText(a.getString(R.styleable.KeyValueEditView_key));
        mKeyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.KeyValueEditView_keySize, context.getResources().getDimensionPixelSize(R.dimen.dp_16)));
        mKeyView.setTextColor(a.getColor(R.styleable.KeyValueEditView_keyColor, ResourcesCompat.getColor(resources, R.color.text_key, null)));
        mValueView.setText(a.getString(R.styleable.KeyValueEditView_value));
        mValueView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(R.styleable.KeyValueEditView_valueSize, context.getResources().getDimensionPixelSize(R.dimen.dp_16)));
        mValueView.setTextColor(a.getColor(R.styleable.KeyValueEditView_valueColor, ResourcesCompat.getColor(resources, R.color.text_value, null)));
        mValueView.setEnabled(a.getBoolean(R.styleable.KeyValueEditView_android_enabled, true));
        a.recycle();
    }

    @Override
    public void setOrientation(int orientation) {

    }

    public void setKey(String key) {
        mKeyView.setText(key);
    }

    public void setKeyColor(int keyColor) {
        mKeyView.setTextColor(keyColor);
    }

    public void setKeySize(float keySize) {
        mKeyView.setTextSize(keySize);
    }

    public void setValue(String value) {
        mValueView.setText(value);
    }

    public void setValueColor(int valueColor) {
        mValueView.setTextColor(valueColor);
    }

    public void setValueSize(float valueSize) {
        mValueView.setTextSize(valueSize);
    }

    public void setEnable(boolean enabled) {
        mValueView.setEnabled(enabled);
    }

    public EditText getValueView() {
        return this.mValueView;
    }
}
