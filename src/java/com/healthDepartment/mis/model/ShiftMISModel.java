/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.model;

import com.healthDepartment.mis.tableClasses.ShiftMIS;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
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
 * @author Shobha
 */
public class ShiftMISModel {
     private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
     private static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();


 public static int  getNoOfRows(String location_type,String search_shift_type,String search_designation,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName)
 {
        search_shift_type = krutiToUnicode.convert_to_unicode(search_shift_type);
         searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
       search_designation = krutiToUnicode.convert_to_unicode(search_designation);
     int noOfRows = 0;
          String addloc="";
          if(location_type.equals("zone"))
              addloc=" and lt.location_type_name='"+location_type+"' and z.zone_name='"+searchZoneName+"' ";
          if(location_type.equals("ward"))
              addloc=" and lt.location_type_name='"+location_type+"' and w.ward_name='"+searchWardName+"' ";
            if(location_type.equals("area"))
              addloc=" and lt.location_type_name='"+location_type+"' and a.area_name='"+searchAreaName+"' ";
             if(location_type.equals("city_location"))
              addloc=" and lt.location_type_name='"+location_type+"' and cl.location='"+searchCityName+"' ";
        try {
             String query="Select count(sdlm.map_id1) as map_id1 "
                     + " from shift_designation_location_map as sdlm "
                     + " left join designation_location_type as  dlt on sdlm.designation_location_type_id=dlt.designation_location_type_id "
                     + " left join designation as d ON dlt.designation_id=d.designation_id "
                     +  " left join shift_type as st on st.shift_type_id=sdlm.shift_type_id "
                     + " left join location_type as lt on dlt.location_type_id=lt.location_type_id "
                     +  " left join zone as z on dlt.zone_id=z.zone_id "
                     +  "  left join ward as w on dlt.ward_id=w.ward_id "
                     +  "  left join area as a on dlt.area_id=a.area_id "
                     +  "  left join city_location as cl on dlt.city_location_id=cl.city_location_id "
                     + " where sdlm.active='y' "  + addloc
                     + " AND IF('"+ search_shift_type +"'='', st.shift_type LIKE '%%', st.shift_type='"+ search_shift_type +"')"
                     + " AND IF('"+ search_designation +"'='', d.designation LIKE '%%', d.designation='"+ search_designation +"')";

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

 public static List<ShiftMIS> showData(int lowerLimit,int noOfRowsToDisplay,String location_type,String search_shift_type,String search_designation,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName)
  {
         search_shift_type = krutiToUnicode.convert_to_unicode(search_shift_type);
        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
       search_designation = krutiToUnicode.convert_to_unicode(search_designation);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
           String addloc="";
          if(location_type.equals("zone"))
              addloc=" and lt.location_type_name='"+location_type+"' and z.zone_name='"+searchZoneName+"' ";
          if(location_type.equals("ward"))
              addloc=" and lt.location_type_name='"+location_type+"' and w.ward_name='"+searchWardName+"' ";
            if(location_type.equals("area"))
              addloc=" and lt.location_type_name='"+location_type+"' and a.area_name='"+searchAreaName+"' ";
             if(location_type.equals("city_location"))
              addloc=" and lt.location_type_name='"+location_type+"' and cl.location='"+searchCityName+"' ";
   String query= "Select sdlm.map_id1,sdlm.map_id2,st.shift_type,d.designation,lt.location_type_name,z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_name,a.area_no,cl.location,cl.location_no,sdlm.no_of_person,sdlm.remark "
   +  " from shift_designation_location_map as sdlm "
   + " left join designation_location_type as  dlt on sdlm.designation_location_type_id=dlt.designation_location_type_id "
   +  " left join designation as d ON dlt.designation_id=d.designation_id "
   +  " left join shift_type as st on st.shift_type_id=sdlm.shift_type_id "
   +  " left join location_type as lt on dlt.location_type_id=lt.location_type_id "
   +  " left join zone as z on dlt.zone_id=z.zone_id "
   +  " left join ward as w on dlt.ward_id=w.ward_id "
   + "  left join area as a on dlt.area_id=a.area_id "
   +  " left join city_location as cl on dlt.city_location_id=cl.city_location_id "
   + " where sdlm.active='y' " + addloc
   + " AND IF('"+ search_shift_type +"'='', st.shift_type LIKE '%%', st.shift_type='"+ search_shift_type +"')"
   + " AND IF('"+ search_designation +"'='', d.designation LIKE '%%', d.designation='"+ search_designation +"')"
   + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);

         ResultSet rs =ps.executeQuery();
         while(rs.next()){
             ShiftMIS sdlb=new ShiftMIS();
             sdlb.setMap_id1(rs.getInt("map_id1"));
             sdlb.setMap_id2(rs.getInt("map_id2"));
             sdlb.setShift_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("shift_type")));
             sdlb.setDesignation(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("designation")));
             sdlb.setLocation_type(rs.getString("location_type_name"));
             String location_type_name = rs.getString("location_type_name");
             sdlb.setZone(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("zone_name")));
             sdlb.setZone_no(rs.getString("zone_no"));
             sdlb.setLocation(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("location")));
             sdlb.setLocation_no(rs.getString("location_no"));
             if(location_type_name.equals("city_location") || location_type_name.equals("area") || location_type_name.equals("ward") )
                 getName(sdlb , location_type_name,rs.getString("ward_name"),rs.getString("area_name"),rs.getString("location"));
             sdlb.setNo_of_person(rs.getInt("no_of_person"));
             sdlb.setRemark(rs.getString("remark"));
             list.add(sdlb);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

 public static void getName(ShiftMIS sdlb, String location_type,String ward,String area,String location){
      String query = "";
      if(location_type.equals("city_location"))
          query="select z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_name,a.area_no,cl.location,cl.location_no from city_location as cl,zone as z,ward as w,area as a"
                    +" where cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id and location='"+location+"' ";

       if(location_type.equals("area"))
           query="select z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_name,a.area_no from zone as z,ward as w,area as a"
                    +" where a.ward_id=w.ward_id and w.zone_id=z.zone_id and area_name='"+area+"' ";

      if(location_type.equals("ward"))
          query="select z.zone_name,z.zone_no,w.ward_name,w.ward_no from zone as z,ward as w "
                    +" where w.zone_id=z.zone_id and ward_name='"+ward+"' ";

      try {
           ResultSet rs = connection.prepareStatement(query).executeQuery();
             while(rs.next()){
                      if(location_type.equals("area") || location_type.equals("city_location") || location_type.equals("ward"))
                      {
                     sdlb.setWard(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("ward_name")));
                     sdlb.setWard_no(rs.getString("ward_no"));
                     sdlb.setZone(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("zone_name")));
                     sdlb.setZone_no(rs.getString("zone_no"));
                     }
                     if(location_type.equals("area") || location_type.equals("city_location"))
                         {
                       sdlb.setArea(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("area_name")));
                       sdlb.setArea_no(rs.getString("area_no"));
                         }
          }
        } catch (Exception e) {
            System.out.println( e);
        }
    }
 public  boolean revision(ShiftMIS bean)
    {
      boolean status=false;
      int rowsAffected=0;
      String query1 = "SELECT max(revision) revision FROM shift_designation_location_map WHERE map_id1 = "+bean.getMap_id1()+" && map_id2=? && active='y' ";
      String query2 = " UPDATE shift_designation_location_map SET active=? WHERE map_id1 = ? && map_id2=? && revision = ? ";
      String query3 = " insert into shift_designation_location_map (no_of_person,revision,active,designation_location_type_id,remark,shift_type_id,map_id1,map_id2) values(?,?,?,?,?,?,?,?)";
      int updateRowsAffected = 0;
      try {

             int map_id2=0;
             map_id2=getRMap_id2(bean);
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query1);
             ps.setInt(1,map_id2);
             ResultSet rs = ps.executeQuery();
             if(rs.next()){
             PreparedStatement pst = (PreparedStatement) connection.prepareStatement(query2);
             pst.setString(1,  "n");
             pst.setInt(2, bean.getMap_id1());
             pst.setInt(3,map_id2);
             pst.setInt(4,rs.getInt("revision"));
             updateRowsAffected = pst.executeUpdate();
             if(updateRowsAffected >= 1){
             int revision = rs.getInt("revision")+1;
             PreparedStatement psmt = (PreparedStatement) connection.prepareStatement(query3);
              psmt.setInt(1,bean.getNo_of_person());
              psmt.setInt(2,revision);
              psmt.setString(3, "y");
              int dlt=getDLT_id(bean.getMap_id1(),map_id2);
              psmt.setInt(4,dlt);
              psmt.setString(5,bean.getRemark());
              int shift_type_id=getShift_type_id(bean.getShift_type());
              psmt.setInt(6,shift_type_id);
              psmt.setInt(7,bean.getMap_id1());
              psmt.setInt(8,map_id2);
              rowsAffected = psmt.executeUpdate();
              if(rowsAffected > 0)
              status=true;
              else status=false;
             }
           }
          } catch (Exception e)
             {
              System.out.println( e);
             }
      if (rowsAffected > 0) {
             message = "Record Revise successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Revised");
        } else {
             message = "Record Not Revised Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Revised");
        }

       return status;
    }

public  boolean insertRecord(ShiftMIS bean)
 {
    int rowsAffected=0;
     int Affected=0,zone_id=0,ward_id=0,area_id=0,location_id=0;
    boolean status=false;
    PreparedStatement ps = null;
    int dlt=0;
    try{
    String  query2= " insert into designation_location_type (designation_id,location_type_id,zone_id,ward_id,area_id,city_location_id,remark)values(?,?,?,?,?,?,?)";

    ps =(PreparedStatement) connection.prepareStatement(query2);
    int getDesignationid=getDesignation_id(bean.getDesignation());
    ps.setInt(1,getDesignationid);
    int locationTypeId=getlocationTypeId(bean.getLocation_type());
    ps.setInt(2,locationTypeId);
    String location_type=bean.getLocation_type();
    if(location_type.equals("zone"))
     zone_id=getZone_id(bean.getZone());
     if(location_type.equals("ward"))
     ward_id=getWard_id(bean.getZone(),bean.getWard());
     if(location_type.equals("area"))
     area_id=getArea_id(bean.getZone(),bean.getWard(),bean.getArea());
     if(location_type.equals("city_location"))
     location_id=getlocation_id(bean.getZone(),bean.getWard(),bean.getArea(),bean.getLocation());
        if(zone_id==0)
        ps.setNull(3, java.sql.Types.NULL);
        else
        ps.setInt(3,zone_id);
         if(ward_id==0)
        ps.setNull(4, java.sql.Types.NULL);
         else
        ps.setInt(4,ward_id);
        if(area_id==0)
        ps.setNull(5, java.sql.Types.NULL);
        else
        ps.setInt(5,area_id);
        if(location_id==0)
        ps.setNull(6, java.sql.Types.NULL);
        else
        ps.setInt(6,location_id);
        ps.setString(7, bean.getRemark());
        Affected = ps.executeUpdate();
        ResultSet rst = ps.getGeneratedKeys();
            if(rst.next())
                dlt = rst.getInt(1);
       if(Affected>0)
      {
      String  query = " insert into shift_designation_location_map (no_of_person,revision,active,designation_location_type_id,remark,shift_type_id) values(?,?,?,?,?,?)";
      String  query1 = " insert into shift_designation_location_map (no_of_person,revision,active,designation_location_type_id,remark,shift_type_id,map_id1,map_id2) values(?,?,?,?,?,?,?,?)";
      int map_id1=0;
      map_id1=getMap_id1(bean.getShift_type(),bean.getLocation_type(),bean.getZone(),bean.getWard(),bean.getArea(),bean.getLocation());
      if(map_id1>0)
      {
         ps =   (PreparedStatement) connection.prepareStatement(query1);
         ps.setInt(7,map_id1);
         int map_id2=0;
         map_id2=getMap_id2(map_id1);
         map_id2=map_id2 + 1;
         ps.setInt(8,map_id2);
    }
    else{
        ps =   (PreparedStatement) connection.prepareStatement(query);
     }
       ps.setInt(1,bean.getNo_of_person());
       ps.setInt(2,bean.getRevision());
       ps.setString(3, "y");
       ps.setInt(4,dlt);
       ps.setString(5,bean.getRemark());
       int shift_type_id=getShift_type_id(bean.getShift_type());
       ps.setInt(6,shift_type_id);
       rowsAffected = ps.executeUpdate();
        }
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

public  boolean deleteRecord(String map_id1, String map_id2){
    boolean status=false;
    int rowsAffected=0;
    try{
        String query=" UPDATE shift_designation_location_map SET active='N' WHERE map_id1 ="+ map_id1 +" && map_id2="+ map_id2;
        rowsAffected = connection.prepareStatement(query).executeUpdate();
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
        System.out.println("not Deleted");
    }
    return status;
}

public static int getRevisionno(ShiftMIS bean,int map_id2) {
        int revision=0;
        try {

            String query = " SELECT max(revision) as revision FROM shift_designation_location_map WHERE map_id1 =? and map_id2=? && active='Y';";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setInt(1,bean.getMap_id1());
             int map_id=0;
            map_id=getMap_id2(bean.getMap_id1());
            pstmt.setInt(2,map_id);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next())
            {
                revision = rset.getInt("revision");

            }
        } catch (Exception e) {
        }
        return revision;
    }



  public static int getShift_type_id(String shift_type) {
      shift_type=krutiToUnicode.convert_to_unicode(shift_type);
        int shift_type_id = 0;
        try {
            String query = "select shift_type_id from shift_type"
                            +" where shift_type='"+shift_type+"'  ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                shift_type_id = rset.getInt("shift_type_id");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return shift_type_id;
    }


public static int getDLT_id(int map_id1,int map_id2) {
        int designation_location_type_id = 0;
        try {
            String query = " select designation_location_type_id from shift_designation_location_map "
                            +" where map_id1="+map_id1+" and map_id2="+map_id2+"  ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                designation_location_type_id = rset.getInt("designation_location_type_id");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return designation_location_type_id;
    }
/*
public static int getType_id(String location_type,String location,String area,String ward,String zone) {
        int get_id = 0;
        String query="";
        try {
            if(location_type.equals("city_location"))
                 query= " SELECT cl.city_location_id "
                           + " FROM area AS a ,ward AS w, zone AS z,city_location as cl "
                           + " WHERE cl.area_id=a.area_id "
                           + " AND a.ward_id = w.ward_id "
                           +  " AND w.zone_id = z.zone_id "
                           +  " AND IF('" + area + "'='', area_name like '%%', area_name ='" + area + "') "
                           +  " AND IF('" + ward + "'='', ward_name like '%%', ward_name ='" + ward + "') "
                           +  " AND IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
                           + " Group by city_location_id " ;
             if(location_type.equals("area"))
                      query= "SELECT a.area_id "
                             +  " FROM area AS a ,ward AS w, zone AS z "
                             + "  WHERE a.ward_id = w.ward_id "
                             +  " AND w.zone_id = z.zone_id "
                             +  " AND IF('" + ward + "'='', ward_name like '%%', ward_name ='" + ward + "') "
                             + " AND IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
                             + " Group by area_id  " ;
             if(location_type.equals("ward"))
              query= " SELECT w.ward_id  FROM ward AS w, zone AS z "
                      + "  WHERE   w.zone_id = z.zone_id "
                      + "  AND IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
                      + " Group by ward_id  ";
             if(location_type.equals("zone"))
              query= " select zone_id from ward where zone_name='"+zone+"' " ;

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                get_id = rset.getInt(1);
            }
          } catch (Exception e) {
            System.out.print(e);}
            return get_id;
    }*/
public static int getZone_id(String zone)
{
     zone=krutiToUnicode.convert_to_unicode(zone);
        int zone_id = 0;
        try {
            String  query= " select zone_id from zone where zone_name='"+zone+"' " ;
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                zone_id = rset.getInt("zone_id");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return zone_id;
    }
public static int getWard_id(String zone,String ward)
{
          zone=krutiToUnicode.convert_to_unicode(zone);
         ward=krutiToUnicode.convert_to_unicode(ward);
        int ward_id = 0;
        try {
              String query= " SELECT w.ward_id  FROM ward AS w, zone AS z "
                       + "  WHERE   w.zone_id = z.zone_id "
                       + "  AND IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
                        + "  AND IF('" + ward + "'='0', ward_name like '%%', ward_name ='" + ward + "') "
                       + " Group by ward_id  ";
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ward_id = rset.getInt("ward_id");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return ward_id;
    }
public static int getArea_id(String zone,String ward,String area)
{
          zone=krutiToUnicode.convert_to_unicode(zone);
         ward=krutiToUnicode.convert_to_unicode(ward);
         area=krutiToUnicode.convert_to_unicode(area);
        int area_id = 0;
        try {
          String query= "SELECT a.area_id "
                             +  " FROM area AS a ,ward AS w, zone AS z "
                             + "  WHERE a.ward_id = w.ward_id "
                             +  " AND w.zone_id = z.zone_id "
                             +  " AND IF('" + ward + "'='', ward_name like '%%', ward_name ='" + ward + "') "
                             + " AND IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
                             + " AND IF('" + area + "'='0', area_name like '%%', area_name ='" + area + "') "
                             + " Group by area_id  " ;
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                area_id = rset.getInt("area_id");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return area_id;
    }

public static int getlocation_id(String zone,String ward,String area,String location)
{
         zone=krutiToUnicode.convert_to_unicode(zone);
         ward=krutiToUnicode.convert_to_unicode(ward);
         area=krutiToUnicode.convert_to_unicode(area);
         location=krutiToUnicode.convert_to_unicode(location);
        int city_location_id = 0;
        try {
           String query= " SELECT cl.city_location_id "
                           + " FROM area AS a ,ward AS w, zone AS z,city_location as cl "
                           + " WHERE cl.area_id=a.area_id "
                           + " AND a.ward_id = w.ward_id "
                           +  " AND w.zone_id = z.zone_id "
                           +  " AND IF('" + area + "'='', area_name like '%%', area_name ='" + area + "') "
                           +  " AND IF('" + ward + "'='', ward_name like '%%', ward_name ='" + ward + "') "
                           +  " AND IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
                           +  " AND IF('" + location + "'='0', location like '%%', location ='" + location + "') "
                           + " Group by city_location_id " ;
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return city_location_id;
    }

public static int getlocationTypeId(String location_type) {
        int location_type_id = 0;
        try {
            String query = "select location_type_id from location_type"
                            +" where location_type_name='"+location_type+"'  ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                location_type_id = rset.getInt("location_type_id");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return location_type_id;
    }

public static int getMap_id1(String shift_type,String location_type,String zone,String ward,String area,String location) {
        int map_id1 = 0;
        String addLocation = "";
        shift_type=krutiToUnicode.convert_to_unicode(shift_type);
         zone=krutiToUnicode.convert_to_unicode(zone);
         ward=krutiToUnicode.convert_to_unicode(ward);
         area=krutiToUnicode.convert_to_unicode(area);
         location=krutiToUnicode.convert_to_unicode(location);
        if(location_type.equals("city_location"))
            addLocation = "location";
        else
            addLocation = location_type + "_name";
        String loc="";
         if(location_type.equals("zone"))
              loc=zone;
        if(location_type.equals("ward"))
               loc=ward;
         if(location_type.equals("area"))
                loc=area;
          if(location_type.equals("city_location"))
                loc=location;

        try {

            String query = "SELECT sdlm.map_id1 FROM shift_type st, shift_designation_location_map sdlm, designation_location_type dlt, location_type lt, "+ location_type +" slt "
                    +  " where st.shift_type_id=sdlm.shift_type_id and sdlm.designation_location_type_id=dlt.designation_location_type_id "
                    +   " and dlt.location_type_id=lt.location_type_id and slt."+ location_type +"_id = dlt."+ location_type +"_id AND slt."+ addLocation +"='" + loc + "' ";


            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                map_id1 = rset.getInt("map_id1");           }
        } catch (Exception e) {
        }
        return map_id1;
    }


public static int getMap_id2(int map_id1) {
        int map_id2 = 0;
        try {
            String query = " select max(map_id2) as map_id2 from shift_designation_location_map where map_id1="+map_id1+" ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next())
            {
                map_id2 = rset.getInt("map_id2");
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return map_id2;
    }
public static int getRMap_id2(ShiftMIS bean) {
        int map_id2 = 0;
           String designation=krutiToUnicode.convert_to_unicode(bean.getDesignation());
        try {
            String query = " select map_id2 from shift_designation_location_map sdlm,designation d,designation_location_type as dlt where "
                              + "  sdlm.designation_location_type_id=dlt.designation_location_type_id and dlt.designation_id=d.designation_id "
                              + "   and map_id1="+bean.getMap_id1()+"  and designation= '"+designation+"' ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next())
            {
                map_id2 = rset.getInt("map_id2");
            }
        } catch (Exception e) {
        }
        return map_id2;
    }


public static int getCity_location_id(String location) {
      location=krutiToUnicode.convert_to_unicode(location);
        int city_location_id = 0;
        try {
            String query = "select city_location_id from city_location"
                    +" where location='"+location+"' ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        } catch (Exception e) {
        }
        return city_location_id;
    }

public static int getDesignation_id(String designation) {
     designation=krutiToUnicode.convert_to_unicode(designation);
        int designation_id = 0;
        try {
            String query = "select designation_id from designation"
                            +" where designation='"+designation+"' ";
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                designation_id = rset.getInt("designation_id");
            }
        } catch (Exception e) {
        }
        return designation_id;
    }

 public static List<String> getArea(String q,String ward,String zone,String area_no){
     List<String> list=new ArrayList<String>();
       ward=krutiToUnicode.convert_to_unicode(ward);
         zone=krutiToUnicode.convert_to_unicode(zone);
   String query =" SELECT a.area_name "
                + "FROM area AS a ,ward AS w, zone AS z "
               + "WHERE a.ward_id = w.ward_id "
               +  "AND w.zone_id = z.zone_id "
               +  "AND IF('" + ward + "'='', ward_name like '%%', ward_name ='" + ward + "') "
               +  "AND IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
                +  "AND IF('" + area_no + "'='', area_no like '%%', area_no ='" + area_no + "') "
              +  "Group by area_name" ;
      try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String area_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("area_name"));
                   if (area_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(area_name);
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

   public static List<String> getAreaNo(String q,String area){
     List<String> list=new ArrayList<String>();
           area=krutiToUnicode.convert_to_unicode(area);
   String query = " SELECT area_no FROM  area "
               +  "WHERE  IF('" + area + "'='', area_name like '%%', area_name ='" + area + "') "
                 + "Group by area_no ";
      try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String area_no = rset.getString("area_no");
                    list.add(area_no);
                    count++;
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }

  public static List<String> getZone(String q,String zone_no){
     List<String> list=new ArrayList<String>();
   String query =" SELECT zone_name FROM zone "
                   +  " WHERE  IF('" + zone_no + "'='', zone_no like '%%', zone_no ='" + zone_no + "') " ;
      try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String zone_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("zone_name"));
                if (zone_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_name);
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
  public static List<String> getZoneNo(String q,String zone){
     List<String> list=new ArrayList<String>();
           zone=krutiToUnicode.convert_to_unicode(zone);
   String query = " SELECT zone_no FROM  zone "
               +  " WHERE  IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
                 + "Group by zone_no ";
      try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String zone_no = rset.getString("zone_no");
                    list.add(zone_no);
                    count++;
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }

    public static List<String> getWard(String q,String zone,String ward_no){
     List<String> list=new ArrayList<String>();
           zone=krutiToUnicode.convert_to_unicode(zone);
   String query = " SELECT w.ward_name  FROM ward AS w, zone AS z "
               +  "WHERE   w.zone_id = z.zone_id "
                + "AND IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
                 + "AND IF('" + ward_no + "'='', ward_no like '%%', ward_no ='" + ward_no + "') "
                 + "Group by ward_name ";
      try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String ward_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("ward_name"));
                   if (ward_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_name);
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
  public static List<String> getWardNo(String q,String ward){
     List<String> list=new ArrayList<String>();
             ward=krutiToUnicode.convert_to_unicode(ward);
   String query = " SELECT ward_no FROM  ward "
               +  "WHERE  IF('" + ward + "'='', ward_name like '%%', ward_name ='" + ward + "') "
                 + "Group by ward_no ";
      try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String ward_no = rset.getString("ward_no");
                    list.add(ward_no);
                    count++;
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }

   public static List<String> getlocation(String q,String area,String ward,String zone,String location_no){
             List<String> list=new ArrayList<String>();
          area=krutiToUnicode.convert_to_unicode(area);
          ward=krutiToUnicode.convert_to_unicode(ward);
          zone=krutiToUnicode.convert_to_unicode(zone);
            String query =" SELECT cl.location "
              + " FROM area AS a ,ward AS w, zone AS z,city_location as cl "
             + " WHERE cl.area_id=a.area_id "
             + " AND a.ward_id = w.ward_id "
            +  " AND w.zone_id = z.zone_id "
            +  " AND IF('" + area + "'='', area_name like '%%', area_name ='" + area + "') "
            +  " AND IF('" + ward + "'='', ward_name like '%%', ward_name ='" + ward + "') "
            +  " AND IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
            +  " AND IF('" + location_no + "'='', location_no like '%%', location_no ='" + location_no + "') "
            + " Group by location " ;
           try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String location = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("location"));
              if (location.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(location);
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
  public static List<String> getLocationNo(String q,String location){
     List<String> list=new ArrayList<String>();
     location=krutiToUnicode.convert_to_unicode(location);
   String query = " SELECT location_no FROM  city_location "
               +  "WHERE  IF('" + location + "'='', location like '%%', location ='" + location + "') "
                 + "Group by location_no ";
      try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String location_no = rset.getString("location_no");
                    list.add(location_no);
                    count++;
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }


 public static List<String> getDesignation(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="SELECT designation FROM designation"
                    +" GROUP BY designation ORDER BY designation ";
      try {
                 ResultSet rset = connection.prepareStatement(query).executeQuery();
                int count = 0;
                q = q.trim();
                while (rset.next()) {
                String designation = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("designation"));
                if (designation.toUpperCase().startsWith(q.toUpperCase())) {
                list.add(designation);
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

 public static List<String> getLocationType(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="SELECT location_type_name FROM location_type"
                    +" GROUP BY location_type_name ORDER BY location_type_name ";
      try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String location_type_name = rset.getString("location_type_name");
                    list.add(location_type_name);
                    count++;
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;

    }

public static List<String> getShiftType(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="SELECT shift_type FROM shift_type"
                    +" GROUP BY shift_type ORDER BY shift_type ";
      try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String shift_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("shift_type"));
                      if (shift_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(shift_type);
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

    public  boolean CheckMapId(ShiftMIS bean,String task,String location_type,String zone,String ward,String area,String location ) {
        int count=0;
        zone=krutiToUnicode.convert_to_unicode(zone);
         ward=krutiToUnicode.convert_to_unicode(ward);
         area=krutiToUnicode.convert_to_unicode(area);
         location=krutiToUnicode.convert_to_unicode(location);
         String designation=krutiToUnicode.convert_to_unicode(bean.getDesignation());
        String addLocation = "";
        if(location_type.equals("city_location"))
            addLocation = "location";
        else
            addLocation = location_type + "_name";
        String loc="";
         if(location_type.equals("zone"))
              loc=zone;
        if(location_type.equals("ward"))
               loc=ward;
         if(location_type.equals("area"))
                loc=area;
          if(location_type.equals("city_location"))
                loc=location;
    try
    {
        String query="SELECT sdlm.map_id1,map_id2 FROM shift_type st, shift_designation_location_map sdlm, designation_location_type dlt,designation d, location_type lt, "+ location_type +" slt "
                    +  " where st.shift_type_id=sdlm.shift_type_id and sdlm.designation_location_type_id=dlt.designation_location_type_id AND dlt.designation_id=d.designation_id "
                    +   " and dlt.location_type_id=lt.location_type_id and slt."+ location_type +"_id = dlt."+ location_type +"_id AND slt."+ addLocation +"='" + loc + "' AND designation='"+designation+"' "; // and map_id1!="+bean.getMap_id1()+" and map_id2!="+bean.getMap_id2()+"  ";
        PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
    ResultSet rs=ps.executeQuery();
    if(rs.next())
    {
        if(task.equals("save")){
            count++;
        }else{
            if(bean.getMap_id1()==rs.getInt(1) && bean.getMap_id2()==rs.getInt(2))
                count++;
        }
    }

    }
    catch (Exception e){
        System.out.print(e);
    }
    if(task.equals("save"))
        return count==0 ? true : false;
    else
        return count > 0 ? true : false;

    }

      public static byte[] generateRecordList(String jrxmlFilePath,List list) {
                byte[] reportInbytes = null;
                HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
                } catch (Exception e) {
                    System.out.println(" generatReport() JRException: " + e);
                }
                return reportInbytes;
            }

      public static ByteArrayOutputStream generateXlsRecordList(String jrxmlFilePath,List list) {
                String reportInbytes = null;
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, mymap, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                     exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                     exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("CityModel generatXlsReportList() JRException: " + e);
                }
                return byteArray;
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
