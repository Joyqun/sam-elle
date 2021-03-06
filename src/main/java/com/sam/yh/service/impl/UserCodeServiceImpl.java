package com.sam.yh.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sam.yh.common.EmailUtils;
import com.sam.yh.common.MobilePhoneUtils;
import com.sam.yh.common.PwdUtils;
import com.sam.yh.common.RandomCodeUtils;
import com.sam.yh.common.SamConstants;
import com.sam.yh.common.msg.DahantSmsService;
import com.sam.yh.common.msg.EmailSendService;
import com.sam.yh.crud.exception.AuthCodeSendException;
import com.sam.yh.crud.exception.AuthCodeVerifyException;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.crud.exception.UserSignupException;
import com.sam.yh.dao.UserCodeMapper;
import com.sam.yh.dao.UserMapper;
import com.sam.yh.enums.UserCodeType;
import com.sam.yh.model.User;
import com.sam.yh.model.UserCode;
import com.sam.yh.service.UserCodeService;

@Service
public class UserCodeServiceImpl implements UserCodeService {
    private static final Logger logger = LoggerFactory.getLogger(UserCodeServiceImpl.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private UserCodeMapper userCodeMapper;

    @Resource
    private DahantSmsService dahantSmsService;
    
    @Resource
    private EmailSendService emailSendService;

    @Override
    public boolean sendSignupAuthCode(String userAccount,String userName) throws CrudException {
    	
        User user1 = userMapper.selectByUserAccount(userAccount);
         
        User user2 = userMapper.selectByUserName(userName);
        
        if(user1!= null){
        	  throw new UserSignupException("邮箱已经被注册");
        }
        if(user2!= null){
      	  throw new UserSignupException("该用户名已被注册");
      }
        
//        if (user != null && !user.getLockStatus()) {
//            throw new UserSignupException("手机号码已经注册");
//        }
        boolean result = false;
        String authCode = sendAndSaveSmsCode(userAccount, UserCodeType.SIGNUP_CODE.getType());
        
        if(MobilePhoneUtils.isValidPhone(userAccount)){
        	result =  dahantSmsService.sendSignupAuthCode(userAccount, authCode);
        }else if(EmailUtils.isValidEmail(userAccount)) {
        	//TODO
        	emailSendService.sendEmail(userAccount, authCode);
        	result = true;
        }else {
        	
        }
        return result;
    }

    @Override                     //此时userAccount里面传过来的是邮箱。
    public boolean sendResetPwdAuthCode(String userAccount) throws CrudException {
        User user = userMapper.selectByUserAccount(userAccount);
        if (user == null) {
            throw new UserSignupException("未注册的账号");
        }
//       String authCode = sendAndSaveSmsCode(mobilePhone, UserCodeType.RESETPWD_CODE.getType());
//        return dahantSmsService.sendResetPwdAuthCode(mobilePhone, authCode);
        boolean result = false;
        String authCode = sendAndSaveSmsCode(userAccount, UserCodeType.RESETPWD_CODE.getType());
        
        if(MobilePhoneUtils.isValidPhone(userAccount)){
        	result =  dahantSmsService.sendSignupAuthCode(userAccount, authCode);
        }else if(EmailUtils.isValidEmail(userAccount)) {
        	//TODO
        	emailSendService.sendEmail(userAccount, authCode);
        	result = true;
        }else {
        	
        }
        return result;
    }

    @Override
    public boolean sendTestAuthCode(String mobilePhone, String content) throws CrudException {
        return dahantSmsService.sendTestSms(mobilePhone, content);
    }

    @Override
    public String genAndSaveUserSalt(String userAccount, int type) {
        UserCode userCode = fetchByUserName(userAccount, UserCodeType.USER_SALT.getType());
        String salt = RandomCodeUtils.genSalt();
        Date now = new Date();
        if (userCode == null) {
            userCode = new UserCode();
            userCode.setUserAccount(userAccount);
            userCode.setCodeType(UserCodeType.USER_SALT.getType());
            userCode.setDynamicCode(salt);
            userCode.setSendTimes(1);
            userCode.setStatus(true);
            userCode.setSendDate(now);
            userCode.setExpiryDate(DateUtils.addMinutes(now, SamConstants.EXPIRY_TIME));

            userCodeMapper.insert(userCode);
        } else {
            userCode.setDynamicCode(salt);
            userCode.setSendTimes(1);
            userCode.setStatus(true);
            userCode.setSendDate(now);
            userCode.setExpiryDate(DateUtils.addMinutes(now, SamConstants.EXPIRY_TIME));

            userCodeMapper.updateByPrimaryKey(userCode);
        }
        return salt;
    }

    /**
     * 短信验证码发送
     */
            //    sendAndSaveSmsCode(userAccount, UserCodeType.RESETPWD_CODE.getType());
    private String sendAndSaveSmsCode(String userAccount, int type) throws AuthCodeSendException {
        UserCode userCode = fetchByUserName(userAccount, type);

        String smsCode = RandomCodeUtils.genSmsCode();
        Date now = new Date();
        if (userCode == null) {
            userCode = new UserCode();
            userCode.setUserAccount(userAccount);
            userCode.setCodeType(type);
            userCode.setDynamicCode(smsCode);
            userCode.setSendTimes(1);
            userCode.setStatus(true);
            userCode.setSendDate(now);
            userCode.setExpiryDate(DateUtils.addMinutes(now, SamConstants.EXPIRY_TIME));

            userCodeMapper.insert(userCode);
            return smsCode;
        }

        if (userCode.getSendTimes() >= SamConstants.MXA_SMS_SEND_TIME && DateUtils.isSameDay(now, userCode.getSendDate())) {
            throw new AuthCodeSendException("已经超过最大发送次数");
        }

        int sendTimes = DateUtils.isSameDay(now, userCode.getSendDate()) ? (userCode.getSendTimes() + 1) : 1;
        if (!userCode.getStatus() || now.after(userCode.getExpiryDate())) {
            // 验证码无效
            userCode.setDynamicCode(smsCode);
            userCode.setSendTimes(sendTimes);
            userCode.setStatus(true);
            userCode.setSendDate(now);
            userCode.setExpiryDate(DateUtils.addMinutes(now, SamConstants.EXPIRY_TIME));

            userCodeMapper.updateByPrimaryKey(userCode);
            return smsCode;
        } else {
            // 验证码有效，重新发送相同的验证码
            userCode.setSendTimes(sendTimes);

            userCodeMapper.updateByPrimaryKey(userCode);
            return userCode.getDynamicCode();
        }

    }

//    @Override
//    public UserCode fetchByUserName(String mobilePhone, int type) {
//        return userCodeMapper.selectByUserNameAndType(mobilePhone, type);
//    }
    @Override
    public UserCode fetchByUserName(String userAccount, int codeType) {
        return userCodeMapper.selectByUserNameAndType(userAccount, codeType);
    }
    
    
    @Override
    public boolean verifyAuthCode(String userAccount, int type, String authCode) throws CrudException {
        logger.info("verifyAuthCode pwd, {}", userAccount);
        logger.info("verifyAuthCode pwd, {}", type);
        UserCode userCode = fetchByUserName(userAccount, type);
        Date now = new Date();

        logger.info("verifyAuthCode pwd, {}", userCode);
        logger.info("verifyAuthCode pwd, {}", userCode.getStatus());
        logger.info("verifyAuthCode pwd, {}", now.before(userCode.getExpiryDate()));       
        logger.info("verifyAuthCode pwd, {}", StringUtils.equals(userCode.getDynamicCode(), authCode));
        logger.info("verifyAuthCode pwd, {}", userCode.getDynamicCode());     
        logger.info("verifyAuthCode pwd, {}", authCode);  
        if (userCode != null && userCode.getStatus() && now.before(userCode.getExpiryDate()) && StringUtils.equals(userCode.getDynamicCode(), authCode)) {
 //         if (userCode != null  && now.before(userCode.getExpiryDate()) && StringUtils.equals(userCode.getDynamicCode(), authCode)) {     	
            userCode.setStatus(false);
            userCodeMapper.updateByPrimaryKey(userCode);
            return true;
        } else {
            throw new AuthCodeVerifyException("验证码错误");
        }
    }

    @Override
    public boolean sendWarningMsg(String mobilePhone, String btyImei) throws CrudException {

        int type = UserCodeType.BTY_WARNING.getType();
        boolean send = needToSendMsg(mobilePhone, btyImei, type);
        if (send) {
            dahantSmsService.sendWarningMsg(mobilePhone, btyImei);
        }

        return send;
    }

    @Override
    public boolean sendMovingMsg(String mobilePhone, String btyImei) throws CrudException {
        int type = UserCodeType.BTY_MOVING.getType();
        boolean send = needToSendMsg(mobilePhone, btyImei, type);
        if (send) {
            dahantSmsService.sendMovingMsg(mobilePhone, btyImei);
        }

        return send;
    }

    private boolean needToSendMsg(String mobilePhone, String btyImei, int type) {
        boolean send = false;
        UserCode userCode = fetchByUserName(btyImei, type);

        Date now = new Date();
        if (userCode == null) {
            userCode = new UserCode();
            userCode.setUserAccount(btyImei);
            userCode.setCodeType(type);
            userCode.setDynamicCode(btyImei);
            userCode.setSendTimes(1);
            userCode.setStatus(true);
            userCode.setSendDate(now);
            userCode.setExpiryDate(DateUtils.addMinutes(now, SamConstants.EXPIRY_TIME));

            userCodeMapper.insert(userCode);
            send = true;
        }

        int sendTimes = DateUtils.isSameDay(now, userCode.getSendDate()) ? (userCode.getSendTimes() + 1) : 1;
        if (now.after(userCode.getExpiryDate()) && sendTimes <= SamConstants.MXA_WARNING_SEND_TIME) {
         //   userCode.setSendTimes(userCode.getSendTimes() + 1);
            userCode.setSendTimes(sendTimes);

            userCode.setSendDate(now);
            userCode.setExpiryDate(DateUtils.addMinutes(now, SamConstants.EXPIRY_TIME));

            userCodeMapper.updateByPrimaryKey(userCode);
            send = true;
        }
        return send;
    }

}
