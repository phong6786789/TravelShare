package com.duan.travelshare.model;

import java.io.Serializable;

public class ChiTietPhong implements Serializable {
    private String IdPhong,Gmail,Address,IdCmt,Img;
    private int Price;

    public ChiTietPhong() {
    }

    public ChiTietPhong(String idPhong, String gmail, String address, String idCmt, String img, int price) {
        IdPhong = idPhong;
        Gmail = gmail;
        Address = address;
        IdCmt = idCmt;
        Img = img;
        Price = price;
    }

    public String getIdPhong() {
        return IdPhong;
    }

    public void setIdPhong(String idPhong) {
        IdPhong = idPhong;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getIdCmt() {
        return IdCmt;
    }

    public void setIdCmt(String idCmt) {
        IdCmt = idCmt;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }
}
