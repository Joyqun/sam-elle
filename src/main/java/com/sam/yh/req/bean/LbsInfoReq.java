package com.sam.yh.req.bean;

import java.io.Serializable;
import java.util.Map;

public class LbsInfoReq implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String imei;
    private String mcc;
    private String mnc;
    private String lac;
    private String ci;


	

	public String getImei() {
		return imei;
	}




	public void setImei(String imei) {
		this.imei = imei;
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

    @Override
    public String toString() {
        return "LbsInfoReq [imei=" + imei + ", mcc=" + mcc + ", mnc=" + mnc + ", lac=" + lac + ", ci=" + ci + "]";
    }



}   
