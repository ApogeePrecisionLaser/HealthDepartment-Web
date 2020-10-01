/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.location.controller;

import com.healthDepartment.location.model.AreaTypeModel;
import com.healthDepartment.location.tableClasses.AreaTypeBean;
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
public class AreaTypeController  extends HttpServlet {
@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is Area TYPE Controller....");
        ServletContext ctx = getServletContext();
        AreaTypeModel areaTypeModel = new AreaTypeModel();
        areaTypeModel.setDriverClass(ctx.getInitParameter("driverClass"));
        areaTypeModel.setConnectionString(ctx.getInitParameter("connectionString"));
        areaTypeModel.setDb_username(ctx.getInitParameter("db_user_name"));
        areaTypeModel.setDb_password(ctx.getInitParameter("db_user_password"));
        areaTypeModel.setConnection();
      response.setContentType("text/html;charset=UTF-8");
       response.setCharacterEncoding("UTF-8");
       request.setCharacterEncoding("UTF-8");
      
       
        String task = request.getParameter("task");
        String searchArea = "", searchZone = "", searchWard = "",zone_name="",ward_name="";
        try {

            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getSearchArea")) {
                    list = areaTypeModel.getAreaName(q);
                }
               else if (JQstring.equals("getZoneName")) {
                    list = areaTypeModel.getZoneName(q);
                }else if (JQstring.equals("getWardName")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        zone_name = request.getParameter("action2");
                    }
                    list = areaTypeModel.getWardName(q, zone_name);
                } 
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                areaTypeModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("Error in Jquery function()::" + e);
        }
          searchArea = request.getParameter("searchArea");
          String  searchAreaNo = request.getParameter("searchAreaNo");
        if (searchArea == null) {
            searchArea = "";
        }
           if (searchAreaNo == null) {
            searchAreaNo = "";
        }
        searchWard = request.getParameter("searchWardType");
   String  searchWardNo = request.getParameter("searchWardNo");
        if (searchWard == null) {
            searchWard = "";
        }
    if (searchWardNo == null) {
            searchWardNo = "";
        }
        searchZone = request.getParameter("searchZone");
         String  searchZone_no = request.getParameter("searchZone_no");
        if (searchZone == null) {
            searchZone = "";
        }
         if (searchZone_no == null) {
            searchZone_no = "";
        }
        if (task == null) {
            task = "";
        }
       if(task.equals("generateMapReport")){
               // String wardType="";
                List listAll = null;
                String jrxmlFilePath;
                response.setContentType("application/pdf");
                ServletOutputStream servletOutputStream = response.getOutputStream();
                listAll=areaTypeModel.showAllData(searchArea,searchWard,searchZone);
                jrxmlFilePath = ctx.getRealPath("/report/location/area.jrxml");
                byte[] reportInbytes = areaTypeModel.generateMapReport(jrxmlFilePath,listAll);
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
                   listAll = areaTypeModel.showAllData(searchArea,searchWard,searchZone);
                   jrxmlFilePath = ctx.getRealPath("/report/location/area.jrxml");
                  ByteArrayOutputStream reportInbytes =areaTypeModel.generateAreaXlsRecordList(jrxmlFilePath, listAll);
                  response.setContentLength(reportInbytes.size());
                  servletOutputStream.write(reportInbytes.toByteArray());
                  servletOutputStream.flush();
                  servletOutputStream.close();
                  return;
             }
        if (task.equals("Delete")) {

            areaTypeModel.deleteRecord(Integer.parseInt(request.getParameter("area_id")));  // Pretty sure that city_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
              String zonename = request.getParameter("zone_name");
                String wardname = request.getParameter("ward_name");
               
                
                 request.setAttribute("zone", zonename);
                
            request.setAttribute("ward", wardname);
            int area_id;
            try {
                area_id = Integer.parseInt(request.getParameter("area_id"));
            } catch (Exception e) {
                area_id = 0;
            }
            if (task.equals("Save AS New")) {
                area_id = 0;
            }
            AreaTypeBean areaTypeBean = new AreaTypeBean();
            areaTypeBean.setArea_id(area_id);
             areaTypeBean.setZone_name(request.getParameter("zone_name"));
            areaTypeBean.setWard_name(request.getParameter("ward_name"));
         //   areaTypeBean.setWard_name(request.getParameter("ward_name"));
            areaTypeBean.setArea_name(request.getParameter("area_name"));
            areaTypeBean.setArea_no(request.getParameter("area_no"));
            areaTypeBean.setRemark(request.getParameter("remark"));

            if (area_id == 0) {
                System.out.println("Inserting values by AreaModel......");
                areaTypeModel.insertRecord(areaTypeBean);
            } else {
                System.out.println("Update values by AreaModel........");
                areaTypeModel.updateRecord(areaTypeBean);
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
        noOfRowsInTable = areaTypeModel.getNoOfRows(searchArea,searchWard,searchZone);//, searchWard, searchCity

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
        }else if (task.equals("Show All Records")) {
            searchArea = "";
            searchZone = "";
            searchWard = "";
            searchZone_no="";
            searchWardNo="";
            searchAreaNo="";
        }
        List<AreaTypeBean> areaTypeList = areaTypeModel.showData(lowerLimit, noOfRowsToDisplay, searchArea,searchWard,searchZone);//,, searchWard, searchCity
        lowerLimit = lowerLimit + areaTypeList.size();
        noOfRowsTraversed = areaTypeList.size();

        // forwarding parameters to jsp
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("areaTypeList", areaTypeList);

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
        }
        if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
            request.setAttribute("showNext", "false");
            request.setAttribute("showLast", "false");
        }
        //end  of search table

        System.out.println("color is :" + areaTypeModel.getMsgBgColor());
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("searchZone", searchZone);
         request.setAttribute("searchZone_no", searchZone_no);
         request.setAttribute("searchWardNo", searchWardNo);
         request.setAttribute("searchAreaNo", searchAreaNo);
        request.setAttribute("searchWardType", searchWard);
        request.setAttribute("searchArea", searchArea);
        request.setAttribute("searchArea", searchArea);
        request.setAttribute("message", areaTypeModel.getMessage());
        request.setAttribute("msgBgColor", areaTypeModel.getMsgBgColor());
        request.getRequestDispatcher("/area_TypeView").forward(request, response);
   }
@Override
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                doGet(request, response);
    }
}
