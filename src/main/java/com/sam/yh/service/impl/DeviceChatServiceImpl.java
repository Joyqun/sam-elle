package com.sam.yh.service.impl;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sam.yh.model.Battery;
import com.sam.yh.service.BatteryService;
import com.sam.yh.service.DeviceChatService;
import com.sam.yh.service.socket.SamBtyDataHandler;

@Service
public class DeviceChatServiceImpl implements DeviceChatService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceChatServiceImpl.class);

    @Resource
    private BatteryService batteryService;

//    @Override
//    public boolean chat(String deviceSimNo, String chatType) {
//        Battery battery = batteryService.fetchBtyBySimNo(deviceSimNo);
//        if (battery == null) {
//            logger.error("数据库中设备不存在, 设备sim卡号:{}", deviceSimNo);
//            return false;
//        }
//        boolean hasConn = false;
//        ChannelGroup channelGroup = SamBtyDataHandler.getChannels();
//        for (Channel c : channelGroup) {
//            String imei = (String) c.attr(AttributeKey.valueOf("IMEI")).get();
//            logger.info("已经连接设备的imei：{}", imei);
//            if (imei != null && imei.equals(battery.getImei())) {
//                String msg = chatType + imei + "\n";
//                c.writeAndFlush(msg);
//                hasConn = true;
//            }
//
//        }
//        if (!hasConn) {
//            logger.error("未获取到长连接, 设备sim卡号:{}", deviceSimNo);
//        }
//
//        return hasConn;
//
//    }
    
    @Override
    public boolean chat(String deviceImei, String chatType) {
        Battery battery = batteryService.fetchBtyByIMEI(deviceImei);
        if (battery == null) {
            logger.error("数据库中设备不存在, 设备Imei卡号:{}", deviceImei);
            return false;
        }
        boolean hasConn = false;
        ChannelGroup channelGroup = SamBtyDataHandler.getChannels();
        for (Channel c : channelGroup) {
            String imei = (String) c.attr(AttributeKey.valueOf("IMEI")).get();
            logger.info("已经连接设备的imei：{}", imei);
            if (imei != null && imei.equals(battery.getImei())) {
                String msg = chatType + imei + "\n";
                c.writeAndFlush(msg);
                hasConn = true;
            }

        }
        if (!hasConn) {
            logger.error("未获取到长连接, 设备Imei卡号:{}", deviceImei);
        }

        return hasConn;

    }

}
