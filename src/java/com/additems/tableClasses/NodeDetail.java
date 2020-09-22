/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.tableClasses;

/**
 *
 * @author Shobha
 */
public class NodeDetail {
private int pipe_detail_id;
    private int node_id;
    private int size=0;
    private double head_latitude;
    private double tail_latitude;
    private double head_longitude;
    private double tail_longitude;
    private double diameter;
    private double length;
    private String diameter_unit;
    private String length_unit;
    private String remark;
    private String node_name;
    private String pipe_type,pipe_name;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }



    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public String getDiameter_unit() {
        return diameter_unit;
    }

    public void setDiameter_unit(String diameter_unit) {
        this.diameter_unit = diameter_unit;
    }

    public double getHead_latitude() {
        return head_latitude;
    }

    public void setHead_latitude(double head_latitude) {
        this.head_latitude = head_latitude;
    }

    public double getHead_longitude() {
        return head_longitude;
    }

    public void setHead_longitude(double head_longitude) {
        this.head_longitude = head_longitude;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getLength_unit() {
        return length_unit;
    }

    public void setLength_unit(String length_unit) {
        this.length_unit = length_unit;
    }

    public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public int getPipe_detail_id() {
        return pipe_detail_id;
    }

    public void setPipe_detail_id(int pipe_detail_id) {
        this.pipe_detail_id = pipe_detail_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public double getTail_latitude() {
        return tail_latitude;
    }

    public void setTail_latitude(double tail_latitude) {
        this.tail_latitude = tail_latitude;
    }

    public double getTail_longitude() {
        return tail_longitude;
    }

    public void setTail_longitude(double tail_longitude) {
        this.tail_longitude = tail_longitude;
    }

    public String getPipe_type() {
        return pipe_type;
    }

    public void setPipe_type(String pipe_type) {
        this.pipe_type = pipe_type;
    }

    public String getPipe_name() {
        return pipe_name;
    }

    public void setPipe_name(String pipe_name) {
        this.pipe_name = pipe_name;
    }
    
}
