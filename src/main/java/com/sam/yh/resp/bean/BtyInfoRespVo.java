package com.sam.yh.resp.bean;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class BtyInfoRespVo implements Serializable {

    private static final long serialVersionUID = -1243313319749017390L;
    private String longitude;
    private String latitude;
    private String temperature;
    private String voltage;
    private String lockStatus;
    private Map<String, String> extension;
    private String lastestDate;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }

    public String getLastestDate() {
        return lastestDate;
    }

    public void setLastestDate(String lastestDate) {
        this.lastestDate = lastestDate;
    }
    
    public String getLockStatus() {
		return lockStatus;
	}

	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}

	public Map<String, String> getExtension() {
		return extension;
	}

	public void setExtension(Map<String, String> extension) {
		this.extension = extension;
	}

	@Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
