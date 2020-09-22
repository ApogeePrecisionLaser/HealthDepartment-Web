package com.healthDepartment.location.model;


import com.healthDepartment.location.tableClasses.WardTypeBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

/**
 *
 * @author Administrator
 */
public class WardModel {

    private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
UnicodeToKrutiDevConverter uk=new UnicodeToKrutiDevConverter();
KrutiDevToUnicodeConverter ku=new KrutiDevToUnicodeConverter();
    public void setConnection() {
        try {
            Class.forName(driverClass);
            connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            //connection = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public byte[] generateMapReport(String jrxmlFilePath, List<WardTypeBean> listAll) {
        byte[] reportInbytes = null;
        Connection c;
        //     HashMap mymap = new HashMap();
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in WardModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

     public ByteArrayOutputStream generateZoneXlsRecordList(String jrxmlFilePath,List list) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
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

    public int insertRecord(WardTypeBean wardTypeBean) {

        String query = "INSERT INTO ward(ward_name,ward_no,description,zone_id) VALUES (?,?,?,?) ";
        int rowsAffected = 0;
        try {
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, ku.convert_to_unicode(wardTypeBean.getWard_name()));
            pstmt.setString(2, wardTypeBean.getWard_no());
            pstmt.setString(3, wardTypeBean.getRemark());    
            int zone_id=getZoneId(wardTypeBean.getZone_m());
            pstmt.setInt(4, zone_id);
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error while inserting record...." + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;

    }

    public int updateRecord(WardTypeBean wardTypeBean) {
        String query = "UPDATE ward_m SET  ward_no_m=?, remark=?, zone_id_m=? WHERE ward_id_m=? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           pstmt.setString(1, wardTypeBean.getWard_no());
            pstmt.setString(2, wardTypeBean.getRemark());
            pstmt.setInt(3, wardTypeBean.getZone_id_m());
            pstmt.setInt(4, wardTypeBean.getWard_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error while updating record........." + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getWardType(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT ward_id, ward_name FROM ward GROUP BY ward_name ORDER BY ward_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String ward_type = uk.Convert_to_Kritidev_010(rset.getString("ward_name"));
                if (ward_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(ward_type);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getWardType ERROR inside WardModel - " + e);
        }
        return list;
    }
      public int getZoneId(String zone) {
          zone=ku.convert_to_unicode(zone);
        String query = " select zone_id from zone where zone_name='"+zone+"' ";
        int zone_id = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                zone_id=rs.getInt("zone_id");
            }
        } catch (Exception e) {
            System.out.println("Error inside getCircleId division type model" + e);
        }

        return zone_id;
    }
 public List<String> getZoneName(String q) {
        List<String> list = new ArrayList<String>();
        String query = " SELECT zone_name FROM zone GROUP BY zone_name ORDER BY zone_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String zone_name_m =uk.Convert_to_Kritidev_010(rset.getString("zone_name"));
                     if (zone_name_m.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(zone_name_m);
                    count++;                
            }
            }
            if (count == 0) {
                list.add("No such Fuse exists.");
            }
        } catch (Exception e) {
            System.out.println("getZoneName ERROR inside WardModel - " + e);
        }
        return list;
    }

    public int getNoOfRows(String searchWardType,String zone)
    {
         searchWardType = ku.convert_to_unicode(searchWardType);
         zone = ku.convert_to_unicode(zone);
        String query = " SELECT Count(*)  FROM ward as w,zone as z "
                       + "  where w.zone_id=z.zone_id "
                + " and IF('"+searchWardType +"'='',w.ward_name LIKE '%%',w.ward_name=?) "
              + " AND IF('"+zone+"'='',z.zone_name LIKE '%%',z.zone_name=?) " ;
        int noOfRows = 0;
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, searchWardType);
            stmt.setString(2, zone);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            noOfRows = Integer.parseInt(rs.getString(1));
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows fuse type model" + e);
        }
        System.out.println("No of Rows in Table for search is" + noOfRows);
        return noOfRows;
    }

    public List<WardTypeBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchWardType,String zone) //
    {
        List<WardTypeBean> list = new ArrayList<WardTypeBean>();
        searchWardType = ku.convert_to_unicode(searchWardType);
        zone = ku.convert_to_unicode(zone);
        String query = " select w.ward_id,w.ward_name,w.ward_no,w.description,z.zone_name from ward as w,zone as z where w.zone_id=z.zone_id "
                + " and IF('"+searchWardType +"'='',w.ward_name LIKE '%%',w.ward_name=?) "
                + " AND IF('"+zone+"'='',z.zone_name LIKE '%%',z.zone_name=?) order by zone_name,ward_no"
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay ;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchWardType);
             pstmt.setString(2, zone);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WardTypeBean wardType = new WardTypeBean();
                wardType.setWard_id(rset.getInt("ward_id"));
                wardType.setWard_name(rset.getString("ward_name"));
                wardType.setWard_no(rset.getString("ward_no"));
                wardType.setRemark(rset.getString("description"));
               // wardType.setWard_rev_no(rset.getInt("ward_rev_no_m"));
                wardType.setZone_m(rset.getString("zone_name"));
                list.add(wardType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<WardTypeBean> showAllData(String searchWardType,String zone) {
        List<WardTypeBean> listAll = new ArrayList<WardTypeBean>();

        String query = "  select z.zone_name,w.ward_name,w.ward_no,w.description from ward as w,zone as z where w.zone_id=z.zone_id "
                + " AND IF('" + searchWardType + "' = '',w.ward_name  LIKE '%%',w.ward_name =?) "
                     + " AND IF('"+zone+"'='',z.zone_name LIKE '%%',z.zone_name=?) "
                + " order by zone_name,ward_no ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, searchWardType);
             pstmt.setString(2, zone);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                WardTypeBean wardType = new WardTypeBean();
             
                wardType.setWard_name(uk.Convert_to_Kritidev_010(rset.getString("ward_name")));
                wardType.setZone_m(uk.Convert_to_Kritidev_010(rset.getString("zone_name")));
                wardType.setRemark(rset.getString("description"));
               wardType.setWard_no(rset.getString("ward_no"));
               
                listAll.add(wardType);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return listAll;
    }

    public int deleteRecord(int ward_id_m) {
        String query = "DELETE FROM ward WHERE ward_id=" + ward_id_m;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Error Record cannot be deleted.....";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error inside closeConnection TrafficTypeModel:" + e);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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
}
