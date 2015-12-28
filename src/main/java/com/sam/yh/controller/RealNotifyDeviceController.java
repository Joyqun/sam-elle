package com.sam.yh.controller;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sam.yh.common.IllegalParamsException;
import com.sam.yh.common.MobilePhoneUtils;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.crud.exception.FetchBtyInfoException;
import com.sam.yh.enums.DeviceChatType;
import com.sam.yh.model.BatteryInfo;
import com.sam.yh.req.bean.DeviceInfoReq;
import com.sam.yh.resp.bean.BtyInfoRespVo;
import com.sam.yh.resp.bean.ResponseUtils;
import com.sam.yh.resp.bean.SamResponse;
import com.sam.yh.service.BatteryService;
import com.sam.yh.service.DeviceChatService;

@RestController
@RequestMapping("/real")
public class RealNotifyDeviceController {

    private static final Logger logger = LoggerFactory.getLogger(RealNotifyDeviceController.class);

    @Resource
    BatteryService batteryService;

    @Resource
    DeviceChatService deviceChatService;

    @Resource
    Integer maxWaitTime;

    @RequestMapping(value = "/device/info", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON)
    public SamResponse RealFetchDeviceInfo(HttpServletRequest httpServletRequest, @RequestBody DeviceInfoReq request) {
        logger.info("Request json String:{}" + request);
        try {
            validateBtyArgs(request);

            // TODO

            // 发送实时命令
            boolean hasConnect = deviceChatService.chat(request.getDeviceSimNo(), DeviceChatType.LASTEST_INFO.getChatType());

            if (hasConnect) {
                TimeUnit.SECONDS.sleep(maxWaitTime);
            }

            BatteryInfo info = batteryService.fetchBtyInfo(request.getDeviceSimNo());
            BtyInfoRespVo respData = new BtyInfoRespVo();
            respData.setLatitude(info.getLatitude());
            respData.setLongitude(info.getLongitude());
            respData.setTemperature(info.getTemperature());
            respData.setVoltage(info.getVoltage());

            respData.setLastestDate(DateFormatUtils.format(info.getReceiveDate(), "yyyy-MM-dd HH:mm:ss"));

            logger.info("Request:{},Response:{}", request, respData);

            return ResponseUtils.getNormalResp(respData);

        } catch (IllegalParamsException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return ResponseUtils.getParamsErrorResp(e.getMessage());
        } catch (CrudException e) {
            logger.error("fetch battery info exception, simNo:{}, exception:{}" + request.getDeviceSimNo(), ExceptionUtils.getStackTrace(e));
            if (e instanceof FetchBtyInfoException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
            } else {
                return ResponseUtils.getSysErrorResp();
            }
        } catch (Exception e) {
            logger.error("fetch battery info exception, simNo:{}, exception:{}" + request.getDeviceSimNo(), ExceptionUtils.getStackTrace(e));
            return ResponseUtils.getSysErrorResp();
        }
    }

    private void validateBtyArgs(DeviceInfoReq btyInfoReq) throws IllegalParamsException {
        if (!MobilePhoneUtils.isValidM2MPhone(btyInfoReq.getDeviceSimNo())) {
            throw new IllegalParamsException("请输入正确的电池sim卡号");
        }
    }

}
