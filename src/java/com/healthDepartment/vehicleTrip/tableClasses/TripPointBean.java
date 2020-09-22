/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.healthDepartment.vehicleTrip.tableClasses;

/**
 *
 * @author Shobha
 */
public class TripPointBean {
 private int trip_id;
    private int stopage_id;
    private int trip_stopage_map_id;
     private String trip_name;
    private String location;
    private String order_no;
    private String arrival_time;
    private String departure_time;
    private String start_time;
    private String route_name;
    private String location_code;

    public String getLocation_code() {
        return location_code;
    }

    public void setLocation_code(String location_code) {
        this.location_code = location_code;
    }

    public int getTrip_stopage_map_id() {
        return trip_stopage_map_id;
    }

    public void setTrip_stopage_map_id(int trip_stopage_map_id) {
        this.trip_stopage_map_id = trip_stopage_map_id;
    }


    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getStopage_name() {
        return stopage_name;
    }

    public void setStopage_name(String stopage_name) {
        this.stopage_name = stopage_name;
    }

    public String getWeek_days() {
        return week_days;
    }

    public void setWeek_days(String week_days) {
        this.week_days = week_days;
    }
    private String week_days;
    private String stopage_name;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }
    private double latitude;
    private double longitude;

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getDeparture_time() {
        return departure_time;
    }

    public void setDeparture_time(String departure_time) {
        this.departure_time = departure_time;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public int getStopage_id() {
        return stopage_id;
    }

    public void setStopage_id(int stopage_id) {
        this.stopage_id = stopage_id;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(int trip_id) {
        this.trip_id = trip_id;
    }

    public String getTrip_name() {
        return trip_name;
    }

    public void setTrip_name(String trip_name) {
        this.trip_name = trip_name;
    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
