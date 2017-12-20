package com.common.jwrouterdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.common.annotation.Route;
import com.common.router.RouteRequest;
import com.common.router.RouterHandler;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/20
 * @desc :
 */
@Route("router1")
public class RouterHandlerdemo extends RouterHandler{

    @Override
    public void handler(RouteRequest routeRequest, @NonNull Context context){
        Log.e("RouterHandler", "hello world~~~handler");
    }
}
