/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.vehicle.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicle.tableClasses.VehicleKeyPersonPoint;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Com7_2
 */
public class VehicleKeyPersonPointModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public int getNoOfRows(String search_point_name, String search_key_person_name) {
    //    search_point_name = krutiToUnicode.convert_to_unicode(search_point_name);
   //     search_key_person_name = krutiToUnicode.convert_to_unicode(search_key_person_name);
        int noOfRows = 0;
        try {
            //String query="SELECT count(vehicle_key_person_point_id) from vehicle_key_person_point ";
            //+ " where  IF('" + vehicleType + "' = '', vehicle_name LIKE '%%',vehicle_name  ='" + vehicleType + "') " ;

            String query = "SELECT  count(*) "
                    + " from vehicle_key_person_point vkpp,key_person kp,vehicle_key_person vkp,shift_key_person_map as skpm,point p"
                    + "  where vkpp.vehicle_key_person_id=vkp.vehicle_key_person_id  and vkp.shift_key_person_map_id=skpm.shift_key_person_map_id and"
                    + "  skpm.key_person_id=kp.key_person_id"
                    + "   and vkpp.point_id=p.point_id AND vkpp.active='Y' AND vkp.active='Y'"
                    + " AND IF('" + search_point_name + "' = '', point_name LIKE '%%',point_name='" + search_point_name + "') "
                    + " And IF('" + search_key_person_name + "' = '',kp.key_person_name LIKE '%%', kp.key_person_name='" + search_key_person_name + "')";


            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public List<VehicleKeyPersonPoint> showDataBean(String search_key_person_name, String search_point_name) {
        List<VehicleKeyPersonPoint> list = new ArrayList<VehicleKeyPersonPoint>();


        String query = "SELECT  p.point_name,p.latitude,p.longitude "
                + " from vehicle_key_person_point vkpp,key_person kp,vehicle_key_person vkp,shift_key_person_map as skpm,point p"
                + "  where vkpp.vehicle_key_person_id=vkp.vehicle_key_person_id  and vkp.shift_key_person_map_id=skpm.shift_key_person_map_id and"
                + "  skpm.key_person_id=kp.key_person_id"
                + "   and vkpp.point_id=p.point_id AND vkpp.active='Y' AND vkp.active='Y'"
                + " AND IF('" + search_point_name + "' = '', point_name LIKE '%%',point_name='" + search_point_name + "') "
                + " And IF('" + search_key_person_name + "' = '',key_person_name LIKE '%%', key_person_name='" + search_key_person_name + "')";


        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                VehicleKeyPersonPoint bean = new VehicleKeyPersonPoint();
                bean.setLatitude(rset.getString("latitude"));
                bean.setLongitude(rset.getString("longitude"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return list;
    }

    public List<VehicleKeyPersonPoint> showData(int lowerLimit, int noOfRowsToDisplay, String search_point_name, String search_key_person_name) {
      //  search_point_name = krutiToUnicode.convert_to_unicode(search_point_name);
     //   search_key_person_name = krutiToUnicode.convert_to_unicode(search_key_person_name);
        List list = new ArrayList();

        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query = "SELECT  vkpp.vehicle_key_person_point_id,kp.key_person_name,p.point_name,vehicle_code,vkpp.date,vkpp.time"
                + " from vehicle_key_person_point vkpp,key_person kp,vehicle_key_person vkp,shift_key_person_map as skpm,point p,vehicle v"
                + "  where vkpp.vehicle_key_person_id=vkp.vehicle_key_person_id  and vkp.shift_key_person_map_id=skpm.shift_key_person_map_id and"
                + "  skpm.key_person_id=kp.key_person_id and vkp.vehicle_id=v.vehicle_id"
                + "   and vkpp.point_id=p.point_id AND vkpp.active='Y' AND vkp.active='Y'"
                + " AND IF('" + search_point_name + "' = '', point_name LIKE '%%',point_name='" + search_point_name + "') "
                + " And IF('" + search_key_person_name + "' = '',key_person_name LIKE '%%', key_person_name='" + search_key_person_name + "')"
                + addQuery;

//         String query = "select vehicle_key_person_id, from city_info"
//                +" WHERE IF('"+ searchcity +"' = '', city_name LIKE '%%',city_name='"+searchcity+"') "
//                +" AND IF('"+ active +"' ='', active LIKE '%%',active='"+active+"') "
//                +addQuery;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VehicleKeyPersonPoint vt = new VehicleKeyPersonPoint();
                vt.setVehicle_key_person_point_id(rs.getInt("vehicle_key_person_point_id"));
                vt.setKey_person_name(rs.getString("key_person_name"));
                vt.setPoint_name(rs.getString("point_name"));
                vt.setVehicle_code(rs.getString("vehicle_code"));
                 String date = getSplit(rs.getString("date"));
                vt.setDate(date);
                vt.setTime(rs.getString("time"));
                list.add(vt);
            }
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return list;
    }

    public boolean insertRecord(VehicleKeyPersonPoint bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int vehicle_key_person_point_id = bean.getVehicle_key_person_point_id();
        if (vehicle_key_person_point_id == 0) {
            query = "insert into vehicle_key_person_point(vehicle_key_person_id,point_id,date,time) values(?,?,?,?)";
        }
//      if(vehicle_key_person_point_id>0)
//         query=" update vehicle_key_person set vehicle_id=?,key_person_id=? where vehicle_key_person_id=?";


        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1, bean.getVehicle_key_person_id());
            ps.setInt(2, bean.getPoint_id());
            String date = getSplit(bean.getDate());
            ps.setString(3, date);
            ps.setString(4, bean.getTime());
//         if(vehicle_key_person_point_id>0)
//           ps.setInt(3,vehicle_key_person_point_id);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            }
        } catch (Exception e) {
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

    public String getSplit(String str) {
        String[] words = str.split("-");
        String mystr = "";
        for (int i = (words.length) - 1; i >= 0; i--) {
            mystr = mystr + words[i];
            if (i > 0) {
                mystr = mystr + "-";
            }
        }
        return mystr;
    }

    public boolean reviseRecords(VehicleKeyPersonPoint bean) {

        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int vehicle_key_person_point_id = bean.getVehicle_key_person_point_id();
        int rev_no = 0;

        String query1 = "SELECT max(rev_no) rev_no FROM vehicle_key_person_point WHERE vehicle_key_person_point_id = " + vehicle_key_person_point_id + " && active='Y' ORDER BY rev_no DESC";
        String query2 = " UPDATE vehicle_key_person_point SET active=? WHERE vehicle_key_person_point_id = ? && rev_no = ? ";
        String query3 = "INSERT INTO vehicle_key_person_point (vehicle_key_person_point_id,vehicle_key_person_id,point_id, rev_no, active,date,time) VALUES (?,?,?,?,?,?,?) ";
        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rev_no = rs.getInt("rev_no");
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, vehicle_key_person_point_id);
                pst.setInt(3, rev_no);
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("rev_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, vehicle_key_person_point_id);
                    psmt.setInt(2, bean.getVehicle_key_person_id());
                    psmt.setInt(3, bean.getPoint_id());
                    psmt.setInt(4, rev);
                    psmt.setString(5, "Y");
                    String date = getSplit(bean.getDate());
                    psmt.setString(6, date);
                    psmt.setString(7, bean.getTime());
                    int a = psmt.executeUpdate();
                    if (a > 0) {
                        status = true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("CityDAOClass reviseRecord() Error: " + e);
        }
        if (updateRowsAffected > 0) {
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

    public boolean deleteRecord(int vehicle_key_person_point_id) {
        boolean status = false;
        int rowsAffected = 0;
        String query = "update vehicle_key_person_point SET active='N' where vehicle_key_person_point_id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, vehicle_key_person_point_id);
            rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            } else {
                status = false;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Deleted successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Deleted");
        } else {
            message = "Record Not Deleted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Deleted");
        }
        return status;
    }

//public static List<String> getSearchKeyPerson(String q)    {
//     List<String> list=new ArrayList<String>();
//   String query ="select key_person_name from key_person kp,vehicle_key_person vkp,vehicle_key_person_point vkpp "
//                  +" where vkpp.vehicle_key_person_id=vkp.vehicle_key_person_id "
//                  +"and vkp.key_person_id=kp.key_person_id "
//                   + "AND vkpp.active='Y' ";
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
    public static List<String> getSearchPointName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select point_name from point p,vehicle_key_person_point vkpp"
                + " where vkpp.point_id=p.point_id "
                + "AND vkpp.active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = rset.getString("point_name");
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<String> getKeyPerson(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select key_person_name from key_person kp,vehicle_key_person vkp,shift_key_person_map as skpm"
                + "  where vkp.shift_key_person_map_id=skpm.shift_key_person_map_id and skpm.key_person_id=kp.key_person_id"
                + "   AND vkp.active='Y' group by key_person_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = rset.getString("key_person_name");
                if (key_person_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(key_person_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static List<String> getPointName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select point_name from point";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String point_name = rset.getString("point_name");
                if (point_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(point_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public int getVehicleId(int vehicle_code) {
        int vehicle_id = 0;
        String query = "select vehicle_id from vehicle where vehicle_code= " + vehicle_code;
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                vehicle_id = rs.getInt("vehicle_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_id;
    }

    public int getVehicleKeyPersonId(String key_person) {
      //  key_person = krutiToUnicode.convert_to_unicode(key_person);

        int vehicle_key_person_id = 0;
        String query = "select vehicle_key_person_id from key_person kp,vehicle_key_person vkp,shift_key_person_map as skpm"
                + "  where vkp.shift_key_person_map_id=skpm.shift_key_person_map_id and skpm.key_person_id=kp.key_person_id"
                + " and key_person_name='" + key_person + "' ";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                vehicle_key_person_id = rs.getInt("vehicle_key_person_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return vehicle_key_person_id;
    }

    public int getPointId(String point_name) {
        //point_name = krutiToUnicode.convert_to_unicode(point_name);

        int point_id = 0;
        String query = "select point_id from point "
                + " where point_name='" + point_name + "'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                point_id = rs.getInt("point_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return point_id;
    }

    public String getPointLatLong(String vehicle__key_person_point_id) {
        String lat = "";
        String longi = "";
        String query = "select latitude,longitude from point p,vehicle_key_person_point vkpp "
                + " where vkpp.point_id=p.point_id "
                + " and vehicle_key_person_point_id=?";

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, vehicle__key_person_point_id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                lat = rs.getString("latitude");
                longi = rs.getString("longitude");


            }
        } catch (Exception e) {
            System.out.println(e);
        }


        return lat + "," + longi;
    }
public int getVehicleKeyPersonId2(int  vehicle_key_person_point_id){
     //key_person = krutiToUnicode.convert_to_unicode(key_person);

     int vehicle_key_person_id=0;
     String query="select vehicle_key_person_id "
                  +" from vehicle_key_person_point vkpp "
                  +" where vkpp.vehicle_key_person_point_id= "+vehicle_key_person_point_id;
     try{
     PreparedStatement pst = connection.prepareStatement(query);
     ResultSet rs=pst.executeQuery();
     while(rs.next()){
         vehicle_key_person_id=rs.getInt("vehicle_key_person_id");
     }
     }catch(Exception e){
         System.out.println(e);
     }
     return vehicle_key_person_id;
 }

public JSONArray  getAllLatLang(int vkp_id){
    // JSONObject jsonObj = new JSONObject();
     JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        String data="";
        List list = new ArrayList();
       // String query =  " select latitude,longitude from cordinate LIMIT 20 ";
        String query="select p.latitude,p.longitude "
                    +" from point p,vehicle_key_person_point vkpp "
                     +" where vkpp.point_id=p.point_id "
                     +" and vkpp.vehicle_key_person_id= "+vkp_id;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
               //General general = new General();
               JSONObject jsonObj = new JSONObject();
               jsonObj.put("latitude",rs.getFloat("latitude"));
               jsonObj.put("longitude",rs.getFloat("longitude"));
               arrayObj.add(jsonObj);
            }



        }catch(Exception e){
            System.out.println(e);
        }

       return arrayObj;
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
