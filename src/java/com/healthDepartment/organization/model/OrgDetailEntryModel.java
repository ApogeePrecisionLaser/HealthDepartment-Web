/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.OrgDetailEntry;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Ritesh
 */
public class OrgDetailEntryModel {

    private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
//    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("OrgDetailEntryModel setConnection() Error: " + e);
        }
    }
  
    public OrgDetailEntry showData(String organisation) {
      //  organisation = krutiToUnicode.convert_to_unicode(organisation);
        OrgDetailEntry orgDE = new OrgDetailEntry();
        String query = "SELECT org.organisation_id, organisation_name, org_type_name ,organisation_sub_type_name, om.org_map_id , "
                + " of1.org_office_id ,org_office_code,of1.org_office_name, office_type ,c.city_name AS officeCity , of1.address_line1, of1.address_line2, of1.email_id1, of1.email_id2, of1.mobile_no1, of1.mobile_no2, of1.landline_no1, of1.landline_no2, "
                + " emp_code, salutation, k.key_person_id, key_person_name , d.designation ,ck.city_name AS personCity, k.address_line1, k.address_line2, k.father_name,k.age,k.email_id1, k.email_id2 ,k.mobile_no1, k.mobile_no2, k.landline_no1, k.landline_no2 "
                + " FROM organisation_name AS org "   
                + " LEFT JOIN (org_office AS of1 LEFT JOIN ( key_person AS k) ON  k.org_office_id = of1.org_office_id) ON of1.organisation_id = org.organisation_id "
                + " LEFT JOIN (city AS c) ON c.city_id = of1.city_id "
                + " LEFT JOIN ( city AS ck ) ON ck.city_id = k.city_id "
                + " LEFT JOIN ( org_office_type AS oft ) ON oft.office_type_id = of1.office_type_id"
                + " LEFT JOIN ( designation AS d ) ON k.designation_id = d.designation_id"
                + " LEFT JOIN ( organisation_map AS om, organisation_sub_type AS ost, organisation_type AS ot )ON org.organisation_id = om.organisation_id AND om.organisation_sub_type_id = ost.organisation_sub_type_id "
                + "  AND ost.organisation_type_id = ot.organisation_type_id "//AND om.organisation_type_id = ot.organisation_type_id
                + " WHERE organisation_name = ? "
                + " GROUP BY org.organisation_id ORDER BY organisation_name, org_office_name, key_person_name ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, organisation);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                orgDE.setOrganisation_id(rset.getInt("organisation_id"));
                orgDE.setOrganisation(rset.getString("organisation_name"));
                orgDE.setOrg_type_name(rset.getString("org_type_name"));
                orgDE.setOrg_sub_type(rset.getString("organisation_sub_type_name"));
                orgDE.setOrg_map_id(rset.getInt("org_map_id"));
                // Office Detail -----
                orgDE.setOrgOfficeList(getOrgOfficeList(rset.getInt("organisation_id")));
                orgDE.setOffice_id(rset.getInt("org_office_id"));
                orgDE.setOffice_code(rset.getString("org_office_code"));
                orgDE.setOffice_name(rset.getString("org_office_name"));
                orgDE.setOffice_type(rset.getString("office_type"));
                orgDE.setOffice_city(rset.getString("officeCity"));
                orgDE.setOffice_address1(rset.getString("of1.address_line1"));
                orgDE.setOffice_address2(rset.getString("of1.address_line2"));
                orgDE.setOffice_mail_id1(rset.getString("of1.email_id1"));
                orgDE.setOffice_mail_id2(rset.getString("of1.email_id2"));
                orgDE.setOffice_mobile1(rset.getString("of1.mobile_no1"));
                orgDE.setOffice_mobile2(rset.getString("of1.mobile_no2"));
                orgDE.setOffice_landLine1(rset.getString("of1.landline_no1"));
                orgDE.setOffice_landLine2(rset.getString("of1.landline_no2"));
                // -- Person Detail -----------
                orgDE.setPersonList(getPersonlist(rset.getInt("organisation_id"), rset.getInt("org_office_id")));
                orgDE.setEmployeeId(rset.getString("emp_code"));
                orgDE.setSalutation(rset.getString("salutation"));
                orgDE.setKeyPersonId(rset.getInt("key_person_id"));
                orgDE.setKeyperson(rset.getString("key_person_name"));
                orgDE.setDesignation(rset.getString("designation"));
                orgDE.setPerson_city(rset.getString("personCity"));
                orgDE.setFather_name(rset.getString("k.father_name"));
                orgDE.setAge(rset.getInt("k.age"));
                orgDE.setPerson_address1(rset.getString("k.address_line1"));
                 orgDE.setPerson_address2(rset.getString("k.address_line2"));     
                orgDE.setPerson_mail_id1(rset.getString("k.email_id1"));
                orgDE.setPerson_mail_id2(rset.getString("k.email_id2"));
                orgDE.setPerson_mobile1(rset.getString("k.mobile_no1"));
                orgDE.setPerson_mobile2(rset.getString("k.mobile_no2"));
                orgDE.setPerson_landLine1(rset.getString("k.landline_no1"));
                orgDE.setPerson_landLine2(rset.getString("k.landline_no2"));

            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return orgDE;
    }

    public Map<Integer, String> getOrgOfficeList(int orgId) {
        Map<Integer, String> orgOfficeList = new LinkedHashMap<Integer, String>();
        try {
            String query = "SELECT oo.org_office_id, oo.org_office_name "
                    + "FROM org_office AS oo, organisation_name AS orgN "
                    + "WHERE oo.organisation_id = orgN.organisation_id AND orgN.organisation_id = ? "
                    + "ORDER BY oo.org_office_name ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, orgId);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                orgOfficeList.put(rset.getInt("org_office_id"), rset.getString("org_office_name"));
            }
        } catch (Exception e) {
            System.out.println("OrgDetailEntryModel getOrgOfficeList() Error: " + e);
        }
        return orgOfficeList;
    }

    public Map<Integer, String> getPersonlist(int orgId, int officeId) {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        String query = " SELECT k.key_person_id, key_person_name FROM key_person AS k, org_office AS of1, organisation_name As org "
                + " WHERE k.org_office_id = of1.org_office_id AND of1.organisation_id = org.organisation_id "
                + " AND org.organisation_id  = ? AND of1.org_office_id = ?  "
                + "  ORDER BY key_person_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, orgId);
            pstmt.setInt(2, officeId);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                map.put(rset.getInt("key_person_id"), rset.getString("key_person_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return map;
    }

    public String getOfficePersonDetail(int organisationId, int officeId) {
        String jSON_format = "";
        String query = " SELECT organisation_name, "
                + " of1.org_office_id ,org_office_code,of1.org_office_name, office_type ,c.city_name AS officeCity , of1.address_line1, of1.address_line2, of1.email_id1, of1.email_id2, of1.mobile_no1, of1.mobile_no2, of1.landline_no1, of1.landline_no2, "
                + " salutation, key_person_name , d.designation ,ck.city_name AS personCity, k.address_line1, k.address_line2, k.email_id1, k.email_id2 ,k.mobile_no1, k.mobile_no2, k.landline_no1, k.landline_no2,k.emp_code "
                + " FROM organisation_name AS org "
                + " LEFT JOIN (org_office AS of1 LEFT JOIN ( key_person AS k) ON  k.org_office_id = of1.org_office_id) ON of1.organisation_id = org.organisation_id "
                + " LEFT JOIN (city AS c) ON c.city_id = of1.city_id "
                + " LEFT JOIN ( city AS ck ) ON ck.city_id = k.city_id "
                + " LEFT JOIN ( org_office_type AS oft ) ON oft.office_type_id = of1.office_type_id "
                + " LEFT JOIN ( designation AS d ) ON k.designation_id = d.designation_id "
                + " WHERE org.organisation_id= ? AND of1.org_office_id = ? "
                + " GROUP BY org.organisation_id ORDER BY organisation_name, org_office_name, key_person_name LIMIT 1 ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, organisationId);
            pstmt.setInt(2, officeId);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                jSON_format = "{ ";
                jSON_format = jSON_format + "\"org_office_code\"" + ": " + "\"" + rset.getString("org_office_code") + "\", ";
                jSON_format = jSON_format + "\"org_office_name\"" + ": " + "\"" + rset.getString("org_office_name") + "\", ";
                jSON_format = jSON_format + "\"office_type\"" + ": " + "\"" + rset.getString("office_type") + "\", ";
                jSON_format = jSON_format + "\"officeCity\"" + ": " + "\"" + rset.getString("officeCity") + "\", ";
                jSON_format = jSON_format + "\"address_line1\"" + ": " + "\"" + rset.getString("of.address_line1") + "\", ";
                jSON_format = jSON_format + "\"address_line2\"" + ": " + "\"" + rset.getString("of.address_line2") + "\", ";
                jSON_format = jSON_format + "\"email_id1\"" + ": " + "\"" + rset.getString("of.email_id1") + "\", ";
                jSON_format = jSON_format + "\"email_id2\"" + ": " + "\"" + rset.getString("of.email_id2") + "\", ";
                jSON_format = jSON_format + "\"mobile_no1\"" + ": " + "\"" + rset.getString("of.mobile_no1") + "\", ";
                jSON_format = jSON_format + "\"mobile_no2\"" + ": " + "\"" + rset.getString("of.mobile_no2") + "\", ";
                jSON_format = jSON_format + "\"landline_no1\"" + ": " + "\"" + rset.getString("of.landline_no1") + "\", ";
                jSON_format = jSON_format + "\"landline_no2\"" + ": " + "\"" + rset.getString("of.landline_no2") + "\", ";

                jSON_format = jSON_format + "\"salutation\"" + ": " + "\"" + rset.getString("salutation") + "\", ";
                jSON_format = jSON_format + "\"key_person_name\"" + ": " + "\"" + rset.getString("key_person_name") + "\", ";
                jSON_format = jSON_format + "\"designation\"" + ": " + "\"" + rset.getString("designation") + "\", ";
                jSON_format = jSON_format + "\"personCity\"" + ": " + "\"" + rset.getString("personCity") + "\", ";

                jSON_format = jSON_format + "\"p_address_line1\"" + ": " + "\"" + rset.getString("k.address_line1") + "\", ";
                jSON_format = jSON_format + "\"p_address_line2\"" + ": " + "\"" + rset.getString("k.address_line2") + "\", ";
                jSON_format = jSON_format + "\"p_email_id1\"" + ": " + "\"" + rset.getString("k.email_id1") + "\", ";
                jSON_format = jSON_format + "\"p_email_id2\"" + ": " + "\"" + rset.getString("k.email_id2") + "\", ";
                jSON_format = jSON_format + "\"p_mobile_no1\"" + ": " + "\"" + rset.getString("k.mobile_no1") + "\", ";
                jSON_format = jSON_format + "\"p_mobile_no2\"" + ": " + "\"" + rset.getString("k.mobile_no2") + "\", ";
                jSON_format = jSON_format + "\"p_landline_no1\"" + ": " + "\"" + rset.getString("k.landline_no1") + "\", ";
                jSON_format = jSON_format + "\"p_landline_no2\"" + ": " + "\"" + rset.getString("k.landline_no2") + "\", ";
                jSON_format = jSON_format + "\"p_emp_code\"" + ": " + "\"" + rset.getString("k.emp_code") + "\"";
                //jSON_format = jSON_format + "\"p_general_image_details_id\"" + ": " + "\"" + rset.getString("k.general_image_details_id") + "\" ";
                jSON_format = jSON_format + "}";
            } else {
                jSON_format = "{ ";
                jSON_format = jSON_format + "\"org_office_code\"" + ": " + "\"" + rset.getString("org_office_code") + "\", ";
                jSON_format = jSON_format + "\"org_office_name\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"office_type\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"officeCity\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"address_line1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"address_line2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"email_id1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"email_id2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"mobile_no1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"mobile_no2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"landline_no1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"landline_no2\"" + ": " + "\"" + "" + "\", ";

                jSON_format = jSON_format + "\"salutation\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"key_person_name\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"designation\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"personCity\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_address_line1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_address_line2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_email_id1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_email_id2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_mobile_no1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_mobile_no2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_landline_no1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_landline_no2\"" + ": " + "\"" + "" + "\" ";
                jSON_format = jSON_format + "\"p_emp_code\"" + ": " + "\"" + rset.getString("k.emp_code") + "\"";
              //  jSON_format = jSON_format + "\"p_general_image_details_id\"" + ": " + "\"" + rset.getString("k.general_image_details_id") + "\" ";
                jSON_format = jSON_format + "}";
            }
        } catch (Exception e) {
            System.out.println("JQueryOrgEmailAndMobileController doGet() Error: " + e);
        }
        return jSON_format;
    }

    public String getPersonDetail(int organisation_id, int office_id, int person_id) {
        String jSON_format = "";
        String query = " SELECT salutation, key_person_name , d.designation ,c.city_name AS personCity, k.address_line1, k.address_line2, "
                + "  k.email_id1, k.email_id2 ,k.mobile_no1, k.mobile_no2, k.landline_no1, k.landline_no2,k.emp_code  "
                + " FROM organisation_name AS org, org_office AS of1 , key_person AS k, city AS c, designation AS d "
                + " WHERE   k.org_office_id = of1.org_office_id AND of1.organisation_id = org.organisation_id "
                + " AND c.city_id = k.city_id AND k.designation_id = d.designation_id "
                + " AND org.organisation_id = ? AND of1.org_office_id = ? AND k.key_person_id = ? "
                + " GROUP BY org.organisation_id ORDER BY organisation_name, org_office_name, key_person_name LIMIT 1 ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, organisation_id);
            pstmt.setInt(2, office_id);
            pstmt.setInt(3, person_id);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                jSON_format = "{ ";

                jSON_format = jSON_format + "\"salutation\"" + ": " + "\"" + rset.getString("salutation") + "\", ";
                jSON_format = jSON_format + "\"key_person_name\"" + ": " + "\"" + rset.getString("key_person_name") + "\", ";
                jSON_format = jSON_format + "\"designation\"" + ": " + "\"" + rset.getString("designation") + "\", ";
                jSON_format = jSON_format + "\"personCity\"" + ": " + "\"" + rset.getString("personCity") + "\", ";

                jSON_format = jSON_format + "\"p_address_line1\"" + ": " + "\"" + rset.getString("k.address_line1") + "\", ";
                jSON_format = jSON_format + "\"p_address_line2\"" + ": " + "\"" + rset.getString("k.address_line2") + "\", ";
                jSON_format = jSON_format + "\"p_email_id1\"" + ": " + "\"" + rset.getString("k.email_id1") + "\", ";
                jSON_format = jSON_format + "\"p_email_id2\"" + ": " + "\"" + rset.getString("k.email_id2") + "\", ";
                jSON_format = jSON_format + "\"p_mobile_no1\"" + ": " + "\"" + rset.getString("k.mobile_no1") + "\", ";
                jSON_format = jSON_format + "\"p_mobile_no2\"" + ": " + "\"" + rset.getString("k.mobile_no2") + "\", ";
                jSON_format = jSON_format + "\"p_landline_no1\"" + ": " + "\"" + rset.getString("k.landline_no1") + "\", ";
                jSON_format = jSON_format + "\"p_landline_no2\"" + ": " + "\"" + rset.getString("k.landline_no2") + "\", ";
                jSON_format = jSON_format + "\"p_emp_code\"" + ": " + "\"" + rset.getString("k.emp_code") + "\" ";
                //jSON_format = jSON_format + "\"p_general_image_details_id\"" + ": " + "\"" + rset.getString("k.general_image_details_id") + "\" ";
                jSON_format = jSON_format + "}";
            } else {
                jSON_format = "{ ";
                jSON_format = jSON_format + "\"salutation\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"key_person_name\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"designation\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"personCity\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_address_line1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_address_line2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_email_id1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_email_id2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_mobile_no1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_mobile_no2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_landline_no1\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_landline_no2\"" + ": " + "\"" + "" + "\", ";
                jSON_format = jSON_format + "\"p_emp_code\"" + ": " + "\"" + "" + "\" ";
               // jSON_format = jSON_format + "\"p_general_image_details_id\"" + ": " + "\"" + "" + "\" ";
                jSON_format = jSON_format + "}";
            }
        } catch (Exception e) {
            System.out.println("JQueryOrgEmailAndMobileController doGet() Error: " + e);
        }
        return jSON_format;
    }

    // $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public int getofficeType_id(String office_type) {
         //office_type = krutiToUnicode.convert_to_unicode(office_type);
        String query = "SELECT office_type_id FROM org_office_type WHERE office_type = ? ";
        int office_type_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, office_type);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            office_type_id = rset.getInt("office_type_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return office_type_id;
    }

    public List<String> getOfficeCode(String q, String office_type) {
       // office_type = krutiToUnicode.convert_to_unicode(office_type);
        List<String> list = new ArrayList<String>();
        int count = 0;
        try {
            String query = "Select org_office_code from org_office where office_type_id = (select org_office_type.office_type_id from org_office_type where org_office_type.office_type='" + office_type + "')";
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                list.add(rset.getString("org_office_code"));
                count++;
            }
        } catch (Exception e) {
            System.out.println("Error in getOfficeCode in OrgDetailEntryModel : " + e);
        }
        if (count == 0) {
            list.add("No such Office code exist");
        }
        return list;
    }

    public List<String> getOrgOfficeType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.office_type FROM org_office_type AS org ORDER BY office_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_type = rset.getString("office_type");
                if (office_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public int getCity_id(String city_name) {
     //  city_name = krutiToUnicode.convert_to_unicode(city_name);
        String query = "SELECT city_id FROM city WHERE city_name = ? ";
        int city_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, city_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            city_id = rset.getInt("city_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return city_id;
    }

    public boolean isOrganisationExist(String organisation) {
       //  organisation = krutiToUnicode.convert_to_unicode(organisation);
        String query = "select count(*) from organisation_name where organisation_name= ? ";
        int count = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {    // move cursor from BOR to valid record.
                count = rset.getInt("count(*)");
            }
            if (count > 0) {
                message = "Organisation" + organisation + " is Already Exist ";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return count == 0 ? true : false;
    }

    public List<String> getOrganisationList(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select organisation_name from organisation_name ORDER BY organisation_name ";
        try {
            int count = 0;
            q = q.trim();
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String orgName = rset.getString("organisation_name");
                if (orgName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(orgName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: in Booking_ViewModel getOrganisationList" + e);
        }
        return list;
    }

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT city_name FROM city AS c  "
                + "  ORDER BY city_name";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String AdvertiseName = rset.getString("city_name");
                if (AdvertiseName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(AdvertiseName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such City Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getDesignation(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select  designation from designation  ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String designation = rset.getString("designation");
                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(designation);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation  exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int getOrgOffice_id(String org_office_name, String organisation_name) {
        //  org_office_name = krutiToUnicode.convert_to_unicode(org_office_name);
        //  organisation_name = krutiToUnicode.convert_to_unicode(organisation_name);
        String query = "SELECT of.org_office_id FROM org_office AS of,organisation_name AS o "
                + "WHERE o.organisation_id=of.organisation_id AND of.org_office_name = ? AND o.organisation_name = ? ";
        int org_office_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_office_name);
            pstmt.setString(2, organisation_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            org_office_id = rset.getInt("org_office_id");
        } catch (Exception e) {
            System.out.println("Error:keypersonModel-- getOrganization_id--" + e);
        }
        return org_office_id;
    }

    public int getOrganisation_id(String organisation_name) {
      //  organisation_name = krutiToUnicode.convert_to_unicode(organisation_name);
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ? ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_id;
    }

    public int getDegination_id(String designation) {
      //  designation = krutiToUnicode.convert_to_unicode(designation);
        String query = "SELECT designation_id FROM designation WHERE designation ='" + designation + "'";
        int designation_id = 0;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                designation_id = rset.getInt("designation_id");
            }
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return designation_id;
    }

    public List<String> getOrgTypeNameList(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org_type_name FROM organisation_type ORDER BY org_type_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = rset.getString("org_type_name");
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getOrganisation_subType_Name(String q, String org_type_name) {
        //   org_type_name = krutiToUnicode.convert_to_unicode(org_type_name);
        List<String> list = new ArrayList<String>();
        String query = "SELECT ost.organisation_sub_type_name "
                + "FROM organisation_sub_type AS ost ,organisation_type AS ot "
                + "WHERE ost.organisation_type_id=ot.organisation_type_id AND org_type_name=? "
                + "ORDER BY organisation_sub_type_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_sub_type_name = rset.getString("organisation_sub_type_name");
                if (org_sub_type_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_sub_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public List<Integer> getOrgTypeSubTypeId(String org_type, String org_sub_type) {
       // org_type = krutiToUnicode.convert_to_unicode(org_type);
       // org_sub_type = krutiToUnicode.convert_to_unicode(org_sub_type);
        List<Integer> list = new ArrayList<Integer>();
        String query = "SELECT o.organisation_type_id, os.organisation_sub_type_id FROM organisation_type o , "
                + " organisation_sub_type  os "
                + " where os.organisation_type_id =o.organisation_type_id and  org_type_name= ? "
                + " and organisation_sub_type_name = ? limit 1 ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, org_type);
            pst.setString(2, org_sub_type);
            ResultSet rset = pst.executeQuery();
            if (rset.next()) {
                list.add(rset.getInt("organisation_type_id"));
                list.add(rset.getInt("organisation_sub_type_id"));
            }

        } catch (Exception e) {
            System.out.println("Error: OrgDetailEntryModel getOrganisation_type" + e);
        }
        return list;
    }

    public boolean createOrganisation(OrgDetailEntry createorgn) {
        boolean result = false;
        String orgDetail = " insert into organisation_name (organisation_name) value(?)";

        String orgn_mapquery = "insert into organisation_map "
                + "(organisation_id,organisation_type_id ,organisation_sub_type_id)"
                + "values( ?, ?, ?)";

        String office_query = "insert into org_office(org_office_code,org_office_name ,organisation_id , office_type_id , city_id, "
                + "address_line1 , address_line2 , mobile_no1, mobile_no2 ,email_id1 ,email_id2, landline_no1, landline_no2  )"
                + "values(?,? ,? , ?, ?, ? , ? , ? , ?, ?, ?, ?, ? )";

        String keyperson_query = "insert into key_person( salutation, key_person_name, designation_id, org_office_id, city_id, address_line1, address_line2,  "
                + " mobile_no1, mobile_no2, email_id1, email_id2 , landline_no1, landline_no2,emp_code,father_name,age)"
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?,?)";
        int rowsAffected = 0;
        boolean errorOccured = false;

        try {
            boolean autoCommit = connection.getAutoCommit();
            PreparedStatement pstmt = null;
            try {
                connection.setAutoCommit(false);
                pstmt = connection.prepareStatement(orgDetail);
                pstmt.setString(1,createorgn.getOrganisation());
                rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    pstmt.close();
                    createorgn.setOrganisation_id(getOrganisation_id(createorgn.getOrganisation()));
                    pstmt = connection.prepareStatement(orgn_mapquery);
                    pstmt.setInt(1, createorgn.getOrganisation_id());
                    pstmt.setInt(2, createorgn.getOrganisation_type_id());
                    pstmt.setInt(3, createorgn.getOrg_sub_type_id());
                    rowsAffected = pstmt.executeUpdate();
                    if (rowsAffected > 0) {
                        pstmt.close();
                        pstmt = connection.prepareStatement(office_query);
                        pstmt.setString(1, createorgn.getOffice_code());
                        pstmt.setString(2, createorgn.getOffice_name());
                        pstmt.setInt(3, createorgn.getOrganisation_id());
                        pstmt.setInt(4, createorgn.getOffice_type_id());
                        pstmt.setInt(5, createorgn.getOffice_city_id());
                        pstmt.setString(6,  createorgn.getOffice_address1());
                        pstmt.setString(7,  createorgn.getOffice_address2());
                        pstmt.setString(8, createorgn.getOffice_mobile1());
                        pstmt.setString(9, createorgn.getOffice_mobile2());
                        pstmt.setString(10, createorgn.getOffice_mail_id1());
                        pstmt.setString(11, createorgn.getOffice_mail_id2());
                        pstmt.setString(12, createorgn.getOffice_landLine1());
                        pstmt.setString(13, createorgn.getOffice_landLine2());
                        rowsAffected = pstmt.executeUpdate();
                        if (rowsAffected > 0) {
                            pstmt.close();
                            createorgn.setOffice_id(getOrgOffice_id(createorgn.getOffice_name(), createorgn.getOrganisation()));
                            pstmt = connection.prepareStatement(keyperson_query);
                            pstmt.setString(1, createorgn.getSalutation());
                            pstmt.setString(2, createorgn.getKeyperson());
                            pstmt.setInt(3, createorgn.getDesiganition_id());
                            pstmt.setInt(4, createorgn.getOffice_id());
                            pstmt.setInt(5, createorgn.getPerson_city_id());
                            pstmt.setString(6,  createorgn.getPerson_address1());
                            pstmt.setString(7,  createorgn.getPerson_address2());
                            pstmt.setString(8, createorgn.getPerson_mobile1());
                            pstmt.setString(9, createorgn.getPerson_mobile2());
                            pstmt.setString(10, createorgn.getPerson_mail_id1());
                            pstmt.setString(11, createorgn.getPerson_mail_id2());
                            pstmt.setString(12, createorgn.getPerson_landLine1());
                            pstmt.setString(13, createorgn.getPerson_landLine2());
                            //System.out.println(createorgn.getEmployeeId());
                            pstmt.setString(14, createorgn.getEmployeeId());
                            pstmt.setString(15, createorgn.getFather_name());
                            pstmt.setInt(16, createorgn.getAge());
                            rowsAffected = pstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                result = true;
                                connection.commit();
                                message = "Record Saved Successfully.";
                                msgBgColor = COLOR_OK;
                            } else {
                                throw new Exception("Error in create organisation");
                            }

                        } else {
                            throw new Exception("Error in create organisation");
                        }

                    } else {
                        throw new Exception("Error in create organisation");
                    }

                } else {
                    throw new Exception("Error in create organisation");
                }
            } catch (Exception e) {
                errorOccured = true;
                connection.rollback();
                message = "Record Can't Save Records due  to some Error";
                msgBgColor = COLOR_ERROR;
                System.out.println("OrgDetailEntryModel createOrganisation Error: " + e);
            } finally {
                pstmt.close();
                connection.setAutoCommit(autoCommit);
            }
        } catch (Exception e) {

            System.out.print("Error OrgDetailEntryModel createOrganisation" + e);
        }
        return result;
    }
    // ORGANISATION

    public int deleteRecordOrgMap(int organisation_id) {
        String query = "DELETE FROM organisation_map WHERE organisation_id = " + organisation_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
            rowsAffected = 1;
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel-- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, Its Currently in use.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteRecordOrgOffice(int organisation_id) {
        int rowsAffected = 0;
        if (deleteRecordOfficePerson(organisation_id) > 0) {
            String query = "DELETE FROM org_office WHERE organisation_id = " + organisation_id;
            try {
                rowsAffected = connection.prepareStatement(query).executeUpdate();
                rowsAffected = 1;
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            if (rowsAffected > 0) {
                message = "Record deleted successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "All Key Person Deleted Successfully but Cannot delete the Office, Office is currently in use.";
                msgBgColor = COLOR_ERROR;
            }
        }

        return rowsAffected;
    }

    public int deleteRecordOrg(int organisation_id) {
        int rowsAffected = 0;
        if (deleteRecordOrgMap(organisation_id) > 0 && deleteRecordOrgOffice(organisation_id) > 0) {
            String query = "DELETE FROM organisation_name WHERE organisation_id = " + organisation_id;
            try {
                rowsAffected = connection.prepareStatement(query).executeUpdate();
            } catch (Exception e) {
                System.out.println("OrganisationNameModel deleteRecord() Error: " + e);
            }
            if (rowsAffected > 0) {
                message = "Record deleted successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "Cannot delete the record, , Its Currently in use. ";
                msgBgColor = COLOR_ERROR;
            }
        }
        return rowsAffected;
    }

    //  ********************************* Organisation Mapping *************************************************
    public int insertRecordOrgMap(OrgDetailEntry OrganisationMap) {
        String query = "INSERT INTO organisation_map (organisation_id, organisation_type_id, organisation_sub_type_id ) "
                + " VALUES(?, ?, ? ) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, OrganisationMap.getOrganisation_id());
            pstmt.setInt(2, OrganisationMap.getOrganisation_type_id());
            pstmt.setInt(3, OrganisationMap.getOrg_sub_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel-insertRecord-" + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecordOrgMap(OrgDetailEntry OrganisationMap) {
        String query = "UPDATE organisation_map SET organisation_id=?, organisation_type_id=?, organisation_sub_type_id=?  WHERE org_map_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, OrganisationMap.getOrganisation_id());
            pstmt.setInt(2, OrganisationMap.getOrganisation_type_id());
            pstmt.setInt(3, OrganisationMap.getOrg_sub_type_id());
            pstmt.setInt(4, OrganisationMap.getOrg_map_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--updateRecord-" + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    // ###############################################  Office Detail #################################
    public int insertRecordOffice(OrgDetailEntry orgOffice) {
        String query = "INSERT INTO "
                + "org_office(organisation_id,org_office_code, org_office_name, office_type_id, address_line1, address_line2, city_id, email_id1, email_id2, mobile_no1, mobile_no2, landline_no1, landline_no2 ) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, orgOffice.getOrganisation_id());
            pstmt.setString(2, orgOffice.getOffice_code());
            pstmt.setString(3,orgOffice.getOffice_name());
            pstmt.setInt(4, orgOffice.getOffice_type_id());
            pstmt.setString(5, orgOffice.getOffice_address1());
            pstmt.setString(6,  orgOffice.getOffice_address2());
            pstmt.setInt(7, orgOffice.getOffice_city_id());
            pstmt.setString(8, orgOffice.getOffice_mail_id1());
            pstmt.setString(9, orgOffice.getOffice_mail_id2());
            pstmt.setString(10, orgOffice.getOffice_mobile1());
            pstmt.setString(11, orgOffice.getOffice_mobile2());
            pstmt.setString(12, orgOffice.getOffice_landLine1());
            pstmt.setString(13, orgOffice.getOffice_landLine2());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: organisation---insertRecord" + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecordOffice(OrgDetailEntry orgOffice) {
        String query = "UPDATE org_office SET organisation_id = ?, org_office_code = ?, org_office_name = ?, office_type_id = ?, address_line1 = ?, "
                + " address_line2 = ?, city_id =?, email_id1 = ?, email_id2 = ?, mobile_no1 = ?, "
                + " mobile_no2 = ?, landline_no1 = ?, landline_no2 = ?  "
                + "WHERE org_office_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, orgOffice.getOrganisation_id());
            pstmt.setString(2, orgOffice.getOffice_code());
            pstmt.setString(3, orgOffice.getOffice_name());
            pstmt.setInt(4, orgOffice.getOffice_type_id());
            pstmt.setString(5,  orgOffice.getOffice_address1());
            pstmt.setString(6,  orgOffice.getOffice_address2());
            pstmt.setInt(7, orgOffice.getOffice_city_id());
            pstmt.setString(8, orgOffice.getOffice_mail_id1());
            pstmt.setString(9, orgOffice.getOffice_mail_id2());
            pstmt.setString(10, orgOffice.getOffice_mobile1());
            pstmt.setString(11, orgOffice.getOffice_mobile2());
            pstmt.setString(12, orgOffice.getOffice_landLine1());
            pstmt.setString(13, orgOffice.getOffice_landLine2());
            pstmt.setInt(14, orgOffice.getOffice_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: updateRecord---updateRecord" + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteRecordOffice(int org_office_id) {
        int rowsAffected = 0;
        if (deleteRecordOfficePerson(org_office_id) > 0) {
            String query = "DELETE FROM org_office WHERE org_office_id = " + org_office_id;
            try {
                rowsAffected = connection.prepareStatement(query).executeUpdate();
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            if (rowsAffected > 0) {
                message = "Record deleted successfully.";
                msgBgColor = COLOR_OK;
            } else {
                message = "All Key Person Deleted Successfully but Cannot delete the Office, Office is currently in use.";
                msgBgColor = COLOR_ERROR;
            }
        }

        return rowsAffected;
    }

    public int deleteRecordOfficePerson(int org_office_id) {
        String query = "DELETE FROM key_person WHERE org_office_id = " + org_office_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
            rowsAffected = 1;
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "All key Person Remove  successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, Key Person is currently in use ..";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
// @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Key Person Alteration @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    public int insertRecordPerson(OrgDetailEntry orgKp) {
        String query = "INSERT INTO key_person( salutation, key_person_name, designation_id, org_office_id, city_id, address_line1, address_line2, "
                + " mobile_no1, mobile_no2, landline_no1, landline_no2, email_id1, email_id2,emp_code,father_name,age) VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?,?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, orgKp.getSalutation());
            pstmt.setString(2,  orgKp.getKeyperson());
            pstmt.setInt(3, orgKp.getDesiganition_id());
            pstmt.setInt(4, orgKp.getOffice_id());
            pstmt.setInt(5, orgKp.getPerson_city_id());
            pstmt.setString(6,  orgKp.getPerson_address1());
            pstmt.setString(7,  orgKp.getPerson_address2());
            pstmt.setString(8, orgKp.getPerson_mobile1());
            pstmt.setString(9, orgKp.getPerson_mobile2());
            pstmt.setString(10, orgKp.getPerson_landLine1());
            pstmt.setString(11, orgKp.getPerson_landLine2());
            pstmt.setString(12, orgKp.getPerson_mail_id1());
            pstmt.setString(13, orgKp.getPerson_mail_id2());
            pstmt.setString(14, orgKp.getEmployeeId());
            pstmt.setString(15, orgKp.getFather_name());
            pstmt.setInt(16, orgKp.getAge());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:OrgDetailEntryModel--insertRecord-- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecordPerson(OrgDetailEntry orgKp) {
        String query = "UPDATE key_person SET  salutation=?, key_person_name=?, designation_id=?, org_office_id=?, city_id=?, address_line1=?, address_line2=?, "
                + " mobile_no1=?, mobile_no2=?, landline_no1=?, landline_no2=?, email_id1=?, email_id2=?, father_name=?, age=? "
                + "WHERE key_person_id=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setString(1, orgKp.getSalutation());
            pstmt.setString(2,  orgKp.getKeyperson());
            pstmt.setInt(3, orgKp.getDesiganition_id());
            pstmt.setInt(4, orgKp.getOffice_id());
            pstmt.setInt(5, orgKp.getPerson_city_id());
            pstmt.setString(6,  orgKp.getPerson_address1());
            pstmt.setString(7,  orgKp.getPerson_address2());
            pstmt.setString(8, orgKp.getPerson_mobile1());
            pstmt.setString(9, orgKp.getPerson_mobile2());
            pstmt.setString(10, orgKp.getPerson_landLine1());
            pstmt.setString(11, orgKp.getPerson_landLine2());
            pstmt.setString(12, orgKp.getPerson_mail_id1());
            pstmt.setString(13, orgKp.getPerson_mail_id2());
            pstmt.setString(14, orgKp.getFather_name());
            pstmt.setInt(15, orgKp.getAge());
            pstmt.setInt(16, orgKp.getKeyPersonId());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:OrgDetailEntryModel-updateRecord-- " + e);
        }
        if (rowsAffected > 0) {
                message = "Record updated successfully.";
                msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteRecordPerson(int key_person_id) {
        String query = "DELETE FROM key_person WHERE key_person_id=" + key_person_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:OrgDetailEntryModel-deleteRecord-- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("OrgDetailEntryModel closeConnection() Error: " + e);
        }
    }
}
