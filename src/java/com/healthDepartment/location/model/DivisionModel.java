package com.healthDepartment.location.model;

import com.healthDepartment.location.tableClasses.DivisionBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;



public class DivisionModel {
    private Connection connection;
    private String driver,url,user,password;
    private String message,messageBGColor;
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

     public byte[] generateMapReport(String jrxmlFilePath,List<DivisionBean> listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in DivisionModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }
     public ByteArrayOutputStream generateDivisionXlsRecordList(String jrxmlFilePath,List list) {
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
              //  HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("DivisionStatusModel generatReport() JRException: " + e);
                }
                return byteArray;
            }
    public List<DivisionBean> showAllData(String divisionName)
    {
        divisionName = krutiToUnicode.convert_to_unicode(divisionName);
        ArrayList<DivisionBean> list = new ArrayList<DivisionBean>();
        String query=   "select division_name,division_description,state_name,ut_name,zone_name from division,state,zone where division.state_id=state.state_id and division.zone_id=zone.zone_id and if('"+divisionName+"'='',division_name LIKE '%%',division_name='"+divisionName+"')";

        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();

            while (rset.next()) {                
                DivisionBean divisionBean= new DivisionBean();
                divisionBean.setDivisionName(rset.getString("division_name"));
                divisionBean.setDivisionDescription(rset.getString("division_description"));
                divisionBean.setStateName(rset.getString("state_name"));
                divisionBean.setUtName(rset.getString("ut_name"));
                divisionBean.setZoneName(rset.getString("zone_name"));               

                list.add(divisionBean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowAllData --- DivisionModel : " + e);
        }

        return list;
    }
    public List<String> getDivName(String q,String stateName,String utName) {
        List<String> list = new ArrayList<String>();
        String query=null;
        if(stateName.equals(""))
            query = " SELECT division_name FROM division where division.state_id=(select state.state_id from state where state.ut_name='"+utName+"') GROUP BY division_name ORDER BY division_name ";
        else
            query = " SELECT division_name FROM division where division.state_id=(select state.state_id from state where state.state_name='"+stateName+"') GROUP BY division_name ORDER BY division_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String division_type = rset.getString("division_name");
                if (division_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(division_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Division exists.......");
            }
        } catch (Exception e) {
            System.out.println("getDivName ERROR inside DivisionModel - " + e);
        }
        return list;
    }
     public List<String> getZone(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone_name FROM zone GROUP BY zone_name ORDER BY zone_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("zone_name"));
                if (zone_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Zone exists.......");
            }
        } catch (Exception e) {
            System.out.println("getZone ERROR inside DivisionModel - " + e);
        }
        return list;
    }
    public List<String> getDivision(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT division_id, division_name FROM division GROUP BY division_name ORDER BY division_name ";
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
            System.out.println("getDivision ERROR inside DivisionModel - " + e);
        }
        return list;
    }
    public List<String> getStateName(String q,String zoneName) {
        List<String> list = new ArrayList<String>();     
        String query = " SELECT state_name FROM state where zone_id=(select zone.zone_id from zone where zone_name='"+zoneName+"') GROUP BY state_name ORDER BY  state_name ";
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
            System.out.println("getStateutName ERROR inside DivisionModel - " + e);
        }
        return list;
    }
     public List<String> getUtName(String q,String zoneName) {
        List<String> list = new ArrayList<String>();       
        String query = "select ut_name from state where state.zone_id=(select zone.zone_id from zone where zone_name='"+zoneName+"') ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String stateut_type = unicodeToKruti.Convert_to_Kritidev_010(rset.getString(1));
                if(stateut_type!=null)
                {
                if (stateut_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(stateut_type);
                    count++;
                }
                }
            }
            if (count == 0) {
                list.add("No such UT exists.......");
            }
        } catch (Exception e) {
            System.out.println("getUtName ERROR inside DivisionModel - " + e);
        }
        return list;
    }
    public void updateRecord(String zoneName,String stateName,String utName,String divisionId,String divisionName,String divisionDescription)
    {       
      PreparedStatement prestaZone=null;
      PreparedStatement prestaState=null;
      PreparedStatement prestaDivision=null;
      PreparedStatement presta = null;
      int rowAffected=0;
      int rowNotAffected=0;     
            try
            {
                zoneName = zoneName;
                stateName = krutiToUnicode.convert_to_unicode(stateName);
                utName = krutiToUnicode.convert_to_unicode(utName);
                divisionName = krutiToUnicode.convert_to_unicode(divisionName);
                divisionDescription = krutiToUnicode.convert_to_unicode(divisionDescription);

                String zoneQuery = "select count(*) from zone where zone_name='"+zoneName+"'";
                prestaZone = connection.prepareStatement(zoneQuery);
                ResultSet resultZone = prestaZone.executeQuery();
                resultZone.next();
                int zoneNo = resultZone.getInt(1);
                if(zoneNo==1)
                {
                    String stateQuery = "select count(*) from state where if ('"+stateName+"'='',ut_name='"+utName+"',state_name='"+stateName+"')";
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
                       
                       
                            String query="update division set division_name='"+divisionName+"',division_description='"+divisionDescription+"',"
                                        + "state_id=(select state_id from state where if('"+stateName+"'='',ut_name='"+utName+"',state_name='"+stateName+"')),"
                                        + "zone_id=(select zone_id from zone where zone_name='"+zoneName+"')"
                                        + "where division_id="+Integer.parseInt(divisionId);
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
                        message ="State name does not exist: ";
                        messageBGColor="red";
                    }
                }else
                {
                 message ="Zone name does not exist: ";
                 messageBGColor="red";
                }
            }catch(Exception e)
            {
                message="Record not updated successfully";
                messageBGColor="red";
                System.out.println("Error in updateRecord DivisionModel : "+e);
            }      
     
}
   
    public void deleteRecord(int divisionId)
    {
        PreparedStatement presta=null;
        try
        {
            presta = connection.prepareStatement("delete from division where division_id=?");
            presta.setInt(1, divisionId);
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
            message = "Record cann't be delted";
            messageBGColor="red";
            System.out.println("Error in deleting recordl ---- DivisionModel - "+e);
        }
    }
    public ArrayList<DivisionBean> getAllRecords(int lowerLimit,int noOfRowsToDisplay,String searchDivision)
    {
        ArrayList<DivisionBean> list = new ArrayList<DivisionBean>();
        searchDivision = krutiToUnicode.convert_to_unicode(searchDivision);
        String query ="SELECT division_id,division_name,division_description,state_name,ut_name,zone_name "
                +"FROM division,state,zone "
                +"WHERE IF('"+searchDivision +"'='',division_name LIKE '%%',division_name=?) and division.state_id=state.state_id and division.zone_id=zone.zone_id "
                +"order by division_name limit "+ lowerLimit +","+ noOfRowsToDisplay ;        
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
             pstmt.setString(1, searchDivision);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                DivisionBean divisionBean = new DivisionBean();
                divisionBean.setDivisionId(rset.getInt(1));
                divisionBean.setDivisionName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(2)));
                divisionBean.setDivisionDescription(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(3)));
                String uname= rset.getString(5);
                if(uname==null || uname.equals(""))
                    divisionBean.setStateName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(4)));
                else
                    divisionBean.setStateName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(4)));//+"("+rset.getString(5)+")"
                divisionBean.setZoneName(unicodeToKruti.Convert_to_Kritidev_010(rset.getString(6)));
               
                list.add(divisionBean);
            }
        } catch (Exception e) {
            System.out.println("Error in getAllRecrod in DivisionModel " + e);
        }
        return list;
    }
    
   
    public int getTotalRowsInTable(String searchDivision)
    {
        searchDivision = krutiToUnicode.convert_to_unicode(searchDivision);
         String query = " SELECT Count(*) "
                + " FROM division "
                + " WHERE IF('" + searchDivision + "' = '', division_name LIKE '%%',division_name =?) "
                + " ORDER BY division_name ";
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchDivision);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows DivisionModel" + e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;        
    }
    
public void insertRecord(String[] zoneName,String[] stateName,String[] utName, String[] division_id,String[] divisionName,String[] divisionDescription)
{
      PreparedStatement prestaZone=null;
      PreparedStatement prestaState=null;
      PreparedStatement prestaDivision=null;
      PreparedStatement presta = null;
      int rowAffected=0;
      int rowNotAffected=0;
      for(int i=0;i<division_id.length;i++)
      {
        if(division_id[i].equals("1"))
        {
            try
            {
                String zone_name = zoneName[i];
                String state_name = krutiToUnicode.convert_to_unicode(stateName[i]);
                String ut_name = krutiToUnicode.convert_to_unicode(utName[i]);
                String division_name = krutiToUnicode.convert_to_unicode(divisionName[i]);
                String division_description = krutiToUnicode.convert_to_unicode(divisionDescription[i]);

                String zoneQuery = "select count(*) from zone where zone_name='"+zone_name+"'";
                prestaZone = connection.prepareStatement(zoneQuery);
                ResultSet resultZone = prestaZone.executeQuery();
                resultZone.next();
                int zoneNo = resultZone.getInt(1);
                if(zoneNo==1)
                {
                    String stateQuery = "select count(*) from state where if ('"+state_name+"'='',ut_name='"+ut_name+"',state_name='"+state_name+"')";
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
                            rowNotAffected++;
                        else
                        {
                            String query = "insert into division(division_name,division_description,state_id,zone_id) values('"+division_name.trim()+"','"+division_description.trim()+"',(select state_id from state where if('"+state_name+"'='',ut_name='"+ut_name+"','"+state_name+"'=state_name)),(select zone.zone_id from zone where zone.zone_name='"+zone_name+"'))";
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
                System.out.println("Error in insertRecord DivisionModel : "+e);
            }
         }
      }
      if(rowAffected>0)
      {
        message=rowAffected+" Record inserted sucessfully";
        messageBGColor="yellow";
      }
      if(rowNotAffected>0)
      {
        message=message+" ("+rowNotAffected+" Record not inserted (Only Division name should unique)";
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
            System.out.println("DivisionModel closeConnection: "+e);
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
        System.out.println("DivisionModel setConnection error: "+e);
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