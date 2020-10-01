/*
 ShiftWorkBench-2
 */

package com.healthDepartment.shift.model;
import java.sql.PreparedStatement;
import java.sql.Connection;
import com.healthDepartment.shift.tableClasses.ShiftBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tushar Singh
 */
public class ShiftModel
{
private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//        public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
 public static int  getNoOfRows()
 {
        int noOfRows = 0;
        try {
        String query="SELECT count(*) from shift_type ";
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

  public static List<ShiftBean> showData(int lowerLimit,int noOfRowsToDisplay)
  {
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="SELECT * from shift_type"
                  + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             ShiftBean sb=new ShiftBean();
             sb.setShift_type_id(rs.getInt("shift_type_id"));
             sb.setShift_type(rs.getString("shift_type"));
             sb.setStarting_time(rs.getString("starting_time"));
             sb.setEnding_time(rs.getString("ending_time"));
              list.add(sb);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

public  boolean insertRecord(ShiftBean bean)
 {
boolean status=false;
String query="";
int rowsAffected=0;
int shift_type_id=bean.getShift_type_id();
if(shift_type_id==0)
 query="insert into shift_type (shift_type,starting_time,ending_time) values(?,?,?)";
if(shift_type_id>0)
 query=" update shift_type set shift_type=?,starting_time=?,ending_time=? where shift_type_id=?";


        try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
        ps.setString(1,bean.getShift_type());
        ps.setString(2,bean.getStarting_time());
         ps.setString(3,bean.getEnding_time());
         if(shift_type_id>0)
           ps.setInt(4,shift_type_id);
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

public  boolean deleteRecord(String shift_type_id){
    boolean status=false;
   int rowsAffected=0;
try{
 rowsAffected = connection.prepareStatement("Delete from shift_type where shift_type_id="+shift_type_id+" ").executeUpdate();
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
