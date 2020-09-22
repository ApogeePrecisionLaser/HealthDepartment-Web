/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.controller;

import com.additems.model.PurposeHeaderModel1;
import com.additems.tableClasses.PurposeHeaderNode1;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shobha
 */
public class GetParentHeaderController1 extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         int lowerLimit = 0, noOfRowsTraversed = 0, noOfRowsToDisplay = 10, noOfRowsInTable;

        ServletContext ctx = getServletContext();
        PurposeHeaderModel1 purposeHeaderModel = new PurposeHeaderModel1();

        HttpSession session = request.getSession();
//        int keyperson_id = 0;
//        if (session == null || (String) session.getAttribute("user_name") == null) {
//            response.sendRedirect("index.jsp");
//            return;
//        }
//        if(session != null){
//           keyperson_id = (Integer) session.getAttribute("user_id");
//        }


        try {
            purposeHeaderModel.setConnection(DBConnection.getConnection(ctx));
        } catch (Exception e) {
            System.out.println("error in GetParentHeaderController setConnection() calling try block" + e);
        }

        try {

            try {
                String JQstring = request.getParameter("action1");
                String q = request.getParameter("q");
                if (JQstring != null) {
                    PrintWriter out = response.getWriter();
                    List<String> list = null;
                    if (JQstring.equals("getParentHeaderName")) {
                        list = purposeHeaderModel.getParent_Header_Name(q);
                    } else if (JQstring.equals("getChildHeaderName")) {
                        list = purposeHeaderModel.getChild_Header_Name(q, request.getParameter("action2"));
                    } else if (JQstring.equals("getIndex")) {
                        list = purposeHeaderModel.getIndex(q);
                    }
//                     else if (JQstring.equals("getsection")) {
//                        list = purposeHeaderModel.getsection(q);
//                    }

                    Iterator<String> iter = list.iterator();
                    while (iter.hasNext()) {
                        String data = iter.next();
                        if (data.equals("Disable")) {
                            out.print(data);
                        } else {
                            out.println(data);
                        }
                    }
                    return;
                }
            } catch (Exception e) {
                System.out.println("\n Error --GetParentHeaderController get JQuery Parameters Part-" + e);
            }


            String headername = request.getParameter("searchheader_name");
            String headerindex = request.getParameter("searchheader_index");
            if (headerindex == null || headerindex.isEmpty()) {
                headerindex = "";
            }
            if (headername == null || headername.isEmpty()) {
                headername = "";
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }

            List<PurposeHeaderNode1> PurposeHeaderList = null;
            // List<PurposeHeaderNode> PurposeHeaderList1 = null;
            String parentHeaderName = "";

            if (task.equals("parent")) {
                PurposeHeaderList = purposeHeaderModel.showDataParent(-1, -1, headername, headerindex);
            } else if (task.equals("child")) {
                parentHeaderName = request.getParameter("parentHeaderName").trim();
                PurposeHeaderList = purposeHeaderModel.showChild_Header_Name(headername, headerindex, parentHeaderName);
            } else if (task.equals("header")) {
                parentHeaderName = "";
                PurposeHeaderList = purposeHeaderModel.showData1(-1, -1, headername, headerindex);
            } else if (task.equals("task_header")) {
                parentHeaderName = "";
                PurposeHeaderList = purposeHeaderModel.showData3(-1, -1, headername, headerindex);
            } else if (task.equals("sectionSuperChild")) {
                parentHeaderName = "";
                PurposeHeaderList = purposeHeaderModel.showData2(-1, -1, headername, headerindex);
                int listSize = PurposeHeaderList.size();

                request.setAttribute("lowerLimit", lowerLimit);
                request.setAttribute("task", task);
                request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
                request.setAttribute("PurposeHeaderList", PurposeHeaderList);
                request.setAttribute("listSize", listSize);
                request.setAttribute("IDGenerator", new UniqueIDGenerator());
                request.setAttribute("message", purposeHeaderModel.getMessage());
                request.setAttribute("msgBgColor", purposeHeaderModel.getMsgBgColor());
                request.setAttribute("headername", headername);
                request.setAttribute("headerindex", headerindex);
                request.setAttribute("parentHeaderName", parentHeaderName);
                purposeHeaderModel.closeConnection();
                request.getRequestDispatcher("getSectionLeavesView").forward(request, response);
            } else if (task.equals("sections")) {
                PurposeHeaderList = purposeHeaderModel.showData2(-1, -1, headername, headerindex);
            }
 else if (task.equals("parent1")) {
                PurposeHeaderList = purposeHeaderModel.showDataParent(-1, -1, headername, headerindex);
            }

            // PurposeHeaderList = purposeHeaderModel.showData1(-1, -1,headername,headerindex);

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("task", task);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("PurposeHeaderList", PurposeHeaderList);
            //  request.setAttribute("PurposeHeaderList1", PurposeHeaderList1);
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", purposeHeaderModel.getMessage());
            request.setAttribute("msgBgColor", purposeHeaderModel.getMsgBgColor());
            request.setAttribute("headername", headername);
            request.setAttribute("headerindex", headerindex);
            request.setAttribute("parentHeaderName", parentHeaderName);
            purposeHeaderModel.closeConnection();
            request.getRequestDispatcher("getParentHeaderView2").forward(request, response);
        } catch (Exception e) {
            System.out.println("GetParentHeaderController main thread " + e);
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
