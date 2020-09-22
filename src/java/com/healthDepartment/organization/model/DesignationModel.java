/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.Designation;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
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
 * @author JPSS
 */
public class DesignationModel {

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
          System.out.println("DesignationModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchDesignation,String searchDesignationCode) {
        int noOfRows = 0;
        searchDesignation = krutiToUnicode.convert_to_unicode(searchDesignation);
        try {
            String query = "select count(*) from designation "
                    + "WHERE if('" + searchDesignation + "' = '', designation like '%%',  designation = '" + searchDesignation + "')"
                    + " AND IF('" + searchDesignationCode + "'='' ,designation_code LIKE '%%',designation_code LIKE '"+searchDesignationCode+".%' OR designation_code like ?)";

           PreparedStatement pstmt = connection.prepareStatement(query);
           pstmt.setString(1,searchDesignationCode);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
        }
        return noOfRows;
    }
   public List<Designation> showAllData(String searchDesignation,String  searchDesignationCode)
           {
             List<Designation> list = new ArrayList<Designation>();
             String query = "SELECT * FROM designation "
                + "WHERE if('" + searchDesignation + "' = '', designation like '%%', designation = '" + searchDesignation + "')"
                + " AND IF('" + searchDesignationCode + "'='' ,designation_code LIKE '%%',designation_code LIKE '"+searchDesignationCode+".%' OR designation_code like ?)"

                + "ORDER BY designation ";
              try {
           PreparedStatement pstmt = connection.prepareStatement(query);
           pstmt.setString(1,searchDesignationCode);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Designation media = new Designation();
                media.setDesignation_id(rset.getInt("designation_id"));
                media.setDesignation(rset.getString("designation"));
                media.setDescription(rset.getString("description"));
                media.setDesignation_code(rset.getString("designation_code"));
                list.add(media);
            }
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
        }
        return list;

       }
    public List<Designation> showData(int lowerLimit, int noOfRowsToDisplay, String searchDesignation,String  searchDesignationCode) {
        List<Designation> list = new ArrayList<Designation>();
        searchDesignation = krutiToUnicode.convert_to_unicode(searchDesignation);
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT * FROM designation "
                + "WHERE if('" + searchDesignation + "' = '', designation like '%%', designation = '" + searchDesignation + "')"
                //+ "AND if('" + searchDesignationCode + "' = '', designation_code like '%%', designation_code = '" + searchDesignationCode + "')"
                + " AND IF('" + searchDesignationCode + "'='' ,designation_code LIKE '%%',designation_code LIKE '"+searchDesignationCode+".%' OR designation_code like ?)"

                + "ORDER BY designation_code "
                + "LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
           PreparedStatement pstmt = connection.prepareStatement(query);
           pstmt.setString(1,searchDesignationCode);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Designation media = new Designation();
                media.setDesignation_id(rset.getInt("designation_id"));
                media.setDesignation(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("designation")));
                media.setDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("description")));
                media.setDesignation_code(rset.getString("designation_code"));
                list.add(media);
            }
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
        }
        return list;
    }

    public int insertRecord(Designation designation) {
        String query = "INSERT INTO designation(designation, description, designation_code ) VALUES( ?, ?, ?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(designation.getDesignation()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(designation.getDescription()));
            pstmt.setString(3, designation.getDesignation_code());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
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

    public int updateRecord(Designation designation) {
        String query = "UPDATE designation SET designation=?, description=? , designation_code=? WHERE designation_id=?";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(designation.getDesignation()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(designation.getDescription()));
            pstmt.setString(3,designation.getDesignation_code());
            pstmt.setInt(4, designation.getDesignation_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
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

    public int deleteRecord(int designation_id) {
        String query = "DELETE FROM designation WHERE designation_id = " + designation_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("designationModel Error: " + e);
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

    public List<String> getDesignationList(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = " SELECT designation FROM designation ORDER BY designation ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String designation = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("designation"));
                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(designation);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:designationModel --getDesignationlist-- " + e);
        }
        return list;
    }
    public List<String> getDesignationCode(String q) {
        List<String> list = new ArrayList<String>();
        q = q.trim();
        int count = 0;
        String query = " SELECT designation_code FROM designation ORDER BY designation_code ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                String designation_code = rset.getString("designation_code");
                if (designation_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(designation_code);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Designation exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:designationModel --getDesignationlist-- " + e);
        }
        return list;
    }
    public byte[] generateDesignationReport(String jrxmlFilePath,List listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in CityModel generateDesignationReport() JRException: " + e);
        }
        return reportInbytes;
    }
     public ByteArrayOutputStream generateDesignationXlsRecordList(String jrxmlFilePath,List list) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
              //  HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("DesignationStatusModel generateDesignationXlsRecordList() JRException: " + e);
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
            System.out.println("designationModel closeConnection() Error: " + e);
        }
    }
}
