/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.model;

import com.additems.tableClasses.Item;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class ItemModel {
private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public List ShowData(int lowerLimit, int noOfRowsToDisplay,String searchitem_name)
    {
        List<Item> list = new ArrayList<Item>();
        String query = " SELECT item_id, i.item_name_id,n.node_name, "
                     + " i.created_date,i.created_by, i.remark, i.revision_no, properties_1, properties_2, properties_3, properties_4, "
                     + " properties_5, i.active"
                     + " FROM item as i "
                     + " LEFT JOIN property_details as p "
                     + " ON  i.properties_1=p.property_details_id "
                     + " AND i.properties_2=p.property_details_id "
                     + " AND i.properties_3=p.property_details_id "
                     + " AND i.properties_3=p.property_details_id "
                     + " AND i.properties_4=p.property_details_id "
                     + " LEFT JOIN item_name as inn ON i.item_name_id=inn.item_name_id"
                     + " LEFT JOIN node1 as n ON i.node_id=n.node_id "
                     + " where i.active='Y' "
                     + " AND if('"+searchitem_name+"'='', inn.item_name LIKE '%%', inn.item_name='"+searchitem_name+"')"
                     + " LIMIT "+lowerLimit+","+noOfRowsToDisplay;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                Item propertiesBean=new Item();
                propertiesBean.setItem_name_id(rs.getInt("item_name_id"));
                propertiesBean.setItem_name(getItem_name(rs.getInt("item_name_id")));
                propertiesBean.setNode_name(rs.getString("node_name"));
               // propertiesBean.setItem_rate(rs.getDouble("item_rate"));
                propertiesBean.setActive(rs.getString("active"));
                propertiesBean.setItem_id(rs.getInt("item_id"));
                //propertiesBean.setItem_serial_no(rs.getString("item_serial_no"));
                //propertiesBean.setItem_unit(rs.getString("item_unit"));
                //propertiesBean.setRate_applicable_from(rs.getString("rate_applicable_from"));
                //propertiesBean.setRate_applicable_to(rs.getString("rate_applicable_to"));
                String id_value=getProperties_id_And_value(rs.getInt("properties_1"));
                if(!id_value.isEmpty())
                {
                propertiesBean.setProperties_1(Integer.parseInt(id_value.split("-")[2]));
                propertiesBean.setProperties_value_1(id_value.split("-")[1]);
                propertiesBean.setProperties_name_1(id_value.split("-")[0]);
                }
                id_value=getProperties_id_And_value(rs.getInt("properties_2"));
                if(!id_value.isEmpty())
                {
                propertiesBean.setProperties_2(Integer.parseInt(id_value.split("-")[2]));
                propertiesBean.setProperties_value_2(id_value.split("-")[1]);
                propertiesBean.setProperties_name_2(id_value.split("-")[0]);
                }
                id_value=getProperties_id_And_value(rs.getInt("properties_3"));
                if(!id_value.isEmpty())
                {
                propertiesBean.setProperties_3(Integer.parseInt(id_value.split("-")[2]));
                propertiesBean.setProperties_value_3(id_value.split("-")[1]);
                propertiesBean.setProperties_name_3(id_value.split("-")[0]);
                }
                id_value=getProperties_id_And_value(rs.getInt("properties_4"));
                if(!id_value.isEmpty())
                {
                propertiesBean.setProperties_4(Integer.parseInt(id_value.split("-")[2]));
                propertiesBean.setProperties_value_4(id_value.split("-")[1]);
                propertiesBean.setProperties_name_4(id_value.split("-")[0]);
                }
                id_value=getProperties_id_And_value(rs.getInt("properties_5"));
                if(!id_value.isEmpty())
                {
                propertiesBean.setProperties_5(Integer.parseInt(id_value.split("-")[2]));
                propertiesBean.setProperties_value_5(id_value.split("-")[1]);
                propertiesBean.setProperties_name_5(id_value.split("-")[0]);
                }
                list.add(propertiesBean);
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    private String getItem_name(int aInt)
    {
        String query = "select item_name from item_name where item_name_id="+aInt+" ";
        String value="";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next())
            {
                value=rs.getString("item_name");

            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return value;
    }
    private String getProperties_id_And_value(int aInt)
    {
        String query = "select p.properties_id,properties_name,value from property_details as pd, properties as p where p.properties_id=pd.properties_id AND property_details_id="+aInt+" ";
        String id_value="";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();

            while (rs.next())
            {
                id_value=rs.getString("properties_name")+"-"+rs.getString("value")+"-"+rs.getInt("properties_id");

            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return id_value;
    }
    public int getNoOfRows(String searchitem_name)
    {
        int count=0;
        String query = " SELECT count(*) as count "
                     + "FROM item as i "
                     + " LEFT JOIN property_details as p "
                     + " ON  i.properties_1=p.property_details_id "
                     + " AND i.properties_2=p.property_details_id "
                     + " AND i.properties_3=p.property_details_id "
                     + " AND i.properties_3=p.property_details_id "
                     + " AND i.properties_4=p.property_details_id"
                     + " LEFT JOIN item_name as inn ON i.item_name_id=inn.item_name_id "
                     + " LEFT JOIN node1 as n ON i.node_id=n.node_id "
                     + " where i.active='Y' "
                     + " AND if('"+searchitem_name+"'='', inn.item_name LIKE '%%', inn.item_name='"+searchitem_name+"')";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                count=rs.getInt("count");
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return count;
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
                list.add("No such  Item_name exists.");
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    public List getSelect(String q, String item)
    {
        List list = new ArrayList();
        int count=0;
         String query = " select p.properties_id,p.properties_name"
                     + " from item_name_properties_map as ipmap ,properties as p, item_name as i "
                     + " where ipmap.properties_id=p.properties_id "
                     + " AND ipmap.item_name_id=i.item_name_id AND i.item_name='"+item+"' ";
                     String output="";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                count++;
                output=output+rs.getInt("properties_id")+"-"+rs.getString("properties_name")+"&#";
            }
            list.add(output);

        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
    return list;
    }
    public List getProperties(String q, String properties_id)
    {
        List list = new ArrayList();
        int count=0;
         String query = "SELECT value FROM property_details where properties_id='"+properties_id+"' order by value";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                list.add(rs.getString("value"));
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return list;
    }
    public boolean insertRecord(Item itemBean,int localVal)
    {
        String add="";
        String addval="";
        boolean b=false;
        if(localVal>0)
        {
            add=",item_id";
            addval=",?";
        }
         String query = "insert into item (item_name_id, "
                 + "  revision_no, properties_1, properties_2, properties_3, properties_4, properties_5,node_id ,active"+add+" ) "
                 + "values (?,?,?,?,?,?,?,?,?"+addval+")";

        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1, itemBean.getItem_name_id());
//             ps.setString(2,itemBean.getItem_serial_no());
//             ps.setDouble(3,itemBean.getItem_rate());
//             ps.setString(4, itemBean.getRate_applicable_from());
//             ps.setString(5,itemBean.getRate_applicable_to());             //pstmt.setNull(2, java.sql.Types.NULL);
//             ps.setString(6,itemBean.getItem_unit());
             if(localVal==0)
                ps.setInt(2,0);
             else
                 ps.setInt(2, getMaxRevisionNo(itemBean.getItem_id())+1);
             int property_id=getPropertiesId(itemBean.getProperties_1(),itemBean.getProperties_value_1());
             if(property_id==0)
                 ps.setNull(3, java.sql.Types.NULL);
             else
                ps.setInt(3,property_id);
             property_id=getPropertiesId(itemBean.getProperties_2(),itemBean.getProperties_value_2());
             if(property_id==0)
                 ps.setNull(4, java.sql.Types.NULL);
             else
                 ps.setInt(4,property_id);
             property_id=getPropertiesId(itemBean.getProperties_3(),itemBean.getProperties_value_3());
             if(property_id==0)
                 ps.setNull(5, java.sql.Types.NULL);
             else
                 ps.setInt(5,property_id);
             property_id=getPropertiesId(itemBean.getProperties_4(),itemBean.getProperties_value_4());
             if(property_id==0)
                 ps.setNull(6, java.sql.Types.NULL);
             else
                 ps.setInt(6,property_id);
             property_id=getPropertiesId(itemBean.getProperties_5(),itemBean.getProperties_value_5());
             if(property_id==0)
                 ps.setNull(7, java.sql.Types.NULL);
             else
                 ps.setInt(7,property_id);
             ps.setInt(8,getNodeId(itemBean.getNode_name()));
             ps.setString(9, "Y");
             if(localVal>0)
                ps.setInt(10,itemBean.getItem_id());

            int rowsAffected=ps.executeUpdate();
            if (rowsAffected > 0)
          {
                b=true;
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        }catch(Exception e)
        {
             message = "Cannot Save the record, some error.";
            msgBgColor = COLOR_ERROR;
            e.printStackTrace();
        }
    return b;
    }
       public int getNodeId(String Node_name) {
        int node_id = 0;
        String query = "SELECT node_id FROM node1 WHERE active='Y' and node_name = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, Node_name);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                node_id = rset.getInt("node_id");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getNodeId() Error: " + e);
        }

        return node_id;
    }
    public int getItemNameId(String item_name)
    {//
        int id=0;
        String query = "SELECT item_name_id FROM item_name where item_name='"+item_name+"'";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                id=rs.getInt("item_name_id");
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return id;
    }
    private int getPropertiesId(int properties_id,String value)
    {
        int id=0;
        String query = "SELECT property_details_id FROM property_details where properties_id='"+properties_id+"' AND value='"+value+"' order by value";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                id=rs.getInt("property_details_id");
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return id;
    }
    private int getMaxRevisionNo(int item_id)
    {
        int id=0;
        String query = "SELECT max(revision_no) as revision_no  FROM item where item_id="+item_id+"";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                id=rs.getInt("revision_no");
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return id;
    }
    public boolean UpdateRecord(Item itemBean) throws SQLException
    {
        String query ="update item set active=? where item_id=?";// "update properties set properties_name='"+itemNameBean.getItem_name()+"' where properties_id="+itemNameBean.getItem_name_id()+"";
        try {
            connection.setAutoCommit(false);
            int j=1;
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setString(1, "N");
             ps.setInt(2, itemBean.getItem_id());
             int rowsAffected=ps.executeUpdate();
             if (rowsAffected > 0)
             {
            boolean b=insertRecord(itemBean,j);
                if(b)
                {
            message = "Record Update successfully.";
            msgBgColor = COLOR_OK;
            connection.commit();
            }
            else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
            connection.rollback();
        }
             }else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        } catch (Exception e) {

             message = "Cannot Update the record, some error.";
            msgBgColor = COLOR_ERROR;
            connection.rollback();
            System.out.println("Update ERROR inside Model - " + e);
        }
        boolean b=false;
        return b;
    }

    public boolean deleteRecord(Item itemBean)
    {
        boolean b=false;
        String query = "delete from item where item_id="+itemBean.getItem_id();
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
        int rowsAffected=ps.executeUpdate();
        if (rowsAffected > 0) {
            b=true;
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        }catch(Exception e)
        {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
            e.printStackTrace();
        }
        return b;
    }
    public List getPropertiesList()
    {
        List list = new ArrayList();
        String query = "select created_date,properties_name from properties order by created_date";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next())
            {
                list.add(rs.getString("properties_name"));
            }
        } catch (Exception e)
        {
            System.out.println("getproperties ERROR inside SurveyModel - " + e);
        }
        return list;
    }
        public String getMessage()
    {
        return message;
    }

    public String getMsgBgColor()
    {
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
