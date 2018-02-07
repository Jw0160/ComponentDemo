package com.common.common_base.widget.dialog;

import android.content.Context;
import android.content.DialogInterface;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/7
 * @desc :自定义Dialog  内部处理逻辑
 */

public abstract class BaseCostomDialog implements DialogInterface.OnDismissListener{

    public abstract int setLayoutId();

    public abstract int setWidth();

    public abstract int setHeight();

    public abstract void setViewData(DialogViewHolder viewHolder);

    private DialogFactory mDialogFactory;
    private Context mContext;

    public BaseCostomDialog(Context context){
        mContext = context;
    }

    private void initDialog(){
        mDialogFactory = new DialogFactory(mContext, setLayoutId()){

            @Override
            public void convert(DialogViewHolder view){
                setViewData(view);
            }
        };
        mDialogFactory.setPercentWidthAndHeight(setWidth(), setHeight());
        mDialogFactory.setDialogDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog){
        cancelDialog();
    }

    public void showDialog(){
        if(mDialogFactory != null){
            mDialogFactory.showDialog(true);
        }
    }

    public void showBottomDialog(){
        if(mDialogFactory != null){
            mDialogFactory.fromBottom().showDialog(true);
        }
    }

    public void showCenterDialog(){
        if(mDialogFactory != null){
            mDialogFactory.fromBottomToMiddle().showDialog(true);
        }
    }

    public void cancelDialog(){
        if(mDialogFactory != null){
            mDialogFactory.cancelDialog();
        }
    }
}
