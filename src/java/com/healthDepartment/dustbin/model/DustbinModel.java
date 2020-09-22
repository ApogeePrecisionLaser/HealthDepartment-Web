/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.dustbin.model;

import com.healthDepartment.dustbin.tableClasses.Dustbin;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DustbinModel {
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();






 public static int  getNoOfRows(String dustbinType,String cityLocation)
 {
         dustbinType = krutiToUnicode.convert_to_unicode(dustbinType);
       cityLocation = krutiToUnicode.convert_to_unicode(cityLocation);
        int noOfRows = 0;
        try {
        String query="select count(dustbin_id) from dustbin_type as dt,city_location as cl,dustbin as d "
                              + " where dt.dustbin_type_id=d.dustbin_type_id and cl.city_location_id=d.city_location_id "
                               + " AND IF('" + dustbinType + "' = '', type_name LIKE '%%',type_name  ='" + dustbinType + "') "
                                  + " AND IF('" + cityLocation + "' = '', location LIKE '%%',location  ='" + cityLocation + "')  ";
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
 public static int  getNoOfRowsLevel(String dustbinType,String cityLocation)
 {
      dustbinType = krutiToUnicode.convert_to_unicode(dustbinType);
       cityLocation = krutiToUnicode.convert_to_unicode(cityLocation);
        int noOfRows = 0;
        try {
        String query="select count(l.level_id) from dustbin_type as dt,dustbin as d,level as l,city_location as cl "
                                 + " where dt.dustbin_type_id=d.dustbin_type_id and d.dustbin_id=l.dustbin_id and cl.city_location_id=d.city_location_id"
                                  + " AND IF('" + dustbinType + "' = '', type_name LIKE '%%',type_name  ='" + dustbinType + "') "
                                  + " AND IF('" + cityLocation + "' = '', location LIKE '%%',location  ='" + cityLocation + "') ";
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

 public static List<Dustbin> showData(int lowerLimit,int noOfRowsToDisplay,String dustbinType,String cityLocation)
  {
      dustbinType = krutiToUnicode.convert_to_unicode(dustbinType);
       cityLocation = krutiToUnicode.convert_to_unicode(cityLocation);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="select dustbin_id,dt.type_name,d.dustbin_no,d.latitude,d.longitude,cl.location ,d.remark from dustbin_type as dt,city_location as cl,dustbin as d "
                              + " where dt.dustbin_type_id=d.dustbin_type_id and cl.city_location_id=d.city_location_id "
                               + " AND IF('" + dustbinType + "' = '', type_name LIKE '%%',type_name  ='" + dustbinType + "') "
                                  + " AND IF('" + cityLocation + "' = '', location LIKE '%%',location  ='" + cityLocation + "') "
                              + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             Dustbin vt=new Dustbin();
             vt.setDustbin_id(rs.getInt("dustbin_id"));
             vt.setDustbin_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("type_name")));
             vt.setDustbin_no(rs.getInt("dustbin_no"));
             vt.setLatitude(rs.getDouble("latitude"));
             vt.setLongitude(rs.getDouble("longitude"));
             vt.setCity_location(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("location")));
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
 public static List<Dustbin> showDataLevel(int lowerLimit,int noOfRowsToDisplay,String dustbinType,String cityLocation)
  {
      dustbinType = krutiToUnicode.convert_to_unicode(dustbinType);
       cityLocation = krutiToUnicode.convert_to_unicode(cityLocation);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = " ";
            String query=" select l.level_id,dt.type_name,d.dustbin_no,l.level,dt.height,dt.volume ,l.created_date,cl.location from dustbin_type as dt,dustbin as d,level as l,city_location as cl "
                                 + " where dt.dustbin_type_id=d.dustbin_type_id and d.dustbin_id=l.dustbin_id and cl.city_location_id=d.city_location_id"
                                  + " AND IF('" + dustbinType + "' = '', type_name LIKE '%%',type_name  ='" + dustbinType + "') "
                                  + " AND IF('" + cityLocation + "' = '', location LIKE '%%',location  ='" + cityLocation + "') "
                                 + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             Dustbin vt=new Dustbin();
             vt.setLevel_id(rs.getInt("level_id"));
             vt.setDustbin_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("type_name")));
             vt.setDustbin_no(rs.getInt("dustbin_no"));
              vt.setLevel(rs.getInt("level"));
             vt.setHeight(rs.getDouble("height"));
             vt.setVolume(rs.getDouble("volume"));
             vt.setCreated_date(rs.getString("created_date"));
             vt.setCity_location(rs.getString("location"));
              list.add(vt);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }
public  boolean insertRecord(Dustbin bean)
 {    
boolean status=false;
String query="";
int rowsAffected=0;
int dustbin_id=bean.getDustbin_id();
if(dustbin_id==0)
 query="insert into dustbin (dustbin_type_id,dustbin_no,latitude,longitude,city_location_id,remark) values(?,?,?,?,?,?)";
if(dustbin_id>0)
 query=" update dustbin set dustbin_type_id=?,dustbin_no=?,latitude=?,longitude=?,city_location_id=?,remark=? where dustbin_id=?";
   try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
        int dustbin_type_id=getDustbin_type_id(bean.getDustbin_type());
            ps.setInt(1, dustbin_type_id);
            ps.setInt(2, bean.getDustbin_no());
            ps.setDouble(3, bean.getLatitude());
            ps.setDouble(4, bean.getLongitude());
            int city_location_id=getCityLocation_id(bean.getCity_location());
            ps.setInt(5, city_location_id);
            ps.setString(6,krutiToUnicode.convert_to_unicode(bean.getRemark()));
           if(dustbin_id>0)
             ps.setInt(7, dustbin_id);
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

public  boolean deleteRecord(String dustbin_id){
    boolean status=false;
   int rowsAffected=0;
try{
 rowsAffected = connection.prepareStatement("Delete from dustbin where dustbin_id="+dustbin_id+" ").executeUpdate();
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

public static int getDustbin_type_id(String dustbin_type) {
        int dustbin_type_id = 0;
        try {
             dustbin_type=krutiToUnicode.convert_to_unicode(dustbin_type);
            String query = "select dustbin_type_id from dustbin_type"
                    +" where type_name='"+dustbin_type+"' ";
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                dustbin_type_id = rset.getInt("dustbin_type_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return dustbin_type_id;
    }
public static int getCityLocation_id(String city_location) {
        int city_location_id = 0;
        try {
             city_location=krutiToUnicode.convert_to_unicode(city_location);
            String query = "select city_location_id from city_location"
                    +" where location='"+city_location+"' ";
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return city_location_id;
    }

public static List<String> getDustbinType(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="select type_name from dustbin_type";
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
 public static List<String> getCityLocation(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="select location from city_location";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String type_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location"));
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
