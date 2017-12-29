package com.common.common_base.utils.network;

/**
 * 网络状态变化观察者
 *
 * @author Tianma at 2016/12/28
 */
public interface NetStateChangeObserver {

    void onNetDisconnected();

    void onNetConnected(NetworkType networkType);
}
