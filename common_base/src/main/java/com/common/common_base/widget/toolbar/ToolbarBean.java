package com.common.common_base.widget.toolbar;

import android.view.View;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/6
 * @desc :
 */

public class ToolbarBean{
    private int resId = 0;
    private int visibleId = View.GONE;

    public ToolbarBean(int resId) {
        this.resId = resId;
        this.visibleId = View.VISIBLE;
    }

    public ToolbarBean() {
        this.resId = -1;
        this.visibleId = View.GONE;
    }

    public int getResId(){
        return resId;
    }

    public void setResId(int resId){
        this.resId = resId;
    }

    public int getVisibleId(){
        return visibleId;
    }

    public void setVisibleId(int visibleId){
        this.visibleId = visibleId;
    }
}
