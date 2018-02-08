package com.common.common_base.mvpbase;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/1
 * @desc :
 */

public interface IBaseView{

    /**
     * 加载错误
     */
    void onError();

    /**
     * 空页面
     */
    void onEmpty();

    /**
     * 加载成功
     */
    void onSuccess();

    /**
     * 正在加载
     */
    void onLoading();

    /**
     * 无网络
     */
    void onNotNetWork();
}
