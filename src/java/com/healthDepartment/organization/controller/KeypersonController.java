/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;

import com.healthDepartment.organization.model.KeypersonModel;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.general.model.GeneralModel;
import com.healthDepartment.organization.tableClasses.KeyPerson;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author SoftTech
 */
public class KeypersonController extends HttpServlet {

    private File tmpDir;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        Map<String, String> map = new HashMap<String, String>();
        ServletContext ctx = getServletContext();
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        KeypersonModel keyModel = new KeypersonModel();
       // KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
        try {
            keyModel.setDriverClass(ctx.getInitParameter("driverClass"));
            keyModel.setDb_username(ctx.getInitParameter("db_user_name"));
            keyModel.setDb_password(ctx.getInitParameter("db_user_password"));
            keyModel.setConnectionString(ctx.getInitParameter("connectionString"));
            keyModel.setConnection();

        } catch (Exception e) {
            System.out.println("error in KeypersonController setConnection() calling try block" + e);
        }
        GeneralModel generalModel = new GeneralModel();
        //generalModel.sendSmsToAssignedFor("9760957577", generalModel.calstr("अमित"));//"अमित");//0905092e093f0924
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
                    if (JQstring.equals("getStateName")) {
                        list = keyModel.getStateName(q);
                    } else if (JQstring.equals("getCityName")) {
                        list = keyModel.getCityName(q);//, request.getParameter("action2")
                    } else if (JQstring.equals("getOfficeType")) {
                        list = keyModel.getOffice_type(q);
                    } else if (JQstring.equals("getsearchOrganisation")) {
                        list = keyModel.getsearchOrganisation(q);
                    } else if (JQstring.equals("getSearchEmpCode")) {
                        list = keyModel.getSearchEmpCode(q);
                    } else if (JQstring.equals("getSearchKeyPerson")) {
                        list = keyModel.getOrgPersonNameList(q);
                    } else if (JQstring.equals("getSearchDesignation")) {
                        list = keyModel.getDesignation(q);
                    } else if (JQstring.equals("getOrgOfficeName")) {
                        list = keyModel.getOrgOfficeName(q, request.getParameter("action2"));
                    } else if (JQstring.equals("getDesignation")) {
                        list = keyModel.getDesignation(q);
                    } else if (JQstring.equals("getOrgOfficeCode")) {
                         list = keyModel.getOrgOfficeCode(q);
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
                    keyModel.closeConnection();
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --SiteListController get JQuery Parameters Part-" + e);
            }


            List items = null;
            Iterator itr = null;
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
            fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
            fileItemFactory.setRepository(tmpDir);
            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
            try {
                items = uploadHandler.parseRequest(request);
                itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                        map.put(item.getFieldName(),item.getString( "UTF-8"));
                    } else {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                        if (item.getName() == null || item.getName().isEmpty()) {
                            map.put(item.getFieldName(), "");
                        } else {
                            String image_name = item.getName();
                            image_name = image_name.substring(0, image_name.length());
                            System.out.println(image_name);
                            map.put(item.getFieldName(), item.getName());
                        }
                    }
                }
                itr = null;
                itr = items.iterator();
            } catch (Exception ex) {
                System.out.println("Error encountered while uploading file" + ex);
            }
            String task1 = request.getParameter("task1");
            if (task1 == null) {
                task1 = "";
            }
            String task = map.get("task");
            if (task == null) {
                task = "";
            }
            String searchOrganisation = null, searchKeyPerson = null, searchOfficeCode = null, searchEmpCode = null, searchDesignation = null;
            searchKeyPerson = request.getParameter("searchKeyPerson");
            searchOrganisation = request.getParameter("searchOrganisation");
            searchOfficeCode = request.getParameter("searchOfficeCode");
            searchEmpCode = request.getParameter("searchEmpCode");
            searchDesignation = request.getParameter("searchDesignation");
            try {
                if (searchOrganisation == null) {
                    searchOrganisation = "";
                }
                if (searchKeyPerson == null) {
                    searchKeyPerson = "";
                }
                if (searchOfficeCode == null) {
                    searchOfficeCode = "";
                }
                if (searchEmpCode == null) {
                    searchEmpCode = "";
                }
                if (searchDesignation == null) {
                    searchDesignation = "";
                }

            } catch (Exception e) {
            }
            if (task1.equals("viewImage")) {
                try {
                    // String jrxmlFilePath;
                    // String img_name = "PRHindi_" ;
                    //String repository_name = "C:/ssadvt_repository/SOR";
                    //int estimate_no = Integer.parseInt(request.getParameter("estimate_no"));
                    //int estimate_revision_no = Integer.parseInt(request.getParameter("estimate_revision_no"));
                    String destinationPath = "";
                    String kp_id = request.getParameter("kp_id");
                    String type = request.getParameter("type");
                    if(kp_id != null && !kp_id.isEmpty()){
                        destinationPath = keyModel.getImagePath(kp_id, type);
                        if(destinationPath.isEmpty())
                            destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                    } else{
                        System.out.println("Image Not Found");
                        destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                    }
                    //destinationPath = keyModel.getImagePath(emp_code);
                    File f = new File(destinationPath);
                    FileInputStream fis = null;
                    if(!f.exists()){
                        destinationPath = "C:\\ssadvt_repository\\health_department\\general\\no_image.png";
                        f = new File(destinationPath);
                    }
                    fis = new FileInputStream(f);
                    if(destinationPath.contains("pdf"))
                        response.setContentType("pdf");
                    else
                        response.setContentType("image/jpeg");

                    //  response.addHeader("Content-Disposition", "attachment; filename=\"" + f.getName() + "\"");
                    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
                    // BufferedImage bi=ImageIO.read(f);
                    response.setContentLength(fis.available());
                    ServletOutputStream os = response.getOutputStream();
                    BufferedOutputStream out = new BufferedOutputStream(os);
                    int ch = 0;
                    ;
                    while ((ch = bis.read()) != -1) {
                        out.write(ch);
                    }

                    bis.close();
                    fis.close();
                    out.close();
                    os.close();
                    response.flushBuffer();

                    keyModel.closeConnection();
                    return;
                } catch (Exception e) {
                    System.out.println("SelectSupplierController Demand Note Error :" + e);
                    return;
                }
            }

                   if (task1.equals("PRINTRecordList")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        response.setCharacterEncoding("UTF-8");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/Report/organization/KeyPerson.jrxml");
                        list=keyModel.showAll(-1,noOfRowsToDisplay,searchKeyPerson,searchOfficeCode, searchEmpCode,searchDesignation);
                        byte[] reportInbytes =keyModel.generateKeyperSonList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        return;
                     }
                 else if (task1 != null && task1.equals("PRINTExcelList")) {

                     String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment; filename=KeyPerson.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/Report/organization/KeyPerson.jrxml");
                       list=keyModel.showAll(-1,noOfRowsToDisplay,searchKeyPerson,searchOfficeCode, searchEmpCode,searchDesignation);
                        ByteArrayOutputStream reportInbytes =keyModel.generateXlsRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.toByteArray().length);

                       servletOutputStream.write(reportInbytes.toByteArray() , 0, reportInbytes.toByteArray().length);
                       servletOutputStream.flush();
                       servletOutputStream.close();

                        return;
}
            if (task.equals("Delete")) {
                //String destination = "E:/Traffic/ImageUpload/";
                keyModel.deleteRecord(Integer.parseInt(map.get("key_person_id")));  // Pretty sure that city_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New")) {
                int key_person_id;
                int general_image_details_id=0;
                try {
                    key_person_id = Integer.parseInt(map.get("key_person_id"));
                    general_image_details_id = Integer.parseInt(map.get("general_image_details_id"));// city_id may or may NOT be available i.e. it can be update or new record.
                } catch (Exception e) {
                    key_person_id = 0;
                    general_image_details_id=0;
                }
                if (task.equals("Save AS New")) {
                    key_person_id = 0;
                    general_image_details_id=0;
                }
                KeyPerson key = new KeyPerson();
                key.setKey_person_id(key_person_id);
                key.setSalutation(new String(map.get("salutation").trim().getBytes("iso-8859-1"), "UTF-8"));
                key.setKey_person_name(map.get("key_person_name").trim());
                key.setFather_name(map.get("father_name").trim());
                key.setAge(Integer.parseInt(map.get("age").trim().isEmpty()?"0":map.get("age").trim()));
                key.setDesignation(map.get("designation").trim());
                key.setDesignation_id(keyModel.getDesignation_id( map.get("designation").trim()));

                key.setOrg_office_id(keyModel.getOrgOffice_id(map.get("org_office_name").trim(), map.get("office_code").trim()));
                key.setCity_id(keyModel.getCity_id(map.get("city_name").trim()));
                key.setAddress_line1(map.get("address_line1").trim());
                key.setAddress_line2(map.get("address_line2").trim());
                key.setAddress_line3(map.get("address_line3").trim());
                key.setMobile_no1(map.get("mobile_no1").trim());
                key.setMobile_no2(map.get("mobile_no2").trim());
                key.setLandline_no1(map.get("landline_no1").trim());
                key.setLandline_no2(map.get("landline_no2").trim());
                key.setEmail_id1(map.get("email_id1").trim());
                key.setEmail_id2(map.get("email_id2").trim());
                key.setEmp_code(map.get("employeeId").trim());
                key.setImage_path(map.get("design_name"));
                key.setId_proof(map.get("id_proof"));
                key.setGeneral_image_details_id(general_image_details_id);
                //String destination = "E:/Traffic/ImageUpload/";
                String photo_destination = keyModel.getDestination_Path("key_person_photo");
                String iD_destination = keyModel.getDestination_Path("key_person_ID");
                response.setContentType("image/jpeg");

                if (key_person_id == 0) {
                    // if city_id was not provided, that means insert new record.

                    keyModel.insertRecord(key, itr, photo_destination, iD_destination);
                } else {
                    // update existing record.
                    keyModel.updateRecord(key, itr, photo_destination, iD_destination);
                }
            } else if (task1.equals("Show All Records")) {
                searchOrganisation = "";
                searchKeyPerson = "";
                searchOfficeCode = "";
                searchEmpCode = "";
                searchDesignation = "";
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
           noOfRowsInTable = keyModel.getNoOfRows(searchOrganisation, searchKeyPerson, searchOfficeCode, searchEmpCode, searchDesignation);
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
            List<KeyPerson> keyList = keyModel.showData(lowerLimit, noOfRowsToDisplay, searchOrganisation, searchKeyPerson, searchOfficeCode, searchEmpCode, searchDesignation);
            lowerLimit = lowerLimit + keyList.size();
            noOfRowsTraversed = keyList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("keyList", keyList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", keyModel.getMessage());
            request.setAttribute("msgBgColor", keyModel.getMsgBgColor());
            request.setAttribute("searchOfficeCode", searchOfficeCode);
            request.setAttribute("searchEmpCode", searchEmpCode);
            request.setAttribute("searchKeyPerson", searchKeyPerson);
            request.setAttribute("searchDesignation", searchDesignation);
            keyModel.closeConnection();
            if (isOrgBasicStep.equals("Yes")) {
                request.setAttribute("stepNo", 4);
                request.getRequestDispatcher("orgBasicEntryView").forward(request, response);
            } else {
                request.getRequestDispatcher("keyPerson_view").forward(request, response);
            }
        } catch (Exception ex) {
            System.out.println("KeypersonController error: " + ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
