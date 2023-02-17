package com.example.androidhms.staff.vo;

import java.sql.Timestamp;

public class PrescriptionVO {

    int prescription_record_id, staff_id;
    String patient_name, social_id, staff_name, prescription_name, treatment_date;

    public PrescriptionVO(int prescription_record_id, int staff_id, String patient_name, String social_id, String staff_name, String prescription_name, String treatment_date) {
        this.prescription_record_id = prescription_record_id;
        this.staff_id = staff_id;
        this.patient_name = patient_name;
        this.social_id = social_id;
        this.staff_name = staff_name;
        this.prescription_name = prescription_name;
        this.treatment_date = treatment_date;
    }

    public int getPrescription_record_id() {
        return prescription_record_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getSocial_id() {
        return social_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public String getPrescription_name() {
        return prescription_name;
    }

    public String getTreatment_date() {
        return treatment_date;
    }
}
