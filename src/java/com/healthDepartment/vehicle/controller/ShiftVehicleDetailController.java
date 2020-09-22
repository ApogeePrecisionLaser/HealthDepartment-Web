/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.vehicle.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import com.healthDepartment.vehicle.model.ShiftVehicleDetailModel;
import com.healthDepartment.vehicle.tableClasses.ShiftVehicleDetail;
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
public class ShiftVehicleDetailController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        ServletContext ctx = getServletContext();
        ShiftVehicleDetailModel vkpm = new ShiftVehicleDetailModel();
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

                if (JQstring.equals("getSearchKey_person_name_givenBy")) {

                    list = vkpm.getSearchKeyPersonNameGivenBy(q);
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

                if (JQstring.equals("getSearchKey_person_name_takenBy")) {

                    list = vkpm.getSearchKeyPersonNameTakenBy(q);
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
                if (JQstring.equals("getVehicle_number")) {
                    list = vkpm.getVehicle_number(q);
                }
                if (JQstring.equals("getZone")) {
                    String case_no = request.getParameter("action3");
                    list = vkpm.getZone(q, case_no);
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
        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;

                if (JQstring.equals("getPoint")) {

                    list = vkpm.getPoint(q);
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
                if (JQstring.equals("getKey_person_name_givenBy")) {
                    list = vkpm.getKeyPersonGivenBy(q);
                }
//                if (JQstring.equals("getZone")) {
//                         String case_no=request.getParameter("action3");
//                        list = vkpm.getZone(q,case_no);
//                    }


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
                if (JQstring.equals("getKey_person_name_takenBy")) {
                    list = vkpm.getKeyPersonTakenBy(q);
                }
//                if (JQstring.equals("getZone")) {
//                         String case_no=request.getParameter("action3");
//                        list = vkpm.getZone(q,case_no);
//                    }


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

        String search_given_by = request.getParameter("search_given_by");
        if (search_given_by == null) {
            search_given_by = "";
        }
        String search_taken_by = request.getParameter("search_taken_by");
        if (search_taken_by == null) {
            search_taken_by = "";
        }
        String searchdate = request.getParameter("search_date");
        if (searchdate == null) {
            searchdate = "";
        }
        if (task == null || task.isEmpty()) {
            task = "";
        }

        if (task.equals("showMapWindow")) {

            String shiftVehicleDetailId = request.getParameter("shiftVehicleDetailId");
            String latitude = "";
            String longitude = "";
            String LatLong = vkpm.getPointLatLong(shiftVehicleDetailId);
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
                vkpm.deleteRecord(Integer.parseInt(request.getParameter("vehicle_key_person_id").trim()));
            } else if (task.equals("save") || task.equals("Save AS New")) {
                int vehicle_key_person_id = 0;
                try {
                    vehicle_key_person_id = Integer.parseInt(request.getParameter("shift_vehicle_detail_id").trim());
                } catch (Exception ex) {
                    vehicle_key_person_id = 0;
                }

                if (task.equals("Save AS New")) {
                    vehicle_key_person_id = 0;
                }
                String givenBy = request.getParameter("given_by");
                String takenBy = request.getParameter("taken_by");
                String vehicleCode = request.getParameter("vehicle_code");
                String vehicleNumber = request.getParameter("vehicle_number");
                String point = request.getParameter("point");
                String date = request.getParameter("date");
                String status = request.getParameter("status");
                String latitude = request.getParameter("latitude");
                String longitude = request.getParameter("longitude");
                //int vehicle_id=vkpm.getVehicleId(vehicle_code);
                // int key_person_id=vkpm.getKeyPersonId(key_person);

                ShiftVehicleDetail vt = new ShiftVehicleDetail();
                vt.setShiftVehicleDetailId(vehicle_key_person_id);
                vt.setGivenBy(givenBy);
                vt.setTakenBy(takenBy);
                vt.setVehicleCode(vehicleCode);
                vt.setVehicleNumber(vehicleNumber);
                vt.setPoint_name(point);
                vt.setDate(date);
                vt.setStatus(status);
                vt.setLatitude(Double.parseDouble(latitude));
                vt.setLongitude(Double.parseDouble(longitude));
                if (vehicle_key_person_id > 0) {
                    vkpm.reviseRecords(vt);
                } else {
                    vkpm.insertRecord(vt);
                }
            } else if (task.equals("Show All Records")) {
                search_given_by = "";
                search_taken_by = "";
                searchdate="";
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

            noOfRowsInTable = vkpm.getNoOfRows(search_given_by, search_taken_by, searchdate);

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

            List<ShiftVehicleDetail> list = vkpm.showData(lowerLimit, noOfRowsToDisplay, search_given_by, search_taken_by, searchdate);

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
            request.setAttribute("search_given_by", search_given_by);
            request.setAttribute("search_taken_by", search_taken_by);
            request.setAttribute("searchdate", searchdate);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", vkpm.getMessage());

            RequestDispatcher rd = request.getRequestDispatcher("shiftVehicleDetail");
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
