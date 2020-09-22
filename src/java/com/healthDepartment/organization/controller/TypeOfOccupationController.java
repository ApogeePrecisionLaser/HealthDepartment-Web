/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.organization.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.model.TypeOfOccupationModel;
import com.healthDepartment.organization.tableClasses.TypeOfOccupation;
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
public class TypeOfOccupationController extends HttpServlet {
   
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
       response.setCharacterEncoding("UTF-8");
       request.setCharacterEncoding("UTF-8");

        ServletContext ctx = getServletContext();
        TypeOfOccupationModel tm=new TypeOfOccupationModel();
        String searchtypeofoccupation=request.getParameter("searchtypeofoccupation");
             if(searchtypeofoccupation==null)
                searchtypeofoccupation="";
        String task=request.getParameter("task");
         if(task==null||task.isEmpty())
           task="";
         int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 5, noOfRowsInTable = 0;
         try{
            tm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));}
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
                if (JQstring.equals("getSearchOccupation"))
                {
                    list = tm.getSearchOccupation(q);
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
                        list=tm.showAll(-1,noOfRowsToDisplay,searchtypeofoccupation);
                        byte[] reportInbytes =tm.generateRecordList(jrxmlFilePath,list);
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
                         list=tm.showAll(-1,noOfRowsToDisplay,searchtypeofoccupation);
                        ByteArrayOutputStream reportInbytes =tm.generateXlsRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.toByteArray().length);

                       servletOutputStream.write(reportInbytes.toByteArray() , 0, reportInbytes.toByteArray().length);
                       servletOutputStream.flush();
                       servletOutputStream.close();

                        return;
}
        try {
             if(task.equals("Delete"))
                tm.deleteRecord(request.getParameter("type_of_occupation_id"));
             else if(task.equals("save") || task.equals("Save AS New"))
             {
                 int type_of_occupation_id=0;
                 try{
                     type_of_occupation_id = Integer.parseInt(request.getParameter("type_of_occupation_id").trim());
                 }catch(Exception ex){
                     type_of_occupation_id = 0;
                 }

            if(task.equals("Save AS New"))
           type_of_occupation_id = 0;
          String name=request.getParameter("name");
          String description=request.getParameter("description");
          TypeOfOccupation to =new TypeOfOccupation();
          to.setType_of_occupation_id(type_of_occupation_id);
          to.setName(name);
          to.setDescription(description);
            tm.insertRecord(to);
         }
             else if (task.equals("Show All Records")) {
               searchtypeofoccupation="";
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

     noOfRowsInTable = tm.getNoOfRows(searchtypeofoccupation);

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

         List<TypeOfOccupation>  list=  tm.showData(lowerLimit, noOfRowsToDisplay,searchtypeofoccupation);

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
        request.setAttribute("searchtypeofoccupation",searchtypeofoccupation);
          request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", tm.getMessage());
      request.getRequestDispatcher("typeofoccupation").forward(request, response);
        }
        catch(Exception e)
        {

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
