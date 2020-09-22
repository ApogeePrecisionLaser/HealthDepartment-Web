/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.healthDepartment.organization.model;

/**
 *
 * @author Tarun
 */
import com.healthDepartment.organization.tableClasses.OrgClientAssoMap;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OrgClientAssoMapModel {

    private Connection connection;
    
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
             System.out.println("OrgClientAssoMapModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows() {
        int noOfRows = 0;
        try {
            ResultSet rset = connection.prepareStatement("select count(*) from org_client_asso_map ").executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
            System.out.println("\n OrgClientAssoMapModel-No Of Rows-- " + noOfRows);
        } catch (Exception e) {
            System.out.println("Error:OrgClientAssoMapModel " + e);
        }
        return noOfRows;
    }

    public List<OrgClientAssoMap> showData(int lowerLimit, int noOfRowsToDisplay) {
        List<OrgClientAssoMap> list = new ArrayList<OrgClientAssoMap>();
        String query = "SELECT org_client_asso_map_id, c.organisation_name AS client_org, ca.organisation_name AS client_asso_org, oca.description "
                + "FROM org_client_asso_map AS oca, organisation_name AS c, organisation_name AS ca "
                + "WHERE oca.org_client_id=c.organisation_id AND oca.org_clie_asso_id=ca.organisation_id "
                + "GROUP BY org_client_asso_map_id ORDER BY c.organisation_name LIMIT "
                + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                OrgClientAssoMap orgClientAssoMap = new OrgClientAssoMap();
                orgClientAssoMap.setOrg_client_asso_map_id(rset.getInt("org_client_asso_map_id"));
                orgClientAssoMap.setOrg_asso_name(rset.getString("client_asso_org"));
                orgClientAssoMap.setOrg_client_name(rset.getString("client_org"));
                orgClientAssoMap.setDescription(rset.getString("description"));
                list.add(orgClientAssoMap);
            }
        } catch (Exception e) {
            System.out.println("Error:OrgClientAssoMapModel-showData- " + e);
        }

        return list;
    }

    public int insertRecord(OrgClientAssoMap OrgClientAssoMap) {
        String query = "INSERT INTO org_client_asso_map (org_client_id, org_clie_asso_id, description) "
                + " VALUES(?, ?, ?) ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, OrgClientAssoMap.getOrg_client_id());
            pstmt.setInt(2, OrgClientAssoMap.getOrg_asso_id());
            pstmt.setString(3, OrgClientAssoMap.getDescription());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: OrgClientAssoMapModel-insertRecord-" + e);
        }
        if (rowsAffected > 0) {
            message = "Record saved successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot save the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int updateRecord(OrgClientAssoMap OrgClientAssoMap) {
        String query = "UPDATE org_client_asso_map SET org_client_id=?, org_clie_asso_id=?, description=? WHERE org_client_asso_map_id= ? ";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);

            pstmt.setInt(1, OrgClientAssoMap.getOrg_client_id());
            pstmt.setInt(2, OrgClientAssoMap.getOrg_asso_id());
            pstmt.setString(3, OrgClientAssoMap.getDescription());
            pstmt.setInt(4, OrgClientAssoMap.getOrg_client_asso_map_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: OrgClientAssoMapModel--updateRecord-" + e);
        }
        if (rowsAffected > 0) {
            message = "Record updated successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot update the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int deleteRecord(int org_client_asso_map_id) {
        String query = "DELETE FROM org_client_asso_map WHERE org_client_asso_map_id= " + org_client_asso_map_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:OrgClientAssoMapModel-- " + e);
        }
        if (rowsAffected > 0) {
            message = "Record deleted successfully.";
            msgBgColor = COLOR_OK;
        } else {
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public List<String> getClientAssoOrgName(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT o.organisation_name FROM organisation_name AS o, organisation_type AS ot, organisation_sub_type AS ost, organisation_map AS om "
                + "WHERE  o.organisation_id= om.organisation_id AND om.organisation_type_id = ot.organisation_type_id AND ot.organisation_type_id= ost.organisation_type_id "
                + "AND ot.org_type_name='Client Associate'  GROUP BY o.organisation_name ORDER BY o.organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = rset.getString("organisation_name");
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrgClientAssoMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public int getClientOrg_id(String organisation_name) {
        String query = "SELECT o.organisation_id FROM organisation_name AS o, organisation_type AS ot, organisation_sub_type AS ost, organisation_map AS om "
                + " WHERE  o.organisation_id= om.organisation_id AND om.organisation_type_id = ot.organisation_type_id AND ot.organisation_type_id= ost.organisation_type_id "
                + " AND ot.org_type_name='Client' AND organisation_name = ? GROUP BY o.organisation_id ";
        int organisation_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            organisation_id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error: OrgClientAssoMapModel--" + e);
        }
        return organisation_id;
    }

    public List<String> getClientOrgNameList(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT o.organisation_name FROM organisation_name AS o, organisation_type AS ot, organisation_sub_type AS ost, organisation_map AS om "
                + "WHERE  o.organisation_id= om.organisation_id AND om.organisation_type_id = ot.organisation_type_id AND ot.organisation_type_id= ost.organisation_type_id "
                + "AND ot.org_type_name='Client'  GROUP BY o.organisation_name ORDER BY o.organisation_name ";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String organisation_name = rset.getString("organisation_name");
                if (organisation_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(organisation_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public int getClientAssoOrgID(String organisation_name) {
        String query = "SELECT o.organisation_id FROM organisation_name AS o, organisation_type AS ot, organisation_sub_type AS ost, organisation_map AS om "
                + " WHERE  o.organisation_id= om.organisation_id AND om.organisation_type_id = ot.organisation_type_id AND ot.organisation_type_id= ost.organisation_type_id "
                + " AND ot.org_type_name='Client Associate' AND organisation_name =? ";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("organisation_id");
        } catch (Exception e) {
            System.out.println("Error:OrgClientAssoMapModel-getClientAssoOrgID-- " + e);
        }
        return id;
    }

    public List<String> getOrganisation_subType_Name(String q, String org_type_name) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT ost.organisation_sub_type_name "
                + "FROM organisation_sub_type AS ost ,organisation_type AS ot "
                + "WHERE ost.organisation_type_id=ot.organisation_type_id AND org_type_name=? "
                + "ORDER BY organisation_sub_type_name ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_sub_type_name = rset.getString("organisation_sub_type_name");
                if (org_sub_type_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_sub_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Organisation Type exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:OrgClientAssoMapModel--getOrganationNameList()-- " + e);
        }
        return list;
    }

    public int getOrganisation_subType_id(String organisation_sutType_name) {
        String query = "SELECT organisation_sub_type_id FROM organisation_sub_type WHERE organisation_sub_type_name = ?";
        int organisation_subType_id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, organisation_sutType_name);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {

                organisation_subType_id = rset.getInt("organisation_sub_type_id");
            }
        } catch (Exception e) {
            System.out.println("Error: OrgClientAssoMapModel--" + e);
        }
        return organisation_subType_id;
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

   

    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("OrgClientAssoMapModel closeConnection() Error: " + e);
        }
    }
}
