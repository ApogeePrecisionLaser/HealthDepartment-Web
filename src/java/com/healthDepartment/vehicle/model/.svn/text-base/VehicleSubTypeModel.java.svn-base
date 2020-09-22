/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicle.tableClasses.VehicleSubType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class VehicleSubTypeModel {
private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();



 public static int  getNoOfRows(String vehicleType,String vehicleSubType)
 {
       vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
       vehicleSubType = krutiToUnicode.convert_to_unicode(vehicleSubType);
        int noOfRows = 0;
        try {
        String query="select count(vehicle_sub_type_id) from vehicle_sub_type as vst,vehicle_type as vt where vst.vehicle_type_id=vt.vehicle_type_id"
                           + " AND  IF('" + vehicleType + "' = '', vehicle_name LIKE '%%',vehicle_name  ='" + vehicleType + "') "
                           + " AND  IF('" + vehicleSubType + "' = '', type_name LIKE '%%',type_name  ='" + vehicleSubType + "') ";
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

 public static List<VehicleSubType> showData(int lowerLimit,int noOfRowsToDisplay,String vehicleType,String vehicleSubType)
  {
       vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
       vehicleSubType = krutiToUnicode.convert_to_unicode(vehicleSubType);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="select vehicle_sub_type_id,vehicle_name,type_name,vst.remark from vehicle_sub_type as vst,vehicle_type as vt where vst.vehicle_type_id=vt.vehicle_type_id"
                           + " AND  IF('" + vehicleType + "' = '', vehicle_name LIKE '%%',vehicle_name  ='" + vehicleType + "') "
                           + " AND  IF('" + vehicleSubType + "' = '', type_name LIKE '%%',type_name  ='" + vehicleSubType + "') "
                           + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             VehicleSubType vst=new VehicleSubType();
             vst.setVehicle_sub_type_id(rs.getInt("vehicle_sub_type_id"));
             vst.setVehicle_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("vehicle_name")));
             vst.setVehicle_sub_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("type_name")));
             vst.setRemark(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("remark")));
              list.add(vst);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

public  boolean insertRecord(VehicleSubType bean)
 {
boolean status=false;
String query="";
int rowsAffected=0;
int vehicle_sub_type_id=bean.getVehicle_sub_type_id();
if(vehicle_sub_type_id==0)
 query="insert into vehicle_sub_type (type_name,remark,vehicle_type_id) values(?,?,?)";
if(vehicle_sub_type_id>0)
 query=" update vehicle_sub_type set type_name=?,remark=?,vehicle_type_id=? where vehicle_sub_type_id=? ";
        try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
        ps.setString(1,krutiToUnicode.convert_to_unicode(bean.getVehicle_sub_type()));
        ps.setString(2,krutiToUnicode.convert_to_unicode(bean.getRemark()));
        int vehicle_type_id=getvehicle_type_id(bean.getVehicle_type());
           ps.setInt(3,vehicle_type_id);
        if(vehicle_sub_type_id>0)
           ps.setInt(4,vehicle_sub_type_id);
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

public  boolean deleteRecord(String vehicle_sub_type_id){
    boolean status=false;
   int rowsAffected=0;
try{
 rowsAffected = connection.prepareStatement("Delete from vehicle_sub_type where vehicle_sub_type_id="+vehicle_sub_type_id+" ").executeUpdate();
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


public static int getvehicle_type_id(String vehicle_type) {
        int vehicle_type_id = 0;
        try {
             vehicle_type=krutiToUnicode.convert_to_unicode(vehicle_type);
            String query = "select vehicle_type_id from vehicle_type"
                    +" where vehicle_name='"+vehicle_type+"' ";
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                vehicle_type_id = rset.getInt("vehicle_type_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_type_id;
    }

public static List<String> getVehicleType(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="select vehicle_name from vehicle_type";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String vehicle_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("vehicle_name"));
                       if(vehicle_name.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(vehicle_name);
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
