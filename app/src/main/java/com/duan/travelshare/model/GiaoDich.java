package com.duan.travelshare.model;

import java.io.Serializable;

public class GiaoDich implements Serializable {
    private ChiTietPhong chiTietPhong;
    private FullUser fullUser;
    private String hoTen, cmnd;
    private String tuTime, tuNgay, denTime, denNgay, ghiChu;
    private String trangThai;
    //0 là đang xác nhận, 1 là đã xác nhận, 2 là hủy;
    public GiaoDich() {
    }

    public GiaoDich(ChiTietPhong chiTietPhong, FullUser fullUser, String hoTen, String cmnd, String tuTime, String tuNgay, String denTime, String denNgay, String ghiChu, String trangThai) {
        this.chiTietPhong = chiTietPhong;
        this.fullUser = fullUser;
        this.hoTen = hoTen;
        this.cmnd = cmnd;
        this.tuTime = tuTime;
        this.tuNgay = tuNgay;
        this.denTime = denTime;
        this.denNgay = denNgay;
        this.ghiChu = ghiChu;
        this.trangThai = trangThai;
    }

    public ChiTietPhong getChiTietPhong() {
        return chiTietPhong;
    }

    public void setChiTietPhong(ChiTietPhong chiTietPhong) {
        this.chiTietPhong = chiTietPhong;
    }

    public FullUser getFullUser() {
        return fullUser;
    }

    public void setFullUser(FullUser fullUser) {
        this.fullUser = fullUser;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getCmnd() {
        return cmnd;
    }

    public void setCmnd(String cmnd) {
        this.cmnd = cmnd;
    }

    public String getTuTime() {
        return tuTime;
    }

    public void setTuTime(String tuTime) {
        this.tuTime = tuTime;
    }

    public String getTuNgay() {
        return tuNgay;
    }

    public void setTuNgay(String tuNgay) {
        this.tuNgay = tuNgay;
    }

    public String getDenTime() {
        return denTime;
    }

    public void setDenTime(String denTime) {
        this.denTime = denTime;
    }

    public String getDenNgay() {
        return denNgay;
    }

    public void setDenNgay(String denNgay) {
        this.denNgay = denNgay;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
