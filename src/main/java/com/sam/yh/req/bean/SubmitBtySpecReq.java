package com.sam.yh.req.bean;

public class SubmitBtySpecReq {

    private String userName;
    private String deviceImei;
//    private String userPhone;
//    private String btySN;
//    private String btySimNo;
//    private String resellerPhone;

    public String getUserName() {
        return userName;
    }
	public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}


}
