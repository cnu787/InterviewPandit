package com.testmyinterview.portal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
import com.testmyinterview.portal.util.ASyncService;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class InternalController {

	private static final Logger logger = LoggerFactory
			.getLogger(InternalController.class);

	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private SchedulerDAO schedulerDAO;
	@Autowired
	private DropDownDAO dropDownDAO;
	@Autowired
	private TmiUtil tmiUtil;
	@Autowired
	private FeedBackBean feedBackBean;
	@Autowired
	private InternalDAO internalDAO;
	@Autowired
	private ExternalUserDao externalUserDao;
	@Autowired
	private AdminDAO adminDAO;
	@Autowired
	private ASyncService asyncService;
	@Value("${smsurl}")
	private String smsurl;
	@Value("${bookingConfmsgEval}")
	private String bookingConfmsgEval;
	@Value("${support}")
	private String support;
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private VelocityEngine velocityEngine;

	/**
	 * This method is used to show evaluator Landing page
	 * 
	 * @param request
	 * @param model:profileStatus
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/internalHome.do")
	public String internalHome(HttpServletRequest request, Model model) {
		String returnUrl = "redirect:/profileAboutMe.do";
		int userId = tmiUtil.getUserId();
		int profileStatus = profileDAO.getProfileStatus(userId);
		returnUrl = "redirect:/myCompleteProfile.do";
		model.addAttribute("profileStatus", profileStatus);
		request.getSession().setAttribute("myProfileStatus", profileStatus);

		return returnUrl;
	}

	/**
	 * This method is used to show evaluator calendar 
	 * 
	 * @param request
	 * @param model:currentGmtTime,timeSlotList,interviewerId
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/internalCalender.do")
	public String internalCalender(HttpServletRequest request, Model model) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		schedulerDAO.updateLapsedSchedule();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int interviewerId = profileDAO.getUserId(profileBean);
		List timeSlotList = schedulerDAO.getTimeSlots();
		model.addAttribute("currentGmtTime", tmiUtil.getCurrentGmtTime());
		model.addAttribute("timeSlotList", timeSlotList);
		model.addAttribute("interviewerId", interviewerId);
		return "internalcalender";
	}

	/**
	 * This method is used to inserting Scheduler timings into DB by evaluator
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/scheduleTimeSlots.do")
	public String internalCalender(HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		List<Map<String, String>> profileDetails = (List<Map<String, String>>) request
				.getSession().getAttribute("mySesProfile");
		String timezone = profileDetails.get(0).get("timezone");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String[] slotids = request.getParameterValues("addStartHour[]");
		String[] weekdays = request.getParameterValues("checkedweekdays[]");
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat formatterToDB = new SimpleDateFormat("yyyy-MM-dd");
		Date firstdate = null, lastdate = null;
		try {
			firstdate = formatter.parse(startDate);
			lastdate = formatter.parse(endDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String strDate = formatterToDB.format(firstdate);
		String enDate = formatterToDB.format(lastdate);
		List<Map<String, Object>> evaluatorSlots = schedulerDAO
				.getEvaluatorSlots(strDate, enDate, userId);

		Calendar start = Calendar.getInstance();
		start.setTime(firstdate);
		Calendar end = Calendar.getInstance();
		end.setTime(lastdate);
		while (!start.after(end)) {
			Date targetDay = start.getTime();
			for (String days : weekdays) {
				if (targetDay.toString().contains(days)) {
					for (String id : slotids) {
						String date = formatterToDB.format(targetDay);
						boolean slotcheck = false;
						for (Map<String, Object> map : evaluatorSlots) {
							if (((map.get("slotid").toString()).equals(id))
									&& ((map.get("scheduledDate").toString())
											.equals(date))) {
								slotcheck = true;
								break;
							}
						}
						if (slotcheck == false) {
							schedulerDAO.addEvaluatorSchedule(id, date, userId,
									timezone);

						}
					}
				}
			}

			start.add(Calendar.DATE, 1);
		}

		return "redirect:/internalCalender.do";
	}

	/**
	 * This method is used to reject interview based on inputs like
	 * slotscheduleid,reason,myinterviewid,starttime
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/rejectInterView.do/{slotscheduleid}/{reason}/{myinterviewid}/{starttime}", method = RequestMethod.GET)
	@ResponseBody
	public void rejectInterView(@PathVariable int slotscheduleid,
			@PathVariable String reason, @PathVariable int myinterviewid,
			@PathVariable String starttime) {
		boolean rescheduleStatus = schedulerDAO.rejectedSlotComment(
				slotscheduleid, reason, myinterviewid, tmiUtil.getUserId(),
				Constants.internalUserTypeId, starttime);
		if (rescheduleStatus == true) {
			InterViewBean ib = new InterViewBean();
			ib.setInterviewid(myinterviewid);
			List<Map<String, String>> userDetails = externalUserDao
					.getBookedSlotDetailsOninterviewID(ib);
			asyncService.sendSMSToCustomers(smsurl,
					userDetails.get(0).get("evalmobile"), bookingConfmsgEval);
			CustomTemplateBean cbEval = new CustomTemplateBean();
			cbEval.setEmailAddress(userDetails.get(0).get("evalemailid"));

			cbEval.setSenderEmailId(support);
			cbEval.setTemplateName("evalinterviewbookingconfirmation.vm");
			cbEval.setMailSubject(userDetails.get(0).get("interviewtypename")
					+ " Interview Booking Confirmation");
			cbEval.setUserDetails(userDetails);
			cbEval.setSkillsDetails(externalUserDao.getInterviewSkills(ib));
			cbEval.setDomainList(externalUserDao.getDomainLst(ib));
			asyncService.sendEmail(cbEval, mailSender, velocityEngine);
		}

	}

	/**
	 * This method is used to accepts interview based on input like 
	 * slotId
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/acceptInterView.do/{slotId}", method = RequestMethod.GET)
	@ResponseBody
	public void acceptInterView(@PathVariable int slotId) {
		schedulerDAO.changeSlotStatus(Constants.slotStatusAcceptId, slotId);
	}
	/**
	 * This method is used to show evaluator payments 
	 * 
	 * @param request
	 * @param model:mypaymentList
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/myPayment.do")
	public String myPayment(HttpServletRequest request, Model model) {
		model.addAttribute("mypaymentList",
				internalDAO.getMyPaymentList(tmiUtil.getUserId()));
		return "myPayment";

	}

	/**
	 * This method is used to request to admin for payments based on input like monthname
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/requestforPayment.do")
	public String requestforPayment(HttpServletRequest request, Model model) {
		String monthname = request.getParameter("monthname");
		int userId = tmiUtil.getUserId();
		internalDAO.updatePaymentStatus(Constants.requestPaymentSendId,
				monthname, userId);
		return "redirect:/myPayment.do";
	}

	/**
	 * This method is used to show month wise payments in details based on inputs like 
	 * monthname	
	 * @param request
	 * @param model:mypaymentList,monthname
	 * @return
	 */
	@Secured({ Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/myPaymentDetailsOnMonth")
	public String myPaymentDetailsOnMonth(HttpServletRequest request,
			Model model) {
		String monthname = request.getParameter("monthname");
		model.addAttribute("monthname", monthname);
		model.addAttribute("mypaymentList", internalDAO
				.getMyPaymentListOnMonth(tmiUtil.getUserId(), monthname));
		return "mypaymentOnMonth";

	}

	/**
	 * This method is used to show evaluator landing page
	 * 
	 * @param request
	 * @param model:
	 * @return
	 */
	@Secured({ Constants.ROLE_INTERNAL })
	@RequestMapping(value = "/internalLanding.do")
	public String externalLanding(HttpServletRequest request, Model model) {
		int userId = tmiUtil.getUserId();
		ProfileBean pb = new ProfileBean();
		pb.setUserId(userId);
		List externalProfile = profileDAO.getUserDetails(pb);
		model.addAttribute("profileStatus", profileDAO.getProfileStatus(userId));
		request.getSession().setAttribute("eLearningStatus",
				externalUserDao.getELearningStatus(userId));
		model.addAttribute("calendarCount",
				schedulerDAO.getCalendarCount(userId));
		model.addAttribute("interviewCount",
				schedulerDAO.getInterviewCount(userId));
		model.addAttribute("feedbackCount",
				schedulerDAO.getFeedbackCount(userId));
		model.addAttribute("referralCount",
				externalUserDao.getReferralCount(pb));
		model.addAttribute("profileReportCount",
				externalUserDao.getEvalProfileReport(userId));
		request.getSession().setAttribute("mySesProfile", externalProfile);
		request.getSession().setAttribute("userLastSession",
				profileDAO.getUserLastSession(userId));
		return "internallanding";
	}

	/**
	 * This method is used to show interview feed back
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/interviewfeedback.do")
	public String interviewfeedback(HttpServletRequest request, Model model) {
		return "interviewfeedback";
	}

	/**
	 * This method is used to update applicant knowledge feedback insert and update based on based on input like interview id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/evaluatorsFeedBackUpdateOnKnowledge")
	public String evaluatorsFeedBackUpdateOnKnowledge(
			HttpServletRequest request, Model model) {
		FeedBackBean fb = new FeedBackBean();
		String interviewId = request.getParameter("interviewId");
		fb.setInterViewId(Integer.parseInt(interviewId));
		fb.setEvalId(Integer.parseInt(request.getParameter("educationalId")));
		fb.setEvalRating(Integer.parseInt(request
				.getParameter("educationalRating")));
		fb.setEvalNote(request.getParameter("educationalNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("interestId")));
		fb.setEvalRating(Integer.parseInt(request
				.getParameter("interestRating")));
		fb.setEvalNote(request.getParameter("interestNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("technicalId")));
		fb.setEvalRating(Integer.parseInt(request
				.getParameter("technicalRating")));
		fb.setEvalNote(request.getParameter("technicalNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("generalId")));
		fb.setEvalRating(Integer.parseInt(request.getParameter("generalRating")));
		fb.setEvalNote(request.getParameter("generalNotes"));
		internalDAO.updateFeedBack(fb);
		request.getSession().setAttribute("interViewId", interviewId);
		return "redirect:/evaluatorsknowledge";
	}

	/**
	 * This method is used to update applicant skills feedback insert and update based on based on input like interview id
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/evaluatorsFeedBackUpdateOnSkills")
	public String evaluatorsFeedBackUpdate(HttpServletRequest request,
			Model model) {
		FeedBackBean fb = new FeedBackBean();
		fb.setInterViewId(Integer.parseInt(request.getParameter("interviewId")));
		fb.setEvalId(Integer.parseInt(request.getParameter("problemId")));
		fb.setEvalRating(Integer.parseInt(request.getParameter("problemRating")));
		fb.setEvalNote(request.getParameter("problemNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("taskId")));
		fb.setEvalRating(Integer.parseInt(request.getParameter("taskRating")));
		fb.setEvalNote(request.getParameter("taskNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("othersteamID")));
		fb.setEvalRating(Integer.parseInt(request
				.getParameter("othersteamRating")));
		fb.setEvalNote(request.getParameter("othersteamNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("relationsId")));
		fb.setEvalRating(Integer.parseInt(request
				.getParameter("relationsRating")));
		fb.setEvalNote(request.getParameter("relationsNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("emergencyId")));
		fb.setEvalRating(Integer.parseInt(request
				.getParameter("emergencyRating")));
		fb.setEvalNote(request.getParameter("emergencyNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("responsibleId")));
		fb.setEvalRating(Integer.parseInt(request
				.getParameter("responsibleRating")));
		fb.setEvalNote(request.getParameter("responsibleNotes"));
		internalDAO.updateFeedBack(fb);
		request.getSession().setAttribute("interViewId",
				request.getParameter("interviewId"));
		return "redirect:/evaluatorsSkills";
	}

	/**
	 * This method is used to update applicant Attitudes feedback insert and update based on input like interview id
	 *
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/evaluatorsFeedBackUpdateOnAttitudes")
	public String evaluatorsFeedBackUpdateOnAttitudes(
			HttpServletRequest request, Model model) {
		FeedBackBean fb = new FeedBackBean();
		fb.setInterViewId(Integer.parseInt(request.getParameter("interviewId")));
		fb.setEvalId(Integer.parseInt(request.getParameter("apperanceId")));
		fb.setEvalRating(Integer.parseInt(request
				.getParameter("apperanceRating")));
		fb.setEvalNote(request.getParameter("apperanceNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("bodyId")));
		fb.setEvalRating(Integer.parseInt(request.getParameter("bodyRating")));
		fb.setEvalNote(request.getParameter("bodyNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("verbalId")));
		fb.setEvalRating(Integer.parseInt(request.getParameter("verbalRating")));
		fb.setEvalNote(request.getParameter("verbalNotes"));
		internalDAO.updateFeedBack(fb);
		fb.setEvalId(Integer.parseInt(request.getParameter("closingId")));
		fb.setEvalRating(Integer.parseInt(request.getParameter("closingRating")));
		fb.setEvalNote(request.getParameter("closingNotes"));
		internalDAO.updateFeedBack(fb);
		request.getSession().setAttribute("interViewId",
				request.getParameter("interviewId"));
		return "redirect:/evaluatorsAttitudes";
	}

	/**
	 * This method is used to show evaluator Instructions
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/evaluatorInstructions")
	public String evaluatorInstructions(HttpServletRequest request, Model model) {

		return "evaluatorInstructions";
	}

	/**
	 * This method is used to show applicant Impression based on input like interview id
	 * 
	 * @param request
	 * @param model:interviewDetails,interviewId
	 * @return
	 */
	@RequestMapping(value = "/evaluatorImpression")
	public String evaluatorImpression(HttpServletRequest request, Model model) {
		String interviewId = request.getParameter("interviewId") == null ? ""
				: request.getParameter("interviewId");
		if (interviewId.isEmpty()) {
			interviewId = (String) request.getSession().getAttribute(
					"interViewId");
			request.getSession().removeAttribute("interViewId");
		}
		model.addAttribute("interviewDetails", internalDAO
				.geteditFeedBackDetails(Integer.parseInt(interviewId),
						Constants.feedbackOverallImpressionId));
		model.addAttribute("interviewId", interviewId);

		return "evaluatorimpressioninterview";
	}

	/**
	 * This method is used to update applicant Impression feedback  insert and update based on input like interview id
	 *  
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/evaluatorFinalUpdate")
	public String evaluatorFinalUpdate(HttpServletRequest request, Model model) {
		FeedBackBean fb = new FeedBackBean();
		fb.setInterViewId(Integer.parseInt(request.getParameter("interviewId")));
		fb.setEvalId(Integer.parseInt(request.getParameter("impression")));
		internalDAO.updateImpression(fb);
		request.getSession().setAttribute("interViewId",
				request.getParameter("interviewId"));
		return "redirect:/evaluatorImpression";
	}

	/**
	 * This method is used to show evaluator feedback based on input like interview id
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/evaluatorSummary")
	public String evaluatorFeedbacKSummary(HttpServletRequest request,
			Model model) {
		String returnUrl = "evaluatorSummary";
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		model.addAttribute("interviewId", interviewId);
		int industryid = profileDAO.getInterviewIndustryId(interviewId);
		model.addAttribute("industryid", industryid);
		int usertypeid = 2;
		model.addAttribute("interviewDetailsKnowledge", internalDAO
				.getEvaluatorFeedBackDetails(interviewId,
						Constants.feebbcakApplicantId, usertypeid, industryid));
		int count = dropDownDAO.getFeedbackCount(usertypeid, interviewId);
		if (count > 1) {
			returnUrl = "redirect:/interviewFeedBack?interviewId="
					+ interviewId;
		}
		return returnUrl;
	}
	/**
	 * This method is used to upadate  evaluator feedback based on input like interview id
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateFeedback")
	public String updateFeedback(HttpServletRequest request, Model model) {
		int interviewId = Integer.parseInt(request.getParameter("interviewId"));
		FeedBackBean fb = new FeedBackBean();
		int feedBackId = request.getParameter("feedbackDone").isEmpty() ? 0
				: Integer.parseInt(request.getParameter("feedbackDone"));
		fb.setFeedBackStatusId(feedBackId);
		int usertypeid = 2;
		fb.setUserType(usertypeid);
		if (feedBackId == 1) {
			internalDAO.updateinterViewStatus(interviewId);
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
	 * This method is used to show evaluator Interview History
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/interviewHistory.do")
	public String getInterviewHistory(HttpServletRequest request, Model model) {
		schedulerDAO.updateLapsedSchedule();
		int evalId = tmiUtil.getUserId();
		model.addAttribute("myScheduleBookings",
				schedulerDAO.getMyScheduleBookings(evalId));
		return "interviewHistory";
	}

	/**
	 * This method is used to get evaluator reject page
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/evalRejectedSlotReason")
	public String evalRejectedSlotReason(HttpServletRequest request, Model model) {
		int slotId = Integer.parseInt(request.getParameter("slotId"));
		model.addAttribute("slotId", slotId);
		return "evalRejectedSlot";
	}

	/**
	 * This method is used to closed interview based on inputs like interviewid
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Secured({ "ROLE_INTERNAL" })
	@RequestMapping(value = "/evalClosedInterView.do/{interviewid}", method = RequestMethod.GET)
	@ResponseBody
	public void evalClosedInterView(@PathVariable int interviewid) {
		adminDAO.updateInterviewStatus(interviewid);
	}

}