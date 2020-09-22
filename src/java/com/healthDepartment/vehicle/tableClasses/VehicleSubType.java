/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.tableClasses;

/**
 *
 * @author Administrator
 */
public class VehicleSubType {
private int vehicle_sub_type_id;
private String vehicle_type,remark,vehicle_sub_type;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVehicle_sub_type() {
        return vehicle_sub_type;
    }

    public void setVehicle_sub_type(String vehicle_sub_type) {
        this.vehicle_sub_type = vehicle_sub_type;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public int getVehicle_sub_type_id() {
        return vehicle_sub_type_id;
    }

    public void setVehicle_sub_type_id(int vehicle_sub_type_id) {
        this.vehicle_sub_type_id = vehicle_sub_type_id;
    }



}
