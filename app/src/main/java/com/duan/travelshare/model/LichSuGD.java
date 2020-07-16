package com.duan.travelshare.model;

import java.io.Serializable;

public class LichSuGD implements Serializable {
    private String IdGD,Date,Gmail,IdPhong,StatusGD;

    public LichSuGD() {
    }

    public LichSuGD(String idGD, String date, String gmail, String idPhong, String statusGD) {
        IdGD = idGD;
        Date = date;
        Gmail = gmail;
        IdPhong = idPhong;
        StatusGD = statusGD;
    }

    public String getIdGD() {
        return IdGD;
    }

    public void setIdGD(String idGD) {
        IdGD = idGD;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
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

    public String getStatusGD() {
        return StatusGD;
    }

    public void setStatusGD(String statusGD) {
        StatusGD = statusGD;
    }
}
