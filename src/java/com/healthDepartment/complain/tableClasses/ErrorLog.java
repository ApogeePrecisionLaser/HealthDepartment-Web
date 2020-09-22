/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.complain.tableClasses;

/**
 *
 * @author Shobha
 */
public class ErrorLog {
    int error_log_id,error_causeby_id;
    String kp1,kp2,error_name,status,date;

    public int getError_causeby_id() {
        return error_causeby_id;
    }

    public void setError_causeby_id(int error_causeby_id) {
        this.error_causeby_id = error_causeby_id;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public int getError_log_id() {
        return error_log_id;
    }

    public void setError_log_id(int error_log_id) {
        this.error_log_id = error_log_id;
    }

    public String getError_name() {
        return error_name;
    }

    public void setError_name(String error_name) {
        this.error_name = error_name;
    }

    public String getKp1() {
        return kp1;
    }

    public void setKp1(String kp1) {
        this.kp1 = kp1;
    }

    public String getKp2() {
        return kp2;
    }

    public void setKp2(String kp2) {
        this.kp2 = kp2;
    }




}
