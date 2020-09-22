/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;

import com.healthDepartment.organization.model.OrganisationSubTypeModel;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.tableClasses.OrganisationSubType;
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
 * @author SoftTech
 */
public class OrganisationSubTypeCont extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 20, noOfRowsInTable;
        ServletContext ctx = getServletContext();
  /*      HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("beforelogin.jsp");
            return;
        }
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();
        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
      
        OrganisationSubTypeModel organisationSubTypeModel = new OrganisationSubTypeModel();
        try {
           //  organisationSubTypeModel.setConnection(DBConnection.getConnection(ctx, session));
            organisationSubTypeModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrganisationSubTypeCont setConnection() calling try block" + e);
        }
        try {        
                String JQstring = request.getParameter("param1");
                String q = request.getParameter("q");
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getOrgTypeName")) {
                        list = organisationSubTypeModel.getOrgTypeNameList(q);
                    }
                    if (JQstring.equals("getOrgSubTypeName")) {
                        list = organisationSubTypeModel.getOrgSubTypeNameList(q);
                    }
                    Iterator iter = list.iterator();
                    while (iter.hasNext()) {
                        out.println(iter.next());
                    }
                    organisationSubTypeModel.closeConnection();
                   return;             
            }
      String task = request.getParameter("task");
            if (task == null)
            {
                task = "";
            }
       String   searchOrgType = request.getParameter("searchOrgType");
       String   searchOrgSubType = request.getParameter("searchOrgSubType");
            try {
                if (searchOrgType == null) {
                    searchOrgType = "";
                }
                if (searchOrgSubType == null) {
                    searchOrgSubType = "";
                }
            } catch (Exception e) {
            }
       List<OrganisationSubType> organisationSubTypeList=null;
          if(task.equals("generateOrgReport"))
         {
           response.setContentType("application/pdf");
           ServletOutputStream servletOutputStream = response.getOutputStream();
           List listAll=organisationSubTypeModel.showAllData(searchOrgType,searchOrgSubType);
           String  jrxmlFilePath = ctx.getRealPath("/report/organization/OrganisationSubTypeReport.jrxml");
            byte[] reportInbytes = organisationSubTypeModel.generateOrgSubTypeReport(jrxmlFilePath,listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
         }else if(task.equals("generateOrgXlsReport"))
         {
                  response.setContentType("application/vnd.ms-excel");
                  response.addHeader("Content-Disposition", "attachment; filename=OrgSubType.xls");
                  ServletOutputStream servletOutputStream = response.getOutputStream();
                  String jrxmlFilePath = ctx.getRealPath("/report/organization/OrganisationSubTypeReport.jrxml");
                  List listAll=organisationSubTypeModel.showAllData( searchOrgType,searchOrgSubType);
                  ByteArrayOutputStream reportInbytes =organisationSubTypeModel.generateOrgSubTypeXlsRecordList(jrxmlFilePath, listAll);
                  response.setContentLength(reportInbytes.size());
                  servletOutputStream.write(reportInbytes.toByteArray());
                  servletOutputStream.flush();
                  servletOutputStream.close();
                  return;
         }           
            if (task.equals("Delete")) {
                organisationSubTypeModel.deleteRecord(Integer.parseInt(request.getParameter("organisation_sub_type_id")));  // Pretty sure that lighting_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int organisation_sub_type_id;
                try {
                    organisation_sub_type_id = Integer.parseInt(request.getParameter("organisation_sub_type_id"));            // lighting_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    organisation_sub_type_id = 0;
                }
                if (task.equals("Save AS New")) {
                    organisation_sub_type_id = 0;
                }
                OrganisationSubType organisationSubType = new OrganisationSubType();
                organisationSubType.setOrganisation_sub_type_id(organisation_sub_type_id);
                organisationSubType.setOrganisation_sub_type_name(request.getParameter("organisation_sub_type_name").trim());
                organisationSubType.setOrganisation_type_id(organisationSubTypeModel.getOrganisationTypeID(request.getParameter("org_type_name").trim()));
                if (organisation_sub_type_id == 0) {
                    // if lighting_id was not provided, that means insert new record.
                    organisationSubTypeModel.insertRecord(organisationSubType);
                } else {
                    // update existing record.
                    organisationSubTypeModel.updateRecord(organisationSubType);
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

            
            if (task.equals("Search")) {
                lowerLimit = noOfRowsTraversed = 0;
            }else if (task.equals("Show All Records")) {
                searchOrgType = "";
                searchOrgSubType = "";
            }// get the number of records (rows) in the table.
            noOfRowsInTable = organisationSubTypeModel.getNoOfRows(searchOrgType, searchOrgSubType);
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
           organisationSubTypeList = organisationSubTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchOrgType, searchOrgSubType);
            lowerLimit = lowerLimit + organisationSubTypeList.size();
            noOfRowsTraversed = organisationSubTypeList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("orgSubTypeList", organisationSubTypeList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("searchOrgType", searchOrgType);
            request.setAttribute("searchOrgSubType", searchOrgSubType);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", organisationSubTypeModel.getMessage());
            request.setAttribute("msgBgColor", organisationSubTypeModel.getMsgBgColor());
            organisationSubTypeModel.closeConnection();
            request.getRequestDispatcher("orgSubType_view").forward(request, response);
        } catch (Exception ex) {
            System.out.println("OrganisationSubTypeCont error: " + ex);
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
