package com.common.common_base.http.function;

import com.common.common_base.http.exception.ApiException;
import com.common.common_base.http.exception.ServerException;
import com.common.common_base.http.retrofit.BaseResult;
import com.common.common_base.utils.util.EncodeUtils;
import com.common.common_base.utils.util.LogUtils;
import com.common.common_base.modle.DemoResult;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * 服务器结果处理函数
 */
public class ServerResultFunction implements Function<BaseResult, Object>{

    @Override
    public Object apply(@NonNull BaseResult response) throws Exception{
        if(!response.isSuccess()){
            LogUtils.e(response.getMessage());
            if(response.getCode() == 50000){
                throw new ServerException(response.getCode(), response.getMessage());
            }
            throw new ApiException(response.getCode(), response.getMessage());
        }
        return new String(EncodeUtils.base64Decode(response.getResult()));
    }
}
