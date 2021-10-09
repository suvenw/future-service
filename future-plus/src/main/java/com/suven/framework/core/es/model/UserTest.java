package com.suven.framework.core.es.model;

import com.suven.framework.core.es.EsIk;

import java.io.Serializable;

/**
 * @Author dongxie
 * @Description: es 公共api代码例子  操作的实体对象
 * @CreateDate 2019-09-11  16:29
 **/
public class UserTest implements Serializable {

    private String id;
    private String userId;
    @EsIk
    private String name;
    @EsIk
    private String context;
    private String sex;
    private int age;
    


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }


    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }




}
