package com.example.androidhms.customer.vo;

import java.io.Serializable;

public class AdmissionRecordVO implements Serializable {
    private int admission_record_id, ward_id, medical_record_id, bed;
    private String admission_date, discharge_date, treatment_name, name, department_name, ward_number;

    public AdmissionRecordVO(int admission_record_id, int ward_id, int medical_record_id, int bed, String admission_date, String discharge_date, String treatment_name, String name, String department_name, String ward_number) {
        this.admission_record_id = admission_record_id;
        this.ward_id = ward_id;
        this.medical_record_id = medical_record_id;
        this.bed = bed;
        this.admission_date = admission_date;
        this.discharge_date = discharge_date;
        this.treatment_name = treatment_name;
        this.name = name;
        this.department_name = department_name;
        this.ward_number = ward_number;
    }

    public int getAdmission_record_id() {
        return admission_record_id;
    }

    public void setAdmission_record_id(int admission_record_id) {
        this.admission_record_id = admission_record_id;
    }

    public int getWard_id() {
        return ward_id;
    }

    public void setWard_id(int ward_id) {
        this.ward_id = ward_id;
    }

    public int getMedical_record_id() {
        return medical_record_id;
    }

    public void setMedical_record_id(int medical_record_id) {
        this.medical_record_id = medical_record_id;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public String getAdmission_date() {
        return admission_date;
    }

    public void setAdmission_date(String admission_date) {
        this.admission_date = admission_date;
    }

    public String getDischarge_date() {
        return discharge_date;
    }

    public void setDischarge_date(String discharge_date) {
        this.discharge_date = discharge_date;
    }

    public String getTreatment_name() {
        return treatment_name;
    }

    public void setTreatment_name(String treatment_name) {
        this.treatment_name = treatment_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    public String getWard_number() {
        return ward_number;
    }

    public void setWard_number(String ward_number) {
        this.ward_number = ward_number;
    }
}
