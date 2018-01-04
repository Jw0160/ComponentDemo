package com.common.jwrouterdemo.activity;

import android.app.ProgressDialog;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.common.common_base.base.BaseActivity;
import com.common.common_base.image.progress.ProgressInterceptor;
import com.common.common_base.image.progress.ProgressListener;
import com.common.jwrouterdemo.R;

/**
 * @author : wangb
 * @email :
 * @date : 2018/1/4
 * @desc :
 */

public class TestGlideActivity extends BaseActivity{

    private ImageView mViewById;
    private String url = "http://guolin.tech/test.gif";
    private ProgressDialog progressDialog;

    @Override
    public int getContentViewId(){
        return R.layout.activity_test_glide;
    }

    @Override
    public void initBundleData(){
        mViewById = (ImageView) findViewById(R.id.img_1);
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("加载中");
    }

    @Override
    public void initData(){
    }

    public void onShowImageClick(View view){
        ProgressInterceptor.addListener(url, new ProgressListener(){
            @Override
            public void onProgress(int progress){
                progressDialog.setProgress(progress);
            }
        });
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(new GlideDrawableImageViewTarget(mViewById){
                    @Override
                    public void onLoadStarted(Drawable placeholder){
                        super.onLoadStarted(placeholder);
                        progressDialog.show();
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation){
                        super.onResourceReady(resource, animation);
                        progressDialog.dismiss();
                        ProgressInterceptor.removeListener(url);
                    }
                });
    }
}
