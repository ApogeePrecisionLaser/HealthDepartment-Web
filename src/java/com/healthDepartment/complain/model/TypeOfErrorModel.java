/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.complain.model;

import com.healthDepartment.complain.tableClasses.TypeOfError;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 *
 * @author Shobha
 */
public class TypeOfErrorModel {

    public static Connection connection;
 private static String message;

    public static String getMessage() {
        return message;
    }

    public static void setMessage(String message) {
        TypeOfErrorModel.message = message;
    }



    public static Connection getConnection() {
        return connection;
    }

    public static void setConnection(Connection connection) {
        TypeOfErrorModel.connection = connection;
    }
    public List getPriority(String q){

     List<String> li = new ArrayList<String>();
        String query = " SELECT priority_name FROM priority "
                + " GROUP BY priority_name "
                + " ORDER BY priority_name ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("priority_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getPriority ERROR inside TypeOfErrorModel" + e);
        }
        return li;


    }

    public List getDesignation(String q){

       List<String> li = new ArrayList<String>();
        String query = " SELECT designation FROM designation "
                + " GROUP BY designation "
                + " ORDER BY designation ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("designation");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getDesignation ERROR inside TypeOfErrorModel" + e);
        }
        return li;
    }

    public List getTypeOfError(String q){

         List<String> li = new ArrayList<String>();
        String query = " SELECT error_name FROM type_of_error "
                + " GROUP BY error_name "
                + " ORDER BY error_name ";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            int count = 0;
            q = q.trim();
            while (rs.next()) {    // move cursor from BOR to valid record.
                String name = rs.getString("error_name");
                if (name.toUpperCase().startsWith(q.toUpperCase())) {
                    li.add(name);
                    count++;
                }
            }
            if (count == 0) {
                li.add("No such Status exists");
            }
        } catch (Exception e) {
            System.out.println("getDesignation ERROR inside TypeOfErrorModel" + e);
        }
        return li;

    }
    public int getPriorityId(String priority){
        PreparedStatement ps=null;
        String query="select priority_id from priority where priority_name='"+priority+"'";
        int priority_id=0;
        try{
        ps=connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            priority_id=rs.getInt("priority_id");
        }
        }
        catch(Exception e){

        }
        return priority_id;
    }




    public int getDesignationId(String designation){
        PreparedStatement ps=null;
        String query="select designation_id from designation where designation='"+designation+"'";
        int designation_id=0;
        try{
        ps=connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            designation_id=rs.getInt("designation_id");
        }
        }
        catch(Exception e){

        }
        return designation_id;
    }

    public void save(TypeOfError typeOfError){
        PreparedStatement ps=null;
        String query = "insert into type_of_error(error_name,remark,priority_id) values(?,?,?)";
        try{
            ps=connection.prepareStatement(query);
            ps.setString(1,typeOfError.getError_name());
            ps.setString(2,typeOfError.getRemark());
//            ps.setString(3,typeOfError.getValue());
//            ps.setInt(4,typeOfError.getDesignation_id());
            ps.setInt(3,typeOfError.getPriority_id());
            int x=ps.executeUpdate();
            if(x>0){
                System.out.println("Record inserted successfully");
                setMessage("Record inserted");
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public void update(TypeOfError typeOfError){
        PreparedStatement ps=null;
        String query = "update type_of_error "
                +" set error_name=?,remark=?,priority_id=? "
                +" where type_of_error_id=? ";

        try{
            ps=connection.prepareStatement(query);
            ps.setString(1,typeOfError.getError_name());
            ps.setString(2,typeOfError.getRemark());
//            ps.setString(3,typeOfError.getValue());
//            ps.setInt(4,typeOfError.getDesignation_id());
            ps.setInt(3,typeOfError.getPriority_id());
            ps.setInt(4,Integer.parseInt(typeOfError.getErrorType_id()));
            int x=ps.executeUpdate();
            if(x>0){
                System.out.println("Record updated successfully");
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void delete(TypeOfError typeOfError){
        PreparedStatement ps=null;
        String query = "delete from type_of_error "
                +" where type_of_error_id=? ";

        try{
            ps=connection.prepareStatement(query);

            ps.setInt(1,Integer.parseInt(typeOfError.getErrorType_id()));
            int x=ps.executeUpdate();
            if(x>0){
                System.out.println("Record deleted successfully");
            }

        }catch(Exception e){
            System.out.println(e);
        }


    }

    public int getNoOfRows(String searchTypeOfError,String searchDesignation){
        int noOfRows=0;
    PreparedStatement ps=null;
    String query="select count(*) "
                        +" from priority p,type_of_error t "
                        +" where t.priority_id=p.priority_id "
//                   +" And IF('" + searchDesignation + "' = '',designation LIKE '%%', designation=?) "
                   +" And IF('" + searchTypeOfError + "' = '',error_name LIKE '%%', error_name=?) ";
    try{

        ps=connection.prepareStatement(query);

//        ps.setString(1,searchDesignation);
        ps.setString(1,searchTypeOfError);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            noOfRows=Integer.parseInt(rs.getString(1));
        }

    }catch(Exception e){
        System.out.println(e);
    }

    return noOfRows;

    }

    public List showData(int lowerLimit, int noOfRowsToDisplay,String searchTypeOfError,String searchDesignation){
        PreparedStatement ps = null;
        List list = new ArrayList();
    String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        if(lowerLimit == -1){
            addQuery = "";
        }
        String query="select type_of_error_id,error_name,t.remark,priority_name "
                        +" from priority p,type_of_error t "
                        +" where t.priority_id=p.priority_id "
//                        +" And IF('" + searchDesignation + "' = '',designation LIKE '%%', designation=?) "
                        +" And IF('" + searchTypeOfError + "' = '',error_name LIKE '%%', error_name=?) "
                        +addQuery;

        try{
    ps= connection.prepareStatement(query);
//        ps.setString(1,searchDesignation);
        ps.setString(1,searchTypeOfError);
    ResultSet rs=ps.executeQuery();
    while(rs.next()){
     TypeOfError typeOfError = new TypeOfError();
     typeOfError.setErrorType_id(rs.getString("type_of_error_id"));
     typeOfError.setError_name(rs.getString("error_name"));
     typeOfError.setRemark(rs.getString("remark"));
    // typeOfError.setValue(rs.getString("value"));
     //typeOfError.setDesignation(rs.getString("designation"));
     typeOfError.setPriority_name(rs.getString("priority_name"));

     list.add(typeOfError);
    }
    }catch(Exception e){
        System.out.println(e);
    }
    return list;

    }

    public  byte[] generateRecordList(String jrxmlFilePath,List li)
     {
                byte[] reportInbytes = null;
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(li);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null, jrBean);
                } catch (Exception e) {

                    System.out.println("TypeOfErrorModel generatReport() JRException: " + e);
                }
                return reportInbytes;
     }

}
