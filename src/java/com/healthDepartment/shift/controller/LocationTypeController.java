/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.shift.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.shift.model.LocationTypeModel;
import com.healthDepartment.shift.tableClasses.LocationTypeBean;
import com.healthDepartment.util.UniqueIDGenerator;
import java.sql.PreparedStatement;
import java.sql.Connection;
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
 * @author Administrator
 */
public class LocationTypeController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
      response.setContentType("text/html;charset=UTF-8");
       response.setCharacterEncoding("UTF-8");
       request.setCharacterEncoding("UTF-8");

        ServletContext ctx = getServletContext();
        LocationTypeModel lm=new LocationTypeModel();
        String searchlocationtype=request.getParameter("searchlocationtype");
             if(searchlocationtype==null)
                searchlocationtype="";
        String task=request.getParameter("task");
         if(task==null||task.isEmpty())
           task="";
         int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 5, noOfRowsInTable = 0;
         try{
            lm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));}
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
                if (JQstring.equals("getlocationtype"))
                {
                   list = lm.getlocationtype(q);
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
  if (task.equals("generateHSReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        response.setCharacterEncoding("UTF-8");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/Report/organization/typeOfOccupation.jrxml");
                        list=lm.showAll(-1,noOfRowsToDisplay,searchlocationtype);
                        byte[] reportInbytes =lm.generateRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        return;
                     }
           if (task.equals("generateReport"))
{
                     String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment; filename=Beneficiary.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/Report/organization/typeOfOccupation.jrxml");
                         list=lm.showAll(-1,noOfRowsToDisplay,searchlocationtype);
                        ByteArrayOutputStream reportInbytes =lm.generateXlsRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.toByteArray().length);

                       servletOutputStream.write(reportInbytes.toByteArray() , 0, reportInbytes.toByteArray().length);
                       servletOutputStream.flush();
                       servletOutputStream.close();

                        return;
}
        try {
             if(task.equals("Delete"))
               lm.deleteRecord(request.getParameter("location_type_id"));
            else if(task.equals("save") || task.equals("Save AS New"))
             {
                 int location_type_id=0;
                 try{
                     location_type_id = Integer.parseInt(request.getParameter("location_type_id").trim());
                 }catch(Exception ex){
                     location_type_id = 0;
                 }

            if(task.equals("Save AS New"))
           location_type_id = 0;
          String location_type_name=request.getParameter("location_type_name");
          String remark=request.getParameter("remark");
          LocationTypeBean lt =new LocationTypeBean();
          lt.setLocation_type_id(location_type_id);
          lt.setLocation_type_name(location_type_name);
          lt.setRemark(remark);
            lm.insertRecord(lt);
         }
             else if (task.equals("Show All Records")) {
               searchlocationtype="";
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

    noOfRowsInTable = lm.getNoOfRows(searchlocationtype);

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

         List<LocationTypeBean>  list=  lm.showData(lowerLimit, noOfRowsToDisplay,searchlocationtype);

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
        request.setAttribute("searchlocationtype",searchlocationtype);
          request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", lm.getMessage());

             request.getRequestDispatcher("/locationtype").forward(request, response);
        }
        catch(Exception e)
        {

        }
    } 

   
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
