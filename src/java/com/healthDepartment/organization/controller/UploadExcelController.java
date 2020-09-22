/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;

import com.healthDepartment.shift.tableClasses.ShiftLoginBean;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Com7_2
 */
public class UploadExcelController extends HttpServlet {

    private File tmpDir;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setContentType("image/jpeg");
        Extract extract = new Extract();

        Map<String, String> map = new HashMap<String, String>();
        String task = "";
        try {
            List items = null;
            Iterator itr = null;
            DiskFileItemFactory fileItemFactory = new DiskFileItemFactory(); //Set the size threshold, above which content will be stored on disk.
            fileItemFactory.setSizeThreshold(1 * 1024 * 1024); //1 MB Set the temporary directory to store the uploaded files of size above threshold.
            fileItemFactory.setRepository(tmpDir);
            ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
            try {
                items = uploadHandler.parseRequest(request);
                itr = items.iterator();
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (item.isFormField()) {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getString() + "\n");//(getString())its for form field
                        map.put(item.getFieldName(), item.getString("UTF-8"));
                    } else {
                        System.out.println("File Name = " + item.getFieldName() + ", Value = " + item.getName());//it is (getName()) for file related things
                        if (item.getName() == null || item.getName().isEmpty()) {
                            map.put(item.getFieldName(), "");
                        } else {
                            String image_name = item.getName();

                            image_name = image_name.substring(0, image_name.length());
                            System.out.println(image_name);
                            map.put(item.getFieldName(), item.getName());
                        }
                    }
                }
                itr = null;
                itr = items.iterator();
            } catch (Exception ex) {
                System.out.println("Error encountered while uploading file" + ex);
            }

            task = map.get("task");
            if (task == null || task.isEmpty()) {
                task = "";
            }
           String task1 = request.getParameter("task1");
            if (task1 == null || task1.isEmpty()) {
                task1 = "";
            }
            if (task1.equals("showMapWindow")) {
                String longi = request.getParameter("logitude");
                String latti = request.getParameter("lattitude");
                List<ShiftLoginBean> List = extract.showDataBean();
                request.setAttribute("CoordinatesList", List);
                request.getRequestDispatcher("/view/MapView/autoMapWindow.jsp").forward(request, response);
                return;
            }
            if (task.equals("Upload File")) {
                String image_name = map.get("file");
                while (itr.hasNext()) {
                    FileItem item = (FileItem) itr.next();
                    if (!item.isFormField()) {
                        extract.makeDirectory("c:\\upload_excel");
                        File file = new File("c:\\upload_excel\\" + image_name);
                        item.write(file);
                    }
                }

                extract.setConnection();
                extract.getExcelData(new File("c:\\upload_excel\\" + image_name));
                extract.closeConnection();
            }

            request.setAttribute("message", extract.getMessage());
            request.getRequestDispatcher("uploadExcel").forward(request, response);
        } catch (Exception e) {
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
