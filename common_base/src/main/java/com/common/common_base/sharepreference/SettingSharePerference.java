package com.common.common_base.sharepreference;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;

import com.common.common_base.APP;
import com.common.common_base.R;
import com.common.common_base.constant.BaseConfig;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/7
 * @desc :
 */

public class SettingSharePerference{
    private SharedPreferences setting;
    private SharedPreferences.Editor mEditor;

    private SettingSharePerference(){
        setting = PreferenceManager.getDefaultSharedPreferences(APP.INSTANCE);
        mEditor = setting.edit();
    }

    public static SettingSharePerference getInstance(){
        return SettingsInstance.instance;
    }

    private static final class SettingsInstance{
        private static final SettingSharePerference instance = new SettingSharePerference();
    }

    /**
     * 获取主题颜色
     */
    public int getColor(){
        int defaultColor = APP.INSTANCE.getResources().getColor(R.color.colorPrimary);
        int color = setting.getInt(APP.INSTANCE.getString(R.string.title_color), defaultColor);
        if((color != 0) && Color.alpha(color) != 255){
            return defaultColor;
        }
        return color;
    }

    /**
     * 设置主题颜色
     */
    public void setColor(int color){
        mEditor.putInt(APP.INSTANCE.getString(R.string.title_color), color).apply();
    }

    /**
     * 获取是否开启导航栏上色
     */
    public boolean getNavBar(){
        return setting.getBoolean(APP.INSTANCE.getString(R.string.nav_bar), false);
    }

    /**
     * 获取字体大小
     */
    public int getTextSize(){
        return setting.getInt(APP.INSTANCE.getString(R.string.text_size), 16);
    }

    /**
     * 设置字体大小
     */
    public void setTextSize(int textSize){
        mEditor.putInt(APP.INSTANCE.getString(R.string.text_size), textSize).apply();
    }


}
