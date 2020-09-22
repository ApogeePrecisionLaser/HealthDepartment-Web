/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.organization.tableClasses;

/**
 *
 * @author Administrator
 */
public class RwaBeneficiaryBean {

    private int rwa_beneficiary_mapping_id;
     private String rwa_name;
     private int emp_code;
     private String b_name;

    public String getB_name() {
        return b_name;
    }

    public void setB_name(String b_name) {
        this.b_name = b_name;
    }

    public int getEmp_code() {
        return emp_code;
    }

    public void setEmp_code(int emp_code) {
        this.emp_code = emp_code;
    }

    public int getRwa_beneficiary_mapping_id() {
        return rwa_beneficiary_mapping_id;
    }

    public void setRwa_beneficiary_mapping_id(int rwa_beneficiary_mapping_id) {
        this.rwa_beneficiary_mapping_id = rwa_beneficiary_mapping_id;
    }

    public String getRwa_name() {
        return rwa_name;
    }

    public void setRwa_name(String rwa_name) {
        this.rwa_name = rwa_name;
    }

     
}
