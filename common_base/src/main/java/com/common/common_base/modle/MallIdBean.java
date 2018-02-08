package com.common.common_base.modle;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/2/8
 * @desc :
 */

public class MallIdBean{
    public MallIdBean(long mallId){
        this.mallId = mallId;
    }

    /**
     * mallId : 123123123123
     */

    private long mallId;

    public long getMallId(){
        return mallId;
    }

    public void setMallId(long mallId){
        this.mallId = mallId;
    }
}
