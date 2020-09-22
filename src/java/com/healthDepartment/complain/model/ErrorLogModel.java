/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.complain.model;

import com.healthDepartment.complain.tableClasses.ErrorLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Shobha
 */
public class ErrorLogModel {
    private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    DecimalFormat df = new DecimalFormat("0.00");

    public int getTotalRowsInTable(String error_person) {

        String query = "select count(*) from (select count(*) from error_log el "
                +" left join shift_key_person_map as skpm1 on el.error_causeby_id=skpm1.shift_key_person_map_id "
                +" left join shift_key_person_map as skpm2 on el.error_sendto_id=skpm2.shift_key_person_map_id "
                +" left join key_person as kp1 on kp1.key_person_id=skpm1.key_person_id "
                +" left join key_person as kp2 on kp2.key_person_id=skpm2.key_person_id "
                +" left join type_of_error as toe on toe.type_of_error_id=el.type_of_error_id "
                +" left join status as s on s.status_id=el.status_id group by error_log_id) as a " ;
             //    +" And IF('" + error_person + "' = '',kp1.key_person_name LIKE '%%', kp1.key_person_name=?) ";
        int noOfRows = 0;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            //  presta.setString(1,error_person);
            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows - OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public ArrayList<ErrorLog> getAllRecords(int lowerLimit, int noOfRowsToDisplay,String error_person) {
        ArrayList<ErrorLog> list = new ArrayList<ErrorLog>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query = " select el.error_log_id, kp1.key_person_name as k1,kp2.key_person_name as k2,s.status_name,toe.error_name,el.created_date,el.error_causeby_id from error_log el "
                +" left join shift_key_person_map as skpm1 on el.error_causeby_id=skpm1.shift_key_person_map_id "
                +" left join shift_key_person_map as skpm2 on el.error_sendto_id=skpm2.shift_key_person_map_id "
                +" left join key_person as kp1 on kp1.key_person_id=skpm1.key_person_id "
                +" left join key_person as kp2 on kp2.key_person_id=skpm2.key_person_id "
               +" left join type_of_error as toe on toe.type_of_error_id=el.type_of_error_id "
                +" left join status as s on s.status_id=el.status_id group by error_log_id "
            //    +" And IF('" + error_person + "' = '',kp1.key_person_name LIKE '%%', kp1.key_person_name=?) "
                        + addQuery;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
         //   pstmt.setString(1,error_person);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ErrorLog ohLevelBean = new ErrorLog();
                ohLevelBean.setError_log_id(rset.getInt("error_log_id"));
                ohLevelBean.setKp1(rset.getString("k1"));
                ohLevelBean.setKp2(rset.getString("k2"));
                ohLevelBean.setStatus(rset.getString("status_name"));
                ohLevelBean.setError_name(rset.getString("error_name"));
                String date=rset.getString("created_date");
                String date1[]=date.split(" ");
                ohLevelBean.setDate(date1[0]);
                ohLevelBean.setError_causeby_id(Integer.parseInt(rset.getString("error_causeby_id")));
                list.add(ohLevelBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

     public List getVehicleCode(String q){

     List<String> li = new ArrayList<String>();
        String query = " SELECT vehicle_code FROM vehicle "
                ;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("vehicle_code");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getPriority ERROR inside TypeOfErrorModel" + e);
        }
        return li;


    }
public int updateVehicleRecord(int error_log_id,int error_causeby_id,String date,String vehicle_code){
    int vehicle_id=getVehicleId(vehicle_code);
    int count=0;
    String query="update vehicle_key_person "
            + "set vehicle_id=? "
            + "where shift_key_person_map_id= "+error_causeby_id
            + " and date='"+date+"'";

     try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, vehicle_id);
             count = ps.executeUpdate();
            if (count == 0) {
               System.out.println("Not updated");
            }
        } catch (Exception e) {
            System.out.println("getPriority ERROR inside TypeOfErrorModel" + e);
        }

    return count;
}
public int getVehicleId(String vehicle_code){
    int vehicle_id=0;
    String query = " SELECT vehicle_id FROM vehicle where vehicle_code="+vehicle_code;

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {    // move cursor from BOR to valid record.
                 vehicle_id = Integer.parseInt(rs.getString("vehicle_id"));

            }

        } catch (Exception e) {
            System.out.println("getPriority ERROR inside TypeOfErrorModel" + e);
        }

    return vehicle_id;
}
 public List getErrorPersonName(String q){

         List<String> li = new ArrayList<String>();
//        String query = "select kp.key_person_name "
//                       +" from error_log el,shift_key_person_map skpm,key_person kp "
//                       +" where el.error_causeby_id=skpm.shift_key_person_map_id "
//                       +" and skpm.shift_key_person_map_id=kp.key_person_id ";

     String query = "select kp.key_person_name "
                       +" from error_log el,shift_key_person_map skpm,key_person kp "
                       +" where el.error_causeby_id=skpm.shift_key_person_map_id "
                       +" and skpm.key_person_id=kp.key_person_id ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("key_person_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getDesignation ERROR inside TypeOfErrorModel" + e);
        }
        return li;

    }
  public  byte[] generateRecordList(String jrxmlFilePath,List li)
     {
                byte[] reportInbytes = null;
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(li);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
                } catch (Exception e) {

                    System.out.println("TypeOfErrorModel generatReport() JRException: " + e);
                }
                return reportInbytes;
     }
    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("PaymentStatusModel setConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

}
