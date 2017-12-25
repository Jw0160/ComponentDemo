package com.common.jwrouterdemo;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.common.common_base.utils.KLog;
import com.common.router.Router;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/20
 * @desc :
 */

public class MyApp extends Application{
    @Override
    public void onCreate(){
        super.onCreate();
        Router.initialize(this, true);
        KLog.init(true);
        Utils.init(this);
    }
}
