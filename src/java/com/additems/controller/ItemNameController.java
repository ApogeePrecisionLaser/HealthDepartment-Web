/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.controller;

import com.additems.model.ItemNameModel;
import com.additems.tableClasses.ItemName;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
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
public class ItemNameController extends HttpServlet {
   
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
        int itemNameId;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext ctx = getServletContext();
        ItemName itemNameBean=new ItemName();
        ItemNameModel itemNameModel=new ItemNameModel();
        itemNameModel.setDriverClass(ctx.getInitParameter("driverClass"));
        itemNameModel.setConnectionString(ctx.getInitParameter("connectionString"));
        itemNameModel.setDb_username(ctx.getInitParameter("db_username"));
        itemNameModel.setDb_password(ctx.getInitParameter("db_password"));

        itemNameModel.setConnection(DBConnection.getConnection(ctx));
        String item_name=request.getParameter("item_name");
        String item_code=request.getParameter("item_code");
          String remark=request.getParameter("remark");
          String searchitem_name=request.getParameter("searchitem_name");  if(searchitem_name==null)searchitem_name="";
          String item_name_id=request.getParameter("item_name_id");
          if(item_name==null)item_name="";
          if(item_code==null)item_code="";
          if(remark==null)remark="";
          if(item_name_id==null || item_name_id.isEmpty())itemNameId=0;
          else itemNameId=Integer.parseInt(item_name_id);
          itemNameBean.setItem_name(item_name);
          itemNameBean.setItem_code(item_code);
          itemNameBean.setRemark(remark);
          itemNameBean.setItem_name_id(itemNameId);

          String JQuery=request.getParameter("action1");
            String q=request.getParameter("q");
               if(JQuery!=null)
                {
                   List list=null;
                    if(JQuery.equals("getItemName"))
                    {
                        list=itemNameModel.getItemName(q);
                    }
                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext())
                    {
                        String data = iter.next();
                        out.println(data);
                    }
                    return;
                }

         int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 6, noOfRowsInTable;
         String task=request.getParameter("task");
         if(task==null)
                task="";
         if(task.equals("Save"))
         {
            itemNameModel.insertRecord(itemNameBean);
         }
         if(task.equals("Save AS New"))
           {
             if(itemNameModel.insertRecord(itemNameBean)){
                 System.out.println("record saved successfully");
             }else{
               System.out.print("record not saved");
             }
           }
        if(task.equals("Delete"))
           {
               if(itemNameModel.deleteRecord(itemNameBean)){
                   System.out.print("record deleted");
                    } else{
                    System.out.print("record not deleted");
                    }
           }
         if(task.equals("Update"))
           {
               if(itemNameModel.UpdateRecord(itemNameBean)){
                   System.out.print("record deleted");
                    } else{
                    System.out.print("record not deleted");
                    }
           }
         if(task.equals("Show All Records"))
           {
            searchitem_name="";
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
        noOfRowsInTable =itemNameModel.getNoOfRows(searchitem_name);
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
            List<ItemName> list=itemNameModel.ShowData(lowerLimit,noOfRowsToDisplay,searchitem_name);
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
        request.setAttribute("message", itemNameModel.getMessage());
            request.setAttribute("msgBgColor", itemNameModel.getMsgBgColor());
        request.setAttribute("searchitem_name", searchitem_name);
         request.setAttribute("lowerLimit",lowerLimit);
        request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        // request.setAttribute("searchcity", searchcity);
            RequestDispatcher rd=request.getRequestDispatcher("item_name");
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
