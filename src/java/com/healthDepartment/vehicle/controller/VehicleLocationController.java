/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicle.model.VehicleModel;
import com.healthDepartment.vehicle.tableClasses.Vehicle;
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
public class VehicleLocationController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
        VehicleModel vm=new VehicleModel();
        String task=request.getParameter("task");
            if(task==null)
                task="";
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        try
        {

            vm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }
       String  vehicleSubType=request.getParameter("vehicleSubType");
             if(vehicleSubType==null)
                vehicleSubType="";
   String  vehicleNo=request.getParameter("vehicleNo");
             if(vehicleNo==null)
                vehicleNo="";
    String   mobileNo=request.getParameter("mobileNo");
             if(mobileNo==null)
                mobileNo="";
      if (task.equals("Show All Records")) {
               vehicleSubType="";
                    vehicleNo="";
                    mobileNo="";
            }

    if(task.equals("showMapWindow")){

       String vehicle_location_id = request.getParameter("vehicle_location_id");
       String latitude="";
       String longitude="";
                   String LatLong= vm.getPointLatLong( vehicle_location_id);
                   System.out.println(LatLong);
                    String[] words=LatLong.split("\\,");
                        for(int i=0;i< words.length;i++){
                        latitude= words[0];
                        longitude=words[1];
                         System.out.println(latitude+"  "+longitude);
                          }
                    request.setAttribute("longi", latitude);
                    request.setAttribute("latti", longitude);
                    //System.out.println(latti + "," + longi);
                    request.getRequestDispatcher("openMapWindowView").forward(request, response);
                    return;

  }

        try {


              String buttonAction = request.getParameter("buttonAction");
             if(buttonAction == null)
                 buttonAction = "none";

      try {
        lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
        noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }

     noOfRowsInTable = vm.getNoOfRowsVehicleLocation(vehicleSubType,vehicleNo,mobileNo);

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
 
         List<Vehicle>  list=  vm.showDataVehicleLocation(lowerLimit, noOfRowsToDisplay,vehicleSubType,vehicleNo,mobileNo);

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
         request.setAttribute("vehicleSubType", vehicleSubType);
         request.setAttribute("vehicleNo", vehicleNo);
         request.setAttribute("mobileNo", mobileNo);
         request.setAttribute("message", vm.getMessage());

               RequestDispatcher rd=request.getRequestDispatcher("vehicleLocation");
               rd.forward(request, response);
        }catch(Exception e){
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
