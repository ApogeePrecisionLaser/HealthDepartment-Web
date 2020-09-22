/*
 ShiftWorkBench-2
 */
package com.healthDepartment.shift.controller;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.shift.model.ShiftTimeModel;
import com.healthDepartment.shift.tableClasses.ShiftTimeBean;
import com.healthDepartment.util.UniqueIDGenerator;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
 * @author Tushar Singh
 */
public class ShiftTimeController extends HttpServlet {
   
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
              response.setContentType("text/html;charset=UTF-8");
          response.setCharacterEncoding("UTF-8");
          request.setCharacterEncoding("UTF-8");
        String task=request.getParameter("task");
         ServletContext ctx = getServletContext();
          ShiftTimeModel sm=new ShiftTimeModel();
         int lowerLimit=0, noOfRowsTraversed=0, noOfRowsToDisplay=1000, noOfRowsInTable = 0;
        try
        {
            sm.setConnection((Connection) DBConnection.getConnectionForUtf(ctx));
        }
        catch(Exception e)
        {
         System.out.print(e);
        }
         String emp_code="",person_name="";
       try {
            String JQstring = request.getParameter("action1");
            String q = request.getParameter("q");
            if (JQstring != null) {
                 PrintWriter out = response.getWriter();
                List<String> list = null;
                   if (JQstring.equals("getSearchPerson"))
                   {
             if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                 emp_code = request.getParameter("action2");
                list = ShiftTimeModel.getSearchPerson(q,emp_code);
                }
                   else if(JQstring.equals("getEmpCode"))
               {
                     if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                    person_name = request.getParameter("action2");

                     list = ShiftTimeModel.getEmpCode(q,person_name);                  
                }
                 else if(JQstring.equals("getName"))
                   {
                     if (request.getParameter("action2") != null && !request.getParameter("action2").isEmpty())
                    person_name = request.getParameter("action2");
                       list= ShiftTimeModel.getName(q,person_name);
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
         if(task==null||task.isEmpty())
               {
                task="";
               }
        Date dt = new Date();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String cut_dt = df.format(dt);
     try
      {
                 int shift_key_person_detail_id=0;
                 try{
                     shift_key_person_detail_id = Integer.parseInt(request.getParameter("shift_key_person_detail_id").trim());
                 }catch(Exception ex){
                     shift_key_person_detail_id = 0;
                 }
        person_name=request.getParameter("person_name");
        String zone=request.getParameter("zone");
        String ward=request.getParameter("ward");
        String area=request.getParameter("area");
         String zone_no=request.getParameter("zone_no");
        String ward_no=request.getParameter("ward_no");
        String area_no=request.getParameter("area_no");
        String date=request.getParameter("date");
        emp_code=request.getParameter("emp_code");
  
      if(task.equals("Save"))
               {
        String[] b_name=request.getParameterValues("name");
        String[] b_occupation_name=request.getParameterValues("occupation_name");
        String[] b_location=request.getParameterValues("location");
        String[] b_is_residencial=request.getParameterValues("is_residencial");
        String[] b_status=request.getParameterValues("status");
        String[] b_beneficiary_id=request.getParameterValues("beneficiary_id");
        String[] time_hour=request.getParameterValues("time_hour");
        String[] time_min=request.getParameterValues("time_min");
        String[] reason_id=request.getParameterValues("reason");
        String[] mobile_no=request.getParameterValues("mobile_no");
        ShiftTimeBean sb=new ShiftTimeBean();
        sb.setB_name(b_name);
        sb.setB_occupation_name(b_occupation_name);
        sb.setB_location(b_location);
        sb.setB_is_residencial(b_is_residencial);
        sb.setB_status(b_status);
        sb.setB_beneficiary_id(b_beneficiary_id);
        sb.setPerson_name(person_name);
        sb.setZone(zone);
        sb.setWard(ward);
        sb.setArea(area);
        sb.setDate(date);
        sb.setTime_hour(time_hour);
        sb.setTime_min(time_min);
        sb.setReason_id(reason_id);
        sb.setB_mobile_no(mobile_no);
        sm.insertRecord(sb);

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
        if (task.equals("Save")) {
  person_name="";zone="";ward="";area="";zone_no="";ward_no="";area_no="";date="";emp_code="";

        }
               noOfRowsInTable = ShiftTimeModel.getNoOfRows(zone,ward,area);
         
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
         List<ShiftTimeBean>  list=  ShiftTimeModel.showData(lowerLimit, noOfRowsToDisplay,zone,ward,area);

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
         request.setAttribute("cut_dt", cut_dt);
         request.setAttribute("list", list);
         request.setAttribute("lowerLimit",lowerLimit);
         request.setAttribute("noOfRowsTraversed",noOfRowsTraversed);
         request.setAttribute("zone",zone);
         request.setAttribute("ward",ward);
         request.setAttribute("area",area);
         request.setAttribute("zone_no",zone_no);
         request.setAttribute("ward_no",ward_no);
         request.setAttribute("area_no",area_no);
          request.setAttribute("emp_code",emp_code);
         request.setAttribute("person_name",person_name);
         request.setAttribute("date",date);
         request.setAttribute("message", sm.getMessage());
         request.setAttribute("noOfRowsInTable",noOfRowsInTable);
         request.setAttribute("IDGenerator", new UniqueIDGenerator());
          request.setAttribute("reason_name", sm.getReason_type());
         RequestDispatcher rd=request.getRequestDispatcher("shifttime");
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
