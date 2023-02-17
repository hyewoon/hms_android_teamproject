package com.example.androidhms.staff.vo;

import java.sql.Timestamp;

public class MedicalReceiptVO {

    String patient_name, staff_name, time, memo;

    public MedicalReceiptVO(String patient_name, String staff_name, String time, String memo) {
        this.patient_name = patient_name;
        this.staff_name = staff_name;
        this.time = time;
        this.memo = memo;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public String getTime() {
        return time;
    }

    public String getMemo() {
        return memo;
    }
}
