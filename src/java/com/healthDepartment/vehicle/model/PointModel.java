/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.vehicle.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicle.tableClasses.Point;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Com7_2
 */
public class PointModel {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
   // public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    //public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public int getNoOfRows(String search_city_location, String search_point_name,String searchzone,String searchward,String searcharea) {
//        search_city_location = krutiToUnicode.convert_to_unicode(search_city_location);
//        search_point_name = krutiToUnicode.convert_to_unicode(search_point_name);

        int noOfRows = 0;
       // int city_location_id = getCityLocationId(search_city_location);
       
            try {
//        String query="SELECT count(point_id) from point "
//                       + " where  IF('" + search_point_name + "' = '', point_name LIKE '%%',point_name  ='" + search_point_name + "') "
//                       +" AND IF('" + city_location_id + "' = '', city_location_id LIKE '%%',city_location_id  ='" + city_location_id + "') " ;
  String query = "SELECT count(*) from point p,city_location cl ,"
                + "zone as z,ward as w,area as a where  p.city_location_id=cl.city_location_id  and cl.area_id=a.area_id"
                + " and a.ward_id=w.ward_id and w.zone_id=z.zone_id  "
                + " And IF('"+searcharea+"' = '',area_name LIKE '%%', area_name='"+searcharea+"')"
               
                + " And IF('"+searchward+"' = '',ward_name LIKE '%%', ward_name='"+searchward+"') "
                 + " AND IF('" + search_city_location + "' = '', location LIKE '%%',location='" + search_city_location + "') "
                + "And IF('"+searchzone+"' = '',zone_name LIKE '%%', zone_name='"+searchzone+"') "
                  + " And IF('" + search_point_name + "' = '',point_name LIKE '%%', point_name='" + search_point_name + "')";
                PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
                ResultSet rset = pstmt.executeQuery();
               while(rset.next())
                noOfRows = Integer.parseInt(rset.getString(1));
            } catch (Exception e) {
                System.out.println(e);
        
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
       } return noOfRows;
    
    }
    public List<Point> showData(int lowerLimit, int noOfRowsToDisplay, String search_city_location, String search_point_name,String searchzone,String searchward,String searcharea) {
//        search_city_location = krutiToUnicode.convert_to_unicode(search_city_location);
//        search_point_name = krutiToUnicode.convert_to_unicode(search_point_name);
        List list = new ArrayList();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        int city_location_id = getCityLocationId(search_city_location);
//            String query="SELECT p.point_id,p.point_name,cl.location,p.latitude,p.longitude from point p,city_location cl "
//                     + " where  p.city_location_id=cl.city_location_id "
//                     + " IF('" + search_point_name + "' = '', point_name LIKE '%%',point_name  ='" + search_point_name + "') "
//                     +" AND  IF('" + city_location_id + "' = '', city_location_id LIKE '%%',city_location_id  ='" + city_location_id + "') "
//                    + addQuery;

        String query = "SELECT p.point_id,p.point_name,cl.location,p.latitude,p.longitude from point p,city_location cl ,"
                + "zone as z,ward as w,area as a where  p.city_location_id=cl.city_location_id  and cl.area_id=a.area_id"
                + " and a.ward_id=w.ward_id and w.zone_id=z.zone_id  "
                + " And IF('"+searcharea+"' = '',area_name LIKE '%%', area_name='"+searcharea+"')"
               
                + " And IF('"+searchward+"' = '',ward_name LIKE '%%', ward_name='"+searchward+"') "
                 + " AND IF('" + search_city_location + "' = '', location LIKE '%%',location='" + search_city_location + "') "
                + "And IF('"+searchzone+"' = '',zone_name LIKE '%%', zone_name='"+searchzone+"') "
                  + " And IF('" + search_point_name + "' = '',point_name LIKE '%%', point_name='" + search_point_name + "') order by point_id desc  "
                + addQuery;
//        String query = " SELECT p.point_id,p.point_name,cl.location,p.latitude,p.longitude "
//                + " from point p,city_location cl "
//                + " where  p.city_location_id=cl.city_location_id "
//                + " AND IF('" + search_city_location + "' = '', location LIKE '%%',location='" + search_city_location + "') "
//                + " And IF('" + search_point_name + "' = '',point_name LIKE '%%', point_name='" + search_point_name + "') order by point_id desc  "
//                + addQuery;



   
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Point vt = new Point();
                vt.setPoint_id(rs.getInt("point_id"));
                vt.setPoint_name(rs.getString("point_name"));
                vt.setCity_location(rs.getString("location"));
                vt.setLatitude(rs.getString("latitude"));
                vt.setLongitude(rs.getString("longitude"));
                list.add(vt);
            }
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return list;
    }

    public boolean insertRecord(Point bean) {
        boolean status = false;
        String query = "";
        int rowsAffected = 0;
        int point_id = bean.getPoint_id();
        if (point_id == 0) {
            query = "insert into point (point_name,city_location_id,latitude,longitude) values(?,?,?,?)";
        }
        if (point_id > 0) {
            query = " update point set point_name=?,city_location_id=?,latitude=?,longitude=? where point_id=?";
        }
        String city_location = bean.getCity_location();
        int city_location_id = getCityLocationId(city_location);
        String point_name = bean.getPoint_name();
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
//        ps.setString(1,krutiToUnicode.convert_to_unicode(bean.getVehicle_type()));
//        ps.setString(2,krutiToUnicode.convert_to_unicode(bean.getRemark()));
            ps.setString(1, point_name);
            ps.setInt(2, city_location_id);
            ps.setString(3, bean.getLatitude());
            ps.setString(4, bean.getLongitude());

            if (point_id > 0) {
                ps.setInt(5, point_id);
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

    public boolean deleteRecord(String point_id) {
        boolean status = false;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement("Delete from point where point_id=" + point_id + " ").executeUpdate();
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

    public static List<String> getVehicleType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select vehicle_name from vehicle_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String vehicle_name = rset.getString("vehicle_name");
                if (vehicle_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(vehicle_name);
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
 public List<String> getZone(String q) {
        List<String> list = new ArrayList<String>();
       
        String query = "select z.zone_name from zone as z "
                + " GROUP BY z.zone_name  ORDER BY z.zone_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_name = rset.getString("zone_name");
                if (zone_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }

        public List<String> getWardName(String q, String zone_name)
    {
        List<String> list = new ArrayList<String>();
         PreparedStatement pstmt;
          //zone_name=krutiToUnicode.convert_to_unicode(zone_name);
        String query = " SELECT w.ward_name  FROM ward AS w, zone AS z "
               +  "WHERE   w.zone_id = z.zone_id "
                + "AND IF('" + zone_name + "'='', zone_name like '%%', zone_name ='" + zone_name + "') "
                 + "Group by ward_name ";
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(query);
           // pstmt.setString(1, zone_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String ward_name =rset.getString("ward_name");
                if (ward_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Ward No exists.");
            }
        } catch (Exception e) {
            System.out.println("getWardType ERROR inside WardTypeModel - " + e);
        }
        return list;
    }
        
           public List<String> getAreaName(String q, String ward_name, String zone_name) {
        List<String> list = new ArrayList<String>();
//            zone_name=krutiToUnicode.convert_to_unicode(zone_name);
//            ward_name=krutiToUnicode.convert_to_unicode(ward_name);
        String query =" SELECT a.area_name "
                + "FROM area AS a ,ward AS w, zone AS z "
               + "WHERE a.ward_id = w.ward_id "
               +  "AND w.zone_id = z.zone_id "
               +  "AND IF('" + ward_name + "'='', ward_name like '%%', ward_name ='" + ward_name + "') "
               +  "AND IF('" + zone_name + "'='', zone_name like '%%', zone_name ='" + zone_name + "') "
              +  "Group by area_name" ;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String areaName = rset.getString("area_name");
                if (areaName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(areaName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Area Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:AreaType1Model-" + e);
        }
        return list;
    }
    public int getCityLocationId(String city_location) {
        int city_location_id = 0;
        //city_location = krutiToUnicode.convert_to_unicode(city_location);
        String query = "select city_location_id from city_location "
                + " where location='" + city_location + "'";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                city_location_id = rs.getInt("city_location_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return city_location_id;
    }
public int getAreaId(String area,String ward,String zone) {
        int area_id = 0;
        //stopage_name = krutiToUnicode.convert_to_unicode(stopage_name);
        String query = "select area_id from area as a, ward as w,zone as z "
                + "where a.ward_id=w.ward_id and w.zone_id=z.zone_id and a.area_name='"+area+"' "
                + "and w.ward_name='"+ward+"' and z.zone_name='"+zone+"' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                area_id = rset.getInt("area_id");
            }
        } catch (Exception e) {
            System.out.println("RouteModel getStopageId() Error: " + e);
        }

        return area_id;
    }
  public int getCityId(int areaid) {
        int city_location_id = 0;
        //stopage_name = krutiToUnicode.convert_to_unicode(stopage_name);
        String query = "select city_location_id from city_location where area_id='"+areaid+"'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        } catch (Exception e) {
            System.out.println("RouteModel getStopageId() Error: " + e);
        }

        return city_location_id;
    }

    public List<String> getCityName(String area,String ward,String zone) {
         int area_id=getAreaId( area, ward, zone);
        int cl_id=getCityId(area_id);
        List<String> list = new ArrayList<String>();
        String query = "select location from city_location where city_location_id='"+cl_id+"' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            
            while (rset.next()) {
                String city_location = rset.getString("location");
               
                    list.add(city_location);
                    count++;
              
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
public int getCitylocationId(String areaid) {
        int city_location_id = 0;
        //stopage_name = krutiToUnicode.convert_to_unicode(stopage_name);
        String query = "select city_location_id from city_location where location='"+areaid+"'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        } catch (Exception e) {
            System.out.println("RouteModel getStopageId() Error: " + e);
        }

        return city_location_id;
    }
    public List<String> getPointName(String q,String citylocation) {  
         int cl_id=getCitylocationId(citylocation);
        List<String> list = new ArrayList<String>();
        String query = "select point_name from point as p,city_location as cl  where p.city_location_id=cl.city_location_id and  "
                + "IF('" + citylocation + "'='', location like '%%', location ='" + citylocation + "') ";  
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String point_name =rset.getString("point_name");
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

    public List<String> getSearchCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select distinct location from point p,city_location cl "
                      +" where p.city_location_id=cl.city_location_id ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String location = rset.getString("location");
                if (location.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location);
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

    public List<String> getSearchPointName(String q) {
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
  public String getPointLatLong(String shiftVehicleDetailId){
    String lat="";
    String longi="";
    String query=" select latitude,longitude "
                  +" from point "
                  +" where point_id=? ";

    try{
        PreparedStatement pst = connection.prepareStatement(query);
        pst.setString(1,shiftVehicleDetailId);
        ResultSet rs  = pst.executeQuery();
        while(rs.next()){
            lat=rs.getString("latitude");
            longi=rs.getString("longitude");


        }
    }catch(Exception e){
        System.out.println(e);
    }


    return lat+","+longi;
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
