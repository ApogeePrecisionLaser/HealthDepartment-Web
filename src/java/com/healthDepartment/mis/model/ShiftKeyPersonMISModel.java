/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.model;

import com.healthDepartment.general.model.GeneralModel;
import com.healthDepartment.mis.tableClasses.ShiftKeyPersonMIS;
import com.healthDepartment.shift.model.ShiftLoginModel;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.util.ArrayList;
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
public class ShiftKeyPersonMISModel {
    private static Connection connection;
    private int map_id1;
    private int map_id2;
    private int no_of_person;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    private boolean personflag=true;

    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

     public static int  getNoOfRows(String location_type,String search_shift_type,String search_designation,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName,String searchperson,String searchdate,String emp_code){
          search_shift_type = krutiToUnicode.convert_to_unicode(search_shift_type);
         searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
       search_designation = krutiToUnicode.convert_to_unicode(search_designation);
       searchperson = krutiToUnicode.convert_to_unicode(searchperson);

//       info_designation = krutiToUnicode.convert_to_unicode(info_designation);
//       //info_location_type = krutiToUnicode.convert_to_unicode(info_location_type);
//       info_zone_name = krutiToUnicode.convert_to_unicode(info_zone_name);
//       info_ward_name = krutiToUnicode.convert_to_unicode(info_ward_name);
//       info_area_name = krutiToUnicode.convert_to_unicode(info_area_name);
         int noOfRows = 0;
         if(!searchdate.isEmpty())
            try {
            searchdate = new GeneralModel().convertToSqlDate(searchdate).toString();
        } catch (ParseException ex) {
            Logger.getLogger(ShiftLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            String query="Select count(*)from (select skpm.shift_key_person_map_id "
 + " from shift_key_person_map as skpm "
 + " left join shift_designation_location_map as sdlm on sdlm.map_id1=skpm.map_id1 and sdlm.map_id2=skpm.map_id2 "
 + " left join shift_type as st on st.shift_type_id=sdlm.shift_type_id "
 + " left join designation_location_type as dlt on sdlm.designation_location_type_id=dlt.designation_location_type_id "
 + "  left join location_type as lt on dlt.location_type_id=lt.location_type_id "
 + " left join designation as d on dlt.designation_id=d.designation_id "
 + " left join zone as z on dlt.zone_id=z.zone_id "
 + " left join ward as w on dlt.ward_id=w.ward_id "
 + " left join area as a on dlt.area_id=a.area_id "
 + " left join city_location as cl on dlt.city_location_id=cl.city_location_id "
 + " left join key_person as kp on skpm.key_person_id= kp.key_person_id "
 + " where skpm.active='y' " + addloc
 + " AND IF('"+ search_shift_type +"'='', st.shift_type LIKE '%%', st.shift_type='"+ search_shift_type +"')"
 + " AND IF('"+ search_designation +"'='', d.designation LIKE '%%', d.designation='"+ search_designation +"')"
 + " AND IF('"+ searchperson +"'='', kp.key_person_name LIKE '%%', kp.key_person_name='"+ searchperson +"')"
 + " AND IF('"+ emp_code +"'='', kp.emp_code LIKE '%%', kp.emp_code='"+ emp_code +"')"
 + " AND IF('"+ searchdate +"'='', skpm.date LIKE '%%', DATE_FORMAT(date,'%Y-%m-%d') ='"+ searchdate +"')"
 + "  group by shift_key_person_map_id) as t_no ";

           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println( e);}
         System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

 public static List<ShiftKeyPersonMIS> showData(int lowerLimit,int noOfRowsToDisplay,String location_type,String search_shift_type,String search_designation,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName,String searchperson,String searchdate,String emp_code){

         search_shift_type = krutiToUnicode.convert_to_unicode(search_shift_type);
         searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
         searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
         searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
         searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
         search_designation = krutiToUnicode.convert_to_unicode(search_designation);
         searchperson = krutiToUnicode.convert_to_unicode(searchperson);

//         info_designation = krutiToUnicode.convert_to_unicode(info_designation);
//      // info_location_type = krutiToUnicode.convert_to_unicode(info_location_type);
//       info_zone_name = krutiToUnicode.convert_to_unicode(info_zone_name);
//       info_ward_name = krutiToUnicode.convert_to_unicode(info_ward_name);
//       info_area_name = krutiToUnicode.convert_to_unicode(info_area_name);

         List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
             if(!searchdate.isEmpty())
            try {
            searchdate = new GeneralModel().convertToSqlDate(searchdate).toString();
        } catch (ParseException ex) {
            Logger.getLogger(ShiftLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
                   String addloc="";
          if(location_type.equals("zone"))
              addloc=" and lt.location_type_name='"+location_type+"' and z.zone_name='"+searchZoneName+"' ";
          if(location_type.equals("ward"))
              addloc=" and lt.location_type_name='"+location_type+"' and w.ward_name='"+searchWardName+"' ";
            if(location_type.equals("area"))
              addloc=" and lt.location_type_name='"+location_type+"' and a.area_name='"+searchAreaName+"' ";
             if(location_type.equals("city_location"))
              addloc=" and lt.location_type_name='"+location_type+"' and cl.location='"+searchCityName+"' ";

            String query= "Select skpm.shift_key_person_map_id,st.shift_type,d.designation,lt.location_type_name,z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_name,a.area_no,cl.location,cl.location_no,kp.key_person_name,kp.emp_code,skpm.date,skpm.remark "
                             + " from shift_key_person_map as skpm "
                             + " left join shift_designation_location_map as sdlm on sdlm.map_id1=skpm.map_id1 "
                             + "  and  sdlm.map_id2=skpm.map_id2 "
                             + " left join shift_type as st on st.shift_type_id=sdlm.shift_type_id "
                             + " left join designation_location_type as dlt on sdlm.designation_location_type_id=dlt.designation_location_type_id "
                             + "  left join location_type as lt on dlt.location_type_id=lt.location_type_id "
                             + " left join designation as d on dlt.designation_id=d.designation_id "
                             + " left join zone as z on dlt.zone_id=z.zone_id "
                             + " left join ward as w on dlt.ward_id=w.ward_id "
                             + " left join area as a on dlt.area_id=a.area_id "
                             + " left join city_location as cl on dlt.city_location_id=cl.city_location_id "
                             + " left join key_person as kp on skpm.key_person_id= kp.key_person_id "
                             + " where skpm.active='y' " + addloc
                             + " AND IF('"+ search_shift_type +"'='', st.shift_type LIKE '%%', st.shift_type='"+ search_shift_type +"')"
                             + " AND IF('"+ search_designation +"'='', d.designation LIKE '%%', d.designation='"+ search_designation +"')"
                             + " AND IF('"+ searchperson +"'='', kp.key_person_name LIKE '%%', kp.key_person_name='"+ searchperson +"')"
                             + " AND IF('"+ emp_code +"'='', kp.emp_code LIKE '%%', kp.emp_code='"+ emp_code +"')"
                             + " AND IF('"+ searchdate +"'='', skpm.date LIKE '%%', DATE_FORMAT(date,'%Y-%m-%d') ='"+ searchdate +"')"
                             + "group by shift_key_person_map_id "
                             + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);

         ResultSet rs =ps.executeQuery();
         while(rs.next()){
             ShiftKeyPersonMIS skpm=new ShiftKeyPersonMIS();
             skpm.setShift_key_person_map_id(rs.getInt("shift_key_person_map_id"));
             skpm.setShift_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("shift_type")));
             skpm.setDesignation(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("designation")));
             skpm.setLocation_type(rs.getString("location_type_name"));
             String location_type_name = rs.getString("location_type_name");
             skpm.setZone(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("zone_name")));
             skpm.setZone_no(rs.getString("zone_no"));
             skpm.setLocation(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("location")));
             skpm.setLocation_no(rs.getString("location_no"));
             if(location_type_name.equals("city_location") || location_type_name.equals("area") || location_type_name.equals("ward") )
                 getName(skpm , location_type_name,rs.getString("ward_name"),rs.getString("area_name"),rs.getString("location"));
             skpm.setPerson(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name")));
             skpm.setPerson_code(rs.getInt("emp_code"));
             String date=""+rs.getDate("date");
              if(date != null && !date.isEmpty()){
                String[] date_array  = date.split("-");
                date = date_array[2] + "-" + date_array[1] + "-" +  date_array[0];
                }
              skpm.setDate(date);
              skpm.setRemark(rs.getString("remark"));
              list.add(skpm);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

 public static void getName(ShiftKeyPersonMIS skpm, String location_type,String ward,String area,String location){
      String query = "";
      if(location_type.equals("city_location"))
          query="select z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_name,a.area_no from city_location as cl,zone as z,ward as w,area as a"
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
                     skpm.setWard(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("ward_name")));
                     skpm.setWard_no(rs.getString("ward_no"));
                     skpm.setZone(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("zone_name")));
                     skpm.setZone_no(rs.getString("zone_no"));
                 }
                     if(location_type.equals("area") || location_type.equals("city_location"))
                         {
                       skpm.setArea(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("area_name")));
                       skpm.setArea_no(rs.getString("area_no"));
                         }
          }
        } catch (Exception e) {
            System.out.println( e);
        }
    }


     public boolean insertRecord(ShiftKeyPersonMIS bean){
         boolean status=false;
        int rowsAffected=0;
        int count=0;
        int skpm=0;
         try{
             getMap_id1(bean.getShift_type(),bean.getLocation_type(),bean.getZone(),bean.getWard(),bean.getArea(),bean.getLocation(),bean.getDesignation());
             String date = new GeneralModel().convertToSqlDate(bean.getDate()).toString();
             int key_person_id=0;
             key_person_id=getKey_Person_id(krutiToUnicode.convert_to_unicode(bean.getPerson()));
              count = checkRecord(date,key_person_id);
             if(count <= no_of_person && personflag){
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement("insert into shift_key_person_map (key_person_id,date,remark,map_id1,map_id2) values(?,?,?,?,?)");

             ps.setInt(1,key_person_id);
             ps.setString(2, date);
             ps.setString(3,bean.getRemark());
             ps.setInt(4,map_id1);
             ps.setInt(5,map_id2);
             rowsAffected = ps.executeUpdate();
             ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                skpm = rs.getInt(1);
             PreparedStatement psmt=(PreparedStatement) connection.prepareStatement("insert into attendence (shift_key_person_map_id,attendence) values(?,?)");
             psmt.setInt(1,skpm);
              psmt.setString(2,"N");
             rowsAffected = psmt.executeUpdate();
             if(rowsAffected > 0)
                 status=true;
             }

         }catch(Exception e){
             System.out.println("ERROR: " + e);
         }
         if (rowsAffected > 0) {
             message = "Record Inserted successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Inserted");
        }
         else if(count < no_of_person && personflag==true ){
             message = "Assigned Value is less";
            msgBgColor = COLOR_OK;
            System.out.println("not Inserted");
        }
          else if(personflag==false){
             message = "Key Person is already Exits";
            msgBgColor = COLOR_OK;
            System.out.println("not Inserted");
        }
         else {
             message = " assigned value is less";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Inserted");
        }
         return status;
     }

     public int checkRecord(String date,int key_person_id){
         int records = 0;
         String query = "SELECT  skpm.key_person_id FROM shift_key_person_map as skpm , key_person as kp "
                 + " WHERE skpm.key_person_id=kp.key_person_id AND date='"+date+"'  and active='Y'";
         try{
             ResultSet rs = connection.prepareStatement(query).executeQuery();
             while(rs.next())
             {
                 if(rs.getInt(1)==key_person_id)
                 {
                 personflag=false;
                 records++;
                 }
             }

         }catch(Exception ex){
             System.out.println("ERROR: in checkRecord in ShiftKeyPersonMapModel : " + ex);
         }
         return records;
     }


     public int getMap_id1(String shift_type,String location_type,String zone,String ward,String area,String location,String designation) {
         shift_type = krutiToUnicode.convert_to_unicode(shift_type);
          zone=krutiToUnicode.convert_to_unicode(zone);
         ward=krutiToUnicode.convert_to_unicode(ward);
         area=krutiToUnicode.convert_to_unicode(area);
         location=krutiToUnicode.convert_to_unicode(location);
         designation=krutiToUnicode.convert_to_unicode(designation);
         int map_id1 = 0;
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

        try {

            String query = "SELECT sdlm.map_id1,sdlm.map_id2,no_of_person FROM shift_type st, shift_designation_location_map sdlm, designation_location_type dlt,designation d,location_type lt, "+ location_type +" slt "
                    +  " where st.shift_type_id=sdlm.shift_type_id and sdlm.designation_location_type_id=dlt.designation_location_type_id AND dlt.designation_id=d.designation_id"
                    +   " and dlt.location_type_id=lt.location_type_id and slt."+ location_type +"_id = dlt."+ location_type +"_id AND slt."+ addLocation +"='" + loc + "' and designation='"+designation+"' and active='y' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {
                this.map_id1 = rset.getInt("map_id1");
                map_id2 = rset.getInt("map_id2");
                no_of_person = rset.getInt("no_of_person");
            }
        } catch (Exception e) {
        }
        return map_id1;
    }



 public static List<String> getDesignation(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="SELECT designation FROM designation d,shift_designation_location_map as sdlm,designation_location_type as dlt "
   + " where sdlm.designation_location_type_id=dlt.designation_location_type_id and dlt.designation_id=d.designation_id  GROUP BY designation ORDER BY designation; ";
      try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String designation =unicodeToKruti.Convert_to_Kritidev_010(rset.getString("designation"));
                   if(designation.toUpperCase().startsWith(q.toUpperCase())){
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
public static List<String> getShiftType(String q)
    {
     List<String> list=new ArrayList<String>();
   String query ="select shift_type from shift_type as st,shift_designation_location_map as sdlm  "
+ " where sdlm.shift_type_id=st.shift_type_id GROUP BY shift_type ORDER BY shift_type; ";
      try {
                    ResultSet rset = connection.prepareStatement(query).executeQuery();
                    int count = 0;
                    q = q.trim();
                    while (rset.next()) {
                     String shift_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("shift_type"));
                       if(shift_type.toUpperCase().startsWith(q.toUpperCase())){
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

public static List<String> getPerson(String q, String designation,String emp_code)
    {
     List<String> list=new ArrayList<String>();
     designation = krutiToUnicode.convert_to_unicode(designation);
   String query = " select key_person_name from key_person kp, designation d,shift_designation_location_map as sdlm,designation_location_type as dlt "
         + "  WHERE d.designation_id=kp.designation_id and d.designation_id=dlt.designation_id and "
          + " sdlm.designation_location_type_id=dlt.designation_location_type_id "
                + " AND IF('"+ emp_code +"'='', kp.emp_code LIKE '%%', kp.emp_code='"+ emp_code +"')"
           + " AND IF('"+ designation +"'='', d.designation LIKE '%%', d.designation='"+ designation +"') GROUP BY key_person_name";
      try {

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
                if(key_person_name.toUpperCase().startsWith(q.toUpperCase())){
                    list.add(key_person_name);
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


public static int getKey_Person_id(String key_person_name) {
        int key_person_id = 0;
        try {
            String person_name=krutiToUnicode.convert_to_unicode(key_person_name);
            String query = "select key_person_id from key_person"
                    +" where key_person_name='"+person_name+"' ";
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                key_person_id = rset.getInt("key_person_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return key_person_id;
    }

 public int deleteRecord(String shift_key_person_map_id) {

        String query = "UPDATE shift_key_person_map set active = 'N' WHERE  shift_key_person_map_id= "+shift_key_person_map_id+" " ;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println( e);
        }
          if (rowsAffected > 0) {
             message = "Record Deleted successfully......";
             msgBgColor = COLOR_OK;
             System.out.println("Deleted");
        } else {
             message = "Record Not Deleted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Deleted");}
       return rowsAffected;
    }
  public static List<String> getZone(String q,String zone_no){
     List<String> list=new ArrayList<String>();
   String query =" SELECT zone_name FROM zone as z "
            // + " where sdlm.designation_location_type_id=dlt.designation_location_type_id and dlt.zone_id=z.zone_id "
                   +  " where  IF('" + zone_no + "'='', zone_no like '%%', zone_no ='" + zone_no + "') group by zone_name " ;
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
               +  "WHERE  IF('" + zone + "'='', zone_name like '%%', zone_name ='" + zone + "') "
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
                 + "  WHERE   w.zone_id = z.zone_id "
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

   public static List<String> getArea(String q,String ward,String zone,String area_no){
     List<String> list=new ArrayList<String>();
        zone=krutiToUnicode.convert_to_unicode(zone);
         ward=krutiToUnicode.convert_to_unicode(ward);
   String query =" SELECT a.area_name "
               + " FROM area AS a ,ward AS w, zone AS z"
               + " WHERE a.ward_id = w.ward_id "
             + "  AND w.zone_id = z.zone_id "
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

      public static List<String> getlocation(String q,String area,String ward,String zone,String location_no){
             List<String> list=new ArrayList<String>();
        zone=krutiToUnicode.convert_to_unicode(zone);
         ward=krutiToUnicode.convert_to_unicode(ward);
         area=krutiToUnicode.convert_to_unicode(area);

            String query =" SELECT cl.location "
              + " FROM area AS a ,ward AS w, zone AS z,city_location as cl,shift_designation_location_map as sdlm,designation_location_type as dlt  "
             + " WHERE sdlm.designation_location_type_id=dlt.designation_location_type_id and dlt.city_location_id=cl.city_location_id and cl.area_id=a.area_id "
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

    public static List<String> getperson_code(String q,String designation,String person_name) {
        List<String> list = new ArrayList<String>();
     designation = krutiToUnicode.convert_to_unicode(designation);
          person_name = krutiToUnicode.convert_to_unicode(person_name);
   String query = " select emp_code from key_person kp, designation d,shift_designation_location_map as sdlm,designation_location_type as dlt "
         + "  WHERE d.designation_id=kp.designation_id and d.designation_id=dlt.designation_id and "
          + " sdlm.designation_location_type_id=dlt.designation_location_type_id "
            + " AND IF('"+ person_name +"'='', kp.key_person_name LIKE '%%', kp.key_person_name='"+ person_name +"')"
           + " AND IF('"+ designation +"'='', d.designation LIKE '%%', d.designation='"+ designation +"') GROUP BY key_person_name";
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
