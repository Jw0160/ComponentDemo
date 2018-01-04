package com.common.common_base.image.progress;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * @author : wangb
 * @email :
 * @date : 2018/1/4
 * @desc :
 */

public class CustomGlideModule implements GlideModule{
    @Override
    public void applyOptions(Context context, GlideBuilder builder){
    }

    @Override
    public void registerComponents(Context context, Glide glide){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ProgressInterceptor());
        OkHttpClient okHttpClient = builder.build();
        glide.register(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory(okHttpClient));
    }
}
