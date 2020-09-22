/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.mis.controller;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.mis.model.ShiftKeyPersonMISModel;
import com.healthDepartment.mis.tableClasses.ShiftKeyPersonMIS;
import com.healthDepartment.shift.model.ShiftKeyPersonMapModel;
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
public class ShiftKeyPersonMISController extends HttpServlet {
   
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
        ServletContext ctx = getServletContext();
         String task=request.getParameter("task");
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 12, noOfRowsInTable = 0;
         ShiftKeyPersonMISModel sdlm=new ShiftKeyPersonMISModel();
          try
        {
            sdlm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }

          String  search_shift_type = request.getParameter("search_shift_type");
          String search_location = request.getParameter("search_location");
          String search_designation = request.getParameter("search_designation");
          String searchCityName= request.getParameter("searchCityName");
          String searchZoneName= request.getParameter("searchZone");
          String searchZoneNo= request.getParameter("searchZone_no");
          String searchWardType= request.getParameter("searchWardType");
          String searchCityNo= request.getParameter("searchCityNo");
          String searchWardNo= request.getParameter("searchWardNo");
          String searchArea= request.getParameter("searchArea");
          String searchAreaNo= request.getParameter("searchAreaNo");
          String searchlocation_type= request.getParameter("searchlocation_type");
          String searchperson= request.getParameter("searchperson");
            String emp_code= request.getParameter("emp_code");
          String searchdate= request.getParameter("search_date");
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
          if(search_shift_type == null)
                search_shift_type = "";
          if(search_location == null)
                search_location = "";
          if(search_designation == null)
                search_designation = "";
           if(searchperson == null)
                searchperson = "";
                if(searchdate == null)
                searchdate = "";
                if(emp_code == null)
                emp_code = "";
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

                 list = sdlm.getZone(q,zone_no);
                }
                 else if (JQstring.equals("getWard"))
                 {
                       if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                       zone = request.getParameter("action2");

                       if (request.getParameter("action3") != null && !request.getParameter("action3").isEmpty())
                       ward_no = request.getParameter("action3");

                    list = sdlm.getWard(q,zone,ward_no);
                 }
                 else if (JQstring.equals("getArea"))
                 {
                  if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                   ward = request.getParameter("action2");

                  if (request.getParameter("action3") != null && !request.getParameter("action3").isEmpty())
                  zone = request.getParameter("action3");
                     if (request.getParameter("action4") != null && !request.getParameter("action4").isEmpty())
                     area_no = request.getParameter("action4");

                    list = sdlm.getArea(q,ward,zone,area_no);
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

                  list = sdlm.getlocation(q,area,ward,zone,location_no);
                 }
                               else if(JQstring.equals("getZoneNo"))
               {
                     if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                    zone = request.getParameter("action2");

                     list = sdlm.getZoneNo(q,zone);
                }
              else if(JQstring.equals("getWardNo"))
                {
                      if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                      ward = request.getParameter("action2");

                   list = sdlm.getWardNo(q,ward);
                }
                    else if(JQstring.equals("getAreaNo"))
                {
                      if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                      area = request.getParameter("action2");

                   list = sdlm.getAreaNo(q,area);
                }
                     else if(JQstring.equals("getLocationNo"))
                {
                      if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                      location = request.getParameter("action2");

                   list = sdlm.getLocationNo(q,location);
                }
                else  if (JQstring.equals("getDesignation"))
                {
                   list = ShiftKeyPersonMISModel.getDesignation(q);
                }
                else if(JQstring.equals("getShiftType"))
                {
                   list = ShiftKeyPersonMISModel.getShiftType(q);
                }
                 else if(JQstring.equals("getPerson"))
                {
                     String designation = request.getParameter("action2");
                     if(designation == null)
                         designation = "";
                        String person_code = request.getParameter("action3");
                     if(person_code == null)
                         person_code = "";
                   list = ShiftKeyPersonMISModel.getPerson(q, designation,person_code);
                }
                    else if(JQstring.equals("getperson_code"))
                {
                     String designation = request.getParameter("action2");
                     if(designation == null)
                         designation = "";
                        String person_name = request.getParameter("action3");
                     if(person_name == null)
                         person_name = "";
                   list = ShiftKeyPersonMISModel.getperson_code(q, designation,person_name);
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }

                return;
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }



    if(task==null||task.isEmpty())
               {
                task="";
               }
//  String info_designation="";
//  String info_location_type="";
//  String info_zone_name="";
//  String info_ward_name="";
//  String info_area_name="";
  if(task.equals("showInfo")){
     search_designation= request.getParameter("designation");
     searchlocation_type= request.getParameter("location_type");
      searchZoneName=request.getParameter("zone_name");
      searchWardType=request.getParameter("ward_name");
      searchArea=request.getParameter("area_name");


        }
             if (task.equals("generateHSReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        response.setCharacterEncoding("UTF-8");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/shift/shiftKeyPersonMap.jrxml");
                        list =  ShiftKeyPersonMISModel.showData(-1, -1,searchlocation_type,search_shift_type, search_designation,searchCityName,searchZoneName,searchWardType,searchArea,searchperson,searchdate,emp_code);
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
                        response.setHeader("Content-Disposition", "attachment; filename=shiftKeyPersonMap.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/Report/shift/shiftKeyPersonMap.jrxml");
                        list =  ShiftKeyPersonMapModel.showData(-1, -1,searchlocation_type,search_shift_type, search_designation,searchCityName,searchZoneName,searchWardType,searchArea,searchperson,searchdate,emp_code);
                       ByteArrayOutputStream reportInbytes =sdlm.generateXlsRecordList(jrxmlFilePath,list);
                       response.setContentLength(reportInbytes.toByteArray().length);
                       servletOutputStream.write(reportInbytes.toByteArray() , 0, reportInbytes.toByteArray().length);
                       servletOutputStream.flush();
                       servletOutputStream.close();
                        return;
}
        try {
            if(task.equals("Delete"))
            {
                sdlm.deleteRecord(request.getParameter("shift_key_person_map_id"));
            }
            else if(task.equals("save") || task.equals("Save AS New")){
                 int shift_key_person_map_id=0;
                 try{
                     shift_key_person_map_id = Integer.parseInt(request.getParameter("shift_key_person_map_id").trim());
                 }catch(Exception ex){
                     shift_key_person_map_id = 0;
                 }
                 if(task.equals("Save AS New"))
                     shift_key_person_map_id = 0;

             String shift_type=request.getParameter("shift_type");
             location=request.getParameter("location");
             area=request.getParameter("area");
             ward=request.getParameter("ward");
             zone=request.getParameter("zone");
             location_type=request.getParameter("location_type");
             String designation=request.getParameter("designation");
             String person=request.getParameter("person");
             String date=request.getParameter("date");
             String remark=request.getParameter("remark");
              ShiftKeyPersonMIS skpb=new ShiftKeyPersonMIS();
              skpb.setShift_key_person_map_id(shift_key_person_map_id);
              skpb.setShift_type(shift_type);
              skpb.setLocation_type(location_type);
              skpb.setZone(zone);
              skpb.setWard(ward);
              skpb.setArea(area);
              skpb.setLocation(location);
              skpb.setDesignation(designation);
              skpb.setPerson(person);
              skpb.setDate(date);
              skpb.setRemark(remark);
               sdlm.insertRecord(skpb);

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

     noOfRowsInTable = ShiftKeyPersonMISModel.getNoOfRows(searchlocation_type,search_shift_type, search_designation,searchCityName,searchZoneName,searchWardType,searchArea,searchperson,searchdate,emp_code);

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
     if (task.equals("save")|| task.equals("Save AS New") ||  task.equals("Delete")) {
            lowerLimit = lowerLimit - noOfRowsTraversed;
        }
 else   if (task.equals("Show All Records")) {
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
            searchperson="";
            searchdate="";
            emp_code="";
        }
         List<ShiftKeyPersonMIS>  list= ShiftKeyPersonMISModel.showData(lowerLimit, noOfRowsToDisplay,searchlocation_type,search_shift_type, search_designation,searchCityName,searchZoneName,searchWardType,searchArea,searchperson,searchdate,emp_code);

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
          request.setAttribute("searchperson", searchperson);
          request.setAttribute("emp_code", emp_code);
           request.setAttribute("search_date", searchdate);
            RequestDispatcher rd=request.getRequestDispatcher("keyPersonMapMis");
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
