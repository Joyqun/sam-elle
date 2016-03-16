package com.sam.yh.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.sam.yh.common.PwdUtils;
import com.sam.yh.common.RandomCodeUtils;
import com.sam.yh.common.SamConstants;
import com.sam.yh.crud.exception.BtyFollowException;
import com.sam.yh.crud.exception.BtyLockException;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.crud.exception.FetchBtyInfoException;
import com.sam.yh.crud.exception.PwdResetException;
import com.sam.yh.crud.exception.AddDeviceException;
import com.sam.yh.crud.exception.BindMobilePhoneException;
import com.sam.yh.crud.exception.UserSignupException;
import com.sam.yh.crud.exception.UserSigninException;
import com.sam.yh.dao.BatteryInfoMapper;
import com.sam.yh.dao.BatteryMapper;
import com.sam.yh.dao.ResellerMapper;
import com.sam.yh.dao.UserBatteryMapper;
import com.sam.yh.dao.UserFollowMapper;
import com.sam.yh.dao.UserMapper;
import com.sam.yh.dao.UserAttributeMapper;
import com.sam.yh.enums.BatteryStatus;
import com.sam.yh.enums.UserAccountType;
import com.sam.yh.enums.UserCodeType;
import com.sam.yh.enums.UserType;
import com.sam.yh.model.Battery;
import com.sam.yh.model.BatteryInfo;
import com.sam.yh.model.PubBatteryInfo;
import com.sam.yh.model.Reseller;
import com.sam.yh.model.User;
import com.sam.yh.model.UserAttribute;
import com.sam.yh.model.UserBattery;
import com.sam.yh.model.UserBatteryKey;
import com.sam.yh.model.UserFollow;
import com.sam.yh.model.UserFollowKey;
import com.sam.yh.req.bean.AddDeviceReq;
import com.sam.yh.req.bean.BindMobilePhoneReq;
import com.sam.yh.service.BatteryService;
import com.sam.yh.service.UserBatteryService;
import com.sam.yh.service.UserCodeService;
import com.sam.yh.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Resource
    private UserCodeService userCodeService;

    @Resource
    private UserBatteryService userBatteryService;
    
    @Resource
    BatteryService batteryService;
    
    @Resource
    private UserMapper userMapper;

    @Resource
    private BatteryMapper batteryMapper;

    @Resource
    private BatteryInfoMapper batteryInfoMapper;

    @Resource
    private UserFollowMapper userFollowMapper;

    @Resource
    private UserBatteryMapper userBatteryMapper;

    @Resource
    private ResellerMapper resellerMapper;
    
    @Resource
    private UserAttributeMapper userAttributeMapper;

    @Resource
    private String adminAccounts;

    @Override
    public User signup(String userName,String userAccount, UserAccountType accountType, String authCode, String hassPwd, String deviceInfo) throws CrudException {


//        if (user != null && !user.getLockStatus()) {
//            throw new UserSignupException("用户账户已使用");
//        }
      User user = fetchUserByUserAccount(userName);
      if (user != null) {
      throw new UserSignupException("用户名已使用");
       }else 
    	   {
    	    User user1 = fetchUserByUserAccount(userAccount);
    	    if (user1 != null) {
    	          throw new UserSignupException("邮箱已使用");
    	           }    	        	   
    	   }
     
        boolean auth = userCodeService.verifyAuthCode(userAccount, UserCodeType.SIGNUP_CODE.getType(), authCode);
        if (!auth) {
            throw new UserSignupException("注册验证码错误");
        }
        Date now = new Date();
        String uuid = UUID.randomUUID().toString();
        String salt = RandomCodeUtils.genSalt();

        if (user == null) {
            user = new User();
            user.setUuid(StringUtils.replace(uuid, "-", ""));
            user.setUserName(userName);
            user.setUserType(UserType.NORMAL_USER.getType());
            user.setSalt(salt);
//            ///gaobo modify
            user.setPassword(PwdUtils.genMd5Pwd(userAccount, salt, hassPwd));
//          user.setPassword(PwdUtils.genMd5Pwd(userName, salt, hassPwd));
//           
//            user.setDeviceInfo(userAccount); 
//            user.setUserAccount(userName);  
//            //modify end
            user.setUserAccount(userAccount);
            user.setAccountType(accountType.getType());
            user.setLockStatus(false);
            user.setDeviceInfo(deviceInfo);
            user.setCreateDate(now);
            user.setLoginDate(now);

            userMapper.insert(user);
        }
//        } else {
//            user.setSalt(salt);
//
//            user.setUserName(userName);
//            user.setUserType(UserType.NORMAL_USER.getType());
//            user.setPassword(PwdUtils.genMd5Pwd(userAccount, salt, hassPwd));
//            user.setLockStatus(false);
//            user.setDeviceInfo(deviceInfo);
//            user.setLoginDate(now);
//
//            userMapper.updateByPrimaryKeySelective(user);
//        }

        return user;
    }

    @Override
    public User signin(String userSigninName, String hassPwd, String deviceInfo) throws CrudException {       
        
//        if (user == null || user.getLockStatus()) {
//            throw new UserSignupException("用户不存在");
//        }
//        logger.info("user pwd, {}", user.getPassword());
//        logger.info("genmd5 pwd, {}", PwdUtils.genMd5Pwd(userAccount, user.getSalt(), hassPwd));
//        logger.info("genmd5 pwd, {}", user.getSalt());       
//        logger.info("genmd5 pwd, {}", hassPwd);
//        logger.info("genmd5 pwd, {}", userAccount); 
    	
    	boolean IsUserAccount = true;
    	String pwdAccount;
    	String accountName;
        User user = fetchUserByUserAccount(userSigninName);
        if (user == null) {
             User user1 =  fetchUserByUserName(userSigninName);
             if (user1 == null){ 
            	 throw new UserSigninException("用户不存在");//不存在        	   
          	   }else
          	   { 
          		 IsUserAccount =false;                   //用户名
                 pwdAccount= user1.getUserAccount();
            	 accountName= userSigninName;         		 
               }
        }else{                                           //邮箱
        	pwdAccount=  userSigninName;
        	accountName= user.getUserName();
        }
        	
//       if (!user.getUserType().equals(getUserType(pwdAccount))) {
//            user.setUserType(getUserType(pwdAccount));
//        }
        
        if(IsUserAccount){
            if (!StringUtils.equals(user.getPassword(), PwdUtils.genMd5Pwd(pwdAccount, user.getSalt(), hassPwd))) {
                throw new UserSigninException("用户名或密码错误");
            }
            user.setLoginDate(new Date());
            user.setDeviceInfo(deviceInfo);
            user.setUserName(accountName);
            user.setUserAccount(pwdAccount);
            userMapper.updateByPrimaryKeySelective(user);
            
            return user;
        }else{
            User user2 = fetchUserByUserName(userSigninName);
            
            if (!StringUtils.equals(user2.getPassword(), PwdUtils.genMd5Pwd(pwdAccount, user2.getSalt(), hassPwd))) {
                throw new UserSigninException("用户名或密码错误");
            }
            user2.setLoginDate(new Date());
            user2.setDeviceInfo(deviceInfo);
            user2.setUserName(accountName);
            user2.setUserAccount(pwdAccount);
            userMapper.updateByPrimaryKeySelective(user2);
            
            return user2;
        }
        
    }

    @Override
    public User resetPwd(String userAccount, String authCode, String hassPwd) throws CrudException {
//  public User resetPwd(String mobilePhone, String authCode, String hassPwd, String deviceInfo) throws CrudException {
        User user = fetchUserByUserAccount(userAccount); 
        if (user == null) {
            throw new PwdResetException("未注册的账户");
        }
        boolean auth = userCodeService.verifyAuthCode(userAccount, UserCodeType.RESETPWD_CODE.getType(), authCode);
        if (!auth) {
            throw new PwdResetException("验证码错误");
        }
        Date now = new Date();
        String salt = user.getSalt();
        user.setPassword(PwdUtils.genMd5Pwd(user.getUserAccount(), salt, hassPwd));
        user.setLockStatus(false);
        user.setLoginDate(now);
//        user.setDeviceInfo(deviceInfo);

        userMapper.updateByPrimaryKeySelective(user);

        return user;
    }

    @Override
    public void addDevice(AddDeviceReq addDeviceReq) throws CrudException {
        if (batteryService.fetchBtyByIMEI(addDeviceReq.getDeviceImei()) != null) {
            throw new AddDeviceException("该IMEI号已存在");//该IMEI号在数据库里已存在
        }
            
        User user = fetchUserByUserAccount(addDeviceReq.getUserAccount());        
        if (user == null) {
           throw new AddDeviceException("未注册的账号");
        }
    	    	
        boolean isCloudBty = true;

        Battery battery = addBattery(addDeviceReq.getDeviceImei(),addDeviceReq.getDeviceImei(),addDeviceReq.getDeviceImei(),addDeviceReq.getDeviceImei(),isCloudBty,0,0);

        UserBattery userBattery = new UserBattery();
        userBattery.setBatteryId(battery.getId());
        userBattery.setUserId(user.getUserId());
        userBattery.setBuyDate(new Date());    

        userBatteryMapper.insert(userBattery);

      
    }
       
    @Override                   //     deviceImei;  mobilePhone;
    public void bindMobilePhone(BindMobilePhoneReq bindMobilePhoneReq)throws CrudException {

      Battery battery= batteryMapper.selectByIMEI(bindMobilePhoneReq.getDeviceImei());

      if (battery == null) {
          throw new BindMobilePhoneException("电池IMEI号不存在");
      }else{
        	
      battery.setBindMobile(bindMobilePhoneReq.getMobilePhone());
        
      batteryMapper.updateByPrimaryKeySelective(battery);
      } 
     
  }
    

    @Override
    public List<PubBatteryInfo> fetchSelfBtyInfo(String userAccount) {
        User user = fetchUserByUserAccount(userAccount);
        if (user == null) {
            return Collections.emptyList();
        }
        List<UserBattery> btys = userBatteryService.fetchUserBattery(user.getUserId());
        List<PubBatteryInfo> btyInfo = new ArrayList<PubBatteryInfo>();
        for (UserBattery userBattery : btys) {
            BatteryInfo info = batteryInfoMapper.selectByBtyId(userBattery.getBatteryId());
            if (info != null) {
                PubBatteryInfo pubInfo = new PubBatteryInfo(info);
                pubInfo.setBtyPubSn(userBattery.getBtyPubSn());
                pubInfo.setBytImei(userBattery.getBytImei());
                pubInfo.setOwnerPhone(user.getUserAccount());
                ///Joy modify
                pubInfo.setLockStatus(info.getLockStatus());
                pubInfo.setExtension((Map<String, String>)JSON.parse(info.getExtension()));
                ///Joy modify end
                btyInfo.add(pubInfo);
            }
        }

        return btyInfo;
    }

    @Override
    public List<PubBatteryInfo> fetchFriendsBtyInfo(String userAccount) {
        User user = fetchUserByUserAccount(userAccount);
        if (user == null) {
            return Collections.emptyList();
        }
        List<UserFollow> btys = userBatteryService.fetchUserFollowBty(user.getUserId());
        List<PubBatteryInfo> btyInfo = new ArrayList<PubBatteryInfo>();
        for (UserFollow userFollow : btys) {
            BatteryInfo info = batteryInfoMapper.selectByBtyId(userFollow.getBatteryId());
            if (info != null) {
                UserBattery userBattery = userBatteryService.fetchUserByBtyId(info.getBatteryId());
                User owner = userMapper.selectByPrimaryKey(userBattery.getUserId());
                PubBatteryInfo pubInfo = new PubBatteryInfo(info);
                pubInfo.setBtyPubSn(userFollow.getBtyPubSn());
                pubInfo.setBytImei(userFollow.getBytImei());
                pubInfo.setOwnerPhone(owner.getUserAccount());
                ///Joy
                pubInfo.setLockStatus(info.getLockStatus());
 //               pubInfo.setExtension(info.getExtension());
                pubInfo.setExtension((Map<String, String>)JSON.parse(info.getExtension()));

                ///JOy end
                btyInfo.add(pubInfo);
            }
        }

        return btyInfo;
    }
    @Override
    public User fetchUserByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }
    

    @Override
    public User fetchUserByUserAccount(String userAccount) {
        return userMapper.selectByUserAccount(userAccount);
    }

    @Override  //(String userAccount, String deviceImei, String btyOwnerAccount)
    public void followBty(String userAccount, String deviceImei, String btyOwnerAccount) throws CrudException {
        User user = fetchUserByUserAccount(userAccount);
        if (user == null) {
            throw new BtyFollowException("用户不存在");
        }

        User btyOwner = fetchUserByUserAccount(btyOwnerAccount);
        if (btyOwner == null) {
            throw new BtyFollowException("用户不存在");
        }

        Battery battery = batteryService.fetchBtyByIMEI(deviceImei);
        if (battery == null) {
            throw new BtyFollowException("电池不存在");
        }
        UserBatteryKey key = new UserBatteryKey();
        key.setUserId(btyOwner.getUserId());
        key.setBatteryId(battery.getId());
        UserBattery userBattery = userBatteryMapper.selectByPrimaryKey(key);
        if (userBattery == null) {
            throw new BtyFollowException("好友手机号码与电池序列号不匹配");
        }

        List<UserFollow> userFollowBtyList = userBatteryService.fetchUserFollowBty(user.getUserId());
        if (userFollowBtyList != null && userFollowBtyList.size() >= SamConstants.MAX_FOLLOW_COUNT) {
            throw new BtyFollowException("您已达到了最大关注数量");
        }
        for (UserFollow userFollow : userFollowBtyList) {
            if (StringUtils.equals(userFollow.getBytImei(), deviceImei) && userFollow.getFollowStatus()) {
                throw new BtyFollowException("您已关注了该电池");
            }
        }

        innerFollow(user, battery);

    }

    @Override
    public void shareBty(String mobile_Phone, String deviceImei, String friendPhone) throws CrudException {
        User owner = fetchUserByUserAccount(mobile_Phone);
        if (owner == null) {
            throw new BtyFollowException("用户不存在");
        }

        User shareUser = fetchUserByUserAccount(friendPhone);
        if (shareUser == null) {
            throw new BtyFollowException("好友不存在");
        }

        Battery battery = batteryService.fetchBtyByIMEI(deviceImei);
        if (battery == null) {
            throw new BtyFollowException("电池不存在");
        }
        UserBatteryKey key = new UserBatteryKey();
        key.setUserId(owner.getUserId());
        key.setBatteryId(battery.getId());
        UserBattery userBattery = userBatteryMapper.selectByPrimaryKey(key);
        if (userBattery == null) {
            throw new BtyFollowException("只能共享自己购买的电池");
        }

        List<UserFollow> userFollowBtyList = userBatteryService.fetchUserFollowBty(shareUser.getUserId());
        if (userFollowBtyList != null && userFollowBtyList.size() >= SamConstants.MAX_FOLLOW_COUNT) {
            throw new BtyFollowException("您好友已达到了最大关注数量");
        }
        for (UserFollow userFollow : userFollowBtyList) {
            if (StringUtils.equals(userFollow.getBytImei(), deviceImei) && userFollow.getFollowStatus()) {
//                throw new BtyFollowException("您已关注了该电池"); Joy modify
                throw new BtyFollowException("您的好友已关注了该设备");

            }
        }

        innerFollow(shareUser, battery);

    }
 
    private void innerFollow(User follower, Battery battery) {
        Date now = new Date();

        UserFollowKey followKey = new UserFollowKey();
        followKey.setBatteryId(battery.getId());
        followKey.setUserId(follower.getUserId());

        UserFollow userFollowExist = userFollowMapper.selectByPrimaryKey(followKey);
        if (userFollowExist != null) {
            userFollowExist.setFollowStatus(true);
            userFollowExist.setFollowDate(now);

            userFollowMapper.updateByPrimaryKeySelective(userFollowExist);
        } else {
            UserFollow userFollow = new UserFollow();
            userFollow.setUserId(follower.getUserId());
            userFollow.setBatteryId(battery.getId());
            userFollow.setFollowStatus(true);
            userFollow.setFollowDate(now);

            userFollowMapper.insert(userFollow);
        }
    }

    @Override
    public void unfollowBty(String mobilePhone, String btyPubSn) throws CrudException {
        // TODO Auto-generated method stub

    }

    @Override
    public void unshareBty(String mobilePhone, String deviceImei, String friendPhone) throws CrudException {
        User owner = fetchUserByUserAccount(mobilePhone);
        if (owner == null) {
            throw new BtyFollowException("用户不存在");
        }

        User shareUser = fetchUserByUserAccount(friendPhone);
        if (shareUser == null) {
            throw new BtyFollowException("好友不存在");
        }

        Battery battery = batteryService.fetchBtyByIMEI(deviceImei);
        if (battery == null) {
            throw new BtyFollowException("电池不存在");
        }
        UserBatteryKey key = new UserBatteryKey();
        key.setUserId(owner.getUserId());
        key.setBatteryId(battery.getId());
        UserBattery userBattery = userBatteryMapper.selectByPrimaryKey(key);
        if (userBattery == null) {
            throw new BtyFollowException("只能删除自己购买的电池的关注者");
        }

        Date now = new Date();

        UserFollowKey followKey = new UserFollowKey();
        followKey.setBatteryId(battery.getId());
        followKey.setUserId(shareUser.getUserId());

        UserFollow userFollow = userFollowMapper.selectByPrimaryKey(followKey);
        userFollow.setFollowStatus(false);
        userFollow.setFollowDate(now);
        userFollowMapper.updateByPrimaryKeySelective(userFollow);

    }

    @Override
    public String getUserType(String mobilePhone) throws CrudException {
        String[] accounts = StringUtils.split(adminAccounts, ",");

        Set<String> admins = Sets.newHashSet(accounts);

        if (admins.contains(mobilePhone)) {
            return UserType.ADMIN.getType();
        }

        User user = userMapper.selectByUserAccount(mobilePhone);
        if (user == null) {
            return UserType.NORMAL_USER.getType();
        }

        Reseller reseller = resellerMapper.selectByPrimaryKey(user.getUserId());
        if (reseller != null) {
            return UserType.RESELLER.getType();
        } else {
            return UserType.NORMAL_USER.getType();
        }

    }

    @Override
    public void lockBty(String mobilePhone, String btyImei) throws CrudException {
        User owner = fetchUserByUserAccount(mobilePhone);
        if (owner == null) {
            throw new BtyLockException("用户不存在");
        }

        Battery battery = batteryService.fetchBtyByIMEI(btyImei);
        if (battery == null) {
            throw new BtyLockException("电池不存在");
        }
        UserBatteryKey key = new UserBatteryKey();
        key.setUserId(owner.getUserId());
        key.setBatteryId(battery.getId());
        UserBattery userBattery = userBatteryMapper.selectByPrimaryKey(key);
        if (userBattery == null) {
            throw new BtyLockException("只能锁定自己购买的电池");
        }

        if (BatteryStatus.LOCKED.getStatus().equals(battery.getStatus())) {
            throw new BtyLockException("电池已经锁定，请先解锁");
        }

        BatteryInfo latestInfo = batteryInfoMapper.selectByBtyId(battery.getId());

        battery.setStatus(BatteryStatus.LOCKED.getStatus());
        battery.setLockLongitude(latestInfo.getLongitude());
        battery.setLockLatitude(latestInfo.getLatitude());
        battery.setLockDate(new Date());

        batteryMapper.updateByPrimaryKeySelective(battery);

    }

    @Override
    public void unlockBty(String mobilePhone, String btyImei) throws CrudException {
        User owner = fetchUserByUserAccount(mobilePhone);
        if (owner == null) {
            throw new BtyLockException("用户不存在");
        }

        Battery battery = batteryService.fetchBtyByIMEI(btyImei);
        if (battery == null) {
            throw new BtyLockException("电池不存在");
        }
        UserBatteryKey key = new UserBatteryKey();
        key.setUserId(owner.getUserId());
        key.setBatteryId(battery.getId());
        UserBattery userBattery = userBatteryMapper.selectByPrimaryKey(key);
        if (userBattery == null) {
            throw new BtyLockException("只能解锁自己购买的电池");
        }

        battery.setStatus(BatteryStatus.NORMAL.getStatus());
        battery.setLockDate(new Date());

        batteryMapper.updateByPrimaryKeySelective(battery);

    }

	@Override
	public User selectByUserName(String userName) throws CrudException {
		return userMapper.selectByUserName(userName);
	}
    private Battery addBattery(String btySn, String imei, String simNo, String iccid, boolean isCloudBty, int resellerId, int cityId) {
        Date now = new Date();
        Battery battery = new Battery();
        battery.setSn(btySn);
        battery.setPubSn(RandomCodeUtils.genBtyPubSn());
        battery.setImei(imei);
        battery.setIccid(iccid);
        battery.setSimNo(simNo);
        battery.setBtyType(isCloudBty);
        battery.setStatus(BatteryStatus.NORMAL.getStatus());
        battery.setResellerId(resellerId);
        battery.setCityId(cityId);
        battery.setSaleStatus(true);
        battery.setCreateDate(now);
        battery.setSaleDate(now);
//        battery.setBindmobile(bindmobile);

        return batteryService.addBattery(battery);
    }

}


