package com.common.router;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/20
 * @desc :
 */

public abstract class RouterHandler{
    public abstract void handler(RouteRequest routeRequest, @NonNull Context context);
}
