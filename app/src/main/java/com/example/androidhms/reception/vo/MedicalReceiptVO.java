package com.example.androidhms.reception.vo;


import java.io.Serializable;

public class MedicalReceiptVO implements Serializable {
	private int patient_id, staff_id;
	private String time, reserve_date, reserve_date_short, reserve_time, reserve_today, 
			      reserve_time_count, current_time, reserve_day,
				memo, doctor_name, patient_name, department_name, location;

	public int getPatient_id() {
		return patient_id;
	}

	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}

	public int getStaff_id() {
		return staff_id;
	}

	public void setStaff_id(int staff_id) {
		this.staff_id = staff_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getReserve_date() {
		return reserve_date;
	}

	public void setReserve_date(String reserve_date) {
		this.reserve_date = reserve_date;
	}

	public String getReserve_date_short() {
		return reserve_date_short;
	}

	public void setReserve_date_short(String reserve_date_short) {
		this.reserve_date_short = reserve_date_short;
	}

	public String getReserve_time() {
		return reserve_time;
	}

	public void setReserve_time(String reserve_time) {
		this.reserve_time = reserve_time;
	}

	public String getReserve_today() {
		return reserve_today;
	}

	public void setReserve_today(String reserve_today) {
		this.reserve_today = reserve_today;
	}

	public String getReserve_time_count() {
		return reserve_time_count;
	}

	public void setReserve_time_count(String reserve_time_count) {
		this.reserve_time_count = reserve_time_count;
	}

	public String getCurrent_time() {
		return current_time;
	}

	public void setCurrent_time(String current_time) {
		this.current_time = current_time;
	}

	public String getReserve_day() {
		return reserve_day;
	}

	public void setReserve_day(String reserve_day) {
		this.reserve_day = reserve_day;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getDoctor_name() {
		return doctor_name;
	}

	public void setDoctor_name(String doctor_name) {
		this.doctor_name = doctor_name;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
