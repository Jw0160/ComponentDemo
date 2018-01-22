package com.common.common_base.http.retrofit;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/22
 * @desc :
 */

public class BaseResult implements Parcelable, Serializable{
    public Long timestamp;
    public String result;
    public int code;
    public boolean success;
    public String message;

    protected BaseResult(Parcel in){
        result = in.readString();
        code = in.readInt();
        success = in.readByte() != 0;
        message = in.readString();
    }

    public static final Creator<BaseResult> CREATOR = new Creator<BaseResult>(){
        @Override
        public BaseResult createFromParcel(Parcel in){
            return new BaseResult(in);
        }

        @Override
        public BaseResult[] newArray(int size){
            return new BaseResult[size];
        }
    };

    public Long getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(Long timestamp){
        this.timestamp = timestamp;
    }

    public String getResult(){
        return result;
    }

    public void setResult(String result){
        this.result = result;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code){
        this.code = code;
    }

    public boolean isSuccess(){
        return success;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(result);
        dest.writeInt(code);
        dest.writeByte((byte) (success ? 1 : 0));
        dest.writeString(message);
    }
}
