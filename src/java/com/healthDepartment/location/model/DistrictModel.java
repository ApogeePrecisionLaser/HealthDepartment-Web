package com.healthDepartment.location.model;

import com.healthDepartment.location.tableClasses.DistrictBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;



    public class DistrictModel {
    private Connection connection;
    private String driver,url,user,password;
    private String message,messageBGColor;
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

     public byte[] generateMapReport(String jrxmlFilePath,List<DistrictBean> listAll) {
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
     public ByteArrayOutputStream generateDistrictXlsRecordList(String jrxmlFilePath,List list) {
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
                    System.out.println("DistrictStatusModel generatReport() JRException: " + e);
                }
                return bytArray;
            }
     public List<DistrictBean> showAllData(String districtName)
    {
         districtName = krutiToUnicode.convert_to_unicode(districtName);
        ArrayList<DistrictBean> list = new ArrayList<DistrictBean>();
        String query="select district_name,district_description,division_name,state_name,ut_name from district,division,state where district.division_id=division.division_id and district.state_id=state.state_id and if('"+districtName+"'='',district_name LIKE '%%',district_name='"+districtName+"')";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {                
                DistrictBean districtBean= new DistrictBean();
                districtBean.setDistrictName(rset.getString("district_name"));
                districtBean.setDistrictDescription(rset.getString("district_description"));
                districtBean.setDivisionName(rset.getString("division_name"));
                districtBean.setStateName(rset.getString("state_name"));
                districtBean.setUtName(rset.getString("ut_name"));
                

                list.add(districtBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- DistrictModel : " + e);
        }

        return list;
    }

     public List<String> getDist(String q,String divName) {
        List<String> list = new ArrayList<String>();
        String query = "select district.district_name from district where district.division_id=(select division.division_id from division where division.division_name='"+divName+"') group by district_name order by district_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String district_type = rset.getString("district_name");
                if (district_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(district_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such District exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDist ERROR inside DistrictModel - " + e);
        }
        return list;
    }
     public List<String> getState(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT state_name FROM state GROUP BY state_name ORDER BY state_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String stateut_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name"));
                if (stateut_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(stateut_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such State exists.......");
            }
        } catch (Exception e) {
            System.out.println("getState ERROR inside DistrictModel - " + e);
        }
        return list;
    }
       public List<String> getUt(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ut_name FROM state GROUP BY ut_name ORDER BY ut_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String stateut_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("ut_name"));
                if (stateut_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(stateut_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Ut exists.......");
            }
        } catch (Exception e) {
            System.out.println("getUt ERROR inside DistrictModel - " + e);
        }
        return list;
    }
    public List<String> getDistrict(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT district_id, district_name FROM district GROUP BY district_name ORDER BY district_name ";
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
                list.add("No such city exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDistrict ERROR inside DistrictModel - " + e);
        }
        return list;
    }
    public List<String> getDivision(String q,String stateName,String utName) {
        List<String> list = new ArrayList<String>();
        stateName = krutiToUnicode.convert_to_unicode(stateName);
        utName = krutiToUnicode.convert_to_unicode(utName);
        String query=null;
        if(stateName==null||stateName.equals(""))
            query = "select division_name from division where division.state_id = (select state.state_id from state where state.ut_name='"+utName+"')";
        else
            query = "select division_name from division where division.state_id = (select state.state_id from state where state.state_name='"+stateName+"')";
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
                list.add("No such division exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDivision ERROR inside DistrictModel - " + e);
        }
        return list;
    }

    public void updateRecord(String stateName,String utName,String divisionName,String districtId,String districtName,String districtDescription)
    {           
      PreparedStatement prestaState=null;
      PreparedStatement prestaDivision=null;
      PreparedStatement prestaDistrict=null;
      PreparedStatement presta = null;
      String query;
      int rowAffected=0;        
      try
      {
          stateName = krutiToUnicode.convert_to_unicode(stateName);
          utName = krutiToUnicode.convert_to_unicode(utName);
          divisionName = krutiToUnicode.convert_to_unicode(divisionName);
          districtName = krutiToUnicode.convert_to_unicode(districtName);
          districtDescription = krutiToUnicode.convert_to_unicode(districtDescription);
        String stateQuery = "select count(*) from state where if ('"+stateName+"'=' ',ut_name='"+utName+"',state_name='"+stateName+"')";
        prestaState = connection.prepareStatement(stateQuery);
        ResultSet resultState = prestaState.executeQuery();
        resultState.next();
        int stateNo = resultState.getInt(1);
        if(stateNo==1)
        {
            String divisionQuery = "select count(*) from division where division_name='"+divisionName+"'";
            prestaDivision = connection.prepareStatement(divisionQuery);
            ResultSet resultDivision = prestaDivision.executeQuery();
            resultDivision.next();
            int divisionNo = resultDivision.getInt(1);
            if(divisionNo==1)
            {
                String districtQuery = "select count(*) from district where district_name='"+districtName+"'";
                prestaDistrict = connection.prepareStatement(districtQuery);
                ResultSet resultDistrict = prestaDistrict.executeQuery();
                resultDistrict.next();
                int districtNo = resultDistrict.getInt(1);               
                    query ="update district set district_name='"+districtName+"',district_description='"+districtDescription+"',"
                    + "district.division_id=(select division.division_id from division where '"+divisionName+"'=division.division_name),"
                    + "district.state_id=(select state.state_id from state where if('"+stateName+"'='',ut_name='"+utName+"',state_name='"+stateName+"')) "
                    + "where district.district_id="+Integer.parseInt(districtId);
                    presta = connection.prepareStatement(query);
                    rowAffected = presta.executeUpdate();
                    if(rowAffected>0)
                    {
                        message="Record updated successfully....";
                        messageBGColor="yellow";
                    }
                    else
                    {
                        message ="Record not updated successfully";
                        messageBGColor="red";
                    }                   
               
            }
            else
            {
                 message = "Division doesn't exists";
                 messageBGColor="red";
            }
         }
        else
        {
             message = "State doesn't exists";
             messageBGColor="red";
        }
        }catch(Exception e)
      {
            message = "Record cann't be updated this record";
            messageBGColor="red";
            System.out.println("Error in updateRecord in DistrictModel: "+e);
        }
 }

public void deleteRecord(int districtId)
{
        PreparedStatement presta=null;
        try
        {
            presta = connection.prepareStatement("delete from district where district_id=?");
            presta.setInt(1, districtId);          
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
            message ="Record cann't be deleted";
            messageBGColor="red";
            System.out.println("Error in deleteRecord : DistrictModel : "+e);
        }
    }
    public ArrayList<DistrictBean> getAllRecords(int lowerLimit,int noOfRowsToDisplay,String searchDistrict)
    {
        searchDistrict = krutiToUnicode.convert_to_unicode(searchDistrict);
        ArrayList<DistrictBean> list = new ArrayList<DistrictBean>();
        
        String query ="SELECT district_id, district_name,district_description,division_name,state_name,ut_name FROM district,division,state WHERE district.division_id=division.division_id and district.state_id=state.state_id and IF('"+searchDistrict +"'='',district_name LIKE '%%',district_name=?) order by district_name limit "+ lowerLimit +","+ noOfRowsToDisplay ;
        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
             pstmt.setString(1, searchDistrict);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DistrictBean districtBean = new DistrictBean();
                districtBean.setDistrictId(rset.getInt("district_id"));
                districtBean.setDistrictName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_name")));
                districtBean.setDistrictDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("district_description")));
                districtBean.setDivisionName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("division_name")));
                String utname= unicodeToKruti.Convert_to_Kritidev_010(rset.getString("ut_name"));
                String stateName = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name"));
                if(utname==null || utname.equals(""))
                    districtBean.setStateName(stateName);
                else
                    districtBean.setStateName(stateName);//+"("+rset.getString("ut_name")+")"
                    
                list.add(districtBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod in DistrictModel " + e);
        }
        return list;
    }
    
   
    public int getTotalRowsInTable(String searchDistrict)
    {
        searchDistrict = krutiToUnicode.convert_to_unicode(searchDistrict);
         String query = " SELECT Count(*) "
                + " FROM district "
                + " WHERE IF('" + searchDistrict + "' = '', district_name LIKE '%%',district_name =?) "
                + " ORDER BY district_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchDistrict);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows DistrictModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;        
}
    
public void insertRecord(String[] stateName,String[] utName,String[] divisionName, String[] district_id,String[] districtName,String[] districtDescription)
{
      PreparedStatement prestaState=null;     
      PreparedStatement prestaDivision=null;
      PreparedStatement prestaDistrict=null;
      PreparedStatement presta = null;
      int rowAffected=0;
      int rowNotAffected=0;
      for(int i=0;i<district_id.length;i++)
      {
        if(district_id[i].equals("1"))
        {               
            try
            {
                String state_name = krutiToUnicode.convert_to_unicode(stateName[i]);
                String ut_name = krutiToUnicode.convert_to_unicode(utName[i]);
                String division_name = krutiToUnicode.convert_to_unicode(divisionName[i]);
                String district_name = krutiToUnicode.convert_to_unicode(districtName[i]);
                String district_description = krutiToUnicode.convert_to_unicode(districtDescription[i]);
                String stateQuery = "select count(*) from state where if ('"+state_name+"'=' ',ut_name='"+ut_name+"',state_name='"+state_name+"')";
                prestaState = connection.prepareStatement(stateQuery);                
                ResultSet resultState = prestaState.executeQuery();               
                resultState.next();
                int stateNo = resultState.getInt(1);
                if(stateNo==1)
                {                    
                    String divisionQuery = "select count(*) from division where division_name='"+division_name+"'";
                    prestaDivision = connection.prepareStatement(divisionQuery);
                    ResultSet resultDivision = prestaDivision.executeQuery();                    
                    resultDivision.next();
                    int divisionNo = resultDivision.getInt(1);
                    if(divisionNo==1)
                    {
                        String districtQuery = "select count(*) from district where district_name='"+district_name+"'";
                        prestaDistrict = connection.prepareStatement(districtQuery);
                        ResultSet resultDistrict = prestaDistrict.executeQuery();                      
                        resultDistrict.next();
                        int districtNo = resultDistrict.getInt(1);
                        if(districtNo==1)
                            rowNotAffected++;
                        else
                        {                            
                            String query = "insert into district(district_name,district_description,division_id,state_id) values('"+district_name.trim()+"','"+district_description.trim()+"',(select division.division_id from division where division.division_name='"+division_name.trim()+"'),(select state.state_id from state where if('"+state_name+"' =' ',ut_name='"+ut_name+"',state_name='"+state_name+"')))";
                            presta = connection.prepareStatement(query);                            
                            presta.executeUpdate();                            
                            rowAffected++;
                        }
                    }
                    else
                        rowNotAffected++;
                }
                else
                    rowNotAffected++;
            }catch(Exception e)
            {
                message="Record not inserted successfully";
                messageBGColor="red";
                System.out.println("Error in insertRecord DistrictModel : "+e);
            }               
         }
       }
      if(rowAffected>0)
      {
        message=rowAffected+" Records are inserted";
        messageBGColor="yellow";
      }
      if(rowNotAffected>0)
      {
        message=message+" ("+rowNotAffected+" Records are not inserted (Only District name should unique)";
        messageBGColor="red";
      }
}

    
   
    public void closeConnection()
    {
        try
        {
            connection.close();           
        }catch(Exception e)
        {
            System.out.println("DistrictModel closeConnection: "+e);
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
        System.out.println("DistrictModel setConnection error: "+e);
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