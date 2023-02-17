package com.example.androidhms.staff.vo;

import java.io.Serializable;

public class PatientVO implements Serializable {

    private int patient_id, height, weight;
    private String name, gender, phone_number, blood_type, allergy, underlying_disease, memo, social_id;

    public PatientVO(int patient_id, int height, int weight, String social_id, String name, String gender, String phone_number, String blood_type, String allergy, String underlying_disease, String memo) {
        this.patient_id = patient_id;
        this.height = height;
        this.weight = weight;
        this.social_id = social_id;
        this.name = name;
        this.gender = gender;
        this.phone_number = phone_number;
        this.blood_type = blood_type;
        this.allergy = allergy;
        this.underlying_disease = underlying_disease;
        this.memo = memo;
    }

    public PatientVO() {}

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
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

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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
