package com.example.androidhms.customer.vo;

import java.io.Serializable;

public class MedicalReceiptVO implements Serializable {
	private int patient_id, staff_id, receipt_id;
	private String time, memo, name, email, introduction, department_name, location;

	public MedicalReceiptVO(int patient_id, int staff_id, int receipt_id, String time, String memo, String name, String email, String introduction, String department_name, String location) {
		this.patient_id = patient_id;
		this.staff_id = staff_id;
		this.receipt_id = receipt_id;
		this.time = time;
		this.memo = memo;
		this.name = name;
		this.email = email;
		this.introduction = introduction;
		this.department_name = department_name;
		this.location = location;
	}

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

	public int getReceipt_id() {
		return receipt_id;
	}

	public void setReceipt_id(int receipt_id) {
		this.receipt_id = receipt_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
