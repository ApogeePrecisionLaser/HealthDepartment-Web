/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.vehicle.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicle.model.PointModel;
import com.healthDepartment.vehicle.tableClasses.Point;
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
 * @author Com7_2
 */
public class PointController extends HttpServlet {

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

        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
        PointModel vtm = new PointModel();
        String task = request.getParameter("task");
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        try {

            vtm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print(e);
        }
        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            String zone="";
            String ward="";
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getcity_name")) {
                       String areaname = request.getParameter("action2");
                          String wardname = request.getParameter("action3");
                          String zonename = request.getParameter("action4");
                         
                        
                    list = vtm.getCityName(areaname,wardname,zonename);
                }if (JQstring.equals("getZone")) {
                    list =vtm.getZone(q);
                }
                else if (JQstring.equals("getWardName")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        zone = request.getParameter("action2");
                    }
                    list = vtm.getWardName(q, zone);
                } else if (JQstring.equals("getAreaName")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        ward = request.getParameter("action2");
                    }
                    if (request.getParameter("action3") != null && !request.getParameter("action3").isEmpty()) {
                        zone= request.getParameter("action3");
                    }

                    list =vtm.getAreaName(q, ward, zone );
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
                if (JQstring.equals("getpoint_name")) {
                    String citylocation = request.getParameter("action2");
                    list = vtm.getPointName(q,citylocation);
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
                if (JQstring.equals("getsearch_city_name")) {
                    list = vtm.getSearchCityName(q);
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


        String search_city_location = request.getParameter("searchCityName");
        if (search_city_location == null) {
            search_city_location = "";
        }
        String searchzone = request.getParameter("searchZone");
        if (searchzone == null) {
            searchzone = "";
        }
        String searchward = request.getParameter("searchWardType");
        if (searchward == null) {
            searchward = "";
        }
        String searcharea = request.getParameter("searchArea");
        if (searcharea == null) {
            searcharea = "";
        }
       
        String search_point_name = request.getParameter("search_point");
        if (search_point_name == null) {
            search_point_name = "";
        }
        if (task == null || task.isEmpty()) {
            task = "";
        }
        if (task.equals("showMapWindow")) {

            String point_id = request.getParameter("point_id");
            String latitude = "";
            String longitude = "";
            String LatLong = vtm.getPointLatLong(point_id);
            System.out.println(LatLong);
            String[] words = LatLong.split("\\,");
            for (int i = 0; i < words.length; i++) {
                latitude = words[0];
                longitude = words[1];
                System.out.println(latitude + "  " + longitude);
            }
            request.setAttribute("longi", latitude);
            request.setAttribute("latti", longitude);
            //System.out.println(latti + "," + longi);
            request.getRequestDispatcher("openMapWindowView").forward(request, response);
            return;

        }
        try {
            if (task.equals("Delete")) {
                vtm.deleteRecord(request.getParameter("point_id"));
            } else if (task.equals("save") || task.equals("Save AS New")) {
                int point_id = 0;
                try {
                    point_id = Integer.parseInt(request.getParameter("point_id").trim());
                } catch (Exception ex) {
                    point_id = 0;
                }

                if (task.equals("Save AS New")) {
                    point_id = 0;
                }
                String zonename = request.getParameter("zone");
                String wardname = request.getParameter("ward");
                 String areaname = request.getParameter("area");
                String city_location = request.getParameter("city_location");
                
                 request.setAttribute("zone", zonename);
                 request.setAttribute("areaname", areaname);
            request.setAttribute("ward", wardname);
            request.setAttribute("city_location", city_location);
                
                String point_name = request.getParameter("point_name");
                String latitude = request.getParameter("latitude");
                String longitude = request.getParameter("longitude");
                Point vt = new Point();
                vt.setPoint_id(point_id);
                vt.setCity_location(city_location);
                vt.setPoint_name(point_name);
                vt.setLatitude(latitude);
                vt.setLongitude(longitude);
                vtm.insertRecord(vt);
            } else if (task.equals("Show All Records")) {
                searchzone = "";
                searchward = "";
                  searcharea = "";
              
                  search_city_location = "";
                search_point_name = "";
            }
            String buttonAction = request.getParameter("buttonAction");
            if (buttonAction == null) {
                buttonAction = "none";
            }

            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }

                 noOfRowsInTable = vtm.getNoOfRows(search_city_location, search_point_name,searchzone,searchward,searcharea);

            if (buttonAction.equals("Next"))
            {
            searchzone = request.getParameter("Zname");
              searchward = request.getParameter("Wname");
            searcharea = request.getParameter("Aname");
          
            search_city_location = request.getParameter("manname");
              search_point_name = request.getParameter("pname");
              
                   noOfRowsInTable = vtm.getNoOfRows(search_city_location, search_point_name,searchzone,searchward,searcharea);
        } else if (buttonAction.equals("Previous")) {
              search_city_location = request.getParameter("manname");
              search_point_name = request.getParameter("pname");
                 searchzone = request.getParameter("Zname");
              searchward = request.getParameter("Wname");
            searcharea = request.getParameter("Aname");
          
                   noOfRowsInTable = vtm.getNoOfRows(search_city_location, search_point_name,searchzone,searchward,searcharea);
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First")) {
                  search_city_location = request.getParameter("manname");
              search_point_name = request.getParameter("pname");
                 searchzone = request.getParameter("Zname");
              searchward = request.getParameter("Wname");
            searcharea = request.getParameter("Aname");
          
        //    noOfRowsInTable = vtm.getNoOfRows(search_city_location, search_point_name);
                lowerLimit = 0;
            } else if (buttonAction.equals("Last")) {
                  search_city_location = request.getParameter("manname");
              search_point_name = request.getParameter("pname");
                 searchzone = request.getParameter("Zname");
              searchward = request.getParameter("Wname");
            searcharea = request.getParameter("Aname");
          
            noOfRowsInTable = vtm.getNoOfRows(search_city_location, search_point_name,searchzone,searchward,searcharea);
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }
            if (task.equals("save") || task.equals("Save AS New") || task.equals("Delete")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;
            }

            List<Point> list = vtm.showData(lowerLimit, noOfRowsToDisplay, search_city_location, search_point_name,searchzone,searchward,searcharea);

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
            request.setAttribute("manname", search_city_location);
          request.setAttribute("pname", search_point_name);   
            request.setAttribute("Zname", searchzone);
          request.setAttribute("Wname", searchward);   
            request.setAttribute("Aname", searcharea);
        request.setAttribute("searchzone", searchzone);
          request.setAttribute("searchward", searchward);   
            request.setAttribute("searcharea", searcharea);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("search_city_location", search_city_location);
            request.setAttribute("search_point_name", search_point_name);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", vtm.getMessage());

            RequestDispatcher rd = request.getRequestDispatcher("point");
            rd.forward(request, response);
        } catch (Exception e) {
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
