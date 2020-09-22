/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.general.model;

import java.sql.Connection;
import java.sql.ResultSet;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Shobha
 */
public class LoginModel {
 private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    //HttpSession session = request.getSession();

public String checkLogin(String user_name,String password,HttpSession session){
    int login_id=0;
    String designation="";
     String query = " select login_id,d.designation,l.key_person_id from login l,key_person kp,designation as d where user_name='"+user_name+"' and password='"+password+"' "
             + " AND kp.key_person_id=l.key_person_id and kp.designation_id=d.designation_id ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                login_id = rs.getInt("login_id");
                designation=rs.getString("designation");
                int  key_person_id=rs.getInt("key_person_id");
                session.setAttribute("key_person_id", key_person_id);
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }


    return designation;

}

        public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println(" closeConnection() Error: " + e);
        }
    }
}
