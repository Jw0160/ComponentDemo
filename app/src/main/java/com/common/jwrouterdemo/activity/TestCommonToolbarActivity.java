package com.common.jwrouterdemo.activity;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.widget.toolbar.ToolbarBean;
import com.common.common_base.widget.toolbar.ToolbarModel;
import com.common.jwrouterdemo.R;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/6
 * @desc :
 */

public class TestCommonToolbarActivity extends BaseActivity{
    @Override
    public int getContentViewId(){
        return R.layout.activity_test_common_toolbar;
    }

    @Override
    public void initBundleData(){
        ToolbarModel lToolbarModel = new ToolbarModel.Builder()
//                .setTitle(new ToolbarBean(R.string.load_end))
                .setTvTitleName(new ToolbarBean(R.string.camera))
                .create();
        initToolBar(lToolbarModel);
    }

    @Override
    public void initData(){
    }
}
