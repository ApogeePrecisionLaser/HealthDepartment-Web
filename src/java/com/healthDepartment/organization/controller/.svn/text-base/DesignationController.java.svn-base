/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;

import com.healthDepartment.organization.model.DesignationModel;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.tableClasses.Designation;
import com.healthDepartment.util.UniqueIDGenerator;
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
import javax.servlet.http.HttpSession;

/**
 *
 * @author JPSS
 */
public class DesignationController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 20, noOfRowsInTable;
        ServletContext ctx = getServletContext();

  /*      HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("beforelogin.jsp");
            return;
        }  
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        DesignationModel designationModel = new DesignationModel();
         try{
           //   designationModel.setConnection(DBConnection.getConnection(ctx, session));
              designationModel.setConnection(DBConnection.getConnectionForUtf(ctx));
            }catch (Exception e) {
                System.out.println("error in DesignationController setConnection() calling try block"+e);
            }
        String message = null;
        String bgColor = null;
        String task = request.getParameter("task");
        String designation = null;
            if(task==null)
            {
                task="";
            }

            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input

                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getDesignationList")) {
                        list = designationModel.getDesignationList(q);
                    }else if (JQstring.equals("getSearchDesignationCode")) {
                        list = designationModel.getDesignationCode(q);
                    }
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        if (data == null) {
                            out.print("");
                        } else {
                            out.println(data);
                        }
                    }
                    designationModel.closeConnection();
                    return;
                }
            
         
            String   searchDesignation= request.getParameter("searchDesignation");
              String  searchDesignationCode=request.getParameter("searchDesignationCode");
           try{
                          
                if (searchDesignation == null) {
                    searchDesignation = "";
                }
                 if ( searchDesignationCode == null) {
                     searchDesignationCode = "";
                }
            } catch (Exception e) 
            {
                System.out.println("Throwing Nullpointer Exception!!!");
            }
            try {
                designation = request.getParameter("designation").trim();
            } catch (Exception e) {
                designation = "";
            }
            if(task.equals("generateDesignationReport"))//start from here
             {
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
          List  listAll=designationModel.showAllData(searchDesignation,  searchDesignationCode);
            jrxmlFilePath = ctx.getRealPath("/report/organization/DesignationReport.jrxml");
            byte[] reportInbytes = designationModel.generateDesignationReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
         }else if(task.equals("generateDesignationXlsReport"))
         {
             String jrxmlFilePath;            
                       response.setContentType("application/vnd.ms-excel");
                       response.addHeader("Content-Disposition", "attachment; filename=Designation.xls");
                       ServletOutputStream servletOutputStream = response.getOutputStream();
                       jrxmlFilePath = ctx.getRealPath("/report/organization/DesignationReport.jrxml");
                    List  listAll=designationModel.showAllData(searchDesignation,  searchDesignationCode);
                       ByteArrayOutputStream reportInbytes =designationModel.generateDesignationXlsRecordList(jrxmlFilePath, listAll);
                       response.setContentLength(reportInbytes.size());
                       servletOutputStream.write(reportInbytes.toByteArray());
                       servletOutputStream.flush();
                       servletOutputStream.close();
                       return;
         }

         
            if (designation.equals("No Designation")) {
                message = "You Could Not Delete or update this record. It is required for Project Operation...";
                bgColor = "red";
            } else {
                if (task == null) {
                    task = "";
                }
                if (task.equals("Delete")) {
                    designationModel.deleteRecord(Integer.parseInt(request.getParameter("designation_id")));  // Pretty sure that media_id will be available.
                } else if (task.equals("Save") || task.equals("Save AS New")) {
                    int designation_id;
                    try {
                        designation_id = Integer.parseInt(request.getParameter("designation_id"));            // media_id may or may NOT be available i.e. it can be update or new record.
                    } catch (Exception e) {
                        designation_id = 0;
                    }
                    if (task.equals("Save AS New")) {
                        designation_id = 0;
                    }
                    Designation media = new Designation();


                    media.setDesignation_id(designation_id);
                    media.setDesignation_code(request.getParameter("designation_code").trim());
                    media.setDesignation(request.getParameter("designation").trim());
                    media.setDescription(request.getParameter("description").trim());
                    if (designation_id == 0) {
                        // if media_id was not provided, that means insert new record.
                        designationModel.insertRecord(media);
                    } else {
                        // update existing record.
                        designationModel.updateRecord(media);
                    }
                }
                message = designationModel.getMessage();
                bgColor = designationModel.getMsgBgColor();
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

            // get the number of records (rows) in the table.
            if (task.equals("Search")) {
                lowerLimit = noOfRowsTraversed = 0;
            }else if (task.equals("Show All Records")) {
                searchDesignation = "";
                searchDesignationCode = "";
            }
            noOfRowsInTable = designationModel.getNoOfRows(searchDesignation,searchDesignationCode);
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

                // Logic to show data in the table.

            } 
            List<Designation> mediaList = designationModel.showData(lowerLimit, noOfRowsToDisplay, searchDesignation,searchDesignationCode);
            lowerLimit = lowerLimit + mediaList.size();
            noOfRowsTraversed = mediaList.size();

            // Now set request scoped attributes, and then forward the request to view.
            // Following request scoped attributes NAME will remain constant from module to module.
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", message);
            request.setAttribute("msgBgColor", bgColor);

            // Following request scoped attributes NAME will change from module to module.
            request.setAttribute("mediaList", mediaList);
            request.setAttribute("searchDesignation", searchDesignation);
              request.setAttribute("searchDesignationCode", searchDesignationCode);
            designationModel.closeConnection();
            request.getRequestDispatcher("designation_view").forward(request, response);
        } catch (Exception ex) {
            System.out.println("DesignationController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
