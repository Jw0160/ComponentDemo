package com.common.jwrouterdemo.activity.test_mvp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.common.common_base.base.BaseFragment;
import com.common.common_base.base.LazyLoadFragment;
import com.common.common_base.utils.util.LogUtils;
import com.common.jwrouterdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/1
 * @desc :
 */

public class TestMvoFragment extends LazyLoadFragment implements ITest.View{
    private static TestMvoFragment mTestMvoFragment;
    @BindView(R.id.tv_test_fragment)
    TextView mTvTestFragment;

    private TestMvpPresenter mTestMvpPresenter = new TestMvpPresenter(this);
    private String mString;

    private TestMvoFragment(){
    }

    public static TestMvoFragment getInstance(Bundle args){
        mTestMvoFragment = new TestMvoFragment();
        mTestMvoFragment.setArguments(args);
        return mTestMvoFragment;
    }

    @Override
    public int getContentViewId(){
        return R.layout.fragment_test_mvp;
    }

    @Override
    public void initBundleData(){
        mTvTestFragment.setText(mTvTestFragment.getText() + getArguments().getString("10086"));
        LogUtils.e("initBundleData" + getArguments().getString("10086"));
    }

    @Override
    public void initData(){
        mTvTestFragment.setText(mTvTestFragment.getText() + getArguments().getString("10086"));
        LogUtils.e("initData" + getArguments().getString("10086"));
        LogUtils.e("initData:mString" + mString);
        mTvTestFragment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mTestMvpPresenter.doLogin("hello,wold", this.getClass().getSimpleName());
            }
        });
    }

    @Override
    public void onShowLoading(){
    }

    @Override
    public void onHideLoading(){
    }

    @Override
    public void onLogin(){
        LogUtils.e("success");
    }

    @Override
    public void fetchData(){
        mString = getArguments().getString("10086");
        mTvTestFragment.setText(mTvTestFragment.getText() + getArguments().getString("10086"));
        LogUtils.e("fetchData" + mString);
    }
}
