/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.general.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.general.model.GeneralModel;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jpss
 */
public class GeneralController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        ServletContext ctx = getServletContext();
       GeneralModel gm = new GeneralModel();
       //PrintWriter out = response.getWriter();


       try {
           gm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
       } catch (Exception e) {
           System.out.print(e);
       }
    
        try {
            String task = request.getParameter("task");
            if(task == null)
                task = "";

        if (task.equals("GetCordinates4"))
            {
                String longi = request.getParameter("longitude");
                String latti = request.getParameter("latitude");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("getCordinate4").forward(request, response);
                return;
            }
            if (task.equals("GetCordinates1"))
            {
                String longi = request.getParameter("longitude");
                String latti = request.getParameter("latitude");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("getCordinate1").forward(request, response);
                return;
            }
            if (task.equals("GetCordinates2"))
            {
                String longi = request.getParameter("longitude");
                String latti = request.getParameter("latitude");
                if(longi == null || longi.equals("undefined"))
                    longi = "0";
                if(latti == null || latti.equals("undefined"))
                    latti = "0";
                request.setAttribute("longi", longi);
                request.setAttribute("latti", latti);
                System.out.println(latti + "," + longi);
                request.getRequestDispatcher("getCordinate2").forward(request, response);
                return;
            }
            if(task.equals("GetDistance"))
            {
                int disance = 0;//MapDetailClass.getDistance(request.getParameter("source"), request.getParameter("destination"));
                PrintWriter out = response.getWriter();
                out.print(disance);
                out.close();
                return;
            }
            if (task.equals("GetBendCordinates"))
            {
                String headlongi = request.getParameter("head_longitude");
                String headlatti = request.getParameter("head_latitude");
                String taillongi = request.getParameter("tail_longitude");
                String taillatti = request.getParameter("tail_latitude");
                if(headlongi == null || headlongi.equals("undefined"))
                    headlongi = "0";
                if(headlatti == null || headlatti.equals("undefined"))
                    headlatti = "0";
                if(taillongi == null || taillongi.equals("undefined"))
                    taillongi = "0";
                if(taillatti == null || taillatti.equals("undefined"))
                    taillatti = "0";
                request.setAttribute("headlongi", headlongi);
                request.setAttribute("headlatti", headlatti);
                request.setAttribute("taillongi", taillongi);
                request.setAttribute("taillatti", taillatti);
                System.out.println(headlongi + "," + headlatti);
                System.out.println(taillongi + "," + taillatti);
                request.getRequestDispatcher("getCordinate3").forward(request, response);
                return;
            }
            if (task.equals("GetCordinates5"))
            {
                List list=gm.getNodeDetail();
                request.setAttribute("list", list);
                request.getRequestDispatcher("node_detail_map1").forward(request, response);
                return;
            }
            
              if (task.equals("GetVehicleCordinates"))
            {
                String latitude = "";
                String longitude="";
                List list=gm.getVehicleLatLong();
               
                request.setAttribute("size", list.size());
                request.setAttribute("CoordinatesList", list);
                request.getRequestDispatcher("/view/MapView/mapWindowAll.jsp").forward(request, response);
                return;
            }
            
            if(task.equals("checkSubPointExits"))
           {
               String node_name = request.getParameter("temp");
              String data= gm.getLatLang(node_name);

              PrintWriter out = response.getWriter();
              out.print(data);
               return;
           }
        } finally { 
           
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
