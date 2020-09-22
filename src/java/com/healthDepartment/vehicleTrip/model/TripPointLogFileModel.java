/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.model;

import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicleTrip.tableClasses.TripPointLogFileBean;
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
public class TripPointLogFileModel {
private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public String Showtime(String week_days) {
        String start_time = "";
        String query = "select start_time, t.trip_id from trip t,route r,week_days w  where r.route_id=t.route_id "
                + " and t.week_days_id=w.week_days_id "
                + " and( day='" + week_days + "') ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                if (start_time.isEmpty()) {
                    start_time = rs.getInt("trip_id") + "_" + rs.getString("start_time");
                } else {
                    start_time = start_time + "##" + rs.getInt("trip_id") + "_" + rs.getString("start_time");
                }
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        return start_time;
    }

    public String Showweek(String route) {
        String day = "";
        route = krutiToUnicode.convert_to_unicode(route);
        String query = "select day from trip t,route r,week_days w, route_name rn  where r.route_id=t.route_id "
                + " and t.week_days_id=w.week_days_id and rn.route_name_id=r.route_name_id "
                + " and(rn.route_name='" + route + "') GROUP BY day ";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                day = day + rs.getString("day") + "##";
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        return day;
    }

    public List<String> getRoute(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select route_name from route_name "
                + " GROUP BY route_name ORDER BY route_name";
        try {

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String route_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("route_name"));
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

    public List<String> getStopageName(String trip_id) {
        List<String> list = new ArrayList<String>();
        //String query = "SELECT stopage_name FROM stopage";
        String query = "SELECT route_order_no, stopage_name, stopage_id, map_order_no,"
                + " IF(map_order_no is null, IF(1 =route_order_no, 'Y', 'N'),IF(map_order_no+1 =route_order_no, 'Y', 'N')) as status"
                + " FROM (SELECT r.order_no as route_order_no, stopage_name, s.stopage_id"
                + " FROM stopage s LEFT JOIN (route r, route r1, trip t) ON r.route_name_id=r1.route_name_id"
                + " AND r.stopage_id=s.stopage_id AND r1.route_id=t.route_id WHERE t.trip_id=" + trip_id + ") as route,"
                + " (SELECT max(order_no) map_order_no FROM trip_stopage_map WHERE trip_id=" + trip_id + ") as map having status = 'Y'";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {    // move cursor from BOR to valid record.
                String stopage_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("stopage_name"));
                list.add(stopage_name);
            } else {
                list.add("");
            }

        } catch (Exception e) {
            System.out.println("getCityName ERROR inside CityLocationModel - " + e);
        }
        return list;
    }

    public List<String> getWeek(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select day from week_days "
                + " GROUP BY day ORDER BY day ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String day = rset.getString("day");
                if (day.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(day);
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

    public List<String> getTime(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select start_time from trip "
                + " GROUP BY start_time ORDER BY start_time ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String start_time = rset.getString("start_time");
                if (start_time.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(start_time);
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

    public int getNoOfRows(String searchRoute, String searchDay, String searchTime, String date) {
        searchRoute = krutiToUnicode.convert_to_unicode(searchRoute);
        if (date != null && !date.isEmpty()) {
            String[] sdate_array = date.split("-");
            date = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        int noOfRows = 0;
        try {
            String query = "select count(*)"
                + " from trip_point_map ts "
                + " left join trip_point_log_file ats on ats.trip_point_map_id=ts.trip_point_map_id and date='"+date+"' "
                + " left join (trip t, week_days w) on t.trip_id=ts.trip_id AND w.week_days_id=t.week_days_id "
                + " left join (route r, route_name rn) on ts.point_id = r.point_id and ts.order_no=r.order_no AND rn.route_name_id=r.route_name_id "
                + " left join (point s) on r.point_id=s.point_id "
                    + " where IF('" + searchRoute + "' = '', rn.route_name LIKE '%%', rn.route_name ='" + searchRoute + "') "
                    + " And IF('" + searchTime + "' = '', t.start_time LIKE '%%', t.start_time ='" + searchTime + "') "
                    //+ " and  date='"+date+"' and  w.day ='" + searchDay + "' "
                    + " and r.active='Y' "
                    + " ORDER BY ts.trip_id ,ts.order_no ";
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = rset.getInt(1);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<TripPointLogFileBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchRoute, String searchDay, String searchTime, String date) {
        searchRoute = krutiToUnicode.convert_to_unicode(searchRoute);
        if (date != null && !date.isEmpty()) {
            String[] sdate_array = date.split("-");
            date = sdate_array[2] + "-" + sdate_array[1] + "-" + sdate_array[0];
        }
        List list = new ArrayList();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
//        String query = "select ts.trip_id, ts.trip_stopage_map_id,route_name,start_time,stopage_name,ts.arrival_time as sa,"
//                + " ts.depature_time as sd,ats.arrival_time as aa,ats.depature_time as ad,latitude,longitude"
//                + " from trip_stopage_map ts "
//                + " left join actual_trip_stopage ats on ats.trip_stopage_map_id=ts.trip_stopage_map_id and date='"+date+"' "
//                + " left join (trip t, week_days w) on t.trip_id=ts.trip_id AND w.week_days_id=t.week_days_id "
//                + " left join (route r, route_name rn) on ts.stopage_id = r.stopage_id and ts.order_no=r.order_no AND rn.route_name_id=r.route_name_id "
//                + " left join (stopage s, stoppage_city_location c) on r.stopage_id=s.stopage_id AND c.city_location_id=s.city_location_id "
//                + " where IF('" + searchRoute + "' = '', rn.route_name LIKE '%%', rn.route_name ='" + searchRoute + "') "
//                + " And IF('" + searchTime + "' = '', t.start_time LIKE '%%', t.start_time ='" + searchTime + "') "
//                 + "  and  w.day ='" + searchDay + "' "
//                + " ORDER BY ts.trip_id ,ts.order_no "
//                + addQuery;

        String query = "select ts.trip_id, ts.trip_point_map_id,route_name,start_time,point_name,ts.arrival_time as sa,"
                + " ts.depature_time as sd,ats.arrival_time as aa,ats.departure_time as ad,latitude,longitude"
                + " from trip_point_map ts "
                + " left join trip_point_log_file ats on ats.trip_point_map_id=ts.trip_point_map_id and date='"+date+"' "
                + " left join (trip t, week_days w) on t.trip_id=ts.trip_id AND w.week_days_id=t.week_days_id "
                + " left join (route r, route_name rn) on ts.point_id = r.point_id and ts.order_no=r.order_no AND rn.route_name_id=r.route_name_id "
                + " left join (point s) on r.point_id=s.point_id "
                + " where IF('" + searchRoute + "' = '', rn.route_name LIKE '%%', rn.route_name ='" + searchRoute + "') "
                + " And IF('" + searchTime + "' = '', t.start_time LIKE '%%', t.start_time ='" + searchTime + "') "
                // + "  and  w.day ='" + searchDay + "' "
                + " ORDER BY ts.trip_id ,ts.order_no "
                + " and r.active='Y' "
                + addQuery;
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TripPointLogFileBean sb = new TripPointLogFileBean();
                sb.setTrip_stopage_map_id(rs.getInt("trip_point_map_id"));
                sb.setStart_time(rs.getString("start_time"));


                if (lowerLimit == -1) {
                    sb.setRoute_name(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("route_name")));
                    //sb.setTrip_name(unicodeToKruti.Convert_to_Kritidev_010(rs.getString("trip_name")));
                } else {
                    sb.setRoute_name(rs.getString("route_name"));
                    //sb.setTrip_name(rs.getString("trip_name"));
                }
                //sb.setRoute_name(rs.getString("route_name"));
                sb.setStopage_name(rs.getString("point_name"));
                sb.setArrival_time(rs.getString("sa"));
                sb.setDeparture_time(rs.getString("sd"));
                sb.setArrival_time_a(rs.getString("aa"));
                sb.setDeparture_time_a(rs.getString("ad"));
                sb.setTrip_id(rs.getInt("trip_id"));
                sb.setLongitude(rs.getDouble("longitude"));
                sb.setLatitude(rs.getDouble("latitude"));
                list.add(sb);
            }
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return list;
    }

    public int updateRecord(TripPointLogFileBean bean) {
        String stopage_name = krutiToUnicode.convert_to_unicode(bean.getStopage_name());
        String route_name = krutiToUnicode.convert_to_unicode(bean.getRoute_name());
        String query = " UPDATE trip_stopage_map SET trip_id=" + bean.getStart_time() + ", "
                //                + "(select trip_id from trip t,week_days w,route r,route_name rn "
                //                + " where t.week_days_id=w.week_days_id "
                //                + " and t.route_id=r.route_id and rn.route_name_id=r.route_name_id "
                //                + "and rn.route_name='" + route_name + "' and day='" + bean.getWeek_days() + "' "
                //                + "and start_time='" + bean.getStart_time() + "'),"
                + " stopage_id=((select s.stopage_id from stopage s,route r, stoppage_city_location c "
                + " where s.stopage_id=r.stopage_id "
                + " and c.city_location_id=s.city_location_id "
                + " and c.location='" + stopage_name + "')), order_no='" + bean.getOrder_no() + "', "
                + " arrival_time='" + bean.getArrival_time() + "' , depature_time='" + bean.getDeparture_time() + "' "
                + " WHERE trip_stopage_map_id = '" + bean.getTrip_stopage_map_id() + "' ";

        //        "update brand set organisation_id=(select organisation_id from organisation_name where organisation_name='"+itemNameBean.getOrg_name()+"'), "
        //                + " brand_name='"+itemNameBean.getBrand_name()+"',remark='"+itemNameBean.getRemark()+"' where brand_id="+itemNameBean.getBrand_id()+"";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);

            //            pstmt.setString(1, bean.getRoute_name());
            //            pstmt.setString(2, bean.getWeek_days());
            //            pstmt.setString(3, bean.getTrip_name());
            //            pstmt.setString(4, bean.getStart_time());
            //            pstmt.setInt(5, bean.getTrip_id());

            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("CityLocationModel updateRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully......";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error......";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public boolean insertRecord(TripPointLogFileBean bean) {
        boolean status = false;
        int rowsAffected = 0;
        try {
            String stopage_name = krutiToUnicode.convert_to_unicode(bean.getStopage_name());
            String route_name = krutiToUnicode.convert_to_unicode(bean.getRoute_name());
//            String query = "insert into trip_stopage_map (trip_id,stopage_id,order_no,arrival_time,depature_time) "
//                    + " VALUES((select trip_id from trip t,week_days w,route r,route_name rn "
//                    + " WHERE t.week_days_id=w.week_days_id "
//                    + " AND t.route_id=r.route_id AND rn.route_name_id=r.route_name_id "
//                    + " AND rn.route_name='"+route_name+"' AND day='"+bean.getWeek_days()+"' "
//                    + " AND start_time='"+bean.getStart_time()+"'),(select s.stopage_id from stopage s,route r, route_name rn "
//                    + " where s.stopage_id=r.stopage_id AND rn.route_name_id=r.route_name_id AND rn.route_name='"+ route_name +"'"
//                    + " AND s.stopage_name='"+stopage_name+"'), (select r.order_no from stopage s,route r, route_name rn "
//                    + " where s.stopage_id=r.stopage_id AND rn.route_name_id=r.route_name_id AND rn.route_name='"+ route_name +"'"
//                    + " AND s.stopage_name='"+stopage_name+"'), '"+bean.getArrival_time()+"','"+bean.getDeparture_time()+"')";
//            String query = "insert into trip_stopage_map (trip_id,stopage_id,order_no,arrival_time,depature_time) "
//                    + " VALUES("+ bean.getTrip_id() +",(select s.stopage_id from stopage s,route r, route_name rn "
//                    + " where s.stopage_id=r.stopage_id AND rn.route_name_id=r.route_name_id AND rn.route_name='"+ route_name +"'"
//                    + " AND s.stopage_name='"+stopage_name+"'), (select r.order_no from stopage s,route r, route_name rn "
//                    + " where s.stopage_id=r.stopage_id AND rn.route_name_id=r.route_name_id AND rn.route_name='"+ route_name +"'"
//                    + " AND s.stopage_name='"+stopage_name+"'), '"+bean.getArrival_time()+"','"+bean.getDeparture_time()+"')";
            String query = "insert into trip_stopage_map (trip_id,stopage_id,order_no,arrival_time,depature_time) "
                    + " select " + bean.getTrip_id() + ", s.stopage_id, r.order_no, '" + bean.getArrival_time() + "','" + bean.getDeparture_time() + "'"
                    + " FROM stopage s,route r, route_name rn "
                    + " where s.stopage_id=r.stopage_id AND rn.route_name_id=r.route_name_id AND rn.route_name='" + route_name + "'"
                    + " AND s.stopage_name='" + stopage_name + "'";
            //+ " and s.stopage_name='"+stopage_name+"'),"+getorder_no( route_name,stopage_name)+",'"+bean.getArrival_time()+"','"+bean.getDeparture_time()+"')";
            // getorder_no( route_name,stopage_name);
            PreparedStatement ps = connection.prepareStatement(query);
            rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            }
        } catch (Exception e) {
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

    public int getorder_no(String route_name, String stoppage_name) {
        int order_no = 0;
        String query = " select order_no from route r, route_name rn, stopage s "
                + "where rn.route_name_id=r.route_name_id and r.stopage_id=s.stopage_id and "
                + "route_name='" + route_name + "' and s.stopage_name='" + stoppage_name + "' ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = connection.prepareStatement(query);
            rset = pstmt.executeQuery();
            if (rset.next()) {
                order_no = rset.getInt("order_no");
            }
        } catch (Exception e) {
            System.out.println("getMailStatus() RequisitionGenerationModel ERROR - " + e);
        }
        return order_no;
    }

    public boolean deleteRecord(int trip_stopage_map_id) {
        boolean status = false;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement("Delete from trip_stopage_map where trip_stopage_map_id=" + trip_stopage_map_id + " ").executeUpdate();
            if (rowsAffected > 0) {
                status = true;
            } else {
                status = false;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e);
        }
        if (rowsAffected > 0) {
            message = "Record Deleted successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Deleted");
        } else {
            message = "Record Not Deleted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Deleted");
        }
        return status;
    }
//    public List<String> getRoute(String q) {
//        List<String> list = new ArrayList<String>();
//        String query = "select route_name from route_name "
//                + " GROUP BY route_name ORDER BY route_name";
//        try {
//
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            int count = 0;
//            q = q.trim();
//            while (rset.next()) {    // move cursor from BOR to valid record.
//                String route_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("route_name"));
//                if (route_name.toUpperCase().startsWith(q.toUpperCase())) {
//                    list.add(route_name);
//                    count++;
//                }
//            }
//            if (count == 0) {
//                list.add("No such Status exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println("getCityName ERROR inside CityLocationModel - " + e);
//        }
//        return list;
//    }
//    public List<String> getTime(String q) {
//        List<String> list = new ArrayList<String>();
//        String query = "select start_time from trip "
//                + " GROUP BY start_time ORDER BY start_time ";
//        try {
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            int count = 0;
//            q = q.trim();
//            while (rset.next()) {    // move cursor from BOR to valid record.
//                String start_time = rset.getString("start_time");
//                if (start_time.toUpperCase().startsWith(q.toUpperCase())) {
//                    list.add(start_time);
//                    count++;
//                }
//            }
//            if (count == 0) {
//                list.add("No such Status exists.......");
//            }
//        } catch (Exception e) {
//            System.out.println("getCityName ERROR inside CityLocationModel - " + e);
//        }
//        return list;
//    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
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

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("CorrespondencePriorityModel closeConnection() Error: " + e);
        }
    }
}
