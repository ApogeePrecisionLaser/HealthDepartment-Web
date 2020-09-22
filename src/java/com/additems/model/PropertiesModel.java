/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.model;

import com.additems.tableClasses.Properties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class PropertiesModel {
private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public List getPropertiesName(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "select properties_name from properties order by properties_name ";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();

            while (rs.next())
            {
                list.add(rs.getString("properties_name"));
                count++;
            }

            if (count == 0) {
                list.add("No such Item_name exists.");
            }
        }
        catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    public List<Properties> ShowData(int lowerLimit, int noOfRowsToDisplay,String searchproperties_name)
    {
        List<Properties> list = new ArrayList<Properties>();
        String query = "SELECT * FROM properties  where if('"+searchproperties_name+"'='', properties_name LIKE '%%', properties_name='"+searchproperties_name+"')"
                + " ORDER BY properties_name LIMIT "+lowerLimit+","+noOfRowsToDisplay;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                Properties propertiesBean=new Properties();
                propertiesBean.setProperties_name(rs.getString("properties_name"));
                propertiesBean.setCode(rs.getString("code"));
                propertiesBean.setRemark(rs.getString("remark"));
                propertiesBean.setProperties_id(rs.getInt("properties_id"));
                list.add(propertiesBean);
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchproperties_name)
    {
        int count=0;
       String query = "SELECT count(*) as count FROM properties where if('"+searchproperties_name+"'='', properties_name LIKE '%%', properties_name='"+searchproperties_name+"')";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                count=rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("properties ERROR inside propertiesModel - " + e);
        }
        return count;
    }
    public boolean insertRecord(Properties propertiesBean)
    {
    boolean b=false;
        String query = "insert into properties (properties_name,code,remark) values (?,?,?)";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setString(1,propertiesBean.getProperties_name());
             ps.setString(2,propertiesBean.getCode());
             ps.setString(3,propertiesBean.getRemark());
             int rowsAffected=ps.executeUpdate();
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        } catch (Exception e) {
             message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("properties ERROR inside propertiesModel - " + e);
        }
        return b;
    }

    public boolean deleteRecord(Properties propertiesBean) {
        boolean b=false;
        String query = "DELETE FROM properties where properties_id=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1,propertiesBean.getProperties_id());
             int rowsAffected=ps.executeUpdate();
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }

        } catch (Exception e)
        {
             message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("properties ERROR inside propertiesModel - " + e);
        }
         return b;
    }
    public boolean UpdateRecord(Properties propertiesBean)
    {
        String query = "update properties set properties_name='"+propertiesBean.getProperties_name()+"',remark='"+propertiesBean.getRemark()+"',code='"+propertiesBean.getCode()+"' where properties_id="+propertiesBean.getProperties_id()+"";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             int rowsAffected=ps.executeUpdate();
             if (rowsAffected > 0) {
            message = "Record Update successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        } catch (Exception e) {
             message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        boolean b=false;
        return b;
    }
        public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }

    public String getDb_password() {
        return db_password;
    }


    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }

    public String getDb_username() {
        return db_username;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }

    public String getDriverClass() {
        return driverClass;
    }

    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }
}
