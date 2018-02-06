package com.common.jwrouterdemo.activity.test_transition.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.common.common_base.base.LazyLoadFragment;
import com.common.common_base.image.ImageDisplayManager;
import com.common.common_base.utils.util.FragmentUtils;
import com.common.jwrouterdemo.R;
import com.common.jwrouterdemo.activity.test_mvp.DetailTransition;
import com.common.jwrouterdemo.activity.test_transition.adapter.TransitionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/5
 * @desc :
 */

public class MainFragment extends LazyLoadFragment{
    @BindView(R.id.rcv_contains)
    RecyclerView mRcvContains;
    private TransitionAdapter1 mAdapter;

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
        mRcvContains.setLayoutManager(new GridLayoutManager(mContext, 2));
        mRcvContains.addItemDecoration(new DividerItemDecoration(mContext, VERTICAL));
        mAdapter = new TransitionAdapter1(data);
        mAdapter.openLoadAnimation();
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
                getActivity().getSupportFragmentManager().beginTransaction()
                        .addSharedElement(view.findViewById(R.id.img_1), "image")
                        .replace(R.id.container, lFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void fetchData(){
    }

    public class TransitionAdapter1 extends BaseQuickAdapter<String, BaseViewHolder>{
        public TransitionAdapter1(@Nullable List<String> data){
            super(R.layout.item_main_fragment, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item){
            helper.setText(R.id.tv_contains, item);
            ImageView lImageView = (ImageView) helper.getView(R.id.img_1);
            ViewCompat.setTransitionName(lImageView, helper.getLayoutPosition() + "_image");
            ImageDisplayManager.getInstance().loadImage((ImageView) helper.getView(R.id.img_1), "https://gss0.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=554df9284e2309f7e73aa514423e20cb/aec379310a55b319f1c36ec94ba98226cffc1757.jpg");
        }
    }
}
