package com.costom.orm.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

/**
 * @author : JoyWong0160
 * @email :
 * @date : 2018/1/16
 * @desc :
 */

@Entity
public class User{
    @Id(autoincrement = true)
    @Unique
    private Long id;
    private String name;
    private Integer age;

    @Generated(hash = 1499888241)
    public User(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Generated(hash = 586692638)
    public User(){
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Integer getAge(){
        return this.age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
