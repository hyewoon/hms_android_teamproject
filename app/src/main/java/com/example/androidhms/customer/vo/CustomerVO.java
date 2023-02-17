package com.example.androidhms.customer.vo;

import java.io.Serializable;

public class CustomerVO implements Serializable {
    private int patient_id, social_id, height, weight;
    private String name, pw, gender, email, phone_number, join_date, salt, social, qr, blood_type, allergy, underlying_disease, memo;

    public CustomerVO(int patient_id, int social_id, int height, int weight, String name, String pw, String gender, String email, String phone_number, String join_date, String salt, String social, String qr, String blood_type, String allergy, String underlying_disease, String memo) {
        this.patient_id = patient_id;
        this.social_id = social_id;
        this.height = height;
        this.weight = weight;
        this.name = name;
        this.pw = pw;
        this.gender = gender;
        this.email = email;
        this.phone_number = phone_number;
        this.join_date = join_date;
        this.salt = salt;
        this.social = social;
        this.qr = qr;
        this.blood_type = blood_type;
        this.allergy = allergy;
        this.underlying_disease = underlying_disease;
        this.memo = memo;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public int getSocial_id() {
        return social_id;
    }

    public void setSocial_id(int social_id) {
        this.social_id = social_id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
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

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getUnderlying_disease() {
        return underlying_disease;
    }

    public void setUnderlying_disease(String underlying_disease) {
        this.underlying_disease = underlying_disease;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
