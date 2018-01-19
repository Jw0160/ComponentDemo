package com.costom.orm.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.costom.orm.DaoMaster;
import com.costom.orm.UserDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;

import org.greenrobot.greendao.database.Database;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/16
 * @desc :
 */

public class GreenDaoUpgradeHelper extends DaoMaster.DevOpenHelper{

    public GreenDaoUpgradeHelper(Context context, String name, SQLiteDatabase.CursorFactory factory){
        super(context, name, factory);
        Log.i("greendao", "GreenDaoUpgradeHelper init");
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion){
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener(){
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists){
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists){
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, UserDao.class);
    }
}
