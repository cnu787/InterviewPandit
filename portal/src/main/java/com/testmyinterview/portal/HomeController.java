package com.testmyinterview.portal;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.testmyinterview.portal.bean.CustomTemplateBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.dao.AdminDAO;
import com.testmyinterview.portal.dao.ProfileDAO;
import com.testmyinterview.portal.util.ASyncService;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.RandomNumberGenerator;
import com.testmyinterview.portal.util.TmiUtil;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private ASyncService asyncService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TmiUtil tmiUtil;
	@Value("${support}")
	private String supportEmail;
	@Value("${siteUrl}")
	private String siteUrl;
	@Value("${resetPasswordSuccessMsg}")
	private String resetPasswordSuccessMsg;
	@Value("${resetPasswordErrMsg}")
	private String resetPasswordErrMsg;
	@Value("${blogsUrl}")
	private String blogsUrl;
	@Value("${emailConfMsg}")
	private String emailConfMsg;
	@Value("${resetPasswordMsg}")
	private String resetPasswordMsg;
	@Value("${smsurl}")
	private String smsurl;
	@Autowired
	private AdminDAO adminDAO;

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);

	/**
	 * This method is used to show home page of interview landing page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/home.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {		
	    
		String url = blogsUrl + "latestPosts.php";
		try {
			model.addAttribute("blogData", tmiUtil.sendGet(url));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "home";
	}
	/**
	 * This method is used to show sessionTimeout page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sessionTimeout.do")
	public String sessionTimeout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "timeout";
	}

	/**
	 * This method is to display access denied page if the user is not accessed
	 * to do some action
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/accessDenied.do")
	public String accessDeniedView(HttpServletRequest request) throws Exception {
		return "accessDeniedPage";
	}

	/**
	 * this method is to display the error message when exception is caught
	 * 
	 *
	 * @return
	 */
	@RequestMapping(value = "/error.do", method = RequestMethod.GET)
	public String error(Model model) {
		return "error";
	}

	/**
	 * This method is to display the login page
	 * 
	  * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login.do")
	public String loginView(HttpServletRequest request, Model model) {		
		String type = (request.getParameter("type") == null) ? "" : request
				.getParameter("type").trim();
		if (request.getSession().getAttribute("resetPasswordSuccessMsg") != null) {
			model.addAttribute("resetPasswordSuccessMsg", request.getSession()
					.getAttribute("resetPasswordSuccessMsg"));
			request.getSession().removeAttribute("resetPasswordSuccessMsg");
		}
		if (request.getSession().getAttribute("loginmessage") != null) {
			model.addAttribute("loginmessage", request.getSession()
					.getAttribute("loginmessage"));
			request.getSession().removeAttribute("loginmessage");
		}
		if (!(type.isEmpty())) {
			String emailId=profileDAO.updateEmailVerification(type);
			if(!emailId.isEmpty()){
			String firstName = "";
			try {
				firstName = profileDAO.getFirstNameByEmailId(emailId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			asyncService.sendSMSToCustomers(smsurl,
					profileDAO.getMobileNoByEmailId(emailId), emailConfMsg);
			CustomTemplateBean customTemplate2 = new CustomTemplateBean();
			customTemplate2.setSenderEmailId(supportEmail);
			customTemplate2.setTemplateName("emailverificationsuccess.vm");
			customTemplate2
					.setMailSubject("Interview Pandit - Thank you for the verification");
			customTemplate2.setEmailAddress(emailId);
			customTemplate2.setFirstName(firstName);
			customTemplate2.setLoginUrl("login.do");
			asyncService.sendEmail(customTemplate2, mailSender, velocityEngine);
			}

		}
		model.addAttribute("authType", Constants.ROLE_EXTERNAL);
		return "login";
	}

	/**
	 * This method is to display the evaluator login page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/evalLogin.do")
	public String evalLogin(HttpServletRequest request, Model model) {		
		String type = (request.getParameter("type") == null) ? "" : request
				.getParameter("type").trim();
		
		System.out.println(request.getSession().getAttribute("resetPasswordSuccessMsg"));
		System.out.println(request.getSession().getAttribute("loginmessage"));
		if (request.getSession().getAttribute("resetPasswordSuccessMsg") != null) {
			model.addAttribute("resetPasswordSuccessMsg", request.getSession()
					.getAttribute("resetPasswordSuccessMsg"));
			request.getSession().removeAttribute("resetPasswordSuccessMsg");
		}
		if (request.getSession().getAttribute("loginmessage") != null) {
			model.addAttribute("loginmessage", request.getSession()
					.getAttribute("loginmessage"));
			request.getSession().removeAttribute("loginmessage");
		}
		if (!(type.isEmpty())) {
			
			String emailId=profileDAO.updateEmailVerification(type);
			if(!emailId.isEmpty()){
			String firstName = "";
			try {
				firstName = profileDAO.getFirstNameByEmailId(emailId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			asyncService.sendSMSToCustomers(smsurl,
					profileDAO.getMobileNoByEmailId(emailId), emailConfMsg);
			CustomTemplateBean customTemplate2 = new CustomTemplateBean();
			customTemplate2.setSenderEmailId(supportEmail);
			customTemplate2.setTemplateName("emailverificationsuccess.vm");
			customTemplate2
					.setMailSubject("Interview Pandit - Thank you for the verification");
			customTemplate2.setEmailAddress(emailId);
			customTemplate2.setFirstName(firstName);
			customTemplate2.setLoginUrl("evalLogin.do");
			asyncService.sendEmail(customTemplate2, mailSender, velocityEngine);
			}

		}
		model.addAttribute("authType", Constants.ROLE_INTERNAL);
		return "login";
	}

	/**
	 * This method is used to redirect the users after login based upon there
	 * ROLE
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL", "ROLE_ADMIN", "ROLE_EXTERNAL",
			"ROLE_CCR","ROLE_EVAL_EVAL" })
	@RequestMapping(value = "/myAuthenticationSuccessHandler.do")
	public String myAuthenticationSuccessHandler(HttpServletRequest request,
			Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();		
		try {			
			request.getSession().removeAttribute("myTmiTestId");
			request.getSession().setAttribute("myTmiTestId",
					tmiUtil.md5Encode(loggedUserName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		if (auth.getAuthorities().contains(
				new SimpleGrantedAuthority(Constants.ROLE_INTERNAL))) {
			int userId = profileDAO.getUserId(profileBean);
			profileDAO.addUserLog(userId);
			model.addAttribute("profileStatus",
					profileDAO.getProfileStatus(tmiUtil.getUserId()));
			return "redirect:/internalLanding.do";
		}
		if (auth.getAuthorities().contains(
				new SimpleGrantedAuthority(Constants.ROLE_EXTERNAL))) {
			int userId = profileDAO.getUserId(profileBean);
			profileDAO.addUserLog(userId);
			return "redirect:/externalLanding.do";
		}
		if (auth.getAuthorities().contains(
				new SimpleGrantedAuthority(Constants.ROLE_ADMIN))) {
			return "redirect:/tmiAdmAdminLanding.do";
		}		
		if (auth.getAuthorities().contains(
				new SimpleGrantedAuthority(Constants.ROLE_CCR))) {
			return "redirect:/tmiAdmCallCenterRepresentativeLanding.do";
		}		
		if (auth.getAuthorities().contains(
				new SimpleGrantedAuthority(Constants.ROLE_EVAL_EVAL))) {
			return "redirect:/tmiAdmEvaluatorsLanding.do";
		}
		return "home";
	}

	/**
	 * This method is used to forgot password
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/forgotPassword.do")
	public String forgotPassword(HttpServletRequest request, Model model) {
		if (request.getSession().getAttribute("resetPasswordErrMsg") != null) {
			model.addAttribute("resetPasswordErrMsg", request.getSession()
					.getAttribute("resetPasswordErrMsg"));
			request.getSession().removeAttribute("resetPasswordErrMsg");
		}
		return "forgotpassword";
	}
	/**
	 * This method is used to reset your  password based on input like  emailid,authType
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetPassword.do")
	public String resetPassword(HttpServletRequest request, Model model)
			throws Exception {
		String emailId = request.getParameter("emailid") == null ? "" : request
				.getParameter("emailid").trim().toLowerCase();
		String authType = request.getParameter("authType");

		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(emailId);
		int emailCount = profileDAO.getEmailcount(profileBean);
		if (!emailId.equals(null) && !emailId.isEmpty()) {
			if (emailCount > 0) {
				authType = profileDAO.getUserAuthority(emailId);
				RandomNumberGenerator randomNumber = new RandomNumberGenerator();
				String newPassword = "Tmi" + randomNumber.getText();
				profileDAO.resetPassword(emailId, newPassword);
				try {

					asyncService.sendSMSToCustomers(smsurl,
							profileDAO.getMobileNoByEmailId(emailId),
							resetPasswordMsg);
					CustomTemplateBean customTemplateBean = new CustomTemplateBean();
					customTemplateBean.setTemplateName("forgotPassword.vm");
					customTemplateBean.setSecurityCode(newPassword);
					customTemplateBean.setMailSubject("Reset Password");
					customTemplateBean.setFirstName(profileDAO
							.getFullNameByEmailId(emailId));
					customTemplateBean.setSenderEmailId(supportEmail);
					customTemplateBean.setEmailAddress(emailId);
					customTemplateBean.setSiteUrl(siteUrl);
					if (authType.equals(Constants.ROLE_INTERNAL)) {
						customTemplateBean.setLoginUrl("evalLogin.do");
					} else if (authType.equals(Constants.ROLE_EXTERNAL)) {
						customTemplateBean.setLoginUrl("login.do");
					}
					asyncService.sendEmail(customTemplateBean, mailSender,
							velocityEngine);
				} catch (MailSendException mse) {
					logger.error(mse.getMessage());
				}
				request.getSession().setAttribute("resetPasswordSuccessMsg",
						resetPasswordSuccessMsg);
				if (authType.equals(Constants.ROLE_INTERNAL)) {
					return "redirect:/evalLogin.do";
				} else if (authType.equals(Constants.ROLE_EXTERNAL)) {
					return "redirect:/login.do";
				}
			} else {
				request.getSession().setAttribute("resetPasswordErrMsg",
						resetPasswordErrMsg);
			}
		} else {
			request.getSession().setAttribute("resetPasswordErrMsg",
					resetPasswordErrMsg);
		}
		return "redirect:/forgotPassword.do";
	}
	/**
	 * This method is used to show abouttmi page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/abouttmi.do")
	public String abouttmi(HttpServletRequest request, Model model) {
		return "abouttmi";
	}
	/**
	 * This method is used to show demo page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/demo.do")
	public String demo(HttpServletRequest request, Model model) {
		return "demo";
	}
	/**
	 * This method is used to show blog page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/blog.do")
	public String blog(HttpServletRequest request, Model model) {
		return "blog";
	}
	/**
	 * This method is used to show faq page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/faq.do")
	public String faq(HttpServletRequest request, Model model) {
		return "faq";
	}
	/**
	 * This method is used to show pricing page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/pricing.do")
	public String pricing(HttpServletRequest request, Model model) {
		return "pricing";
	}
	/**
	 * This method is used to show copyright page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/copyright.do")
	public String copyright(HttpServletRequest request, Model model) {
		return "copyright";
	}
	/**
	 * This method is used to show terms and condition page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/termsandcondition")
	public String termsandcondition(HttpServletRequest request, Model model) {
		return "termsandcondition";
	}
	/**
	 * This method is used to show contact us page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/contactus.do")
	public String contactus(HttpServletRequest request, Model model) {
		return "contactus";
	}
	/**
	 * This method is used to show interviewSkills page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/interviewSkills.do")
	public String interviewSkills(HttpServletRequest request, Model model) {
		return "interviewSkills";
	}
	/**
	 * This method is used to show interviewModals page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/interviewModals.do")
	public String interviewModals(HttpServletRequest request, Model model) {
		return "interviewModals";
	}
	/**
	 * This method is used to show employerZone page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/employerZone.do")
	public String employerZone(HttpServletRequest request, Model model) {
		return "employerZone";
	}
}
