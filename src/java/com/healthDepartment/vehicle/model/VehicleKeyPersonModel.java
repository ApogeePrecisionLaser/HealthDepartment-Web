/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.vehicle.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicle.tableClasses.VehicleKeyPerson;
import com.healthDepartment.vehicle.tableClasses.VehicleType;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Com7_2
 */
public class VehicleKeyPersonModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public static int getNoOfRows(String vehicleType, String key_person_name) {
        vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
        key_person_name = krutiToUnicode.convert_to_unicode(key_person_name);
        int noOfRows = 0;
        try {
            String query = " SELECT  count(vehicle_key_person_id) "
                    + " from vehicle v,key_person kp,vehicle_key_person vkp,shift_key_person_map skpm  where "
                    + " vkp.vehicle_id=v.vehicle_id  AND skpm.key_person_id=kp.key_person_id "
                    + " and skpm.shift_key_person_map_id=vkp.shift_key_person_map_id and skpm.active='Y' "
                    + " AND vkp.active='Y' "
                    + " AND IF('" + vehicleType + "' = '', vehicle_code LIKE '%%',vehicle_code='" + vehicleType + "') "
                    + " And IF('" + key_person_name + "' = '',key_person_name LIKE '%%', key_person_name='" + key_person_name + "')";

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

    public static List<VehicleKeyPerson> showData(int lowerLimit, int noOfRowsToDisplay, String vehicleType, String key_person_name) {
        //vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
        vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
        key_person_name = krutiToUnicode.convert_to_unicode(key_person_name);
        List list = new ArrayList();

        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query = " SELECT  vehicle_key_person_id,key_person_name,vehicle_code,date,verify "
                + " from vehicle v,key_person kp,vehicle_key_person vkp,shift_key_person_map skpm  where "
                + " vkp.vehicle_id=v.vehicle_id  AND skpm.key_person_id=kp.key_person_id "
                + " and skpm.shift_key_person_map_id=vkp.shift_key_person_map_id and skpm.active='Y' "
                + " AND vkp.active='Y' "
                + " AND IF('" + vehicleType + "' = '', vehicle_code LIKE '%%',vehicle_code='" + vehicleType + "') "
                + " And IF('" + key_person_name + "' = '',key_person_name LIKE '%%', key_person_name='" + key_person_name + "') "
                + addQuery;

//                     + " where  IF('" + vehicle + "' = '', vehicle_name LIKE '%%',vehicle_name  ='" + vehicle + "') "
//                     +" AND  IF('" + vehicle + "' = '', vehicle_name LIKE '%%',vehicle_name  ='" + vehicle + "') "
//         String query = "select vehicle_key_person_id, from city_info"
//                +" WHERE IF('"+ searchcity +"' = '', city_name LIKE '%%',city_name='"+searchcity+"') "
//                +" AND IF('"+ active +"' ='', active LIKE '%%',active='"+active+"') "
//                +addQuery;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                VehicleKeyPerson vt = new VehicleKeyPerson();
                vt.setVehicle_key_person_id(rs.getInt("vehicle_key_person_id"));
                vt.setKey_person_name(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name")));
                vt.setVehicle_code(rs.getInt("vehicle_code"));
                vt.setDate(rs.getString("date"));
                vt.setVerify(rs.getString("verify"));
                list.add(vt);
            }
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return list;
    }

    public boolean insertRecord(VehicleKeyPerson bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int vehicle_key_person_id = bean.getVehicle_key_person_id();
        if (vehicle_key_person_id == 0) {
            query = "insert into vehicle_key_person(vehicle_id,shift_key_person_map_id) values(?,?)";
        }
        if (vehicle_key_person_id > 0) {
            query = " update vehicle_key_person set vehicle_id=?,shift_key_person_map_id=? where vehicle_key_person_id=?";
        }

        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ps.setInt(1, bean.getVehicle_id());
            ps.setInt(2, bean.getKey_person_id());
            if (vehicle_key_person_id > 0) {
                ps.setInt(3, vehicle_key_person_id);
            }
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

    public boolean reviseRecords(VehicleKeyPerson bean) {

        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int vehicle_key_person_id = bean.getVehicle_key_person_id();

        String query1 = "SELECT max(rev_no) rev_no FROM vehicle_key_person WHERE vehicle_key_person_id = " + vehicle_key_person_id + " && active='Y' ORDER BY rev_no DESC";
        String query2 = " UPDATE vehicle_key_person SET active=? WHERE vehicle_key_person_id = ? && rev_no = ? ";
        String query3 = "INSERT INTO vehicle_key_person (vehicle_key_person_id,vehicle_id,shift_key_person_map_id, rev_no, active) VALUES (?,?,?,?,?) ";
        int updateRowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query1);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
                pst.setString(1, "N");
                pst.setInt(2, vehicle_key_person_id);
                pst.setInt(3, rs.getInt("rev_no"));
                updateRowsAffected = pst.executeUpdate();
                if (updateRowsAffected >= 1) {
                    int rev = rs.getInt("rev_no") + 1;
                    PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
                    psmt.setInt(1, vehicle_key_person_id);
                    psmt.setInt(2, bean.getVehicle_id());
                    psmt.setInt(3, bean.getKey_person_id());

                    psmt.setInt(4, rev);
                    psmt.setString(5, "Y");
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
        String query = "update vehicle_key_person SET active='N' where vehicle_key_person_id=?";
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
                String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
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

    public static List<String> getSearchKeyPersonName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select key_person_name from key_person kp,vehicle_key_person vkp "
                + " where vkp.key_person_id=kp.key_person_id "
                + "and vkp.active='Y' ";
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
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public static List<String> getSearchVehicleCode(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select vehicle_code from vehicle v,vehicle_key_person vkp "
                + "where vkp.vehicle_id=v.vehicle_id "
                + "and vkp.active='Y' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String vehicle_code = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("vehicle_code"));
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

    public int getKeyPersonId(String key_person) {
        key_person = krutiToUnicode.convert_to_unicode(key_person);

        int key_person_id = 0;
        String query = "select shift_key_person_map_id  from key_person kp,shift_key_person_map skpm"
                + " where skpm.key_person_id=kp.key_person_id and  key_person_name= '" + key_person + "'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                key_person_id = rs.getInt("shift_key_person_map_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return key_person_id;
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
