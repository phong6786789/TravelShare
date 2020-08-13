package com.duan.travelshare.model;

import java.io.Serializable;

public class Save implements Serializable {
   private   ChiTietPhong chiTietPhong;

    public Save(ChiTietPhong chiTietPhong) {
        this.chiTietPhong = chiTietPhong;
    }

    public Save() {
    }

    public ChiTietPhong getChiTietPhong() {
        return chiTietPhong;
    }

    public void setChiTietPhong(ChiTietPhong chiTietPhong) {
        this.chiTietPhong = chiTietPhong;
    }
}
