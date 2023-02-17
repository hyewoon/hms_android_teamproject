package com.example.androidhms.staff.vo;

public class AdmissionMemoVO {

    int admission_memo_id, staff_id;
    String name, memo, write_date;

    public AdmissionMemoVO(int admission_memo_id, int staff_id, String name, String memo, String write_date) {
        this.admission_memo_id = admission_memo_id;
        this.staff_id = staff_id;
        this.name = name;
        this.memo = memo;
        this.write_date = write_date;
    }

    public int getAdmission_memo_id() {
        return admission_memo_id;
    }

    public int getStaff_id() {
        return staff_id;
    }

    public String getName() {
        return name;
    }

    public String getMemo() {
        return memo;
    }

    public String getWrite_date() {
        return write_date;
    }
}
