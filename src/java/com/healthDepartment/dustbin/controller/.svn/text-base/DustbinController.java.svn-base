/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.dustbin.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.dustbin.model.DustbinModel;
import com.healthDepartment.dustbin.tableClasses.Dustbin;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
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
public class DustbinController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
   response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
       DustbinModel dm=new DustbinModel();
        String task=request.getParameter("task");
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        try
        {

            dm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }
     String dustbinType=request.getParameter("dustbinType");
             if(dustbinType==null)
                dustbinType="";
     String cityLocation=request.getParameter("cityLocation");
             if(cityLocation==null)
                cityLocation="";
           if(task==null||task.isEmpty())
            {
                task="";
            }
          try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getDustbinType"))
                {
                    list = dm.getDustbinType(q);
                }
              if (JQstring.equals("getCityLocation"))
                {
                    list = dm.getCityLocation(q);
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


          if(task.equals("Delete"))
                 dm.deleteRecord(request.getParameter("dustbin_id"));
          else if(task.equals("save") || task.equals("Save AS New"))
             {
                 int dustbin_id=0;
                 try{
                     dustbin_id = Integer.parseInt(request.getParameter("dustbin_id").trim());
                 }catch(Exception ex){
                     dustbin_id = 0;
                 }

                 if(task.equals("Save AS New"))
                     dustbin_id = 0;

             String dustbin_type=request.getParameter("dustbin_type");
             String dustbin_no=request.getParameter("dustbin_no");
             String latitude=request.getParameter("latitude");
             String longitude=request.getParameter("longitude");
             String city_location=request.getParameter("city_location");
             String remark=request.getParameter("remark");

             Dustbin d=new Dustbin();
             d.setDustbin_id(dustbin_id);
             d.setDustbin_type(dustbin_type);
             d.setDustbin_no(Integer.parseInt(dustbin_no));
             d.setLatitude(Double.parseDouble(latitude));
             d.setLongitude(Double.parseDouble(longitude));
             d.setCity_location(city_location);
             d.setRemark(remark);

            dm.insertRecord(d);
         }
               else if (task.equals("Show All Records")) {
               dustbinType="";
               cityLocation="";
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

     noOfRowsInTable = dm.getNoOfRows(dustbinType,cityLocation);

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

         List<Dustbin>  list=  dm.showData(lowerLimit, noOfRowsToDisplay,dustbinType,cityLocation);

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
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", dm.getMessage());
        request.setAttribute("dustbinType",dustbinType);
        request.setAttribute("cityLocation",cityLocation);
              RequestDispatcher rd=request.getRequestDispatcher("dustbin");
              rd.forward(request, response);
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
