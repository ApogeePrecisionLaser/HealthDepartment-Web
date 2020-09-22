/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.organization.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.model.RwaModel;
import com.healthDepartment.organization.tableClasses.Rwa;
import com.healthDepartment.util.UniqueIDGenerator;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Administrator
 */
public class RwaController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
        String task=request.getParameter("task");
          int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay =5, noOfRowsInTable = 0;
         if(task==null)
            task="";
        RwaModel rwa=new RwaModel();
          try{
            rwa.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));}
            catch(Exception e)
           {
           System.out.print(e);
             }
        try {
             String emp_code="",person_name="";
             String JQstring = request.getParameter("action1");
             String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getPersonName"))
                {
                   if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                     emp_code = request.getParameter("action2");
                
                    list = rwa.getPersonName(q,emp_code);
                }
                else if(JQstring.equals("getEmp_code"))
                {
                  if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                    person_name = request.getParameter("action2");
                  list = rwa.getEmp_code(q,person_name);
                }
                   Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }

                return;
            }
        }
        catch(Exception e)
         {
            System.out.println(e);
         }
            try{
               if(task.equals("Delete"))
                rwa.deleteRecord(request.getParameter("rwa_id"));
                int rwa_id=0;
                try
                {
          rwa_id = Integer.parseInt(request.getParameter("rwa_id").trim());
                    }
          catch(Exception e)
                {
               rwa_id=0;
                }
         if(task.equals("save")|| task.equals("Save AS New"))
              {
            if(task.equals("Save AS New"))
                   rwa_id = 0;
          String rwa_name=request.getParameter("rwa_name");
          String emp_code1=request.getParameter("emp_code1");
          String emp_code2=request.getParameter("emp_code2");
          String emp_code3=request.getParameter("emp_code3");
          String emp_code4=request.getParameter("emp_code4");
          String emp_code5=request.getParameter("emp_code5");
          String emp_code6=request.getParameter("emp_code6");
          String payment_schedule=request.getParameter("payment_schedule");
          String monthly_fee=request.getParameter("monthly_fee");
          String description=request.getParameter("description");
          Rwa rb=new Rwa();
          rb.setRwa_id(rwa_id);
          rb.setRwa_name(rwa_name);
          rb.setEmp_code1(Integer.parseInt(emp_code1));
          rb.setEmp_code2(Integer.parseInt(emp_code2));
          rb.setEmp_code3(Integer.parseInt(emp_code3));
          rb.setEmp_code4(Integer.parseInt(emp_code4));
          rb.setEmp_code5(Integer.parseInt(emp_code5));
          rb.setEmp_code6(Integer.parseInt(emp_code6));
          rb.setPayment_schedule(Integer.parseInt(payment_schedule));
          rb.setMonthly_fee(Integer.parseInt(monthly_fee));
          rb.setDescription(description);
          if(rwa_id > 0){
                    rwa.reviseRecord(rb);
                  }
          else{
                rwa.insertRecord(rb);
               }
                }
String buttonAction = request.getParameter("buttonAction");
             if(buttonAction == null)
                 buttonAction = "none";
              try {
        lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
        noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            }catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }

     noOfRowsInTable = rwa.getNoOfRows();

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
     else if (task.equals("Show All Records")) {

        }
         List<Rwa>  list=  rwa.showData(lowerLimit, noOfRowsToDisplay);
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
          request.setAttribute("payment_type", rwa.getPayment_type());
         request.setAttribute("lowerLimit",lowerLimit);
         request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", rwa.getMessage());
         request.getRequestDispatcher("rwa").forward(request, response);
        }
        catch(Exception e)
        {
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
