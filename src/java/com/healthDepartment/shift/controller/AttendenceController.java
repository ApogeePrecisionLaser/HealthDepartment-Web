/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.shift.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.shift.model.AttendenceModel;
import com.healthDepartment.shift.tableClasses.ShiftLoginBean;
import com.healthDepartment.util.UniqueIDGenerator;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class AttendenceController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       response.setContentType("text/html;charset=UTF-8");
          response.setCharacterEncoding("UTF-8");
          request.setCharacterEncoding("UTF-8");
        String task=request.getParameter("task");
        if(task==null)
            task="";
         ServletContext ctx = getServletContext();
          AttendenceModel am=new AttendenceModel();
         int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay=100, noOfRowsInTable= 0;
        try
        {
            am.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }
        try {
            String  date=request.getParameter("date");
         
             String buttonAction = request.getParameter("buttonAction");
             if(buttonAction == null)
                 buttonAction = "none";
     if(task.equals("Save"))
     {
       String[] attendence_id=request.getParameterValues("attendence_id");
       String[] attendence=request.getParameterValues("attendence");
        ShiftLoginBean sb=new ShiftLoginBean();
        sb.setA_attendence_id(attendence_id);
        sb.setA_attendence(attendence);
        am.updateRecord(sb);
    }
      if(task.equals("Save"))
          date="";
              try {
        lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
        noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
      
               noOfRowsInTable = am.getNoOfRows(date);

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
       if (task.equals("save") || task.equals("Save AS New") || task.equals("Revised") || task.equals("Delete") ) {
            lowerLimit = lowerLimit - noOfRowsTraversed;
        }
         List<ShiftLoginBean>  list=  am.showData(date);

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
         request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
         request.setAttribute("date",date);
         request.setAttribute("message", am.getMessage());
         request.setAttribute("noOfRowsInTable",noOfRowsInTable);
         request.setAttribute("IDGenerator", new UniqueIDGenerator());
            RequestDispatcher rd=request.getRequestDispatcher("attendence");
            rd.forward(request, response);
        }
        catch(Exception e)
        {
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
