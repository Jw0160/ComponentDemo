package com.common.jwrouterdemo;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.common.common_base.image.ImageDisplayManager;
import com.common.common_base.utils.KLog;
import com.common.common_base.utils.util.Utils;
import com.common.common_base.widget.loadlayout.LoadingLayout;
import com.common.router.Router;
import com.costom.orm.DaoMaster;
import com.costom.orm.DaoSession;
import com.costom.orm.helper.GreenDaoUpgradeHelper;

/**
 * @author : wangb
 * @email :
 * @date : 2017/12/20
 * @desc :
 */

public class MyApp extends Application{
    private DaoSession mDaoSession;
    private static MyApp instance;

    public static MyApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        ImageDisplayManager.getInstance().init(this);
        Router.initialize(this, true);
        KLog.init(true);
        Utils.init(this);
        initGreedDaoDB();
        initLoadLayout();
    }

    private void initLoadLayout(){
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                .setErrorImage(R.mipmap.define_error)
                .setEmptyImage(R.mipmap.define_empty)
                .setNoNetworkImage(R.mipmap.define_nonetwork)
                .setAllTipTextColor(R.color.Grey50)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(R.color.Grey50)
                .setReloadButtonWidthAndHeight(150,40)
                .setAllPageBackgroundColor(R.color.Grey10);
    }

    private void initGreedDaoDB(){
        //MigrationHelper.DEBUG = true; //如果你想查看日志信息，请将DEBUG设置为true
        GreenDaoUpgradeHelper helper = new GreenDaoUpgradeHelper(this, "test.db",
                null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        if(mDaoSession == null){
            initGreedDaoDB();
        }
        return mDaoSession;
    }
}
