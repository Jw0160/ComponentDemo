package com.common.jwrouterdemo.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;

import com.common.common_base.utils.util.LogUtils;
import com.common.common_base.base.PermissionsActivity;
import com.common.jwrouterdemo.R;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * @author : wangb
 * @email :
 * @date : 2018/1/3
 * @desc :
 */

public class TestPermissonsActivity extends PermissionsActivity{
    @Override
    public int getContentViewId(){
        return R.layout.activity_test_permissions;
    }

    @Override
    public void initData(){
    }

    @Override
    public void initBundleData(){
    }

    @Override
    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions){
        LogUtils.e("succeed...............");
    }

    public void onBtnClick(View view){
        onRequestPermissions(10086, Permission.CAMERA);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10086){
            LogUtils.e("onActivityResult...............");
        }
    }
}
