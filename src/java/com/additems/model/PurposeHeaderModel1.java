/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.model;


import com.additems.tableClasses.PurposeHeaderNode1;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Shobha
 */
public class PurposeHeaderModel1 {
    private Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";

    public void setConnection(Connection con) {
        try {
            connection = con;
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel setConnection() Error: " + e);
        }
    }

    public int getNoOfRows(String parentheadername, String subheadername, String headerindex) {
        int noOfRows = 0;

        String query = "SELECT count(t.node_id) as count"
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' " //t.index_no NOT LIKE '%.0%'
                + " AND if('" + subheadername + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + subheadername + "%')"
                + " AND if('" + parentheadername + "'='', pn.node_name LIKE '%%', pn.node_name LIKE '%" + parentheadername + "%')"
                + " AND if('" + headerindex + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + headerindex + "%')";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                noOfRows = rset.getInt("count");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getNoOfRows() Error: " + e);
        }
        return noOfRows;
    }

    public List<PurposeHeaderNode1> showData(int lowerLimit, int noOfRowsToDisplay, String parentheadername, String subheadername, String headerindex) {
        List<PurposeHeaderNode1> list = new ArrayList<PurposeHeaderNode1>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

//        /* code to show root node -- starts here */
//        int nodeId = 1;
//        String query1 = "SELECT t.node_id,n.node_name,t.active,t.remark "
//                      + "FROM node as n,tree as t "
//                      + "where n.node_id=t.node_id "
//                      + " AND if('"+subheadername+"'='', n.node_name LIKE '%%', n.node_name LIKE '%"+subheadername+"%')"
//                      + " And n.active='Y' And t.active='Y' And n.node_id= " + nodeId ;
//                     // + " AND if('"+subheadername+"'='', n.node_name LIKE '%%', n.node_name LIKE '%"+subheadername+"%')"
//                      //+ " AND if('"+parentheadername+"'='', pn.node_name LIKE '%%', pn.node_name LIKE '%"+parentheadername+"%')"
//                     // + " AND if('"+headerindex+"'='', t.index_no LIKE '%%',t.index_no LIKE '"+headerindex+"%') ORDER BY t.index_no"
//                     // + addQuery;
//        try {
//            ResultSet rset1 = connection.prepareStatement(query1).executeQuery();
//            while (rset1.next()) {
//                PurposeHeaderNode headerNode = new PurposeHeaderNode();
//               // headerNode.setIndex_no(rset1.getString("index_no"));
//                headerNode.setNode_id(rset1.getInt("node_id"));
//                headerNode.setTree_node_name(rset1.getString("node_name"));
//               // headerNode.setNode_parent_name(rset1.getString("parent_node_name"));
//                headerNode.setTree_active(rset1.getString("active"));
//                headerNode.setTree_remark(rset1.getString("remark"));
//                list.add(headerNode);
//            }
//        } catch (Exception e) {
//            System.out.println("PurposeHeaderModel showData() query1 block Error: " + e);
//        }
//
//        /* code to show root node -- ends here */

        String query = "SELECT t.index_no,t.node_id,n.node_name, pn.node_name AS parent_node_name,t.active,t.remark "
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' "
                + " AND if('" + subheadername + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + subheadername + "%')"
                + " AND if('" + parentheadername + "'='', pn.node_name LIKE '%%', pn.node_name LIKE '%" + parentheadername + "%')"
                + " AND if('" + headerindex + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + headerindex + "%')"
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no "
                + addQuery;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                PurposeHeaderNode1 headerNode = new PurposeHeaderNode1();
                headerNode.setIndex_no(rset.getString("index_no"));
                headerNode.setNode_id(rset.getInt("node_id"));
                headerNode.setTree_node_name(rset.getString("node_name"));
                headerNode.setNode_parent_name(rset.getString("parent_node_name"));
                headerNode.setTree_active(rset.getString("active"));
                headerNode.setTree_remark(rset.getString("remark"));
                list.add(headerNode);
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel showData() Error: " + e);
        }
        return list;
    }

    public List<PurposeHeaderNode1> showDataParent(int lowerLimit, int noOfRowsToDisplay, String headername, String headerindex) {
        List<PurposeHeaderNode1> list = new ArrayList<PurposeHeaderNode1>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        /* code to show root node -- starts here */
        String query1 = "SELECT n.node_id,n.node_name "
                + " FROM node1 as n "
                + " where n.active='Y' "
                + " AND if('" + headername + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + headername + "%')"
                + " And n.generation_no=0 ";
        try {
            if (headerindex.isEmpty() && headername.isEmpty()) {
                ResultSet rset = connection.prepareStatement(query1).executeQuery();
                while (rset.next()) {
                    PurposeHeaderNode1 headerNode = new PurposeHeaderNode1();
                    headerNode.setNode_id(rset.getInt("node_id"));
                    headerNode.setTree_node_name(rset.getString("node_name"));
                    list.add(headerNode);
                }
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel showDataParent() query1 block Error: " + e);
        }
        /* code to show root node -- starts here */

        String query = "SELECT t.index_no,t.node_id,n.node_name, pn.node_name AS parent_node_name,t.active,t.remark "
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' "
                + " AND if('" + headername + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + headername + "%')"
                + " AND if('" + headerindex + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + headerindex + "%') "
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no "
                + addQuery;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                PurposeHeaderNode1 headerNode = new PurposeHeaderNode1();
                headerNode.setIndex_no(rset.getString("index_no"));
                headerNode.setNode_id(rset.getInt("node_id"));
                headerNode.setTree_node_name(rset.getString("node_name"));
                headerNode.setNode_parent_name(rset.getString("parent_node_name"));
                headerNode.setTree_active(rset.getString("active"));
                headerNode.setTree_remark(rset.getString("remark"));
                list.add(headerNode);
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel showDataParent() Error: " + e);
        }
        return list;
    }

    public List<PurposeHeaderNode1> showChild_Header_Name(String headername, String headerindex, String parentHeaderName) {
        List<PurposeHeaderNode1> list = new ArrayList<PurposeHeaderNode1>();
        int genNo = 0, maxGenNo = 0, parentHeaderId = 0;

        try {
            parentHeaderId = getNodeId(parentHeaderName);
            String query1 = "SELECT generation_no FROM node1 where active='Y' and node_id = " + parentHeaderId;
            String query2 = "SELECT max(generation_no) as generation_no FROM node1 where active='Y' ";

            try {
                ResultSet rst = connection.prepareStatement(query1).executeQuery();
                if (rst.next()) {
                    genNo = rst.getInt("generation_no");
                }
            } catch (Exception e) {
                System.out.println("PurposeHeaderModel - showChild_Header_Name() - query1 block error: " + e);
            }

            try {
                ResultSet rtst = connection.prepareStatement(query2).executeQuery();
                if (rtst.next()) {
                    maxGenNo = rtst.getInt("generation_no");
                }
            } catch (Exception e) {
                System.out.println("PurposeHeaderModel - showChild_Header_Name() - query2 block error: " + e);
            }

            String query3 = "SELECT t.index_no,t.node_id,n.node_name from node As n,tree as t where n.generation_no Between " + genNo + " And " + maxGenNo
                    + " and t.node_id NOT IN (SELECT node_id FROM tree1 where active='Y' AND node_parent_id = " + parentHeaderId + ") "
                    + " AND if('" + headername + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + headername + "%')"
                    + " AND if('" + headerindex + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + headerindex + "%') "
                    + " AND t.node_id=n.node_id And t.active='Y' And n.active='Y' "
                    + " and t.node_id != " + parentHeaderId  //ORDER BY n.node_id";
                    + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                    + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                    + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                    + " t.index_no ";

            ResultSet rset = connection.prepareStatement(query3).executeQuery();
            int count = 0;

            while (rset.next()) {
                //list = rset.getInt("node_id") + "," + rset.getString("node_name") + "," + rset.getInt("index_no");
                PurposeHeaderNode1 headerNode = new PurposeHeaderNode1();
                headerNode.setIndex_no(rset.getString("index_no"));
                headerNode.setNode_id(rset.getInt("node_id"));
                headerNode.setTree_node_name(rset.getString("node_name"));
                list.add(headerNode);
                count++;
            }
        } catch (Exception e) {
            System.out.println("Error: PurposeHeaderModel- showChild_Header_Name" + e);
        }
        return list;
    }

    public List<PurposeHeaderNode1> showData1(int lowerLimit, int noOfRowsToDisplay, String headername, String headerindex) {
        List<PurposeHeaderNode1> list = new ArrayList<PurposeHeaderNode1>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String query = "SELECT t.index_no,t.node_id,n.node_name, pn.node_name AS parent_node_name,t.active,t.remark "
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' "
                + " AND if('" + headername + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + headername + "%')"
                + " AND if('" + headerindex + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + headerindex + "%') "
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no "
                + addQuery;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                PurposeHeaderNode1 headerNode = new PurposeHeaderNode1();
                headerNode.setIndex_no(rset.getString("index_no"));
                headerNode.setNode_id(rset.getInt("node_id"));
                headerNode.setTree_node_name(rset.getString("node_name"));
                headerNode.setNode_parent_name(rset.getString("parent_node_name"));
                headerNode.setTree_active(rset.getString("active"));
                headerNode.setTree_remark(rset.getString("remark"));
                list.add(headerNode);
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel showData1() Error: " + e);
        }
        return list;
    }

    public List<PurposeHeaderNode1> showData3(int lowerLimit, int noOfRowsToDisplay, String headername, String headerindex) {
        List<PurposeHeaderNode1> list = new ArrayList<PurposeHeaderNode1>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String query = "SELECT t.index_no,t.node_id,n.node_name, pn.node_name AS parent_node_name,t.active,t.remark "
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' AND t.index_no LIKE '1%' " // AND t.index_no NOT LIKE '2%'
                + " AND if('" + headername + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + headername + "%')"
                + " AND if('" + headerindex + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + headerindex + "%') "
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no "
                + addQuery;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                PurposeHeaderNode1 headerNode = new PurposeHeaderNode1();
                headerNode.setIndex_no(rset.getString("index_no"));
                headerNode.setNode_id(rset.getInt("node_id"));
                headerNode.setTree_node_name(rset.getString("node_name"));
                headerNode.setNode_parent_name(rset.getString("parent_node_name"));
                headerNode.setTree_active(rset.getString("active"));
                headerNode.setTree_remark(rset.getString("remark"));
                list.add(headerNode);
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel showData3() Error: " + e);
        }
        return list;
    }

    public List<PurposeHeaderNode1> showData2(int lowerLimit, int noOfRowsToDisplay, String headername, String headerindex) {
        List<PurposeHeaderNode1> list = new ArrayList<PurposeHeaderNode1>();
        String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if (lowerLimit == -1) {
            addQuery = "";
        }

        String query = "SELECT t.index_no,t.node_id,n.node_name, pn.node_name AS parent_node_name,t.active,t.remark "
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' And t.isSuperChild = 'Y' AND t.index_no LIKE '2%' "
                + " AND if('" + headername + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + headername + "%')"
                + " AND if('" + headerindex + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + headerindex + "%') "
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no "
                + addQuery;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                PurposeHeaderNode1 headerNode = new PurposeHeaderNode1();
                headerNode.setIndex_no(rset.getString("index_no"));
                headerNode.setNode_id(rset.getInt("node_id"));
                headerNode.setTree_node_name(rset.getString("node_name"));
                headerNode.setNode_parent_name(rset.getString("parent_node_name"));
                headerNode.setTree_active(rset.getString("active"));
                headerNode.setTree_remark(rset.getString("remark"));
                list.add(headerNode);
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel showData2() Error: " + e);
        }
        return list;
    }

    public List<String> getIndex(String q) {
        List<String> list = new ArrayList<String>();
        int nodeId = 1;
        String query1 = "SELECT index_no from tree1 where active='Y' and node_id != " + nodeId
                + " ORDER BY CAST(SUBSTRING_INDEX(index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no ";

        try {
            ResultSet rset = connection.prepareStatement(query1).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String index_no = rset.getString("index_no");
                if (index_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(index_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Child Parent Header Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: PurposeHeaderModel- getIndex" + e);
        }
        return list;
    }

//       public List<String> getsection(String q) {
//        List<String> list = new ArrayList<String>();
//        String query1 = "select node_name from node where active='Y GROUP BY node_name ORDER BY node_name";
//
//        try {
//            PreparedStatement pstmt = connection.prepareStatement(query1);
//
//            ResultSet rset = pstmt.executeQuery();
//            int count = 0;
//            q = q.trim();
//            while (rset.next()) {
//                String node_name = rset.getString("node_name");
//                if (node_name.toUpperCase().startsWith(q.toUpperCase())) {
//                    list.add(node_name);
//                    count++;
//                }
//            }
//            if (count == 0) {
//                list.add("No such Child Parent Header Name exists.");
//            }
//        } catch (Exception e) {
//            System.out.println("Error: PurposeHeaderModel- getsection" + e);
//        }
//        return list;
//    }
    public boolean checkNodeName(String NodeName) {
        boolean check = false;
        int count = 0;
        String query = "SELECT count(*) FROM node1 WHERE node_name = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, NodeName);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                count = Integer.parseInt(rset.getString(1));
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel checkNodeName() Error: " + e);
        }

        if (count > 0) {
            check = true;
        }

        return check;
    }

    public int insertNodeRecord(PurposeHeaderNode1 headerNode, int NodeParentId, String NodeName) {
        String query = "INSERT INTO node1(node_name, generation_no, active, revision_no, remark) VALUES(?,?,?,?,?) "; //node_id,
        int rowsAffected = 0, rev_no = 0;
        if (!checkNodeName(NodeName)) {
            try {
                PreparedStatement pstmt = connection.prepareStatement(query);
                // pstmt.setInt(1, headerNode.getNode_id());
                pstmt.setString(1, headerNode.getNode_name());
                pstmt.setInt(2, getGenerationNo(NodeParentId));
                pstmt.setString(3, "Y");
                pstmt.setInt(4, rev_no);
                pstmt.setString(5, headerNode.getRemark());
                rowsAffected = pstmt.executeUpdate();
            } catch (Exception e) {
                System.out.println("PurposeHeaderModel insertNodeRecord() Error: " + e);
            }
            if (rowsAffected > 0) {
                message = "New Node Added Successfully.";
                msgBgColor = COLOR_OK;
                int NodeId = getNodeId(headerNode.getNode_name());
                insertTreeRecord(NodeId, NodeParentId);
            } else {
                message = "Cannot Add New Node, Some Error.";
                msgBgColor = COLOR_ERROR;
            }

        } else {
            System.out.println("PurposeHeaderModel insertNodeRecord() node_name already exists in node table viz. - " + NodeName);
            message = "Cannot Add..Node already exists";
            msgBgColor = COLOR_ERROR;
//             int NodeId = getNodeId(headerNode.getNode_name());
//             insertTreeRecord(NodeId,NodeParentId);
        }

        return rowsAffected;
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

    public int getNodeParentId(int Node_id) {
        int node_parent_id = 0;
        String query = "SELECT node_parent_id FROM tree1 where active='Y' and "
                + "node_id = " + Node_id;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            //pstmt.setString(1, Node_name);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                node_parent_id = rset.getInt("node_parent_id");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getNodeParentId() Error: " + e);
        }

        return node_parent_id;
    }

    public String getNodeName(int Node_id) {
        String node_name = "";
        String query = "SELECT node_name FROM node1 WHERE active='Y' and node_id = " + Node_id;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                node_name = rset.getString("node_name");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getNodeName() Error: " + e);
        }

        return node_name;
    }

    public int getChildNodeParentId(int NodeChildId) {
        int ChildNodeParentId = 0;
        String query = "SELECT node_parent_id FROM tree1 WHERE active='Y' and node_id = " + NodeChildId;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next()) {
                ChildNodeParentId = rset.getInt("node_parent_id");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getChildNodeParentId() Error: " + e);
        }

        return ChildNodeParentId;
    }

    public int insertTreeRecord(int NodeId, int NodeParentId) {
        String query = "INSERT INTO tree1(node_id, node_parent_id, node_parent_rev_no, "
                + "tree_parent_id, revision, active, index_no, isSuperChild, node_rev) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?,?) "; //tree_id,
        int rowsAffected = 0, Revision = 0;


        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            // pstmt.setInt(1, headerTree.getTree_id());
            pstmt.setInt(1, NodeId);
            pstmt.setInt(2, NodeParentId);
            pstmt.setInt(3, getNodeParentRevNo(NodeParentId));
            int num = getTreeParentId(NodeParentId);
            if (num == 0) {
                pstmt.setNull(4, java.sql.Types.NULL);
            } else {
                pstmt.setInt(4, num);
            }
            pstmt.setInt(5, Revision);
            pstmt.setString(6, "Y");
            pstmt.setString(7, getIndexNo(NodeParentId, NodeId));
            pstmt.setString(8, check_isSuperChild(NodeParentId, NodeId));
            pstmt.setInt(9, getNodeRev(NodeId));
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel insertTreeRecord() Error: " + e);
        }
        if (rowsAffected > 0) {
            System.out.println("Record inserted in tree Successfully");
            message = "Record inserted in tree Successfully";
            msgBgColor = COLOR_OK;
        } else {
            System.out.println("Cannot insert record in tree, Some Error.");
            message = "Cannot insert record in tree, Some Error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowsAffected;
    }

    public int insertParentRecord(String parentHeader, String childHeader, String description) {
        int rowsAffected = 0, rowsaffected1 = 0, rowsaffected2 = 0, rowsaffected3 = 0, maxRevision = 0, incRevision = 0;
        List<Integer> childList = new ArrayList<Integer>();
        try {
            int NodeParentId = getNodeId(parentHeader);
            int NodeChildId = getNodeId(childHeader);
            int NodeChildGenNo = getGenerationNo(NodeParentId);
            String query1 = "SELECT node_id FROM tree1 where active='Y' and node_parent_id = " + NodeChildId;
            try {
                ResultSet rset = connection.prepareStatement(query1).executeQuery();
                while (rset.next()) {
                    childList.add(rset.getInt("node_id"));
                }

                for (int i = 0; i < childList.size(); i++) {
                    int NodeChildId1 = childList.get(i);
                    String query = "SELECT node_id FROM tree1 where active='Y' and node_parent_id = " + NodeChildId1;
                    ResultSet rs = connection.prepareStatement(query).executeQuery();
                    while (rs.next()) {
                        childList.add(rs.getInt("node_id"));
                    }
                }

                int size = childList.size();
                for (int i = 0; i <= size; i++) {
                    if (i > 0) {
                        NodeChildId = childList.get(i - 1);
                        NodeParentId = getChildNodeParentId(NodeChildId);
                        NodeChildGenNo = getGenerationNo(NodeParentId);

                    }
                    /* code for entry in node table -- starts here */
                    String query2 = "select max(revision_no) revision_no from node1 "
                            + "where active = 'Y' AND node_id= " + NodeChildId;
                    String query3 = "INSERT INTO node1(node_id,node_name, generation_no, active, revision_no, remark) "
                            + " Select node_id,node_name, ?, ?, ?, remark from node1 n where active='Y' and "
                            + " node_id= " + NodeChildId;
                    String query4 = "Update node1 Set active = 'N' "
                            + "where node_id= " + NodeChildId + " And revision_no = ?";
                    ResultSet rst = connection.prepareStatement(query2).executeQuery();
                    if (rst.next()) {
                        maxRevision = rst.getInt("revision_no");
                    }
                    incRevision = maxRevision + 1;

                    PreparedStatement pstmt = connection.prepareStatement(query3);
                    pstmt.setInt(1, NodeChildGenNo);
                    pstmt.setString(2, "Y");
                    pstmt.setInt(3, incRevision);
                    rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        System.out.println("PurposeHeaderModel:insertParentRecord-query3- Record inserted in node successfully");
                        PreparedStatement pst = connection.prepareStatement(query4);
                        pst.setInt(1, maxRevision);
                        rowsaffected1 = pst.executeUpdate();
                        if (rowsaffected1 > 0) {
                            System.out.println("PurposeHeaderModel:insertParentRecord-query4- Record updated in node successfully");
                        }
                    }
                    /* code for entry in node table -- ends here */

                    /* code for entry in tree table -- starts here */
                    String query5 = "select max(revision) revision from tree1 "
                            + "where active = 'Y' AND node_id= " + NodeChildId;
                    String query6 = "INSERT INTO tree1(tree_id,node_id, node_parent_id, node_parent_rev_no, "
                            + "tree_parent_id, revision, active, index_no, isSuperChild, node_rev) "
                            + " Select tree_id,?,?,?,?,?,?,?,isSuperChild,? "
                            + "from tree1 t where active='Y' and "
                            + " node_id= " + NodeChildId;
                    String query7 = "Update tree1 Set active = 'N' "
                            + "where node_id= " + NodeChildId + " And revision = ?";
                    ResultSet rste = connection.prepareStatement(query5).executeQuery();
                    if (rste.next()) {
                        maxRevision = rste.getInt("revision");
                    }
                    incRevision = maxRevision + 1;

                    PreparedStatement pstmnt = connection.prepareStatement(query6);
                    pstmnt.setInt(1, NodeChildId);
                    pstmnt.setInt(2, NodeParentId);
                    pstmnt.setInt(3, getNodeParentRevNo(NodeParentId));
                    int num = getTreeParentId(NodeParentId);
                    if (num == 0) {
                        pstmnt.setNull(4, java.sql.Types.NULL);
                    } else {
                        pstmnt.setInt(4, num);
                    }
                    pstmnt.setInt(5, incRevision);
                    pstmnt.setString(6, "Y");
                    if (i == 0) {
                        pstmnt.setString(7, getIndexNo(NodeParentId, NodeChildId));  // getIndexNo_1(NodeParentId, NodeChildId)
                    } else {
                        pstmnt.setString(7, getIndexNo_2(NodeParentId, NodeChildId));
                    }
                    pstmnt.setInt(8, getNodeRev1(getNodeName(NodeChildId)));
                    rowsaffected2 = pstmnt.executeUpdate();

                    if (rowsaffected2 > 0) {
                        System.out.println("PurposeHeaderModel:insertParentRecord-query6- Record inserted in tree successfully");
                        PreparedStatement pst = connection.prepareStatement(query7);
                        pst.setInt(1, maxRevision);
                        rowsaffected3 = pst.executeUpdate();
                        if (rowsaffected3 > 0) {
                            System.out.println("PurposeHeaderModel:insertParentRecord-query7- Record updated in tree successfully");
                        }
                    }
                    /* code for entry in tree table -- ends here */

                }

            } catch (Exception e) {
                System.out.println("PurposeHeaderModel insertParentRecord() query1 try block Error: " + e);
            }


        } catch (Exception e) {
            System.out.println("PurposeHeaderModel insertParentRecord() Error: " + e);
        }

        return rowsaffected3;
    }

    public int updateParentRecord(int NodeChildId, int NodeParentId) {
        int rowsaffected = 0;
        try {
            String query8 = "Update tree1 SET isSuperChild='N' where active='Y' and node_id = " + NodeParentId;
            PreparedStatement pstmt = connection.prepareStatement(query8);
            rowsaffected = pstmt.executeUpdate();
            if (rowsaffected > 0) {
                System.out.println("PurposeHeaderModel - updateParentRecord() - update successfull");
                int rowupdated = updatePrevParentRecord(NodeChildId);
                if (rowsaffected > 0) {
                    message = "Record inserted in tree Successfully";
                    msgBgColor = COLOR_OK;
                    System.out.println("PurposeHeaderModel - updatePrevParentRecord() - update successfull");
                } else {
                    message = "Cannot insert record in tree, Some Error.";
                    msgBgColor = COLOR_ERROR;
                    System.out.println("PurposeHeaderModel - updatePrevParentRecord() - update unsuccessfull");
                }
            } else {
                System.out.println("PurposeHeaderModel - updateParentRecord() - update unsuccessfull");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel updateParentRecord() Error: " + e);
        }
        return rowsaffected;
    }

    public int updatePrevParentRecord(int NodeChildId) {
        int node_Prev_parent_id = 0, rowupdated = 0, count = 0;
        String query = "SELECT node_parent_id FROM tree1 WHERE active = 'N' AND node_id = " + NodeChildId;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                node_Prev_parent_id = rset.getInt("node_parent_id");
            }

            String query1 = "SELECT COUNT(*) FROM tree1 where active = 'Y' AND node_parent_id = " + node_Prev_parent_id;
            ResultSet rst = connection.prepareStatement(query1).executeQuery();
            if (rst.next()) {
                count = Integer.parseInt(rst.getString(1));
            }
            if (count == 0) {
                String query2 = "Update tree1 SET isSuperChild='Y' where active='Y' and node_id = " + node_Prev_parent_id;
                PreparedStatement pstmt = connection.prepareStatement(query2);
                rowupdated = pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("PurposeHeaderModel updatePrevParentRecord() Error: " + e);
        }

        return rowupdated;
    }

    public int updatePrevGenIndex(int NodeChildId, int NodeChildGen, int NodeParentId, int NodeParentGen, String NodeChildIndex, int NodePrevParentId) {
        int rowsAffected = 0, count = 0, branchNodeId = 0, branchLastPart = 0, rowupdated = 0;
        String branchNodeIndex = "", branchFirstPart1 = "";
        List<Integer> childList = new ArrayList<Integer>();
        List<Integer> childList1 = new ArrayList<Integer>();
        String firstPart = NodeChildIndex.substring(0, NodeChildIndex.lastIndexOf("."));
        int lastPart = Integer.parseInt(NodeChildIndex.substring((NodeChildIndex.lastIndexOf(".")) + 1, NodeChildIndex.length()));
        try {
            if (NodeChildGen == NodeParentGen) {
                String query = "SELECT COUNT(*) FROM node1 where active = 'Y' and generation_no = " + NodeChildGen;
                ResultSet rset = connection.prepareStatement(query).executeQuery();
                if (rset.next()) {
                    count = Integer.parseInt(rset.getString(1)); // count>=2 always
                }
                count = count + 1; // not required
                if (count > 0) {    // not required
                    String query1 = "SELECT node_id FROM node1 where active='Y' and generation_no = " + NodeChildGen;
                    ResultSet rs = connection.prepareStatement(query1).executeQuery();
                    while (rs.next()) {
                        childList.add(rs.getInt("node_id"));
                    }
                    int size = childList.size();
                    for (int i = 0; i < size; i++) {
                        branchNodeId = childList.get(i);
                        branchNodeIndex = getChildIndex(branchNodeId);
                        branchLastPart = Integer.parseInt(branchNodeIndex.substring((branchNodeIndex.lastIndexOf(".")) + 1, branchNodeIndex.length()));
                        if (lastPart != count) {  // lastPart < count
                            if (lastPart == 1) {
                                branchLastPart = branchLastPart - 1;
                            } else {
                                if (branchLastPart > lastPart) {
                                    branchLastPart = branchLastPart - 1;
                                }
                            }

                            if (branchLastPart < lastPart) {
                            } else {
                                branchNodeIndex = firstPart + "." + String.valueOf(branchLastPart);
                                String query2 = "Update tree1 SET index_no = ? where active='Y' and node_id = " + branchNodeId;
                                PreparedStatement pstmt = connection.prepareStatement(query2);
                                pstmt.setString(1, branchNodeIndex);
                                rowsAffected = pstmt.executeUpdate();
                            }
                        }
                        if (rowsAffected > 0) {
                            /* code to update index_no of next generation -- starts */
                            childList1.add(branchNodeId);
                            for (int j = 0; j < childList1.size(); j++) {
                                branchNodeId = childList1.get(j);
                                String query3 = "SELECT node_id FROM tree1 where active='Y' and node_parent_id = " + branchNodeId;
                                ResultSet rs1 = connection.prepareStatement(query3).executeQuery();
                                while (rs1.next()) {
                                    childList1.add(rs.getInt("node_id"));
                                }
                            }

                            int size1 = childList1.size();
                            for (int k = 0; k < (size1 - 1); k++) {
                                int branchNodeId1 = childList1.get(k + 1);
                                String branchNodeIndex1 = getChildIndex(branchNodeId1);
                                String branchLastPart1 = branchNodeIndex1.substring((branchNodeIndex1.lastIndexOf(".")) + 1, branchNodeIndex1.length());

                                String query4 = "SELECT index_no from tree1 where active='Y' and node_id = (SELECT node_parent_id FROM tree1 where active='Y' and "
                                        + "node_id = " + branchNodeId1 + ")";
                                ResultSet rs4 = connection.prepareStatement(query4).executeQuery();
                                if (rs4.next()) {
                                    branchFirstPart1 = rs4.getString("index_no");
                                }

                                branchNodeIndex = branchFirstPart1 + "." + branchLastPart1;
                                String query5 = "Update tree1 SET index_no = ? where active='Y' and node_id = " + branchNodeId1;
                                PreparedStatement pstmt1 = connection.prepareStatement(query5);
                                pstmt1.setString(1, branchNodeIndex);
                                rowupdated = pstmt1.executeUpdate();

                            }
                            /* code to update index_no of next generation -- ends */
                            System.out.println("PurposeHeaderModel - updatePrevGenIndex() - update successfull");
                        } else {
                            System.out.println("PurposeHeaderModel - updatePrevGenIndex() - update unsuccessfull");
                        }
                    }
                }
            } else {
                String query = "SELECT COUNT(*) FROM tree1 where active='Y' and node_parent_id = " + NodePrevParentId;
                ResultSet rset = connection.prepareStatement(query).executeQuery();
                if (rset.next()) {
                    count = Integer.parseInt(rset.getString(1));  // count>=2 always
                }
                count = count + 1; // not required
                if (count > 0) {    // not required
                    String query1 = "SELECT node_id FROM tree1 where active='Y' and node_parent_id = " + NodePrevParentId;
                    ResultSet rs = connection.prepareStatement(query1).executeQuery();
                    while (rs.next()) {
                        childList.add(rs.getInt("node_id"));
                    }
                    int size = childList.size();
                    for (int i = 0; i < size; i++) {
                        branchNodeId = childList.get(i);
                        branchNodeIndex = getChildIndex(branchNodeId);
                        firstPart = branchNodeIndex.substring(0, branchNodeIndex.lastIndexOf("."));
                        branchLastPart = Integer.parseInt(branchNodeIndex.substring((branchNodeIndex.lastIndexOf(".")) + 1, branchNodeIndex.length()));
                        if (lastPart != count) {  // lastPart < count
                            if (lastPart == 1) {
                                branchLastPart = branchLastPart - 1;
                            } else {
                                if (branchLastPart > lastPart) {
                                    branchLastPart = branchLastPart - 1;
                                }
                            }

                            if (branchLastPart < lastPart) {
                            } else {
                                branchNodeIndex = firstPart + "." + String.valueOf(branchLastPart);
                                String query2 = "Update tree1 SET index_no = ? where active='Y' and node_id = " + branchNodeId;
                                PreparedStatement pstmt = connection.prepareStatement(query2);
                                pstmt.setString(1, branchNodeIndex);
                                rowsAffected = pstmt.executeUpdate();
                            }
                        }
                        if (rowsAffected > 0) {
                            /* code to update index_no of next generation -- starts */
                            childList1.add(branchNodeId);
                            for (int j = 0; j < childList1.size(); j++) {
                                branchNodeId = childList1.get(j);
                                String query3 = "SELECT node_id FROM tree1 where active='Y' and node_parent_id = " + branchNodeId;
                                ResultSet rs1 = connection.prepareStatement(query3).executeQuery();
                                while (rs1.next()) {
                                    childList1.add(rs1.getInt("node_id"));
                                }
                            }

                            int size1 = childList1.size();
                            for (int k = 0; k < (size1 - 1); k++) {
                                int branchNodeId1 = childList1.get(k + 1);
                                String branchNodeIndex1 = getChildIndex(branchNodeId1);
                                String branchLastPart1 = branchNodeIndex1.substring((branchNodeIndex1.lastIndexOf(".")) + 1, branchNodeIndex1.length());

                                String query4 = "SELECT index_no from tree1 where active='Y' and node_id = (SELECT node_parent_id FROM tree1 where active='Y' and "
                                        + "node_id = " + branchNodeId1 + ")";
                                ResultSet rs4 = connection.prepareStatement(query4).executeQuery();
                                if (rs4.next()) {
                                    branchFirstPart1 = rs4.getString("index_no");
                                }

                                branchNodeIndex = branchFirstPart1 + "." + branchLastPart1;
                                String query5 = "Update tree1 SET index_no = ? where active='Y' and node_id = " + branchNodeId1;
                                PreparedStatement pstmt1 = connection.prepareStatement(query5);
                                pstmt1.setString(1, branchNodeIndex);
                                rowupdated = pstmt1.executeUpdate();

                            }
                            /* code to update index_no of next generation -- ends */
                            System.out.println("PurposeHeaderModel - updatePrevGenIndex() - update successfull");
                        } else {
                            System.out.println("PurposeHeaderModel - updatePrevGenIndex() - update unsuccessfull");
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel updatePrevGenIndex() Error: " + e);
        }
        return rowsAffected;
    }

    public String getNodeId_Gen(String parentHeaderName) {
        String list = "";
        String query = "SELECT node_id,generation_no FROM node1 WHERE active='Y' and node_name = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, parentHeaderName);
            ResultSet rset = pstmt.executeQuery();
            if (rset.next())
            {
                list = rset.getInt("node_id") + "," + rset.getString("generation_no");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getNodeId_Gen() Error: " + e);
        }

        return list;
    }

    public int getGenerationNo(int NodeParentId) {
        int generation_no = 0, count = 0, parent_gen_no = 0;
        String query = "SELECT generation_no FROM node1 where active = 'Y' and node_id = " + NodeParentId;
        try {
            ResultSet rset = connection.prepareStatement("SELECT COUNT(*) FROM node1 where active = 'Y' ").executeQuery();
            if (rset.next()) {
                count = Integer.parseInt(rset.getString(1));
            }
            if (count == 0) {
                generation_no = 0;
            } else {
                ResultSet rst = connection.prepareStatement(query).executeQuery();
                if (rst.next()) {
                    parent_gen_no = rst.getInt("generation_no");
                }
                generation_no = parent_gen_no + 1;
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getGenerationNo() Error: " + e);
        }

        return generation_no;
    }

    public int getNodeParentRevNo(int NodeParentId) {
        int revision_no = 0;
        String query = "SELECT revision_no FROM node1 where active='Y' and node_id = " + NodeParentId;
        try {
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            rst.next();
            revision_no = rst.getInt("revision_no");
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getNodeParentRevNo() Error: " + e);
        }
        return revision_no;
    }

    public int getNodeRev(int NodeId) {
        int revision_no = 0;
        String query = "SELECT revision_no FROM node1 where active='Y' and node_id = " + NodeId;
        try {
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            rst.next();
            revision_no = rst.getInt("revision_no");
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getNodeRev() Error: " + e);
        }
        return revision_no;
    }

    public int getNodeRev1(String NodeName) {
        int revision_no = 0;
        String query = "SELECT revision_no FROM node1 where active='Y' and node_name = ? ";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, NodeName);
            ResultSet rst = pstmt.executeQuery();
            if (rst.next()) {
                revision_no = rst.getInt("revision_no");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getNodeRev1() Error: " + e);
        }
        return revision_no;
    }

    public int getTreeParentId(int NodeParentId) {
        int tree_parent_id = 0;
        String query = "SELECT tree_id FROM tree1 where active='Y' and node_id = " + NodeParentId;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            try {
                tree_parent_id = rset.getInt("tree_id");
            } catch (Exception e) {
                tree_parent_id = 0;
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getTreeParentId() Error: " + e);
        }
        return tree_parent_id;
    }

    public String check_isSuperChild(int NodeParentId, int NodeId) {
        String isSuperChild = "";
        int genNo = 0, maxGenNo = 0;
        String query = "Update tree1 SET isSuperChild='N' where node_id = " + NodeParentId;
        String query1 = "SELECT generation_no FROM node1 where node_id = " + NodeId;
        String query2 = "SELECT max(generation_no) as generation_no FROM node1";
//       try{
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            int rowsaffected = pstmt.executeUpdate();
            if (rowsaffected > 0) {
                System.out.println("PurposeHeaderModel - check_isSuperChild() - update successfull");
            } else {
                System.out.println("PurposeHeaderModel - check_isSuperChild() - update unsuccessfull");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel - check_isSuperChild() - update block error: " + e);
        }

//             try{
//               ResultSet rst = connection.prepareStatement(query1).executeQuery();
//               rst.next();
//               genNo = rst.getInt("generation_no");
//             } catch(Exception e){
//                 System.out.println("PurposeHeaderModel - check_isSuperChild() - query1 block error: " + e);
//               }
//
//            try{
//              ResultSet rtst = connection.prepareStatement(query2).executeQuery();
//              rtst.next();
//              maxGenNo = rtst.getInt("generation_no");
//            } catch(Exception e){
//                 System.out.println("PurposeHeaderModel - check_isSuperChild() - query2 block error: " + e);
//               }
//
//       if(genNo < maxGenNo){
//           isSuperChild = "N";
//        } else{
//           isSuperChild = "Y";
//         }
        isSuperChild = "Y";

//       } catch(Exception e){
//           System.out.println("PurposeHeaderModel - check_isSuperChild() error: " + e);
//        }

        return isSuperChild;
    }

    public String getIndexNo(int NodeParentId, int NodeId) {
        String index_no = "", parentIndexNo = "";
        int count = 0;
        String query = "SELECT COUNT(*) FROM tree1 where active='Y' and node_parent_id = " + NodeParentId;
        String query1 = "SELECT max(CAST(index_no AS UNSIGNED)) as index_no from tree1 where active='Y' and node_id IN(SELECT node_id FROM node1 where active='Y' and "
                + "generation_no = (SELECT generation_no FROM node1 where active='Y' and node_id = " + NodeId + "))";
        String query2 = "SELECT index_no FROM tree1 where active='Y' and node_id = " + NodeParentId;
        String query3 = "SELECT index_no from tree1 where active='Y' and node_id IN(SELECT node_id FROM tree1 where active='Y' and "
                + "node_parent_id = " + NodeParentId + ")";

        if (NodeParentId == 1) {
            try {
                ResultSet rset = connection.prepareStatement(query).executeQuery();
                if (rset.next()) {
                    count = Integer.parseInt(rset.getString(1));
                }
                if (count == 0) {
                    index_no = "1";
                } else {
                    ResultSet rst = connection.prepareStatement(query1).executeQuery();
                    if (rst.next()) {
                        index_no = rst.getString("index_no");
                        index_no = String.valueOf(Integer.parseInt(index_no) + 1);
                    }
                }
            } catch (Exception e) {
                System.out.println("PurposeHeaderModel getIndexNo() node_parent_id is " + NodeParentId + " try block Error: " + e);
            }
        } else {
            try {
                ResultSet rtst = connection.prepareStatement(query2).executeQuery();
                if (rtst.next()) {
                    parentIndexNo = rtst.getString("index_no");
                }

                ResultSet rset = connection.prepareStatement(query).executeQuery();
                if (rset.next()) {
                    count = Integer.parseInt(rset.getString(1));
                }
                if (count == 0) {
                    index_no = parentIndexNo + ".1";
                } else {
                    List<String> childIndexList = new ArrayList<String>();
                    List<Integer> childIndexlastPartList = new ArrayList<Integer>();
                    String childIndex = "", childIndexlastPart = "";
                    ResultSet rst = connection.prepareStatement(query3).executeQuery();
                    while (rst.next()) {
                        index_no = rst.getString("index_no");
                        childIndexList.add(index_no);
                    }
                    for (int j = 0; j < childIndexList.size(); j++) {
                        childIndex = childIndexList.get(j);
                        childIndexlastPart = childIndex.substring((index_no.lastIndexOf(".")) + 1, childIndex.length());
                        childIndexlastPartList.add(Integer.parseInt(childIndexlastPart));
                    }
                    int maxValue = Collections.max(childIndexlastPartList);
                    index_no = parentIndexNo + "." + String.valueOf(maxValue + 1);
                }
            } catch (Exception e) {
                System.out.println("PurposeHeaderModel getIndexNo() node_parent_id is " + NodeParentId + " try block Error: " + e);
            }
        }

        return index_no;
    }

//    public String getIndexNo_1(int NodeParentId, int NodeId) {
//       String index_no = "", parentIndexNo="";
//       int count = 0;
//       String query = "SELECT COUNT(*) FROM tree where active='Y' and node_parent_id = " + NodeParentId;
//       String query1 = "SELECT max(CAST(index_no AS UNSIGNED)) as index_no from tree where active='Y' and node_id IN(SELECT node_id FROM tree where active='Y' and "
//               + "node_parent_id = " + NodeParentId + ")";
//       String query2 = "SELECT index_no FROM tree where active='Y' and node_id = " + NodeParentId;
//       String query3 = "SELECT index_no from tree where active='Y' and node_id IN(SELECT node_id FROM tree where active='Y' and "
//               + "node_parent_id = " + NodeParentId + ")";
//
//       if(NodeParentId == 1){
//         try {
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            if(rset.next())
//            count = Integer.parseInt(rset.getString(1));
//            if(count == 0){
//                index_no = "1";
//            } else {
//                ResultSet rst = connection.prepareStatement(query1).executeQuery();
//                if(rst.next()){
//                index_no = rst.getString("index_no");
//                index_no = String.valueOf(Integer.parseInt(index_no)+1);
//                }
//            }
//        } catch (Exception e) {
//            System.out.println("PurposeHeaderModel getIndexNo_1() node_parent_id is " + NodeParentId + " try block Error: " + e);
//        }
//       } else {
//           try {
//            ResultSet rtst = connection.prepareStatement(query2).executeQuery();
//            if(rtst.next())
//            parentIndexNo = rtst.getString("index_no");
//
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            if(rset.next())
//            count = Integer.parseInt(rset.getString(1));
//            if(count == 0){
//                index_no = parentIndexNo + ".1";
//            } else {
//                List<String> childIndexList = new ArrayList<String>();
//                List<Integer> childIndexlastPartList = new ArrayList<Integer>();
//                String childIndex = "" , childIndexlastPart = "" ;
//                ResultSet rst = connection.prepareStatement(query3).executeQuery();
//                while(rst.next()){
//                index_no = rst.getString("index_no");
//                childIndexList.add(index_no);
//                }
//                for(int j=0;j<childIndexList.size();j++){
//                childIndex = childIndexList.get(j);
//                childIndexlastPart = childIndex.substring((index_no.lastIndexOf("."))+1, childIndex.length());
//                childIndexlastPartList.add(Integer.parseInt(childIndexlastPart));
//                }
//                int maxValue=Collections.max(childIndexlastPartList);
//                index_no = parentIndexNo + "." + String.valueOf(maxValue+1);
//            }
//
//        } catch (Exception e) {
//            System.out.println("PurposeHeaderModel getIndexNo_1() node_parent_id is " + NodeParentId + " try block Error: " + e);
//        }
//       }
//
//        return index_no;
//    }
    public String getIndexNo_2(int NodeParentId, int NodeId) {
        String index_no = "", parentIndexNo = "", childIndexNo = "";
//       int count = 0;
//       String query = "SELECT COUNT(*) FROM tree where active='Y' and node_parent_id = " + NodeParentId;
//       String query1 = "SELECT max(index_no) as index_no from tree where active='Y' and node_id IN(SELECT node_id FROM tree where active='Y' and "
//               + "node_parent_id = " + NodeParentId + ")";
        String query2 = "SELECT index_no FROM tree1 where active='Y' and node_id = ? ";

//       if(NodeParentId == 1){
//         try {
//            ResultSet rset = connection.prepareStatement(query).executeQuery();
//            rset.next();
//            count = Integer.parseInt(rset.getString(1));
//            if(count == 0){
//                index_no = "1";
//            } else {
//                ResultSet rst = connection.prepareStatement(query1).executeQuery();
//                rst.next();
//                index_no = rst.getString("index_no");
//                index_no = String.valueOf(Integer.parseInt(index_no)+1);
//            }
//        } catch (Exception e) {
//            System.out.println("PurposeHeaderModel getIndexNo_2() node_parent_id is " + NodeParentId + " try block Error: " + e);
//        }
//       } else {
        try {
            PreparedStatement pstmt = connection.prepareStatement(query2);
            pstmt.setInt(1, NodeParentId);
            ResultSet rtst = pstmt.executeQuery();
            if (rtst.next()) {
                parentIndexNo = rtst.getString("index_no");
            }

            PreparedStatement pstm = connection.prepareStatement(query2);
            pstm.setInt(1, NodeId);
            ResultSet rst = pstm.executeQuery();
            if (rst.next()) {
                childIndexNo = rst.getString("index_no");
            }

            String firstPart = childIndexNo.substring(0, childIndexNo.lastIndexOf("."));
            String lastPart = childIndexNo.substring((childIndexNo.lastIndexOf(".")) + 1, childIndexNo.length());
            index_no = parentIndexNo + "." + lastPart;

        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getIndexNo_2() node_parent_id is " + NodeParentId + " try block Error: " + e);
        }
//       }

        return index_no;
    }

    public String getChildIndex(int NodeId) {
        String index_no = "";
        String query = "SELECT index_no FROM tree1 where active='Y' and node_id = " + NodeId;
        try {
            ResultSet rst = connection.prepareStatement(query).executeQuery();
            if (rst.next()) {
                index_no = rst.getString("index_no");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getChildIndex() Error: " + e);
        }
        return index_no;
    }

    public int getMaxTreeRev(int Node_id) {
        int maxRevision = 0;
        String query = "select max(revision) revision from tree1 where node_id = " + Node_id;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            if (rset.next()) {
                maxRevision = rset.getInt("revision");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel getMaxTreeRev() Error: " + e);
        }

        return maxRevision;
    }

    public int deleteRecord(int NodeHeaderId) {
        int rowsAffected = 0, rowsAffected1 = 0, rowsAffected2 = 0, rowupdated = 0, maxRevision = 0;
        String query = "UPDATE node1 SET active = 'N' WHERE active='Y' and node_id = " + NodeHeaderId;
        maxRevision = getMaxTreeRev(NodeHeaderId);
        String query1 = "UPDATE tree1 SET active = 'N' and index_no = '0' WHERE active='Y' and revision = " + maxRevision + " and node_id = " + NodeHeaderId;
        String NodeHeaderIndex = "";

        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel deleteRecord() query block Error: " + e);
        }

        if (rowsAffected > 0) {
            System.out.println("Record deleted from node for node_id = " + NodeHeaderId);

            try {
                rowsAffected1 = connection.prepareStatement(query1).executeUpdate();
            } catch (Exception e) {
                System.out.println("PurposeHeaderModel deleteRecord() query1 block Error: " + e);
            }

            try {
                if (rowsAffected1 > 0) {
                    System.out.println("Record deleted from tree for node_id = " + NodeHeaderId);
                    /* code to update index_no of next generation -- starts */
                    List<Integer> childList1 = new ArrayList<Integer>();
                    childList1.add(NodeHeaderId);
                    for (int j = 0; j < childList1.size(); j++) {
                        NodeHeaderId = childList1.get(j);
                        String query3 = "SELECT node_id FROM tree1 where active='Y' and node_parent_id = " + NodeHeaderId;
                        ResultSet rs1 = connection.prepareStatement(query3).executeQuery();
                        while (rs1.next()) {
                            childList1.add(rs1.getInt("node_id"));
                        }
                    }

                    int size1 = childList1.size();
                    String branchFirstPart1 = "", branchNodeIndex = "";
                    for (int k = 0; k < (size1 - 1); k++) {
                        int branchNodeId1 = childList1.get(k + 1);
                        String branchNodeIndex1 = getChildIndex(branchNodeId1);
                        String branchLastPart1 = branchNodeIndex1.substring((branchNodeIndex1.lastIndexOf(".")) + 1, branchNodeIndex1.length());
//                              if(k==0){
//                                  int NodeHeaderIdMain = childList1.get(k);
//                                  String query4 = "SELECT index_no from tree where revision = (select max(revision) revision from tree where node_id = " + NodeHeaderIdMain + ") and node_id = " + NodeHeaderIdMain;
//                                  ResultSet rs4 = connection.prepareStatement(query4).executeQuery();
//                                  if(rs4.next())
//                                  branchFirstPart1 = rs4.getString("index_no");
//                              } else{
//                                  String query4 = "SELECT index_no from tree where active='Y' and node_id = (SELECT node_parent_id FROM tree where active='Y' and "
//                                                  + "node_id = " + branchNodeId1 + ")";
//                                  ResultSet rs4 = connection.prepareStatement(query4).executeQuery();
//                                  if(rs4.next())
//                                  branchFirstPart1 = rs4.getString("index_no");
//                              }

                        branchNodeIndex = "0." + branchLastPart1;
                        String query5 = "Update tree1 SET index_no = ? where active='Y' and node_id = " + branchNodeId1;
                        PreparedStatement pstmt1 = connection.prepareStatement(query5);
                        pstmt1.setString(1, branchNodeIndex);
                        rowupdated = pstmt1.executeUpdate();

                    }
                    /* code to update index_no of next generation -- ends */
                    System.out.println("PurposeHeaderModel - deleteRecord() - query1 update successfull");
                    message = "Record deleted successfully.";
                    msgBgColor = COLOR_OK;
                } else {
                    System.out.println("PurposeHeaderModel - deleteRecord() - query1 update unsuccessfull");
                    message = "Cannot delete the record, some error.";
                    msgBgColor = COLOR_ERROR;
                }

            } catch (Exception e) {
                System.out.println("PurposeHeaderModel deleteRecord() query2 block Error: " + e);
            }

        } else {
            System.out.println("PurposeHeaderModel - deleteRecord() - query update unsuccessfull");
            message = "Cannot delete the record, some error.";
            msgBgColor = COLOR_ERROR;
        }
        return rowupdated;
    }

    public int updateRecord(String NodeHeaderName, int NodeHeaderId) {
        int rowsaffected = 0, rowsaffected1 = 0, maxRevision = 0, incRevision = 0, rowsaffected2 = 0;
        try {
            if (!checkNodeName(NodeHeaderName)) {
                /* code for entry in node table -- starts here */
                String query2 = "select max(revision_no) revision_no from node1 "
                        + "where active = 'Y' AND node_id= " + NodeHeaderId;
                String query3 = "INSERT INTO node1(node_id,node_name, generation_no, active, revision_no, remark) "
                        + " Select node_id,?, generation_no, ?, ?, remark from node1 n where active='Y' and "
                        + " node_id= " + NodeHeaderId;
                String query4 = "Update node1 Set active = 'N' "
                        + "where node_id= " + NodeHeaderId + " And revision_no = ?";

                ResultSet rst = connection.prepareStatement(query2).executeQuery();
                if (rst.next()) {
                    maxRevision = rst.getInt("revision_no");
                }
                incRevision = maxRevision + 1;

                PreparedStatement pstmt = connection.prepareStatement(query3);
                pstmt.setString(1, NodeHeaderName);
                pstmt.setString(2, "Y");
                pstmt.setInt(3, incRevision);
                rowsaffected = pstmt.executeUpdate();

                if (rowsaffected > 0) {
                    System.out.println("PurposeHeaderModel:updateRecord - query3 - Record inserted in node successfully");
                    PreparedStatement pst = connection.prepareStatement(query4);
                    pst.setInt(1, maxRevision);
                    rowsaffected1 = pst.executeUpdate();


                    if (rowsaffected1 > 0) {
                        System.out.println("PurposeHeaderModel:updateRecord - query4 - Record updated in node successfully");
                        String query5 = "Update tree Set node_rev = " + incRevision + " where active = 'Y' AND node_id= " + NodeHeaderId;
                        PreparedStatement pstt = connection.prepareStatement(query5);
                        rowsaffected2 = pstt.executeUpdate();
                    } else {
                        System.out.println("PurposeHeaderModel:updateRecord - query4 - Record not updated in node");
                        message = "Cannot update record,Some Error";
                        msgBgColor = COLOR_ERROR;
                    }

                    if (rowsaffected2 > 0) {
                        System.out.println("PurposeHeaderModel:updateRecord - query5 - Record updated in tree successfully");
                        message = "Record updated Successfully";
                        msgBgColor = COLOR_OK;
                    } else {
                        System.out.println("PurposeHeaderModel:updateRecord - query5 - Record not updated in tree");
                        message = "Cannot update record,Some Error";
                        msgBgColor = COLOR_ERROR;
                    }

                } else {
                    System.out.println("PurposeHeaderModel:updateRecord - query3 - Record not inserted in node");
                    message = "Cannot update record,Some Error";
                    msgBgColor = COLOR_ERROR;
                }
                /* code for entry in node table -- ends here */

            } else {
                System.out.println("PurposeHeaderModel updateRecord() node_name already exists in node table viz. - " + NodeHeaderName);
                message = "Cannot Update..Node already exists";
                msgBgColor = COLOR_ERROR;
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel updateRecord() Error: " + e);
        }
        return rowsaffected;
    }

    public List<String> getParent_Header_Name(String q) {
        List<String> list = new ArrayList<String>();
        int count = 0;

        String query1 = "SELECT n.node_name "
                + " FROM node1 as n "
                + " where n.active='Y' "
                + " And n.generation_no=0 ";

        try {
            ResultSet rset = connection.prepareStatement(query1).executeQuery();
            q = q.trim();
            while (rset.next()) {
                String Parent_Header_Name = rset.getString("node_name");
                if (Parent_Header_Name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(Parent_Header_Name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Parent Header Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:PurposeHeaderModel--getParent_Header_Name() query1 block-- " + e);
        }

        String query = "SELECT n.node_name "
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' "
                // + " ORDER BY n.node_id "
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no ";

        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            q = q.trim();
            while (rset.next()) {
                String Parent_Header_Name = rset.getString("node_name");
                if (Parent_Header_Name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(Parent_Header_Name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Parent Header Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error:PurposeHeaderModel--getParent_Header_Name()-- " + e);
        }
        return list;
    }

    public List<String> getChild_Header_Name(String q, String parentHeaderName) {
        List<String> list = new ArrayList<String>();
        int genNo = 0, maxGenNo = 0, parentHeaderId = 0;

        try {
            parentHeaderId = getNodeId(parentHeaderName);
            String query1 = "SELECT generation_no FROM node1 where active='Y' and node_id = " + parentHeaderId;
            String query2 = "SELECT max(generation_no) as generation_no FROM node1 where active='Y' ";

            try {
                ResultSet rst = connection.prepareStatement(query1).executeQuery();
                rst.next();
                genNo = rst.getInt("generation_no");
            } catch (Exception e) {
                System.out.println("PurposeHeaderModel - getChild_Header_Name() - query1 block error: " + e);
            }

            try {
                ResultSet rtst = connection.prepareStatement(query2).executeQuery();
                rtst.next();
                maxGenNo = rtst.getInt("generation_no");
            } catch (Exception e) {
                System.out.println("PurposeHeaderModel - getChild_Header_Name() - query2 block error: " + e);
            }

            String query3 = "SELECT node_name from node1 As n where generation_no Between " + genNo + " And " + maxGenNo
                    + " and node_id NOT IN (SELECT node_id FROM tree1 where active='Y' AND  node_parent_id = " + parentHeaderId + ") "
                    + " and active='Y' and node_id != " + parentHeaderId
                    + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                    + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                    + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                    + " t.index_no ";

            ResultSet rset = connection.prepareStatement(query3).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String childHeaderName = rset.getString("node_name");
                if (childHeaderName.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(childHeaderName);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Child Parent Header Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: PurposeHeaderModel- getChild_Header_Name" + e);
        }
        return list;
    }

    public List<String> getSearchIndex(String q) {
        List<String> list = new ArrayList<String>();

        String query1 = "SELECT t.index_no "
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' "
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no ";

        try {
            ResultSet rset = connection.prepareStatement(query1).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String index_no = rset.getString("index_no");
                if (index_no.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(index_no);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Sub Header Index exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: PurposeHeaderModel- getSearchIndex" + e);
        }
        return list;
    }

    public List<String> getSearchSubHeader(String q) {
        List<String> list = new ArrayList<String>();

        String query1 = "SELECT n.node_name "
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' "
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no ";

        try {
            ResultSet rset = connection.prepareStatement(query1).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String node_name = rset.getString("node_name");
                if (node_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(node_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Sub Header Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: PurposeHeaderModel- getSearchSubHeader" + e);
        }
        return list;
    }

    public List<String> getSearchParentHeader(String q) {
        List<String> list = new ArrayList<String>();

        String query1 = "SELECT pn.node_name AS parent_node_name "
                + " FROM node1 as n,tree1 as t, node1 as pn "
                + " where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                + " And n.active='Y' And t.active='Y' and pn.active='Y' "
                + " AND t.index_no NOT LIKE '0%' "
                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED), "
                + " CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',-1) AS UNSIGNED), "
                + " t.index_no ";

        try {
            ResultSet rset = connection.prepareStatement(query1).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String parent_node_name = rset.getString("parent_node_name");
                if (parent_node_name.toUpperCase().startsWith(q.toUpperCase())) {
                    if (!list.contains(parent_node_name)) {
                        list.add(parent_node_name);
                    }
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Parent Header Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: PurposeHeaderModel- getSearchParentHeader" + e);
        }
        return list;
    }

    public ArrayList<PurposeHeaderNode1> getItemListForReport(String searchheader_index, String searchSub_header_name, String searchParent_header_name) {
       String mainQuery = "", selectQuery;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<PurposeHeaderNode1> mainList = new ArrayList<PurposeHeaderNode1>();
        // ArrayList<String> name1 = new ArrayList<String>();
        try {
//            if (searchheader_index.isEmpty()) {
//                mainList = getItemListFullForReport(searchheader_index, searchSub_header_name, searchParent_header_name);
//            } else {
            mainQuery = "SELECT t.index_no,t.node_id,n.node_name, pn.node_name AS parent_node_name,t.active,t.remark "
                    + "FROM node1 as n,tree1 as t, node1 as pn "
                    + "where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                    + "And n.active='Y' And t.active='Y' and pn.active='Y' AND t.index_no NOT LIKE '0%' " //index_no not like '%0%'
                    + " AND if('" + searchSub_header_name + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + searchSub_header_name + "%')"
                    + " AND if('" + searchParent_header_name + "'='', pn.node_name LIKE '%%', pn.node_name LIKE '%" + searchParent_header_name + "%')"
                    + " AND if('" + searchheader_index + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + searchheader_index + "%') "
                    + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED),CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED),t.index_no "; //ORDER BY t.index_no


            ps = connection.prepareStatement(mainQuery);
            rs = ps.executeQuery();
            while (rs.next()) {
                PurposeHeaderNode1 headerNode = new PurposeHeaderNode1();
                headerNode.setIndex_no(rs.getString("index_no"));
                headerNode.setNode_id(rs.getInt("node_id"));
                headerNode.setTree_node_name(rs.getString("node_name"));
                headerNode.setNode_parent_name(rs.getString("parent_node_name"));
                mainList.add(headerNode);
            }
//            }

        } catch (Exception e) {
            System.out.println("Error occured inside PurposeHeaderModel getItemListForReport : " + e);
        }
        return mainList;
    }

    public ArrayList<PurposeHeaderNode1> getItemListForReport_comment(String searchheader_index, String searchSub_header_name, String searchParent_header_name) {
        String str[] = searchheader_index.split("\\.");
        String subString = searchheader_index, tempString = "", mainSNo = "1", mainQuery = "", selectQuery;
        PreparedStatement ps;
        ResultSet rs;
        ArrayList<PurposeHeaderNode1> mainList = new ArrayList<PurposeHeaderNode1>();
        ArrayList<String> name1 = new ArrayList<String>();
        try {
            if (searchheader_index.isEmpty()) {
                mainList = getItemListFullForReport(searchheader_index, searchSub_header_name, searchParent_header_name);
            } else {
                mainQuery = "Select n.node_name, t.index_no "
                        + " from node1 as n, task as tk, tree1 as t "
                        + " where n.node_id=tk.node_id And n.node_id=t.node_id "
                        + " And n.active='Y' And tk.active='Y' And t.active='Y' AND t.index_no NOT LIKE '%0%' "
                        + " AND t.index_no = " + str[0];
                ps = connection.prepareStatement(mainQuery);
                rs = ps.executeQuery();
                while (rs.next()) {
                    PurposeHeaderNode1 headerListReport = new PurposeHeaderNode1();
                    headerListReport.setIndex_no(rs.getString("index_no"));
                    headerListReport.setTree_node_name(rs.getString("node_name"));
                    name1.add(searchheader_index);
                    headerListReport.setName(name1);
                    try {
                        ArrayList<PurposeHeaderNode1> headerList = new ArrayList<PurposeHeaderNode1>();
                        for (int i = 0; i < str.length; i++) {
                            if (i == str.length - 1) {
                                //selectQuery = "select item_s_no,item_name from item AS i, sor_for AS sf WHERE sf.sor_for_id=i.sor_for_id AND active='Y'AND item_type= '" + search_item_type + "' AND sor_for_name= '" + sor_for + "' AND item_s_no = '" + sNo + "'";
                                selectQuery = "Select n.node_name, t.index_no "
                                        + " from node1 as n, task as tk, tree1 as t "
                                        + " where n.node_id=tk.node_id And n.node_id=t.node_id "
                                        + " And n.active='Y' And tk.active='Y' And t.active='Y' AND t.index_no NOT LIKE '%0%' "
                                        + " AND t.index_no = '" + searchheader_index + "'";
                            } else {
                                tempString = subString.substring(0, (subString.indexOf(".", tempString.length() + 1)));
                                //selectQuery = "select item_s_no,item_name from item AS i, sor_for AS sf WHERE sf.sor_for_id=i.sor_for_id AND active='Y' AND item_type= '" + search_item_type + "' AND sor_for_name= '" + sor_for + "' AND item_s_no = '" + tempString + "'";
                                selectQuery = "Select n.node_name, t.index_no "
                                        + " from node1 as n, task as tk, tree1 as t "
                                        + " where n.node_id=tk.node_id And n.node_id=t.node_id "
                                        + " And n.active='Y' And tk.active='Y' And t.active='Y' AND t.index_no NOT LIKE '%0%' "
                                        + " AND t.index_no = '" + tempString + "'";
                            }
                            ps = connection.prepareStatement(selectQuery);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                PurposeHeaderNode1 itemHeader = new PurposeHeaderNode1();
                                itemHeader.setIndex_no(rs.getString("index_no"));
                                itemHeader.setTree_node_name(rs.getString("node_name"));
                                headerList.add(itemHeader);
                            }
                        }
                        headerListReport.setItemHeader(headerList);
                    } catch (Exception e) {
                    }
                    try {
                        ArrayList<PurposeHeaderNode1> leafList = new ArrayList<PurposeHeaderNode1>();
                        String s = "";
                        char c = searchheader_index.charAt(searchheader_index.length() - 1);
                        if (c == '.') {
                            s = "%";
                        } else {
                            s = "%";
                        }

                        String serialNo = searchheader_index.concat(s);
//                        if (isItemApproved.equals("N")) {
//                            selectQuery = "SELECT t.index_no, tk.task_id, tk.node_id, tk.amount, tk.unit, n.node_name, tk.description "
//                                    + " from node as n, task as tk, tree as t "
//                                    + " where t.index_no = SUBSTRING_INDEX('" + searchheader_index + "','.',1) "
//                                    + " And n.active='Y' And tk.active='Y' And t.active='Y' "
//                                    + " and n.node_id=tk.node_id And n.node_id=t.node_id AND t.index_no like '" + searchheader_index + "" + s + "' ";

                        selectQuery = "SELECT t.index_no,t.node_id,n.node_name, pn.node_name AS parent_node_name,t.active,t.remark "
                                + "FROM node1 as n,tree1 as t, node1 as pn "
                                + "where n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                                + "And n.active='Y' And t.active='Y' and pn.active='Y' AND t.index_no NOT LIKE '%0%' "
                                + " AND if('" + searchSub_header_name + "'='', n.node_name LIKE '%%', n.node_name LIKE '%" + searchSub_header_name + "%')"
                                + " AND if('" + searchParent_header_name + "'='', pn.node_name LIKE '%%', pn.node_name LIKE '%" + searchParent_header_name + "%')"
                                + " AND if('" + searchheader_index + "'='', t.index_no LIKE '%%',t.index_no LIKE '" + searchheader_index + "%') "
                                + " ORDER BY CAST(SUBSTRING_INDEX(t.index_no, '.', 1) AS UNSIGNED),CAST(SUBSTRING_INDEX(SUBSTRING_INDEX(t.index_no,'.',2),'.',1) AS UNSIGNED),t.index_no "; //ORDER BY t.index_no



//                        } else {
//                            selectQuery = "SELECT t.index_no, tk.task_id, tk.node_id, tk.amount, tk.unit, n.node_name, tk.description "
//                                    + " from node as n, task as tk, tree as t "
//                                    + " where "
//                                    //+ " t.index_no = SUBSTRING_INDEX('" + sNo + "','.',1) "
//                                    + " And n.active='Y' And tk.active='Y' And t.active='Y' "
//                                    + " and n.node_id=tk.node_id And n.node_id=t.node_id AND t.index_no like '" + searchheader_index + "" + s + "' ";
//
//                        }

                        ps = connection.prepareStatement(selectQuery);
//                         ps.setDate(1, setDate(est_created_date));
                     /*   ps.setString(2, sNo);
                        ps.setString(3, serialNo);  */
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            PurposeHeaderNode1 itemLeaf = new PurposeHeaderNode1();
                            itemLeaf.setIndex_no(rs.getString("index_no"));
                            itemLeaf.setTree_node_name(rs.getString("node_name"));
                            itemLeaf.setNode_parent_name(rs.getString("parent_node_name"));
//                            itemLeaf.setItem_rate(rs.getDouble("amount"));
//                            itemLeaf.setRemark(rs.getString("description"));
                            leafList.add(itemLeaf);
                        }
                        headerListReport.setItemLeaf(leafList);

                    } catch (Exception e) {
                    }
                    //PurposeHeaderNode.setSor_for(sor_for);
                    mainList.add(headerListReport);
                }
            }

        } catch (Exception e) {
            System.out.println("Error occured inside PurposeHeaderModel getItemListForReport : " + e);
        }
        return mainList;
    }

    public ArrayList<PurposeHeaderNode1> getItemListFullForReport(String searchheader_index, String searchSub_header_name, String searchParent_header_name) {
        String str[] = searchheader_index.split("\\.");
        String subString = searchheader_index, tempString = "", mainSNo = "1", mainQuery = "", selectQuery;
        PreparedStatement ps;
        ResultSet rs, rsMain;
        ArrayList<PurposeHeaderNode1> mainList = new ArrayList<PurposeHeaderNode1>();
        ArrayList<String> name1 = new ArrayList<String>();
        try {
            if (searchheader_index.isEmpty()) {

                //mainQuery = "select  item_s_no, item_name from item AS i, sor_for AS sf WHERE sf.sor_for_id=i.sor_for_id AND active='Y' AND sor_for_name= '" + sor_for + "'AND item_type= '" + search_item_type + "' AND LOCATE('.', item_s_no) = 0 ";
                mainQuery = "Select n.node_name, t.index_no "
                        + " from node1 as n, task as tk, tree1 as t "
                        + " where n.node_id=tk.node_id And n.node_id=t.node_id "
                        + " And n.active='Y' And tk.active='Y' And t.active='Y' AND t.index_no NOT LIKE '%0%' "
                        + " AND LOCATE('.', t.index_no) = 0 ";
                // + " And t.index_no like '" + sNo + "" + s + "'"   //AND item_type= '" + search_item_type + "'
                // + " AND IF('" + search_item_name + "'='' , node_name LIKE '%%' ,node_name LIKE '" + search_item_name + "')"
                // + " ORDER BY tk.task_id";

                ps = connection.prepareStatement(mainQuery);
                rsMain = ps.executeQuery();
                while (rsMain.next()) {
                    PurposeHeaderNode1 itemListReport = new PurposeHeaderNode1();
                    itemListReport.setIndex_no(rsMain.getString("index_no"));
                    itemListReport.setTree_node_name(rsMain.getString("node_name"));
                    str[0] = rsMain.getString("index_no");
                    searchheader_index = rsMain.getString("index_no");
                    name1.add(searchheader_index);
                    itemListReport.setName(name1);
                    try {
                        ArrayList<PurposeHeaderNode1> headerList = new ArrayList<PurposeHeaderNode1>();
                        for (int i = 0; i < str.length; i++) {
                            if (i == str.length - 1) {
                                //selectQuery = "select item_s_no,item_name from item  AS i, sor_for AS sf WHERE sf.sor_for_id=i.sor_for_id AND active='Y' AND sor_for_name= '" + sor_for + "'and item_type= '" + search_item_type + "' AND item_s_no = '" + sNo + "'";
                                selectQuery = "Select n.node_name, t.index_no "
                                        + " from node1 as n, task as tk, tree1 as t "
                                        + " where n.node_id=tk.node_id And n.node_id=t.node_id "
                                        + " And n.active='Y' And tk.active='Y' And t.active='Y' AND t.index_no NOT LIKE '%0%' "
                                        + " AND t.index_no = '" + searchheader_index + "'";
                            } else {
                                tempString = subString.substring(0, (subString.indexOf(".", tempString.length() + 1)));
                                // selectQuery = "select item_s_no,item_name from item  AS i, sor_for AS sf WHERE sf.sor_for_id=i.sor_for_id AND active='Y' AND sor_for_name= '" + sor_for + "' and item_type= '" + search_item_type + "' AND item_s_no = '" + tempString + "'";
                                selectQuery = "Select n.node_name, t.index_no "
                                        + " from node1 as n, task as tk, tree1 as t "
                                        + " where n.node_id=tk.node_id And n.node_id=t.node_id "
                                        + " And n.active='Y' And tk.active='Y' And t.active='Y' AND t.index_no NOT LIKE '%0%' "
                                        + " AND t.index_no = '" + tempString + "'";
                            }
                            ps = connection.prepareStatement(selectQuery);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                PurposeHeaderNode1 itemHeader = new PurposeHeaderNode1();
                                itemHeader.setIndex_no(rs.getString("index_no"));
                                itemHeader.setTree_node_name(rs.getString("node_name"));
                                headerList.add(itemHeader);
                            }
                        }
                        itemListReport.setItemHeader(headerList);
                    } catch (Exception e) {
                    }
                    try {
                        ArrayList<PurposeHeaderNode1> leafList = new ArrayList<PurposeHeaderNode1>();
                        String s = "";
                        char c = searchheader_index.charAt(searchheader_index.length() - 1);
                        if (c == '.') {
                            s = "%";
                        } else {
                            s = "%";
                        }
                        /*
                        if (c == '.') {
                        s = "%";
                        } else {
                        s = ".%";
                        }
                         */
                        String serialNo = searchheader_index.concat(s);

                        //selectQuery = "select * from item  AS i, sor_for AS sf WHERE sf.sor_for_id=i.sor_for_id AND active='Y' AND sor_for_name= '" + sor_for + "' and item_type= '" + search_item_type + "'"
                        //         + " AND if((select count(*) from item where item_s_no like ?)=0,item_s_no=?,item_s_no like ?)";

                        selectQuery = "Select n.node_name, t.index_no , pn.node_name AS parent_node_name " // tk.amount,  tk.unit, tk.description
                                + " from node1 as n, task as tk, tree1 as t ,node1 as pn "
                                + " where n.node_id=tk.node_id And n.node_id=t.node_id AND t.node_parent_id=pn.node_id "
                                + " And n.active='Y' And tk.active='Y' And t.active='Y' And pn.active='Y' AND t.index_no NOT LIKE '%0%' "
                                + " AND if((select count(*) from tree where index_no like ?)=0,index_no=?,index_no like ?)";
                        ps = connection.prepareStatement(selectQuery);
                        ps.setString(1, serialNo);
                        ps.setString(2, searchheader_index);
                        ps.setString(3, serialNo);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            PurposeHeaderNode1 itemLeaf = new PurposeHeaderNode1();
                            itemLeaf.setIndex_no(rs.getString("index_no"));
                            itemLeaf.setTree_node_name(rs.getString("node_name"));
                            itemLeaf.setNode_parent_name(rs.getString("parent_node_name"));
//                            itemLeaf.setItem_rate(rs.getDouble("amount"));
//                            itemLeaf.setRemark(rs.getString("description"));
                            leafList.add(itemLeaf);
                        }
                        itemListReport.setItemLeaf(leafList);

                    } catch (Exception e) {
                    }
                    //itemListReport.setSor_for(sor_for);
                    mainList.add(itemListReport);
                }
            }

        } catch (Exception e) {
        }
        return mainList;
    }

    public byte[] generateHierarchyDetailPdfList(String jrxmlFilePath, ArrayList<PurposeHeaderNode1> finalMainList) {
        byte[] reportInbytes = null;
        HashMap mymap = new HashMap();
        try {
            ArrayList<PurposeHeaderNode1> dataList = finalMainList;
            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(dataList);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, beanColDataSource);
        } catch (Exception e) {
            System.out.println("Error: in PurposeHeaderModel generateHierarchyDetailPdfList() JRException: " + e);
        }
        return reportInbytes;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            } else {
                System.out.println("PurposeHeaderModel closeConnection() : Connection already closed ");
            }
        } catch (Exception e) {
            System.out.println("PurposeHeaderModel closeConnection() Error: " + e);
        }
    }

    public String getMessage() {
        return message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

}
