/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.TypeOfOccupation;
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
public class TypeOfOccupationModel {
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

//    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

 public static int  getNoOfRows(String searchtypeofoccupation)
 { 
     //searchtypeofoccupation = krutiToUnicode.convert_to_unicode(searchtypeofoccupation);
        int noOfRows = 0;
        try {
        String query="SELECT count(*) from type_of_occupation where "
                    + "  IF('" + searchtypeofoccupation + "' = '', name LIKE '%%',name  =?) " ;
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, searchtypeofoccupation);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println( e);
        }
         System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }
 
  public static List<TypeOfOccupation> showData(int lowerLimit,int noOfRowsToDisplay,String searchtypeofoccupation)
  {
      // searchtypeofoccupation = krutiToUnicode.convert_to_unicode(searchtypeofoccupation);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="SELECT type_of_occupation_id,name,Description from type_of_occupation"
                      + " where IF('" + searchtypeofoccupation + "' = '', name LIKE '%%',name  =?) "
                  + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
          ps.setString(1, searchtypeofoccupation);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             TypeOfOccupation to=new TypeOfOccupation();
             to.setType_of_occupation_id(rs.getInt("type_of_occupation_id"));
             to.setName(rs.getString("name"));
             to.setDescription(rs.getString("Description"));
              list.add(to);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }
    public static List<TypeOfOccupation> showAll(int lowerLimit,int noOfRowsToDisplay,String searchtypeofoccupation)
  {
        //searchtypeofoccupation = krutiToUnicode.convert_to_unicode(searchtypeofoccupation);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="SELECT name,Description from type_of_occupation"
                      + " where IF('" + searchtypeofoccupation + "' = '', name LIKE '%%',name  =?) "
                  + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
          ps.setString(1, searchtypeofoccupation);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             TypeOfOccupation to=new TypeOfOccupation();
             to.setName(rs.getString("name"));
             to.setDescription(rs.getString("Description"));
              list.add(to);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }
public  boolean insertRecord(TypeOfOccupation bean)
 {
boolean status=false;
int rowsAffected=0;
        try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement("insert into type_of_occupation (name,Description) values(?,?)");
        ps.setString(1,bean.getName());
        ps.setString(2,bean.getDescription());

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

  public  boolean deleteRecord(String type_of_occupation_id){
    boolean status=false;
   int rowsAffected=0;
try{
 rowsAffected = connection.prepareStatement("Delete from type_of_occupation where type_of_occupation_id="+type_of_occupation_id+" ").executeUpdate();
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

     public List<String> getSearchOccupation(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select name from type_of_occupation group by name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String name = rset.getString("name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such record exists.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
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
