package com.common.jwrouterdemo.activity.test_transition.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.common_base.base.LazyLoadFragment;
import com.common.common_base.image.ImageDisplayManager;
import com.common.jwrouterdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/5
 * @desc :
 */

public class DetailFragment extends LazyLoadFragment{
    @BindView(R.id.img_w)
    ImageView mImgW;
    @BindView(R.id.tv_contains)
    TextView mTvContains;

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
