package com.common.common_base.base;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/25
 * @desc :
 */

public interface BaseConfigInterface{
    /**
     * 获取显示view的xml文件ID
     */
    int getContentViewId();



    /**
     * 获取上一个界面传送过来的数据
     */
    void initBundleData();

    /**
     * 初始化应用程序，设置一些初始化数据,获取数据等操作
     */
    void initData();
}
