
package com.healthDepartment.location.tableClasses;

public class CityBean {
    private int cityId;
    private String cityName,cityDescription;
    int pin_code,std_code;

    public int getPin_code() {
        return pin_code;
    }

    public void setPin_code(int pin_code) {
        this.pin_code = pin_code;
    }

    public int getStd_code() {
        return std_code;
    }

    public void setStd_code(int std_code) {
        this.std_code = std_code;
    }

 
    public void setCityId(int cityId)
    {
        this.cityId = cityId;
    }
     public void setCityName(String cityName)
    {
        this.cityName = cityName;
    }
      public void setCityDescription(String cityDescription)
    {
        this.cityDescription = cityDescription;
    }
    public int getCityId()
    {
        return cityId;
    }
     public String getCityName()
    {
        return cityName;
    }
      public String getCityDescription()
    {
        return cityDescription;
    }
}
