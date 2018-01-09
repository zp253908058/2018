package com.teeny.wms.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teeny.wms.R;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see BadgeView
 * @since 2017/7/15
 */

public class BadgeView extends LinearLayout {

    private TextView mContentView;
    private TextView mBadgeView;

    public BadgeView(Context context) {
        this(context, null);
    }

    public BadgeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BadgeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super.setOrientation(HORIZONTAL);

        mContentView = new TextView(context);
        LayoutParams contentLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        contentLayoutParams.weight = 1;
        mContentView.setGravity(Gravity.CENTER);
        mContentView.setTextColor(Color.BLACK);
        mContentView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.dp_16));
        mContentView.setLayoutParams(contentLayoutParams);

        mBadgeView = new TextView(context);
        LayoutParams badgeLayoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT);
        badgeLayoutParams.weight = 1;
        mBadgeView.setTextColor(Color.RED);
        mBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.dp_24));
        mBadgeView.setGravity(Gravity.CENTER);
        mBadgeView.setLayoutParams(badgeLayoutParams);
        mBadgeView.setText("0");

        addView(mContentView);
        addView(mBadgeView);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BadgeView, defStyleAttr, 0);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.BadgeView_android_text:
                    mContentView.setText(a.getString(attr));
                    break;
                case R.styleable.BadgeView_android_textSize:
                    mContentView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(attr, context.getResources().getDimensionPixelSize(R.dimen.dp_16)));
                    break;
                case R.styleable.BadgeView_android_textColor:
                    mContentView.setTextColor(a.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.BadgeView_badge:
                    mBadgeView.setText(String.valueOf(a.getInt(attr, 0)));
                    break;
                case R.styleable.BadgeView_badgeTextColor:
                    mBadgeView.setTextColor(a.getColor(attr, Color.BLACK));
                    break;
                case R.styleable.BadgeView_badgeTextSize:
                    mBadgeView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(attr, context.getResources().getDimensionPixelSize(R.dimen.dp_16)));
                    break;
            }
        }
        a.recycle();
    }

    @Override
    public void setOrientation(int orientation) {

    }

    /**
     * Sets the string value of the TextView. TextView <em>does not</em> accept
     * HTML-like formatting, which you can do with text strings in XML resource files.
     * To style your strings, attach android.text.style.* objects to a
     * {@link android.text.SpannableString SpannableString}, or see the
     * <a href="{@docRoot}guide/topics/resources/available-resources.html#stringresources">
     * Available Resource Types</a> documentation for an example of setting
     * formatted text in the XML resource file.
     *
     * @attr ref android.R.styleable#TextView_text
     */
    public final void setText(CharSequence text) {
        mContentView.setText(text);
    }

    /**
     * Sets the text that this TextView is to display (see
     * {@link #setText(CharSequence)}) and also sets whether it is stored
     * in a styleable/spannable buffer and whether it is editable.
     *
     * @attr ref android.R.styleable#TextView_text
     * @attr ref android.R.styleable#TextView_bufferType
     */
    public void setText(CharSequence text, TextView.BufferType type) {
        mContentView.setText(text, type);
    }

    /**
     * Sets the TextView to display the specified slice of the specified
     * char array.  You must promise that you will not change the contents
     * of the array except for right before another call to setText(),
     * since the TextView has no way to know that the text
     * has changed and that it needs to invalidate and re-layout.
     */
    public void setText(char[] text, int start, int len) {
        mContentView.setText(text, start, len);
    }


    public void setText(@StringRes int resid) {
        mContentView.setText(resid);
    }

    public void setText(@StringRes int resid, TextView.BufferType type) {
        mContentView.setText(resid, type);
    }

    /**
     * Sets the text color for all the states (normal, selected,
     * focused) to be this color.
     *
     * @param color A color value in the form 0xAARRGGBB.
     *              Do not pass a resource ID. To get a color value from a resource ID, call
     *              {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     * @see #setTextColor(ColorStateList)
     */
    public void setTextColor(@ColorInt int color) {
        mContentView.setTextColor(color);
    }

    /**
     * Sets the text color.
     *
     * @see #setTextColor(int)
     */
    public void setTextColor(ColorStateList colors) {
        mContentView.setTextColor(colors);
    }

    /**
     * Set the default text size to the given value, interpreted as "scaled
     * pixel" units.  This size is adjusted based on the current density and
     * user font size preference.
     *
     * @param size The scaled pixel size.
     */
    public void setTextSize(float size) {
        mContentView.setTextSize(size);
    }

    /**
     * Set the default text size to a given unit and value.  See {@link
     * TypedValue} for the possible dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     */
    public void setTextSize(int unit, float size) {
        mContentView.setTextSize(unit, size);
    }

    public void setBadge(int badge) {
        mBadgeView.setText(String.valueOf(badge));
    }

    public void setBadgeTextColor(@ColorInt int color) {
        mBadgeView.setTextColor(color);
    }

    public void setBadgeTextColor(ColorStateList colors) {
        mBadgeView.setTextColor(colors);
    }

    public void setBadgeTextSize(float size) {
        mBadgeView.setTextSize(size);
    }

    public void setBadgeTextSize(int unit, float size) {
        mBadgeView.setTextSize(unit, size);
    }
}
