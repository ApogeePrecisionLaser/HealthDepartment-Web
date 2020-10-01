/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.location.controller;
import com.healthDepartment.location.tableClasses.CityLocationBean;
import com.healthDepartment.location.model.CityLocationModel;
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
 * @author Administrator
 */
public class CityLocationController extends HttpServlet {
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;
        System.out.println("this is CityLocation Controller....");
        ServletContext ctx = getServletContext();
        CityLocationModel cityLocationModel = new CityLocationModel();
        cityLocationModel.setDriverClass(ctx.getInitParameter("driverClass"));
        cityLocationModel.setDb_username(ctx.getInitParameter("db_user_name"));
        cityLocationModel.setDb_password(ctx.getInitParameter("db_user_password"));
        cityLocationModel.setConnectionString(ctx.getInitParameter("connectionString"));
        cityLocationModel.setConnection();
          response.setContentType("text/html;charset=UTF-8");
       response.setCharacterEncoding("UTF-8");
       request.setCharacterEncoding("UTF-8");


          String task = request.getParameter("task");
          String searchCityName= request.getParameter("searchCityName");
          String searchZoneName= request.getParameter("searchZone");
          String searchZoneNo= request.getParameter("searchZone_no");
          String searchWardType= request.getParameter("searchWardType");
          String searchCityNo= request.getParameter("searchCityNo");
          String searchWardNo= request.getParameter("searchWardNo");
          String searchArea= request.getParameter("searchArea");
          String searchAreaNo= request.getParameter("searchAreaNo");
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
          String lati=request.getParameter("latitude");
          String longi=request.getParameter("longitude");
          Double latitude,longitude;
         if(lati==null || lati.isEmpty()){
           latitude=0.0;
         }else{
              latitude=Double.parseDouble(request.getParameter("latitude") );
         }
         if(longi==null || longi.isEmpty()){
           longitude=0.0;
         }else{
              longitude=Double.parseDouble(request.getParameter("longitude") );
         }
          
         try {
             String zone="",ward="";
            //----- This is only for Vendor key Person JQuery
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getCity")) {
                    list =cityLocationModel.searchCityName(q);
                }if (JQstring.equals("getZoneName")) {
                    list =cityLocationModel.getZoneName(q);
                }if (JQstring.equals("getLocationName")) {
                    String action2 = request.getParameter("action2");
                    if(action2 == null)
                        action2 = "";
                    list =cityLocationModel.getLocationName(q, action2);
                }if (JQstring.equals("getLocationCode")) {
                    String action2 = request.getParameter("action2");
                    if(action2 == null)
                        action2 = "";
                    list =cityLocationModel.getLocationCode(q, action2);
                }if (JQstring.equals("getCityName")) {
                    list =cityLocationModel.getCityName(q);
                }if (JQstring.equals("getZone")) {
                    list =cityLocationModel.getZone(q);
                }
                else if (JQstring.equals("getWardName")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        zone = request.getParameter("action2");
                    }
                    list = cityLocationModel.getWardName(q, zone);
                } else if (JQstring.equals("getAreaName")) {
                    if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty()) {
                        ward = request.getParameter("action2");
                    }
                    if (request.getParameter("action3") != null && !request.getParameter("action3").isEmpty()) {
                        zone= request.getParameter("action3");
                    }

                    list =cityLocationModel.getAreaName(q, ward, zone );
                }

                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                        out.println(data);
                }
             cityLocationModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --ClientPersonMapController get JQuery Parameters Part-" + e);
        }

         if (task == null) {
            task = "";
        }
           if (task.equals("showMapWindow")) {

            String point_id = request.getParameter("point_id");
            String latitude1 = "";
            String longitude1 = "";
            String LatLong = cityLocationModel.getPointLatLong(point_id);
            System.out.println(LatLong);
            String[] words = LatLong.split("\\,");
            for (int i = 0; i < words.length; i++) {
                latitude1 = words[0];
                longitude1 = words[1];
                System.out.println(latitude1 + "  " + longitude1);
            }
            request.setAttribute("longi", latitude1);
            request.setAttribute("latti", longitude1);
            //System.out.println(latti + "," + longi);
            request.getRequestDispatcher("openMapWindowView").forward(request, response);
            return;

        }
          if (task.equals("showMapWindow1")) {       

    searchCityName= request.getParameter("searchCityName");
           searchZoneName= request.getParameter("searchZone");
        
         searchWardType= request.getParameter("searchWardType");
       
          
          searchArea= request.getParameter("searchArea");
          
             
               List<CityLocationBean> List = cityLocationModel.showDataLattitude(searchArea,searchCityName,searchZoneName,searchWardType);
             
               int size=List.size();
               
          //  String LatLong = qm.getPointLatLong(point_id);
          //  System.out.println(LatLong);
           // String[] words = LatLong.split("\\,");
            
            request.setAttribute("CoordinatesList", List);
            request.setAttribute("size", size);
            //System.out.println(latti + "," + longi);
           // request.getRequestDispatcher("openMapWindowView").forward(request, response);
            request.getRequestDispatcher("view/MapView/mapWindowAll.jsp").forward(request, response);
            return;

        }
        if (task.equals("generateMapReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/pdf");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/location/location.jrxml");
                        list=cityLocationModel.showAllData(searchCityName,searchZoneName,searchWardType,searchArea);
                        byte[] reportInbytes =cityLocationModel.generateRecordList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.length);
                        servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        cityLocationModel.closeConnection();
                        return;
            }else if (task.equals("generateExcelReport")) {
                        String jrxmlFilePath;
                        List list=null;
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition", "attachment; filename=CityLocation_report.xls");
                        ServletOutputStream servletOutputStream = response.getOutputStream();
                        jrxmlFilePath = ctx.getRealPath("/report/location/location.jrxml");
                        list=cityLocationModel.showAllData(searchCityName,searchZoneName,searchWardType,searchArea);
                        ByteArrayOutputStream reportInbytes =cityLocationModel.generateExcelList(jrxmlFilePath,list);
                        response.setContentLength(reportInbytes.size());
                        servletOutputStream.write(reportInbytes.toByteArray());
                        servletOutputStream.flush();
                        servletOutputStream.close();
                        cityLocationModel.closeConnection();
                        return;
            }
        if (task.equals("Delete")) {
           cityLocationModel.deleteRecord(Integer.parseInt(request.getParameter("city_location_id")));  // Pretty sure that organisation_type_id will be available.
        } else if (task.equals("Save") || task.equals("Save AS New")) {
            int city_location_id;
            try {
                city_location_id = Integer.parseInt(request.getParameter("city_location_id"));
            } catch (Exception e) {
                city_location_id = 0;
            }
            if (task.equals("Save AS New")) {
                city_location_id = 0;
            }
            String city_name=request.getParameter("city_name");
           String zone=request.getParameter("zone");
           String ward= request.getParameter("ward");
           String area=request.getParameter("area");
            String location_no=request.getParameter("location_no");
           CityLocationBean cityLocationBean= new CityLocationBean();
           cityLocationBean.setCity_location_id(city_location_id);
           cityLocationBean.setCity(city_name);
           cityLocationBean.setZone(zone);
           cityLocationBean.setWard(ward);
           cityLocationBean.setArea(area);
           cityLocationBean.setLocation_no(location_no);
           cityLocationBean.setRemark(request.getParameter("remark"));
           cityLocationBean.setLatitude(latitude);
           cityLocationBean.setLongitude(longitude);
            if (city_location_id == 0) {
                System.out.println("Inserting values by model......");
                cityLocationModel.insertRecord(cityLocationBean);
            } else {
                System.out.println("Update values by model........");
                cityLocationModel.updateRecord(cityLocationBean);
            }
        }


        try {
            if (searchCityName == null) {
                searchCityName= "";
            }
             if (searchZoneName == null) {
                searchZoneName= "";
            }
          
        } catch (Exception e) {
        }
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
        System.out.println("searching.......... " +  searchCityName);
        noOfRowsInTable = cityLocationModel.getNoOfRows(searchCityName,searchZoneName,searchWardType,searchArea);

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
            searchCityName = "";
            searchCityNo  = "";
            searchArea = "";
           searchAreaNo="";
            searchZoneName = "";
            searchZoneNo="";
        }
         List<CityLocationBean> cityLocationList = cityLocationModel.showData(lowerLimit,noOfRowsToDisplay,searchCityName,searchZoneName,searchWardType,searchArea);
        lowerLimit = lowerLimit + cityLocationList.size();
        noOfRowsTraversed = cityLocationList.size();

        if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

        request.setAttribute("cityLocationList",cityLocationList);
        request.setAttribute("lowerLimit", lowerLimit);
        request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
        request.setAttribute("searchCityName", searchCityName);
         request.setAttribute("searchCityNo", searchCityNo);
         request.setAttribute("searchZone", searchZoneName);
         request.setAttribute("searchZone_no", searchZoneNo);
         request.setAttribute("searchWardNo", searchWardNo);
         request.setAttribute("searchAreaNo", searchAreaNo);
        request.setAttribute("searchWardType", searchWardType);
        request.setAttribute("searchArea", searchArea);
        request.setAttribute("searchArea", searchArea);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
         request.setAttribute("message",cityLocationModel.getMessage());
    request.getRequestDispatcher("/cityLocationView").forward(request, response);
    }
          @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);}
}

