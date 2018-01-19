package com.common.common_base.utils.system;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.common.common_base.modle.ContactsInfoResult;
import com.common.common_base.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : wangb
 * @email :
 * @date : 2018/1/10
 * @desc :
 */

public class PhoneSystemUtils{
    /**
     * @return :
     * @TODO :跳转到通讯录
     * @parms
     */
    public static void goContactsView(Activity activity){
        Uri uri = Uri.parse("content://contacts/people");
        Intent intent = new Intent(Intent.ACTION_PICK, uri);
        activity.startActivityForResult(intent, 0);
    }

    /**
     * @param uri     data.getData();
     * @param context
     * @return
     * @TODO :解析通讯录返回的Uri
     */
    public static ContactsInfoResult getPhoneContacts(Uri uri, Context context){
        ContactsInfoResult lContactsInfoResult = new ContactsInfoResult();
        //得到ContentResolver对象
        ContentResolver cr = context.getContentResolver();
        //取得电话本中开始一项的光标
        Cursor cursor = cr.query(uri, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            //取得联系人姓名
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
            lContactsInfoResult.setPhoneName(cursor.getString(nameFieldColumnIndex));
            //取得电话号码
            String ContactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + ContactId, null, null);
            List<String> phoneNumbers = new ArrayList<>();
            if(phone != null){
                while(phone.moveToNext()){
                    phoneNumbers.add(phone.getString(phone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ", "").replace("-", ""));
                }
                lContactsInfoResult.setPhoneNumber(phoneNumbers);
            }
            phone.close();
            cursor.close();
        }else{
            return null;
        }
        return lContactsInfoResult;
    }

    /**
     * @param context
     * @return
     * @TODO :获取整个通讯录
     */
    public static List<ContactsInfoResult> getContacts(Context context){
        List<ContactsInfoResult> lContactsInfoResults = new ArrayList<>();
        //联系人的Uri，也就是content://com.android.contacts/contacts
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        //指定获取_id和display_name两列数据，display_name即为姓名
        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
        };
        //根据Uri查询相应的ContentProvider，cursor为获取到的数据集
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            do{
                ContactsInfoResult lContactsInfoResult = new ContactsInfoResult();
                Long id = cursor.getLong(0);
                //获取姓名
                String name = cursor.getString(1).replace(" ", "").replace("-", "");
                //指定获取NUMBER这一列数据
                String[] phoneProjection = new String[]{
                        ContactsContract.CommonDataKinds.Phone.NUMBER
                };
                lContactsInfoResult.setPhoneName(name);
                //根据联系人的ID获取此人的电话号码
                Cursor phonesCusor = context.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        phoneProjection,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id,
                        null,
                        null);
                //因为每个联系人可能有多个电话号码，所以需要遍历
                List<String> phones = new ArrayList<>();
                if(phonesCusor != null && phonesCusor.moveToFirst()){
                    do{
                        String num = phonesCusor.getString(0).replace(" ", "").replace("-", "");
                        phones.add(num);
                    }while(phonesCusor.moveToNext());
                }
                //设置到result中
                lContactsInfoResult.setPhoneNumber(phones);
                lContactsInfoResults.add(lContactsInfoResult);
            }while(cursor.moveToNext());
        }else{
            return null;
        }
        return lContactsInfoResults;
    }

    /**
     * @return :
     * @TODO :Get the width of the screen height with a point spread
     * @parms
     */
    public static Point getScreenWidthHeight(){
        DisplayMetrics dm = Resources.getSystem().getDisplayMetrics();
        int width = dm.widthPixels; // Get the width
        int height = dm.heightPixels; // Get the high
        return new Point(width, height);
    }

    /**
     * @return :
     * @TODO :复制
     * @parms
     */
    public static void copyContext(Context context, String string){
        // 从API11开始android推荐使用android.content.ClipboardManager
        // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 将文本内容放到系统剪贴板里。
        cm.setPrimaryClip(ClipData.newPlainText("newPlainText", string));
        ToastUtil.showToast(context, "复制成功");
    }

    /**
     * 获取手机号码
     *
     * @param context
     * @return
     */
    public static String getPhoneNum(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getLine1Number();
    }

    /**
     * 获取设备ID
     *
     * @param context
     * @return
     */
    public static String getDeviceid(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取IMEI
     *
     * @param context
     * @return
     */
    public static String getImei(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * 获取IMSI
     *
     * @param context
     * @return
     */
    public static String getImsi(Context context){
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }

    /**
     * 是否是小米系统
     *
     * @return
     */
    public static boolean isMIUI(){
        String name = Build.MANUFACTURER;
        if(name.equals("Xiaomi")){
            return true;
        }
        return false;
    }
}
