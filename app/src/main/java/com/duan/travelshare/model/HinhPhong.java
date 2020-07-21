package com.duan.travelshare.model;

public class HinhPhong {
    String idHinh, linkHinh;

    public HinhPhong(String idHinh, String linkHinh) {
        this.idHinh = idHinh;
        this.linkHinh = linkHinh;
    }

    public String getIdHinh() {
        return idHinh;
    }

    public void setIdHinh(String idHinh) {
        this.idHinh = idHinh;
    }

    public String getLinkHinh() {
        return linkHinh;
    }

    public void setLinkHinh(String linkHinh) {
        this.linkHinh = linkHinh;
    }
}
