package com.common.common_base.mvpbase;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.base.BaseFragment;
import com.common.common_base.base.BaseFragmentActivity;
import com.common.common_base.listener.LifeCycleListener;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/1
 * @desc :
 */

public class BasePresenter<T extends IBaseView> implements IBasePresenter, LifeCycleListener{
    private Reference<T> mActivityRef;
    protected T mActivity;

    public BasePresenter(T activity){
        attachActivity(activity);
        setListener(activity);
    }

    /**
     * 关联
     *
     * @param activity
     */
    private void attachActivity(T activity){
        mActivityRef = new WeakReference<T>(activity);
        mActivity = mActivityRef.get();
    }

    /**
     * 销毁
     */
    private void detachActivity(){
        if(isActivityAttached()){
            mActivityRef.clear();
            mActivityRef = null;
        }
    }

    /**
     * 获取
     *
     * @return
     */
    public T getActivity(){
        if(mActivityRef == null){
            return null;
        }
        return mActivityRef.get();
    }

    /**
     * 是否已经关联
     */
    public boolean isActivityAttached(){
        return mActivityRef != null && mActivityRef.get() != null;
    }

    /**
     * 设置生命周期监听
     */
    private void setListener(T activity){
        if(getActivity() != null){
            if(activity instanceof BaseActivity){
                ((BaseActivity) getActivity()).setOnLifeCycleListener(this);
            }else if(activity instanceof BaseFragmentActivity){
                ((BaseFragmentActivity) getActivity()).setOnLifeCycleListener(this);
            }else if(activity instanceof BaseFragment){
                ((BaseFragment) getActivity()).setOnLifeCycleListener(this);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
    }

    @Override
    public void onStart(){
    }

    @Override
    public void onRestart(){
    }

    @Override
    public void onResume(){
    }

    @Override
    public void onPause(){
    }

    @Override
    public void onStop(){
    }

    @Override
    public void onDestroy(){
        detachActivity();
    }
}
