/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.controller;


import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shobha
 */
public class MISController extends HttpServlet {
   
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

         response.setContentType("text/html;charset=UTF-8");
         response.setCharacterEncoding("UTF-8");
         request.setCharacterEncoding("UTF-8");
         ServletContext ctx = getServletContext();
         String task=request.getParameter("task");
        String  date_from=request.getParameter("date_from");
             if(date_from==null)
                date_from="";
        String  date_to=request.getParameter("date_to");
             if(date_to==null)
                date_to="";
        String  select_type=request.getParameter("select_type");
             if(select_type==null)
                select_type="";
           if(task==null||task.isEmpty())
            {
                task="";
            }
        request.setAttribute("date_from", date_from);
         request.setAttribute("date_to",date_to);
      try{
          if(select_type.equals("attendance")){

               RequestDispatcher rd=request.getRequestDispatcher("ShiftAttendenceDetailMISController");
               rd.forward(request, response);
          }
          else if(select_type.equals("error"))
          {

               RequestDispatcher rd=request.getRequestDispatcher("vehicleKeyPerson");
               rd.forward(request, response);
          }
          else if(select_type.equals("shift"))
          {

               RequestDispatcher rd=request.getRequestDispatcher("ShiftMISController");
               rd.forward(request, response);
          }
          else if(select_type.equals("vehicle"))
          {

               RequestDispatcher rd=request.getRequestDispatcher("VehicleKeyPersonMISController");
               rd.forward(request, response);
          }



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
