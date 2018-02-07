package com.common.common_base.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import com.common.common_base.constant.BaseConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/7
 * @desc :
 */

public class AppCommonSharePreference{
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private static Context mContext;
    private final String SharePreferencename = "";
    private Object object;

    public static void init(Context context){
        mContext = context;
    }

    private AppCommonSharePreference(){
        if(mContext != null){
            throw new NullPointerException("context is null");
        }
        mPreferences = mContext.getSharedPreferences(SharePreferencename, Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static AppCommonSharePreference getInstance(){
        return AppCommonInstance.instance;
    }

    private static final class AppCommonInstance{
        private static final AppCommonSharePreference instance = new AppCommonSharePreference();
    }

    /**
     * 保存数据 , 所有的类型都适用
     *
     * @param key
     * @param object
     */
    public synchronized void saveParam(String key, @NonNull Object object){
        if(mEditor == null)
            mEditor = mPreferences.edit();
        // 得到object的类型
        String type = object.getClass().getSimpleName();
        if("String".equals(type)){
            // 保存String 类型
            mEditor.putString(key, (String) object);
        }else if("Integer".equals(type)){
            // 保存integer 类型
            mEditor.putInt(key, (Integer) object);
        }else if("Boolean".equals(type)){
            // 保存 boolean 类型
            mEditor.putBoolean(key, (Boolean) object);
        }else if("Float".equals(type)){
            // 保存float类型
            mEditor.putFloat(key, (Float) object);
        }else if("Long".equals(type)){
            // 保存long类型
            mEditor.putLong(key, (Long) object);
        }else{
            // 不是基本类型则是保存对象
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            try{
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                String productBase64 = Base64.encodeToString(
                        baos.toByteArray(), Base64.DEFAULT);
                mEditor.putString(key, productBase64);
                Log.d(this.getClass().getSimpleName(), "save object success");
            }catch(IOException e){
                e.printStackTrace();
                Log.e(this.getClass().getSimpleName(), "save object error");
            }
        }
        mEditor.apply();
        mEditor = null;
    }

    /**
     * 得到保存数据的方法，所有类型都适用
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public Object getParam(String key, Object defaultObject){
        String type = defaultObject.getClass().getSimpleName();
        if("String".equals(type)){
            return mPreferences.getString(key, (String) defaultObject);
        }else if("Integer".equals(type)){
            return mPreferences.getInt(key, (Integer) defaultObject);
        }else if("Boolean".equals(type)){
            return mPreferences.getBoolean(key, (Boolean) defaultObject);
        }else if("Float".equals(type)){
            return mPreferences.getFloat(key, (Float) defaultObject);
        }else if("Long".equals(type)){
            return mPreferences.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    public Object getObject(String key){
        String wordBase64 = mPreferences.getString(key, "");
        byte[] base64 = Base64.decode(wordBase64.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream bais = new ByteArrayInputStream(base64);
        try{
            ObjectInputStream bis = new ObjectInputStream(bais);
            object = bis.readObject();
            Log.d(this.getClass().getSimpleName(), "Get object success");
            return object;
        }catch(Exception e){
        }
        Log.e(this.getClass().getSimpleName(), "Get object is error");
        return null;
    }

    /**
     * 是否首次
     */
    public boolean getIsFirstTime(){
        return (boolean) getParam(BaseConfig.FIRST_TIME, true);
    }

    public void setIsFirstTime(boolean flag){
        saveParam(BaseConfig.FIRST_TIME, flag);
    }
}
