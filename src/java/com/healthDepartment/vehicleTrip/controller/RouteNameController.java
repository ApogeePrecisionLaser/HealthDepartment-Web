/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicleTrip.model.RouteNameModel;
import com.healthDepartment.vehicleTrip.tableClasses.RouteName;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
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
public class RouteNameController extends HttpServlet {
   
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
        RouteNameModel sm = new RouteNameModel();
        String task = request.getParameter("task");
        //String search_route_type = request.getParameter("search_route_type");
        String search_route_name = request.getParameter("search_route_name");
        String search_route_no = request.getParameter("search_route_no");
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
                if (JQstring.equals("getRoute")) {
                    list = sm.getRoute(q);
                }
                if (JQstring.equals("getRouteNo")) {
                    list = sm.getRouteNo(q);
                }
                if (JQstring.equals("getRouteType")) {
                    list = sm.getRouteType(q);
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

            if (task.equals("Cancel")) {
                sm.cancelRecord(Integer.parseInt(request.getParameter("route_name_id").trim()));
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int route_name_id = 0;
                try {
                    route_name_id = Integer.parseInt(request.getParameter("route_name_id").trim());
                } catch (Exception ex) {
                    route_name_id = 0;
                }
                if (task.equals("Save AS New")) {
                    route_name_id = 0;
                }

                String route_name = request.getParameter("route_name");
                //String route_type = request.getParameter("route_type");
                String route_no = request.getParameter("route_no");
                String remark = request.getParameter("remark");
                RouteName rn = new RouteName();
                rn.setRoute_name_id(route_name_id);
                rn.setRoute_name(route_name);
               // rn.setRoute_type(route_type);
                rn.setRoute_no(route_no);
                rn.setRemark(remark);
                if (task.equals("Save") || task.equals("Save AS New")) {
                    sm.insertRecord(rn);
                }

            }
            try {

                if (search_route_name == null) {
                    search_route_name = "";
                }
                if (search_route_no == null) {
                    search_route_no = "";
                }
//                if (search_route_type == null) {
//                    search_route_type = "";
//                }
            } catch (Exception e) {
            }
            if (task != null && task.equals("generateMapReport")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List<RouteName> mainList = sm.showData(-1, -1, search_route_name, search_route_no);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/route_name.jrxml");
                byte[] reportInbytes = sm.generateRecordList(jrxmlFilePath, mainList);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                sm.closeConnection();
                return;
            } else if (task != null && task.equals("generateReport")) {
                String jrxmlFilePath;
                List listAll = null;
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=RouteName.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List<RouteName> mainList = sm.showData(-1, -1, search_route_name, search_route_no);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/route_name.jrxml"); // organisationlist.jrxml
                ByteArrayOutputStream reportInbytes = sm.generateExcelList(jrxmlFilePath, mainList);
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
                //search_route_type = "";
                search_route_name = "";
                search_route_no = "";
            }
            noOfRowsInTable = sm.getNoOfRows( search_route_name, search_route_no);

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

            List<RouteName> list = sm.showData(lowerLimit, noOfRowsToDisplay, search_route_name, search_route_no);

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
            //request.setAttribute("search_route_type", search_route_type);
            request.setAttribute("search_route_name", search_route_name);
            request.setAttribute("search_route_no", search_route_no);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", sm.getMessage());

            request.getRequestDispatcher("/route_name").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
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
