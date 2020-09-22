/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.tableClasses;

/**
 *
 * @author Shobha
 */
public class ShiftBenificiaryDetailMIS {
private int shift_key_person_detail_id,ride_no_from,ride_no_to, skpd_id,cordinate_id,attendence_id;
private String start_time, end_time, remark ,location,name,image_name ;
private String  father_name,mobile_no,occupation_name,is_residencial,date,status,reason,attendence;
private String  occupation_type,emp_name,emp_code;
private double latitude,longitude;
private String[] a_attendence_id,a_attendence;

    public String[] getA_attendence() {
        return a_attendence;
    }

    public void setA_attendence(String[] a_attendence) {
        this.a_attendence = a_attendence;
    }

    public String[] getA_attendence_id() {
        return a_attendence_id;
    }

    public void setA_attendence_id(String[] a_attendence_id) {
        this.a_attendence_id = a_attendence_id;
    }

    public String getAttendence() {
        return attendence;
    }

    public void setAttendence(String attendence) {
        this.attendence = attendence;
    }

    public int getAttendence_id() {
        return attendence_id;
    }

    public void setAttendence_id(int attendence_id) {
        this.attendence_id = attendence_id;
    }


    public int getCordinate_id() {
        return cordinate_id;
    }

    public void setCordinate_id(int cordinate_id) {
        this.cordinate_id = cordinate_id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(String emp_code) {
        this.emp_code = emp_code;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getOccupation_type() {
        return occupation_type;
    }

    public void setOccupation_type(String occupation_type) {
        this.occupation_type = occupation_type;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public String getIs_residencial() {
        return is_residencial;
    }

    public void setIs_residencial(String is_residencial) {
        this.is_residencial = is_residencial;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getOccupation_name() {
        return occupation_name;
    }

    public void setOccupation_name(String occupation_name) {
        this.occupation_name = occupation_name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

public int getSkpd_id() {
        return skpd_id;
    }

    public void setSkpd_id(int skpd_id) {
        this.skpd_id = skpd_id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRide_no_from() {
        return ride_no_from;
    }

    public void setRide_no_from(int ride_no_from) {
        this.ride_no_from = ride_no_from;
    }

    public int getRide_no_to() {
        return ride_no_to;
    }

    public void setRide_no_to(int ride_no_to) {
        this.ride_no_to = ride_no_to;
    }

    public int getShift_key_person_detail_id() {
        return shift_key_person_detail_id;
    }

    public void setShift_key_person_detail_id(int shift_key_person_detail_id) {
        this.shift_key_person_detail_id = shift_key_person_detail_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }


}
