package com.common.common_base.widget.toolbar;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.common_base.R;
import com.common.common_base.utils.ViewUtil;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/6
 * @desc :
 */

public class CommonToolbar extends LinearLayout{

    private Toolbar mToolbar;
    private TextView mTvTitle;

    public CommonToolbar(Context context){
        super(context, null);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs){
        super(context, attrs, 0);
    }

    public CommonToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View lView = ViewUtil.getView(getContext(), R.layout.common_toolbar_layout, this);
        mToolbar = ((Toolbar) lView.findViewById(R.id.toolbar));
        mTvTitle = ((TextView) lView.findViewById(R.id.tv_center_title));
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

    public void setToolbarModle(ToolbarModel toolbarModle){

    }
}
