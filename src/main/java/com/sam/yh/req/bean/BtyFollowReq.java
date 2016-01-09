package com.sam.yh.req.bean;

public class BtyFollowReq {

    private String userPhone;
//    private String btyPubSn;
    private String btyOwnerPhone;
    private String deviceImei;

    public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}

	public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getBtyOwnerPhone() {
        return btyOwnerPhone;
    }

    public void setBtyOwnerPhone(String btyOwnerPhone) {
        this.btyOwnerPhone = btyOwnerPhone;
    }

}
