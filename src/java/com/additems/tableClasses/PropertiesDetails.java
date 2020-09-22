/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.tableClasses;

/**
 *
 * @author Shobha
 */
public class PropertiesDetails {
private String properties_name;
    private int property_details_id;
    private String value;
    private String remark;

    public String getProperties_name()
    {
        return properties_name;
    }

    public void setProperties_name(String properties_name)
    {
        this.properties_name = properties_name;
    }

    public int getProperty_details_id()
    {
        return property_details_id;
    }

    public void setProperty_details_id(int property_details_id)
    {
        this.property_details_id = property_details_id;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

}
