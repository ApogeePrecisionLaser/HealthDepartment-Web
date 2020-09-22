/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.location.controller;

/**
 *
 * @author Administrator
 */
import com.healthDepartment.location.model.WardModel;
import com.healthDepartment.location.tableClasses.WardTypeBean;
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

/**
 *
 * @author JPSS
 */
public class WardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is FUSE Controller....");
        ServletContext ctx = getServletContext();
        WardModel wardTypeModel = new WardModel();
        wardTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        wardTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        wardTypeModel.setDb_username(ctx.getInitParameter("db_user_name"));
        wardTypeModel.setDb_password(ctx.getInitParameter("db_user_password"));
        wardTypeModel.setConnection();
         response.setContentType("text/html;charset=UTF-8");
       response.setCharacterEncoding("UTF-8");
       request.setCharacterEncoding("UTF-8");

        String task = request.getParameter("task");
        try {
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getWardType")) {
                    list = wardTypeModel.getWardType(q);
                } else if (JQstring.equals("getZoneName")) {
                    list = wardTypeModel.getZoneName(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                wardTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }
        if (task == null) {
            task = "";
        }
          String  searchWardType = request.getParameter("searchWardType");
           String  searchWardNo = request.getParameter("searchWardNo");
              String searchZone= request.getParameter("searchZone");
         String searchZone_no= request.getParameter("searchZone_no");
        try {
            if (searchWardType == null) {
                searchWardType = "";
            }
            if (searchWardNo == null) {
                searchWardNo = "";
            }
               if (searchZone == null) {
                searchZone = "";
            }
            if (searchZone_no == null) {
                searchZone_no = "";
            }
        } catch (Exception e) {
            System.out.println("Exception while searching in controller" + e);
        }
        if (task.equals("generateMapReport")) {
           
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = wardTypeModel.showAllData(searchWardType,searchZone);
            jrxmlFilePath = ctx.getRealPath("/report/location/ward.jrxml");
            byte[] reportInbytes = wardTypeModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        }
        if(task.equals("generateReport"))
             {
                  String jrxmlFilePath;
                  List listAll=null;
                  response.setContentType("application/vnd.ms-excel");
                  response.addHeader("Content-Disposition", "attachment; filename=ward.xls");
                  ServletOutputStream servletOutputStream = response.getOutputStream();
                   listAll = wardTypeModel.showAllData(searchWardType,searchZone);
                   jrxmlFilePath = ctx.getRealPath("/report/location/ward.jrxml");
                  ByteArrayOutputStream reportInbytes =wardTypeModel.generateZoneXlsRecordList(jrxmlFilePath, listAll);
                  response.setContentLength(reportInbytes.size());
                  servletOutputStream.write(reportInbytes.toByteArray());
                  servletOutputStream.flush();
                  servletOutputStream.close();
                  return;
             }
        if (task.equals("Delete")) {
            wardTypeModel.deleteRecord(Integer.parseInt(request.getParameter("ward_id_m")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int ward_id_m;
            try {
                ward_id_m = Integer.parseInt(request.getParameter("ward_id_m"));
            } catch (Exception e) {
                ward_id_m = 0;
            }
            if (task.equals("Save AS New") || (task.equals("Save")  )) {
                ward_id_m = 0;
            }
            WardTypeBean wardTypeBean = new WardTypeBean();
            wardTypeBean.setWard_id(ward_id_m);
            wardTypeBean.setZone_m(request.getParameter("zone_name_m"));
            wardTypeBean.setWard_name(request.getParameter("ward_name"));
            wardTypeBean.setWard_no(request.getParameter("ward_no"));
            wardTypeBean.setRemark(request.getParameter("remark"));
            if (ward_id_m == 0) {
                System.out.println("Inserting values by model......");
                wardTypeModel.insertRecord(wardTypeBean);
            }// else {
             //   System.out.println("Update values by model........");
             //   wardTypeModel.updateRecord(wardTypeBean);
          //  }
        }
        // Start of Auto Completer code
      

        // End of Auto Completer code
    
        // Start of Search Table
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
        System.out.println("searching.......... " + searchWardType);
        noOfRowsInTable = wardTypeModel.getNoOfRows(searchWardType,searchZone);

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
        } else if (task.equals("Show All Records")) {
            searchWardType = "";
            searchWardNo="";
        }
        // Logic to show data in the table.
        List<WardTypeBean> wardTypeList = wardTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchWardType,searchZone);
        lowerLimit = lowerLimit + wardTypeList.size();
        noOfRowsTraversed = wardTypeList.size();
        // Now set request scoped attributes, and then forward the request to view.
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("wardTypeList", wardTypeList);
        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        // End of Search Table
        System.out.println("color is :" + wardTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchWardType", searchWardType);
          request.setAttribute("searchWardNo", searchWardNo);
       request.setAttribute("searchZone", searchZone);
          request.setAttribute("searchZone_no", searchZone_no);
        request.setAttribute("message", wardTypeModel.getMessage());
        request.setAttribute("msgBgColor", wardTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/wardView").forward(request, response);



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        doGet(request, response);
    }
}
