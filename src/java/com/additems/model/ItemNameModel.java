/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.model;

import com.additems.tableClasses.ItemName;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class ItemNameModel {
 private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public List getItemName(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "select Item_name from item_name order by Item_name ";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next())
            {
                list.add(rs.getString("Item_name"));
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
    public List<ItemName> ShowData(int lowerLimit, int noOfRowsToDisplay,String searchitem_name)
    {
        List<ItemName> list = new ArrayList<ItemName>();
        String query = "SELECT * FROM item_name where if('"+searchitem_name+"'='', item_name LIKE '%%', item_name='"+searchitem_name+"')"
                + " ORDER BY item_name LIMIT "+lowerLimit+","+noOfRowsToDisplay;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                ItemName itemNameBean=new ItemName();
                itemNameBean.setItem_name(rs.getString("item_name"));
                String item_code=rs.getString("item_code");
                if(item_code==null)item_code="";
                itemNameBean.setItem_code(item_code);
                itemNameBean.setRemark(rs.getString("remark"));
                itemNameBean.setItem_name_id(rs.getInt("item_name_id"));
                    list.add(itemNameBean);
            }
        } catch (Exception e)
        {
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    public int getNoOfRows(String searchitem_name)
    {
        int count=0;
       String query = "SELECT count(*) as count FROM item_name where if('"+searchitem_name+"'='', item_name LIKE '%%', item_name='"+searchitem_name+"')";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                count=rs.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        return count;
    }
    public boolean insertRecord(ItemName itemNameBean) {
    boolean b=false;
        String query = "insert into item_name (item_name,remark) values (?,?)";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setString(1,itemNameBean.getItem_name());
             ps.setString(2,itemNameBean.getRemark());
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
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        return b;
    }
    public boolean UpdateRecord(ItemName itemNameBean)
    {
        String query = "update item_name set item_name='"+itemNameBean.getItem_name()+"',remark='"+itemNameBean.getRemark()+"' where item_name_id="+itemNameBean.getItem_name_id()+"";
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
    public boolean deleteRecord(ItemName itemNameBean) {
        boolean b=false;
        String query = "DELETE FROM item_name where item_name_id=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1,itemNameBean.getItem_name_id());
             int rowsAffected=ps.executeUpdate();
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }

        } catch (Exception e) {
             message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
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
