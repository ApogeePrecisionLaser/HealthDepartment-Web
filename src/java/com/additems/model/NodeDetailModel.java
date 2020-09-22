/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.model;

import com.additems.tableClasses.NodeDetail;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Shobha
 */
public class NodeDetailModel {
private Connection connection;
    private String driver, url, user, password;
    private String message, messageBGColor;
    private String driverClass;
    private String connectionString;
    private String db_username;
    private String db_password;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";

    public int getTotalRowsInTable(String search_node_name, String search_pipe_type, int node_id) {
        String query = " select count(node_detail_id) "
                + " from node1 as n,node_detail as pd,road_type as pt where n.node_id=pd.node_id AND pt.road_type_id=pd.road_type_id"
                + " AND if('" + search_node_name + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + search_node_name + "%')"
                + " AND if(" + node_id + "=0, n.node_id LIKE '%%', n.node_id = '" + node_id + "')"
                + " AND if('" + search_pipe_type + "'='', pt.road_type LIKE '%%', pt.road_type ='" + search_pipe_type + "')";
        int noOfRows = 0;
        try {
            PreparedStatement presta = connection.prepareStatement(query);
            ResultSet rs = presta.executeQuery();
            rs.next();
            noOfRows = rs.getInt(1);
        } catch (Exception e) {
            System.out.println("Error inside getNoOfRows  : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

    public List<NodeDetail> showAllData(int lowerLimit, int noOfRowsToDisplay, String search_node_name, String search_pipe_type, int node_id) {
        ArrayList<NodeDetail> list = new ArrayList<NodeDetail>();
        String query = " select pd.pipe_name,n.node_name,node_detail_id, head_latitude, head_longitude, tail_longitude,pt.road_type,"
                + " tail_lattitude, width, width_unit, length, length_unit, pd.remark"
                + " from node1 as n,node_detail as pd,road_type as pt where n.node_id=pd.node_id AND pt.road_type_id=pd.road_type_id"
                + " AND if('" + search_node_name + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + search_node_name + "%')"
                + " AND if(" + node_id + "=0 , n.node_id LIKE '%%', n.node_id = '" + node_id + "')"
                + " AND if('" + search_pipe_type + "'='', pt.road_type LIKE '%%', pt.road_type = '" + search_pipe_type + "')";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                NodeDetail pd = new NodeDetail();
                pd.setNode_name(rset.getString("node_name"));
                pd.setPipe_detail_id(rset.getInt("node_detail_id"));
                pd.setHead_latitude(rset.getDouble("head_latitude"));
                pd.setHead_longitude(rset.getDouble("head_longitude"));
                pd.setTail_longitude(rset.getDouble("tail_longitude"));
                pd.setTail_latitude(rset.getDouble("tail_lattitude"));
                pd.setDiameter(rset.getDouble("width"));
                pd.setDiameter_unit(rset.getString("width_unit"));
                pd.setLength(rset.getDouble("length"));
                pd.setLength_unit(rset.getString("length_unit"));
                pd.setRemark(rset.getString("remark"));
                pd.setPipe_type(rset.getString("road_type"));
                pd.setPipe_name(rset.getString("pipe_name"));
                list.add(pd);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public JSONObject getPipeDetail(int pipe_detail_id) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String query = " Select latitude,longitude,pd.diameter,color,width from type_of_bend as tob left join pipe_bend as pb ON tob.type_of_bend_id=pb.type_of_bend_id left join pipe_detail as pd ON pd.pipe_detail_id=pb.pipe_detail_id left join plotting_detail as pld ON pld.diameter=pd.diameter where pb.pipe_detail_id=" + pipe_detail_id + " order by bend_order";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                JSONObject json1 = new JSONObject();
                json1.put("latitude", rs.getDouble("latitude"));
                json1.put("longitude", rs.getDouble("longitude"));
                json1.put("color", rs.getString("color"));
                json1.put("width", rs.getString("width"));
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside PipeTypeModel - " + e);
        }
        json.put("data", jsonArray);
        return json;
    }

    public JSONObject getData() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT pipe_detail_id, node_id, head_latitude, head_longitude, tail_longitude, tail_lattitude, diameter, pipe_type FROM pipe_detail p left join pipe_type as pt ON pt.pipe_type_id=p.pipe_type_id";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                JSONObject json1 = new JSONObject();
                json1.put("head_latitude", rs.getDouble("head_latitude"));
                json1.put("head_longitude", rs.getDouble("head_longitude"));
                json1.put("tail_latitude", rs.getDouble("tail_lattitude"));
                json1.put("tail_longitude", rs.getDouble("tail_longitude"));
                json1.put("dataArray", getPipeData(rs.getInt("pipe_detail_id")));
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside PipeTypeModel - " + e);
        }
        json.put("data", jsonArray);
        return json;
    }

    public JSONObject GetCompleteData() {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String query = "SELECT o.overheadtank_id,w.latitude as water_plant_latitude, w.longitude as water_plant_longitude,o.latitude as overheadtank_latitude,o.longitude as overheadtank_longitude "
                + " FROM watertreatmentplant_overheadtank as womap "
                + " left join watertreatmentplant as w ON w.watertreatmentplant_id=womap.watertreatmentplant_id "
                + " left join overheadtank as o ON o.overheadtank_id=womap.overheadtank_id";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                JSONObject json1 = new JSONObject();
                json1.put("water_plant_latitude", rs.getDouble("water_plant_latitude"));
                json1.put("water_plant_longitude", rs.getDouble("water_plant_longitude"));
                json1.put("overheadtank_latitude", rs.getDouble("overheadtank_latitude"));
                json1.put("overheadtank_longitude", rs.getDouble("overheadtank_longitude"));
                json1.put("dataArray1", getPipeDetails(rs.getInt("overheadtank_id")));
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside PipeTypeModel - " + e);
        }
        json.put("data", jsonArray);
        return json;
    }

    public JSONArray getPipeDetails(int overheadtank_id) {
        JSONArray jsonArray = new JSONArray();
        String query = " SELECT head_latitude, head_longitude, tail_longitude, tail_lattitude, diameter, pipe_detail_id FROM node_overheadtank_mapping as nmap left join (overheadtank as o,node as n,pipe_detail as pd) on nmap.overheadtank_id=o.overheadtank_id and  nmap.node_id=n.node_id and pd.node_id=n.node_id where o.overheadtank_id="+overheadtank_id;
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                JSONObject json1 = new JSONObject();
                json1.put("head_latitude", rs.getDouble("head_latitude"));
                json1.put("head_longitude", rs.getDouble("head_longitude"));
                json1.put("tail_latitude", rs.getDouble("tail_lattitude"));
                json1.put("tail_longitude", rs.getDouble("tail_longitude"));
                json1.put("dataArray2", getPipeData(rs.getInt("pipe_detail_id")));
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside PipeTypeModel - " + e);
        }
        return jsonArray;
    }

    public JSONArray getPipeData(int pipe_detail_id) {
        JSONArray jsonArray = new JSONArray();
        String query = " Select latitude,longitude,pd.diameter,color,width from type_of_bend as tob "
                + " left join pipe_bend as pb ON tob.type_of_bend_id=pb.type_of_bend_id "
                + " left join pipe_detail as pd ON pd.pipe_detail_id=pb.pipe_detail_id "
                + " left join plotting_detail as pld ON pld.pipe_detail_id=pd.pipe_detail_id"
                + " where pb.pipe_detail_id=" + pipe_detail_id + " order by bend_order";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                JSONObject json1 = new JSONObject();
                json1.put("latitude", rs.getDouble("latitude"));
                json1.put("longitude", rs.getDouble("longitude"));
                json1.put("color", rs.getString("color"));
                json1.put("width", rs.getString("width"));
                jsonArray.add(json1);
            }
        } catch (Exception e) {
            System.out.println("ShowData ERROR inside PipeTypeModel - " + e);
        }
        return jsonArray;
    }

    public List<NodeDetail> showNodeData(int node_id) {
        ArrayList<NodeDetail> list = new ArrayList<NodeDetail>();
        String query = " select pd.pipe_name,n.node_name,n.node_id,pipe_detail_temp_id, head_latitude, head_longitude, tail_longitude,pt.pipe_type,"
                + " tail_lattitude, diameter, diameter_unit, length, length_unit, pd.remark"
                + " from node as n,pipe_detail_temp as pd,pipe_type as pt "
                + " where n.node_id=pd.node_id AND pt.pipe_type_id=pd.pipe_type_id"
                + " and n.node_id=" + node_id;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                NodeDetail pd = new NodeDetail();
                pd.setNode_id(rset.getInt("node_id"));
                pd.setNode_name(rset.getString("node_name"));
                pd.setPipe_detail_id(rset.getInt("pipe_detail_temp_id"));
                pd.setHead_latitude(rset.getDouble("head_latitude"));
                pd.setHead_longitude(rset.getDouble("head_longitude"));
                pd.setTail_longitude(rset.getDouble("tail_longitude"));
                pd.setTail_latitude(rset.getDouble("tail_lattitude"));
                pd.setDiameter(rset.getDouble("diameter"));
                pd.setDiameter_unit(rset.getString("diameter_unit"));
                pd.setLength(rset.getDouble("length"));
                pd.setLength_unit(rset.getString("length_unit"));
                pd.setRemark(rset.getString("remark"));
                pd.setPipe_type(rset.getString("pipe_type"));
                pd.setPipe_name(rset.getString("pipe_name"));
                list.add(pd);
            }
        } catch (Exception e) {
            System.out.println("Error in showAllRecrod -- : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
        return list;
    }

    public void deleteRecord(int pipe_detail_id) {
        PreparedStatement presta = null;
        try {
            presta = connection.prepareStatement("DELETE FROM pipe_detail WHERE pipe_detail_id=?");
            presta.setInt(1, pipe_detail_id);
            int i = presta.executeUpdate();
            if (i > 0) {
                message = "Record deleted successfully......";
                messageBGColor = "yellow";
            } else {
                message = "Record not deleted successfully......";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in deleteRecord ---- OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public void insertRecord(NodeDetail pd) {
        String query = "INSERT INTO node_detail( node_id, head_latitude, head_longitude, tail_lattitude,tail_longitude, width, width_unit, length, length_unit, remark,road_type_id,pipe_name) "
                + " VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            int node_id = getNodeId(pd.getNode_name());
            ps.setInt(1, node_id);
            ps.setDouble(2, pd.getHead_latitude());
            ps.setDouble(3, pd.getHead_longitude());
            ps.setDouble(4, pd.getTail_latitude());
            ps.setDouble(5, pd.getTail_longitude());
            ps.setDouble(6, pd.getDiameter());
            ps.setString(7, pd.getDiameter_unit());
            ps.setDouble(8, pd.getLength());
            ps.setString(9, pd.getLength_unit());
            ps.setString(10, pd.getRemark());
            ps.setInt(11, getpipe_type_id(pd.getPipe_type()));
            ps.setString(12, pd.getPipe_name());
            int i = ps.executeUpdate();
            if (i >= 0) {
                message = "Record inserted succesfully";
                messageBGColor = "Yellow";
            } else {
                message = "Record not inserted succesfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in insertRecord - OHLevelModel : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public int getNodeId(String node_name) {
        int node_id = 0;
        String query = "SELECT node_id FROM node1 WHERE node_name ='" + node_name + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                node_id = rs.getInt("node_id");
            }
        } catch (Exception ex) {
        }
        return node_id;
    }

    public int getpipe_type_id(String pipe_type) {
        int pipe_type_id = 0;
        String query = "SELECT road_type_id FROM road_type WHERE road_type ='" + pipe_type + "' ";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            if (rs.next()) {
                pipe_type_id = rs.getInt("road_type_id");
            }
        } catch (Exception ex) {
        }
        return pipe_type_id;
    }

    public void updateRecord(NodeDetail pd) {
        String query = "UPDATE pipe_detail SET node_id=?, head_latitude=?, head_longitude=?, tail_longitude=?, tail_lattitude=?, diameter=?, diameter_unit=?, length=?, length_unit=?, remark=?,pipe_type_id=?,pipe_name=? where pipe_detail_id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            int node_id = getNodeId(pd.getNode_name());
            ps.setInt(1, node_id);
            ps.setDouble(2, pd.getHead_latitude());
            ps.setDouble(3, pd.getHead_longitude());
            ps.setDouble(4, pd.getTail_latitude());
            ps.setDouble(5, pd.getTail_longitude());
            ps.setDouble(6, pd.getDiameter());
            ps.setString(7, pd.getDiameter_unit());
            ps.setDouble(8, pd.getLength());
            ps.setString(9, pd.getLength_unit());
            ps.setString(10, pd.getRemark());
            ps.setInt(11, getpipe_type_id(pd.getPipe_type()));
            ps.setString(12, pd.getPipe_name());
            ps.setInt(13, pd.getPipe_detail_id());
            int i = ps.executeUpdate();
            if (i > 0) {
                message = "Record updated successfully";
                messageBGColor = "yellow";
            } else {
                message = "Record not updated successfully";
                messageBGColor = "red";
            }
        } catch (Exception e) {
            System.out.println("Error in updateRecord  : " + e);
            message = "Something going wrong";
            messageBGColor = "red";
        }
    }

    public List<String> getPipeType(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT road_type FROM road_type WHERE active='Y' "
                + " AND road_type LIKE '" + q.trim() + "%' ORDER BY road_type";
        try {
            ResultSet rs = connection.prepareStatement(query).executeQuery();
            while (rs.next()) {
                list.add(rs.getString("road_type"));
            }
        } catch (Exception ex) {
            System.out.println("getPipeType ERROR inside PipeTypeModel - " + ex);
        }
        return list;
    }

    public List<String> getNodeName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "select node_name from node1";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String node_name = rset.getString("node_name");
                if (node_name.startsWith(q)) {
                    list.add(node_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No Person of such Office Code Exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:keypersonModel--getDesignationList()-- " + e);
        }
        return list;
    }

    public byte[] generateMapReport(String jrxmlFilePath, List listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in OverHeadTankModel generateMapReport() JRException: " + e);
        }
        return reportInbytes;
    }

    public void setConnection() {
        try {
            Class.forName(driver);
            // connection = DriverManager.getConnection(connectionString+"?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", db_username, db_password);
            connection = (Connection) DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("WattageTypeModel setConnection() Error: " + e);
        }
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Error in closeConnection -- OHLevelModel : " + e);
        }
    }

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
            System.out.println("PaymentStatusModel setConnection() Error: " + e);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageBGColor() {
        return messageBGColor;
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

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }
}
