package com.sam.yh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.enums.UserAccountType;
import com.sam.yh.model.PubBatteryInfo;
import com.sam.yh.model.User;
import com.sam.yh.req.bean.AddDeviceReq;
import com.sam.yh.req.bean.BindMobilePhoneReq;

public interface UserService {

    @Transactional
    public User signup(String userName, String userAccount, UserAccountType accountType, String authCode, String hassPwd, String deviceInfo) throws CrudException;

    @Transactional
    public User signin(String getUserSigninName, String hassPwd, String deviceInfo) throws CrudException;
  
    @Transactional
    public User resetPwd(String userAccount, String authCode, String hassPwd) throws CrudException;
//  public User resetPwd(String mobilePhone, String authCode, String hassPwd, String deviceInfo) throws CrudException;
    
    @Transactional
    public void addDevice(AddDeviceReq addDeviceReq) throws CrudException;
    
    @Transactional
    public void bindMobilePhone(BindMobilePhoneReq bindMobilePhoneReq) throws CrudException;
    
    @Transactional
    public List<PubBatteryInfo> fetchSelfBtyInfo(String userAccount);

    @Transactional
    public List<PubBatteryInfo> fetchFriendsBtyInfo(String userAccount);
    
    @Transactional
    public User fetchUserByUserName(String userAccount);

    @Transactional
    public User fetchUserByUserAccount(String userAccount);

    @Transactional
    public void followBty(String userAccount, String deviceImei, String btyOwnerAccount) throws CrudException;

    @Transactional
    public void shareBty(String mobilePhone, String deviceImei, String friendPhone) throws CrudException;

    @Transactional
    public void unshareBty(String mobilePhone, String deviceImei, String friendPhone) throws CrudException;

    @Transactional
    public void unfollowBty(String mobilePhone, String btyPubSn) throws CrudException;

    @Transactional
    public void lockBty(String mobilePhone, String btyImei) throws CrudException;

    @Transactional
    public void unlockBty(String mobilePhone, String btyImei) throws CrudException;

    public String getUserType(String mobilePhone) throws CrudException;
    
    public User selectByUserName(String userName) throws CrudException;


}
