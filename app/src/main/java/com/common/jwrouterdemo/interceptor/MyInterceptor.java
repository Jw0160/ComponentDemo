package com.common.jwrouterdemo.interceptor;

import android.content.Context;
import android.widget.Toast;

import com.common.annotation.Interceptor;
import com.common.router.RouteInterceptor;
import com.common.router.RouteRequest;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/21
 * @desc :
 */
@Interceptor("MyInterceptor")
public class MyInterceptor implements RouteInterceptor{
    @Override
    public boolean intercept(Context context, RouteRequest routeRequest) {
        Toast.makeText(context, String.format("Intercepted: {uri: %s, interceptor: %s}",
                routeRequest.getUri().toString(), MyInterceptor.class.getName()),
                Toast.LENGTH_LONG).show();
        return true;
    }
}
