package com.example.androidhms.reception.vo;


import java.io.Serializable;

public class MedicalRecordVO  implements Serializable {
	private int medical_record_id, staff_id, patient_id;
	private String  record_date, record_time, record_day,record_today,
					treatment_date, prescription_name,
					treatment_name, admission, memo, patient, doctor, department_name ;

	public int getMedical_record_id() {
		return medical_record_id;
	}

	public void setMedical_record_id(int medical_record_id) {
		this.medical_record_id = medical_record_id;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	public String getRecord_date() {
		return record_date;
	}

	public void setRecord_date(String record_date) {
		this.record_date = record_date;
	}

	public String getRecord_time() {
		return record_time;
	}

	public void setRecord_time(String record_time) {
		this.record_time = record_time;
	}

	public String getRecord_day() {
		return record_day;
	}

	public void setRecord_day(String record_day) {
		this.record_day = record_day;
	}

	public String getRecord_today() {
		return record_today;
	}

	public void setRecord_today(String record_today) {
		this.record_today = record_today;
	}

	public String getTreatment_date() {
		return treatment_date;
	}

	public void setTreatment_date(String treatment_date) {
		this.treatment_date = treatment_date;
	}

	public String getPrescription_name() {
		return prescription_name;
	}

	public void setPrescription_name(String prescription_name) {
		this.prescription_name = prescription_name;
	}

	public String getTreatment_name() {
		return treatment_name;
	}

	public void setTreatment_name(String treatment_name) {
		this.treatment_name = treatment_name;
	}

	public String getAdmission() {
		return admission;
	}

	public void setAdmission(String admission) {
		this.admission = admission;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPatient() {
		return patient;
	}

	public void setPatient(String patient) {
		this.patient = patient;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
}
