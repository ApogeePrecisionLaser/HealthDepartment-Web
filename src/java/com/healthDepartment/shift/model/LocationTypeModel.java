/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.shift.model;

import com.healthDepartment.shift.tableClasses.LocationTypeBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.io.ByteArrayOutputStream;
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
 * @author Administrator
 */
public class LocationTypeModel {
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public int getNoOfRows(String searchlocationtype)
    {
         searchlocationtype = krutiToUnicode.convert_to_unicode(searchlocationtype);
        String query = " SELECT Count(*) "
                + " FROM location_type as l "
               + " where IF('"+searchlocationtype +"'='',l.location_type_name LIKE '%%',l.location_type_name=?) order by l.location_type_name " ;
        int noOfRows = 0;
        try {
            PreparedStatement stmt = (PreparedStatement) connection.prepareStatement(query);
            stmt.setString(1, searchlocationtype);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows fuse type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<LocationTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchlocationtype) 
    {
        List<LocationTypeBean> list = new ArrayList<LocationTypeBean>();
  searchlocationtype = krutiToUnicode.convert_to_unicode(searchlocationtype);
        String query = " select location_type_id,location_type_name,remark from location_type as l "
               + " where IF('"+searchlocationtype +"'='',l.location_type_name LIKE '%%',l.location_type_name=?) order by l.location_type_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, searchlocationtype);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                LocationTypeBean lt = new LocationTypeBean();
                lt.setLocation_type_id(rset.getInt("location_type_id"));
                lt.setLocation_type_name(rset.getString("location_type_name"));
                 lt.setRemark(rset.getString("remark"));
                list.add(lt);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }


public  boolean insertRecord(LocationTypeBean bean)
 {
boolean status=false;
int rowsAffected=0;
        try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement("insert into location_type (location_type_name,remark) values(?,?)");
        ps.setString(1,krutiToUnicode.convert_to_unicode(bean.getLocation_type_name()));
        ps.setString(2,krutiToUnicode.convert_to_unicode(bean.getRemark()));

         rowsAffected = ps.executeUpdate();
        if(rowsAffected > 0)
        status=true;
        }
        catch(Exception e){
        System.out.println("ERROR: " + e);
        }
       if (rowsAffected > 0) {
             message = "Record Inserted successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Inserted");
        } else {
             message = "Record Not Inserted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Inserted");
        }
return status;
}
    public List<LocationTypeBean> showAll(int lowerLimit, int noOfRowsToDisplay,String searchlocationtype)
    {
        searchlocationtype = krutiToUnicode.convert_to_unicode(searchlocationtype);
        ArrayList<LocationTypeBean> list = new ArrayList<LocationTypeBean>();
        String query=  " SELECT location_type_name,remark "
         + "  FROM location_type as l where "
       + " IF('"+searchlocationtype +"'='',l.location_type_name LIKE '%%',l.location_type_name=?) ";

        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
               pstmt.setString(1, searchlocationtype);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                 LocationTypeBean Bean = new LocationTypeBean();
                Bean.setLocation_type_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(1)));
                Bean.setRemark(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(2)));
                list.add(Bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- ZoneModel : " + e);
        }

        return list;
    }
 public  boolean deleteRecord(String location_type_id){
    boolean status=false;
   int rowsAffected=0;
try{
     rowsAffected = connection.prepareStatement("Delete from location_type where location_type_id="+location_type_id+" ").executeUpdate();
     if(rowsAffected > 0)
     status=true;
     else status=false;
     }catch(Exception e){
     System.out.println("ERROR: " + e);
    }
    if (rowsAffected > 0) {
             message = "Record Deleted successfully......";
             msgBgColor = COLOR_OK;
             System.out.println("Deleted");
        } else {
             message = "Record Not Deleted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Deleted");}
return status;
}

 public static byte[] generateRecordList(String jrxmlFilePath,List list) {
                byte[] reportInbytes = null;
                HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
                } catch (Exception e) {
                    System.out.println(" generatReport() JRException: " + e);
                }
                return reportInbytes;
            }
  public static ByteArrayOutputStream generateXlsRecordList(String jrxmlFilePath,List list) {
                String reportInbytes = null;
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    //reportInbytes = JasperFillManager.fillReportToFile(jrxmlFilePath, mymap, jrBean);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, jrBean);

                    JRXlsExporter exporter = new JRXlsExporter();
                     exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                     exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);

                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("CityModel generatXlsReportList() JRException: " + e);
                }
                return byteArray;
            }

      public List<String> getlocationtype(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT location_type_name FROM location_type GROUP BY location_type_name ORDER BY location_type_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String location_type_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location_type_name"));
                if (location_type_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such record Type exists.");
            }
        } catch (Exception e) {
            System.out.println(" ERROR  - " + e);
        }
        return list;
    }

        public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }
   public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
