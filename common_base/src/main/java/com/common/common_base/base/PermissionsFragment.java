package com.common.common_base.base;

import android.support.annotation.NonNull;

import com.common.common_base.utils.util.LogUtils;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RationaleListener;

import java.util.List;

/**
 * @author : wangb
 * @email :
 * @date : 2018/1/3
 * @desc :
 */

public abstract class PermissionsFragment extends BaseFragment  implements PermissionListener, RationaleListener{
    protected void onRequestPermissions(int requestCode, String[] permissions){
        AndPermission.with(this).requestCode(requestCode).permission(permissions).callback(this).rationale(this).start();
    }

    @Override
    public void onFailed(int requestCode, @NonNull List<String> deniedPermissions){
        LogUtils.e("onFailed...............");
        // 用户否勾选了不再提示并且拒绝了权限，那么提示用户到设置中授权。
        if(AndPermission.hasAlwaysDeniedPermission(PermissionsFragment.this, deniedPermissions)){
            // 第一种：用默认的提示语。
            AndPermission.defaultSettingDialog(PermissionsFragment.this, requestCode).show();
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

    @Override
    public void showRequestPermissionRationale(int requestCode, final Rationale rationale){
        // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
        AndPermission.rationaleDialog(mContext, rationale)
                //自定义内容
                //                .setTitle("title")
                //                .setMessage("message")
                //                .setPositiveButton("ojbk")
                //                .setNegativeButton("not ojbk", new DialogInterface.OnClickListener(){
                //                    @Override
                //                    public void onClick(DialogInterface dialog, int which){
                //                        rationale.cancel();
                //                        LogUtils.e("cancel.......");
                //                    }
                //                })
                .show();
    }
}
