/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.controller;

import com.additems.model.ItemModel;
import com.additems.tableClasses.Item;

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
public class ItemController extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        try {
         ServletContext ctx = getServletContext();
        Item itemBean=new Item();
        ItemModel itemModel=new ItemModel();
        itemModel.setDriverClass(ctx.getInitParameter("driverClass"));
        itemModel.setConnectionString(ctx.getInitParameter("connectionString"));
        itemModel.setDb_username(ctx.getInitParameter("db_username"));
        itemModel.setDb_password(ctx.getInitParameter("db_password"));
        itemModel.setConnection((Connection) DBConnection.getConnection(ctx));
            int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 6, noOfRowsInTable,j=0;
            String JQuery=request.getParameter("action1");
            String q=request.getParameter("q");
               if(JQuery!=null)
                {
                   String action2=request.getParameter("action2");
                   if(action2==null)action2="";
                   List list=null;
                    if(JQuery.equals("getItemName"))
                    {
                        list=itemModel.getItemName(q);
                    }
                   if(JQuery.equals("getSelect"))
                    {
                        String item=request.getParameter("item_name");
                        list=itemModel.getSelect(q,item);
                    }
                   if(JQuery.equals("getProperties"))
                    {
                        list=itemModel.getProperties(q,action2);
                    }

                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext())
                    {
                        String data = iter.next();
                        out.println(data);
                    }
                    return;
                }
            String searchitem_name=request.getParameter("searchitem_name");
            if(searchitem_name==null)searchitem_name="";
            String item_id=request.getParameter("item_id");
            if(item_id==null || item_id.isEmpty())
            {
                item_id="";
            } else{
                  itemBean.setItem_id(Integer.parseInt(item_id));
            }
            String item_name = request.getParameter("item_name");
            String node_name = request.getParameter("parentHeaderName");
            if(item_name!=null){
                int id=itemModel.getItemNameId(item_name);
                itemBean.setItem_name_id(id);
            }
            if(node_name!=null){

                itemBean.setNode_name(node_name);
            }
//            String item_serial_no=request.getParameter("item_serial_no");
//                itemBean.setItem_serial_no(item_serial_no);
//            String item_rate=request.getParameter("item_rate");
//                if(item_rate!=null)itemBean.setItem_rate(Double.parseDouble(item_rate));
//            String rate_applicable_from=request.getParameter("rate_applicable_from");
//                itemBean.setRate_applicable_from(rate_applicable_from);
//            String rate_applicable_to=request.getParameter("rate_applicable_to");
//                itemBean.setRate_applicable_to(rate_applicable_to);
//            String item_unit=request.getParameter("item_unit");
//                itemBean.setItem_unit(item_unit);
            String properties_1=request.getParameter("properties1_id");
                if(properties_1==null || properties_1.isEmpty())
                    itemBean.setProperties_1(0);
                else
                    itemBean.setProperties_1(Integer.parseInt(properties_1));
            String properties_2=request.getParameter("properties2_id");
                if(properties_2==null || properties_2.isEmpty())
                    itemBean.setProperties_2(0);
                else
                    itemBean.setProperties_2(Integer.parseInt(properties_2));
            String properties_3=request.getParameter("properties3_id");
                if(properties_3==null || properties_3.isEmpty())
                    itemBean.setProperties_3(0);
                else
                    itemBean.setProperties_3(Integer.parseInt(properties_3));
            String properties_4=request.getParameter("properties4_id");
                if(properties_4==null || properties_4.isEmpty())
                    itemBean.setProperties_4(0);
                else
                    itemBean.setProperties_4(Integer.parseInt(properties_4));
            String properties_5=request.getParameter("properties5_id");
                if(properties_5==null || properties_5.isEmpty())
                    itemBean.setProperties_5(0);
                else
                    itemBean.setProperties_5(Integer.parseInt(properties_5));
           String properties_value_1=request.getParameter("properties_1");
           String properties_value_2=request.getParameter("properties_2");
           String properties_value_3=request.getParameter("properties_3");
           String properties_value_4=request.getParameter("properties_4");
           String properties_value_5=request.getParameter("properties_5");
           if(properties_value_1!=null)itemBean.setProperties_value_1(properties_value_1);
           if(properties_value_2!=null)itemBean.setProperties_value_2(properties_value_2);
           if(properties_value_3!=null)itemBean.setProperties_value_3(properties_value_3);
           if(properties_value_4!=null)itemBean.setProperties_value_4(properties_value_4);
           if(properties_value_5!=null)itemBean.setProperties_value_5(properties_value_5);
         String task=request.getParameter("task");


         if(task==null)
                task="";
         if(task.equals("Save"))
         {
            itemModel.insertRecord(itemBean,j);
         }
         if(task.equals("Save AS New"))
           {
             if(itemModel.insertRecord(itemBean,j)){
                 System.out.println("record saved successfully");
             }else{
               System.out.print("record not saved");
             }
           }
        if(task.equals("Delete"))
           {
               if(itemModel.deleteRecord(itemBean)){
                   System.out.print("record deleted");
                    } else{
                    System.out.print("record not deleted");
                    }
           }
            if(task.equals("Update"))
           {
               if(itemModel.UpdateRecord(itemBean)){
                   System.out.print("record updated");
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
        noOfRowsInTable =itemModel.getNoOfRows(searchitem_name);
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
                if (lowerLimit < 0)
                {
                    lowerLimit = 0;
                }
            }
            List list=itemModel.ShowData(lowerLimit,noOfRowsToDisplay,searchitem_name);
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
        request.setAttribute("message", itemModel.getMessage());
            request.setAttribute("msgBgColor", itemModel.getMsgBgColor());
         request.setAttribute("lowerLimit",lowerLimit);
        request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
        request.setAttribute("searchitem_name",searchitem_name);
        request.setAttribute("IDGenerator", new UniqueIDGenerator());
        RequestDispatcher rd=request.getRequestDispatcher("item");
        rd.forward(request, response);
        }catch(Exception e)
        {
            e.printStackTrace();
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
