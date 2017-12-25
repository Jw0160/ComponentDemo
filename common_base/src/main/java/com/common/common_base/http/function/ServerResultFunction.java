package com.common.common_base.http.function;

import com.blankj.utilcode.util.LogUtils;
import com.common.common_base.DemoResult;
import com.common.common_base.http.BaseHttpResponse;
import com.common.common_base.http.exception.ServerException;
import com.google.gson.Gson;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 服务器结果处理函数
 *
 * String
 */
public class ServerResultFunction implements Function<DemoResult, Object>{

    @Override
    public Object apply(@NonNull DemoResult response) throws Exception{
        LogUtils.e(response.toString());
//        if(!response.){
        //            throw new ServerException(response, response);
        //        }
//        return new Gson().toJson(response);
        return "{hello world}";
    }
}