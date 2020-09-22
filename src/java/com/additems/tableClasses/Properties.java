/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.tableClasses;

/**
 *
 * @author Shobha
 */
public class Properties {
 private String properties_name;
    private int properties_id;
    private String code;
    private String remark;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getProperties_id()
    {
        return properties_id;
    }

    public void setProperties_id(int properties_id)
    {
        this.properties_id = properties_id;
    }

    public String getProperties_name()
    {
        return properties_name;
    }

    public void setProperties_name(String properties_name)
    {
        this.properties_name = properties_name;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

}
