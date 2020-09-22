/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicle.model.VehicleSubTypeModel;
import com.healthDepartment.vehicle.tableClasses.VehicleSubType;
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
public class VehicleSubTypeController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         response.setContentType("text/html;charset=UTF-8");
          response.setCharacterEncoding("UTF-8");
          request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
        VehicleSubTypeModel vstm=new VehicleSubTypeModel();
        String task=request.getParameter("task");
          int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        try
        {

            vstm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }
   String  vehicleSubType="",vehicleType="";
    vehicleSubType=request.getParameter("vehicleSubType");
             if(vehicleSubType==null)
                vehicleSubType="";
    vehicleType=request.getParameter("vehicleType");
             if(vehicleType==null)
                vehicleType="";
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
                if (JQstring.equals("getVehicleType"))
                {
                    list = vstm.getVehicleType(q);
                }
             if (JQstring.equals("getVehicleSubType"))
                {
                    list = vstm.getVehicleSubType(q);
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
                 vstm.deleteRecord(request.getParameter("vehicle_sub_type_id"));
        else if(task.equals("save") || task.equals("Save AS New"))
             {
                 int vehicle_sub_type_id=0;
                 try{
                     vehicle_sub_type_id = Integer.parseInt(request.getParameter("vehicle_sub_type_id").trim());
                 }catch(Exception ex){
                     vehicle_sub_type_id = 0;
                 }

                 if(task.equals("Save AS New"))
                     vehicle_sub_type_id = 0;
             String vehicle_type=request.getParameter("vehicle_type");
             String vehicle_sub_type=request.getParameter("vehicle_sub_type");
             String remark=request.getParameter("remark");
             VehicleSubType vst=new VehicleSubType();
             vst.setVehicle_sub_type_id(vehicle_sub_type_id);
             vst.setVehicle_type(vehicle_type);
             vst.setVehicle_sub_type(vehicle_sub_type);
             vst.setRemark(remark);

            vstm.insertRecord(vst);
         }
      else if (task.equals("Show All Records")) {
               vehicleSubType="";
                    vehicleType="";

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

     noOfRowsInTable = vstm.getNoOfRows(vehicleType,vehicleSubType);

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

         List<VehicleSubType>  list=  vstm.showData(lowerLimit, noOfRowsToDisplay,vehicleType,vehicleSubType);

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
           request.setAttribute("vehicleSubType", vehicleSubType);
            request.setAttribute("vehicleType", vehicleType);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", vstm.getMessage());

               RequestDispatcher rd=request.getRequestDispatcher("vehicleSubType");
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
