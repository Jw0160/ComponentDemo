package com.common.router;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/21
 * @desc :
 */

public enum RouteType{
    /**
     * ACTIVITY 类型
     */
    RT_ACTIVITY("Activity"),
    /**
     * FRAGMENT 类型
     */
    RT_FRAGMENT("Fragment"),
    /**
     * VIEW 类型
     */
    RT_VIEW("View"),
    /**
     * DATA 类型
     */
    RT_DATA("Data");


    private String desc;

    RouteType(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return desc;
    }
}
