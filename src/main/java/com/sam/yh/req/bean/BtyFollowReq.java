package com.sam.yh.req.bean;

public class BtyFollowReq {

    private String userAccount;
//    private String btyPubSn;
    private String btyOwnerAccount;
    private String deviceImei;

    public String getDeviceImei() {
		return deviceImei;
	}

	public void setDeviceImei(String deviceImei) {
		this.deviceImei = deviceImei;
	}

	public String getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	public String getBtyOwnerAccount() {
		return btyOwnerAccount;
	}

	public void setBtyOwnerAccount(String btyOwnerAccount) {
		this.btyOwnerAccount = btyOwnerAccount;
	}


}
