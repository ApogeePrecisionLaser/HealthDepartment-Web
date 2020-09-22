/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.dustbin.model;

import com.healthDepartment.dustbin.tableClasses.DustbinType;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DustbinTypeModel {
private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
 public static int  getNoOfRows(String dustbinType)
 {
        dustbinType = krutiToUnicode.convert_to_unicode(dustbinType);
        int noOfRows = 0;
        try {
        String query="SELECT count(dustbin_type_id) from dustbin_type "
                     + " where IF('" + dustbinType + "' = '', type_name LIKE '%%',type_name  ='" + dustbinType + "') ";
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

 public static List<DustbinType> showData(int lowerLimit,int noOfRowsToDisplay,String dustbinType)
  {
      dustbinType = krutiToUnicode.convert_to_unicode(dustbinType);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="SELECT * from dustbin_type"
                       + " where IF('" + dustbinType + "' = '', type_name LIKE '%%',type_name  ='" + dustbinType + "') "
                       + addQuery;
    try{
           PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
           ResultSet rs =ps.executeQuery();
             while(rs.next()){
             DustbinType vt=new DustbinType();
             vt.setDustbin_type_id(rs.getInt("dustbin_type_id"));
             vt.setDustbin_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("type_name")));
             vt.setHeight(rs.getDouble("height"));
              vt.setVolume(rs.getDouble("volume"));
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

public  boolean insertRecord(DustbinType bean)
 {
boolean status=false;
String query="";
int rowsAffected=0;
int dustbin_type_id=bean.getDustbin_type_id();
if(dustbin_type_id==0)
 query="insert into dustbin_type (type_name,height,volume,remark) values(?,?,?,?)";
if(dustbin_type_id>0)
 query=" update dustbin_type set type_name=?,height=?,volume=?,remark=? where dustbin_type_id=?";


        try
        {
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
         ps.setString(1,krutiToUnicode.convert_to_unicode(bean.getDustbin_type()));
         ps.setDouble(2, bean.getHeight());
         ps.setDouble(3, bean.getVolume());
        ps.setString(4,krutiToUnicode.convert_to_unicode(bean.getRemark()));
         if(dustbin_type_id>0)
           ps.setInt(5,dustbin_type_id);
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

public  boolean deleteRecord(String dustbin_type_id){
    boolean status=false;
   int rowsAffected=0;
try{
 rowsAffected = connection.prepareStatement("Delete from dustbin_type where dustbin_type_id="+dustbin_type_id+" ").executeUpdate();
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
