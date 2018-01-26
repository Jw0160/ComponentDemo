package com.common.jwrouterdemo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.utils.CacheDataManager;
import com.common.common_base.utils.util.BarUtils;
import com.common.common_base.utils.util.CacheUtils;
import com.common.common_base.utils.util.LogUtils;
import com.common.common_base.utils.util.SnackbarUtils;
import com.common.jwrouterdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/23
 * @desc :
 */

public class TestToorBarActivity extends BaseActivity{
    @BindView(R.id.fal_btn1)
    FloatingActionButton mFalBtn1;

    @Override
    public int getContentViewId(){
        return R.layout.activity_test_toorbar;
    }

    @Override
    public void initBundleData(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Toolbar");
        toolbar.setForegroundGravity(Gravity.CENTER);
        setSupportActionBar(toolbar);
        //设置APP图标
        //        toolbar.setLogo(R.mipmap.ic_launcher);
        //        toolbar.setSubtitle("副标题");
        //设置导航图标一定要设置在setsupportactionbar后面才有用不然他会显示小箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);// 给左上角图标的左边加上一个返回的图标
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                LogUtils.e("navigation is click");
            }
        });
    }

    @Override
    public void initData(){
        mFalBtn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                CacheUtils.getInstance().getCacheSize();
                SnackbarUtils.with(mFalBtn1).setMessage("ok....").show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int itemId = item.getItemId();
        if(itemId == R.id.action_share){
            LogUtils.e("menu is click");
            try{
                String lTotalCacheSize = CacheDataManager.getTotalCacheSize(mContext);
                LogUtils.e("lTotalCacheSize :" + lTotalCacheSize);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
