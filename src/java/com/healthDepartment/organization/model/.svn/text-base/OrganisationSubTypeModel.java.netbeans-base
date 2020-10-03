package com.healthDepartment.organization.model;

import com.healthDepartment.organization.tableClasses.OrganisationSubType;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;

/**
 *
 * @author Soft_Tech
 */
public class OrganisationSubTypeModel {

    private Connection connection;
   
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "lightyellow";
    private final String COLOR_ERROR = "red";
    private KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    private UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();

  public void setConnection(Connection con) {
        try {

            connection = con;
        } catch (Exception e) {
             System.out.println("OrganisationSubTypeModel setConnection() Error: " + e);
        }
    }
    public int getNoOfRows(String searchOrgType, String searchOrgSubType) {
        int noOfRows = 0;
        searchOrgType = krutiToUnicode.convert_to_unicode(searchOrgType);
        searchOrgSubType = krutiToUnicode.convert_to_unicode(searchOrgSubType);
        try {
            String query = "select count(*) FROM organisation_sub_type AS ost, organisation_type AS ot "
                    + "WHERE ost.organisation_type_id=ot.organisation_type_id "
                    + "AND if('" + searchOrgType + "' = '', ot.org_type_name like '%%', ot.org_type_name like  '" + searchOrgType + "') "
                    + "AND if('" + searchOrgSubType + "' = '', ost.organisation_sub_type_name like '%%', ost.organisation_sub_type_name like  '" + searchOrgSubType + "') ";
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));
        } catch (Exception e) {
            System.out.println("Error: OrganisationSubTypeModel " + e);
        }
        return noOfRows;
    }
     public List<OrganisationSubType> showAllData(String searchOrgType, String searchOrgSubType)
    {
          List<OrganisationSubType> list = new ArrayList<OrganisationSubType>();
          searchOrgType = krutiToUnicode.convert_to_unicode(searchOrgType);
        searchOrgSubType = krutiToUnicode.convert_to_unicode(searchOrgSubType);
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT ost.organisation_sub_type_id, ost.organisation_sub_type_name, ot.org_type_name "
                + "FROM organisation_sub_type AS ost, organisation_type AS ot "
                + "WHERE ost.organisation_type_id=ot.organisation_type_id "
                + "AND if('" + searchOrgType + "' = '', ot.org_type_name like '%%', ot.org_type_name like  '" + searchOrgType + "') "
                + "AND if('" + searchOrgSubType + "' = '', ost.organisation_sub_type_name like '%%', ost.organisation_sub_type_name like  '" + searchOrgSubType + "') "
                 + "ORDER BY org_type_name,organisation_sub_type_name";
                
            try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                OrganisationSubType organisationSubType = new OrganisationSubType();
                organisationSubType.setOrganisation_sub_type_id(rset.getInt("organisation_sub_type_id"));
                organisationSubType.setOrganisation_sub_type_name(rset.getString("organisation_sub_type_name"));
                organisationSubType.setOrg_type_name(rset.getString("org_type_name"));
                list.add(organisationSubType);
                System.out.println(rset.getString("organisation_sub_type_name")+"\n"+rset.getString("org_type_name"));
            }
        } catch (Exception e) {
            System.out.println("Error: OrganisationSubTypeModel-" + e);
        }
        return list;
     }
    public List<OrganisationSubType> showData(int lowerLimit, int noOfRowsToDisplay, String searchOrgType, String searchOrgSubType) {
        List<OrganisationSubType> list = new ArrayList<OrganisationSubType>();
        searchOrgType = krutiToUnicode.convert_to_unicode(searchOrgType);
        searchOrgSubType = krutiToUnicode.convert_to_unicode(searchOrgSubType);
        // Use DESC or ASC for descending or ascending order respectively of fetched data.
        String query = "SELECT ost.organisation_sub_type_id, ost.organisation_sub_type_name, ot.org_type_name "
                + "FROM organisation_sub_type AS ost, organisation_type AS ot "
                + "WHERE ost.organisation_type_id=ot.organisation_type_id "
                + "AND if('" + searchOrgType + "' = '', ot.org_type_name like '%%', ot.org_type_name like  '" + searchOrgType + "') "
                + "AND if('" + searchOrgSubType + "' = '', ost.organisation_sub_type_name like '%%', ost.organisation_sub_type_name like  '" + searchOrgSubType + "') "
                + "ORDER BY org_type_name,organisation_sub_type_name "
                + " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) {
                OrganisationSubType organisationSubType = new OrganisationSubType();
                organisationSubType.setOrganisation_sub_type_id(rset.getInt("organisation_sub_type_id"));
                organisationSubType.setOrganisation_sub_type_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("organisation_sub_type_name")));
                organisationSubType.setOrg_type_name(unicodeToKruti.Convert_to_Kritidev_010(rset.getString("org_type_name")));
                list.add(organisationSubType);
            }
        } catch (Exception e) {
            System.out.println("Error: OrganisationSubTypeModel-" + e);
        }
        return list;
    }

    public int insertRecord(OrganisationSubType organisationSubType) {
        String query = "INSERT INTO organisation_sub_type( organisation_sub_type_name, organisation_type_id )"
                + "VALUES(?, ?)";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(organisationSubType.getOrganisation_sub_type_name()));
            pstmt.setInt(2, organisationSubType.getOrganisation_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: OrganisationSubTypeModel- " + e);
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

    public int updateRecord(OrganisationSubType organisationSubType) {
        String query = "UPDATE organisation_sub_type SET organisation_sub_type_name=?, organisation_type_id=? WHERE organisation_sub_type_id=?";
        int rowsAffected = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, krutiToUnicode.convert_to_unicode(organisationSubType.getOrganisation_sub_type_name()));
            pstmt.setInt(2, organisationSubType.getOrganisation_type_id());
            pstmt.setInt(3, organisationSubType.getOrganisation_sub_type_id());
            rowsAffected = pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error:OrganisationSubTypeModel-" + e);
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

    public int deleteRecord(int organisation_sub_type_id) {
        String query = "DELETE FROM organisation_sub_type WHERE organisation_sub_type_id= " + organisation_sub_type_id;
        int rowsAffected = 0;
        try {
            rowsAffected = connection.prepareStatement(query).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error: OrganisationSubTypeModel-" + e);
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

    public int getOrganisationTypeID(String org_type_name) {
        org_type_name = krutiToUnicode.convert_to_unicode(org_type_name);
        String query = "SELECT organisation_type_id FROM organisation_type WHERE org_type_name = ?";
        int id = 0;
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, org_type_name);
            ResultSet rset = pstmt.executeQuery();
            rset.next();    // move cursor from BOR to valid record.
            id = rset.getInt("organisation_type_id");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return id;
    }

    public List<String> getOrgTypeNameList(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT org_type_name FROM organisation_type ORDER BY org_type_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_type_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("org_type_name"));
                if (org_type_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Org Type Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }

    public List<String> getOrgSubTypeNameList(String q) {
        List<String> list = new ArrayList<String>();
        String query = "SELECT organisation_sub_type_name FROM organisation_sub_type GROUP BY organisation_sub_type_name";
        try {
            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {    // move cursor from BOR to valid record.
                String org_sub_type_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("organisation_sub_type_name"));
                if (org_sub_type_name.toUpperCase().startsWith(q.toUpperCase())) {
                    list.add(org_sub_type_name);
                    count++;
                }
            }
            if (count == 0) {
                list.add("No such Sub Org Type Name exists.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        return list;
    }
     public byte[] generateOrgSubTypeReport(String jrxmlFilePath,List listAll) {
        byte[] reportInbytes = null;
        try {

            JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(listAll);
            JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
            reportInbytes = JasperRunManager.runReportToPdf(compiledReport, null , beanColDataSource );
        } catch (Exception e) {
            System.out.println("Error: in OrgSubType Model generateOrgSubTypeReport() JRException: " + e);
        }
        return reportInbytes;
    }
     public ByteArrayOutputStream generateOrgSubTypeXlsRecordList(String jrxmlFilePath,List list) {
                ByteArrayOutputStream bytArray = new ByteArrayOutputStream();
              //  HashMap mymap = new HashMap();
                try {
                    JRBeanCollectionDataSource jrBean=new JRBeanCollectionDataSource(list);
                    JasperReport compiledReport = JasperCompileManager.compileReport(jrxmlFilePath);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(compiledReport, null, jrBean);
                    JRXlsExporter exporter = new JRXlsExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, bytArray);
                    exporter.exportReport();
                } catch (Exception e) {
                    System.out.println("Error : OrgSubType Model generateOrgSubTypeXlsRecordList() JRException: " + e);
                }
                return bytArray;
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
            System.out.println("OrganisationSubTypeModel closeConnection() Error: " + e);
        }
    }
}
