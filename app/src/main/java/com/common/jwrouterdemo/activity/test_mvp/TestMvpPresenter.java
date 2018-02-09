package com.common.jwrouterdemo.activity.test_mvp;

import com.common.common_base.http.api.BaseApi;
import com.common.common_base.http.exception.ApiException;
import com.common.common_base.http.observer.BaseHttpRxObserver;
import com.common.common_base.http.observer.HttpRxObservable;
import com.common.common_base.http.retrofit.RetrofitUtils;
import com.common.common_base.modle.LoginBean;
import com.common.common_base.modle.MallIdBean;
import com.common.common_base.mvpbase.PresenterManage;
import com.common.common_base.mvpbase.IBaseView;
import com.common.common_base.utils.JSONUtil;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/1
 * @desc :
 */

public class TestMvpPresenter<T extends IBaseView> extends PresenterManage<TestMvoFragment> implements ITest.Persenter{

    public TestMvpPresenter(TestMvoFragment activity){
        super(activity);
    }

    @Override
    public void doLogin(String name, String pwd){
        //        doLoadData();
        doShowMall();
    }

    @Override
    public void doLoadData(){
        String lBody = JSONUtil.toJSON(new LoginBean("13510220341", "1234567", 11992444753960964L));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                lBody);
        Observable lObservable = HttpRxObservable.getObservable(RetrofitUtils.get().retrofit().create(BaseApi.class).doPost("login.json", body));
        lObservable.subscribe(new BaseHttpRxObserver<Object>(getView()){

            @Override
            public void getSuccess(Object response){
                getView().onLogin();
            }

            @Override
            public void getError(ApiException e){
            }
        });
    }

    private void doShowMall(){
        String lBody = JSONUtil.toJSON(new MallIdBean(11992447153102849L));
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                lBody);
        Observable lObservable = HttpRxObservable.getObservable(RetrofitUtils.get().retrofit().create(BaseApi.class).doPost("mall/show.json", body));
        lObservable.subscribe(new BaseHttpRxObserver<Object>(getView()){

            @Override
            public void getSuccess(Object response){
            }

            @Override
            public void getError(ApiException e){
            }
        });
    }
}
