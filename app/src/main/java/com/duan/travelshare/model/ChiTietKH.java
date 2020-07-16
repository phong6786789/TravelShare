package com.duan.travelshare.model;

import java.io.Serializable;

public class ChiTietKH implements Serializable {
    private String Gmail,NameKH,AddressKH,LatMapKH,LongMapKH,BrithdayKH,PhoneKH;

    public ChiTietKH() {
    }
    public ChiTietKH(String gmail, String nameKH, String addressKH, String latMapKH, String longMapKH, String brithdayKH, String phoneKH) {
        Gmail = gmail;
        NameKH = nameKH;
        AddressKH = addressKH;
        LatMapKH = latMapKH;
        LongMapKH = longMapKH;
        BrithdayKH = brithdayKH;
        PhoneKH = phoneKH;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public String getNameKH() {
        return NameKH;
    }

    public void setNameKH(String nameKH) {
        NameKH = nameKH;
    }

    public String getAddressKH() {
        return AddressKH;
    }

    public void setAddressKH(String addressKH) {
        AddressKH = addressKH;
    }

    public String getLatMapKH() {
        return LatMapKH;
    }

    public void setLatMapKH(String latMapKH) {
        LatMapKH = latMapKH;
    }

    public String getLongMapKH() {
        return LongMapKH;
    }

    public void setLongMapKH(String longMapKH) {
        LongMapKH = longMapKH;
    }

    public String getBrithdayKH() {
        return BrithdayKH;
    }

    public void setBrithdayKH(String brithdayKH) {
        BrithdayKH = brithdayKH;
    }

    public String getPhoneKH() {
        return PhoneKH;
    }

    public void setPhoneKH(String phoneKH) {
        PhoneKH = phoneKH;
    }
}
