package com.duan.travelshare.model;

import java.io.Serializable;

public class DanhSach implements Serializable {
    private String IdDanhSach,Gmail,IdPhong;

    public DanhSach() {
    }

    public DanhSach(String idDanhSach, String gmail, String idPhong) {
        IdDanhSach = idDanhSach;
        Gmail = gmail;
        IdPhong = idPhong;
    }

    public String getIdDanhSach() {
        return IdDanhSach;
    }

    public void setIdDanhSach(String idDanhSach) {
        IdDanhSach = idDanhSach;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public String getIdPhong() {
        return IdPhong;
    }

    public void setIdPhong(String idPhong) {
        IdPhong = idPhong;
    }
}
