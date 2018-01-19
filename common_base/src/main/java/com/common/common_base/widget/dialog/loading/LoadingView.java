package com.common.common_base.widget.dialog.loading;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.common.common_base.R;

/**
 * Created by zzz40500 on 15/4/6.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint("NewApi")
public class LoadingView extends RelativeLayout{
    private static final int ANIMATION_DURATION = 500;
    private static float mDistance = 200;
    private ShapeLoadingView mShapeLoadingView;
    private ImageView mIndicationIm;
    private TextView mLoadTextView;
    private int mTextAppearance;
    private String mLoadText;

    public LoadingView(Context context){
        this(context, null);
    }

    @Override
    public void setVisibility(int visibility){
        super.setVisibility(visibility);
        // visibility == View.GONE停止动画
        if(visibility == View.GONE || visibility == View.INVISIBLE){
            mIndicationIm.clearAnimation();
            mShapeLoadingView.clearAnimation();
        }
    }

    public LoadingView(Context context, AttributeSet attrs){
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initLayout();
    }

    private void initLayout(){
        inflate(getContext(), R.layout.load_view, this);
        mDistance = 100;
        mShapeLoadingView = (ShapeLoadingView) findViewById(R.id.shapeLoadingView);
        mIndicationIm = (ImageView) findViewById(R.id.indication);
        mLoadTextView = (TextView) findViewById(R.id.promptTV);
        if(mTextAppearance != -1){
            // android:textAppearance设置文字外观。
            // 如 “?android:attr/textAppearanceLargeInverse”
            // 这里引用的是系统自带的一个外观，?表示系统是否有这种外观，否则使用默认的外观。
            // 可设置的值如下：textAppearanceButton/textAppearanceInverse/textAppearanceLarge
            // /textAppearanceLargeInverse/textAppearanceMediu
            mLoadTextView.setTextAppearance(getContext(), mTextAppearance);
        }
        setLoadingText(mLoadText);
        this.postDelayed(new Runnable(){
            @Override
            public void run(){
                freeFall();
            }
        }, 200);
    }

    /**
     * 初始化参数
     *
     * @param context
     * @param attrs
     */
    private void init(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.LoadingView);
        mLoadText = typedArray.getString(R.styleable.LoadingView_loadingText);
        mTextAppearance = typedArray.getResourceId(
                R.styleable.LoadingView_loadingTextAppearance, -1);
        typedArray.recycle();
    }

    public void setLoadingText(CharSequence loadingText){
        if(TextUtils.isEmpty(loadingText)){
            mLoadTextView.setVisibility(GONE);
        }else{
            mLoadTextView.setVisibility(VISIBLE);
        }
        mLoadTextView.setText(loadingText);
    }

    /**
     * 上抛
     */
    public void upThrow(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                mShapeLoadingView, "translationY", mDistance, 0);
        ObjectAnimator scaleIndication = ObjectAnimator.ofFloat(mIndicationIm,
                "scaleX", 0.2f, 1);
        ObjectAnimator objectAnimator1 = null;
        switch(mShapeLoadingView.getShape()){
            case SHAPE_RECT:
                objectAnimator1 = ObjectAnimator.ofFloat(mShapeLoadingView,
                        "rotation", 0, -120);
                break;
            case SHAPE_CIRCLE:
                objectAnimator1 = ObjectAnimator.ofFloat(mShapeLoadingView,
                        "rotation", 0, 180);
                break;
            case SHAPE_TRIANGLE:
                objectAnimator1 = ObjectAnimator.ofFloat(mShapeLoadingView,
                        "rotation", 0, 180);
                break;
        }
        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator1.setDuration(ANIMATION_DURATION);
        objectAnimator.setInterpolator(new DecelerateInterpolator(factor));
        objectAnimator1.setInterpolator(new DecelerateInterpolator(factor));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.playTogether(objectAnimator, objectAnimator1,
                scaleIndication);
        animatorSet.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation){
            }

            @Override
            public void onAnimationEnd(Animator animation){
                freeFall();
            }

            @Override
            public void onAnimationCancel(Animator animation){
            }

            @Override
            public void onAnimationRepeat(Animator animation){
            }
        });
        animatorSet.start();
    }

    public float factor = 1.2f;

    /**
     * 下落
     */
    public void freeFall(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(
                mShapeLoadingView, "translationY", 0, mDistance);
        ObjectAnimator scaleIndication = ObjectAnimator.ofFloat(mIndicationIm,
                "scaleX", 1, 0.2f);
        objectAnimator.setDuration(ANIMATION_DURATION);
        objectAnimator.setInterpolator(new AccelerateInterpolator(factor));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.playTogether(objectAnimator, scaleIndication);
        animatorSet.addListener(new Animator.AnimatorListener(){
            @Override
            public void onAnimationStart(Animator animation){
            }

            @Override
            public void onAnimationEnd(Animator animation){
                mShapeLoadingView.changeShape();
                upThrow();
            }

            @Override
            public void onAnimationCancel(Animator animation){
            }

            @Override
            public void onAnimationRepeat(Animator animation){
            }
        });
        animatorSet.start();
    }
}
