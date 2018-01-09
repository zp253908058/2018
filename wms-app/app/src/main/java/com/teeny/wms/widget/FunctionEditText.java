package com.teeny.wms.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

/**
 * Class description:带右边按钮功能的EditText
 *
 * @author zp
 * @version 1.0
 * @see BadgeView
 * @since 2017/7/16
 */

public class FunctionEditText extends AppCompatEditText implements View.OnTouchListener {

    private static final String TAG = "FunctionEditText";
    private boolean mIsTouchRightDrawable = false;

    public FunctionEditText(Context context) {
        this(context, null);
    }

    public FunctionEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public FunctionEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        setOnTouchListener(this);
        setGravity(Gravity.CENTER_VERTICAL);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        Drawable rightDrawable = getClickDrawable();
        if (rightDrawable == null) {
            return false;
        }
        if (getText().length() == 0) {
            return false;
        }
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();
        Rect rect = rightDrawable.getBounds();
        int height = rect.height();
        int distance = (getHeight() - height) / 2;
        boolean isInnerWidth = x > (getWidth() - getTotalPaddingRight()) && x < (getWidth() - getPaddingRight());
        boolean isInnerHeight = y > distance && y < (distance + height);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isInnerWidth && isInnerHeight) {
                    mIsTouchRightDrawable = true;
                    return true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (mIsTouchRightDrawable && isInnerWidth && isInnerHeight) {
                    this.setText("");
                    return true;
                }
                mIsTouchRightDrawable = false;
                break;
            case MotionEvent.ACTION_CANCEL:
                mIsTouchRightDrawable = false;
                break;
        }
        return false;
    }

    private Drawable getClickDrawable() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return getCompoundDrawables()[2];
        }
        Drawable drawable;
        drawable = getCompoundDrawablesRelative()[2];
        if (drawable == null) {
            drawable = getCompoundDrawables()[2];
        }
        return drawable;
    }
}
