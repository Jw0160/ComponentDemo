package com.common.common_base.utils;

import android.content.Context;
import android.content.DialogInterface;

import com.common.common_base.R;
import com.common.common_base.base.PermissionsActivity;
import com.yanzhenjie.alertdialog.AlertDialog;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.PermissionNo;
import com.yanzhenjie.permission.PermissionYes;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * @author : wangb
 * @email :
 * @date : 2018/1/3
 * @desc :动态申请权限类
 */

public class PermissionRequestUtil{

    private Context mContext;
    private PermissionCallback mCallback;
    private static final int REQUEST_CODE = 10086;

    public PermissionRequestUtil(Context context, PermissionCallback callback){
        mContext = context;
        mCallback = callback;
    }

    public interface PermissionCallback{
        void onSuccessful();

        void onFailure();
    }

    public void request(String[] permissions){
        AndPermission.with(mContext)
                .requestCode(REQUEST_CODE)
                .permission(permissions)
                .callback(this)
                .rationale(rationaleListener)
                .start();
    }

    @PermissionYes(REQUEST_CODE)
    public void yes(List<String> permissions){
        this.mCallback.onSuccessful();
    }

    @PermissionNo(value = REQUEST_CODE)
    public void no(List<String> permissions){
        this.mCallback.onFailure();
        if(AndPermission.hasAlwaysDeniedPermission(mContext, permissions)){
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(mContext).show();
            // 第二种：用自定义的提示语。
            //             AndPermission.defaultSettingDialog(this, REQUEST_CODE_SETTING)
            //                     .setTitle("权限申请失败")
            //                     .setMessage("我们需要的一些权限被您拒绝或者系统发生错误申请失败，请您到设置页面手动授权，否则功能无法正常使用！")
            //                     .setPositiveButton("好，去设置")
            //                     .show();
            //            第三种：自定义dialog样式。
            //            SettingService settingService = AndPermission.defineSettingDialog(this, REQUEST_CODE_SETTING);
            //            你的dialog点击了确定调用：
            //            settingService.execute();
            //            你的dialog点击了取消调用：
            //            settingService.cancel();
        }
    }

    /**
     * Rationale支持，这里自定义对话框。
     */
    private RationaleListener rationaleListener = new RationaleListener(){
        @Override
        public void showRequestPermissionRationale(int requestCode, final Rationale rationale){
            // 这里使用自定义对话框，如果不想自定义，用AndPermission默认对话框：
            // AndPermission.rationaleDialog(Context, Rationale).show();
            // 自定义对话框。
            AlertDialog.newBuilder(mContext)
                    .setTitle("提示")
                    .setMessage("我们需要的一些必要权限被禁止，请授权给我们。")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.cancel();
                            rationale.resume();
                        }
                    })
                    .setNegativeButton("就不", new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which){
                            dialog.cancel();
                            rationale.cancel();
                        }
                    }).show();
        }
    };
}
