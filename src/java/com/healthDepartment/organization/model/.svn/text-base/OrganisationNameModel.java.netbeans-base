package com.healthDepartment.organization.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.healthDepartment.organization.tableClasses.OrganisationName;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
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

/**
 *
 * @author Tarun
 */
public class OrganisationNameModel {

    private Connection connection;
    
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

  public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
             System.out.println("QtOohDefaultsModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("SELECT COUNT(*) FROM organisation_name ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public int getNoOfRows(String organisation_name) {
        int noOfRows = 0;
        organisation_name = krutiToUnicode.convert_to_unicode(organisation_name);
        try {
            String query = " select  COUNT(*) from organisation_name  where  organisation_name like ?  ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name + '%');
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("OrganisationNameModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<OrganisationName> showData(int lowerLimit, int noOfRowsToDisplay, String org_name) {
        List<OrganisationName> list = new ArrayList<OrganisationName>();
        org_name = krutiToUnicode.convert_to_unicode(org_name);
        String query;
        PreparedStatement pstmt = null;
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        try {
            if (org_name == null || org_name.isEmpty()) {
                query = "SELECT * FROM organisation_name  ORDER BY organisation_name "
                        + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
                pstmt = connection.prepareStatement(query);
            } else {
                query = "SELECT * FROM organisation_name where  organisation_name like ? ORDER BY organisation_name "
                        + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
                pstmt = connection.prepareStatement(query);
                pstmt.setString(1, org_name + '%');
            }
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OrganisationName orgName = new OrganisationName();
                orgName.setOrganisation_id(rset.getInt("organisation_id"));
                orgName.setOrganisation_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("organisation_name")));
                orgName.setDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("description")));
                list.add(orgName);
            }
        } catch (Exception e) {
            System.out.println("OrganisationNameModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(OrganisationName organisation_name) {
        String query = "INSERT INTO organisation_name(organisation_name,description) VALUES(?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(organisation_name.getOrganisation_name()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(organisation_name.getDescription()));
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("OrganisationNameModel insertRecord() Error: " + e);
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

    public int updateRecord(OrganisationName organisation_name) {
        String query = "UPDATE organisation_name SET organisation_name = ?, description=? WHERE organisation_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(organisation_name.getOrganisation_name()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(organisation_name.getDescription()));
            pstmt.setInt(3, organisation_name.getOrganisation_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("OrganisationNameModel updateRecord() Error: " + e);
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

    public int deleteRecord(int organisation_id) {
        String query = "DELETE FROM organisation_name WHERE organisation_id = " + organisation_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("OrganisationNameModel deleteRecord() Error: " + e);
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

    public List<String> getOrganisationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT organisation_name FROM organisation_name ORDER BY organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String orgTypeName = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("organisation_name"));
                if (orgTypeName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(orgTypeName);
                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println("Error:OrganisationNameModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public byte[] generateSiteList(String jrxmlFilePath) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, mymap, connection);
        } catch (Exception e) {
            System.out.println("Error: in OrganisationNameModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }
    public ByteArrayOutputStream generateOrginisationXlsRecordList(String jrxmlFilePath) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
              //  HashMap mymap = new HashMap();
                try {
                   // JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, connection);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("OrginisationStatusModel generateOrgnisitionXlsRecordList() JRException: " + e);
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
            System.out.println("OrganisationNameModel closeConnection() Error: " + e);
        }
    }
}
