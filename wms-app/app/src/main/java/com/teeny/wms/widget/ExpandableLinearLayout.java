package com.teeny.wms.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.IntRange;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.teeny.wms.R;

/**
 * Class description:
 *
 * @author zp
 * @version 1.0
 * @see ExpandableLinearLayout
 * @since 2018/1/21
 */

public class ExpandableLinearLayout extends LinearLayout {

    private static final int INVALID_POSITION = -1;
    private static final int DEFAULT_DURATION = 300;

    private int mDuration = DEFAULT_DURATION;
    private int mExpandableViewIndex = INVALID_POSITION;
    private View mExpandableView;
    private int mViewSize = 0;
    private TimeInterpolator mInterpolator = new LinearInterpolator();
    private boolean mIsExpand = false;
    private boolean mIsAnimating;
    private boolean mIsFirstMeasure = true;

    private boolean mIsInitialized = false;

    public ExpandableLinearLayout(Context context) {
        this(context, null);
    }

    public ExpandableLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandableLinearLayout, defStyleAttr, 0);
        if (a.hasValue(R.styleable.ExpandableLinearLayout_duration)) {
            setDuration(a.getInteger(R.styleable.ExpandableLinearLayout_duration, DEFAULT_DURATION));
        }
        if (a.hasValue(R.styleable.ExpandableLinearLayout_expandableViewIndex)) {
            setExpandableViewIndex(a.getInteger(R.styleable.ExpandableLinearLayout_expandableViewIndex, INVALID_POSITION));
        }
        if (a.hasValue(R.styleable.ExpandableLinearLayout_expand)) {
            setExpand(a.getBoolean(R.styleable.ExpandableLinearLayout_expandableViewIndex, false));
        }
        if (a.hasValue(R.styleable.ExpandableLinearLayout_interpolator)) {
            setInterpolator(InterpolatorProvider.createInterpolator(a.getInteger(R.styleable.ExpandableLinearLayout_interpolator, InterpolatorProvider.LINEAR_INTERPOLATOR)));
        }
        setOnClickListener(this::onClick);
        a.recycle();
        mIsInitialized = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mIsFirstMeasure) {
            int count = getChildCount();
            if (mExpandableViewIndex >= count) {
                throw new IllegalArgumentException("invalid index.");
            }
            if (mExpandableViewIndex >= 0) {
                mExpandableView = getChildAt(mExpandableViewIndex);
                mExpandableView.setVisibility(VISIBLE);
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (!mIsFirstMeasure) {
            return;
        }
        if (mExpandableView == null) {
            return;
        }
        LayoutParams params = (LayoutParams) mExpandableView.getLayoutParams();
        mViewSize = isVertical() ? params.height : params.width;
        if (!mIsExpand) {
            if (isVertical()) {
                params.height = 0;
            } else {
                params.width = 0;
            }
        }
        mIsFirstMeasure = false;
    }

    private boolean isVertical() {
        return getOrientation() == LinearLayout.VERTICAL;
    }

    private void onClick(View view) {
        toggle(!mIsExpand);
    }

    public void toggle(boolean expand) {
        if (mExpandableView == null) {
            return;
        }
        if (mIsAnimating) {
            return;
        }
        createExpandAnimator().start();
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
    }

    public boolean isExpand() {
        return mIsExpand;
    }

    public void setExpand(boolean expand) {
        if (mIsExpand != expand) {
            mIsExpand = expand;
            if (mIsInitialized) {
                requestLayout();
            }
        }
    }

    public int getExpandableViewIndex() {
        return mExpandableViewIndex;
    }

    public void setExpandableViewIndex(int expandableViewIndex) {
        if (mExpandableViewIndex != expandableViewIndex) {
            mExpandableViewIndex = expandableViewIndex;
            if (mIsInitialized) {
                requestLayout();
            }
        }
    }

    public TimeInterpolator getInterpolator() {
        return mInterpolator;
    }

    public void setInterpolator(TimeInterpolator interpolator) {
        mInterpolator = interpolator;
    }

    private ValueAnimator createExpandAnimator() {
        int from = mIsExpand ? mViewSize : 0;
        int to = mIsExpand ? 0 : mViewSize;
        final ValueAnimator valueAnimator = ValueAnimator.ofInt(from, to);
        valueAnimator.setDuration(mDuration);
        valueAnimator.setInterpolator(mInterpolator);
        valueAnimator.addUpdateListener(animator -> {
            if (isVertical()) {
                mExpandableView.getLayoutParams().height = (int) animator.getAnimatedValue();
            } else {
                mExpandableView.getLayoutParams().width = (int) animator.getAnimatedValue();
            }
            requestLayout();
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animator) {
                mIsAnimating = true;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mIsAnimating = false;
                mIsExpand = !mIsExpand;
            }
        });
        return valueAnimator;
    }

    private static class InterpolatorProvider {
        private static final int ACCELERATE_DECELERATE_INTERPOLATOR = 0;
        private static final int ACCELERATE_INTERPOLATOR = 1;
        private static final int ANTICIPATE_INTERPOLATOR = 2;
        private static final int ANTICIPATE_OVERSHOOT_INTERPOLATOR = 3;
        private static final int BOUNCE_INTERPOLATOR = 4;
        private static final int DECELERATE_INTERPOLATOR = 5;
        private static final int FAST_OUT_LINEAR_IN_INTERPOLATOR = 6;
        private static final int FAST_OUT_SLOW_IN_INTERPOLATOR = 7;
        private static final int LINEAR_INTERPOLATOR = 8;
        private static final int LINEAR_OUT_SLOW_IN_INTERPOLATOR = 9;
        private static final int OVERSHOOT_INTERPOLATOR = 10;

        static TimeInterpolator createInterpolator(@IntRange(from = 0, to = 10) final int interpolatorType) {
            switch (interpolatorType) {
                case ACCELERATE_DECELERATE_INTERPOLATOR:
                    return new AccelerateDecelerateInterpolator();
                case ACCELERATE_INTERPOLATOR:
                    return new AccelerateInterpolator();
                case ANTICIPATE_INTERPOLATOR:
                    return new AnticipateInterpolator();
                case ANTICIPATE_OVERSHOOT_INTERPOLATOR:
                    return new AnticipateOvershootInterpolator();
                case BOUNCE_INTERPOLATOR:
                    return new BounceInterpolator();
                case DECELERATE_INTERPOLATOR:
                    return new DecelerateInterpolator();
                case FAST_OUT_LINEAR_IN_INTERPOLATOR:
                    return new FastOutLinearInInterpolator();
                case FAST_OUT_SLOW_IN_INTERPOLATOR:
                    return new FastOutSlowInInterpolator();
                case LINEAR_INTERPOLATOR:
                    return new LinearInterpolator();
                case LINEAR_OUT_SLOW_IN_INTERPOLATOR:
                    return new LinearOutSlowInInterpolator();
                case OVERSHOOT_INTERPOLATOR:
                    return new OvershootInterpolator();
                default:
                    return new LinearInterpolator();
            }
        }
    }
}
