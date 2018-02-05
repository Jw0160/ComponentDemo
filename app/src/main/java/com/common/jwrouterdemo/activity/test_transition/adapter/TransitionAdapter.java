package com.common.jwrouterdemo.activity.test_transition.adapter;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.common.jwrouterdemo.R;

import java.util.List;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/5
 * @desc :
 */

public class TransitionAdapter extends BaseQuickAdapter<String, BaseViewHolder>{
    public TransitionAdapter(@Nullable List<String> data){
        super(R.layout.item_main_fragment, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item){
        helper.setText(R.id.tv_contains, item)
                .setImageResource(R.id.img_1, R.drawable.timg);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position){
    }
}
