/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.vehicle.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicle.model.VehicleKeyPersonModel;
import com.healthDepartment.vehicle.tableClasses.VehicleKeyPerson;
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
public class VehicleKeyPersonController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
        VehicleKeyPersonModel vkpm = new VehicleKeyPersonModel();
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

                if (JQstring.equals("getsearch_key_person_name")) {

                    list = vkpm.getSearchKeyPersonName(q);   
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

                if (JQstring.equals("getsearch_vehicle_code")) {

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
        /////////////////////////////////////////////

        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getkey_person_name")) {
                    list = vkpm.getKey_person_name(q);
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

                if (JQstring.equals("getVehicle_code")) {

                    list = vkpm.getVehicle_code(q);
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
        String key_person_name = request.getParameter("search_key_person_name");
        if (key_person_name == null) {
            key_person_name = "";
        }
        String vehicle = request.getParameter("search_vehicle_code");
        if (vehicle == null) {
            vehicle = "";
        }
        if (task == null || task.isEmpty()) {
            task = "";
        }
        try {
            if (task.equals("Delete")) {
                vkpm.deleteRecord(Integer.parseInt(request.getParameter("vehicle_key_person_id").trim()));
            } else if (task.equals("save") || task.equals("Save AS New")) {
                int vehicle_key_person_id = 0;
                try {
                    vehicle_key_person_id = Integer.parseInt(request.getParameter("vehicle_key_person_id").trim());
                } catch (Exception ex) {
                    vehicle_key_person_id = 0;
                }

                if (task.equals("Save AS New")) {
                    vehicle_key_person_id = 0;
                }
                int vehicle_code = Integer.parseInt(request.getParameter("vehicle_code"));
                String key_person = request.getParameter("key_person_name");
                int vehicle_id = vkpm.getVehicleId(vehicle_code);
                int key_person_id = vkpm.getKeyPersonId(key_person);

                VehicleKeyPerson vt = new VehicleKeyPerson();
                vt.setVehicle_key_person_id(vehicle_key_person_id);
                vt.setVehicle_id(vehicle_id);
                vt.setKey_person_id(key_person_id);
                if (vehicle_key_person_id > 0) {
                    vkpm.reviseRecords(vt);
                } else {
                    vkpm.insertRecord(vt);
                }
            } else if (task.equals("Show All Records")) {
                key_person_name = "";
                vehicle = "";
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

            noOfRowsInTable = vkpm.getNoOfRows(vehicle, key_person_name);

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

            List<VehicleKeyPerson> list = vkpm.showData(lowerLimit, noOfRowsToDisplay, vehicle, key_person_name);

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
            // request.setAttribute("vehicleType", vehicleType);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", vkpm.getMessage());

            RequestDispatcher rd = request.getRequestDispatcher("vehicleKeyPerson");
            rd.forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
            //out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
