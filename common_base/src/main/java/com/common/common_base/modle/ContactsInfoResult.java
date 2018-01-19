package com.common.common_base.modle;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author : wangb
 * @email :
 * @date : 2018/1/10
 * @desc :  通讯录信息
 */

public class ContactsInfoResult implements Parcelable{
    private String phoneName;
    private List<String> phoneNumber;

    public ContactsInfoResult(){
    }

    public ContactsInfoResult(String phoneName, List<String> phoneNumber){
        this.phoneName = phoneName;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneName(){
        return phoneName;
    }

    public void setPhoneName(String phoneName){
        this.phoneName = phoneName;
    }

    public List<String> getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(List<String> phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(this.phoneName);
        dest.writeStringList(this.phoneNumber);
    }

    protected ContactsInfoResult(Parcel in){
        this.phoneName = in.readString();
        this.phoneNumber = in.createStringArrayList();
    }

    public static final Parcelable.Creator<ContactsInfoResult> CREATOR = new Parcelable.Creator<ContactsInfoResult>(){
        @Override
        public ContactsInfoResult createFromParcel(Parcel source){
            return new ContactsInfoResult(source);
        }

        @Override
        public ContactsInfoResult[] newArray(int size){
            return new ContactsInfoResult[size];
        }
    };
}
