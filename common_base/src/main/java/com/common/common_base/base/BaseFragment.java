package com.common.common_base.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.common.common_base.R;
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

public abstract class BaseFragment extends RxFragment implements BaseActivityInterface{
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

    protected boolean isVisible = false;//当前Fragment是否可见
    protected boolean isInitView = false;//是否与View建立起映射关系
    protected boolean parentIsVisible = true;//是否与View建立起映射关系
    protected boolean isFirstLoad = true;//是否是第一次加载数据

    private Unbinder unbinder;
    public String TAG = "";

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        TAG = this.getClass().getSimpleName();
        LogUtils.e(TAG + ":onCreateView");
        rootView = inflater.inflate(getContentViewId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        try{
            mTitleBar= (CommonTitleBar) getContentView().findViewById(R.id.title_bar);
        }catch(Exception e){
            e.printStackTrace();
        }
        isInitView = true;
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
     *  获取Fragment当前布局实例
     *
     * @return View 返回View的实例
     */
    public View getContentView() {
        return rootView;
    }
    /**
     * 重写父类方法，完成初始化类的工作，调用 initView();requestServiceHook();initData();
     *
     * @param savedInstanceState
     * @see Fragment#onActivityCreated(Bundle)
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        eventRegister();
        initBundleData();
        lazyLoad();
    }

    @Override
    public void onDestroy(){
        eventDestroy();
        super.onDestroy();
    }

    @Override
    public void onDestroyView(){
        try{
            unbinder.unbind();
        }catch(Exception pE){
        }
        super.onDestroyView();
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser){
        //        LogUtil.e(TAG, "setUserVisibleHint() called with: ");
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()){
            isVisible = true;
            onVisible();
        }else{
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible(){
        if(mContext == null){
            return;
        }
        lazyLoad();
    }

    protected void lazyLoad(){
        if(isFirstLoad){
            LogUtils.d("第一次加载 " + " isInitView  " + isInitView + "  isVisible  " + isVisible + "   " + this.getClass().getSimpleName());
        }else{
            LogUtils.d("不是第一次加载" + " isInitView  " + isInitView + "  isVisible  " + isVisible + "   " + this.getClass().getSimpleName());
        }
        BaseFragment parentFragment = (BaseFragment) getParentFragment();//如果是嵌套fragment
        if(parentFragment != null){
            LogUtils.d("有父fragment");
            if(parentFragment.getVisible()){
                LogUtils.d("父fragment 是显示状态");
                isVisible = true;
            }else{
                LogUtils.d("父fragment 不是显示状态," + "不加载" + "   " + this.getClass().getSimpleName());
                if(isFirstLoad){ //如果是第一次加载，而且父fragment没有在显示状态，这不加载数据，但是如果不是第一次加载之后，则不要重复进入该判断条件
                    parentIsVisible = false;
                    return;
                }
            }
        }else{//如果没有父亲fragment，表示第一集fragment
            EventBus.getDefault().post(TAG);//这发送广播，通知子fragment可能要刷新
        }
        if(!isFirstLoad || !isVisible || !isInitView){
            LogUtils.d("不加载" + "   " + this.getClass().getSimpleName());
            return;
        }
        isFirstLoad = false;
        LogUtils.d("完成数据第一次加载");
        initData();
    }

    public boolean getVisible(){
        return isVisible;
    }

    protected void onInvisible(){
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onActionEvent(String fragmentAction){
        LogUtils.e("fragmentAction called with: ");
        if(fragmentAction.equals(TAG)){
            BaseFragment parentFragment = (BaseFragment) getParentFragment();//如果是嵌套fragment
            if(parentFragment != null){
                if(parentFragment.getVisible() && !parentIsVisible){//父fragment 从隐身状态 切换到显示状态
                    LogUtils.e(this.getClass().getSimpleName() + "need refresh ");
                    parentIsVisible = true;
                    initData();
                }
            }
        }
    }
}
