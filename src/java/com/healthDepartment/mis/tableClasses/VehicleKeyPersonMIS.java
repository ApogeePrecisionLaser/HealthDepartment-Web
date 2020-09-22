/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.tableClasses;

/**
 *
 * @author Shobha
 */
public class VehicleKeyPersonMIS {
int vehicle_key_person_id;
    int vehicle_id;
    int key_person_id;

    String key_person_name;
    int vehicle_code;

    public String getKey_person_name() {
        return key_person_name;
    }

    public void setKey_person_name(String key_person_name) {
        this.key_person_name = key_person_name;
    }

    public int getVehicle_code() {
        return vehicle_code;
    }

    public void setVehicle_code(int vehicle_code) {
        this.vehicle_code = vehicle_code;
    }



    public int getKey_person_id() {
        return key_person_id;
    }

    public void setKey_person_id(int key_person_id) {
        this.key_person_id = key_person_id;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public int getVehicle_key_person_id() {
        return vehicle_key_person_id;
    }

    public void setVehicle_key_person_id(int vehicle_key_person_id) {
        this.vehicle_key_person_id = vehicle_key_person_id;
    }



}
