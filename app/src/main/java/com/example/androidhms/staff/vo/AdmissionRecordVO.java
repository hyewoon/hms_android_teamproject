package com.example.androidhms.staff.vo;

public class AdmissionRecordVO {

    int ward_id, admission_record_id, patient_id, staff_id;
    String patient_name, staff_name, treatment_name, department_name, admission_date, discharge_date;

    public AdmissionRecordVO(int ward_id, int admission_record_id, int patient_id, int staff_id,
                             String patient_name, String staff_name, String treatment_name,
                             String department_name, String admission_date, String discharge_date) {
        this.ward_id = ward_id;
        this.admission_record_id = admission_record_id;
        this.patient_id = patient_id;
        this.staff_id = staff_id;
        this.patient_name = patient_name;
        this.staff_name = staff_name;
        this.treatment_name = treatment_name;
        this.department_name = department_name;
        this.admission_date = admission_date;
        this.discharge_date = discharge_date;
    }

    public int getWard_id() {
        return ward_id;
    }

    public int getAdmission_record_id() {
        return admission_record_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public String getTreatment_name() {
        return treatment_name;
    }

    public String getDepartment_name() {
        return department_name;
    }

    public String getAdmission_date() {
        return admission_date;
    }

    public String getDischarge_date() {
        return discharge_date;
    }

}
