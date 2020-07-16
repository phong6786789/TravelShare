package com.duan.travelshare.model;

import java.io.Serializable;

public class Phong implements Serializable {
    private String IdPhong,NamePhong,Gmail;

    public Phong() {
    }

    public Phong(String idPhong, String namePhong, String gmail) {
        IdPhong = idPhong;
        NamePhong = namePhong;
        Gmail = gmail;
    }

    public String getIdPhong() {
        return IdPhong;
    }

    public void setIdPhong(String idPhong) {
        IdPhong = idPhong;
    }

    public String getNamePhong() {
        return NamePhong;
    }

    public void setNamePhong(String namePhong) {
        NamePhong = namePhong;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }
}
