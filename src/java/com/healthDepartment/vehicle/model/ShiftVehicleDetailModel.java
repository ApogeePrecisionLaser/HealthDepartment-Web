/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.vehicle.model;

import com.healthDepartment.general.model.GeneralModel;
import com.healthDepartment.shift.model.ShiftLoginModel;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicle.tableClasses.ShiftVehicleDetail;
import com.healthDepartment.vehicle.tableClasses.VehicleKeyPerson;
import com.healthDepartment.vehicle.tableClasses.VehicleType;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Com7_2
 */
public class ShiftVehicleDetailModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public int getNoOfRows(String search_given_by, String search_taken_by, String searchdate) {
      //  search_given_by = krutiToUnicode.convert_to_unicode(search_given_by);
      //  search_taken_by = krutiToUnicode.convert_to_unicode(search_taken_by);
        if (!searchdate.isEmpty()) {
            try {
                searchdate = new GeneralModel().convertToSqlDate(searchdate).toString();
            } catch (ParseException ex) {
            }
        }
        int noOfRows = 0;
        try {
            String query = " select count(*) from (Select count(svd.shift_vehicle_detail_id)  from shift_vehicle_detail as svd "
                    + " left join shift_key_person_map as skpm1 on skpm1.shift_key_person_map_id=svd.shift_key_person_map_id1 "
                    + " left join shift_key_person_map as skpm2 on skpm2.shift_key_person_map_id=svd.shift_key_person_map_id2 "
                    + " left join shift_key_person_map as skpm on shift_key_person_map_id1 and svd.shift_key_person_map_id2=skpm.shift_key_person_map_id "
                    + " left join shift_designation_location_map as sdlm on sdlm.map_id1=skpm.map_id1   and  sdlm.map_id2=skpm.map_id2 "
                    + " left join shift_type as st on st.shift_type_id=sdlm.shift_type_id "
                    + " left join designation_location_type as dlt on sdlm.designation_location_type_id=dlt.designation_location_type_id "
                    + " left join location_type as lt on dlt.location_type_id=lt.location_type_id "
                    + " left join designation as d on dlt.designation_id=d.designation_id "
                    + " left join zone as z on dlt.zone_id=z.zone_id "
                    + " left join ward as w on dlt.ward_id=w.ward_id "
                    + " left join area as a on dlt.area_id=a.area_id "
                    + " left join city_location as cl on dlt.city_location_id=cl.city_location_id "
                    + " left join key_person as kp1 on skpm1.key_person_id=kp1.key_person_id "
                    + " left join key_person as kp2 on skpm2.key_person_id=kp2.key_person_id "
                    + " left join vehicle as v on v.vehicle_id=svd.vehicle_id "
                    + " left join point as p on p.point_id=svd.point_id "
                    + " where skpm.active='y'  "
                    + " AND IF('" + searchdate + "'='', svd.date_time LIKE '%%', DATE_FORMAT(svd.date_time,'%Y-%m-%d') LIKE'" + searchdate + "%')"
                    + " AND IF('" + search_given_by + "' = '', kp1.key_person_name LIKE '%%',kp1.key_person_name='" + search_given_by + "') "
                    + " And IF('" + search_taken_by + "' = '',kp2.key_person_name LIKE '%%', kp2.key_person_name='" + search_taken_by + "') group by shift_vehicle_detail_id ) as a";

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

    public List<ShiftVehicleDetail> showData(int lowerLimit, int noOfRowsToDisplay, String search_given_by, String search_taken_by, String searchdate) {
        //vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
     //   search_given_by = krutiToUnicode.convert_to_unicode(search_given_by);
     //   search_taken_by = krutiToUnicode.convert_to_unicode(search_taken_by);
        List list = new ArrayList();
        if (!searchdate.isEmpty()) {
            try {
                searchdate = new GeneralModel().convertToSqlDate(searchdate).toString();
            } catch (ParseException ex) {
            }
        }
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query = "Select svd.shift_vehicle_detail_id,svd.status,svd.latitude,svd.longitude,skpm.shift_key_person_map_id,st.shift_type,d.designation,lt.location_type_name, "
                + " z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_name,a.area_no,cl.location,cl.location_no,kp1.key_person_name as k1,kp2.key_person_name as k2 "
                + " ,svd.date_time,skpm.remark,vehicle_code,vehicle_no,p.point_name  from shift_vehicle_detail as svd "
                + " left join shift_key_person_map as skpm1 on skpm1.shift_key_person_map_id=svd.shift_key_person_map_id1 "
                + " left join shift_key_person_map as skpm2 on skpm2.shift_key_person_map_id=svd.shift_key_person_map_id2 "
                + " left join shift_key_person_map as skpm on shift_key_person_map_id1 and svd.shift_key_person_map_id2=skpm.shift_key_person_map_id "
                + " left join shift_designation_location_map as sdlm on sdlm.map_id1=skpm.map_id1   and  sdlm.map_id2=skpm.map_id2 "
                + " left join shift_type as st on st.shift_type_id=sdlm.shift_type_id "
                + " left join designation_location_type as dlt on sdlm.designation_location_type_id=dlt.designation_location_type_id "
                + " left join location_type as lt on dlt.location_type_id=lt.location_type_id "
                + " left join designation as d on dlt.designation_id=d.designation_id "
                + " left join zone as z on dlt.zone_id=z.zone_id "
                + " left join ward as w on dlt.ward_id=w.ward_id "
                + " left join area as a on dlt.area_id=a.area_id "
                + " left join city_location as cl on dlt.city_location_id=cl.city_location_id "
                + " left join key_person as kp1 on skpm1.key_person_id=kp1.key_person_id "
                + " left join key_person as kp2 on skpm2.key_person_id=kp2.key_person_id "
                + " left join vehicle as v on v.vehicle_id=svd.vehicle_id "
                + " left join point as p on p.point_id=svd.point_id "
                + " where skpm.active='y'  "
                //+ " and d.designation='' "
                + " AND IF('" + searchdate + "'='', svd.date_time LIKE '%%', DATE_FORMAT(svd.date_time,'%Y-%m-%d') LIKE'" + searchdate + "%')"
                + " AND IF('" + search_given_by + "' = '', kp1.key_person_name LIKE '%%',kp1.key_person_name='" + search_given_by + "') "
                + " And IF('" + search_taken_by + "' = '',kp2.key_person_name LIKE '%%', kp2.key_person_name='" + search_taken_by + "') group by shift_vehicle_detail_id"
                + addQuery;



        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ShiftVehicleDetail vt = new ShiftVehicleDetail();
                vt.setShiftVehicleDetailId(rs.getInt("shift_vehicle_detail_id"));
                vt.setGivenBy(rs.getString("k1"));
                vt.setTakenBy(rs.getString("k2"));
                vt.setVehicleCode(rs.getString("vehicle_code"));
                vt.setVehicleNumber(rs.getString("vehicle_no"));
                vt.setPoint_name(rs.getString("point_name"));
                vt.setDate(rs.getString("date_time"));
                vt.setStatus(rs.getString("status"));
                vt.setLatitude(rs.getDouble("latitude"));
                vt.setLongitude(rs.getDouble("longitude"));
                list.add(vt);
            }
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return list;
    }

    public boolean insertRecord(ShiftVehicleDetail bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;


        int vehicle_id = getVehicleId(bean.getVehicleCode());
        int point_id = getPointId(bean.getPoint_name());
        query = "insert into shift_vehicle_detail(shift_key_person_map_id1,shift_key_person_map_id2,vehicle_id,point_id,status,date_time,latitude,longitude) "
                + "values(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            int shift_key_person_map_id1 = getKeyPersonId(bean.getGivenBy());
            ps.setInt(1, shift_key_person_map_id1);
            int shift_key_person_map_id2 = getKeyPersonId(bean.getTakenBy());
            ps.setInt(2, shift_key_person_map_id2);
            ps.setInt(3, vehicle_id);
            ps.setInt(4, point_id);
            ps.setString(5, bean.getStatus());
            String date = getSplit(bean.getDate());
            ps.setString(6, date);
            ps.setDouble(7, bean.getLatitude());
            ps.setDouble(8, bean.getLongitude());
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

    public boolean reviseRecords(ShiftVehicleDetail bean) {

        boolean status = false;
        String query = "";
        int rowsAffected = 0;

        int vehicle_id = getVehicleId(bean.getVehicleCode());
        int point_id = getPointId(bean.getPoint_name());

        int shift_vehicle_detail_id = bean.getShiftVehicleDetailId();


        //int vehicle_key_person_id=bean.getShiftVehicleDetailId();


        String query1 = "SELECT max(rev_no) rev_no FROM shift_vehicle_detail WHERE shift_vehicle_detail_id = " + shift_vehicle_detail_id + " && active='Y' ORDER BY rev_no DESC";
        String query2 = " UPDATE shift_vehicle_detail SET active=? WHERE shift_vehicle_detail_id = ? && rev_no = ? ";
        String query3 = "INSERT INTO shift_vehicle_detail (shift_vehicle_detail_id,shift_key_person_map_id1,shift_key_person_map_id2,vehicle_id,point_id,status,date_time,latitude,longitude,rev_no, active) VALUES (?,?,?,?,?,?,?,?,?,?,?) ";
        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, shift_vehicle_detail_id);
                pst.setInt(3, rs.getInt("rev_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("rev_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, shift_vehicle_detail_id);
                    int shift_key_person_map_id1 = getKeyPersonId(bean.getGivenBy());
                    ps.setInt(2, shift_key_person_map_id1);
                    int shift_key_person_map_id2 = getKeyPersonId(bean.getTakenBy());
                    ps.setInt(3, shift_key_person_map_id2);
                    ps.setInt(4, vehicle_id);
                    ps.setInt(5, point_id);
                    ps.setString(5, bean.getStatus());
                    // String date=bean.getDate();
                    String date = getSplit(bean.getDate());
                    ps.setString(6, date);
                    ps.setDouble(7, bean.getLatitude());
                    ps.setDouble(8, bean.getLongitude());

                    psmt.setInt(9, rev);
                    psmt.setString(10, "Y");
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

    public boolean deleteRecord(int vehicle_key_person_id) {
        boolean status = false;
        int rowsAffected = 0;
        String query = "update shift_vehicle_detail SET active='N' where shift_vehicle_detail_id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, vehicle_key_person_id);
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

    public static List<String> getKey_person_name(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select key_person_name from key_person";
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

    public static List<String> getSearchKeyPersonNameGivenBy(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select key_person_name "
                + " from key_person kp,shift_key_person_map skpm,shift_vehicle_detail svd "
                + " where kp.key_person_id=skpm.key_person_id "
                + " and skpm.shift_key_person_map_id=svd.shift_key_person_map_id1 "
                + " and svd.active='Y' ";
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

    public static List<String> getSearchKeyPersonNameTakenBy(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select key_person_name "
                + " from key_person kp,shift_key_person_map skpm,shift_vehicle_detail svd "
                + " where kp.key_person_id=skpm.key_person_id "
                + " and skpm.shift_key_person_map_id=svd.shift_key_person_map_id2 "
                + " and svd.active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
//                     String vehicle_code = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
                String vehicle_code = rset.getString("key_person_name");
                if (vehicle_code.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(vehicle_code);
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

    public static List<String> getVehicle_code(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select vehicle_code from vehicle";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                int vehicle_code = rset.getInt("vehicle_code");
                //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                list.add(Integer.toString(vehicle_code));
                count++;
                //}
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<String> getZone(String q, String vehicle_code) {
        List<String> list = new ArrayList<String>();

        String query = "select vehicle_no from vehicle where vehicle_code=" + vehicle_code;

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            while (rset.next()) {
                String remark = rset.getString("vehicle_no").trim();
                list.add(remark);
                count++;
            }
        } catch (Exception e) {
            System.out.println("Error:AdvocateTypeModel--getAdvocateName()-- " + e);
        }
        return list;
    }

    public static List<String> getVehicle_number(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select vehicle_no from vehicle";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String vehicle_no = rset.getString("vehicle_no");
                //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                list.add(vehicle_no);
                count++;
                //}
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static List<String> getPoint(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select point_name from point";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String point_name = rset.getString("point_name");
                //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                list.add(point_name);
                count++;
                //}
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static List<String> getKeyPersonGivenBy(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select key_person_name from key_person kp,shift_key_person_map skpm "
                + " where kp.key_person_id=skpm.key_person_id "
                + " group by key_person_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String point_name = rset.getString("key_person_name");
                //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                list.add(point_name);
                count++;
                //}
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static List<String> getKeyPersonTakenBy(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select key_person_name from key_person kp,shift_key_person_map skpm "
                + " where kp.key_person_id=skpm.key_person_id "
                + " group by key_person_name";
        ;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String point_name = rset.getString("key_person_name");
                //if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                list.add(point_name);
                count++;
                //}
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public int getVehicleId(String vehicle_code) {
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

    public int getKeyPersonId(String key_person) {
       // key_person = krutiToUnicode.convert_to_unicode(key_person);

        int shift_key_person_map_id = 0;
        String query = "select shift_key_person_map_id from key_person kp,shift_key_person_map skp "
                + " where kp.key_person_id=skp.key_person_id "
                + " and kp.key_person_name='" + key_person + "'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                shift_key_person_map_id = rs.getInt("shift_key_person_map_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return shift_key_person_map_id;
    }

    public int getPointId(String key_person) {
        // key_person = krutiToUnicode.convert_to_unicode(key_person);

        int key_person_id = 0;
        String query = "select point_id from point where point_name= '" + key_person + "'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                key_person_id = rs.getInt("point_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return key_person_id;
    }

    public String getPointLatLong(String shiftVehicleDetailId) {
        String lat = "";
        String longi = "";
        String query = " select latitude,longitude "
                + " from shift_vehicle_detail svd "
                + " where shift_vehicle_detail_id=? ";

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, shiftVehicleDetailId);
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
