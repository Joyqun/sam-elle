package com.sam.yh.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.enums.UserAccountType;
import com.sam.yh.model.PubBatteryInfo;
import com.sam.yh.model.User;

public interface UserService {

    @Transactional
    public User signup(String userAccount, UserAccountType accountType, String authCode, String hassPwd, String deviceInfo) throws CrudException;

    @Transactional
    public User signin(String userAccount, String hassPwd, String deviceInfo) throws CrudException;

    @Transactional
    public User resetPwd(String mobilePhone, String authCode, String hassPwd, String deviceInfo) throws CrudException;

    @Transactional
    public List<PubBatteryInfo> fetchSelfBtyInfo(String mobilePhone);

    @Transactional
    public List<PubBatteryInfo> fetchFriendsBtyInfo(String mobilePhone);

    @Transactional
    public User fetchUserByUserAccount(String mobilePhone);

    @Transactional
    public void followBty(String mobilePhone, String btyPubSn, String btyOwnerPhone) throws CrudException;

    @Transactional
    public void shareBty(String mobilePhone, String btyPubSn, String friendPhone) throws CrudException;

    @Transactional
    public void unshareBty(String mobilePhone, String btyPubSn, String friendPhone) throws CrudException;

    @Transactional
    public void unfollowBty(String mobilePhone, String btyPubSn) throws CrudException;

    @Transactional
    public void lockBty(String mobilePhone, String btyImei) throws CrudException;

    @Transactional
    public void unlockBty(String mobilePhone, String btyImei) throws CrudException;

    public String getUserType(String mobilePhone) throws CrudException;
}
