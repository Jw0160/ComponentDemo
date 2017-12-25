package com.common.common_base.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 构建Api请求参数
 *
 */

public class BaseHttpRequest{

    public static final String appKey = "1889b37351288";
    private static final String k_key = "key";

    /**
     * 获取基础request
     *
     * String
     */
    public static final Map<String, Object> getRequest() {
        Map<String, Object> map = new HashMap<>();
        map.put(k_key, appKey);
        return map;
    }


}
