package com.common.jwrouterdemo.activity.test_mvp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.base.BaseFragmentActivity;
import com.common.common_base.utils.util.LogUtils;
import com.common.jwrouterdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/1
 * @desc :
 */

public class TestMvpActivity extends BaseFragmentActivity{

    private List<Fragment> mFragments;
    private TabLayout mTabLayout;
    private List<String> mStrings;

    @Override
    public int getContentViewId(){
        return R.layout.activity_test_mvp;
    }

    @Override
    public void initBundleData(){
        //        getSupportFragmentManager().beginTransaction().add(R.id.container, new TestMvoFragment()).commit();
        mFragments = new ArrayList<>();
        ViewPager lViewPager = (ViewPager) findViewById(R.id.vp_contain);
        mTabLayout = (TabLayout) findViewById(R.id.tl_test);
        mStrings = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            mStrings.add("" + i);
            Bundle lArgs = new Bundle();
            lArgs.putString("10086", i + "");
            mFragments.add(TestMvoFragment.getInstance(lArgs));
        }
        lViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()){
            @Override
            public Fragment getItem(int position){
                return mFragments.get(position);
            }

            @Override
            public int getCount(){
                return mFragments.size();
            }

            @Override
            public CharSequence getPageTitle(int position){
                return mStrings.get(position);
            }
        });
        mTabLayout.setupWithViewPager(lViewPager);
        lViewPager.setOffscreenPageLimit(mFragments.size());
        lViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels){
            }

            @Override
            public void onPageSelected(int position){
            }

            @Override
            public void onPageScrollStateChanged(int state){
            }
        });
    }

    @Override
    public void initData(){
    }

    public void onMvpClick(View view){
    }
}
