package com.common.jwrouterdemo.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.common_base.base.BaseActivity;
import com.common.jwrouterdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/26
 * @desc :
 */

public class TestAvatarImageActivity extends BaseActivity{
    @BindView(R.id.ll_title)
    LinearLayout mLlTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getContentViewId(){
        return R.layout.activity_test_image;
    }

    @Override
    public void initBundleData(){
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData(){
    }


}
