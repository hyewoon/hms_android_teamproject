package com.example.androidhms.reception.vo;

public class PatientNameVO {

  private  int patient_id;
  private  String name, gender, phone_number, social_id;

  public PatientNameVO(int patient_id, String name, String gender, String phone_number, String social_id) {
    this.patient_id = patient_id;
    this.name = name;
    this.gender = gender;
    this.phone_number = phone_number;
    this.social_id = social_id;
  }

  public PatientNameVO() {

  }

  public int getPatient_id() {
    return patient_id;
  }

  public void setPatient_id(int patient_id) {
    this.patient_id = patient_id;
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

  public String getSocial_id() {
    return social_id;
  }

  public void setSocial_id(String social_id) {
    this.social_id = social_id;
  }
}
