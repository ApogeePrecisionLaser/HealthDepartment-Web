/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicleTrip.tableClasses.TripVehicleBean;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
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
 * @author Shobha
 */
public class TripVehicleModel {
private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//        public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
 public static int  getNoOfRows(String search_vehicle_code,String search_trip_name)
 {
      //vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
      //key_person_name = krutiToUnicode.convert_to_unicode(key_person_name);
        int noOfRows = 0;
        try {
        String query="select count(*) "
                          +" from trip_vehicle_map tvm,trip t,vehicle_key_person vkp,vehicle v "
                         +" where tvm.trip_id=t.trip_id "
                          +" and tvm.vehicle_key_person_id=vkp.vehicle_key_person_id "
                         +" and vkp.vehicle_id=v.vehicle_id "
                          +" and tvm.active='Y' "
                       + " AND IF('" + search_vehicle_code + "' = '', vehicle_code LIKE '%%',vehicle_code='" + search_vehicle_code + "') "
                        + " And IF('" + search_trip_name + "' = '',trip_name LIKE '%%', trip_name='" + search_trip_name + "') ";

           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println( e);
        }
         System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

 public static List<TripVehicleBean> showData(int lowerLimit,int noOfRowsToDisplay,String search_vehicle_code,String search_trip_name)
  {
        //vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
    // search_vehicle_code = krutiToUnicode.convert_to_unicode(search_vehicle_code);
      //search_trip_name = krutiToUnicode.convert_to_unicode(search_trip_name);
       List list = new ArrayList();

         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="select trip_vehicle_map_id,trip_name,vehicle_code,tvm.date "
                          +" from trip_vehicle_map tvm,trip t,vehicle_key_person vkp,vehicle v "
                         +" where tvm.trip_id=t.trip_id "
                          +" and tvm.vehicle_key_person_id=vkp.vehicle_key_person_id "
                         +" and vkp.vehicle_id=v.vehicle_id "
                          +" and tvm.active='Y' "
                       
                        + " AND IF('" + search_vehicle_code + "' = '', vehicle_code LIKE '%%',vehicle_code='" + search_vehicle_code + "') "
                        + " And IF('" + search_trip_name + "' = '',trip_name LIKE '%%', trip_name='" + search_trip_name + "') "
                      + addQuery;

    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             TripVehicleBean vt=new TripVehicleBean();
             vt.setTrip_vehicle_map_id(rs.getInt("trip_vehicle_map_id"));
             vt.setTrip_name(rs.getString("trip_name"));
             vt.setVehicle_code(rs.getInt("vehicle_code"));
             vt.setDate(rs.getString("date"));
              list.add(vt);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

public  boolean insertRecord(TripVehicleBean bean)
 {
boolean status=false;
String query="";
int rowsAffected=0;
      int trip_vehicle_map_id=bean.getTrip_vehicle_map_id();
      int vehicle_key_person_id=getVehicleId(bean.getVehicle_code());
      int trip_id=getTripId(bean.getTrip_name());
      if(trip_vehicle_map_id==0)
         query="insert into trip_vehicle_map(vehicle_key_person_id,trip_id,date) values(?,?,?)";
      if(trip_vehicle_map_id>0)
         query=" update trip_vehicle_map set vehicle_key_person_id=?,trip_id=? where trip_vehicle_map_id=?";


        try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
        ps.setInt(1,vehicle_key_person_id);
        ps.setInt(2,trip_id);
        String date=getSplit(bean.getDate());
        ps.setString(3, date);
         if(trip_vehicle_map_id>0)
           ps.setInt(3,trip_vehicle_map_id);
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
public String getSplit(String str){
        String[] words=str.split("-");
        String mystr="";
        for(int i=(words.length)-1;i>=0;i--){
            mystr=mystr+words[i];
            if(i>0)
                mystr=mystr+"-";
        }
        return mystr;
    }

public boolean reviseRecords(TripVehicleBean bean){

    boolean status=false;
    String query="";
    int rowsAffected=0;
      int trip_vehicle_map_id=bean.getTrip_vehicle_map_id();
      int vehicle_key_person_id=getVehicleId(bean.getVehicle_code());
      int trip_id=getTripId(bean.getTrip_name());

      String query1 = "SELECT max(rev_no) rev_no FROM trip_vehicle_map WHERE trip_vehicle_map_id = "+trip_vehicle_map_id+" && active='Y' ORDER BY rev_no DESC";
      String query2 = " UPDATE trip_vehicle_map SET active=? WHERE trip_vehicle_map_id = ? && rev_no = ? ";
      String query3 = "INSERT INTO trip_vehicle_map (trip_vehicle_map_id,vehicle_key_person_id,trip_id, rev_no, active) VALUES (?,?,?,?,?) ";
      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,trip_vehicle_map_id);
           pst.setInt(3, rs.getInt("rev_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("rev_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             psmt.setInt(1,trip_vehicle_map_id);
             psmt.setInt(2,vehicle_key_person_id);
             psmt.setInt(3,trip_id);

             psmt.setInt(4, rev);
             psmt.setString(5, "Y");
             int a = psmt.executeUpdate();
              if(a > 0)
              status=true;
             }
           }
          } catch (Exception e)
             {
              System.out.println("CityDAOClass reviseRecord() Error: " + e);
             }
      if (updateRowsAffected > 0) {
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


public  boolean deleteRecord(int vehicle_key_person_id){
    boolean status=false;
   int rowsAffected=0;
   String query = "update trip_vehicle_map SET active='N' where trip_vehicle_map_id=?";
try{
    PreparedStatement pst = connection.prepareStatement(query);
    pst.setInt(1,vehicle_key_person_id);
    rowsAffected= pst.executeUpdate();
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
public static List<String> getTrip_name(String q)    {
     List<String> list=new ArrayList<String>();
   String query ="select trip_name from trip group by trip_name";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String trip_name = rset.getString("trip_name");
                       //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(trip_name);
                     count++;
                       // }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }


public static List<String> getSearchTripName(String q)    {
     List<String> list=new ArrayList<String>();
   String query ="select trip_name "
                  +" from trip_vehicle_map tvm,trip t "
                  +" where tvm.trip_id=t.trip_id "
                   +" and tvm.active='Y' ";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String trip_name = (rset.getString("trip_name"));
                       if(trip_name.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(trip_name);
                     count++;
                        }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }
public static List<String> getSearchVehicleCode(String q)    {
     List<String> list=new ArrayList<String>();
   String query =" select vehicle_code "
                   +" from trip_vehicle_map tvm,vehicle_key_person vkp, vehicle v "
                   +" where tvm.vehicle_key_person_id=vkp.vehicle_key_person_id "
                   +" and vkp.vehicle_id=v.vehicle_id "
                   +" and tvm.active='Y' ";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String vehicle_code = rset.getString("vehicle_code");
                       if(vehicle_code.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(vehicle_code);
                     count++;
                        }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }






    public static List<String> getVehicle_code(String q)    {
     List<String> list=new ArrayList<String>();
   String query ="select vehicle_code "
                  +" from vehicle v,vehicle_key_person vkp "
                  +" where v.vehicle_id=vkp.vehicle_id ";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     int vehicle_code =rset.getInt("vehicle_code");
                       //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(Integer.toString(vehicle_code));
                     count++;
                        //}
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }


 public int getVehicleId(int vehicle_code){
     int vehicle_key_person_id=0;
     String query="select vehicle_key_person_id "
                   +" from vehicle v,vehicle_key_person vkp "
                   +" where vkp.vehicle_id=v.vehicle_id "
                   +" and v.vehicle_code= "+vehicle_code;
     try{
     PreparedStatement pst = connection.prepareStatement(query);
     ResultSet rs=pst.executeQuery();
     while(rs.next()){
         vehicle_key_person_id=rs.getInt("vehicle_key_person_id");
     }
     }catch(Exception e){
         System.out.println(e);
     }
     return vehicle_key_person_id;
 }
 public int getTripId(String key_person){
     //key_person = krutiToUnicode.convert_to_unicode(key_person);

     int trip_id=0;
     String query="select trip_id from trip where trip_name= '"+key_person+"'";
     try{
     PreparedStatement pst = connection.prepareStatement(query);
     ResultSet rs=pst.executeQuery();
     while(rs.next()){
         trip_id=rs.getInt("trip_id");
     }
     }catch(Exception e){
         System.out.println(e);
     }
     return trip_id;
 }

// public  byte[] generateRecordList(String jrxmlFilePath,List li)
//     {
//                byte[] reportInbytes = null;
//                try {
//                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(li);
//                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
//                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
//                } catch (Exception e) {
//
//                    System.out.println("com.mis.model ErrorLogMsgModel Error generateRecordList() "+ e);
//                }
//                return reportInbytes;
//     }
 public static byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
//            JasperReport compiledReport1 = JasperCompileManager.compileReport(jrxmlFilePath);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport1, null, jrBean);
//            String path = createAppropriateDirectories1("C:/ssadvt_repository/prepaid/RideReceipt");
//            path = path + "/receipt.pdf";
//            JRPdfExporter exporter = new JRPdfExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
//            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("GeneralModel generateRecordList() JRException: " + e);
        }
        return reportInbytes;
    }

    public static ByteArrayOutputStream generateExcelList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
            //print(jasperPrint);
        } catch (Exception e) {
            System.out.println("GeneralModel generateExcelList() JRException: " + e);
        }
        return reportInbytes;
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
