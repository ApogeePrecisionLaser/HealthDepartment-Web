/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.model;

import com.additems.tableClasses.PropertiesDetails;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Shobha
 */
public class PropertiesDetailsModel {
private Connection connection;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
      private int getpropertiesID(String search)
        {
        String query="select properties_id from properties where properties_name='"+search+"'";
        int id=0;
             try {
                ResultSet rs = connection.prepareStatement(query).executeQuery();
                while (rs.next())
                {
                    id=rs.getInt("properties_id");
                }
            }catch(Exception e){e.printStackTrace();}
         return id;
     }

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
    public List<PropertiesDetails> ShowData(int lowerLimit, int noOfRowsToDisplay,String searchproperties_name)
    {
        List<PropertiesDetails> list = new ArrayList<PropertiesDetails>();
        int id=getpropertiesID(searchproperties_name);
        String query = "select pd.property_details_id, pd.properties_id, pd.value, pd.created_by, pd.created_date, pd.remark, p.properties_name "
                + " from properties as p ,property_details as pd "
                + "where p.properties_id=pd.properties_id "
                + "AND if('"+id+"'='0', pd.properties_id LIKE '%%', pd.properties_id="+id+")"
                + " LIMIT "+lowerLimit+","+noOfRowsToDisplay;

        try {
                ResultSet rs = connection.prepareStatement(query).executeQuery();
                while (rs.next())
                {
                PropertiesDetails  propertiesDetailsBean=new PropertiesDetails();
                propertiesDetailsBean.setProperties_name(rs.getString("properties_name"));
                propertiesDetailsBean.setValue(rs.getString("value"));
                propertiesDetailsBean.setRemark(rs.getString("remark"));
                propertiesDetailsBean.setProperty_details_id(rs.getInt("property_details_id"));
                list.add(propertiesDetailsBean);
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
        String query = "select count(*) as count"
                + " from properties as p ,property_details as pd "
                + "where p.properties_id=pd.properties_id";

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
    public boolean insertRecord(PropertiesDetails propertiesDetailsBean) {
    boolean b=false;
        String query = "insert into property_details(properties_id,value,remark) values ((select properties_id from properties where properties_name='"+propertiesDetailsBean.getProperties_name()+"'),?,?)";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);

             ps.setString(1,propertiesDetailsBean.getValue());
             ps.setString(2,propertiesDetailsBean.getRemark());
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

    public boolean deleteRecord(PropertiesDetails propertiesDetailsBean) {
        boolean b=false;
        String query = "DELETE FROM property_details where property_details_id=?";
        try {
             PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);
             ps.setInt(1,propertiesDetailsBean.getProperty_details_id());
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
    public boolean UpdateRecord(PropertiesDetails propertiesDetailsBean)
    {
        String query = "update property_details set properties_id=(select properties_id from properties where properties_name='"+propertiesDetailsBean.getProperties_name()+"'),value='"+propertiesDetailsBean.getValue()+"',remark='"+propertiesDetailsBean.getRemark()+"' where property_details_id="+propertiesDetailsBean.getProperty_details_id()+"";
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
