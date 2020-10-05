/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicle.tableClasses.ShiftVehicleDetail;
import com.healthDepartment.vehicle.tableClasses.VehicleKeyPerson;
import com.healthDepartment.vehicle.tableClasses.VehicleType;
import com.healthDepartment.vehicle.tableClasses.VehicleWeight;
import java.io.File;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Vivek
 */
public class VehicleWeightModel {
 private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//        public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
 public static int  getNoOfRows(String search_vehicle_code,String search_vehicle_number)
 {
    //  search_vehicle_code = krutiToUnicode.convert_to_unicode(search_vehicle_code);
      //search_taken_by = krutiToUnicode.convert_to_unicode(search_taken_by);
        int noOfRows = 0;
        try {

            String query=" select count(*)"
        +" from vehicle_weight vw,vehicle v "
        +" where vw.vehicle_id=v.vehicle_id "
        +" AND IF('" + search_vehicle_code + "' = '', vehicle_code LIKE '%%',vehicle_code='" + search_vehicle_code + "') "
        +" AND IF('" + search_vehicle_number + "' = '', vehicle_no LIKE '%%',vehicle_no='" + search_vehicle_number + "') ";


//+ " AND IF('" + search_given_by + "' = '', shift_key_person_map_id1 LIKE '%%',shift_key_person_map_id1='" + search_given_by + "') "
//+ " And IF('" + search_taken_by + "' = '',shift_key_person_map_id2 LIKE '%%', shift_key_person_map_id2='" + search_taken_by + "')";

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

 public static List<VehicleWeight> showData(int lowerLimit,int noOfRowsToDisplay,String search_vehicle_code,String search_vehicle_number)
  {
        //vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
    // search_vehicle_code = krutiToUnicode.convert_to_unicode(search_vehicle_code);
     // search_taken_by = krutiToUnicode.convert_to_unicode(search_taken_by);
       List list = new ArrayList();

         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
//           
String query=" select vw.vehicle_weight_id,v.vehicle_code,v.vehicle_no,vw.weight,vw.date_time,vw.image_path"
        +" from vehicle_weight vw,vehicle v "
        +" where vw.vehicle_weight_id=v.vehicle_id "
        +" AND IF('" + search_vehicle_code + "' = '', vehicle_code LIKE '%%',vehicle_code='" + search_vehicle_code + "') "
        +" AND IF('" + search_vehicle_number + "' = '', vehicle_no LIKE '%%',vehicle_no='" + search_vehicle_number + "') "
        +addQuery;

    try {
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
              ResultSet rs =ps.executeQuery();
             while(rs.next()){
             VehicleWeight vt=new VehicleWeight();
             vt.setVehicleWeightId(rs.getInt("vehicle_weight_id"));
             vt.setVehicle_code(rs.getString("vehicle_code"));
             vt.setVehicle_number(rs.getString("vehicle_no"));
             vt.setWeight(rs.getDouble("weight"));
             vt.setDate_time(rs.getString("date_time"));

               //vt.setEnding_time(rs.getString("ending_time"));

              list.add(vt);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

//public  boolean insertRecord(ShiftVehicleDetail bean)
// {
//boolean status=false;
//String query="";
//int rowsAffected=0;
//
//
//int vehicle_id=getVehicleId(bean.getVehicleCode());
//int point_id=getPointId(bean.getPoint_name());
//
//      int shift_vehicle_detail_id=bean.getShiftVehicleDetailId();
//
//
//
//      if(shift_vehicle_detail_id==0)
//         query="insert into shift_vehicle_detail(shift_key_person_map_id1,shift_key_person_map_id2,vehicle_id,point_id,status,date_time,latitude,longitude,starting_time,ending_time) "
//                 +"values(?,?,?,?,?,?,?,?,?,?)";
////      if(shift_vehicle_detail_id>0)
////         query=" update vehicle_key_person set vehicle_id=?,key_person_id=? where vehicle_key_person_id=?";
//
//
//        try{
//         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
//         int shift_key_person_map_id1=getKeyPersonId(bean.getGivenBy());
//           ps.setInt(1, shift_key_person_map_id1);
//           int shift_key_person_map_id2=getKeyPersonId(bean.getTakenBy());
//           ps.setInt(2, shift_key_person_map_id2);
//           ps.setInt(3, vehicle_id);
//           ps.setInt(4, point_id);
//           ps.setString(5,bean.getStatus());
//          // String date=bean.getDate();
//           String date=getSplit(bean.getDate());
//           ps.setString(6,date);
//           ps.setDouble(7,bean.getLatitude());
//           ps.setDouble(8,bean.getLongitude());
//           ps.setString(9, bean.getStarting_time());
//           ps.setString(10,bean.getEnding_time());
//         rowsAffected = ps.executeUpdate();
//        if(rowsAffected > 0)
//        status=true;
//        }
//        catch(Exception e){
//    System.out.println("ERROR: " + e);
//        }
//       if (rowsAffected > 0) {
//             message = "Record Inserted successfully......";
//            msgBgColor = COLOR_OK;
//            System.out.println("Inserted");
//        } else {
//             message = "Record Not Inserted Some Error!";
//            msgBgColor = COLOR_ERROR;
//            System.out.println("not Inserted");
//        }
//return status;
//}
//public String getSplit(String str){
//        String[] words=str.split("-");
//        String mystr="";
//        for(int i=(words.length)-1;i>=0;i--){
//            mystr=mystr+words[i];
//            if(i>0)
//                mystr=mystr+"-";
//        }
//        return mystr;
//    }
//
//public boolean reviseRecords(ShiftVehicleDetail bean){
//
//    boolean status=false;
//    String query="";
//    int rowsAffected=0;
//
//    int vehicle_id=getVehicleId(bean.getVehicleCode());
//    int point_id=getPointId(bean.getPoint_name());
//
//      int shift_vehicle_detail_id=bean.getShiftVehicleDetailId();
//
//
//      //int vehicle_key_person_id=bean.getShiftVehicleDetailId();
//
//
//      String query1 = "SELECT max(rev_no) rev_no FROM shift_vehicle_detail WHERE shift_vehicle_detail_id = "+shift_vehicle_detail_id+" && active='Y' ORDER BY rev_no DESC";
//      String query2 = " UPDATE shift_vehicle_detail SET active=? WHERE shift_vehicle_detail_id = ? && rev_no = ? ";
//      String query3 = "INSERT INTO shift_vehicle_detail (shift_vehicle_detail_id,shift_key_person_map_id1,shift_key_person_map_id2,vehicle_id,point_id,status,date_time,latitude,longitude,starting_time,ending_time,rev_no, active) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
//      int updateRowsAffected = 0;
//      try {
//           PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
//           ResultSet rs = ps.executeQuery();
//           if(rs.next()){
//           PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
//           pst.setString(1,  "N");
//           pst.setInt(2,shift_vehicle_detail_id);
//           pst.setInt(3, rs.getInt("rev_no"));
//           updateRowsAffected = pst.executeUpdate();
//             if(updateRowsAffected >= 1){
//             int rev = rs.getInt("rev_no")+1;
//             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
//             psmt.setInt(1,shift_vehicle_detail_id);
//             int shift_key_person_map_id1=getKeyPersonId(bean.getGivenBy());
//            ps.setInt(2, shift_key_person_map_id1);
//           int shift_key_person_map_id2=getKeyPersonId(bean.getTakenBy());
//           ps.setInt(3, shift_key_person_map_id2);
//           ps.setInt(4, vehicle_id);
//           ps.setInt(5, point_id);
//           ps.setString(5,bean.getStatus());
//          // String date=bean.getDate();
//           String date=getSplit(bean.getDate());
//           ps.setString(6,date);
//           ps.setDouble(7,bean.getLatitude());
//           ps.setDouble(8,bean.getLongitude());
//            ps.setString(9, bean.getStarting_time());
//           ps.setString(10,bean.getEnding_time());
//
//             psmt.setInt(11, rev);
//             psmt.setString(12, "Y");
//             int a = psmt.executeUpdate();
//              if(a > 0)
//              status=true;
//             }
//           }
//          } catch (Exception e)
//             {
//              System.out.println("CityDAOClass reviseRecord() Error: " + e);
//             }
//      if (updateRowsAffected > 0) {
//             message = "Record Inserted successfully......";
//            msgBgColor = COLOR_OK;
//            System.out.println("Inserted");
//        } else {
//             message = "Record Not Inserted Some Error!";
//            msgBgColor = COLOR_ERROR;
//            System.out.println("not Inserted");
//        }
//
//       return status;
//
//    }
//
//
//public  boolean deleteRecord(int vehicle_key_person_id){
//    boolean status=false;
//   int rowsAffected=0;
//   String query = "update shift_vehicle_detail SET active='N' where shift_vehicle_detail_id=?";
//try{
//    PreparedStatement pst = connection.prepareStatement(query);
//    pst.setInt(1,vehicle_key_person_id);
//    rowsAffected= pst.executeUpdate();
// if(rowsAffected > 0)
//    status=true;
//else status=false;
//}catch(Exception e){
//    System.out.println("ERROR: " + e);
//}
// if (rowsAffected > 0) {
//             message = "Record Deleted successfully......";
//             msgBgColor = COLOR_OK;
//             System.out.println("Deleted");
//        } else {
//             message = "Record Not Deleted Some Error!";
//            msgBgColor = COLOR_ERROR;
//            System.out.println("not Deleted");}
//return status;
//}
public static String getGeneral_image_detail_id(int idVal)    {

   String query ="select image_path from vehicle_weight where vehicle_weight_id="+idVal;
   String path="";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();

            while(rset.next()){

                path=rset.getString("image_path");

            }

        } catch (Exception e) {
            System.out.println( e);
        }
        return path;
    }

//public static List<String> getKey_person_name(String q)    {
//     List<String> list=new ArrayList<String>();
//   String query ="select key_person_name from key_person";
//      try {
//                    ResultSet rset = connection.prepareStatement(query).executeQuery();
//                    int count = 0;
//                    q = q.trim();
//                    while (rset.next()) {
//                     String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
//                       if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
//                     list.add(key_person_name);
//                     count++;
//                        }
//            }
//            if (count == 0) {
//                list.add("No such Status exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println( e);
//        }
//        return list;
//    }
//
//
public static List<String> getSearchVehicleCode(String q)    {
     List<String> list=new ArrayList<String>();
   String query =" select vehicle_code from vehicle v,vehicle_weight vw "
                 +" where vw.vehicle_id=v.vehicle_id ";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String vehicle_code =rset.getString("vehicle_code");
                       if(vehicle_code.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(vehicle_code);
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
public static List<String> getSearchVehicleNumber(String q)    {
     List<String> list=new ArrayList<String>();
   String query =" select vehicle_no from vehicle v,vehicle_weight vw "
                 +" where vw.vehicle_id=v.vehicle_id ";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String vehicle_no =rset.getString("vehicle_no");
                       if(vehicle_no.toUpperCase().startsWith(q.toUpperCase())){
                     list.add(vehicle_no);
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
//public static List<String> getSearchKeyPersonNameTakenBy(String q)    {
//     List<String> list=new ArrayList<String>();
//   String query ="select key_person_name "
//                  +" from key_person kp,shift_key_person_map skpm,shift_vehicle_detail svd "
//                  +" where kp.key_person_id=skpm.key_person_id "
//                   +" and skpm.shift_key_person_map_id=svd.shift_key_person_map_id2 "
//                   +" and svd.active='Y' ";
//      try {
//                    ResultSet rset = connection.prepareStatement(query).executeQuery();
//                    int count = 0;
//                    q = q.trim();
//                    while (rset.next()) {
////                     String vehicle_code = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
//                        String vehicle_code = rset.getString("key_person_name");
//                       if(vehicle_code.toUpperCase().startsWith(q.toUpperCase())){
//                     list.add(vehicle_code);
//                     count++;
//                        }
//            }
//            if (count == 0) {
//                list.add("No such Status exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println( e);
//        }
//        return list;
//    }
//
//
//
//
//
//
//    public static List<String> getVehicle_code(String q)    {
//     List<String> list=new ArrayList<String>();
//   String query ="select vehicle_code from vehicle";
//      try {
//                    ResultSet rset = connection.prepareStatement(query).executeQuery();
//                    int count = 0;
//                    q = q.trim();
//                    while (rset.next()) {
//                     int vehicle_code =rset.getInt("vehicle_code");
//                       //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
//                     list.add(Integer.toString(vehicle_code));
//                     count++;
//                        //}
//            }
//            if (count == 0) {
//                list.add("No such Status exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println( e);
//        }
//        return list;
//    }
//    public List<String> getZone(String q,String vehicle_code) {
//        List<String> list = new ArrayList<String>();
//
//         String query = "select vehicle_no from vehicle where vehicle_code="+vehicle_code;
//
//        try {
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            int count = 0;
//            while (rset.next()) {
//              String   remark = rset.getString("vehicle_no").trim();
//                    list.add(remark);
//                    count++;
//            }
//        } catch (Exception e) {
//            System.out.println("Error:AdvocateTypeModel--getAdvocateName()-- " + e);
//        }
//        return list;
//    }
//
//
//    public static List<String> getVehicle_number(String q)    {
//     List<String> list=new ArrayList<String>();
//   String query ="select vehicle_no from vehicle";
//      try {
//                    ResultSet rset = connection.prepareStatement(query).executeQuery();
//                    int count = 0;
//                    q = q.trim();
//                    while (rset.next()) {
//                     String vehicle_no =rset.getString("vehicle_no");
//                       //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
//                     list.add(vehicle_no);
//                     count++;
//                        //}
//            }
//            if (count == 0) {
//                list.add("No such Status exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println( e);
//        }
//        return list;
//    }
//
//
//     public static List<String> getPoint(String q)    {
//     List<String> list=new ArrayList<String>();
//   String query ="select point_name from point";
//      try {
//                    ResultSet rset = connection.prepareStatement(query).executeQuery();
//                    int count = 0;
//                    q = q.trim();
//                    while (rset.next()) {
//                     String point_name =rset.getString("point_name");
//                       //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
//                     list.add(point_name);
//                     count++;
//                        //}
//            }
//            if (count == 0) {
//                list.add("No such Status exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println( e);
//        }
//        return list;
//    }
//
//public static List<String> getKeyPersonGivenBy(String q)    {
//     List<String> list=new ArrayList<String>();
//   String query ="select key_person_name from key_person kp,shift_key_person_map skpm "
//                   +" where kp.key_person_id=skpm.key_person_id "
//                    +" group by key_person_name";
//      try {
//                    ResultSet rset = connection.prepareStatement(query).executeQuery();
//                    int count = 0;
//                    q = q.trim();
//                    while (rset.next()) {
//                     String point_name =rset.getString("key_person_name");
//                       //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
//                     list.add(point_name);
//                     count++;
//                        //}
//            }
//            if (count == 0) {
//                list.add("No such Status exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println( e);
//        }
//        return list;
//    }
//
//public static List<String> getKeyPersonTakenBy(String q)    {
//     List<String> list=new ArrayList<String>();
//   String query ="select key_person_name from key_person kp,shift_key_person_map skpm "
//                   +" where kp.key_person_id=skpm.key_person_id "
//                    +" group by key_person_name";
//;
//      try {
//                    ResultSet rset = connection.prepareStatement(query).executeQuery();
//                    int count = 0;
//                    q = q.trim();
//                    while (rset.next()) {
//                     String point_name =rset.getString("key_person_name");
//                       //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
//                     list.add(point_name);
//                     count++;
//                        //}
//            }
//            if (count == 0) {
//                list.add("No such Status exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println( e);
//        }
//        return list;
//    }
//
//
//
//
// public int getVehicleId(String vehicle_code){
//     int vehicle_id=0;
//     String query="select vehicle_id from vehicle where vehicle_code= "+vehicle_code;
//     try{
//     PreparedStatement pst = connection.prepareStatement(query);
//     ResultSet rs=pst.executeQuery();
//     while(rs.next()){
//         vehicle_id=rs.getInt("vehicle_id");
//     }
//     }catch(Exception e){
//         System.out.println(e);
//     }
//     return vehicle_id;
// }
// public int getKeyPersonId(String key_person){
//     key_person = krutiToUnicode.convert_to_unicode(key_person);
//
//     int shift_key_person_map_id=0;
//     String query="select shift_key_person_map_id from key_person kp,shift_key_person_map skp "
//                     +" where kp.key_person_id=skp.key_person_id "
//                     +" and kp.key_person_name='"+key_person+"'";
//     try{
//     PreparedStatement pst = connection.prepareStatement(query);
//     ResultSet rs=pst.executeQuery();
//     while(rs.next()){
//         shift_key_person_map_id=rs.getInt("shift_key_person_map_id");
//     }
//     }catch(Exception e){
//         System.out.println(e);
//     }
//     return shift_key_person_map_id;
// }
//
//public int getPointId(String key_person){
//    // key_person = krutiToUnicode.convert_to_unicode(key_person);
//
//     int key_person_id=0;
//     String query="select point_id from point where point_name= '"+key_person+"'";
//     try{
//     PreparedStatement pst = connection.prepareStatement(query);
//     ResultSet rs=pst.executeQuery();
//     while(rs.next()){
//         key_person_id=rs.getInt("point_id");
//     }
//     }catch(Exception e){
//         System.out.println(e);
//     }
//     return key_person_id;
// }
public int getVehicleId(String vehicle_code){
    int vehicle_id=0;
    String query="select vehicle_id from vehicle where vehicle_code= "+vehicle_code;
    try{
    PreparedStatement pst = connection.prepareStatement(query);
    ResultSet rs=pst.executeQuery();
    while(rs.next()){
        vehicle_id=rs.getInt("vehicle_id");
    }
    }catch(Exception e){
        System.out.println(e);
    }
    return vehicle_id;
}

public int saveVehicleWeightRecord_webService(int vehicle_id,String weight,String path){

    boolean status=false;
    String query="";
    int rowsAffected=0;

        query="insert into vehicle_weight(weight,date_time,image_path) "
                +" values(?,?,?) ";
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       Date date = new Date();
      System.out.println((dateFormat.format(date)));
      String date1=dateFormat.format(date);
      String arrDate[]=date1.split(" ");

       String date2=arrDate[0];
       String time=arrDate[1];

       try{
        PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
       
        ps.setDouble(1, Double.parseDouble(weight));
        ps.setString(2,date1);
        ps.setString(3,path);
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
    return rowsAffected;
}

public boolean makeDirectory(String dirPathName) {
       boolean result = false;
       System.out.println("dirPathName---" + dirPathName);
       //dirPathName = "C:/ssadvt/sor/organisation" ;
       File directory = new File(dirPathName);
       if (!directory.exists()) {
           try {
               result = directory.mkdirs();
           } catch (Exception e) {
               System.out.println("Error - " + e);
           }
       }
       return result;
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
