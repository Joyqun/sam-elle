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
import com.sam.yh.crud.exception.BtyFollowException;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.req.bean.BtyShareReq;
import com.sam.yh.resp.bean.ResponseUtils;
import com.sam.yh.resp.bean.SamResponse;
import com.sam.yh.service.UserService;

@RestController
@RequestMapping("/user")
public class RemoveFollowerController {

    private static final Logger logger = LoggerFactory.getLogger(RemoveFollowerController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/bty/unshare", method = RequestMethod.POST)
    public SamResponse shareBty(HttpServletRequest httpServletRequest, @RequestParam("jsonReq") String jsonReq) {
        logger.info("Request json String:" + jsonReq);

        BtyShareReq req = JSON.parseObject(jsonReq, BtyShareReq.class);

        try {
            validateBtyShareArgs(req);

            userService.unshareBty(req.getUserAccount().trim(), req.getDeviceImei().trim(), req.getFriendAccount().trim());

            return ResponseUtils.getNormalResp(StringUtils.EMPTY);
        } catch (IllegalParamsException e) {
            return ResponseUtils.getParamsErrorResp(e.getMessage());
        } catch (CrudException e) {
            logger.error("remove bty follower exception, " + req.getUserAccount(), e);
            if (e instanceof BtyFollowException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
            } else {
                return ResponseUtils.getSysErrorResp();
            }
        } catch (Exception e) {
            logger.error("remove bty follower exception, " + req.getUserAccount(), e);
            return ResponseUtils.getSysErrorResp();
        }
    }

    private void validateBtyShareArgs(BtyShareReq btyShareReq) throws IllegalParamsException {

         if ( !EmailUtils.isValidEmail(btyShareReq.getUserAccount())) {
            throw new IllegalParamsException("请输入正确的电子邮箱");
           }
         if (StringUtils.isBlank(btyShareReq.getDeviceImei())) {
            throw new IllegalParamsException("不存在的电池");
           }

         if ( !EmailUtils.isValidEmail(btyShareReq.getFriendAccount())) {
             throw new IllegalParamsException("请输入好友正确的电子邮箱");
           }

         if (StringUtils.equals(btyShareReq.getUserAccount(), btyShareReq.getFriendAccount())) {
             throw new IllegalParamsException("不能关注自己");
          }
}
    

}
