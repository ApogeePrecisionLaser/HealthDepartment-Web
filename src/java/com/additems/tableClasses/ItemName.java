/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.tableClasses;

/**
 *
 * @author Shobha
 */
public class ItemName {
private String item_name;
    private String item_code;
    private String remark;
    private int item_name_id;

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_name_id() {
        return item_name_id;
    }

    public void setItem_name_id(int item_name_id) {
        this.item_name_id = item_name_id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
