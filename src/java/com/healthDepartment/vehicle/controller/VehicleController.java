/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicle.model.VehicleModel;
import com.healthDepartment.vehicle.tableClasses.Vehicle;
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
public class VehicleController extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
        VehicleModel vm=new VehicleModel();
        String task=request.getParameter("task");
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
          String  vehicleSubType="",vehicleNo="", mobileNo="";
        try
        {

            vm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }
    vehicleSubType=request.getParameter("vehicleSubType");
             if(vehicleSubType==null)
                vehicleSubType="";
     vehicleNo=request.getParameter("vehicleNo");
             if(vehicleNo==null)
                vehicleNo="";
       mobileNo=request.getParameter("mobileNo");
             if(mobileNo==null)
                mobileNo="";
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
                if (JQstring.equals("getVehicleSubType"))
                {
                    list = vm.getVehicleSubType(q);
                }
               if (JQstring.equals("getVehicleNo"))
                {
                    list = vm.getVehicleNo(q);
                }
                  if (JQstring.equals("getMobileNo"))
                {
                    list = vm.getMobileNo(q);
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
                 vm.deleteRecord(request.getParameter("vehicle_id"));
        else if(task.equals("save") || task.equals("Save AS New"))
             {
                 int vehicle_id=0;
                 try{
                     vehicle_id = Integer.parseInt(request.getParameter("vehicle_id").trim());
                 }catch(Exception ex){
                     vehicle_id = 0;
                 }

                 if(task.equals("Save AS New"))
                     vehicle_id = 0;
   
             String vehicle_sub_type=request.getParameter("vehicle_sub_type");
             String vehicle_no=request.getParameter("vehicle_no");
             String vehicle_code=request.getParameter("vehicle_code");
             String permit_validity=request.getParameter("permit_validity");
             String fitness_validity=request.getParameter("fitness_validity");
             String puc_validity=request.getParameter("puc_validity");
             String mobile_no=request.getParameter("mobile_no");
             String imei_no=request.getParameter("imei_no");
             String remark=request.getParameter("remark");
             
             Vehicle v=new Vehicle();
             v.setVehicle_id(vehicle_id);
             v.setVehicle_sub_type(vehicle_sub_type);
             v.setVehicle_no(vehicle_no);
             v.setVehicle_code(Integer.parseInt(vehicle_code));
             v.setPermit_validity(permit_validity);
             v.setFitness_validity(fitness_validity);
             v.setPuc_validity(puc_validity);
             v.setMobile_no(mobile_no);
             v.setImei_no(imei_no);
             v.setRemark(remark);

            vm.insertRecord(v);
         }
           else if (task.equals("Show All Records")) {
               vehicleSubType="";
                    vehicleNo="";
                    mobileNo="";
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

     noOfRowsInTable = vm.getNoOfRows(vehicleSubType,vehicleNo,mobileNo);

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

         List<Vehicle>  list=  vm.showData(lowerLimit, noOfRowsToDisplay,vehicleSubType,vehicleNo,mobileNo);

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
         request.setAttribute("vehicleNo", vehicleNo);
         request.setAttribute("mobileNo", mobileNo);
         request.setAttribute("IDGenerator", new UniqueIDGenerator());
         request.setAttribute("message", vm.getMessage());

               RequestDispatcher rd=request.getRequestDispatcher("vehicle");
               rd.forward(request, response);
        }
        catch(Exception e){
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
