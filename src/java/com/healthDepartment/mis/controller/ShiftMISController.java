/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.controller;


import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.mis.model.ShiftMISModel;
import com.healthDepartment.mis.tableClasses.ShiftMIS;

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
public class ShiftMISController extends HttpServlet {
   
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
          response.setCharacterEncoding("UTF-8");
          request.setCharacterEncoding("UTF-8");
        String task=request.getParameter("task");
        String search_shift_type = "";
        String search_location = "";
        String search_designation = "";
        ShiftMISModel sdlm=new ShiftMISModel();
        ServletContext ctx = getServletContext();
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 9, noOfRowsInTable = 0;
          try
        {

            sdlm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }
           String location_type="",zone="",ward="",area="",location="",zone_no="",ward_no="",area_no="",location_no="";
            try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;

                 if (JQstring.equals("getZone"))
                 {
              if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                 zone_no = request.getParameter("action2");

                 list = ShiftMISModel.getZone(q,zone_no);
                }
                 else if (JQstring.equals("getWard"))
                 {
                       if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                       zone = request.getParameter("action2");

                       if (request.getParameter("action3") != null && !request.getParameter("action3").isEmpty())
                       ward_no = request.getParameter("action3");

                    list = ShiftMISModel.getWard(q,zone,ward_no);
                 }
                 else if (JQstring.equals("getArea"))
                 {
                  if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                   ward = request.getParameter("action2");

                  if (request.getParameter("action3") != null && !request.getParameter("action3").isEmpty())
                  zone = request.getParameter("action3");
                     if (request.getParameter("action4") != null && !request.getParameter("action4").isEmpty())
                     area_no = request.getParameter("action4");

                    list = ShiftMISModel.getArea(q,ward,zone,area_no);
                 }
                  else if (JQstring.equals("getlocation"))
                 {
                  if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                  area = request.getParameter("action2");

                  if (request.getParameter("action3") != null && !request.getParameter("action3").isEmpty())
                  ward = request.getParameter("action3");

                  if (request.getParameter("action4") != null && !request.getParameter("action4").isEmpty())
                  zone = request.getParameter("action4");

                  if (request.getParameter("action5") != null && !request.getParameter("action5").isEmpty())
                  location_no = request.getParameter("action5");

                  list = ShiftMISModel.getlocation(q,area,ward,zone,location_no);
                 }

                else if(JQstring.equals("getDesignation"))
                {
                   list = ShiftMISModel.getDesignation(q);
                }
                else if(JQstring.equals("getShiftType"))
                {
                   list = ShiftMISModel.getShiftType(q);
                }
               else if(JQstring.equals("getZoneNo"))
               {
                     if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                    zone = request.getParameter("action2");

                     list = ShiftMISModel.getZoneNo(q,zone);
                }
              else if(JQstring.equals("getWardNo"))
                {
                      if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                      ward = request.getParameter("action2");

                   list = ShiftMISModel.getWardNo(q,ward);
                }
                    else if(JQstring.equals("getAreaNo"))
                {
                      if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                      area = request.getParameter("action2");

                   list = ShiftMISModel.getAreaNo(q,area);
                }
                     else if(JQstring.equals("getLocationNo"))
                {
                      if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                      location = request.getParameter("action2");

                   list = ShiftMISModel.getLocationNo(q,location);
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
         search_shift_type = request.getParameter("search_shift_type");
         search_location = request.getParameter("search_location");
         search_designation = request.getParameter("search_designation");
         String searchCityName= request.getParameter("searchCityName");
          String searchZoneName= request.getParameter("searchZone");
          String searchZoneNo= request.getParameter("searchZone_no");
          String searchWardType= request.getParameter("searchWardType");
          String searchCityNo= request.getParameter("searchCityNo");
          String searchWardNo= request.getParameter("searchWardNo");
          String searchArea= request.getParameter("searchArea");
          String searchAreaNo= request.getParameter("searchAreaNo");
          String searchlocation_type= request.getParameter("searchlocation_type");
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
           if(searchlocation_type==null)
              searchlocation_type="";
           if ( search_shift_type == null)
                search_shift_type = "";
           if ( search_location == null)
                search_location = "";
           if ( search_designation == null)
                search_designation = "";
           if(task==null||task.isEmpty())
              task="";
            if (task.equals("generateHSReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        response.setCharacterEncoding("UTF-8");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/shift/shiftDesignationLocationMap.jrxml");
                        list =  ShiftMISModel.showData(-1, -1,searchlocation_type,search_shift_type, search_designation,searchCityName,searchZoneName,searchWardType,searchArea);
                        byte[] reportInbytes =sdlm.generateRecordList(jrxmlFilePath,list);
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
                        response.setHeader("Content-Disposition", "attachment; filename=shiftDesignationLocation.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/Report/shift/shiftDesignationLocationMap.jrxml");
                        list =  ShiftMISModel.showData(-1, -1,searchlocation_type,search_shift_type, search_designation,searchCityName,searchZoneName,searchWardType,searchArea);
                       ByteArrayOutputStream reportInbytes =sdlm.generateXlsRecordList(jrxmlFilePath,list);
                       response.setContentLength(reportInbytes.toByteArray().length);
                       servletOutputStream.write(reportInbytes.toByteArray() , 0, reportInbytes.toByteArray().length);
                       servletOutputStream.flush();
                       servletOutputStream.close();
                        return;
                   }


        try{

        if(task.equals("Delete")){
               sdlm.deleteRecord(request.getParameter("map_id1"), request.getParameter("map_id2"));
        }else if(task.equals("save") || task.equals("Save AS New") || task.equals("Revised")){
             int map_id1=0;
                 try{
                     map_id1 = Integer.parseInt(request.getParameter("map_id1").trim());
                 }catch(Exception ex){
                     map_id1 = 0;
                 }
             int map_id2=0;
                 try{
                     map_id2 = Integer.parseInt(request.getParameter("map_id2").trim());
                 }catch(Exception ex){
                     map_id2 = 0;
                 }
             int no_of_person=0;
                 try{
                     no_of_person = Integer.parseInt(request.getParameter("no_of_person").trim());
                 }catch(Exception ex){
                     no_of_person = 0;
                 }
                 if(task.equals("Save AS New"))
                     map_id1 = 0;


              String shift_type=request.getParameter("shift_type");
                String remark=request.getParameter("remark");
              location=request.getParameter("location");
              String designation=request.getParameter("designation");
              location_type=request.getParameter("location_type");
              zone=request.getParameter("zone");
              ward=request.getParameter("ward");
              area=request.getParameter("area");
              ShiftMIS sdlb=new ShiftMIS();
              sdlb.setMap_id1(map_id1);
              sdlb.setMap_id2(map_id2);
              sdlb.setShift_type(shift_type);
              sdlb.setLocation_type(location_type);
              sdlb.setZone(zone);
              sdlb.setWard(ward);
              sdlb.setArea(area);
              sdlb.setLocation(location);
              sdlb.setDesignation(designation);
              sdlb.setNo_of_person(no_of_person);
              sdlb.setRemark(remark);
              if(sdlm.CheckMapId(sdlb, task,location_type,zone,ward,area,location))
             {
                  if(task.equals("save"))
                      sdlm.insertRecord(sdlb);

               if(task.equals("Revised"))
                   sdlm.revision(sdlb);
              }
              if(task.equals("Save AS New"))
           {
                sdlm.insertRecord(sdlb);
           }
        }
               String buttonAction = request.getParameter("buttonAction");
             if(buttonAction == null)
                 buttonAction = "none";

              try {
        lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
        noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e) {
                lowerLimit = noOfRowsTraversed = 0;
            }
        if (task.equals("Show All Records")) {
            search_shift_type = "";
            search_location = "";
            search_designation = "";
            searchWardType = "";
            searchWardNo="";
            searchCityName = "";
            searchCityNo  = "";
            searchArea = "";
           searchAreaNo="";
            searchZoneName = "";
            searchZoneNo="";
        }
               noOfRowsInTable = ShiftMISModel.getNoOfRows(searchlocation_type,search_shift_type, search_designation,searchCityName,searchZoneName,searchWardType,searchArea);

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
       if (task.equals("save") || task.equals("Save AS New") || task.equals("Revised") || task.equals("Delete") ) {
            lowerLimit = lowerLimit - noOfRowsTraversed;
        }
         List<ShiftMIS>  list=  ShiftMISModel.showData(lowerLimit, noOfRowsToDisplay,searchlocation_type,search_shift_type, search_designation,searchCityName,searchZoneName,searchWardType,searchArea);

          lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();
            if ((lowerLimit - noOfRowsTraversed) == 0) {
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
         request.setAttribute("list", list);
         request.setAttribute("lowerLimit",lowerLimit);
         request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        request.setAttribute("message", sdlm.getMessage());
         request.setAttribute("search_shift_type", search_shift_type);
        request.setAttribute("search_designation", search_designation);
        request.setAttribute("searchCityName", searchCityName);
         request.setAttribute("searchCityNo", searchCityNo);
         request.setAttribute("searchZone", searchZoneName);
         request.setAttribute("searchZone_no", searchZoneNo);
         request.setAttribute("searchWardNo", searchWardNo);
         request.setAttribute("searchAreaNo", searchAreaNo);
        request.setAttribute("searchWardType", searchWardType);
        request.setAttribute("searchArea", searchArea);
        request.setAttribute("searchlocation_type", searchlocation_type);
          RequestDispatcher rd=request.getRequestDispatcher("shiftmis");
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
