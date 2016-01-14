package com.testmyinterview.portal;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.testmyinterview.portal.bean.AdminProfileBean;
import com.testmyinterview.portal.bean.CustomTemplateBean;
import com.testmyinterview.portal.bean.FeedBackBean;
import com.testmyinterview.portal.bean.InterViewBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.dao.AdminDAO;
import com.testmyinterview.portal.dao.DropDownDAO;
import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.dao.InternalDAO;
import com.testmyinterview.portal.dao.ProfileDAO;
import com.testmyinterview.portal.dao.SchedulerDAO;
import com.testmyinterview.portal.kookoo.CallStatusCodes;
import com.testmyinterview.portal.paging.PageNavigator;
import com.testmyinterview.portal.util.ASyncService;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.RandomNumberGenerator;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class AdminController {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DropDownDAO dropDownDAO;
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private SchedulerDAO schedulerDAO;
	@Autowired
	private ExternalUserDao externalUserDao;
	@Autowired
	private ASyncService asyncService;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Value("${userEmailExistsMsg}")
	private String userEmailExistsMsg;
	@Value("${siteUrl}")
	private String siteUrl;
	@Value("${support}")
	private String supportEmail;
	@Value("${testUrl}")
	private String testUrl;
	@Value("${registrationSuccessMsg}")
	private String registrationSuccessMsg;
	@Autowired
	private TmiUtil tmiUtil;
	@Autowired
	private AdminDAO adminDAO;
	@Autowired
	private InternalDAO internalDAO;
	@Autowired
	private PageNavigator pageNavigator;

	

	/**
	 * This method is used to display the admin  login page
	 * 
	 * @return
	 */	
	@RequestMapping(value = "/adminlogin.do")
	public String adminlogin(HttpServletRequest request, Model model) {

		return "adminlogin";
	}

	/**
	 * This method is to show Admin  home page
	 * 
	 * @param request :  if mySesProfile is not empty removing mysesProfile attribute 
	 * @param model : adding ongoing call data 
	 * @return 
	 */	
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmAdminLanding.do")
	public String adminHome(HttpServletRequest request, Model model) {
		if (request.getSession().getAttribute("mySesProfile") != null) {
			request.getSession().removeAttribute("mySesProfile");
		}
		AdminProfileBean apb = new AdminProfileBean();
		apb.setAdminId(tmiUtil.getAdminUserId());
		List adminProfile = adminDAO.getAdminDetails(apb);
		request.getSession().setAttribute("mySesProfile", adminProfile);
		model.addAttribute("appNoAnswerCount", profileDAO
				.getanswercount(Integer.toString(CallStatusCodes.APPLICANT_NA)));
		model.addAttribute("evalNoAnswerCount", profileDAO
				.getanswercount(Integer.toString(CallStatusCodes.EVALUATOR_NA)));
		model.addAttribute("successCount", profileDAO.getanswercount(Integer
				.toString(CallStatusCodes.SUCCESS)));
		model.addAttribute(
				"ongoingCount",
				profileDAO.getanswercount(CallStatusCodes.CALLING_APPLICANT
						+ "," + CallStatusCodes.CALLING_EVALUATOR));
		model.addAttribute("dashboardsearchtypelkp",
				dropDownDAO.getdashboardsearchtypelkp());
		return "adminDetails";
	}

	/**
	 * This method is used add bulk upload  interviews for a  group 
	 * 
	 * @param request : checking unregistered users or registered users 
	 * @param model   : adding  dropdown data show in page dropdownlist 
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmuploadEmails.do")
	public String tmiuploadEmails(HttpServletRequest request, Model model) {
		model.addAttribute("industryList", dropDownDAO.getAdminIndustryList());
		model.addAttribute("interviewrolesList",
				dropDownDAO.getInterviewerRolelkp());
		model.addAttribute("interviewlocationsList",
				dropDownDAO.getInterviewLocations());
		model.addAttribute("interviewtypesList",
				dropDownDAO.getInterviewTypes());
		model.addAttribute("timeSlotList", schedulerDAO.getTimeSlots());
		model.addAttribute("companyNamesList",
				dropDownDAO.getCompanyNameNotOthers());
		if (request.getSession().getAttribute("unregisteredMembersEval") != null) {
			model.addAttribute("unregisteredMembersEval", (List) request
					.getSession().getAttribute("unregisteredMembersEval"));
			request.getSession().removeAttribute("unregisteredMembersEval");
		}
		if (request.getSession().getAttribute("unregisteredMembersApp") != null) {
			model.addAttribute("unregisteredMembersApp", (List) request
					.getSession().getAttribute("unregisteredMembersApp"));
			request.getSession().removeAttribute("unregisteredMembersApp");
		}
		if (request.getSession().getAttribute("count") != null) {
			model.addAttribute("count",
					request.getSession().getAttribute("count"));
			request.getSession().removeAttribute("count");
		}
		return "bulkinterviews";
	}

	/**
	 * This method is to upload .csv or .xlsx files and read the content(emails) from it,
	 *      inserting  emails  for applicant and evaluator , updateSlot,addInterview, add interviewgrop    
	 * 
	 * @param request: get user entered data in bulk upload page 
	 *            
	 * @return :  bulk upload  interviews page 
	 */
	@RequestMapping(value = "/tmiAdmuploadFiles.do", method = RequestMethod.POST)
	public String uploadCompanyLogo(MultipartHttpServletRequest request) {

		int otherDomain = 1;
		MultipartFile file = request.getFile("BrowserHidden");
		String industry = request.getParameter("industry");
		String interviewtype = request.getParameter("interviewtype");
		String career = request.getParameter("career");
		String companyName = request.getParameter("companyname");
		int companyId = Integer
				.parseInt((request.getParameter("companyId") == null || request
						.getParameter("companyId").trim().isEmpty()) ? "-1"
						: request.getParameter("companyId"));
		String position = request.getParameter("position");
		String interviewrole = request.getParameter("interviewrole");
		String location = request.getParameter("location");
		String modeofInterview = request.getParameter("telephonic");
		String startDate = request.getParameter("startDate");
		String addStartHour = request.getParameter("addStartHour");
		String groupName = request.getParameter("groupName");
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
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatterToDB = new SimpleDateFormat("yyyy-MM-dd");
		Date firstdate = null;
		try {
			firstdate = formatter.parse(startDate);

		} catch (Exception e) {
			e.printStackTrace();
		}
		String strDate = formatterToDB.format(firstdate);
		ArrayList allUserList = new ArrayList();
		Map<String, String> responseMap = new HashMap<String, String>();
		try {
			allUserList = TmiUtil.readExcelFile(file.getInputStream());
			// TODO
			int slotCount = 0;
			int bulkCount = 0;
			String evalemailId = "";
			String interviewId = "";
			int evaluatorId = 0;
			int bulkGroupId = 0;
			int appmailcount = 0;
			String evalemail = "EvalEmail";
			ArrayList unregisteredeval = new ArrayList();
			ArrayList unregisteredeapp = new ArrayList();
			if (allUserList.size() > 0) {
				for (int i = 1; i < allUserList.size(); i++) {
					ArrayList userList = (ArrayList) allUserList.get(i);
					if (userList.size() > 0) {
						String emailId =((String) userList.get(0)).trim();
						if (userList.size() == 2) {
							evalemailId = ((String) userList.get(1)).trim();
							evaluatorId = profileDAO
									.getUserIdByEmailId(evalemailId);
							slotCount = 0;
						}
						if (evaluatorId > 0) {
							if (emailId.contains("@")) {
								int emailCount = profileDAO.getEmail(emailId);
								if (emailCount == 1) {
									if (bulkCount == 0) {
										bulkGroupId = adminDAO
												.checkBulkGroup(groupName);
									}
									if (bulkCount == 0 && bulkGroupId == 0) {
										bulkGroupId = adminDAO
												.addBulkGroup(groupName);
										bulkCount = 1;
									}
									int userId = adminDAO.getUserId(emailId);
									appmailcount = adminDAO
											.checkUserIdBulkGroup(bulkGroupId,
													userId);
									if (slotCount == 0) {
										if (appmailcount == 0) {
											interviewId = adminDAO.updateSlot(
													addStartHour, strDate,
													evaluatorId);
											slotCount = 1;
										}
									}
									if (appmailcount == 0) {
										adminDAO.addInterviewGroup(bulkGroupId,
												interviewId, userId);
										int count = adminDAO.addInterview(
												interviewId, emailId, industry,
												domains, position,
												interviewrole, location,
												modeofInterview, interviewtype,
												companyId, companyName, career);
										request.getSession().setAttribute(
												"count", count);
									}
								} else if (emailCount == 0) {
									if (!emailId.equals("Email")) {
										unregisteredeapp.add(emailId);
									}
								}
							}
						} else if (evaluatorId == 0) {
							if (!evalemailId.equals(evalemail)) {
								unregisteredeval.add(evalemailId);
								evalemail = evalemailId;
							}
							if (!emailId.equals("Email")) {
								unregisteredeapp.add(emailId);
							}
						}
					}
				}
			} 
			request.getSession().setAttribute("unregisteredMembersApp",
					unregisteredeapp);
			request.getSession().setAttribute("unregisteredMembersEval",
					unregisteredeval);
			request.getSession().setAttribute("errorMsg",
					responseMap.get("errorMsg"));
			request.getSession().setAttribute("userAddMsg",
					responseMap.get("userAddMsg"));
		} catch (org.springframework.web.multipart.MaxUploadSizeExceededException me) {
			request.getSession().setAttribute("maxFileUploadMsg",
					"maxFileUploadMsg");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMsg", "fileContentErrMsg");
		}
		return "redirect:/tmiAdmuploadEmails.do";

	}

	/**
	 * This method is to create user like CCR,Subadmin,Evaluators_Evaluator
	 * 
	 * @param request : success message or failure message for User registration 
	 * @param model :  adding success or failure message 
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmCreateAdminUser.do")
	public String createAdminUser(HttpServletRequest request, Model model) {
		model.addAttribute("userroleList", dropDownDAO.getAdminUserRolelkp());
		if (request.getSession()
				.getAttribute("adminUserRegistrationSuccessMsg") != null) {
			model.addAttribute(
					"adminUserRegistrationSuccessMsg",
					request.getSession().getAttribute(
							"adminUserRegistrationSuccessMsg"));
			request.getSession().removeAttribute(
					"adminUserRegistrationSuccessMsg");
		}
		if (request.getSession().getAttribute("userEmailExistsMsg") != null) {
			model.addAttribute("userEmailExistsMsg", request.getSession()
					.getAttribute("userEmailExistsMsg"));
			request.getSession().removeAttribute("userEmailExistsMsg");
		}
		return "createadminuser";
	}

	/**
	 * This method is used to created user  details inserting  into DB
	 *      and sending email to new users
	 * @param request : getting user  data like name,mobile,role,etc..  
	 * @param model :
	 * @return :create user page 
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmAddAdminUserDetails.do")
	public String addAdminUserDetails(HttpServletRequest request, Model model) {
		String firstName = request.getParameter("firstName").trim();
		String lastName = request.getParameter("lastName").trim();
		String phoneNumber = request.getParameter("phoneNumber").trim();
		int chooseyourRole = Integer.parseInt(request.getParameter("userRole")
				.trim());
		String emailaddress = request.getParameter("emailaddress").trim();
		String timezone = request.getParameter("localtimezone").trim();
		try {
			AdminProfileBean profileBean = new AdminProfileBean();
			profileBean.setFirstName(firstName);
			profileBean.setTimezone(timezone);
			profileBean.setLastName(lastName);
			profileBean.setChooseyourRole(chooseyourRole);
			profileBean.setEmailaddress(emailaddress);
			profileBean.setPhoneNumber(phoneNumber);
			int emailCount = profileDAO.getEmailcount(profileBean);
			if (emailCount == 0) {
				RandomNumberGenerator randomNumber = new RandomNumberGenerator();
				String securityCode = "Tmi"+randomNumber.getText();
				profileBean.setSecurityCode(securityCode);
				profileDAO.adminUserRegister(profileBean);
				int userId = adminDAO.getAdminUserId(emailaddress);
				adminDAO.checkaddauditTable(userId, Constants.AdminAddUser,Constants.AdminAddUser,
						tmiUtil.getAdminUserId());
				request.getSession().setAttribute(
						"adminUserRegistrationSuccessMsg",
						registrationSuccessMsg);
				 CustomTemplateBean customTemplate = new CustomTemplateBean();
				 customTemplate.setSenderEmailId(supportEmail);
				 customTemplate.setTemplateName("adminuserregistrationsuccess.vm");
				 customTemplate.setMailSubject(firstName+" Welcome to Interview Pandit");
				 customTemplate.setEmailAddress(emailaddress);
				 customTemplate.setFirstName(firstName);
				 customTemplate.setPassword(securityCode);
				 customTemplate.setPhoneNumber(phoneNumber);
				 customTemplate.setSiteUrl(siteUrl);
				 asyncService.sendEmail(customTemplate,
				 mailSender,velocityEngine);

			} else {
				request.getSession().setAttribute("userEmailExistsMsg",
						userEmailExistsMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/tmiAdmCreateAdminUser.do";
	}

	/**
	 * This method is used to search interview list based on applicant name or 
	 * 						  evaluator name or interview id
	 * 
	 * @param request :get user entered text 
	 * @param model : adding  search results 
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmAdmincallDetails")
	public String getotherDomainProfile1(HttpServletRequest request, Model model) {
		String connectstatus = request.getParameter("connectstatus");
		String enterString = request.getParameter("evaluatorName");
		int dashboard = request.getParameter("dashboard") == null?0:Integer.parseInt(request.getParameter("dashboard"));		
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));
		
		List adminDetailsList = new ArrayList();
		int  adminLstCount=0;
		if (dashboard == 1) {
			adminDetailsList=adminDAO.getadminDetailsBasedOnTmiId(enterString);
			 adminLstCount =adminDetailsList.size();
		} else if (dashboard == 2) {
			adminDetailsList=adminDAO.getadminDetailsBasedOnApplicantName(enterString);
			 adminLstCount =adminDetailsList.size();
		} else if (dashboard == 3) {
			adminDetailsList=adminDAO.getadminDetailsBasedOnevaluator(enterString);
			 adminLstCount =adminDetailsList.size();	
		} else if(dashboard == 0){
			adminDetailsList=adminDAO.getadminDetails(connectstatus,
					tmiUtil.getAdminUserId());
			 adminLstCount =adminDetailsList.size();	
		}
		if (adminLstCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("adminDetailsList", adminDetailsList.subList(firstResult, (adminLstCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult + (adminLstCount - (pageNo - 1) * pageLimit)));	
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#", adminLstCount, pageNo, pageLimit, 2));

		} 
		else 
		{
			model.addAttribute("nodata", "nodetails");
		}
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		model.addAttribute("adminLstCount",adminLstCount);
		model.addAttribute("pageLimit",pageLimit);
		model.addAttribute("connectstatus",connectstatus);
		model.addAttribute("enterString",enterString);
		model.addAttribute("dashboard",dashboard);
		
		
		
		return "admincallDetails";
	}

	/**
	 * This method is used to get ongoing interviews Calling status details
	 * 
	 * @param request
	 * @param model : adding call status
	 * @return
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmAdminDetails.do")
	public String adminDetails(HttpServletRequest request, Model model) {	
		model.addAttribute("count", Constants.countFirst);
		model.addAttribute("noanswer", profileDAO.getanswercount(Constants.KookooNoAnswer));
		model.addAttribute("dtmfNot", profileDAO.getanswercount(Constants.KookooDTMF));
		model.addAttribute("success", profileDAO.getanswercount(Constants.KookooSuccess));
		model.addAttribute("dashboardsearchtypelkp",
				dropDownDAO.getdashboardsearchtypelkp());
		return "adminDetails";
	}

	/**
	 * This method is used to upload bulk applicant emails for mockTest
	 * 
	 * @param request : checking email registered users or not
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmMockTest.do")
	public String tmiAdmMakeTest(HttpServletRequest request, Model model) {
		if (request.getSession().getAttribute("unregisteredMembersApp") != null) {
			model.addAttribute("unregisteredMembersApp", (List) request
					.getSession().getAttribute("unregisteredMembersApp"));
			request.getSession().removeAttribute("unregisteredMembersApp");
		}
		if (request.getSession().getAttribute("count") != null) {
			model.addAttribute("count",
					request.getSession().getAttribute("count"));
			request.getSession().removeAttribute("count");
		}
		if (request.getSession().getAttribute("interviewStatus") != null) {
			model.addAttribute("interviewStatus", request.getSession()
					.getAttribute("interviewStatus"));
			request.getSession().removeAttribute("interviewStatus");
		}
		return "mocktestemails";
	}

	/**
	 * This method is used  to upload .csv or .xlsx files and read the content(emails) from it
	 *           inserting files into db
	 * @param request :  get user uploaded file  .csv or .xlsx 
	 * @return : mocktest bulk upload emails page
	 */
	@RequestMapping(value = "/tmiAdmMockuploadFiles.do", method = RequestMethod.POST)
	public String tmiAdmMockuploadFiles(MultipartHttpServletRequest request) {
		int otherDomain = 1;
		MultipartFile file = request.getFile("BrowserHidden");
		int interviewStatus = Integer.parseInt(request
				.getParameter("interviewStatus"));
		ArrayList unregisteredeapp = new ArrayList();
		ArrayList allUserList = new ArrayList();
		Map<String, String> responseMap = new HashMap<String, String>();
		try {
			allUserList = TmiUtil.readExcelFile(file.getInputStream());
			if (allUserList.size() > 0) {
				for (int i = 0; i < allUserList.size(); i++) {
					ArrayList userList = (ArrayList) allUserList.get(i);
					if (userList.size() > 0) {
						String emailId = ((String) userList.get(0)).trim();
						if (emailId.contains("@")) {
							int emailCount = profileDAO.getEmail(emailId);
							if (emailCount == 1) {
								ProfileBean pb = new ProfileBean();
								pb.setEmailaddress(emailId);
								int userId = profileDAO.getUserId(pb);
								String fullName=profileDAO.getFullNameByUserId(userId);
								int count = adminDAO.addMockTest(userId,
										interviewStatus);
								request.getSession().setAttribute("count",
										count);								
								String url = testUrl + "addUser.php?name="+URLEncoder.encode(fullName, "UTF-8")+"&email="+emailId+"&status="+interviewStatus;		
								asyncService.sendGet(url);
								
							} else {
								unregisteredeapp.add(emailId);
							}
						}
					}
				}
			}
			request.getSession().setAttribute("interviewStatus",
					interviewStatus);
			request.getSession().setAttribute("unregisteredMembersApp",
					unregisteredeapp);
			request.getSession().setAttribute("errorMsg",
					responseMap.get("errorMsg"));
			request.getSession().setAttribute("userAddMsg",
					responseMap.get("userAddMsg"));
		} catch (org.springframework.web.multipart.MaxUploadSizeExceededException me) {
			request.getSession().setAttribute("maxFileUploadMsg",
					"maxFileUploadMsg");
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMsg", "fileContentErrMsg");
		}
		return "redirect:/tmiAdmMockTest.do";

	}

	/**
	 * This method is used to get Bulk group interviews details
	 * 
	 * @param request
	 * @param model  : add bulk group details to model 
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmBulkGroup.do")
	public String tmiAdmBulkGroup(HttpServletRequest request, Model model) {
		model.addAttribute("bulkGroupList", adminDAO.getBulkGroupList());
		return "bulkgroup";
	}

	/**
	 * This method is used to get bulkgroup details based on bulkGroupId
	 * 
	 * @param request : get  bulkGroupId
	 * @param model 
	 * @return
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmInterviewBulkGroup.do")
	public String tmiAdmInterviewBulkGroup(HttpServletRequest request,
			Model model) {
		int bulkGroupId = Integer.parseInt(request.getParameter("bulkGroupId"));
		model.addAttribute("interviewList",
				adminDAO.getInterviewGroupList(bulkGroupId));
		return "interviewbulkgroup";
	}

	/**
	 * This method is to show evaluator feedback details based on interview id
	 * 
	 * @param request :  get interviewId
	 * @param model  : add Evaluator FeedBack Details
	 * @return
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/admininterviewfeedback")
	public String admininterviewfeedback(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		String returnUrl = "admininterviewfeedback";
		int industryid = profileDAO.getInterviewIndustryId(interviewId);
		int usertypeid = 2;
		model.addAttribute("interviewId", interviewId);
		model.addAttribute("interviewDetailsKnowledge", internalDAO
				.getEvaluatorFeedBackDetails(interviewId,
						Constants.feebbcakApplicantId, usertypeid, industryid));
		int count = dropDownDAO.getFeedbackCount(usertypeid, interviewId);
		if (count > 1) {
			returnUrl = "redirect:/interviewFeedBack?interviewId="
					+ interviewId + "&industryid=" + industryid;
		}
		return returnUrl;
	}

	/**
	 * This method is used to addFeedback or updateFeedBack based on interviewId  insert into DB
	 * 
	 * @param request : get  interviewId and feedbackDone 
	 * @param model 
	 * @return
	 */
	@RequestMapping(value = "/adminupdateFeedback")
	public String adminupdateFeedback(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		FeedBackBean fb = new FeedBackBean();
		int feedBackId = request.getParameter("feedbackDone").isEmpty() ? 0
				: Integer.parseInt(request.getParameter("feedbackDone"));
		if (feedBackId == 1) {
			internalDAO.updateinterViewStatus(interviewId);
		}
		fb.setFeedBackStatusId(feedBackId);
		adminDAO.checkaddauditTable(interviewId, Constants.AdminFeedback, Constants.AdminFeedback,
				tmiUtil.getAdminUserId());
		int usertypeid = 2;
		fb.setUserType(usertypeid);
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
		fb.setInterViewId(Integer.parseInt(request.getParameter("interviewId")));
		fb.setEvalId(Integer.parseInt(request.getParameter("overallNotes")));
		internalDAO.updateImpression(fb);
		request.getSession().setAttribute("interViewId",
				request.getParameter("interviewId"));
		int industryid = profileDAO.getInterviewIndustryId(interviewId);
		return "redirect:/interviewFeedBack?interviewId=" + interviewId
				+ "&industryid=" + industryid;
	}

	/**
	 * This method is used to get Evaluator feedback details based on interviewid and industryid
	 * 
	 * @param request : get interviewid and industryid
	 * @param model   : add Evaluator FeedBack Details
	 * @return
	 */
	@RequestMapping(value = "/interviewFeedBack")
	public String evaluatorSummary(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		int industryid = Integer.parseInt(request.getParameter("industryid"));
		model.addAttribute("interviewDetailsKnowledge", internalDAO
				.getEvaluatorFeedBackDetails(interviewId,
						Constants.feebbcakApplicantId, Constants.internalUserTypeId, industryid));
		return "myinterviewFeedback";
	}

	/**
	 * This method is used to Search and get Ongoing call details based on TMIId and EvaluatorName and ApplicantName
	 * 
	 * @param request : get entered text
	 * @param model   :  add search results 
	 * @return
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmAdmincallDetailsBasedOnSearch")
	public String getotherDomainProfileBasedOnSearch(
			HttpServletRequest request, Model model) {
		String enterString = request.getParameter("evaluatorName");
		int dashboard = Integer.parseInt(request.getParameter("dashboard"));
		if (dashboard == 1) {
			model.addAttribute("adminDetailsList",
					adminDAO.getadminDetailsBasedOnTmiId(enterString));
		} else if (dashboard == 2) {
			model.addAttribute("adminDetailsList",
					adminDAO.getadminDetailsBasedOnApplicantName(enterString));
		} else if (dashboard == 3) {
			model.addAttribute("adminDetailsList",
					adminDAO.getadminDetailsBasedOnevaluator(enterString));
		}

		return "admincallDetails";
	}

	/**
	 * This method is used to  call to get all registered users details
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmUsersDetails")
	public String getUsersDetails(HttpServletRequest request, Model model) {

		return "registeredUserDetails";
	}

	/**
	 * This method is used  to get all registered users and search user based on emailid
	 * 
	 * @param request :get emailid 
	 * @param model   : add  all registered users or search results 
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmGetallRegisteredUsers")
	public String getGetallRegisteredUsers(HttpServletRequest request,
			Model model) {
		int defaultPageLimit = adminDAO.getDefaultPageLimit();
		int pageNo = request.getParameter("pageNo") == null ? 1 : Integer
				.parseInt(request.getParameter("pageNo"));
		int pageLimit = request.getParameter("pageLimit") == null ? defaultPageLimit
				: Integer.parseInt(request.getParameter("pageLimit"));
		String EmailId=request.getParameter("emailId") ==null ?"":request.getParameter("emailId");
		List registeredUsersList = new ArrayList();
		if(EmailId.isEmpty()){
		registeredUsersList = adminDAO.getAllregisteredUsers();
		}else {
			registeredUsersList = adminDAO.getAllregisteredUsersWithEmailId(EmailId);	
		}
		int registeredListCount = registeredUsersList.size();
		if (registeredListCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("registeredUserList", registeredUsersList
					.subList(firstResult, (registeredListCount > pageNo
							* pageLimit) ? firstResult + pageLimit
							: firstResult
									+ (registeredListCount - (pageNo - 1)
											* pageLimit)));
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#",
					registeredListCount, pageNo, pageLimit, 2));

		} else {
			model.addAttribute("nodata", "nodetails");
		}
		model.addAttribute("paginationList", adminDAO.getPaginationData());
		model.addAttribute("registeredListCount", registeredListCount);
		model.addAttribute("pageLimit", pageLimit);
		model.addAttribute("EmailId", EmailId);
		return "registereduserstable";
	}

	/**
	 * This method is used to get All  interview bookings
	 * 
	 * @param request 
	 * @param model  : add all interviews like booked,cancel,closed, etc...
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmAllInterviewBooking")
	public String getFaildInterviewBooking(HttpServletRequest request,
			Model model) {
		model.addAttribute("booked",
				adminDAO.getInterviewCount(Constants.interviewBookedId));
		model.addAttribute("closed",
				adminDAO.getInterviewCount(Constants.interviewClosedId));
		model.addAttribute("cancelled",
				adminDAO.getInterviewCount(Constants.interviewCancelledId));
		model.addAttribute("paymentcancelled", adminDAO
				.getInterviewCount(Constants.interviewPaymentCancelledId));
		model.addAttribute("paymentfailure",
				adminDAO.getInterviewCount(Constants.interviewPaymentFailureId));
		model.addAttribute("rejectInterviews",
				adminDAO.getRejectInterviewCount());
		return "adminAllInterviews";
	}

	/**
	 * This method is to get all Interview Booking details based on interview type
	 * 
	 * @param request :get interviewtypeid
	 * @param model  : add all interviews like booked or cancel or closed, etc..
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmInterviewBookingdetails")
	public String getInterviewBookingFailed(HttpServletRequest request,
			Model model) {
		int defaultPageLimit = adminDAO.getDefaultPageLimit();
		String typeofinterview = request.getParameter("interviewTypeId");
		int pageNo = request.getParameter("pageNo") == null ? 1 : Integer
				.parseInt(request.getParameter("pageNo"));
		int pageLimit = request.getParameter("pageLimit") == null ? defaultPageLimit
				: Integer.parseInt(request.getParameter("pageLimit"));
		List registeredUsersList = new ArrayList();
		if(typeofinterview.equals("1") ||typeofinterview.equals("2") || typeofinterview.equals("3") || typeofinterview.equals("4") ||
				typeofinterview.equals("5")){		
		registeredUsersList = adminDAO
				.getAllInterviewBookingDetails(typeofinterview);
		}else{
		if(typeofinterview.equals("6")){		
			registeredUsersList = adminDAO
					.getRejectInterviewBookingDetails();			
		}else{			
		registeredUsersList = adminDAO
				.getAllInterviewBookingDetailsWithNoSlotStatus(typeofinterview);		
		}
	}
		int registeredListCount = registeredUsersList.size();
		if (registeredListCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("registeredUserList", registeredUsersList
					.subList(firstResult, (registeredListCount > pageNo
							* pageLimit) ? firstResult + pageLimit
							: firstResult
									+ (registeredListCount - (pageNo - 1)
											* pageLimit)));
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#",
					registeredListCount, pageNo, pageLimit, 2));

		} else {
			model.addAttribute("nodata", "nodetails");
		}

		model.addAttribute("paginationList", adminDAO.getPaginationData());
		model.addAttribute("registeredListCount", registeredListCount);
		model.addAttribute("pageLimit", pageLimit);
		model.addAttribute("typeofinterviewId", typeofinterview);
		return "adminAllInterviewsTable";
	}

	/**
	 * This method is used to Reports Page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmReportsPage.do")
	public String getReportsPage(HttpServletRequest request, Model model) {
		return "adminReportsPage";
	}

	/**
	 * This method is used to get ongoing bookings
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmOngoingBooking.do")
	public String getOngoingBooking(HttpServletRequest request, Model model) {
		return "adminOngoingBooking";
	}

	/**
	 * This method is used to get ongoing bookings, payment not done slot selected
	 * 
	 * @param request : get pageno and pagelimit 
	 * @param model   : add ongoing bookings
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/ongoingBookingDetails")
	public String getOngoingBookingDetails(HttpServletRequest request,
			Model model) {
		AdminProfileBean apb = new AdminProfileBean();
		apb.setAdminId(tmiUtil.getAdminUserId());
		int defaultPageLimit = adminDAO.getDefaultPageLimit();
		int pageNo = request.getParameter("pageNo") == null ? 1 : Integer
				.parseInt(request.getParameter("pageNo"));
		int pageLimit = request.getParameter("pageLimit") == null ? defaultPageLimit
				: Integer.parseInt(request.getParameter("pageLimit"));

		List ongoingBookingList = new ArrayList();
		ongoingBookingList = adminDAO.getOngoingBookingDetails(apb);
		int ongoingLstCount = ongoingBookingList.size();
		if (ongoingLstCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("ongoingBookingList",
					ongoingBookingList
							.subList(firstResult, (ongoingLstCount > pageNo
									* pageLimit) ? firstResult + pageLimit
									: firstResult
											+ (ongoingLstCount - (pageNo - 1)
													* pageLimit)));
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#",
					ongoingLstCount, pageNo, pageLimit, 2));

		} else {
			model.addAttribute("nodata", "nodetails");
		}

		model.addAttribute("paginationList", adminDAO.getPaginationData());
		model.addAttribute("ongoingBookingListCount", ongoingLstCount);
		model.addAttribute("pageLimit", pageLimit);
		return "adminOngoingBookingTable";
	}

	/**
	 * This method is used to get evaluator monthly slots who are less then 20 in moth
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmEvalMonthSlotCount.do")
	public String getEvalMonthSlotCount(HttpServletRequest request, Model model) {
		return "adminEvalMonthSlotCount";
	}

	/**
	 * This method is used to get evaluator monthly slots who are less then 20
	 * in moth
	 * 
	 * @param request : get page No and page limit for pagination
	 * @param model   : add evaluator monthly slots count
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/getEvalMonthSlotCount")
	public String getEvalMonthSlotCountList(HttpServletRequest request,
			Model model) {

		int defaultPageLimit = adminDAO.getDefaultPageLimit();
		int pageNo = request.getParameter("pageNo") == null ? 1 : Integer
				.parseInt(request.getParameter("pageNo"));
		int pageLimit = request.getParameter("pageLimit") == null ? defaultPageLimit
				: Integer.parseInt(request.getParameter("pageLimit"));

		List evalSlotCountLst = new ArrayList();
		evalSlotCountLst = adminDAO.getEvalMonthSlotCount();
		int evalSlotCount = evalSlotCountLst.size();
		if (evalSlotCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("evalSlotCountList", evalSlotCountLst.subList(
					firstResult,
					(evalSlotCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult
							+ (evalSlotCount - (pageNo - 1) * pageLimit)));
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#",
					evalSlotCount, pageNo, pageLimit, 2));

		} else {
			model.addAttribute("nodata", "nodetails");
		}
		model.addAttribute("paginationList", adminDAO.getPaginationData());
		model.addAttribute("evalSlotCount", evalSlotCount);
		model.addAttribute("pageLimit", pageLimit);
		return "adminEvalMonthSlotCountTable";
	}

	/**
	 * This method is used to call  Evaluator  Unavailable or Evaluator Rejected interviews
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmApplicantUnavailableSlots.do")
	public String applicantUnavailableSlots(HttpServletRequest request,
			Model model) {
		return "adminApplicantUnavailableSlots";
	}

	/**
	 * This method is used to get Evaluator  Unavailable or Evaluator Rejected interviews
	 * 
	 * @param request :  get page No and page limit for pagination
	 * @param model   : add evaluator unavailable or evaluator rejected interviews details
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/getApplicantUnavailableSlots")
	public String getApplicantUnavailableSlots(HttpServletRequest request,
			Model model) {
		int defaultPageLimit = adminDAO.getDefaultPageLimit();
		int pageNo = request.getParameter("pageNo") == null ? 1 : Integer
				.parseInt(request.getParameter("pageNo"));
		int pageLimit = request.getParameter("pageLimit") == null ? defaultPageLimit
				: Integer.parseInt(request.getParameter("pageLimit"));

		List applcntSlotLst = new ArrayList();
		applcntSlotLst = adminDAO.getApplicantUnavailableSlots();
		int applcntSlotCount = applcntSlotLst.size();
		if (applcntSlotCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("applicantUnavailSlotList", applcntSlotLst
					.subList(firstResult, (applcntSlotCount > pageNo
							* pageLimit) ? firstResult + pageLimit
							: firstResult
									+ (applcntSlotCount - (pageNo - 1)
											* pageLimit)));
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#",
					applcntSlotCount, pageNo, pageLimit, 2));

		} else {
			model.addAttribute("nodata", "nodetails");
		}
		model.addAttribute("paginationList", adminDAO.getPaginationData());
		model.addAttribute("applcntSlotCount", applcntSlotCount);
		model.addAttribute("pageLimit", pageLimit);
		return "adminApplicantUnavailableSlotsTable";
	}

	/**
	 * This method is used to get Interview Summary based on interview id
	 * 
	 * @param request : get interviewId
	 * @param model   :add interview skills and domain details 
	 * @return
	 */
	@Secured({ Constants.ROLE_ADMIN })
	@RequestMapping(value = "/admInterviewSummary")
	public String getInterviewSummary(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		AdminProfileBean apb = new AdminProfileBean();
		apb.setAdminId(tmiUtil.getAdminUserId());
		InterViewBean ib = new InterViewBean();
		ib.setInterviewid(interviewId);
		model.addAttribute("userDetails",
				adminDAO.getUnavailableSlotDetailsOninterviewID(ib, apb));
		model.addAttribute("skillsDetails",
				externalUserDao.getInterviewSkills(ib));
		model.addAttribute("domainList", externalUserDao.getDomainLst(ib));
		return "adminviewInterviewSummary";
	}
	/**
	 * This method is  used to get Interview Summary based on interview id
	 * 
	 * @param request  : get interviewId
	 * @param model    :add interview details, interview skills and domain details 
	 * @return
	 */
	@Secured({ Constants.ROLE_ADMIN })
	@RequestMapping(value = "/admUnAvailableslotInterviewSummary")
	public String getUnAvailableslotInterviewSummary(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		AdminProfileBean apb = new AdminProfileBean();
		apb.setAdminId(tmiUtil.getAdminUserId());
		InterViewBean ib = new InterViewBean();
		ib.setInterviewid(interviewId);
		model.addAttribute("userDetails",
				adminDAO.getUnavailableSlotInterviewSummary(ib, apb));
		model.addAttribute("skillsDetails",
				externalUserDao.getInterviewSkills(ib));
		model.addAttribute("domainList", externalUserDao.getDomainLst(ib));
		return "adminviewInterviewSummary";
	}

	/**
	 * This method is used to get  evaluator Payment Request List
	 * 
	 * @param request
	 * @param model   : add evaluator Payment Request List
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmEvalPaymentRequest.do")
	public String tmiAdmEvalPaymentRequest(HttpServletRequest request,
			Model model) {
		model.addAttribute("evalPaymentReqList",
				adminDAO.getEvalPaymentReqList(tmiUtil.getAdminUserId()));
		return "adminevalpaymentrequest";
	}

	/**
	 * This method is used to evaluator Payment Details based On Month
	 * 
	 * @param request : get month name
	 * @param model   : add evaluator Payment Details
	 * @return
	 */
	@Secured({ Constants.ROLE_ADMIN })
	@RequestMapping(value = "/evalPaymentDetailsOnMonth")
	public String myPaymentDetailsOnMonth(HttpServletRequest request,
			Model model) {
		String monthname = request.getParameter("monthname");
		int evalid = Integer.parseInt(request.getParameter("evalid"));
		model.addAttribute("monthname", monthname);
		model.addAttribute("mypaymentList",
				internalDAO.getMyPaymentListOnMonth(evalid, monthname));
		return "mypaymentOnMonth";

	}

	/**
	 * This method is used to update Evaluator interview amount  based on date 
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_ADMIN })
	@RequestMapping(value = "/evalPaymentSubmit")
	public String evalPaymentSubmit(HttpServletRequest request, Model model) {
		String interviewmonth = request.getParameter("interviewmonth");
		String referNo = request.getParameter("referNo");
		int evaluserid = Integer.parseInt(request.getParameter("evaluserid"));
		adminDAO.payEvalMonthlyReq(evaluserid, interviewmonth, referNo,
				tmiUtil.getAdminUserId());
		return "redirect:/tmiAdmEvalPaymentRequest.do";

	}

	/**
	 * This method is to used to update slot as Reject and reason for Reject slot
	 * 
	 * @param request : get reson , interviewid, time, scheduleid
	 * @param model
	 * @return
	 */
	@Secured({ Constants.ROLE_ADMIN })
	@RequestMapping(value = "/tmiadmrejectInterView/{slotscheduleid}/{reason}/{myinterviewid}/{starttime}", method = RequestMethod.GET)
	@ResponseBody
	public void tmiadmrejectInterView(@PathVariable int slotscheduleid,
			@PathVariable String reason, @PathVariable int myinterviewid,
@PathVariable String starttime) {
		schedulerDAO.rejectedSlotComment(slotscheduleid, reason, myinterviewid,
				tmiUtil.getAdminUserId(), Constants.adminUsertype,starttime);
		adminDAO.checkaddauditTable(myinterviewid, Constants.AdminReject,Constants.AdminReject,
				tmiUtil.getAdminUserId());
	}

	/**
	 * This method is to used to close interview based on interviewid and update interview table
	 * 
	 * @param request : get interviewid
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiadmClosedInterView.do/{interviewid}", method = RequestMethod.GET)
	@ResponseBody
	public void tmiadmClosedInterView(@PathVariable int interviewid) {
		adminDAO.updateInterviewStatus(interviewid);
		adminDAO.checkaddauditTable(interviewid, Constants.AdminClosing,Constants.AdminClosing,
				tmiUtil.getAdminUserId());
	}

	/**
	 * This method is used to show applicant feedback 
	 * 
	 * @param request : get interviewid
	 * @param model  : add Applicant FeedBack Details
	 * @return
	 */
	@RequestMapping(value = "/tmiadmimyInterviewFeedBackApplicant")
	public String tmiadminapplicantSummary(HttpServletRequest request,
			Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		int usertypeid = 1;
		int industryid = profileDAO.getInterviewIndustryId(interviewId);
		model.addAttribute("interviewDetailsKnowledge", internalDAO
				.getApplicantFeedBackDetails(interviewId,
						Constants.feebbcakApplicantId, usertypeid, industryid));
		return "viewapplicantfeedback";
	}

	/**
	 * This method is used to accepting Slot by admin
	 * 
	 * @param request : get slotscheduleid
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiadmAcceptSlot.do/{slotId}", method = RequestMethod.GET)
	@ResponseBody
	public void tmiadmAcceptSlot(@PathVariable int slotId) {
		schedulerDAO.changeSlotStatus(Constants.slotStatusAcceptId, slotId);
		adminDAO.checkaddauditTable(slotId, Constants.AdminSlotAccept,Constants.AdminSlotAccept,
				tmiUtil.getAdminUserId());

	}

	/**
	 * This Method is used to get Interview recorded Files
	 * 
	 * @param request :get interview TmiId
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiGetInterviewFiles/{interviewTmiId}", method = RequestMethod.GET)
	@ResponseBody
	public List tmiGetInterviewFiles(@PathVariable String interviewTmiId,
			Model model) {
		return adminDAO.getInterviewFiles(interviewTmiId);

	}
	/**
	 * This Method is used to Send  Email Verification 
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiSendVerificationEmail", method = RequestMethod.GET)
	public String tmiSendVerificationEmail(Model model) {
	List<Map<String,String>> sendVerificationEmailList =adminDAO.sendVerificationEmail();
	int i=0;
	for (Map<String, String> temp : sendVerificationEmailList) {
		i+=1;
		CustomTemplateBean customTemplate = new CustomTemplateBean();
		customTemplate.setSenderEmailId(supportEmail);
		customTemplate.setTemplateName("registrationsuccess.vm");
		customTemplate
				.setMailSubject("Interview Pandit - Thank you for registering");
		customTemplate.setEmailAddress(temp.get("username"));
		customTemplate.setFirstName(temp.get("firstname"));		
		customTemplate.setSiteUrl(siteUrl);
		customTemplate.setSecurityCode(temp.get("securitycode"));
		customTemplate.setLoginUrl("login.do");		
		tmiUtil.postEmail(customTemplate, mailSender,
				velocityEngine);
		
	}
		
		
	return	"";
	}
	/**
	 * This method is to show mockTestResultlist
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmMockTestResultByList.do")
	public String mockTestResultByList(HttpServletRequest request, Model model) {
		model.addAttribute("mockTestResultCount",0);
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		return "mockTestResultModelList";
	}
	/**
	 * This method is to show mockTestResultlist
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/tmiAdmMockTestResultModelList")
	public String tmiAdmResultByList(HttpServletRequest request, Model model) {	
		String testStartDate=request.getParameter("testStartDate");
		String testEndDate=request.getParameter("testEndDate");
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));
		List<String> mockTestResultByList=externalUserDao.getMockTestResultByList(testStartDate,testEndDate);
		int  mockTestResultCount=mockTestResultByList.size();
		ArrayList list = new ArrayList();
		if (mockTestResultCount > 0) {
		int firstResult = pageNo * pageLimit - pageLimit;
		List<String> mockTestResultSubList=mockTestResultByList.subList(firstResult, (mockTestResultCount > pageNo * pageLimit) ? firstResult
				+ pageLimit : firstResult + (mockTestResultCount - (pageNo - 1) * pageLimit));		
		for(int i=0;i<mockTestResultSubList.size();i++){		
		String testRollNo=mockTestResultSubList.get(i);
		List<Map<String, Object>> testResultList = null;
		List<Map<String, Object>> examresultsList = null;
		try {
			List<Map<String,String>> mockTestResultList=externalUserDao.getMockTestResultList(testRollNo);
			testResultList = TmiUtil.convertJsonToListOfMap(mockTestResultList.get(0).get("tmimockresultList"));	
			examresultsList = TmiUtil.convertJsonToListOfMap(testResultList
					.get(0).get("exam_results").toString());
			testResultList.get(0).remove("exam_results");
			testResultList.get(0).put("exam_results", examresultsList);
			testResultList.get(0).put("fullname", mockTestResultList.get(0).get("fullname"));
			testResultList.get(0).put("emailid", mockTestResultList.get(0).get("emailid"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		list.add(testResultList);		
		}
		model.addAttribute("testResultList", list);	
		model.addAttribute("pageNav", pageNavigator.buildPageNav("#", mockTestResultCount, pageNo, pageLimit, 2));
		}
		else 
		{
			model.addAttribute("nodata", "No data available");
		}		
		
		model.addAttribute("testStartDate",testStartDate);
		model.addAttribute("testEndDate",testEndDate);
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		model.addAttribute("mockTestResultCount",mockTestResultCount);
		model.addAttribute("pageLimit",pageLimit);				
		return "mockTestResultModelListInner";
	}
}