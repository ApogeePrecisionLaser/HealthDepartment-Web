/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.model;

import com.healthDepartment.general.model.GeneralModel;
import com.healthDepartment.mis.tableClasses.ShiftBenificiaryDetailMIS;
import com.healthDepartment.shift.model.ShiftTimeModel;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Shobha
 */
public class ShiftBenificiaryDetailMISModel {
private static Connection connection;
    private String message;
    private String msgBgColor;
    private String st;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
    private boolean personflag=true;

   public int  getNoOfRows(String searchdate,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName,String searchemp,String mobileno ,String occupationtype) {
        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
            searchemp = krutiToUnicode.convert_to_unicode(searchemp);
             occupationtype = krutiToUnicode.convert_to_unicode(occupationtype);
        int noOfRows = 0;
        try {
            if(!searchdate.isEmpty())
            searchdate = new GeneralModel().convertToSqlDate(searchdate).toString();
       String query= " select count(skpd.shift_key_person_detail_id)"
                      + " from  shift_key_person_detail as skpd "
                      + "  left join beneficiary as b on skpd.beneficiary_id=b.beneficiary_id "
                      + "  left join type_of_occupation  as too on b.type_of_occupation_id=too.type_of_occupation_id "
                      + "  left join shift_key_person_map as skpm on skpd.shift_key_person_map_id=skpm.shift_key_person_map_id "
                      +  "  left join key_person as k on skpm.key_person_id=k.key_person_id "
                      + "   left join city_location as cl on  b.city_location_id=cl.city_location_id "
                      +  " left join area as a on cl.area_id=a.area_id"
                      + "  left join ward as w on a.ward_id=w.ward_id "
                      +  "  left join zone as z on  w.zone_id=z.zone_id "
                     +  "  left join key_person as kp on b.key_person_id=kp.key_person_id "
                     + "  left join reason as r on skpd.reason_id=r.reason_id "
                     +  " where IF('"+ searchdate +"'='', skpd.date LIKE '%%',DATE_FORMAT(skpd.date,'%Y-%m-%d') ='"+ searchdate +"')"
                     + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  =?) "
                     + "And IF('" + searchZoneName + "' = '', z.zone_name LIKE '%%', z.zone_name =?) "
                     + "And IF('" + searchWardName + "' = '', w.ward_name LIKE '%%', w.ward_name =?) "
                     + "And IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =?) "
                     + " And IF('" + searchemp + "' = '',k.key_person_name LIKE '%%', k.key_person_name='" + searchemp + "') "
                     + " And IF('" + mobileno + "' = '', kp.mobile_no1 LIKE '%%', kp.mobile_no1 ='" + mobileno + "') "
                     + " And IF('" + occupationtype + "' = '', too.name LIKE '%%', too.name ='" + occupationtype + "') " ;

           PreparedStatement pstmt = connection.prepareStatement(query);
               pstmt.setString(1, searchCityName);
               pstmt.setString(2, searchZoneName);
               pstmt.setString(3, searchWardName);
               pstmt.setString(4, searchAreaName);
            ResultSet rset = pstmt.executeQuery();

            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));

        } catch (Exception e) {
            System.out.println("Error:-getNoOfRows-- " + e);
        }
         System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

 public  List<ShiftBenificiaryDetailMIS> showData(int lowerLimit,int noOfRowsToDisplay,String searchdate,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName,String searchemp,String mobileno ,String occupationtype){
       List list = new ArrayList();
         searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
        occupationtype = krutiToUnicode.convert_to_unicode(occupationtype);
        searchemp = krutiToUnicode.convert_to_unicode(searchemp);
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";

             if(!searchdate.isEmpty())
            try {
            searchdate = new GeneralModel().convertToSqlDate(searchdate).toString();
        } catch (ParseException ex) {
            Logger.getLogger(ShiftBenificiaryDetailMISModel.class.getName()).log(Level.SEVERE, null, ex);
        }
            String query= " select skpd.shift_key_person_detail_id,k.key_person_name as cleaner,k.emp_code,kp.key_person_name,kp.father_name,kp.mobile_no1,occupation_name,too.name, "
                            + " cl.location,is_residencial,skpd.date,skpd.status,r.reason_name "
                            + " from  shift_key_person_detail as skpd "
                            + " left join beneficiary as b on skpd.beneficiary_id=b.beneficiary_id "
                            + " left join type_of_occupation  as too on b.type_of_occupation_id=too.type_of_occupation_id "
                            + "  left join shift_key_person_map as skpm on skpd.shift_key_person_map_id=skpm.shift_key_person_map_id "
                            + "  left join key_person as k on skpm.key_person_id=k.key_person_id "
                            + "  left join city_location as cl on  b.city_location_id=cl.city_location_id "
                            + "  left join area as a on cl.area_id=a.area_id "
                            + " left join ward as w on a.ward_id=w.ward_id "
                            + "  left join zone as z on  w.zone_id=z.zone_id "
                            +  " left join key_person as kp on b.key_person_id=kp.key_person_id "
                            + "  left join reason as r on skpd.reason_id=r.reason_id "
                            +  " where IF('"+ searchdate +"'='', skpd.date LIKE '%%', DATE_FORMAT(skpd.date,'%Y-%m-%d') ='"+ searchdate +"') "
                            + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  =?) "
                            + " And IF('" + searchZoneName + "' = '', z.zone_name LIKE '%%', z.zone_name =?) "
                            + " And IF('" + searchWardName + "' = '', w.ward_name LIKE '%%', w.ward_name =?) "
                            + " And IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =?) "
                            + " And IF('" + searchemp + "' = '',k.key_person_name LIKE '%%', k.key_person_name='" + searchemp + "') "
                            + " And IF('" + mobileno + "' = '', kp.mobile_no1 LIKE '%%', kp.mobile_no1 ='" + mobileno + "') "
                            + " And IF('" + occupationtype + "' = '', too.name LIKE '%%', too.name ='" + occupationtype + "') order by cleaner"
                            +  addQuery ;

    try{
       PreparedStatement ps = connection.prepareStatement(query);
                  ps.setString(1, searchCityName);
                  ps.setString(2, searchZoneName);
                  ps.setString(3, searchWardName);
                  ps.setString(4, searchAreaName);
         ResultSet rs =ps.executeQuery();
         while(rs.next()){
             ShiftBenificiaryDetailMIS slb=new ShiftBenificiaryDetailMIS();
               slb.setShift_key_person_detail_id(rs.getInt("shift_key_person_detail_id"));
               slb.setEmp_name(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("cleaner")));
               slb.setEmp_code(rs.getString("emp_code"));
               slb.setName(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("key_person_name")));
               slb.setFather_name(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("father_name")));
               slb.setMobile_no(rs.getString("mobile_no1"));
               slb.setOccupation_name(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("occupation_name")));
               slb.setOccupation_type(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("name")));
               slb.setLocation(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("location")));
               slb.setIs_residencial(rs.getString("is_residencial"));
               String[] datetime=rs.getString("date").split(" ");
               String[] date=datetime[0].split("-");
               String date1=date[2] +"-"+date[1] +"-"+date[0];
               String[] min=datetime[1].split(":");
               String min1=min[0]+":"+min[1];
               String time=date1+ " "+min1;
               slb.setDate(time);
               String  status=rs.getString("status");
               if(status.equals("Y"))
                 slb.setStatus("Yes");
             else if(status.equals("I"))
                 slb.setStatus("Initial");
               else
                  slb.setStatus("No");
                  slb.setReason(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("reason_name")));
              list.add(slb);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }

    public List<String> getOccupationtype(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select name from type_of_occupation as toc,beneficiary as b where toc.type_of_occupation_id=b.type_of_occupation_id group by name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String name =unicodeToKruti.Convert_to_Kritidev_010( rset.getString("name"));
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(name);
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

    public List<String> getmobileno(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select mobile_no1 from key_person as kp, beneficiary as b where b.key_person_id=kp.key_person_id";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String mobile_no1 = rset.getString("mobile_no1");
                if (mobile_no1.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(mobile_no1);
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

        public List<String> getsearchemp(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select kp.key_person_name from key_person as kp,shift_key_person_map as skpm,shift_key_person_detail as skpd "
                        + " where skpm.key_person_id=kp.key_person_id and skpd.shift_key_person_map_id=skpm.shift_key_person_map_id group by key_person_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String key_person_name =unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
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
 public List<ShiftBenificiaryDetailMIS> showDataBean(String emp_code) {
        List<ShiftBenificiaryDetailMIS> list = new ArrayList<ShiftBenificiaryDetailMIS>();
                Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MINUTE, -5);
        int Hr = cal.get(Calendar.HOUR_OF_DAY);
        int Min = cal.get(Calendar.MINUTE);
        int Date = cal.get(Calendar.DATE);
        int Month = cal.get(Calendar.MONTH) + 1;
        int Year = cal.get(Calendar.YEAR);
        String date = Year +"-" + Month + "-" + Date + " " + Hr + ":" + Min + ":00";
        String query = "SELECT * FROM (SELECT cordinate_id, latitude, longitude, imei_no "
               + " FROM cordinate  WHERE created_date >= '"+ date +"' "
               + " AND IF('"+ emp_code +"'='', emp_code LIKE '%%', emp_code='" + emp_code + "') "
              + "  ORDER BY cordinate_id DESC) AS sc GROUP BY imei_no " ;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ShiftBenificiaryDetailMIS bean = new ShiftBenificiaryDetailMIS();
                bean.setCordinate_id(rset.getInt("cordinate_id"));
                bean.setLatitude(rset.getDouble("latitude"));
                bean.setLongitude(rset.getDouble("longitude"));
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return list;
    }
 /*   public JSONObject getPersonDetails(String emp_code){
        JSONObject jsonObj = new JSONObject();
        String query = "select kp.key_person_id,kp.key_person_name,kp.mobile_no1,st.shift_type,kp.address_line1,st.starting_time,st.ending_time,skpm.date"
                       + "  from key_person as kp,shift_key_person_map as skpm,shift_designation_location_map as sdlm,shift_type as st  "
                        +  "  where kp.key_person_id=skpm.key_person_id and skpm.map_id1=sdlm.map_id1 and skpm.map_id2=sdlm.map_id2 and "
                         + "  sdlm.shift_type_id= st.shift_type_id and kp.emp_code='"+emp_code+"' ";
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                jsonObj.put("key_person_id", rs.getInt("key_person_id"));
                jsonObj.put("key_person_name", rs.getString("key_person_name"));
                jsonObj.put("mobile_no1", rs.getString("mobile_no1"));
                jsonObj.put("address_line1", rs.getString("address_line1"));
                jsonObj.put("shift_type", rs.getString("shift_type"));
                jsonObj.put("date", rs.getString("date"));
                jsonObj.put("starting_time", rs.getString("starting_time"));
                jsonObj.put("ending_time", rs.getString("ending_time"));
            }
        }catch(Exception ex){
            System.out.println("ERROR : in getVehicleDetails()  in RideModel : " + ex);
        }
        return jsonObj;
    }*/
    public JSONObject getAreaDetails(String emp_code){
        JSONObject jsonObj = new JSONObject();
        String query = "  select lt.location_type_name,z.zone_name,w.ward_name,a.area_name,cl.location, "
                          + "  kp.key_person_id,kp.key_person_name,kp.emp_code,kp.mobile_no1,st.shift_type, "
                          +  "  kp.address_line1,st.starting_time,st.ending_time,skpm.date "
                          + " from key_person as kp "
                          +  " left join shift_key_person_map as skpm on kp.key_person_id=skpm.key_person_id "
                          + "  left join shift_designation_location_map as sdlm on skpm.map_id1=sdlm.map_id1 and skpm.map_id2=sdlm.map_id2 "
                          +  "  left join designation_location_type as dlt on sdlm.designation_location_type_id=dlt.designation_location_type_id "
                          +  "  left join shift_type as st on sdlm.shift_type_id=st.shift_type_id "
                          + "  left join location_type as lt on dlt.location_type_id=lt.location_type_id "
                          +  " left join zone as z on dlt.zone_id=z.zone_id "
                          +  " left join ward as w on dlt.ward_id=w.ward_id "
                          +  " left join area as a on dlt.area_id=a.area_id "
                          + " left join city_location as cl on dlt.city_location_id=cl.city_location_id "
                          + " where kp.emp_code='"+emp_code+"' " ;

        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                jsonObj.put("key_person_id", rs.getInt("key_person_id"));
                jsonObj.put("key_person_name", rs.getString("key_person_name"));
                jsonObj.put("emp_code", rs.getString("emp_code"));
                jsonObj.put("mobile_no1", rs.getString("mobile_no1"));
                jsonObj.put("address_line1", rs.getString("address_line1"));
                jsonObj.put("shift_type", rs.getString("shift_type"));
                jsonObj.put("date", rs.getString("date"));
                String[] starting_time=rs.getString("starting_time").split(":");
                String s_time=starting_time[0]+":"+starting_time[1];
                jsonObj.put("starting_time",s_time);
                String[] ending_time= rs.getString("ending_time").split(":");
                String e_time=ending_time[0]+":"+ending_time[1];
                jsonObj.put("ending_time",e_time);
                    String zone_name = rs.getString("zone_name");
                    String ward_name = rs.getString("ward_name");
                    String area_name = rs.getString("area_name");
                    String location = rs.getString("location");
                    String location_type_name = rs.getString("location_type_name");
                    if(location_type_name.equals("zone") || location_type_name.equals("area") || location_type_name.equals("ward")|| location_type_name.equals("city_location") )
                    jsonObj=getName(jsonObj,location_type_name,zone_name,ward_name,area_name,location);
            }
        }catch(Exception ex){
            System.out.println("ERROR : in getAreaDetails()  in ShiftLoginModel : " + ex);
        }
        return jsonObj;
    }

   public static JSONObject getName(  JSONObject jsonObj,String location_type,String zone,String ward,String area,String location ){
    String query = "";
          if(location_type.equals("city_location"))
           query="select z.zone_id,z.zone_name,z.zone_no,w.ward_id,w.ward_name,w.ward_no,a.area_id,a.area_name,a.area_no,cl.location_no from city_location as cl,zone as z,ward as w,area as a"
                    +" where cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id and location='"+location+"' ";

        if(location_type.equals("area"))
          query="select z.zone_id,z.zone_name,z.zone_no,w.ward_id,w.ward_name,w.ward_no,a.area_id,a.area_no from zone as z,ward as w,area as a"
                    +" where a.ward_id=w.ward_id and w.zone_id=z.zone_id and area_name='"+area+"' ";

          if(location_type.equals("ward"))
          query="select z.zone_id,z.zone_name,z.zone_no,w.ward_id,w.ward_no from zone as z,ward as w "
                    +" where w.zone_id=z.zone_id and ward_name='"+ward+"' ";

        if(location_type.equals("zone"))
          query="Select z.zone_id,zone_no from zone "
                + " where zone_name='"+zone+"' ";
        try {
             ResultSet rs = connection.prepareStatement(query).executeQuery();
             while(rs.next()){
                   if(location_type.equals("city_location"))
                      {
                          jsonObj.put("zone_id", rs.getInt("zone_id"));
                          jsonObj.put("zone_name", rs.getString("zone_name"));
                          jsonObj.put("zone_no", rs.getString("zone_no"));
                          jsonObj.put("ward_id", rs.getInt("ward_id"));
                          jsonObj.put("ward_name", rs.getString("ward_name"));
                          jsonObj.put("ward_no", rs.getString("ward_no"));
                          jsonObj.put("area_name", rs.getString("area_name"));
                          jsonObj.put("area_no", rs.getString("area_no"));
                          jsonObj.put("location_no",rs.getString("location_no"));
                          jsonObj.put("location", location);
                     }
                      if(location_type.equals("area"))
                      {
                          jsonObj.put("zone_id", rs.getInt("zone_id"));
                          jsonObj.put("zone_name", rs.getString("zone_name"));
                          jsonObj.put("zone_no",rs.getString("zone_no"));
                          jsonObj.put("ward_id", rs.getInt("ward_id"));
                          jsonObj.put("ward_name",rs.getString("ward_name"));
                          jsonObj.put("ward_no", rs.getString("ward_no"));
                          jsonObj.put("area_id", rs.getInt("area_id"));
                          jsonObj.put("area_no", rs.getString("area_no"));
                          jsonObj.put("area_name", area);
                     }
                     if(location_type.equals("ward"))
                      {
                         jsonObj.put("zone_id", rs.getInt("zone_id"));
                         jsonObj.put("zone_name", rs.getString("zone_name"));
                         jsonObj.put("zone_no", rs.getString("zone_no"));
                         jsonObj.put("ward_id", rs.getInt("ward_id"));
                         jsonObj.put("ward_no", rs.getString("ward_no"));
                         jsonObj.put("ward_name", ward);
                     }
                        if(location_type.equals("zone"))
                      {
                       jsonObj.put("zone_id", rs.getInt("zone_id"));
                       jsonObj.put("zone_no", rs.getString("zone_no"));
                       jsonObj.put("zone_name", zone);
                     }
                 }
        } catch (Exception e) {
            System.out.println( e);
        }
    return jsonObj;
    }
    public JSONArray getBeneficiaryDetails(String zone,String ward,String area){
    JSONArray jsonArray = new JSONArray();
        String query = "   select beneficiary_id,kp.key_person_id,kp.salutation,kp.key_person_name,kp.age,kp.email_id1,kp.father_name, "
                         + "   kp.mobile_no1,cl.city_location_id,kp.address_line1,kp.address_line2,is_residencial,b.occupation_name,too.name,b.no_of_person "
                        + "  from  beneficiary as b,city_location as cl,area as a,ward as w,zone as z,key_person as kp,type_of_occupation as too "
                        + "   where b.city_location_id=cl.city_location_id and cl.area_id=a.area_id and b.type_of_occupation_id=too.type_of_occupation_id and "
                        + "  a.ward_id=w.ward_id and w.zone_id=z.zone_id and b.key_person_id=kp.key_person_id "
                        +  "  and zone_name='"+zone+"' and ward_name='"+ward+"' and area_name='"+area+"' ";

        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("beneficiary_id", rs.getInt("beneficiary_id"));
            jsonObj.put("key_person_id", rs.getInt("key_person_id"));
            jsonObj.put("salutation", rs.getString("salutation"));
            jsonObj.put("key_person_name", rs.getString("key_person_name"));
            jsonObj.put("age", rs.getInt("age"));
            jsonObj.put("email_id1", rs.getString("email_id1"));
            jsonObj.put("father_name", rs.getString("father_name"));
            jsonObj.put("mobile_no1", rs.getString("mobile_no1"));
            jsonObj.put("occupation_name", rs.getString("occupation_name"));
            jsonObj.put("occupation_type", rs.getString("name"));
            jsonObj.put("address", rs.getString("address_line1"));
            jsonObj.put("address_line2", rs.getString("address_line2"));
            jsonObj.put("city_location_id", rs.getInt("city_location_id"));
            jsonObj.put("is_residencial", rs.getString("is_residencial"));
            jsonObj.put("no_of_person", rs.getInt("no_of_person"));
            jsonArray.add(jsonObj);
            }
        }catch(Exception ex){
            System.out.println("ERROR : in getBeneficiaryDetails  in ShiftLoginModel : " + ex);
        }
        return  jsonArray;
    }

     public JSONArray getCityDetails(String zone,String ward,String area){
      JSONArray jsonArray = new JSONArray();
        String query = "  select cl.city_location_id,cl.location,location_no,cl.latitude,cl.longitude from city_location as cl,area as a,ward as w,zone as z "
                        + " where  cl.area_id=a.area_id and  a.ward_id=w.ward_id and w.zone_id=z.zone_id and zone_name='"+zone+"' and ward_name='"+ward+"' and area_name='"+area+"' " ;

        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("city_location_id", rs.getInt("city_location_id"));
            jsonObj.put("location", rs.getString("location"));
            jsonObj.put("location_no", rs.getString("location_no"));
            jsonObj.put("latitude", rs.getString("latitude"));
            jsonObj.put("longitude", rs.getString("longitude"));
            jsonArray.add(jsonObj);
            }
        }catch(Exception ex){
            System.out.println("ERROR : in getBeneficiaryDetails  in ShiftLoginModel : " + ex);
        }
        return  jsonArray;
    }

     public JSONArray getReasonDetails(){
         JSONArray jsonArray = new JSONArray();
        String query = "select reason_id,reason_name from reason" ;

        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("reason_id", rs.getInt("reason_id"));
            jsonObj.put("reason_name", rs.getString("reason_name"));
            jsonArray.add(jsonObj);
            }
        }catch(Exception ex){
            System.out.println("ERROR : in getReasonDetails  in ShiftLoginModel : " + ex);
        }
        return  jsonArray;
    }
     public JSONArray getOccupationTypeDetails(){
         JSONArray jsonArray = new JSONArray();
        String query = "select type_of_occupation_id,name from type_of_occupation;" ;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("type_of_occupation_id", rs.getInt("type_of_occupation_id"));
            jsonObj.put("occupation_type", rs.getString("name"));
            jsonArray.add(jsonObj);
            }
        }catch(Exception ex){
            System.out.println("ERROR : in getOccupationTypeDetails  in ShiftLoginModel : " + ex);
        }
        return  jsonArray;
    }
       public int insertShiftRecord(String beneficiary_id, String reason_id, String date_time, String emp_code, String status){
        int rowAffected = 0;
        String reason="";
         String query="insert into shift_key_person_detail (shift_key_person_map_id,beneficiary_id,status,date,reason_id) values(?,?,?,?,?)";

        try{
            PreparedStatement pstmt = connection.prepareStatement(query);
           String[] d_time=date_time.split(" ");
           String date=d_time[0];
            int shift_key_person_map_id=getShiftKeyPersonMapId(emp_code,date);
                pstmt.setInt(1, shift_key_person_map_id);
                pstmt.setString(2, beneficiary_id);
                pstmt.setString(3, status);
                pstmt.setString(4, date_time);
                if(Integer.parseInt(reason_id)==0)
                    pstmt.setNull(5, java.sql.Types.NULL);
                else {
                  pstmt.setInt(5, Integer.parseInt(reason_id));
                 }
              String mobile_no=getMobileNO(beneficiary_id);
               rowAffected = pstmt.executeUpdate();
              ShiftTimeModel stm=new ShiftTimeModel();
              stm.setConnection(connection);
              if(status.equals("Y"))
              {
            stm.sendSmsToAssignedFor(mobile_no," जबलपुर नगर निगम की सफाई में सहायता करने के लिए आपका धन्यवाद ");

               }
              else
              {
             reason =  stm.getReason(reason_id);
           stm.sendSmsToAssignedFor(mobile_no, reason+" के कारण जबलपुर नगर निगम की सफाई में आपकी सहायता नही मिल पाई ");
              }
        }catch(Exception ex){
            System.out.println("ERROR  : "+ ex);
        }
        return rowAffected;
    }

          public static int getShiftKeyPersonMapId(String emp_code,String date)
        {
        int shift_key_person_map_id = 0;
        try {
            String  query= "select shift_key_person_map_id from shift_key_person_map as skpm,key_person as kp where skpm.key_person_id=kp.key_person_id "
                              +  " and kp.emp_code='"+emp_code+"' and skpm.date='"+date+"' ";
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                shift_key_person_map_id = rset.getInt("shift_key_person_map_id");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return shift_key_person_map_id;
    }

    public int insertKeyPerson(String salutation,String person_name,String father_name,String age,String address_line1,String address_line2,String mobile_no1,String email_id1,String latitude,String longitude,String key_person_id,String occupation_type,String occupation_name,String no_of_person,String city_location,String is_residencial) throws SQLException {
        int rowsAffected = 0;
        String query="";
        PreparedStatement pstmt = null;
        int emp_code=0;
        try{
            connection.setAutoCommit(false);
            int person_id=Integer.parseInt(key_person_id);
            if(person_id==0)
            query = "INSERT INTO key_person( salutation, key_person_name, designation_id, org_office_id, city_id, address_line1, "
                    + "address_line2, "
                    + " mobile_no1,email_id1, emp_code, father_name, age,latitude,longitude) "
                    + "VALUES( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
            if(person_id!=0)
               query = "UPDATE key_person SET  salutation=?, key_person_name=?, designation_id=?, org_office_id=?, city_id=?, address_line1=?, "
                + "address_line2=?, "
                + " mobile_no1=?, email_id1=?,emp_code=?, father_name=?, age=?,latitude=?,longitude=? "
                + " WHERE key_person_id=?";

            pstmt = connection.prepareStatement(query);
            pstmt.setString(1, salutation);
            pstmt.setString(2,person_name);
            pstmt.setInt(3, 12);
            pstmt.setInt(4,18);
            pstmt.setInt(5,2);
            pstmt.setString(6,address_line1);
            pstmt.setString(7,address_line2);
            pstmt.setString(8, mobile_no1);
            pstmt.setString(9,email_id1 );
            if(person_id==0)
            {
            emp_code=getMaxEmpCode() + 1;
            pstmt.setInt(10, emp_code);
            }
           else{
            emp_code=getEmpCode(person_id) ;
            pstmt.setInt(10, emp_code);
            pstmt.setInt(15, person_id);
           }
            pstmt.setString(11,father_name);
            int age1=Integer.parseInt(age);
            pstmt.setInt(12, age1);
            if(latitude!="")
            {
            double  latitude1=Double.parseDouble(latitude);
            pstmt.setDouble(13,latitude1 );
            }
          else
            pstmt.setNull(13, java.sql.Types.NULL);
            if(longitude!="")
            {
            double  longitude1=Double.parseDouble(longitude);
             pstmt.setDouble(14, longitude1);
              }
            else
            pstmt.setNull(14, java.sql.Types.NULL);
            rowsAffected = pstmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error:keypersonModel--insertRecord-- " + e);
        }
        if(rowsAffected > 0)
        {
          int i=insertBeneficiary(occupation_type,occupation_name,no_of_person,city_location,is_residencial,key_person_id);
           if(i>0)
            {
              connection.commit();
              message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
             }
            else
            {
            connection.rollback();
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
            }
        } else {
            connection.rollback();
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }
     public  int insertBeneficiary(String occupation_type,String occupation_name,String no_of_person,String city_location,String is_residencial,String key_person_id)
 {

        int rowsAffected=0;
        int person_id=Integer.parseInt(key_person_id);
        String query="";
        try{
          if(person_id==0)
          query="insert into beneficiary (occupation_name,key_person_id,is_residencial,no_of_person,type_of_occupation_id,city_location_id) values(?,?,?,?,?,?)";
          if(person_id!=0)
          query="update beneficiary set occupation_name=?,key_person_id=?,is_residencial=?,no_of_person=?,type_of_occupation_id=?,city_location_id=? where key_person_id=? ";
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
         ps.setString(1,occupation_name);
         if(person_id == 0)
         ps.setNull(2, java.sql.Types.NULL);
         else
         ps.setInt(2, person_id);
         if(is_residencial.equals("Yes"))
         ps.setString(3,"Y");
         else
         ps.setString(3,"N");
         int no_person=Integer.parseInt(no_of_person);
         ps.setInt(4, no_person);
         int occupation_type1=Integer.parseInt(occupation_type);
         if(occupation_type1 == 0)
         ps.setNull(5, java.sql.Types.NULL);
         else
         ps.setInt(5, occupation_type1);
         int city_location_id=getCityLocationId(city_location);
         ps.setInt(6, city_location_id);
         if(person_id!=0)
           ps.setInt(7, person_id);
         rowsAffected = ps.executeUpdate();
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
  public static int getCityLocationId(String location) {
        int city_location_id = 0;
        try {
               location=krutiToUnicode.convert_to_unicode(location);
               String query = "select city_location_id from city_location"
                    +" where location='"+location+"' ";
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return city_location_id;
    }
  public  int insertCordinate(String latitude,String longitude,String emp_code,String imei_no,String mobile_no)
           {
        int rowsAffected=0;
        String query="insert into cordinate(latitude,longitude,emp_code,imei_no,mobile_no) values(?,?,?,?,?)";
        try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             double  latitude1=Double.parseDouble(latitude);
            if(latitude1==0)
               ps.setNull(1, java.sql.Types.NULL);
            else
               ps.setDouble(1,latitude1 );
            double  longitude1=Double.parseDouble(longitude);
            if(longitude1==0)
              ps.setNull(2, java.sql.Types.NULL);
            else
              ps.setDouble(2,longitude1);
             int code=Integer.parseInt(emp_code);
              ps.setInt(3,code);
              ps.setString(4,imei_no);
              ps.setString(5,mobile_no);
        rowsAffected = ps.executeUpdate();

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
    public  int updateAttendence(String skpm_id)
           {
        int rowsAffected=0;
        String query="update attendence set attendence='Y' where shift_key_person_map_id=? ";
        try{
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
          ps.setInt(1,Integer.parseInt(skpm_id));
        rowsAffected = ps.executeUpdate();

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
    public String getskpmId(String emp_code) {
        int skpm_id = 0;
        String attendence="";
        String id_attendence="";
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String current_date = dateFormat.format(date);
            String query = "select a.shift_key_person_map_id,attendence from attendence as a,shift_key_person_map as skpm,key_person as kp "
                              + " where a.shift_key_person_map_id=skpm.shift_key_person_map_id and skpm.key_person_id=kp.key_person_id and kp.emp_code='"+emp_code+"' and skpm.date='"+current_date+"' ";
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                skpm_id = rset.getInt("shift_key_person_map_id");
                attendence = rset.getString("attendence");
            }
            id_attendence=skpm_id+"_"+attendence;
        } catch (Exception e) {
            System.out.println(e);
        }
        return id_attendence;
    }
        public String getMobileNO(String beneficiary_id) {
       String mobile_no="";
        try {
            String query = "select mobile_no1 from key_person as kp, beneficiary as b where b.key_person_id=kp.key_person_id and b.beneficiary_id="+beneficiary_id;
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                mobile_no = rset.getString("mobile_no1");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return mobile_no;
    }
    public List<String> getLocationCordinates(String zone,String ward,String area) {
        List<String> list = new ArrayList<String>();
        String destination="";
        String query = " select cl.latitude,cl.longitude from city_location as cl,area as a,ward as w,zone as z,beneficiary as b where "
                         + "   b.city_location_id=cl.city_location_id and cl.area_id=a.area_id and "
                         + "   a.ward_id=w.ward_id and w.zone_id=z.zone_id   and zone_name='"+zone+"' and ward_name='"+ward+"' and area_name='"+area+"' ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;

            while (rset.next()) {
                 destination = rset.getString("latitude")+","+rset.getString("longitude");
                    list.add(destination);
                    count++;
            }
            if (count == 0) {
                list.add("No such record exists.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

     public static int getMaxEmpCode()
     {
         int emp_code=0;
        try {
            String  query= "SELECT max(emp_code) as emp_code FROM key_person k" ;
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                emp_code = rset.getInt("emp_code");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return emp_code;
    }
      public static int getEmpCode(int person_id)
     {
         int emp_code=0;
        try {
            String  query= "SELECT emp_code FROM key_person k where key_person_id="+person_id ;
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                emp_code = rset.getInt("emp_code");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return emp_code;
    }

 public int insertAllRemainingBeneficary(String emp_id, String zone, String ward, String area){
    int rowAffected = 0;
    int mapID  = getKeyPersonMapId(emp_id);
    String beneficiaryid = getAllBeneficiary();
    String addQuery = "and beneficiary_id not in("+ beneficiaryid +")";
    if(beneficiaryid.isEmpty())
        addQuery = "";
    String query = " insert into shift_key_person_detail (shift_key_person_map_id,beneficiary_id,status,date)  (select " + mapID + ", beneficiary_id, 'I', concat(concat(current_date(),' '),current_time())"
            + " from  beneficiary as b,city_location as cl,area as a,ward as w,zone as z,key_person as kp,type_of_occupation as too"
            + " where b.city_location_id=cl.city_location_id and cl.area_id=a.area_id and b.type_of_occupation_id=too.type_of_occupation_id and"
            + " a.ward_id=w.ward_id and w.zone_id=z.zone_id and b.key_person_id=kp.key_person_id "
            +  "  and zone_name='"+zone+"' and ward_name='"+ward+"' and area_name='"+area+"' "+ addQuery +")";
    try{
        rowAffected = connection.prepareStatement(query).executeUpdate();
    }catch(Exception ex){
        System.out.println("ERROR: in insertAllRemainingBeneficaries() in ShiftTimeModel : " + ex);
    }
    return rowAffected;
}

 public String getAllBeneficiary(){
     String id = "";
     String query = " SELECT beneficiary_id from shift_key_person_detail d WHERE date_format(date, '%Y-%m-%d') = current_date()";
     try{
        ResultSet rs = connection.prepareStatement(query).executeQuery();
         while(rs.next())
             if(id.isEmpty()){
                    id = rs.getString("beneficiary_id");
                } else
                    id = id + "," + rs.getString("beneficiary_id");
    }catch(Exception ex){
        System.out.println("ERROR: in insertAllRemainingBeneficaries() in ShiftTimeModel : " + ex);
    }
    return id;
 }

 public int getKeyPersonMapId(String emp_id){
     int id = 0;
     String query = "select shift_key_person_map_id from shift_key_person_map as skpm,key_person as kp where skpm.key_person_id=kp.key_person_id"
             + " and kp.emp_code = "+ emp_id +" and skpm.date=current_date()";
     try{
         ResultSet rs = connection.prepareStatement(query).executeQuery();
         if(rs.next())
             id = rs.getInt(1);
     }catch(Exception ex){
         System.out.println("ERROR : " + ex);
     }
     return id;
 }

 public void sendMessageToNextEightBeneficiary(){
 }

 public String getBeneficiaryDetails1(String zone,String ward,String area){
    String mobile_no = "";
    String name = "";
        String query = "   select beneficiary_id,kp.key_person_id,kp.salutation,kp.key_person_name,kp.age,kp.email_id1,kp.father_name, "
                         + "   kp.mobile_no1,cl.city_location_id,kp.address_line1,kp.address_line2,is_residencial,b.occupation_name,too.name,b.no_of_person "
                        + "  from  beneficiary as b,city_location as cl,area as a,ward as w,zone as z,key_person as kp,type_of_occupation as too "
                        + "   where b.city_location_id=cl.city_location_id and cl.area_id=a.area_id and b.type_of_occupation_id=too.type_of_occupation_id and "
                        + "  a.ward_id=w.ward_id and w.zone_id=z.zone_id and b.key_person_id=kp.key_person_id "
                        +  "  and zone_name='"+zone+"' and ward_name='"+ward+"' and area_name='"+area+"' LIMIT 1, 8 ";

        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
                if(mobile_no.isEmpty()){
                    mobile_no = rs.getString("mobile_no1");
                } else
                    mobile_no = mobile_no + "," + rs.getString("mobile_no1");
            }
            ShiftTimeModel stm=new ShiftTimeModel();
            stm.sendSmsToAssignedFor(mobile_no," जबलपुर नगर निगम का सफाई कर्मचारी आपके एरिया मे आ चुका है कृप्या सफाई मे सहयोग करें धन्यवाद ");
        }catch(Exception ex){
            System.out.println("ERROR : in getBeneficiaryDetails  in ShiftLoginModel : " + ex);
        }
        return mobile_no;
    }



 public JSONArray getVehicleKeyPersonDetails(String emp_code){
      JSONArray jsonArray = new JSONArray();


      String query =   " select v.vehicle_code,v.vehicle_no "
                      +" from vehicle_key_person vkp,vehicle v "
                      +" where vkp.vehicle_id=v.vehicle_id "
                      +" and vkp.key_person_id="+emp_code;
        try{
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next()){
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("vehicle_code", rs.getInt("vehicle_code"));
            jsonObj.put("vehicle_no", rs.getString("vehicle_no"));
            jsonArray.add(jsonObj);
            }
        }catch(Exception ex){
            System.out.println("ERROR : in getVehicleKeyPersonDetails  in ShiftLoginModel : " + ex);
        }
        return  jsonArray;
    }

//  public JSONArray getShiftVehicleDetails(String emp_code_givenBy,String emp_code_takenBy,String vehicle_code){
//      JSONArray jsonArray = new JSONArray();
//
//
//      String query =   " select v.vehicle_code,v.vehicle_no "
//                      +" from vehicle_key_person vkp,vehicle v "
//                      +" where vkp.vehicle_id=v.vehicle_id "
//                      +" and vkp.key_person_id="+emp_code;
//        try{
//            ResultSet rs = connection.prepareStatement(query).executeQuery();
//            while(rs.next()){
//            JSONObject jsonObj = new JSONObject();
//            jsonObj.put("vehicle_code", rs.getInt("vehicle_code"));
//            jsonObj.put("vehicle_no", rs.getString("vehicle_no"));
////            jsonObj.put("location_no", rs.getString("location_no"));
////            jsonObj.put("latitude", rs.getString("latitude"));
////            jsonObj.put("longitude", rs.getString("longitude"));
//            jsonArray.add(jsonObj);
//            }
//        }catch(Exception ex){
//            System.out.println("ERROR : in getVehicleKeyPersonDetails  in ShiftLoginModel : " + ex);
//        }
//        return  jsonArray;
//    }


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
      public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }
}
