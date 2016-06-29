package com.nex3z.expandablecircleview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ExpandableCircleView extends View {

    private static final int DEFAULT_OUTER_COLOR = Color.BLACK;
    private static final int DEFAULT_INNER_COLOR = Color.BLUE;
    private static final int DEFAULT_EXPAND_ANIMATION_DURATION = 100;
    private static final float DEFAULT_INNER_CIRCLE_PROPORTION = 0.5f;

    private int mOuterColor = DEFAULT_OUTER_COLOR;
    private int mInnerColor = DEFAULT_INNER_COLOR;
    private int mExpandAnimationDuration = DEFAULT_EXPAND_ANIMATION_DURATION;
    private float mProportion = 0;
    private Paint mOuterPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mInnerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ObjectAnimator mExpandAnimator;

    public ExpandableCircleView(Context context) {
        super(context);
        init();
    }

    public ExpandableCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public ExpandableCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ExpandableCircleView,
                0, 0);

        try {
            mOuterColor = a.getColor(R.styleable.ExpandableCircleView_outerColor,
                    DEFAULT_OUTER_COLOR);
            mInnerColor = a.getColor(R.styleable.ExpandableCircleView_innerColor,
                    DEFAULT_INNER_COLOR);
            mProportion = a.getFloat(R.styleable.ExpandableCircleView_proportion,
                    DEFAULT_INNER_CIRCLE_PROPORTION);
            mExpandAnimationDuration = a.getInt(
                    R.styleable.ExpandableCircleView_expandAnimationDuration,
                    DEFAULT_EXPAND_ANIMATION_DURATION);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStyle(Paint.Style.STROKE);
        mInnerPaint.setColor(mInnerColor);

        mExpandAnimator = ObjectAnimator.ofFloat(this, "Proportion", 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        int width = getWidth() - paddingLeft - paddingRight;
        int height = getHeight() - paddingTop - paddingBottom;

        float borderRadius = (width < height ? width : height) / 2;
        float cx = paddingLeft + borderRadius;
        float cy = paddingTop + borderRadius;

        canvas.drawCircle(cx, cy, borderRadius, mOuterPaint);
        canvas.drawCircle(cx, cy, borderRadius * mProportion, mInnerPaint);
    }

    /**
     * Expand inner circle with animation.
     *
     * @param proportion the proportion of the inner circle to expand
     */
    public void expand(float proportion) {
        if (mExpandAnimator.isRunning()) {
            mExpandAnimator.cancel();
        }
        mExpandAnimator.setFloatValues(proportion);
        mExpandAnimator.setDuration(mExpandAnimationDuration).start();
    }

    /**
     * Gets the proportion of the inner circle.
     *
     * @return the proportion of the inner circle
     */
    public float getProportion() {
        return mProportion;
    }

    /**
     * Expands inner circle to specific proportion without animation.
     *
     * @param proportion the proportion of the inner circle to expand
     */
    public void setProportion(float proportion) {
        mProportion = proportion;
        invalidate();
    }

    /**
     * Gets the color of outer circle.
     *
     * @return the color of the outer circle
     */
    public int getOuterColor() {
        return mOuterColor;
    }

    /**
     * Changes the color of outer circle.
     *
     * @param color the color of the outer circle.
     */
    public void setOuterColor(int color) {
        mOuterColor = color;
        mOuterPaint.setColor(mOuterColor);
    }

    /**
     * Get the color of inner circle.
     *
     * @return the color of the inner circle
     */
    public int getInnerColor() {
        return mOuterColor;
    }

    /**
     * Changes the color of inner circle.
     *
     * @param color the color of the inner circle.
     */
    public void setInnerColor(int color) {
        mInnerColor = color;
        mInnerPaint.setColor(mInnerColor);
    }

    /**
     * Get the animation duration in millisecond for the inner circle to expand.
     *
     * @return the animation duration in millisecond for the inner circle to expand.
     */
    public int getExpandAnimationDuration() {
        return mExpandAnimationDuration;
    }

    /**
     * Changes the animation duration in millisecond for the inner circle to expand.
     *
     * @param duration the animation duration in millisecond for the inner circle to expand.
     */
    public void setExpandAnimationDuration(int duration) {
        mExpandAnimationDuration = duration;
    }

}
