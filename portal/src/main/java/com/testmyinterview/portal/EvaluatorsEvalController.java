package com.testmyinterview.portal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testmyinterview.portal.bean.AdminProfileBean;
import com.testmyinterview.portal.bean.CustomTemplateBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.dao.AdminDAO;
import com.testmyinterview.portal.dao.DropDownDAO;
import com.testmyinterview.portal.dao.ProfileDAO;
import com.testmyinterview.portal.paging.PageNavigator;
import com.testmyinterview.portal.util.ASyncService;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class EvaluatorsEvalController {

	private static final Logger logger = LoggerFactory
			.getLogger(AdminController.class);
	@Value("${support}")
	private String support;
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private DropDownDAO dropDownDAO;
	@Autowired
	private AdminDAO adminDAO;
	@Autowired
	private TmiUtil tmiUtil;
	@Autowired
	private PageNavigator pageNavigator;
	@Autowired
	private ASyncService asyncService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Value("${smsurl}")
	private String smsurl;
	@Value("${e-learnMsg}")
	private String elearnMsg;

	/**
	 * This method is to show evaluator evaluator home page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EVAL_EVAL","ROLE_ADMIN"})
	@RequestMapping(value = "/tmiAdmEvaluatorsLanding.do")
	public String adminHome(HttpServletRequest request, Model model) {
		AdminProfileBean apb = new AdminProfileBean();
		apb.setAdminId(tmiUtil.getAdminUserId());
		List adminProfile = adminDAO.getAdminDetails(apb);
		request.getSession().setAttribute("mySesProfile", adminProfile);
		model.addAttribute("allMembers", adminDAO.getAllevaluatorCount());
		model.addAttribute("newMembers", adminDAO.getNewevaluatorCount());
		model.addAttribute("mappedMembers", adminDAO.getMappedevaluatorCount());
		model.addAttribute("trainingMembers",
				adminDAO.getTrainingEvaluatorMembersCount());
		model.addAttribute("unsuitableMembers",
				adminDAO.getUnsuitableEvaluatorMembersCount());
		model.addAttribute("dashboardsearchtypelkp",
				dropDownDAO.getdashboardsearchtypelkp());
		return "evaluatormap";

	}

	/**
	 * This method is to show evaluator Map page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EVAL_EVAL","ROLE_ADMIN"})
	@RequestMapping(value = "/tmiAdmevaluatorMap.do")
	public String evaluatorMap(HttpServletRequest request, Model model) {
		model.addAttribute("allMembers", adminDAO.getAllevaluatorCount());
		model.addAttribute("newMembers", adminDAO.getNewevaluatorCount());
		model.addAttribute("mappedMembers", adminDAO.getMappedevaluatorCount());
		model.addAttribute("trainingMembers",
				adminDAO.getTrainingEvaluatorMembersCount());
		model.addAttribute("unsuitableMembers",
				adminDAO.getUnsuitableEvaluatorMembersCount());
		return "evaluatormap";
	}

	/**
	 * This method is to show evaluator CompleteProfile based on input
	 *  like userId
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EVAL_EVAL","ROLE_ADMIN"})
	@RequestMapping(value = "/evalCompleteProfile")
	public String evalCompleteProfile(HttpServletRequest request, Model model) {
		int userId = Integer.parseInt(request.getParameter("userId"));
		ProfileBean profileBean = new ProfileBean();
		profileBean.setUserId(userId);
		profileBean.setUserId(userId);
		List externalProfile = profileDAO.getUserDetails(profileBean);
		model.addAttribute("myprofile", externalProfile);
		model.addAttribute("educationList",
				profileDAO.getEducationDetails(profileBean));
		model.addAttribute("skillsList",
				profileDAO.getSkillDetails(profileBean));
		model.addAttribute("careerList",
				profileDAO.getCareerDetails(profileBean));
		model.addAttribute("UserPreference",
				profileDAO.getUserPreference(userId));
		model.addAttribute("domainList", profileDAO.getDomainNames(profileBean));
		model.addAttribute("userId", userId);
		int industryId = dropDownDAO.getIndustryId(userId);
		model.addAttribute("interviewerRoleList",
				dropDownDAO.getInterviewerRolelkpOnly(industryId));
		model.addAttribute("evalpreferences",
				adminDAO.getEvalApprovedPreferences(userId));
		return "evalCompleteProfile";
	}

	/**
	 * This method is to add evaluator TmiRoles and
	 * if he suitable for interview sending email evaluator
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EVAL_EVAL","ROLE_ADMIN"})
	@RequestMapping(value = "/addEvalTmiRoles")
	public String addEvalTmiRoles(HttpServletRequest request, Model model) {
		int trainingstatus = 0;
		int adminId = tmiUtil.getAdminUserId();
		int otherRole = 1;
		String role = null;
		int userId = Integer.parseInt(request.getParameter("userId"));
		ProfileBean pb = new ProfileBean();
		pb.setUserId(userId);
		int interviewsuitable = Integer.parseInt(request
				.getParameter("interviewsuitable"));
		if (interviewsuitable == 1) {
			trainingstatus = request.getParameter("trainingStatus") == null ? 1
					: Integer.parseInt(request.getParameter("trainingStatus"));
			String[] values = request.getParameterValues("roles");
			StringBuilder roles = new StringBuilder();
			for (String n : values) {
				if (roles.length() > 0)
					roles.append(',');
				if (n.equals("0")) {
					otherRole = Integer.parseInt(n);
				}
				if (!n.equals("-1")) {
					roles.append("").append(n).append("");
				}

			}

			role = roles.toString();
		}
		String comments = request.getParameter("comment");
		adminDAO.addEvaluatorDetails(adminId, interviewsuitable, role,
				comments, userId, trainingstatus);
		if (interviewsuitable == 1) {
			List<Map<String, String>> userDetails = profileDAO
					.getUserDetails(pb);
			asyncService.sendSMSToCustomers(smsurl,
					userDetails.get(0).get("mobileno"), elearnMsg);

			CustomTemplateBean cb = new CustomTemplateBean();
			cb.setEmailAddress(userDetails.get(0).get("emailid"));

			cb.setSenderEmailId(support);
			cb.setTemplateName("elearnconfirmation.vm");

			cb.setMailSubject("eLearning Confirmation");
			cb.setUserDetails(userDetails);
			asyncService.sendEmail(cb, mailSender, velocityEngine);

		}

		return "redirect:/tmiAdmevaluatorMap.do";
	}

	/**
	 * This method is to show evaluator details based on inputs like 
	 * selectType,enterName
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EVAL_EVAL","ROLE_ADMIN"})
	@RequestMapping(value = "/evalDetails")
	public String evalDetails(HttpServletRequest request, Model model) {
		int defaultPageLimit = adminDAO.getDefaultPageLimit();
		String evalstatus = request.getParameter("status");
		String selectType = request.getParameter("selectType");
		String enterName = request.getParameter("enterName");
		int pageNo = request.getParameter("pageNo") == null ? 1 : Integer
				.parseInt(request.getParameter("pageNo"));
		int pageLimit = request.getParameter("pageLimit") == null ? defaultPageLimit
				: Integer.parseInt(request.getParameter("pageLimit"));
		List evalSearchList = new ArrayList();
		if (evalstatus.equals("new")) {
			evalSearchList = adminDAO.getNewEvaluatorDetails();
		} else if (evalstatus.equals("mappedToTmi")) {
			evalSearchList = adminDAO.getMapeedToEvaluatorDetails();
		} else if (evalstatus.equals("underTraining")) {
			evalSearchList = adminDAO.getUnderTrainingEvaluatorDetails();
		} else if (evalstatus.equals("unsuitable")) {
			evalSearchList = adminDAO.getUnsuitableEvaluatorDetails();
		} else if (evalstatus.equals("all")) {
			evalSearchList = adminDAO.getAllEvaluatorDetails();
		} else if (evalstatus.isEmpty() && selectType != null
				&& enterName != null) {
			evalSearchList = adminDAO
					.getAllEvaluatorDetailsByEvalName(enterName);
		}
		int evalLstCount = evalSearchList.size();

		if (evalLstCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("evaluatorList", evalSearchList.subList(
					firstResult,
					(evalLstCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult
							+ (evalLstCount - (pageNo - 1) * pageLimit)));
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#",
					evalLstCount, pageNo, pageLimit, 2));
			model.addAttribute("evalstatus", evalstatus);
		} else {
			model.addAttribute("nodata", "nodetails");
			model.addAttribute("evalstatus", evalstatus);
		}
		model.addAttribute("paginationList", adminDAO.getPaginationData());
		model.addAttribute("evalLstCount", evalLstCount);
		model.addAttribute("pageLimit", pageLimit);
		model.addAttribute("evalstatus", evalstatus);
		model.addAttribute("selectType", selectType);
		return "evaluatordetails";
	}

	/**
	 * This method is to show evaluators details based on inputs like
	 * status
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EVAL_EVAL","ROLE_ADMIN"})
	@RequestMapping(value = "/evalDetailsOnNameEvalScreeen")
	public String evalDetailsOnNameEvalScreeen(HttpServletRequest request,
			Model model) {
		int defaultPageLimit = adminDAO.getDefaultPageLimit();
		String evalstatus = request.getParameter("status");
		int pageNo = request.getParameter("pageNo") == null ? 1 : Integer
				.parseInt(request.getParameter("pageNo"));
		int pageLimit = request.getParameter("pageLimit") == null ? defaultPageLimit
				: Integer.parseInt(request.getParameter("pageLimit"));

		List evalSearchList = new ArrayList();
		if (evalstatus.equals("new")) {
			evalSearchList = adminDAO.getNewEvaluatorDetails();
		} else if (evalstatus.equals("mappedToTmi")) {
			evalSearchList = adminDAO.getMapeedToEvaluatorDetails();
		} else if (evalstatus.equals("underTraining")) {
			evalSearchList = adminDAO.getUnderTrainingEvaluatorDetails();
		} else if (evalstatus.equals("unsuitable")) {
			evalSearchList = adminDAO.getUnsuitableEvaluatorDetails();
		} else if (evalstatus.equals("all")) {
			evalSearchList = adminDAO.getAllEvaluatorDetails();
		}
		int evalLstCount = evalSearchList.size();

		if (evalLstCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("evaluatorList", evalSearchList.subList(
					firstResult,
					(evalLstCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult
							+ (evalLstCount - (pageNo - 1) * pageLimit)));
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#",
					evalLstCount, pageNo, pageLimit, 2));

		} else {
			model.addAttribute("nodata", "nodetails");
		}
		model.addAttribute("paginationList", adminDAO.getPaginationData());
		model.addAttribute("evalLstCount", evalLstCount);
		model.addAttribute("pageLimit", pageLimit);
		model.addAttribute("evalstatus", evalstatus);
		return "evaluatordetails";
	}
}