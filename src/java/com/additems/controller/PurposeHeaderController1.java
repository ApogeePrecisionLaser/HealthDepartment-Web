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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shobha
 */
public class PurposeHeaderController1 extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay = 10, noOfRowsInTable;

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
            System.out.println("error in PurposeHeaderController setConnection() calling try block" + e);
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
                        String parentName = request.getParameter("action2");
                        if(parentName==null || parentName.isEmpty()){
                           return;
                        }else{
                           list = purposeHeaderModel.getChild_Header_Name(q, request.getParameter("action2"));
                        }
                    } else if (JQstring.equals("getSearchIndex")) {
                        list = purposeHeaderModel.getSearchIndex(q);
                    } else if (JQstring.equals("getSearchSubHeader")) {
                        list = purposeHeaderModel.getSearchSubHeader(q);
                    } else if (JQstring.equals("getSearchParentHeader")) {
                        list = purposeHeaderModel.getSearchParentHeader(q);
                    }

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
                System.out.println("\n Error --PurposeHeaderController get JQuery Parameters Part-" + e);
            }


            String headerindex=request.getParameter("searchheader_index");
            String subheadername=request.getParameter("searchSub_header_name");
            String parentheadername=request.getParameter("searchParent_header_name");
            if(headerindex==null || headerindex.isEmpty())
            {
                headerindex="";
            }
               if(subheadername==null || subheadername.isEmpty())
            {
                subheadername="";
            }
               if(parentheadername==null || parentheadername.isEmpty())
            {
                parentheadername="";
            }

            String requester = request.getParameter("requester");
            if (requester != null && requester.equals("PRINT")) {
                //PrintWriter out = response.getWriter();
                try {
                    String jrxmlFilePath = "";
                    byte[] reportInbytes = null;
                    response.setContentType("application/pdf");
                    ArrayList<PurposeHeaderNode1> mainList = new ArrayList<PurposeHeaderNode1>();
                    String searchheader_index = request.getParameter("searchheader_index");
                    String searchSub_header_name = request.getParameter("searchSub_header_name");
                    String searchParent_header_name = request.getParameter("searchParent_header_name").trim();
                    mainList = purposeHeaderModel.getItemListForReport(searchheader_index, searchSub_header_name, searchParent_header_name);
                    String pdfType = request.getParameter("pdfType");
                    if (pdfType == null || pdfType.isEmpty()) {
                        pdfType = "";
                    }
                    if (pdfType.equals("sort")) {
                        jrxmlFilePath = ctx.getRealPath("/report/itemList_3.jrxml");
                        reportInbytes = purposeHeaderModel.generateHierarchyDetailPdfList(jrxmlFilePath, mainList);
                    }
                    ServletOutputStream servletOutputStream = response.getOutputStream();
                    response.setContentLength(reportInbytes.length);
                    servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
                    servletOutputStream.flush();
                    servletOutputStream.close();
                } catch (Exception e) {
                }
                return;
            }

            String task = request.getParameter("task");
            if (task == null) {
                task = "";
            }

            String CheckUncheck = request.getParameter("add_parent");
            if (CheckUncheck == null) {
                CheckUncheck = "N";
            }

            if (task.equals("Delete Header")) {
                int NodeHeaderId = purposeHeaderModel.getNodeId(request.getParameter("HeaderName").trim());
                if(NodeHeaderId != 1){
                  purposeHeaderModel.deleteRecord(NodeHeaderId);
                }else{
                    System.out.println("Cannot delete root node having node_id = " + NodeHeaderId);
                }

            } else if (task.equals("Update Header")) {
                int node_id;
                node_id = Integer.parseInt(request.getParameter("header_node_id"));
                String NodeHeaderName = request.getParameter("HeaderName").trim();
               // int NodeHeaderId = purposeHeaderModel.getNodeId(NodeHeaderName);
                purposeHeaderModel.updateRecord(NodeHeaderName, node_id);

            } else if (task.equals("Add Header")) {
                if(CheckUncheck.equals("N")){
                int node_id;
                try {
                    node_id = Integer.parseInt(request.getParameter("node_id"));
                } catch (Exception e) {
                    node_id = 0;
                }

                PurposeHeaderNode1 headerNode = new PurposeHeaderNode1();
                headerNode.setNode_id(node_id);
                String NodeName = request.getParameter("newHeaderName").trim();
                headerNode.setNode_name(request.getParameter("newHeaderName").trim());
                //headerNode.setActive(request.getParameter("active"));
                headerNode.setRemark(request.getParameter("description").trim());
                int NodeParentId = purposeHeaderModel.getNodeId(request.getParameter("parentHeaderName").trim());


                if (NodeParentId > 0 && node_id == 0) {
                    // if parent's entry is not in the node table, child's entry cannot be done
                    purposeHeaderModel.insertNodeRecord(headerNode, NodeParentId, NodeName);
                }
                } else{
                    //PurposeHeaderNode headerNode = new PurposeHeaderNode();
                    String description = request.getParameter("description").trim();
                    //headerNode.setRemark(description);
                    String childHeader = request.getParameter("childHeaderName").trim();
                    //headerNode.setNode_name(childHeader);
                    String parentHeader = request.getParameter("parentHeaderName").trim();
                    String Plist = purposeHeaderModel.getNodeId_Gen(parentHeader);
                    int NodeParentId = Integer.parseInt(Plist.split(",")[0]);
                    int NodeParentGen = Integer.parseInt(Plist.split(",")[1]);
                    String Clist = purposeHeaderModel.getNodeId_Gen(childHeader);
                    int NodeChildId = Integer.parseInt(Clist.split(",")[0]);
                    int NodeChildGen = Integer.parseInt(Clist.split(",")[1]);
                    String NodeChildIndex = purposeHeaderModel.getChildIndex(NodeChildId);
                    int NodePrevParentId = purposeHeaderModel.getNodeParentId(NodeChildId);

                  if (NodeParentId > 0 && NodeChildId>0) {
                    int rowsaffected = purposeHeaderModel.insertParentRecord(parentHeader, childHeader, description);
                    if(rowsaffected >0){
                        purposeHeaderModel.updateParentRecord(NodeChildId, NodeParentId);
                        if(NodePrevParentId != 1)
                        purposeHeaderModel.updatePrevGenIndex(NodeChildId, NodeChildGen, NodeParentId, NodeParentGen, NodeChildIndex,NodePrevParentId);
                      }
                  }

                }
            } else if (task.equals("Show All Records")) {
                headerindex="";
                subheadername="";
                parentheadername="";
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
            noOfRowsInTable = purposeHeaderModel.getNoOfRows(parentheadername, subheadername,headerindex);                  // get the number of records (rows) in the table.

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

            if (task.equals("Add Header") || task.equals("Delete Header") || task.equals("Update Header") ) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }

            List<PurposeHeaderNode1> PurposeHeaderList = purposeHeaderModel.showData(lowerLimit,noOfRowsToDisplay, parentheadername, subheadername,headerindex);

            lowerLimit = lowerLimit + PurposeHeaderList.size();
            noOfRowsTraversed = PurposeHeaderList.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("parentheadername", parentheadername);
            request.setAttribute("subheadername", subheadername);
            request.setAttribute("headerindex", headerindex);
            request.setAttribute("PurposeHeaderList", PurposeHeaderList);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }

            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("message", purposeHeaderModel.getMessage());
            request.setAttribute("msgBgColor", purposeHeaderModel.getMsgBgColor());
            purposeHeaderModel.closeConnection();
            request.getRequestDispatcher("purposeHeaderView2").forward(request, response);
        } catch (Exception e) {
            System.out.println("PurposeHeaderController main thread " + e);
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
