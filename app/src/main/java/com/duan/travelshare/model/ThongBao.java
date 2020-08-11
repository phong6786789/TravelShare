package com.duan.travelshare.model;

import java.io.Serializable;

public class ThongBao implements Serializable {
    private GiaoDich giaoDich;
    private String ngay, thoiGian;

    public ThongBao() {
    }

    public ThongBao(GiaoDich giaoDich, String ngay, String thoiGian) {
        this.giaoDich = giaoDich;
        this.ngay = ngay;
        this.thoiGian = thoiGian;
    }

    public GiaoDich getGiaoDich() {
        return giaoDich;
    }

    public void setGiaoDich(GiaoDich giaoDich) {
        this.giaoDich = giaoDich;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }
}
