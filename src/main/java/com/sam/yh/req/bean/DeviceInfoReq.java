package com.sam.yh.req.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class DeviceInfoReq implements Serializable {

	private static final long serialVersionUID = 5375037727343932182L;


//	private String deviceSimNo;

	private String chatType;
	
	private String deviceImei;
	

//	public String getDeviceSimNo() {
//		return deviceSimNo;
//	}
//
//	public void setDeviceSimNo(String deviceSimNo) {
//		this.deviceSimNo = deviceSimNo;
//	}

	public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}
	
	public String getChatType() {
		return chatType;
	}

	public void setChatType(String chatType) {
		this.chatType = chatType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
