package com.common.router;

import android.content.Context;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/20
 * @desc :实现此接口,完成 {@link RouterHandler} 中handler的内容操作
 */

public interface IRouterHelper{
    void startActivity(RouteRequest routeRequest, Context context, Class target);
}
