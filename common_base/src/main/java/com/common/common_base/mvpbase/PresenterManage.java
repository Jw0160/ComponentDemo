package com.common.common_base.mvpbase;

import android.os.Bundle;

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

public class PresenterManage<T extends IBaseView> implements LifeCycleListener{
    private Reference<T> mViewRef;
    protected T mView;

    public PresenterManage(T target){
        attachTarget(target);
        setListener(target);
    }

    /**
     * 关联
     *
     * @param target
     */
    private void attachTarget(T target){
        mViewRef = new WeakReference<T>(target);
        mView = mViewRef.get();
    }

    /**
     * 销毁
     */
    private void detachTarget(){
        if(isTargetAttached()){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 获取
     *
     * @return
     */
    public T getView(){
        if(mViewRef == null){
            return null;
        }
        return mViewRef.get();
    }

    /**
     * 是否已经关联
     */
    public boolean isTargetAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }

    /**
     * 设置生命周期监听
     */
    private void setListener(T listener){
        if(getView() != null){
            if(listener instanceof BaseActivity){
                ((BaseActivity) getView()).setOnLifeCycleListener(this);
            }else if(listener instanceof BaseFragmentActivity){
                ((BaseFragmentActivity) getView()).setOnLifeCycleListener(this);
            }else if(listener instanceof BaseFragment){
                ((BaseFragment) getView()).setOnLifeCycleListener(this);
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
        detachTarget();
    }
}
