/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.controller;

import com.additems.model.NodeDetailModel;
import com.additems.tableClasses.NodeDetail;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

/**
 *
 * @author Shobha
 */
public class NodeDetailController extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
     List<NodeDetail> list = new ArrayList<NodeDetail>();
    NodeDetail pd1 = null;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int lowerLimit, noOfRowsTraversed, noOfRowsToDisplay = 3, noOfRowsInTable, node_id = 0;
        System.out.println("this is WaterTreatmentPlantController ....");
        ServletContext ctx = getServletContext();
        NodeDetailModel pipeDetailModel = new NodeDetailModel();
        pipeDetailModel.setDriverClass(ctx.getInitParameter("driverClass"));
        pipeDetailModel.setConnectionString(ctx.getInitParameter("connectionString"));
        pipeDetailModel.setDb_username(ctx.getInitParameter("db_username"));
        pipeDetailModel.setDb_password(ctx.getInitParameter("db_password"));
        try {
            pipeDetailModel.setConnection(DBConnection.getConnectionForUtf(ctx));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println("Connection is - " + pipeDetailModel.getConnection());
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain; charset=UTF-8");
        String task = request.getParameter("task");
        if (task == null) {
            task = "";
        }
        String search_node_name = "";
        String search_pipe_type = "";
        search_node_name = request.getParameter("search_node_name");
        if (search_node_name == null) {
            search_node_name = "";
        }
        search_pipe_type = request.getParameter("search_pipe_type");
        if (search_pipe_type == null) {
            search_pipe_type = "";
        }
        try
        {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");   // field own input
            if (JQstring != null) {
                PrintWriter out = response.getWriter();
                List<String> list = null;
                if (JQstring.equals("getNodeName")) {
                    list = pipeDetailModel.getNodeName(q);
                } else if (JQstring.equals("getPipeType")) {
                    list = pipeDetailModel.getPipeType(q);
                }
                Iterator<String> iter = list.iterator();
                while (iter.hasNext()) {
                    String data = iter.next();
                    out.println(data);
                }
                pipeDetailModel.closeConnection();
                return;
            }
        } catch (Exception e) {
            System.out.println("\n Error --OverHeadTankController get JQuery Parameters Part-" + e);
        }
        if (task.equals("generateReport")) {
            List listAll = null;
            String jrxmlFilePath;
            response.setContentType("application/pdf");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            listAll = pipeDetailModel.showAllData(-1, -1, search_node_name, search_pipe_type, 0);
            jrxmlFilePath = ctx.getRealPath("/report/pipe_detail.jrxml");
            byte[] reportInbytes = pipeDetailModel.generateMapReport(jrxmlFilePath, listAll);
            response.setContentLength(reportInbytes.length);
            servletOutputStream.write(reportInbytes, 0, reportInbytes.length);
            servletOutputStream.flush();
            servletOutputStream.close();
            return;
        } else if (task.equals("GetPipeBend")) {
            JSONObject json = pipeDetailModel.getPipeDetail(Integer.parseInt(request.getParameter("pipe_detail_id")));
            PrintWriter out = response.getWriter();
            out.print(json);
            return;
        } else if (task.equals("GetData")) {
            JSONObject json = pipeDetailModel.getData();
            PrintWriter out = response.getWriter();
            out.print(json);
            return;
        } else if (task.equals("GetCompleteData"))
        {
            JSONObject json = pipeDetailModel.GetCompleteData();
            PrintWriter out = response.getWriter();
            out.print(json);
            return;
        }
        try {
            if (task.equals("showNodeData")) {
                node_id = Integer.parseInt(request.getParameter("node_id"));
            } else if (task.equals("Delete")) {
                pipeDetailModel.deleteRecord(Integer.parseInt(request.getParameter("pipe_detail_id")));  // Pretty sure that organisation_type_id will be available.
            } else if (task.equals("Save") || task.equals("Save AS New") || task.equals("Update")) {
                int pipe_detail_id;
                try {
                    pipe_detail_id = Integer.parseInt(request.getParameter("pipe_detail_id"));
                } catch (Exception e) {
                    pipe_detail_id = 0;
                }
                if (task.equals("Save AS New") || task.equals("Save")) {
                    pipe_detail_id = 0;
                }
                String node_name = request.getParameter("parentHeaderName");
                String head_latitude = request.getParameter("head_latitude");
                String head_longitude = request.getParameter("head_longitude");
                String tail_latitude = request.getParameter("tail_latitude");
                String tail_longitude = request.getParameter("tail_longitude");
                String diameter = request.getParameter("diameter");
                String diameter_unit = request.getParameter("diameter_unit");
                String length = request.getParameter("length");
                String length_unit = request.getParameter("length_unit");
                String remark = request.getParameter("remark");
                String pipe_type = request.getParameter("pipe_type");
                String pipe_name = request.getParameter("pipe_name");

                NodeDetail pd = new NodeDetail();
                pd.setPipe_detail_id(pipe_detail_id);
                pd.setNode_name(node_name);
                pd.setHead_latitude(Double.parseDouble(head_latitude));
                pd.setHead_longitude(Double.parseDouble(head_longitude));
                pd.setTail_latitude(Double.parseDouble(tail_latitude));
                pd.setTail_longitude(Double.parseDouble(tail_longitude));
                pd.setDiameter(Double.parseDouble(diameter));
                pd.setDiameter_unit(diameter_unit);
                pd.setLength(Double.parseDouble(length));
                pd.setLength_unit(length_unit);
                pd.setRemark(remark);
                pd.setPipe_type(pipe_type);
                pd.setPipe_name(pipe_name);
                if (pipe_detail_id == 0) {
                    System.out.println("Inserting values by ......");
                    pipeDetailModel.insertRecord(pd);
                } else {
                    System.out.println("Update values by WaterTreatmentPlantModel........");
                    pipeDetailModel.updateRecord(pd);
                }
            }
            if (task.equals("Show All Records")) {
                search_node_name = "";
                search_pipe_type = "";
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
            noOfRowsInTable = pipeDetailModel.getTotalRowsInTable(search_node_name, search_pipe_type, node_id);
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

            if (task.equals("Save") || task.equals("Delete") || task.equals("Save AS New") || task.equals("Update")) {
                lowerLimit = lowerLimit - noOfRowsTraversed;    // Here objective is to display the same view again, i.e. reset lowerLimit to its previous value.
            }
            pd1 = new NodeDetail();
            list = pipeDetailModel.showAllData(lowerLimit, noOfRowsToDisplay, search_node_name, search_pipe_type, node_id);
//            if (list.size() == 0) {
//                pd1 = pipeDetailModel.showNodeData(node_id).get(0);
//            }
            lowerLimit = lowerLimit + list.size();
            noOfRowsTraversed = list.size();

            // Now set request scoped attributes, and then forward the request to view.
            request.setAttribute("lowerLimit", lowerLimit);
            request.setAttribute("noOfRowsTraversed", noOfRowsTraversed);
            request.setAttribute("list", list);
            request.setAttribute("pipeDetail", pd1);

            if ((lowerLimit - noOfRowsTraversed) == 0) {     // if this is the only data in the table or when viewing the data 1st time.
                request.setAttribute("showFirst", "false");
                request.setAttribute("showPrevious", "false");
            }
            if (lowerLimit == noOfRowsInTable) {             // if No further data (rows) in the table.
                request.setAttribute("showNext", "false");
                request.setAttribute("showLast", "false");
            }
            //  }
            request.setAttribute("IDGenerator", new UniqueIDGenerator());
            request.setAttribute("search_node_name", search_node_name);
            request.setAttribute("search_pipe_type", search_pipe_type);
            //   request.setAttribute("message", sourceWaterLevelModel.getMessage());
            request.getRequestDispatcher("node_detail").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
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
