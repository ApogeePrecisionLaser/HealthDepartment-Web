/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.additems.tableClasses;

import java.util.ArrayList;

/**
 *
 * @author Shobha
 */
public class PurposeHeaderNode1 {
    private int node_id;
    private String node_name;
    private int generation_no;
    private String active;
    private int revision_no;
    private String remark;
    private String index_no; // field of tree table but added for showData

     public int getNode_id() {
        return node_id;
    }

    public void setNode_id(int node_id) {
        this.node_id = node_id;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public int getGeneration_no() {
        return generation_no;
    }

    public void setGeneration_no(int generation_no) {
        this.generation_no = generation_no;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

     public int getRevision_no() {
        return revision_no;
    }

    public void setRevision_no(int revision_no) {
        this.revision_no = revision_no;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIndex_no() {
        return index_no;
    }

    public void setIndex_no(String index_no) {
        this.index_no = index_no;
    }



    private int tree_id;
    private int tree_node_id;
    private int node_parent_id;
    private int node_parent_rev_no;
    private int tree_parent_id;
    private int revision;
    private String tree_active;
   // private String index_no;
    private String isSuperChild;
    private String tree_remark;
    private String tree_node_name;
    private String node_parent_name;

     public String getNode_parent_name() {
        return node_parent_name;
    }

    public void setNode_parent_name(String node_parent_name) {
        this.node_parent_name = node_parent_name;
    }

    public String getTree_node_name() {
        return tree_node_name;
    }

    public void setTree_node_name(String tree_node_name) {
        this.tree_node_name = tree_node_name;
    }

    public int getTree_id() {
        return tree_id;
    }

    public void setTree_id(int tree_id) {
        this.tree_id = tree_id;
    }

     public int getTree_node_id() {
        return tree_node_id;
    }

    public void setTree_node_id(int tree_node_id) {
        this.tree_node_id = tree_node_id;
    }

    public int getNode_parent_id() {
        return node_parent_id;
    }

    public void setNode_parent_id(int node_parent_id) {
        this.node_parent_id = node_parent_id;
    }

    public int getNode_parent_rev_no() {
        return node_parent_rev_no;
    }

    public void setNode_parent_rev_no(int node_parent_rev_no) {
        this.node_parent_rev_no = node_parent_rev_no;
    }

    public int getTree_parent_id() {
        return tree_parent_id;
    }

    public void setTree_parent_id(int tree_parent_id) {
        this.tree_parent_id = tree_parent_id;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getTree_active() {
        return tree_active;
    }

    public void setTree_active(String tree_active) {
        this.tree_active = tree_active;
    }

    public String getIsSuperChild() {
        return isSuperChild;
    }

    public void setIsSuperChild(String isSuperChild) {
        this.isSuperChild = isSuperChild;
    }

    public String getTree_remark() {
        return tree_remark;
    }

    public void setTree_remark(String tree_remark) {
        this.tree_remark = tree_remark;
    }

    //for pdf report
    private ArrayList<String> name;

     public ArrayList<String> getName() {
        return name;
    }

    public void setName(ArrayList<String> name) {
        this.name = name;
    }

    private ArrayList<PurposeHeaderNode1> itemHeader;

     public ArrayList<PurposeHeaderNode1> getItemHeader() {
        return itemHeader;
    }

    public void setItemHeader(ArrayList<PurposeHeaderNode1> itemHeader) {
        this.itemHeader = itemHeader;
    }

    private ArrayList<PurposeHeaderNode1> itemLeaf;

    public ArrayList<PurposeHeaderNode1> getItemLeaf() {
        return itemLeaf;
    }

    public void setItemLeaf(ArrayList<PurposeHeaderNode1> itemLeaf) {
        this.itemLeaf = itemLeaf;
    }


}
