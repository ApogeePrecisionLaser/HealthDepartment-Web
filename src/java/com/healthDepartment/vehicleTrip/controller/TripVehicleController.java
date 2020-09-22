/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicleTrip.model.TripVehicleModel;
import com.healthDepartment.vehicleTrip.tableClasses.TripVehicleBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
public class TripVehicleController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

         response.setContentType("text/html;charset=UTF-8");
         response.setCharacterEncoding("UTF-8");
         request.setCharacterEncoding("UTF-8");
         ServletContext ctx = getServletContext();
         TripVehicleModel vkpm=new TripVehicleModel();
         String task=request.getParameter("task");
          int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
          String requester = request.getParameter("requester");
        try
        {

            vkpm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }

          try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;

                 if (JQstring.equals("getsearch_trip_name"))
                {

                    list = vkpm.getSearchTripName(q);
                }
               Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
        }


          try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;

                 if (JQstring.equals("getsearch_vehicle_code"))
                {

                    list = vkpm.getSearchVehicleCode(q);
                }
               Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
          /////////////////////////////////////////////

           try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getTrip_name"))
                {
                    list = vkpm.getTrip_name(q);
                }

               Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

          try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;

                 if (JQstring.equals("getVehicle_code"))
                {

                    list = vkpm.getVehicle_code(q);
                }
               Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
                return;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
  String  search_trip_name=request.getParameter("search_trip_name");
             if(search_trip_name==null)
                search_trip_name="";
  String  search_vehicle_code=request.getParameter("search_vehicle_code");
             if(search_vehicle_code==null)
                search_vehicle_code="";
           if(task==null||task.isEmpty())
            {
                task="";
            }
      try{
          if(task.equals("Delete"))
                 vkpm.deleteRecord(Integer.parseInt(request.getParameter("trip_vehicle_map_id").trim()));
        else if(task.equals("save") || task.equals("Save AS New"))
             {
                 int trip_vehicle_map_id=0;
                 try{
                     trip_vehicle_map_id = Integer.parseInt(request.getParameter("trip_vehicle_map_id").trim());
                 }catch(Exception ex){
                     trip_vehicle_map_id = 0;
                 }

                 if(task.equals("Save AS New"))
                     trip_vehicle_map_id = 0;
             int vehicle_code=Integer.parseInt(request.getParameter("vehicle_code"));
             String trip_name=request.getParameter("trip_name");
             String date=request.getParameter("date");
            // int vehicle_id=vkpm.getVehicleId(vehicle_code);
             //int key_person_id=vkpm.getKeyPersonId(trip_name);

             TripVehicleBean vt=new TripVehicleBean();
             vt.setTrip_vehicle_map_id(trip_vehicle_map_id);
             vt.setVehicle_code(vehicle_code);
             vt.setTrip_name(trip_name);
             vt.setDate(date);
             if(trip_vehicle_map_id>0){
                 vkpm.reviseRecords(vt);
             }else{
            vkpm.insertRecord(vt);
                 }
         }
                else if (task.equals("Show All Records")) {
                    search_trip_name="";
                    search_vehicle_code="";
            }


          try{
//             if (task.equals("generateHSReport"))
//           {
//              String jrxmlFilePath;
//              List li=null;
//              response.setContentType("application/pdf");
//              ServletOutputStream servletOutputStream = response.getOutputStream();
//              jrxmlFilePath = ctx.getRealPath("/report/vehicle/VehicleKeyPerson.jrxml");
//              li=vkpm.showData(-1,-1,search_vehicle_code,search_trip_name);
//              byte[] reportInbytes =vkpm.generateRecordList(jrxmlFilePath,li);
//              response.setContentLength(reportInbytes.length);
//              servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
//              servletOutputStream.flush();
//              servletOutputStream.close();
//
//              return;
//                 }
             if (requester != null && requester.equals("PRINT")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List<TripVehicleBean> mainList = vkpm.showData(-1,-1,search_vehicle_code,search_trip_name);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/tripVehicleMap.jrxml");
                byte[] reportInbytes = vkpm.generateRecordList(jrxmlFilePath, mainList);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                //vkpm.closeConnection();
                return;
            } else if (requester != null && requester.equals("PRINTXls")) {
                String jrxmlFilePath;
                List listAll = null;
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=RouteList.xls");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                List<TripVehicleBean> mainList = vkpm.showData(-1,-1,search_vehicle_code,search_trip_name);
                jrxmlFilePath = ctx.getRealPath("/report/vehicleTrip/tripVehicleMap.jrxml"); // organisationlist.jrxml
                //                       ByteArrayOutputStream reportInbytes = routeModel.generateRouteXlsRecordList(jrxmlFilePath);
                //                       response.setContentLength(reportInbytes.size());
                //                       servletOutputStream.write(reportInbytes.toByteArray());
                ByteArrayOutputStream reportInbytes = vkpm.generateExcelList(jrxmlFilePath, mainList);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                return;
            }
           }catch(Exception e){
               System.out.println("com.mis.controller ErrorLogMsgController generateHSReport Error "+e);
           }

        String buttonAction = request.getParameter("buttonAction");
             if(buttonAction == null)
                 buttonAction = "none";

              try {
        lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
        noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }

     noOfRowsInTable = vkpm.getNoOfRows(search_vehicle_code,search_trip_name);

    if (buttonAction.equals("Next"));
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
     if (task.equals("save") || task.equals("Save AS New") ||  task.equals("Delete")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;
        }

         List<TripVehicleBean>  list=  vkpm.showData(lowerLimit, noOfRowsToDisplay,search_vehicle_code,search_trip_name);

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
         request.setAttribute("lowerLimit",lowerLimit);
         // request.setAttribute("vehicleType", vehicleType);

         request.setAttribute("search_vehicle_code", search_vehicle_code);
         request.setAttribute("search_trip_name", search_trip_name);

         request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", vkpm.getMessage());

            RequestDispatcher rd=request.getRequestDispatcher("tripvehicle");
               rd.forward(request, response);
        } catch(Exception e) {
            System.out.println(e);
            //out.close();
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
