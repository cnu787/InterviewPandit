package com.testmyinterview.portal;

import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testmyinterview.portal.bean.CustomTemplateBean;
import com.testmyinterview.portal.bean.InterViewBean;
import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.util.ASyncService;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class PaymentController {

	@Autowired
	private TmiUtil tmiUtil;
	@Autowired
	private ExternalUserDao externalUserDao;
	@Value("${support}")
	private String support;
	@Autowired
	private ASyncService asyncService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Value("${bookingConfmsg}")
	private String bookingConfmsg;
	@Value("${bookingConfmsgEval}")
	private String bookingConfmsgEval;
	@Value("${paymentSuccessMsg}")
	private String paymentSuccessMsg;

	@Value("${KEY}")
	private String KEY;
	@Value("${SALT}")
	private String SALT;
	@Value("${smsurl}")
	private String smsurl;
	/**
	 * This Method is used to show the payment Success 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paymentSuccess.do")
	public String paypalSuccess(HttpServletRequest request, Model model) {
		Enumeration parameters = request.getParameterNames();
		while (parameters.hasMoreElements()) {
			String parameterName = parameters.nextElement().toString();
			String params = request.getParameter(parameterName);
		}
		String myToken = request.getParameter("myToken") == null ? "" : request
				.getParameter("myToken");
		String firstName = request.getParameter("firstname");
		String amount = request.getParameter("amount");
		String phoneNumber = request.getParameter("phoneNumber");
		String emailId = request.getParameter("email");
		String status = request.getParameter("status") == null ? "" : request
				.getParameter("status");
		String interviewTMIId = request.getParameter("txnid");
		String productinfo = request.getParameter("productinfo");
		String mihpayid = request.getParameter("mihpayid");
		int interviewId = externalUserDao.getInterViewIDByTMI(interviewTMIId);
		String requestHash = request.getParameter("hash");
		String udf1 = request.getParameter("udf1");
		String udf2 = request.getParameter("udf2");
		String udf3 = request.getParameter("udf3");
		String udf4 = request.getParameter("udf4");
		String udf5 = request.getParameter("udf5");
		String strToRecalculateHash = SALT + "|" + status + "||||||" + udf5
				+ "|" + udf4 + "|" + udf3 + "|" + udf2 + "|" + udf1 + "|"
				+ emailId + "|" + firstName + "|" + productinfo + "|" + amount
				+ "|" + interviewTMIId + "|" + KEY;	
		
		
		try {
			
			String currentToken = tmiUtil.md5Encode(emailId + interviewTMIId);
			MessageDigest mda = MessageDigest.getInstance("SHA-512");
			byte[] digesta = mda.digest(strToRecalculateHash.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digesta.length; i++) {
				sb.append(Integer.toString((digesta[i] & 0xff) + 0x100, 16)
						.substring(1));
			}
			String recalculatedHash = sb.toString(); // byte to
			if (requestHash.equals(recalculatedHash)
					|| myToken.equals(currentToken)) {
				if (status.equals("success") || myToken.equals(currentToken)) {
					status = "success";
					externalUserDao.updateInterviewStatus(interviewTMIId);
					InterViewBean ib = new InterViewBean();
					ib.setInterviewid(interviewId);
					List<Map<String, String>> userDetails = externalUserDao
							.getBookedSlotDetailsOninterviewID(ib);
					Authentication auth = SecurityContextHolder.getContext()
							.getAuthentication();
					if (auth.getAuthorities()
							.contains(
									new SimpleGrantedAuthority(
											Constants.ROLE_EXTERNAL))) {
						asyncService.sendSMSToCustomers(smsurl, userDetails
								.get(0).get("mobileno"), paymentSuccessMsg);
					}
					asyncService.sendSMSToCustomers(smsurl, userDetails.get(0)
							.get("mobileno"), bookingConfmsg);

					CustomTemplateBean cb = new CustomTemplateBean();
					cb.setEmailAddress(emailId);

					cb.setSenderEmailId(support);
					cb.setTemplateName("interviewbookingconfirmation.vm");

					cb.setMailSubject(userDetails.get(0).get(
							"interviewtypename")
							+ " Interview Booking Confirmation");
					cb.setUserDetails(userDetails);
					cb.setSkillsDetails(externalUserDao.getInterviewSkills(ib));
					cb.setDomainList(externalUserDao.getDomainLst(ib));
					asyncService.sendEmail(cb, mailSender, velocityEngine);
					asyncService.sendSMSToCustomers(smsurl, userDetails.get(0)
							.get("evalmobile"), bookingConfmsgEval);

					CustomTemplateBean cbEval = new CustomTemplateBean();
					cbEval.setEmailAddress(userDetails.get(0)
							.get("evalemailid"));

					cbEval.setSenderEmailId(support);
					cbEval.setTemplateName("evalinterviewbookingconfirmation.vm");
					cbEval.setMailSubject(userDetails.get(0).get(
							"interviewtypename")
							+ " Interview Booking Confirmation");
					cbEval.setUserDetails(userDetails);
					cbEval.setSkillsDetails(externalUserDao
							.getInterviewSkills(ib));
					cbEval.setDomainList(externalUserDao.getDomainLst(ib));
					asyncService.sendEmail(cbEval, mailSender, velocityEngine);

				}
				externalUserDao.updatePaymentStatus(mihpayid, interviewId,
						amount, status);

			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		if (auth.getAuthorities().contains(
				new SimpleGrantedAuthority(Constants.ROLE_EXTERNAL))) {
			return "redirect:/interviewSuccess.do?interviewId=" + interviewId
					+ "&status=" + status;
		} else {
			return "redirect:/tmiadmininterviewSuccess.do?interviewId="
					+ interviewId + "&status=" + status;
		}

	}

	/**
	 * This method is used to  show payment failure
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/paymentFailure.do")
	public String paymentFailure(HttpServletRequest request, Model model) {
		String interviewTMIId = request.getParameter("txnid");
		String amount = request.getParameter("amount");
		String mihpayid = request.getParameter("mihpayid");
		String status = request.getParameter("status");
		int interviewId = externalUserDao.getInterViewIDByTMI(interviewTMIId);
		externalUserDao.updateInterviewStatusOnFailure(interviewTMIId);
		externalUserDao.updatePaymentStatus(mihpayid, interviewId, amount,
				status);
		return "redirect:/interviewSuccess.do?interviewId=" + interviewId
				+ "&status=" + status;
	}

	/**
	 * This method is used to  show payment Cancel
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/paymentCancel.do")
	public String paymentCancel(HttpServletRequest request, Model model) {
		String interviewTMIId = request.getParameter("txnid");
		String amount = request.getParameter("amount");
		String mihpayid = request.getParameter("mihpayid");
		String status = request.getParameter("status");
		int interviewId = externalUserDao.getInterViewIDByTMI(interviewTMIId);
		externalUserDao.updateInterviewStatusOnCancel(interviewTMIId);
		externalUserDao.updatePaymentStatus(mihpayid, interviewId, amount,
				status);
		return "redirect:/interviewSuccess.do?interviewId=" + interviewId
				+ "&status=" + status;
	}
}
