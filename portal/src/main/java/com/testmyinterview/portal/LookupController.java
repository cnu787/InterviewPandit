package com.testmyinterview.portal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.dao.AdminDAO;
import com.testmyinterview.portal.dao.DropDownDAO;
import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.dao.ProfileDAO;
import com.testmyinterview.portal.dao.SchedulerDAO;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class LookupController {

	private static final Logger logger = LoggerFactory
			.getLogger(InternalController.class);
	@Autowired
	private ExternalUserDao externalUserDao;
	@Autowired
	private DropDownDAO dropDownDAO;
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private TmiUtil tmiUtil;
	@Autowired
	private SchedulerDAO schedulerDAO;
	@Autowired
	private AdminDAO adminDAO;

	/**
	 * This method is used to get domain List based on input like 
	 * industryId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getDomainList")
	@ResponseBody
	public List internalHome(HttpServletRequest request, Model model) {
		int industryId = Integer.parseInt(request.getParameter("industryId"));
		List domainList = null;
		if (industryId != -1) {
			domainList = dropDownDAO.getDomainList(industryId);
		}
		return domainList;
	}

	/**
	 * This method is used to show the interviewer role List
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getInterviewerRole")
	@ResponseBody
	public List getInterviewerRole(HttpServletRequest request, Model model) {
		int industryId = Integer.parseInt(request.getParameter("industryId"));
		List interviewerRoleList = null;
		if (industryId != -1) {
			interviewerRoleList = dropDownDAO.getInterviewerRolelkp(industryId);
		}
		return interviewerRoleList;
	}

	/**
	 * This method is used to show the Skills List
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getSkillsList")
	@ResponseBody
	public List skillsHome(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		int skilltypeId = Integer.parseInt(request.getParameter("skilltypeId"));
		int industryId = Integer.parseInt(request.getParameter("industryId"));
		List skillList = null;
		if (skilltypeId != -1) {
			skillList = dropDownDAO.getSkillsList(skilltypeId, industryId);
		}
		return skillList;
	}

	/**
	 * This method is used to update Others SkillType and OthersskillName based on input like
	 * otherType,otherName
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getSkillotherTypes.do/{otherType}/{otherName}", method = RequestMethod.GET)
	@ResponseBody
	public int getSkillotherTypes(@PathVariable String otherType,
			@PathVariable String otherName) {
		int userId = tmiUtil.getUserId();
		profileDAO.addOtherCategoryTypes(otherType, otherName, userId);
		return userId;
	}

	/**
	 * This method is used to update Others SkillName based on input like
	 * otherName
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getSkillotherName.do/{otherName}", method = RequestMethod.GET)
	@ResponseBody
	public int getSkillotherName(@PathVariable String otherName) {
		int userId = tmiUtil.getUserId();
		profileDAO.addOtherCategoryName(otherName, userId);
		return userId;
	}

	/**
	 * This method is used to update Others SkillType and OthersSkillName For interview based on input like
	 * otherType,otherName
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getSkillotherTypesInterview.do/{otherType}/{otherName}", method = RequestMethod.GET)
	@ResponseBody
	public int getSkillotherTypesInterview(@PathVariable String otherType,
			@PathVariable String otherName) {
		int userId = tmiUtil.getUserId();
		externalUserDao.addOtherCategoryTypesInterview(otherType, otherName,
				userId);
		return userId;
	}

	/**
	 * This method is used to update Others Skills Name For interview based on input like
	 * otherName
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getSkillotherNameInterview.do/{otherName}", method = RequestMethod.GET)
	@ResponseBody
	public int getSkillotherNameInterview(@PathVariable String otherName) {
		int userId = tmiUtil.getUserId();
		externalUserDao.addOtherCategoryNameInteriview(otherName, userId);
		return userId;
	}

	/**
	 * This method is used to update others Industry , others Domain and others otherinterviewroles For
	 * interview based on inputs like otherIndustry,otherDomain,otherinterviewroles
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getIndustryotherTypesInterview.do/{otherIndustry}/{otherDomain}/{otherinterviewroles}", method = RequestMethod.GET)
	@ResponseBody
	public int getIndustryotherTypesInterview(
			@PathVariable String otherIndustry,
			@PathVariable String otherDomain,
			@PathVariable String otherinterviewroles) {
		int userId = tmiUtil.getUserId();
		externalUserDao.addIndustryotherInterview(otherIndustry, otherDomain,
				otherinterviewroles, userId);
		return userId;
	}

	/**
	 * This method is used update Others Domain based on input like
	 * otherDomain
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getotherDomainInterview.do/{otherDomain}", method = RequestMethod.GET)
	@ResponseBody
	public int getotherDomainInterview(@PathVariable String otherDomain) {
		int userId = tmiUtil.getUserId();
		externalUserDao.addDomainotherInterview(otherDomain, userId);
		return userId;
	}

	/**
	 * This method is used to update Others interview roles
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getotherInterview.do/{otherInterview}", method = RequestMethod.GET)
	@ResponseBody
	public int getotherInterview(@PathVariable String otherInterview) {
		int userId = tmiUtil.getUserId();
		externalUserDao.addinterviewotherInterview(otherInterview, userId);
		return userId;
	}

	/**
	 * This method is used to update others Industry and others  Domain  For
	 * profile based on input like otherIndustry,otherDomain
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getIndustryotherTypeProfile.do/{otherIndustry}/{otherDomain}", method = RequestMethod.GET)
	@ResponseBody
	public int getIndustryotherTypeProfile(@PathVariable String otherIndustry,
			@PathVariable String otherDomain) {

		int userId = tmiUtil.getUserId();
		externalUserDao.addIndustryotherProfile(otherIndustry, otherDomain,
				userId);
		return userId;
	}

	/**
	 * This method is used update Others  Domain  For
	 * profile based on input like otherDomain
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getotherDomainProfile.do/{otherDomain}", method = RequestMethod.GET)
	@ResponseBody
	public int getotherDomainProfile(@PathVariable String otherDomain) {
		int userId = tmiUtil.getUserId();
		externalUserDao.addDomainotherProfile(otherDomain, userId);
		return userId;
	}

	/**
	 * This method is used to get domain List other then Others based on input like 
	 * industryId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getDomainListNotOthers")
	@ResponseBody
	public List getDomainListNotOthers(HttpServletRequest request, Model model) {
		int industryId = Integer.parseInt(request.getParameter("industryId"));
		List domainList = null;
		if (industryId != -1) {
			domainList = dropDownDAO.getDomainListOnly(industryId);
		}
		return domainList;
	}

	/**
	 * This method is used interviewer role List Other then Others based on input like 
	 * industryId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getInterviewerRoleNotOthers")
	@ResponseBody
	public List getInterviewerRoleNotOthers(HttpServletRequest request,
			Model model) {
		int industryId = Integer.parseInt(request.getParameter("industryId"));
		List interviewerRoleList = null;
		if (industryId != -1) {
			interviewerRoleList = dropDownDAO
					.getInterviewerRolelkpOnly(industryId);
		}
		return interviewerRoleList;
	}
	/**
	 * This method is used interviewer role List Other then Others based on input like 
	 * industryId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getInterviewerRoleWithRateCard")
	@ResponseBody
	public List getInterviewerRoleWithRateCard(HttpServletRequest request,
			Model model) {
		int industryId = Integer.parseInt(request.getParameter("industryId"));
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		List interviewerRoleList = null;
		if (industryId != -1) {
			interviewerRoleList = dropDownDAO
					.getInterviewerRolelkpOnly(industryId,interviewId);
		}
		return interviewerRoleList;
	}
	/**
	 * This method is used to update Others company based on input like
	 * othercompanyName
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getotherCompaynName.do/{othercompanyName}", method = RequestMethod.GET)
	@ResponseBody
	public int getotherCompaynName(@PathVariable String othercompanyName) {
		int userId = tmiUtil.getUserId();
		externalUserDao.addOtherCompany(othercompanyName, userId);
		return userId;
	}

	/**
	 * This method is used to update Others company name based on input like
	 * othercompanyName
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getotherCompaynNameCareer.do/{othercompanyName}", method = RequestMethod.GET)
	@ResponseBody
	public int getotherCompaynNameCareer(@PathVariable String othercompanyName) {
		int userId = tmiUtil.getUserId();
		externalUserDao.addOtherCompanyCareer(othercompanyName, userId);
		return userId;
	}

	/**
	 * This method is used to get Skills List With others
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getSkillsListWithOthers")
	@ResponseBody
	public List getSkillsListWithOthers(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		int skilltypeId = Integer.parseInt(request.getParameter("skilltypeId"));
		int industryId = Integer.parseInt(request.getParameter("industryId"));
		List skillList = null;
		if (skilltypeId != -1) {
			skillList = dropDownDAO.getSkillsListWithothers(skilltypeId,
					industryId);
		}
		return skillList;
	}

	/**
	 * this method is used to auto suggest the Company name based on input like search
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/autoSuggestCompanyName.do")
	public @ResponseBody
	List autoSuggestCompanyName(HttpServletRequest request, Model model) {
		List companyNamesList = null;
		String text_search = request.getParameter("search").trim();
		if (!text_search.isEmpty()) {
			companyNamesList = dropDownDAO.getCompanyNames(text_search);
		}
		return companyNamesList;
	}

	/**
	 * this method is used to auto suggest the Degree Name based on input like search
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/autoSuggestDegreeName.do")
	public @ResponseBody
	List autoSuggestDegreeName(HttpServletRequest request, Model model) {
		List degreeNameList = null;
		String text_search = request.getParameter("search").trim();
		if (!text_search.isEmpty()) {
			degreeNameList = dropDownDAO.getDegreeNames(text_search);
		}
		return degreeNameList;
	}

	/**
	 * This method is used to auto suggest the University names based on input like search
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/autoSuggestUniversityNames.do")
	public @ResponseBody
	List autoSuggestuniversityNames(HttpServletRequest request, Model model)
			throws Exception {
		List universityNamesList = null;
		String text_search = request.getParameter("search");
		if (!text_search.isEmpty()) {
			universityNamesList = dropDownDAO.getUniversityNames(text_search);
		}
		return universityNamesList;
	}

	/**
	 * This method is used to update Interview Status  based on inputs like
	 * slotscheduleid,interviewStatus,comments,startTime,interviewid
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateinterviewStatus.do/{slotscheduleid}/{interviewStatus}/{comments}/{startTime}/{interviewid}", method = RequestMethod.GET)
	@ResponseBody
	public int updateinterviewStatus(@PathVariable String slotscheduleid,
			@PathVariable String interviewStatus, @PathVariable String comments,@PathVariable String startTime,@PathVariable String interviewid) {
		int userId = tmiUtil.getAdminUserId();
		schedulerDAO.changeSlotStatusByCCR(Integer.parseInt(interviewStatus),
				Integer.parseInt(slotscheduleid),Integer.parseInt(interviewid),startTime,comments,userId,Constants.adminUsertype);		
		adminDAO.checkaddauditTable(Integer.parseInt(slotscheduleid),Constants.AdminSlotStatus,Constants.AdminSlotStatus,userId) ;
		return userId;
	}

	/**
	 * this method is used to auto suggest the applicant MailId 
	 * 
	 * @param request
	 * @param model
	 */
	@RequestMapping(value = "/autoSuggestUserName.do")
	public @ResponseBody
	List autoSuggestUserName(HttpServletRequest request, Model model) {
		List applicantNamesList = null;
		String text_search = request.getParameter("search").trim();
		if (!text_search.isEmpty()) {
			applicantNamesList = dropDownDAO.getApplicantNames(text_search);
		}
		return applicantNamesList;
	}
	
	/**
	 * This method is used interviewer role List Other then Others based on input like 
	 * industryId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getInterviewerRoleWithRate")
	@ResponseBody
	public List getInterviewerRoleWithRate(HttpServletRequest request,
			Model model) {
		List interviewerRoleList = null;
			interviewerRoleList = dropDownDAO
					.getInterviewerRolelkprate();
		System.out.println("list size in lcc"+interviewerRoleList.size());
		model.addAttribute("rolelist",
				interviewerRoleList);
		return interviewerRoleList;
	}
}