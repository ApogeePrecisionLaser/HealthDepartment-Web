/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.mis.model;

import com.healthDepartment.general.model.GeneralModel;
import com.healthDepartment.mis.controller.WeightMISController;
import com.healthDepartment.mis.tableClasses.WeightMIS;
import com.healthDepartment.shift.model.ShiftLoginModel;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Shobha
 */
public class WeightMISModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public static int getNoOfRows(String search_given_by, String search_taken_by, String searchdate) {
        search_given_by = krutiToUnicode.convert_to_unicode(search_given_by);
        search_taken_by = krutiToUnicode.convert_to_unicode(search_taken_by);

        if (!searchdate.isEmpty()) {
            try {
                searchdate = new GeneralModel().convertToSqlDate(searchdate).toString();
            } catch (ParseException ex) {
                Logger.getLogger(ShiftLoginModel.class.getName()).log(Level.SEVERE, null, ex);
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

    public static List<WeightMIS> showData(int lowerLimit, int noOfRowsToDisplay, String search_given_by, String search_taken_by, String searchdate) {
        //vehicleType = krutiToUnicode.convert_to_unicode(vehicleType);
        search_given_by = krutiToUnicode.convert_to_unicode(search_given_by);
        search_taken_by = krutiToUnicode.convert_to_unicode(search_taken_by);

        if (!searchdate.isEmpty()) {
            try {
                searchdate = new GeneralModel().convertToSqlDate(searchdate).toString();
            } catch (ParseException ex) {
                Logger.getLogger(ShiftLoginModel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List list = new ArrayList();

        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
//            String query= "Select skpm.shift_key_person_map_id,st.shift_type,d.designation,lt.location_type_name, "
//+" z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_name,a.area_no,cl.location,cl.location_no,kp1.key_person_name as k1,kp2.key_person_name as k2 "
//+" ,skpm.date,skpm.remark,vehicle_code,vehicle_no,p.point_name  from shift_vehicle_detail as svd "
//+" left join shift_key_person_map as skpm1 on skpm1.shift_key_person_map_id=svd.shift_key_person_map_id1 "
//+" left join shift_key_person_map as skpm2 on skpm2.shift_key_person_map_id=svd.shift_key_person_map_id2 "
//+" left join shift_key_person_map as skpm on shift_key_person_map_id1 and svd.shift_key_person_map_id2=skpm.shift_key_person_map_id "
//+" left join shift_designation_location_map as sdlm on sdlm.map_id1=skpm.map_id1   and  sdlm.map_id2=skpm.map_id2 "
//+" left join shift_type as st on st.shift_type_id=sdlm.shift_type_id "
//+" left join designation_location_type as dlt on sdlm.designation_location_type_id=dlt.designation_location_type_id "
//+" left join location_type as lt on dlt.location_type_id=lt.location_type_id "
//+" left join designation as d on dlt.designation_id=d.designation_id "
//+" left join zone as z on dlt.zone_id=z.zone_id "
//+" left join ward as w on dlt.ward_id=w.ward_id "
//+" left join area as a on dlt.area_id=a.area_id "
//+" left join city_location as cl on dlt.city_location_id=cl.city_location_id "
//+" left join key_person as kp1 on skpm1.key_person_id=kp1.key_person_id "
//+" left join key_person as kp2 on skpm2.key_person_id=kp2.key_person_id "
//+" left join vehicle as v on v.vehicle_id=svd.vehicle_id "
//+" left join point as p on p.point_id=svd.point_id "
//+" where skpm.active='y' "
//+ " and d.designation='कतपअमत' "
//+ " AND IF('" + search_given_by + "' = '', shift_key_person_map_id1 LIKE '%%',shift_key_person_map_id1='" + search_given_by + "') "
//+ " And IF('" + search_taken_by + "' = '',shift_key_person_map_id2 LIKE '%%', shift_key_person_map_id2='" + search_taken_by + "')"
//+ addQuery;
           String query = "Select svd.shift_vehicle_detail_id,svd.status,svd.latitude,svd.longitude,skpm.shift_key_person_map_id,st.shift_type,d.designation,lt.location_type_name, "
                + " z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_name,a.area_no,cl.location,cl.location_no,kp1.key_person_name as k1,kp2.key_person_name as k2 "
                + " ,svd.date_time,skpm.remark,vehicle_code,vehicle_no,p.point_name,v.mobile_no  from shift_vehicle_detail as svd "
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


//svd.shift_vehicle_detail_id,

        // + " AND IF('" + vehicleType + "' = '', vehicle_code LIKE '%%',vehicle_code='" + vehicleType + "') "
        //+ " And IF('" + key_person_name + "' = '',key_person_name LIKE '%%', key_person_name='" + key_person_name + "') "


//                     + " where  IF('" + vehicle + "' = '', vehicle_name LIKE '%%',vehicle_name  ='" + vehicle + "') "
//                     +" AND  IF('" + vehicle + "' = '', vehicle_name LIKE '%%',vehicle_name  ='" + vehicle + "') "

//         String query = "select vehicle_key_person_id, from city_info"
//                +" WHERE IF('"+ searchcity +"' = '', city_name LIKE '%%',city_name='"+searchcity+"') "
//                +" AND IF('"+ active +"' ='', active LIKE '%%',active='"+active+"') "
//                +addQuery;
        double total = 0.0d;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WeightMIS vt = new WeightMIS();
                vt.setShiftVehicleDetailId(rs.getInt("shift_vehicle_detail_id"));
                vt.setGivenBy(rs.getString("k1"));
                vt.setTakenBy(rs.getString("k2"));
                vt.setVehicleCode(rs.getString("vehicle_code"));
                vt.setPoint_name(rs.getString("point_name"));
                vt.setDate(rs.getString("date_time"));
                vt.setStatus(rs.getString("status"));
                vt.setLatitude(rs.getDouble("latitude"));
                vt.setLongitude(rs.getDouble("longitude"));        
                vt.setWeight(Double.parseDouble(rs.getString("mobile_no")));
                total = total + Double.parseDouble(rs.getString("mobile_no"));
                list.add(vt);
            }
        } catch (Exception e) {
            System.out.println("error: " + e);
        }


        System.out.println("Total=" + total);
        WeightMISController.total = total;

        return list;
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



    public boolean insertRecord_webService(String emp_code_givenBy, String emp_code_takenBy, String vehicle_code) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;


        int vehicle_id = getVehicleId(vehicle_code);
        String point = "fghgjmhj";
        int point_id = getPointId(point);
        String status1 = "Full";
        //int shift_vehicle_detail_id=bean.getShiftVehicleDetailId();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        System.out.println((dateFormat.format(date)));
        String date1 = dateFormat.format(date);
        String arrDate[] = date1.split(" ");

        String date2 = arrDate[0];
        String time = arrDate[1];

        query = "insert into shift_vehicle_detail(shift_key_person_map_id1,shift_key_person_map_id2,vehicle_id,point_id,status,date_time,starting_time) "
                + "values(?,?,?,?,?,?,?)";


        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            int shift_key_person_map_id1 = getKeyPersonId_webService(emp_code_givenBy);
            ps.setInt(1, shift_key_person_map_id1);
            int shift_key_person_map_id2 = getKeyPersonId_webService(emp_code_takenBy);
            ps.setInt(2, shift_key_person_map_id2);
            ps.setInt(3, vehicle_id);
            ps.setInt(4, point_id);
            ps.setString(5, status1);
            // String date=bean.getDate();
            // String date3=getSplit_webService(date2);
            ps.setString(6, date2);
            //ps.setDouble(7,bean.getLatitude());
            //ps.setDouble(8,bean.getLongitude());
            ps.setString(7, time);
            //ps.setString(10,bean.getEnding_time());
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

//public String getSplit_webService(String str){
//             String[] words=str.split("/");
//        String mystr="";
//        for(int i=0;i<words.length;i++){
//            mystr=mystr+words[i];
//            if(i<words.length-1)
//                mystr=mystr+"-";
//        }
//        return mystr;
//
//    }
   

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
        key_person = krutiToUnicode.convert_to_unicode(key_person);

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

    public int getKeyPersonId_webService(String key_person_code) {
        //key_person = krutiToUnicode.convert_to_unicode(key_person);

        int shift_key_person_map_id = 0;
        String query = "select shift_key_person_map_id from key_person kp,shift_key_person_map skp "
                + " where kp.key_person_id=skp.key_person_id "
                + " and kp.emp_code=" + key_person_code;
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

    public static byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (Exception e) {
            System.out.println(" generatReport() JRException: " + e);
        }
        return reportInbytes;
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
