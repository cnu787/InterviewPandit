package com.testmyinterview.portal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.testmyinterview.portal.bean.AdminProfileBean;
import com.testmyinterview.portal.bean.CustomTemplateBean;
import com.testmyinterview.portal.bean.InterViewBean;
import com.testmyinterview.portal.bean.PaymentBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.dao.AdminDAO;
import com.testmyinterview.portal.dao.DropDownDAO;
import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.dao.ProfileDAO;
import com.testmyinterview.portal.dao.SchedulerDAO;
import com.testmyinterview.portal.paging.PageNavigator;
import com.testmyinterview.portal.util.ASyncService;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class CallCenterRepresentativeController {
	@Autowired
	private TmiUtil tmiUtil;
	@Autowired
	private AdminDAO adminDAO;
	@Autowired
	private DropDownDAO dropDownDAO; 
	@Autowired
	private ExternalUserDao externalUserDao;
	@Autowired
	private SchedulerDAO schedulerDAO;
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private PageNavigator pageNavigator;	
	@Value("${support}")
	private String support;
	@Autowired
	private ASyncService asyncService;
	@Value("${bookingConfmsgEval}")
	private String bookingConfmsgEval;
	@Value("${bookingConfmsg}")
	private String bookingConfmsg;
	@Value("${smsurl}")
	private String smsurl;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;
	@Value("${maxFileUploadMsg}")
	private String maxFileUploadMsg;
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

	
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	/**
	 * This method is to show Call Center Representative home page 
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmCallCenterRepresentativeLanding.do")
	public String adminHome(HttpServletRequest request, Model model) {
		if (request.getSession().getAttribute("mySesProfile") != null) {			
			request.getSession().removeAttribute("mySesProfile");
		}
		String days=((request.getParameter("mydays")==null ||request.getParameter("mydays").isEmpty()) ? "0":request.getParameter("mydays"));
		AdminProfileBean apb=new AdminProfileBean();
		apb.setAdminId(tmiUtil.getAdminUserId());
		List adminProfile = adminDAO.getAdminDetails(apb);
		request.getSession().setAttribute("mySesProfile", adminProfile);		
		model.addAttribute("closedInterviewsCount",adminDAO.getClosedInterviews());
		model.addAttribute("ongoingInterviewsCount",adminDAO.getOngoingInterviews());
		model.addAttribute("evalUnavailableCount",adminDAO.getEvalUnavailableData(days));
		model.addAttribute("closedInterviewsData",adminDAO.getClosedInterviewsData(days));
		model.addAttribute("cancelledInterviewsData",adminDAO.getCancelInterviewsData(days));
        model.addAttribute("dashboardsearchtypelkp",dropDownDAO.getdashboardsearchtypelkp());
        model.addAttribute("daycount", days);
		return "callcenterrepresentativehome";
	}
	
	/**
	 * This method is to show evaluator unavaiableDetails based on input like
	 * mydays
	 * @param request :mydays
	 * @param model:reschedule,mydays
	 * @return  
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmGetEvalUnavailableDetails.do")
	public String getEvalUnavailableDetails(HttpServletRequest request, Model model) {		
		model.addAttribute("dashboardsearchtypelkp",dropDownDAO.getdashboardsearchtypelkp());
		String days=request.getParameter("mydays");
		String reschedule=((request.getParameter("reschedule") == null || request.getParameter("reschedule").isEmpty()) ? "0" : request.getParameter("reschedule"));
		model.addAttribute("reschedule", reschedule);
		model.addAttribute("mydays", days);
		return "evaluatorUnavailableDetails";
	}
	/**
	 * This method is to show Closed interviews Details based on input like
	 * mydays 
	 * @param request
	 * @param model:daycount,dashboardsearchtypelkp
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmGetClosedInterviewsDetails.do")
	public String getClosedInterviewsDetails(HttpServletRequest request, Model model) {	
		String days=request.getParameter("mydays");
		model.addAttribute("daycount", days);
		  model.addAttribute("dashboardsearchtypelkp",dropDownDAO.getdashboardsearchtypelkp());
		return "closedInterviewsDetails";
	}
	

	
	/**
	 * This method is to show Cancel interviews details based on input like
	 * mydays
	 * 
	 * @param request
	 * @param model:daycount,dashboardsearchtypelkp
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmGetCancelInterviewsDetails.do")
	public String getCancelInterviewsDetails(HttpServletRequest request, Model model) {	
		String days=request.getParameter("mydays");
		model.addAttribute("daycount", days);
		  model.addAttribute("dashboardsearchtypelkp",dropDownDAO.getdashboardsearchtypelkp());
		return "cancelledInterviewsDetails";
	}
	/**
	 * This method is to show cancel interviews Details based on search and all cancel interviews inputs like
	 * mydays,evaluatorName,dashboard
	 * @param request
	 * @param model:cancelInterviewsData
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmcancelInterviewsDetailsBasedonSearch")
	public String cancelledInterviewsDetailsBasedonSearch(HttpServletRequest request, Model model) {	
		String days=request.getParameter("mydays");
		String enterstring=request.getParameter("evaluatorName");
		int dashboard=request.getParameter("dashboard").isEmpty() ? 0 : Integer.parseInt(request.getParameter("dashboard"));
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));		
		List cancelInterviewsList = new ArrayList();
		if(dashboard == 0){
			cancelInterviewsList=adminDAO.getCancelInterviewsData(days);
		}else if(dashboard == 1){
		cancelInterviewsList=adminDAO.getCancelInterviewsDataBasedOnTmiId(enterstring);
		}else if(dashboard == 2){
			cancelInterviewsList=adminDAO.getCancelInterviewsDataBasedOnApplicantName(enterstring);
		}else if(dashboard == 3){
			cancelInterviewsList=adminDAO.getCancelInterviewsDataBasedOnEvaluatorName(enterstring);
		}
		int cancelInterviewsListCount =cancelInterviewsList.size();
		if (cancelInterviewsListCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("cancelInterviewsData", cancelInterviewsList.subList(firstResult, (cancelInterviewsListCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult + (cancelInterviewsListCount - (pageNo - 1) * pageLimit)));	
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#", cancelInterviewsListCount, pageNo, pageLimit, 2));

		} 
		else 
		{
			model.addAttribute("nodata", "nodetails");
		}	
		model.addAttribute("dashboard", dashboard);
		model.addAttribute("enterstring", enterstring);
		model.addAttribute("daycount", days);
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		model.addAttribute("cancelInterviewsListCount",cancelInterviewsListCount);
		model.addAttribute("pageLimit",pageLimit);
		return "cancelledInterviewsTable";
	}
	/**
	 * This method is used to get Evaluator availability Details based on input like
	 * interviewId 
	 * @param request
	 * @param model:evalAvailableList,applicantNameAndPhone
	 * @return
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/searchEvalAvailability")
	public String searchEvalAvailability(HttpServletRequest request, Model model) {
		int interviewId=Integer.parseInt(request.getParameter("interviewId"));		
		String evalStr =schedulerDAO.getApplicantEvaluatorMatch(interviewId,externalUserDao.getRoleID(interviewId));
		model.addAttribute("applicantNameAndPhone",adminDAO.getApplicantNameAndPhone(interviewId));
		model.addAttribute("evalAvailableList",adminDAO.getEvalNameAndPhone(evalStr,interviewId));		
		return "searchEvalAvailability";
	}			
	/**
	 * This method is  Used to Assigning Evaluator to Applicant slots based on inputs like
	 * interviewid,slotscheduleid,evalid and sending email email for both applicant and evaluator
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/assignEvaluatorToApplicantSlot.do/{interviewid}/{slotscheduleid}/{evalid}",method = RequestMethod.GET)
	@ResponseBody
	public boolean assignEvaluatorToApplicantSlot(@PathVariable int interviewid,@PathVariable int slotscheduleid,@PathVariable int evalid) {
		int status= adminDAO.rescheduleApplicantSlot(evalid,interviewid,slotscheduleid,tmiUtil.getAdminUserId(),Constants.adminUsertype);
					adminDAO.checkaddauditTable(interviewid, Constants.AdminReassigned,Constants.AdminReassigned, tmiUtil.getAdminUserId());
					
		boolean updateStatus =false;
		InterViewBean ib = new InterViewBean();
		ib.setInterviewid(interviewid);	
		List<Map<String, String>> userDetails= externalUserDao.getBookedSlotDetailsOninterviewID(ib);
		if(status==0 || status ==1){
			updateStatus =true;
			 asyncService.sendSMSToCustomers(smsurl,userDetails.get(0).get("evalmobile"),bookingConfmsgEval);
			CustomTemplateBean cbEval=new CustomTemplateBean();
			cbEval.setEmailAddress(userDetails.get(0).get("evalemailid"));			
			cbEval.setSenderEmailId(support);
			cbEval.setTemplateName("evalinterviewbookingconfirmation.vm");					
			cbEval.setMailSubject(userDetails.get(0).get("interviewtypename")+" Interview Booking Confirmation");
			cbEval.setUserDetails(userDetails);
			cbEval.setSkillsDetails(externalUserDao.getInterviewSkills(ib));
			cbEval.setDomainList(externalUserDao.getDomainLst(ib));
	  		asyncService.sendEmail(cbEval, mailSender,
					velocityEngine); 
		}
		if(status==0){
			updateStatus =true;
			 asyncService.sendSMSToCustomers(smsurl,userDetails.get(0).get("mobileno"),bookingConfmsg);
			CustomTemplateBean cb=new CustomTemplateBean();
			cb.setEmailAddress(userDetails.get(0).get("emailid"));
			cb.setSenderEmailId(support);
			cb.setTemplateName("interviewbookingconfirmation.vm");	
			cb.setMailSubject(userDetails.get(0).get("interviewtypename")+" Interview Booking Confirmation");
			cb.setUserDetails(userDetails);
	  		cb.setSkillsDetails(externalUserDao.getInterviewSkills(ib));
	  		cb.setDomainList(externalUserDao.getDomainLst(ib));
	  		asyncService.sendEmail(cb, mailSender,
					velocityEngine); 
		}
	
		return updateStatus ;
	}	
	/**
	 * This method is to show DashBoard Report based on inputs 
	 * like Interview ID, Applicant name, Evaluator Name,dashboard
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiAdmGetDashboardReport.do")
	public String tmiAdmGetDashboardReport(HttpServletRequest request, Model model) {
		String returnUrl="";
		int dashboardId=Integer.parseInt(request.getParameter("dashboard")==null?"1":request.getParameter("dashboard"));
		String enterString=request.getParameter("enterString");			
		model.addAttribute("dashboardsearchtypelkp",dropDownDAO.getdashboardsearchtypelkp());
		model.addAttribute("dashboardId",dashboardId);	
  		model.addAttribute("enterString",enterString);	
		if(dashboardId==1){
			int interviewId=dropDownDAO.getInterviewId(enterString);
			if(interviewId!=0){
				InterViewBean ib = new InterViewBean();
				ib.setInterviewid(interviewId);	
		  		model.addAttribute("userDetails",externalUserDao.getBookedSlotDetailsOninterviewID(ib));
		  		model.addAttribute("skillsDetails",externalUserDao.getInterviewSkills(ib));
		  		model.addAttribute("domainList",externalUserDao.getDomainLst(ib));	
			}
			returnUrl= "interviewIdDetails";
		}else if(dashboardId==2){
				returnUrl= "interviewapplicantsdetails";
		}else if(dashboardId==3){
		  		returnUrl= "interviewevaluatordetails";
		}
		return returnUrl;
	}
	
	/**
	 * This method is to show detail Report of enterString based on inputs 
	 * like Interview ID, Applicant name, Evaluator Name,dashboardId,enterString
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/getDashboardReport")
	public String getDashboardReport(HttpServletRequest request, Model model) {
		String returnUrl="";
		int dashboardId=Integer.parseInt(request.getParameter("dashboardId"));
		String enterString=request.getParameter("enterString");
		model.addAttribute("dashboardId",dashboardId);	
  		model.addAttribute("enterString",enterString);	
		List userDetailsList = new ArrayList();
		 if(dashboardId==2){		
				userDetailsList=adminDAO.getApplicantDetails(enterString);
				returnUrl= "interviewApplicantDetailsTable";
		}else if(dashboardId==3){			
		  		userDetailsList=adminDAO.getEvaluatorDetails(enterString);
		  		returnUrl= "interviewEvaluatorDetailsTable";
		  		}
		 	int defaultPageLimit=adminDAO.getDefaultPageLimit();
			int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
			int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));		
			
			int userDetailsListCount =userDetailsList.size();
			if (userDetailsListCount > 0) {
				int firstResult = pageNo * pageLimit - pageLimit;
				model.addAttribute("userDetails", userDetailsList.subList(firstResult, (userDetailsListCount > pageNo * pageLimit) ? firstResult
								+ pageLimit : firstResult + (userDetailsListCount - (pageNo - 1) * pageLimit)));	
				model.addAttribute("pageNav", pageNavigator.buildPageNav("#", userDetailsListCount, pageNo, pageLimit, 2));

			} 
			else 
			{
				model.addAttribute("nodata", "nodetails");
			}			
			model.addAttribute("paginationList",adminDAO.getPaginationData());
			model.addAttribute("userDetailsListCount",userDetailsListCount);
			model.addAttribute("pageLimit",pageLimit);
		return returnUrl;
	}
	/**
	 * This method is to show Applicant Interview Details based on inputs 
	 * like Interview ID
	 * @param request
	 * @param model:userDetails,skillsDetails,domainList
	 * @return 
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiAdmGetApplicantDetails")
	public String tmiAdmGetApplicantDetails(HttpServletRequest request, Model model) {
		int interviewId=Integer.parseInt(request.getParameter("interviewId"));		
			InterViewBean ib = new InterViewBean();
				ib.setInterviewid(interviewId);	
		  		model.addAttribute("userDetails",adminDAO.getApplicantDetailsOnInterviewId(interviewId));
		  		model.addAttribute("skillsDetails",externalUserDao.getInterviewSkills(ib));
		  		model.addAttribute("domainList",externalUserDao.getDomainLst(ib)); 		
			return "interviewdetailsapplicant";
		}
	/**
	 * This method is to show Applicant Details based on inputs 
	 * like Interview ID
	 * @param request
	 * @param model:userDetails,skillsDetails,domainList
	 * @return 
	 */
	@RequestMapping(value = "/tmiAdmGetApplicantDetailsApplicant")
	public String tmiAdmGetApplicantDetailsApplicant(HttpServletRequest request, Model model) {
		int interviewId=Integer.parseInt(request.getParameter("interviewId"));		
			InterViewBean ib = new InterViewBean();
				ib.setInterviewid(interviewId);	
		  		model.addAttribute("userDetails",adminDAO.getApplicantDetailsOnInterviewId(interviewId));
		  		model.addAttribute("skillsDetails",externalUserDao.getInterviewSkills(ib));
		  		model.addAttribute("domainList",externalUserDao.getDomainLst(ib)); 		
			return "interviewdetailsapplicanttmi";
		}
	/**
	 * This method is to show CompleteProfile based on input like userId
	 * 
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({"ROLE_CCR","ROLE_ADMIN"})	
	@RequestMapping(value = "/usersCompleteProfile")
	public String evalCompleteProfile(HttpServletRequest request, Model model) {	
		int userId=Integer.parseInt(request.getParameter("userId"));		
		ProfileBean profileBean=new ProfileBean();		
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
		model.addAttribute("UserPreference",profileDAO.getUserPreference(userId));
		model.addAttribute("domainList",
				profileDAO.getDomainNames(profileBean));
		model.addAttribute("userId",userId);
		int industryId=dropDownDAO.getIndustryId(userId);
		model.addAttribute("interviewerRoleList",dropDownDAO.getInterviewerRolelkpOnly(industryId));
		return "usersCompleteProfile";
	}
	/**
	 * This method is to show evaluator Unavailability based on input like evaluatorName,reschedule
	 * 
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/evalUnavailableDetailsbasedonName")
	public String evalUnavailableDetailsbasedonName(HttpServletRequest request, Model model) {		
		String enterString=request.getParameter("evaluatorName");
		String reschedule=request.getParameter("reschedule");		
		String days=((request.getParameter("mydays")==null ||request.getParameter("mydays").isEmpty()) ? "0":request.getParameter("mydays"));
		int dashboard=request.getParameter("dashboard").isEmpty() ? 0 : Integer.parseInt(request.getParameter("dashboard"));
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));
		List evalUnavailableList = new ArrayList();
		if(dashboard == 0){
			evalUnavailableList=adminDAO.getEvalUnavailableData(days);
		}else if(dashboard == 1){
			evalUnavailableList=adminDAO.getEvalUnavailableDataBasedOnTMIId(enterString);
		}else if(dashboard == 2){
			evalUnavailableList=adminDAO.getEvalUnavailableDataBasedOnApplicantName(enterString);
			}
		else if(dashboard == 3){
		evalUnavailableList=adminDAO.getEvalUnavailableDataBasedOnName(enterString);
		}
		int evalLstCount =evalUnavailableList.size();
		if (evalLstCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("evalUnavailableList", evalUnavailableList.subList(firstResult, (evalLstCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult + (evalLstCount - (pageNo - 1) * pageLimit)));	
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#", evalLstCount, pageNo, pageLimit, 2));

		} 
		else 
		{
			model.addAttribute("nodata", "nodetails");
		}	
		model.addAttribute("reschedule", reschedule);
		model.addAttribute("mydays",days);
		model.addAttribute("dashboard",dashboard);
		model.addAttribute("enterString",enterString);
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		model.addAttribute("evalLstCount",evalLstCount);
		model.addAttribute("pageLimit",pageLimit);
		return "evalUnavailableTable";
	}
	/**
	 * This method is to show closed interview details based on input like
	 * mydays,evaluatorName 
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiAdmclosedInterviewsDetailsBasedonSearch")
	public String closedInterviewsDetailsBasedonSearch(HttpServletRequest request, Model model) {	
		String days=request.getParameter("mydays");
		String enterString=request.getParameter("evaluatorName");
		int dashboard=request.getParameter("dashboard").isEmpty() ? 0 :Integer.parseInt(request.getParameter("dashboard"));
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));		
		List closedInterviewsList = new ArrayList();
		if(dashboard==0){
			closedInterviewsList=adminDAO.getClosedInterviewsData(days);
		}
		if(dashboard == 1){
		closedInterviewsList=adminDAO.getClosedInterviewsDataBasedOnTmiId(enterString);
		}else if(dashboard == 2){
			closedInterviewsList=adminDAO.getClosedInterviewsDataBasedOnApplicantName(enterString);
		}else if(dashboard == 3){
			closedInterviewsList=adminDAO.getClosedInterviewsDataBasedOnEvaluatorName(enterString);
		}
		int closedInterviewsListCount =closedInterviewsList.size();
		if (closedInterviewsListCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("closedInterviewsData", closedInterviewsList.subList(firstResult, (closedInterviewsListCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult + (closedInterviewsListCount - (pageNo - 1) * pageLimit)));	
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#", closedInterviewsListCount, pageNo, pageLimit, 2));

		} 
		else 
		{
			model.addAttribute("nodata", "nodetails");
		}		
		model.addAttribute("enterstring", enterString);
		model.addAttribute("dashboard", dashboard);
		model.addAttribute("daycount", days);
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		model.addAttribute("closedInterviewsListCount",closedInterviewsListCount);
		model.addAttribute("pageLimit",pageLimit);
		return "closedInterviewsTable";
	}
	/**
	 * This method is to show applicant slot Reschedule based on input like
	 *  interviewId,userid,startTimestamp,startDate,reschedule
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/admApplicantSlotReschedule")
	public String getApplicantSlotbooking(HttpServletRequest request, Model model) {
		schedulerDAO.updateLapsedSchedule();
		int interviewId=Integer.parseInt(request.getParameter("interviewId"));
		int userid=Integer.parseInt(request.getParameter("userid"));
		String startTimestamp =request.getParameter("startTimestamp") ;
		String startDate =request.getParameter("startDate") ;
		String reschedule=request.getParameter("reschedule");
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat ndf = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar c = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		try {
			c.setTime(ndf.parse(startDate));
			c2.setTime(sdformat.parse(startTimestamp));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String apnewDate = sdf.format(c.getTime());
		startTimestamp = sdformat.format(c2.getTime());	
		String compStr=sdf.format(c2.getTime());
		List appcntAvaiSlotsList = new ArrayList();
		if(apnewDate.equals(compStr)){
			
			appcntAvaiSlotsList=adminDAO.getApplicantAvailableSlotsForReschedule(schedulerDAO.getApplicantEvaluatorMatch(interviewId,externalUserDao.getRoleID(interviewId)),userid,startTimestamp,apnewDate);	
		}else{
			appcntAvaiSlotsList=adminDAO.getApplicantAvailableSlotsForReschedule(schedulerDAO.getApplicantEvaluatorMatch(interviewId,externalUserDao.getRoleID(interviewId)),userid,"0000-00-00 00:00:00",apnewDate);	
		}
		
		int aplSlotLstCount=appcntAvaiSlotsList.size();		
		if (aplSlotLstCount > 0) {	
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("applicantAvailabeSlots", appcntAvaiSlotsList.subList(firstResult, (aplSlotLstCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult + (aplSlotLstCount - (pageNo - 1) * pageLimit)));	
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#", aplSlotLstCount, pageNo, pageLimit, 2));
		} 
		else {
			model.addAttribute("nodata", "nodetails");
		}
		model.addAttribute("reschedule", reschedule);	
		model.addAttribute("interviewId", interviewId);	
		model.addAttribute("userid", request.getParameter("userid"));
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		model.addAttribute("evaluatorscheduleid", schedulerDAO.getEvaluatorscheduleid(interviewId));
		model.addAttribute("aplSlotLstCount",aplSlotLstCount);
		model.addAttribute("pageLimits",pageLimit);
		model.addAttribute("applDate",startDate);
		model.addAttribute("startTimestamp",startTimestamp);
		return "adminApplicantSlotReschedule";
	    
	}
	/**
	 * This method is used to show interview booking Steps for create off line interview
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiAdmininterviewBooking.do")
	public String tmiadmininterviewBooking(HttpServletRequest request, Model model) {	
		return "admininterviewbooking";
	}
	/**
	 * This method is to used to create off line interview landing page
	 * 
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiadmininterviewRole")
	public String tmiadmininterviewRole(HttpServletRequest request, Model model) {
		int userId= tmiUtil.getAdminUserId();
		int interviewId =dropDownDAO.checkAdminInterviewID(userId);
		if(interviewId !=0){
			model.addAttribute("externalProfile",externalUserDao.getInterviewDetails(interviewId));
			model.addAttribute("profileDetails",dropDownDAO.getUserProfileDetailsOnInterViewId(interviewId));
			model.addAttribute("interviewid",interviewId);
			
		}
  		model.addAttribute("industryList", dropDownDAO.getAdminIndustryList()); 
  		model.addAttribute("interviewrolesList", dropDownDAO.getInterviewerRolelkp());
  		model.addAttribute("interviewlocationsList", dropDownDAO.getInterviewLocations());
  		model.addAttribute("interviewtypesList", dropDownDAO.getInterviewTypes());
  		model.addAttribute("companyNamesList", dropDownDAO.getCompanyName());
  		model.addAttribute("currencytypeList", dropDownDAO.getCurrencytypelkp());
  		
  		return "admininterviewroles";
	}
	
	
	/**
	 * This method is to add interview details like interviewId,industry,domain etc..
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiadminaddinterviewerdetails")	
	public String tmiadminaddinterviewerdetails(HttpServletRequest request, Model model) {	
		try {
		
			int otherDomain =1;		
			int applicantId=Integer.parseInt(request.getParameter("applicantId"));		
			int interviewId=request.getParameter("admininterviewId").isEmpty()?0:Integer.parseInt(request.getParameter("admininterviewId"));
			if(interviewId == 0){
			 interviewId=adminDAO.addInterviewId(applicantId);
			 adminDAO.checkaddauditTable(interviewId,Constants.AdminCreateInterview,Constants.AdminCreateInterview,tmiUtil.getAdminUserId());
			}
			 adminDAO.addAdminInterviewbooking(tmiUtil.getAdminUserId(),interviewId);
			request.getSession().setAttribute("interviewId", interviewId);
		InterViewBean ib = new InterViewBean();
		ib.setUserid(applicantId);  		
  		 ib.setInterviewid(interviewId);
  	  ib.setIndustryid(Integer.parseInt(request.getParameter("industry")));
  	
		String[] values = request.getParameterValues("domain");
		StringBuilder domain = new StringBuilder();
		for (String n : values) { 			 
		    if (domain.length() > 0) domain.append(',');		   
		    if(n.equals("0")){
		    	otherDomain=Integer.parseInt(n);		    
		    }
		    if(!n.equals("-1")){
		    domain.append("").append(n).append("");
		    }		    
		}		
		String updateResume=request.getParameter("update_resume")==null?"":request.getParameter("update_resume");
		String domains=domain.toString();
		ib.setDomainid(domains);
   	  ib.setCareer(Integer.parseInt(request.getParameter("career")));
   	  ib.setPosition(request.getParameter("position"));
   	  ib.setResumeName(request.getParameter("uploadFile"));
   	  ib.setInterviewrole(Integer.parseInt(request.getParameter("interviewrole")));
   	  ib.setInterviewlocation(Integer.parseInt(request.getParameter("location")));
   	  ib.setInterviewmode(Integer.parseInt(request.getParameter("telephonic")));  
   	  ib.setInterviewtype(Integer.parseInt(request.getParameter("interviewtype")));
   	  ib.setCompanyName(request.getParameter("companyname"));
   	ib.setCompanyId(Integer.parseInt((request.getParameter("companyId")==null || request.getParameter("companyId").trim().isEmpty())? "-1" :request.getParameter("companyId")));
   	ib.setIpLocation(request.getParameter("currencytype"));
   	 int sum= externalUserDao.checkIndustry(ib);
   	 if(sum ==0){
   		externalUserDao.deleteInterviewskills(ib);
   	 } 
   	 int count=externalUserDao.checkIndustryInProfile(ib);
   	 if(count == 1 && sum==0){
   		externalUserDao.addProfileSkills(ib);
   	 }
   	if(!updateResume.isEmpty()){
		profileDAO.updateProfileResume(request, ib);
	}
   	externalUserDao.updateInterView(request,ib,otherDomain);
   	} catch (Exception e) {
		if (e instanceof org.springframework.web.multipart.MaxUploadSizeExceededException) {
			request.getSession().setAttribute("maxFileUploadMsg",
					maxFileUploadMsg);					
		}
		e.printStackTrace();
	}
		
		
		return "redirect:/tmiadmininterviewRole";			
	}
	
	
	/**
	 * This method is show slot booking page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({Constants.ROLE_CCR,Constants.ROLE_ADMIN})
	@RequestMapping(value = "/admintmislotBooking")
	public String admintmislotBooking(HttpServletRequest request, Model model) {
		int adminuserId= tmiUtil.getAdminUserId();
		int interviewId =dropDownDAO.checkAdminInterviewID(adminuserId);
		model.addAttribute("interviewId",interviewId);
		schedulerDAO.updateLapsedSchedule();		
		return "admintmislotbooking";		
	}
	
	/**
	 * This method is to show evaluator Slot details based on interviewId 
	 * 
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiadminapplicantSlotbooking")
	public String tmiadminapplicantSlotbooking(HttpServletRequest request, Model model) {
		int adminuserId= tmiUtil.getAdminUserId();
		int interviewId =dropDownDAO.checkAdminInterviewID(adminuserId);
		int userId=dropDownDAO.getUserIdBasedOnInterviewId(interviewId);
		schedulerDAO.updateLapsedSchedule();
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		int roleid = externalUserDao.getRoleID(interviewId);
		String currGmtDate = tmiUtil.getCurrentGmtDate();
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
		c.add(Calendar.DATE, 2);  // number of days to add
		currGmtDate = sdformat.format(c.getTime());
		String applDate =request.getParameter("startDate")==null ? currGmtDate : request.getParameter("startDate") ;
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));
		List appcntAvaiSlotsList = new ArrayList();
		try {
			
			c2.setTime(sdformat.parse(applDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String formatDate = sdf.format(c2.getTime());
		appcntAvaiSlotsList=adminDAO.adminGetApplicantAvailabeSlots(schedulerDAO.getApplicantEvaluatorMatch(interviewId,externalUserDao.getRoleID(interviewId)),userId,formatDate);
		
		int aplSlotLstCount=appcntAvaiSlotsList.size();		
		if (aplSlotLstCount > 0) {	
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("applicantAvailabeSlots", appcntAvaiSlotsList.subList(firstResult, (aplSlotLstCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult + (aplSlotLstCount - (pageNo - 1) * pageLimit)));	
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#", aplSlotLstCount, pageNo, pageLimit, 2));
		} 
		else {
			model.addAttribute("nodata", "nodetails");
		}
		model.addAttribute("interviewId", interviewId);	
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		model.addAttribute("evaluatorscheduleid", schedulerDAO.getEvaluatorscheduleid(interviewId));
		model.addAttribute("aplSlotLstCount",aplSlotLstCount);
		model.addAttribute("pageLimit",pageLimit);
		model.addAttribute("applDate",applDate);
		model.addAttribute("formatDate",formatDate);
		model.addAttribute("timeSlotList",schedulerDAO.getTimeSlots());
		model.addAttribute("gmtdate",currGmtDate);
		return "adminapplicantSlotbooking";
	}
	/**
	 * This method is used to update Slot ScheduleStatus based on inputs like
	 * scheduleId,interviewId 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiadminupdateSlotScheduleIdStatus.do/{scheduleId}/{interviewId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean tmiadminupdateSlotScheduleIdStatus(@PathVariable String scheduleId,@PathVariable String interviewId ) {
	return schedulerDAO.updateSlotScheduleIdStatus(scheduleId,Integer.parseInt(interviewId));
	}	
	/**
	 * This method is used to show Interview Summary 
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiadmininterviewSummary")
	public String interviewSummary(HttpServletRequest request, Model model) {
		int adminuserId= tmiUtil.getAdminUserId();
		int interviewId =dropDownDAO.checkAdminInterviewID(adminuserId);
		InterViewBean ib = new InterViewBean();
		ib.setInterviewid(interviewId);		
		int userId=dropDownDAO.getUserIdBasedOnInterviewId(interviewId);
		List userDetails=externalUserDao.getBookingSlotDetailsOninterviewID(ib);
  		model.addAttribute("userDetails",userDetails);
  		model.addAttribute("skillsDetails",externalUserDao.getInterviewSkills(ib));
  		model.addAttribute("domainList",externalUserDao.getDomainLst(ib));  		
  		HashMap userDetMap=  (HashMap) userDetails.get(0);
  		model.addAttribute("actualAmount",(String)userDetMap.get("amount"));  		
		model.addAttribute("evalAmount",(String) userDetMap.get("evalamount"));
  		model.addAttribute("finalAmount", "0");
  		try{  
		model.addAttribute("myToken",tmiUtil.md5Encode(profileDAO.getEmailIdByUserId(userId)+(String) userDetMap.get("interviewtmiid")));
  		}catch(Exception e){
  			
  		} 	
  		PaymentBean pb=new PaymentBean();
  		pb.setFirstName((String) userDetMap.get("firstname"));
  		pb.setEmailId((String) userDetMap.get("emailid"));
  		pb.setPhoneNumber((String) userDetMap.get("mobileno"));  		
  		pb.setAmount("0"); 
  		pb.setProductInfo((String) userDetMap.get("interviewtypename"));
  		pb.setTxnId((String) userDetMap.get("interviewtmiid"));    		
  		pb.setSalt(SALT);
  		pb.setSurl(SURL);
  		pb.setKey(KEY);
  		pb.setCurl(CURL);  		
  		externalUserDao.addInterviewAmount(ib.getInterviewid(),(String)userDetMap.get("amount"),(String) userDetMap.get("evalamount"),"0");
  		model.addAttribute("surl", SURL);
		model.addAttribute("curl", CURL);
		model.addAttribute("furl", FURL);
		model.addAttribute("key", KEY);
  		model.addAttribute("firstname", pb.getFirstName());
		model.addAttribute("phone", pb.getPhoneNumber());		
		model.addAttribute("txnid", pb.getTxnId());
		model.addAttribute("productinfo", pb.getProductInfo());		
		model.addAttribute("email", pb.getEmailId());  		
  		model=tmiUtil.paymentDetails(model,pb);
  		model.addAttribute("interviewId", interviewId);  	
		return "admininterviewSummary";
	}
	/**
	 * This method is to show interview Success page
	 * 
	 * @param request
	 * @param model:status,bookedSlotDetails,skillsNameList,domainList
	 * @return
	 */
	@Secured({Constants.ROLE_EXTERNAL,Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiadmininterviewSuccess.do")
	public String tmiadmininterviewSuccess(HttpServletRequest request, Model model) {
		int interviewId=Integer.parseInt(request.getParameter("interviewId"));
		String status=request.getParameter("status");
		InterViewBean ib=new InterViewBean();
		ib.setInterviewid(interviewId);
		model.addAttribute("status",status);
		model.addAttribute("bookedSlotDetails",externalUserDao.getBookedSlotDetailsOninterviewID(ib));
		model.addAttribute("skillsNameList",externalUserDao.getSkillsName(interviewId));
		model.addAttribute("domainList",externalUserDao.getDomainLst(ib));
		return "admininterviewsuccess";
	}
	/**
	 * This method is used to delete interview and it's details based on input like 
	 * interviewId
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiadminCancelInterview.do")	
	public String tmiadminCancelInterview(HttpServletRequest request, Model model) {			
		String interviewId=request.getParameter("interviewId");
		int userId=tmiUtil.getAdminUserId();		
	adminDAO.deleteadminbookingInteviews(userId,interviewId);
		return "redirect:/tmiAdmininterviewBooking.do";		 
	}
	/**
	 * This method is to show the interview Slots based on inputs like 
	 * selectDate,addStartHour
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({Constants.ROLE_EXTERNAL,Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/addInterviewSlot")	
	public String adminaddInterviewSlot(HttpServletRequest request, Model model) {	
		String  applDate="";
		int adminuserId= tmiUtil.getAdminUserId();
		int interviewId =dropDownDAO.checkAdminInterviewID(adminuserId);
		int userId=dropDownDAO.getUserIdBasedOnInterviewId(interviewId);
		String appTimeZone=dropDownDAO.getTimeZone(Integer.toString(userId));
		schedulerDAO.updateLapsedSchedule();
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		String currGmtDate = tmiUtil.getCurrentGmtDate();
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
		c.add(Calendar.DATE, 2);  // number of days to add
		currGmtDate = sdformat.format(c.getTime());
		
		String applDate1 =request.getParameter("selectDate")==null ? currGmtDate : request.getParameter("selectDate");
		String slotId=request.getParameter("addStartHour") ==null ?"00:00:00" : request.getParameter("addStartHour");	
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));
	try{	
		Date date = new SimpleDateFormat("dd-MM-yyyy").parse(applDate1);
		 applDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
	}catch(Exception e){
		
	}
	String stimeFormat = applDate.concat(" "+slotId);
			String evalList= schedulerDAO.getApplicantEvaluatorMatch(interviewId,externalUserDao.getRoleID(interviewId));
			List evalAvaiSlotsList = new ArrayList();
			evalAvaiSlotsList=adminDAO.getEvalListBasedNoSlotesGivenTime(evalList,stimeFormat,appTimeZone);
			int aplSlotLstCount=evalAvaiSlotsList.size();
			if (aplSlotLstCount > 0) {	
				int firstResult = pageNo * pageLimit - pageLimit;
				model.addAttribute("applicantAvailabeSlots", evalAvaiSlotsList.subList(firstResult, (aplSlotLstCount > pageNo * pageLimit) ? firstResult
								+ pageLimit : firstResult + (aplSlotLstCount - (pageNo - 1) * pageLimit)));	
				model.addAttribute("pageNav", pageNavigator.buildPageNav("#", aplSlotLstCount, pageNo, pageLimit, 2));
			} 
			else {
				model.addAttribute("nodata", "nodetails");
			}
			model.addAttribute("interviewSlotId", dropDownDAO.getSlotIdbasedOnslottime(slotId));	
			model.addAttribute("interviewId", interviewId);	
			model.addAttribute("userid", request.getParameter("userid"));
			model.addAttribute("paginationList",adminDAO.getPaginationData());
			model.addAttribute("evaluatorscheduleid", schedulerDAO.getEvaluatorscheduleid(interviewId));
			model.addAttribute("aplSlotLstCount",aplSlotLstCount);
			model.addAttribute("pageLimits",pageLimit);
			model.addAttribute("applDate",applDate1);			
			model.addAttribute("timeSlotList",schedulerDAO.getTimeSlots());
			model.addAttribute("gmtdate",applDate1);
			model.addAttribute("slotId",slotId);
			model.addAttribute("slotTo",dropDownDAO.getSlotToTime(slotId));
		return "adminaddslotforInterview";		 
	}
	/**
	 * This method is to show interview Slots
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({Constants.ROLE_EXTERNAL,Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiadminaddInterviewSlot")	
	public String tmiadminaddInterviewSlot1(HttpServletRequest request, Model model) {			
		String slotId=request.getParameter("addStartHour");
		String adate=request.getParameter("selectDate");
	try{	
		Date date = new SimpleDateFormat("dd-MM-yyyy").parse(adate);
		adate = new SimpleDateFormat("yyyy-MM-dd").format(date);
	}catch(Exception e){
		
	}
		String stimeFormat = adate.concat(" "+slotId);
		int adminuserId= tmiUtil.getAdminUserId();
		int interviewId =dropDownDAO.checkAdminInterviewID(adminuserId);
	String evalList= schedulerDAO.getApplicantEvaluatorMatch(interviewId,externalUserDao.getRoleID(interviewId));	
		return "adminaddslotforInterview";		 
	}
	/**
	 * This method is to used to create Slot and book interview to applicant based on inputs
	 * like scheduledate,slotscheduleId,slotscheduleUserId 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/tmiadminaddSlotScheduleId.do/{scheduledate}/{slotscheduleId}/{slotscheduleUserId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean tmiadminaddSlotScheduleId(@PathVariable String scheduledate,@PathVariable String slotscheduleId,@PathVariable String slotscheduleUserId ) {
		boolean scheduleStatus = true;
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat formatterToDB = new SimpleDateFormat("yyyy-MM-dd");
		Date firstdate=null;
		try{
			 firstdate = formatter.parse(scheduledate);
			}catch(Exception e){
				e.printStackTrace();	
			}
		
		String strDate =formatterToDB.format(firstdate);
		String slottime= dropDownDAO.getSlotTime(slotscheduleId);
		String startTime=strDate.concat(" "+slottime);
		int adminuserId= tmiUtil.getAdminUserId();
		int interviewId1 =dropDownDAO.checkAdminInterviewID(adminuserId);
		int appuserId=dropDownDAO.getUserIdBasedOnInterviewId(interviewId1);
		String timezone=dropDownDAO.getTimeZone(Integer.toString(appuserId));
	int count=adminDAO.checkSlot(startTime,slotscheduleUserId,slotscheduleId,timezone);
	if(count == 0){	
	int slotscheduleid=	adminDAO.adminaddEvaluatorSchedule(slotscheduleId,strDate,Integer.parseInt(slotscheduleUserId),timezone);
		adminDAO.checkaddauditTable(slotscheduleid, Constants.AdminSlotCreate,Constants.AdminSlotCreate,tmiUtil.getAdminUserId());
	scheduleStatus=schedulerDAO.updateSlotScheduleIdStatus(Integer.toString(slotscheduleid),interviewId1);
	}else {
		scheduleStatus=false;
	}
	return scheduleStatus;
	}
	/**
	 * This method is to used to cancel applicant interview based on inputs
	 * like myinterviewid,mytmiid,appuserid,cancelslotReson
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/cancelApplicantInterView.do/{myinterviewid}/{mytmiid}/{appuserid}/{cancelslotReson}", method = RequestMethod.GET)
	@ResponseBody 
	public boolean requestAdminForSlot(@PathVariable int myinterviewid,@PathVariable String mytmiid,@PathVariable int appuserid,@PathVariable String cancelslotReson) {
		int loggeduserid = tmiUtil.getAdminUserId();
        return adminDAO.cancelApplicantInterviewid(myinterviewid,mytmiid,appuserid,loggeduserid,cancelslotReson);
	}
	/**
	 * This method is to show all applicant booked interviews 
	 * @param request
	 * @param model:dashboardsearchtypelkp
	 * @return 
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiAdmininterviewCancelBooking.do")
	public String admininterviewCancelBooking(HttpServletRequest request, Model model) {		
		model.addAttribute("dashboardsearchtypelkp",dropDownDAO.getdashboardsearchtypelkp());		  		
		return "bookedInterviewDetailsAllApplicants";
	}
	
	/**
	 * This method is to show all applicant booked interviews based on inputs like
	 * evaluatorName,dashboard  
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({Constants.ROLE_ADMIN,Constants.ROLE_CCR})
	@RequestMapping(value = "/tmiAdmAllAplicantBookedInterviewsBasedonSearch")
	public String aplicantBookedInterviewsDetailsBasedonSearch(HttpServletRequest request, Model model) {	
		String enterstring=request.getParameter("evaluatorName");
		int dashboard=request.getParameter("dashboard").isEmpty() ? 0 : Integer.parseInt(request.getParameter("dashboard"));
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));		
		List applicantbookedinterviewsList = new ArrayList();
		if(dashboard==0){
			applicantbookedinterviewsList=adminDAO.getAllAplicantBookedInterviews();
		}else if(dashboard == 1){
			applicantbookedinterviewsList=adminDAO.getAplicantBookedInterviewBasedOnTmiId(enterstring);
		}else if(dashboard == 2){
			applicantbookedinterviewsList=adminDAO.getAplicantBookedInterviewsBasedOnApplicantName(enterstring);
		}else if(dashboard == 3){
			applicantbookedinterviewsList=adminDAO.getAplicantBookedInterviewsBasedOnEvaluatorName(enterstring);
		}
		int appbookedinterviewsListCount =applicantbookedinterviewsList.size();
		if (appbookedinterviewsListCount > 0) {
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("userDetails", applicantbookedinterviewsList.subList(firstResult, (appbookedinterviewsListCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult + (appbookedinterviewsListCount - (pageNo - 1) * pageLimit)));	
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#", appbookedinterviewsListCount, pageNo, pageLimit, 2));

		} 
		else 
		{
			model.addAttribute("nodata", "nodetails");
		}			
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		model.addAttribute("userDetailsListCount",appbookedinterviewsListCount);
		model.addAttribute("pageLimit",pageLimit);
		model.addAttribute("enterstring",enterstring);
		model.addAttribute("dashboard",dashboard);
	return "bookedInterviewDetailsAllApplicantsTable";
	}
	/**
	 * This method is to show Applicant Reschedule Available Slots based on inputs like
	 * interviewId,userid,startTimestamp,startDate,reschedule
	 * @param request
	 * @param model
	 * @return 
	 */
	@Secured({ "ROLE_ADMIN","ROLE_CCR"})
	@RequestMapping(value = "/admRescheduleApplicantInterview")
	public String getAdminRescheduleApplicantAvailableSlots(HttpServletRequest request, Model model) {
		schedulerDAO.updateLapsedSchedule();
		int interviewId=Integer.parseInt(request.getParameter("interviewId"));
		int userid=Integer.parseInt(request.getParameter("userid"));
		String startTimestamp =request.getParameter("startTimestamp") ;
		String startDate =request.getParameter("startDate") ;
		String reschedule=request.getParameter("reschedule");
		int defaultPageLimit=adminDAO.getDefaultPageLimit();
		int pageNo=request.getParameter("pageNo")==null ? 1 : Integer.parseInt(request.getParameter("pageNo"));
		int pageLimit=request.getParameter("pageLimit")==null ? defaultPageLimit : Integer.parseInt(request.getParameter("pageLimit"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat ndf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar c = Calendar.getInstance();		
		try {
			c.setTime(ndf.parse(startDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String apnewDate = sdf.format(c.getTime());
		List appcntAvaiSlotsList = new ArrayList();			
		appcntAvaiSlotsList=adminDAO.getAdminRescheduleApplicantAvailableSlots(schedulerDAO.getApplicantEvaluatorMatch(interviewId,externalUserDao.getRoleID(interviewId)),userid,apnewDate);	
		int aplSlotLstCount=appcntAvaiSlotsList.size();		
		if (aplSlotLstCount > 0) {	
			int firstResult = pageNo * pageLimit - pageLimit;
			model.addAttribute("applicantAvailabeSlots", appcntAvaiSlotsList.subList(firstResult, (aplSlotLstCount > pageNo * pageLimit) ? firstResult
							+ pageLimit : firstResult + (aplSlotLstCount - (pageNo - 1) * pageLimit)));	
			model.addAttribute("pageNav", pageNavigator.buildPageNav("#", aplSlotLstCount, pageNo, pageLimit, 2));
		} 
		else {
			model.addAttribute("nodata", "nodetails");
		}
		model.addAttribute("reschedule", reschedule);	
		model.addAttribute("interviewId", interviewId);	
		model.addAttribute("userid", request.getParameter("userid"));
		model.addAttribute("paginationList",adminDAO.getPaginationData());
		model.addAttribute("evaluatorscheduleid", schedulerDAO.getEvaluatorscheduleid(interviewId));
		model.addAttribute("aplSlotLstCount",aplSlotLstCount);
		model.addAttribute("pageLimits",pageLimit);
		model.addAttribute("applDate",startDate);
		model.addAttribute("startTimestamp",startTimestamp);
		return "adminApplicantSlotReschedule";
	    
	}
	
}