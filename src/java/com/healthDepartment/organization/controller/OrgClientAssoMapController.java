package com.healthDepartment.organization.controller;

import com.healthDepartment.organization.model.OrgClientAssoMapModel;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.tableClasses.OrgClientAssoMap;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class OrgClientAssoMapController extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 20, noOfRowsInTable;
        ServletContext ctx = getServletContext();

   /*     HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
            response.sendRedirect("beforelogin.jsp");
            return;
        }
        String role = (String) session.getAttribute("user_role");   */
        //((Integer)session.getAttribute("user_id")).intValue();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        PrintWriter out = response.getWriter();
        OrgClientAssoMapModel orgClientAssoMapModel = new OrgClientAssoMapModel();
        try {
            // orgClientAssoMapModel.setConnection(DBConnection.getConnection(ctx, session));
            orgClientAssoMapModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrgClientAssoMapController setConnection() calling try block" + e);
        }
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                List<String> list = null;
                if (JQstring.equals("getClientAssoOrganization")) {
                    list = orgClientAssoMapModel.getClientAssoOrgName(q);
                } else if (JQstring.equals("getClientOrgName")) {
                    list = orgClientAssoMapModel.getClientOrgNameList(q);
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
                orgClientAssoMapModel.closeConnection();
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
            request.getParameter("org_client_asso_map_id");
            orgClientAssoMapModel.deleteRecord(Integer.parseInt(request.getParameter("org_client_asso_map_id")));  // Pretty sure that lighting_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int org_client_asso_map_id;
            try {
                org_client_asso_map_id = Integer.parseInt(request.getParameter("org_client_asso_map_id"));            // lighting_id may or may NOT be available i.e. it can be update or new record.
            } catch (Exception e) {
                org_client_asso_map_id = 0;
            }
            if (task.equals("Save AS New")) {
                org_client_asso_map_id = 0;
            }
            OrgClientAssoMap organisationMap = new OrgClientAssoMap();
            organisationMap.setOrg_client_asso_map_id(org_client_asso_map_id);
            organisationMap.setOrg_asso_id(orgClientAssoMapModel.getClientAssoOrgID(request.getParameter("client_asso_org").trim()));
            organisationMap.setOrg_client_id(orgClientAssoMapModel.getClientOrg_id(request.getParameter("client_org").trim()));
            organisationMap.setDescription(request.getParameter("description").trim());
            if (org_client_asso_map_id == 0) {
                orgClientAssoMapModel.insertRecord(organisationMap);
            } else {                // update existing record.
                orgClientAssoMapModel.updateRecord(organisationMap);
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
        noOfRowsInTable = orgClientAssoMapModel.getNoOfRows();                  // get the number of records (rows) in the table.
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
        List<OrgClientAssoMap> orgCAMapList = orgClientAssoMapModel.showData(lowerLimit, noOfRowsToDisplay);
        lowerLimit = lowerLimit + orgCAMapList.size();
        noOfRowsTraversed = orgCAMapList.size();

        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("orgCAMapList", orgCAMapList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", orgClientAssoMapModel.getMessage());
        request.setAttribute("msgBgColor", orgClientAssoMapModel.getMsgBgColor());
        orgClientAssoMapModel.closeConnection();
        request.getRequestDispatcher("orgClientAssoMap_view").forward(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
