package com.common.common_base.utils;

import android.content.Context;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.common_base.R;

public class ToastUtil{
    private static Toast myToast;
    private static TextView contentText;
    private static View view = null;

    private static void initToast(Context context){
        view = ViewUtil.getView(context, R.layout.layout_toast, null);
        contentText = (TextView) view.findViewById(R.id.toast_content_tv);
        contentText.setAlpha((float) 0.9);
        myToast = new Toast(context);
        view.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        myToast.setView(view);
    }

    private static void showToast(Context context, CharSequence msg, int duration){
        if(context == null){
            return;
        }
        if(null == view){
            initToast(context.getApplicationContext());
        }
        if(contentText != null && isMainThread()){//如果不是主线程，则不能更新taost中的文字
            synchronized(contentText){
                if(myToast != null){
                    contentText.setText(msg);
                    myToast.setGravity(Gravity.BOTTOM, 0,  30);
                    myToast.setDuration(duration);
                    myToast.show();
                }else{
                    if(!isMainThread()){
                        Looper.prepare();
                    }
                    Toast.makeText(context, msg, duration).show();
                }
            }
        }else{
            if(!isMainThread()){
                try{
                    Looper.prepare();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            Toast.makeText(context, msg, duration).show();
        }
    }

    public static void showToast(Context context, CharSequence msg){
        if(!isMainThread()){
            try{
                Looper.prepare();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, int msg){
        showToast(context, context.getResources().getString(msg), Toast.LENGTH_SHORT);
    }

    private static boolean isMainThread(){
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}