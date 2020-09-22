
package com.healthDepartment.location.tableClasses;

public class DivisionBean {
    private int divisionId;
    private String divisionName,divisionDescription,stateName,utName,zoneName;

  
    public int getDivisionId() {
        return divisionId;
    }

   
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

   public String getDivisionName() {
        return divisionName;
    }

    
    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    
    public String getDivisionDescription() {
        return divisionDescription;
    }

   
    public void setDivisionDescription(String divisionDescription) {
        this.divisionDescription = divisionDescription;
    }

   
    public String getStateName() {
        return stateName;
    }

   
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

   
    public String getZoneName() {
        return zoneName;
    }

   
    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    /**
     * @return the utName
     */
    public String getUtName() {
        return utName;
    }

    /**
     * @param utName the utName to set
     */
    public void setUtName(String utName) {
        this.utName = utName;
    }

    

}
