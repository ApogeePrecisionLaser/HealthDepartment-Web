/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.OrganisationMap;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class OrganisationMapModel {

    private Connection connection;
  
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
             System.out.println("OrganisationMapModel setConnection() Error: " + e);
        }
    }
    public int getNoOfRows(int organisation_id, int org_type_id, int org_sub_type_id) {
        int noOfRows = 0;
        try {
            String query = "SELECT count(*) FROM organisation_map AS m, organisation_name AS org, organisation_type AS ot, organisation_sub_type AS ost "
                    + "WHERE m.organisation_id=org.organisation_id AND m.organisation_type_id=ot.organisation_type_id "
                    + "AND m.organisation_sub_type_id= ost.organisation_sub_type_id and "
                    + "if(" + organisation_id + "=0, org.organisation_id like '%%',org.organisation_id=" + organisation_id + ") and "
                    + "if(" + org_type_id + "=0,ot.organisation_type_id like'%%', ot.organisation_type_id=" + org_type_id + ") and "
                    + "if(" + org_sub_type_id + "=0,ost.organisation_sub_type_id like'%%', ost.organisation_sub_type_id =" + org_sub_type_id + ") ";

            PreparedStatement psmt = connection.prepareStatement(query);
            ResultSet rset = psmt.executeQuery();
            if (rset.next()) {
                noOfRows = Integer.parseInt(rset.getString(1));
            }
            System.out.println("\n OrganisationMapModel-No Of Rows-- " + noOfRows);
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel " + e);
        }
        return noOfRows;
    }

    public List<OrganisationMap> showData(int lowerLimit, int noOfRowsToDisplay, int organisation_id, int org_type_id, int org_sub_type_id) {
        List<OrganisationMap> list = new ArrayList<OrganisationMap>();
        String query = "SELECT m.org_map_id ,org.organisation_name, ot.org_type_name, ost.organisation_sub_type_name ,m.description "
                + "FROM organisation_map AS m, organisation_name AS org, organisation_type AS ot, organisation_sub_type AS ost "
                + "WHERE m.organisation_id=org.organisation_id AND m.organisation_type_id=ot.organisation_type_id "
                + "AND m.organisation_sub_type_id= ost.organisation_sub_type_id and "
                + "if(" + organisation_id + "=0, org.organisation_id like '%%',org.organisation_id=" + organisation_id + ") and "
                + "if(" + org_type_id + "=0,ot.organisation_type_id like'%%', ot.organisation_type_id=" + org_type_id + ") and "
                + "if(" + org_sub_type_id + "=0,ost.organisation_sub_type_id like'%%', ost.organisation_sub_type_id =" + org_sub_type_id + ") "
                + "ORDER BY org.organisation_name LIMIT "
                + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                OrganisationMap organisationMap = new OrganisationMap();
                organisationMap.setOrg_map_id(rset.getInt("org_map_id"));
                organisationMap.setOrganisation_name(rset.getString("organisation_name"));
                organisationMap.setOrg_type_name(rset.getString("org_type_name"));
                organisationMap.setOrganisation_sub_type_name(rset.getString("organisation_sub_type_name"));
                organisationMap.setDescription(rset.getString("description"));
                list.add(organisationMap);
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel-- " + e);
        }

        return list;
    }

    public int insertRecord(OrganisationMap OrganisationMap) {
        String query = "INSERT INTO organisation_map (organisation_id, organisation_type_id, organisation_sub_type_id, description) "
                + " VALUES(?, ?, ?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, OrganisationMap.getOrganisation_id());
            pstmt.setInt(2, OrganisationMap.getOrganisation_type_id());
            pstmt.setInt(3, OrganisationMap.getOrganisation_sub_type_id());
            pstmt.setString(4, OrganisationMap.getDescription());
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

    public int updateRecord(OrganisationMap OrganisationMap) {
        String query = "UPDATE organisation_map SET organisation_id=?, organisation_type_id=?, organisation_sub_type_id=?, description=? WHERE org_map_id=?";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, OrganisationMap.getOrganisation_id());
            pstmt.setInt(2, OrganisationMap.getOrganisation_type_id());
            pstmt.setInt(3, OrganisationMap.getOrganisation_sub_type_id());
            pstmt.setString(4, OrganisationMap.getDescription());
            pstmt.setInt(5, OrganisationMap.getOrg_map_id());
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

    public int deleteRecord(int site_id) {
        String query = "DELETE FROM organisation_map WHERE org_map_id=" + site_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel-- " + e);
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

    public List<String> getOrganisation_Name(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org.organisation_name FROM organisation_name AS org ORDER BY organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = rset.getString("organisation_name");
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
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

    public int getOrganisation_id(String organisation_name) {
        String query = "SELECT organisation_id FROM organisation_name WHERE organisation_name = ?";
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

    public int getOrganisationTypeID(String org_type_name) {
        String query = "SELECT organisation_type_id FROM organisation_type WHERE org_type_name = ?";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel- " + e);
        }
        return id;
    }

    public int getOrganisationSubTypeID(int org_sub_type_id, String org_type_name) {
        String query = " SELECT organisation_sub_type_id FROM organisation_sub_type "
                + " WHERE organisation_sub_type_name = ?  and organisation_type_id= ? ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_type_name);
            pstmt.setInt(2, org_sub_type_id);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("organisation_sub_type_id");
        } catch (Exception e) {
            System.out.println("Error:OrganisationMapModel- " + e);
        }
        return id;
    }

    public List<String> getOrganisation_subType_Name(String q, String org_type_name) {
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

    public int getOrganisation_subType_id(String organisation_Type, String organisation_subType_name) {
        String query = "SELECT ost.organisation_sub_type_id "
                + "FROM organisation_sub_type AS ost ,organisation_type AS ot "
                + "WHERE ost.organisation_type_id=ot.organisation_type_id AND org_type_name= ? "
                + "AND ost.organisation_sub_type_name = ?  ";
        int organisation_subType_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_Type);
            pstmt.setString(2, organisation_subType_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                organisation_subType_id = rset.getInt("organisation_sub_type_id");
            }
        } catch (Exception e) {
            System.out.println("Error: OrganisationMapModel--" + e);
        }
        return organisation_subType_id;
    }

    public byte[] generateOrganisationMapList(String jrxmlFilePath, int organisation_id, int org_type_id, int org_sub_type_id) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        mymap.put("organisation_id", organisation_id);
        mymap.put("org_type_id", org_type_id);
        mymap.put("org_sub_type", org_sub_type_id);
        Connection con = connection;
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, con);
        } catch (Exception e) {
            System.out.println("Error: in OrganisationMapModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }
 public  ByteArrayOutputStream generateOrginisationMapXlsRecordList(String jrxmlFilePath, int organisation_id, int org_type_id, int org_sub_type_id) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
               HashMap mymap = new HashMap();
               mymap.put("organisation_id", organisation_id);
               mymap.put("org_type_id", org_type_id);
               mymap.put("org_sub_type", org_sub_type_id);
               Connection con = connection;
                try {
                    //JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, con);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("OrginisationMapStatusModel generateOrginisationMapXlsRecordList() JRException: " + e);
                }
                return bytArray;
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
            System.out.println("OrganisationMapModel closeConnection() Error: " + e);
        }
    }
}
