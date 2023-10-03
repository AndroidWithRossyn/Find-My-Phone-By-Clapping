package com.phonefinder.finderbyclap.devicefind.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

public class DashedLineDrawable extends Drawable {
    private Paint paint;

    public DashedLineDrawable(Context context) {
        paint = new Paint();
        paint.setColor(Color.parseColor("#B3BBCB")); // Set the line color here
        paint.setStrokeWidth(2); // Set the line width here
        paint.setStyle(Paint.Style.STROKE);
        float[] intervals = new float[]{15.0f, 10.0f}; // Adjust the intervals for desired dash effect
        DashPathEffect dashPathEffect = new DashPathEffect(intervals, 0); // The second argument is the phase (use 0 for no phase)
        paint.setPathEffect(dashPathEffect);
    }

    @Override
    public void draw(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 0);
        path.lineTo(0, canvas.getHeight());
        canvas.drawPath(path, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }
}
