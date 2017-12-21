package com.common.jwrouterdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.common.annotation.InjectParam;
import com.common.annotation.Route;
import com.common.router.RouteRequest;
import com.common.router.Router;
import com.common.router.RouterHandler;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/20
 * @desc :
 */
@Route(value = {"router1", "maowo://activity"})
public class RouterHandlerdemo extends RouterHandler{

    @Override
    public void handler(RouteRequest routeRequest, @NonNull Context context){
        Bundle lExtras = routeRequest.getExtras();
        String lId = lExtras.getString("id");
        String lIds = lExtras.getString("ids");
        String status = lExtras.getString("statu");
        String statu = lExtras.getString("status");
        Log.e("RouterHandler", "hello world~~~id:" + lId + ",status:" + status + ",statu:" + statu + ",lIds:" + lIds);
    }
}
