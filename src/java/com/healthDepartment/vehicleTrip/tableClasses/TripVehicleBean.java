/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.tableClasses;

/**
 *
 * @author Shobha
 */
public class TripVehicleBean {

   String trip_name;
   String remark;
    int vehicle_code;
    int trip_vehicle_map_id;
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    

    public int getTrip_vehicle_map_id() {
        return trip_vehicle_map_id;
    }

    public void setTrip_vehicle_map_id(int trip_vehicle_map_id) {
        this.trip_vehicle_map_id = trip_vehicle_map_id;
    }

    

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }

   

    public int getVehicle_code() {
        return vehicle_code;
    }

    public void setVehicle_code(int vehicle_code) {
        this.vehicle_code = vehicle_code;
    }



}
