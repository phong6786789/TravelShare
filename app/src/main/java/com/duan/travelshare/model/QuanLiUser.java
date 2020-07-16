package com.duan.travelshare.model;

import java.io.Serializable;

public class QuanLiUser implements Serializable {
    private String User,LockUser;

    public QuanLiUser() {
    }

    public QuanLiUser(String user, String lockUser) {
        User = user;
        LockUser = lockUser;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getLockUser() {
        return LockUser;
    }

    public void setLockUser(String lockUser) {
        LockUser = lockUser;
    }
}
