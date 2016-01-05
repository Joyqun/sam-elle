package com.sam.yh.common.msg;

import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailSendServiceImpl implements EmailSendService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailSendServiceImpl.class);
	
	@Resource
    private String mailHost;
    @Resource
    private String mailSender;
    @Resource
    private String mailUserName;
    @Resource
    private String mailPassword;

	@Override
	public void sendEmail(String email, String authCode) {
		// TODO Auto-generated method stub
		sendEmailMsg(email, "您的注册验证码是：" + authCode);
	}
	
	private void sendEmailMsg(String email, String content) {
		logger.info("kaishi");
		JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        // 设定mail server
        senderImpl.setHost(mailHost);
        senderImpl.setUsername(mailUserName);
        senderImpl.setPassword(mailPassword);

        Properties prop = new Properties();
        // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.timeout", "25000");// milliseconds
        senderImpl.setJavaMailProperties(prop);
        // 建立邮件消息
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(email);
        mailMessage.setFrom(mailSender);
        mailMessage.setSubject("sam验证码");
        mailMessage.setText(content);

        // 发送邮件
        senderImpl.send(mailMessage);

        logger.info("successfully send mail");
	}

}
