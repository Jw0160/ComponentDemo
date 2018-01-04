package com.common.common_base.image.transform;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;


public class GlideKeepScaleTransform extends BitmapTransformation {
    public GlideKeepScaleTransform(Context context) {
        super(context);
    }


    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap source, int outWidth, int outHeight) {

        int targetWidth = outWidth;

        if(source.getWidth()==0){
            return source;
        }
        //如果图片小于设置的宽度，则返回原图
        if(source.getWidth()==targetWidth){
            return source;
        }else{
            //如果图片大小大于等于设置的宽度，则按照设置的宽度比例来缩放
            double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
            int targetHeight = (int) (targetWidth * aspectRatio);
            if (targetHeight != 0 && targetWidth != 0) {
                Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
                if (result != source) {
                    // Same bitmap is returned if sizes are the same
//                    source.recycle();
                }
                return result;
            } else {
                return source;
            }

        }

    }

    @Override
    public String getId() {
        return getClass().getName() + Math.round(100);
    }
}
