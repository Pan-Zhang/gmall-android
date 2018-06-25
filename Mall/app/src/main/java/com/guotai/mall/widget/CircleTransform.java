package com.guotai.mall.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.guotai.mall.R;
import com.squareup.picasso.Transformation;

/**
 * Created by ez on 2017/6/16.
 */

public class CircleTransform implements Transformation {

    private int mBorderWidth = 4;  //边框宽度
    private int mBorderColor = R.color.colorWhite;  //边框颜色
    private Context context;

    public CircleTransform(Context context){
        this.context = context.getApplicationContext();
    }

    public CircleTransform(Context context, int mBorderWidth, int mBorderColor){
        this(context);
        this.mBorderColor = mBorderColor;
        this.mBorderWidth = mBorderWidth;

    }

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig() != null
                ? source.getConfig() : Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        //绘制圆形
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        //绘制边框
        Paint mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
        mBorderPaint.setColor(context.getResources().getColor(mBorderColor));
        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
        mBorderPaint.setAntiAlias(true);

        //将边框和圆形画到canvas上
        float r = size / 2f;
        float r1 = (size-2*mBorderWidth) / 2f;
        canvas.drawCircle(r, r, r1, paint);
        canvas.drawCircle(r, r, r1, mBorderPaint);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "Circle";
    }
}
