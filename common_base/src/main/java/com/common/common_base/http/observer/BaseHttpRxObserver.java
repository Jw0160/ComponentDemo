package com.common.common_base.http.observer;

import com.common.common_base.http.exception.ApiException;
import com.common.common_base.http.exception.ExceptionEngine;
import com.common.common_base.mvpbase.IBaseView;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/8
 * @desc :
 */

public abstract class BaseHttpRxObserver<T> extends HttpRxObserver<T>{

    private IBaseView mBaseView;
    private String TAG = this.getClass().getSimpleName();

    public BaseHttpRxObserver(@NonNull IBaseView baseView){
        mBaseView = baseView;
    }

    public BaseHttpRxObserver(@NonNull IBaseView baseView, String TAG){
        super(TAG);
        mBaseView = baseView;
        this.TAG = TAG;
    }

    @Override
    protected void onStart(Disposable d){
        mBaseView.onLoading();
    }

    @Override
    protected void onError(ApiException e){
        if(e.getCode() == ExceptionEngine.NOT_NET_WORK){
            mBaseView.onNotNetWork();
        }else{
            mBaseView.onError();
        }
        getError(e);
    }

    @Override
    protected void onSuccess(T response){
        if(response != null){
            mBaseView.onSuccess();
        }else{
            mBaseView.onEmpty();
        }
        //// TODO: 2018/2/8 数据处理 json  2  bean
        getSuccess(response);
    }

    /**
     * 成功的回调
     */
    public abstract void getSuccess(T response);

    /**
     * 失败的回调
     */
    public abstract void getError(ApiException e);
}
