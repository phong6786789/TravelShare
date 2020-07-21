package com.duan.travelshare.model;

import java.io.Serializable;

public class Phong implements Serializable {
    private String idPhong,namePhong,gmail;
    ChiTietPhong chiTietPhong;
    public Phong() {
    }

    public Phong(String idPhong, String namePhong, String gmail) {
        this.idPhong = idPhong;
        this.namePhong = namePhong;
        this.gmail = gmail;
    }

    public String getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(String idPhong) {
        this.idPhong = idPhong;
    }

    public String getNamePhong() {
        return namePhong;
    }

    public void setNamePhong(String namePhong) {
        this.namePhong = namePhong;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
}
