/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.dbCon;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
//import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author jpss
 */
public class DBConnection {

   // private static BasicDataSource dataSource = null;
    private static Connection conn = null;
    private Connection db_connection;
    private Connection generic_connection;
    private String driverClass;
    private String connectionString;
    private String db_userName;
    private String db_userPassword;
    private Map<String, Integer>  dbUserIdMap = new HashMap<String, Integer>();

    public Map<String, Integer> getDbUserIdMap() {
        return dbUserIdMap;
    }

    public void setDbUserIdMap(Map<String, Integer> dbUserIdMap) {
        this.dbUserIdMap = dbUserIdMap;
    }

    public Connection setConnection(String driverClass, String connectionString, String db_userName, String db_userPasswrod) {
        Connection conn = null;
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(connectionString, db_userName, db_userPasswrod);
        } catch (Exception e) {
            System.out.println("DBConnection setConnection(Connection con) Error: " + e);
        }
        return conn;
    }

    public Map<String, Connection> getDBConnectionFromGeneric() {
        boolean result = true;
        Map<String, Connection>  dbConMap = new HashMap<String, Connection>();
        if (generic_connection != null) {
            try {
                PreparedStatement ps = generic_connection.prepareStatement("SELECT db_name from meter_generic.db WHERE active = 'Y'");
                ResultSet rs = ps.executeQuery();
                while (rs.next() && result) {
                    String db_name = rs.getString("db_name");
                    int i = connectionString.lastIndexOf("/");
                    connectionString = connectionString.substring(0, i);
                    connectionString = connectionString + "/" + db_name;
                    int user_id = setUserFullDetail();
                    if (user_id > 0) {
                        db_connection = setConnection(driverClass, connectionString, db_userName, db_userPassword);
                        if (db_connection != null) {
                            dbConMap.put(db_name, db_connection);
                            dbUserIdMap.put(db_name, user_id);
                        }
                    }
                }
                rs.close();
                ps.close();
            } catch (Exception e) {
                System.out.println("DBConnection getDBConnectionFromGeneric Error: " + e);
            }
        }
        return dbConMap;
    }

    public int setUserFullDetail() {
        int user_id = 0;

        String query = " SELECT u.user_id,db_user_name, db_user_pass "
                + "FROM `user` AS u, user_roles AS ur, user_role_map AS urm "
                + "WHERE u.user_id = urm.user_id "
                + "AND ur.user_role_id = urm.user_role_id "
                + "AND role_name = 'Super Admin';";
        try {
            Class.forName(driverClass);
            Connection con = DriverManager.getConnection(connectionString, "guest", "guest");//---------------------
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rst = pst.executeQuery();
            while (rst.next()) {
                user_id = rst.getInt("user_id");
                db_userName = rst.getString("db_user_name");
                db_userPassword = rst.getString("db_user_pass");
                System.out.println("&&&&&&&&&&&connectionString : " + connectionString + " User Name :" + db_userName + " user password :" + db_userPassword);
            }
            con.close();
        } catch (Exception e) {
            System.out.println("DBConnection setUserFullDetail() Error: " + e);
        }
        return user_id;
    }

//    public static BasicDataSource init(ServletContext ctx,HttpSession session) {
//        dataSource = new BasicDataSource();
//        dataSource.setDriverClassName(ctx.getInitParameter("driverClass"));
//        dataSource.setUsername((String) session.getAttribute("db_userName"));
//        dataSource.setPassword((String) session.getAttribute("db_userPasswrod"));
//        dataSource.setUrl(ctx.getInitParameter("connectionString"));
//        return dataSource;
//    }

//    static  {
//        FileRename file= new FileRename();
//        try {
//            FileRename.getConnection();
//        } catch (SQLException ex) {
//           System.out.println(" Error in creating connection");
//        }
//        file.getfileList();
//    }
    public static synchronized Connection getConnection(ServletContext ctx, HttpSession session) throws SQLException {
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = DriverManager.getConnection((String) session.getAttribute("connectionString"), (String) session.getAttribute("db_user_name"), (String) session.getAttribute("db_user_password"));
        } catch (Exception e) {
            System.out.println("DBConncetion getConnection() Error: " + e);
        }
        return conn;
    }

    public static synchronized Connection getConnection(ServletContext ctx) throws SQLException {
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = DriverManager.getConnection((String) ctx.getInitParameter("connectionString"), (String) ctx.getInitParameter("db_user_name"), (String) ctx.getInitParameter("db_user_password"));
        } catch (Exception e) {
            System.out.println("DBConncetion getConnection() Error: " + e);
        }
        return conn;
    }


    public static synchronized Connection getConnectionForUtf(ServletContext ctx, HttpSession session) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = (Connection) DriverManager.getConnection((String) session.getAttribute("connectionString") + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", (String) session.getAttribute("db_user_name"), (String) session.getAttribute("db_user_password"));
        } catch (Exception e) {
            System.out.println(" setConnection() Error: " + e);
        }
        return conn;
    }

     public static synchronized Connection getConnectionForUtf(ServletContext ctx) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = (Connection) DriverManager.getConnection((String) ctx.getInitParameter("connectionString") + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", (String) ctx.getInitParameter("db_user_name"), (String) ctx.getInitParameter("db_user_password"));
        } catch (Exception e) {
            System.out.println(" getConnectionForUtf() Error: " + e);
        }
        return conn;
    }

    public static void closeConncetion(Connection con) {

        try {
            con.close();
        } catch (Exception e) {
            System.out.println("DBConncetion closeConnection() Error: " + e);
        }

    }

    /*controller part
    try {
    model.setConnection(DBConnection.getConnection(ctx, session));
    } catch (Exception e) {
    System.out.println("error in StockDocumentTypeController setConnection() calling try block"+e);
    }
     */
    /*model part
    public void setConnection(Connection con) {
    try {

    connection = con;
    } catch (Exception e) {
    System.out.println("StockDocumentTypeModel setConnection() Error: " + e);
    }
    }
     */

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public Connection getDb_connection() {
        return db_connection;
    }

    public void setDb_connection(Connection db_connection) {
        this.db_connection = db_connection;
    }

    public String getDb_userName() {
        return db_userName;
    }

    public void setDb_userName(String db_userName) {
        this.db_userName = db_userName;
    }

    public String getDb_userPassword() {
        return db_userPassword;
    }

    public void setDb_userPassword(String db_userPassword) {
        this.db_userPassword = db_userPassword;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    public Connection getGeneric_connection() {
        return generic_connection;
    }

    public void setGeneric_connection(Connection generic_connection) {
        this.generic_connection = generic_connection;
    }
}
