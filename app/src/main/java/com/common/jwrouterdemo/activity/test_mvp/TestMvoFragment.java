package com.common.jwrouterdemo.activity.test_mvp;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.common.common_base.base.LazyLoadFragment;
import com.common.common_base.utils.util.FragmentUtils;
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
    @BindView(R.id.tv_test_fragment)
    TextView mTvTestFragment;
    @BindView(R.id.img_1)
    ImageView mImg1;

    private TestMvpPresenter mTestMvpPresenter = new TestMvpPresenter(this);
    private String mString;
    private FragmentManager mSupportFragmentManager;
    ///////////////////////////////////////////////////////////////////////////
    // // STOPSHIP: 2018/2/5
    ///////////////////////////////////////////////////////////////////////////

    private TestMvoFragment(){
    }

    public static TestMvoFragment getInstance(Bundle args){
        TestMvoFragment mTestMvoFragment;
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
        //        mTvTestFragment.setText(mTvTestFragment.getText() + getArguments().getString("10086"));
        //        LogUtils.e("initBundleData" + getArguments().getString("10086"));
        mSupportFragmentManager = mContext.getSupportFragmentManager();
        mImg1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(FragmentUtils.getTopInStack(mSupportFragmentManager) instanceof TestMvpFragment2){
                    return;
                }
                TestMvpFragment2 lTestMvpFragment2 = TestMvpFragment2.newInstance(null);
                //                setExitTransition(new Fade());
                //                lTestMvpFragment2.setEnterTransition(new Fade());
                //                lTestMvpFragment2.setSharedElementEnterTransition(new DetailTransition());
                //                lTestMvpFragment2.setSharedElementReturnTransition(new DetailTransition());
                //                //
//                                ViewCompat.setTransitionName(mImg1, "image");
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    lTestMvpFragment2.setSharedElementEnterTransition(new DetailTransition());
                    setExitTransition(new Fade());
                    lTestMvpFragment2.setEnterTransition(new Fade());
                    lTestMvpFragment2.setSharedElementReturnTransition(new DetailTransition());
                }
                FragmentUtils.replace(mSupportFragmentManager, lTestMvpFragment2, R.id.container, true, mImg1);
                //                getActivity().getSupportFragmentManager().beginTransaction()
                //                        .addSharedElement(mImg1, "image")
                //                        .replace(R.id.container, lTestMvpFragment2)
                //                        .addToBackStack(this.getClass().getSimpleName())
                //                        .commit();
            }
        });
    }

    @Override
    public void initData(){
        //        mTvTestFragment.setText(mTvTestFragment.getText() + getArguments().getString("10086"));
        //        LogUtils.e("initData" + getArguments().getString("10086"));
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
        //        mString = getArguments().getString("10086");
        //        mTvTestFragment.setText(mTvTestFragment.getText() + getArguments().getString("10086"));
        LogUtils.e("fetchData" + mString);
    }
}
