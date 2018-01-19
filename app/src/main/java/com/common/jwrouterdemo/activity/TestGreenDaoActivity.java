package com.common.jwrouterdemo.activity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.common.common_base.base.BaseActivity;
import com.common.common_base.utils.ToastUtil;
import com.common.common_base.widget.dialog.DialogFactory;
import com.common.common_base.widget.dialog.DialogViewHolder;
import com.common.common_base.widget.popupwindow.EasyPopup;
import com.common.common_base.widget.popupwindow.HorizontalGravity;
import com.common.common_base.widget.popupwindow.VerticalGravity;
import com.common.jwrouterdemo.MyApp;
import com.common.jwrouterdemo.R;
import com.costom.orm.UserDao;
import com.costom.orm.entity.User;

import java.util.List;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/16
 * @desc :
 */

public class TestGreenDaoActivity extends BaseActivity{

    private EditText mEtInput;
    private TextView mTvTest1, mTvPopup1;
    private Button mBtnInsert, mBtnQuery;
    private View mBtnShowPopup;
    private EasyPopup mPopup;

    @Override
    public int getContentViewId(){
        return R.layout.activity_test_greendao;
    }

    @Override
    public void initBundleData(){
        mEtInput = (EditText) findViewById(R.id.et_input);
        mTvTest1 = (TextView) findViewById(R.id.tv_test1);
        mTvPopup1 = (TextView) findViewById(R.id.tv_popup);
        mBtnInsert = (Button) findViewById(R.id.btn_insert);
        mBtnQuery = (Button) findViewById(R.id.btn_query);
        mBtnShowPopup = findViewById(R.id.btn_show_popup);
        mPopup = new EasyPopup(mContext)
                .setContentView(R.layout.layout_center_pop)
                .createPopup();
    }

    @Override
    public void initData(){
        mBtnInsert.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                MyApp.getInstance().getDaoSession().getUserDao().insert(new User(null, "heloo", 18));
                ToastUtil.showToast(mContext, "success.............\nok0000000000000000000");
            }
        });
        mBtnQuery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                List<User> lList = MyApp.getInstance().getDaoSession().getUserDao().queryBuilder().where((UserDao.Properties.Age.eq("18"))).list();
                String content = "";
                for(User lUser : lList){
                    content += lUser.getId() + ",";
                }
                mTvTest1.setText(content);
            }
        });
        findViewById(R.id.btn_show_dialoog).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //                DialogFactory lDialogFactory = new DialogFactory(mContext, R.layout.layout_toast){
                //                    @Override
                //                    public void convert(DialogViewHolder view){
                //                    }
                //                };
                //                lDialogFactory.setPercentWidthAndHeight(0, 0).showDialog(true);
                mPopup.showAtAnchorView(mBtnShowPopup, VerticalGravity.BELOW, HorizontalGravity.LEFT);
            }
        });
        mBtnShowPopup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPopup.showAtAnchorView(mTvPopup1, VerticalGravity.ABOVE, HorizontalGravity.RIGHT);
            }
        });
        mTvPopup1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mPopup.showAtAnchorView(mTvPopup1, VerticalGravity.ABOVE, HorizontalGravity.LEFT);
            }
        });
    }
}
