package com.common.router.matcher;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.common.router.RouteRequest;
import com.common.router.RouterHandler;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/20
 * @desc :
 */

public class RouterHandlerMatcher extends AbsMatcher{
    public RouterHandlerMatcher(int priority){
        super(priority);
    }

    @Override
    public boolean match(Context context, Uri uri, @Nullable String route, RouteRequest routeRequest){
        return !isEmpty(route) && uri.toString().equals(route);
    }

    @Override
    public RouterHandler generate(Context context, Uri uri, @Nullable Class<?> target){
        try{
            if(target != null && target.newInstance() instanceof RouterHandler){
                return target != null ? (RouterHandler) target.newInstance() : null;
            }
        }catch(InstantiationException e){
            e.printStackTrace();
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }
        return null;
    }
}
