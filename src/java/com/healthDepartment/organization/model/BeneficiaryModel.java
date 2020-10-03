/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.Beneficiary;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.io.ByteArrayOutputStream;
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
public class BeneficiaryModel {
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();
 public static int  getNoOfRows(String searchoccupation_name,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName,String searchPersonName,String person_code)
 {
//        searchoccupation_name = krutiToUnicode.convert_to_unicode(searchoccupation_name);
//        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
//        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
//        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
//        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
//       searchPersonName = krutiToUnicode.convert_to_unicode(searchPersonName);
        int noOfRows = 0;
        try {
        String query="select count(b.beneficiary_id) "
                         +  " from type_of_occupation as t,beneficiary as b,key_person as kp,zone as z,ward as w,area as a,city_location as cl "
                         +  "  where b.type_of_occupation_id=t.type_of_occupation_id and b.key_person_id=kp.key_person_id and b.city_location_id=cl.city_location_id and "
                          +  "  cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id "
                              + " And IF('" + searchoccupation_name + "' = '', b.occupation_name LIKE '%%', b.occupation_name  =?) "
                            + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  =?) "
                           + "And IF('" + searchZoneName + "' = '', z.zone_name LIKE '%%', z.zone_name =?) "
                           + "And IF('" + searchWardName + "' = '', w.ward_name LIKE '%%', w.ward_name =?) "
                           + "And IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =?) "
                           + "And IF('" + searchPersonName + "' = '', kp.key_person_name LIKE '%%', kp.key_person_name =?) "
                           + "And IF('" + person_code + "' = '', kp.emp_code LIKE '%%', kp.emp_code =?) " ;
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
                   pstmt.setString(1, searchoccupation_name);
                   pstmt.setString(2, searchCityName);
                  pstmt.setString(3, searchZoneName);
                  pstmt.setString(4, searchWardName);
                  pstmt.setString(5, searchAreaName);
                  pstmt.setString(6, searchPersonName);
                 pstmt.setString(7, person_code);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println( e);
        }
         System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

 public static List<Beneficiary> showData(int lowerLimit,int noOfRowsToDisplay,String searchoccupation_name,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName,String searchPersonName,String person_code)
  {
//           searchoccupation_name = krutiToUnicode.convert_to_unicode(searchoccupation_name);
//           searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
//           searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
//           searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
//           searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
//           searchPersonName = krutiToUnicode.convert_to_unicode(searchPersonName);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query="select b.beneficiary_id,t.name,b.occupation_name,kp.key_person_name,kp.emp_code,z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_name,a.area_no,cl.location,cl.location_no,b.no_of_person,b.is_residencial,b.Description,b.rfid"
                         +  " from type_of_occupation as t,beneficiary as b,key_person as kp,zone as z,ward as w,area as a,city_location as cl "
                         +  "  where b.type_of_occupation_id=t.type_of_occupation_id and b.key_person_id=kp.key_person_id and b.city_location_id=cl.city_location_id and "
                         +  "  cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id "
                         + " And IF('" + searchoccupation_name + "' = '', b.occupation_name LIKE '%%', b.occupation_name  =?) "
                         + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  =?) "
                         + "And IF('" + searchZoneName + "' = '', z.zone_name LIKE '%%', z.zone_name =?) "
                         + "And IF('" + searchWardName + "' = '', w.ward_name LIKE '%%', w.ward_name =?) "
                         + "And IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =?) "
                         + "And IF('" + searchPersonName + "' = '', kp.key_person_name LIKE '%%', kp.key_person_name =?) "
                         + "And IF('" + person_code + "' = '', kp.emp_code LIKE '%%', kp.emp_code =?) "
                         + addQuery;
    try{
       PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
           ps.setString(1, searchoccupation_name);
                 ps.setString(2, searchCityName);
                 ps.setString(3, searchZoneName);
                 ps.setString(4, searchWardName);
                 ps.setString(5, searchAreaName);
                 ps.setString(6, searchPersonName);
                 ps.setString(7, person_code);
            ResultSet rs =ps.executeQuery();
             while(rs.next()){
            Beneficiary b=new Beneficiary();
            b.setBeneficiary_id(rs.getInt("beneficiary_id"));
            b.setType_of_beneficiary(rs.getString("name"));
            b.setOccupation_name(rs.getString("occupation_name"));
            b.setPerson_name(rs.getString("key_person_name"));
            b.setPerson_code(rs.getInt("emp_code"));
            b.setZone(rs.getString("zone_name"));
            b.setZone_no(rs.getString("zone_no"));
            b.setWard(rs.getString("ward_name"));
            b.setWard_no(rs.getString("ward_no"));
            b.setArea(rs.getString("area_name"));
            b.setArea_no(rs.getString("area_no"));
            b.setLocation(rs.getString("location"));
            b.setLocation_no(rs.getString("location_no"));
            b.setIs_residencial(rs.getString("is_residencial"));
            b.setNo_of_person(rs.getInt("no_of_person"));
            b.setDescription(rs.getString("Description"));
            b.setRfid(rs.getString("rfid"));
              list.add(b);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }


 public static List<Beneficiary> showAll(int lowerLimit,int noOfRowsToDisplay,String searchoccupation_name,String searchCityName, String searchZoneName, String searchWardName,String searchAreaName,String searchPersonName,String person_code)
  {
//        searchoccupation_name = krutiToUnicode.convert_to_unicode(searchoccupation_name);
//        searchCityName = krutiToUnicode.convert_to_unicode(searchCityName);
//        searchZoneName = krutiToUnicode.convert_to_unicode(searchZoneName);
//        searchWardName = krutiToUnicode.convert_to_unicode(searchWardName);
//        searchAreaName = krutiToUnicode.convert_to_unicode(searchAreaName);
//        searchPersonName = krutiToUnicode.convert_to_unicode(searchPersonName);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
            String query= " select   kp.key_person_name,kp.emp_code,kp.father_name,Concat(kp.address_line1,'_',kp.address_line2,'_',kp.address_line3)as address,b.no_of_person, "
                            + " b.occupation_name,tp.name,kp.mobile_no1,b.is_residencial,b.description "
                            + " from key_person as kp,beneficiary as b,type_of_occupation as tp,city_location as cl,area as a,ward as w,zone as z "
                            +  " where b.key_person_id=kp.key_person_id and b.type_of_occupation_id=tp.type_of_occupation_id and b.city_location_id=cl.city_location_id and "
                            + " cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id "
                            + " And IF('" + searchoccupation_name + "' = '', b.occupation_name LIKE '%%', b.occupation_name  =?) "
                            + " And IF('" + searchCityName + "' = '', cl.location LIKE '%%', cl.location  =?) "
                            + "And IF('" + searchZoneName + "' = '', z.zone_name LIKE '%%', z.zone_name =?) "
                            + "And IF('" + searchWardName + "' = '', w.ward_name LIKE '%%', w.ward_name =?) "
                            + "And IF('" + searchAreaName + "' = '', a.area_name LIKE '%%', a.area_name =?) "
                            + "And IF('" + searchPersonName + "' = '', kp.key_person_name LIKE '%%', kp.key_person_name =?) "
                            + "And IF('" + person_code + "' = '', kp.emp_code LIKE '%%', kp.emp_code =?) "
                            +  " order by emp_code "
                            + addQuery;
             try{
                 PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
                  ps.setString(1, searchoccupation_name);
                  ps.setString(2, searchCityName);
                  ps.setString(3, searchZoneName);
                  ps.setString(4, searchWardName);
                  ps.setString(5, searchAreaName);
                  ps.setString(6, searchPersonName);
                  ps.setString(7, person_code);
                 ResultSet rs =ps.executeQuery();
                 while(rs.next()){
                 Beneficiary b=new Beneficiary();
                 b.setKey_person_name(rs.getString("key_person_name"));
                 b.setPerson_code(rs.getInt("emp_code"));
                 b.setFather_name(rs.getString("father_name"));
                 b.setAddress(rs.getString("address"));
                 b.setNo_of_person(rs.getInt("no_of_person"));
                 b.setOccupation_name(rs.getString("occupation_name"));
                 b.setType_of_beneficiary(rs.getString("name"));
                 b.setMobile_no1(rs.getString("mobile_no1"));
                 b.setIs_residencial(rs.getString("is_residencial"));
                 b.setDescription(rs.getString("Description"));
                list.add(b);
          }
          }
            catch(Exception e)
            {
             System.out.println("error: " + e);
            }
     return list;
    }
 public  boolean insertRecord(Beneficiary bean)
 {      String query="";
        boolean status=false;
        int rowsAffected=0;
        try{
          int  beneficiary_id=bean.getBeneficiary_id();
         if(beneficiary_id==0)
          query="insert into beneficiary (occupation_name,key_person_id,is_residencial,Description,no_of_person,type_of_occupation_id,city_location_id,rfid) values(?,?,?,?,?,?,?,?)" ;
          if(beneficiary_id>0)
          query="update beneficiary set occupation_name=?,key_person_id=?,is_residencial=?,Description=?,no_of_person=?,type_of_occupation_id=?,city_location_id=?,rfid=? where beneficiary_id=?" ;
         PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
         ps.setString(1,bean.getOccupation_name());
         int key_person_id=getKey_Person_id(bean.getPerson_code());
         if(key_person_id == 0)
         ps.setNull(2, java.sql.Types.NULL);
         else
         ps.setInt(2, key_person_id);
         ps.setString(3, bean.getIs_residencial());
         ps.setString(4,bean.getDescription());
         ps.setInt(5, bean.getNo_of_person());
         int type_of_occupation_id=getType_of_occupation_id(bean.getType_of_beneficiary());
         if(type_of_occupation_id == 0)
         ps.setNull(6, java.sql.Types.NULL);
         else
         ps.setInt(6, type_of_occupation_id);
         int city_location_id=getCityLocationId(bean.getLocation());
         ps.setInt(7, city_location_id);
         ps.setString(8, bean.getRfid());
        if(beneficiary_id>0)
         ps.setInt(9, beneficiary_id);
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
return status;
}
  public static int getKey_Person_id(int person_code) {
        int key_person_id = 0;
        try {
            //String person_name=krutiToUnicode.convert_to_unicode(key_person_name);
            String query = "select key_person_id from key_person"
                    +" where emp_code="+person_code;
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                key_person_id = rset.getInt("key_person_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return key_person_id;
    }
  public static int getCityLocationId(String location) {
        int city_location_id = 0;
        try {

               //location=krutiToUnicode.convert_to_unicode(location);
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
public static int getType_of_occupation_id(String type_of_occupation_name) {
        int type_of_occupation_id = 0;
        try {
            type_of_occupation_name=type_of_occupation_name;
            String query = "select type_of_occupation_id from type_of_occupation"
                    +" where name='"+type_of_occupation_name+"' ";
            ResultSet rset =connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                type_of_occupation_id = rset.getInt("type_of_occupation_id");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return type_of_occupation_id;
    }
  public  boolean deleteRecord(String beneficiary_id){
             boolean status=false;
             int rowsAffected=0;
             try{
             rowsAffected = connection.prepareStatement("Delete from beneficiary where beneficiary_id="+beneficiary_id+" ").executeUpdate();
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
             System.out.println("not Deleted");}
            return status;
}

   public List<String> getTypeOfOccupation(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select name from type_of_occupation group by name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String name = rset.getString("name");
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
   public List<String> getPersonName(String q,String emp_code) {
        List<String> list = new ArrayList<String>();
        String query = " select key_person_name from key_person "
                     +  " where IF('" + emp_code + "'='', emp_code like '%%', emp_code ='" + emp_code + "') "
                 + "Group by key_person_name";
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
                list.add("No such record exists.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
      public List<String> getSearchPersonName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select key_person_name from key_person as kp,beneficiary as b where b.key_person_id=kp.key_person_id group by key_person_name ";
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
                list.add("No such record exists.");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
      public List<String> getperson_code(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select emp_code from key_person as kp,beneficiary as b where b.key_person_id=kp.key_person_id group by emp_code ";
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
            public List<String> getEmp_code(String q,String person_name)
            {
         //  person_name=krutiToUnicode.convert_to_unicode(person_name);
        List<String> list = new ArrayList<String>();
        String query = " select emp_code from key_person as kp where  "
                 +  "  IF('" + person_name + "'='', key_person_name like '%%', key_person_name ='" + person_name + "') " ;
              
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
   public List<String> getSearchOccupationName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " select occupation_name from beneficiary group by occupation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {   
                String occupation_name = rset.getString("occupation_name");
                if (occupation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(occupation_name);
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
