package com.duan.travelshare.model;

import java.io.Serializable;

public class Save implements Serializable {
    public String email;
   private   ChiTietPhong chiTietPhong;

    public Save() {
    }

    public Save(String email, ChiTietPhong chiTietPhong) {
        this.email = email;
        this.chiTietPhong = chiTietPhong;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ChiTietPhong getChiTietPhong() {
        return chiTietPhong;
    }

    public void setChiTietPhong(ChiTietPhong chiTietPhong) {
        this.chiTietPhong = chiTietPhong;
    }
}
