/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.model;

import com.additems.tableClasses.ItemNamePropertiesMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class ItemNamePropertiesMapModel {
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
                list.add("No such properties_name exists.");
            }
        }
        catch (Exception e)
        {
            System.out.println("getproperties ERROR inside Model - " + e);
        }
        return list;
    }
    public List getItemName(String q)
    {
        List<String> list = new ArrayList<String>();
        String query = "select Item_name from item_name order by Item_name ";

        try {
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
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside Model - " + e);
        }
        return list;
    }
    public List<ItemNamePropertiesMap> ShowData(int lowerLimit, int noOfRowsToDisplay,ItemNamePropertiesMap itemNamePropertiesMapBean,String searchitem_name,String searchproperties_name)
    {
        List<ItemNamePropertiesMap> list = new ArrayList<ItemNamePropertiesMap>();
        String query = "SELECT inpm.item_name_properties_map_id,p.properties_name,i.item_name,inpm.order_no "
                + " FROM Item_Name_Properties_Map as inpm ,item_name as i , properties as p "
                + " where inpm.item_name_id=i.item_name_id AND inpm.properties_id=p.properties_id "
                + "AND if('"+searchitem_name+"'='', i.item_name LIKE '%%', i.item_name='"+searchitem_name+"')"
                + "AND if('"+searchproperties_name+"'='', p.properties_name LIKE '%%', p.properties_name='"+searchproperties_name+"')"
                + " LIMIT "+lowerLimit+","+noOfRowsToDisplay;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                ItemNamePropertiesMap itemNameBean=new ItemNamePropertiesMap();
                itemNameBean.setItem_name(rs.getString("item_name"));
                itemNameBean.setProperties_name(rs.getString("properties_name"));
                itemNameBean.setItem_name_properties_map_id(rs.getInt("item_name_properties_map_id"));
                itemNameBean.setOrder_no(rs.getInt("order_no"));
                list.add(itemNameBean);
            }
        } catch (Exception e)
        {
            System.out.println("getFuseType ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    public int getNoOfRows(ItemNamePropertiesMap itemNamePropertiesMapBean,String searchitem_name,String searchproperties_name)
    {
        int count=0;
       String query = "SELECT count(*) as count "
                + " FROM Item_Name_Properties_Map as inpm ,item_name as i , properties as p "
                + " where inpm.item_name_id=i.item_name_id AND inpm.properties_id=p.properties_id ";

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
    public boolean insertRecord(ItemNamePropertiesMap itemNameBean) {
    boolean b=false;
        String query = "insert into Item_Name_Properties_Map (item_name_id,properties_id,order_no) values (?,?,?)";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             int item_name_id=getitem_name_id(itemNameBean.getItem_name());
             ps.setInt(1,item_name_id);
             int properties_id=getproperties_name_id(itemNameBean.getProperties_name());
             ps.setInt(2,properties_id);
             ps.setInt(3, getOrderNo(item_name_id)+1);
              int rowsAffected=ps.executeUpdate();
            if (rowsAffected > 0) {
            rowsAffected=0;
                String item_code1=getItem_code(item_name_id);
                if(item_code1.isEmpty()){
                    item_code1=""+item_name_id+"."+properties_id;
                }
            else{
            item_code1=item_code1+"."+properties_id;
                }
            String query1 = "update item_name set item_code='"+item_code1+"' where item_name_id="+item_name_id;
            try {
             ps=(PreparedStatement) connection.prepareStatement(query1);
             rowsAffected=ps.executeUpdate();
            if (rowsAffected > 0) {}
            }catch(Exception e)
            {
                e.printStackTrace();
            }
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
     private String getItem_code(int item_name_id) {
        String query = "SELECT item_code FROM item_name "
                + " where item_name_id= "+item_name_id;
        String item_code="";
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next())
            {
                item_code=rs.getString("item_code");
            }
            if(item_code==null)item_code="";
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    return item_code;
    }
    private int getitem_name_id(String item_name) {
        String query = "SELECT item_name_id FROM item_name as i"
                + " where i.item_name='"+item_name+"'";
        int item_name_id=0;
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next())
            {
                item_name_id=rs.getInt("item_name_id");
            }
        }catch(Exception e)
        {

        }
    return item_name_id;
    }
    private int getproperties_name_id(String properties_name) {
        String query = "SELECT properties_id FROM properties as i "
                + " where i.properties_name='"+properties_name+"'";
        int item_name_id=0;
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next())
            {
                item_name_id=rs.getInt("properties_id");
            }
        }catch(Exception e)
        {

        }
    return item_name_id;
    }
    private int getOrderNo(int item_name_id)
    {
         String query = "SELECT max(order_no) as order_no FROM item_name_properties_map as i "
                + " where i.item_name_id="+item_name_id+"";
        int order_no=0;
        try
        {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while(rs.next())
            {
                order_no=rs.getInt("order_no");
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    return order_no;
    }

    public boolean deleteRecord(ItemNamePropertiesMap itemNameBean) {
        boolean b=false;
        String query = "DELETE FROM Item_Name_Properties_Map where item_name_properties_map_id=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1,itemNameBean.getItem_name_properties_map_id());
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
    public boolean UpdateRecord(ItemNamePropertiesMap itemNameBean)
    {
        String query = "update item_name_properties_map set item_name_id ="+getitem_name_id(itemNameBean.getItem_name())+", properties_id="+getproperties_name_id(itemNameBean.getProperties_name())+" where item_name_properties_map_id="+itemNameBean.getItem_name_properties_map_id()+"";
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
             message = "Cannot Update the record, some error.";
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


    public void setDb_password(String db_password)
    {
        this.db_password = db_password;
    }

    public String getDb_username()
    {
        return db_username;
    }

    public void setDb_username(String db_username)
    {
        this.db_username = db_username;
    }

    public String getDriverClass()
    {
        return driverClass;
    }

    public void setDriverClass(String driverClass)
    {
        this.driverClass = driverClass;
    }

}
