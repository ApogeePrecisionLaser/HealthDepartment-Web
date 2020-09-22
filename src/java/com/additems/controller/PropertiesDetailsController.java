/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.controller;

import com.additems.model.PropertiesDetailsModel;
import com.additems.tableClasses.PropertiesDetails;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Shobha
 */
public class PropertiesDetailsController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        try
        {
        int PropertiesDetailsId;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext ctx = getServletContext();
        PropertiesDetails propertiesDetailsBean=new PropertiesDetails();
        PropertiesDetailsModel properties_model=new PropertiesDetailsModel();
        properties_model.setDriverClass(ctx.getInitParameter("driverClass"));
        properties_model.setConnectionString(ctx.getInitParameter("connectionString"));
        properties_model.setDb_username(ctx.getInitParameter("db_username"));
        properties_model.setDb_password(ctx.getInitParameter("db_password"));
        properties_model.setConnection((Connection) DBConnection.getConnection(ctx));
        String properties_name=request.getParameter("properties_name");
        String value=request.getParameter("value");
        String searchproperties_name=request.getParameter("searchproperties_name");
          String remark=request.getParameter("remark");
          String properties_details_id=request.getParameter("property_details_id");
          if(properties_name==null)properties_name="";
          if(remark==null)remark="";
          if(properties_details_id==null || properties_details_id.isEmpty())
              PropertiesDetailsId=0;
          else PropertiesDetailsId=Integer.parseInt(properties_details_id);
          propertiesDetailsBean.setProperties_name(properties_name);
          propertiesDetailsBean.setRemark(remark);
          propertiesDetailsBean.setValue(value);
          propertiesDetailsBean.setProperty_details_id(PropertiesDetailsId);
         int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 6, noOfRowsInTable;
         String task=request.getParameter("task");
         String JQuery=request.getParameter("action1");
            String q=request.getParameter("q");
               if(JQuery!=null)
                {
                   List list=null;
                    if(JQuery.equals("getPropertiesName"))
                    {
                        list=properties_model.getPropertiesName(q);
                    }
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext())
                    {
                        String data = iter.next();
                        out.println(data);
                    }
                    return;
                }
         if(task==null)
                task="";
         if(task.equals("Save"))
         {
            properties_model.insertRecord(propertiesDetailsBean);
         }
         if(task.equals("Save AS New"))
           {
             if(properties_model.insertRecord(propertiesDetailsBean)){
                 System.out.println("record saved successfully");
             }else{
               System.out.print("record not saved");
             }
           }
        if(task.equals("Delete"))
           {
               if(properties_model.deleteRecord(propertiesDetailsBean)){
                   System.out.print("record deleted");
                    } else{
                    System.out.print("record not deleted");
                    }
           }
             if(task.equals("Update"))
           {
               if(properties_model.UpdateRecord(propertiesDetailsBean)){
                   System.out.print("record Updated");
                    } else{
                    System.out.print("record not updated");
                    }
           }
            String buttonAction = request.getParameter("buttonAction");
         if(buttonAction == null)
                 buttonAction = "none";
              try {
        lowerLimit = Integer.parseInt(request.getParameter("lowerLimit"));
        noOfRowsTraversed = Integer.parseInt(request.getParameter("noOfRowsTraversed"));
            } catch (Exception e)
            {
                lowerLimit = noOfRowsTraversed = 0;
            }
        noOfRowsInTable =properties_model.getNoOfRows(searchproperties_name);
        if (buttonAction.equals("Next"));
        else if (buttonAction.equals("Previous"))
            {
                int temp = lowerLimit - noOfRowsToDisplay - noOfRowsTraversed;
                if (temp < 0)
                {
                    noOfRowsToDisplay = lowerLimit - noOfRowsTraversed;
                    lowerLimit = 0;
                } else
                {
                    lowerLimit = temp;
                }
            } else if (buttonAction.equals("First"))
            {
                lowerLimit = 0;
            }
            else if (buttonAction.equals("Last"))
            {
                lowerLimit = noOfRowsInTable - noOfRowsToDisplay;
                if (lowerLimit < 0) {
                    lowerLimit = 0;
                }
            }
            List list=properties_model.ShowData(lowerLimit,noOfRowsToDisplay,searchproperties_name);
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();
            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable)
            {
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
        request.setAttribute("list", list);
        request.setAttribute("message", properties_model.getMessage());
        request.setAttribute("msgBgColor", properties_model.getMsgBgColor());
        request.setAttribute("searchproperties_name", searchproperties_name);
         request.setAttribute("lowerLimit",lowerLimit);
        request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        // request.setAttribute("searchcity", searchcity);
            RequestDispatcher rd=request.getRequestDispatcher("properties_details");
            rd.forward(request, response);
        }catch(Exception e){}
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
