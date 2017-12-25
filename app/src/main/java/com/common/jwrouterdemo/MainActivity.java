package com.common.jwrouterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.common.common_base.http.api.DownLoadApi;
import com.common.common_base.http.exception.ApiException;
import com.common.common_base.http.observer.HttpRxObservable;
import com.common.common_base.http.observer.HttpRxObserver;
import com.common.common_base.http.retrofit.RetrofitUtils;
import com.common.router.Router;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;

public class MainActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShow(View view){
        Router.build("http://example.com/user/activity?id=123123&status=ok").skipInterceptors().go(this);
    }

    public void onBtn1(View view){
        Bundle lbundler = new Bundle();
        lbundler.putString("ids", "asdasdsa");
        lbundler.putString("statu", "ok0000000000");
        Router.build("maowo://activity?id=123123&status=okasdasd").with(lbundler).go(this);
    }

    public void onDownload(View view){
        Map<String, String> map = new HashMap<>();
        map.put("bbb", "bbb");
        HttpRxObservable
                .getObservable(RetrofitUtils.get().retrofit().create(DownLoadApi.class).getDownladoResult("jsonObject", "aaa"))
                .subscribe(new HttpRxObserver(){
                    @Override
                    protected void onStart(Disposable d){
                        Log.d("MainActivity", "Disposable:" + d);
                    }

                    @Override
                    protected void onError(ApiException e){
                        Log.d("MainActivity", "ApiException:" + e);
                    }

                    @Override
                    protected void onSuccess(Object response){
                        Log.d("MainActivity", "response:" + response);
                    }
                })
        ;
    }
}
