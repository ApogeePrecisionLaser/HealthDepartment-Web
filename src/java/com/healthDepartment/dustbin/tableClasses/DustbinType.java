/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.dustbin.tableClasses;

/**
 *
 * @author Administrator
 */
public class DustbinType {
private int dustbin_type_id;
private String dustbin_type,remark;
private double height,volume;
    public String getDustbin_type() {
        return dustbin_type;
    }

    public void setDustbin_type(String dustbin_type) {
        this.dustbin_type = dustbin_type;
    }

    public int getDustbin_type_id() {
        return dustbin_type_id;
    }

    public void setDustbin_type_id(int dustbin_type_id) {
        this.dustbin_type_id = dustbin_type_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

}
