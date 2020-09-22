/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;
import javax.servlet.ServletContext;

/**
 *
 * @author Shobha
 */
public class TripSchedularModel extends TimerTask{



     private ServletContext ctx;
    private static Connection connection;
    Date dt = new Date();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String cut_dt = df.format(dt);

    public void setCtx(ServletContext ctx) {
        this.ctx = ctx;
    }


    public void run() {
        try {

            getAndInsertData();

        } catch (Exception ex) {
            System.out.println(" run() Error: " + ex);
        }
    }

 public void getAndInsertData() throws SQLException{


     int error_log_msg_id=0;
     int node_id=0;
     int overheadtank_id=0;

        String query=" select tvm.trip_vehicle_map_id,tvm.trip_id,vkp.vehicle_id,tvm.date "
                     +" from trip_vehicle_map tvm,vehicle_key_person vkp "
                     +" where tvm.vehicle_key_person_id=vkp.vehicle_key_person_id "
                     + " AND IF('" + cut_dt + "'='', tvm.date LIKE '%%',tvm.date >='" + cut_dt + "') ";

        try{
        PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query);
        ResultSet rs = ps1.executeQuery();
        while(rs.next()){

            int trip_vehicle_map_id=rs.getInt("trip_vehicle_map_id");
            int trip_id=rs.getInt("trip_id");
            int vehicle_id=rs.getInt("vehicle_id");
            String date=rs.getString("date");

            insertRecords(trip_vehicle_map_id,trip_id,vehicle_id,date);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

        public void insertRecords(int trip_vehicle_map_id,int trip_id,int vehicle_id,String date){


         String query="insert into trip_log_file(trip_vehicle_map_id,trip_id,vehicle_id,date)  "
                        +" value(?,?,?,?)";

        try{
        PreparedStatement ps1 = (PreparedStatement) connection.prepareStatement(query);
        ps1.setInt(1, trip_vehicle_map_id);
        ps1.setInt(2, trip_id);
        ps1.setInt(3, vehicle_id);
        ps1.setString(4, date);
        int i = ps1.executeUpdate();
        if(i==1){
            System.out.println("Record inserted successfully");

            }
        }
        catch(Exception e){
            System.out.println(e);
        }
 }




     public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        TripSchedularModel.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
