package com.costom.orm.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.costom.orm.DaoMaster;
import com.costom.orm.DaoSession;
import com.costom.orm.UserDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/16
 * @desc :
 */

public class GreenDaoManager{

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static GreenDaoManager mInstance; //单例

    private GreenDaoManager(Context context){
        if(mInstance == null){
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, "user1-db", null){
                @Override
                public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
                    MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener(){
                        @Override
                        public void onCreateAllTables(Database db, boolean ifNotExists){
                            DaoMaster.createAllTables(db, ifNotExists);
                        }

                        @Override
                        public void onDropAllTables(Database db, boolean ifExists){
                            DaoMaster.dropAllTables(db, ifExists);
                        }
                    }, UserDao.class);//此处为自己需要处理的表
                }
            };
            mDaoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
            mDaoSession = mDaoMaster.newSession();
        }
    }

    public static GreenDaoManager getInstance(Context context){
        if(mInstance == null){
            synchronized(GreenDaoManager.class){//保证异步处理安全操作
                if(mInstance == null){
                    mInstance = new GreenDaoManager(context);
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getMaster(){
        return mDaoMaster;
    }

    public DaoSession getSession(){
        return mDaoSession;
    }

    public DaoSession getNewSession(){
        mDaoSession = mDaoMaster.newSession();
        return mDaoSession;
    }
}
