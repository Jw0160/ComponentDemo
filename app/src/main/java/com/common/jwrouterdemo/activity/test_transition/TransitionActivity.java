package com.common.jwrouterdemo.activity.test_transition;

import android.support.v7.widget.Toolbar;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.utils.util.FragmentUtils;
import com.common.common_base.utils.util.TimeUtils;
import com.common.common_base.utils.util.ToastUtils;
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
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private long mNowMills, mLastMills;

    @Override
    public int getContentViewId(){
        return R.layout.activity_transition_layout;
    }

    @Override
    public void initBundleData(){
        initToolBar(mToolbar, "transition");
        FragmentUtils.add(getSupportFragmentManager(), MainFragment.newInstance(null), R.id.container);
    }

    @Override
    public void initData(){
    }


}
