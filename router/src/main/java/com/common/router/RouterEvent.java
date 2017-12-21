package com.common.router;

import android.os.Bundle;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/21
 * @desc :
 */

public class RouterEvent{
    private int code;
    private boolean success;
    private String routerIp;
    private RouteType type;
    private String obj;
    private Bundle bundle;

    public RouterEvent(){
        success = true;
    }

    public RouterEvent(boolean success){
        this.success = success;
    }

    public int getCode(){
        return code;
    }

    public RouterEvent setCode(int code){
        this.code = code;
        return this;
    }

    public boolean isSuccess(){
        return success;
    }

    public RouterEvent setSuccess(boolean success){
        this.success = success;
        return this;
    }

    public String getRouterIp(){
        return routerIp;
    }

    public RouterEvent setRouterIp(String routerIp){
        this.routerIp = routerIp;
        return this;
    }

    public RouteType getType(){
        return type;
    }

    public RouterEvent setType(RouteType type){
        this.type = type;
        return this;
    }

    public String getObj(){
        return obj;
    }

    public RouterEvent setObj(String obj){
        this.obj = obj;
        return this;
    }

    public Bundle getBundle(){
        return bundle;
    }

    public RouterEvent setBundle(Bundle bundle){
        this.bundle = bundle;
        return this;
    }
}
