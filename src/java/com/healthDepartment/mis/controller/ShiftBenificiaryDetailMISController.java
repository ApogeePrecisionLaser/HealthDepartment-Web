/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.mis.model.ShiftBenificiaryDetailMISModel;
import com.healthDepartment.mis.tableClasses.ShiftBenificiaryDetailMIS;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shobha
 */
public class ShiftBenificiaryDetailMISController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
          ServletContext ctx = getServletContext();
        ShiftBenificiaryDetailMISModel slm=new ShiftBenificiaryDetailMISModel();
        String task=request.getParameter("task");
        if(task==null)
           task="";
         int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 8, noOfRowsInTable = 0;
         String searchdate="";

         searchdate=request.getParameter("search_date");
       // try {
        //    searchdate = new GeneralModel().convertToSqlDate(searchdate).toString();
       // } catch (Exception e) {
       //   System.out.print(e);
      //  }

        //  if(searchdate != null && !searchdate.isEmpty()){
         //   String[] date_array  = searchdate.split("-");
          //  searchdate = date_array[2] + "-" + date_array[1] + "-" + date_array[0];
          //  }
        try
        {

            slm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }
          String searchCityName= request.getParameter("searchCityName");
          String searchZoneName= request.getParameter("searchZone");
          String searchZoneNo= request.getParameter("searchZone_no");
          String searchWardType= request.getParameter("searchWardType");
          String searchCityNo= request.getParameter("searchCityNo");
          String searchWardNo= request.getParameter("searchWardNo");
          String searchArea= request.getParameter("searchArea");
          String searchAreaNo= request.getParameter("searchAreaNo");
          String searchPersonName= request.getParameter("searchPersonName");
          String searchemp= request.getParameter("searchemp");
          String mobileno= request.getParameter("mobileno");
          String occupationtype= request.getParameter("occupationtype");
           if(searchCityName==null)
            searchCityName="";
           if(searchCityNo==null)
            searchCityNo="";
           if(searchZoneNo==null)
             searchZoneNo="";
          if(searchZoneName==null)
          searchZoneName="";
          if(searchCityNo==null)
            searchCityNo="";
          if(searchWardNo==null)
            searchWardNo="";
          if(searchAreaNo==null)
            searchAreaNo="";
          if(searchArea==null)
              searchArea="";
         if(searchWardType==null)
          searchWardType="";
         if(searchPersonName== null)
          searchPersonName="";
          if(searchemp== null)
          searchemp="";
           if(mobileno== null)
          mobileno="";
        if(occupationtype== null)
          occupationtype="";
                      try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getOccupationtype"))
                {
                    list = slm.getOccupationtype(q);
                }
                else if(JQstring.equals("getmobileno"))
                {
                  list = slm.getmobileno(q);
                }
                 else if(JQstring.equals("getsearchemp"))
                {
                 list = slm.getsearchemp(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }

                return;
            }
        } catch (Exception e) {
            System.out.println(e);
        }


          if(task.equals("shiftBenificiaryMISInfo")){

               searchPersonName=request.getParameter("person_name");
                searchdate=request.getParameter("date");

          }
           if (task.equals("generateHSReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        response.setCharacterEncoding("UTF-8");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/shift/shiftDetail.jrxml");
                        list = slm.showData(-1, noOfRowsToDisplay, searchdate,searchCityName,searchZoneName,searchWardType,searchArea,searchemp,mobileno,occupationtype);
                        byte[] reportInbytes =slm.generateRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        return;
                     }
           if (task.equals("generateReport"))
                {
                     String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment; filename=shiftDetail.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/Report/shift/ShiftDetail.jrxml");
                        list = slm.showData(-1, noOfRowsToDisplay, searchdate,searchCityName,searchZoneName,searchWardType,searchArea,searchemp,mobileno,occupationtype);
                       ByteArrayOutputStream reportInbytes =slm.generateXlsRecordList(jrxmlFilePath,list);
                       response.setContentLength(reportInbytes.toByteArray().length);
                       servletOutputStream.write(reportInbytes.toByteArray() , 0, reportInbytes.toByteArray().length);
                       servletOutputStream.flush();
                       servletOutputStream.close();

                        return;
                 }
     if (task.equals("showMapWindow")) {
            String longi = request.getParameter("logitude");
            String latti = request.getParameter("lattitude");
            List<ShiftBenificiaryDetailMIS> List = slm.showDataBean(longi);
            request.setAttribute("CoordinatesList", List);
            request.getRequestDispatcher("/view/MapView/autoMapWindow.jsp").forward(request, response);
            return;
        }

        try {
               String buttonAction = request.getParameter("buttonAction");
             if(buttonAction == null)
                 buttonAction = "none";
                  try {
        lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
        noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
             if(searchdate==null)
             searchdate="";
        noOfRowsInTable = slm.getNoOfRows(searchdate,searchCityName,searchZoneName,searchWardType,searchArea,searchemp,mobileno,occupationtype);

      if (buttonAction.equals("Next"));
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

        if (task.equals("Show All Records")) {
            searchdate = "";
           searchWardType = "";
           searchWardNo="";
           searchCityName = "";
           searchCityNo  = "";
           searchArea = "";
           searchAreaNo="";
           searchZoneName = "";
           searchZoneNo="";
          searchemp="";mobileno="";occupationtype="";
            searchPersonName="";
        }

         List<ShiftBenificiaryDetailMIS> List = slm.showData(lowerLimit,noOfRowsToDisplay,searchdate,searchCityName,searchZoneName,searchWardType,searchArea,searchemp,mobileno,occupationtype);
        lowerLimit = lowerLimit + List.size();
        noOfRowsTraversed = List.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

         request.setAttribute("lowerLimit", lowerLimit);
         request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
         request.setAttribute("search_date", searchdate);
         request.setAttribute("list", List);
         request.setAttribute("searchCityName", searchCityName);
         request.setAttribute("searchCityNo", searchCityNo);
         request.setAttribute("searchZone", searchZoneName);
         request.setAttribute("searchZone_no", searchZoneNo);
         request.setAttribute("searchWardNo", searchWardNo);
         request.setAttribute("searchAreaNo", searchAreaNo);
         request.setAttribute("searchWardType", searchWardType);
         request.setAttribute("searchArea", searchArea);
         request.setAttribute("searchPersonName", searchPersonName);
         request.setAttribute("searchArea", searchArea);
         request.setAttribute("searchemp", searchemp);
         request.setAttribute("mobileno", mobileno);
         request.setAttribute("occupationtype", occupationtype);
         request.setAttribute("IDGenerator", new UniqueIDGenerator());
            RequestDispatcher rd=request.getRequestDispatcher("shiftbenificiaryMIS");
            rd.forward(request, response);
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
