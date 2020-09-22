/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.dustbin.tableClasses;


/**
 *
 * @author Administrator
 */
public class Dustbin {
private int dustbin_id,dustbin_no,level_id;
private String dustbin_type,remark,city_location;
private double latitude,longitude,level,height,volume;
private String created_date;

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public int getLevel_id() {
        return level_id;
    }

    public void setLevel_id(int level_id) {
        this.level_id = level_id;
    }

    public String getCity_location() {
        return city_location;
    }

    public void setCity_location(String city_location) {
        this.city_location = city_location;
    }

    public int getDustbin_id() {
        return dustbin_id;
    }

    public void setDustbin_id(int dustbin_id) {
        this.dustbin_id = dustbin_id;
    }

    public String getDustbin_type() {
        return dustbin_type;
    }

    public void setDustbin_type(String dustbin_type) {
        this.dustbin_type = dustbin_type;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getDustbin_no() {
        return dustbin_no;
    }

    public void setDustbin_no(int dustbin_no) {
        this.dustbin_no = dustbin_no;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
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
