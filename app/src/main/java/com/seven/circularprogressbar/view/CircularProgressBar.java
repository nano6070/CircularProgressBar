package com.seven.circularprogressbar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.seven.circularprogressbar.R;

/**
 * Created by Seven on 2017/4/18.
 */
public class CircularProgressBar extends View {
    private int progressColor;
    private int progressBackgroundColor;
    private int stroke_width;
    private float mProgress = 0.0f;

    private float mTranslationOffsetX;
    private float mTranslationOffsetY;

    private RectF mBounds = new RectF();
    private float mRadius;

    private Paint mPaint;
    private Paint mBackgroundPaint;

    public int getStroke_width() {
        return stroke_width;
    }

    public void setStroke_width(int stroke_width) {
        this.stroke_width = stroke_width;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        if (mProgress == progress) {
            return;
        }

        this.mProgress = progress;
        invalidate();
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
        updateProgressColor();
    }

    public int getProgressBackgroundColor() {
        return progressBackgroundColor;
    }

    public void setProgressBackgroundColor(int progressBackgroundColor) {
        this.progressBackgroundColor = progressBackgroundColor;
        updateBackgroundColor();
    }

    public CircularProgressBar(Context context) {
        this(context, null);
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularProgressBar(Context context, AttributeSet attrs, int defStyleRes) {
        super(context, attrs, defStyleRes);
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularProgressBar, defStyleRes, 0);
        if (attributes != null) {
            setProgressColor(attributes.getColor(R.styleable.CircularProgressBar_progress_color, Color.CYAN));
            setProgressBackgroundColor(attributes.getColor(R.styleable.CircularProgressBar_progress_background_color, Color.TRANSPARENT));
            setProgress(attributes.getFloat(R.styleable.CircularProgressBar_progress, 0.0f));
            setStroke_width((int) attributes.getDimension(R.styleable.CircularProgressBar_stroke_width, 8));
        }

        updateBackgroundColor();
    }

    private float getCurrentRotation() {
        return 360 * mProgress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float progressRotation = getCurrentRotation();
        //将画布的原点移到(mTranslationOffsetX, mTranslationOffsetY)
        canvas.translate(mTranslationOffsetX, mTranslationOffsetY);
        //画背景
        canvas.drawArc(mBounds, 270, -(360 - progressRotation), false,
                mBackgroundPaint);
        //画进度
        canvas.drawArc(mBounds, 270, progressRotation, false, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = getDefaultSize(getSuggestedMinimumHeight() + getPaddingTop() + getPaddingBottom(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth() + getPaddingLeft() + getPaddingRight(), widthMeasureSpec);
        int diameter;
        switch (MeasureSpec.getMode(widthMeasureSpec)) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                diameter = width;
                break;
            case MeasureSpec.EXACTLY:
                diameter = MeasureSpec.getSize(widthMeasureSpec);
                break;
            default:
                diameter = width;
                break;
        }

        switch (MeasureSpec.getMode(heightMeasureSpec)) {
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                diameter = height;
                break;
            case MeasureSpec.EXACTLY:
                diameter = MeasureSpec.getSize(heightMeasureSpec);
                break;
            default:
                diameter = height;
                break;
        }

        diameter = Math.min(width, height);
        setMeasuredDimension(diameter, diameter);

        float radius = diameter / 2;
        float fixwidth = stroke_width / 2;
        mRadius = radius - fixwidth;

        mBounds.set(-mRadius, -mRadius, mRadius, mRadius);

        mTranslationOffsetX = radius;
        mTranslationOffsetY = radius;
    }

    private void updateBackgroundColor() {
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(getProgressBackgroundColor());
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(getStroke_width());

        invalidate();
    }

    private void updateProgressColor() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getProgressColor());
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getStroke_width());

        invalidate();
    }
}
