package com.testmyinterview.portal;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.testmyinterview.portal.bean.InterViewBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.dao.ExternalUserDao;
import com.testmyinterview.portal.dao.ProfileDAO;
import com.testmyinterview.portal.dao.SchedulerDAO;
import com.testmyinterview.portal.util.TmiUtil;

@Controller
public class SchedulerController {
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private SchedulerDAO schedulerDAO;
	@Autowired
	private ExternalUserDao externalUserDao;
	@Autowired
	private TmiUtil tmiUtil;

	private static final Logger logger = LoggerFactory
			.getLogger(SchedulerController.class);

	/**
	 * This method is to show Evaluator WeekView Slots based on inputs like weekIncr,interviewerId
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getScheduleForWeek.do/{weekIncr}/{interviewerId}", method = RequestMethod.GET)
	@ResponseBody
	public List getScheduleForWeek(@PathVariable int weekIncr,
			@PathVariable int interviewerId) {
		return schedulerDAO.getScheduleForWeek(weekIncr, interviewerId);
	}

	/**
	 * This method is to show Evaluator MonthView Slots based on input like
	 * monthIncr,interviewerId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getScheduleForCurrentMonth.do/{monthIncr}/{interviewerId}", method = RequestMethod.GET)
	@ResponseBody
	public List getScheduleForCurrentMonth(@PathVariable int monthIncr,
			@PathVariable int interviewerId) {
		return schedulerDAO
				.getScheduleForCurrentMonth(monthIncr, interviewerId);
	}

	/**
	 * This method is to show Applicant Available WeekSlots for interview booking based on inputs like weekIncr,interviewerId
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/getApplicantAvailabeSlotsForWeek.do/{weekIncr}/{interviewerId}", method = RequestMethod.GET)
	@ResponseBody
	public List getApplicantAvailabeSlotsForWeek(@PathVariable int weekIncr,
			@PathVariable int interviewerId) {
		InterViewBean ib = new InterViewBean();
		ib.setUserid(interviewerId);
		return schedulerDAO.getApplicantAvailabeSlotsForWeek(weekIncr,
				schedulerDAO.getApplicantEvaluatorMatch(externalUserDao
						.getInterViewID(ib),externalUserDao
						.getRoleID(ib.getInterviewid())), tmiUtil.getUserId());
	}

	/**
	 * This method is to update Slot ScheduleId Status based on inputs like scheduleId
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateSlotScheduleIdStatus.do/{scheduleId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateSlotScheduleIdStatus(@PathVariable String scheduleId) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String loggedUserName = auth.getName();
		ProfileBean profileBean = new ProfileBean();
		profileBean.setEmailaddress(loggedUserName);
		int userId = profileDAO.getUserId(profileBean);
		InterViewBean ib = new InterViewBean();
		ib.setUserid(userId);
		int interviewId = externalUserDao.getInterViewID(ib);
		return schedulerDAO.updateSlotScheduleIdStatus(scheduleId, interviewId);
	}

	/**
	 * This method is to inserting or updating Scheduler timings into DB based on inputs like
	 * slotid,scheduledate,interviewerId
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/updateEvaluatorScheduleSlots.do/{slotid}/{scheduledate}/{interviewerId}", method = RequestMethod.GET)
	@ResponseBody
	public boolean updateEvaluatorScheduleSlots(HttpServletRequest request,
			@PathVariable int slotid, @PathVariable String scheduledate,
			@PathVariable int interviewerId) {
		List<Map<String, String>> profileDetails = (List<Map<String, String>>) request
				.getSession().getAttribute("mySesProfile");
		String timezone = profileDetails.get(0).get("timezone");
		return schedulerDAO.updateEvaluatorScheduleSlots(slotid, scheduledate,
				interviewerId, timezone);
	}
	/**
	 * This method is to used to request for slot based on inputs like
	 * scheduledate
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/requestAdminForSlot.do/{scheduledate}", method = RequestMethod.GET)
	@ResponseBody 
	public int requestAdminForSlot(@PathVariable String scheduledate) {
		int interviewId = tmiUtil.getInterviewId();
		return schedulerDAO.requestAdminForSlot(scheduledate, interviewId);
	}

}