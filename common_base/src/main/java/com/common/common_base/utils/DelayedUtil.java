package com.common.common_base.utils;
/**
 * 
 * ============================================================
 * 
 * copyright ：ZENG HUI (c) 2014
 * 
 * author : HUI
 * 
 * version ：1.0
 * 
 * date created ： On January 31, 2015  下午1:44:49
 * 
 * description ：
 * 			Delay tools
 * 
 * revision history ：
 * 
 * ============================================================
 *
 */
public class DelayedUtil extends Thread{
	private static long lastClickTime;
    public synchronized static boolean isFastClick(long interval) {
        long time = System.currentTimeMillis();   
        if ( time - lastClickTime < interval) {   
            return false;   
        }   
        lastClickTime = time;   
        return true;   
    }
}
