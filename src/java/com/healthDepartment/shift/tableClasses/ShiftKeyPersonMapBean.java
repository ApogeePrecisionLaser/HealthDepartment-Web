/*
 ShiftWorkBench-2
 */

package com.healthDepartment.shift.tableClasses;

/**
 *
 * @author Tushar Singh
 */
public class ShiftKeyPersonMapBean {
    private String shift_type,location,designation,date,person,remark;
    private int shift_key_person_map_id,person_code;
    private String location_type,zone,zone_no,ward,ward_no,area,area_no,location_no,vehicle_code;

    public String getVehicle_code() {
        return vehicle_code;
    }

    public void setVehicle_code(String vehicle_code) {
        this.vehicle_code = vehicle_code;
    }

    public int getPerson_code() {
        return person_code;
    }

    public void setPerson_code(int person_code) {
        this.person_code = person_code;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_no() {
        return area_no;
    }

    public void setArea_no(String area_no) {
        this.area_no = area_no;
    }

    public String getLocation_no() {
        return location_no;
    }

    public void setLocation_no(String location_no) {
        this.location_no = location_no;
    }

    public String getLocation_type() {
        return location_type;
    }

    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getWard_no() {
        return ward_no;
    }

    public void setWard_no(String ward_no) {
        this.ward_no = ward_no;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getZone_no() {
        return zone_no;
    }

    public void setZone_no(String zone_no) {
        this.zone_no = zone_no;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getShift_key_person_map_id() {
        return shift_key_person_map_id;
    }

    public void setShift_key_person_map_id(int shift_key_person_map_id) {
        this.shift_key_person_map_id = shift_key_person_map_id;
    }

    public String getShift_type() {
        return shift_type;
    }

    public void setShift_type(String shift_type) {
        this.shift_type = shift_type;
    }

}
