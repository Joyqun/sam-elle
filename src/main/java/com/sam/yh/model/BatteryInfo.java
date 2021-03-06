package com.sam.yh.model;

import java.util.Date;
import java.util.Map;

public class BatteryInfo {
    private Long id;

    private Integer batteryId;

    private String longitude;

    private String latitude;

    private String temperature;

    private String voltage;
    
    private String lockStatus;
    
    private String extension;

    private String status;

    private Date receiveDate;

    private Date sampleDate;
    
//    private Map<String, String> extension;

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBatteryId() {
        return batteryId;
    }

    public void setBatteryId(Integer batteryId) {
        this.batteryId = batteryId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage == null ? null : voltage.trim();
    }
    
    public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus == null ? lockStatus : lockStatus.trim();
	}
	
	public String getStatus() {
        return status;
    }

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension == null ? null : extension.trim();
	}


//    public Map<String, String> getExtension() {
//		return extension;
//	}
//
//	public void setExtension(Map<String, String> extension) {
//		this.extension = extension;
//	}

	public void setStatus(String status) {
        this.status = status;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public Date getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(Date sampleDate) {
        this.sampleDate = sampleDate;
    }
}