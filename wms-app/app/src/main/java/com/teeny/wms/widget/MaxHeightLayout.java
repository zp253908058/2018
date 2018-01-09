package com.teeny.wms.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.teeny.wms.R;
import com.teeny.wms.util.WindowUtils;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see MaxHeightLayout
 * @since 2017/8/14
 */

public class MaxHeightLayout extends LinearLayout {

    private static final float DEFAULT_MAX_HEIGHT_RATIO = 0.6f;
    private static final int DEFAULT_MAX_HEIGHT = 0;

    private float mMaxHeightRatio = DEFAULT_MAX_HEIGHT_RATIO;// 优先级高
    private int mMaxHeight = DEFAULT_MAX_HEIGHT;// 优先级低

    public MaxHeightLayout(Context context) {
        this(context, null);
    }

    public MaxHeightLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaxHeightLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaxHeightLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaxHeightLayout, defStyleAttr, 0);

        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
            int attr = a.getIndex(i);
            switch (attr) {
                case R.styleable.MaxHeightLayout_android_maxHeight:
                    setMaxHeight(a.getDimensionPixelSize(attr, DEFAULT_MAX_HEIGHT));
                    break;
                case R.styleable.MaxHeightLayout_maxHeightRatio:
                    setMaxHeightRatio(a.getFloat(attr, DEFAULT_MAX_HEIGHT_RATIO));
                    break;
            }
        }
        a.recycle();

        int ratioHeight = (int) (mMaxHeightRatio * getScreenHeight(getContext()));

        if (mMaxHeight <= 0) {
            mMaxHeight = ratioHeight;
        } else {
            mMaxHeight = Math.min(mMaxHeight, ratioHeight);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        heightSize = heightSize <= mMaxHeight ? heightSize : mMaxHeight;
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, heightMode);
        super.onMeasure(widthMeasureSpec, maxHeightMeasureSpec);
    }

    public float getMaxHeightRatio() {
        return mMaxHeightRatio;
    }

    public void setMaxHeightRatio(float maxHeightRatio) {
        if (mMaxHeightRatio != maxHeightRatio) {
            mMaxHeightRatio = maxHeightRatio;
            requestLayout();
        }
    }

    public int getMaxHeight() {
        return mMaxHeight;
    }

    public void setMaxHeight(int maxHeight) {
        if (mMaxHeight != maxHeight) {
            mMaxHeight = maxHeight;
            requestLayout();
        }

    }

    /**
     * 获取屏幕高度
     *
     * @param context context
     */

    private int getScreenHeight(Context context) {
        return WindowUtils.getScreenHeight(context);
    }
}
