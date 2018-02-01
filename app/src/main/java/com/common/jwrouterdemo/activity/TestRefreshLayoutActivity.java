package com.common.jwrouterdemo.activity;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.common.common_base.base.BaseActivity;
import com.common.common_base.utils.util.ToastUtils;
import com.common.jwrouterdemo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/19
 * @desc :
 */

public class TestRefreshLayoutActivity extends BaseActivity{
    @BindView(R.id.rcv_layout)
    RecyclerView mRcvLayout;
    @BindView(R.id.srl_refresh)
    SmartRefreshLayout mSrlRefresh;
    private List<String> mData;
    private BaseAdapter mAdapter;

    @Override
    public int getContentViewId(){
        return R.layout.activity_refresh_layout_test;
    }

    @Override
    public void initBundleData(){
        mSrlRefresh.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener(){
            @Override
            public void onLoadmore(RefreshLayout refreshlayout){
                mAdapter.addData(mData);
                mSrlRefresh.finishLoadmore();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout){
                ToastUtils.showLong("refresh.....");
                mSrlRefresh.finishRefresh(1000);
            }
        });
    }

    @Override
    public void initData(){
//        mSrlRefresh.autoRefresh();
        mData = new ArrayList<>();
        for(int i = 0; i < 25; i++){
            mData.add(i + "success");
        }
        mRcvLayout.setLayoutManager(new LinearLayoutManager(this));
        mRcvLayout.addItemDecoration(new DividerItemDecoration(this, VERTICAL));
        mAdapter = new BaseAdapter(mData);
        mAdapter.openLoadAnimation();
        mRcvLayout.setAdapter(mAdapter);
    }

    public class BaseAdapter extends BaseQuickAdapter<String, BaseViewHolder>{
        public BaseAdapter(@Nullable List<String> data){
            super(R.layout.item_refresh_layout, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item){
            helper.setText(R.id.tv_test, item);
        }
    }
}
