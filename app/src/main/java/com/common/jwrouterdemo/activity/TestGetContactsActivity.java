package com.common.jwrouterdemo.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.common.common_base.utils.util.LogUtils;
import com.common.common_base.base.PermissionsActivity;
import com.common.common_base.modle.ContactsInfoResult;
import com.common.common_base.utils.system.PhoneSystemUtils;
import com.common.jwrouterdemo.R;
import com.yanzhenjie.permission.Permission;

import java.util.List;

/**
 * @author : wangb
 * @email :
 * @date : 2018/1/10
 * @desc :
 */

public class TestGetContactsActivity extends PermissionsActivity{
    @Override
    public int getContentViewId(){
        return R.layout.activity_test_contacts;
    }

    @Override
    public void initBundleData(){
    }

    @Override
    public void initData(){
    }

    @Override
    public void onSucceed(int requestCode, @NonNull List<String> grantPermissions){
        if(requestCode == 10086){
            PhoneSystemUtils.goContactsView(this);
        }else if(requestCode == 10010){
            List<ContactsInfoResult> lContacts = PhoneSystemUtils.getContacts(this);
            for(ContactsInfoResult lContact : lContacts){
                LogUtils.e(lContact.getPhoneName() + "phones: " + lContact.getPhoneNumber().get(0));
            }
        }
    }

    public void getContacts(View view){
        switch(view.getId()){
            case R.id.btn1:
                onRequestPermissions(10086, Permission.CONTACTS);
                break;
            case R.id.btn2:
                onRequestPermissions(10010, Permission.CONTACTS);
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null){ return; }
        //处理返回的data,获取选择的联系人信息
        Uri uri = data.getData();
        ContactsInfoResult lPhoneContacts = PhoneSystemUtils.getPhoneContacts(uri, this);
        LogUtils.e("name" + lPhoneContacts.getPhoneName());
        for(String lS : lPhoneContacts.getPhoneNumber()){
            LogUtils.e("phone : " + lS);
        }
    }
}
