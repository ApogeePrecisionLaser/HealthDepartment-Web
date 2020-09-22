/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicleTrip.model.RouteModel;
import com.healthDepartment.vehicleTrip.tableClasses.RouteBean;
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
public class RouteController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        ServletContext ctx = getServletContext();

        /*     HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
        response.sendRedirect("beforelogin.jsp");
        return;
        }
         *
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        RouteModel routeModel = new RouteModel();
        try {
            //       routeModel.setConnection(DBConnection.getConnection(ctx, session));
            routeModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in RouteController setConnection() calling try block" + e);
        }


        try {
            String requester = request.getParameter("requester");
            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getRouteName")) {
                        list = routeModel.getRouteName(q);
                    }
                    if (JQstring.equals("getStopageName")) {
                        list = routeModel.getStopageName(q);
                    }
                    if (JQstring.equals("getInputRouteName")) {
                        list = routeModel.getInputRouteName(q);
                    }
                    if (JQstring.equals("getInputStopageName")) {
                        list = routeModel.getInputStopageName(q);
                    }
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        if (data.equals("Disable")) {
                            out.print(data);
                        } else {
                            out.println(data);
                        }
                    }
                    routeModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --RouteController get JQuery Parameters Part-" + e);
            }

            String searchRouteName = request.getParameter("search_route_name");
            if (searchRouteName == null || searchRouteName.isEmpty()) {
                searchRouteName = "";
            }
            String searchStopageName = request.getParameter("search_stopage_name");
            if (searchStopageName == null || searchStopageName.isEmpty()) {
                searchStopageName = "";
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }

            if (task.equals("Delete")) {
                routeModel.deleteRecord(Integer.parseInt(request.getParameter("route_id")));
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Save & Next")) {
                int route_id;
                try {
                    // route_id may or may NOT be available i.e. it can be update or new record.
                    route_id = Integer.parseInt(request.getParameter("route_id"));
                } catch (Exception e) {
                    route_id = 0;
                }
                if (task.equals("Save AS New")) {
                    route_id = 0;
                }
                RouteBean routeBean = new RouteBean();
                routeBean.setRoute_id(route_id);
                String route_name = request.getParameter("route_name").trim();
                routeBean.setRoute_name(route_name);
                int route_name_id = routeModel.getRouteNameId(route_name);
                routeBean.setRoute_name_id(route_name_id);
                String stopage_name = request.getParameter("stopage_name").trim();
                routeBean.setStopage_name(stopage_name);
                int stopage_id = routeModel.getStopageId(stopage_name);
                routeBean.setStopage_id(stopage_id);
                String order_no = request.getParameter("order_no").trim();
                routeBean.setOrder_no(Integer.parseInt(order_no));
                if (route_id == 0) {
                    // if route_id was not provided, that means insert new record.
                  //  if (routeModel.checkStopage(stopage_id, route_name_id)) {
                        routeModel.insertRecord(routeBean);
                  //  }
                } else {
                    // update existing record.
               //     if (routeModel.checkStopage(stopage_id, route_name_id)) {
                        routeModel.updateRecord(routeBean);
               //     }
                }
                request.setAttribute("route_name", route_name);
            } else if (task.equals("Show All Records")) {
                searchRouteName = "";
                searchStopageName = "";
            }


            if (requester != null && requester.equals("PRINT")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List<RouteBean> mainList = routeModel.showData(-1, -1, searchRouteName, searchStopageName);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/routelist.jrxml");
//                byte[] reportInbytes = routeModel.generateRouteList(jrxmlFilePath);
                byte[] reportInbytes = routeModel.generateRecordList(jrxmlFilePath, mainList);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                routeModel.closeConnection();
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                String jrxmlFilePath;
                List listAll = null;
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=RouteList.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List<RouteBean> mainList = routeModel.showData(-1, -1, searchRouteName, searchStopageName);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/routelist.jrxml"); // organisationlist.jrxml
//                       ByteArrayOutputStream reportInbytes = routeModel.generateRouteXlsRecordList(jrxmlFilePath);
//                       response.setContentLength(reportInbytes.size());
//                       servletOutputStream.write(reportInbytes.toByteArray());
                ByteArrayOutputStream reportInbytes = routeModel.generateExcelList(jrxmlFilePath, mainList);
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


            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            }

            noOfRowsInTable = routeModel.getNoOfRows(searchRouteName, searchStopageName);

            if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
            else if (buttonAction.equals("Previous")) {
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

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }


            // Logic to show data in the table.
            List<RouteBean> routeDetailsList = routeModel.showData(lowerLimit, noOfRowsToDisplay, searchRouteName, searchStopageName);
            lowerLimit = lowerLimit + routeDetailsList.size();
            noOfRowsTraversed = routeDetailsList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("routeDetailsList", routeDetailsList);
            request.setAttribute("searchRouteName", searchRouteName);
            request.setAttribute("searchStopageName", searchStopageName);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", routeModel.getMessage());
            request.setAttribute("msgBgColor", routeModel.getMsgBgColor());
            routeModel.closeConnection();
            request.getRequestDispatcher("route").forward(request, response);

        } catch (Exception ex) {
            System.out.println("RouteController error: " + ex);
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
