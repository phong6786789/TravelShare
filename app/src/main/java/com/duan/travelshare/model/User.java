package com.duan.travelshare.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName, password, loaiUser;

    public User() {
    }
    public User(String userName, String password, String loaiUser) {
        this.userName = userName;
        this.password = password;
        this.loaiUser = loaiUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoaiUser() {
        return loaiUser;
    }

    public void setLoaiUser(String loaiUser) {
        this.loaiUser = loaiUser;
    }
}
