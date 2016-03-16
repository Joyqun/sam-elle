package com.sam.yh.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.ctc.smscloud.xml.webservice.utils.WebServiceXmlClientUtil;
import com.sam.yh.common.DistanceUtils;
import com.sam.yh.common.TempUtils;
import com.sam.yh.common.msg.SmsSendResp;
import com.sam.yh.crud.exception.CrudException;
import com.sam.yh.crud.exception.FetchBtyInfoException;
import com.sam.yh.dao.BatteryInfoMapper;
import com.sam.yh.dao.BatteryMapper;
import com.sam.yh.dao.UserMapper;
import com.sam.yh.enums.BatteryStatus;
import com.sam.yh.model.Battery;
import com.sam.yh.model.BatteryInfo;
import com.sam.yh.model.PubBatteryInfo;
//import com.sam.yh.model.LocalBasic;
import com.sam.yh.model.User;
import com.sam.yh.model.UserBattery;
import com.sam.yh.req.bean.BatteryInfoReq;
import com.sam.yh.req.bean.LatLonReq;
import com.sam.yh.req.bean.LbsInfoReq;
import com.sam.yh.resp.bean.BtyInfoRespVo;
import com.sam.yh.service.BatteryService;
import com.sam.yh.service.LocalBasicService;
import com.sam.yh.service.UserBatteryService;
import com.sam.yh.service.UserCodeService;

import io.netty.handler.codec.http.HttpResponse;

@Service
public class BatteryServiceImpl implements BatteryService {

	
    private static final Logger logger = LoggerFactory.getLogger(BatteryServiceImpl.class);
       
    @Resource
    private LocalBasicService localBasicService;

    @Resource
    private BatteryMapper batteryMapper;

    @Resource
    private BatteryInfoMapper batteryInfoMapper;

    @Resource
    private UserBatteryService userBatteryService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserCodeService userCodeService;

    @Resource
    private Long MoveDis;
    


    @Override
    public Battery uploadBatteryInfo(BatteryInfoReq batteryInfoReqVo) throws CrudException {
    	
        if (batteryInfoReqVo == null) {
            return null;
        }
        
        logger.info("Upload battery info request:22" + batteryInfoReqVo);
        Battery battery = fetchBtyByIMEI(batteryInfoReqVo.getImei());
        if (battery == null) {
            return null;
        }
        //get the longitude and latitude of the last time
        BatteryInfo lastinfo = fetchBtyInfo(batteryInfoReqVo.getImei());
        String lastlon;
        String lastlat;

        if(lastinfo == null){
          lastlon="0";
          lastlat="0";
        }else{
           lastlon=lastinfo.getLongitude();
           lastlat=lastinfo.getLatitude();	
        }

        logger.info("0lastlon0" + lastlon);
        logger.info("0lastlat0" + lastlat);

 
        LatLonReq latLonReq=new LatLonReq();
        BatteryInfo info = new BatteryInfo();
        info.setBatteryId(battery.getId());
        info.setTemperature(convertAdcToTemp(batteryInfoReqVo.getTemperature()));
        info.setVoltage(convertAdcToVo(batteryInfoReqVo.getVoltage()));
        info.setLockStatus(batteryInfoReqVo.getLockstatus()); 
        // TODO
        // info.setSampleDate(batteryInfoReqVo.getSampleDate());
        info.setSampleDate(new Date());
        BatteryStatus status = getBatteryStatus(batteryInfoReqVo);
        info.setStatus(status.getStatus());
        info.setReceiveDate(new Date());      
        if(((int)((double)Double.valueOf(batteryInfoReqVo.getLongitude()))==0) && ((int)((double)Double.valueOf(batteryInfoReqVo.getLongitude()))==0)){
        	latLonReq=localBasicService.uploadLbsInfo(batteryInfoReqVo.getMcc(),batteryInfoReqVo.getMnc(),batteryInfoReqVo.getLac(),batteryInfoReqVo.getCi());           
        	String lon = latLonReq.getLon().equals("") ? lastlon : latLonReq.getLon();
            String lat = latLonReq.getLat().equals("") ? lastlat : latLonReq.getLat();
            
            info.setLongitude(lon);
            info.setLatitude(lat); 
            String s="{\"isGps\":\"0\"}";
            info.setExtension(s);
        }else{
            String lon =  batteryInfoReqVo.getLongitude();
            String lat =  batteryInfoReqVo.getLatitude();
            info.setLongitude(lon);
            info.setLatitude(lat);  
            String s="{\"isGps\":\"1\"}";
            info.setExtension(s);
         }
        
        batteryInfoMapper.insert(info);

        if (BatteryStatus.T_ABNORMAL.getStatus().equals(status.getStatus()) || BatteryStatus.V_ABNORMAL.getStatus().equals(status.getStatus())) {
            logger.info("T_ABNORMAL or V_ABNORMAL" );
        	sendWarningMsg(battery);
            
 
        }

//        if (BatteryStatus.LOCKED.getStatus().equals(battery.getStatus())) {
//            long moveDis = (long) DistanceUtils.GetDistance(batteryInfoReqVo.getLongitude(), batteryInfoReqVo.getLatitude(), battery.getLockLongitude(),
//                    battery.getLockLatitude());
//            if (moveDis > MoveDis) {
//                sendMovingMsg(battery);
//            }
//        }

        return battery;
    }
    
    
    
   private String convertAdcToTemp(String adc) {
        return TempUtils.isWarning(adc) ? adc : TempUtils.getTemp(adc);
    }

    private String convertAdcToVo(String adc) {
        float tem = Float.valueOf(adc);
//        float vol = (float) ((int) ((tem / 10.73) * 10)) / 10;
        float vol = (float) (tem/1000000.0);
        return String.valueOf(vol);
    }

    private BatteryStatus getBatteryStatus(BatteryInfoReq batteryInfoReqVo) {
        BatteryStatus status = BatteryStatus.NORMAL;
        String adcTmp = batteryInfoReqVo.getTemperature();
		if(adcTmp==null)
			adcTmp="200";
        if (TempUtils.isWarning(adcTmp)) {
            status = BatteryStatus.T_ABNORMAL;
        }

        float adcVol = Float.valueOf(convertAdcToVo(batteryInfoReqVo.getVoltage()));
        if (adcVol < 10 || adcVol > 90) {
            status = BatteryStatus.V_ABNORMAL;
        }

        return status;
    }

    private void sendWarningMsg(Battery battery) throws CrudException {

        UserBattery userBattery = userBatteryService.fetchUserByBtyId(battery.getId());
        User user = userMapper.selectByPrimaryKey(userBattery.getUserId());
        userCodeService.sendWarningMsg(user.getUserAccount(), battery.getImei());
    }

    private void sendMovingMsg(Battery battery) throws CrudException {

        UserBattery userBattery = userBatteryService.fetchUserByBtyId(battery.getId());
        User user = userMapper.selectByPrimaryKey(userBattery.getUserId());
        userCodeService.sendMovingMsg(user.getUserAccount(), battery.getImei());
    }

    @Override
    public Battery addBattery(Battery battery) {
        batteryMapper.insertSelective(battery);
        return battery;
    }
   
    @Override
    public Battery fetchBtyByIMEI(String imei) {
        return batteryMapper.selectByIMEI(imei);
    }

    @Override
    public Battery fetchBtyByPubSn(String pubSn) {
        return batteryMapper.selectByPubSn(pubSn);
    }

    @Override
    public Battery fetchBtyBySimNo(String simNo) {
        return batteryMapper.selectBySimNo(simNo);
    }

    @Override
    public Battery fetchBtyBySN(String btySn) {
        return batteryMapper.selectBySn(btySn);
    }

    @Override
    public int countSoldBtys(int resellerId) {
        return batteryMapper.countByReseller(resellerId);
    }

    @Override
    public int countCityBtys(int cityId) {
        return batteryMapper.countByCity(cityId);
    }

//    @Override
//    public BatteryInfo fetchBtyInfo(String btySimNo) throws CrudException {
//        Battery battery = batteryMapper.selectBySimNo(btySimNo);
//        if (battery == null) {
//            throw new FetchBtyInfoException("电池不存在");
//        }
//
//        BatteryInfo info = batteryInfoMapper.selectByBtyId(battery.getId());
//
//        if (info == null) {
//            throw new FetchBtyInfoException("未接收到此电池发送的信息");
//        }
//
//        return info;
//    }
    @Override
    public BatteryInfo fetchBtyInfo(String deviceImei) throws CrudException {
        Battery battery = batteryMapper.selectByIMEI(deviceImei);
        if (battery == null) {
            throw new FetchBtyInfoException("电池不存在");
        }

        BatteryInfo info = batteryInfoMapper.selectByBtyId(battery.getId());

//        if (info == null) {
//            throw new FetchBtyInfoException("未接收到此电池发送的信息");
//        }    
        return info;
    }

}
