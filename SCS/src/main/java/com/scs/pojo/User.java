package com.scs.pojo;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String userName;
    private String md5password;

    public User() {
    }

    public User(Integer id, String userName, String md5password) {
        this.id = id;
        this.userName = userName;
        this.md5password = md5password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMd5password() {
        return md5password;
    }

    public void setMd5password(String md5password) {
        this.md5password = md5password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", md5password='" + md5password + '\'' +
                '}';
    }
}
