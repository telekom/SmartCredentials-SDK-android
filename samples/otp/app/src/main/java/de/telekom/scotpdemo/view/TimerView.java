package de.telekom.scotpdemo.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import de.telekom.scotpdemo.R;

/**
 * Created by gabriel.blaj@endava.com at 8/31/2020
 */
public class TimerView extends View {

    private static final float BORDER_STROKE_WIDTH_DP = 5;
    private static final float PADDING_BETWEEN_BORDER_AND_CONTENT_DP = 3;
    private static final float TIMER_START_ANGLE = 270;

    private Paint borderPaint;
    private Paint innerPaint;
    private float borderStrokeWidth;
    private float paddingBetweenBorderAndContent;

    private float remainingTimeInDegrees = 360;

    public TimerView(Context context) {
        super(context);
        init();
    }

    public TimerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TimerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        borderStrokeWidth = convertDpToPixels(BORDER_STROKE_WIDTH_DP);
        paddingBetweenBorderAndContent = convertDpToPixels(PADDING_BETWEEN_BORDER_AND_CONTENT_DP);
        setupBorderPaint();
        setupInnerPaint();
    }

    private void setupBorderPaint() {
        borderPaint = new Paint();
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setAntiAlias(true);
        borderPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));
        borderPaint.setStrokeWidth(borderStrokeWidth);
    }

    private void setupInnerPaint() {
        innerPaint = new Paint();
        innerPaint.setStyle(Paint.Style.FILL);
        innerPaint.setAntiAlias(true);
        innerPaint.setColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));
    }

    public void updateTimer(int remainingDegrees) {
        remainingTimeInDegrees = remainingDegrees;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;

        canvas.drawCircle(centerX, centerY, Math.min(centerX, centerY) - borderStrokeWidth / 2, borderPaint);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        RectF oval = buildInnerCircle(width, height);
        canvas.drawArc(oval, TIMER_START_ANGLE, remainingTimeInDegrees, true, innerPaint);
    }

    private RectF buildInnerCircle(int width, int height) {
        return new RectF(
                borderStrokeWidth + paddingBetweenBorderAndContent,
                borderStrokeWidth + paddingBetweenBorderAndContent,
                width - borderStrokeWidth - paddingBetweenBorderAndContent,
                height - borderStrokeWidth - paddingBetweenBorderAndContent);
    }

    private int convertDpToPixels(float dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
