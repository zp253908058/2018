package com.teeny.wms.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teeny.wms.R;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see NavigationItemView
 * @since 2017/7/15
 */

public class NavigationItemView extends LinearLayout {
    private ImageView mIconView;
    private TextView mTitleView;

    public NavigationItemView(Context context) {
        this(context, null);
    }

    public NavigationItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigationItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NavigationItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {

        super.setOrientation(VERTICAL);
        Resources resources = context.getResources();

        mIconView = new ImageView(context);
        LayoutParams iconLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        iconLayoutParams.weight = 3;
        mIconView.setLayoutParams(iconLayoutParams);
        mIconView.setScaleType(ImageView.ScaleType.CENTER);

        mTitleView = new TextView(context);
        LayoutParams titleLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, 0);
        titleLayoutParams.weight = 1;
        mTitleView.setTextColor(ResourcesCompat.getColor(resources, R.color.colorPrimary, null));
        mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimensionPixelSize(R.dimen.dp_16));
        mTitleView.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
        mTitleView.setLayoutParams(titleLayoutParams);

        addView(mIconView);
        addView(mTitleView);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NavigationItemView, defStyleAttr, 0);
        final int N = a.getIndexCount();
        for (int i = 0; i < N; i++) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.NavigationItemView_android_text:
                    mTitleView.setText(a.getString(attr));
                    break;
                case R.styleable.NavigationItemView_android_textSize:
                    mTitleView.setTextSize(TypedValue.COMPLEX_UNIT_PX, a.getDimensionPixelSize(attr, context.getResources().getDimensionPixelSize(R.dimen.dp_16)));
                    break;
                case R.styleable.NavigationItemView_android_textColor:
                    mTitleView.setTextColor(a.getColor(attr, ResourcesCompat.getColor(resources, R.color.text_color, null)));
                    break;
                case R.styleable.NavigationItemView_navigation_icon:
                    mIconView.setImageDrawable(a.getDrawable(attr));
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
        mTitleView.setText(text);
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
        mTitleView.setText(text, type);
    }

    /**
     * Sets the TextView to display the specified slice of the specified
     * char array.  You must promise that you will not change the contents
     * of the array except for right before another call to setText(),
     * since the TextView has no way to know that the text
     * has changed and that it needs to invalidate and re-layout.
     */
    public void setText(char[] text, int start, int len) {
        mTitleView.setText(text, start, len);
    }


    public void setText(@StringRes int resid) {
        mTitleView.setText(resid);
    }

    public void setText(@StringRes int resid, TextView.BufferType type) {
        mTitleView.setText(resid, type);
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
        mTitleView.setTextColor(color);
    }

    /**
     * Sets the text color.
     *
     * @see #setTextColor(int)
     */
    public void setTextColor(ColorStateList colors) {
        mTitleView.setTextColor(colors);
    }

    /**
     * Set the default text size to the given value, interpreted as "scaled
     * pixel" units.  This size is adjusted based on the current density and
     * user font size preference.
     *
     * @param size The scaled pixel size.
     */
    public void setTextSize(float size) {
        mTitleView.setTextSize(size);
    }

    /**
     * Set the default text size to a given unit and value.  See {@link
     * TypedValue} for the possible dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     */
    public void setTextSize(int unit, float size) {
        mTitleView.setTextSize(unit, size);
    }

    public void setIcon(@DrawableRes int resId) {
        mIconView.setImageResource(resId);
    }

    public void setIcon(Drawable drawable) {
        mIconView.setImageDrawable(drawable);
    }
}
