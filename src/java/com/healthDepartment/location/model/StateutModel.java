package com.healthDepartment.location.model;

import com.healthDepartment.location.tableClasses.StateutBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

public class StateutModel {
    private Connection connection;
    private String driver,url,user,password;
    private String message,messageBGColor;
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

     public byte[] generateMapReport(String jrxmlFilePath,List<StateutBean> listAll) {
        byte[] reportInbytes = null;
         Connection c;
         HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in StateTypeModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
      public ByteArrayOutputStream generateStateXlsRecordList(String jrxmlFilePath,List list) {
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
                    System.out.println("StatutModel generateStateXlsRecordList() JRException: " + e);
                }
                return bytArray;
            }
     public List<StateutBean> showAllData(String stateutName)
    {        
        ArrayList<StateutBean> list = new ArrayList<StateutBean>();
        String query1=   "select state_name,ut_name,stateut_description,zone_name,country_name from state, zone,country"
                        + " where state.zone_id = zone.zone_id and state.country_id=country.country_id and if('"+stateutName+"'='',state_name LIKE '%%',state_name='"+stateutName+"')";     
        try {
            PreparedStatement pstmt = connection.prepareStatement(query1);            
            ResultSet rset = pstmt.executeQuery();           
            while(rset.next())
            {
                StateutBean stateutBean= new StateutBean();
                stateutBean.setStateName(rset.getString("state_name"));
                stateutBean.setUtName(rset.getString("ut_name"));
                stateutBean.setStateutDescription(rset.getString("stateut_description"));
                stateutBean.setZoneName(rset.getString("zone_name"));
                stateutBean.setCountryName(rset.getString("country_name"));
                
                list.add(stateutBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData() - StateutModel : " + e);
        }

        return list;
    }
     public List<String> getUtName(String q,String zoneName) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ut_name FROM state where state.zone_id = (select zone.zone_id from zone where zone.zone_name='"+zoneName+"') GROUP BY ut_name ORDER BY ut_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ut_type = rset.getString("ut_name");
                if (ut_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ut_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such UT exists.......");
            }
        } catch (Exception e) {
            System.out.println("getUtName ERROR inside StateutModel - " + e);
        }
        return list;
    }
     public List<String> getStateName(String q,String zoneName) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT state_name FROM state where state.zone_id = (select zone.zone_id from zone where zone.zone_name='"+zoneName+"') GROUP BY state_name ORDER BY state_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_type = rset.getString("state_name");
                if (state_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(state_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such State exists.......");
            }
        } catch (Exception e) {
            System.out.println("getStateName ERROR inside StateutModel - " + e);
        }
        return list;
    }
    public List<String> getStateut(String q) {
        List<String> state_name = new ArrayList<String>();
        List<String> ut_name = new ArrayList<String>();
        String query = " SELECT state_name,ut_name FROM state GROUP BY state_name,ut_name ORDER BY state_name,ut_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String state_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("state_name"));
                String ut_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("ut_name"));
                if(!(state_type==null || state_type.equals("")))
                {
                if (state_type.toUpperCase().startsWith(q.toUpperCase())) {
                    state_name.add(state_type);
                    count++;
                }
                }else if(!(ut_type==null || ut_type.equals("")))
                {
                if (ut_type.toUpperCase().startsWith(q.toUpperCase())) {
                    ut_name.add(ut_type);
                    count++;
                }
                }
            }
            state_name.addAll(ut_name);
            Collections.sort(state_name);
            if (count == 0) {
                state_name.add("No such State or UT exists.......");
            }
        } catch (Exception e) {
            System.out.println("getStateut ERROR inside StateutModel - " + e);
        }
        return state_name;
    }
    public List<String> getZoneName(String q) {
        List<String> list = new ArrayList<String>();     
        String query = " SELECT zone_name FROM zone GROUP BY zone_name ORDER BY  zone_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_type = rset.getString("zone_name");                
                if (zone_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such State & UT exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside StateutModel - " + e);
        }
        return list;
    }
    public void updateRecord(String stateutId,String countryName,String zoneName,String stateName,String utName,String stateutDescription)
    {
      PreparedStatement prestaCountry=null;
      PreparedStatement prestaZone=null;
      PreparedStatement prestaState=null;
      PreparedStatement presta = null;
      int rowAffected=0;
      int rowNotAffected=0;
        try
        {
            String state_name = krutiToUnicode.convert_to_unicode(stateName);
            String ut_name = krutiToUnicode.convert_to_unicode(utName);
            String stateut_description = krutiToUnicode.convert_to_unicode(stateutDescription);
        String countryQuery = "select count(*) from country where country_name='"+countryName+"'";
        prestaCountry = connection.prepareStatement(countryQuery);
        ResultSet resultCountry = prestaCountry.executeQuery();
        resultCountry.next();
        int countryNo  = resultCountry.getInt(1);        
        if(countryNo==1)
        {      
            String zoneQuery = "select count(*) from zone where zone_name='"+zoneName.trim()+"'";
            prestaZone = connection.prepareStatement(zoneQuery);
            ResultSet resultZone = prestaZone.executeQuery();
            resultZone.next();
            int zoneNo = resultZone.getInt(1);            
            if(zoneNo==1)
            {
                String stateQuery = "select count(*) from state where state_name='"+state_name+"'";
                prestaState = connection.prepareStatement(stateQuery);
                ResultSet resultState = prestaState.executeQuery();
                resultState.next();
                int stateNo = stateNo = resultState.getInt(1);
                    String query = "update state set state_name='"+state_name.trim()+"',ut_name='"+ut_name.trim()+"',stateut_description='"+stateut_description.trim()+"',"
                    +"zone_id=(select zone_id from zone where '"+zoneName.trim()+"'=zone.zone_name),"
                    +"country_id=(select country_id from country where '"+countryName.trim()+"'=country.country_name)"
                    +"where state_id="+Integer.parseInt(stateutId.trim());
                    presta = connection.prepareStatement(query);
                    int i = presta.executeUpdate();
                    if(i>0)
                    {
                        message=i+" Record updated successfully......";
                        messageBGColor="yellow";
                    }
                    else
                    {
                        message="Record not updated successfully......";
                        messageBGColor="red";
                    }
                
            }
            else
            {
                message ="Record not update because of not existance of Zone";
                messageBGColor="red";
            }
          } 
        else
        {
            message ="Record not update because of not existance of Country";
            messageBGColor="red";
        }
          }catch(Exception e)
            {
              System.out.println("Error in updateRecord in stateutModel : "+e);
            }
    }      

    public void deleteRecord(int stateut_id)
    {
        PreparedStatement presta=null;
        try
        {
            presta = connection.prepareStatement("delete from state where state_id=?");
            presta.setInt(1, stateut_id);
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
            System.out.println("Error in deleting recordl ---- StateutModel - "+e);
        }
    }
    public ArrayList<StateutBean> getAllRecords(int lowerLimit,int noOfRowsToDisplay,String searchStateut)
    {
        ArrayList<StateutBean> list = new ArrayList<StateutBean>();
        String query=null;
        searchStateut = krutiToUnicode.convert_to_unicode(searchStateut);        
        if(searchStateut.equals(""))            
            query = "select state_id,state_name,ut_name,stateut_description,zone_name,country_name from state,zone,country where state.zone_id=zone.zone_id and state.country_id=country.country_id order by state_name limit "+lowerLimit+","+noOfRowsToDisplay+"";
        else
            query = "select state_id,state_name,ut_name,stateut_description,zone_name,country_name from state,zone,country where (state.zone_id=zone.zone_id and state.country_id=country.country_id) and ((state_name='"+searchStateut+"')or(ut_name='"+searchStateut+"')) limit "+lowerLimit+","+noOfRowsToDisplay+"";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);             
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                StateutBean stateutBean = new StateutBean();
                stateutBean.setStateutId(rset.getInt(1));
                stateutBean.setStateName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(2)));
                stateutBean.setUtName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(3)));
                stateutBean.setStateutDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(4)));
                stateutBean.setZoneName(rset.getString(5));
                stateutBean.setCountryName(rset.getString(6));
               
                list.add(stateutBean);
            }
           
        } catch (Exception e) {
            System.out.println("Error in getAllRecord in StateutModel " + e);
        }         
        return list;
    }
    
   
    public int getTotalRowsInTable(String searchStateut)
    {
        String query;
        searchStateut = krutiToUnicode.convert_to_unicode(searchStateut);
        if(!searchStateut.equals(""))
            query = "select count(*) from state where state_name='"+searchStateut+"' or ut_name='"+searchStateut+"'";
        else
            query = "select count(*) from state";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);            
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows StateutModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;        
    }

     public void insertRecord(String[] countryName,String[] zoneName, String[] stateut_id,String[] stateName,String[] utName, String[] stateutDescription)
    {
      PreparedStatement prestaCountry=null;
      PreparedStatement prestaZone=null;
      PreparedStatement prestaState=null;
      PreparedStatement presta = null;
      int rowAffected=0;
      int rowNotAffected=0;
     for(int i=0;i<stateut_id.length;i++)
     {
      if(stateut_id[i].equals("1"))
      {
        try
        {
            String state_name = krutiToUnicode.convert_to_unicode(stateName[i]);
            String ut_name = krutiToUnicode.convert_to_unicode(utName[i]);
            String stateut_description = krutiToUnicode.convert_to_unicode(stateutDescription[i]);
        String countryQuery = "select count(*) from country where country_name='"+countryName[i]+"'";
        prestaCountry = connection.prepareStatement(countryQuery);
        ResultSet resultCountry = prestaCountry.executeQuery();
        resultCountry.next();
        int countryNo = resultCountry.getInt(1);
        if(countryNo==1)
        {
            String zoneQuery = "select count(*) from zone where zone_name='"+zoneName[i]+"'";
            prestaZone = connection.prepareStatement(zoneQuery);
            ResultSet resultZone = prestaZone.executeQuery();
            resultZone.next();
            int zoneNo = resultZone.getInt(1);
            if(zoneNo==1)
            {
                String stateQuery = "select count(*) from state where state_name='"+state_name+"'";
                prestaState = connection.prepareStatement(stateQuery);
                ResultSet resultState = prestaState.executeQuery();
                resultState.next();
                int stateNo = resultState.getInt(1);
                if(stateNo==1)
                    rowNotAffected++;
                else
                {
                    String query = "insert into state(state_name,ut_name,stateut_description,zone_id,country_id)"
                        + "values('"+state_name.trim()+"','"+ut_name.trim()+"','"+stateut_description.trim()+"',(select zone.zone_id from zone where '"+zoneName[i].trim()+"'=zone.zone_name),(select country.country_id from country where '"+countryName[i].trim()+"'=country.country_name))";
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
              System.out.println("Error in insertRecords in stateutModel : "+e);
        }
      }     
     }
       if(rowAffected>0)
      {
          message=rowAffected+" Record inserted successfully";
          messageBGColor="yellow";
      }
      if(rowNotAffected>0)
      {
          if(message==null)
             message ="";
          message = message+"("+rowNotAffected+" Record not inserted (Only State or UT name should unique))";
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
            System.out.println("StateutModel closeConnection: "+e);
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
        System.out.println("StateutModel setConnection error: "+e);
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