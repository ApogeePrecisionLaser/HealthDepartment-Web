/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.shift.model;

import com.healthDepartment.shift.tableClasses.ShiftLoginBean;
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
public class AttendenceModel {
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

   public static int  getNoOfRows(String date)
 {        int noOfRows = 0;
               if(date != null && !date.isEmpty()){
            String[] date_array  = date.split("-");
            date = date_array[2] + "-" + date_array[1] + "-" + date_array[0];
            }
        try {
            String query = "select count(a.attendence_id) from shift_key_person_map as skpm,key_person as kp,attendence a "
   + " where skpm.key_person_id=kp.key_person_id and skpm.shift_key_person_map_id=a.shift_key_person_map_id and skpm.date='"+date+"' and  a.attendence='N' ";

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

 public static List<ShiftLoginBean> showData(String date)
    {
          if(date != null && !date.isEmpty()){
            String[] date_array  = date.split("-");
            date = date_array[2] + "-" + date_array[1] + "-" + date_array[0];
            }
      List list = new ArrayList();
     String query = "select a.attendence_id,kp.emp_code,kp.key_person_name,kp.mobile_no1,a.attendence from shift_key_person_map as skpm,key_person as kp,attendence a "
                      + " where skpm.key_person_id=kp.key_person_id and skpm.shift_key_person_map_id=a.shift_key_person_map_id and skpm.date='"+date+"' and a.attendence='N' " ;
                     
      try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
             while(rs.next()){
                  ShiftLoginBean sb=new ShiftLoginBean();
                   sb.setAttendence_id(rs.getInt("attendence_id"));
                   sb.setEmp_code(rs.getString("emp_code"));
                   sb.setEmp_name(rs.getString("key_person_name"));
                   sb.setMobile_no(rs.getString("mobile_no1"));
                   sb.setAttendence(rs.getString("attendence"));
                  list.add(sb);
             }

        } catch (Exception e) {
            System.out.println( e);
        }
        return list;

    }
   public static int  getNoOfRowsAttendence(String empcode,String mobileno,String sdate,String searchemp,String attendence)
 {       
      // searchemp = krutiToUnicode.convert_to_unicode(searchemp);
       int noOfRows = 0;
            if(sdate != null && !sdate.isEmpty()){
                   String[] sdate_array  = sdate.split("-");
                   sdate = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
                     }
        try {
            String query = "select count(a.attendence_id) from shift_key_person_map as skpm,key_person as kp,attendence as a "
                         + " where skpm.key_person_id=kp.key_person_id and skpm.shift_key_person_map_id=a.shift_key_person_map_id  "
                         + "And IF('" + empcode + "' = '', kp.emp_code LIKE '%%', kp.emp_code ='" + empcode + "') " 
                         + "And IF('" + mobileno + "' = '', kp.mobile_no1 LIKE '%%', kp.mobile_no1 ='" + mobileno + "') " 
                         + "And IF('" + sdate + "' = '', skpm.date LIKE '%%', skpm.date ='" + sdate + "') "
                         + "And IF('" + attendence + "' = '', a.attendence LIKE '%%', a.attendence='" + attendence + "') "
                         + "And IF('" + searchemp + "' = '', kp.key_person_name LIKE '%%', kp.key_person_name ='" + searchemp + "') " ;

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

 public static List<ShiftLoginBean> showAttendenceData(int lowerLimit,int noOfRowsToDisplay,String empcode,String mobileno,String sdate,String searchemp,String attendence)
    {
     //searchemp = krutiToUnicode.convert_to_unicode(searchemp);
             if(sdate != null && !sdate.isEmpty()){
                   String[] sdate_array  = sdate.split("-");
                   sdate = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
                     }
      List list = new ArrayList();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
     String query = "select a.attendence_id,kp.emp_code,kp.key_person_name,kp.mobile_no1,a.attendence,skpm.date,a.start_time,a.end_time "
                      + " from shift_key_person_map as skpm,key_person as kp,attendence a "
                      + " where skpm.key_person_id=kp.key_person_id and skpm.shift_key_person_map_id=a.shift_key_person_map_id  "
                      + "And IF('" + empcode + "' = '', kp.emp_code LIKE '%%', kp.emp_code ='" + empcode + "') "
                      + "And IF('" + mobileno + "' = '', kp.mobile_no1 LIKE '%%', kp.mobile_no1 ='" + mobileno + "') "
                      + "And IF('" + sdate + "' = '', skpm.date LIKE '%%', skpm.date ='" + sdate + "') "
                      + "And IF('" + attendence + "' = '', a.attendence LIKE '%%', a.attendence='" + attendence + "') "
                      + "And IF('" + searchemp + "' = '', kp.key_person_name LIKE '%%', kp.key_person_name ='" + searchemp + "') "
                      + addQuery;

      try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
             while(rs.next()){
                  ShiftLoginBean sb=new ShiftLoginBean();
                   sb.setAttendence_id(rs.getInt("attendence_id"));
                   sb.setEmp_code(rs.getString("emp_code"));
                   sb.setEmp_name(rs.getString("key_person_name"));
                   sb.setMobile_no(rs.getString("mobile_no1"));
                   String date=rs.getString("date");
                   if(date != null && !date.isEmpty()){
                   String[] date_array  = date.split("-");
                   date = date_array[2] + "-" + date_array[1] + "-" + date_array[0];
                     }
                   sb.setDate(date);
                   sb.setAttendence(rs.getString("attendence"));
                   String start_time=rs.getString("start_time");
                   if(start_time==null)
                       start_time="";
                   String end_time=rs.getString("end_time");
                   if(end_time==null)
                       end_time="";
                   sb.setStart_time(start_time);
                   sb.setEnd_time(end_time);
                  list.add(sb);
             }

        } catch (Exception e) {
            System.out.println( e);
        }
        return list;

    }
 public  boolean updateRecord(ShiftLoginBean bean)
   {
        boolean status=false;
        int rowsAffected=0;
        String query=" update attendence set attendence=? where attendence_id=? ";
        try{
         String[] a_attendence_id=bean.getA_attendence_id();
         for(int i=0;i<a_attendence_id.length;i++)
          {
          PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
          ps.setString(1,bean.getA_attendence()[i]);
          ps.setInt(2,Integer.parseInt(bean.getA_attendence_id()[i]));
          rowsAffected = ps.executeUpdate();
           }
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

   public List<String> getsearchemp(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select key_person_name from shift_key_person_map as skpm,key_person as kp,attendence a "
                    + "  where skpm.key_person_id=kp.key_person_id and skpm.shift_key_person_map_id=a.shift_key_person_map_id group by key_person_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = rset.getString("key_person_name");
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
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
      public List<String> getmobileno(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select mobile_no1 from shift_key_person_map as skpm,key_person as kp,attendence a "
                    + "  where skpm.key_person_id=kp.key_person_id and skpm.shift_key_person_map_id=a.shift_key_person_map_id group by key_person_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String mobile_no1 = rset.getString("mobile_no1");
                if (mobile_no1.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(mobile_no1);
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
           public List<String> getempcode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select emp_code from shift_key_person_map as skpm,key_person as kp,attendence a "
                    + "  where skpm.key_person_id=kp.key_person_id and skpm.shift_key_person_map_id=a.shift_key_person_map_id group by key_person_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
              String emp_code = rset.getString("emp_code");
                    list.add(emp_code);
                    count++;
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

        public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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

}
