/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.general.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author jpss
 */
public class MapDetailClass {
    public static int getDistance(String source, String destination){
        int distance = -1;
        try {                
                URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins="+source+"&destinations="+destination);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestMethod("GET");
                
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));               
                String mapData = "";
                String tempData = "";
                tempData = in.readLine();
                while (tempData != null) {
                    mapData = mapData + tempData;
                    tempData = in.readLine();
                }
                
                JSONTokener tokener = new JSONTokener(mapData);
                JSONObject top = new JSONObject(tokener);
                JSONArray jArray = top.getJSONArray("rows");
                top = jArray.getJSONObject(0);
                jArray = top.getJSONArray("elements");
                top = jArray.getJSONObject(0);
                JSONObject distanceObject = top.getJSONObject("distance");
                distance = distanceObject.getInt("value");
                JSONObject durationObject = top.getJSONObject("duration");
                int duration = durationObject.getInt("value");

                System.out.println("\nCrunchify REST Service Invoked Successfully.. : " + mapData);
                in.close();
            } catch (Exception e) {
                System.out.println("\nError while calling Crunchify REST Service");
                System.out.println(e);
            }
        return distance;
    }

}
