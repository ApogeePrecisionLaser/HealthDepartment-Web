package com.healthDepartment.location.model;

import static com.healthDepartment.dustbin.model.DustbinModel.krutiToUnicode;
import com.healthDepartment.location.tableClasses.CityBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import net.sf.jasperreports.engine.JRExporterParameter;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

public class CityModel {
    private Connection connection;
    private String driver,url,user,password;
    private String message,messageBGColor;
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

     public byte[] generateMapReport(String jrxmlFilePath,List<CityBean> listAll) {
        byte[] reportInbytes = null;        
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in CityModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
     public ByteArrayOutputStream generateCityXlsRecordList(String jrxmlFilePath,List list) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
              //  HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("CityStatusModel generatReport() JRException: " + e);
                }
                return bytArray;
            }
    public List<CityBean> showAllData(String cityName)
    {
        cityName = krutiToUnicode.convert_to_unicode(cityName);
        ArrayList<CityBean> list = new ArrayList<CityBean>();
        String query=   "select city_name,pin_code,std_code,city_description from city where "
           + " if('"+cityName+"'='',city_name LIKE '%%',city_name='"+cityName+"')" ;

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();           
            while (rset.next()) {                
                CityBean cityBean= new CityBean();
                cityBean.setCityName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name")));
                cityBean.setCityDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_description")));
                cityBean.setPin_code(rset.getInt("pin_code"));
                 cityBean.setStd_code(rset.getInt("std_code"));
                list.add(cityBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- CityModel : " + e);
        }

        return list;
    }

     public List<String> getDivision(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT division_name FROM division GROUP BY division_name ORDER BY division_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("division_name"));
                if (division_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such city exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCity ERROR inside CityModel - " + e);
        }
        return list;
    }
    public List<String> getCity(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT city_id, city_name FROM city GROUP BY city_name ORDER BY city_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("city_name"));
                if (city_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such city exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCity ERROR inside CityModel - " + e);
        }
        return list;
    }
    public List<String> getCityName(String q,String distName) {
        List<String> list = new ArrayList<String>();       

        String query = "select city_name FROM city where city.district_id=(select district.district_id from district where district_name='"+distName+"') GROUP BY city_name ORDER BY city_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String city_type = rset.getString("city_name");
                if (city_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(city_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such city exists.......");
            }
        } catch (Exception e) {
            System.out.println("getCityName ERROR inside CityModel - " + e);
        }
        return list;
    }
    public List<String> getDistrict(String q,String diviName) {
        List<String> list = new ArrayList<String>();
        diviName = krutiToUnicode.convert_to_unicode(diviName);
        String query = " SELECT district_name FROM district where district.division_id=(select division.division_id from division where division.division_name='"+diviName+"') GROUP BY district_name ORDER BY district_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String district_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name"));
                if (district_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(district_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such District exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDistrict ERROR inside CityModel - " + e);
        }
        return list;
    }

 
    public void deleteRecord(int cityId)
    {
        PreparedStatement presta=null;
        try
        {
            presta = connection.prepareStatement("delete from city where city_id=?");
            presta.setInt(1, cityId);
            int i = presta.executeUpdate();
            if(i>0)
            {
                message="Record deleted successfully......";
                messageBGColor="yellow";
            }
            else
            {
                message="Record not deleted successfully......";
                messageBGColor="red";
            }
        }catch(Exception e)
        {
            System.out.println("Error in deleting recordl ---- CityModel : "+e);
        }
    }
    public ArrayList<CityBean> getAllRecords(int lowerLimit,int noOfRowsToDisplay,String searchCity)
    {
        searchCity = krutiToUnicode.convert_to_unicode(searchCity);
        ArrayList<CityBean> list = new ArrayList<CityBean>();
        /*
        String query = " SELECT city_id, city_name, city_description,district_id,division_id "
                + " FROM city "
                + " WHERE IF('" + searchCity + "' = '', city_name LIKE '%%',city_name =?) "
                + " ORDER BY city_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
         
         */
        String query ="SELECT city_id,city_name,pin_code,std_code,city_description FROM city "
             + " WHERE IF('"+searchCity +"'='',city_name LIKE '%%',city_name=?) and active='Y' "
                + " order by city_name limit "+ lowerLimit +","+ noOfRowsToDisplay ;
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
             pstmt.setString(1, searchCity);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                CityBean CityBean = new CityBean();
                CityBean.setCityId(rset.getInt(1));
                CityBean.setCityName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(2)));
                CityBean.setPin_code(rset.getInt(3));
                CityBean.setStd_code(rset.getInt(4));
                CityBean.setCityDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(5)));
                list.add(CityBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod -- CityModel : " + e);
        }
        return list;
    }
    
   
    public int getTotalRowsInTable(String searchCity)
    {
        searchCity = krutiToUnicode.convert_to_unicode(searchCity);
         String query = " SELECT Count(*) "
                + " FROM city "
                + " WHERE IF('" + searchCity + "' = '', city_name LIKE '%%',city_name =?) "
                + " ORDER BY city_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchCity);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows CityModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;        
    }
    
    public void insertRecord(CityBean bean)
    {   
        int rowAffected=0;
        try
        {
                      String query = "insert into city(city_name,city_description,pin_code,std_code) values(?,?,?,?)";
                      PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
                      ps.setString(1,krutiToUnicode.convert_to_unicode(bean.getCityName()));
                      ps.setString(2,bean.getCityDescription());
                      ps.setInt(3,bean.getPin_code());
                      ps.setInt(4,bean.getStd_code());
                      rowAffected = ps.executeUpdate();
          }
    catch(Exception e)
        {
              message=rowAffected+" Already Exist or Some Error";
          messageBGColor="yellow";
              System.out.println("Error in insertRecord in CityModel : "+e);   
         }
       if(rowAffected>0)
      {
          message=rowAffected+" Record inserted successfully";
          messageBGColor="yellow";
      }
}   
   public int updateRecord(CityBean bean) {
        String query = " UPDATE city SET  city_name = ?, city_description=?, pin_code=?, std_code=? WHERE city_id = '"+bean.getCityId()+"' ";
        int rowsAffected = 0;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);

              ps.setString(1,krutiToUnicode.convert_to_unicode(bean.getCityName()));
                      ps.setString(2,bean.getCityDescription());
                      ps.setInt(3,bean.getPin_code());
                      ps.setInt(4,bean.getStd_code());
                      rowsAffected = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("AreaModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully......";
            messageBGColor = "yellow";
        } else {
            message = "Cannot update the record, some error......";
            messageBGColor = "yellow";
        }
        return rowsAffected;
    }
    public void closeConnection()
    {
        try
        {
            connection.close();           
        }catch(Exception e)
        {
            System.out.println("CityModel closeConnection: "+e);
        }
    }
    public void setConnection()
    {
     try
     {
        Class.forName(driver);
        connection = DriverManager.getConnection(url + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8",user,password);
     }catch(Exception e)
     {
        System.out.println("CityModel setConnection error: "+e);
     }
    }
    public Connection getConnection()
    {
        return connection;
    }
    public void setDriver(String driver)
    {
        this.driver = driver;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
    public void setUser(String user)
    {
        this.user = user;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getDriver()
    {
        return driver;
    }

    public String getUrl()
    {
        return url;
    }
    public String getUser()
    {
        return user;
    }
    public String getPassword()
    {
        return password;
    }
    public String getMessage()
    {
        return message;
    }
    public String getMessageBGColor()
    {
        return messageBGColor;
    }

}