package com.common.common_base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.common.common_base.R;
import com.common.common_base.listener.LifeCycleListener;
import com.common.common_base.utils.util.LogUtils;
import com.common.common_base.utils.system.KeyBoardUtil;
import com.common.common_base.widget.titlebar.CommonTitleBar;
import com.trello.rxlifecycle2.components.support.RxFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/25
 * @desc :
 */

public abstract class BaseFragment extends RxFragment implements BaseConfigInterface{
    /**
     * Fragment运行时的Context
     */
    protected BaseFragmentActivity mContext;
    /**
     * Fragment根View
     */
    protected View rootView;
    /**
     * 全局头部栏
     */
    protected CommonTitleBar mTitleBar;
    private Unbinder unbinder;
    public String TAG = "";

    /**
     * 回调函数
     */
    public LifeCycleListener mListener;

    public void setOnLifeCycleListener(LifeCycleListener listener){
        mListener = listener;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.mContext = (BaseFragmentActivity) context;
    }

    /**
     * 重写父类方法，完成绑定界面布局，注解界面
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     * @see Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        TAG = this.getClass().getSimpleName();
        LogUtils.e(TAG + ":onCreateView");
        rootView = inflater.inflate(getContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        try{
            mTitleBar = (CommonTitleBar) getContentView().findViewById(R.id.title_bar);
        }catch(Exception e){
            e.printStackTrace();
        }
        rootView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent ev){
                if(ev.getAction() == MotionEvent.ACTION_DOWN){
                    View v = mContext.getCurrentFocus();
                    if(KeyBoardUtil.isShouldHideKeyboard(v, ev)){
                        KeyBoardUtil.hideKeyboard(v.getWindowToken(), mContext);
                    }
                }
                return false;
            }
        });

        return rootView;
    }

    /**
     * 获取Fragment当前布局实例
     *
     * @return View 返回View的实例
     */
    public View getContentView(){
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(mListener != null){
            mListener.onCreate(savedInstanceState);
        }
        initBundleData();
        initData();
    }

    @Override
    public void onDestroy(){
        eventDestroy();
        super.onDestroy();
        if(mListener != null){
            mListener.onDestroy();
        }
    }

    @Override
    public void onDestroyView(){
        try{
            unbinder.unbind();
        }catch(Exception pE){
        }
        super.onDestroyView();
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
}
