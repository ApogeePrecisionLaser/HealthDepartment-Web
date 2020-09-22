/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.tableClasses;

/**
 *
 * @author Shobha
 */
public class ItemNamePropertiesMap {
 private String properties_name;
    private String item_name;
    private int item_name_properties_map_id;
    private int order_no;

    public int getOrder_no() {
        return order_no;
    }

    public void setOrder_no(int order_no) {
        this.order_no = order_no;
    }

    public String getItem_name()
    {
        return item_name;
    }

    public void setItem_name(String item_name)
    {
        this.item_name = item_name;
    }

    public String getProperties_name()
    {
        return properties_name;
    }

    public void setProperties_name(String properties_name)
    {
        this.properties_name = properties_name;
    }

    public int getItem_name_properties_map_id() {
        return item_name_properties_map_id;
    }

    public void setItem_name_properties_map_id(int item_name_properties_map_id) {
        this.item_name_properties_map_id = item_name_properties_map_id;
    }
   
}
