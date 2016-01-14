package com.testmyinterview.portal.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.testmyinterview.portal.util.TmiUtil;

public class KookooDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TmiUtil tmiUtil;

	/**
	 *  This method is used to insert NewInterview into interviewhistory
	 * @param tmiId
	 * @param evaluator_ph
	 * @param applicant_ph
	 * @param call_status
	 * @return
	 */
	@Transactional
	public int insertNewInterviewHistory(String tmiId, String evaluator_ph,
			String applicant_ph, int call_status) {
		String newInterviewHistory = "INSERT INTO interviewhistory(interview_tmi_id,evaluator_phone,applicant_phone,call_status,interview_start_time) VALUES (?,?,?,?,?)";
		int rows = jdbcTemplate.update(newInterviewHistory, tmiId,
				evaluator_ph, applicant_ph, call_status,
				tmiUtil.getCurrentGmtTime());
		return rows;
	}

	/**
	 * This method is used to insert Call Initiated Audit into interview_audit table
	 * 
	 * @param tmiId
	 * @param description
	 * @return
	 */
	@Transactional
	public int insertCallInitiatedAuditRow(String tmiId, String description) {
		String callInitiateAuditQuery = "INSERT INTO interview_audit(interview_tmi_id,description,time_stamp) VALUES (?,?,?)";
		int rows = jdbcTemplate.update(callInitiateAuditQuery, tmiId,
				description, tmiUtil.getCurrentGmtTime());
		return rows;
	}

	/**
	 * This method is used to insert Applicant Call Response 
	 * 
	 * @param tmiId
	 * @param retryingApplicant
	 * @param event
	 * @param status
	 * @param message
	 * @param callDuration
	 * @param sid
	 * @param data
	 * @return
	 */
	@Transactional
	public int insertApplicantResponseAuditRow(String tmiId,
			int retryingApplicant, String event, String status, String message,
			String callDuration, String sid, String data) {
		int call_duration = 0;
		if (callDuration != null) {
			if (callDuration.isEmpty()) {
				call_duration = 0;
			} else {
				call_duration = Integer.parseInt(callDuration);
			}
		} else {
			call_duration = 0;
		}
		String elauatorResponseAuditQuery = "INSERT INTO interview_audit(interview_tmi_id,description,event,status,message,call_duration,kookoo_sid,file_link,time_stamp) VALUES (?,?,?,?,?,?,?,?,?)";
		int rows = jdbcTemplate.update(elauatorResponseAuditQuery, tmiId,
				retryingApplicant, event, status, message, call_duration, sid,
				data, tmiUtil.getCurrentGmtTime());
		return rows;
	}

	/**
	 * This method is used to insert Evaluator call Response
	 * @param tmiId
	 * @param retryingEvaluator
	 * @param sid
	 * @param event
	 * @param status
	 * @return
	 */
	@Transactional
	public int insertEvaluatorResponseAuditRow(String tmiId,
			int retryingEvaluator, String sid, String event, String status) {
		String elauatorResponseAuditQuery = "INSERT INTO interview_audit(interview_tmi_id,kookoo_sid,description,event,status,time_stamp) VALUES (?,?,?,?,?,?)";
		int rows = jdbcTemplate.update(elauatorResponseAuditQuery, tmiId, sid,
				retryingEvaluator, event, status, tmiUtil.getCurrentGmtTime());
		return rows;
	}

	/**
	 * This method is used to update  Evaluator call Response into interviewhistory
	 * @param tmiId
	 * @param sid
	 * @param call_status
	 * @param interviewer_call_status
	 * @param count
	 * @return
	 */
	@Transactional
	public int updateHistoryEvaluatorResponse(String tmiId, String sid,
			int call_status, String interviewer_call_status, int count) {
		String updateEvaluatorResponseQuery = "UPDATE interviewhistory SET kookoo_sid=?,interviewer_call_status=?,call_status=?,evaluator_nr_count=? WHERE interview_tmi_id=?";
		int rows = jdbcTemplate.update(updateEvaluatorResponseQuery, sid,
				interviewer_call_status, call_status, count, tmiId);
		return rows;
	}

	/**
	 * This method is used to update  Applicant call Response into interviewhistory
	 * @param tmiId
	 * @param call_status
	 * @param applicant_call_status
	 * @param count
	 * @param sid
	 * @param called_number
	 * @return
	 */
	@Transactional
	public int updateHistoryApplicantResponse(String tmiId, int call_status,
			String applicant_call_status, int count, String sid,
			String called_number) {
		String updateEvaluatorResponseQuery = "UPDATE interviewhistory SET applicant_call_status=?,call_status=?, applicant_nr_count=?,kookoo_sid=?,caller_id=? WHERE interview_tmi_id=?";
		int rows = jdbcTemplate.update(updateEvaluatorResponseQuery,
				applicant_call_status, call_status, count, sid, called_number,
				tmiId);
		return rows;
	}

	/**
	 * This method is used to update  call total duration time into interviewhistory
	 * @param tmiId
	 * @param sid
	 * @param duration
	 * @param called_number
	 */
	@Transactional
	public void updateHistoryTotalTime(String tmiId, String sid, int duration,
			String called_number) {
		String updateEvaluatorResponseQuery = "UPDATE interviewhistory SET total_time=?,kookoo_sid=?,caller_id=? WHERE interview_tmi_id=?";
		jdbcTemplate.update(updateEvaluatorResponseQuery, duration, sid,
				called_number, tmiId);
	}

	/**
	 * This method is used to get  Evaluator no Response count
	 * @param tmiId
	 * @return
	 */
	public int getEvaluatorNACount(String tmiId) {
		String evaluatorNACountQuery = "SELECT evaluator_nr_count FROM interviewhistory WHERE interview_tmi_id = ?";
		Map<String, Object> row = jdbcTemplate.queryForMap(
				evaluatorNACountQuery, tmiId);
		String nr_count = row.get("evaluator_nr_count").toString();
		int count = Integer.parseInt(nr_count);
		return count;
	}

	/**
	 * This method is used to get  DMF no Response count
	 * @param tmiId
	 * @return
	 */
	public int getNoDMFCount(String tmiId) {
		String getNoDMFQuery = "SELECT no_dmf_count FROM interviewhistory WHERE interview_tmi_id = ?";
		Map<String, Object> row = jdbcTemplate
				.queryForMap(getNoDMFQuery, tmiId);
		String no_dmf_count = row.get("no_dmf_count").toString();
		int count = Integer.parseInt(no_dmf_count);
		return count;
	}

	/**
	 * This method is used to get Wrong  DMF  count
	 * @param tmiId
	 * @return
	 */
	public int getWrongDMFCount(String tmiId) {
		String getWrongDMFQuery = "SELECT wrong_dmf_count FROM interviewhistory WHERE interview_tmi_id = ?";
		Map<String, Object> row = jdbcTemplate.queryForMap(getWrongDMFQuery,
				tmiId);
		String wrong_dmf_count = row.get("wrong_dmf_count").toString();
		int count = Integer.parseInt(wrong_dmf_count);
		return count;
	}

	/**
	 * This method is used to get  DMF status
	 * @param tmiId
	 * @return
	 */
	public int getDMFStatus(String tmiId) {
		String getDMFStatusQuery = "SELECT dtmf FROM interviewhistory WHERE interview_tmi_id = ?";
		return jdbcTemplate.queryForObject(getDMFStatusQuery, Integer.class,
				tmiId);

	}

	/**
	 * This method is used to get  Phone No based on tmiid
	 * @param tmiId
	 * @return
	 */
	public Map<String, Object> getPhoneNumByTmiId(String tmiId) {
		String getPhoneNumByTmiIdQuery = "SELECT evaluator_phone,applicant_phone FROM interviewhistory WHERE interview_tmi_id = ?";
		Map<String, Object> row = jdbcTemplate.queryForMap(
				getPhoneNumByTmiIdQuery, tmiId);
		return row;
	}

	/**
	 * This method is used to get interview history count
	 * @param tmiId
	 * @return
	 */
	public int getInterviewHistoryCount(String tmiId) {
		String getInterviewHistoryCountQuery = "SELECT count(*) FROM interviewhistory WHERE interview_tmi_id = ?";
		return jdbcTemplate.queryForObject(getInterviewHistoryCountQuery,
				Integer.class, tmiId);
	}

	/**
	 * This method is used to update  no DMF   count
	 * @param tmiId
	 * @param count
	 * @param sid
	 */
	@Transactional
	public void updateNoDMFCount(String tmiId, int count, String sid) {
		String updateDMFCount = "UPDATE interviewhistory SET kookoo_sid=?, no_dmf_count=? WHERE interview_tmi_id=?";
		jdbcTemplate.update(updateDMFCount, sid, count, tmiId);
	}

	/**
	 * This method is used to update wrong  DMF  count
	 * @param tmiId
	 * @param count
	 * @param sid
	 */
	@Transactional
	public void updateWrongDMFCount(String tmiId, int count, String sid) {
		String updateDMFCount = "UPDATE interviewhistory SET kookoo_sid=?, wrong_dmf_count=? WHERE interview_tmi_id=?";
		jdbcTemplate.update(updateDMFCount, sid, count, tmiId);
	}

	/**
	 * This method is used to update Evaluator DMF History 
	 * @param tmiId
	 * @param sid
	 * @param callStatus
	 */
	@Transactional
	public void updateHistoryEvaluatorDMF(String tmiId, String sid,
			int callStatus) {
		String updateEvaluatorDmfQuery = "UPDATE interviewhistory set kookoo_sid=?, call_status=?,dtmf=? WHERE interview_tmi_id = ?";
		jdbcTemplate.update(updateEvaluatorDmfQuery, sid, callStatus,
				callStatus, tmiId);
	}

	/**
	 * This method is used to get Applicant Details
	 * @param tmiId
	 * @return
	 */
	public Map<String, Object> getApplicantDetails(String tmiId) {
		String getApplicantPhone = "SELECT applicant_phone,applicant_nr_count FROM interviewhistory WHERE interview_tmi_id = ?";
		Map<String, Object> row = jdbcTemplate.queryForMap(getApplicantPhone,
				tmiId);
		return row;
	}

	/**
	 * This method is used to update Evaluator Response History 
	 * @param tmiId
	 * @param sid
	 * @param status
	 * @param count
	 */
	public void updateHistoryEvaluatorResponse(String tmiId, String sid,
			String status, int count) {
		String updateEvaluatorDmfQuery = "UPDATE interviewhistory set kookoo_sid=?,interviewer_call_status=?,interview_end_time=?  WHERE interview_tmi_id = ?";
		jdbcTemplate.update(updateEvaluatorDmfQuery, sid, status,
				tmiUtil.getCurrentGmtTime(), tmiId);
	}

	/**
	 *  This method is used to get Interview Call Details
	 * @return
	 */
	public List<Map<String, Object>> getInterviewCallDet() {
		String query = "select i.interviewtmiid,(select mobileno from profile pp where "
				+ "pp.userid=i.userid)as applicantmob,(select mobileno from profile p "
				+ "where p.userid=s.InterviewerId) as evalmob from interview i, "
				+ "slotschedule s where i.interviewtmiid NOT IN (SELECT ih.interview_tmi_id "
				+ "FROM interviewhistory ih) and s.status=4 and i.status=1 and i.interviewmodeid=1 and "
				+ "s.starttime between date_sub(utc_timestamp(),INTERVAL 1 MINUTE) and "
				+ "DATE_ADD(utc_timestamp(),INTERVAL 1 MINUTE) and "
				+ "i.evaluatorscheduleid=s.slotscheduleid";
		return jdbcTemplate.queryForList(query);

	}

	/**
	 *  This method is used to get Applicant Call Status
	 * @param tmiId
	 * @return
	 */
	public int getApplicantCallStatus(String tmiId) {
		String applicantCallStatusQuery = "SELECT call_status FROM interviewhistory WHERE interview_tmi_id=?";
		return jdbcTemplate.queryForObject(applicantCallStatusQuery,
				Integer.class, tmiId);
	}

}
