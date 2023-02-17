package com.example.androidhms.reception.vo;


public class WardVO {
	int admission_record_id, ward_id, medical_record_id, bed, ward_number;
	String doctor_name, department_name,patient_name, 
	admission_date, admission_time,admission_day,
	discharge_date,discharge_time, discharge_day	
	;


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

	public int getWard_number() {
		return ward_number;
	}

	public void setWard_number(int ward_number) {
		this.ward_number = ward_number;
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

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getAdmission_date() {
		return admission_date;
	}

	public void setAdmission_date(String admission_date) {
		this.admission_date = admission_date;
	}

	public String getAdmission_time() {
		return admission_time;
	}

	public void setAdmission_time(String admission_time) {
		this.admission_time = admission_time;
	}

	public String getAdmission_day() {
		return admission_day;
	}

	public void setAdmission_day(String admission_day) {
		this.admission_day = admission_day;
	}

	public String getDischarge_date() {
		return discharge_date;
	}

	public void setDischarge_date(String discharge_date) {
		this.discharge_date = discharge_date;
	}

	public String getDischarge_time() {
		return discharge_time;
	}

	public void setDischarge_time(String discharge_time) {
		this.discharge_time = discharge_time;
	}

	public String getDischarge_day() {
		return discharge_day;
	}

	public void setDischarge_day(String discharge_day) {
		this.discharge_day = discharge_day;
	}
}
