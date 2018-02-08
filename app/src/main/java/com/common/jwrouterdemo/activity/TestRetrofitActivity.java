package com.common.jwrouterdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.http.api.BaseApi;
import com.common.common_base.http.exception.ApiException;
import com.common.common_base.http.observer.HttpRxObservable;
import com.common.common_base.http.observer.HttpRxObserver;
import com.common.common_base.http.retrofit.RetrofitUtils;
import com.common.common_base.modle.LoginBean;
import com.common.common_base.utils.JSONUtil;
import com.common.common_base.utils.util.LogUtils;
import com.common.jwrouterdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/22
 * @desc :
 */

public class TestRetrofitActivity extends BaseActivity{
    @BindView(R.id.btn_connect)
    Button mBtnConnect;
    @BindView(R.id.tv_show_content)
    TextView mTvShowContent;
    @BindView(R.id.btn_jump)
    Button mBtnJump;

    @Override
    public int getContentViewId(){
        return R.layout.activty_test_retrofit;
    }

    @Override
    public void initBundleData(){
    }

    @Override
    public void initData(){
        mBtnJump.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(TestRetrofitActivity.this, TestRetrofitActivity.class));
            }
        });
    }

    @OnClick(R.id.btn_connect)
    public void onViewClicked(){
        String lBody = JSONUtil.toJSON(new LoginBean("13510220341", "1234567", 11992444753960964L));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                lBody);
        Observable lObservable = HttpRxObservable.getObservable(RetrofitUtils.get().retrofit().create(BaseApi.class).doPost("login.json",body));
        lObservable.subscribe(new HttpRxObserver(){
            @Override
            protected void onStart(Disposable d){
                LogUtils.e("onStart:" + d.toString());
            }

            @Override
            protected void onError(ApiException e){
                LogUtils.e("onError:" + e.getMessage());
            }

            @Override
            protected void onSuccess(Object response){
                LogUtils.e("onSuccess:" + ((String) response).toString());
            }
        });
    }

    @Override
    public void onBackPressed(){
        LogUtils.e("onBack...........");
        super.onBackPressed();
    }
}
