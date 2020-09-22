/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.tableClasses;

import java.util.Map;

/**
 *
 * @author SoftTech
 */
public class OrgDetailEntry {
    private String organisation;
    private int organisation_id;
    private int org_map_id;
    private int organisation_type_id;
    private String org_type_name;
    private int org_sub_type_id;
    private String org_sub_type;
    // _____________________ Office Detail ______________
    private Map<Integer, String> orgOfficeList;
    private String office_name,office_code;
    private int office_id;
    private String office_type;
    private int office_type_id;
    private String office_city;
    private int office_city_id;
    private String office_address1;
    private String office_address2;
    private String office_mail_id1;
    private String office_mail_id2;
    private String office_mobile1;
    private String office_mobile2;
    private String office_landLine1;
    private String office_landLine2;
    // _______________ Person Name ___________
    private Map<Integer, String> personList;
    private String salutation;
    private String employeeId;
    private int keyPersonId;
    private String keyperson;
    private int desiganition_id;
    private String designation;
    private String person_city;
    private int person_city_id;
    private String person_address1;
    private String person_address2;
    private String person_mail_id1;
    private String person_mail_id2;
    private String person_mobile1;
    private String person_mobile2;
    private String person_landLine1;
    private String person_landLine2;
    private String image_name;
      private String father_name;
      private int age;
    private int general_image_details_id;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFather_name() {
        return father_name;
    }

    public void setFather_name(String father_name) {
        this.father_name = father_name;
    }

    public int getGeneral_image_details_id() {
        return general_image_details_id;
    }

    public void setGeneral_image_details_id(int general_image_details_id) {
        this.general_image_details_id = general_image_details_id;
    }

    

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public int getOrg_map_id() {
        return org_map_id;
    }

    public void setOrg_map_id(int org_map_id) {
        this.org_map_id = org_map_id;
    }

    public int getKeyPersonId() {
        return keyPersonId;
    }

    public void setKeyPersonId(int keyPersonId) {
        this.keyPersonId = keyPersonId;
    }

    public Map<Integer, String> getPersonList() {
        return personList;
    }

    public void setPersonList(Map<Integer, String> personList) {
        this.personList = personList;
    }

    public Map<Integer, String> getOrgOfficeList() {
        return orgOfficeList;
    }

    public void setOrgOfficeList(Map<Integer, String> orgOfficeList) {
        this.orgOfficeList = orgOfficeList;
    }

    
    public String getOffice_type() {
        return office_type;
    }

    public void setOffice_type(String office_type) {
        this.office_type = office_type;
    }


    public int getDesiganition_id() {
        return desiganition_id;
    }

    public void setDesiganition_id(int desiganition_id) {
        this.desiganition_id = desiganition_id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getKeyperson() {
        return keyperson;
    }

    public void setKeyperson(String keyperson) {
        this.keyperson = keyperson;
    }

    public String getOffice_address1() {
        return office_address1;
    }

    public void setOffice_address1(String office_address1) {
        this.office_address1 = office_address1;
    }

    public String getOffice_address2() {
        return office_address2;
    }

    public void setOffice_address2(String office_address2) {
        this.office_address2 = office_address2;
    }

    public String getOffice_city() {
        return office_city;
    }

    public void setOffice_city(String office_city) {
        this.office_city = office_city;
    }

    public int getOffice_city_id() {
        return office_city_id;
    }

    public void setOffice_city_id(int office_city_id) {
        this.office_city_id = office_city_id;
    }

    public int getOffice_id() {
        return office_id;
    }

    public void setOffice_id(int office_id) {
        this.office_id = office_id;
    }

    public String getOffice_landLine1() {
        return office_landLine1;
    }

    public void setOffice_landLine1(String office_landLine1) {
        this.office_landLine1 = office_landLine1;
    }

    public String getOffice_landLine2() {
        return office_landLine2;
    }

    public void setOffice_landLine2(String office_landLine2) {
        this.office_landLine2 = office_landLine2;
    }

    public String getOffice_mail_id1() {
        return office_mail_id1;
    }

    public void setOffice_mail_id1(String office_mail_id1) {
        this.office_mail_id1 = office_mail_id1;
    }

    public String getOffice_mail_id2() {
        return office_mail_id2;
    }

    public void setOffice_mail_id2(String office_mail_id2) {
        this.office_mail_id2 = office_mail_id2;
    }

    public String getOffice_mobile1() {
        return office_mobile1;
    }

    public void setOffice_mobile1(String office_mobile1) {
        this.office_mobile1 = office_mobile1;
    }

    public String getOffice_mobile2() {
        return office_mobile2;
    }

    public void setOffice_mobile2(String office_mobile2) {
        this.office_mobile2 = office_mobile2;
    }

    public String getOffice_name() {
        return office_name;
    }

    public void setOffice_name(String office_name) {
        this.office_name = office_name;
    }

    public int getOffice_type_id() {
        return office_type_id;
    }

    public void setOffice_type_id(int office_type_id) {
        this.office_type_id = office_type_id;
    }

    public String getOrg_sub_type() {
        return org_sub_type;
    }

    public void setOrg_sub_type(String org_sub_type) {
        this.org_sub_type = org_sub_type;
    }

    public int getOrg_sub_type_id() {
        return org_sub_type_id;
    }

    public void setOrg_sub_type_id(int org_sub_type_id) {
        this.org_sub_type_id = org_sub_type_id;
    }

    public String getOrg_type_name() {
        return org_type_name;
    }

    public void setOrg_type_name(String org_type_name) {
        this.org_type_name = org_type_name;
    }

    

    public String getOrganisation() {
        return organisation;
    }

    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    public int getOrganisation_id() {
        return organisation_id;
    }

    public void setOrganisation_id(int organisation_id) {
        this.organisation_id = organisation_id;
    }

    public int getOrganisation_type_id() {
        return organisation_type_id;
    }

    public void setOrganisation_type_id(int organisation_type_id) {
        this.organisation_type_id = organisation_type_id;
    }

    public String getPerson_address1() {
        return person_address1;
    }

    public void setPerson_address1(String person_address1) {
        this.person_address1 = person_address1;
    }

    public String getPerson_address2() {
        return person_address2;
    }

    public void setPerson_address2(String person_address2) {
        this.person_address2 = person_address2;
    }

    public String getPerson_city() {
        return person_city;
    }

    public void setPerson_city(String person_city) {
        this.person_city = person_city;
    }

    public int getPerson_city_id() {
        return person_city_id;
    }

    public void setPerson_city_id(int person_city_id) {
        this.person_city_id = person_city_id;
    }

    public String getPerson_landLine1() {
        return person_landLine1;
    }

    public void setPerson_landLine1(String person_landLine1) {
        this.person_landLine1 = person_landLine1;
    }

    public String getPerson_landLine2() {
        return person_landLine2;
    }

    public void setPerson_landLine2(String person_landLine2) {
        this.person_landLine2 = person_landLine2;
    }

    public String getPerson_mail_id1() {
        return person_mail_id1;
    }

    public void setPerson_mail_id1(String person_mail_id1) {
        this.person_mail_id1 = person_mail_id1;
    }

    public String getPerson_mail_id2() {
        return person_mail_id2;
    }

    public void setPerson_mail_id2(String person_mail_id2) {
        this.person_mail_id2 = person_mail_id2;
    }

    public String getPerson_mobile1() {
        return person_mobile1;
    }

    public void setPerson_mobile1(String person_mobile1) {
        this.person_mobile1 = person_mobile1;
    }

    public String getPerson_mobile2() {
        return person_mobile2;
    }

    public void setPerson_mobile2(String person_mobile2) {
        this.person_mobile2 = person_mobile2;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    /**
     * @return the office_code
     */
    public String getOffice_code() {
        return office_code;
    }

    /**
     * @param office_code the office_code to set
     */
    public void setOffice_code(String office_code) {
        this.office_code = office_code;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    
}
