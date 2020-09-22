/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.Rwa;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Administrator
 */
public class RwaModel {
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private static int emp_code;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    

 public static int  getNoOfRows()
 {
        int noOfRows = 0;
        try {
        String query="select count(rwa_id) from rwa "
                +" where r.active='Y'";
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

 public static List<Rwa> showData(int lowerLimit,int noOfRowsToDisplay)
  {

       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="select * from rwa as r,payment_schedule as ps where r.payment_schedule_id=ps.payment_schedule_id "
                    +" and r.active='Y' "
                         + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs =ps.executeQuery();
             while(rs.next()){
             Rwa r=new Rwa();
             r.setRwa_id(rs.getInt("rwa_id"));
             r.setRwa_name(krutiToUnicode.convert_to_unicode(rs.getString("rwa_name")));
             String president_name=getKey_Person_Name(rs.getInt("president_id"));
             r.setPresident_name(president_name);
             r.setEmp_code1(emp_code);
             String secretary_name=getKey_Person_Name(rs.getInt("secretary_id"));
             r.setSecretary_name(secretary_name);
             r.setEmp_code2(emp_code);
             String treasrar_name=getKey_Person_Name(rs.getInt("tresrar_id"));
             r.setTreasrar_name(treasrar_name);
             r.setEmp_code3(emp_code);
             String working1_name=getKey_Person_Name(rs.getInt("working_1"));
             r.setWorking1_name(working1_name);
             r.setEmp_code4(emp_code);
             String working2_name=getKey_Person_Name(rs.getInt("working_2"));
             r.setWorking2_name(working2_name);
             r.setEmp_code5(emp_code);
             String working3_name=getKey_Person_Name(rs.getInt("working_3"));
             r.setWorking3_name(working3_name);
             r.setEmp_code6(emp_code);
             r.setPayment_schedule_name(rs.getString("payment_schedule_name"));
             r.setMonthly_fee(rs.getInt("monthly_fees"));
             r.setDescription(rs.getString("remark"));

              list.add(r);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

     public static String getKey_Person_Name(int id) {
        String key_person_name="";
        try {

            String query = "select key_person_name,emp_code from key_person"
                    +" where key_person_id="+id ;
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                key_person_name = rset.getString("key_person_name");
               emp_code = rset.getInt("emp_code");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return key_person_name;
    }

    public  boolean insertRecord(Rwa bean)
 {      String query="";
        boolean status=false;
        int rowsAffected=0;
        int persident_empCode=bean.getEmp_code1();
        int designation_id=getDesignation_id(persident_empCode);
        try{
      int rwa_id=bean.getRwa_id();
        if(rwa_id==0)
          query="insert into rwa (president_id,secretary_id,tresrar_id,working_1,working_2,working_3,rwa_name,monthly_fees,payment_schedule_id,remark,designation_id) values(?,?,?,?,?,?,?,?,?,?,?)" ;
//          if(rwa_id>0)
//         query="update rwa set president_id=?,secretary_id=?,tresrar_id=?,working_1=?,working_2=?,working_3=?,rwa_name=?,monthly_fees=?,payment_schedule_id=?,remark=?,designation_id=? where rwa_id=?" ;
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
         
         int emp_code1=getKey_Person_id(bean.getEmp_code1());
         if(emp_code1 == 0)
         ps.setNull(1, java.sql.Types.NULL);
         else
         ps.setInt(1, emp_code1);
         int emp_code2=getKey_Person_id(bean.getEmp_code2());
         if(emp_code2 == 0)
         ps.setNull(2, java.sql.Types.NULL);
         else
         ps.setInt(2, emp_code2);
         int emp_code3=getKey_Person_id(bean.getEmp_code3());
         if(emp_code3 == 0)
         ps.setNull(3, java.sql.Types.NULL);
         else
         ps.setInt(3, emp_code3);
          int emp_code4=getKey_Person_id(bean.getEmp_code4());
         if(emp_code4 == 0)
         ps.setNull(4, java.sql.Types.NULL);
         else
         ps.setInt(4, emp_code4);

         int emp_code5=getKey_Person_id(bean.getEmp_code5());
         if(emp_code5 == 0)
         ps.setNull(5, java.sql.Types.NULL);
         else
         ps.setInt(5, emp_code5);

         int emp_code6=getKey_Person_id(bean.getEmp_code6());
         if(emp_code6 == 0)
         ps.setNull(6, java.sql.Types.NULL);
         else
         ps.setInt(6, emp_code6);
         ps.setString(7, krutiToUnicode.convert_to_unicode(bean.getRwa_name()));
         ps.setInt(8, bean.getMonthly_fee());
         ps.setInt(9, bean.getPayment_schedule());
         ps.setString(10, bean.getDescription());
         ps.setInt(11,designation_id);
//         if(rwa_id>0)
//          ps.setInt(12, rwa_id);
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
    public  boolean reviseRecord(Rwa bean)
 {      String query="";
        boolean status=false;
        int rowsAffected=0;
        int rwa_id=bean.getRwa_id();
        int persident_empCode=bean.getEmp_code1();
        int designation_id=getDesignation_id(persident_empCode);
        String query1 = "SELECT max(rev_no) rev_no FROM rwa WHERE rwa_id = "+rwa_id+" && active='Y' ORDER BY rev_no DESC";
      String query2 = " UPDATE rwa SET active=? WHERE rwa_id = ? && rev_no = ? ";
      String  query3="insert into rwa (rwa_id,president_id,secretary_id,tresrar_id,working_1,working_2,working_3,rwa_name,monthly_fees,payment_schedule_id,remark,designation_id,rev_no,active) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
      int updateRowsAffected = 0;
      try {
           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
           ResultSet rs = ps.executeQuery();
           if(rs.next()){
           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
           pst.setString(1,  "N");
           pst.setInt(2,rwa_id);
           pst.setInt(3, rs.getInt("rev_no"));
           updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int rev = rs.getInt("rev_no")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
             int emp_code1=getKey_Person_id(bean.getEmp_code1());
             psmt.setInt(1,rwa_id);
         if(emp_code1 == 0)
         psmt.setNull(2, java.sql.Types.NULL);
         else
         psmt.setInt(2, emp_code1);
         int emp_code2=getKey_Person_id(bean.getEmp_code2());
         if(emp_code2 == 0)
         psmt.setNull(3, java.sql.Types.NULL);
         else
         psmt.setInt(3, emp_code2);
         int emp_code3=getKey_Person_id(bean.getEmp_code3());
         if(emp_code3 == 0)
         psmt.setNull(4, java.sql.Types.NULL);
         else
         psmt.setInt(4, emp_code3);
          int emp_code4=getKey_Person_id(bean.getEmp_code4());
         if(emp_code4 == 0)
         psmt.setNull(5, java.sql.Types.NULL);
         else
         psmt.setInt(5, emp_code4);

         int emp_code5=getKey_Person_id(bean.getEmp_code5());
         if(emp_code5 == 0)
         psmt.setNull(6, java.sql.Types.NULL);
         else
         psmt.setInt(6, emp_code5);

         int emp_code6=getKey_Person_id(bean.getEmp_code6());
         if(emp_code6 == 0)
         psmt.setNull(7, java.sql.Types.NULL);
         else
         psmt.setInt(7, emp_code6);
         psmt.setString(8, krutiToUnicode.convert_to_unicode(bean.getRwa_name()));
         psmt.setInt(9, bean.getMonthly_fee());
         psmt.setInt(10, bean.getPayment_schedule());
         psmt.setString(11, bean.getDescription());
         psmt.setInt(12,designation_id);

             psmt.setInt(13, rev);
             psmt.setString(14, "Y");
             rowsAffected = psmt.executeUpdate();
              if(rowsAffected > 0)
              status=true;
               }
          }
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
    public int getDesignation_id(int persident_empCode){
        int code=0;
        String query="select designation_id "
                      +" from key_person kp "
                      +" where kp.emp_code="+persident_empCode;
        try{
        PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            code=rs.getInt("designation_id");
        }
        }catch(Exception e){
            System.out.println("Error in getDesignation_id()"+e);
        }
        return code;
    }
  public  boolean deleteRecord(String rwa_id){
             boolean status=false;
             int rowsAffected=0;
             try{
             rowsAffected = connection.prepareStatement("Delete from rwa where rwa_id="+rwa_id+" ").executeUpdate();
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

    public static int getKey_Person_id(int emp_code) {
        int key_person_id = 0;
        try {

            String query = "select key_person_id from key_person"
                    +" where emp_code="+emp_code ;
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                key_person_id = rset.getInt("key_person_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return key_person_id;
    }
    
       public List<String> getPersonName(String q,String emp_code) {
        List<String> list = new ArrayList<String>();
        String query = " select key_person_name from key_person "
                     +  " where IF('" + emp_code + "'='', emp_code like '%%', emp_code ='" + emp_code + "') "
                 + "Group by key_person_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
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
   public List<String> getEmp_code(String q,String person_name)
            {
           person_name=krutiToUnicode.convert_to_unicode(person_name);
        List<String> list = new ArrayList<String>();
        String query = " select emp_code from key_person as kp where  "
                 +  "  IF('" + person_name + "'='', key_person_name like '%%', key_person_name ='" + person_name + "') " ;

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String emp_code = rset.getString("emp_code");
                if (emp_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(emp_code);
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
      public Map<Integer, String> getPayment_type() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        String query = "SELECT payment_schedule_id,payment_schedule_name FROM payment_schedule ";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                map.put(rset.getInt("payment_schedule_id"), rset.getString("payment_schedule_name"));
            }
        } catch (Exception e) {
            System.out.println("Error in vehicle_type" + e);
        }
        return map;
    }
    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        RwaModel.connection = connection;
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
