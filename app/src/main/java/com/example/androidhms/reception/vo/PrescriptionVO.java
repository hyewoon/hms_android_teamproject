package com.example.androidhms.reception.vo;


public class PrescriptionVO {
	int prescription_record_id, medical_record_id, patient_id;
	String treate_date,treate_day, time, treatment_name, 
	patient_name,doctor_name ,department_name, memo;

	public int getPrescription_record_id() {
		return prescription_record_id;
	}

	public void setPrescription_record_id(int prescription_record_id) {
		this.prescription_record_id = prescription_record_id;
	}

	public int getMedical_record_id() {
		return medical_record_id;
	}

	public void setMedical_record_id(int medical_record_id) {
		this.medical_record_id = medical_record_id;
	}

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	public String getTreate_date() {
		return treate_date;
	}

	public void setTreate_date(String treate_date) {
		this.treate_date = treate_date;
	}

	public String getTreate_day() {
		return treate_day;
	}

	public void setTreate_day(String treate_day) {
		this.treate_day = treate_day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTreatment_name() {
		return treatment_name;
	}

	public void setTreatment_name(String treatment_name) {
		this.treatment_name = treatment_name;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
}
