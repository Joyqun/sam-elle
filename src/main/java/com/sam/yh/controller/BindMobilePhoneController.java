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
import com.sam.yh.common.IllegalParamsException;
import com.sam.yh.common.MobilePhoneUtils;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.crud.exception.M2mEditTermalException;
//import com.sam.yh.crud.exception.SubmitBtySpecException;
import com.sam.yh.crud.exception.BindMobilePhoneException;
import com.sam.yh.req.bean.BindMobilePhoneReq;
import com.sam.yh.resp.bean.ResponseUtils;
import com.sam.yh.resp.bean.SamResponse;
import com.sam.yh.service.UserService;

@RestController
@RequestMapping("/user")
public class BindMobilePhoneController {

    public static final Logger logger = LoggerFactory.getLogger(BindMobilePhoneController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/bindmobilephone", method = RequestMethod.POST)
    public SamResponse submitBtySpec(HttpServletRequest httpServletRequest, @RequestParam("jsonReq") String jsonReq) {

        logger.info("Request json String:" + jsonReq);
        BindMobilePhoneReq req = JSON.parseObject(jsonReq, BindMobilePhoneReq.class);

        try {
            validatebindmobilephoneArgs(req);

            userService.bindMobilePhone(req);

            return ResponseUtils.getNormalResp(StringUtils.EMPTY);
        } catch (IllegalParamsException e) {
            return ResponseUtils.getParamsErrorResp(e.getMessage());
        } catch (CrudException e) {
            logger.error("bindmobilephone exception, " + req.getDeviceImei(), e);
            if (e instanceof BindMobilePhoneException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
//            } else if (e instanceof M2mEditTermalException) {
//                return ResponseUtils.getServiceErrorResp("云电池Sim卡激活失败，请联系客服。");
            } else {
                return ResponseUtils.getSysErrorResp();
            }
        } catch (Exception e) {
            logger.error("bindmobilephone exception, " + req.getDeviceImei(), e);
            return ResponseUtils.getSysErrorResp();
        }
    }

    private void validatebindmobilephoneArgs(BindMobilePhoneReq bindMobilePhoneReq) throws IllegalParamsException {

        if (StringUtils.isBlank(bindMobilePhoneReq.getDeviceImei())) {
            throw new IllegalParamsException("请输入Imei号");
        }
        if (!MobilePhoneUtils.isValidPhone(bindMobilePhoneReq.getMobilePhone())) {
            throw new IllegalParamsException("请输入正确的手机号码");
        }
//        if (StringUtils.isBlank(submitBtySpecReq.getBtySN())) {
//            throw new IllegalParamsException("请输入序列号");
//        }
//      if (!MobilePhoneUtils.isValidPhone(submitBtySpecReq.getUserPhone())) {
//      throw new IllegalParamsException("请输入购买人正确的手机号码");
//        }
    }

}
