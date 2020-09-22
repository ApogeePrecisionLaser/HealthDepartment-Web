/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicleTrip.model.TripLogFileModel;
import com.healthDepartment.vehicleTrip.tableClasses.TripLogFileBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
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
public class TripLogFileController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        ServletContext ctx = getServletContext();
        TripLogFileModel sm = new TripLogFileModel();
        String task = request.getParameter("task");
        String searchTripName = request.getParameter("searchTripName");
       // String searchDay = request.getParameter("searchDay");
        String searchVehicleCode = request.getParameter("searchVehicleCode");
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 5, noOfRowsInTable = 0;
        String requester = request.getParameter("requester");
        try {

            sm.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print(e);
        }
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTripName")) {
                    list = sm.getTripName(q);
                }
                if (JQstring.equals("getWeek")) {
                    list = sm.getWeek(q);
                }
                if (JQstring.equals("getVehicleCode")) {
                    list = sm.getVehicleCode(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
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
            if (task.equals("getTime")) {
                PrintWriter out = response.getWriter();
                String day = request.getParameter("day");
                String start_time = sm.Showtime(day);
                out.println(start_time);
                return;

            }
            if (task.equals("getDay")) {
                PrintWriter out = response.getWriter();
                String route = request.getParameter("route");
                String week_day = sm.Showweek(route);
                out.println(week_day);
                return;

            }
            if (task.equals("Delete")) {
               // sm.cancelRecord(Integer.parseInt(request.getParameter("trip_id").trim()));
            } else if (task.equals("save") || task.equals("Save AS New")) {
                int trip_id = 0;
                try {
                    trip_id = Integer.parseInt(request.getParameter("trip_id").trim());
                } catch (Exception ex) {
                    trip_id = 0;
                }
                if (task.equals("Save AS New")) {
                    trip_id = 0;
                }
                String every_day = request.getParameter("every_day");
                String route_name = request.getParameter("route_name");
                String trip_name = request.getParameter("trip_name");
                String week_days = request.getParameter("week_days");
                String start_time_hour = request.getParameter("start_time_hour").trim();
                String start_time_min = request.getParameter("start_time_min").trim();

                TripLogFileBean sb = new TripLogFileBean();
                String starting_time = start_time_hour + ":" + start_time_min;
                if (starting_time.equals(":")) {
                    starting_time = "";
                }
//                sb.setTrip_id(trip_id);
//                sb.setStart_time(starting_time);
//                sb.setWeek_days(week_days);
//                sb.setRoute_name(route_name);
//                sb.setTrip_name(trip_name);
//                if (trip_id == 0) {
//                    sm.insertRecord(sb, every_day);
//                } else {
//                    System.out.println("Update values by model........");
//                    sm.updateRecord(sb);
//                }
            }
            try {

                if (searchTripName == null) {
                    searchTripName = "";
                }
                
                if (searchVehicleCode == null) {
                    searchVehicleCode = "";
                }
            } catch (Exception e) {
            }
            try{
            if (requester != null && requester.equals("PRINT")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List<TripLogFileBean> mainList = sm.showData(-1, -1, searchTripName, searchVehicleCode);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/tripLogFile.jrxml");
                byte[] reportInbytes = sm.generateRecordList(jrxmlFilePath, mainList);
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
                List<TripLogFileBean> mainList = sm.showData(-1, -1, searchTripName, searchVehicleCode);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/tripLogFile.jrxml"); // organisationlist.jrxml
                //                       ByteArrayOutputStream reportInbytes = routeModel.generateRouteXlsRecordList(jrxmlFilePath);
                //                       response.setContentLength(reportInbytes.size());
                //                       servletOutputStream.write(reportInbytes.toByteArray());
                ByteArrayOutputStream reportInbytes = sm.generateExcelList(jrxmlFilePath, mainList);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
            }catch(Exception e){
                System.out.println("Error in TripLogFileController "+e);
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
                searchTripName = "";
                searchVehicleCode = "";
                
            }
            noOfRowsInTable = sm.getNoOfRows(searchTripName, searchVehicleCode);

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

            List<TripLogFileBean> list = sm.showData(lowerLimit, noOfRowsToDisplay, searchTripName, searchVehicleCode);

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
            request.setAttribute("searchTripName", searchTripName);
            request.setAttribute("searchVehicleCode", searchVehicleCode);
            //request.setAttribute("searchTime", searchTime);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", sm.getMessage());
            RequestDispatcher rd = request.getRequestDispatcher("triplogfile");
            rd.forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
