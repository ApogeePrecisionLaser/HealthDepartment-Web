/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.dustbin.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.dustbin.model.DustbinTypeModel;
import com.healthDepartment.dustbin.tableClasses.DustbinType;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.IOException;
import java.sql.Connection;
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
public class DustbinTypeController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
          response.setContentType("text/html;charset=UTF-8");
          response.setCharacterEncoding("UTF-8");
          request.setCharacterEncoding("UTF-8");
          ServletContext ctx = getServletContext();
          DustbinTypeModel dtm=new DustbinTypeModel();
        String task=request.getParameter("task");
          int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        try
        {

            dtm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }

           if(task==null||task.isEmpty())
            {
                task="";
            }
          String dustbinType=request.getParameter("dustbinType");
             if(dustbinType==null)
                dustbinType="";
        try {
              if(task.equals("Delete"))
                 dtm.deleteRecord(request.getParameter("dustbin_type_id"));
        else if(task.equals("save") || task.equals("Save AS New"))
             {
                 int dustbin_type_id=0;
                 try{
                     dustbin_type_id = Integer.parseInt(request.getParameter("dustbin_type_id").trim());
                 }catch(Exception ex){
                     dustbin_type_id = 0;
                 }

                 if(task.equals("Save AS New"))
                     dustbin_type_id = 0;
             String dustbin_type=request.getParameter("dustbin_type");
             String height=request.getParameter("height");
             String volume=request.getParameter("volume");
             String remark=request.getParameter("remark");
             DustbinType vt=new DustbinType();
             vt.setDustbin_type_id(dustbin_type_id);
             vt.setDustbin_type(dustbin_type);
             vt.setHeight(Double.parseDouble(height));
             vt.setVolume(Double.parseDouble(volume));
             vt.setRemark(remark);

            dtm.insertRecord(vt);
         }
                 else if (task.equals("Show All Records")) {
               dustbinType="";
              
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

     noOfRowsInTable = dtm.getNoOfRows(dustbinType);

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

         List<DustbinType>  list=  dtm.showData(lowerLimit, noOfRowsToDisplay,dustbinType);

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
         request.setAttribute("dustbinType",dustbinType);
        request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", dtm.getMessage());
              RequestDispatcher rd=request.getRequestDispatcher("dustbinType");
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
