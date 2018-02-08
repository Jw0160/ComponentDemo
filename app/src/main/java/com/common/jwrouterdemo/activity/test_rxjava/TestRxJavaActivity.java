package com.common.jwrouterdemo.activity.test_rxjava;

import android.os.Bundle;

import com.common.common_base.base.BaseActivity;
import com.common.jwrouterdemo.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/8
 * @desc :
 */

public class TestRxJavaActivity extends BaseActivity{
    @Override
    public int getContentViewId(){
        return R.layout.activity_test_rxjava;
    }

    @Override
    public void initBundleData(){
    }

    @Override
    public void initData(){
    }

    @OnClick(R.id.btn1)
    public void onViewClicked(){
        Observable.error(new NullPointerException())
                .subscribe(new Observer<Object>(){
                    @Override
                    public void onSubscribe(@NonNull Disposable d){
                    }

                    @Override
                    public void onNext(@NonNull Object o){
                    }

                    @Override
                    public void onError(@NonNull Throwable e){
                    }

                    @Override
                    public void onComplete(){
                    }
                });
    }
}
