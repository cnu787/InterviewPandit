package com.testmyinterview.portal;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.testmyinterview.portal.bean.CustomTemplateBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.dao.DropDownDAO;
import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.dao.ProfileDAO;
import com.testmyinterview.portal.util.ASyncService;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.RandomNumberGenerator;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class ProfileController {
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private DropDownDAO dropDownDAO;
	@Autowired
	private ExternalUserDao externalUserDao;
	@Autowired
	private ASyncService asyncService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	// below values are read from properties file
	@Value("${userEmailExistsMsg}")
	private String userEmailExistsMsg;
	@Value("${registrationSuccessMsg}")
	private String registrationSuccessMsg;
	@Value("${support}")
	private String supportEmail;
	@Value("${siteUrl}")
	private String siteUrl;
	@Value("${maxFileUploadMsg}")
	private String maxFileUploadMsg;
	@Autowired
	private TmiUtil tmiUtil;
	@Value("${regConfMsg}")
	private String regConfMsg;
	@Value("${smsurl}")
	private String smsurl;

	private static final Logger logger = LoggerFactory
			.getLogger(ProfileController.class);

	/**
	 * This Method is used to show the create applicant account 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/registerView.do")
	public String registerView(HttpServletRequest request, Model model) {
		model.addAttribute("usertypelkp", dropDownDAO.getUserlkpList());
		if (request.getSession().getAttribute("registrationSuccessMsg") != null) {
			model.addAttribute("registrationSuccessMsg", request.getSession()
					.getAttribute("registrationSuccessMsg"));
			request.getSession().removeAttribute("registrationSuccessMsg");
		}
		if (request.getSession().getAttribute("userEmailExistsMsg") != null) {
			model.addAttribute("userEmailExistsMsg", request.getSession()
					.getAttribute("userEmailExistsMsg"));
			request.getSession().removeAttribute("userEmailExistsMsg");
		}
		return "registeraccount";
	}

	/**
	 * This Method is used to create your account for users for both applicant and evaluator
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/register.do")
	public String register(HttpServletRequest request) {
		int referralId = request.getParameter("referralId").isEmpty() ? 0
				: Integer.parseInt(request.getParameter("referralId"));
		String firstName = request.getParameter("firstName").trim();
		String lastName = request.getParameter("lastName").trim();
		String chooseyourUsername = request.getParameter("chooseyourUsername")
				.trim();
		int chooseyourRole = Integer.parseInt(request
				.getParameter("chooseyourRole"));
		String timeZone = request.getParameter("localtimezone").trim();
		String emailaddress = request.getParameter("emailaddress").trim()
				.toLowerCase();
		String password = request.getParameter("password").trim();
		String registrationType = request.getParameter("registrationType")
				.trim();
		String phoneNumber = request.getParameter("phoneNumber").trim();
		String myCountryCode = request.getParameter("myCountryCode").trim();
		String authType = "";
		try {
			ProfileBean profileBean = new ProfileBean();
			profileBean.setReferralId(referralId);
			profileBean.setFirstName(firstName);
			profileBean.setLastName(lastName);
			profileBean.setScreenName(chooseyourUsername);
			profileBean.setChooseyourRole(chooseyourRole);
			profileBean.setEmailaddress(emailaddress);
			profileBean.setPassword(password);
			profileBean.setTimeZone(timeZone);
			profileBean.setPhoneNumber(phoneNumber);
			profileBean.setCountryCode(myCountryCode);
			int emailCount = profileDAO.getEmailcount(profileBean);
			if (emailCount == 0) {				
				RandomNumberGenerator randomNumber = new RandomNumberGenerator();				
				String securityCode = randomNumber.getText();
				String securityCodeMd5=tmiUtil.md5Encode(profileBean.getEmailaddress()+securityCode);
				int count = profileDAO.userRegister(profileBean,securityCodeMd5);
				request.getSession().setAttribute("registrationSuccessMsg",
						registrationSuccessMsg);
				CustomTemplateBean customTemplate2 = new CustomTemplateBean();
				customTemplate2.setSenderEmailId(supportEmail);
				customTemplate2.setTemplateName("greeting.vm");
				customTemplate2.setMailSubject("Greetings from Interview Pandit");
				customTemplate2.setEmailAddress(emailaddress);
				customTemplate2.setFirstName(firstName);				
				customTemplate2.setSecurityCode(securityCodeMd5);
				customTemplate2.setSiteUrl(siteUrl);
				if (profileBean.getChooseyourRole() == 1) {
					customTemplate2.setLoginUrl("login.do");
				} else {
					customTemplate2.setLoginUrl("evalLogin.do");
				}
				asyncService.sendEmail(customTemplate2, mailSender,
						velocityEngine);				
				asyncService
						.sendSMSToCustomers(smsurl, phoneNumber, regConfMsg);

			} else {
				authType = profileDAO.getUserAuthority(profileBean
						.getEmailaddress());
				if (authType.equals(Constants.ROLE_EXTERNAL)
						&& chooseyourRole != 1) {
					request.getSession()
							.setAttribute(
									"userEmailExistsMsg",
									"You have already registered as an Applicant with this email id. Pls use a diferent email id.");
				} else if (authType.equals(Constants.ROLE_INTERNAL)
						&& chooseyourRole == 1) {
					request.getSession()
							.setAttribute(
									"userEmailExistsMsg",
									"You have already registered as an Evaluator with this email id. Pls use a diferent email id.");
				} else {
					request.getSession().setAttribute("userEmailExistsMsg",
							userEmailExistsMsg);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (chooseyourRole == 1) {
			return "redirect:/registerView.do";
		} else {
			return "redirect:/empRegisterView.do";
		}

	}
	/**
	 * This method is used to update career status
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/careermap")
	public String careermap(HttpServletRequest request, Model model) {
		String career = request.getParameter("career");
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		profileBean.setUserId(userId);
		if (career != null) {
			profileBean.setCareer(Integer.parseInt(career));
			profileDAO.updateCareer(profileBean);
			model.addAttribute("careerId", profileDAO.getCareerId(profileBean));
		}
		model.addAttribute("usertypelkp", dropDownDAO.getUserlkpList());
		return "redirect:/career";
	}
	/**
	 * This method is used to show preferences of internal user
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/yourpreference")
	public String yourpreference(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		model.addAttribute("UserPreference",
				profileDAO.getUserPreference(userId));
		return "preference";
	}

	/**
	 * This method is used to show career status
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/career")
	public String career(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		model.addAttribute("careerId", profileDAO.getCareerId(profileBean));
		return "career";
	}

	/**
	 * This method is used to show the  study details
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/studyPage")
	public String studyPage(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		profileBean.setUserId(profileDAO.getUserId(profileBean));
		model.addAttribute("educationList",
				profileDAO.getEducationDetails(profileBean));
		return "study";
	}

	/**
	 * This method is used to show the  user profile details 
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/myCompleteProfile.do")
	public String myCompleteProfile(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		profileBean.setUserId(userId);
		List externalProfile = profileDAO.getUserDetails(profileBean);
		request.getSession().setAttribute("eLearningStatus",
				externalUserDao.getELearningStatus(userId));
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
		request.getSession().setAttribute("myProfileStatus",
				profileDAO.getProfileStatus(userId));
		return "mycompleteprofile";
	}

	/**
	 * This method is used to show the external,internal user personal details
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/profileAboutMe")
	public String profileAboutMe(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		if (request.getParameter("edit") != null) {
			model.addAttribute("edit", request.getParameter("edit").trim());
		}

		if (request.getSession().getAttribute("maxFileUploadMsg") != null) {
			model.addAttribute("maxFileUploadMsg", request.getSession()
					.getAttribute("maxFileUploadMsg"));
			request.getSession().removeAttribute("maxFileUploadMsg");
		}

		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		request.getSession().setAttribute("userLastSession",
				profileDAO.getUserLastSession(userId));
		profileBean.setUserId(userId);
		List externalProfile = profileDAO.getUserDetails(profileBean);
		model.addAttribute("externalProfile", externalProfile);
		model.addAttribute("industryList", dropDownDAO.getIndustryList());
		return "profileaboutme";
	}

	/**
	 * This method is to add  Profile Details
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/applicantProfile")
	public String applicantProfile(HttpServletRequest request, Model model) {
		int otherDomain = 1;
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setFirstName(request.getParameter("firstName").trim());
		profileBean.setLastName(request.getParameter("lastName").trim());
		profileBean.setTimeZone(request.getParameter("localtimezone").trim());
		profileBean.setScreenName(request.getParameter("screenname").trim());
		profileBean.setPhoneNumber(request.getParameter("phoneNumber").trim());
		profileBean
				.setCountryCode(request.getParameter("myCountryCode").trim());
		profileBean.setIndustryOther(request.getParameter("otherIndustry")
				.trim());
		profileBean.setDomainOther(request.getParameter("otherDomain").trim());
		profileBean.setIndustry(Integer.parseInt(request
				.getParameter("industry")));
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
		String domains = domain.toString();
		profileBean.setDomain(domains);
		profileBean.setEmailaddress(loggedUserName);
		profileBean.setUserId(profileDAO.getUserId(profileBean));
		profileDAO.checkCount(profileBean);
		try {
			profileDAO.updateProfile(request, profileBean, otherDomain);

		} catch (Exception e) {
			if (e instanceof org.springframework.web.multipart.MaxUploadSizeExceededException) {
				request.getSession().setAttribute("maxFileUploadMsg",
						maxFileUploadMsg);
				return "redirect:/profileAboutMe";
			}
		}
		List externalProfile = profileDAO.getUserDetails(profileBean);
		request.getSession().setAttribute("mySesProfile", externalProfile);
		return "redirect:/profileAboutMe";
	}

	/**
	 * This method is to show Study details
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/applicantStudyView")
	public String applicantStudyView(HttpServletRequest request, Model model) {
		model.addAttribute("degreeList", dropDownDAO.getDegreeList());
		model.addAttribute("yearsList", dropDownDAO.getGraduateYearList());
		model.addAttribute("monthsList", dropDownDAO.getGraduateMonthsList());
		model.addAttribute("universityList", dropDownDAO.getUniversityList());
		String studyId = request.getParameter("studyId");
		if (studyId != null) {
			List eduList = profileDAO.getEducationDetailsonId(studyId);
			model.addAttribute("updateeducationList", eduList);
			String edit = "EDIT";
			model.addAttribute("edit", edit);
		}
		return "applicantstudy";
	}

	/**
	 * This method is to add Study Details
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/applicantStudy")
	public String applicantStudy(HttpServletRequest request, Model model) {
		try {
			String edit = request.getParameter("edit");
			String educationid = request.getParameter("educationid");
			String previousdegreeid = request.getParameter("previousdegreeid");
			String previousuniversityid = request
					.getParameter("previousuniversityid");
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String loggedUserName = auth.getName();
			ProfileBean profileBean = new ProfileBean();
			profileBean.setEmailaddress(loggedUserName);
			profileBean.setSchool(request.getParameter("schoolName").trim());
			profileBean.setGraduatedYear(Integer.parseInt(request.getParameter(
					"graduateyears").trim()));
			profileBean.setGraduatedMonth(Integer.parseInt(request
					.getParameter("graduatemonth").trim()));
			profileBean
					.setDegreeName(request.getParameter("degreename").trim());
			profileBean.setFieldofStudy(request.getParameter("study").trim());
			profileBean.setUniversityName(request
					.getParameter("universityname").trim());
			profileBean.setUserId(profileDAO.getUserId(profileBean));
			if (!profileBean.getFieldofStudy().isEmpty()) {
				profileBean.setDegreeId(Integer.parseInt(request.getParameter(
						"degreeid").trim()));
				profileBean.setUniversityid(Integer.parseInt(request
						.getParameter("universityid").trim()));
				if (!edit.isEmpty() && !educationid.isEmpty()) {
					profileBean.setEducationid(Integer.parseInt(educationid));
					profileDAO.updateEducation(profileBean, previousdegreeid,
							previousuniversityid);
				} else {
					profileDAO.addEducation(profileBean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/applicantStudyView";
	}

	/**
	 * This method is to show delete Study 
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/applicantStudydelete")
	public String applicantStudydelete(HttpServletRequest request, Model model) {
		String studyId = request.getParameter("studyId").trim();
		String edit = request.getParameter("edit") == null ? "" : request
				.getParameter("edit").trim();
		profileDAO.deleteEducation(studyId, tmiUtil.getUserId());
		if (edit.equals("true")) {
			return "redirect:/studyPage.do?edit=true";
		} else {
			return "redirect:/studyPage";
		}
	}

	/**
	 * This method is used to show skill
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/skillsView")
	public String skillsView(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		profileBean.setUserId(profileDAO.getUserId(profileBean));
		model.addAttribute("skillsList",
				profileDAO.getSkillDetails(profileBean));
		List externalProfile = profileDAO.getUserDetails(profileBean);
		model.addAttribute("externalProfile", externalProfile);
		return "skillsview";
	}

	/**
	 * This method is used to delete skills based on input like
	 * skillId
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/skilldelete")
	public String skilldelete(HttpServletRequest request, Model model) {
		String skillId = request.getParameter("skillId").trim();
		profileDAO.deleteSkill(Integer.parseInt(skillId));
		String edit = request.getParameter("edit") == null ? "" : request
				.getParameter("edit").trim();
		if (edit.equals("true")) {
			return "redirect:/skillsView.do?edit=true";
		} else {
			return "redirect:/skillsView";
		}
	}

	/**
	 * This method is to show the add skills details page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/skillspage")
	public String skillspage(HttpServletRequest request, Model model) {

		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		profileBean.setUserId(userId);
		int careerstatus = dropDownDAO.getCareerStatus(profileBean);
		model.addAttribute("usertypelkp", dropDownDAO.getUserlkpList());
		model.addAttribute("industryId", profileDAO.getUserIndustryId(userId));
		model.addAttribute("skillratinglkp", dropDownDAO.getSkillsRating());
		model.addAttribute("years", dropDownDAO.getYears());
		model.addAttribute("monthsList", dropDownDAO.getExperienceMonthsList());
		model.addAttribute("skilltypeList",
				dropDownDAO.getSkilltypeListWithOthers());
		model.addAttribute("careerId", profileDAO.getCareerId(profileBean));
		String skillId = request.getParameter("skillId") == null ? "" : request
				.getParameter("skillId").trim();
		if (!skillId.isEmpty()) {
			List skillList = profileDAO.getSkillDetailsonId(skillId);
			model.addAttribute("updateskillList", skillList);
			String edit = "EDIT";
			model.addAttribute("edit", edit);
		}
		return "skills";
	}

	/**
	 * This method is to show the add company details page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/careerpage")
	public String careerpage(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		profileBean.setUserId(userId);
		int careerstatus = dropDownDAO.getCareerStatus(profileBean);
		model.addAttribute("monthsList", dropDownDAO.getGraduateMonthsList());
		model.addAttribute("yearsList", dropDownDAO.getGraduateYearList());
		model.addAttribute("companyNamesList", dropDownDAO.getCompanyName());
		String careerId = request.getParameter("careerId") == null ? ""
				: request.getParameter("careerId");
		if (!careerId.isEmpty()) {
			List carrerList = profileDAO.getCareerDetailsonId(careerId);
			model.addAttribute("updatecarrerList", carrerList);
			String edit = "EDIT";
			model.addAttribute("edit", edit);
		}
		return "careermap";

	}

	/**
	 * This method is used to add skills details
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/addSkills")
	public String addSkills(HttpServletRequest request, Model model) {
		try {
			String edit = request.getParameter("edit");
			String Skillid = request.getParameter("Skillid");
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String loggedUserName = auth.getName();
			ProfileBean profileBean = new ProfileBean();
			profileBean.setEmailaddress(loggedUserName);
			profileBean.setSkillName(Integer.parseInt(request
					.getParameter("skillname")));
			profileBean.setSkillTypeId(Integer.parseInt(request
					.getParameter("skilltype")));
			profileBean.setSkillRating(Integer.parseInt(request
					.getParameter("skillrating")));
			profileBean.setYearsOfExperience(Integer.parseInt(request
					.getParameter("exprienceyear")));
			profileBean.setMonthsOfExperience(Integer.parseInt(request
					.getParameter("expriencemonth")));
			profileBean.setSkillTypeOther(request
					.getParameter("otherSkillType").trim());
			profileBean.setSkillNameOther(request
					.getParameter("otherSkillName").trim());
			profileBean.setUserId(profileDAO.getUserId(profileBean));
			if (profileBean.getSkillName() != -1) {
				if (!edit.isEmpty() && !Skillid.isEmpty()) {
					profileBean.setSkillid(Integer.parseInt(Skillid));
					profileDAO.updateSkills(profileBean);
				} else {
					profileDAO.addSkills(profileBean);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "redirect:/skillspage";

	}

	/**
	 * This method is used to add work experience details
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/applicantCareer")
	public String applicantCareer(HttpServletRequest request, Model model) {
		try {
			String previousCompanyId = request
					.getParameter("previousCompanyId");
			String edit = request.getParameter("edit");
			String careerid = request.getParameter("careerid");
			String tilloption = request.getParameter("tilldate");
			Authentication auth = SecurityContextHolder.getContext()
					.getAuthentication();
			String loggedUserName = auth.getName();
			ProfileBean profileBean = new ProfileBean();
			profileBean.setEmailaddress(loggedUserName);
			profileBean.setCompanyName(request.getParameter("companyname"));
			profileBean.setPositionDesignation(request.getParameter("position")
					.trim());
			profileBean.setLocation(request.getParameter("location").trim());
			profileBean.setStartMonth(Integer.parseInt(request
					.getParameter("month")));
			profileBean.setStartYear(Integer.parseInt(request
					.getParameter("year")));
			if(tilloption==null){
				profileBean.setExpmonth(Integer.parseInt(request
						.getParameter("expmonth")));
				profileBean.setExpryear(Integer.parseInt(request
						.getParameter("expryear")));
				
			}else if(tilloption.equals("1")){
				String year1 = request.getParameter("expyear");
			//	profileBean.setGraduatedYear(Integer.parseInt(year1));
				profileBean.setExpmonth(Integer.parseInt(request
						.getParameter("exprmnth")));
				profileBean.setExpryear(Integer.parseInt(year1));
			}
			
			profileBean.setUserId(profileDAO.getUserId(profileBean));
			if (profileBean.getStartMonth() != -1
					&& profileBean.getStartYear() != -1) {
				profileBean.setCompanyId(Integer.parseInt(request.getParameter(
						"companyId").trim()));
				if (!careerid.isEmpty() && !edit.isEmpty()) {
					profileBean.setCareerId(Integer.parseInt(careerid));
					profileDAO.updateCareers(profileBean, previousCompanyId);
				} else {
					profileDAO.addCareer(profileBean);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/careermap";
	}

	/**
	 * This method is to display work experience details 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/careerView")
	public String carrerView(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		profileBean.setUserId(profileDAO.getUserId(profileBean));
		model.addAttribute("careerList",
				profileDAO.getCareerDetails(profileBean));
		return "careerview";
	}

	/**
	 * This method is used to delete work experience details input like careerId
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/careerdelete")
	public String careerdelete(HttpServletRequest request, Model model) {
		String careerId = request.getParameter("careerId");
		profileDAO
				.deletecareer(Integer.parseInt(careerId), tmiUtil.getUserId());
		String edit = request.getParameter("edit") == null ? "" : request
				.getParameter("edit").trim();
		if (edit.equals("true")) {
			return "redirect:/careerView.do?edit=true";
		} else {
			return "redirect:/careerView";
		}
	}

	/**
	 * This method is used to add evaluator Preferences 
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/addPrefence")
	public String addPrefence(HttpServletRequest request, Model model) {
		Enumeration parameters = request.getParameterNames();
		String interviewtype = "";
		String interviewmode = "";
		String interviewlocality = "";
		while (parameters.hasMoreElements()) {
			String parameterName = parameters.nextElement().toString();
			String[] values = request.getParameterValues(parameterName);
			StringBuilder sb = new StringBuilder();
			for (String n : values) {
				if (sb.length() > 0)
					sb.append(',');
				sb.append("").append(n).append("");
			}
			if (parameterName.equals("interviewtype[]")) {
				interviewtype = sb.toString();
			}
			if (parameterName.equals("interviewmode[]")) {
				interviewmode = sb.toString();
			}
			if (parameterName.equals("interviewlocality[]")) {
				interviewlocality = sb.toString();
			}
		}
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		profileDAO.addPreferences(userId, interviewtype, interviewmode,
				interviewlocality);
		return "redirect:/yourpreference";
	}

	/**
	 * This Method is used to show complete profile based on inputs like 
	 * userId
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/completeProfile")
	public String completeProfile(HttpServletRequest request, Model model) {
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
		request.getSession().setAttribute("myProfileStatus",
				profileDAO.getProfileStatus(userId));
		return "completeprofile";
	}

	/**
	 * This Method is used to show evaluator register page
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/empRegisterView.do")
	public String empRegisterView(HttpServletRequest request, Model model) {
		model.addAttribute("usertypelkp", dropDownDAO.getUserlkpList());
		if (request.getSession().getAttribute("registrationSuccessMsg") != null) {
			model.addAttribute("registrationSuccessMsg", request.getSession()
					.getAttribute("registrationSuccessMsg"));
			request.getSession().removeAttribute("registrationSuccessMsg");
		}
		if (request.getSession().getAttribute("userEmailExistsMsg") != null) {
			model.addAttribute("userEmailExistsMsg", request.getSession()
					.getAttribute("userEmailExistsMsg"));
			request.getSession().removeAttribute("userEmailExistsMsg");
		}
		return "empRegisteraccount";
	}
	/**
	 * This Method is show to refer friend screen 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */	
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/referfriend.do")
	public String referfriend(HttpServletRequest request, Model model) {
		model.addAttribute("usertypelkp", dropDownDAO.getUserlkpList());
		if (request.getSession().getAttribute("referrerSuccessMsg") != null) {
			model.addAttribute("referrerSuccessMsg", request.getSession()
					.getAttribute("referrerSuccessMsg"));
			request.getSession().removeAttribute("referrerSuccessMsg");
		}

		return "referfriend";
	}
	/**
	 * This Method is used to send request refer friend mail id based on inputs like
	 * chooseyourRole,emailid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/referfriendsubmit.do")
	public String referfriendsubmit(HttpServletRequest request, Model model) {
		String emailId = request.getParameter("emailid") == null ? "" : request
				.getParameter("emailid");
		int userType = request.getParameter("chooseyourRole") == null ? 0
				: Integer.parseInt(request.getParameter("chooseyourRole"));
		if (!emailId.equals("") && (userType != 0)) {
			ProfileBean pb = new ProfileBean();
			pb.setUserId(tmiUtil.getUserId());
			List<Map> userDetails = profileDAO.getUserDetails(pb);
			CustomTemplateBean cb = new CustomTemplateBean();
			cb.setEmailAddress(emailId);
			cb.setReferralId((Integer) userDetails.get(0).get("userid"));
			cb.setSenderEmailId(supportEmail);
			cb.setUserType(userType);
			cb.setTemplateName("referafriend.vm");
			cb.setSiteUrl(siteUrl);
			cb.setMailSubject("Invitation from "
					+ userDetails.get(0).get("firstname") + " "
					+ userDetails.get(0).get("lastname"));
			asyncService.sendEmail(cb, mailSender, velocityEngine);
			request.getSession().setAttribute("referrerSuccessMsg",
					"Invitation sent successfully.");
		}

		return "redirect:/referfriend.do";
	}
	/**
	 * This Method is show to change password Screen 
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/changePassword.do")
	public String changePassword(HttpServletRequest request, Model model) {
		if (request.getSession().getAttribute("passsuccessmsg") != null) {
			model.addAttribute("passsuccessmsg", request.getSession()
					.getAttribute("passsuccessmsg"));
			request.getSession().removeAttribute("passsuccessmsg");
		}
		if (request.getSession().getAttribute("passerrmsg") != null) {
			model.addAttribute("passerrmsg",
					request.getSession().getAttribute("passerrmsg"));
			request.getSession().removeAttribute("passerrmsg");
		}
		return "changepassword";
	}
	/**
	 * This Method is used to change password based on inputs like
	 * currentPassword,newPassword,confirmPassword
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@Secured({ Constants.ROLE_EXTERNAL, Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/changePasswordSubmit.do")
	public String changePasswordSubmit(HttpServletRequest request, Model model) {
		String currentPassword = request.getParameter("currentPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		int passwordCount = profileDAO.getPasswordCount(currentPassword,
				tmiUtil.getEmailId());
		if (passwordCount > 0) {
			if (newPassword.equals(confirmPassword)) {
				profileDAO
						.updatePassword(tmiUtil.getEmailId(), confirmPassword);
				request.getSession().setAttribute("passsuccessmsg",
						"Password updated successfully.");
			} else {
				request.getSession().setAttribute("passerrmsg",
						"Password do not match.");
			}
		} else {
			request.getSession().setAttribute("passerrmsg",
					"Your current password is incorrect.");
		}
		return "redirect:/changePassword.do";
	}

}