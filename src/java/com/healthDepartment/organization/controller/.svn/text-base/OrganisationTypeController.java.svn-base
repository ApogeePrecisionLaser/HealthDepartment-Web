/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;

import com.healthDepartment.organization.model.OrganisationTypeModel;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.tableClasses.OrganisationType;
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
 * @author Soft_Tech
 */
public class OrganisationTypeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();
    /*    HttpSession session = request.getSession(false);
        if (session == null) {
            request.getRequestDispatcher("beforelogin.jsp").forward(request, response);
        }
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OrganisationTypeModel orgTypeModel = new OrganisationTypeModel();
        try {
            // orgTypeModel.setConnection(DBConnection.getConnection(ctx, session));
            orgTypeModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrganisationTypeController setConnection() calling try block" + e);
        }
          String requester = request.getParameter("requester");
        try {
            
            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getOrganisationType")) {
                        list = orgTypeModel.getOrgType(q);
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
                    orgTypeModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
            }
            String  searchOrgType = request.getParameter("searchOrgType");
            try {
                if (searchOrgType == null) {
                    searchOrgType = "";
                }
            } catch (Exception e) {
            }

             if (requester != null && requester.equals("PRINT")) {
                String jrxmlFilePath;
                List list=null;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/organization/OrganisationTypeList.jrxml");
                list=orgTypeModel.showAllData(searchOrgType);
                byte[] reportInbytes = orgTypeModel.generateSiteList(jrxmlFilePath,list);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();               
                return;
            }else if(requester!=null && requester.equals("PRINTXls"))
            {        String jrxmlFilePath;
                      List listAll=null;
                       response.setContentType("application/vnd.ms-excel");
                       response.addHeader("Content-Disposition", "attachment; filename=Orginisation_Report.xls");
                       ServletOutputStream servletOutputStream = response.getOutputStream();
                       jrxmlFilePath = ctx.getRealPath("/report/organization/OrganisationTypeList.jrxml");
                       listAll=orgTypeModel.showAllData(searchOrgType);
                       ByteArrayOutputStream reportInbytes =orgTypeModel.generateOrginisationXlsRecordList(jrxmlFilePath,listAll);
                       response.setContentLength(reportInbytes.size());
                       servletOutputStream.write(reportInbytes.toByteArray());
                       servletOutputStream.flush();
                       servletOutputStream.close();
                       return;
            }
          
           

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("Delete")) {
                orgTypeModel.deleteRecord(Integer.parseInt(request.getParameter("organisation_type_id")));  // Pretty sure that organisation_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int organisation_type_id;
                try {
                    organisation_type_id = Integer.parseInt(request.getParameter("organisation_type_id"));  // organisation_type_id may or may NOT be available i.e. it can be update or new record.

                } catch (Exception e) {
                    organisation_type_id = 0;
                }
                if (task.equals("Save AS New")) {
                    organisation_type_id = 0;
                }
                OrganisationType organisationType = new OrganisationType();
                organisationType.setOrganisation_type_id(organisation_type_id);
                organisationType.setOrg_type_name(request.getParameter("org_type_name").trim());
                organisationType.setDescription(request.getParameter("description"));
                if (organisation_type_id == 0) {
                    // if organisation_type_id was not provided, that means insert new record.
                    orgTypeModel.insertRecord(organisationType);
                } else {
                    // update existing record.
                    orgTypeModel.updateRecord(organisationType);
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
                searchOrgType = "";
            }
            noOfRowsInTable = orgTypeModel.getNoOfRows(searchOrgType);                  // get the number of records (rows) in the table.
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
            List<OrganisationType> orgTypeList = orgTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchOrgType);
            lowerLimit = lowerLimit + orgTypeList.size();
            noOfRowsTraversed = orgTypeList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("orgTypeList", orgTypeList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", orgTypeModel.getMessage());
            request.setAttribute("msgBgColor", orgTypeModel.getMsgBgColor());
            request.setAttribute("searchOrgType", searchOrgType);
            orgTypeModel.closeConnection();
            request.getRequestDispatcher("orgType_view").forward(request, response);
        } catch (Exception ex) {
            System.out.println("OrganisationTypeController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
