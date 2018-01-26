package com.common.common_base.widget.titlebar;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.common_base.R;
import com.common.common_base.image.ImageDisplayManager;
import com.common.common_base.utils.CheckUtil;
import com.common.common_base.utils.DelayedUtil;
import com.common.common_base.utils.ViewUtil;
import com.common.common_base.utils.system.AppManagerUtil;
import com.common.common_base.widget.badge.MaterialBadgeTextView;

/**
 * 项目头部整体bar
 */
public class CommonTitleBar extends RelativeLayout{
    private static final String TAG = "CommonTitleBar";

    private Context mContext;
    /**
     * 整个头部layout
     */
    private RelativeLayout mLayout;
    /**
     * 头部整个返回按钮
     */
    private ImageView mComtopReturn;

    /**
     * 头部一般的返回按钮
     */
    private ImageView mComtopBack;
    /**
     * 头部一般的背景图片
     */
    private ImageView mImgTitlebac;

    /**
     * 头部的标题文字
     */
    private TextView mComtopTitle;
    /**
     * 头部右边的按钮
     */
    private TextView mCustomerRightButton;
    /**
     * 头部左边的按钮
     */
    private TextView mCustomerLeftButton;
    /**
     * 左边和右边字体大小
     */
    private int mRightAndReturnTextSize = 14;

    /**
     * 头部右边图片按钮1
     */
    private ImageView mRightImg1;
    /**
     * 头部右边图片按钮2
     */
    private ImageView mRightImg2;

    private boolean mRetrunIsClick = true;
    // 头部中间的自定大小
    private int mMiddleTextSize = 16;

    MaterialBadgeTextView mDotBadgeView;
    private View mBacView;

    /*****************
     * 头部点击监听
     ******************/
    public interface TitleCilckLisenter{
        // 返回按钮
        void returnClick();
    }

    private TitleCilckLisenter mLisenter;

    public void setOnTitleClickLisenter(TitleCilckLisenter lisenter){
        this.mLisenter = lisenter;
    }

    public CommonTitleBar(Context context){
        this(context, null);
    }

    public CommonTitleBar(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public CommonTitleBar(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        this.mContext = context;
        initView();
        initAttributeSet(attrs);
        clearResource();
    }

    /**
     * 初始化自定义属性值
     *
     * @param attrs
     */
    private void initAttributeSet(AttributeSet attrs){
        TypedArray array = mContext.obtainStyledAttributes(attrs,
                R.styleable.CommonTitleBar);
        if(!isInEditMode()){
            int barBackground = array.getResourceId(
                    R.styleable.CommonTitleBar_barBackground, 0);
            if(barBackground != 0){
                mLayout.setBackgroundResource(barBackground);
            }
            mRightAndReturnTextSize = array.getDimensionPixelSize(
                    R.styleable.CommonTitleBar_rightAndReturnTextSize,
                    mRightAndReturnTextSize);
            mCustomerRightButton.setTextSize(mRightAndReturnTextSize);
            mMiddleTextSize = array.getDimensionPixelSize(
                    R.styleable.CommonTitleBar_middleTextSize, mMiddleTextSize);
            mComtopTitle.setTextSize(mMiddleTextSize);
            mRetrunIsClick = array.getBoolean(
                    R.styleable.CommonTitleBar_returnIsClick, true);
            if(mRetrunIsClick){
                //主要用于区分普通bar和webview的bar返回键的作用，如果设置为false，则默认不设置监听，通过setBackClick设置真的点击事件
                mComtopBack.setOnClickListener(new OnClickListener(){
                    @Override
                    public void onClick(View v){
                        finishActivity();
                    }
                });
            }
            mComtopReturn.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View v){
                    finishActivity();
                }
            });
        }
        array.recycle();
    }

    // 防止头部在执行动画时，点击两次
    protected void finishActivity(){
        if(DelayedUtil.isFastClick(500)){
            if(mLisenter != null){
                // 有监听调用监听
                mLisenter.returnClick();
                // 中断下面代码执行
                return;
            }
            AppManagerUtil.getInstance().finishActivity((Activity) mContext);
        }
    }

    /**
     * initialization
     */
    private void initView(){
        View view = ViewUtil.getView(mContext, R.layout.layout_common_title_bar, this);
        mLayout = (RelativeLayout) view.findViewById(R.id.common_top_root);
        mComtopReturn = (ImageView) view.findViewById(R.id.comtop_return);
        mComtopBack = (ImageView) view.findViewById(R.id.comtop_back);
        mImgTitlebac = (ImageView) view.findViewById(R.id.img_title_bac);
        mComtopTitle = (TextView) view.findViewById(R.id.comtop_title);
        mCustomerLeftButton = (TextView) view
                .findViewById(R.id.customer_left_btn);
        mCustomerRightButton = (TextView) view
                .findViewById(R.id.customer_right_button);
        mRightImg1 = (ImageView) view.findViewById(R.id.img_btn1);
        mRightImg2 = (ImageView) view.findViewById(R.id.img_btn2);
        mDotBadgeView = (MaterialBadgeTextView) view.findViewById(R.id.tips_title);
        mBacView = view.findViewById(R.id.bac_view);
        mDotBadgeView.setHighLightMode();
        mDotBadgeView.setVisibility(INVISIBLE);
        //        mDotBadgeView.setVisibility(GONE);
    }

    /**
     * 设置根目录背景颜色
     *
     * @param color
     */
    public void setBackgroundClor(int color){
        mLayout.setBackgroundColor(color);
    } /**
     * 设置根目录背景颜色
     *
     * @param color
     */
    public void setBackgroundResourceId(int color){
        mLayout.setBackgroundResource(color);
    }

    /**
     * 设置根目录背景图片
     *
     * @param url
     */
    public void setBackgroundImg(String url){
        ImageDisplayManager.getInstance().loadImage(mImgTitlebac, url);
    }

    public void setBackgroundImgVisibility(){
        mImgTitlebac.setVisibility(VISIBLE);
        mBacView.setVisibility(VISIBLE);
    }

    /**
     * 设置根目录背景颜色
     *
     * @param resouce
     */
    @Override
    public void setBackgroundResource(int resouce){
        mLayout.setBackgroundResource(resouce);
    }

    /**
     * 隐藏整个view
     */
    public void hidden(){
        this.setVisibility(View.GONE);
    }

    /**
     * 设置头部标题资源背景颜色
     *
     * @param resouceId
     */
    public void setMiddleBackgroundResouce(int resouceId){
        mComtopTitle.setText("");
        mComtopTitle.setBackgroundResource(resouceId);
    }

    /**
     * 设置标题内容
     *
     * @param title
     */
    public void setTitle(String title){
        mComtopTitle.setText(title);
        mComtopTitle.setVisibility(VISIBLE);
    }

    /**
     * 设置标题字体颜色
     */
    public void setTitleColor(){
        mComtopTitle.setTextColor(Color.WHITE);
    }

    /**
     * @param resourceId
     */
    public void setTitle(int resourceId){
        mComtopTitle.setText(resourceId);
    }

    public String getTitle(){
        return mComtopTitle.getText().toString();
    }

    /**
     * 隐藏返回按钮
     */
    public void hiddenRebackButton(){
        mComtopReturn.setVisibility(View.GONE);
    }

    /**
     * 设置返回按钮的图片资源
     *
     * @param resourceId
     */
    public void setRebackResource(int resourceId){
        mComtopReturn.setVisibility(View.VISIBLE);
        mComtopReturn.setImageResource(resourceId);
    }

    /**
     * 隐藏左边按钮
     */
    public void hiddenLeftButton(){
        mCustomerLeftButton.setVisibility(View.GONE);
    }

    /**
     * 得到左边按钮
     */
    public TextView getleftButton(){
        return mCustomerLeftButton;
    }

    /**
     * 设置左边字体颜色
     *
     * @param color
     */
    public void setleftTextColor(int color){
        mCustomerLeftButton.setTextColor(color);
    }

    /**
     * 设置左边按钮文字
     *
     * @param text
     */
    public void setleftText(String text){
        mCustomerLeftButton.setVisibility(View.VISIBLE);
        mCustomerLeftButton.setText(text);
    }

    /**
     * 设置左边按钮文字
     *
     * @param resourceId
     */
    public void setLeftText(int resourceId){
        mCustomerLeftButton.setVisibility(View.VISIBLE);
        mCustomerLeftButton.setText(resourceId);
    }

    /**
     * 隐藏右边按钮
     */
    public void hiddenRightButton(){
        mCustomerRightButton.setVisibility(View.GONE);
    }

    /**
     * 获取右边按钮
     */
    public TextView getRightButton(){
        return mCustomerRightButton;
    }

    /**
     * 设置右边字体颜色
     *
     * @param color
     */
    public void setRightTextColor(int color){
        mCustomerRightButton.setTextColor(color);
    }

    /**
     * 设置右边按钮文字
     *
     * @param text
     */
    public void setRightText(String text){
        mCustomerRightButton.setVisibility(View.VISIBLE);
        mCustomerRightButton.setText(text);
    }

    /**
     * 设置右边按钮文字
     *
     * @param resourceId
     */
    public void setRightText(int resourceId){
        mCustomerRightButton.setVisibility(View.VISIBLE);
        mCustomerRightButton.setText(resourceId);
    }

    /**
     * 显示右边图片按钮1
     *
     * @param resouce
     */
    public void setRightImageResouce1(int resouce){
        mRightImg1.setVisibility(View.VISIBLE);
        mRightImg1.setImageResource(resouce);
    }

    /**
     * 显示右边图片按钮2
     *
     * @param resouce
     */
    public void setRightImageResouce2(int resouce){
        mRightImg2.setVisibility(View.VISIBLE);
        mRightImg2.setImageResource(resouce);
    }

    /**
     * 显示右边图片按钮1点击事件
     *
     * @param pOnClickListener
     */
    public void setRightImageClick1(OnClickListener pOnClickListener){
        mRightImg1.setOnClickListener(pOnClickListener);
    }

    public void setRigthImage1Visibility(int visibility){
        mRightImg1.setVisibility(visibility);
    }

    /**
     * 显示右边图片按钮2点击事件
     *
     * @param pOnClickListener
     */
    public void setRightImageClick2(OnClickListener pOnClickListener){
        mRightImg2.setOnClickListener(pOnClickListener);
    }

    /**
     * 设置右文字的点击事件
     *
     * @param pOnClickListener
     */
    public void setRightTextClick(OnClickListener pOnClickListener){
        mCustomerRightButton.setOnClickListener(pOnClickListener);
    }

    public void switchRightTextNotClick(){
        mCustomerRightButton.setEnabled(false);
        try{
            mCustomerRightButton.setTextColor(Color.parseColor("#bbbbbb"));
        }catch(Exception pE){
            pE.printStackTrace();
        }
    }

    public void switchRightTextClickable(){
        mCustomerRightButton.setEnabled(true);
        try{
            mCustomerRightButton.setTextColor(Color.parseColor("#282828"));
        }catch(Exception pE){
            pE.printStackTrace();
        }
    }

    public void switchRightTextClickable(String color){
        mCustomerRightButton.setEnabled(true);
        try{
            mCustomerRightButton.setTextColor(Color.parseColor(color));
        }catch(Exception pE){
            pE.printStackTrace();
        }
    }

    /**
     * 设置最左image按钮点击事件
     *
     * @param onClickListener
     */
    public void setBackClick(OnClickListener onClickListener){
        mComtopBack.setOnClickListener(onClickListener);
    }

    public void showLeftImg1Dot(boolean isShow){
        if(isShow){
            mDotBadgeView.show();
        }else{
            mDotBadgeView.hide();
        }
    }

    public boolean isDotShow(){
        if(mDotBadgeView == null){
            return false;
        }else{
            return mDotBadgeView.isShown();
        }
    }

    /**
     * hidden left return button
     */
    public void hiddenReturnButton(){
        mComtopReturn.setVisibility(View.GONE);
    }

    /**
     * hidden left return button
     */
    public void showReturnButton(){
        mComtopReturn.setVisibility(View.VISIBLE);
    }

    public void setModel(TitleModule module){
        ViewBean vb = module.getLeftImgVB1();
        if(!CheckUtil.isNull(vb) && vb.visibleId != View.GONE){
            mComtopBack.setImageResource(vb.resId);
        }else{
            vb = new ViewBean();
        }
        mComtopBack.setVisibility(vb.visibleId);
        //默认隐藏所以可以为空
        vb = module.getLeftImgVB2();
        if(!CheckUtil.isNull(vb) && vb.visibleId != View.GONE){
            mComtopReturn.setImageResource(vb.resId);
        }else{
            vb = new ViewBean();
        }
        mComtopReturn.setVisibility(vb.visibleId);
        //默认隐藏所以可以为空
        vb = module.getLeftTxtVB();
        if(!CheckUtil.isNull(vb) && vb.visibleId != View.GONE){
            mCustomerLeftButton.setText(vb.resId);
        }else{
            vb = new ViewBean();
        }
        mCustomerLeftButton.setVisibility(vb.visibleId);
        vb = module.getTitleVB();
        if(!CheckUtil.isNull(vb) && vb.visibleId != View.GONE){
            mComtopTitle.setText(vb.resId);
        }else{
            vb = new ViewBean();
        }
        mComtopTitle.setVisibility(vb.visibleId);
        //默认隐藏所以可以为空
        vb = module.getRightTxtVB();
        if(!CheckUtil.isNull(vb) && vb.visibleId != View.GONE){
            mCustomerRightButton.setText(vb.resId);
        }else{
            vb = new ViewBean();
        }
        mCustomerRightButton.setVisibility(vb.visibleId);
        //默认隐藏所以可以为空
        vb = module.getRightImgVB1();
        if(!CheckUtil.isNull(vb) && vb.visibleId != View.GONE){
            mRightImg1.setImageResource(vb.resId);
        }else{
            vb = new ViewBean();
        }
        mRightImg1.setVisibility(vb.visibleId);
        //默认隐藏所以可以为空
        vb = module.getRightImgVB2();
        if(!CheckUtil.isNull(vb) && vb.visibleId != View.GONE){
            mRightImg2.setImageResource(vb.resId);
        }else{
            vb = new ViewBean();
        }
        mRightImg2.setVisibility(vb.visibleId);
    }

    /**
     * 清楚资源状态
     */
    public void clearResource(){
        setModel(TitleModuleBuilder.builder
                .setRightImgVB1(null)
                .setRightImgVB2(null)
                .setRightTxtVB(null)
                .setLeftTxtVB(null)
                .setLeftImgVB1(null)
                .setLeftImgVB2(null)
                .setTitleVB(null)
                .createTitleModule());
    }

    public void setViewGone(int... ids){
        for(int lI : ids){
            getView(lI).setVisibility(GONE);
        }
    }

    public final <E extends View> E getView(int id){
        try{
            return (E) findViewById(id);
        }catch(ClassCastException ex){
            Log.e("BaseAdapterItem", "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }
}