package com.common.common_base.utils;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.common.common_base.R;

/**
 * Created by jack on 2017/5/22.
 */

public class AnimationUtil{
    /**
     * 某个控件实现一个摇晃动画，
     *
     * @param view 要实现摇晃动画的View的实例
     */
    public static void startShakeAnimation(View view) {
        if (view == null) {
            return;
        }
        Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake_x);
        view.startAnimation(shake);
        view.requestFocus();
    }

    /**
     * shake animation and show toast if prompt isn't empty
     * <p>某个控件实现一个摇晃动画，如果prompt不为空，则还展示一个Toast
     *
     * @param view   要实现摇晃动画的View的实例
     * @param prompt 需要提示的内容
     */
    public static void startShakeAnimation(View view, String prompt) {
        if (view == null) {
            return;
        }
        Animation shake = AnimationUtils.loadAnimation(view.getContext(), R.anim.shake_x);
        view.startAnimation(shake);
        if (!TextUtils.isEmpty(prompt)) {
            Toast.makeText(view.getContext(), prompt, Toast.LENGTH_SHORT).show();
        }
        view.requestFocus();
    }
}
