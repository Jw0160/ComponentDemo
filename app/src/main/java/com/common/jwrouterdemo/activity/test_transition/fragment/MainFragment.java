package com.common.jwrouterdemo.activity.test_transition.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.common.common_base.base.LazyLoadFragment;
import com.common.common_base.utils.util.FragmentUtils;
import com.common.jwrouterdemo.R;
import com.common.jwrouterdemo.activity.test_mvp.DetailTransition;
import com.common.jwrouterdemo.activity.test_transition.adapter.TransitionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/5
 * @desc :
 */

public class MainFragment extends LazyLoadFragment{
    @BindView(R.id.rcv_contains)
    RecyclerView mRcvContains;
    private TransitionAdapter mAdapter;

    private MainFragment(){
        //no instance
    }

    public static MainFragment newInstance(Bundle args){
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentViewId(){
        return R.layout.fragment_main_transition;
    }

    @Override
    public void initBundleData(){
    }

    @Override
    public void initData(){
        List<String> data = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            data.add(getString(R.string.lorem));
        }
        mRcvContains.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new TransitionAdapter(data);
        mRcvContains.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position){
                DetailFragment lFragment = DetailFragment.newInstance(null);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    lFragment.setSharedElementEnterTransition(new DetailTransition());
                    setExitTransition(new Fade());
                    lFragment.setEnterTransition(new Fade());
                    lFragment.setSharedElementReturnTransition(new DetailTransition());
                }
                FragmentUtils.replace(mContext.getSupportFragmentManager(), lFragment, R.id.container, true, view.findViewById(R.id.img_1));
            }
        });
    }

    @Override
    public void fetchData(){
    }
}
