/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.shift.tableClasses;

/**
 *
 * @author Administrator
 */
public class LocationTypeBean {
  private int  location_type_id;
  private String location_type_name,remark;

    public int getLocation_type_id() {
        return location_type_id;
    }

    public void setLocation_type_id(int location_type_id) {
        this.location_type_id = location_type_id;
    }

    public String getLocation_type_name() {
        return location_type_name;
    }

    public void setLocation_type_name(String location_type_name) {
        this.location_type_name = location_type_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
