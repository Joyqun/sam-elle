package com.sam.yh.req.bean;

import java.io.Serializable;
import java.util.Map;

public class BatteryInfoReq implements Serializable {

    private static final long serialVersionUID = 1L;
    private String imei;
    private String imsi;
    private String phonenumber;
    private String longitude;
    private String latitude;
    private String temperature;
    private String voltage;
    private String lockstatus;
    private String extension;
    private String mcc;
    private String mnc;
    private String lac;
    private String ci;
    private String isGps;
 
//    private Map<String, String> extension;


    
	public String getLockstatus() {
		return lockstatus;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public String getLac() {
		return lac;
	}

	public void setLac(String lac) {
		this.lac = lac;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getIsGps() {
		return isGps;
	}

	public void setIsGps(String isGps) {
		this.isGps = isGps;
	}

	public void setLockstatus(String lockstatus) {
		this.lockstatus = lockstatus;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	

//    public Map<String, String> getExtension() {
//		return extension;
//	}
//
//	public void setExtension(Map<String, String> extension) {
//		this.extension = extension;
//	}
	
	public String getImei() {
        return imei;
    }

	public void setImei(String imei) {
        this.imei = imei;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

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

    @Override
    public String toString() {
        return "BatteryInfoReq [imei=" + imei + ", imsi=" + imsi + ", phonenumber=" + phonenumber + ", longitude=" + longitude + ", latitude=" + latitude
                + ", temperature=" + temperature + ", voltage=" + voltage + "]";
    }

}
