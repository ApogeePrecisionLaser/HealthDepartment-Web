/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.webServices;

import com.healthDepartment.dbCon.DBConnection;
import com.healthDepartment.general.model.MapDetailClass;
import com.healthDepartment.shift.model.ShiftLoginModel;
import com.healthDepartment.shift.model.ShiftTimeModel;
import com.healthDepartment.vehicle.model.VehicleWeightModel;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import sun.misc.BASE64Decoder;

/**
 *
 * @author Administrator
 */
@Path("/shift")
public class HealthDepartmentWebServices {

    @Context
    ServletContext serveletContext;
    Connection connection = null;
    //private  String zone;
    // private  String ward;
    //private  String area;

    @POST
    @Path("/detail")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //@Produces(MediaType.TEXT_PLAIN)
    public JSONObject personDetail(String emp_code) throws Exception {
        
        JSONObject obj = new JSONObject();
        JSONArray arrayObj = new JSONArray();
        Response res = null;
        System.out.println("ShiftWebServices");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in personDetail() in RideWebservices : " + ex);
        }
        JSONObject jsonObj = slm.getAreaDetails(emp_code);
        String zone = jsonObj.getString("zone_name");
        String ward = jsonObj.getString("ward_name");
        String area = jsonObj.getString("area_name");
        String designation = jsonObj.getString("designation");
        System.out.println("Data Retrived : " + jsonObj);
        arrayObj.put(jsonObj);
        obj.put("Data", arrayObj);
        arrayObj = slm.getBeneficiaryDetails(zone, ward, area);
        System.out.println("Data Retrived : " + arrayObj);
        obj.put("BeneficiaryData", arrayObj);
        arrayObj = slm.getCityDetails(zone, ward, area);
        System.out.println("Data Retrived : " + arrayObj);
        obj.put("city_location", arrayObj);
        arrayObj = slm.getReasonDetails();
        System.out.println("Data Retrived : " + arrayObj);
        obj.put("reason", arrayObj);
        arrayObj = slm.getOccupationTypeDetails();
        System.out.println("Data Retrived : " + arrayObj);
        obj.put("occupation_type", arrayObj);
        if (designation.equals("ड्राइवर")) {
            arrayObj = slm.getVehicleKeyPersonDetails(emp_code);
            System.out.println("Data Retrived : " + arrayObj);
            obj.put("VehicleDetail", arrayObj);

            arrayObj = slm.getvehicleDetails(zone, ward, area);
            System.out.println("Data Retrived : " + arrayObj);
            obj.put("AllVehicle", arrayObj);
        }
        res = Response.ok(obj, MediaType.APPLICATION_JSON).build();
        slm.closeConnection();
        return obj;
    }

    @POST
    @Path("/insert")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String InsertRecord(JSONObject jsonObj) throws Exception {
        JSONObject obj = new JSONObject();
        Response res = null;
        String reply = "";
        String beneficiary_id, reason_id, emp_code, status, date_time;
        beneficiary_id = jsonObj.get("beneficiary_id").toString();
        reason_id = jsonObj.get("reason").toString();
        emp_code = jsonObj.get("emp_code").toString();
        status = jsonObj.get("status").toString();
        date_time = jsonObj.get("date").toString();
        //mobile_no = jsonObj.get("mobile_no").toString();
        System.out.println("insertRecord");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        int result = slm.insertShiftRecord(beneficiary_id, reason_id, date_time, emp_code, status);
        String id_attendence = slm.getskpmId(emp_code);
        String skpm_id = id_attendence.split("_")[0];
        String attendence = id_attendence.split("_")[1];
        if (attendence.equals("N")) {
            result = slm.updateAttendence(skpm_id);
            JSONObject jsonObj1 = slm.getAreaDetails(emp_code);
            String zone = jsonObj1.getString("zone_name");
            String ward = jsonObj1.getString("ward_name");
            String area = jsonObj1.getString("area_name");
            slm.getBeneficiaryDetails1(zone, ward, area);
        }
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        slm.closeConnection();
        return reply;
    }

    @POST
    @Path("/shiftFinish")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String shiftFinish(String emp_id) throws Exception {
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        JSONObject jsonObj = slm.getAreaDetails(emp_id);
        String zone = jsonObj.getString("zone_name");
        String ward = jsonObj.getString("ward_name");
        String area = jsonObj.getString("area_name");
        System.out.println("Data Retrived : " + jsonObj);
        String id_attendence = slm.getskpmId(emp_id);
        String skpm_id = id_attendence.split("_")[0];
        slm.updateFinishTime(skpm_id);
        slm.insertAllRemainingBeneficary(emp_id, zone, ward, area);
        slm.closeConnection();
        return "Success";
    }

    @POST
    @Path("/insertKeyPerson")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String InsertKeyPerson(JSONObject jsonObj) throws Exception {
        JSONObject obj = new JSONObject();
        Response res = null;
        String reply = "";
        String salutation, person_name, father_name, age, address_line1, address_line2, mobile_no1, email_id1, no_of_person, city_location, is_residencial, occupation_type, occupation_name, key_person_id;
        String latitude = "";
        String longitude = "";
        salutation = jsonObj.get("salutation").toString();
        person_name = jsonObj.get("person_name").toString();
        father_name = jsonObj.get("father_name").toString();
        age = jsonObj.get("age").toString();
        address_line1 = jsonObj.get("address_line1").toString();
        address_line2 = jsonObj.get("address_line2").toString();
        mobile_no1 = jsonObj.get("mobile_no").toString();
        email_id1 = jsonObj.get("email").toString();
        no_of_person = jsonObj.get("no_of_person").toString();
        city_location = jsonObj.get("location").toString();
        is_residencial = jsonObj.get("is_residencial").toString();
        occupation_type = jsonObj.get("occupationTypeId").toString();
        occupation_name = jsonObj.get("occupation_name").toString();
        key_person_id = jsonObj.get("key_person_id").toString();
        if (Integer.parseInt(key_person_id) == 0) {
            latitude = jsonObj.get("latitude").toString();
            longitude = jsonObj.get("longitude").toString();
        }

        System.out.println("insertRecord");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        int result = slm.insertKeyPerson(salutation, person_name, father_name, age, address_line1, address_line2, mobile_no1, email_id1, latitude, longitude, key_person_id, occupation_type, occupation_name, no_of_person, city_location, is_residencial);
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        slm.closeConnection();
        return reply;
    }

//    @POST
//    @Path("/insertCordinate")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String InsertCordinate(JSONObject jsonObj) throws Exception {
//        JSONObject obj = new JSONObject();
//        Response res = null;
//        String reply = "";
//        int result = 0;
//        String latitude, longitude, imei_no, emp_code, mobile_no;
//        imei_no = jsonObj.get("deviceid").toString();
//        emp_code = jsonObj.get("empcode").toString();
//        latitude = jsonObj.get("latitude").toString();
//        longitude = jsonObj.get("longitude").toString();
//        if (jsonObj.get("phoneno") == null) {
//            mobile_no = "";
//        } else {
//            mobile_no = jsonObj.get("phoneno").toString();
//        }
//        System.out.println("insertRecord");
//        ShiftLoginModel slm = new ShiftLoginModel();
//        try {
//            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
//        } catch (Exception ex) {
//            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
//        }
//        slm.insertCordinate(latitude, longitude, emp_code, imei_no, mobile_no);
//        System.out.println("record insert in cordinate table");
//        String id_attendence = slm.getskpmId(emp_code);
//        String skpm_id = id_attendence.split("_")[0];
//        String attendence = id_attendence.split("_")[1];
//
//        if (attendence.equals("N")) {
//            JSONObject Obj = slm.getAreaDetails(emp_code);
//            String zone = Obj.getString("zone_name");
//            String ward = Obj.getString("ward_name");
//            String area = Obj.getString("area_name");
//            List destination = slm.getLocationCordinates(zone, ward, area);
//            Iterator itr = destination.iterator();
//            while (itr.hasNext()) {
//                String data = (String) itr.next();
//                int distance = MapDetailClass.getDistance(latitude + "," + longitude, data);
//                if (distance < 30) {
//                    result = slm.updateAttendence(skpm_id);
//                    break;
//                }
//            }
//        }
//        if (result > 0) {
//            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
//            res = Response.ok(jsonObj, MediaType.APPLICATION_JSON).build();
//        } else {
//        }
//        slm.closeConnection();
//
//        return reply;
//    }

    @POST
    @Path("/vehicleWeight")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String insertVehicleWeightRecord(JSONObject jsonObj) throws Exception {

        String reply = "";
        VehicleWeightModel vwm = new VehicleWeightModel();
        try {
            vwm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        String vehicle_code, weight;
        vehicle_code = jsonObj.get("vehicle_code").toString();
        weight = jsonObj.get("weight").toString();
        int vehicle_id = vwm.getVehicleId(vehicle_code);
        int affected = 0;

        System.out.println("Receiving data...");
        String path = "C:\\ssadvt_repository\\HealthDepartment\\vechileWeightImage";
        vwm.makeDirectory(path);
        FileOutputStream outputStream = null;
        byte[] fileAsBytes = null;
        String file = "";
        org.json.JSONArray jsonArray = null;
        int size = 0;
        org.json.JSONObject jsn = new org.json.JSONObject(jsonObj.toString());
        if (jsonObj.get("images") != null) {
            jsonArray = jsn.getJSONArray("images");//new JSONArray(json.get("images") == null ? "" : json.get("images").toString());
            size = jsonArray.length();
        }
        org.json.JSONObject jsonObject = null;
        for (int i = 0; i < size; i++) {
            try {
                System.out.println(" Image Uploading.....");
                jsonObject = jsonArray.getJSONObject(i);
                fileAsBytes = new BASE64Decoder().decodeBuffer(jsonObject.getString("byte_arr"));
                String fileName = jsonObject.getString("imgname");
                file = path + "\\" + fileName;
                outputStream = new FileOutputStream(file);
                outputStream.write(fileAsBytes);
                outputStream.close();
            } catch (Exception ex) {
                System.out.println("ERROR : in saveComplainReportFiles() in ComplainAppWebservices : " + ex);
            }
        }
        int result = vwm.saveVehicleWeightRecord_webService(vehicle_id, weight, file);
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        return reply;
    }

    @POST
    @Path("/vehicleCordinates")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String rideCordinates(JSONObject jsonObj) throws Exception {
        JSONObject obj = new JSONObject();
        Response res = null;
        String rideStatus="", ride_id, date_time="", latitude="", longitude="";
       
        rideStatus = jsonObj.get("status").toString();
        latitude = jsonObj.get("latitude").toString();
        longitude = jsonObj.get("longitude").toString();
       // date_time = jsonObj.get("date_time").toString();
        System.out.println("rideCordinates");
       ShiftLoginModel slm = new ShiftLoginModel();
       try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        String status = "success";
        
        
        int result = slm.InsertLatLong(rideStatus, latitude, longitude, date_time);
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            status = "Not Successfully";
        }
        slm.closeConnection();
        return status;
    }
    
    
    
    
    
    
    
    
    
    
    
    @POST
    @Path("/insertVehicle")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String InsertVehicleRecord(JSONObject jsonObj) throws Exception {
        JSONObject obj = new JSONObject();
        Response res = null;
        String reply = "";
        String beneficiary_id, vehicle_code, emp_code, latitude, longitude, date_time, status;
        beneficiary_id = jsonObj.get("beneficiary_id").toString();
        emp_code = jsonObj.get("emp_code").toString();
        latitude = jsonObj.get("latitude").toString();
        date_time = jsonObj.get("date_time").toString();
        longitude = jsonObj.get("longitude").toString();
        status = jsonObj.get("weight").toString();
        System.out.println("insertRecord");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        int result = slm.insertVehicle(emp_code, beneficiary_id, latitude, longitude, date_time, status);

        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        slm.closeConnection();
        return reply;
    }

    @POST
    @Path("/insertVehicle1")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String InsertVehicleRecord1(JSONObject jsonObj) throws Exception {
        int result = 0;
        String reply = "";
        String beneficiary_id, emp_code, latitude, longitude, date_time, status;        org.json.JSONObject jsn = new org.json.JSONObject(jsonObj.toString());
        org.json.JSONArray jsonArray = jsn.getJSONArray("Nfc_Driver");
        int size = jsonArray.length();

        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        for (int i = 0; i < size; i++) {
            beneficiary_id = jsonObj.get("beneficiary_id").toString();
            emp_code = jsonObj.get("emp_code").toString();
            latitude = jsonObj.get("latitude").toString();
            date_time = jsonObj.get("date").toString();
            longitude = jsonObj.get("longitude").toString();
            status = jsonObj.get("weight").toString();
            System.out.println("insertRecord");
            result = slm.insertVehicle(emp_code, beneficiary_id, latitude, longitude, date_time, status);
        }
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        slm.closeConnection();
        return reply;
    }

    @POST
    @Path("/vehicle_Attendance")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String vehicleAttendence(JSONObject jsonObj) throws Exception {
        int result = 0;
        String reply = "";
        String vehicle_code, emp_code;
        vehicle_code = jsonObj.get("beneficiary_id").toString();
        emp_code = jsonObj.get("emp_code").toString();
        System.out.println("insertRecord");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }
        String id_attendence = slm.getskpmId(emp_code);
        String skpm_id = id_attendence.split("_")[0];
        String attendence = id_attendence.split("_")[1];

        String skpm_idFromVehicleCode = slm.getSkpmIdFromVehicleCode(vehicle_code);
        if (skpm_id.equals(skpm_idFromVehicleCode)) {
            if (attendence.equals("N")) {
                result = slm.updateAttendence(skpm_id);
                result = slm.updateVehicleVerify(skpm_id);
            }
        }
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        slm.closeConnection();
        return reply;
    }


    @POST
    @Path("/update_vehicle")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String vehicleUpdate(JSONObject jsonObj) throws Exception {
        int result = 0;
        String reply = "";
        String vehicle_code;
        vehicle_code = jsonObj.get("beneficiary_id").toString();

        System.out.println("insertRecord");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }

        result=slm.InsertErrorLog(vehicle_code);
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        slm.closeConnection();
        return reply;
    }
    @POST
    @Path("/fingerPrint")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String FingerPrint(JSONObject jsonObj) throws Exception {
        int result = 0;
        String reply = "";
        String vehicle_code;
        vehicle_code = jsonObj.get("beneficiary_id").toString();

        System.out.println("insertRecord");
        ShiftLoginModel slm = new ShiftLoginModel();
        try {
            slm.setConnection(DBConnection.getConnectionForUtf(serveletContext));
        } catch (Exception ex) {
            System.out.println("ERROR : in insertRecord() in ShiftWebservices : " + ex);
        }

        result=slm.InsertErrorLog(vehicle_code);
        if (result > 0) {
            System.out.println("Data Retrived : " + jsonObj + " : Saved...");
            reply = "Successfully";
        } else {
            System.out.println("Data Retrived : " + jsonObj + " : Not Saved Some Error...");
            reply = " Not Successfully";
        }
        slm.closeConnection();
        return reply;
    }
}
