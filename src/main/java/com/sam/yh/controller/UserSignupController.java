package com.sam.yh.controller;

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
import com.sam.yh.common.EmailUtils;
import com.sam.yh.common.IllegalParamsException;
import com.sam.yh.common.MobilePhoneUtils;
import com.sam.yh.common.PwdUtils;
import com.sam.yh.crud.exception.AuthCodeVerifyException;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.crud.exception.UserSignupException;
import com.sam.yh.enums.UserAccountType;
import com.sam.yh.model.User;
import com.sam.yh.req.bean.UserSignupReq;
import com.sam.yh.resp.bean.ResponseUtils;
import com.sam.yh.resp.bean.SamResponse;
import com.sam.yh.resp.bean.UserInfoResp;
import com.sam.yh.service.UserService;

@RestController
@RequestMapping("/user")
public class UserSignupController {

    private static final Logger logger = LoggerFactory.getLogger(UserSignupController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public SamResponse signup(HttpServletRequest httpServletRequest, @RequestParam("jsonReq") String jsonReq) {

        logger.info("Request json String, {}", jsonReq);

        UserSignupReq req = JSON.parseObject(jsonReq, UserSignupReq.class);

        try {
            validateSignupArgs(req);

            UserAccountType userAccountType = UserAccountType.getUserAccount(req.getAccountType());

            User user = userService.signup(req.getUserAccount(), userAccountType, req.getAuthCode(), req.getPassword1(), req.getDeviceInfo());

            UserInfoResp respData = new UserInfoResp();
            respData.setUserUid(user.getUuid());
            respData.setUserType(user.getUserType());

            logger.info("user signup, req:{}, resp:{}", req, respData);
            return ResponseUtils.getNormalResp(respData);
        } catch (IllegalParamsException e) {
            logger.error("signup exception", e);
            return ResponseUtils.getParamsErrorResp(e.getMessage());
        } catch (CrudException e) {
            logger.error("signup exception", e);
            if (e instanceof UserSignupException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
            } else if (e instanceof AuthCodeVerifyException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
            } else {
                return ResponseUtils.getSysErrorResp();
            }
        } catch (Exception e) {
            logger.error("signup exception", e);
            return ResponseUtils.getSysErrorResp();
        }

    }

    private void validateSignupArgs(UserSignupReq userSignupReq) throws IllegalParamsException {
        if (StringUtils.isBlank(userSignupReq.getAccountType())) {
            throw new IllegalParamsException("未知账户类型");
        }
        UserAccountType userAccountType = UserAccountType.getUserAccount(userSignupReq.getAccountType());
        if (userAccountType == null) {
            throw new IllegalParamsException("未知账户类型");
        }
        if (UserAccountType.MOBILE_PHONE.equals(userAccountType) && !MobilePhoneUtils.isValidPhone(userSignupReq.getUserAccount())) {
            throw new IllegalParamsException("请输入正确的手机号码");
        }

        if (UserAccountType.EMAIL.equals(userAccountType) && !EmailUtils.isValidEmail(userSignupReq.getUserAccount())) {
            throw new IllegalParamsException("请输入正确的电子邮箱");
        }

        if (StringUtils.isBlank(userSignupReq.getPassword1()) || StringUtils.isBlank(userSignupReq.getPassword2())) {
            throw new IllegalParamsException("密码不能为空");
        }

        if (!PwdUtils.isValidPwd(userSignupReq.getPassword1())) {
            throw new IllegalParamsException("密码长度为8-20位字符");
        }

        if (!StringUtils.equals(userSignupReq.getPassword1(), userSignupReq.getPassword2())) {
            throw new IllegalParamsException("密码输入不一致");
        }

    }
}
