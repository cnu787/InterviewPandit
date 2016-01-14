package com.testmyinterview.portal.util;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.javamail.JavaMailSender;

import com.testmyinterview.portal.bean.CustomTemplateBean;
public interface ASyncService {
void sendEmail(CustomTemplateBean customTemplate,JavaMailSender mailSender,VelocityEngine velocityEngine);
void sendSMSToCustomers(String message, String mobileNo,String smsurl);
void sendGet(String url)throws Exception;
}
