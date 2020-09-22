/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.tableClasses;

/**
 *
 * @author Administrator
 */
public class Vehicle {
    private int vehicle_id,vehicle_code,vehicle_location_id;
    private String vehicle_sub_type,vehicle_no,mobile_no,remark;
    private String permit_validity;
    private String fitness_validity;
    private String puc_validity;
    private String imei_no;
    private double latitude,longitude;

    public int getVehicle_location_id() {
        return vehicle_location_id;
    }

    public void setVehicle_location_id(int vehicle_location_id) {
        this.vehicle_location_id = vehicle_location_id;
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
    
    public String getFitness_validity() {
        return fitness_validity;
    }

    public void setFitness_validity(String fitness_validity) {
        this.fitness_validity = fitness_validity;
    }

    public String getImei_no() {
        return imei_no;
    }

    public void setImei_no(String imei_no) {
        this.imei_no = imei_no;
    }



    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getPermit_validity() {
        return permit_validity;
    }

    public void setPermit_validity(String permit_validity) {
        this.permit_validity = permit_validity;
    }

    public String getPuc_validity() {
        return puc_validity;
    }

    public void setPuc_validity(String puc_validity) {
        this.puc_validity = puc_validity;
    }

    public int getVehicle_code() {
        return vehicle_code;
    }

    public void setVehicle_code(int vehicle_code) {
        this.vehicle_code = vehicle_code;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        this.vehicle_no = vehicle_no;
    }

    public String getVehicle_sub_type() {
        return vehicle_sub_type;
    }

    public void setVehicle_sub_type(String vehicle_sub_type) {
        this.vehicle_sub_type = vehicle_sub_type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
