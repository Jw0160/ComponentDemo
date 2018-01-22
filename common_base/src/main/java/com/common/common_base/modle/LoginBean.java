package com.common.common_base.modle;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/22
 * @desc :
 */

public class LoginBean{

    /**
     * username : 18684652827
     * password : 123456
     * mallBrandId : 11992444753960964
     */

    private String username;
    private String password;
    private Long mallBrandId;

    public LoginBean(String username, String password, Long mallBrandId){
        this.username = username;
        this.password = password;
        this.mallBrandId = mallBrandId;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public Long getMallBrandId(){
        return mallBrandId;
    }

    public void setMallBrandId(Long mallBrandId){
        this.mallBrandId = mallBrandId;
    }
}
