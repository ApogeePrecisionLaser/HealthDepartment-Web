/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.model;

import com.healthDepartment.general.model.GeneralModel;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicle.tableClasses.Vehicle;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class VehicleModel {
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    


 public static int  getNoOfRows(String vehicleSubType,String vehicleNo, String mobileNo)
 {
      vehicleSubType = krutiToUnicode.convert_to_unicode(vehicleSubType);
        int noOfRows = 0;
        try {
        String query="select count(vehicle_id) "
                   +  "   from vehicle as v,vehicle_sub_type as vst where v.vehicle_sub_type_id=vst.vehicle_sub_type_id  "
                   + " AND  IF('" + vehicleSubType + "' = '', type_name LIKE '%%',type_name  ='" + vehicleSubType + "') "
                   + " AND IF('" + vehicleNo + "' = '', vehicle_no LIKE '%%',vehicle_no ='" + vehicleNo + "') "
                   + " AND IF('" + mobileNo + "' = '', mobile_no LIKE '%%',mobile_no ='" + mobileNo + "') " ;
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
 public static int  getNoOfRowsVehicleLocation(String vehicleSubType,String vehicleNo, String mobileNo)
 {
        vehicleSubType = krutiToUnicode.convert_to_unicode(vehicleSubType);
        int noOfRows = 0;
        try {
        String query=" select count(vl.vehicle_location_id) "
                           +  "  from vehicle_sub_type as vst,vehicle as v,vehicle_location as vl "
                           +  "   where vst.vehicle_sub_type_id=v.vehicle_sub_type_id and v.vehicle_id=vl.vehicle_id "
                               + " AND  IF('" + vehicleSubType + "' = '', type_name LIKE '%%',type_name  ='" + vehicleSubType + "') "
                   + " AND IF('" + vehicleNo + "' = '', vehicle_no LIKE '%%',vehicle_no ='" + vehicleNo + "') "
                   + " AND IF('" + mobileNo + "' = '', mobile_no LIKE '%%',mobile_no ='" + mobileNo + "') " ;
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
 public static List<Vehicle> showData(int lowerLimit,int noOfRowsToDisplay,String vehicleSubType,String vehicleNo, String mobileNo)
  {
      vehicleSubType = krutiToUnicode.convert_to_unicode(vehicleSubType);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="select vehicle_id,type_name,vehicle_no,vehicle_code,permit_validity,fitness_validity,puc_validity,mobile_no,imei_no,v.remark "
                             +  "   from vehicle as v,vehicle_sub_type as vst where v.vehicle_sub_type_id=vst.vehicle_sub_type_id "
                             + " AND  IF('" + vehicleSubType + "' = '', type_name LIKE '%%',type_name  ='" + vehicleSubType + "') "
                             + " AND IF('" + vehicleNo + "' = '', vehicle_no LIKE '%%',vehicle_no ='" + vehicleNo + "') "
                             + " AND IF('" + mobileNo + "' = '', mobile_no LIKE '%%',mobile_no ='" + mobileNo + "') "
                             + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             Vehicle vt=new Vehicle();
             vt.setVehicle_id(rs.getInt("vehicle_id"));
             vt.setVehicle_sub_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("type_name")));
             vt.setVehicle_no(rs.getString("vehicle_no"));
             vt.setVehicle_code(rs.getInt("vehicle_code"));
              String date=rs.getString("permit_validity");
              if(date != null && !date.isEmpty()){
                String[] date_array  = date.split("-");
                date = date_array[2] + "-" + date_array[1] + "-" +  date_array[0];
                }
             vt.setPermit_validity(date);
              date=rs.getString("fitness_validity");
              if(date != null && !date.isEmpty()){
                String[] date_array  = date.split("-");
                date = date_array[2] + "-" + date_array[1] + "-" +  date_array[0];
                }
             vt.setFitness_validity(date);
                date=rs.getString("puc_validity");
              if(date != null && !date.isEmpty()){
                String[] date_array  = date.split("-");
                date = date_array[2] + "-" + date_array[1] + "-" +  date_array[0];
                }
             vt.setPuc_validity(date);
             vt.setMobile_no(rs.getString("mobile_no"));
             vt.setImei_no(rs.getString("imei_no"));
             vt.setRemark(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("remark")));
              list.add(vt);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }
 public static List<Vehicle> showDataVehicleLocation(int lowerLimit,int noOfRowsToDisplay,String vehicleSubType,String vehicleNo, String mobileNo)
  {
        vehicleSubType = krutiToUnicode.convert_to_unicode(vehicleSubType);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query=" select vl.vehicle_location_id,vst.type_name,v.vehicle_no,v.vehicle_code,v.mobile_no,vl.latitude,vl.longitude "
                           +  "  from vehicle_sub_type as vst,vehicle as v,vehicle_location as vl "
                           +  "   where vst.vehicle_sub_type_id=v.vehicle_sub_type_id and v.vehicle_id=vl.vehicle_id "
                           + " AND  IF('" + vehicleSubType + "' = '', type_name LIKE '%%',type_name  ='" + vehicleSubType + "') "
                           + " AND IF('" + vehicleNo + "' = '', vehicle_no LIKE '%%',vehicle_no ='" + vehicleNo + "') "
                           + " AND IF('" + mobileNo + "' = '', mobile_no LIKE '%%',mobile_no ='" + mobileNo + "') " 
                           + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             Vehicle vt=new Vehicle();
             vt.setVehicle_location_id(rs.getInt("vehicle_location_id"));
             vt.setVehicle_sub_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("type_name")));
             vt.setVehicle_no(rs.getString("vehicle_no"));
             vt.setVehicle_code(rs.getInt("vehicle_code"));
             vt.setMobile_no(rs.getString("mobile_no"));
             vt.setLatitude(rs.getDouble("latitude"));
             vt.setLongitude(rs.getDouble("longitude"));
              list.add(vt);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }
public  boolean insertRecord(Vehicle bean)
 {
     GeneralModel generalModel = new GeneralModel();
boolean status=false;
String query="";
int rowsAffected=0;
int vehicle_id=bean.getVehicle_id();
if(vehicle_id==0)
 query="insert into vehicle (vehicle_sub_type_id,vehicle_no,vehicle_code,permit_validity,fitness_validity,puc_validity,mobile_no,imei_no,remark) values(?,?,?,?,?,?,?,?,?)";
if(vehicle_id>0)
 query=" update vehicle set vehicle_sub_type_id=?,vehicle_no=?,vehicle_code=?,permit_validity=?,fitness_validity=?,puc_validity=?,mobile_no=?,imei_no=?,remark=? where vehicle_id=?";


        try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
        int vehicle_sub_type_id=getvehicle_sub_type_id(bean.getVehicle_sub_type());
            ps.setInt(1, vehicle_sub_type_id);
            ps.setString(2, bean.getVehicle_no());
            ps.setInt(3, bean.getVehicle_code());
            Date permit_validity=generalModel.convertToSqlDate(bean.getPermit_validity());
            Date fitness_validity= generalModel.convertToSqlDate(bean.getFitness_validity());
            Date puc_validity=generalModel.convertToSqlDate(bean.getPuc_validity());
            ps.setDate(4, (java.sql.Date) permit_validity);
            ps.setDate(5, (java.sql.Date) fitness_validity);
            ps.setDate(6, (java.sql.Date) puc_validity);
            ps.setString(7, bean.getMobile_no());
            ps.setString(8, bean.getImei_no());
            ps.setString(9,krutiToUnicode.convert_to_unicode(bean.getRemark()));
           if(vehicle_id>0)
             ps.setInt(10, vehicle_id);
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

public  boolean deleteRecord(String vehicle_id){
    boolean status=false;
   int rowsAffected=0;
try{
 rowsAffected = connection.prepareStatement("Delete from vehicle where vehicle_id="+vehicle_id+" ").executeUpdate();
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

public static int getvehicle_sub_type_id(String vehicle_sub_type) {
        int vehicle_type_id = 0;
        try {
             vehicle_sub_type=krutiToUnicode.convert_to_unicode(vehicle_sub_type);
            String query = "select vehicle_sub_type_id from vehicle_sub_type"
                    +" where type_name='"+vehicle_sub_type+"' ";
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                vehicle_type_id = rset.getInt("vehicle_sub_type_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_type_id;
    }

public static List<String> getVehicleSubType(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="select type_name from vehicle_sub_type";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String type_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("type_name"));
                       if(type_name.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(type_name);
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

public static List<String> getVehicleNo(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="select vehicle_no from vehicle";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String type_name = rset.getString("vehicle_no");
                       if(type_name.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(type_name);
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
public static List<String> getMobileNo(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="select mobile_no from vehicle";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String type_name = rset.getString("mobile_no");
                       if(type_name.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(type_name);
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


public String getPointLatLong(String vehicle_location_id){
    String lat="";
    String longi="";
//    String query="select latitude,longitude from vehicle_location p,vehicle_key_person_point vkpp "
//            +" where vkpp.point_id=p.point_id "
//            +" and vehicle_key_person_point_id=?";


    String query="select latitude,longitude from vehicle_location p "
            +" where p.vehicle_location_id=? ";

    try{
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1,vehicle_location_id);
        ResultSet rs  = pst.executeQuery();
        while(rs.next()){
            lat=rs.getString("latitude");
            longi=rs.getString("longitude");


        }
    }catch(Exception e){
        System.out.println(e);
    }


    return lat+","+longi;
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
