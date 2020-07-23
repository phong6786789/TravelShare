package com.duan.travelshare.model;

import java.io.Serializable;

public class User implements Serializable {
    private String userName, password, loaiUser;
    private String linkImage;

    public User() {
    }

    public User(String userName, String password, String loaiUser, String linkImage) {
        this.userName = userName;
        this.password = password;
        this.loaiUser = loaiUser;
        this.linkImage = linkImage;
    }

    public User(String userName, String linkImage) {
        this.userName = userName;
        this.linkImage = linkImage;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
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
