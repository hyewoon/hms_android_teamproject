package com.example.androidhms.customer.vo;

import java.io.Serializable;

public class AccountVO implements Serializable {
    private int patient_id;
    private String pw, email, salt, social, qr, date;

    public AccountVO(int patient_id, String pw, String email, String salt, String social, String qr, String date) {
        this.patient_id = patient_id;
        this.pw = pw;
        this.email = email;
        this.salt = salt;
        this.social = social;
        this.qr = qr;
        this.date = date;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getSocial() {
        return social;
    }

    public void setSocial(String social) {
        this.social = social;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
