package com.sam.yh.req.bean;

public class BtyShareReq {

    private String userPhone;
//    private String btyPubSn;
    private String friendPhone;
    private String deviceImei;
 
    
	public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    
    public String getFriendPhone() {
		return friendPhone;
	}

	public void setFriendPhone(String friendPhone) {
		this.friendPhone = friendPhone;
	}

	public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}



}
