/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.OrgOfficeType;
import com.healthDepartment.shift.tableClasses.ShiftTimeBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
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
 * @author Tarun
 */
public class BeneficiaryShiftSearchModel {

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
             System.out.println("OrgOfficeTypeModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchOrgOfficeType) {
        int noOfRows = 0;
        
        try {
            searchOrgOfficeType = krutiToUnicode.convert_to_unicode(searchOrgOfficeType);
            String query = " SELECT COUNT(*) FROM org_office_type "
                    + " WHERE IF('" + searchOrgOfficeType + "'='' ,office_type LIKE '%%',office_type = ?) ";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, searchOrgOfficeType);
            ResultSet rset = pst.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }
     public List<OrgOfficeType> showAllData(String searchOrgOfficeType)
    {
          List<OrgOfficeType> list = new ArrayList<OrgOfficeType>();          
              String query = " SELECT office_type_id, office_type,  description FROM org_office_type "
                + " WHERE IF('" + searchOrgOfficeType + "'='' ,office_type LIKE '%%',office_type = ?)  ";
               try {
                   searchOrgOfficeType = krutiToUnicode.convert_to_unicode(searchOrgOfficeType);
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchOrgOfficeType);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                OrgOfficeType orgOfficeType = new OrgOfficeType();
                orgOfficeType.setOffice_type_id(rset.getInt("office_type_id"));
                orgOfficeType.setOffice_type(rset.getString("office_type"));
                orgOfficeType.setDescription(rset.getString("description"));
                list.add(orgOfficeType);
            }
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel showData() Error: " + e);
        }
        return list;
     }
    public List<ShiftTimeBean> showData(int lowerLimit, int noOfRowsToDisplay, String beneficiaryMobileNo) {
        List<ShiftTimeBean> list = new ArrayList<ShiftTimeBean>();
        
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
//        String query = " SELECT office_type_id, office_type,  description FROM org_office_type "
//                + " WHERE IF('" + searchOrgOfficeType + "'='' ,office_type LIKE '%%',office_type = ?)  "
//                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        String query = " SELECT sk.shift_key_person_map_id, kp.key_person_name AS person_name, sk.date,  kp1.key_person_name AS emp_name, "
                + " kp1.emp_code, st.starting_time, st.ending_time, cl.location, a.area_name, kp1.mobile_no1 "
                + " FROM key_person kp, beneficiary b, city_location cl, Area a, designation_location_type d, "
                + " shift_designation_location_map s, shift_key_person_map sk, key_person kp1, shift_type st "
                + " WHERE st.shift_type_id = s.shift_type_id AND kp1.key_person_id=sk.key_person_id AND sk.map_id1=s.map_id1 "
                + " AND sk.map_id2=s.map_id2 AND s.designation_location_type_id = d.designation_location_type_id "
                + " AND  d.area_id=a.area_id AND a.area_id = cl.area_id AND cl.city_location_id = b.city_location_id "
                + " AND kp.key_person_id=b.key_person_id AND sk.date = current_date() AND kp.mobile_no1='"+ beneficiaryMobileNo +"'";
        try {            
            PreparedStatement pstmt = connection.prepareStatement(query);            
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ShiftTimeBean bean = new ShiftTimeBean();
                bean.setEmp_code(rset.getInt("emp_code"));
                bean.setPerson_name(rset.getString("person_name"));
                bean.setName(rset.getString("emp_name"));
                bean.setMobile_no(rset.getString("mobile_no1"));
                bean.setArea(rset.getString("area_name"));
                bean.setLocation(rset.getString("location"));
                bean.setDate(rset.getString("date"));
                bean.setStart_time(rset.getString("starting_time"));
                bean.setEnd_time(rset.getString("ending_time"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel showData() Error: " + e);
        }
        return list;
    }

    public int insertRecord(OrgOfficeType org_office_type) {
        String query = "INSERT INTO org_office_type(office_type,  description) VALUES(?,?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(org_office_type.getOffice_type()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(org_office_type.getDescription()));
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel insertRecord() Error: " + e);
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

    public int updateRecord(OrgOfficeType org_office_type) {
        String query = "UPDATE org_office_type SET office_type = ?,description=? WHERE office_type_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(org_office_type.getOffice_type()));
            pstmt.setString(2, krutiToUnicode.convert_to_unicode(org_office_type.getDescription()));
            pstmt.setInt(3, org_office_type.getOffice_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel updateRecord() Error: " + e);
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

    public int deleteRecord(int office_type_id) {
        String query = "DELETE FROM org_office_type WHERE office_type_id = " + office_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("OrgOfficeTypeModel deleteRecord() Error: " + e);
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

    public List<String> getOrgOfficeType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT office_type_id, office_type FROM org_office_type o ORDER BY office_type ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String office_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("office_type"));
                if (office_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(office_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Office Type exists.");
            }
        } catch (Exception e) {
            System.out.println("getOrgOfficeType ERROR - " + e);
        }
        return list;
    }
    public byte[] orgOfficeReport(String jrxmlFilePath,List listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in OrgOfficeReport orgOfficeReport() JRException: " + e);
        }
        return reportInbytes;
    }
     public ByteArrayOutputStream orgOfficeXlsRecordList(String jrxmlFilePath,List list) {
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
                    System.out.println("CityStatusModel orgOfficeXlsRecordList() JRException: " + e);
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
            System.out.println("getOrgOfficeType closeConnection() Error: " + e);
        }
    }
}
