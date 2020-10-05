/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.vehicle.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicle.model.VehicleKeyPersonPointModel;
import com.healthDepartment.vehicle.tableClasses.VehicleKeyPersonPoint;
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
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Com7_2
 */
public class VehicleKeyPersonPointController extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //PrintWriter out = response.getWriter();

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
        VehicleKeyPersonPointModel vkpm = new VehicleKeyPersonPointModel();
        String task = request.getParameter("task");
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        try {

            vkpm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print(e);
        }

        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if (JQstring.equals("getsearch_key_person")) {

                    list = vkpm.getKeyPerson(q);
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

                if (JQstring.equals("getsearch_point_name")) {

                    list = vkpm.getSearchPointName(q);
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

                if (JQstring.equals("getkey_person")) {

                    list = vkpm.getKeyPerson(q);
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

                    list = vkpm.getPointName(q);
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
        String search_key_person_name = request.getParameter("search_key_person_name");
        if (search_key_person_name == null) {
            search_key_person_name = "";
        }
        String search_point_name = request.getParameter("search_point_name");
        if (search_point_name == null) {
            search_point_name = "";
        }
        if (task == null || task.isEmpty()) {
            task = "";
        }
 if (task.equals("showMapWindow")) {
            String longi = request.getParameter("long");
            String latti = request.getParameter("lat");
            request.setAttribute("longi", longi);
            request.setAttribute("latti", latti);
            System.out.println(latti + "," + longi);
            request.getRequestDispatcher("openMapWindowView1").forward(request, response);
            return;
        }
//        if (task.equals("showMapWindow")) {
//
//            int vehicle__key_person_point_id = Integer.parseInt(request.getParameter("vehicle_key_person_map_id"));
//            String latitude = "";
//            String longitude = "";
//            int vehicle_key_person_id = vkpm.getVehicleKeyPersonId2(vehicle__key_person_point_id);
//
//            request.setAttribute("vehicle_key_person_id", vehicle_key_person_id);
//            request.getRequestDispatcher("openMapWindowView1").forward(request, response);
//            return;
//
//        }

        if (task.equals("showAllPointMap")) {
            //  int key_person_id = 12;
//         search_key_person_name=request.getParameter("search_key_person");
//         search_point_name=request.getParameter("search_point_name");
            List<VehicleKeyPersonPoint> List = vkpm.showDataBean(search_key_person_name, search_point_name);
            request.setAttribute("CoordinatesList", List);
            request.getRequestDispatcher("/view/MapView/allKeyPersonPointMapView.jsp").forward(request, response);
            return;
        }
        if (task.equals("getAllCordinates")) {
            int data = Integer.parseInt(request.getParameter("data"));
            //  System.out.println("data="+data);
            JSONObject obj1 = new JSONObject();
            JSONArray arrayObj = new JSONArray();
            //String node_name = request.getParameter("temp");
            arrayObj = vkpm.getAllLatLang(data);
            obj1.put("map", arrayObj);
            PrintWriter out = response.getWriter();
            out.print(obj1);
            return;
        }
        try {
            if (task.equals("Delete")) {
                vkpm.deleteRecord(Integer.parseInt(request.getParameter("vehicle_key_person_point_id").trim()));
            } else if (task.equals("save") || task.equals("Save AS New")) {
                int vehicle_key_person_point_id = 0;
                try {
                    vehicle_key_person_point_id = Integer.parseInt(request.getParameter("vehicle_key_person_point_id").trim());
                } catch (Exception ex) {
                    vehicle_key_person_point_id = 0;
                }

                if (task.equals("Save AS New")) {
                    vehicle_key_person_point_id = 0;
                }
                String key_person = request.getParameter("key_person");
                String point_name = request.getParameter("point_name");
                String date = request.getParameter("date");
                String starting_time_hour = request.getParameter("starting_time_hour").trim();
                String starting_time_min = request.getParameter("starting_time_min").trim();

                String starting_time = starting_time_hour + ":" + starting_time_min;
                if (starting_time.equals(":")) {
                    starting_time = "";
                }

                //int vehicle_id=vkpm.getVehicleId(vehicle_code);
                int vehicle_key_person_id = vkpm.getVehicleKeyPersonId(key_person);
                int point_id = vkpm.getPointId(point_name);

                VehicleKeyPersonPoint vt = new VehicleKeyPersonPoint();
                vt.setVehicle_key_person_point_id(vehicle_key_person_point_id);
                //vt.setVehicle_id(vehicle_id);
                vt.setDate(date);
                vt.setTime(starting_time);
                vt.setVehicle_key_person_id(vehicle_key_person_id);
                vt.setPoint_id(point_id);
                if (vehicle_key_person_point_id > 0) {
                    vkpm.reviseRecords(vt);
                } else {
                    vkpm.insertRecord(vt);
                }
            } else if (task.equals("Show All Records")) {
                search_key_person_name = "";
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

            noOfRowsInTable = vkpm.getNoOfRows(search_point_name, search_key_person_name);

            if (buttonAction.equals("Next")); else if (buttonAction.equals("Previous")) {
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
            if (task.equals("save") || task.equals("Save AS New") || task.equals("Delete")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;
            }

            List<VehicleKeyPersonPoint> list = vkpm.showData(lowerLimit, noOfRowsToDisplay, search_point_name, search_key_person_name);

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
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("search_key_person_name", search_key_person_name);
            request.setAttribute("search_point_name", search_point_name);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", vkpm.getMessage());

            RequestDispatcher rd = request.getRequestDispatcher("vehicleKeyPersonPoint");
            rd.forward(request, response);
        } catch (Exception e) {
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
