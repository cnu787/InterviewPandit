package com.testmyinterview.portal.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.testmyinterview.portal.bean.CustomTemplateBean;

/**
 * this class is used to asynacService like sendEmail,sendSMSToCustomers,SendGetURL
 *  
 *
 */
@Service
public class AsyncServiceImpl implements ASyncService {

	private static final Logger logger = LoggerFactory
			.getLogger(AsyncServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.testmyinterview.portal.util.ASyncService#sendEmail(com.testmyinterview.portal.bean.CustomTemplateBean, org.springframework.mail.javamail.JavaMailSender, org.apache.velocity.app.VelocityEngine)
	 */
	@Async
	public void sendEmail(CustomTemplateBean customTemplate,
			JavaMailSender mailSender, VelocityEngine velocityEngine) {
		try {
			postEmail(customTemplate, mailSender, velocityEngine);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * /** this method is to send email with velocity template
	 * 
	 * @param player
	 */
	@Async
	public void postEmail(final CustomTemplateBean customTemplate,
			JavaMailSender mailSender, final VelocityEngine velocityEngine) {	
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage) {
				MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
				if (customTemplate.getRecepients() != null) {
					try {
						message.setBcc(customTemplate.getRecepients());
					} catch (MessagingException e) {

						e.printStackTrace();
					}
				} else {
					try {
						message.setBcc(customTemplate.getEmailAddress());
					} catch (MessagingException e) {

						e.printStackTrace();
					}
				}

				try {
					message.setFrom(customTemplate.getSenderEmailId());
				} catch (MessagingException e) {

					e.printStackTrace();
				}
				try {
					message.setSubject(customTemplate.getMailSubject());
				} catch (MessagingException e) {

					e.printStackTrace();
				}
				Map model = new HashMap();
				model.put("customTemplate", customTemplate);
				String notificationMsg = VelocityEngineUtils
						.mergeTemplateIntoString(velocityEngine,
								"com/testmyinterview/portal/template/"
										+ customTemplate.getTemplateName(),
								"UTF-8", model);
				try {
					message.setText(notificationMsg, true);
				} catch (MessagingException e) {
					e.printStackTrace();
				}

			}
		};
		mailSender.send(preparator);
	}

	
	/* (non-Javadoc)
	 * @see com.testmyinterview.portal.util.ASyncService#sendSMSToCustomers(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Async
	public void sendSMSToCustomers(String smsurl, String mobileNo,
			String message) {
		try {
			if (mobileNo.length() > 1) {
				smsurl = smsurl.replace("$sendTo", mobileNo);
				message = URLEncoder.encode(message, "UTF-8");
				smsurl = smsurl.replace("$message", message);
				URL url = new URL(smsurl);

				HttpURLConnection con = (HttpURLConnection) url
						.openConnection();
				con.connect();
				InputStream in = con.getInputStream();
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see com.testmyinterview.portal.util.ASyncService#sendGet(java.lang.String)
	 */
	@Async
	public void sendGet(String url1) throws Exception{
		
		URL url = new URL(url1);
		HttpURLConnection con = (HttpURLConnection) url
				.openConnection();
		con.connect();
		InputStream in = con.getInputStream();
		
		
	}

}