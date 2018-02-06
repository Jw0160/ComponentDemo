package com.common.jwrouterdemo.activity.test_transition;

import android.support.v7.widget.Toolbar;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.utils.util.FragmentUtils;
import com.common.common_base.utils.util.TimeUtils;
import com.common.common_base.utils.util.ToastUtils;
import com.common.common_base.widget.toolbar.ToolbarBean;
import com.common.common_base.widget.toolbar.ToolbarModel;
import com.common.jwrouterdemo.R;
import com.common.jwrouterdemo.activity.test_transition.fragment.MainFragment;

import butterknife.BindView;

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
        initToolBar(new ToolbarModel.Builder().setTitle(new ToolbarBean(R.string.camera)).create());
        FragmentUtils.add(getSupportFragmentManager(), MainFragment.newInstance(null), R.id.container);
    }

    @Override
    public void initData(){
    }
}
