package com.common.jwrouterdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.widget.FrameLayout;
import android.widget.ProgressBar;


import com.common.common_base.http.api.DownLoadApi;
import com.common.common_base.http.exception.ApiException;
import com.common.common_base.http.observer.HttpRxObservable;
import com.common.common_base.http.observer.HttpRxObserver;
import com.common.common_base.http.retrofit.RetrofitUtils;
import com.common.common_base.utils.util.LogUtils;
import com.common.common_base.view.BaseSettingWebView;
import com.costom.orm.UserDao;

import io.reactivex.disposables.Disposable;

public class MainActivity extends Activity{

    private FrameLayout mFrameRoot;
    private BaseSettingWebView mBaseSettingWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        MyApp.getInstance().getDaoSession().getUserDao().queryBuilder().where(UserDao.Properties.Name.eq("")).list();
    }

    private void initView(){
        mFrameRoot = (FrameLayout) findViewById(R.id.frame_root);
    }

    public void onClickShow(View view){
        mBaseSettingWebView = new BaseSettingWebView(this);
        ProgressBar lView = new ProgressBar(this);
        mFrameRoot.addView(mBaseSettingWebView);
        //        mFrameRoot.addView(lView);
        mBaseSettingWebView.addJavascriptInterface(new CostomJsBridge(), "testJsBridge");
        mBaseSettingWebView.loadUrl("file:///android_asset/pubuliu.html");
    }

    public void onBtn1(View view){
        switch(view.getId()){
            case R.id.btn1:
                // 弹出提示框
                mBaseSettingWebView.loadUrl("javascript:alert('alert')");
                break;
            case R.id.btn2:   // 弹出提示框
                mBaseSettingWebView.loadUrl("javascript:confirm('Confirm')");
                break;
            case R.id.btn3:   // 弹出提示框
                mBaseSettingWebView.loadUrl("javascript:prompt('Prompt')");
                break;
            case R.id.btn4:   // 弹出提示框
                mBaseSettingWebView.evaluateJavascript("getGreetings()", new ValueCallback<String>(){
                    @Override
                    public void onReceiveValue(String value){
                        LogUtils.e("value : " + value);
                    }
                });
                break;
            default:
        }
        // 调用注入的jsobj.say方法
        //        mBaseSettingWebView.loadUrl("javascript:testJsBridge.onShow('helloaaaaaaaaaaa')");
    }

    public void onDownload(View view){
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        if(mBaseSettingWebView != null){
            mBaseSettingWebView.setWebViewClient(null);
            mBaseSettingWebView.setWebChromeClient(null);
            mBaseSettingWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mBaseSettingWebView.clearHistory();
            ((ViewGroup) mBaseSettingWebView.getParent()).removeView(mBaseSettingWebView);
            mBaseSettingWebView.destroy();
            mBaseSettingWebView = null;
        }
    }

    public class CostomJsBridge{
        @JavascriptInterface
        public void onShow(String str){
            LogUtils.e(str);
        }
    }
}
