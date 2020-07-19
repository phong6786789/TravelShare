package com.duan.travelshare.model;

import java.io.Serializable;

public class ChiTietPhong implements Serializable {
    private String idPhong,gmail,address,idCmt,img;
    private int price;

    public ChiTietPhong() {
    }

    public ChiTietPhong(String idPhong, String gmail, String address, String idCmt, String img, int price) {
        this.idPhong = idPhong;
        this.gmail = gmail;
        this.address = address;
        this.idCmt = idCmt;
        this.img = img;
        this.price = price;
    }

    public String getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(String idPhong) {
        this.idPhong = idPhong;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCmt() {
        return idCmt;
    }

    public void setIdCmt(String idCmt) {
        this.idCmt = idCmt;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
