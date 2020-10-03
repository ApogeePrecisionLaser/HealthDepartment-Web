/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicleTrip.tableClasses.RouteName;
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
public class RouteNameModel {
    private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
//    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public List<RouteName> showData(int lowerLimit, int noOfRowsToDisplay, String search_route_name, String search_route_no) {
        List<RouteName> list = new ArrayList<RouteName>();
       // search_route_name = krutiToUnicode.convert_to_unicode(search_route_name);
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1 && noOfRowsToDisplay == -1) {
            addQuery = "";
        }

        try {
//            String query = "Select * from route_name r,route_type rt where r.route_type_id=rt.route_type_id "
//                    + "And IF('" + search_route_type + "' = '', rt.route_type LIKE '%%', rt.route_type ='" + search_route_type + "') "
//                    + "And IF('" + search_route_name + "' = '', r.route_name LIKE '%%',r.route_name='" + search_route_name + "') "
//                    + "And IF('" + search_route_no + "' = '', r.route_no LIKE '%%',r.route_no ='" + search_route_no + "') "
//                    + addQuery;

            String query = "Select * from route_name r  "
                    + "WHERE IF('" + search_route_name + "' = '', r.route_name LIKE '%%',r.route_name='" + search_route_name + "') "
                    + "And IF('" + search_route_no + "' = '', r.route_no LIKE '%%',r.route_no ='" + search_route_no + "') "
                    + addQuery;


            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                RouteName rn = new RouteName();
                rn.setRoute_name_id(rset.getInt("route_name_id"));
                if (lowerLimit == -1 && noOfRowsToDisplay == -1) {
                    rn.setRoute_name(rset.getString("route_name"));
                } else {
                    rn.setRoute_name(rset.getString("route_name"));
                }
                //rn.setRoute_type(rset.getString("route_type"));
                rn.setRoute_no(rset.getString("route_no"));
                rn.setRemark(rset.getString("remark"));
                list.add(rn);
            }
        } catch (Exception e) {
            System.out.println("RouteModel showData() Error: " + e);
        }
        return list;
    }

    public int getNoOfRows( String search_route_name, String search_route_no) {
       // search_route_name = krutiToUnicode.convert_to_unicode(search_route_name);
        int noOfRows = 0;
        try {
//            String query = " Select count(route_name_id) from route_name r,route_type rt where r.route_type_id=rt.route_type_id "
//                    + "And IF('" + search_route_type + "' = '', rt.route_type LIKE '%%', rt.route_type ='" + search_route_type + "') "
//                    + "And IF('" + search_route_name + "' = '', r.route_name LIKE '%%',r.route_name='" + search_route_name + "') "
//                    + "And IF('" + search_route_no + "' = '', r.route_no LIKE '%%',r.route_no ='" + search_route_no + "') ";

              String query = " Select count(route_name_id) from route_name r  "
                    + "WHERE IF('" + search_route_name + "' = '', r.route_name LIKE '%%',r.route_name='" + search_route_name + "') "
                    + "And IF('" + search_route_no + "' = '', r.route_no LIKE '%%',r.route_no ='" + search_route_no + "') ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                noOfRows = rset.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public int insertRecord(RouteName rn) {
        String query = "";
        if (rn.getRoute_name_id() == 0) {
            query = "INSERT INTO route_name(route_name,route_no,remark) VALUES(?,?,?) ";
        } else {
            query = " update route_name set route_name=?,route_no=?,remark=? where route_name_id=? ";
        }
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setInt(1, getRouteTypeId(rn.getRoute_type()));
            pstmt.setString(1,rn.getRoute_name());
            pstmt.setString(2, rn.getRoute_no());
            pstmt.setString(3, rn.getRemark());
            if (rn.getRoute_name_id() > 0) {
                pstmt.setInt(4, rn.getRoute_name_id());
            }
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("StoppageModel insertRecord() Error: " + e);
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

    public int getRouteTypeId(String route_type) {
        int route_type_id = 0;
        String query = "SELECT route_type_id FROM route_type WHERE route_type = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, route_type);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                route_type_id = rset.getInt("route_type_id");
            }
        } catch (Exception e) {
            System.out.println("StoppageModel getStopageId() Error: " + e);
        }

        return route_type_id;
    }

    public int cancelRecord(int route_name_id) {
        int rowsAffected = 0;
        String query = "delete from route_name WHERE route_name_id=" + route_name_id;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Deleted successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Trip Deleted");
        } else {
            message = "Record Not Deleted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("Trip not Deleted");
        }
        return rowsAffected;
    }

    public List<String> getRoute(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select route_name from route_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String route_name = rset.getString("route_name");
                if (route_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(route_name);
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

    public List<String> getRouteNo(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select route_no from route_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String route_no = rset.getString("route_no");
                if (route_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(route_no);
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

    public List<String> getRouteType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select route_type from route_type";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String route_type = rset.getString("route_type");
                if (route_type.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(route_type);
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

    public static byte[] generateRecordList(String jrxmlFilePath, List list) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
//            JasperReport compiledReport1 = JasperCompileManager.compileReport(jrxmlFilePath);
//            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport1, null, jrBean);
//            String path = createAppropriateDirectories1("C:/ssadvt_repository/prepaid/RideReceipt");
//            path = path + "/receipt.pdf";
//            JRPdfExporter exporter = new JRPdfExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path);
//            exporter.exportReport();
        } catch (Exception e) {
            System.out.println("GeneralModel generateRecordList() JRException: " + e);
        }
        return reportInbytes;
    }
public static ByteArrayOutputStream generateExcelList(String jrxmlFilePath, List list) {
        ByteArrayOutputStream reportInbytes = new ByteArrayOutputStream();
        HashMap mymap = new HashMap();
        try {
            JRBeanCollectionDataSource jrBean = new JRBeanCollectionDataSource(list);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, reportInbytes);
            exporter.exportReport();
            //print(jasperPrint);
        } catch (Exception e) {
            System.out.println("GeneralModel generateExcelList() JRException: " + e);
        }
        return reportInbytes;
    }
    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("StoppageModel setConnection() Error: " + e);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("StoppageModel closeConnection() Error: " + e);
        }
    }

}
