package com.testmyinterview.portal.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.testmyinterview.portal.bean.InterViewBean;
import com.testmyinterview.portal.bean.ProfileBean;

public class DropDownDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * This Method is used to get usertype lookup List
	 * 
	 * @return : usertype list
	 */
	public List getUserlkpList() {
		List userList = null;
		try {
			String cityListQuery = "SELECT * FROM usertypelkp order by usertype";
			userList = jdbcTemplate.queryForList(cityListQuery);
		} catch (Exception exception) {

		}
		return userList;
	}

	/**
	 * This Method is used to get industry lookup List
	 * 
	 * @return : industry list 
	 */
	public List getIndustryList() {
		List userList = null;
		try {
			String industryListQuery = "SELECT * FROM industrylkp order by industryid =1 DESC";
			userList = jdbcTemplate.queryForList(industryListQuery);
		} catch (Exception exception) {

		}
		return userList;
	}

	/**
	 * This Method is used to get industry  lookup not for other industries
	 * 
	 * @return : industry list 
	 */
	public List getAdminIndustryList() {
		List userList = null;
		try {
			String industryListQuery = "SELECT * FROM industrylkp WHERE industryid !=0 order by industryid";
			userList = jdbcTemplate.queryForList(industryListQuery);
		} catch (Exception exception) {

		}
		return userList;
	}

	/**
	 *  This Method is used to get Domain List lookup List based on industryId
	 * 
	 * @param industryId
	 * @return :  Domain List
	 */
	public List getDomainList(int industryId) {
		List userList = null;
		try {
			String domainListQuery = "SELECT * FROM domainlkp where industryid=? union SELECT * FROM domainlkp where domainid=0 order by domainname";
			userList = jdbcTemplate.queryForList(domainListQuery, industryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	/**
	 * This Method is used to  get Degree List
	 * 
	 * @return :  Degree List 
	 */
	public List getDegreeList() {
		List degreeList = null;
		try {
			String degreeListQuery = "SELECT * FROM degreelkp order by degreename";
			degreeList = jdbcTemplate.queryForList(degreeListQuery);
		} catch (Exception exception) {

		}
		return degreeList;
	}

	/**
	 * This Method is used to get graduate years List
	 * 
	 * @return : years List
	 */
	public List getGraduateYearList() {
		List graduateList = null;
		try {
			String graduateListQuery = "SELECT * FROM graduatelkp order by graduateId desc";
			graduateList = jdbcTemplate.queryForList(graduateListQuery);
		} catch (Exception exception) {

		}
		return graduateList;
	}

	/**
	 * This Method is used to get graduate months List
	 * 
	 * @return : graduate months List
	 */
	public List getGraduateMonthsList() {
		List graduateList = null;
		try {
			String graduateListQuery = "SELECT * FROM monthslkp where monthid!=0 ";
			graduateList = jdbcTemplate.queryForList(graduateListQuery);
		} catch (Exception exception) {

		}
		return graduateList;
	}

	/**
	 * This Method is used to get Career Months List
	 * 
	 * @return : Career Months List
	 */
	public List getCareerMonthsList() {
		List graduateList = null;
		try {
			String graduateListQuery = "SELECT * FROM monthslkp";
			graduateList = jdbcTemplate.queryForList(graduateListQuery);
		} catch (Exception exception) {

		}
		return graduateList;
	}

	/**
	 * This Method is used  to get university List
	 * 
	 * @return :  university List 
	 */
	public List getUniversityList() {
		List universityList = null;
		try {
			String graduateListQuery = "SELECT * FROM universitylkp order by universityname";
			universityList = jdbcTemplate.queryForList(graduateListQuery);
		} catch (Exception exception) {

		}
		return universityList;
	}

	/**
	 * This Method is used to get skill rating List
	 * 
	 * @return : skill rating List
	 */
	public List getSkillsRating() {
		List ratingList = null;
		try {
			String graduateListQuery = "SELECT * FROM skillratinglkp order by rating";
			ratingList = jdbcTemplate.queryForList(graduateListQuery);
		} catch (Exception exception) {

		}
		return ratingList;
	}

	/**
	 * This Method is used to get years List
	 * 
	 * @return : years List
	 */
	public List getYears() {
		List yearList = null;
		try {
			String graduateListQuery = "SELECT * FROM yearlkp order by year";
			yearList = jdbcTemplate.queryForList(graduateListQuery);
		} catch (Exception exception) {
		}
		return yearList;
	}

	/**
	 * This Method is used to get  all skill types List
	 * 
	 * @return : skill types List
	 */
	public List getSkilltypeListWithOthers() {
		List skillList = null;
		try {
			String skillListQuery = "SELECT * FROM skilltypelkp order by skilltypeid desc";
			skillList = jdbcTemplate.queryForList(skillListQuery);
		} catch (Exception exception) {

		}
		return skillList;
	}

	/**
	 *  This Method is used to get  skill types List not other skills
	 *  
	 * @return : skill types List
	 */
	public List getSkilltypeList() {
		List skillList = null;
		try {
			String skillListQuery = "SELECT * FROM skilltypelkp where skilltypeid !=0 order by skilltypeid desc";
			skillList = jdbcTemplate.queryForList(skillListQuery);
		} catch (Exception exception) {

		}
		return skillList;
	}

	/**
	 * This Method is used  to get skill names List
	 * 
	 * @return : skill names List
	 */
	public List getSkillNamesList() {
		List skillnameList = null;
		try {
			String skillListQuery = "SELECT * FROM skillnamelkp order by skillname";
			skillnameList = jdbcTemplate.queryForList(skillListQuery);
		} catch (Exception exception) {
		}
		return skillnameList;
	}

	/**
	 * This Method is used  to get Skills lookup List based on skilltypeid and industryid
	 * 
	 * @param skilltypeId
	 * @param industryId
	 * @return  : Skills lookup List
	 */
	public List getSkillsList(int skilltypeId, int industryId) {
		List skillsList = null;
		try {
			String domainListQuery = "call getSkillsList(?,?);";
			skillsList = jdbcTemplate.queryForList(domainListQuery,
					skilltypeId, industryId);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return skillsList;
	}

	/**
	 * This Method is used  to get career status based on userid
	 * @param pb
	 * @return : careerstatus
	 */
	public int getCareerStatus(ProfileBean pb) {
		int careerId = 0;
		String careerQuery = "SELECT careerstatus FROM profile WHERE userid=?";
		careerId = jdbcTemplate.queryForObject(careerQuery, Integer.class,
				pb.getUserId());

		return careerId;
	}

	/**
	 * This Method is used to get  months List
	 * 
	 * @return months list 
	 */
	public List getExperienceMonthsList() {
		List graduateList = null;
		try {
			String graduateListQuery = "SELECT * FROM monthslkp  order by monthid limit 12";
			graduateList = jdbcTemplate.queryForList(graduateListQuery);
		} catch (Exception exception) {
		}
		return graduateList;
	}

	/**
	 * This Method is used to get resume status based on userid
	 * @param ib
	 * @return : resume status
	 */
	public int getResumeStatus(InterViewBean ib) {
		String resumeQuery = "SELECT count(resumeid) FROM profile WHERE userid=?";
		int count = jdbcTemplate.queryForObject(resumeQuery, Integer.class,
				ib.getUserid());
		return count;
	}

	/**
	 * This Method is used to get Interviewer role List based on industryid
	 * 
	 * @param industryId
	 * @return : Interviewer role List
	 */
	public List getInterviewerRolelkp(int industryId) {

		String InterviewerRolelkpQuery = "SELECT * FROM interviewerrolelkp WHERE industryid=? "
				+ "union SELECT * FROM interviewerrolelkp where industryid=0  order by interviewerrolename";
		return jdbcTemplate.queryForList(InterviewerRolelkpQuery, industryId);
	}

	/**
	 * This Method is used to get Interviewer role lookup List
	 * 
	 * @return : Interviewer role lookup List
	 */
	public List getInterviewerRolelkp() {

		String InterviewerRolelkpQuery = "SELECT * FROM interviewerrolelkp order by interviewerrolename";
		return jdbcTemplate.queryForList(InterviewerRolelkpQuery);
	}

	/**
	 * This Method is used to get Interviewer location List
	 * 
	 * @return : location List 
	 */
	public List getInterviewLocations() {
		List interviewlocations = null;
		try {
			String interviewlocationsQuery = "SELECT * FROM interviewlocationlkp order by locationname";
			interviewlocations = jdbcTemplate
					.queryForList(interviewlocationsQuery);
		} catch (Exception exception) {

		}
		return interviewlocations;
	}

	/**
	 * This Method is used to get Interview types List
	 * 
	 * @return :  Interview types List
	 */
	public List getInterviewTypes() {
		List interviewtypes = null;
		try {
			String interviewtypesQuery = "SELECT * FROM interviewtypelkp order by typeofinterview";
			interviewtypes = jdbcTemplate.queryForList(interviewtypesQuery);
		} catch (Exception exception) {

		}
		return interviewtypes;
	}

	/**
	 *This method is used to get admin user role lookup list
	 * 
	 * @return :  admin user role lookup list
	 */
	public List getAdminUserRolelkp() {
		String adminuserroleQuery = "SELECT * FROM adminuserrolelkp order by roleid";
		return jdbcTemplate.queryForList(adminuserroleQuery);
	}

	/**
	 * This Method is used  to get Domains based on industryId
	 * 
	 * @param industryId
	 * @return
	 */
	public List getDomainListOnly(int industryId) {
		List userList = null;
		try {
			String domainListQuery = "SELECT * FROM domainlkp where industryid=? ";
			userList = jdbcTemplate.queryForList(domainListQuery, industryId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}

	/**
	 * This Method  is used  to get Interviewer role lookup List based on industryid
	 * 
	 * @param industryId
	 * @return : Interviewer role lookup list 
	 */
	public List getInterviewerRolelkpOnly(int industryId) {

		String InterviewerRolelkpQuery = "SELECT * FROM interviewerrolelkp WHERE industryid=?";
		return jdbcTemplate.queryForList(InterviewerRolelkpQuery, industryId);
	}
	/**
	 * This Method  is used  to get Interviewer role lookup List with rate card based on industryid
	 * 
	 * @param industryId
	 * @return : Interviewer role lookup list 
	 */
	public List getInterviewerRolelkpOnly(int industryId,int interviewId) {

		String InterviewerRolelkpQuery = "SELECT irl.*,irc.amount FROM interviewerrolelkp irl,"
				+ "interviewratecardlkp irc WHERE irc.interviewerroleid=irl.interviewerroleid "
				+ "and irc.currencytypeid=(select if(i.iplocation='IN',1,2) from interview "
				+ "i where i.interviewid=?) and irl.industryid=?";
		return jdbcTemplate.queryForList(InterviewerRolelkpQuery,interviewId,industryId);
	}
	/**
	 * This Method is used to get Dash board search type lookup
	 * 
	 * @return : Dash board search type lookup list
	 */
	public List getdashboardsearchtypelkp() {
		String dashbordlkpQuery = "SELECT * FROM dashboardtypelkp";
		return jdbcTemplate.queryForList(dashbordlkpQuery);
	}

	/**
	 * This Method is used to get InterviewId based on tmiid
	 * @param enterString
	 * @return : interviewid 
	 */
	public int getInterviewId(String enterString) {
		int interviewId = 0;
		try {
			String interviewIdQuery = "SELECT interviewid FROM interview WHERE interviewtmiid=?";
			interviewId = jdbcTemplate.queryForObject(interviewIdQuery,
					Integer.class,enterString);
		} catch (Exception e) {
			return 0;
		}
		return interviewId;
	}

	/**
	 * This method is used to get IndustryId based on userid
	 * @param userId
	 * @return : industryid
	 */
	public int getIndustryId(int userId) {
		int industryId = 0;
		try {
			String industryQuery = "SELECT industryid FROM profile WHERE userid='"
					+ userId + "'";
			industryId = jdbcTemplate.queryForObject(industryQuery,
					Integer.class);
		} catch (Exception e) {
			return 0;
		}
		return industryId;
	}

	/**
	 * This Method is used to get company Names 
	 * 
	 * @return : company Names list
	 */
	public List getCompanyName() {
		String companyNameQuery = "SELECT * FROM companyNamelkp order by companyname ";
		return jdbcTemplate.queryForList(companyNameQuery);
	}

	/**
	 * This Method is usd to get company Names not other 
	 * 
	 * @return :  company Names list
	 */
	public List getCompanyNameNotOthers() {
		String companyNameQuery = "SELECT * FROM companyNamelkp where companyId!=0 order by companyname ";
		return jdbcTemplate.queryForList(companyNameQuery);
	}

	/**
	 * This Method is used to get Feedback Section List based on feedbackid and industryid
	 * 
	 * @param feedBackId
	 * @param industryid
	 * @return : Feedback Section List
	 */
	public List getFeedbackSection(int feedBackId, int industryid) {
		List feedBackList = null;
		try {
			String getFeedbackSectionListQuery = "SELECT * FROM feedbacksubsectionlkp WHERE feedbacksectionid=? and industryid=?";
			feedBackList = jdbcTemplate.queryForList(
					getFeedbackSectionListQuery, feedBackId, industryid);
		} catch (Exception exception) {
		}
		return feedBackList;
	}

	/**
	 * This Method is used to get other Skills  List with  based on skilltypeid and industryid 
	 * 
	 * @param skilltypeId
	 * @param industryId
	 * @return : other Skills  List
	 */
	public List getSkillsListWithothers(int skilltypeId, int industryId) {
		List skillsList = null;
		try {
			String domainListQuery = "call getSkillsListWithOthers(?,?);";
			skillsList = jdbcTemplate.queryForList(domainListQuery,
					skilltypeId, industryId);
		} catch (Exception e) {
		}
		return skillsList;
	}

	/**
	 * This Method is used to get usertypeid  based on userid
	 * @param userId
	 * @return :usertypeid
	 */
	public int getUserType(int userId) {
		String getUserTypeQuery = "SELECT usertypeid FROM profile WHERE userid='"
				+ userId + "'";
		return jdbcTemplate.queryForObject(getUserTypeQuery, Integer.class);
	}

	/**
	 * This Method is used to get Feedback Count
	 * @param usertypeid
	 * @param interviewId
	 * @return : count
	 */
	public int getFeedbackCount(int usertypeid, int interviewId) {
		int applicantFeedBack = 0;
		try {
			String applicantFeedBackQuery = "SELECT count(*) FROM userfeedback WHERE usertypeid= ? "
					+" and interviewid=? and status=1";
			applicantFeedBack = jdbcTemplate.queryForObject(
					applicantFeedBackQuery, Integer.class,usertypeid,interviewId);
		} catch (Exception e) {
			return 0;
		}
		return applicantFeedBack;
	}

	/**
	 * This Method is used to search  company names based on  matched text
	 * @param text_search
	 * @return : company names
	 */
	public List getCompanyNames(String text_search) {
		List names = null;
		try {
			String companynameQuery = "SELECT companyId, companyname FROM companyNamelkp WHERE companyId!=0 and companyname Like ? limit 10";
			names = jdbcTemplate.queryForList(companynameQuery,text_search.trim()+'%');
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return names;
	}

	/**
	 * This Method is used to search Degree Names based on  matched text 
	 * @param text_search
	 * @return : Degree Names
	 */
	public List getDegreeNames(String text_search) {
		List names = null;
		try {
			String degreeNameQuery = "SELECT degreeid, degreename FROM degreelkp WHERE degreename Like ? limit 10";
			names = jdbcTemplate.queryForList(degreeNameQuery,text_search.trim()+'%');
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return names;
	}

	/**
	 * This Method is used to search University Names based on  matched text 
	 * @param text_search
	 * @return : University Names
	 */
	public List getUniversityNames(String text_search) {
		List names = null;
		try {
			String universityNameQuery = "SELECT universityid,universityname FROM universitylkp WHERE  universityname Like ? limit 10";
			names = jdbcTemplate.queryForList(universityNameQuery,text_search.trim()+'%');
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return names;
	}

	/**
	 * this method is used to auto suggest the userMailId of applicant based on matched text
	 *
	 * @param text_search
	 * @return : Applicant Names list
	 */
	public List getApplicantNames(String text_search) {
		List names = null;
		try {
			String applicantnameQuery = "SELECT userid,emailid,concat(emailid,'-',firstname,' ',lastname) as appname,resumeid "
					+ "FROM profile WHERE usertypeid=1 and emailid Like ? limit 10";
			names = jdbcTemplate.queryForList(applicantnameQuery,text_search.trim()+'%');
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return names;
	}

	/**
	 * this method is used to get UserId Based on interviewId
	 * 
	 * @param interviewId
	 * @return :userid
	 */
	public int getUserIdBasedOnInterviewId(int interviewId) {
		String getUserIdQuery = "SELECT userid FROM interview WHERE interviewid=?";
		return jdbcTemplate.queryForObject(getUserIdQuery, Integer.class,
				interviewId);

	}

	/**
	 * this method is used to get UserId details based on interviewid
	 *
	 * @param interviewId
	 * @return :UserId details list
	 */
	public List getUserProfileDetailsOnInterViewId(int interviewId) {
		String getUserDetailsQuery = "SELECT userid,emailid FROM profile "
				+ "WHERE userid=(SELECT userid FROM interview WHERE interviewid=?)";
		return jdbcTemplate.queryForList(getUserDetailsQuery, interviewId);
	}

	/**
	 * This method is used to check UserId and interviewid at admin admininterviewbooking
	 * 
	 * @param userId
	 * @return : interviewid
	 */
	public int checkAdminInterviewID(int userId) {
		int interviewCount = 0;
		String checkAdminInterviewIDQuery = "SELECT count(*) FROM interview as iv,"
				+ " admininterviewbooking as ab WHERE ab.admin=? and iv.interviewid=ab.interviewid and"
				+ " iv.status=6";
		interviewCount = jdbcTemplate.queryForObject(
				checkAdminInterviewIDQuery, Integer.class, userId);
		if (interviewCount == 1) {
			String getInterviewIdQuery = "SELECT iv.interviewid FROM interview as iv, admininterviewbooking as ab"
					+ " WHERE ab.admin=? and iv.interviewid=ab.interviewid and iv.status=6";
			return jdbcTemplate.queryForObject(getInterviewIdQuery,
					Integer.class, userId);
		} else {
			return interviewCount;
		}
	}

	/**
	 * This method is used to get slotid from slot lookup based on slottime
	 * 
	 * @param slotId
	 * @return : slotid
	 */
	public int getSlotIdbasedOnslottime(String slotId) {
		String getSlotIdQuery = "SELECT slotid FROM slotlkp WHERE slottime=?";
		return jdbcTemplate.queryForObject(getSlotIdQuery, Integer.class,
				slotId);
	}

	/**
	 * this method is used to get time Zone of User
	 * 
	 * @param userId
	 * @return : timezone
	 */
	public String getTimeZone(String userId) {
		String timeZoneQuery = "SELECT timezone FROM profile WHERE userid=?";
		return jdbcTemplate.queryForObject(timeZoneQuery, String.class, userId);

	}

	/**
	 * this method is used to get Slot TO Time based on slotid
	 * 
	 * @param slotId
	 * @return  slot time
	 */
	public String getSlotToTime(String slotId) {
		String slotTOTimeQuery = "SELECT slotto FROM slotlkp WHERE slottime=?";
		return jdbcTemplate.queryForObject(slotTOTimeQuery, String.class,
				slotId);
	}

	/**
	 * This method is used to get slot time by slotid
	 * 
	 * @param slotscheduleId
	 * @return  slot time
	 */
	public String getSlotTime(String slotscheduleId) {
		String getSlotTimeQuery = "SELECT slottime FROM slotlkp WHERE slotid=?";
		return jdbcTemplate.queryForObject(getSlotTimeQuery, String.class,
				slotscheduleId);
	}
	/**
	 * This Method to get currency type lookup
	 * 
	 * @return : currency type
	 */
	public List getCurrencytypelkp() {
		String getCurrencytypelkpQuery = "SELECT * FROM currencytypelkp";
		return jdbcTemplate.queryForList(getCurrencytypelkpQuery);
	}
	
	/**
	 * This Method  is used  to get Interviewer role lookup List with rate card based on industryid
	 * 
	 * @param industryId
	 * @return : Interviewer role lookup list 
	 */
	public List getInterviewerRolelkprate() {
		List roles=null;
		String InterviewerRolelkpQuery = "SELECT irl.*,irc.amount,irc.currencytypeid,ird.description FROM interviewerrolelkp irl,"
				+ "interviewratecardlkp irc,interviewerroledesc ird WHERE irc.interviewerroleid=irl.interviewerroleid and irc.interviewerroleid=ird.interviewerroleid";
		roles=jdbcTemplate.queryForList(InterviewerRolelkpQuery);
		return roles;
	}
}