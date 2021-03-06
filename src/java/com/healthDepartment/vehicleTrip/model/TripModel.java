/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.model;


import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.healthDepartment.vehicleTrip.tableClasses.TripBean;
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
public class TripModel {
private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
//    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
//    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

    public String Showtime(String week_days) {
        String start_time = "";
        String query = "select start_time from trip t,route r,week_days w  where r.route_id=t.route_id "
                + " and t.week_days_id=w.week_days_id "
                + " and( day='" + week_days + "') ";

        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                //               unit_name =unit_name+rs.getInt("id1")+"_"+rs.getInt("id2")+"&#"+rs.getString("un2")+"##";
                start_time = start_time + rs.getString("start_time") + "##";

                //list.add(uploaded_req_rev_no);
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        return start_time;
    }

    public String Showweek(String route) {
        String day = "";
        String query = "select day from trip t,route r,week_days w, route_name rn  where r.route_id=t.route_id "
                + " and t.week_days_id=w.week_days_id and rn.route_name_id=r.route_name_id "
                + " and(rn.route_name='" + route + "') ";

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
                String route_name =rset.getString("route_name");
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

    public int getNoOfRows(String searchRoute, String searchDay, String searchTime) {
       // searchRoute = krutiToUnicode.convert_to_unicode(searchRoute);
        int noOfRows = 0;
        try {
            String query = " select count(*) from (select count( trip_id) "
                    + "from trip t,route r,week_days w,route_name rn "
                    + " where t.route_id=r.route_id "
                    + "and w.week_days_id=t.week_days_id and rn.route_name_id=r.route_name_id "
                    + "And IF('" + searchRoute + "' = '', rn.route_name LIKE '%%', rn.route_name ='" + searchRoute + "') "
                    + "And IF('" + searchDay + "' = '', w.day LIKE '%%', w.day ='" + searchDay + "') "
                    + "And IF('" + searchTime + "' = '', t.start_time LIKE '%%', t.start_time ='" + searchTime + "') and t.active='y' group by trip_id ) as t ";
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

    public List<TripBean> showData(int lowerLimit, int noOfRowsToDisplay, String searchRoute, String searchDay, String searchTime) {
        List list = new ArrayList();
       // searchRoute = krutiToUnicode.convert_to_unicode(searchRoute);
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }
        String query = "select t.trip_id, t.trip_name, "
                + "w.day,rn.route_name,t.start_time "
                + "from trip t,route r,week_days w,route_name rn "
                + " where t.route_id=r.route_id "
                + "and w.week_days_id=t.week_days_id and rn.route_name_id=r.route_name_id "
                + "And IF('" + searchRoute + "' = '', rn.route_name LIKE '%%', rn.route_name ='" + searchRoute + "') "
                + "And IF('" + searchDay + "' = '', w.day LIKE '%%', w.day ='" + searchDay + "') "
                + "And IF('" + searchTime + "' = '', t.start_time LIKE '%%', t.start_time ='" + searchTime + "') and t.active='y'"
                + " group by trip_id ORDER BY rn.route_name, w.week_days_id, t.start_time "
                + addQuery;
        try {
            PreparedStatement ps = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                TripBean sb = new TripBean();
                sb.setTrip_id(rs.getInt("trip_id"));
                sb.setStart_time(rs.getString("start_time"));
                sb.setWeek_days(rs.getString("day"));
                if (lowerLimit == -1) {
                    sb.setRoute_name(rs.getString("route_name"));
                    sb.setTrip_name(rs.getString("trip_name"));
                } else {
                    sb.setRoute_name(rs.getString("route_name"));
                    sb.setTrip_name(rs.getString("trip_name"));
                }
                list.add(sb);
            }
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return list;
    }

    public int updateRecord(TripBean bean) {
        String query = " UPDATE trip SET route_id=(select route_id from route r,route_name rn  "
                + "where rn.route_name_id=r.route_name_id and route_name=? and order_no=1 and  r.active='Y' ),"
                + "week_days_id=(select week_days_id from week_days where day=?), trip_name=?, start_time=? "
                + " WHERE trip_id = ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            pstmt.setString(1, bean.getRoute_name());
            pstmt.setString(2, bean.getWeek_days());
            pstmt.setString(3, bean.getTrip_name());
            pstmt.setString(4, bean.getStart_time());
            pstmt.setInt(5, bean.getTrip_id());
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

    public boolean insertRecord(TripBean bean, String every_day) {
        boolean status = false;
        int rowsAffected = 0;
        String route_name =bean.getRoute_name();
        String trip_name = bean.getTrip_name();//krutiToUnicode.convert_to_unicode(bean.getTrip_name());
        String weekDaysQuery = "SELECT week_days_id, day FROM week_days";
        String query = "insert into trip (trip_name, route_id,week_days_id,start_time) "
                + "values(?, (select r.route_id from route r,route_name rn "
                + " where rn.route_name_id=r.route_name_id "
                + "and rn.route_name=? and order_no=1 and  r.active='Y' )  "
                + ",(select w.week_days_id from week_days w where day=?),?) ";
        try {
            if (every_day != null && every_day.equals("Y")) {
                ResultSet rs = connection.prepareStatement(weekDaysQuery).executeQuery();
                while (rs.next()) {
                    String start_time = bean.getStart_time();
                    if(validateTrip(route_name, rs.getInt("week_days_id"), start_time) == 0) {
                        PreparedStatement pst = connection.prepareStatement(query);
                        pst.setString(1, trip_name);
                        pst.setString(2, route_name);
                        pst.setString(3, rs.getString("day"));
                        pst.setString(4, start_time);
                        rowsAffected = pst.executeUpdate();
                    }
                }
            } else {
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, trip_name);
                pst.setString(2, route_name);
                pst.setString(3, bean.getWeek_days());
                pst.setString(4, bean.getStart_time());
                rowsAffected = pst.executeUpdate();
            }
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

    public int validateTrip(String route_name, int week_days_id, String time) {
        int id = 0;
        String query = "SELECT t.trip_id FROM trip t, route r,route_name rn "
                + " WHERE t.route_id=r.route_id AND rn.route_name_id=r.route_name_id AND order_no=1 "
                + " AND t.start_time='" + time + "' AND rn.route_name='" + route_name + "' AND week_days_id=" + week_days_id;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                id = rs.getInt("trip_id");
            }
        } catch (Exception ex) {
        }
        return id;
    }

    public int getorder_no(String route_name, String stoppage_name) {
        int order_no = 0;
        String query = " select order_no from route r,trip  t,route_name rn, stoppage_city_location c where t.route_id=r.route_id "
                + "and rn.route_name_id=r.route_name_id and route_name='" + route_name + "' and c.location=' " + stoppage_name + "' ";
        PreparedStatement pstmt;
        ResultSet rset;
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(query);
            //            pstmt.setString(1, );
            rset = pstmt.executeQuery();
            if (rset.next()) {
                order_no = rset.getInt("order_no");
            }
        } catch (Exception e) {
            System.out.println("getMailStatus() RequisitionGenerationModel ERROR - " + e);
        }
        return order_no;
    }

    public int cancelRecord(int trip_id) {
        int rowsAffected = 0;
        String query = "UPDATE trip SET active='N' WHERE trip_id=" + trip_id;
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
