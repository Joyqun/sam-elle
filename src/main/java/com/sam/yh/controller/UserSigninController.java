package com.sam.yh.controller;

import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Sets;
import com.sam.yh.common.EmailUtils;
import com.sam.yh.common.IllegalParamsException;
import com.sam.yh.common.MobilePhoneUtils;
import com.sam.yh.common.PwdUtils;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.crud.exception.UserSignupException;
import com.sam.yh.enums.UserAccountType;
import com.sam.yh.model.User;
import com.sam.yh.req.bean.UserSigninReq;
import com.sam.yh.resp.bean.ResponseUtils;
import com.sam.yh.resp.bean.SamResponse;
import com.sam.yh.resp.bean.UserInfoResp;
import com.sam.yh.service.UserService;

@RestController
@RequestMapping("/user")
public class UserSigninController {

    @Autowired
    UserService userService;

    @Resource
    private String adminAccounts;

    private static final Logger logger = LoggerFactory.getLogger(UserSigninController.class);

    @RequestMapping(value = "/signin", method = RequestMethod.POST)
    public SamResponse signin(HttpServletRequest httpServletRequest, @RequestParam("jsonReq") String jsonReq) {

        logger.info("Request json String:" + jsonReq);

        UserSigninReq req = JSON.parseObject(jsonReq, UserSigninReq.class);

        try {
            validateSigninArgs(req);
            //joy
            //UserAccountType userAccountType = UserAccountType.getUserAccount(req.getAccountType());

            User user = userService.signin(req.getUserSigninName(), req.getPassword(), req.getDeviceInfo());

            UserInfoResp respData = new UserInfoResp();
            respData.setUserUid(user.getUuid());
            respData.setUserType(user.getUserType());
            respData.setUserName(user.getUserName());
            respData.setUserAccount(user.getUserAccount());
            

            logger.info("user signin, req:{}, resp:{}", req, respData);
            return ResponseUtils.getNormalResp(respData);
        } catch (IllegalParamsException e) {
            logger.error("signin exception", e);
            return ResponseUtils.getParamsErrorResp(e.getMessage());
        } catch (CrudException e) {
            logger.error("signin exception", e);
            if (e instanceof UserSignupException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
            } else {
                return ResponseUtils.getSysErrorResp();
            }
        } catch (Exception e) {
            logger.error("signin exception", e);
            return ResponseUtils.getSysErrorResp();
        }
    }

    private void validateSigninArgs(UserSigninReq userSigninReq) throws IllegalParamsException {
/*        if (StringUtils.isBlank(userSigninReq.getAccountType())) {
            throw new IllegalParamsException("未知账户类型");
        }*/
        UserAccountType userAccountType = UserAccountType.getUserAccount(userSigninReq.getAccountType());
        if (userAccountType == null) {
            throw new IllegalParamsException("未知账户类型");
        }
//        if (!isAdminAccount(userSigninReq.getUserAccount())) {
//            if (UserAccountType.MOBILE_PHONE.equals(userAccountType) && !MobilePhoneUtils.isValidPhone(userSigninReq.getUserAccount())) {
//                throw new IllegalParamsException("请输入正确的手机号码");
//            }
// /*           if (UserAccountType.EMAIL.equals(userAccountType) && !EmailUtils.isValidEmail(userSigninReq.getUserAccount())) {
//                throw new IllegalParamsException("请输入正确的电子邮箱");
//            }*/
//        }

        if (!PwdUtils.isValidPwd(userSigninReq.getPassword())) {
            throw new IllegalParamsException("密码长度为8-20位字符");
        }

    }

//    private boolean isAdminAccount(String userAccount) {
//        String[] accounts = StringUtils.split(adminAccounts, ",");
//        if (accounts == null || accounts.length == 0) {
//            logger.error("获取admin account 失败");
//        }
//        Set<String> admins = Sets.newHashSet(accounts);
//        return admins.contains(userAccount);
//    }
}
