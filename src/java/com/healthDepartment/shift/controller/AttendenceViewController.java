/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.shift.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.shift.model.AttendenceModel;
import com.healthDepartment.shift.tableClasses.ShiftLoginBean;
import com.healthDepartment.util.UniqueIDGenerator;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
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
 * @author Administrator
 */
public class AttendenceViewController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        ServletContext ctx = getServletContext();
        AttendenceModel am = new AttendenceModel();
        int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable = 0;
        try {
            am.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.print(e);
        }
        String searchemp = request.getParameter("searchemp");
        String empcode = request.getParameter("empcode");
        String mobileno = request.getParameter("mobileno");
        String date = request.getParameter("search_date");
        String attendence = request.getParameter("attendence");
        if (searchemp == null) {
            searchemp = "";
        }
        if (empcode == null) {
            empcode = "";
        }
        if (mobileno == null) {
            mobileno = "";
        }
        if (date == null) {
            date = "";
        }
        if (attendence == null) {
            attendence = "";
        }
        try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getempcode")) {
                    list = am.getempcode(q);
                } else if (JQstring.equals("getmobileno")) {
                    list = am.getmobileno(q);
                } else if (JQstring.equals("getsearchemp")) {
                    list = am.getsearchemp(q);
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
        if (task.equals("generateHSReport")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/pdf");
            response.setCharacterEncoding("UTF-8");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/report/shift/attendence.jrxml");
            list = am.showAttendenceData(-1, noOfRowsToDisplay, empcode, mobileno, date, searchemp, attendence);
            byte[] reportInbytes = am.generateRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
        if (task.equals("generateReport")) {
            String jrxmlFilePath;
            List list = null;
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=Attendence.xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            jrxmlFilePath = ctx.getRealPath("/Report/shift/attendence.jrxml");
            list = am.showAttendenceData(-1, noOfRowsToDisplay, empcode, mobileno, date, searchemp, attendence);
            ByteArrayOutputStream reportInbytes = am.generateXlsRecordList(jrxmlFilePath, list);
            response.setContentLength(reportInbytes.toByteArray().length);
            servletOutputStream.write(reportInbytes.toByteArray(), 0, reportInbytes.toByteArray().length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
        try {
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
            if (task.equals("Show All Records")) {
                searchemp = "";
                empcode = "";
                mobileno = "";
                date = "";
            }
            noOfRowsInTable = am.getNoOfRowsAttendence(empcode, mobileno, date, searchemp, attendence);

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
            if (task.equals("save") || task.equals("Save AS New") || task.equals("Revised") || task.equals("Delete")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;
            }
            List<ShiftLoginBean> list = am.showAttendenceData(lowerLimit, noOfRowsToDisplay, empcode, mobileno, date, searchemp, attendence);

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
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("empcode", empcode);
            request.setAttribute("mobileno", mobileno);
            request.setAttribute("searchemp", searchemp);
            request.setAttribute("search_date", date);
            request.setAttribute("attendence", attendence);
            request.setAttribute("message", am.getMessage());
            request.setAttribute("noOfRowsInTable", noOfRowsInTable);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            RequestDispatcher rd = request.getRequestDispatcher("attendenceview");
            rd.forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
