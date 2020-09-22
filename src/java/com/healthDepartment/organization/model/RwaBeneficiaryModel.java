/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.RwaBeneficiaryBean;
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
public class RwaBeneficiaryModel {
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    
 public static int  getNoOfRows()
 {
        int noOfRows = 0;
        try {
        String query="select count(rwa_beneficiary_mapping_id) from rwa_beneficiary_mapping " ;
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
  public static List<RwaBeneficiaryBean> showData(int lowerLimit,int noOfRowsToDisplay)
  {

       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="select rwa_beneficiary_mapping_id,rwa_name,kp.key_person_name,kp.emp_code from rwa as r,key_person as kp,rwa_beneficiary_mapping as rbm,beneficiary as b where "
+ " r.rwa_id=rbm.rwa_id and rbm.beneficiary_id=b.beneficiary_id and b.key_person_id=kp.key_person_id " 
                         + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs =ps.executeQuery();
             while(rs.next()){
             RwaBeneficiaryBean r=new RwaBeneficiaryBean();
             r.setRwa_beneficiary_mapping_id(rs.getInt("rwa_beneficiary_mapping_id"));
             r.setRwa_name(krutiToUnicode.convert_to_unicode(rs.getString("rwa_name")));
             r.setB_name(krutiToUnicode.convert_to_unicode(rs.getString("key_person_name")));
             r.setEmp_code(rs.getInt("emp_code"));

              list.add(r);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

    public  boolean insertRecord(RwaBeneficiaryBean bean)
 {      String query="";
        boolean status=false;
        int rowsAffected=0;
        try{
      int rwa_id=bean.getRwa_beneficiary_mapping_id();
        if(rwa_id==0)
          query="insert into rwa_beneficiary_mapping (rwa_id,beneficiary_id) values(?,?)" ;
          if(rwa_id>0)
         query="update rwa_beneficiary_mapping set rwa_id=?,beneficiary_id=? where rwa_id=?" ;
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);

         int rwa=getRwa_id(bean.getRwa_name());
         if(rwa == 0)
         ps.setNull(1, java.sql.Types.NULL);
         else
         ps.setInt(1, rwa);
         int b_id=getBeneficiary_id(bean.getEmp_code());
         if(b_id == 0)
         ps.setNull(2, java.sql.Types.NULL);
         else
         ps.setInt(2, b_id);

         if(rwa_id>0)
          ps.setInt(3, rwa_id);
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
  public  boolean deleteRecord(String rwa_id){
             boolean status=false;
             int rowsAffected=0;
             try{
             rowsAffected = connection.prepareStatement("Delete from rwa_beneficiary_mapping where rwa_beneficiary_mapping_id="+rwa_id+" ").executeUpdate();
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
public static int getRwa_id(String rwa_name) {
        int rwa_id = 0;
        try {
            String  name=krutiToUnicode.convert_to_unicode(rwa_name);
            String query = "select rwa_id from rwa"
                    +" where rwa_name='"+name+"' ";
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                rwa_id = rset.getInt("rwa_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return rwa_id;
    }

   public static int getBeneficiary_id(int emp_code) {
        int beneficiary_id = 0;
        try {

            String query = "select beneficiary_id from key_person as kp,beneficiary as b"
                    +" where  kp.key_person_id=b.key_person_id and emp_code="+emp_code ;
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                beneficiary_id = rset.getInt("beneficiary_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return beneficiary_id;
    }
  public List<String> getRwaName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select rwa_name from rwa "

                 + "Group by rwa_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String rwa_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("rwa_name"));
                if (rwa_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(rwa_name);
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






    
    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        RwaBeneficiaryModel.connection = connection;
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
