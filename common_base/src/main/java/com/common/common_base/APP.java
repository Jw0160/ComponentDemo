package com.common.common_base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

import com.common.common_base.utils.KLog;
import com.common.common_base.utils.ToastUtil;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/20
 * @desc :
 */

public class APP{
    @SuppressLint("StaticFieldLeak")
    public static final Application INSTANCE;

    static{
        Application app = null;
        try{
            app = (Application) Class.forName("android.app.AppGlobals").getMethod("getInitialApplication").invoke(null);
            if(app == null){
                throw new IllegalStateException("Static initialization of Applications must be on main thread.");
            }
        }catch(final Exception e){
            KLog.e("Failed to get current application from AppGlobals." + e.getMessage());
            try{
                app = (Application) Class.forName("android.app.ActivityThread").getMethod("currentApplication").invoke(null);
            }catch(final Exception ex){
                KLog.e("Failed to get current application from ActivityThread." + e.getMessage());
            }
        }finally{
            INSTANCE = app;
        }
    }

    public static void toast(String msg){
        ToastUtil.showToast(INSTANCE, msg);
    }

    public static void toast(int msgId){
        ToastUtil.showToast(INSTANCE, msgId);
    }

    public static void longToast(String msg){
        ToastUtil.showToast(INSTANCE, msg);
    }
}
