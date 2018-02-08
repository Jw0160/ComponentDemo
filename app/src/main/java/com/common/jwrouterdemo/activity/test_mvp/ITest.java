package com.common.jwrouterdemo.activity.test_mvp;

import com.common.common_base.mvpbase.IBasePresenter;
import com.common.common_base.mvpbase.IBaseView;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/1
 * @desc :
 */

public interface ITest{
    interface View extends IBaseView{
        void onLogin();
    }

    interface Persenter extends IBasePresenter{
        void doLogin(String name, String pwd);
    }
}
