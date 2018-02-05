package com.common.jwrouterdemo.activity.test_transition.fragment;

import android.os.Bundle;

import com.common.common_base.base.LazyLoadFragment;
import com.common.jwrouterdemo.R;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/5
 * @desc :
 */

public class DetailFragment extends LazyLoadFragment{
    private DetailFragment(){
        //no instance
    }

    public static DetailFragment newInstance(Bundle args){
        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId(){
        return R.layout.fagment_detail;
    }

    @Override
    public void initBundleData(){
    }

    @Override
    public void initData(){
    }

    @Override
    public void fetchData(){
    }
}
