/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.tableClasses;

/**
 *
 * @author Shobha
 */
public class RouteBean {
private int route_id;
    private int order_no;
    private int stopage_id;
    private int route_name_id;
    private String remark;
    private String stopage_name;
    private String route_name;

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getRoute_id() {
        return route_id;
    }

    public void setRoute_id(int route_id) {
        this.route_id = route_id;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public int getRoute_name_id() {
        return route_name_id;
    }

    public void setRoute_name_id(int route_name_id) {
        this.route_name_id = route_name_id;
    }

    public int getStopage_id() {
        return stopage_id;
    }

    public void setStopage_id(int stopage_id) {
        this.stopage_id = stopage_id;
    }

    public String getStopage_name() {
        return stopage_name;
    }

    public void setStopage_name(String stopage_name) {
        this.stopage_name = stopage_name;
    }
}
