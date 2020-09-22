/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.controller;

import com.healthDepartment.shift.tableClasses.ShiftLoginBean;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import jxl.Sheet;
import jxl.Workbook;

/**
 *
 * @author Administrator
 */
public class Extract {

    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    double latitude;
    double longitude;

    public void getExcelData(File exFile) {
        //   List<MeterBill> dataList = new ArrayList<MeterBill>();
        //     QuickBillEntryModel quickBillEntryModel = new QuickBillEntryModel();
        //    quickBillEntryModel.setConnection(connection);
        String msn = "";
        int rowAffacted = 0;
        try {
            //exFile = uploadFile(exFile);
            Workbook workbook = Workbook.getWorkbook(exFile);
            String[] sheetNames = workbook.getSheetNames();
            //Sheet sheet = workbook.getSheet("Sheet1");
            //Cell a1 = sheet.getCell("A1");             //(Column,Row);


            /*try {
            if (status.equals("new")) {
            sanctionMsn.removeAll(sanctionMsn);
            }
            } catch (Exception e) {
            System.out.println("ExcelModel: Collection's removeAll method call error in getUploadedExcelData : " + e);
            }*/
            for (int i = 0; i < sheetNames.length; i++) {
                // String bill_month = sheetNames[i].split("_"+(i+1))[0];
                Sheet sheet = workbook.getSheet(sheetNames[i]);
                int row = sheet.getRows();
                //    String checkContent = sheet.getCell("A" + 1).getContents().trim();
                int rowNo = 2;
                int a = 0;
                while (rowNo <= row) {

                    //  String id = sheet.getCell("B" + rowNo).getContents() == null || sheet.getCell("B" + rowNo).getContents().isEmpty() ? "" : sheet.getCell("B" + rowNo).getContents().trim();
                    //  String pfno =""; // sheet.getCell("C" + rowNo).getContents();
                    String pf_no = sheet.getCell("C" + rowNo).getContents();
                    String wage = sheet.getCell("B" + rowNo).getContents();
                 //   String pf = sheet.getCell("F" + rowNo).getContents();
               //     String days = sheet.getCell("H" + rowNo).getContents();
//                    String fh_name = sheet.getCell("G" + rowNo).getContents();
//                    String ac_no = sheet.getCell("I" + rowNo).getContents();
//                    String ifsc_code = sheet.getCell("J" + rowNo).getContents();
//                   String age = sheet.getCell("H" + rowNo).getContents();
//                    String with_dt = sheet.getCell("D" + rowNo).getContents();
//                    String pf_no = sheet.getCell("E" + rowNo).getContents();
                    //   String nomination = sheet.getCell("H" + rowNo).getContents();

                         String latitude_longitude= UTM2Deg("44 N "+pf_no+" "+wage+"");
                         String latitude=latitude_longitude.split("_")[0];
                         String longitude=latitude_longitude.split("_")[1];
                    String query = "INSERT INTO utm_cordinate ( utm_latitude, utm_longitude,latitude,longitude) "
                            + " VALUES(?,?,?,?)";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1,  pf_no);
                    pst.setString(2, wage);
                    pst.setString(3, latitude);
                    pst.setString(4, longitude);
//                    pst.setString(5, fh_name);
//                    pst.setString(6, ac_no);
//                    pst.setString(7, ifsc_code);
//                    pst.setString(8,with_dt);
//                    pst.setString(9, pf_no);
//                    pst.setString(10, age);
//                    pst.setString(11, "");
//                    pst.setString(12, "RICHPACK");
                    int affect = pst.executeUpdate();
                    rowNo++;
                    a++;
                    //checkContent = sheet.getCell("A" + rowNo).getContents() == null || sheet.getCell("A" + rowNo).getContents().isEmpty() ? "" : sheet.getCell("A" + rowNo).getContents().trim();
                }
            }
       message = "Excel Uploaded Successfully.";
            workbook.close();
        } catch (Exception e) {
            System.out.println("ExcelModel getUploadedExcelData exception : " + e);
            //  message = "Error: in your Excel File Please Check";
            //  msgBgColor = COLOR_ERROR;
        }
        if (rowAffacted > 0) {
        }
        //  return dataList;
    }
    public boolean makeDirectory(String dirPathName) {
        boolean result = false;
        System.out.println("dirPathName---" + dirPathName);
        //dirPathName = "C:/ssadvt/sor/organisation" ;
        File directory = new File(dirPathName);
        if (!directory.exists()) {
            try {
                result = directory.mkdirs();
            } catch (Exception e) {
                System.out.println("Error - " + e);
            }
        }
        return result;
    }
    public void setConnection() {
        try {
            System.out.println("hii");
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/health_department?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", "jpss_2", "jpss_1277");
        } catch (Exception e) {
            System.out.println("ReadMailModel setConnection() Error: " + e);
        }
    }
    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
        public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String  UTM2Deg(String UTM)
    {
        String[] parts=UTM.split(" ");
        int Zone=Integer.parseInt(parts[0]);
        char Letter=parts[1].toUpperCase(Locale.ENGLISH).charAt(0);
        double Easting=Double.parseDouble(parts[2]);
        double Northing=Double.parseDouble(parts[3]);
        double Hem;
        if (Letter>'M')
            Hem='N';
        else
            Hem='S';
        double north;
        if (Hem == 'S')
            north = Northing - 10000000;
        else
            north = Northing;
        latitude = (north/6366197.724/0.9996+(1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)-0.006739496742*Math.sin(north/6366197.724/0.9996)*Math.cos(north/6366197.724/0.9996)*(Math.atan(Math.cos(Math.atan(( Math.exp((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*( 1 -  0.006739496742*Math.pow((Easting - 500000) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996 )/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996)))*Math.tan((north-0.9996*6399593.625*(north/6366197.724/0.9996 - 0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996 )*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))-north/6366197.724/0.9996)*3/2)*(Math.atan(Math.cos(Math.atan((Math.exp((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996)))*Math.tan((north-0.9996*6399593.625*(north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3))/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))-north/6366197.724/0.9996))*180/Math.PI;
        latitude=Math.round(latitude*10000000);
        latitude=latitude/10000000;
        longitude =Math.atan((Math.exp((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3))-Math.exp(-(Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2)/3)))/2/Math.cos((north-0.9996*6399593.625*( north/6366197.724/0.9996-0.006739496742*3/4*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.pow(0.006739496742*3/4,2)*5/3*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2* north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4-Math.pow(0.006739496742*3/4,3)*35/27*(5*(3*(north/6366197.724/0.9996+Math.sin(2*north/6366197.724/0.9996)/2)+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/4+Math.sin(2*north/6366197.724/0.9996)*Math.pow(Math.cos(north/6366197.724/0.9996),2)*Math.pow(Math.cos(north/6366197.724/0.9996),2))/3)) / (0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2))))*(1-0.006739496742*Math.pow((Easting-500000)/(0.9996*6399593.625/Math.sqrt((1+0.006739496742*Math.pow(Math.cos(north/6366197.724/0.9996),2)))),2)/2*Math.pow(Math.cos(north/6366197.724/0.9996),2))+north/6366197.724/0.9996))*180/Math.PI+Zone*6-183;
        longitude=Math.round(longitude*10000000);
        longitude=longitude/10000000;
//        System.out.println("latitude="+latitude);
//        System.out.println("longitude="+longitude);
        return latitude+"_"+longitude;
    }
//    public static void main(String... arg) {
//        //PackingDivisionModel qbem = new PackingDivisionModel();
//        try {
//            //  setConnection();
//            //     getExcelData(new File("D:\\wage.xls"));
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
    public List<ShiftLoginBean> showDataBean() {
        List<ShiftLoginBean> list = new ArrayList<ShiftLoginBean>();


        String query = "select utm_cordinate_id,latitude,longitude from utm_cordinate";
        try {
            setConnection();
            java.sql.PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ShiftLoginBean bean = new ShiftLoginBean();
                bean.setCordinate_id(rset.getInt("utm_cordinate_id"));
                bean.setLatitude(rset.getDouble("latitude"));
                bean.setLongitude(rset.getDouble("longitude"));
                list.add(bean);
            }
          closeConnection();
        } catch (Exception e) {
            System.out.println("Error in uploadExcel" + e);
        }
        return list;
    }
}
