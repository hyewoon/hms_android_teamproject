package com.example.androidhms.customer.vo;

import java.io.Serializable;

public class RecieptVO implements Serializable {
    private int staff_id, department_id;
    private String name, social_id, email, phone_number, introduction, department_name;

    public RecieptVO(int staff_id, int department_id, String name, String social_id, String email, String phone_number, String introduction, String department_name) {
        this.staff_id = staff_id;
        this.department_id = department_id;
        this.name = name;
        this.social_id = social_id;
        this.email = email;
        this.phone_number = phone_number;
        this.introduction = introduction;
        this.department_name = department_name;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(int staff_id) {
        this.staff_id = staff_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
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

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }
}
