/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;

import com.healthDepartment.organization.model.OrgDetailEntryModel;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.organization.tableClasses.OrgDetailEntry;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author JPSS
 */
public class OrgDetailEntryController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ServletContext ctx = getServletContext();
        /*       HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_name") == null) {
        response.sendRedirect("beforelogin.jsp");
        return;
        }
        String role = (String) session.getAttribute("user_role"); */
        //((Integer)session.getAttribute("user_id")).intValue();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        OrgDetailEntryModel createOrganModel = new OrgDetailEntryModel();
        try {
            //      createOrganModel.setConnection(DBConnection.getConnection(ctx, session));
            createOrganModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (Exception e) {
            System.out.println("error in OrgDetailEntryController setConnection() calling try block" + e);
        }

        String input = null, task = null, subTask = "";
        List list = null;
        String data = null;
        String searchOrganisation = "";
        if (request.getParameter("q") != null) {
            PrintWriter out = response.getWriter();
            input = request.getParameter("q").trim();
            String JQstring = request.getParameter("action1");
            if (JQstring != null) {
                if (JQstring.equals("searchOrganisation")) {
                    list = createOrganModel.getOrganisationList(input);
                } else if (JQstring.equals("Office_type")) {
                    list = createOrganModel.getOrgOfficeType(input);
                } else if (JQstring.equals("getOfficeCode")) {
                    list = createOrganModel.getOfficeCode(input, request.getParameter("office_type"));
                } else if (JQstring.equals("City")) {
                    list = createOrganModel.getCityName(input);
                } else if (JQstring.equals("Designation")) {
                    list = createOrganModel.getDesignation(input);
                } else if (JQstring.equals("getOrgTypeName")) {
                    list = createOrganModel.getOrgTypeNameList(input);
                } else if (JQstring.equals("getOrgSubTypeName")) {
                    list = createOrganModel.getOrganisation_subType_Name(input, request.getParameter("action2"));
                }
            }
            Iterator itr = list.iterator();
            while (itr.hasNext()) {
                data = (String) itr.next();
                out.println(data);
            }
            createOrganModel.closeConnection();
            return;
        }

        try {
            Map<Integer, String> map = new LinkedHashMap<Integer, String>();
            if (request.getParameter("ajaxRequest") != null || !(request.getParameter("ajaxRequest").isEmpty())) {
                PrintWriter out = response.getWriter();
                String jSONFormate = "";
                if (request.getParameter("ajaxRequest").equals("getOfficeDetail")) {
                    String org_office_id = request.getParameter("org_office_id");
                    String organisation_id = request.getParameter("organisation_id");
                    if (organisation_id != null && !organisation_id.isEmpty() && org_office_id != null && !org_office_id.isEmpty()) {
                        String jSONFormat = createOrganModel.getOfficePersonDetail(Integer.parseInt(organisation_id.trim()), Integer.parseInt(org_office_id.trim()));
                        out.println(jSONFormat);
                    }
                } else if (request.getParameter("ajaxRequest").equals("getPersonDetail")) {
                    String organisation_id = request.getParameter("organisation_id").trim();
                    String org_office_id = request.getParameter("org_office_id").trim();
                    String person_id = request.getParameter("person_id").trim();
                    if (organisation_id != null && !organisation_id.isEmpty() && org_office_id != null && !org_office_id.isEmpty()) {
                        jSONFormate = createOrganModel.getPersonDetail(Integer.parseInt(organisation_id.trim()), Integer.parseInt(org_office_id.trim()), Integer.parseInt(person_id.trim()));
                        out.println(jSONFormate);
                    }
                } else if (request.getParameter("ajaxRequest").equals("searchPerson")) {
                    String organisation_id = request.getParameter("organisation_id");
                    String org_office_id = request.getParameter("org_office_id");
                    if (organisation_id != null && !organisation_id.isEmpty() && org_office_id != null && !org_office_id.isEmpty()) {
                        map = createOrganModel.getPersonlist(Integer.parseInt(organisation_id.trim()), Integer.parseInt(org_office_id.trim()));
                        for (Integer key : map.keySet()) {
                            out.println(key + "&#;");
                            out.println(map.get(key) + "&#;");
                        }
                    }
                } else if (request.getParameter("ajaxRequest").equals("searchOffice")) {
                    String organisation_id = request.getParameter("organisation_id");
                    if (organisation_id != null && !organisation_id.isEmpty()) {
                        map = createOrganModel.getOrgOfficeList(Integer.parseInt(organisation_id.trim()));
                        for (Integer key : map.keySet()) {
                            out.println(key + "&#;");
                            out.println(map.get(key) + "&#;");
                        }
                    }
                }
                return;
            }
        } catch (Exception abc) {
        }
        task = request.getParameter("task");
        subTask = request.getParameter("subTask");
        if (subTask != null) {
            subTask = subTask.trim();
        }
        request.setAttribute("isSearch", "No");
        List<OrgDetailEntry> orgDetailList = new ArrayList<OrgDetailEntry>();
        if (task != null) {
            if (task.equals("CreateOrganisation")) {
                request.setAttribute("isvalidOrgn", "false");
            } else if (task.equals("Save")) {
                String jQueryMessage = "", organisation = null, isUpdate = "No";

                OrgDetailEntry createOrgn = new OrgDetailEntry();
                createOrgn.setOrganisation(request.getParameter("organisation"));
                if (request.getParameter("organisation") != null && !request.getParameter("organisation").isEmpty()) {
                    createOrgn.setOrganisation(request.getParameter("organisation").trim());                            //  createOrgn.setOrganisation_id(createOrganModel.getOrganisation_id(organisation));
                }
                if (request.getParameter("org_map_id") != null && !request.getParameter("org_map_id").isEmpty()) {
                    createOrgn.setOrg_map_id(Integer.parseInt(request.getParameter("org_map_id").trim()));
                } else {
                    createOrgn.setOrg_map_id(0);
                }
                if (request.getParameter("organisation_id") != null && !request.getParameter("organisation_id").isEmpty()) {
                    createOrgn.setOrganisation_id(Integer.parseInt(request.getParameter("organisation_id").trim()));
                } else {
                    createOrgn.setOrganisation_id(0);
                }
                if (request.getParameter("org_type_name") != null && !request.getParameter("org_type_name").isEmpty()) {
                    if (request.getParameter("org_sub_type") != null && !request.getParameter("org_sub_type").isEmpty()) {
                        list = createOrganModel.getOrgTypeSubTypeId(request.getParameter("org_type_name").trim(), request.getParameter("org_sub_type").trim());
                        createOrgn.setOrganisation_type_id((Integer) list.get(0));
                        createOrgn.setOrg_sub_type_id((Integer) list.get(1));
                        createOrgn.setOrg_type_name(request.getParameter("org_type_name").trim());
                        createOrgn.setOrg_sub_type(request.getParameter("org_sub_type").trim());
                    }
                }
                //###################################### Office Detail ############################################
                if (request.getParameter("office_id") != null && !request.getParameter("office_id").isEmpty()) {
                    createOrgn.setOffice_id(Integer.parseInt(request.getParameter("office_id").trim()));
                } else {
                    createOrgn.setOffice_id(0);
                }
                if (request.getParameter("office_name") != null && !request.getParameter("office_name").isEmpty()) {
                    createOrgn.setOffice_name(request.getParameter("office_name").trim());
                }
                 if (request.getParameter("office_code") != null && !request.getParameter("office_code").isEmpty()) {
                    createOrgn.setOffice_code(request.getParameter("office_code").trim());
                }
                if (request.getParameter("office_type") != null && !request.getParameter("office_type").isEmpty()) {
                    createOrgn.setOffice_type_id(createOrganModel.getofficeType_id(request.getParameter("office_type").trim()));
                    createOrgn.setOffice_type(request.getParameter("office_type").trim());
                }
                if (request.getParameter("office_city") != null && !request.getParameter("office_city").isEmpty()) {
                    createOrgn.setOffice_city_id(createOrganModel.getCity_id(request.getParameter("office_city").trim()));
                    createOrgn.setOffice_city(request.getParameter("office_city").trim());
                }
                createOrgn.setOffice_address1(request.getParameter("office_address1"));
                createOrgn.setOffice_address2(request.getParameter("office_address2"));
                createOrgn.setOffice_mail_id1(request.getParameter("office_mail_id1"));
                createOrgn.setOffice_mail_id2(request.getParameter("office_mail_id2"));
                createOrgn.setOffice_mobile1(request.getParameter("office_mobile1"));
                createOrgn.setOffice_mobile2(request.getParameter("office_mobile2"));
                createOrgn.setOffice_landLine1(request.getParameter("office_landLine1"));
                createOrgn.setOffice_landLine2(request.getParameter("office_landLine2"));
                // ************************************ Person Detail ***************************
                if (request.getParameter("keyPersonId") != null && !request.getParameter("keyPersonId").isEmpty()) {
                    createOrgn.setKeyPersonId(Integer.parseInt(request.getParameter("keyPersonId").trim()));
                } else {
                    createOrgn.setKeyPersonId(0);
                }
                createOrgn.setSalutation(request.getParameter("salutation"));
                if (request.getParameter("employeeId") != null && !request.getParameter("employeeId").isEmpty()) {
                    createOrgn.setEmployeeId(request.getParameter("employeeId"));
                }
                if (request.getParameter("key_person") != null && !request.getParameter("key_person").isEmpty()) {
                    createOrgn.setKeyperson(request.getParameter("key_person"));
                }
                if (request.getParameter("designation") != null && !request.getParameter("designation").isEmpty()) {
                    String designation = request.getParameter("designation");
                    createOrgn.setDesignation(designation);
                    createOrgn.setDesiganition_id(createOrganModel.getDegination_id(designation));
                }
                if (request.getParameter("person_city") != null && !request.getParameter("person_city").isEmpty()) {
                    createOrgn.setPerson_city(request.getParameter("person_city").trim());
                    createOrgn.setPerson_city_id(createOrganModel.getCity_id(request.getParameter("person_city").trim()));
                }
                  createOrgn.setFather_name(request.getParameter("father_name"));
                createOrgn.setAge(Integer.parseInt(request.getParameter("age")));
                createOrgn.setPerson_address1(request.getParameter("person_address1"));
                createOrgn.setPerson_address2(request.getParameter("person_address2"));
                createOrgn.setPerson_mobile1(request.getParameter("person_mobile1"));
                createOrgn.setPerson_mobile2(request.getParameter("person_mobile2"));
                createOrgn.setPerson_mail_id1(request.getParameter("person_mail_id1"));
                createOrgn.setPerson_mail_id2(request.getParameter("person_mail_id2"));
                createOrgn.setPerson_landLine1(request.getParameter("person_landLine1"));
                createOrgn.setPerson_landLine2(request.getParameter("person_landLine2"));                        /// check the Organisation exist or not
                if (subTask.equals("SaveAllRecord")) {
                    if (createOrganModel.isOrganisationExist(organisation)) {                            // insert the data in the various table
                        if (createOrganModel.createOrganisation(createOrgn)) {
                            jQueryMessage = "green" + "&#;" + createOrganModel.getMessage();
                        } else {
                            request.setAttribute("isSearch", "Yes");
                            jQueryMessage = "red" + "&#;" + createOrganModel.getMessage();
                        }
                        request.setAttribute("orgDetail", createOrgn);
                    } else {
                        jQueryMessage = "red" + "&#;" + createOrganModel.getMessage();
                    }
                } else if (subTask.equals("saveOrgMap")) {
                    if (createOrgn.getOrg_map_id() == 0) {
                        createOrganModel.insertRecordOrgMap(createOrgn);
                    } else {
                        createOrganModel.updateRecordOrgMap(createOrgn);
                    }
                    jQueryMessage = createOrganModel.getMsgBgColor() + "&#;" + createOrganModel.getMessage();
                } else if (subTask.equals("saveOrgOffice")) {
                    if (createOrgn.getOffice_id() == 0) {
                        createOrganModel.insertRecordOffice(createOrgn);
                        isUpdate = "Yes";
                    } else {
                        createOrganModel.updateRecordOffice(createOrgn);
                        isUpdate = "No";
                    }
                    jQueryMessage = createOrgn.getOffice_name() + "&#;" + isUpdate + "&#;" + createOrganModel.getMsgBgColor() + "&#;" + createOrganModel.getMessage();
                } else if (subTask.equals("savePerson")) {
                    if (createOrgn.getKeyPersonId() == 0) {
                        createOrganModel.insertRecordPerson(createOrgn);
                        isUpdate = "Yes";
                    } else {
                        createOrganModel.updateRecordPerson(createOrgn);
                        isUpdate = "No";
                    }
                    jQueryMessage = createOrgn.getKeyperson() + "&#;" + isUpdate + "&#;" + createOrganModel.getMsgBgColor() + "&#;" + createOrganModel.getMessage();
                } else if (subTask.equals("deleteOrg")) {
                    jQueryMessage = createOrgn.getKeyperson() + "&#;" + createOrganModel.deleteRecordOrg(createOrgn.getOrganisation_id()) + "&#;" + createOrganModel.getMsgBgColor() + "&#;" + createOrganModel.getMessage();
                } else if (subTask.equals("deleteOrgOffice")) {
                    jQueryMessage = createOrgn.getKeyperson() + "&#;" + createOrganModel.deleteRecordOffice(createOrgn.getOffice_id()) + "&#;" + createOrganModel.getMsgBgColor() + "&#;" + createOrganModel.getMessage();
                } else if (subTask.equals("deletePerson")) {
                    jQueryMessage = createOrgn.getKeyperson() + "&#;" + createOrganModel.deleteRecordPerson(createOrgn.getKeyPersonId()) + "&#;" + createOrganModel.getMsgBgColor() + "&#;" + createOrganModel.getMessage();
                }
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.print(jQueryMessage);
                return;
            } else if (task.equals("Search")) {
                searchOrganisation = request.getParameter("searchOrganisation");
                if (searchOrganisation != null && !searchOrganisation.isEmpty()) {
                    request.setAttribute("isSearch", "Yes");
                    request.setAttribute("orgDetail", createOrganModel.showData(searchOrganisation));
                }
            }
        }
        request.setAttribute("searchOrganisation", searchOrganisation);
        request.setAttribute("message", createOrganModel.getMessage());
        request.setAttribute("messageBgColor", createOrganModel.getMsgBgColor());
        createOrganModel.closeConnection();
        request.getRequestDispatcher("orgDetailEntryView").forward(request, response);

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }
}
