package com.sam.yh.req.bean;

public class ListFollowersReq {

    private String userPhone;
//    private String btyPubSn;
    private String deviceImei;
    
	public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}




}
