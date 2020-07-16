package com.duan.travelshare.model;

import java.io.Serializable;

public class ChiTietChuChoThue implements Serializable {
    private String Gmail,NameCCT,AddressCCT,BirthdayCCT,PhoneCCT;

    public ChiTietChuChoThue() {
    }

    public ChiTietChuChoThue(String gmail, String nameCCT, String addressCCT, String birthdayCCT, String phoneCCT) {
        Gmail = gmail;
        NameCCT = nameCCT;
        AddressCCT = addressCCT;
        BirthdayCCT = birthdayCCT;
        PhoneCCT = phoneCCT;
    }

    public String getGmail() {
        return Gmail;
    }

    public void setGmail(String gmail) {
        Gmail = gmail;
    }

    public String getNameCCT() {
        return NameCCT;
    }

    public void setNameCCT(String nameCCT) {
        NameCCT = nameCCT;
    }

    public String getAddressCCT() {
        return AddressCCT;
    }

    public void setAddressCCT(String addressCCT) {
        AddressCCT = addressCCT;
    }

    public String getBirthdayCCT() {
        return BirthdayCCT;
    }

    public void setBirthdayCCT(String birthdayCCT) {
        BirthdayCCT = birthdayCCT;
    }

    public String getPhoneCCT() {
        return PhoneCCT;
    }

    public void setPhoneCCT(String phoneCCT) {
        PhoneCCT = phoneCCT;
    }
}
