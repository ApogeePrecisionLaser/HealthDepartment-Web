/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicleTrip.model.TripPointLogFileModel;
import com.healthDepartment.vehicleTrip.tableClasses.TripPointLogFileBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shobha
 */
public class TripPointLogFileController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException, ParseException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ServletContext ctx = getServletContext();
        TripPointLogFileModel sm = new TripPointLogFileModel();

        String task = request.getParameter("task");
        String searchRoute = request.getParameter("searchRoute");
        String searchDay = "", current_date = "";
        String searchTime = request.getParameter("searchTime");
        String date = request.getParameter("date");
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        String requester = request.getParameter("requester");
        try {

            sm.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print(e);
        }

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         DateFormat format2 = new SimpleDateFormat("EEEE");
        Date date1 = new Date();
        current_date = dateFormat.format(date1);
        if (date == null) {
            date = current_date;
            Date dt1 = dateFormat.parse(current_date);
            searchDay = format2.format(dt1).substring(0, 3);
        } else {
            Date dt1 = dateFormat.parse(date);
            searchDay = format2.format(dt1).substring(0, 3);
        }
        try {
            //----- This is only for Vendor key Person JQuery

            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getRoute")) {
                    list = sm.getRoute(q);
                     //out.println(list);
                } else if (JQstring.equals("getTime")) {
                    list = sm.getTime(q);
                    //out.println(list);
                } 
                    
                
                if (list != null) {
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        out.println(data);
                    }
                }
                sm.closeConnection();
                out.close();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

        if (task == null || task.isEmpty()) {
            task = "";
        }

        try {
            if (task.equals("Delete")) {
                sm.deleteRecord(Integer.parseInt(request.getParameter("trip_stopage_map_id").trim()));
            } else if (task.equals("save") || task.equals("Save AS New")) {
                int trip_stopage_map_id = 0;
                try {
                    trip_stopage_map_id = Integer.parseInt(request.getParameter("trip_stopage_map_id").trim());
                } catch (Exception ex) {
                    trip_stopage_map_id = 0;
                }
                if (task.equals("Save AS New")) {
                    trip_stopage_map_id = 0;
                }
                String route_name = request.getParameter("route_name");
                String week_days = request.getParameter("week_days");
                String stopage_name = request.getParameter("stopage_name");
                String order_no = request.getParameter("order_no");
                String starting_time_hour = request.getParameter("starting_time_hour").trim();
                String starting_time_min = request.getParameter("starting_time_min").trim();
                String start_time = request.getParameter("start_time").trim();
                String trip_id = request.getParameter("start_time").trim();
                String ending_time_hour = request.getParameter("ending_time_hour").trim();
                String ending_time_min = request.getParameter("ending_time_min").trim();
                TripPointLogFileBean sb = new TripPointLogFileBean();
                String starting_time = starting_time_hour + ":" + starting_time_min;
                if (starting_time.equals(":")) {
                    starting_time = "";
                }
                String ending_time = ending_time_hour + ":" + ending_time_min;
                if (ending_time.equals(":")) {
                    ending_time = "";
                }
                sb.setTrip_stopage_map_id(trip_stopage_map_id);
                sb.setStart_time(start_time);
                sb.setTrip_id(Integer.parseInt(trip_id));
                sb.setArrival_time(starting_time);
                sb.setDeparture_time(ending_time);
                sb.setStopage_name(stopage_name);
                sb.setWeek_days(week_days);
                sb.setRoute_name(route_name);
                sb.setOrder_no(order_no);
                if (trip_stopage_map_id == 0) {
                    sm.insertRecord(sb);
                } else {
                    System.out.println("Update values by model........");
                    sm.updateRecord(sb);
                }
                request.setAttribute("route_name", route_name);
                request.setAttribute("week_days", week_days);
                request.setAttribute("start_time", start_time);
            }
            try {
                if (searchRoute == null) {
                    searchRoute = "";
                }
                if (searchTime == null) {
                    searchTime = "";
                }
            } catch (Exception e) {
            }
            if (task.equals("showMapWindow")) {
            String longi = request.getParameter("logitude");
            String latti = request.getParameter("lattitude");
            request.setAttribute("longi", longi);
            request.setAttribute("latti", latti);
            System.out.println(latti + "," + longi);
            request.getRequestDispatcher("openMapWindowView1").forward(request, response);
            return;
        }


            if (requester != null && requester.equals("PRINT")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List<TripPointLogFileBean> mainList = sm.showData(-1, -1, searchRoute, searchDay, searchTime, date);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/tripPointLogFile.jrxml");
                byte[] reportInbytes = TripPointLogFileModel.generateRecordList(jrxmlFilePath, mainList);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                sm.closeConnection();
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                String jrxmlFilePath;
                List listAll = null;
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=RouteList.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List<TripPointLogFileBean> mainList = sm.showData(-1, -1, searchRoute, searchDay, searchTime, date);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/tripPointLogFile.jrxml"); // organisationlist.jrxml
                //                       ByteArrayOutputStream reportInbytes = routeModel.generateRouteXlsRecordList(jrxmlFilePath);
                //                       response.setContentLength(reportInbytes.size());
                //                       servletOutputStream.write(reportInbytes.toByteArray());
                ByteArrayOutputStream reportInbytes = TripPointLogFileModel.generateExcelList(jrxmlFilePath, mainList);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            String buttonAction = request.getParameter("buttonAction");
            if (buttonAction == null) {
                buttonAction = "none";
            }
            if (task.equals("Show All Records")) {
                searchRoute = "";
                searchTime = "";
                date = current_date;
            }
            noOfRowsInTable = sm.getNoOfRows(searchRoute, searchDay, searchTime, date);

            if (buttonAction.equals("Next")); else if (buttonAction.equals("Previous")) {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;

                } else {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First")) {
                lowerLimit = 0;
            } else if (buttonAction.equals("Last")) {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }
            if (task.equals("save") || task.equals("Save AS New") || task.equals("Delete")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;
            }

            List<TripPointLogFileBean> list = sm.showData(lowerLimit, noOfRowsToDisplay, searchRoute, searchDay, searchTime, date);

            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();
            if ((lowerLimit - noOfRowsTraversed) == 0) {
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("list", list);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("searchRoute", searchRoute);
            request.setAttribute("searchDay", searchDay);
            request.setAttribute("searchTime", searchTime);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", sm.getMessage());
            request.setAttribute("date", date);
            request.getRequestDispatcher("trippointlog").forward(request, response);
        } catch (Exception e) {
            System.out.print(e);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(TripPointLogFileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(TripPointLogFileController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
