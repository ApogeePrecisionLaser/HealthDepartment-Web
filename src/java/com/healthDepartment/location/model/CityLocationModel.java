/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.location.model;

import com.healthDepartment.location.tableClasses.CityLocationBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
 * @author Administrator
 */
public class CityLocationModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public Connection getConnection() {
        return connection;
    }

    public void setConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = (Connection) DriverManager.getConnection(connectionString + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            System.out.println("connected - " + connection);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
        } catch (Exception e) {
            System.out.println("OfficerBookModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public ByteArrayOutputStream generateExcelList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            //reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("OfficerBookModel generatReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public int deleteRecord(int status_type_id) {
        String query = " DELETE FROM city_location WHERE city_location_id = " + status_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("StatusType Model deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {

            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, it is used in another GUI.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(CityLocationBean cityLocationTypeBean) {
        String query = " UPDATE city_location SET location=?, remark=?, area_id=?, "
                + " latitude=?,longitude=?,location_no=? WHERE city_location_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

      
            pstmt.setString(1, cityLocationTypeBean.getLocation());
            pstmt.setString(2, cityLocationTypeBean.getRemark());
                int area_id=getAreaeId(cityLocationTypeBean.getArea());
            pstmt.setInt(3, area_id);
            pstmt.setDouble(4, cityLocationTypeBean.getLatitude());
            pstmt.setDouble(5,cityLocationTypeBean.getLongitude());
            pstmt.setString(6, cityLocationTypeBean.getLocation_no());
             pstmt.setInt(7, cityLocationTypeBean.getCity_location_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("CityLocationModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error......";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int insertRecord(CityLocationBean cityLocationeBean) {
        int rowsAffected = 0;
        //String query = "INSERT INTO city_location (zone_new_id,location,remark, location_code) VALUES (?,?,?, ?) ";
         String query = "INSERT INTO city_location (location,remark,latitude,longitude,area_id,location_no) VALUES (?,?,?,?,?,?) ";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, cityLocationeBean.getCity());
            //stmt.setString(2, krutiToUnicode.convert_to_unicode(cityLocationeBean.getLocation()));
            pstmt.setString(2, cityLocationeBean.getRemark());
           // pstmt.setString(4, cityLocationeBean.getLocation_code());
            pstmt.setDouble(3,cityLocationeBean.getLatitude());
            pstmt.setDouble(4,cityLocationeBean.getLongitude());
            int area_id=getAreaeId(cityLocationeBean.getArea());
            pstmt.setInt(5, area_id);
            pstmt.setString(6,cityLocationeBean.getLocation_no());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: Data inserting: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("inserted");
        } else {
            message = "Record Not saved ......";
            msgBgColor = COLOR_OK;
            System.out.println("not inserted");
        }
        return rowsAffected;
    }

    public List<CityLocationBean> showData(int lowerLimit, int noOfRowsToDisplay,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName) {
        List<CityLocationBean> list = new ArrayList<CityLocationBean>();
//        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
//        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
//        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
//        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);

        String query = " select cl.city_location_id,z.zone_name,w.ward_name,a.area_name,cl.location,cl.location_no,cl.remark,cl.latitude,cl.longitude "
                           + "from city_location as cl,zone as z, ward as w,area as a where "
                           + "cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id "
                           + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  =?) "
                           + "And IF('" + searchZoneName + "' = '', z.zone_name LIKE '%%', z.zone_name =?) "
                           + "And IF('" + searchWardName + "' = '', w.ward_name LIKE '%%', w.ward_name =?) "
                           + "And IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =?)order by zone_name,ward_name,area_name,location_no"
                           + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCityName);
            pstmt.setString(2, searchZoneName);
            pstmt.setString(3, searchWardName);
            pstmt.setString(4, searchAreaName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CityLocationBean cityLocationType = new CityLocationBean();
                cityLocationType.setCity_location_id(rset.getInt("city_location_id"));
                cityLocationType.setCity(rset.getString("location"));
                cityLocationType.setZone(rset.getString("zone_name"));
                cityLocationType.setWard(rset.getString("ward_name"));
                cityLocationType.setArea(rset.getString("area_name"));
                cityLocationType.setRemark(rset.getString("remark"));
               cityLocationType.setLocation_no(rset.getString("location_no"));
                cityLocationType.setLatitude(rset.getDouble("latitude"));
                cityLocationType.setLongitude(rset.getDouble("longitude"));
                list.add(cityLocationType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public List<CityLocationBean> showAllData(String searchCityName, String searchZoneName, String searchWardName,String searchAreaName) {
        List<CityLocationBean> list = new ArrayList<CityLocationBean>();
//        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
//        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
//        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
//        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);

        String query = " SELECT z.zone_name,w.ward_name,a.area_name,cl.location,cl.location_no,cl.remark,cl.latitude,cl.longitude "
               + " FROM city_location as cl,zone as z,ward as w,area as a where "
              +  " cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id "
              + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  =?) "
                    + "And IF('" + searchZoneName + "' = '', z.zone_name LIKE '%%', z.zone_name =?) "
                         + "And IF('" + searchWardName + "' = '', w.ward_name LIKE '%%', w.ward_name =?) "
                         + "And IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =?) ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchCityName);
            pstmt.setString(2, searchZoneName);
            pstmt.setString(3, searchWardName);
            pstmt.setString(4, searchAreaName);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                 CityLocationBean cityLocationType = new CityLocationBean();
                 cityLocationType.setZone(rset.getString("zone_name"));
                 cityLocationType.setWard(rset.getString("ward_name"));
                 cityLocationType.setArea(rset.getString("area_name"));
                cityLocationType.setLocation(rset.getString("location"));
                cityLocationType.setRemark(rset.getString("remark"));
                cityLocationType.setLocation_no(rset.getString("location_no"));
                cityLocationType.setLatitude(rset.getDouble("latitude"));
                cityLocationType.setLongitude(rset.getDouble("longitude"));
                list.add(cityLocationType);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowData()....: " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchCityName, String searchZoneName, String searchWardName,String searchAreaName)
    {
//        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
//        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
//        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
//        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
        String query = " select count(city_location_id) as id "
                         + " from city_location as cl,zone as z, ward as w,area as a where "
                         + " cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id " 
                         + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  =?) "
                         + "And IF('" + searchZoneName + "' = '', z.zone_name LIKE '%%', z.zone_name =?) "
                         + "And IF('" + searchWardName + "' = '', w.ward_name LIKE '%%', w.ward_name =?) "
                         + "And IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =?) ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchCityName);
            stmt.setString(2, searchZoneName);
            stmt.setString(3, searchWardName);
            stmt.setString(4, searchAreaName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CityLoactionModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<String> searchCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT distinct cl.location FROM city_location as cl "
                + " GROUP BY cl.location ORDER BY cl.location";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
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
            System.out.println("searchCityName ERROR inside CityLocationModell - " + e);
        }
        return list;
    }

    public List<String> getCityName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select distinct city_name from city "
                + " GROUP BY city_name ORDER BY city_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_name =rset.getString("city_name");
                if (city_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_name);
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

    public List<String> getZone(String q) {
        List<String> list = new ArrayList<String>();
       
        String query = "select distinct z.zone_name from zone as z "
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
        String query = " SELECT distinct w.ward_name  FROM ward AS w, zone AS z "
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
 public List<CityLocationBean> showDataLattitude(String searchArea,String searchCityName,String searchZoneName,String searchWardType) {
        List<CityLocationBean> list = new ArrayList<CityLocationBean>();
        String client_task="Jabalpur";
        //int client_equip_id = getClientEquipId(user_id,equip_name);
         if(searchArea==null)
            searchArea="";
         if(searchCityName==null)
            searchCityName=""; 
         if(searchZoneName==null)
            searchZoneName="";
         if(searchWardType==null)
            searchWardType="";      
        int rev = 3;
       String query = " select cl.latitude,cl.longitude "      
                           + "from city_location as cl,zone as z, ward as w,area as a where "
                           + "cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id "
                           + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  ='" + searchCityName + "') "
                           + "And IF('" + searchZoneName + "' = '', z.zone_name LIKE '%%', z.zone_name ='" + searchZoneName + "') "
                           + "And IF('" + searchWardType + "' = '', w.ward_name LIKE '%%', w.ward_name ='" + searchWardType + "') "
                           + "And IF('" + searchArea + "' = '', a.area_name LIKE '%%', a.area_name ='" + searchArea + "')";
//        String query = " select task,p.position_data1,p.position_data2,position_data3 from position p"
//                      + " where p.is_live='No' and p.active='Y'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CityLocationBean bean = new CityLocationBean();
                bean.setLatitude(rset.getDouble("latitude"));
                 bean.setLongitude(rset.getDouble("longitude"));
                 
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return list;
    }
 public String getPointLatLong(String shiftVehicleDetailId){
    String lat="";
    String longi="";
    String query=" SELECT latitude,longitude FROM city_location where city_location_id=?";
   
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
        public List<String> getAreaName(String q, String ward_name, String zone_name) {
        List<String> list = new ArrayList<String>();
//            zone_name=krutiToUnicode.convert_to_unicode(zone_name);
//            ward_name=krutiToUnicode.convert_to_unicode(ward_name);
        String query =" SELECT distinct a.area_name "
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

    public List<String> getZoneName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select distinct zone from city_location as cl ,zone_new as z where cl.zone_new_id=z.zone_new_id"
                + " GROUP BY zone ORDER BY zone";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone =rset.getString("zone");
                if (zone.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }

    public List<String> getLocationName(String q, String location_code) {
        List<String> list = new ArrayList<String>();
        String query = "select distinct location from city_location "
                + " WHERE IF('"+ location_code +"'='', location_code LIKE '%%', location_code='"+ location_code +"') "
                + " GROUP BY location ORDER BY location";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
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
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }

    public List<String> getLocationCode(String q, String location_name) {
        List<String> list = new ArrayList<String>();
     //   location_name = krutiToUnicode.convert_to_unicode(location_name);
        String query = "select location_code from city_location "
                + " WHERE IF('"+ location_name +"'='', location LIKE '%%', location='"+ location_name +"') "
                + " GROUP BY location ORDER BY location";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String location = rset.getString("location_code");
                if (location.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }        

    public int getAreaeId(String area) {
        //List<String> list = new ArrayList<String>();
        int area_id = 0;
      //  area = krutiToUnicode.convert_to_unicode(area);
        String query = "select area_id from area where area_name='"+area+"'";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                area_id = rset.getInt("area_id");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside CityLocationModel - " + e);
        }
        return area_id;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("statusTypemodel closeConnection() Error: " + e);
        }
    }
}
