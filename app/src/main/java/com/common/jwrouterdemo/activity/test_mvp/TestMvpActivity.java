package com.common.jwrouterdemo.activity.test_mvp;

import android.view.View;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.base.BaseFragmentActivity;
import com.common.common_base.utils.util.LogUtils;
import com.common.jwrouterdemo.R;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/1
 * @desc :
 */

public class TestMvpActivity extends BaseFragmentActivity{

    @Override
    public int getContentViewId(){
        return R.layout.activity_test_mvp;
    }

    @Override
    public void initBundleData(){
        getSupportFragmentManager().beginTransaction().add(R.id.container, new TestMvoFragment()).commit();
    }

    @Override
    public void initData(){
    }

    public void onMvpClick(View view){
    }
}
