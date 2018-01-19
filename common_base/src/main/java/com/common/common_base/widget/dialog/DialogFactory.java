package com.common.common_base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.common.common_base.R;
import com.common.common_base.utils.system.PhoneSystemUtils;
import com.common.common_base.utils.system.KeyBoardUtil;

/**
 * author : Jack
 * <p>
 * version ：1.0
 * <p>
 * date created ：
 * <p>
 * description ：DialogUtil 工具
 */
public abstract class DialogFactory{
    private static final String TAG = "DialogFactory";
    private Dialog mDialog;
    private Window mDialogWindow;
    private DialogViewHolder dilaogVh;
    private View mRootView;

    LayoutParams params = new LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT);

    public DialogFactory(final Context context, int layoutId){
        dilaogVh = DialogViewHolder.get(context, layoutId);
        mRootView = dilaogVh.getConvertView();
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(mRootView, new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        mDialogWindow = mDialog.getWindow();
        mDialog.setCanceledOnTouchOutside(true);
        mRootView.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent ev){
                if(ev.getAction() == MotionEvent.ACTION_DOWN){
                    View v = mDialog.getCurrentFocus();
                    if(KeyBoardUtil.isShouldHideKeyboard(v, ev)){
                        KeyBoardUtil.hideKeyboard(v.getWindowToken(), context);
                    }
                }
                return false;
            }
        });
        convert(dilaogVh);
    }

    public DialogViewHolder getDilaogVh(){
        return dilaogVh;
    }

    public void setDilaogVh(DialogViewHolder dilaogVh){
        this.dilaogVh = dilaogVh;
    }

    /**
     * 默人显示方式
     */
    public DialogFactory showDialog(){
        //        LogUtil.d("showDialog() called with: is null == " + (mDialog == null) + ", isshow == " + mDialog.isShowing());
        if(mDialog != null && !mDialog.isShowing()){
            mDialog.show();
        }
        return this;
    }

    /**
     * 从底部一直弹到中间
     */
    public DialogFactory fromBottomToMiddle(){
        mDialogWindow.setWindowAnimations(R.style.main_menu_animstyle);
        mDialogWindow.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        return this;
    }

    /**
     * 从底部弹出
     */
    public DialogFactory fromBottom(){
        fromBottomToMiddle();
        mDialogWindow.setGravity(Gravity.CENTER | Gravity.BOTTOM);
        return this;
    }

    /**
     * 从底部弹出
     */
    public DialogFactory showDialog(int style){
        mDialogWindow.setWindowAnimations(style);
        mDialog.show();
        return this;
    }

    /**
     * 显示一个Dialog可以传递一个是否显示动画
     */
    public DialogFactory showDialog(boolean isAnimation){
        if(isAnimation){
            mDialogWindow.setWindowAnimations(R.style.dialog_scale_animstyle);
        }
        mDialog.show();
        return this;
    }

    /**
     * 把弹出框view holder传出去
     */
    public abstract void convert(DialogViewHolder view);

    /**
     * cancel dialog
     */
    public void cancelDialog(){
        if(mDialog != null){
            mDialog.cancel();
            //            mDialog = null;  //赋值为null 后 则无法再次弹出
        }
    }

    public DialogFactory setDialogDismissListener(OnDismissListener listener){
        mDialog.setOnDismissListener(listener);
        return this;
    }

    public DialogFactory setOnCancelListener(OnCancelListener listener){
        mDialog.setOnCancelListener(listener);
        return this;
    }

    /**
     * 设置是否允许手动取消
     * @param cancel
     * @return
     */
    public DialogFactory setCancelable(boolean cancel){
        mDialog.setCancelable(cancel);
        return this;
    }

    public DialogFactory setCanceledOnTouchOutside(boolean cancel){
        mDialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    /**
     * 全屏显示
     */
    public DialogFactory fullScreen(){
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.height = LayoutParams.MATCH_PARENT;
        wl.width = LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * 全屏宽度
     */
    public DialogFactory fullWidth(){
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * 设置高度和宽度
     */
    public DialogFactory setWidthAndHeight(int width, int height){
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.width = width;
        wl.height = height;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     *
     * 设置百分比高宽
     * @param pWidth
     * @param pHeight
     * @return
     */
    public DialogFactory setPercentWidthAndHeight(int pWidth, int pHeight){
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        if(pWidth > 0){
            wl.width = (PhoneSystemUtils.getScreenWidthHeight().x * pWidth) / 100;
        }else{
            wl.width = LayoutParams.WRAP_CONTENT;
        }
        if(pHeight > 0){
            wl.height = (PhoneSystemUtils.getScreenWidthHeight().y * pHeight) / 100;
        }else{
            wl.height = LayoutParams.WRAP_CONTENT;
        }
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    /**
     * 全屏高度
     */
    public DialogFactory fullHeight(){
        WindowManager.LayoutParams wl = mDialogWindow.getAttributes();
        wl.height = LayoutParams.MATCH_PARENT;
        mDialog.onWindowAttributesChanged(wl);
        return this;
    }

    public void dismissDialog(){
        if(mDialog != null && mDialog.isShowing()){
            mDialog.dismiss();
        }
    }
}
