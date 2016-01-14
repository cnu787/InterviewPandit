package com.testmyinterview.portal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.testmyinterview.portal.util.TmiUtil;
import com.testmyinterview.portal.dao.ExternalUserDao;

public class SchedulerDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TmiUtil tmiUtil;
	@Autowired
	private ExternalUserDao externalUserDao;

	/**
	 * This Method is used to get the evaluators  matching skills of applicant
	 * 
	 * @param interviewId
	 * @return
	 */
	public String getApplicantEvaluatorMatch(int interviewId,int roleid) {
		String applicantEvaluatorMatchQuery = "call ApplicantEvaluatorMatch(?,?)";

		List<Map<String, Object>> evalList = jdbcTemplate.queryForList(
				applicantEvaluatorMatchQuery, interviewId, roleid);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < evalList.size(); i++) {
			if (sb.length() > 0) {
				sb.append(',');
			}
			sb.append(evalList.get(i).get("userid"));
		}

		return sb.toString();
	}
	
	/**
	 * This Method is used to get the evaluators  matching skills of applicant irrespective of technicalskills
	 * 
	 * @param interviewId
	 * @return
	 */
	public String getApplicantEvaluatorMatchForDir(int interviewId,int roleid) {
		String applicantEvaluatorMatchQuery = "call ApplicantEvaluatorMatchForDir(?,?)";

		List<Map<String, Object>> evalList = jdbcTemplate.queryForList(
				applicantEvaluatorMatchQuery, interviewId, roleid);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < evalList.size(); i++) {
			if (sb.length() > 0) {
				sb.append(',');
			}
			sb.append(evalList.get(i).get("userid"));
		}

		return sb.toString();
	}


	/**
	 * This Method is used to get Applicant available week solts 
	 * 
	 * @param weekIncr
	 * @param interviewerId
	 * @param userId
	 * @return week Slots List
	 */
	@Transactional
	public List getApplicantAvailabeSlotsForWeek(int weekIncr,
			String interviewerId, int userId) {
		String scheduleWeekQuery = "call ApplicantAvailabeSlotsForWeek(?,?,?);";
		List weekSlotsList = jdbcTemplate.queryForList(scheduleWeekQuery,
				weekIncr, interviewerId, userId);
		return weekSlotsList;
	}

	/**
	 * This Method is used to get Applicant available solts 
	 * 
	 * @param interviewerId
	 * @param userId
	 * @param applDate
	 * @return Slots List
	 */
	@Transactional
	public List getApplicantAvailabeSlots(String interviewerId, int userId,
			String applDate) {
		String scheduleWeekQuery = "call getApplicantAvailabeSlots(?,?,?);";
		List weekSlotsList = jdbcTemplate.queryForList(scheduleWeekQuery,
				interviewerId, userId, applDate);
		return weekSlotsList;
	}

	/**
	 * This Method is used to get Evaluator Week Schedule slots 
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@Transactional
	public List getScheduleForWeek(int weekIncr, int interviewerId) {
		String scheduleWeekQuery = "SELECT  s.slotscheduleid, s.slotid, DATEDIFF(s.scheduledDate,adddate(CURDATE(),?)) AS dayindex,"
				+ " s.status,'' as name FROM slotschedule s WHERE datediff(scheduledDate,adddate(CURDATE(),?)) >= 0"
				+ " AND datediff(scheduledDate,adddate(CURDATE(),?)) < 7 "
				+ " AND s.InterviewerId =?";
		int weekIncrFactor = 7 * weekIncr;
		List weekSlotsList = jdbcTemplate.queryForList(scheduleWeekQuery,
				weekIncrFactor, weekIncrFactor, weekIncrFactor, interviewerId);
		return weekSlotsList;
	}

	/**
	 * This Method is used to get Evaluator month Schedule slots 
	 * @param monthIncr
	 * @param interviewerId
	 * @return
	 */
	@Transactional
	public List getScheduleForCurrentMonth(int monthIncr, int interviewerId) {
		String scheduleCurrentMonthQuery = "SELECT slotid, Day(scheduledDate) as dayindex FROM"
				+ " slotschedule WHERE MONTH(scheduledDate) = MONTH( CURDATE()) +"
				+ monthIncr
				+ " AND YEAR( scheduledDate ) = YEAR( CURDATE( ) ) AND scheduledDate >= CURDATE() AND "
				+ "find_in_set(InterviewerId,?) ORDER BY scheduledDate DESC";
		List monthSlotsList = jdbcTemplate.queryForList(
				scheduleCurrentMonthQuery, interviewerId);
		return monthSlotsList;
	}

	/**
	 * This Method is used to get Time slots from DataBase
	 * 
	 * @return time slots
	 */
	@Transactional
	public List getTimeSlots() {
		String scheduleTimeSlots = "SELECT slotid,slotfrom as startTime ,slotto as endTime,slottime from slotlkp";
		List timeSlotList = jdbcTemplate.queryForList(scheduleTimeSlots);
		return timeSlotList;
	}

	/**
	 * This Method is used to  add Evaluator Schedule into DataBase
	 * 
	 * @param slotid
	 * @param date
	 * @param userId
	 * @param timezone
	 */
	@Transactional
	public void addEvaluatorSchedule(String slotid, String date, int userId,
			String timezone) {
		String slotscheduleQuery = "INSERT INTO slotschedule(slotid,scheduledDate,InterviewerId,"
				+ "currenttimestamp,starttime,endtime)  VALUES ('"
				+ slotid
				+ "','"
				+ date
				+ "','"
				+ userId
				+ "','"
				+ tmiUtil.getCurrentGmtTime()
				+ "',convert_tz((select concat('"
				+ date
				+ "',' ',slkp.slottime) from slotlkp slkp where slkp.slotid='"
				+ slotid
				+ "'),'"
				+ timezone
				+ "','+00:00'),convert_tz((select concat('"
				+ date
				+ "',' ',slkp.slottime) from slotlkp slkp "
				+ " where slkp.slotid ='"
				+ slotid
				+ "'),'"
				+ timezone
				+ "','+01:00'))";
		jdbcTemplate.update(slotscheduleQuery);
	}

	/**
	 * This Method is used to update Evaluator SlotScheduleId Status 
	 * 
	 * @param scheduleId
	 * @param interviewId
	 * @return
	 */
	@Transactional
	public boolean updateSlotScheduleIdStatus(String scheduleId, int interviewId) {

		boolean scheduleStatus = true;

		String scheduleStatusQuery = "SELECT status FROM slotschedule WHERE slotscheduleid =?";
		int status = jdbcTemplate.queryForObject(scheduleStatusQuery,
				Integer.class, scheduleId);
		String scheduleCountQery = "SELECT count(*) FROM interview WHERE evaluatorscheduleid ="
				+ scheduleId + " AND interviewid =" + interviewId;
		int scheduleCount = jdbcTemplate.queryForObject(scheduleCountQery,
				Integer.class);
		String evaluatorscheduleidQery = "SELECT evaluatorscheduleid FROM interview WHERE  interviewid ="
				+ interviewId;
		String evaluatorscheduleid = jdbcTemplate.queryForObject(
				evaluatorscheduleidQery, String.class);
		if (evaluatorscheduleid != null
				&& !evaluatorscheduleid.equals(scheduleId)) {
			String updateschedulepreviuosStatusQuery = "UPDATE slotschedule SET  status =0, statustimestamp =null WHERE slotscheduleid =?";
			jdbcTemplate.update(updateschedulepreviuosStatusQuery,
					evaluatorscheduleid);
		}
		if (status == 0 || scheduleCount > 0) {
			String updatescheduleStatusQuery = "UPDATE slotschedule SET  status =1, statustimestamp ='"
					+ tmiUtil.getCurrentGmtTime() + "' WHERE slotscheduleid =?";
			jdbcTemplate.update(updatescheduleStatusQuery, scheduleId);
			String updateInterviewScheduleQuery = "UPDATE interview SET evaluatorscheduleid ="
					+ scheduleId + " WHERE interviewid =" + interviewId;
			jdbcTemplate.update(updateInterviewScheduleQuery);
			scheduleStatus = true;
		} else if (scheduleCount == 0) {

			scheduleStatus = false;
		}
		return scheduleStatus;
	}

	/**
	 *  This Method is used to get Evaluator SlotScheduleId  
	 * @param interviewId
	 * @return
	 */
	public List getEvaluatorscheduleid(int interviewId) {
		String evaluatorscheduleidQery = "SELECT slotscheduleid,slotid, WEEKDAY(scheduledDate) as dayindex, "
				+ " (yearweek(scheduledDate,1)-yearweek(utc_timestamp(),1))as weekindex FROM  slotschedule s,interview i WHERE slotscheduleid =i.evaluatorscheduleid "
				+ "and interviewid=" + interviewId;
		return jdbcTemplate.queryForList(evaluatorscheduleidQery);
	}

	/**
	 *  This Method is used to get Evaluator Schedule bookings
	 * @param interviewerId
	 * @return
	 */
	public List getMyScheduleBookings(int interviewerId) {
		String getMyScheduleBookingsQuery = "SELECT i.userid, i.interviewid,i.interviewtmiid,p.screenName ,"
				+ "i.designation,im.interviewtypename,s.slotscheduleid,i.status as interviewstatus,"
				+ "s.status as slotstatus,concat(date_format(scheduledDate,'%d %b'),"
				+ "' ',sl.slotfrom,'-',sl.slotto)as slottime,s.starttime,i.applicantfeedbackstatus,"
				+ "i.evaluatorfeedbackstatus,(SELECT status FROM userfeedback WHERE interviewid="
				+ "i.interviewid and usertypeid=2 limit 1)as status FROM interview i,slotschedule s,"
				+ "profile p,inteviewmodelkp im,slotlkp sl  WHERE i.evaluatorscheduleid=s.slotscheduleid "
				+ "and s.slotid=sl.slotid and p.userid=i.userid and i.interviewmodeid=im.interviewtypeid"
				+ " and s.InterviewerId=? and s.status in(2,3,4) and i.status in(1,2) order by scheduledDate";
		return jdbcTemplate.queryForList(getMyScheduleBookingsQuery,
				interviewerId);
	}

	/**
	 *  This Method is used to get Evaluator Slots 
	 * @param firstdate
	 * @param lastdate
	 * @param userId
	 * @return
	 */
	public List<Map<String, Object>> getEvaluatorSlots(String firstdate,
			String lastdate, int userId) {
		List<Map<String, Object>> list = null;
		String evaluatorslotsQuery = "SELECT slotid,scheduledDate FROM slotschedule WHERE InterviewerId ="
				+ userId
				+ " AND scheduledDate BETWEEN '"
				+ firstdate
				+ "' AND '" + lastdate + "'";
		list = jdbcTemplate.queryForList(evaluatorslotsQuery);
		return list;
	}

	/**
	 * This Method is used to insert or delete Evaluator Schedule Slots
	 * 
	 * @param slotid
	 * @param scheduledate
	 * @param evaluatorid
	 * @param timezone
	 * @return
	 */
	@Transactional
	public boolean updateEvaluatorScheduleSlots(int slotid,
			String scheduledate, int evaluatorid, String timezone) {
		tmiUtil.getUserId();
		boolean slotStatus = false;
		String slotsCountQuery = "SELECT count(*) FROM slotschedule WHERE InterviewerId ="
				+ evaluatorid
				+ " AND slotid ="
				+ slotid
				+ " AND scheduledDate ='" + scheduledate + "'";
		int scheduleCount = jdbcTemplate.queryForObject(slotsCountQuery,
				Integer.class);
		if (scheduleCount == 0) {
			String insertSlotsQuery = "INSERT INTO slotschedule(slotid,scheduledDate,InterviewerId,"
					+ "currenttimestamp,starttime,endtime)  VALUES ('"
					+ slotid
					+ "','"
					+ scheduledate
					+ "','"
					+ evaluatorid
					+ "','"
					+ tmiUtil.getCurrentGmtTime()
					+ "',convert_tz((select concat('"
					+ scheduledate
					+ "',' ',slkp.slottime) from slotlkp slkp where slkp.slotid='"
					+ slotid
					+ "'),'"
					+ timezone
					+ "','+00:00'),convert_tz((select concat('"
					+ scheduledate
					+ "',' ',slkp.slottime) from slotlkp slkp "
					+ " where slkp.slotid ='"
					+ slotid
					+ "'),'"
					+ timezone
					+ "','+01:00'))";
			jdbcTemplate.update(insertSlotsQuery);
			slotStatus = true;

		} else if (scheduleCount == 1) {
			String statustQuery = "SELECT status FROM slotschedule WHERE InterviewerId ="
					+ evaluatorid
					+ " AND slotid ="
					+ slotid
					+ " AND scheduledDate ='" + scheduledate + "'";
			int scheduleStatus = jdbcTemplate.queryForObject(statustQuery,
					Integer.class);
			if (scheduleStatus == 0) {

				String deleteScheduleSlotQuery = "DELETE FROM slotschedule WHERE status =0 AND  slotid = "
						+ slotid
						+ " AND scheduledDate ='"
						+ scheduledate
						+ "' AND InterviewerId=" + evaluatorid;
				jdbcTemplate.update(deleteScheduleSlotQuery);
				slotStatus = true;
			} else if (scheduleStatus != 0) {
				slotStatus = false;
			}
		}
		return slotStatus;
	}

	/**
	 *  This Method is used to update lapsed schedule
	 * 
	 */
	@Transactional
	public void updateLapsedSchedule() {
		String updateLapsedScheduleQuery = "call  updateLapsedSchedule();";
		jdbcTemplate.update(updateLapsedScheduleQuery);

	}

	/**
	 * This Method is used to Change Slot Status in ScheduleSlot table
	 * 
	 * @param slotstatusid
	 * @param slotscheduleid
	 */
	@Transactional
	public void changeSlotStatus(int slotstatusid, int slotscheduleid) {
		String updateSlotQuery = "UPDATE slotschedule SET status="
				+ slotstatusid + " , statustimestamp ='"
				+ tmiUtil.getCurrentGmtTime() + "' WHERE slotscheduleid="
				+ slotscheduleid + "";
		jdbcTemplate.update(updateSlotQuery);

	}
	
	/**
	 * This Method is used to Change Slot Status in ScheduleSlot table
	 * 
	 * @param slotstatusid
	 * @param slotscheduleid
	 * @param interviewid
	 * @param starttime
	 * @param comments
	 * @param userId
	 * @param usertype
	 */
	@Transactional
	public void changeSlotStatusByCCR(int slotstatusid, int slotscheduleid,int interviewid,String starttime,String comments,int userId,int usertype) {
		if(slotstatusid == 3){
			String userids = getApplicantEvaluatorMatch(interviewid,externalUserDao.getRoleID(interviewid));
			String slotscheduleQuery = "SELECT slotscheduleid FROM slotschedule WHERE find_in_set(InterviewerId,?) AND starttime =? AND status=0";
			List<Map<String, Object>> slotscheduleList = jdbcTemplate.queryForList(
					slotscheduleQuery, userids, starttime);
			if (slotscheduleList.size() > 0) {
				String assignSlotQuery = "UPDATE slotschedule SET status = 2 , statustimestamp = ? WHERE slotscheduleid=?";
				jdbcTemplate.update(assignSlotQuery, tmiUtil.getCurrentGmtTime(),
						slotscheduleList.get(0).get("slotscheduleid"));

				String updateEvaluatorscheduleidQuery = "update interview set evaluatorscheduleid = ? where interviewid=?";
				jdbcTemplate.update(updateEvaluatorscheduleidQuery,
						slotscheduleList.get(0).get("slotscheduleid"), interviewid);	
				
			}					
			String insertRejectslotQuery = "INSERT INTO  evalrejectslot (slotscheduleid,comment ,currenttimestamp,"
					+ "interviewid,loggeduserid,usertypeid) VALUES (?,?,?,?,?,?)";
			jdbcTemplate.update(insertRejectslotQuery, slotscheduleid, comments,
					tmiUtil.getCurrentGmtTime(), interviewid, userId, usertype);
			
			
			
		}
		
		String updateSlotQuery = "UPDATE slotschedule SET status="
				+ slotstatusid + " , statustimestamp ='"
				+ tmiUtil.getCurrentGmtTime() + "' WHERE slotscheduleid="
				+ slotscheduleid + "";
		jdbcTemplate.update(updateSlotQuery);

	}

	/**
	 * This Method is used to get the count of the evaluator interviews
	 * @param userId
	 * @return
	 */
	public int getCalendarCount(int userId) {
		String calendarCountQuery = "SELECT count(*) FROM slotschedule WHERE InterviewerId=? and status=2";
		return jdbcTemplate.queryForObject(calendarCountQuery, Integer.class,
				userId);
	}

	/**
	 * This Method is used to get the count of current day interviews
	 * 
	 * @param userId
	 * @return
	 */
	public int getInterviewCount(int userId) {
		String interviewCountQuery = "SELECT count(*) FROM interview i,slotschedule s "
				+ "WHERE i.evaluatorscheduleid=s.slotscheduleid and s.InterviewerId=? and "
				+ "s.status=4 and i.status=1 and s.scheduledDate=CURDATE()";
		return jdbcTemplate.queryForObject(interviewCountQuery, Integer.class,
				userId);
	}

	/**
	 * This Method is used to get the count of interview feedback by evaluator
	 * 
	 * @param userId
	 * @return
	 */
	public Object getFeedbackCount(int userId) {
		String feedbackCountQuery = "SELECT count(*) FROM interview i,slotschedule s WHERE "
				+ "i.evaluatorscheduleid=s.slotscheduleid and s.status=4 and i.status=2  "
				+ "and evaluatorfeedbackstatus=0 and s.InterviewerId=?";
		return jdbcTemplate.queryForObject(feedbackCountQuery, Integer.class,
				userId);
	}

	/**
	 *  This Method is used to request admin for slot 
	 * @param scheduledate
	 * @param interviewId
	 * @return
	 */
	public int requestAdminForSlot(String scheduledate, int interviewId) {
		String InterviewidQuerycount = "select count(*) from unavailableslots where interviewid="+interviewId;
		int InterviewidQuerycoun = jdbcTemplate.queryForObject(InterviewidQuerycount, Integer.class);
		if(InterviewidQuerycoun<1){
		String insertInterviewidQuery = "INSERT INTO unavailableslots(interviewid, scheduledDate) VALUES (?,?)";
		return jdbcTemplate.update(insertInterviewidQuery, interviewId, scheduledate);
		}else{
			return 0;
		}

	}

	/**
	 * This Method is used to insert comments for rejected slots
	 * 
	 * @param slotscheduleid
	 * @param comment
	 * @param interviewid
	 * @param userid
	 * @param usertype
	 * @param starttime
	 * @return
	 */
	@Transactional
	public boolean rejectedSlotComment(int slotscheduleid, String comment,
			int interviewid, int userid, int usertype, String starttime) {
		boolean assaignStatus = false;
		String userids = getApplicantEvaluatorMatch(interviewid,externalUserDao.getRoleID(interviewid));
		String slotscheduleQuery = "SELECT slotscheduleid FROM slotschedule WHERE find_in_set(InterviewerId,?) AND starttime =? AND status=0";
		List<Map<String, Object>> slotscheduleList = jdbcTemplate.queryForList(
				slotscheduleQuery, userids, starttime);
		if (slotscheduleList.size() > 0) {
			String assignSlotQuery = "UPDATE slotschedule SET status = 2 , statustimestamp = ? WHERE slotscheduleid=?";
			jdbcTemplate.update(assignSlotQuery, tmiUtil.getCurrentGmtTime(),
					slotscheduleList.get(0).get("slotscheduleid"));

			String updateEvaluatorscheduleidQuery = "update interview set evaluatorscheduleid = ? where interviewid=?";
			jdbcTemplate.update(updateEvaluatorscheduleidQuery,
					slotscheduleList.get(0).get("slotscheduleid"), interviewid);
			assaignStatus = true;
		}
		String updateSlotQuery = "UPDATE slotschedule SET status = 3 , statustimestamp = ?  WHERE slotscheduleid=?";
		jdbcTemplate.update(updateSlotQuery, tmiUtil.getCurrentGmtTime(),
				slotscheduleid);		
		String insertRejectslotQuery = "INSERT INTO  evalrejectslot (slotscheduleid,comment ,currenttimestamp,"
				+ "interviewid,loggeduserid,usertypeid) VALUES (?,?,?,?,?,?)";
		jdbcTemplate.update(insertRejectslotQuery, slotscheduleid, comment,
				tmiUtil.getCurrentGmtTime(), interviewid, userid, usertype);		

		return assaignStatus;
	}
	
	
}