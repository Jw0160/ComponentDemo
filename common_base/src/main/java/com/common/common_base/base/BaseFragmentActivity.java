package com.common.common_base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;

import com.common.common_base.R;
import com.common.common_base.utils.util.LogUtils;
import com.common.common_base.utils.system.AppManagerUtil;
import com.common.common_base.utils.system.KeyBoardUtil;
import com.common.common_base.widget.titlebar.CommonTitleBar;
import com.trello.rxlifecycle2.components.support.RxFragmentActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/25
 * @desc :
 */

public abstract class BaseFragmentActivity extends RxFragmentActivity implements BaseActivityInterface{
    protected Context mContext;
    protected Unbinder unBinder;
    private String TAG;

    /**
     * 全局头部栏
     */
    protected CommonTitleBar mTitleBar;

    /**
     * 回调函数
     */
    //    public LifeCycleListener mListener;
    //
    //    public void setOnLifeCycleListener(LifeCycleListener listener){
    //        mListener = listener;
    //    }
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        TAG = this.getClass().getSimpleName();
        LogUtils.e(TAG + ":onCreate");
        //        if(mListener != null){
        //            mListener.onCreate(savedInstanceState);
        //        }
        AppManagerUtil.getInstance().addActivity(this);
        setContentView(getContentViewId());
        mContext = this;
        unBinder = ButterKnife.bind(this);
        try{
            mTitleBar = (CommonTitleBar) findViewById(R.id.title_bar);
        }catch(Exception e){
            e.printStackTrace();
        }
        initBundleData();
        initData();
    }

    @Override
    protected void onStart(){
        super.onStart();
        //        if(mListener != null){
        //            mListener.onStart();
        //        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        //        if(mListener != null){
        //            mListener.onRestart();
        //        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        //        if(mListener != null){
        //            mListener.onResume();
        //        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        //        if(mListener != null){
        //            mListener.onPause();
        //        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        //        if(mListener != null){
        //            mListener.onStop();
        //        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //        if(mListener != null){
        //            mListener.onDestroy();
        //        }
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
