/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.complain.controller;

import com.healthDepartment.complain.model.ErrorLogModel;
import com.healthDepartment.complain.tableClasses.ErrorLog;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
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
public class ErrorLogController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        System.out.println("this is OHLevelController ....");
        ServletContext ctx = getServletContext();
        ErrorLogModel OnOffError = new ErrorLogModel();
        String task=" ";

        OnOffError.setDriver(ctx.getInitParameter("driverClass"));
        OnOffError.setUrl(ctx.getInitParameter("connectionString"));
        OnOffError.setUser(ctx.getInitParameter("db_username"));
        OnOffError.setPassword(ctx.getInitParameter("db_password"));
        try {
            OnOffError.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception ex) {
            System.out.println(ex);
        }
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        try {
               // PrintWriter pw = response.getWriter();
                String JQString = request.getParameter("action1");
                String q = request.getParameter("q");

                if (JQString != null) {
                     PrintWriter pw = response.getWriter();
                    List<String> list = null;
                    if (JQString.equals("getVehicleCode")) {
                        String action1 = request.getParameter("action1");

                        //call getPriority() method
                        list = OnOffError.getVehicleCode(q);
                    }
                    Iterator<String> itr = list.iterator();
                    while (itr.hasNext()) {
                        String data = itr.next();
                        pw.println(data);
                    }

                    return;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        String error_person=request.getParameter("search_error_person");

        if(error_person==null){
            error_person="";
        }


         try {
               // PrintWriter pw = response.getWriter();
                String JQString = request.getParameter("action1");
                String q = request.getParameter("q");

                if (JQString != null) {
                     PrintWriter pw = response.getWriter();
                    List<String> list = null;
                    if (JQString.equals("getErrorPerson")) {
                        String action1 = request.getParameter("action1");
                        if (action1 == null) {
                            action1 = "";
                        }
                        //call getPriority() method
                        list = OnOffError.getErrorPersonName(q);
                    }
                    Iterator<String> itr = list.iterator();
                    while (itr.hasNext()) {
                        String data = itr.next();
                        pw.println(data);
                    }
                    return;
                }
            } catch (Exception e) {
                System.out.println(e);
            }


try{
        task=request.getParameter("task");
        if(task.equals("updateVehicleError")){
            request.setAttribute("error_log_id",request.getParameter("error_id"));
            request.setAttribute("error_causeby_id",request.getParameter("error_causeby_id"));
            request.setAttribute("date",request.getParameter("date"));
             request.getRequestDispatcher("updatevehicleerror").forward(request, response);



        }
        if(task.equals("updateVehicleRecord")){
           int error_log_id=Integer.parseInt(request.getParameter("error_id"));
           int error_causeby_id=Integer.parseInt(request.getParameter("error_causeby_id"));
           String date=request.getParameter("date");
           String vehicle_code=request.getParameter("vehicle_code");

              OnOffError.updateVehicleRecord(error_log_id,error_causeby_id,date,vehicle_code);

        }
        }catch(Exception e){
            System.out.println(e);
        }
         try{
             if (task.equals("generateHSReport"))
           {
              String jrxmlFilePath;
              List li=null;
              response.setContentType("application/pdf");
              ServletOutputStream servletOutputStream = response.getOutputStream();
              jrxmlFilePath = ctx.getRealPath("/report/complain/ErrorLog.jrxml");
              li=OnOffError.getAllRecords(-1,-1,error_person);
              byte[] reportInbytes =OnOffError.generateRecordList(jrxmlFilePath,li);
              response.setContentLength(reportInbytes.length);
              servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
              servletOutputStream.flush();
              servletOutputStream.close();

              return;
                 }
           }catch(Exception e){
               System.out.println(e);
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
        try {
            noOfRowsInTable = OnOffError.getTotalRowsInTable(error_person);                  // get the number of records (rows) in the table.

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

            List<ErrorLog> ohLevelList = OnOffError.getAllRecords(lowerLimit, noOfRowsToDisplay,error_person);
            lowerLimit = lowerLimit + ohLevelList.size();
            noOfRowsTraversed = ohLevelList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("ohLevelList", ohLevelList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            //   }
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", OnOffError.getMessage());
            request.setAttribute("msgBgColor", OnOffError.getMessageBGColor());
            request.getRequestDispatcher("errorlog").forward(request, response);
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
