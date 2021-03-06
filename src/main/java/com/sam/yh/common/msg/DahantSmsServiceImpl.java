package com.sam.yh.common.msg;

import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ctc.smscloud.xml.webservice.utils.WebServiceXmlClientUtil;

@Service
public class DahantSmsServiceImpl implements DahantSmsService {

    private static final Logger logger = LoggerFactory.getLogger(DahantSmsServiceImpl.class);

    @Resource
    private String dhServerUrl;
    @Resource
    private String dhUserName;
    @Resource
    private String dhPassword;
    @Resource
    private Boolean smsEnable;
    @Resource
    private String apkShortUrl;

    @Override
    public boolean sendSignupAuthCode(String mobilePhone, String authCode) {
        // TODO
        String content = "您正在注册为亚亨蓄电池会员，注册验证码为" + authCode;
        return sendSms(mobilePhone, content);
    }

    @Override
    public boolean sendResetPwdAuthCode(String mobilePhone, String authCode) {
        // TODO
        String content = "您正在重置亚亨蓄电池会员密码，验证码为" + authCode;
        return sendSms(mobilePhone, content);
    }

    @Override
    public boolean sendTestSms(String mobilePhone, String content) {
        // TODO
        // String content = "您正在测试亚亨蓄电池短信验证码，验证码为" + authCode;
        return sendSms(mobilePhone, content);
    }

    @Override
    public boolean sendLogResellerSuccess(String mobilePhone, String initPwd) {
        // TODO
        String content = "恭喜您已经成为亚亨蓄电池经销商，初始密码为" + initPwd + "，请点击下载" + apkShortUrl;
        return sendSms(mobilePhone, content);
    }

    @Override
    public boolean sendLogResellerSuccess(String mobilePhone) {
        // TODO
        String content = "恭喜您已经成为亚亨蓄电池经销商，请登录APP查看。";
        return sendSms(mobilePhone, content);
    }

    @Override
    public boolean sendBuyInfo(String mobilePhone) {
        // TODO
        String content = "您购买的亚亨蓄电池已经成功录入系统，请您下载APP并跟踪，请点击下载" + apkShortUrl;
        return sendSms(mobilePhone, content);
    }

    @Override
    public boolean sendWarningMsg(String mobilePhone, String btyImei) {
        // TODO
        String content = "您的电池IMEI" + btyImei + "温度或电压出现异常，请登录APP查看。";
        return sendSms(mobilePhone, content);
    }

    @Override
    public boolean sendMovingMsg(String mobilePhone, String btyImei) {
        // TODO
        String content = "您的电池IMEI" + btyImei + "设置位置锁定后发生异常移动，请登录APP查看最新地点。";
        return sendSms(mobilePhone, content);
    }

    private boolean sendSms(final String mobilePhone, String content) {
        logger.info("send sms to " + mobilePhone + ", content:" + content);
        if (!smsEnable) {
            return true;
        }

        WebServiceXmlClientUtil.setServerUrl(dhServerUrl);
        String respInfo = WebServiceXmlClientUtil.sendSms(dhUserName, dhPassword, StringUtils.EMPTY, mobilePhone, content, StringUtils.EMPTY,
                StringUtils.EMPTY, StringUtils.EMPTY);

        final SmsSendResp resp = parseRespInfo(respInfo);
        if (resp == null || resp.getResult() != 0) {
            logger.error("resp:" + respInfo);
            return false;
        } else {
            Runnable runnable = new Runnable() {

                @Override
                public void run() {
                    try {
                        TimeUnit.SECONDS.sleep(30L);
                        getReport(mobilePhone, resp.getMsgid());
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
            };

            new Thread(runnable).start();
            return true;
        }
    }

    private SmsSendResp parseRespInfo(String respInfo) {
        SmsSendResp resp = null;
        try {
            JAXBContext context = JAXBContext.newInstance(SmsSendResp.class);
            Unmarshaller un = context.createUnmarshaller();
            StringReader sr = new StringReader(respInfo);
            resp = (SmsSendResp) un.unmarshal(sr);
        } catch (JAXBException e) {
            logger.error("failed parse xml String" + respInfo, e);
        }
        return resp;
    }

    private String getReport(String mobilePhone, String msgid) {
        WebServiceXmlClientUtil.setServerUrl(dhServerUrl);
        logger.info("send sms to " + mobilePhone + ", msgid:" + msgid);
        String respInfo = WebServiceXmlClientUtil.getReport(dhUserName, dhPassword, msgid, mobilePhone);
        logger.info(respInfo);

        return respInfo;
    }

    @Override
    public String getSms() {
        WebServiceXmlClientUtil.setServerUrl(dhServerUrl);
        logger.info("get user repaly sms");
        String respInfo = WebServiceXmlClientUtil.getSms(dhUserName, dhPassword);
        logger.info(respInfo);

        return respInfo;
    }

}
