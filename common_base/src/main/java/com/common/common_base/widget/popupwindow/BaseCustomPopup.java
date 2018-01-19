package com.common.common_base.widget.popupwindow;

import android.content.Context;
import android.view.View;

/**
 * Created by zyyoona7 on 2017/8/4.
 * 自定义PopupWindow使用
 */

public abstract class BaseCustomPopup extends EasyPopup{
    private static final String TAG = "BaseCustomPopup";

    protected BaseCustomPopup(Context context){
        super(context);
    }

    @Override
    public void onPopupWindowCreated(){
        super.onPopupWindowCreated();
        //执行设置PopupWindow属性也可以通过Builder中设置
        //setContentView(x,x,x);
        //...
        initAttributes();
    }

    @Override
    public void onPopupWindowViewCreated(View contentView){
        initViews(contentView);
    }

    @Override
    public void onPopupWindowDismiss(){
    }

    /**
     * 可以在此方法中设置PopupWindow需要的属性
     * eg:
     * setContentView(R.layout.layout_complex,
     * ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(300));
     * setFocusAndOutsideEnable(false)
     * .setBackgroundDimEnable(true)
     * .setDimValue(0.5f);
     */
    protected abstract void initAttributes();

    /**
     * 初始化view {@see getView()}
     * getView()获得布局文件中的View,配置功能
     *
     * @param view
     */
    protected abstract void initViews(View view);
}
