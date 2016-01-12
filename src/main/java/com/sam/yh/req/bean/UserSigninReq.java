package com.sam.yh.req.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserSigninReq implements Serializable {

    private static final long serialVersionUID = 1L;

//    private String userAccount;
    private String userSigninName;
    private String accountType;
    private String password;
    private String deviceInfo;
    

//    public String getUserAccount() {
//        return userAccount;
//    }
//
//    public void setUserAccount(String userAccount) {
//        this.userAccount = userAccount;
//    }
    


	public String getUserSigninName() {
		return userSigninName;
	}

	public void setUserSigninName(String userSigninName) {
		this.userSigninName = userSigninName;
	}

    public String getAccountType() {
        return accountType;
    }

	public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
