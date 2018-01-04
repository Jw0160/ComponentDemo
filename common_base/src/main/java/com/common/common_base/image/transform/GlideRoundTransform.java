package com.common.common_base.image.transform;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * 圆角矩形转换器
 */
public class GlideRoundTransform extends BitmapTransformation {

    public final int DEFAULT_CORNER_RADIO = 10;

    private  float radius = 0f;


    /**
     * 圆角矩形Bitmap转换器
     * @param context 这里的context可以不用考虑传入的是哪里的Context或者Activity，最终在源码都是拿的getApplicationContext
     * @param cornerRadio 圆角角度，dp值
     */
    public GlideRoundTransform(Context context, int cornerRadio) {
        super(context);
        if (cornerRadio >= 0){
            this.radius = Resources.getSystem().getDisplayMetrics().density * cornerRadio;
        }else{
            this.radius = Resources.getSystem().getDisplayMetrics().density * DEFAULT_CORNER_RADIO;
        }
    }

    @Override protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return roundCrop(pool, toTransform);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;

        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, radius, radius, paint);
        return result;
    }

    @Override public String getId() {
        return getClass().getName() + Math.round(radius);
    }
}
