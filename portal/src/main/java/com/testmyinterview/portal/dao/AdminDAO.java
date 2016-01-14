package com.testmyinterview.portal.dao;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;
import java.util.Map;
import java.sql.PreparedStatement;


//import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;


import com.testmyinterview.portal.bean.AdminProfileBean;
import com.testmyinterview.portal.bean.InterViewBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.TmiUtil;

public class AdminDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TmiUtil tmiUtil;
	@Autowired
	private ProfileDAO profileDAO;
	@Autowired
	private ExternalUserDao externalUserDao;

	/**
	 * this method is used to get admin id based on emailAddress
	 *
	 * @param abp : emailid
	 * @return :userId
	 */
	public int getAdminId(AdminProfileBean abp) {
		String userIdQuery = "SELECT adminid FROM subadminprofile WHERE emailid=?";
		int userId = jdbcTemplate.queryForObject(userIdQuery, Integer.class,
				abp.getEmailaddress());
		return userId;
	}

	/**
	 * this method is used to get admin user details based on user id
	 * 
	 * @param abp : adminid
	 * @return    : Admin User Details
	 */
	public List getAdminDetails(AdminProfileBean abp) {
		String userDetailsQuery = "SELECT * FROM subadminprofile WHERE adminid=?";
		List userList = jdbcTemplate.queryForList(userDetailsQuery,
				abp.getAdminId());
		return userList;
	}
	
	/**
	 * this method is used to add  interview
	 * @param interviewId
	 * @param emailId
	 * @param industry
	 * @param domains
	 * @param position
	 * @param interviewrole
	 * @param location
	 * @param modeofInterview
	 * @param interviewtype
	 * @param companyId
	 * @param companyName
	 * @param career
	 * @return
	 */
	@Transactional
	public int addInterview(final String interviewId, final String emailId,
			final String industry, final String domains, final String position,
			final String interviewrole, final String location,
			final String modeofInterview, final String interviewtype,
			final int companyId, String companyName, final String career) {
		long timestamp = new Timestamp(new Date().getTime()).getTime();
		String tmi = "INP";		
		final String tmiid = tmi.concat(Long.toString(timestamp));
		final String loactionId="IN";
		final String addinterviewQuery = "INSERT INTO interview(interviewid, interviewtmiid, userid,"
				+ "evaluatorscheduleid,resumeid,industryid, domainid, designation,interviewerroleid,"
				+ "interviewerlocationid,interviewmodeid,interviewtypeid, companyName,status, careerstatus,"
				+ "createddate,iplocation) select null,?,userid,?,resumeid,?,?,?,"
				+ "?,?,?,?,?,1,?,?,? from profile where emailid=?";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(addinterviewQuery,
								new String[] { "interviewid" });
				ps.setString(1, tmiid);
				ps.setString(2, interviewId);
				ps.setString(3, industry);
				ps.setString(4, domains);
				ps.setString(5, position);
				ps.setString(6, interviewrole);
				ps.setString(7, location);
				ps.setString(8, modeofInterview);
				ps.setString(9, interviewtype);
				ps.setInt(10, companyId);
				ps.setString(11, career);
				ps.setString(12, tmiUtil.getCurrentGmtTime());
				ps.setString(13, loactionId);
				ps.setString(14, emailId);
				
				return ps;
			}
		}, keyHolder);
		String interviewid = keyHolder.getKey().toString();
		if (companyId == 0) {
			
			ProfileBean pb = new ProfileBean();
			pb.setEmailaddress(emailId);
			int userID = profileDAO.getUserId(pb);
			externalUserDao.addOtherCategory(Constants.interviewPageId,
					Constants.companyCategoryId, userID, companyName,
					interviewid);
			
		}
		externalUserDao.addBulkInterviewAmount(interviewid);
		
		String addpaymentQuery = "INSERT INTO payment(paymentid,paymenttransactionid,interviewid,amount,status,"
				+ "currenttimestamp) select null,null,interviewid,0,'success',? "
				+ "from interview where interviewtmiid=?";
		return jdbcTemplate.update(addpaymentQuery,
				tmiUtil.getCurrentGmtTime(), tmiid);
		
	}

	/**
	 * This method is used to  add slotscheduleid into slotschedule based on evalatorid and date &time
	 * @param addStartHour
	 * @param startDate
	 * @param evaluatorId
	 * @return : slotscheduleid
	 */
	public String updateSlot(final String addStartHour, final String startDate,
			final int evaluatorId) {
		final String insertSlotQuery = "INSERT INTO slotschedule(slotscheduleid,slotid,scheduledDate,InterviewerId,status,"
				+ "currenttimestamp,statustimestamp,starttime,endtime) VALUES (null,?,?,?,4,utc_timestamp(),utc_timestamp,"
				+ "CONVERT_TZ(concat(?,' ',(SELECT slottime FROM slotlkp WHERE slotid=?)),(SELECT timezone FROM profile "
				+ "WHERE userid=?),'+00:00'),CONVERT_TZ(concat(?,' ',(SELECT slottime FROM slotlkp WHERE slotid=?)),"
				+ "(SELECT timezone FROM profile WHERE userid=?),'+01:00'))";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(insertSlotQuery,
								new String[] { "slotscheduleid" });
				ps.setString(1, addStartHour);
				ps.setString(2, startDate);
				ps.setInt(3, evaluatorId);
				ps.setString(4, startDate);
				ps.setString(5, addStartHour);
				ps.setInt(6, evaluatorId);
				ps.setString(7, startDate);
				ps.setString(8, addStartHour);
				ps.setInt(9, evaluatorId);

				return ps;
			}
		}, keyHolder);	
		return keyHolder.getKey().toString();
	}
	/**
	 * this method is used to get Closed Interviews Count
	 * @return : Closed Interviews Count
	 */
	public int getClosedInterviews() {

		String countQuery = "SELECT count(*)  FROM interview i,slotschedule s WHERE i.status =2 AND i.evaluatorscheduleid = s.slotscheduleid AND s.scheduledDate =DATE(now())";
		return jdbcTemplate.queryForObject(countQuery, Integer.class);
	}

	/**
	 * This Method is used to get Cancelled Interviews count
	 * 
	 * @return : Cancelled Interviews count
	 */
	public int getCancelledInterviews() {

		String countQuery = "SELECT count(*)  FROM interview i,slotschedule s WHERE i.status =3 AND i.evaluatorscheduleid = s.slotscheduleid AND s.scheduledDate = DATE(now())";
		return jdbcTemplate.queryForObject(countQuery, Integer.class);
	}

	/**
	 * This Method is used to get EVALUATORS UNAVAILABLE interviews details based on days
	 * 
	 * @param days 
	 * @return  : interviews list
	 */
	public List<Map<String, Object>> getEvalUnavailableData(String days) {
		String evalQuery = "SELECT p.userid, i.interviewid,i.interviewtmiid,concat(p.firstname,' ',"
				+ "p.lastname) as ApplicantName,p.mobileno as appMobile, r.interviewerrolename,"
				+ "DATE_FORMAT((SELECT  CONVERT_TZ(s.starttime,'+00:00',p.timezone)),'%d %b') as date,"
				+ "concat(sl.slotfrom,'-',sl.slotto) as timeslot,  'No status' as status,s.starttime as starttime,"
				+ "CAST(TIMEDIFF( STR_TO_DATE(s.starttime,'%Y-%m-%d %H:%i:%s'),utc_timestamp())as char) as"
				+ " timedif,(TIMEDIFF( STR_TO_DATE(s.starttime,'%Y-%m-%d %H:%i:%s'),utc_timestamp())< "
				+ "CAST('48:00:00' AS time)) as timestatus,concat(DATE_FORMAT((SELECT "
				+ "CONVERT_TZ(s.starttime,'+00:00',p.timezone)),'%h:%i %p'),'-', DATE_FORMAT((SELECT "
				+ "CONVERT_TZ(s.endtime,'+00:00',p.timezone)),'%h:%i %p')) as applTime FROM interview i,"
				+ "slotschedule s,  interviewerrolelkp r,profile p, slotlkp sl WHERE  i.status =1 AND "
				+ "i.evaluatorscheduleid =s.slotscheduleid  AND  s.status =2 AND s.statustimestamp <="
				+ "(UTC_TIMESTAMP() - INTERVAL 8 HOUR) AND p.userid =i.userid AND "
				+ "r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid AND DATE(s.starttime) >="
				+ " DATE( UTC_TIMESTAMP()) AND DATE(s.starttime) <= DATE((UTC_TIMESTAMP()+INTERVAL ? DAY))"
				+ " UNION SELECT p.userid, i.interviewid,i.interviewtmiid,concat(p.firstname,' ',"
				+ "p.lastname) as ApplicantName,p.mobileno as appMobile,  r.interviewerrolename,"
				+ "DATE_FORMAT((SELECT  CONVERT_TZ(s.starttime,'+00:00',p.timezone)),'%d %b') as date,"
				+ "concat(sl.slotfrom,'-',sl.slotto) as timeslot,   'Rejected' as status,s.starttime as starttime,"
				+ "CAST(TIMEDIFF( STR_TO_DATE(s.starttime,'%Y-%m-%d %H:%i:%s'),utc_timestamp())as char) as"
				+ " timedif,(TIMEDIFF( STR_TO_DATE(s.starttime,'%Y-%m-%d %H:%i:%s'),utc_timestamp())<"
				+ " CAST('48:00:00' AS time)) as timestatus,concat(DATE_FORMAT((SELECT "
				+ "CONVERT_TZ(s.starttime,'+00:00',p.timezone)),'%h:%i %p'),'-',DATE_FORMAT((SELECT "
				+ "CONVERT_TZ(s.endtime,'+00:00',p.timezone)),'%h:%i %p')) as applTime FROM interview i,"
				+ "slotschedule s, interviewerrolelkp r,profile p, slotlkp sl   WHERE  i.status =1 AND "
				+ "i.evaluatorscheduleid =s.slotscheduleid  AND s.status =3 AND p.userid =i.userid AND "
				+ "r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid AND DATE(s.starttime) >="
				+ " DATE( UTC_TIMESTAMP()) AND DATE(s.starttime) <= DATE((UTC_TIMESTAMP()+INTERVAL ? DAY))"
				+ " ORDER by starttime ASC";
		return jdbcTemplate.queryForList(evalQuery,days,days);
	}

	/**
	 * This Method is used to get Closed Interviews details based on TIME INTERVAL
	 * 
	 * @param date
	 * @return  : Closed Interviews List
	 */
	public List<Map<String, Object>> getClosedInterviewsData(String date) {
		String closedInterviewQuery = "SELECT p.userid as appuserid,p2.userid as evaluserid, i.interviewid,i.interviewtmiid,"
				+ "concat(p.firstname,' ',p.lastname) as ApplicantName, "
				+ "concat(p2.firstname,' ',p2.lastname) as EvaluatorName ,r.interviewerrolename,"
				+ "date_format(s.scheduledDate,'%d %b') as date, "
				+ "concat(sl.slotfrom,'-',sl.slotto) as timeslot, 'Closed' as status "
				+ " FROM slotschedule s,  interview i, interviewerrolelkp r, profile p,profile p2,"
				+ " slotlkp sl WHERE i.evaluatorscheduleid =s.slotscheduleid AND  i.status =2 and s.status=4 "
				+ " AND p.userid =i.userid AND p2.userid=s.InterviewerId AND"
				+ " r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid AND "
				+ "DATE(s.endtime) <= DATE( UTC_TIMESTAMP()) AND DATE(s.endtime) >= "
				+ "DATE( (UTC_TIMESTAMP()-INTERVAL '"
				+ date
				+ "' DAY)) order by s.starttime desc";
		return jdbcTemplate.queryForList(closedInterviewQuery);
	}
	/**
	 * This Method is used to get cancel interviews Details based on time interval 
	 * 
	 * @param date
	 * @return : Cancel Interviews List
	 */
	public List<Map<String, Object>> getCancelInterviewsData(String date) {
		String cancelInterviewQuery = "SELECT p.userid as appuserid,p2.userid as evaluserid,"
				+ " i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) as ApplicantName,"
				+ " concat(p2.firstname,' ',p2.lastname) as EvaluatorName ,r.interviewerrolename,"
				+ " date_format(s.scheduledDate,'%d %b') as date, concat(sl.slotfrom,'-',sl.slotto) as timeslot, "
				+ " 'Cancelled' as status  FROM slotschedule s,  interview i, interviewerrolelkp r,"
				+ " profile p,profile p2, slotlkp sl,audit a WHERE i.evaluatorscheduleid =s.slotscheduleid"
				+ " AND  i.status =3  AND p.userid =i.userid AND p2.userid=s.InterviewerId"
				+ " AND r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid AND"
				+ " i.interviewid=a.audittypeId AND a.audittype='Cancel Interview' and DATE(a.auditcreatedtime) <= DATE( UTC_TIMESTAMP())"
				+ " AND DATE(a.auditcreatedtime) >= DATE( (UTC_TIMESTAMP()-INTERVAL '"
				+  date+"'  DAY)) order by s.starttime desc";
		return jdbcTemplate.queryForList(cancelInterviewQuery);
	}
	/**
	 * This Method is used to get cancel interview based on interviewid
	 * 
	 * @param interviewtmiid
	 * @return :  cancel interview details
	 */
	public List getCancelInterviewsDataBasedOnTmiId(String interviewtmiid) {		
		String cancelInterviewQuery = "SELECT p.userid as appuserid,p2.userid as evaluserid,"
				+ " i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) as ApplicantName,"
				+ " concat(p2.firstname,' ',p2.lastname) as EvaluatorName ,r.interviewerrolename,"
				+ " date_format(s.scheduledDate,'%d %b') as date, concat(sl.slotfrom,'-',sl.slotto) as timeslot, "
				+ " 'Cancelled' as status  FROM slotschedule s,  interview i, interviewerrolelkp r,"
				+ " profile p,profile p2, slotlkp sl WHERE i.evaluatorscheduleid =s.slotscheduleid"
				+ " AND  i.status =3 AND p.userid =i.userid AND p2.userid=s.InterviewerId"
				+ " AND r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid AND"
				+ " i.interviewtmiid =? order by s.starttime desc";
		return jdbcTemplate.queryForList(cancelInterviewQuery,interviewtmiid);
	}
	/**
	 * This Method is used to get cancel interviews list based on applicant name
	 * 
	 * @param applicantname
	 * @return : cancel interviews list
	 */
	public List getCancelInterviewsDataBasedOnApplicantName(String applicantname) {		
		String cancelInterviewQuery = "SELECT p.userid as appuserid,p2.userid as evaluserid,"
				+ " i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) as ApplicantName,"
				+ " concat(p2.firstname,' ',p2.lastname) as EvaluatorName ,r.interviewerrolename,"
				+ " date_format(s.scheduledDate,'%d %b') as date, concat(sl.slotfrom,'-',sl.slotto) as timeslot, "
				+ " 'Cancelled' as status  FROM slotschedule s,  interview i, interviewerrolelkp r,"
				+ " profile p,profile p2, slotlkp sl WHERE i.evaluatorscheduleid =s.slotscheduleid"
				+ " AND  i.status =3 AND p.userid =i.userid AND p2.userid=s.InterviewerId"
				+ " AND r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid AND"
				+ " (p.firstname LIKE ? or p.lastname LIKE ? or "
				+ " concat(p.firstname,' ',p.lastname) LIKE ? )"
				+ " and p.usertypeid=1 order by s.starttime desc";
		return jdbcTemplate.queryForList(cancelInterviewQuery,'%'+applicantname+'%','%'+applicantname+'%','%'+applicantname+'%');
	}
	/**
	 * This Method is used to get cancel interviews list based on Evaluator Name
	 * 
	 * @param evaluatorname
	 * @return : cancel interviews list
	 */
	public List getCancelInterviewsDataBasedOnEvaluatorName(String evaluatorname) {		
		String cancelInterviewQuery = "SELECT p.userid as appuserid,p2.userid as evaluserid,"
				+ " i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) as ApplicantName,"
				+ " concat(p2.firstname,' ',p2.lastname) as EvaluatorName ,r.interviewerrolename,"
				+ " date_format(s.scheduledDate,'%d %b') as date, concat(sl.slotfrom,'-',sl.slotto) as timeslot, "
				+ " 'Cancelled' as status  FROM slotschedule s,  interview i, interviewerrolelkp r,"
				+ " profile p,profile p2, slotlkp sl WHERE i.evaluatorscheduleid =s.slotscheduleid"
				+ " AND  i.status =3  AND p.userid =i.userid AND p2.userid=s.InterviewerId"
				+ " AND r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid AND"
				+ " (p2.firstname LIKE ? or p2.lastname LIKE ? or"
				+ " concat(p2.firstname,' ',p2.lastname) LIKE ? )"
				+ " and p2.usertypeid=2 order by s.starttime desc";
		return jdbcTemplate.queryForList(cancelInterviewQuery,'%'+evaluatorname+'%','%'+evaluatorname+'%','%'+evaluatorname+'%');
	}
	/**
	 * This Method is used to get Evaluator names and mobile no based on evaluator and interviewid
	 * 
	 * @param evalid
	 * @param interviewId
	 * @return : evaluator name and mobile no
	 */
	@Transactional
	public List<Map<String, Object>> getEvalNameAndPhone(String evalid,
			int interviewId) {
		String evalNamesQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname) as name,"
				+ "p.mobileno FROM profile p,interview i,slotschedule s "
				+ " WHERE find_in_set(p.userid, '"
				+ evalid
				+ "') AND i.evaluatorscheduleid=s.slotscheduleid"
				+ " AND s.InterviewerId!=p.userid AND  i.interviewid ="
				+ interviewId;
		List<Map<String, Object>> evalNamesList = jdbcTemplate
				.queryForList(evalNamesQuery);
		return evalNamesList;
	}

	/**
	 * This Method is used to get Applicant name and mobile no Based on interviewid
	 * 
	 * 
	 * @param interviewId
	 * @return : Applicant Name and Mobile No
	 */
	@Transactional
	public Map<String, Object> getApplicantNameAndPhone(int interviewId) {
		String appCntNamesQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname) as applicantname,"
				+ "p.mobileno,r.interviewerrolename FROM profile p,interview i,interviewerrolelkp r "
				+ " WHERE  p.userid = i.userid  AND  r.interviewerroleid=i.interviewerroleid "
				+ " AND  i.interviewid = " + interviewId;
		Map<String, Object> applCntNamesList = jdbcTemplate
				.queryForMap(appCntNamesQuery);
		return applCntNamesList;
	}

	/**
	 * This method is used to assign an evaluator to applicant 
	 * @param interviewid
	 * @param evalid
	 * @return 
	 */
	@Transactional
	public boolean assignEvaluatorToApplicantSlot(final int interviewid,
			final int evalid) {

		String preSlotidQuery = "SELECT evaluatorscheduleid FROM interview WHERE interviewid="
				+ interviewid;
		int preSlotid = jdbcTemplate.queryForObject(preSlotidQuery,
				Integer.class);
		final String insertEvalSlotQuery = "INSERT INTO slotschedule ( slotscheduleid , slotid ,scheduledDate ,InterviewerId ,status , currenttimestamp ,statustimestamp,starttime,endtime ) SELECT NULL , slotid , scheduledDate , ? AS InterviewerId, 4 AS  status ,utc_timestamp(),utc_timestamp(),starttime,endtime FROM slotschedule ,interview  WHERE slotscheduleid = evaluatorscheduleid AND interviewid =?";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int insertStatus = jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(insertEvalSlotQuery,
								new String[] { "slotscheduleid" });
				ps.setInt(1, evalid);
				ps.setInt(2, interviewid);
				return ps;
			}
		}, keyHolder);
		String scheduleid = keyHolder.getKey().toString();
		int slotscheduleId = Integer.parseInt(scheduleid);
		String updateEvaluatorscheduleidQuery = "update interview set evaluatorscheduleid=? where interviewid=?";
		jdbcTemplate.update(updateEvaluatorscheduleidQuery, slotscheduleId,
				interviewid);
		String scheduleStatusQuery = "update slotschedule set status = 5 where slotscheduleid=?";
		jdbcTemplate.update(scheduleStatusQuery, preSlotid);
		return true;
	}

	/**
	 * This Method is used to add or update  Evaluator details in to evalapprovedpreferences
	 * 
	 * @param adminId
	 * @param interviewsuitable
	 * @param role
	 * @param comments
	 * @param userId
	 * @param trainingstatus
	 */
	@Transactional
	public void addEvaluatorDetails(int adminId, int interviewsuitable,
			String role, String comments, int userId, int trainingstatus) {
		int count = 0;
		String evalQuery = "SELECT count(*) FROM evalapprovedpreferences WHERE evaluatorid='"
				+ userId + "'";
		count = jdbcTemplate.queryForObject(evalQuery, Integer.class);
		checkaddauditTable(userId, Constants.AdminEvalMapping, comments, adminId);
		if (count == 1) {
			String evalNamesQuery = "UPDATE evalapprovedpreferences SET adminid=?,evalstatus=?,"
					+ "interviewtmiroles=?,tminotes=?,currenttimestamp=?,trainingstatus=? WHERE evaluatorid=?";
			jdbcTemplate.update(evalNamesQuery, adminId, interviewsuitable,
					role, comments, tmiUtil.getCurrentGmtTime(),
					trainingstatus, userId);
		} else {
			String eavalinsertQuery = "INSERT INTO evalapprovedpreferences(evaluatorid,evalstatus,adminid,"
					+ "interviewtmiroles, tminotes,currenttimestamp,trainingstatus) VALUES "
					+ "(?,?,?,?,?,?,?)";
			jdbcTemplate.update(eavalinsertQuery, userId, interviewsuitable,
					adminId, role, comments, tmiUtil.getCurrentGmtTime(),
					trainingstatus);
		}
	}

	/**
	 * This Method is used to get New Evaluator details
	 * 
	 * @return : evaluator details
	 */
	public List getNewEvaluatorDetails() {
		String evaluatorDetailsQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname)as evlname,"
				+ "concat('+',countrycode,'-',p.mobileno) as mobileno,ip.industryname,0 as evaluatorsRole,0 as Training FROM profile as p,industrylkp as ip WHERE "
				+ "ip.industryid=p.industryid and p.userid NOT IN (SELECT evaluatorid FROM "
				+ "evalapprovedpreferences) and  p.usertypeid=2";
		return jdbcTemplate.queryForList(evaluatorDetailsQuery);

	}

	/**
	 * This Method is used to get mapped Evaluator Details
	 * 
	 * @return :Mapped Evaluator Details list
	 */
	public List getMapeedToEvaluatorDetails() {
		String evaluatorDetailsQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname)as evlname,"
				+ "concat('+',countrycode,'-',p.mobileno) as mobileno ,ip.industryname,0 as evaluatorsRole,ep.trainingstatus as Training FROM "
				+ "profile as p,industrylkp as ip,evalapprovedpreferences as ep WHERE "
				+ "ep.trainingstatus=2 and ep.evalstatus!=0  and ip.industryid=p.industryid and ep.evaluatorid=p.userid "
				+ " and  p.usertypeid=2";
		return jdbcTemplate.queryForList(evaluatorDetailsQuery);

	}

	/**
	 * This Method is used to get mapped to evaluator approved preferences under
	 * 										Training
	 * 
	 * @return : Under Training Evaluator Details
	 */
	public List getUnderTrainingEvaluatorDetails() {
		String evaluatorDetailsQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname)as evlname,"
				+ "concat('+',countrycode,'-',p.mobileno) as mobileno ,ip.industryname,0 as evaluatorsRole,ep.trainingstatus as Training FROM "
				+ "profile as p,industrylkp as ip,evalapprovedpreferences as ep WHERE "
				+ "ep.trainingstatus IN(1,3) and ip.industryid=p.industryid and ep.evaluatorid=p.userid "
				+ " and  p.usertypeid=2";
		return jdbcTemplate.queryForList(evaluatorDetailsQuery);

	}

	/**
	 * This Method is used to get the evaluator Unsuitable
	 * 
	 * 
	 * @return : Unsuitable Evaluator list
	 */
	public List getUnsuitableEvaluatorDetails() {
		String evaluatorDetailsQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname)as evlname,"
				+ "concat('+',countrycode,'-',p.mobileno) as mobileno ,ip.industryname,0 as evaluatorsRole,ep.trainingstatus as Training FROM "
				+ "profile as p,industrylkp as ip,evalapprovedpreferences as ep WHERE "
				+ "ep.evalstatus=0 and ip.industryid=p.industryid and ep.evaluatorid=p.userid "
				+ "and  p.usertypeid=2";
		return jdbcTemplate.queryForList(evaluatorDetailsQuery);

	}

	/**
	 * This Method is used to get the all evaluator Details
	 * 
	 * @return : get All Evaluator Details List
	 */
	public List getAllEvaluatorDetails() {
		String evaluatorDetailsQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname)as evlname,concat('+',countrycode,'-',p.mobileno) as mobileno,"
				+ "ip.industryname,0 as evaluatorsRole,ep.trainingstatus as Training FROM industrylkp as "
				+ "ip,profile as p LEFT OUTER JOIN evalapprovedpreferences as ep ON ep.evaluatorid=p.userid "
				+ "where ip.industryid=p.industryid and p.usertypeid=2";
		return jdbcTemplate.queryForList(evaluatorDetailsQuery);

	}

	/**
	 * This Method is used to get the evaluator ApprovedPreferences based on
	 * 											userId
	 * @param userId
	 * @return  : Evaluator Approved Preferences List
	 */
	public List getEvalApprovedPreferences(int userId) {
		String evaluatorDetailsQuery = "SELECT * FROM evalapprovedpreferences WHERE evaluatorid='"
				+ userId + "'";
		return jdbcTemplate.queryForList(evaluatorDetailsQuery);
	}

	/** 
	 * This method is used to get all evaluator count
	 * 
	 * @return :  Evaluator count
	 */
	public int getAllevaluatorCount() {
		String allEvalQuery = "SELECT count(*) FROM profile WHERE usertypeid=2 and profilestatus=1";
		return jdbcTemplate.queryForObject(allEvalQuery, Integer.class);

	}

	/**
	 * 
	 * This method is used to get New evaluator count
	 * 
	 * @return : Evaluator count
	 */
	public int getNewevaluatorCount() {
		String allEvalQuery = "SELECT count(*) FROM profile WHERE usertypeid=2 and userid not in"
				+ "( SELECT evaluatorid FROM evalapprovedpreferences) and profilestatus=1";
		return jdbcTemplate.queryForObject(allEvalQuery, Integer.class);

	}

	/**
	 * 
	 * this method is used to get evaluator count based on training completed
	 * 
	 * @return :  Evaluator count
	 */
	public int getMappedevaluatorCount() {
		String allEvalQuery = " SELECT count(*) FROM evalapprovedpreferences where "
				+ "evalstatus=1 and trainingstatus=2";
		return jdbcTemplate.queryForObject(allEvalQuery, Integer.class);

	}

	/**
	 * 
	 * this method is used to get evaluator count from evalapprovedpreferences
	 * members Under Training
	 * 
	 * @return  :  Evaluator count
	 */
	public int getTrainingEvaluatorMembersCount() {
		String allEvalQuery = " SELECT count(*) FROM evalapprovedpreferences where trainingstatus in(1,3)";
		return jdbcTemplate.queryForObject(allEvalQuery, Integer.class);

	}

	/**
	 * 
	 * this method is used to get evaluator count from evalapprovedpreferences
	 *												    Unsuitable for interview
	 * @return  :  Evaluator count
	 */
	public int getUnsuitableEvaluatorMembersCount() {
		String allEvalQuery = "SELECT count(*) FROM evalapprovedpreferences where evalstatus=0";
		return jdbcTemplate.queryForObject(allEvalQuery, Integer.class);

	}

	/**
	 * 
	 * this method is used to check mock interview status of user if exits
	 * update status if not exit's add user to the table
	 * 
	 * @param userid
	 * @param interviewStatus
	 * @return
	 */
	@Transactional
	public int addMockTest(int userid, int interviewStatus) {
		int count = 0;
		String userCountQuery = "SELECT count(*) FROM mocktest WHERE userid=?";
		count = jdbcTemplate.queryForObject(userCountQuery, Integer.class,
				userid);
		if (count == 1) {
			String mockupdateQuey = "UPDATE mocktest SET status=? WHERE userid=?";
			return jdbcTemplate.update(mockupdateQuey, interviewStatus, userid);
		} else {
			String mockinserQuey = "INSERT INTO mocktest(userid,status) VALUES (?,?)";
			return jdbcTemplate.update(mockinserQuey, userid, interviewStatus);
		}
	}
	/**
	 * This method is used to get min page limit from pagination table
	 * @return :min page limits 
	 */
	public int getDefaultPageLimit() {
		String defaultPageLimitQuery = "SELECT min(paginationlimit) FROM pagination";
		return jdbcTemplate
				.queryForObject(defaultPageLimitQuery, Integer.class);

	}

	/**
	 * This method is used to get all pagination limits from pagination table
	 * @return : pagination limit list
	 */
	public List getPaginationData() {
		String paginationDataQuery = "SELECT * FROM pagination order by paginationlimit asc";
		return jdbcTemplate.queryForList(paginationDataQuery);
	}

	/**
	 * This method is used to add BulkGroup  into DB
	 * @param groupName
	 * @return :new inserted  bulkgroupid
	 */
	@Transactional
	public int addBulkGroup(final String groupName) {
		final String insertSlotQuery = "INSERT INTO bulkgroup(bulkgroupname)VALUES (?);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(insertSlotQuery,
								new String[] { "bulkgroupid" });
				ps.setString(1, groupName);

				return ps;
			}
		}, keyHolder);
		return Integer.parseInt(keyHolder.getKey().toString());
	}

	/**
	 * This method is used to Insert InterviewGroup details into InterviewGroup
	 * @param bulkGroupId
	 * @param interviewId
	 * @param userId
	 */
	@Transactional
	public void addInterviewGroup(int bulkGroupId, String interviewId,
			int userId) {
		String addInterviewGroupQuery = "INSERT INTO interviewgroup(bulkgroupid,slotscheduleid,userid)VALUES(?,?,?);";
		jdbcTemplate.update(addInterviewGroupQuery, bulkGroupId, interviewId,
				userId);
	}

	/**
	 * This method is used to get bulk group details
	 * @return : Bulk group details list
	 */
	public List getBulkGroupList() {
		String getBulkGroupListQuery = "SELECT * FROM bulkgroup";
		return jdbcTemplate.queryForList(getBulkGroupListQuery);
	}

	/**
	 * This method is used to get  interview group list based on bulkgroupid
	 * @param bulkGroupId
	 * @return : bulk interviewgroup details list 
	 */
	public List getInterviewGroupList(int bulkGroupId) {
		String getInterviewGroupQuery = "SELECT p.*,iv.interviewid,iv.interviewtmiid,"
				+ "iv.applicantfeedbackstatus,iv.evaluatorfeedbackstatus,iv.designation,iv.status,ip.industryname,"
				+ "ik.interviewerrolename,iv.status,concat(DATE_FORMAT(CONVERT_TZ(scd.starttime"
				+ ",'+00:00',p.timezone),'%h:%i %p'),' - ',DATE_FORMAT(CONVERT_TZ(scd.endtime,"
				+ "'+00:00',p.timezone),'%h:%i %p')) as timeslot,DATE_FORMAT(CONVERT_TZ("
				+ "scd.starttime,'+00:00',p.timezone),'%d %b,%Y') as date FROM interview as iv"
				+ ",industrylkp as ip,interviewerrolelkp as ik ,slotschedule scd,slotlkp slt,"
				+ "profile p,bulkgroup bg,interviewgroup ig  WHERE  iv.evaluatorscheduleid = "
				+ "scd.slotscheduleid AND slt.slotid = scd.slotid and ik.interviewerroleid="
				+ "iv.interviewerroleid and ip.industryid= iv.industryid and iv.status in(1,2,3) "
				+ "and  iv.userid =p.userid and scd.slotscheduleid=ig.slotscheduleid and "
				+ "bg.bulkgroupid=ig.bulkgroupid and iv.userid=ig.userid and bg.bulkgroupid=? order by scd.starttime";
		return jdbcTemplate.queryForList(getInterviewGroupQuery, bulkGroupId);
	}

	/**
	 * This method is used to search  applicant and get details 
	 * @param enterString
	 * @return : Applicant interview details
	 */
	public List getApplicantDetails(String enterString) {
		String getApplicantDetailsQuery = "SELECT i.status, i.interviewid, p.userid,i.interviewtmiid,"
				+ "concat(p.firstname,' ', p.lastname) as UserName,ip.industryname,i.designation,"
				+ "irp.interviewerrolename,concat(date_format(scd.scheduledDate,'%d %b'),' ',"
				+ "slt.slotfrom,'-',slt.slotto) as timeslot  FROM interview as i,industrylkp as ip,"
				+ "interviewerrolelkp as irp,slotschedule scd,slotlkp slt,profile as p WHERE  "
				+ "i.evaluatorscheduleid = scd.slotscheduleid AND slt.slotid = scd.slotid and "
				+ " irp.interviewerroleid=i.interviewerroleid and ip.industryid=i.industryid "
				+ "and p.userid=i.userid and  i.userid IN(SELECT userid FROM profile WHERE "
				+ " usertypeid =1 and (firstname Like ? or lastname Like ? or "
				+ "CONCAT(firstname,' ',lastname)Like ? )"
				+ ") and find_in_set(i.status,'1,2,3,4,5') order by scd.scheduledDate desc";		
		List applicantList = jdbcTemplate
				.queryForList(getApplicantDetailsQuery,'%'+enterString+'%','%'+enterString+'%','%'+enterString+'%');

		return applicantList;
	}

	/**
	 * This method is used to Search Evaluator and get Evaluator interview details
	 * @param enterString
	 * @return : Evaluator interview detail list
	 */
	public List getEvaluatorDetails(String enterString) {
		String getEvaluatorDetailsQuery = "SELECT sh.slotscheduleid,sh.status as slotStatus,iv.status as "
				+ "interviewStatus,iv.interviewtmiid,ir.interviewerrolename,"
				+ "concat(p.firstname,' ',p.lastname)as appname,concat(p1.firstname,' ',p1.lastname)as "
				+ "evaname,concat( date_format(sh.scheduledDate,'%d %b'),' ',sk.slotfrom,'-',sk.slotto)as"
				+ " slotTime,iv.interviewid,p.userid as appuserid,p1.userid as evauserid FROM "
				+ "slotschedule as sh,interview as iv,interviewerrolelkp as ir,profile as p,"
				+ "profile as p1,slotlkp as sk WHERE sk.slotid =sh.slotid and p1.userid=sh.InterviewerId"
				+ " and p.userid=iv.userid and ir.interviewerroleid=iv.interviewerroleid and "
				+ "  iv.evaluatorscheduleid=sh.slotscheduleid and InterviewerId in"
				+ "(SELECT userid FROM profile WHERE usertypeid=2 and (firstname Like ? or lastname Like ? "
				+ " or CONCAT(firstname,' ',lastname) Like ?)) and sh.status !=0 order by sh.scheduledDate desc ";
		List evaluatorList = jdbcTemplate
				.queryForList(getEvaluatorDetailsQuery,'%'+enterString+'%','%'+enterString+'%','%'+enterString+'%');
		return evaluatorList;
	}

	/**
	 * This method is used to get applicant interview details based on interviewid
	 * @param interviewid
	 * @return : interview details list
	 */
	public List getApplicantDetailsOnInterviewId(int interviewid) {
		String getApplicantDetailsQuery = "SELECT scd.slotscheduleid,scd.starttime,i.interviewid,"
				+ "concat( p2.firstname, ' ', p2.lastname ) AS evaname,ivp.typeofinterview,"
				+ "i.interviewtmiid,concat(p.firstname,' ', p.lastname) as UserName, "
				+ "ip.industryname,i.designation,irp.interviewerrolename,ilp.locationname,"
				+ "imp.interviewtypename,concat(DATE_FORMAT(CONVERT_TZ(scd.starttime,'+00:00',"
				+ "p.timezone),'%h:%i %p'),'-',DATE_FORMAT(CONVERT_TZ(scd.endtime,'+00:00',"
				+ "p.timezone),'%h:%i %p')) as timeslot,DATE_FORMAT(CONVERT_TZ(scd.starttime,"
				+ "'+00:00',p.timezone),'%d %b,%Y') as date,  ia.actualamount as amount,p.emailid,"
				+ "p.firstname,p.mobileno,p.countrycode FROM industrylkp as ip,interviewerrolelkp"
				+ " as irp,interviewamount ia,interviewlocationlkp as ilp,inteviewmodelkp as imp,"
				+ "slotschedule scd,slotlkp slt,profile as p,profile p2,interviewtypelkp as ivp,"
				+ "interview as i WHERE  p2.userid=scd.InterviewerId and i.evaluatorscheduleid = "
				+ "scd.slotscheduleid AND slt.slotid = scd.slotid and imp.interviewtypeid="
				+ "i.interviewmodeid and  ilp.locationid=i.interviewerlocationid and  "
				+ "irp.interviewerroleid=i.interviewerroleid and ip.industryid=i.industryid "
				+ "and p.userid=i.userid and ivp.interviewtypeid=i.interviewtypeid and "
				+ "ia.interviewid=i.interviewid and i.interviewid=?";
		List applicantList = jdbcTemplate.queryForList(
				getApplicantDetailsQuery, interviewid);

		return applicantList;
	}

	/**
	 * This Method is used to add audit details into  audit table
	 *
	 * @param audittype
	 * @param comments
	 * @param userId
	 * @param slotscheduleid
	 * @return 
	 */
	@Transactional
	public int addauditTable(String audittype, String comments, int userId,
			int slotscheduleid) {
		String addauditTableQuery = "INSERT INTO audit(audittype,auditdescription,auditcreatorid,auditcreatedtime,audittypeId) VALUES (?,?,?,?,?)";
		jdbcTemplate.update(addauditTableQuery, audittype, comments, userId,
				tmiUtil.getCurrentGmtTime(), slotscheduleid);
		return 0;
	}

	/**
	 * This Method is used to get the evaluator Details based on evaluator Name
	 * 
	 * 
	 * @param enterName
	 * @return : Evaluator Details list
	 */
	public List getAllEvaluatorDetailsByEvalName(String enterName) {
		String getEvalDetailsQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname)as evlname,"
				+ "concat('+',countrycode,'-',p.mobileno) as mobileno,ip.industryname,0 as evaluatorsRole,"
				+ "ep.trainingstatus as Training FROM industrylkp as ip,profile as p LEFT OUTER JOIN "
				+ "evalapprovedpreferences as ep ON ep.evaluatorid=p.userid  where ip.industryid=p.industryid "
				+ "and  p.usertypeid=2 and (p.firstname Like ? or p.lastname Like ? or CONCAT(p.firstname,' ',p.lastname)Like ?)";
		return jdbcTemplate.queryForList(getEvalDetailsQuery,'%'+enterName+'%','%'+enterName+'%','%'+enterName+'%');
	}

	/**
	 * This method is used to get Ongoing Interviews count
	 * 
	 * @return : Ongoing Interviews count
	 */
	public Object getOngoingInterviews() {
		String countQuery = "SELECT count(*) FROM interviewhistory WHERE call_status=2";
		return jdbcTemplate.queryForObject(countQuery, Integer.class);
	}

	/**
	 * This Method is used to  get Ongoing Booking Details based on admin id
	 * 
	 * @param apb
	 * @return : Ongoing Booking Details List
	 */
	public List getOngoingBookingDetails(AdminProfileBean apb) {
		String ongoingBookingQuery = "SELECT  i.interviewid,i.interviewtmiid,"
				+ " concat(p.firstname,' ',p.lastname) as ApplicantName,concat(p2.firstname,' ',"
				+ "p2.lastname) as EvaluatorName, DATE_FORMAT((SELECT CONVERT_TZ(s.starttime,'+00:00',"
				+ "sp.timezone)),'%d %b') as  date,concat(DATE_FORMAT((SELECT CONVERT_TZ(s.starttime,"
				+ " '+00:00',sp.timezone)),'%h:%i %p'),'-',DATE_FORMAT((SELECT CONVERT_TZ(s.endtime,"
				+ " '+00:00',sp.timezone)),'%h:%i %p')) as timeslot,IF(i.industryid!=0,(SELECT "
				+ " ind.industryname FROM industrylkp ind WHERE ind.industryid = i.industryid),"
				+ " (SELECT otherCategoryName FROM otherCategories WHERE otherCategoryType=2 AND"
				+ " otherpageid=2 AND otherskillid=i.interviewid)) as industry, s.starttime FROM"
				+ " interview i ,slotschedule s,  profile p,profile p2,subadminprofile sp WHERE"
				+ "  i.evaluatorscheduleid =s.slotscheduleid  AND  s.status =1 AND  i.status =0  AND p.userid =i.userid"
				+ " AND p2.userid =s.InterviewerId AND sp.adminid=? ORDER by starttime ASC";
		return jdbcTemplate.queryForList(ongoingBookingQuery, apb.getAdminId());
	}

	/**
	 * This Method is used to get the evaluator monthly slot count less then 20 slots 
	 * 
	 * @return : Evaluator Monthly  Slots Count list
	 */
	public List getEvalMonthSlotCount() {
		String evalMonthSlotCountQuery = "SELECT count(*) as evalcount ,p.userid ,"
				+ " concat(p.firstname,' ',p.lastname) as evalname FROM  slotschedule sd,profile p"
				+ " WHERE p.userid = InterviewerId AND  MONTH(sd.starttime) =  "
				+ " MONTH('"
				+ tmiUtil.getCurrentGmtTime()
				+ "') AND  YEAR(sd.starttime) = "
				+ " YEAR('"
				+ tmiUtil.getCurrentGmtTime()
				+ "') group by InterviewerId HAVING count(*)<20";
		return jdbcTemplate.queryForList(evalMonthSlotCountQuery);
	}

	/**
	 * This Method is used to  get Evaluator Unavailable Slots for applicant search date 
	 * 
	 * @return : evaluator unavailable list
	 */
	public List getApplicantUnavailableSlots() {
		String appSlotQuery = "SELECT i.interviewtmiid, u.scheduledDate,u.interviewid,CONCAT(p.firstname,' ',p.lastname)as name,p.emailid,p.mobileno,GROUP_CONCAT(sk.skillname)as skillname FROM unavailableslots u, interview i, profile p,skillnamelkp sk"
				+ " WHERE i.interviewid =u.interviewid and p.userid = (select userid from interview where interviewid =i.interviewid)"
				+ "and sk.skillnameid in (select skillnameid from skills where userid = (select userid from interview where interviewid =11)) order by u.scheduledDate desc";
		return jdbcTemplate.queryForList(appSlotQuery);
	}

	/**
	 * This Method is used to get EVALUATORS UNAVAILABLE based on Name
	 * 
	 * @param evaluatorName
	 * @return : Evaluator Unavailable Details list
	 */	
	public List<Map<String, Object>> getEvalUnavailableDataBasedOnName(
			String evaluatorName) {
		String evalQuery = "SELECT  p.userid,i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) "
				+ "as ApplicantName,p.mobileno as appMobile,r.interviewerrolename , DATE_FORMAT((SELECT CONVERT_TZ(s.starttime,"
				+ "'+00:00',p.timezone)),'%d %b,') as date,"
				+ "concat(sl.slotfrom,'-',sl.slotto) as timeslot, 'No status' as status,s.starttime ,"
				+ " CAST(TIMEDIFF( STR_TO_DATE(s.starttime,'%Y-%m-%d %H:%i:%s'),utc_timestamp())as char)as timedif,"
				+ "(TIMEDIFF( STR_TO_DATE(s.starttime,'%Y-%m-%d %H:%i:%s'),utc_timestamp())< CAST('48:00:00'  AS time))as timestatus,"
				+ "concat(DATE_FORMAT((SELECT CONVERT_TZ(s.starttime,'+00:00',p.timezone)),'%h:%i %p'),'-',"
				+ "DATE_FORMAT((SELECT CONVERT_TZ(s.endtime,'+00:00',p.timezone)),'%h:%i %p')) as applTime "
				+ "FROM interview i ,slotschedule s, interviewerrolelkp r, profile p, slotlkp sl,profile as p1 "
				+ " WHERE  i.status =1 AND i.evaluatorscheduleid =s.slotscheduleid  AND  s.status =2 AND "
				+ "s.statustimestamp <= (UTC_TIMESTAMP() - INTERVAL 8 HOUR)  AND p.userid =i.userid  "
				+ "AND r.interviewerroleid=i.interviewerroleid AND p1.userid=s.InterviewerId AND "
				+ "sl.slotid=s.slotid and p1.usertypeid=2 and (p1.firstname Like ? or "
				+ "p1.lastname Like ? or concat(p1.firstname,' ',p1.lastname) Like ?)"
				+ " UNION "
				+ "SELECT p.userid, i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) as ApplicantName,p.mobileno as appMobile,"
				+ " r.interviewerrolename ,DATE_FORMAT((SELECT CONVERT_TZ(s.starttime,"
				+ "'+00:00',p.timezone)),'%d %b') as date, "
				+ "concat(sl.slotfrom,'-',sl.slotto) as timeslot,  'Rejected' as status,s.starttime,"
				+ "CAST(TIMEDIFF( STR_TO_DATE(s.starttime,'%Y-%m-%d %H:%i:%s'),utc_timestamp())as char)as timedif,"
				+ "(TIMEDIFF( STR_TO_DATE(s.starttime,'%Y-%m-%d %H:%i:%s'),utc_timestamp())< CAST('48:00:00'  AS time))as timestatus,"
				+ " concat(DATE_FORMAT((SELECT CONVERT_TZ(s.starttime,'+00:00',p.timezone)),'%h:%i %p'),'-',"
				+ " DATE_FORMAT((SELECT CONVERT_TZ(s.endtime,'+00:00',p.timezone)),'%h:%i %p')) as applTime "
				+ " FROM interview i ,slotschedule s, interviewerrolelkp r, profile p, slotlkp sl,profile as p1"
				+ "  WHERE  i.status =1 AND i.evaluatorscheduleid =s.slotscheduleid  AND  s.status =3  "
				+ " AND p.userid =i.userid AND r.interviewerroleid=i.interviewerroleid AND"
				+ " p1.userid=s.InterviewerId AND sl.slotid=s.slotid and p1.usertypeid=2 and "
				+ "(p1.firstname Like ? or p1.lastname Like ? or "
				+ "concat(p1.firstname,' ',p1.lastname) Like ?) ORDER by starttime ASC";
		return jdbcTemplate.queryForList(evalQuery,'%'+evaluatorName+'%','%'+evaluatorName+'%','%'+evaluatorName+'%','%'+evaluatorName+'%','%'+evaluatorName+'%','%'+evaluatorName+'%');
	}

	/**
	 * This Method is used to get closed interview details based on TmiId
	 * 
	 * @param evaluatorName
	 * @return :  Closed Interview list
	 */
	public List<Map<String, Object>> getClosedInterviewsDataBasedOnTmiId(
			String evaluatorName) {
		String closedInterviewQuery = "SELECT p.userid as appuserid,p2.userid as evaluserid, i.interviewid,i.interviewtmiid,"
				+ "concat(p.firstname,' ',p.lastname) as ApplicantName, concat(p2.firstname,' ',p2.lastname) "
				+ "as EvaluatorName ,r.interviewerrolename,date_format(s.scheduledDate,'%d %b') as date, "
				+ "concat(sl.slotfrom,'-',sl.slotto) as timeslot, 'Closed' as status  FROM slotschedule s, "
				+ " interview i, interviewerrolelkp r, profile p,profile p2, slotlkp sl WHERE"
				+ " i.evaluatorscheduleid =s.slotscheduleid AND  i.status =2  AND p.userid =i.userid"
				+ " AND p2.userid=s.InterviewerId AND r.interviewerroleid=i.interviewerroleid AND "
				+ "sl.slotid=s.slotid AND i.interviewtmiid=?  order by s.starttime ASC";
		return jdbcTemplate.queryForList(closedInterviewQuery,evaluatorName);
	}

	/**
	 * This Method is used to  get closed interviews based on Applicant Name
	 * 
	 * @param evaluatorName
	 * @return : Closed interviews details list
	 */
	public List<Map<String, Object>> getClosedInterviewsDataBasedOnApplicantName(
			String applicantName) {
		String closedInterviewQuery = "SELECT p.userid as appuserid,p2.userid as evaluserid, "
				+ "i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) as ApplicantName,"
				+ " concat(p2.firstname,' ',p2.lastname) as EvaluatorName ,r.interviewerrolename,"
				+ "date_format(s.scheduledDate,'%d %b') as date, concat(sl.slotfrom,'-',sl.slotto) as timeslot, "
				+ "'Closed' as status  FROM slotschedule s,  interview i, interviewerrolelkp r, profile p,profile p2,"
				+ " slotlkp sl WHERE i.evaluatorscheduleid =s.slotscheduleid AND  i.status =2  AND p.userid =i.userid "
				+ "AND p2.userid=s.InterviewerId AND r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid AND "
				+ " (p.firstname LIKE ? or p.lastname LIKE ? or concat(p.firstname,' ',p.lastname)LIKE ?)"
				+ " and p.usertypeid=1 order by s.starttime ASC";
		return jdbcTemplate.queryForList(closedInterviewQuery,'%'+applicantName+'%','%'+applicantName+'%','%'+applicantName+'%');
	}

	/**
	 * This Method is used to get closed interviews based on Evaluator Name
	 * 
	 * @param evaluatorName
	 * @return : Closed interviews details list
	 */
	public List<Map<String, Object>> getClosedInterviewsDataBasedOnEvaluatorName(
			String evaluatorName) {
		String closedInterviewQuery = "SELECT p.userid as appuserid,p2.userid as evaluserid, "
				+ "i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) as ApplicantName,"
				+ " concat(p2.firstname,' ',p2.lastname) as EvaluatorName ,r.interviewerrolename,"
				+ "date_format(s.scheduledDate,'%d %b') as date, concat(sl.slotfrom,'-',sl.slotto) as timeslot, "
				+ "'Closed' as status  FROM slotschedule s,  interview i, interviewerrolelkp r, profile p,profile p2,"
				+ " slotlkp sl WHERE i.evaluatorscheduleid =s.slotscheduleid AND  i.status =2  AND p.userid =i.userid "
				+ "AND p2.userid=s.InterviewerId AND r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid AND "
				+ " (p2.firstname LIKE ? or p2.lastname LIKE ? or concat(p2.firstname,' ',p2.lastname)LIKE ? )"
				+ " and p2.usertypeid=2 order by s.starttime ASC";
		return jdbcTemplate.queryForList(closedInterviewQuery,'%'+evaluatorName+'%','%'+evaluatorName+'%','%'+evaluatorName+'%');
	}

	/**
	 * This Method is used to get ongoing call details based on adminid and status
	 *
	 * @param status
	 * @param adminId
	 * @return : ongoing call details list
	 */
	public List getadminDetails(String status, int adminId) {
		String adminDetailsQuery = "SELECT p1.userid as EvaluatorUserId,p.userid as applicantUserId,i.interviewid "
				+ "as intId, ih.call_status, i.interviewtmiid AS InterviewID, concat(p1.firstname,' ',"
				+ "p1.lastname) AS EvaluatorName, p1.mobileno AS EvaluatorNumber,concat(p.firstname,' ',"
				+ "p.lastname) AS ApplicantName, p.mobileno AS ApplicantNumber, s.scheduledDate AS Date,"
				+ " concat( sl.slotfrom, '-', sl.slotto ) AS 'Time' FROM interviewhistory ih,subadminprofile sap,"
				+ " interview i, profile p, profile p1, slotschedule s, slotlkp sl WHERE p.userid = i.userid "
				+ "AND i.evaluatorscheduleid = s.slotscheduleid AND p1.userid = s.InterviewerId AND "
				+ "sl.slotid = s.slotid AND i.interviewtmiid=ih.interview_tmi_id and "
				+ "DATE(ih.interview_start_time)=date(CONVERT_TZ(utc_timestamp(),'+00:00',sap.timezone)) "
				+ "and find_in_set(call_status,?) and sap.adminid=? order by s.scheduledDate desc";
		return jdbcTemplate.queryForList(adminDetailsQuery, status, adminId);
	}

	/**
	 * This Method is used to get call Status Based on TMI Id
	 * 
	 * @param request
	 * @param response
	 * @return : ongoing call status list 
	 */
	public List getadminDetailsBasedOnTmiId(String enterString) {
		String adminDetailsQuery = "SELECT p1.userid as EvaluatorUserId,p.userid as applicantUserId,i.interviewid as intId, "
				+ "ih.call_status, i.interviewtmiid AS InterviewID, "
				+ "concat(p1.firstname,' ',p1.lastname) AS EvaluatorName, p1.mobileno AS EvaluatorNumber, "
				+ "concat(p.firstname,' ',p.lastname) AS ApplicantName, p.mobileno AS ApplicantNumber, "
				+ "s.scheduledDate AS Date, concat( sl.slotfrom, '-', sl.slotto ) AS Time FROM "
				+ "interviewhistory ih, interview i, profile p, profile p1, slotschedule s, slotlkp sl "
				+ "WHERE  p.userid = i.userid AND i.evaluatorscheduleid = s.slotscheduleid AND "
				+ "p1.userid = s.InterviewerId AND sl.slotid = s.slotid AND ih.interview_tmi_id = i.interviewtmiid "
				+ "AND i.interviewtmiid =?";
		return jdbcTemplate.queryForList(adminDetailsQuery, enterString);
	}

	/**
	 * This Method is used to get call status based on applicantName
	 * 
	 * @param enterString
	 * @return : applicant ongoing call status details list
	 */
	public List getadminDetailsBasedOnApplicantName(String enterString) {
		String adminDetailsQuery = "SELECT p1.userid as EvaluatorUserId,p.userid as applicantUserId,i.interviewid as intId,"
				+ "ih.call_status, i.interviewtmiid AS InterviewID, "
				+ "concat(p1.firstname,' ',p1.lastname) AS EvaluatorName, p1.mobileno AS "
				+ "EvaluatorNumber, concat(p.firstname,' ',p.lastname) AS ApplicantName, "
				+ "p.mobileno AS ApplicantNumber, s.scheduledDate AS Date, "
				+ "concat( sl.slotfrom, '-', sl.slotto ) AS Time FROM interviewhistory ih,"
				+ " interview i, profile p, profile p1, slotschedule s, slotlkp sl WHERE "
				+ "p.userid = i.userid AND i.evaluatorscheduleid = s.slotscheduleid AND "
				+ "p1.userid = s.InterviewerId AND sl.slotid = s.slotid AND "
				+ "ih.interview_tmi_id = i.interviewtmiid AND (p.firstname Like "
				+ " ? or p.lastname Like ? or "
				+ "concat(p.firstname,' ',p.lastname) LIKE ?) order "
				+ "by s.scheduledDate desc";		
		return jdbcTemplate.queryForList(adminDetailsQuery,'%'+enterString+'%','%'+enterString+'%','%'+enterString+'%');
	}

	/**
	 * This Method is used to get call Status Based on evaluator name
	 * 
	 * @param enterString
	 * @return :evaluator ongoing call status details list
	 */
	public List getadminDetailsBasedOnevaluator(String enterString) {

		String adminDetailsQuery = "SELECT p1.userid as EvaluatorUserId,p.userid as applicantUserId,i.interviewid as intId, "
				+ "ih.call_status, i.interviewtmiid AS InterviewID, "
				+ "concat(p1.firstname,' ',p1.lastname) AS EvaluatorName, p1.mobileno AS EvaluatorNumber, "
				+ "concat(p.firstname,' ',p.lastname) AS ApplicantName, p.mobileno AS ApplicantNumber, "
				+ "s.scheduledDate AS Date, concat( sl.slotfrom, '-', sl.slotto ) AS Time FROM "
				+ "interviewhistory ih, interview i, profile p, profile p1, slotschedule s, slotlkp sl "
				+ "WHERE  p.userid = i.userid AND i.evaluatorscheduleid = s.slotscheduleid AND "
				+ "p1.userid = s.InterviewerId AND sl.slotid = s.slotid AND ih.interview_tmi_id = i.interviewtmiid "
				+ "AND (p1.firstname Like ? or p1.lastname Like ? or concat(p1.firstname,' ',p1.lastname)"
				+ "LIKE ?) order by s.scheduledDate desc ";
		return jdbcTemplate.queryForList(adminDetailsQuery,'%'+enterString+'%','%'+enterString+'%','%'+enterString+'%');
	}

	/**
	 * This Method is used to get all Registered Users
	 * 
	 * @return : Registered users list
	 */
	public List<Map<String, Object>> getAllregisteredUsers() {
		String registeredUsersQuery = "SELECT p.userid,p.emailid,concat(p.firstname,' ',p.lastname)as "
				+ "fullname,ip.industryname,DATE_FORMAT(DATE(p.createddate), '%d-%b-%Y') as createddate,up.usertype FROM usertypelkp as up,profile "
				+ "as p LEFT OUTER JOIN industrylkp as ip on ip.industryid=p.industryid where "
				+ "p.usertypeid = up.usertypeid order By p.userid";
		return jdbcTemplate.queryForList(registeredUsersQuery);
	}

	/**
	 * This Method is used to get Interview Booking based on status
	 * 	
	 * @param typeofinterview
	 * @return : all interviews list
	 */
	public List<Map<String, Object>> getAllInterviewBookingDetails(
			String typeofinterview) {
		String allInterviewUsersQuery = "SELECT s.slotscheduleid,s.status as slotstatus,p.userid as appuserid,"
				+ "p2.userid as evaluserid,i.applicantfeedbackstatus,i.evaluatorfeedbackstatus,  "
				+ "i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) as "
				+ "ApplicantName,    concat(p2.firstname,' ',p2.lastname) as EvaluatorName ,s.starttime,"
				+ "r.interviewerrolename,date_format(s.scheduledDate,'%d %b') as date, "
				+ "concat(sl.slotfrom,'-',sl.slotto) as timeslot, i.status as interviewstatus FROM "
				+ "slotschedule s,  interview i, interviewerrolelkp r, profile p,profile p2,"
				+ "slotlkp sl WHERE i.evaluatorscheduleid =s.slotscheduleid AND i.status in(?) "
				+ "AND p.userid =i.userid AND p2.userid= s.InterviewerId AND "
				+ "r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid order by "
				+ "s.starttime desc";
		return jdbcTemplate.queryForList(allInterviewUsersQuery,typeofinterview);
	}
	/**
	 * This Method is used to get All Interview Booking Details With No Slot Status
	 * 
	 * @param typeofinterview
	 * @return : all interviews list
	 */
	public List<Map<String, Object>> getAllInterviewBookingDetailsWithNoSlotStatus(
			String typeofinterview) {		
		String allInterviewUsersQuery = "select * from (SELECT s.slotscheduleid,s.status as slotstatus,p.userid as appuserid,"
				+ "p2.userid as evaluserid,i.applicantfeedbackstatus,i.evaluatorfeedbackstatus, i.interviewid,i.interviewtmiid,"
				+ "concat(p.firstname,' ',p.lastname) as ApplicantName,    concat(p2.firstname,' ',p2.lastname) as EvaluatorName ,"
				+ "s.starttime,r.interviewerrolename,date_format(s.scheduledDate,'%d %b') as date, concat(sl.slotfrom,'-',sl.slotto)"
				+ " as timeslot, i.status as interviewstatus,'notreject' as evalstatus FROM slotschedule s,  interview i, interviewerrolelkp r,"
				+ " profile p,profile p2,slotlkp sl WHERE i.evaluatorscheduleid =s.slotscheduleid AND find_in_set(i.status,?) AND "
				+ "p.userid =i.userid AND p2.userid= s.InterviewerId AND r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid "
				+ "union all "
				+ "SELECT s.slotscheduleid,s.status as slotstatus,p.userid as appuserid,p2.userid as evaluserid,i.applicantfeedbackstatus,"
				+ "i.evaluatorfeedbackstatus,  i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) as ApplicantName,    "
				+ "concat(p2.firstname,' ',p2.lastname) as EvaluatorName,s.starttime,r.interviewerrolename,"
				+ "date_format(s.scheduledDate,'%d %b') as date, concat(sl.slotfrom,'-',sl.slotto) as timeslot,i.status as interviewstatus,'rejected' as evalstatus"
				+ " FROM slotschedule s,  interview i, interviewerrolelkp r, profile p,profile p2,slotlkp sl,evalrejectslot as ej WHERE "
				+ " p.userid =i.userid AND p2.userid= s.InterviewerId AND r.interviewerroleid=i.interviewerroleid AND sl.slotid=s.slotid "
				+ "and i.interviewid=ej.interviewid and ej.slotscheduleid =s.slotscheduleid  and s.status in (3,5)) as a order by "
				+ "starttime desc";
		return jdbcTemplate.queryForList(allInterviewUsersQuery,typeofinterview);
	}
	
	
	
	/**
	 * This Method is used to get Rejected Interview Booking Details
	 * 
	 * @return :rejected  interview details list
	 */
	public List<Map<String, Object>> getRejectInterviewBookingDetails(
			) {
		String allInterviewUsersQuery = "SELECT s.slotscheduleid,s.status as slotstatus,"
				+ "p.userid as appuserid,p2.userid as evaluserid,i.applicantfeedbackstatus,"
				+ "i.evaluatorfeedbackstatus,  i.interviewid,i.interviewtmiid,"
				+ "concat(p.firstname,' ',p.lastname) as ApplicantName,    "
				+ "concat(p2.firstname,' ',p2.lastname) as EvaluatorName,s.starttime,"
				+ "r.interviewerrolename,date_format(s.scheduledDate,'%d %b') as date,"
				+ " concat(sl.slotfrom,'-',sl.slotto) as timeslot, i.status as interviewstatus "
				+ "FROM slotschedule s,  interview i, interviewerrolelkp r, profile p,profile p2,"
				+ "slotlkp sl,evalrejectslot as ej WHERE  p.userid =i.userid AND "
				+ "p2.userid= s.InterviewerId AND r.interviewerroleid=i.interviewerroleid AND "
				+ "sl.slotid=s.slotid and i.interviewid=ej.interviewid "
				+ "and ej.slotscheduleid =s.slotscheduleid  and s.status in (3,5) "
				+ " order by s.starttime desc";
		return jdbcTemplate.queryForList(allInterviewUsersQuery);
	}

	/**
	 * This method is used to get Unavailable Slots Details based on interviewid
	 * @param ib
	 * @param apb
	 * @return : Unavailable slots details list  
	 * 
	 */
	public List getUnavailableSlotDetailsOninterviewID(InterViewBean ib,
			AdminProfileBean apb) {
		List bookedSlotsDetails = null;
		try {
			String bookedSlotsDetailsQuery = "SELECT i.resumeid,i.careerstatus,ivp.typeofinterview, "
					+ "i.interviewtmiid,concat(p.firstname,' ', p.lastname) as UserName, "
					+ "IF(i.companyName<>0, (SELECT cl.companyname FROM companyNamelkp cl  WHERE "
					+ "cl.companyId=i.companyName ), (select otherCategoryName from otherCategories "
					+ "where otherskillid = i.interviewid and otherpageid=2 and otherCategoryType=6 )) "
					+ "as companyName, i.designation,irp.interviewerrolename,ilp.locationname,  "
					+ "imp.interviewtypename, If(i.industryid!=0,(SELECT industryname FROM industrylkp "
					+ "WHERE  industryid =i.industryid), (SELECT otherCategoryName FROM otherCategories "
					+ "WHERE otherCategoryType=2 and  otherpageid=2 and otherskillid=i.interviewid))  "
					+ "as industryname,if(i.evaluatorscheduleid !=0,(SELECT  concat(DATE_FORMAT("
					+ "CONVERT_TZ(scd.starttime,'+00:00', sp.timezone),'%h:%i %p'), '-',DATE_FORMAT("
					+ "CONVERT_TZ(scd.endtime,'+00:00', sp.timezone),'%h:%i %p')) FROM 	slotschedule "
					+ "scd  WHERE i.evaluatorscheduleid=scd.slotscheduleid),'') as timeslot, if("
					+ "i.evaluatorscheduleid !=0,(SELECT DATE_FORMAT(CONVERT_TZ(scd.starttime, '+00:00',"
					+ " sp.timezone),'%d %b,%Y') FROM slotschedule scd  WHERE i.evaluatorscheduleid="
					+ "scd.slotscheduleid),'') as date,  ia.actualamount as amount, p.emailid,p.mobileno,p.countrycode "
					+ "FROM  interviewerrolelkp as irp,interviewlocationlkp as ilp, inteviewmodelkp as imp, "
					+ "profile as p,interviewtypelkp as ivp,interview as i,interviewamount ia,"
					+ "subadminprofile sp  WHERE  imp.interviewtypeid=i.interviewmodeid  and "
					+ "ilp.locationid=i.interviewerlocationid and irp.interviewerroleid=i.interviewerroleid "
					+ "and p.userid=i.userid and ivp.interviewtypeid=i.interviewtypeid and "
					+ "ia.interviewid=i.interviewid and i.interviewid=? AND sp.adminid=?";

			bookedSlotsDetails = jdbcTemplate.queryForList(
					bookedSlotsDetailsQuery, ib.getInterviewid(),
					apb.getAdminId());
		} catch (EmptyResultDataAccessException ed) {
			return null;
		}

		return bookedSlotsDetails;
	}

	/**
	 * This method is used to reschedule Applicant Slot 
	 * @param evalid
	 * @param interviewid
	 * @param slotscheduleid
	 * @param admid
	 * @param usertypeid
	 * @return
	 */
	@Transactional
	public int rescheduleApplicantSlot(final int evalid, int interviewid,
			int slotscheduleid, int admid, int usertypeid) {
		int schedulecountid = slotscheduleid;
		String preSlotidQuery = "SELECT evaluatorscheduleid FROM interview WHERE interviewid="
				+ interviewid;
		final int preSlotid = jdbcTemplate.queryForObject(preSlotidQuery,
				Integer.class);

		String evalStatusQuery = "SELECT count(*) FROM slotschedule WHERE   InterviewerId=? and "
				+ "starttime = (select starttime from slotschedule where slotscheduleid=?)";
		int evalStatusCount = jdbcTemplate.queryForObject(evalStatusQuery,
				Integer.class, evalid, slotscheduleid);
		if (slotscheduleid != 0) {
			String statusQuery = "SELECT status FROM slotschedule WHERE slotscheduleid =?";
			int status = jdbcTemplate.queryForObject(statusQuery,
					Integer.class, slotscheduleid);
			if (status == 0) {
				schedulecountid = slotscheduleid;
				String newScheduleStatusQuery = "update slotschedule set status = 4,currenttimestamp =?,statustimestamp = ? where slotscheduleid=? AND status=0";
				jdbcTemplate.update(newScheduleStatusQuery,
						tmiUtil.getCurrentGmtDate(),
						tmiUtil.getCurrentGmtDate(), slotscheduleid);

				String updateEvaluatorscheduleidQuery = "update interview set evaluatorscheduleid= ? where interviewid= ?";
				jdbcTemplate.update(updateEvaluatorscheduleidQuery,
						slotscheduleid, interviewid);
			}

		} else if (slotscheduleid == 0 && evalStatusCount == 0) {
			final String insertEvalSlotQuery = "INSERT INTO slotschedule ( slotscheduleid , slotid ,scheduledDate ,InterviewerId ,status ,"
					+ " currenttimestamp ,statustimestamp,starttime,endtime ) SELECT NULL , slotid , scheduledDate , ? AS InterviewerId,"
					+ " 4 AS  status ,utc_timestamp(),utc_timestamp(),starttime,endtime FROM slotschedule   WHERE slotscheduleid = ?";
			KeyHolder keyHolder = new GeneratedKeyHolder();
			int insertStatus = jdbcTemplate.update(
					new PreparedStatementCreator() {
						@Override
						public java.sql.PreparedStatement createPreparedStatement(
								java.sql.Connection arg0) throws SQLException {
							PreparedStatement ps = (PreparedStatement) arg0
									.prepareStatement(insertEvalSlotQuery,
											new String[] { "slotscheduleid" });
							ps.setInt(1, evalid);
							ps.setInt(2, preSlotid);
							return ps;
						}
					}, keyHolder);
			String scheduleid = keyHolder.getKey().toString();
			int slotscheduleId = Integer.parseInt(scheduleid);
			String updateEvaluatorscheduleidQuery = "update interview set evaluatorscheduleid="
					+ slotscheduleId + " where interviewid=" + interviewid;
			jdbcTemplate.update(updateEvaluatorscheduleidQuery);
			schedulecountid = slotscheduleId;
		}
		String scheduleCountQery = "SELECT count(*) FROM interview WHERE evaluatorscheduleid ="
				+ schedulecountid + " AND interviewid =" + interviewid;
		int scheduleCount = jdbcTemplate.queryForObject(scheduleCountQery,
				Integer.class);
		int starttimestatus = 2;

		if (scheduleCount == 0) {
			starttimestatus = 2;
		} else if (scheduleCount == 1) {
			String starttimeQuery = "SELECT count(*) FROM slotschedule WHERE slotscheduleid=? and "
					+ "starttime = (select starttime from slotschedule where slotscheduleid=?)";
			starttimestatus = jdbcTemplate.queryForObject(starttimeQuery,
					Integer.class, schedulecountid, preSlotid);
			String scheduleStatusQuery = "update slotschedule set status = 5 where slotscheduleid "
					+ "in(SELECT slotscheduleid FROM `evalrejectslot` WHERE interviewid=?)";
			jdbcTemplate.update(scheduleStatusQuery,interviewid);
			int evalrejectslotCount=0;
			String countevalrejectslotQuery="SELECT count(*) FROM evalrejectslot WHERE interviewid=?";
			evalrejectslotCount=jdbcTemplate.queryForObject(countevalrejectslotQuery, Integer.class,interviewid);
			if(evalrejectslotCount == 0){
			String insertCommentQuery = "INSERT INTO  evalrejectslot (slotscheduleid,comment ,currenttimestamp,"
					+ "interviewid,loggeduserid,usertypeid) VALUES (?,'admin Reassigned slot ','"
					+ tmiUtil.getCurrentGmtTime()
					+ "','"
					+ interviewid
					+ "','"
					+ admid + "','" + usertypeid + "')";
			jdbcTemplate.update(insertCommentQuery,preSlotid);
			checkaddauditTable(interviewid, Constants.AdminReject,Constants.AdminReject, tmiUtil.getAdminUserId());
			}		
		}
		return starttimestatus;
	}

	/**
	 * This method is used to get evaluator payment request list based on adminid
	 * @param adminId
	 * @return : evaluator payment request list
	 */
	public List getEvalPaymentReqList(int adminId) {
		String getEvalPaymentReqListQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname)as "
				+ "fullname,date_format(s.scheduledDate,'%M %Y')as interviewmonth,sum(ia.evalamount)as"
				+ " amount,convert_tz(ia.applieddatetime,'+00:00',sap.timezone)as applieddatetime"
				+ " FROM interviewamount ia,profile p,subadminprofile sap,slotschedule s,interview i"
				+ " WHERE p.userid=s.InterviewerId and ia.evalpaymentstatus=1 and "
				+ "ia.interviewid=i.interviewid and i.evaluatorscheduleid=s.slotscheduleid and"
				+ " s.status=4 and i.status=2 and sap.adminid=? GROUP BY p.userid,YEAR(s.scheduledDate),"
				+ "MONTH(s.scheduledDate) order by applieddatetime";
		return jdbcTemplate.queryForList(getEvalPaymentReqListQuery, adminId);
	}

	/**
	 * This method is used to update   evaluator monthly interviewamount details
	 * @param evaluserid
	 * @param interviewmonth
	 * @param referNo
	 * @param adminuserid
	 */
	@Transactional
	public void payEvalMonthlyReq(int evaluserid, String interviewmonth,
			String referNo, int adminuserid) {
		String payEvalMonthlyReqQuery = "update interviewamount ia,slotschedule s,interview i "
				+ "set evalpaymentstatus=2,processeddatetime=utc_timestamp(),"
				+ "referenceno=?,ia.adminid=? where ia.interviewid=i.interviewid and "
				+ "i.evaluatorscheduleid=s.slotscheduleid and s.status=4 "
				+ "and i.status=2 and s.InterviewerId=? and date_format(s.scheduledDate,'%M %Y')=?";
		jdbcTemplate.update(payEvalMonthlyReqQuery, referNo, adminuserid,
				evaluserid, interviewmonth);

	}

	/**
	 * This Method is used to get EVALUATORS UNAVAILABLE based on TmiId
	 *
	 * @param evaluatorName
	 * @return : Evaluator unavailable details list
	 */
	public List<Map<String, Object>> getEvalUnavailableDataBasedOnTMIId(
			String evaluatorName) {
		String evalQuery = "SELECT  p.userid,i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname)"
				+ " as ApplicantName,r.interviewerrolename , date_format(s.scheduledDate,'%d %b') as date, "
				+ "concat(sl.slotfrom,'-',sl.slotto) as timeslot, 'No status' as status,s.starttime  "
				+ "FROM interview i ,slotschedule s, interviewerrolelkp r, profile p, slotlkp sl,"
				+ "profile as p1  WHERE  i.status =1 AND i.evaluatorscheduleid =s.slotscheduleid  "
				+ "AND  s.status =2 AND s.statustimestamp <= (UTC_TIMESTAMP() - INTERVAL 8 HOUR)  "
				+ "AND p.userid =i.userid  AND r.interviewerroleid=i.interviewerroleid AND "
				+ "p1.userid=s.InterviewerId AND sl.slotid=s.slotid and p.usertypeid=1 and "
				+ "i.interviewtmiid=? UNION SELECT p.userid, i.interviewid,"
				+ "i.interviewtmiid,concat(p.firstname,' ',p.lastname) as ApplicantName, "
				+ "r.interviewerrolename ,  date_format(s.scheduledDate,'%d %b') as date, "
				+ "concat(sl.slotfrom,'-',sl.slotto) as timeslot,  'Rejected' as status,s.starttime "
				+ " FROM interview i ,slotschedule s, interviewerrolelkp r, profile p, slotlkp sl,"
				+ "profile as p1  WHERE  i.status =1 AND i.evaluatorscheduleid =s.slotscheduleid  "
				+ "AND  s.status =3   AND p.userid =i.userid AND r.interviewerroleid=i.interviewerroleid "
				+ "AND p1.userid=s.InterviewerId AND sl.slotid=s.slotid and p.usertypeid=1 and "
				+ "i.interviewtmiid=? ORDER by starttime ASC";
		return jdbcTemplate.queryForList(evalQuery,evaluatorName,evaluatorName);
	}

	/**
	 * This Method is used to get EVALUATORS UNAVAILABLE based on applicant Name
	 * 
	 * @param applicantName
	 * @return :  Evaluator unavailable details list
	 */
	public List<Map<String, Object>> getEvalUnavailableDataBasedOnApplicantName(
			String applicantName) {
		String applicantNameQuery = "SELECT  p.userid,i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname)"
				+ " as ApplicantName,r.interviewerrolename , date_format(s.scheduledDate,'%d %b') "
				+ "as date, concat(sl.slotfrom,'-',sl.slotto) as timeslot, 'No status' as status,"
				+ "s.starttime  FROM interview i ,slotschedule s, interviewerrolelkp r, profile p, "
				+ "slotlkp sl,profile as p1  WHERE  i.status =1 AND i.evaluatorscheduleid =s.slotscheduleid  "
				+ "AND  s.status =2 AND s.statustimestamp <= (UTC_TIMESTAMP() - INTERVAL 8 HOUR) "
				+ " AND p.userid =i.userid  AND r.interviewerroleid=i.interviewerroleid AND "
				+ "p1.userid=s.InterviewerId AND sl.slotid=s.slotid and p.usertypeid=1 and "
				+ "(p.firstname Like ? or p.lastname Like ? "
				+ "or concat(p.firstname,' ',p.lastname) Like ?) UNION "
				+ "SELECT p.userid, i.interviewid,i.interviewtmiid,concat(p.firstname,' ',p.lastname) "
				+ "as ApplicantName, r.interviewerrolename ,  date_format(s.scheduledDate,'%d %b') as date,"
				+ " concat(sl.slotfrom,'-',sl.slotto) as timeslot,  'Rejected' as status,s.starttime  "
				+ "FROM interview i ,slotschedule s, interviewerrolelkp r, profile p, slotlkp sl,"
				+ "profile as p1  WHERE  i.status =1 AND i.evaluatorscheduleid =s.slotscheduleid  "
				+ "AND  s.status =3   AND p.userid =i.userid AND r.interviewerroleid=i.interviewerroleid "
				+ "AND p1.userid=s.InterviewerId AND sl.slotid=s.slotid and p.usertypeid=1 and "
				+ "(p.firstname Like ? or p.lastname Like ? or concat(p.firstname,' ',p.lastname)"
				+ " Like ?) ORDER by starttime ASC";
		return jdbcTemplate.queryForList(applicantNameQuery,'%'+applicantName+'%','%'+applicantName+'%','%'+applicantName+'%','%'+applicantName+'%','%'+applicantName+'%','%'+applicantName+'%');
	}

	/**
	 * This Method is used to get Applicant available solts for reschedule interview 
	 * 
	 * @param interviewerId
	 * @param userId
	 * @param starttimestamp
	 * @param applidate
	 * @return : Applicant available solts list
	 */
	@Transactional
	public List getApplicantAvailableSlotsForReschedule(String interviewerId,
			int userId, String starttimestamp, String applidate) {
		String scheduleWeekQuery = "call getApplicantAvailableSlotsForReschedule(?,?,?,?);";
		List weekSlotsList = jdbcTemplate.queryForList(scheduleWeekQuery,
				interviewerId, userId, starttimestamp, applidate);
		return weekSlotsList;
	}

	/**
	 * This Method is used to get Interviews counts based on interview status id
	 *
	 * @param interviewbookedid
	 * @return : Interviews count
	 */
	public int getInterviewCount(int interviewbookedid) {
		String interviewCountQuery = "SELECT count(*) FROM interview as iv,slotschedule as sh"
				+ " WHERE sh.slotscheduleid=iv.evaluatorscheduleid and iv.status in(?) ";
		return jdbcTemplate.queryForObject(interviewCountQuery, Integer.class,
				interviewbookedid);
	}
	/**
	 * This Method is used to get Rejected Interviews Count
	 * 
	 * @return : Rejected Interviews Count
	 */
	public int getRejectInterviewCount() {
		String interviewCountQuery = "SELECT count(*) FROM interview as iv,slotschedule as sh ,evalrejectslot as ej"
				+ " WHERE sh.slotscheduleid=ej.slotscheduleid and iv.interviewid=ej.interviewid and sh.status in (3,5)";
		return jdbcTemplate.queryForObject(interviewCountQuery, Integer.class
				);
	}

	/**
	 * This Method is used to check groupName is already exist or not
	 * 
	 * @param groupName
	 * @return : count
	 */
	public int checkBulkGroup(String groupName) {
		int count = 0;
		String checkBulkGroupQuery = "SELECT count(*) FROM bulkgroup WHERE bulkgroupname like(?)";
		count = jdbcTemplate.queryForObject(checkBulkGroupQuery, Integer.class,
				groupName);
		if (count == 1) {
			String getGroupIdQuery = "SELECT bulkgroupid FROM bulkgroup WHERE bulkgroupname like(?)";
			count = jdbcTemplate.queryForObject(getGroupIdQuery, Integer.class,
					groupName);
		}
		return count;
	}

	/**
	 * This Method is used to check groupId and UsererId is already exist or not
	 * 
	 * @param bulkGroupId
	 * @param emailId
	 * @return : count
	 */
	public int checkUserIdBulkGroup(int bulkGroupId, int emailId) {
		String checkUserIdBulkGroupQuery = "SELECT count(*) FROM interviewgroup WHERE bulkgroupid=? and userid=?";
		return jdbcTemplate.queryForObject(checkUserIdBulkGroupQuery,
				Integer.class, bulkGroupId, emailId);
	}

	/**
	 * This Method is used to check groupId and UserId is already exist or not
	 * 
	 * @param emailId
	 * @return : userid
	 */
	public int getUserId(String emailId) {
		String userIdQuery = "SELECT userid FROM profile WHERE emailid = ?";
		return jdbcTemplate.queryForObject(userIdQuery, Integer.class, emailId);
	}

	/**
	 * This Method is used to update interview to close  
	 * 
	 * @param interviewid
	 * @return
	 */
	@Transactional
	public void updateInterviewStatus(int interviewid) {
		String updateinterviewQuery = "UPDATE interview SET status=? where interviewid=?";
		jdbcTemplate.update(updateinterviewQuery, Constants.interviewClosedId,
				interviewid);

	}

	/**
	 * This Method is used to add or update audit table while admin enter feedback
	 *  
	 * @param interviewId
	 * @param feedback
	 * @param comments
	 * @param adminUserId
	 */
	@Transactional
	public void checkaddauditTable(int interviewId, String feedback,String comments,
			int adminUserId) {
		int count = 0;
		String checkadudittableQuery = "SELECT count(*) FROM audit WHERE audittype=? and audittypeId=?";
		count = jdbcTemplate.queryForObject(checkadudittableQuery,
				Integer.class, feedback, interviewId);
		if (count == 1) {
			String deleteAuditQuery = "DELETE FROM audit WHERE audittype=? and audittypeId=?";
			jdbcTemplate.update(deleteAuditQuery, feedback, interviewId);
		}
		String insertAudiQuery = "INSERT INTO audit(audittype,audittypeId,auditcreatorid,"
				+ "auditcreatedtime,auditdescription) VALUES (?,?,?,?,?)";
		jdbcTemplate.update(insertAudiQuery, feedback, interviewId,
				adminUserId, tmiUtil.getCurrentGmtTime(), comments);
	}

	/**
	 * This Method is used to get admin UserId based on emailid
	 * 
	 * @param emailaddress
	 * @return : adminid
	 */
	public int getAdminUserId(String emailaddress) {
		String getAdminUserIdQuery = "SELECT adminid FROM subadminprofile WHERE emailid like ?";
		return jdbcTemplate.queryForObject(getAdminUserIdQuery, Integer.class,
				emailaddress);

	}

	/**
	 * This Method is used to add an interview  and get interviewid
	 * .
	 * @param applicantId
	 * @return : interviewid
	 */
	@Transactional
	public int addInterviewId(final int applicantId) {
		final String adduserQuery = "insert into interview (userid,createddate,interviewtmiid,status) VALUES(?,?,?,6)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				long timestamp = new Timestamp(new Date().getTime()).getTime();
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(adduserQuery,
								new String[] { "interviewid" });
				ps.setInt(1, applicantId);
				ps.setString(2, tmiUtil.getCurrentGmtTime());
				ps.setString(3, "INP" + String.valueOf(timestamp));
				return ps;
			}
		}, keyHolder);
		return Integer.parseInt(keyHolder.getKey().toString());
	}

	/**
	 * This Method is used to get Interview recorded Files based on interview tmi id
	 * 
	 * @param interviewTmiId
	 * @return : file_link
	 */
	public List getInterviewFiles(String interviewTmiId) {
		String getInterviewFilesQuery = "SELECT file_link FROM interview_audit where file_link is not null and interview_tmi_id=?";
		return jdbcTemplate
				.queryForList(getInterviewFilesQuery, interviewTmiId);
	}

	/**
	 * This Method is used to add adminUserId and interviewId at admininterviewbooking
	 * 
	 * @param adminUserId
	 * @param interviewId
	 */
	public void addAdminInterviewbooking(int adminUserId, int interviewId) {
		int count = 0;
		String checkInterviewIdQuery = "SELECT count(*) FROM admininterviewbooking WHERE admin=?";
		count = jdbcTemplate.queryForObject(checkInterviewIdQuery,
				Integer.class, adminUserId);
		if (count == 1) {
			String deleteAdminInterviewQuery = "DELETE FROM admininterviewbooking WHERE admin=?";
			jdbcTemplate.update(deleteAdminInterviewQuery, adminUserId);
		}
		String insertadminInterview = "INSERT INTO admininterviewbooking(admin,interviewid) VALUES (?,?)";
		jdbcTemplate.update(insertadminInterview, adminUserId, interviewId);
	}

	/**
	 * This Method is used to get Applicant available solts for interview  by admin 
	 * 
	 * @param interviewerId
	 * @param userId
	 * @param applDate
	 * @return :Applicant available solts list
	 */
	@Transactional
	public List adminGetApplicantAvailabeSlots(String interviewerId,
			int userId, String applDate) {
		String scheduleWeekQuery = "call admingetApplicantAvailabeSlots(?,?,?);";
		List weekSlotsList = jdbcTemplate.queryForList(scheduleWeekQuery,
				interviewerId, userId, applDate);
		return weekSlotsList;
	}

	/**
	 * This Method is used to Delete interview based on admin UserId and interviewId
	 * 
	 * @param userId
	 * @param interviewId
	 */
	@Transactional
	public void deleteadminbookingInteviews(int userId, String interviewId) {
		String updateSlotStatusQuery = "UPDATE slotschedule set status=0 WHERE slotscheduleid=(SELECT evaluatorscheduleid"
				+ " FROM interview WHERE interviewid='" + interviewId + "')";		
		jdbcTemplate.update(updateSlotStatusQuery);
		String deleteAdminBooking = "DELETE FROM admininterviewbooking WHERE interviewid=? and admin=?";
		jdbcTemplate.update(deleteAdminBooking, interviewId, userId);
		String deleteInserViewSkills = "DELETE FROM interview_profile_skills WHERE interviewid=? ";
		jdbcTemplate.update(deleteInserViewSkills, interviewId);
		String interviewamountQuery="DELETE FROM interviewamount WHERE interviewid=?";
		jdbcTemplate.update(interviewamountQuery, interviewId);		
		String auditQuery="DELETE FROM audit WHERE audittype=? and audittypeId=?";
		jdbcTemplate.update(auditQuery, Constants.AdminCreateInterview,interviewId);
		String deleteInterviewSlot = "DELETE FROM interview WHERE interviewid="
				+ interviewId + "";
		jdbcTemplate.update(deleteInterviewSlot);
	}

	/**
	 * This Method is used to get Evaluators List based on slots in givenTime
	 * 
	 * 
	 * @param evalList
	 * @param stimeFormat
	 * @param appTimeZone
	 * @return : Evaluators slots List
	 */
	public List getEvalListBasedNoSlotesGivenTime(String evalList,
			String stimeFormat,String appTimeZone) {
		String apptimeZoneQuey="SELECT CONVERT_TZ(STR_TO_DATE('"+stimeFormat+"','%Y-%m-%d %H:%i:%s'),'"+appTimeZone+"','+00:00')" ;
		String applicantStartTIme=jdbcTemplate.queryForObject(apptimeZoneQuey,String.class);
		String count = "";
		String getEvaluatorUserIdsQuerys = "select getEvalWithNoslotsAtGivenTime(?,?,0)";
		count = jdbcTemplate.queryForObject(getEvaluatorUserIdsQuerys,
				String.class, evalList, applicantStartTIme);
		String getEvalDetailsQuery = "SELECT p.userid,concat(p.firstname,' ',p.lastname) as ename,"
				+ "p.mobileno,p.timezone FROM profile p WHERE find_in_set(p.userid , '"
				+ count + "')";
		return jdbcTemplate.queryForList(getEvalDetailsQuery);
	}

	/**
	 * This Method is used to check Evaluators Slot available or not
	 * 
	 * @param startTime
	 * @param slotscheduleUserId
	 * @param slotscheduleId
	 * @param timezone
	 * @return : count
	 */
	public int checkSlot(String startTime, String slotscheduleUserId,
			String slotscheduleId, String timezone) {
		String checkSlotQuery = "SELECT count(*) FROM slotschedule WHERE "
				+ "InterviewerId=? and starttime=CONVERT_TZ(STR_TO_DATE('"
				+ startTime + "','%Y-%m-%d %H:%i:%s'),'" + timezone
				+ "','+00:00')" + " and slotid=?";
		return jdbcTemplate.queryForObject(checkSlotQuery, Integer.class,
				slotscheduleUserId, slotscheduleId);
	}

	/**
	 * This Method is used to  add Evaluator Schedule into DataBase
	 * 
	 * @param slotid
	 * @param date
	 * @param userId
	 * @param timezone
	 * @return : slotscheduleid
	 */
	@Transactional
	public int adminaddEvaluatorSchedule(final String slotid,
			final String date, final int userId, final String timezone) {
		final String slotscheduleQuery = "INSERT INTO slotschedule(slotid,scheduledDate,InterviewerId,"
				+ "currenttimestamp,starttime,endtime)  VALUES (?,'"
				+ date
				+ "',?,'"
				+ tmiUtil.getCurrentGmtTime()
				+ "',"
				+ "convert_tz((select concat('"
				+ date
				+ "',' ',slkp.slottime) from slotlkp slkp where slkp.slotid=?),'"
				+ timezone
				+ "' "
				+ ",'+00:00'),convert_tz((select concat('"
				+ date
				+ "',' ',slkp.slottime) from slotlkp slkp "
				+ " where slkp.slotid =?),'" + timezone + "','+01:00'))";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(slotscheduleQuery,
								new String[] { "slotscheduleid" });
				ps.setString(1, slotid);
				ps.setInt(2, userId);
				ps.setString(3, slotid);
				ps.setString(4, slotid);
				return ps;
			}
		}, keyHolder);
		String interviewid = keyHolder.getKey().toString();
		int slotscheduleid = Integer.parseInt(interviewid);
		return slotscheduleid;
	}
	/**
	 * This method is to get verified email details
	 * 	
	 * @return : verified email details list
	 */
	public List sendVerificationEmail() {
		String sendVerificationEmailQuery = "select u.username,p.firstname,e.securitycode from "
				+ "users u,profile p,emailverification e where p.emailid=e.emailid and "
				+ "p.emailid=u.username and p.usertypeid=1 and u.enabled=0";
		return jdbcTemplate.queryForList(sendVerificationEmailQuery);
	}

	/**
	 * This Method is used to cancel interview and refund money to applicant wallet
	 * 
	 * @param myinterviewid
	 * @param mytmiid
	 * @param appuserid
	 * @param loggeduserid
	 * @param cancelslotReson
	 * @return : status
	 */
	@Transactional
	public boolean cancelApplicantInterviewid(int myinterviewid, String mytmiid,
			int appuserid, int loggeduserid,String cancelslotReson) {
		boolean cancelStatus=false;	
		String appamountQuery = "SELECT appamount FROM interviewamount WHERE interviewid=?";
		String appamount = jdbcTemplate.queryForObject(appamountQuery,
				String.class, myinterviewid);
		String preSlotidQuery ="SELECT evaluatorscheduleid FROM interview WHERE interviewid =?";
		int preSlotid=jdbcTemplate.queryForObject(preSlotidQuery,Integer.class, myinterviewid);
		String insertWalletQuery = "INSERT INTO wallet(userid, amount, reference, status,"
				+ " currenttimestamp) VALUES (?,?,?,?,?)";
		int walletstatus = jdbcTemplate.update(insertWalletQuery,appuserid,
				appamount, mytmiid, Constants.walletStatusTrue,
				tmiUtil.getCurrentGmtTime());		
		checkaddauditTable(myinterviewid,Constants.AdminCancel,cancelslotReson,loggeduserid);
		if(walletstatus==1){
		String updateinterviewstatusQuery = "update interview set status=3 where interviewid=?";
		jdbcTemplate.update(updateinterviewstatusQuery, myinterviewid);
		
		String scheduleStatusQuery = "update slotschedule set status = 0 where slotscheduleid=? and status not in(3,5)";
		jdbcTemplate.update(scheduleStatusQuery, preSlotid);
		cancelStatus=true;
		}else{
			cancelStatus=false;
		}
		return cancelStatus;
	}

	/**
	 * This method is used to get all Applicant Booked Interviews
	 * @return : applicant booked interviews list
	 */
	public List getAllAplicantBookedInterviews() {
		String applicantDetailsQuery="SELECT i.status, i.interviewid, p.userid,i.interviewtmiid,"
				+ "concat(p.firstname,' ', p.lastname) as UserName,ip.industryname,"
				+ "i.designation,irp.interviewerrolename,concat(date_format(scd.scheduledDate,'%d %b'),"
				+ "' ', slt.slotfrom,'-',slt.slotto) as timeslot FROM interview as i,industrylkp as ip,"
				+ " interviewerrolelkp as irp,slotschedule scd,slotlkp slt,profile as p WHERE  "
				+ "i.evaluatorscheduleid = scd.slotscheduleid AND slt.slotid = scd.slotid and  "
				+ "irp.interviewerroleid=i.interviewerroleid and ip.industryid=i.industryid and "
				+ "p.userid=i.userid and i.interviewmodeid <> 4 and p.usertypeid =1 and i.status=1"
				+ " order by scd.starttime desc";
		return jdbcTemplate.queryForList(applicantDetailsQuery);
	}

	/**
	 * This method is used to get Applicant Booked Interview Based On TmiId
	 * @param interviewid
	 * @return : applicant booked interviews list
	 */
	public List getAplicantBookedInterviewBasedOnTmiId(String interviewid) {
		String appinterviewQuery="SELECT i.status, i.interviewid, p.userid,i.interviewtmiid,"
				+ "concat(p.firstname,' ', p.lastname) as UserName,ip.industryname,i.designation,"
				+ "irp.interviewerrolename,concat(date_format(scd.scheduledDate,'%d %b'),' ',"
				+ " slt.slotfrom,'-',slt.slotto) as timeslot FROM interview as i,"
				+ "industrylkp as ip, interviewerrolelkp as irp,slotschedule scd,slotlkp slt,"
				+ "profile as p WHERE  i.evaluatorscheduleid = scd.slotscheduleid AND "
				+ "slt.slotid = scd.slotid and  irp.interviewerroleid=i.interviewerroleid"
				+ " and ip.industryid=i.industryid and i.interviewmodeid <> 4 and p.userid=i.userid and  "
				+ " p.usertypeid =1 and i.status=1 AND i.interviewtmiid =? order by scd.starttime desc";
		return jdbcTemplate.queryForList(appinterviewQuery,interviewid);
	}
	/**
	 * This method is used to get Applicant Booked Interview Based On applicant Name
	 * @param appName
	 * @return : applicant booked interviews list
	 */
	public List getAplicantBookedInterviewsBasedOnApplicantName(
			String appName) {
		String appinterviewQuery="SELECT i.status, i.interviewid, p.userid,i.interviewtmiid,"
				+ "concat(p.firstname,' ', p.lastname) as UserName,ip.industryname,i.designation,"
				+ "irp.interviewerrolename,concat(date_format(scd.scheduledDate,'%d %b'),' ',"
				+ " slt.slotfrom,'-',slt.slotto) as timeslot FROM interview as i,"
				+ "industrylkp as ip, interviewerrolelkp as irp,slotschedule scd,slotlkp slt,"
				+ "profile as p WHERE  i.evaluatorscheduleid = scd.slotscheduleid AND "
				+ "slt.slotid = scd.slotid and  irp.interviewerroleid=i.interviewerroleid"
				+ " and ip.industryid=i.industryid and i.interviewmodeid <> 4 and p.userid=i.userid and  "
				+ " i.status=1 AND   (p.firstname LIKE ? or p.lastname LIKE ? or"
				+ " concat(p.firstname,' ',p.lastname) LIKE ? )"
				+ " and p.usertypeid=1 order by scd.starttime desc";
		return jdbcTemplate.queryForList(appinterviewQuery,'%'+appName+'%','%'+appName+'%','%'+appName+'%');
	}
	/**
	 * This method is used to get Applicant Booked Interview Based On evaluator Name
	 * @param evalName
	 * @return : applicant booked interviews list
	 */
	public List getAplicantBookedInterviewsBasedOnEvaluatorName(
			String evalName) {
		String evalnameinterviewQuery="SELECT i.status, i.interviewid, p.userid,i.interviewtmiid,"
				+ "concat(p.firstname,' ', p.lastname) as UserName,ip.industryname,i.designation,"
				+ "irp.interviewerrolename,concat(date_format(scd.scheduledDate,'%d %b'),' ',"
				+ " slt.slotfrom,'-',slt.slotto) as timeslot FROM interview as i,"
				+ "industrylkp as ip, interviewerrolelkp as irp,slotschedule scd,slotlkp slt,"
				+ "profile as p,profile as p2 WHERE  i.evaluatorscheduleid = scd.slotscheduleid AND "
				+ "slt.slotid = scd.slotid and  irp.interviewerroleid=i.interviewerroleid"
				+ " and ip.industryid=i.industryid and i.interviewmodeid <> 4 AND p.userid=i.userid"
				+ " and p2.userid=scd.InterviewerId and  "
				+ "  p2.usertypeid=2 AND i.status=1 AND  (p2.firstname LIKE ? or p2.lastname LIKE ? or"
				+ " concat(p2.firstname,' ',p2.lastname) LIKE ? ) order by scd.starttime desc";
		return jdbcTemplate.queryForList(evalnameinterviewQuery,'%'+evalName+'%','%'+evalName+'%','%'+evalName+'%');
	}
	/**
	 * This Method is used to get Applicant available solts for interview Reschedule
	 *
	 * @param interviewerId
	 * @param userId
	 * @param applidate
	 * @return : applicant available slots list
	 */
	@Transactional
	public List getAdminRescheduleApplicantAvailableSlots(String interviewerId,
			int userId, String applidate) {
		String scheduleWeekQuery = "call getAdminRescheduleApplicantAvailableSlots(?,?,?);";
		List weekSlotsList = jdbcTemplate.queryForList(scheduleWeekQuery,
				interviewerId, userId,applidate);
		return weekSlotsList;
	}
	/**
	 * This Method is used to get all Registered Users based on  emailId
	 * 
	 * @param EmailId
	 * @return : Registered Users list
	 */
	public List<Map<String, Object>> getAllregisteredUsersWithEmailId(String EmailId) {
		String registeredUsersQuery = "SELECT p.userid,p.emailid,concat(p.firstname,' ',p.lastname)as"
				+ "	fullname,ip.industryname,DATE_FORMAT(DATE(p.createddate), '%d-%b-%Y') as "
				+ "createddate,up.usertype FROM usertypelkp as up,profile as p LEFT OUTER JOIN "
				+ "industrylkp as ip on ip.industryid=p.industryid where p.usertypeid = "
				+ "up.usertypeid and p.emailid like ? ";
		return jdbcTemplate.queryForList(registeredUsersQuery,'%'+EmailId+'%');
	}
	/**
	 *  This Method is used to get evaluator Unavailable Slot interview summary based onInterview 
	 * @param ib
	 * @param apb
	 * @return : interview summary  list
	 */
	public List getUnavailableSlotInterviewSummary(InterViewBean ib,
			AdminProfileBean apb) {
		List bookedSlotsDetails = null;
		try {
			String bookedSlotsDetailsQuery = "SELECT i.resumeid,i.careerstatus,ivp.typeofinterview, "
					+ "i.interviewtmiid,concat(p.firstname,' ', p.lastname) as UserName, "
					+ "IF(i.companyName<>0, (SELECT cl.companyname FROM companyNamelkp cl  WHERE "
					+ "cl.companyId=i.companyName ), (select otherCategoryName from otherCategories "
					+ "where otherskillid = i.interviewid and otherpageid=2 and otherCategoryType=6 )) "
					+ "as companyName, i.designation,irp.interviewerrolename,ilp.locationname,  "
					+ "imp.interviewtypename, If(i.industryid!=0,(SELECT industryname FROM industrylkp "
					+ "WHERE  industryid =i.industryid), (SELECT otherCategoryName FROM otherCategories "
					+ "WHERE otherCategoryType=2 and  otherpageid=2 and otherskillid=i.interviewid))  "
					+ "as industryname,if(i.evaluatorscheduleid !=0,(SELECT  concat(DATE_FORMAT("
					+ "CONVERT_TZ(scd.starttime,'+00:00', sp.timezone),'%h:%i %p'), '-',DATE_FORMAT("
					+ "CONVERT_TZ(scd.endtime,'+00:00', sp.timezone),'%h:%i %p')) FROM 	slotschedule "
					+ "scd  WHERE i.evaluatorscheduleid=scd.slotscheduleid),'') as timeslot, if("
					+ "i.evaluatorscheduleid !=0,(SELECT DATE_FORMAT(CONVERT_TZ(scd.starttime, '+00:00',"
					+ " sp.timezone),'%d %b,%Y') FROM slotschedule scd  WHERE i.evaluatorscheduleid="
					+ "scd.slotscheduleid),'') as date, p.emailid,p.mobileno,p.countrycode "
					+ "FROM  interviewerrolelkp as irp,interviewlocationlkp as ilp, inteviewmodelkp as imp, "
					+ "profile as p,interviewtypelkp as ivp,interview as i,"
					+ "subadminprofile sp  WHERE  imp.interviewtypeid=i.interviewmodeid  and "
					+ "ilp.locationid=i.interviewerlocationid and irp.interviewerroleid=i.interviewerroleid "
					+ "and p.userid=i.userid and ivp.interviewtypeid=i.interviewtypeid and "
					+ "i.interviewid=? AND sp.adminid=?";

			bookedSlotsDetails = jdbcTemplate.queryForList(
					bookedSlotsDetailsQuery, ib.getInterviewid(),
					apb.getAdminId());
		} catch (EmptyResultDataAccessException ed) {
			return null;
		}

		return bookedSlotsDetails;
	}
}
