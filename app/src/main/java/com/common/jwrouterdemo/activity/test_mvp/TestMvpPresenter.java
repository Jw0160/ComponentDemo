package com.common.jwrouterdemo.activity.test_mvp;

import com.common.common_base.mvpbase.BasePresenter;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/1
 * @desc :
 */

public class TestMvpPresenter extends BasePresenter<ITest.View> implements ITest.Persenter{

    public TestMvpPresenter(ITest.View activity){
        super(activity);
    }

    @Override
    public void doLogin(String name, String pwd){
        getActivity().onLogin();
    }
}
