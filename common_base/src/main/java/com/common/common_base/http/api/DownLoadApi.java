package com.common.common_base.http.api;

import com.common.common_base.modle.DemoResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/25
 * @desc :
 */

public interface DownLoadApi{
    @GET("{path}")
    Observable<DemoResult> getDownladoResult(@Path("path") String path, @Header("aaa") String lang);
}
