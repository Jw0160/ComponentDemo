package com.common.jwrouterdemo.activity;

import android.widget.Button;
import android.widget.TextView;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.http.api.BaseApi;
import com.common.common_base.http.exception.ApiException;
import com.common.common_base.http.observer.HttpRxObservable;
import com.common.common_base.http.observer.HttpRxObserver;
import com.common.common_base.http.retrofit.RetrofitUtils;
import com.common.common_base.utils.JSONUtil;
import com.common.common_base.utils.util.LogUtils;
import com.common.jwrouterdemo.R;
import com.common.common_base.modle.LoginBean;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
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

    @Override
    public int getContentViewId(){
        return R.layout.activty_test_retrofit;
    }

    @Override
    public void initBundleData(){
    }

    @Override
    public void initData(){
    }

    @OnClick(R.id.btn_connect)
    public void onViewClicked(){
        String lBody = JSONUtil.toJSON(new LoginBean("13510220341", "1234567", 11992444753960964L));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                lBody);
        Observable lObservable = HttpRxObservable.getObservable(RetrofitUtils.get().retrofit().create(BaseApi.class).doPost("login.json", new LoginBean("13510220341", "1234567", 11992444753960964L)));
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
}
