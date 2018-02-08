package com.common.common_base.http.api;

import com.common.common_base.http.retrofit.BaseResult;
import com.common.common_base.modle.LoginBean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/22
 * @desc :
 */

public interface BaseApi{
    @POST("{path}")
    //    @Headers({"Content-Type: application/json", "Accept: application/json", "mw-client:android"})
    @Headers({"mw-client:android"})
    Observable<BaseResult> doPost(@Path("path") String path, @Body RequestBody body);
}
