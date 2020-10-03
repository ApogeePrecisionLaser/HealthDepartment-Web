/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicle.tableClasses.VehicleType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class VehicleTypeModel {
private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//        public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
 public static int  getNoOfRows(String vehicleType)
 {
    //  vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
        int noOfRows = 0;
        try {
        String query="SELECT count(vehicle_type_id) from vehicle_type "
                       + " where  IF('" + vehicleType + "' = '', vehicle_name LIKE '%%',vehicle_name  ='" + vehicleType + "') " ;
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

 public static List<VehicleType> showData(int lowerLimit,int noOfRowsToDisplay,String vehicleType)
  {
        //vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="SELECT * from vehicle_type"
                     + " where  IF('" + vehicleType + "' = '', vehicle_name LIKE '%%',vehicle_name  ='" + vehicleType + "') "
                    + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             VehicleType vt=new VehicleType();
             vt.setVehicle_type_id(rs.getInt("vehicle_type_id"));
             vt.setVehicle_type(rs.getString("vehicle_name"));
             vt.setRemark(rs.getString("remark"));
              list.add(vt);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

public  boolean insertRecord(VehicleType bean)
 {
boolean status=false;
String query="";
int rowsAffected=0;
int vehicle_type_id=bean.getVehicle_type_id();
if(vehicle_type_id==0)
 query="insert into vehicle_type (vehicle_name,remark) values(?,?)";
if(vehicle_type_id>0)
 query=" update vehicle_type set vehicle_name=?,remark=? where vehicle_type_id=?";


        try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
        ps.setString(1,bean.getVehicle_type());
        ps.setString(2,bean.getRemark());
         if(vehicle_type_id>0)
           ps.setInt(3,vehicle_type_id);
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

public  boolean deleteRecord(String vehicle_type_id){
    boolean status=false;
   int rowsAffected=0;
try{
 rowsAffected = connection.prepareStatement("Delete from vehicle_type where vehicle_type_id="+vehicle_type_id+" ").executeUpdate();
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
public static List<String> getVehicleType(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="select vehicle_name from vehicle_type";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String vehicle_name = rset.getString("vehicle_name");
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
