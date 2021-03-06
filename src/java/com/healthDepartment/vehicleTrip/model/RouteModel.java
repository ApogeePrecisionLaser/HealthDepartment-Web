/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicle.tableClasses.Point;
import com.healthDepartment.vehicleTrip.tableClasses.RouteBean;
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
public class RouteModel {
private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
//    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("RouteModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String searchRouteName, String searchStopageName) {
        int noOfRows = 0;
       // searchRouteName = krutiToUnicode.convert_to_unicode(searchRouteName);
       // searchStopageName = krutiToUnicode.convert_to_unicode(searchStopageName);
        try {

//            String query = " select  COUNT(*) from route AS r, route_name as rn, stopage as s "
//                          + " WHERE r.route_name_id = rn.route_name_id And r.stopage_id = s.stopage_id "
//                          + " And rn.route_name like ? And s.stopage_name like ? ";
//            PreparedStatement pstmt = connection.prepareStatement(query);
//            pstmt.setString(1, searchRouteName + '%');
//            pstmt.setString(2, searchStopageName + '%');
//            ResultSet rset = pstmt.executeQuery();
//            rset.next();
//            noOfRows = Integer.parseInt(rset.getString(1));

            String query = " SELECT count(r.route_id) "
                     + " FROM route AS r, route_name as rn, point as s "
                    + " WHERE r.route_name_id = rn.route_name_id And r.point_id = s.point_id and r.active='Y' "
                    + " AND if('" + searchRouteName + "'='', rn.route_name LIKE '%%', rn.route_name LIKE '%" + searchRouteName + "%') " // rn.route_name=?
                    + " AND if('" + searchStopageName + "'='', s.point_name LIKE '%%', s.point_name='" + searchStopageName + "') "; //s.stopage_name LIKE '%"+searchStopageName+"%'

            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, searchRouteName);
            //pstmt.setString(2, searchStopageName);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                noOfRows = Integer.parseInt(rset.getString(1));
            }

        } catch (Exception e) {
            System.out.println("RouteModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<RouteBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchRouteName, String searchStopageName) {
        List<RouteBean> list = new ArrayList<RouteBean>();
      //  searchRouteName = krutiToUnicode.convert_to_unicode(searchRouteName);
        //searchStopageName = krutiToUnicode.convert_to_unicode(searchStopageName);
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1 && noOfRowsToDisplay == -1) {
            addQuery = "";
        }
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        try {
            String query = "SELECT r.route_id, rn.route_name, s.point_name, r.order_no "
                    + " FROM route AS r, route_name as rn, point as s "
                    + " WHERE r.route_name_id = rn.route_name_id And r.point_id = s.point_id and r.active='Y' "
                    //                + " And rn.route_name like ? And s.stopage_name like ? "
                    + " AND if('" + searchRouteName + "'='', rn.route_name LIKE '%%', rn.route_name LIKE '%" + searchRouteName + "%') " // rn.route_name=?
                    + " AND if('" + searchStopageName + "'='', s.point_name LIKE '%%', s.point_name='" + searchStopageName + "') " //s.stopage_name LIKE '%"+searchStopageName+"%'
                    + " ORDER BY rn.route_name,s.point_name,r.order_no"
                    + addQuery;

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                RouteBean routeBean = new RouteBean();
                routeBean.setRoute_id(rset.getInt("route_id"));
                if (lowerLimit == -1 && noOfRowsToDisplay == -1) {
                    routeBean.setRoute_name(rset.getString("route_name"));
                    routeBean.setStopage_name(rset.getString("point_name"));
                } else {
                    routeBean.setRoute_name(rset.getString("route_name"));
                    routeBean.setStopage_name(rset.getString("point_name"));
                }
                routeBean.setOrder_no(rset.getInt("order_no"));
                list.add(routeBean);
            }
        } catch (Exception e) {
            System.out.println("RouteModel showData() Error: " + e);
        }
        return list;
    }

    public int getRouteNameId(String route_name) {
        int route_name_id = 0;
        //route_name = krutiToUnicode.convert_to_unicode(route_name);
        String query = "SELECT route_name_id FROM route_name WHERE route_name = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, route_name);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                route_name_id = rset.getInt("route_name_id");
            }
        } catch (Exception e) {
            System.out.println("RouteModel getRouteNameId() Error: " + e);
        }

        return route_name_id;
    }

    public int getStopageId(String stopage_name) {
        int stopage_id = 0;
        //stopage_name = krutiToUnicode.convert_to_unicode(stopage_name);
        String query = "SELECT point_id FROM point WHERE point_name = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, stopage_name);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                stopage_id = rset.getInt("point_id");
            }
        } catch (Exception e) {
            System.out.println("RouteModel getStopageId() Error: " + e);
        }

        return stopage_id;
    }

    public boolean checkStopage(int stopage_id, int route_name_id) {
        boolean check = true;
        int count = 0, stopageId = 0;
        List<Integer> list = new ArrayList<Integer>();
        String query = "SELECT stopage_id FROM route WHERE route_name_id = " + route_name_id;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                stopageId = rset.getInt("stopage_id");
                list.add(stopageId);
            }

            for (int i = 0; i < list.size(); i++) {
                if (stopage_id == list.get(i)) {
                    check = false;
                }
            }

        } catch (Exception e) {
            System.out.println("RouteModel checkStopage() Error: " + e);
            check = false;
        }

        return check;
    }

    public int allotOrder_no(int route_name_id) {
        int order_no = 0, stopageId = 0, maxOrderNo = 0;
        List<Integer> list = new ArrayList<Integer>();
        String query = "SELECT stopage_id FROM route WHERE route_name_id = " + route_name_id;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                stopageId = rset.getInt("stopage_id");
                list.add(stopageId);
            }

            if (list.isEmpty()) {
                order_no = 1;
            } else {
                String query1 = "select max(order_no) order_no from route where route_name_id = " + route_name_id;
                try {
                    ResultSet rst = connection.prepareStatement(query1).executeQuery();
                    if (rst.next()) {
                        maxOrderNo = rst.getInt("order_no");
                    }

                    order_no = maxOrderNo + 1;

                } catch (Exception e) {
                    System.out.println("RouteModel allotOrder_no() query1 block Error: " + e);
                }
            }

        } catch (Exception e) {
            System.out.println("RouteModel allotOrder_no() Error: " + e);
        }

        return order_no;
    }

    public int insertRecord(RouteBean routeBean) {
        String query = "INSERT INTO route(route_name_id,point_id,order_no) VALUES(?, ?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, routeBean.getRoute_name_id());
            pstmt.setInt(2, routeBean.getStopage_id());
            pstmt.setInt(3, routeBean.getOrder_no()); //allotOrder_no(routeBean.getRoute_name_id()
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("RouteModel insertRecord() Error: " + e);
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

    public int updateRecord(RouteBean routeBean) {
        String query = "INSERT INTO route(route_name_id,point_id,order_no,route_id,revision_no,active) VALUES(?,?,?,?,?,?) ";
        String max_query = "select max(revision_no) from route  WHERE route_id =" + routeBean.getRoute_id();
        String update_query = "update route set active='N' where route_id = " + routeBean.getRoute_id();
        int rowsAffected = 0;
        int revision_no = 0;
        try {
            connection.prepareStatement(update_query).executeUpdate();
            PreparedStatement ps = connection.prepareStatement(max_query);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                revision_no = rs.getInt(1);
            }
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, routeBean.getRoute_name_id());
            pstmt.setInt(2, routeBean.getStopage_id());
            pstmt.setInt(3, routeBean.getOrder_no()); //allotOrder_no(routeBean.getRoute_name_id())
            pstmt.setInt(4, routeBean.getRoute_id());
            pstmt.setInt(5, revision_no + 1);
            pstmt.setString(6, "Y");
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("RouteModel updateRecord() Error: " + e);
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

    public int deleteRecord(int route_id) {
        //    String query = "DELETE FROM route WHERE route_id = " + route_id;
        String query = "update route set active='N' where route_id = " + route_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("RouteModel deleteRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getRouteName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT rn.route_name FROM route_name as rn, route as r "
                + " Where r.route_name_id=rn.route_name_id "
                + " group by rn.route_name ORDER BY rn.route_name";
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
        } catch (Exception e) {
            System.out.println("Error:RouteModel--getRouteName()-- " + e);
        }
        return list;
    }

    public List<String> getStopageName(String q) {
        List<String> list = new ArrayList<String>();
//        String query = "SELECT s.stopage_name FROM stopage as s, route as r "
//                + " Where r.stopage_id=s.stopage_id "
//                + " group by s.stopage_name ORDER BY s.stopage_name ";
        String query = "SELECT s.point_name FROM point as s, route as r "
                + " Where r.point_id=s.point_id "
                + " group by s.point_name ORDER BY s.point_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String stopage_name = rset.getString("point_name");
                if (stopage_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(stopage_name);
                    count++;
                }
            }
        } catch (Exception e) {
            System.out.println("Error:RouteModel--getStopageName()-- " + e);
        }
        return list;
    }
 public List<Point> showDataLattitude(String searchArea) {
        List<Point> list = new ArrayList<Point>();
        
        //int client_equip_id = getClientEquipId(user_id,equip_name);
         if(searchArea==null)
            searchArea="";
               
        int rev = 3;
       String query = "select p.latitude,p.longitude from route r,point p,route_name rn \n" +
"where r.route_name_id=rn.route_name_id and \n" +
"r.point_id=p.point_id and rn.route_name='"+searchArea+"' ";
 
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                Point bean = new Point();
                bean.setLatitude(rset.getString("latitude"));
                 bean.setLongitude(rset.getString("longitude"));
                 
                list.add(bean);
            }
        } catch (Exception e) {
            System.out.println("Error in ShowDataBean() :ShiftLoginModel" + e);
        }
        return list;
    }
    public List<String> getInputRouteName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT rn.route_name FROM route_name as rn "
                //+ " Where r.route_name_id=rn.route_name_id "
                + " group by rn.route_name ORDER BY rn.route_name";
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
        } catch (Exception e) {
            System.out.println("Error:RouteModel--getInputRouteName()-- " + e);
        }
        return list;
    }
 public int getAreaId(String area,String ward,String zone) {
        int area_id = 0;
        //stopage_name = krutiToUnicode.convert_to_unicode(stopage_name);
        String query = "select area_id from area as a, ward as w,zone as z "
                + "where a.ward_id=w.ward_id and w.zone_id=z.zone_id and a.area_name='"+area+"' "
                + "and w.ward_name='"+ward+"' and z.zone_name='"+zone+"' ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                area_id = rset.getInt("area_id");
            }
        } catch (Exception e) {
            System.out.println("RouteModel getStopageId() Error: " + e);
        }

        return area_id;
    }
  public int getCityId(int areaid) {
        int city_location_id = 0;
        //stopage_name = krutiToUnicode.convert_to_unicode(stopage_name);
        String query = "select city_location_id from city_location where area_id='"+areaid+"'";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
           
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                city_location_id = rset.getInt("city_location_id");
            }
        } catch (Exception e) {
            System.out.println("RouteModel getStopageId() Error: " + e);
        }

        return city_location_id;
    }

    public List<String> getInputStopageName(String area,String ward,String zone) {
        int area_id=getAreaId( area, ward, zone);
        int cl_id=getCityId(area_id);
        List<String> list = new ArrayList<String>();
        String query = "SELECT s.point_name FROM point as s  where s.city_location_id='"+cl_id+"' "
              
                + " group by s.point_name ORDER BY s.point_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            
            while (rset.next()) {    // move cursor from BOR to valid record.
                String stopage_name = rset.getString("point_name");
           
                    list.add(stopage_name);
                    count++;
                
            }
        } catch (Exception e) {
            System.out.println("Error:RouteModel--getInputStopageName()-- " + e);
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
            System.out.println("RouteModel closeConnection() Error: " + e);
        }
    }
}
