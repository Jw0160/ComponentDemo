package com.common.jwrouterdemo.activity.test_transition;

import android.os.Bundle;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.utils.util.FragmentUtils;
import com.common.common_base.widget.toolbar.ToolbarBean;
import com.common.common_base.widget.toolbar.ToolbarModel;
import com.common.jwrouterdemo.R;
import com.common.jwrouterdemo.activity.TestCommonToolbarActivity;
import com.common.jwrouterdemo.activity.test_transition.fragment.MainFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/5
 * @desc :
 */

public class TransitionActivity extends BaseActivity{

    private long mNowMills, mLastMills;

    @Override
    public int getContentViewId(){
        return R.layout.activity_transition_layout;
    }

    @Override
    public void initBundleData(){
        initToolBar(new ToolbarModel.Builder().setTitle(new ToolbarBean(R.string.sms)).create());
        FragmentUtils.add(getSupportFragmentManager(), MainFragment.newInstance(null), R.id.container);
    }

    @Override
    public void initData(){
    }


    @OnClick(R.id.btn_in)
    public void onViewClicked(){
        enterNextActivity(TestCommonToolbarActivity.class);
    }
}
