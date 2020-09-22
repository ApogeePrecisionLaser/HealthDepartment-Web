/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicle.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicle.model.VehicleWeightModel;
import com.healthDepartment.vehicle.tableClasses.VehicleWeight;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shobha
 */
public class VehicleWeightController extends HttpServlet {
   
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
        //PrintWriter out = response.getWriter();

         response.setContentType("text/html;charset=UTF-8");
         response.setCharacterEncoding("UTF-8");
         request.setCharacterEncoding("UTF-8");
         ServletContext ctx = getServletContext();
         VehicleWeightModel vkpm=new VehicleWeightModel();
         String task=request.getParameter("task");
          int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        try
        {

            vkpm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
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

                 if (JQstring.equals("getSearch_vehicle_code"))
                {

                    list = vkpm.getSearchVehicleCode(q);
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
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;

                 if (JQstring.equals("getSearch_vehicle_number"))
                {

                    list = vkpm.getSearchVehicleNumber(q);
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



  String  search_vehicle_code=request.getParameter("search_vehicle_code");
  String  search_vehicle_number=request.getParameter("search_vehicle_number");
             if(search_vehicle_code==null)
                search_vehicle_code="";
              if(search_vehicle_number==null)
                search_vehicle_number="";

           if(task==null||task.isEmpty())
            {
                task="";
            }
      try{
          if (task.equals("viewImage")) {
                try {
                    // String jrxmlFilePath;
                    // String img_name = "PRHindi_" ;
                    //String repository_name = "C:/ssadvt_repository/SOR";
                    //int estimate_no = Integer.parseInt(request.getParameter("estimate_no"));
                    //int estimate_revision_no = Integer.parseInt(request.getParameter("estimate_revision_no"));
                    String destinationPath = "";
                  //  String kp_id = request.getParameter("filingdata_id");
                    int idVal = Integer.parseInt(request.getParameter("idVal").trim());
                    destinationPath=vkpm.getGeneral_image_detail_id(idVal);

//                    String type = request.getParameter("type");
//                    if (kp_id != null && !kp_id.isEmpty()) {
//                        destinationPath = vtm.getGeneral_image_detail_id(Integer.parseInt(kp_id), type);
//                        if (destinationPath.isEmpty()) {
//                            destinationPath = "C:\\ssadvt_repository\\prepaid\\general\\no_image.png";
//                        }
//                    } else {
//                        System.out.println("Image Not Found");
//                        destinationPath = "C:\\ssadvt_repository\\prepaid\\general\\no_image.png";
//                    }
                    //destinationPath = keyModel.getImagePath(emp_code);
                    File f = new File(destinationPath);
                    FileInputStream fis = null;
                    if (!f.exists()) {
                        destinationPath = "C:\\ssadvt_repository\\prepaid\\general\\no_image.png";
                        f = new File(destinationPath);
                    }
                    fis = new FileInputStream(f);
                    if (destinationPath.contains("pdf")) {
                        response.setContentType("pdf");
                    } else {
                        response.setContentType("image/jpeg");
                    }

                    //  response.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                    // BufferedImage bi=ImageIO.read(f);
                    response.setContentLength(fis.available());
                    ServletOutputStream os = response.getOutputStream();
                    BufferedOutputStream out = new BufferedOutputStream(os);
                    int ch = 0;
                    ;
                    while ((ch = bis.read()) != -1) {
                        out.write(ch);
                    }

                    bis.close();
                    fis.close();
                    out.close();
                    os.close();
                    response.flushBuffer();

                  // vkpm.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("SelectSupplierController Demand Note Error :" + e);
                    return;
                }
            }



       
                 if (task.equals("Show All Records")) {
                    search_vehicle_code="";
                    search_vehicle_number="";
                    
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

     noOfRowsInTable = vkpm.getNoOfRows(search_vehicle_code,search_vehicle_number);

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

         List<VehicleWeight>  list=  vkpm.showData(lowerLimit, noOfRowsToDisplay,search_vehicle_code,search_vehicle_number);

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
         request.setAttribute("search_vehicle_code", search_vehicle_code);
         request.setAttribute("search_vehicle_number", search_vehicle_number);
        // request.setAttribute("search_taken_by", search_taken_by);
         request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", vkpm.getMessage());

             RequestDispatcher rd=request.getRequestDispatcher("vehicleWeight");
               rd.forward(request, response);
        } finally {
            ///out.close();
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
