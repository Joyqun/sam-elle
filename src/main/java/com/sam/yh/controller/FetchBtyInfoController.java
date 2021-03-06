package com.sam.yh.controller;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
import com.sam.yh.common.SamConstants;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.crud.exception.FetchBtyInfoException;
import com.sam.yh.model.Battery;
import com.sam.yh.model.BatteryInfo;
import com.sam.yh.req.bean.DeviceInfoReq;
import com.sam.yh.resp.bean.BtyInfoRespVo;
import com.sam.yh.resp.bean.ResponseUtils;
import com.sam.yh.resp.bean.SamResponse;
import com.sam.yh.service.BatteryService;
import com.sam.yh.service.socket.SamBtyDataHandler;

@RestController
@RequestMapping("/bty")
public class FetchBtyInfoController {

    private static final Logger logger = LoggerFactory.getLogger(FetchBtyInfoController.class);

    @Autowired
    BatteryService batteryService;

    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public SamResponse fetchBtyInfo(HttpServletRequest httpServletRequest, @RequestParam("jsonReq") String jsonReq) {
        logger.info("Request json String:" + jsonReq);
        DeviceInfoReq req = JSON.parseObject(jsonReq, DeviceInfoReq.class);

        try {
            validateBtyArgs(req);

            BatteryInfo info = batteryService.fetchBtyInfo(req.getDeviceImei());
            BtyInfoRespVo respData = new BtyInfoRespVo();
            respData.setLatitude(info.getLatitude());
            respData.setLongitude(info.getLongitude());
            respData.setTemperature(info.getTemperature());
            respData.setVoltage(info.getVoltage());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(info.getReceiveDate());
            respData.setLastestDate(dateString);

            return ResponseUtils.getNormalResp(respData);
        } catch (IllegalParamsException e) {
            return ResponseUtils.getParamsErrorResp(e.getMessage());
        } catch (CrudException e) {
            logger.error("fetch battery info exception, " + req.getDeviceImei(), e);
            if (e instanceof FetchBtyInfoException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
            } else {
                return ResponseUtils.getSysErrorResp();
            }
        } catch (Exception e) {
            logger.error("fetch battery info exception, " + req.getDeviceImei(), e);
            return ResponseUtils.getSysErrorResp();
        }
    }

    @RequestMapping(value = "/info/real", method = RequestMethod.POST)//实时刷新采用的接口
    public SamResponse fetchBtyInfoReal(HttpServletRequest httpServletRequest, @RequestParam("jsonReq") String jsonReq) {
        logger.info("Request json String:" + jsonReq);
        DeviceInfoReq req = JSON.parseObject(jsonReq, DeviceInfoReq.class);

        try {
            validateBtyArgs(req);

            sendReq(req.getDeviceImei());

            TimeUnit.SECONDS.sleep(SamConstants.MAX_WAIT_SECONDS);

            BatteryInfo info = batteryService.fetchBtyInfo(req.getDeviceImei());
            BtyInfoRespVo respData = new BtyInfoRespVo();
            respData.setLatitude(info.getLatitude());
            respData.setLongitude(info.getLongitude());
            respData.setTemperature(info.getTemperature());
            respData.setVoltage(info.getVoltage());

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(info.getReceiveDate());
            respData.setLastestDate(dateString);

            logger.info("Response json String:" + JSON.toJSONString(ResponseUtils.getNormalResp(respData)));
            return ResponseUtils.getNormalResp(respData);
        } catch (IllegalParamsException e) {
            logger.error(ExceptionUtils.getStackTrace(e));
            return ResponseUtils.getParamsErrorResp(e.getMessage());
        } catch (CrudException e) {
            logger.error("fetch battery info exception, " + req.getDeviceImei(), e);
            if (e instanceof FetchBtyInfoException) {
                return ResponseUtils.getServiceErrorResp(e.getMessage());
            } else {
                return ResponseUtils.getSysErrorResp();
            }
        } catch (Exception e) {
            logger.error("fetch battery info exception, " + req.getDeviceImei(), e);
            return ResponseUtils.getSysErrorResp();
        }
    }

//    private void validateBtyArgs(DeviceInfoReq btyInfoReq) throws IllegalParamsException {
//        if (!MobilePhoneUtils.isValidM2MPhone(btyInfoReq.getDeviceSimNo())) {
//            throw new IllegalParamsException("请输入正确的电池sim卡号");
//        }
//    }
    private void validateBtyArgs(DeviceInfoReq btyInfoReq) throws IllegalParamsException {
        if (StringUtils.isBlank(btyInfoReq.getDeviceImei())) {
            throw new IllegalParamsException("请输入IMEI");
        }
    }

//    private void sendReq(String simNo) {
//        Battery battery = batteryService.fetchBtyBySimNo(simNo);
//        if (battery == null) {
//            logger.error("电池不存在, " + simNo);
//            return;
//        }
//        boolean hasConn = false;
//        ChannelGroup channelGroup = SamBtyDataHandler.getChannels();
//        for (Channel c : channelGroup) {
//            String imei = (String) c.attr(AttributeKey.valueOf("IMEI")).get();
//            logger.info("已经连接的imei：" + imei);
//            if (imei != null && imei.equals(battery.getImei())) {
//                c.writeAndFlush("tellme" + imei + "\n");
//                hasConn = true;
//            }
//
//        }
//        if (!hasConn) {
//            logger.error("未获取到长连接, " + simNo);
//        }
//
//        // ConcurrentHashMap<String, Channel> map =
//        // SamBtyDataHandler.getChannelMap();
//        // Channel channel = map.get(battery.getImei());
//        // if (channel == null) {
//        // logger.error("未获取到长连接, " + simNo);
//        // }
//        //
//        // channel.writeAndFlush("tellme\r\n");
//    }
    private void sendReq(String Imei) {
        Battery battery = batteryService.fetchBtyByIMEI(Imei);
        if (battery == null) {
            logger.error("电池不存在, " + Imei);
            return;
        }
        boolean hasConn = false;
        ChannelGroup channelGroup = SamBtyDataHandler.getChannels();
        for (Channel c : channelGroup) {
            String imei = (String) c.attr(AttributeKey.valueOf("IMEI")).get();
            logger.info("已经连接的imei：" + imei);
            if (imei != null && imei.equals(battery.getImei())) {
                c.writeAndFlush("tellme" + imei + "\n");
                hasConn = true;
            }

        }
        if (!hasConn) {
            logger.error("未获取到长连接, " + Imei);
        }

        // ConcurrentHashMap<String, Channel> map =
        // SamBtyDataHandler.getChannelMap();
        // Channel channel = map.get(battery.getImei());
        // if (channel == null) {
        // logger.error("未获取到长连接, " + simNo);
        // }
        //
        // channel.writeAndFlush("tellme\r\n");
    }
}
