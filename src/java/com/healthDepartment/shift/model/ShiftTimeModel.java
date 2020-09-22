/*
 ShiftWorkBench-2
 */

package com.healthDepartment.shift.model;

import java.sql.PreparedStatement;
import java.sql.Connection;
import com.healthDepartment.shift.tableClasses.ShiftTimeBean;
import com.healthDepartment.util.KrutiDevToUnicodeConverter;
import com.healthDepartment.util.UnicodeToKrutiDevConverter;
import com.sun.jersey.json.impl.writer.A2EXmlStreamWriterProxy;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Tushar Singh
 */
public class ShiftTimeModel {
    private static Connection connection;
    private String message;
    private String msgBgColor;
    private final String COLOR_OK = "yellow";
    private final String COLOR_ERROR = "red";
    public static KrutiDevToUnicodeConverter krutiToUnicode = new KrutiDevToUnicodeConverter();
    public static UnicodeToKrutiDevConverter unicodeToKruti = new UnicodeToKrutiDevConverter();



     public static int  getNoOfRows(String zone,String ward,String area)
 {
             zone = krutiToUnicode.convert_to_unicode(zone);
          ward = krutiToUnicode.convert_to_unicode(ward);
          area = krutiToUnicode.convert_to_unicode(area);
         int noOfRows = 0;
        try {
            String query ="select count(beneficiary_id) from  beneficiary as b,city_location as cl,area as a,ward as w,zone as z "
                  + " where b.city_location_id=cl.city_location_id and cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id "
                  + "  and zone_name='"+zone+"' and ward_name='"+ward+"' and area_name='"+area+"' ";

            PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            rset.next();
            noOfRows = Integer.parseInt(rset.getString(1));

        } catch (Exception e) {
            System.out.println( e);
        }
         System.out.println("No of Rows in Table for search is****....." + noOfRows);
        return noOfRows;
    }

     public static List<ShiftTimeBean> showData(int lowerLimit,int noOfRowsToDisplay,String zone,String ward,String area)
    {
          zone = krutiToUnicode.convert_to_unicode(zone);
          ward = krutiToUnicode.convert_to_unicode(ward);
          area = krutiToUnicode.convert_to_unicode(area);
       List list = new ArrayList();
         String addQuery = " LIMIT " + lowerLimit + ", " + noOfRowsToDisplay;
          if(lowerLimit == -1)
            addQuery = "";
   String query ="select beneficiary_id,kp.key_person_name,kp.father_name,kp.mobile_no1,occupation_name,cl.location,is_residencial from  beneficiary as b,city_location as cl,area as a,ward as w,zone as z,key_person as kp "
                  + " where b.city_location_id=cl.city_location_id and cl.area_id=a.area_id and a.ward_id=w.ward_id and w.zone_id=z.zone_id and b.key_person_id=kp.key_person_id "
                  + "  and zone_name='"+zone+"' and ward_name='"+ward+"' and area_name='"+area+"' " 
                  + addQuery;
      try {

            ResultSet rs = connection.prepareStatement(query).executeQuery();
             while(rs.next()){
                  ShiftTimeBean sb=new ShiftTimeBean();
                  sb.setBeneficiary_id(rs.getInt("beneficiary_id"));
                  sb.setName(rs.getString("key_person_name"));
                   sb.setFather_name(rs.getString("father_name"));
                   sb.setMobile_no(rs.getString("mobile_no1"));
                  sb.setOccupation_name(rs.getString("occupation_name"));
                  sb.setLocation(rs.getString("location"));
                  sb.setIs_residencial(rs.getString("is_residencial"));
                  sb.setStatus("Yes");
                  list.add(sb);
             }
           
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;

    }
  public  boolean insertRecord(ShiftTimeBean bean)
   {
        boolean status=false;
        int rowsAffected=0;
        String reason="";
        String query="insert into shift_key_person_detail (shift_key_person_map_id,beneficiary_id,status,date,reason_id) values(?,?,?,?,?)";
        try{
         String[] b_beneficiary_id=bean.getB_beneficiary_id();
         int shift_key_person_map_id=getShiftKeyPersonMapId(bean.getPerson_name());
         for(int i=0;i<b_beneficiary_id.length;i++)
          {
            PreparedStatement ps=(PreparedStatement) connection.prepareStatement(query);

            ps.setInt(1,shift_key_person_map_id);
            ps.setInt(2,Integer.parseInt(bean.getB_beneficiary_id()[i]));
            if(bean.getB_status()[i].equals("Yes"))
            ps.setString(3,"Y");
            else
            ps.setString(3,"N");
            String date = bean.getDate();
            if(date != null && !date.isEmpty()){
            String[] date_array  = date.split("-");
            date = date_array[2] + "-" + date_array[1] + "-" + date_array[0];
            }
            String time=bean.getTime_hour()[i]+":"+ bean.getTime_min()[i];
            String datetime=date +" "+time;
            ps.setString(4,datetime );
            int reason_id= Integer.parseInt(bean.getReason_id()[i]);
            if(reason_id==0 || bean.getB_status()[i].equals("Yes"))
            ps.setNull(5, java.sql.Types.NULL);
            else
            ps.setInt(5,reason_id);
            rowsAffected = ps.executeUpdate();
             if(bean.getB_status()[i].equals("Yes"))
             sendSmsToAssignedFor(bean.getB_mobile_no()[i]," जबलपुर नगर निगम की सफाई में सहायता करने के लिए आपका धन्यवाद ");
             else{
                if(reason_id>0)
            reason =  getReason(bean.getReason_id()[i]);
             sendSmsToAssignedFor(bean.getB_mobile_no()[i], reason + " के कारण जबलपुर नगर निगम की सफाई में आपकी सहायता नही मिली ");
              }
          }
        if(rowsAffected > 0)

        status=true;
        }
        catch(Exception e){
    System.out.println("ERROR: " + e);
        }
       if (rowsAffected > 0) {
             message = "Record Inserted successfully......";
            msgBgColor = COLOR_OK;
            System.out.println("Inserted");
        } else {
             message = "Record Not Inserted Some Error!";
            msgBgColor = COLOR_ERROR;
            System.out.println("not Inserted");
        }
return status;
}

     public static String getReason(String id){
        String reason_name="";
   String query = "select reason_name from reason where reason_id= "+id;
      try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            while (rset.next()) { 
                 reason_name = rset.getString("reason_name");
                 System.out.print(reason_name);
          }
        } catch (Exception e) {
            System.out.println( e);
        }
        return reason_name;
    }

 public static int getShiftKeyPersonMapId(String person_name)
{
     person_name=krutiToUnicode.convert_to_unicode(person_name);
        int shift_key_person_map_id = 0;
        try {
            String  query= " select shift_key_person_map_id from shift_key_person_map as skpm,key_person as kp "
                                 + "  where skpm.key_person_id=kp.key_person_id and key_person_name='"+person_name+"' " ;
           PreparedStatement pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                shift_key_person_map_id = rset.getInt("shift_key_person_map_id");
            }
        }   catch (Exception e) {
            System.out.print(e);}
            return shift_key_person_map_id;
    }

 public static List<String> getSearchPerson(String q,String emp_code)
    {
     List<String> list=new ArrayList<String>();
   String query = " select key_person_name from key_person as kp,shift_key_person_map as skpm "
                      + " where skpm.key_person_id=kp.key_person_id and skpm.active='y'  "
                      +  " and  IF('" + emp_code + "'='', emp_code like '%%', emp_code ='" + emp_code + "') group by key_person_name " ;
      try {

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                   String key_person_name = unicodeToKruti.Convert_to_Kritidev_010(rset.getString("key_person_name"));
                    list.add(key_person_name);
                    count++;
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }

   public static List<String> getEmpCode(String q,String person_name){
     List<String> list=new ArrayList<String>();
      person_name=krutiToUnicode.convert_to_unicode(person_name);
   String query = " SELECT emp_code FROM  key_person "
               +  "WHERE  IF('" + person_name + "'='', key_person_name like '%%', key_person_name ='" + person_name + "') "
                 + "Group by emp_code ";
      try {
           ResultSet rset = connection.prepareStatement(query).executeQuery();
            int count = 0;
            q = q.trim();
            while (rset.next()) {
                String emp_code = rset.getString("emp_code");
                    list.add(emp_code);
                    count++;
            }
            if (count == 0) {
                list.add("No such Status exists.......");
            }
        } catch (Exception e) {
            System.out.println( e);
        }
        return list;
    }

    public Map<Integer, String> getReason_type() {
        Map<Integer, String> map = new LinkedHashMap<Integer, String>();
        String query = "SELECT reason_id, reason_name FROM reason ";
        PreparedStatement pstmt;
        try {
            pstmt = (PreparedStatement) connection.prepareStatement(query);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                map.put(rset.getInt("reason_id"), rset.getString("reason_name"));
            }
        } catch (Exception e) {
            System.out.println("Error in vehicle_type" + e);
        }
        return map;
    }
 public static List<String> getName(String q,String person_name)
    {
          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String current_date = dateFormat.format(date);
     person_name=krutiToUnicode.convert_to_unicode(person_name);
     List<String> list=new ArrayList<String>();
   String query = "select z.zone_name,z.zone_no,w.ward_name,a.area_name,lt.location_type_name "
                    + " from key_person as kp "
                    + " left join  shift_key_person_map as skpm on skpm.key_person_id=kp.key_person_id "
                    + " left join shift_designation_location_map as sdlm on sdlm.map_id1=skpm.map_id1 "
                    + "  and sdlm.map_id2=skpm.map_id2 "
                    + " left join designation_location_type as dlt on sdlm.designation_location_type_id=dlt.designation_location_type_id "
                    + " left join zone as z on dlt.zone_id=z.zone_id "
                    + " left join ward as w on dlt.ward_id=w.ward_id "
                    +  "  left join area as a on dlt.area_id=a.area_id "
                    + " left join city_location as cl on dlt.city_location_id=cl.city_location_id "
                    +  "  left join location_type as lt on dlt.location_type_id=lt.location_type_id "
                    + "  where  key_person_name='"+person_name+"' and skpm.date='"+current_date+"' group by location_type_name " ;
      try {

            ResultSet rset = connection.prepareStatement(query).executeQuery();
            q = q.trim();
            while (rset.next()) {
                    String zone_name = rset.getString("zone_name");
                    String ward_name = rset.getString("ward_name");
                    String area_name = rset.getString("area_name");
                    String location_type_name = rset.getString("location_type_name");
                    if(location_type_name.equals("zone") || location_type_name.equals("area") || location_type_name.equals("ward") )
                    list=getNameList(location_type_name,zone_name,ward_name,area_name);
                  
            }
        } catch (Exception e) {
            System.out.println( e);
        }
    return list;
    }

 public static List<String> getNameList(String location_type,String zone,String ward,String area){
      List<String> list=new ArrayList<String>();
      String query = "";
       if(location_type.equals("area"))
           query="select z.zone_name,z.zone_no,w.ward_name,w.ward_no,a.area_no from zone as z,ward as w,area as a"
                    +" where a.ward_id=w.ward_id and w.zone_id=z.zone_id and area_name='"+area+"' ";

      if(location_type.equals("ward"))
          query="select z.zone_name,z.zone_no,w.ward_no from zone as z,ward as w "
                    +" where w.zone_id=z.zone_id and ward_name='"+ward+"' ";

       if(location_type.equals("zone"))
        query="Select zone_no from zone "
              + " where zone_name='"+zone+"' ";
      try {
           ResultSet rs = connection.prepareStatement(query).executeQuery();
             while(rs.next()){

                     if(location_type.equals("zone"))
                         {
                          String zone_no = rs.getString("zone_no");
                          String val=zone+"_"+zone_no;
                            list.add(val);
                         }
                      if( location_type.equals("ward"))
                        {
                          String zone_name = rs.getString("zone_name");
                          String zone_no = rs.getString("zone_no");
                          String ward_no = rs.getString("ward_no");
                           String val=zone_name+"_"+zone_no+"&#"+ward+"_"+ward_no;
                            list.add(val);
                        }
                     if(location_type.equals("area"))
                         {
                         String ward_name = rs.getString("ward_name");
                         String ward_no = rs.getString("ward_no");
                         String zone_name = rs.getString("zone_name");
                         String zone_no = rs.getString("zone_no");
                         String area_no = rs.getString("area_no");
                         String val=zone_name+"_"+zone_no+"&#"+ward_name+"_"+ward_no+"&#"+area+"_"+area_no;
                         list.add(val);
                         }
          }
        } catch (Exception e) {
            System.out.println( e);
        }
      return list;
    }

  
// public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
//       String result = "";
//       try {
//           String host_url = "http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
//           String tempMessage = messageStr1;
//           messageStr1 = calstr(messageStr1);
//           String sender_id = java.net.URLEncoder.encode("TEST SMS", "UTF-8");         // e.g. "TEST+SMS"
//           System.out.println("messageStr1 is = " + messageStr1);
//           messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
//           String queryString = "user=" + "jpss1277@gmail.com" + ":" + "8826887606" + "&senderID=" + sender_id + "&receipientno=" + numberStr1 + "&msgtype=4&dcs=8&ishex=1&msgtxt=" + messageStr1;
//           String url = host_url + queryString;
//           result = callURL(url);
//           System.out.println("SMS URL: " + url);
//       } catch (Exception e) {
//           result = e.toString();
//           System.out.println("SMSModel sendSMS() Error: " + e);
//       }
//       return result;
//   }

 public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
       String result = "";
       try {
           String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";//"http://api.mVaayoo.com/mvaayooapi/MessageCompose?";
           String tempMessage = messageStr1;
           String sender_id = java.net.URLEncoder.encode("TEST SMS", "UTF-8");         // e.g. "TEST+SMS"
           System.out.println("messageStr1 is = " + messageStr1);
           messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
           String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number=" + numberStr1 + "&text=" + messageStr1 + "&route=";
           String url = host_url + queryString;
           result = callURL(url);
           System.out.println("SMS URL: " + url);
       } catch (Exception e) {
           result = e.toString();
           System.out.println("SMSModel sendSMS() Error: " + e);
       }
       return result;
   }
   private String callURL(String strURL) {
       String status = "";
       try {
           java.net.URL obj = new java.net.URL(strURL);
           HttpURLConnection httpReq = (HttpURLConnection) obj.openConnection();
           httpReq.setDoOutput(true);
           httpReq.setInstanceFollowRedirects(true);
           httpReq.setRequestMethod("GET");
           status = httpReq.getResponseMessage();
       } catch (MalformedURLException me) {
           status = me.toString();
       } catch (IOException ioe) {
           status = ioe.toString();
       } catch (Exception e) {
           status = e.toString();
       }
       return status;
   }
public String dhex(int str) {
       return Integer.toString(str, 16).toUpperCase();
   }

  public String calstr (String str){// convert unicode to hexadecimal
int haut = 0;
String CPstring = "";
for (int i = 0; i < str.length(); i++)
{
int b = str.charAt(i);
if (b < 0 || b > 0xFFFF) {
CPstring += "Error " + dhex(b) + "!";
}
if (haut != 0) {
if (0xDC00 <= b && b <= 0xDFFF) {
CPstring += dhex(0x10000 + ((haut - 0xD800) << 10) + (b - 0xDC00)) + ' ';
haut = 0;
continue;
}
else {
CPstring += "!erreur " + dhex(haut) + "!";
haut = 0;
}
}
if (0xD800 <= b && b <= 0xDBFF) {
haut = b;
}
else {
CPstring += dhex(b) + ' ';
}
}
CPstring = CPstring.substring(0, CPstring.length()-1);

String hex = convertCP2HexNCR(CPstring);
       return hex;
}

public String convertCP2HexNCR (String argstr)
{
 String outputString = "";
 argstr = argstr.replace("\\s+", "");
 if (argstr.length() == 0) { return ""; }
 argstr = argstr.replace("\\s+/g", " ");
 String[] listArray = argstr.split(" ");
 for ( int i = 0; i < listArray.length; i++ )
 {
   int n = Integer.parseInt(listArray[i], 16);
   if((n == 32 || n >= 65 && n <= 90) || (n >= 97 && n <= 122) || (n >= 48 && n <= 57) || n == 45 || n == 46 || n == 58)
       outputString += "00" + dhex(n);// + ';';
   else
       outputString += "0" + dhex(n);// + ';';
 }
 return( outputString );
}

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

        public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMsgBgColor() {
        return msgBgColor;
    }

    public void setMsgBgColor(String msgBgColor) {
        this.msgBgColor = msgBgColor;
    }


}
