/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;

import com.healthDepartment.organization.model.OrgOfficeModel;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.tableClasses.Org_Office;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
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

public class OrgOfficeController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
        ServletContext ctx = getServletContext();

  /*      HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("beforelogin.jsp");
            return;
        }
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();

        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OrgOfficeModel organisationModel = new OrgOfficeModel();
        try {
            // organisationModel.setConnection(DBConnection.getConnection(ctx, session));
            organisationModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrgOfficeController setConnection() calling try block" + e);
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
                String action2 = "";
                String q = request.getParameter("q");   // field own input
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getOrganization")) {
                        list = organisationModel.getOrganisation_Name(q);
                    }  else if (JQstring.equals("getOrgOfficeCodeSearch")){
                        list = organisationModel.getOrgOfficeCodeSearch(q);
                    } else if (JQstring.equals("getOrgOfficeNameSearch")){
                        list = organisationModel.getOrgOfficeNameSearch(q,request.getParameter("action2"));
                    }
                    else if (JQstring.equals("getOrgOfficeType")) {
                         list = organisationModel.OrgOfficeType(q);
                    }// else if (JQstring.equals("getStateName")) {
                      //  list = organisationModel.getStateName(q);
                   // }
                else if (JQstring.equals("getCityName")) {
                        list = organisationModel.getCityName(q);//, request.getParameter("action2")
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
                    organisationModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --OrgOfficeController get JQuery Parameters Part-" + e);
            }


            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }
            if (task.equals("Delete")) {
                organisationModel.deleteRecord(Integer.parseInt(request.getParameter("org_office_id")));  // Pretty sure that org_office_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int org_office_id;
                try {
                    org_office_id = Integer.parseInt(request.getParameter("org_office_id"));            // org_office_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    org_office_id = 0;
                }
                if (task.equals("Save AS New")) {
                    org_office_id = 0;
                }
                KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
                Org_Office orgOffice = new Org_Office();
                orgOffice.setOrg_office_id(org_office_id);
                orgOffice.setOrganisation_id(organisationModel.getOrganisation_id(krutiToUnicode.convert_to_unicode(request.getParameter("organisation_name").trim())));
                orgOffice.setOrg_office_name(request.getParameter("org_office_name").trim());
                orgOffice.setOffice_type_id(organisationModel.getOrgOfficeType_id(krutiToUnicode.convert_to_unicode(request.getParameter("office_type").trim())));
                orgOffice.setOrg_office_code(request.getParameter("org_office_code").trim());
                orgOffice.setAddress_line1(request.getParameter("address_line1").trim());
                orgOffice.setAddress_line2(request.getParameter("address_line2").trim());
                orgOffice.setAddress_line3(request.getParameter("address_line3").trim());
                orgOffice.setCity_id(organisationModel.getCity_id(krutiToUnicode.convert_to_unicode(request.getParameter("city_name").trim())));
                orgOffice.setEmail_id1(request.getParameter("email_id1").trim());
                orgOffice.setEmail_id2(request.getParameter("email_id2").trim());
                orgOffice.setMobile_no1(request.getParameter("mobile_no1").trim());
                orgOffice.setMobile_no2(request.getParameter("mobile_no2").trim());
                orgOffice.setLandline_no1(request.getParameter("landline_no1").trim());
                orgOffice.setLandline_no2(request.getParameter("landline_no2").trim());
                orgOffice.setLandline_no3(request.getParameter("landline_no3").trim());
                if (org_office_id == 0) {
                    // if org_office_id was not provided, that means insert new record.
                    organisationModel.insertRecord(orgOffice);
                } else {
                    // update existing record.
                    organisationModel.updateRecord(orgOffice);
                }
            }
            if (requester != null && requester.equals("PRINT")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                //response.addHeader("Content-Disposition", "attachment; filename=OrganizationOffice_report.pdf");
                int organisation_id = 0;
                request.getParameter("organisation");
                if (request.getParameter("organisation") != null && !request.getParameter("organisation").isEmpty()) {
                    organisation_id = organisationModel.getOrganisation_id(request.getParameter("organisation").trim());
                }
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/organization/orgOfficelist.jrxml");
                byte[] reportInbytes = organisationModel.generateOrganisationOfficeList(jrxmlFilePath, organisation_id);
//                ByteArrayOutputStream byteArray = organisationModel.generateOrganisationOfficeList(jrxmlFilePath, organisation_id);
//                response.setContentLength(byteArray.size());
//                servletOutputStream.write(byteArray.toByteArray());
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                organisationModel.closeConnection();
                return;
            }else if (requester != null && requester.equals("PrintExcel")) {
                String jrxmlFilePath;
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=OrganizationOffice_report.xls");
                int organisation_id = 0;
                request.getParameter("organisation");
                if (request.getParameter("organisation") != null && !request.getParameter("organisation").isEmpty()) {
                    organisation_id = organisationModel.getOrganisation_id(request.getParameter("organisation").trim());
                }
                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/organization/orgOfficelist.jrxml");
                ByteArrayOutputStream reportInbytes = organisationModel.generateOrganisationOfficeExcelList(jrxmlFilePath, organisation_id);
                response.setContentLength(reportInbytes.size());
                servletOutputStream.write(reportInbytes.toByteArray());
                servletOutputStream.flush();
                servletOutputStream.close();
                organisationModel.closeConnection();
                return;
            }else
            if (requester != null && requester.equals("PRINTAddress")) {
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                int org_office_id = Integer.parseInt(request.getParameter("org_office_id"));

                ServletOutputStream servletOutputStream = response.getOutputStream();
                jrxmlFilePath = ctx.getRealPath("/report/office_address.jrxml");
                byte[] reportInbytes = organisationModel.generateofficeAddressList(jrxmlFilePath, org_office_id);
                response.setContentLength(reportInbytes.length);
                servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                servletOutputStream.flush();
                servletOutputStream.close();
                organisationModel.closeConnection();
                return;
            }

            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
            String org_name = "";
            String office_code_search = "";
            String office_name_search = "";
            org_name = request.getParameter("org_name");
            office_code_search = request.getParameter("office_code_search");
            office_name_search = request.getParameter("office_name_search");
            try {

                if (org_name == null) {
                    org_name = "";
                }
                if (office_name_search == null) {
                    office_name_search = "";
                }
                if (office_code_search == null) {
                    office_code_search = "";
                }

            } catch (Exception e) {
            }
//        String org_name = "";
//        if (request.getParameter("search_org") != null) {
//            if (request.getParameter("search_org").equals("SEARCH")) {
//                org_name = request.getParameter("org_name");
//            }
//        }
//        if (request.getParameter("clear_org") != null) {
//            if (request.getParameter("clear_org").equals("CLEAR")) {
//                org_name = request.getParameter("org_name");
//                org_name = org_name.trim();
//            }
//        }
            if (task.equals("Show All Records")) {
                org_name = "";
                office_code_search = "";
                office_name_search = "";
            }

            String buttonAction = request.getParameter("buttonAction"); // Holds the name of any of the four buttons: First, Previous, Next, Delete.
            if (buttonAction == null) {
                buttonAction = "none";
            } else {
                org_name = request.getParameter("org_name");
            }
        //    if (org_name != null && !org_name.isEmpty()) {
                noOfRowsInTable = organisationModel.getNoOfRows(org_name, office_code_search, office_name_search);
        //    } else {
        //        noOfRowsInTable = organisationModel.getNoOfRows();
        //    }// get the number of records (rows) in the table.
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
            List<Org_Office> organisationList = organisationModel.showData(lowerLimit, noOfRowsToDisplay, org_name, office_code_search, office_name_search);
            lowerLimit = lowerLimit + organisationList.size();
            noOfRowsTraversed = organisationList.size();

            // Now set request scoped attributes, and then forward the request to view.
            // Following request scoped attributes NAME will remain constant from module to module.
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", organisationModel.getMessage());
            request.setAttribute("msgBgColor", organisationModel.getMsgBgColor());

            // Following request scoped attributes NAME will change from module to module.
            request.setAttribute("organisationList", organisationList);
            request.setAttribute("org_name", org_name);
            request.setAttribute("office_code_search", office_code_search);
            request.setAttribute("office_name_search", office_name_search);
            organisationModel.closeConnection();
            if (isOrgBasicStep.equals("Yes")) {
                if (task.equals("Save & Next")) {
                    response.sendRedirect("personCount.do?isOrgBasicStep=Yes&searchOrganisation=" + request.getParameter("organisation_name"));
                    return;
                } else {
                    request.setAttribute("stepNo", 3);
                }
                request.getRequestDispatcher("orgBasicEntryView").forward(request, response);
            } else {
                request.getRequestDispatcher("organisation_view").forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println("OrgOfficeController error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
