/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;

import com.healthDepartment.organization.model.OrgOfficeTypeModel;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.tableClasses.OrgOfficeType;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tarun
 */
public class OrgOfficeTypeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 20, noOfRowsInTable;
        ServletContext ctx = getServletContext();
    /*    HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("beforelogin.jsp");
            return;
        }
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OrgOfficeTypeModel orgOfficeTypeModel = new OrgOfficeTypeModel();
        try {
       //     orgOfficeTypeModel.setConnection(DBConnection.getConnection(ctx, session));
            orgOfficeTypeModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrgOfficeTypeController setConnection() calling try block" + e);
        }
        try {
            String searchOrgOfficeType = "";
            String searchOrgOfficeCode = "";
            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getOrganisationOfficeType")) {
                        list = orgOfficeTypeModel.getOrgOfficeType(q);
                    }
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        if (data.equals("Disable")) {
                            out.print(data);
                        } else {
                            out.println(data);
                        }
                    }
                    orgOfficeTypeModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
            }
            searchOrgOfficeType = request.getParameter("searchOrgOfficeType");
          try {
                if (searchOrgOfficeType == null) {
                    searchOrgOfficeType = "";
                }
            } catch (Exception e) {
            }
            String search= request.getParameter("searchOfficeType");
            if(search==null)
            {
                search="";
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            else if(task.equals("generateOrgOfficeReport"))//start from here
         {
            List listAll = null;
            String jrxmlFilePath;            
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll=orgOfficeTypeModel.showAllData(search);
            jrxmlFilePath = ctx.getRealPath("/report/organization/OrganisationOfficeType.jrxml");
            byte[] reportInbytes = orgOfficeTypeModel.orgOfficeReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
         }else if(task.equals("generateOrgOfficeXlsReport"))
         {     String jrxmlFilePath;
              List listAll=null;         
                       response.setContentType("application/vnd.ms-excel");
                       response.addHeader("Content-Disposition", "attachment; filename=city.xls");
                       ServletOutputStream servletOutputStream = response.getOutputStream();
                       jrxmlFilePath = ctx.getRealPath("/report/organization/OrganisationOfficeType.jrxml");
                       listAll=orgOfficeTypeModel.showAllData(search);
                       ByteArrayOutputStream reportInbytes =orgOfficeTypeModel.orgOfficeXlsRecordList(jrxmlFilePath, listAll);
                       response.setContentLength(reportInbytes.size());
                       servletOutputStream.write(reportInbytes.toByteArray());
                       servletOutputStream.flush();
                       servletOutputStream.close();
                       return;
         }
            if (task.equals("Delete")) {
                orgOfficeTypeModel.deleteRecord(Integer.parseInt(request.getParameter("office_type_id")));  // Pretty sure that office_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int office_type_id;
                try {
                    // office_type_id may or may NOT be available i.e. it can be update or new record.
                    office_type_id = Integer.parseInt(request.getParameter("office_type_id"));
                } catch (Exception e) {
                    office_type_id = 0;
                }
                if (task.equals("Save AS New")) {
                    office_type_id = 0;
                }
                OrgOfficeType orgOfficeType = new OrgOfficeType();
                orgOfficeType.setOffice_type_id(office_type_id);
                orgOfficeType.setOffice_type(request.getParameter("office_type").trim());
                orgOfficeType.setDescription(request.getParameter("description").trim());
                if (office_type_id == 0) {
                    // if office_type_id was not provided, that means insert new record.
                    orgOfficeTypeModel.insertRecord(orgOfficeType);
                } else {
                    // update existing record.
                    orgOfficeTypeModel.updateRecord(orgOfficeType);
                }
            }

            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            }
            if (task.equals("Show All Records")) {
                searchOrgOfficeType = "";
                searchOrgOfficeCode = "";
            }
            noOfRowsInTable = orgOfficeTypeModel.getNoOfRows(searchOrgOfficeType);                  // get the number of records (rows) in the table.
            if (buttonAction.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
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

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            } 
            // Logic to show data in the table.
            List<OrgOfficeType> orgOfficeTypeList = orgOfficeTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchOrgOfficeType);
            lowerLimit = lowerLimit + orgOfficeTypeList.size();
            noOfRowsTraversed = orgOfficeTypeList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("orgOfficeTypeList", orgOfficeTypeList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", orgOfficeTypeModel.getMessage());
            request.setAttribute("searchOrgOfficeType", searchOrgOfficeType);
            request.setAttribute("searchOrgOfficeCode", searchOrgOfficeCode);
            request.setAttribute("msgBgColor", orgOfficeTypeModel.getMsgBgColor());
            orgOfficeTypeModel.closeConnection();
            request.getRequestDispatcher("orgOfficeType_view").forward(request, response);
        } catch (Exception ex) {
            System.out.println("OrgOfficeTypeController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
