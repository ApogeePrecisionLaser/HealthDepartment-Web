/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.tableClasses;

/**
 *
 * @author Shobha
 */
public class RouteName {
private int route_name_id;
    private String route_type,route_name;
    private String route_no;
    private String remark;

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRoute_name_id() {
        return route_name_id;
    }

    public void setRoute_name_id(int route_name_id) {
        this.route_name_id = route_name_id;
    }

    public String getRoute_no() {
        return route_no;
    }

    public void setRoute_no(String route_no) {
        this.route_no = route_no;
    }

    public String getRoute_type() {
        return route_type;
    }

    public void setRoute_type(String route_type) {
        this.route_type = route_type;
    }

  
}
