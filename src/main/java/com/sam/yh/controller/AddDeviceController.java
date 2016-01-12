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
import com.sam.yh.crud.exception.AuthCodeVerifyException;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.crud.exception.M2mEditTermalException;
//import com.sam.yh.crud.exception.SubmitBtySpecException;
import com.sam.yh.crud.exception.AddDeviceException;
import com.sam.yh.enums.UserAccountType;
import com.sam.yh.model.User;
import com.sam.yh.req.bean.AddDeviceReq;
import com.sam.yh.resp.bean.ResponseUtils;
import com.sam.yh.resp.bean.SamResponse;
import com.sam.yh.resp.bean.UserInfoResp;
import com.sam.yh.service.UserService;

@RestController
@RequestMapping("/user")
public class AddDeviceController {

    public static final Logger logger = LoggerFactory.getLogger(AddDeviceController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/adddevice", method = RequestMethod.POST)
    public SamResponse adddevice(HttpServletRequest httpServletRequest, @RequestParam("jsonReq") String jsonReq) {

        logger.info("Request json String, {}", jsonReq);

        AddDeviceReq req = JSON.parseObject(jsonReq, AddDeviceReq.class);

        try {
            validateadddeviceArgs(req);

            userService.addDevice(req);
      
            return ResponseUtils.getNormalResp(StringUtils.EMPTY);
        } catch (IllegalParamsException e) {
            logger.error("adddevice exception", e);
            return ResponseUtils.getParamsErrorResp(e.getMessage());
        } catch (CrudException e) {
            logger.error("adddevice exception", e);
            if (e instanceof AddDeviceException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
            } else if (e instanceof AuthCodeVerifyException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
            } else {
                return ResponseUtils.getSysErrorResp();
            }
        } catch (Exception e) {
            logger.error("AddDevice exception", e);
            return ResponseUtils.getSysErrorResp();
        }

    }

    private void validateadddeviceArgs(AddDeviceReq addDeviceReq) throws IllegalParamsException {
        if (StringUtils.isBlank(addDeviceReq.getDeviceImei())) {
            throw new IllegalParamsException("请输入IMEI");
        }
        
      if (!EmailUtils.isValidEmail(addDeviceReq.getUserAccount())) {
      throw new IllegalParamsException("请输入正确的电子邮箱");
      }

    }

}
