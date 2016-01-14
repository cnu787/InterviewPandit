package com.testmyinterview.portal;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.testmyinterview.portal.bean.FeedBackBean;
import com.testmyinterview.portal.bean.InterViewBean;
import com.testmyinterview.portal.bean.PaymentBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.dao.AdminDAO;
import com.testmyinterview.portal.dao.DropDownDAO;
import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.dao.InternalDAO;
import com.testmyinterview.portal.dao.ProfileDAO;
import com.testmyinterview.portal.dao.SchedulerDAO;
import com.testmyinterview.portal.paging.PageNavigator;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class ExternalController {

	@Autowired
	private AdminDAO adminDAO;
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private DropDownDAO dropDownDAO;
	@Autowired
	private ExternalUserDao externalUserDao;
	@Value("${maxFileUploadMsg}")
	private String maxFileUploadMsg;
	@Autowired
	private SchedulerDAO schedulerDAO;
	@Autowired
	private InternalDAO internalDAO;
	@Autowired
	private TmiUtil tmiUtil;
	@Value("${SURL}")
	private String SURL;
	@Value("${FURL}")
	private String FURL;
	@Value("${CURL}")
	private String CURL;
	@Value("${KEY}")
	private String KEY;
	@Value("${SALT}")
	private String SALT;
	@Value("${paymentGateWayURl}")
	private String paymentGateWayURl;

	@Autowired
	private PageNavigator pageNavigator;

	private static final Logger logger = LoggerFactory
			.getLogger(ExternalController.class);

	/**
	 * This method is used to show Applicant home page
	 * 
	 * @param request
	 * @param model:profileStatus
	 * @return
	 */	
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/externalHome.do")
	public String externalHome(HttpServletRequest request, Model model) {
		String returnUrl = "redirect:/profileAboutMe.do";
		int userId = tmiUtil.getUserId();
		int profileStatus = profileDAO.getProfileStatus(userId);
		returnUrl = "redirect:/myCompleteProfile.do";
		model.addAttribute("profileStatus", profileStatus);
		return returnUrl;
	}
	/**
	 * This method is used to show interview Landing page
	 * 
	 * @param request
	 * @param model:interviewCount,profileStatus,interviewList,mocktestStatus
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/interviewLanding.do")
	public String interviewLanding(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean pb = new ProfileBean();
		pb.setEmailaddress(loggedUserName);
		pb.setUserId(profileDAO.getUserId(pb));
		int userId = profileDAO.getUserId(pb);
		model.addAttribute("interviewCount",
				externalUserDao.getInterviewCount(pb, "0"));
		model.addAttribute("profileStatus", profileDAO.getProfileStatus(userId));
		model.addAttribute("interviewList",
				externalUserDao.getInterViewDetails(userId));
		model.addAttribute("mocktestStatus", externalUserDao.getMocktestStatus(userId));

		return "interviewLand";
	}
	/**
	 * This method is used to show add interview skill page 
	 * 
	 * @param request
	 * @param model
	 * @return
	 */	
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/interviewMySkills")
	public String interviewMySkills(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		String editskillId = request.getParameter("skillId");
		String pagetype = request.getParameter("pagetype");
		String interviewsparam = request.getParameter("interviewsparam");
		InterViewBean ib = new InterViewBean();
		ib.setEmailaddress(loggedUserName);
		ib.setUserid(externalUserDao.getUserId(ib));
		model.addAttribute("industryId",
				profileDAO.getInterviewIndustryId(tmiUtil.getInterviewId()));
		model.addAttribute("interviewid", externalUserDao.getInterViewID(ib));
		model.addAttribute("skillratinglkp", dropDownDAO.getSkillsRating());
		model.addAttribute("years", dropDownDAO.getYears());
		model.addAttribute("monthsList", dropDownDAO.getExperienceMonthsList());
		model.addAttribute("skilltypeList", dropDownDAO.getSkilltypeList());
		model.addAttribute("interviewsparam", interviewsparam);
		model.addAttribute("interviewPageIdentifier", "interviewMySkills");
		if (editskillId != null && pagetype != null) {
			if (Integer.parseInt(pagetype) == 2) {
				model.addAttribute("updateskillList", externalUserDao
						.getInterViewSkillDetailsonId(editskillId));
				String edit = "EDIT";
				model.addAttribute("edit", edit);
			} else {
				model.addAttribute("updateskillList",
						profileDAO.getSkillDetailsonId(editskillId));
				String edit = "EDIT";
				model.addAttribute("edit", edit);
			}
		}
		return "myskill";
	}

	/**
	 * This method is used to add interview skills
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL })
	@RequestMapping(value = "/addSkillsToInterView")
	public String addSkills(HttpServletRequest request, Model model) {
		try {
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String loggedUserName = auth.getName();
			InterViewBean interviewBean = new InterViewBean();
			interviewBean.setEmailaddress(loggedUserName);
			interviewBean.setUserid(externalUserDao.getUserId(interviewBean));
			String edit = request.getParameter("edit");
			String Skillid = request.getParameter("Skillid");
			String interviewskillid = request.getParameter("interviewskillid");
			String interviewsparam = request.getParameter("interviewsparam") == null ? ""
					: request.getParameter("interviewsparam");
			interviewBean.setEmailaddress(loggedUserName);
			interviewBean.setInterviewid(Integer.parseInt(request
					.getParameter("interviewid")));
			interviewBean.setSkillName(Integer.parseInt(request
					.getParameter("skillname")));
			interviewBean.setSkillTypeId(Integer.parseInt(request
					.getParameter("skilltype")));
			interviewBean.setSkillRating(Integer.parseInt(request
					.getParameter("skillrating")));
			interviewBean.setYearsOfExperience(Integer.parseInt(request
					.getParameter("exprienceyear")));
			interviewBean.setMonthsOfExperience(Integer.parseInt(request
					.getParameter("expriencemonth")));
			interviewBean.setUserid(externalUserDao.getUserId(interviewBean));
			if (interviewBean.getSkillName() != -1
					&& interviewBean.getSkillTypeId() != -1
					&& interviewBean.getMonthsOfExperience() != -1) {
				if (!edit.isEmpty() && !interviewskillid.isEmpty()
						&& Skillid.isEmpty()) {
					interviewBean.setInterviewskillid(Integer
							.parseInt(interviewskillid));
					externalUserDao.updateInterViewSkills(interviewBean);
				} else if (!edit.isEmpty() && interviewskillid.isEmpty()
						&& !Skillid.isEmpty()) {
					interviewBean.setSkillid(Integer.parseInt(Skillid));
					externalUserDao.updateSkills(interviewBean);
				} else {
					externalUserDao.addInterViewSkills(interviewBean);
				}
			}
		} catch (Exception ex) {

		}
		return "redirect:/interviewMySkills";

	}
	/**
	 * This method is used to show interview Skills
	 * 
	 * @param request
	 * @param model:intterviewlist,externalUserDao
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL })
	@RequestMapping(value = "/interviewTechSkills")
	public String interviewerTechSkills(HttpServletRequest request, Model model) {
		InterViewBean ib = new InterViewBean();
		ib.setUserid(tmiUtil.getUserId());
		ib.setInterviewid(tmiUtil.getInterviewId());
		model.addAttribute("intterviewlist",
				externalUserDao.getInterviewSkills(ib));
		model.addAttribute("profilecount",
				externalUserDao.checkIndustryProfilecount(ib));
		return "interskill";
	}
	/**
	 * This method is used to show interview roles page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/interviewRole")
	public String interviewRole(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		InterViewBean ib = new InterViewBean();
		List interviewerRoleList = null;
		interviewerRoleList = dropDownDAO
				.getInterviewerRolelkprate();
		ProfileBean pb = new ProfileBean();
		pb.setEmailaddress(loggedUserName);
		pb.setUserId(profileDAO.getUserId(pb));
		ib.setEmailaddress(loggedUserName);
		ib.setUserid(pb.getUserId());
		int interviewid = externalUserDao.getInterViewID(ib);
		model.addAttribute("externalProfile",
				externalUserDao.getInterviewDetails(interviewid));
		model.addAttribute("resume", dropDownDAO.getResumeStatus(ib));
		model.addAttribute("career", dropDownDAO.getCareerStatus(pb));
		model.addAttribute("industryList", dropDownDAO.getAdminIndustryList());
		model.addAttribute("interviewrolesList",
				dropDownDAO.getInterviewerRolelkp());
		model.addAttribute("interviewlocationsList",
				dropDownDAO.getInterviewLocations());
		model.addAttribute("interviewtypesList",
				dropDownDAO.getInterviewTypes());
		model.addAttribute("companyNamesList", dropDownDAO.getCompanyName());
		model.addAttribute("rolelist",
				interviewerRoleList);
		return "interviewroles";
	}
	/**
	 * This method is used to show the applicant interview booking steps load in this method
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/interviewBooking.do")
	public String interviewBooking(HttpServletRequest request, Model model) {
		int interviewid = 0;
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		System.out.println("loggedUserName"+loggedUserName);
		ProfileBean pb = new ProfileBean();
		pb.setEmailaddress(loggedUserName);
		System.out.println("userid  "+profileDAO.getUserId(pb));
		pb.setUserId(profileDAO.getUserId(pb));
		int interviewcountId = externalUserDao.getInterviewCount(pb, "0");
		if (interviewcountId == 0) {
			interviewid = externalUserDao.addUsertInterview(pb);
		}
		return "interviewbooking";
	}
	/**
	 * This method is used to update the applicant interview booking location
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/interviewBookingLocation.do")
	public void interviewBookingLocation(HttpServletRequest request) {
		String interviewLocation=request.getParameter("myCurrentLocation");
		externalUserDao.updateInterviewBookingLocation(interviewLocation,tmiUtil.getInterviewId());
		
		
	}

	/**
	 * This method is used to delete interview skills based on input like interviewskillId
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL })
	@RequestMapping(value = "/interViewSkilldelete")
	public String skilldelete(HttpServletRequest request, Model model) {
		int interviewskillId = Integer.parseInt(request.getParameter(
				"interviewskillId").trim());
		int pagetype = Integer
				.parseInt(request.getParameter("pagetype").trim());
		if (pagetype == 2) {
			externalUserDao.deleteInterViewSkill(interviewskillId);
		}
		externalUserDao.deleteInterviewprofileId(interviewskillId, pagetype);
		return "redirect:/interviewTechSkills";
	}

	/**
	 * This method is used to update interview Skills to profile skills based on input like 
	 * skillspage
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL })
	@RequestMapping(value = "/updateinterviewSkills")
	public String updateinterviewSkills(HttpServletRequest request, Model model) {
		String Skillpage = request.getParameter("skillspage") == null ? ""
				: request.getParameter("skillspage");
		if (!Skillpage.isEmpty()) {
			Enumeration parameters = request.getParameterNames();
			Map skillidMap = new HashMap();
			Map pagetypeMap = new HashMap();
			Map updateskillsMap = new HashMap();
			while (parameters.hasMoreElements()) {
				String parameterName = parameters.nextElement().toString();
				if (parameterName.indexOf("skillid_") != -1) {
					skillidMap.put(parameterName.split("_")[1],
							request.getParameter(parameterName));
				}
				if (parameterName.indexOf("pagetype_") != -1) {
					pagetypeMap.put(parameterName.split("_")[1],
							request.getParameter(parameterName));
				}
				if (parameterName.indexOf("updateskills_") != -1) {
					updateskillsMap.put(parameterName.split("_")[1],
							request.getParameter(parameterName));
				}
			}
			int interviewid = tmiUtil.getInterviewId();
			int userid = tmiUtil.getUserId();
			int count = externalUserDao.updateIndustryToProfile(userid,
					interviewid);
			for (int i = 0; i < skillidMap.size(); i++) {
				String skillid = (String) skillidMap.get(Integer.toString(i));
				String pageId = (String) pagetypeMap.get(Integer.toString(i));
				String updateskillType = (String) updateskillsMap.get(Integer
						.toString(i));
				if (Integer.parseInt(pageId) == 2
						&& Integer.parseInt(updateskillType) == 0) {
					externalUserDao.updateskilltointerview(userid, skillid,
							interviewid);
				}
			}
		}
		return "redirect:/interviewTechSkills";
	}

	/**
	 * This method is used to add or update  interview details based on inputs
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL })
	@RequestMapping(value = "/addinterviewerdetails")
	public String addinterviewerdetails(HttpServletRequest request, Model model) {
		try {
			int otherDomain = 1;
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String loggedUserName = auth.getName();
			InterViewBean ib = new InterViewBean();
			ib.setEmailaddress(loggedUserName);
			ib.setUserid(externalUserDao.getUserId(ib));
			ib.setInterviewid(externalUserDao.getInterViewID(ib));
			ib.setIndustryid(Integer.parseInt(request.getParameter("industry")));

			String[] values = request.getParameterValues("domain");
			StringBuilder domain = new StringBuilder();
			for (String n : values) {
				if (domain.length() > 0)
					domain.append(',');
				if (n.equals("0")) {
					otherDomain = Integer.parseInt(n);
				}
				if (!n.equals("-1")) {
					domain.append("").append(n).append("");
				}

			}
			String updateResume = request.getParameter("update_resume") == null ? ""
					: request.getParameter("update_resume");
			String domains = domain.toString();
			ib.setDomainid(domains);
			ib.setIpLocation(request.getParameter("myCurrentLocation").trim());
			ib.setCareer(Integer.parseInt(request.getParameter("career")));
			ib.setPosition(request.getParameter("position"));
			ib.setResumeName(request.getParameter("uploadFile"));
			ib.setInterviewrole(Integer.parseInt(request
					.getParameter("interviewrole")));
			ib.setInterviewlocation(Integer.parseInt(request
					.getParameter("location")));
			ib.setInterviewmode(Integer.parseInt(request
					.getParameter("telephonic")));
			ib.setInterviewtype(Integer.parseInt(request
					.getParameter("interviewtype")));
			ib.setCompanyName(request.getParameter("companyname"));
			ib.setCompanyId(Integer.parseInt((request.getParameter("companyId") == null || request
					.getParameter("companyId").trim().isEmpty()) ? "-1"
					: request.getParameter("companyId")));

			int sum = externalUserDao.checkIndustry(ib);
			if (sum == 0) {
				externalUserDao.deleteInterviewskills(ib);
			}
			int count = externalUserDao.checkIndustryInProfile(ib);
			if (count == 1 && sum == 0) {
				externalUserDao.addProfileSkills(ib);
			}
			if (!updateResume.isEmpty()) {
				profileDAO.updateProfileResume(request, ib);
			}
			externalUserDao.updateInterView(request, ib, otherDomain);
		} catch (Exception e) {
			if (e instanceof org.springframework.web.multipart.MaxUploadSizeExceededException) {
				request.getSession().setAttribute("maxFileUploadMsg",
						maxFileUploadMsg);
			}
		}

		return "redirect:/interviewRole";
	}

	/**
	 * This method is used to show slot booking page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL })
	@RequestMapping(value = "/slotBooking")
	public String internalCalender(HttpServletRequest request, Model model) {
		schedulerDAO.updateLapsedSchedule();
		return "slotbooking";
	}

	/**
	 * This method is used to show evaluator slots details based on input like startDate
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/applicantSlotbooking")
	public String getApplicantSlotbooking(HttpServletRequest request,
			Model model) {
		int interviewId = tmiUtil.getInterviewId();
		schedulerDAO.updateLapsedSchedule();
		int defaultPageLimit = adminDAO.getDefaultPageLimit();
		String currGmtDate = tmiUtil.getCurrentGmtDate();
		InterViewBean ib = new InterViewBean();
		int role = externalUserDao.getRoleID(interviewId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdformat = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(currGmtDate));
			c2.setTime(sdformat.parse(currGmtDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(Calendar.DATE, 2); // number of days to add
		currGmtDate = sdformat.format(c.getTime());
		String applDate = request.getParameter("startDate") == null ? currGmtDate
				: request.getParameter("startDate");
		int pageNo = request.getParameter("pageNo") == null ? 1 : Integer
				.parseInt(request.getParameter("pageNo"));
		int pageLimit = request.getParameter("pageLimit") == null ? defaultPageLimit
				: Integer.parseInt(request.getParameter("pageLimit"));
		List appcntAvaiSlotsList = new ArrayList();
		try {

			c2.setTime(sdformat.parse(applDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String formatDate = sdf.format(c2.getTime());
		if(role<4){
		appcntAvaiSlotsList = schedulerDAO.getApplicantAvailabeSlots(
				schedulerDAO.getApplicantEvaluatorMatch(interviewId,role),
				tmiUtil.getUserId(), formatDate);
		}else{
			appcntAvaiSlotsList = schedulerDAO.getApplicantAvailabeSlots(
					schedulerDAO.getApplicantEvaluatorMatchForDir(interviewId,role),
					tmiUtil.getUserId(), formatDate);
		}
		//appcntAvaiSlotsList = schedulerDAO.getApplicantAvailabeSlots(interviewId,tmiUtil.getUserId(), formatDate);

		int aplSlotLstCount = appcntAvaiSlotsList.size();
		if (aplSlotLstCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("applicantAvailabeSlots",
					appcntAvaiSlotsList
							.subList(firstResult, (aplSlotLstCount > pageNo
									* pageLimit) ? firstResult + pageLimit
									: firstResult
											+ (aplSlotLstCount - (pageNo - 1)
													* pageLimit)));
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#",
					aplSlotLstCount, pageNo, pageLimit, 2));
		} else {
			model.addAttribute("nodata", "nodetails");
		}
		model.addAttribute("interviewId", interviewId);
		model.addAttribute("paginationList", adminDAO.getPaginationData());
		model.addAttribute("evaluatorscheduleid",
				schedulerDAO.getEvaluatorscheduleid(interviewId));
		model.addAttribute("aplSlotLstCount", aplSlotLstCount);
		model.addAttribute("pageLimit", pageLimit);
		model.addAttribute("applDate", applDate);
		model.addAttribute("formatDate", formatDate);
		return "applicantSlotbooking";
	}

	/**
	 * This method is used to show interview skills priority list
	 * 
	 * @param request
	 * @param model:skillNamesList
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL })
	@RequestMapping(value = "/skillspriority")
	public String skillspriority(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		InterViewBean ib = new InterViewBean();
		ib.setEmailaddress(loggedUserName);
		ib.setUserid(externalUserDao.getUserId(ib));
		int interviewid = externalUserDao.getInterViewID(ib);
		model.addAttribute("skillNamesList",
				externalUserDao.getSkillsName(interviewid));
		return "skillsprioritylist";
	}

	/**
	 * This method is used to showing interview booking success page
	 * 
	 * @param request
	 * @param model:status,bookedSlotDetails,skillsNameList,domainList
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL })
	@RequestMapping(value = "/interviewSuccess.do")
	public String interviewSuccess(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		String status = request.getParameter("status");
		InterViewBean ib = new InterViewBean();
		ib.setInterviewid(interviewId);
		model.addAttribute("status", status);
		model.addAttribute("bookedSlotDetails",
				externalUserDao.getBookedSlotDetailsOninterviewID(ib));
		model.addAttribute("skillsNameList",
				externalUserDao.getSkillsName(interviewId));
		model.addAttribute("domainList", externalUserDao.getDomainLst(ib));
		return "interviewsuccess";
	}
	/**
	 * This method is used to show interview feed back
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/interviewFeedback.do")
	public String interviewFeedback(HttpServletRequest request, Model model) {

		return "interviewFeedback";
	}

	/**
	 * This method is used to get Interview Summary with all payment details
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/interviewSummary")
	public String interviewSummary(HttpServletRequest request, Model model) {
		InterViewBean ib = new InterViewBean();
		ib.setInterviewid(tmiUtil.getInterviewId());
		int userId = tmiUtil.getUserId();
		List userDetails = externalUserDao
				.getBookingSlotDetailsOninterviewID(ib);
		model.addAttribute("userDetails", userDetails);
		model.addAttribute("skillsDetails",
				externalUserDao.getInterviewSkills(ib));
		model.addAttribute("domainList", externalUserDao.getDomainLst(ib));
		Float myWalletAmount = externalUserDao.getWalletTotal(userId);
		String finalAmount = "0";
		DecimalFormat df = new DecimalFormat("0.00");
		HashMap userDetMap = (HashMap) userDetails.get(0);
		String actualAmount = (String) userDetMap.get("amount");
		float sumByInterview = externalUserDao.getWalletSumByInterview(userId,
				(String) userDetMap.get("interviewtmiid"));
		if (sumByInterview != 0 || myWalletAmount > 0) {

			if (sumByInterview != 0) {
				float subFinalAmount = sumByInterview
						+ Float.parseFloat(actualAmount);
				if (subFinalAmount > 0) {
					finalAmount = Float.toString(subFinalAmount);
					if (myWalletAmount < subFinalAmount) {
						finalAmount = df
								.format(subFinalAmount - myWalletAmount);
						if (myWalletAmount != 0)
							externalUserDao.addWalletAmount(userId,
									"-" + df.format(myWalletAmount),
									(String) userDetMap.get("interviewtmiid"),
									0);
					} else {
						finalAmount = "0";
						if (subFinalAmount != 0)
							externalUserDao.addWalletAmount(userId, "-"
									+ subFinalAmount,
									(String) userDetMap.get("interviewtmiid"),
									0);
					}
				} else {
					finalAmount = "0";
					if (-subFinalAmount != 0)
						externalUserDao.addWalletAmount(userId,
								(Float.toString(-subFinalAmount)),
								(String) userDetMap.get("interviewtmiid"), 0);
				}
			} else {
				if (myWalletAmount < Float.parseFloat(actualAmount)) {
					finalAmount = df.format(Float.parseFloat(actualAmount)
							- myWalletAmount);
					if (myWalletAmount != 0)
						externalUserDao.addWalletAmount(userId,
								"-" + df.format(myWalletAmount),
								(String) userDetMap.get("interviewtmiid"), 0);
				} else {
					finalAmount = "0";
					if (Float.parseFloat(actualAmount) != 0)
						externalUserDao.addWalletAmount(userId, "-"
								+ actualAmount,
								(String) userDetMap.get("interviewtmiid"), 0);
				}
			}
		} else {
			finalAmount = actualAmount;
		}
		float myWalletAmountAct = externalUserDao.getWalletTotal(userId);
		model.addAttribute("myWalletAmountFloat", myWalletAmountAct);
		model.addAttribute("myWalletAmount", df.format(myWalletAmountAct));
		model.addAttribute("actualAmount", actualAmount);
		model.addAttribute("finalAmount", finalAmount);		
		try {			
			model.addAttribute("myToken", tmiUtil.md5Encode(tmiUtil.getEmailId()
					+ (String) userDetMap.get("interviewtmiid")));
		} catch (Exception e) {

		}
		PaymentBean pb = new PaymentBean();
		pb.setFirstName((String) userDetMap.get("firstname"));
		pb.setEmailId((String) userDetMap.get("emailid"));
		pb.setPhoneNumber((String) userDetMap.get("mobileno"));
		pb.setAmount(finalAmount);
		pb.setProductInfo((String) userDetMap.get("interviewtypename"));
		pb.setTxnId((String) userDetMap.get("interviewtmiid"));
		pb.setSalt(SALT);
		pb.setSurl(SURL);
		pb.setKey(KEY);
		pb.setCurl(CURL);
		pb.setActualAmount(actualAmount);	
		pb.setEvalAmount((String) userDetMap.get("evalamount"));
		externalUserDao.addInterviewAmount(ib.getInterviewid(),actualAmount,pb.getEvalAmount(),finalAmount);
		model.addAttribute("evalAmount", pb.getEvalAmount());
		model.addAttribute("surl", SURL);
		model.addAttribute("curl", CURL);
		model.addAttribute("furl", FURL);
		model.addAttribute("key", KEY);
		model.addAttribute("paymentGateWayURl", paymentGateWayURl);
		model.addAttribute("firstname", pb.getFirstName());
		model.addAttribute("phone", pb.getPhoneNumber());
		model.addAttribute("txnid", pb.getTxnId());
		model.addAttribute("productinfo", pb.getProductInfo());
		model.addAttribute("email", pb.getEmailId());
		model = tmiUtil.paymentDetails(model, pb);
		return "interviewSummary";
	}

	/**
	 * This method is used to get Interview Summary
	 * 
	 * @param request
	 * @param model:userDetails,externalUserDao,domainList
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL,
			Constants.ROLE_ADMIN, Constants.ROLE_CCR })
	@RequestMapping(value = "/myInterviewSummary")
	public String myInterviewSummary(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		InterViewBean ib = new InterViewBean();
		ib.setInterviewid(interviewId);
		model.addAttribute("userDetails",
				externalUserDao.getBookedSlotDetailsOninterviewID(ib));
		model.addAttribute("skillsDetails",
				externalUserDao.getInterviewSkills(ib));
		model.addAttribute("domainList", externalUserDao.getDomainLst(ib));
		return "myInterviewSummary";
	}

	/**
	 * This method is used to add priority of skills
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL })
	@RequestMapping(value = "/addskillpriorityList")
	public String addskillpriorityList(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		InterViewBean ib = new InterViewBean();
		ib.setEmailaddress(loggedUserName);
		ib.setUserid(externalUserDao.getUserId(ib));
		ib.setInterviewid(externalUserDao.getInterViewID(ib));
		Enumeration parameters = request.getParameterNames();
		Map ratingMap = new HashMap();
		Map skillsMap = new HashMap();
		Map pageMap = new HashMap();
		while (parameters.hasMoreElements()) {
			String parameterName = parameters.nextElement().toString();
			if (parameterName.indexOf("rating_") != -1) {
				ratingMap.put(parameterName.split("_")[1],
						request.getParameter(parameterName));
			}
			if (parameterName.indexOf("skills_") != -1) {
				skillsMap.put(parameterName.split("_")[1],
						request.getParameter(parameterName));
			}
			if (parameterName.indexOf("page_") != -1) {
				pageMap.put(parameterName.split("_")[1],
						request.getParameter(parameterName));
			}

		}
		externalUserDao.changeSkillPriorty(ib);
		for (int i = 0; i < ratingMap.size(); i++) {
			String ratingid = (String) ratingMap.get(Integer.toString(i));
			if (!ratingid.equals("-1")) {
				if ((String) skillsMap.get(Integer.toString(i)) != null) {
					String pageid = (String) pageMap.get(Integer.toString(i));
					String skillid = (String) skillsMap
							.get(Integer.toString(i));
					ib.setPageid(Integer.parseInt(pageid));
					ib.setSkillid(Integer.parseInt(skillid));
					ib.setSkillPriority(Integer.parseInt(ratingid));
					externalUserDao.updateSkillsPriority(ib);
				}
			}
		}

		return "redirect:/skillspriority";
	}

	/**
	 * This method is used to update Evaluator slot status
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateInterviewStatusOnTime/{interviewId}", method = RequestMethod.GET)
	@ResponseBody
	public Boolean updateInterviewStatusOnTime(@PathVariable int interviewId) {
		externalUserDao.updateInterviewStatusOnTime(interviewId);
		return true;
	}

	/**
	 * This method is used to show external landing page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_EXTERNAL" })
	@RequestMapping(value = "/externalLanding.do")
	public String externalLanding(HttpServletRequest request, Model model) {
		int userId = tmiUtil.getUserId();
		List interviewerRoleList = null;
		interviewerRoleList = dropDownDAO
				.getInterviewerRolelkprate();
		ProfileBean pb = new ProfileBean();
		pb.setUserId(userId);
		request.getSession().setAttribute("userLastSession",
				profileDAO.getUserLastSession(userId));
		List externalProfile = profileDAO.getUserDetails(pb);
		request.getSession().setAttribute("mySesProfile", externalProfile);
		model.addAttribute("mockTestStatus",
				externalUserDao.getMyMockTestStatus(userId));
		model.addAttribute("myprofile", externalProfile);
		model.addAttribute("interviewCount",
				externalUserDao.getInterviewCount(pb, "1"));
		model.addAttribute("interviewBookingCount",
				externalUserDao.getInterviewCount(pb, "1,2,3"));
		model.addAttribute("profileReportCount",
				externalUserDao.getProfileReport(userId));
		model.addAttribute("referralCount",
				externalUserDao.getReferralCount(pb));
		model.addAttribute("rolelist",
				interviewerRoleList);
		return "externallanding";
	}
	/**
	 * This method is used to show InterviewFeedBack based on input like 
	 * interviewId
	 * @param request
	 * @param model:interviewDetailsKnowledge
	 * @return
	 */
	@RequestMapping(value = "/myInterviewFeedBack")
	public String evaluatorSummary(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		int usertypeid = 2;
		int industryid = profileDAO.getInterviewIndustryId(interviewId);
		model.addAttribute("interviewDetailsKnowledge", internalDAO
				.getEvaluatorFeedBackDetails(interviewId,
						Constants.feebbcakApplicantId, usertypeid, industryid));
		return "myinterviewFeedback";
	}
	/**
	 * This method is used to show applicant feedback screen based input like interviewId
	 * @param request
	 * @param model:interviewDetailsKnowledge
	 * @return
	 */
	@RequestMapping(value = "/applicantFeebBackSummary")
	public String applicantFeebBackSummary(HttpServletRequest request,
			Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		int usertypeid = 1;
		String returnUrl = "applicantaddFeedback";
		model.addAttribute("interviewId", interviewId);
		int industryid = profileDAO.getInterviewIndustryId(interviewId);
		model.addAttribute("interviewDetailsKnowledge", internalDAO
				.getApplicantFeedBackDetails(interviewId,
						Constants.feebbcakApplicantId, usertypeid, industryid));
		int count = dropDownDAO.getFeedbackCount(usertypeid, interviewId);
		if (count > 1) {
			returnUrl = "redirect:/myInterviewFeedBackApplicant?interviewId="
					+ interviewId;
		}
		return returnUrl;
	}

	/**
	 * This method is used to show add feedback by applicant
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/applicantUpdateFeedback")
	public String applicantUpdateFeedback(HttpServletRequest request,
			Model model) {
		FeedBackBean fb = new FeedBackBean();
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		int feedBackId = request.getParameter("feedbackDone").isEmpty() ? 0
				: Integer.parseInt(request.getParameter("feedbackDone"));		
		fb.setFeedBackStatusId(feedBackId);
		int usertypeid = 1;
		fb.setUserType(usertypeid);
		if (feedBackId == 1) {
			externalUserDao.updateApplicantfeedbackstatus(interviewId);
		}
		fb.setInterViewId(interviewId);
		Enumeration parameters = request.getParameterNames();
		Map educationalIdMap = new HashMap();
		Map educationalRatingMap = new HashMap();
		Map educationalNotesMap = new HashMap();
		while (parameters.hasMoreElements()) {
			String parameterName = parameters.nextElement().toString();
			if (parameterName.indexOf("educationalId_") != -1) {
				educationalIdMap.put(parameterName.split("_")[1],
						request.getParameter(parameterName));
			}
			if (parameterName.indexOf("educationalRating_") != -1) {
				educationalRatingMap.put(parameterName.split("_")[1],
						request.getParameter(parameterName));
			}
			if (parameterName.indexOf("educationalNotes_") != -1) {
				educationalNotesMap.put(parameterName.split("_")[1],
						request.getParameter(parameterName));
			}
		}
		for (int i = 0; i < educationalRatingMap.size(); i++) {
			String ratingid = (String) educationalRatingMap.get(Integer
					.toString(i));
			String educationalId = (String) educationalIdMap.get(Integer
					.toString(i));
			String educationalNotes = (String) educationalNotesMap.get(Integer
					.toString(i));
			fb.setEvalRating(Integer.parseInt(ratingid));
			fb.setEvalId(Integer.parseInt(educationalId));
			fb.setEvalNote(educationalNotes);
			internalDAO.updateFeedBack(fb);
		}
		return "redirect:myInterviewFeedBackApplicant?interviewId="
				+ interviewId;
	}

	/**
	 * This method is used to show applicant feedback by applicant based on input like  interviewId
	 * 
	 * @param request
	 * @param model:interviewDetailsKnowledge
	 * @return
	 */
	@RequestMapping(value = "/myInterviewFeedBackApplicant")
	public String applicantSummary(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		int userId = tmiUtil.getUserId();
		int usertypeid = dropDownDAO.getUserType(userId);
		int industryid = profileDAO.getInterviewIndustryId(interviewId);
		model.addAttribute("interviewDetailsKnowledge", internalDAO
				.getApplicantFeedBackDetails(interviewId,
						Constants.feebbcakApplicantId, usertypeid, industryid));
		return "viewapplicantfeedback";
	}
	/**
	 * This method is used to show applicant wallet amount
	 * 
	 * @param request
	 * @param model:myWalletHistory
	 * @return
	 */
	@RequestMapping(value = "/myWallet.do")
	public String myWallet(HttpServletRequest request, Model model) {
		int userId = tmiUtil.getUserId();
		model.addAttribute("myWalletHistory",
				externalUserDao.getWalletHistory(userId));
		return "mywallet";
	}
}