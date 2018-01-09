package com.teeny.wms.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.teeny.wms.R;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see KeyValueTextView
 * @since 2017/7/18
 */

public class KeyValueTextView extends View {
    private static final String TAG = "KeyValueTextView";
    private static final int GRAVITY_LEFT = 0;
    private static final int GRAVITY_CENTER = 1;
    private static final int GRAVITY_RIGHT = 2;

    @Documented
    @Retention(CLASS)
    @Target({METHOD, PARAMETER, FIELD})
    @IntDef({GRAVITY_LEFT, GRAVITY_CENTER, GRAVITY_RIGHT})
    @interface Gravity {
    }

    private static final String SEPARATOR = ":  ";

    private TextPaint mTextPaint;

    private String mKey;
    private int mKeyColor;
    private int mKeySize;
    private String mValue;
    private int mValueColor;
    private int mValueSize;
    private String mSeparator;
    private int mSeparatorColor;
    private int mSeparatorSize;
    @Gravity
    private int mGravity;

    private float mKeyWidth;
    private float mSeparatorWidth;
    private int mContentWidth;
    private int mMaxHeight;

    public KeyValueTextView(Context context) {
        this(context, null);
    }

    public KeyValueTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyValueTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyValueTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        Resources resources = context.getResources();
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyValueTextView, defStyleAttr, 0);
        String key = "";
        if (a.hasValue(R.styleable.KeyValueTextView_key)) {
            key = a.getString(R.styleable.KeyValueTextView_key);
        }
        setKey(key);
        setKeyColor(a.getColor(R.styleable.KeyValueTextView_keyColor, ResourcesCompat.getColor(resources, R.color.text_key, null)));
        setKeySize(a.getDimensionPixelSize(R.styleable.KeyValueTextView_keySize, resources.getDimensionPixelSize(R.dimen.sp_14)));
        String value = "";
        if (a.hasValue(R.styleable.KeyValueTextView_value)) {
            value = a.getString(R.styleable.KeyValueTextView_value);
        }
        setValue(value);
        setValueColor(a.getColor(R.styleable.KeyValueTextView_valueColor, ResourcesCompat.getColor(resources, R.color.text_value, null)));
        setValueSize(a.getDimensionPixelSize(R.styleable.KeyValueTextView_valueSize, resources.getDimensionPixelSize(R.dimen.sp_14)));
        String separator = SEPARATOR;
        if (a.hasValue(R.styleable.KeyValueTextView_separator)) {
            separator = a.getString(R.styleable.KeyValueTextView_separator);
        }
        setSeparator(separator);
        setSeparatorColor(a.getColor(R.styleable.KeyValueTextView_separatorColor, ResourcesCompat.getColor(resources, R.color.text_key, null)));
        setSeparatorSize(a.getDimensionPixelSize(R.styleable.KeyValueTextView_separatorSize, resources.getDimensionPixelSize(R.dimen.sp_14)));
        setGravity(a.getInt(R.styleable.KeyValueTextView_gravity, GRAVITY_LEFT));
        a.recycle();

        mTextPaint = new TextPaint();
        mTextPaint.density = resources.getDisplayMetrics().density;
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mContentWidth = 0;
        mTextPaint.setTextSize(mKeySize);
        mContentWidth += mKeyWidth = mTextPaint.measureText(mKey);
        mContentWidth += mSeparatorWidth = mTextPaint.measureText(mSeparator);
        Paint.FontMetrics keyFontMetrics = mTextPaint.getFontMetrics();
        float keyHeight = (int) (keyFontMetrics.bottom - keyFontMetrics.top);

        mTextPaint.setTextSize(mValueSize);
        mContentWidth += mTextPaint.measureText(mValue);
        Paint.FontMetrics valueFontMetrics = mTextPaint.getFontMetrics();
        float valueHeight = (valueFontMetrics.bottom - valueFontMetrics.top);
        mMaxHeight = (int) Math.max(keyHeight, valueHeight);
        setMeasuredDimension(View.getDefaultSize(mContentWidth + getPaddingLeft() + getPaddingRight(), widthMeasureSpec),
                View.getDefaultSize(mMaxHeight + getPaddingTop() + getPaddingBottom(), heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int top = getPaddingTop();
        int bottom = getMeasuredHeight() - getPaddingBottom();
        int centerY = (bottom + top) / 2 + mMaxHeight / 4;
        int start = 0;
        int width = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        switch (mGravity) {
            case GRAVITY_RIGHT:
                start = width - mContentWidth;
                start += getPaddingLeft();
                break;
            case GRAVITY_LEFT:
                start += getPaddingLeft();
                break;
            case GRAVITY_CENTER:
                int offset = (width - mContentWidth) / 2;
                start += getPaddingLeft();
                start += offset;
                break;
        }
        mTextPaint.setColor(mKeyColor);
        mTextPaint.setTextSize(mKeySize);
        canvas.drawText(mKey, start, centerY, mTextPaint);
        mTextPaint.setColor(mSeparatorColor);
        canvas.drawText(mSeparator, start + mKeyWidth, centerY, mTextPaint);

        mTextPaint.setColor(mValueColor);
        mTextPaint.setTextSize(mValueSize);
        canvas.drawText(mValue, start + mKeyWidth + mSeparatorWidth, centerY, mTextPaint);
    }


    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        if (key == null) {
            key = "";
        }
        if (!key.equals(mKey)) {
            mKey = key;
            requestLayout();
            invalidate();
        }
    }

    public int getKeyColor() {
        return mKeyColor;
    }

    public void setKeyColor(int keyColor) {
        if (mKeyColor != keyColor) {
            mKeyColor = keyColor;
            invalidate();
        }
    }

    public int getKeySize() {
        return mKeySize;
    }

    public void setKeySize(int keySize) {
        if (mKeySize != keySize) {
            mKeySize = keySize;
            requestLayout();
            invalidate();
        }
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        if (value == null) {
            value = "";
        }
        if (!value.equals(mValue)) {
            mValue = value;
            requestLayout();
            invalidate();
        }
    }

    public int getValueColor() {
        return mValueColor;
    }

    public void setValueColor(int valueColor) {
        if (mValueColor != valueColor) {
            mValueColor = valueColor;
            invalidate();
        }
    }

    public int getValueSize() {
        return mValueSize;
    }

    public void setValueSize(int valueSize) {
        if (mValueSize != valueSize) {
            mValueSize = valueSize;
            requestLayout();
            invalidate();
        }
    }

    public String getSeparator() {
        return mSeparator;
    }

    public void setSeparator(String separator) {
        if (separator == null) {
            separator = SEPARATOR;
        }
        if (!separator.equals(mSeparator)) {
            mSeparator = separator;
            requestLayout();
            invalidate();
        }
    }

    public int getSeparatorColor() {
        return mSeparatorColor;
    }

    public void setSeparatorColor(int separatorColor) {
        if (mSeparatorColor != separatorColor) {
            mSeparatorColor = separatorColor;
            invalidate();
        }
    }

    public int getSeparatorSize() {
        return mSeparatorSize;
    }

    public void setSeparatorSize(int separatorSize) {
        if (mSeparatorSize != separatorSize) {
            mSeparatorSize = separatorSize;
            requestLayout();
            invalidate();
        }
    }

    public void setGravity(@Gravity int gravity) {
        if (mGravity != gravity) {
            mGravity = gravity;
            invalidate();
        }
    }

    public int getGravity() {
        return mGravity;
    }
}
