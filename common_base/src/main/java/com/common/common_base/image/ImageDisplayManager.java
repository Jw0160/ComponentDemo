package com.common.common_base.image;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.common.common_base.image.transform.GlideCircleTransform;
import com.common.common_base.image.transform.GlideKeepScaleTransform;
import com.common.common_base.image.transform.GlideRoundTransform;
import com.common.common_base.utils.util.LogUtils;

import java.io.File;

/**
 * Created by Jack on 2016/5/25.
 * 图片加载工具类，包括网路图片，本地图片，特殊图片的加载
 * V1.0
 */
public class ImageDisplayManager extends ImageDisplayBase {
    private static final String TAG = "ImageDisplyHelper";
    private static ImageDisplayManager mInstance;

    private ImageDisplayManager() {
    }

    public static ImageDisplayManager getInstance() {
        if (mInstance == null) {
            synchronized (ImageDisplayManager.class) {
                if (mInstance == null) {
                    mInstance = new ImageDisplayManager();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void display(Object with, ImageView imageView, Object resourceType, FileType fileType, int placeHolder, int errorResource, int roundRadius) {
        RequestManager requestManager = null;
        if (defContext == null) {
            try {
                throw new Exception("defContext is null");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (with == null) {
            with = defContext;
        }
        if (with instanceof Context) {
            requestManager = Glide.with((Context) with);
        } else if (with instanceof Activity) {
            requestManager = Glide.with((Activity) with);
        } else if (with instanceof Fragment) {
            requestManager = Glide.with((Fragment) with);
        } else if (with instanceof FragmentActivity) {
            requestManager = Glide.with((FragmentActivity) with);
        } else {//def
            requestManager = Glide.with((Context) with);
        }
        DrawableTypeRequest request = null;
        if (resourceType instanceof String) {

            String resourceStr = (String) resourceType;
            if (resourceStr.startsWith("http")){
                if (resourceStr.contains("?x-oss-process=image")) {
                    resourceStr += "/format,jpg/quality,q_70";
                } else if (resourceStr.contains("?") && (resourceStr.lastIndexOf("?") != resourceStr.length() - 1)) {
                    resourceStr += "&x-oss-process=image/format,jpg/quality,q_70";
                } else {
                    resourceStr += "?x-oss-process=image/format,jpg/quality,q_70";
                }
            }
            request = requestManager.load(resourceStr);
        } else if (resourceType instanceof Integer) {
            request = requestManager.load((int) resourceType);
        } else if (resourceType instanceof File) {
            request = requestManager.load((File) resourceType);
        } else {//def
            request = requestManager.load(resourceType.toString());
        }
        DrawableRequestBuilder requestBuilder = null;
        requestBuilder = request.placeholder(placeHolder == -1 ? -1 : placeHolder)
                .error(errorResource == -1 ? -1 : errorResource).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL);
        if (fileType == FileType.CIRCLE) {
            //加载圆形图片转换器
            requestBuilder.transform(new GlideCircleTransform(defContext));
        } else if (fileType == FileType.NORMAL) {

        } else if (fileType == FileType.ROUND) {
            if (roundRadius == -1) {
                roundRadius = 7;
            }
            requestBuilder.transform(new GlideRoundTransform(defContext, roundRadius));
        } else if (fileType == FileType.KEEP_SCALE) {
            requestBuilder.transform(new GlideKeepScaleTransform(defContext));
        }

        requestBuilder.listener(new RequestListener() {
            @Override
            public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
//                LogUtil.d("onException() called with:  == " + e.getMessage() + ", model == " + model.toString() + ", Target == " + target.toString() + ",isFirstResource == " + isFirstResource);

                return false;
            }

            @Override
            public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
//                LogUtil.d("onResourceReady() called with:  == " + resource.toString() + ", model == " + model.toString() + ", isFromMemoryCache == " + isFromMemoryCache + ",isFirstResource == " + isFirstResource);

                return false;
            }
        }).into(imageView);
    }

    @Override
    public void pauseLoadImage(Object with) {
        if (with == null) {
            with = defContext;
        }
        if (with instanceof Context) {
            Glide.with((Context) with).pauseRequests();
        } else if (with instanceof Activity) {
            Glide.with((Activity) with).pauseRequests();
        } else if (with instanceof Fragment) {
            Glide.with((Fragment) with).pauseRequests();
        } else if (with instanceof FragmentActivity) {
            Glide.with((FragmentActivity) with).pauseRequests();
        } else {//def
            Glide.with((Context) with).pauseRequests();
        }
    }

    @Override
    public void resumeLoadImage(Object with) {
        if (with == null) {
            with = defContext;
        }
        if (with instanceof Context) {
            Glide.with((Context) with).resumeRequests();
        } else if (with instanceof Activity) {
            Glide.with((Activity) with).resumeRequests();
        } else if (with instanceof Fragment) {
            Glide.with((Fragment) with).resumeRequests();
        } else if (with instanceof FragmentActivity) {
            Glide.with((FragmentActivity) with).resumeRequests();
        } else {//def
            Glide.with((Context) with).resumeRequests();
        }
    }


    public void loadImageCheck(Activity activity, String path, ImageView imageView) {
        Glide.with(activity).load(path).skipMemoryCache(true).crossFade().listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                LogUtils.d(TAG, "onException && isFirstResource == " + isFirstResource);
                e.printStackTrace();
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                LogUtils.d(TAG, "onResourceReady");
                return false;
            }
        }).into(10, 10);
    }
}
