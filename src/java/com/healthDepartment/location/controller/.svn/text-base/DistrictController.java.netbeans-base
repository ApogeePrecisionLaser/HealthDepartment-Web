package com.healthDepartment.location.controller;

import com.healthDepartment.location.model.DistrictModel;
import com.healthDepartment.location.tableClasses.DistrictBean;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DistrictController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
    {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 15, noOfRowsInTable;
         
        System.out.println("Starting application");
        response.setContentType("text/html");
        ServletContext ctx = getServletContext();
        
        DistrictModel districtModel = new DistrictModel();
        districtModel.setDriver(ctx.getInitParameter("driverClass"));
        districtModel.setUrl(ctx.getInitParameter("connectionString"));
        districtModel.setUser(ctx.getInitParameter("db_user_name"));
        districtModel.setPassword(ctx.getInitParameter("db_user_password"));
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        districtModel.setConnection();
         System.out.println(districtModel.getConnection());
         String task = request.getParameter("task");
         
         String searchDistrict= request.getParameter("searchDistrict");         

        try {                      
            String jQstring1 = request.getParameter("action1");           
            String q = request.getParameter("q");   // field own input   
            if(jQstring1!=null)
            {
              PrintWriter out = response.getWriter();
              List<String> list = null;
              if (jQstring1.equals("getDistrict")) 
                    list = districtModel.getDistrict(q);
              else if(jQstring1.equals("getState"))
                    list = districtModel.getState(q);
              else if (jQstring1.equals("getUt"))
                    list = districtModel.getUt(q);
              else if (jQstring1.equals("getDivision"))
                    {
                        String stateName = request.getParameter("stateName");
                        String utName = request.getParameter("utName");
                        list = districtModel.getDivision(q,stateName,utName);
                    }
              else if(jQstring1.equals("getDist"))
                    {                        
                        String div = request.getParameter("divisionName");
                        list = districtModel.getDist(q,div);
                    }
              Iterator<String> iter = list.iterator();
              while (iter.hasNext()) {
                String data = iter.next();
                out.println(data);
              }
              districtModel.closeConnection();
              return;        
            }
        }catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }      
        
         if(searchDistrict==null)
             searchDistrict="";

         if(task==null)
            task="";
        else if(task.equals("generateMapReport"))
         {
            List listAll = null;
            String jrxmlFilePath;
            String search= request.getParameter("searchDistrict");
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll=districtModel.showAllData(search);           
            jrxmlFilePath = ctx.getRealPath("/report/location/DistrictReport.jrxml");
            byte[] reportInbytes = districtModel.generateMapReport(jrxmlFilePath,listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
         }else
             if(task.equals("generateXlsMapReport"))
             {
                  String jrxmlFilePath;
              List listAll=null;
               String search= request.getParameter("searchDistrict");
                        response.setContentType("application/vnd.ms-excel");
                        response.addHeader("Content-Disposition", "attachment; filename=District.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                       jrxmlFilePath = ctx.getRealPath("/report/location/DistrictReport.jrxml");
                      listAll=districtModel.showAllData(search);
                     ByteArrayOutputStream reportInbytes =districtModel.generateDistrictXlsRecordList(jrxmlFilePath, listAll);
                    response.setContentLength(reportInbytes.size());
                     servletOutputStream.write(reportInbytes.toByteArray());
                     servletOutputStream.flush();
                     servletOutputStream.close();
                     return;
             }
        else if(task.equals("Search All Records"))
             searchDistrict="";
        else if(task.equals("Save all records")||task.equals("Save As New")||task.equals("Save"))
         {
            if(task.equals("Save all records"))
                districtModel.insertRecord(request.getParameterValues("stateName"),request.getParameterValues("utName"),request.getParameterValues("divisionName"),request.getParameterValues("district_id"),request.getParameterValues("districtName"),request.getParameterValues("districtDescription"));
            else if(task.equals("Save As New"))
            {
                String district_id[]={"1"};
                districtModel.insertRecord(request.getParameterValues("stateName"),request.getParameterValues("utName"),request.getParameterValues("divisionName"),district_id,request.getParameterValues("districtName"),request.getParameterValues("districtDescription"));
            }else if(task.equals("Save"))
                districtModel.updateRecord(request.getParameter("stateName"),request.getParameter("utName"),request.getParameter("divisionName"),request.getParameter("districtId"),request.getParameter("districtName"),request.getParameter("districtDescription"));

         }
        else if(task.equals("Delete"))                              
             districtModel.deleteRecord(Integer.parseInt(request.getParameter("districtId")));
        
         noOfRowsInTable = districtModel.getTotalRowsInTable(searchDistrict);
        
            try {
                lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
                noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }      
           
            if (task.equals("Next")); // lowerLimit already has value such that it shows forward records, so do nothing here.
            else if (task.equals("Previous")) {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0) {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else {
                    lowerLimit = temp;
                }
            } else if (task.equals("First")) {
                lowerLimit = 0;
            } else if (task.equals("Last")) {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;               
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Add All Records")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
              }                
         ArrayList<DistrictBean> list = districtModel.getAllRecords(lowerLimit,noOfRowsToDisplay,searchDistrict);
         lowerLimit = lowerLimit + list.size();
         noOfRowsTraversed = list.size();

         if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
            request.setAttribute("showFirst", "false");
            request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            request.setAttribute("searchDistrict", searchDistrict);
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("message", districtModel.getMessage());
            request.setAttribute("messageBGColor", districtModel.getMessageBGColor());
            request.setAttribute("districtList", list);
            districtModel.closeConnection();
            
            request.getRequestDispatcher("districtView").forward(request, response);
            
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException
    {   
        doGet(request, response);
    }
}
