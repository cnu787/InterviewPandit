package com.testmyinterview.portal.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.testmyinterview.portal.InternalController;
import com.testmyinterview.portal.bean.FeedBackBean;

public class InternalDAO {
	@Autowired
	private FeedBackBean feedBackBean;
	@Autowired
	private InternalController internalController;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * This Method is used to insert Feed back to userfeedback
	 * 
	 * @param fb
	 */
	@Transactional
	public void updateFeedBack(FeedBackBean fb) {
		int count = 0;
		String countQuery = "SELECT count(*) FROM userfeedback WHERE interviewid=? and feedbacksubsectionid=? and usertypeid=?";
		count = jdbcTemplate.queryForObject(countQuery, Integer.class,
				fb.getInterViewId(), fb.getEvalId(), fb.getUserType());
		if (count != 0) {
			String deleteQuey = "DELETE FROM userfeedback WHERE interviewid=? and feedbacksubsectionid=? and usertypeid=?";
			jdbcTemplate.update(deleteQuey, fb.getInterViewId(),
					fb.getEvalId(), fb.getUserType());
		}
		String feedBackQuery = "INSERT INTO userfeedback(interviewid,feedbacksubsectionid,rating, notes,usertypeid,status) "
				+ "VALUES (?,?,?,?,?,?)";
		jdbcTemplate.update(feedBackQuery, fb.getInterViewId(), fb.getEvalId(),
				fb.getEvalRating(), fb.getEvalNote(), fb.getUserType(),
				fb.getFeedBackStatusId());

	}

	/**
	 * This Method is used to return applicant interview feed Back details
	 * 
	 * @param interviewId
	 * @param sectionid
	 * @param usertypeid
	 * @param industryid
	 * @return
	 */
	public List getApplicantFeedBackDetails(int interviewId, int sectionid,
			int usertypeid, int industryid) {
		String getInterviewfeedbackQuery = "SELECT ub.*,fss.feedbacksubsectionid,fss.feedbacksubsectionname,fp.feedbacksectionname"
				+ " FROM feedbacksectionlkp fp inner join feedbacksubsectionlkp fss on "
				+ "fp.feedbacksectionid=fss.feedbacksectionid and fp.feedbacksectionid=? "
				+ "left join userfeedback ub  on fss.feedbacksubsectionid=ub.feedbacksubsectionid "
				+ "and ub.interviewid=? and ub.usertypeid=? and fss.industryid=? ";
		return jdbcTemplate.queryForList(getInterviewfeedbackQuery, sectionid,
				interviewId, usertypeid, industryid);
	}

	/**
	 * This Method is used to return Evaluator interview feed Back details
	 * 
	 * @param interviewId
	 * @param sectionid
	 * @param usertypeid
	 * @param industryid
	 * @return
	 */
	public List getEvaluatorFeedBackDetails(int interviewId, int sectionid,
			int usertypeid, int industryid) {
		String getInterviewfeedbackQuery = "SELECT ub.*,fss.feedbacksubsectionid as subsectionid,fss.feedbacksubsectionname,fp.feedbacksectionname"
				+ " FROM feedbacksectionlkp fp inner join feedbacksubsectionlkp fss on "
				+ "fp.feedbacksectionid=fss.feedbacksectionid and fp.feedbacksectionid!=? "
				+ "left join userfeedback ub  on fss.feedbacksubsectionid=ub.feedbacksubsectionid "
				+ "and ub.interviewid=? and ub.usertypeid=? and fss.industryid=? ORDER by subsectionid ";
		return jdbcTemplate.queryForList(getInterviewfeedbackQuery, sectionid,
				interviewId, usertypeid, industryid);
	}

	/**
	 * This method is used to update feedback
	 * @param fb
	 */
	@Transactional
	public void updateImpression(FeedBackBean fb) {
		int count = 0;
		String countQuery = "SELECT count(*) FROM userfeedback WHERE interviewid=? and feedbacksubsectionid in(SELECT "
				+ "feedbacksubsectionid FROM feedbacksubsectionlkp WHERE feedbacksectionid=4 )";
		count = jdbcTemplate.queryForObject(countQuery, Integer.class,
				fb.getInterViewId());
		if (count != 0) {
			String deleteQuey = "DELETE FROM userfeedback WHERE interviewid=? and feedbacksubsectionid "
					+ "in(SELECT feedbacksubsectionid FROM feedbacksubsectionlkp WHERE feedbacksectionid=4 )";
			jdbcTemplate.update(deleteQuey, fb.getInterViewId());
		}
		String updateQuery = "INSERT INTO userfeedback(userfeedbackid,interviewid,feedbacksubsectionid,"
				+ "rating,notes,usertypeid,status) select null,?,?,null, feedbacksubsectionname,?,? FROM "
				+ "feedbacksubsectionlkp WHERE feedbacksubsectionid=?";
		jdbcTemplate.update(updateQuery, fb.getInterViewId(), fb.getEvalId(),
				fb.getUserType(), fb.getFeedBackStatusId(), fb.getEvalId());

	}

	/**
	 *  This method is used to Edit  feedback details
	 * 
	 * @param interviewId
	 * @param feedbackoverallimpressionid
	 * @return
	 */
	public int geteditFeedBackDetails(int interviewId,
			int feedbackoverallimpressionid) {
		int interfeback = 15;
		try {
			String interviewDetailsQuery = "SELECT feedbacksubsectionid FROM userfeedback WHERE "
					+ "interviewid=? and feedbacksubsectionid in(SELECT feedbacksubsectionid FROM "
					+ "feedbacksubsectionlkp WHERE feedbacksectionid=?)";
			interfeback = jdbcTemplate.queryForObject(interviewDetailsQuery,
					Integer.class, interviewId, feedbackoverallimpressionid);
		} catch (Exception exception) {
			return 15;
		}
		return interfeback;
	}

	/**
	 *  This method is used to update interView Status
	 * @param interviewId
	 */
	@Transactional
	public void updateinterViewStatus(int interviewId) {
		String interviewStatusQuey = "UPDATE interview SET evaluatorfeedbackstatus=1,status=2 WHERE interviewid=?";
		jdbcTemplate.update(interviewStatusQuey, interviewId);

	}

	/**
	 *  This method is used to get evaluator payment details  
	 * 
	 * @param userId
	 * @return
	 */
	public List getMyPaymentList(int userId) {
		String getMyPaymentListQuery = "SELECT date_format(s.scheduledDate,'%M %Y')as month,"
				+ "sum(ia.evalamount)as amount,ia.evalpaymentstatus FROM "
				+ "interviewamount ia,slotschedule s,interview i,profile p "
				+ "where ia.interviewid=i.interviewid and i.evaluatorfeedbackstatus=1 and i.evaluatorscheduleid=s.slotscheduleid "
				+ "and s.status=4 and i.status=2 and date_format(s.scheduledDate,'%m %Y') < "
				+ "date_format(convert_tz(utc_timestamp(),'+00:00',p.timezone),'%m %Y') and "
				+ "p.userid=s.InterviewerId and s.InterviewerId=? GROUP BY YEAR(s.scheduledDate), "
				+ "MONTH(s.scheduledDate) ";
		return jdbcTemplate.queryForList(getMyPaymentListQuery, userId);
	}

	/**
	 *  This method is used to update evaluator payment status 
	 * @param status
	 * @param monthname
	 * @param userId
	 */
	@Transactional
	public void updatePaymentStatus(int status, String monthname, int userId) {
		String updatePaymentQuery = "UPDATE interviewamount ia,slotschedule s,interview i SET "
				+ "ia.evalpaymentstatus=?,ia.applieddatetime=utc_timestamp() where "
				+ "ia.interviewid=i.interviewid and i.evaluatorscheduleid=s.slotscheduleid "
				+ "and s.status=4 and i.status=2 and date_format(s.scheduledDate,'%M %Y')=? "
				+ "and s.InterviewerId=?";
		jdbcTemplate.update(updatePaymentQuery, status, monthname, userId);

	}

	/**
	 *  This method is used to get evaluator payment details based on month  
	 * @param userId
	 * @param monthname
	 * @return
	 */
	public List getMyPaymentListOnMonth(int userId, String monthname) {
		String myPaymentListOnMonthQuery = "SELECT date_format(s.scheduledDate,'%d %M %Y')as month,"
				+ "ia.evalamount as amount,ia.evalpaymentstatus,i.interviewtmiid,"
				+ "concat(p.firstname,' ',p.lastname) as name,ip.industryname,irp.interviewerrolename,"
				+ "concat(DATE_FORMAT(CONVERT_TZ(s.starttime,'+00:00',p.timezone),'%h:%i %p'),'-',"
				+ "DATE_FORMAT(CONVERT_TZ(s.endtime,'+00:00',p.timezone),'%h:%i %p')) as timeslot,"
				+ "DATE_FORMAT(s.scheduledDate,'%d %b,%Y') as date FROM interviewamount as ia,"
				+ "slotschedule as s,interview as i,profile as p,industrylkp as ip,interviewerrolelkp as irp"
				+ " where i.evaluatorfeedbackstatus=1 and i.userid=p.userid and ip.industryid=i.industryid and "
				+ "irp.interviewerroleid=i.interviewerroleid and ia.interviewid=i.interviewid and "
				+ "i.evaluatorscheduleid=s.slotscheduleid and s.status=4 and i.status=2 and "
				+ "s.InterviewerId=? and  date_format(s.scheduledDate,'%M %Y')="
				+ "date_format(STR_TO_DATE(?,'%M %Y'),'%M %Y')";
		return jdbcTemplate.queryForList(myPaymentListOnMonthQuery, userId,
				monthname);
	}

	/**
	 * this method is used to update the e-learning and assessment status
	 * @param status
	 */
	@Transactional
	public void updateElearningStatus(String status,int userId) {
		String updateElearningStatusQuery="update evalapprovedpreferences set trainingstatus=if(trainingstatus!=2,?,2) WHERE evaluatorid=?";
		jdbcTemplate.update(updateElearningStatusQuery,status,userId);
		
	}

}
