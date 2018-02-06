package com.common.common_base.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.common.common_base.R;
import com.common.common_base.listener.LifeCycleListener;
import com.common.common_base.utils.system.SystemBarTintManager;
import com.common.common_base.utils.util.LogUtils;
import com.common.common_base.utils.system.AppManagerUtil;
import com.common.common_base.utils.system.KeyBoardUtil;
import com.common.common_base.widget.titlebar.CommonTitleBar;
import com.common.common_base.widget.toolbar.ToolbarModel;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrInterface;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/25
 * @desc :
 */

public abstract class BaseActivity extends RxAppCompatActivity implements BaseConfigInterface{
    protected Context mContext;
    protected Unbinder unBinder;
    /**
     * 全局头部栏
     */
    protected CommonTitleBar mTitleBar;
    private String TAG;
    protected SlidrInterface mSlidrInterface;

    /**
     * 回调函数
     */
    public LifeCycleListener mListener;
    private Toolbar mCommonToolbar;
    private TextView mTvCenterTitle;

    public void setOnLifeCycleListener(LifeCycleListener listener){
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        LogUtils.e(TAG + ":onCreate");
        if(mListener != null){
            mListener.onCreate(savedInstanceState);
        }
        AppManagerUtil.getInstance().addActivity(this);
        setContentView(getContentViewId());
        mContext = this;
        unBinder = ButterKnife.bind(this);
        try{
            mTitleBar = (CommonTitleBar) findViewById(R.id.title_bar);
        }catch(Exception e){
            e.printStackTrace();
        }
        getCommonToolBar();
        //        initSlidable();
        //        initSystemBar(this);
        initBundleData();
        initData();
    }

    private void getCommonToolBar(){
        try{
            mCommonToolbar = ((Toolbar) this.findViewById(R.id.toolbar));
            mTvCenterTitle = ((TextView) this.findViewById(R.id.tv_center_title));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    protected void initToolBar(ToolbarModel toolbarModel){
        if(toolbarModel.getTitle() != null && toolbarModel.getTitle().getVisibleId() != View.GONE){
            mCommonToolbar.setTitle(toolbarModel.getTitle().getResId());
            mCommonToolbar.setForegroundGravity(Gravity.CENTER);
        }else{
            mCommonToolbar.setTitle("");
        }
        if(toolbarModel.getLogo() != null && toolbarModel.getLogo().getVisibleId() != View.GONE){
            mCommonToolbar.setLogo(toolbarModel.getLogo().getResId());
        }
        if(toolbarModel.getSubtitle() != null && toolbarModel.getSubtitle().getVisibleId() != View.GONE){
            mCommonToolbar.setSubtitle(toolbarModel.getSubtitle().getResId());
        }
        if(toolbarModel.getTvTitleName() != null && toolbarModel.getTvTitleName().getVisibleId() != View.GONE){
            if(mTvCenterTitle != null){
                mTvCenterTitle.setVisibility(View.VISIBLE);
                mTvCenterTitle.setText(toolbarModel.getTvTitleName().getResId());
            }else{
                mTvCenterTitle.setVisibility(View.GONE);
            }
        }
        setSupportActionBar(mCommonToolbar);
        if(toolbarModel.getNavigationIcon() != null && toolbarModel.getNavigationIcon().getVisibleId() != View.GONE){
            mCommonToolbar.setNavigationIcon(toolbarModel.getNavigationIcon().getResId());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * @param activity 当前活动Activtiy实例
     *                 void
     */
    protected void initSystemBar(Activity activity){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setTranslucentStatus(activity, true);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        tintManager.setStatusBarTintEnabled(true);
        // 使用颜色资源
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setStatusBarTintColor(Color.parseColor("#00000000"));
    }

    /**
     * 设置系统顶部栏和程序主题颜色统一
     *
     * @param activity 当前活动Activity实例
     * @param on       void
     */
    @TargetApi(19)
    private void setTranslucentStatus(Activity activity, boolean on){
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if(on){
            winParams.flags |= bits;
        }else{
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    private void initSlidable(){
        SlidrConfig config = new SlidrConfig.Builder()
                //                .primaryColor(Color.BLUE)
                //                .secondaryColor(getResources().getColor())
                //                                .position(SlidrPosition.LEFT|RIGHT|TOP|BOTTOM|VERTICAL|HORIZONTAL)
                //                .sensitivity(1f)
                //                .scrimColor(Color.BLACK)
                //                .scrimStartAlpha(0.8f)
                //                .scrimEndAlpha(0f)
                //                .velocityThreshold(2400)
                //                .distanceThreshold(0.25f)
                .edge(true)
                //                .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%
                .build();
        mSlidrInterface = Slidr.attach(this, config);
    }

    @Override
    protected void onStart(){
        super.onStart();
        if(mListener != null){
            mListener.onStart();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        if(mListener != null){
            mListener.onRestart();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(mListener != null){
            mListener.onResume();
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(mListener != null){
            mListener.onPause();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(mListener != null){
            mListener.onStop();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mListener != null){
            mListener.onDestroy();
        }
        //移除view绑定
        if(unBinder != null){
            unBinder.unbind();
        }
        eventDestroy();
        AppManagerUtil.getInstance().finishActivity(this);
    }

    protected void eventRegister(){
        // 在要接收消息的页面的OnCreate()中注册EventBus
        try{
            if(!EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().register(this);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void eventDestroy(){
        // 在OnDestroy()方法中解注册EventBus
        try{
            if(EventBus.getDefault().isRegistered(this)){
                EventBus.getDefault().unregister(this);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev){
        if(ev.getAction() == MotionEvent.ACTION_DOWN){
            View v = getCurrentFocus();
            if(KeyBoardUtil.isShouldHideKeyboard(v, ev)){
                KeyBoardUtil.hideKeyboard(v.getWindowToken(), mContext);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}
