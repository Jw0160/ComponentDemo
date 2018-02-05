package com.common.jwrouterdemo.activity.test_mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.common_base.base.LazyLoadFragment;
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

public class TestMvpFragment2 extends LazyLoadFragment{
    @BindView(R.id.tv_test2)
    TextView mTvTest2;
    @BindView(R.id.img_1)
    ImageView mImg1;

    private TestMvpFragment2(){
        //no instance
    }

    public static TestMvpFragment2 newInstance(Bundle args){
        TestMvpFragment2 fragment = new TestMvpFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId(){
        return R.layout.fragment_test_2;
    }

    @Override
    public void initBundleData(){
        mImg1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void initData(){
    }

    @Override
    public void fetchData(){
    }

}
