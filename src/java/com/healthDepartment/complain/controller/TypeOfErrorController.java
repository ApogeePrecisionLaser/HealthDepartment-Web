/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.complain.controller;

import com.healthDepartment.complain.model.TypeOfErrorModel;
import com.healthDepartment.complain.tableClasses.TypeOfError;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shobha
 */
public class TypeOfErrorController extends HttpServlet {
   
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
       // PrintWriter out = response.getWriter();
          ServletContext ctx = getServletContext();
          int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 5, noOfRowsInTable = 0;

          TypeOfErrorModel typeOfErrorModel = new TypeOfErrorModel();
          HttpSession session = request.getSession(false);
        if (session == null) {
            request.getRequestDispatcher("/beforeLoginHomeView").forward(request, response);
            return;
        }

        try{


             try {
            TypeOfErrorModel.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print(e);
        }
             String searchPriority=request.getParameter("priority");
            String searchDesignation=request.getParameter("designation");
            String searchTypeOfError = request.getParameter("search_type_of_error");
           // String searchDesignation = request.getParameter("search_designation");
              if(searchPriority == null){
                  searchPriority="";
              }
            if(searchDesignation == null){
                  searchDesignation="";

              }
            if(searchTypeOfError == null){
                  searchTypeOfError="";

              }



            try {
               // PrintWriter pw = response.getWriter();
                String JQString = request.getParameter("action1");
                String q = request.getParameter("q");

                if (JQString != null) {
                     PrintWriter pw = response.getWriter();
                    List<String> list = null;
                    if (JQString.equals("getPriority")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getPriority() method
                        list = typeOfErrorModel.getPriority(q);
                    }
                    if (JQString.equals("getDesignation")) {
                        String action2 = request.getParameter("action2");
                        if (action2 == null) {
                            action2 = "";
                        }
                        //call getDesignation() method
                        list = typeOfErrorModel.getDesignation(q);
                    }

                     if (JQString.equals("getTypeOfErrorName")) {
                        String action1 = request.getParameter("action1");
                        if (action1 == null) {
                            action1 = "";
                        }
                        //call getDesignation() method
                        list = typeOfErrorModel.getTypeOfError(q);
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

             String task=request.getParameter("task");
            if(task == null){
                task="";
            }
             if (task.equals("Show All Records")) {
            searchTypeOfError = "";
            searchDesignation="";
            }

             if(task.equals("Save") ||task.equals("Update") || task.equals("Delete")){

                 String errorType = request.getParameter("type_of_error");
                // String errorTime = request.getParameter("error_time");
                 //String designation = request.getParameter("designation");
                 String priority = request.getParameter("priority");
                 String remark = request.getParameter("remark");
                 String errorType_id = request.getParameter("type_of_error_id");

                 if(errorType_id == null){
                     errorType_id="0";
                 }

                 int priority_id=typeOfErrorModel.getPriorityId(priority);
                 //int designation_id=typeOfErrorModel.getDesignationId(designation);
                 TypeOfError typeOfError  = new TypeOfError();
                 typeOfError.setError_name(errorType);
                 //typeOfError.setValue(errorTime);
                 //typeOfError.setDesignation_id(designation_id);
                 typeOfError.setPriority_id(priority_id);
                 typeOfError.setRemark(remark);
                 typeOfError.setErrorType_id(errorType_id);


                 if(task.equals("Save")){
                     if(errorType_id=="0"){
                     typeOfErrorModel.save(typeOfError);
                     }
                   else{
                       errorType_id="0";
                        typeOfError.setErrorType_id(errorType_id);
                         typeOfErrorModel.save(typeOfError);
                         }

                 }
                 if(task.equals("Update")){
                  typeOfErrorModel.update(typeOfError);
                 }
                 if(task.equals("Delete")){
                     typeOfErrorModel.delete(typeOfError);
                 }
             }

             try{
             if (task.equals("generateHSReport"))
           {
              String jrxmlFilePath;
              List li=null;
              response.setContentType("application/pdf");
              ServletOutputStream servletOutputStream = response.getOutputStream();
              jrxmlFilePath = ctx.getRealPath("/report/complain/TypeOfError.jrxml");
              li=typeOfErrorModel.showData(-1,-1,searchTypeOfError,searchDesignation);
              byte[] reportInbytes =typeOfErrorModel.generateRecordList(jrxmlFilePath,li);
              response.setContentLength(reportInbytes.length);
              servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
              servletOutputStream.flush();
              servletOutputStream.close();

              return;
                 }
           }catch(Exception e){
               System.out.println(e);
           }

              String buttonAction = request.getParameter("buttonAction");
            if (buttonAction == null) {
                buttonAction = "none";
            }
             try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }

           noOfRowsInTable = typeOfErrorModel.getNoOfRows(searchTypeOfError,searchDesignation);//call getNoOfRows() method

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

           if (task.equals("Save") || task.equals("Update") || task.equals("Delete")) {
               lowerLimit = lowerLimit - noOfRowsTraversed;
           }
            //List list = cdm.showData(lowerLimit, noOfRowsToDisplay);
            List list = typeOfErrorModel.showData(lowerLimit,noOfRowsToDisplay,searchTypeOfError,searchDesignation);
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

           // List list = otkpm.showData();

             request.setAttribute("List",list);
             request.setAttribute("lowerLimit", lowerLimit);
             request.setAttribute("message", TypeOfErrorModel.getMessage());
             request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
             request.setAttribute("IDGenerator", new UniqueIDGenerator());


             request.getRequestDispatcher("/typeoferror").forward(request, response);

        }finally{

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
