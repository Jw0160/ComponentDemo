package com.common.common_base.image;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Jack on 2016/5/25.
 * 图片加载工具基类
 */
public abstract class ImageDisplayBase {

    Context defContext;

    enum FileType{
        CIRCLE, NORMAL, ROUND, KEEP_SCALE
    }

    public void init(Context context){
        defContext = context;
    }

    /**
     * 图片加载显示基础方法，基类只要实现这个方法之后就可以完成全部方法的调用
     *
     * @param with          这个参数是用来帮助图片显示可以根据目标界面容器的生命周期方法配合工作，
     *                      传入的参数可以是Context、Activity、Fragment、FragmentActivtiy，
     *                      值得注意的是如果你的图片显示界面不在主界面，
     *                      则你的依赖关系最好是getApplication,以保证图片加载不会出现异常
     * @param imageView     目标ImageView
     * @param resource      资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     * @param fileType      枚举类型，用来判断加载什么形状的图片，需要自定义图片裁剪工具
     * @param placeHolder   未加载时的占位图片资源地址
     * @param errorResource 加载失败后的错误提示图资源地址
     * @param roundRadius   圆角半径  圆角角度，dp值
     */
    public abstract void display(Object with, ImageView imageView, Object resource, FileType fileType, int placeHolder, int errorResource, int roundRadius);

    /**
     * 加载不同类型的图片，不传入依赖with，默认使用getApplicationContext,一般情况下不要使用这种方式
     *
     * @param imageView     目标ImageView
     * @param resource      资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     * @param fileType      枚举类型，用来判断加载什么形状的图片，需要自定义图片裁剪工具
     * @param placeHolder   未加载时的占位图片资源地址
     * @param errorResource 加载失败后的错误提示图资源地址
     */
    private void display(ImageView imageView, Object resource, FileType fileType, int placeHolder, int errorResource){
        display(imageView.getContext().getApplicationContext(), imageView, resource, fileType, placeHolder, errorResource, -1);
    }

    /**
     * 加载正常图像图片
     *
     * @param imageView     目标ImageView
     * @param resource      资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     * @param placeHolder   未加载时的占位图片资源地址
     * @param errorResource 加载失败后的错误提示图资源地址
     */
    public void loadImage(Object with, ImageView imageView, Object resource, int placeHolder, int errorResource){
        display(with, imageView, resource, FileType.NORMAL, placeHolder, errorResource, -1);
    }

    /**
     * 加载正常图像图片
     *
     * @param imageView 目标ImageView
     * @param resource  资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     */
    public void loadImage(Object with, ImageView imageView, Object resource){
        loadImage(with, imageView, resource, -1, -1);
    }

    /**
     * 加载正常图像图片，不传入依赖with，默认使用getApplicationContext,一般情况下不要使用这种方式
     *
     * @param imageView     目标ImageView
     * @param resource      资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     * @param placeHolder   未加载时的占位图片资源地址
     * @param errorResource 加载失败后的错误提示图资源地址
     */
    public void loadImage(ImageView imageView, Object resource, int placeHolder, int errorResource){
        display(imageView, resource, FileType.NORMAL, placeHolder, errorResource);
    }

    /**
     * 加载正常图像图片，不传入依赖with，默认使用getApplicationContext,一般情况下不要使用这种方式
     *
     * @param imageView 目标ImageView
     * @param resource  资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     */
    public void loadImage(ImageView imageView, Object resource){
        loadImage(imageView, resource, -1, -1);
    }

    /**
     * 加载圆形图片
     *
     * @param imageView     目标ImageView
     * @param resource      资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     * @param placeHolder   未加载时的占位图片资源地址
     * @param errorResource 加载失败后的错误提示图资源地址
     */
    public void loadCircleImage(Object with, ImageView imageView, Object resource, int placeHolder, int errorResource){
        display(with, imageView, resource, FileType.CIRCLE, placeHolder, errorResource, -1);
    }

    /**
     * 加载圆形图片
     *
     * @param imageView 目标ImageView
     * @param resource  资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     */
    public void loadCircleImage(Object with, ImageView imageView, Object resource){
        loadCircleImage(with, imageView, resource, -1, -1);
    }

    /**
     * 加载圆形图片，不传入依赖with，默认使用getApplicationContext,一般情况下不要使用这种方式
     *
     * @param imageView     目标ImageView
     * @param resource      资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     * @param placeHolder   未加载时的占位图片资源地址
     * @param errorResource 加载失败后的错误提示图资源地址
     */
    public void loadCircleImage(ImageView imageView, Object resource, int placeHolder, int errorResource){
        display(imageView, resource, FileType.CIRCLE, placeHolder, errorResource);
    }

    /**
     * 加载圆形图片，不传入依赖with，默认使用getApplicationContext,一般情况下不要使用这种方式
     *
     * @param imageView 目标ImageView
     * @param resource  资源文件，资源类型可以是File、String、int资源类型，但每一种类型应该检查数据有效性
     */
    public void loadCircleImage(ImageView imageView, Object resource){
        loadCircleImage(imageView, resource, -1, -1);
    }

    public void loadKeepScaleImage(ImageView imageView, Object resource){
        display(imageView, resource, FileType.KEEP_SCALE, -1, -1);
    }

    public void loadRoundImage(ImageView imageView, Object resource){
        display(imageView, resource, FileType.ROUND, -1, -1);
    }

    public void loadRoundImage(ImageView imageView, Object resource, int roundRadius) {
        display(imageView.getContext().getApplicationContext(), imageView, resource, FileType.ROUND, -1, -1, roundRadius);
    }

    /**
     * 暂停加载图片
     *
     * @param with 依赖的Context或Activity
     */
    public abstract void pauseLoadImage(Object with);

    /**
     * 恢复加载图片
     *
     * @param with 依赖的Context或Activity
     */
    public abstract void resumeLoadImage(Object with);

    /**
     * 使用Glide第三方库不用指定大小，他会自动根据View的大小调整加载图片的大小
     * 如果使用Picasso来实现的话，可能需要重写一系列的方法
     */
    public void loadImageSetSize(){
    }
}
