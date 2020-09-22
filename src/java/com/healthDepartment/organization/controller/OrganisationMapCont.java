/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;


import com.healthDepartment.organization.model.OrganisationMapModel;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.tableClasses.OrganisationMap;
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

/**  *
 * @author Soft_Tech
 */
public class OrganisationMapCont extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();
 /*       HttpSession session = request.getSession(false);
        if (session == null) {
            request.getRequestDispatcher("beforelogin.jsp").forward(request, response);
        }
        String role = (String) session.getAttribute("user_role");*/
        //((Integer)session.getAttribute("user_id")).intValue();
        
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");

        OrganisationMapModel organisationMapModel = new OrganisationMapModel();
        try {
          //  organisationMapModel.setConnection(DBConnection.getConnection(ctx, session));
            organisationMapModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrganisationMapCont setConnection() calling try block" + e);
        }
        try {
            String isOrgBasicStep = request.getParameter("isOrgBasicStep");
            if (isOrgBasicStep != null && !isOrgBasicStep.isEmpty()) {
                isOrgBasicStep = isOrgBasicStep.trim();
            } else {
                isOrgBasicStep = "";
            }
            String requester = request.getParameter("requester");
            try {
                //----- This is only for Vendor key Person JQuery
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getOrganization")) {
                        list = organisationMapModel.getOrganisation_Name(q);
                    } else if (JQstring.equals("getOrgTypeName")) {
                        list = organisationMapModel.getOrgTypeNameList(q);
                    } else if (JQstring.equals("getOrgSubTypeName")) {
                        list = organisationMapModel.getOrganisation_subType_Name(q, request.getParameter("action2"));
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
                    organisationMapModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --OrganisationMapCont get JQuery Parameters Part-" + e);
            }
            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("Delete")) {
                request.getParameter("org_map_id");
                organisationMapModel.deleteRecord(Integer.parseInt(request.getParameter("org_map_id")));  // Pretty sure that lighting_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int org_map_id;
                try {
                    org_map_id = Integer.parseInt(request.getParameter("org_map_id"));            // lighting_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    org_map_id = 0;
                }
                if (task.equals("Save AS New")) {
                    org_map_id = 0;
                }
                OrganisationMap organisationMap = new OrganisationMap();
                organisationMap.setOrg_map_id(org_map_id);
                organisationMap.setOrganisation_id(organisationMapModel.getOrganisation_id(request.getParameter("organisation_name").trim()));
                organisationMap.setOrganisation_type_id(organisationMapModel.getOrganisationTypeID(request.getParameter("org_type_name").trim()));
                organisationMap.setOrganisation_sub_type_id(organisationMapModel.getOrganisation_subType_id(request.getParameter("org_type_name").trim(), request.getParameter("organisation_sub_type_name").trim()));
                organisationMap.setDescription(request.getParameter("description").trim());
                if (org_map_id == 0) {
                    organisationMapModel.insertRecord(organisationMap);
                } else {                // update existing record.
                    organisationMapModel.updateRecord(organisationMap);
                }
            }
            int organisation_id = 0, org_type_id = 0, org_sub_type_id = 0;
            if (request.getParameter("organisation") != null && !request.getParameter("organisation").isEmpty()) {
                organisation_id = organisationMapModel.getOrganisation_id(request.getParameter("organisation").trim());
                request.setAttribute("organisation", request.getParameter("organisation"));
            }
            if (request.getParameter("org_type") != null && !request.getParameter("org_type").isEmpty()) {
                org_type_id = organisationMapModel.getOrganisationTypeID(request.getParameter("org_type"));
                request.setAttribute("org_type", request.getParameter("org_type"));
            }
            if (request.getParameter("org_sub_type") != null && !request.getParameter("org_sub_type").isEmpty()) {
                org_sub_type_id = organisationMapModel.getOrganisationSubTypeID(org_type_id, request.getParameter("org_sub_type"));
                request.setAttribute("org_sub_type", request.getParameter("org_sub_type"));
            }
            if (requester != null && requester.equals("PRINT")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                //  int organisation_id = 0, org_type_id = 0;
                request.getParameter("organisation");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/organization/orgMaplist.jrxml");
                byte[] reportInbytes = organisationMapModel.generateOrganisationMapList(jrxmlFilePath, organisation_id, org_type_id, org_sub_type_id);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                organisationMapModel.closeConnection();
                return;
            }else
                if(requester!=null && requester.equals("PRINTXls"))
                {
                      String jrxmlFilePath;                 
                       response.setContentType("application/vnd.ms-excel");
                       response.addHeader("Content-Disposition", "attachment; filename=Orginisation_Map_Report.xls");
                       ServletOutputStream servletOutputStream = response.getOutputStream();
                       jrxmlFilePath = ctx.getRealPath("/report/organization/orgMaplist.jrxml");
                       ByteArrayOutputStream reportInbytes =organisationMapModel.generateOrginisationMapXlsRecordList(jrxmlFilePath, organisation_id, org_type_id, org_sub_type_id);
                       response.setContentLength(reportInbytes.size());
                       servletOutputStream.write(reportInbytes.toByteArray());
                       servletOutputStream.flush();
                       servletOutputStream.close();
                       return;
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
            noOfRowsInTable = organisationMapModel.getNoOfRows(organisation_id, org_type_id, org_sub_type_id);                  // get the number of records (rows) in the table.
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
            List<OrganisationMap> orgMapList = organisationMapModel.showData(lowerLimit, noOfRowsToDisplay, organisation_id, org_type_id, org_sub_type_id);
            lowerLimit = lowerLimit + orgMapList.size();
            noOfRowsTraversed = orgMapList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("orgMapList", orgMapList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", organisationMapModel.getMessage());
            request.setAttribute("msgBgColor", organisationMapModel.getMsgBgColor());
            organisationMapModel.closeConnection();
            if (isOrgBasicStep.equals("Yes")) {
                if (task.equals("Save & Next")) {
                    String orgName = request.getParameter("organisation_name");
                    response.sendRedirect("organisationCont.do?isOrgBasicStep=Yes&org_name="+ request.getParameter("organisation_name"));
                    return;
                }else{
                    request.setAttribute("stepNo", 2);
                }
                
                request.getRequestDispatcher("orgBasicEntryView").forward(request, response);
            } else {
                request.getRequestDispatcher("organisationMap_view").forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println("OrganisationMapCont error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
