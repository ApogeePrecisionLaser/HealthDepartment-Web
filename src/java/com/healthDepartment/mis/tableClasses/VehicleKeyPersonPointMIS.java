/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.tableClasses;

/**
 *
 * @author Shobha
 */
public class VehicleKeyPersonPointMIS {
int vehicle_key_person_point_id;
    int vehicle_key_person_id;
    int point_id;
    int key_person_id;
    String point_name;
    String key_person_name;

    String latitude;
    String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }



    public String getKey_person_name() {
        return key_person_name;
    }

    public void setKey_person_name(String key_person_name) {
        this.key_person_name = key_person_name;
    }



    public int getKey_person_id() {
        return key_person_id;
    }

    public void setKey_person_id(int key_person_id) {
        this.key_person_id = key_person_id;
    }

    public int getPoint_id() {
        return point_id;
    }

    public void setPoint_id(int point_id) {
        this.point_id = point_id;
    }

    public String getPoint_name() {
        return point_name;
    }

    public void setPoint_name(String point_name) {
        this.point_name = point_name;
    }

    public int getVehicle_key_person_id() {
        return vehicle_key_person_id;
    }

    public void setVehicle_key_person_id(int vehicle_key_person_id) {
        this.vehicle_key_person_id = vehicle_key_person_id;
    }

    public int getVehicle_key_person_point_id() {
        return vehicle_key_person_point_id;
    }

    public void setVehicle_key_person_point_id(int vehicle_key_person_point_id) {
        this.vehicle_key_person_point_id = vehicle_key_person_point_id;
    }


}
