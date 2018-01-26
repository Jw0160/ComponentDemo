package com.common.common_base.widget.titlebar;

import android.view.View;


public class ViewBean {
    public int resId = 0;
    public int visibleId = View.GONE;

    public ViewBean(int resId) {
        this.resId = resId;
        this.visibleId = View.VISIBLE;
    }

    public ViewBean() {
        this.resId = -1;
        this.visibleId = View.GONE;
    }
}
