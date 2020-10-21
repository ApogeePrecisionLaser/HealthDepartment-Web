package com.healthDepartment.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
//import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.ws.rs.core.Context;

public class CheckHighTemperature implements ServletContextListener {
	
	@Context
	static
	ServletContext serveletContext;
        
        static String lat1="";
        static String long1="";
        static int count=0;
        
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		// Your code here
		//System.out.println("HelloWorld Listener has been shutdown");

	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {

		// Your code here
		System.out.println("HelloWorld Listener initialized.");

		TimerTask vodTimer = new VodTimerTask();

		Timer timer = new Timer();
		timer.schedule(vodTimer, 1000, (5 * 1000));

	}
	
	class VodTimerTask extends TimerTask {

		@Override
		public void run() {
			String id="";
                    try {
                        id = checkLatLong();
                    } catch (SQLException ex) {
                        Logger.getLogger(CheckHighTemperature.class.getName()).log(Level.SEVERE, null, ex);
                    }
			System.out.println("get status --" + id);

			// for sms send to admin
			
			// end sms sent to admin

		}
	}

	/*public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new CheckHighTemperature(), 3000, 1*60*1000);
	}*/

	public static String checkLatLong() throws SQLException {
		String latitude="";
                String longitude="";
		String result="";
                
		Connection connection=null;
		try {
			Class.forName("com.mysql.jdbc.Driver");  
			connection=DriverManager.getConnection(  
			"jdbc:mysql://localhost:3306/health_department?useSSL=false","root","root");
			
			String query= " Select latitude,longitude from app_cordinates where status='Y' " +
                                      " order by app_cordinates_id desc limit 1 ";
			//System.out.println("query -"+query);
			PreparedStatement psmt = connection.prepareStatement(query);
			ResultSet rst = psmt.executeQuery();
			while (rst.next()) {
				latitude=rst.getString("latitude");
                                longitude = rst.getString("longitude");
                                
                               if(lat1.equals(latitude) && long1.equals(longitude))
                               {
                                   count++;
                                   
                                   if(count>=3)
                                   {
                                    System.out.println("Same LatLong for More Than 10 counts");
                                   result = "Same LatLong for More Than 10 counts";
                                   
                                   count=0;
                                   }
                                   else
                                   {
                                      result = "Different lat long";
                                   }
                               }
                                
                                
			}
			 lat1 = latitude;
                                long1 = longitude;
                                
			
			
		}catch(Exception e) {
			System.out.println("Exception in CheckHighTemperature service -"+e);
		}finally {
			connection.close();
		}
		/*if(temp !="") {
			return rfid;
		}else {
			return rfid;
		}*/
		return result;
	}

	public static String random(int size) {
		StringBuilder generatedToken = new StringBuilder();
		try {
			SecureRandom number = SecureRandom.getInstance("SHA1PRNG");
			// Generate 20 integers 0..20
			for (int i = 0; i < size; i++) {
				generatedToken.append(number.nextInt(9));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return generatedToken.toString();
	}

	public String sendSmsToAssignedFor(String numberStr1, String messageStr1) {
		String result = "";
		try {
			String host_url = "http://login.smsgatewayhub.com/api/mt/SendSMS?";
			messageStr1 = java.net.URLEncoder.encode(messageStr1, "UTF-8");
			String queryString = "APIKey=WIOg7OdIzkmYTrqTsw262w&senderid=JPSOFT&channel=2&DCS=8&flashsms=0&number="
					+ numberStr1 + "&text=" + messageStr1 + "&route=";
			String url = host_url + queryString;
			result = callURL(url);
			//System.out.println("SMS URL: " + url);
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

}
