package com.testmyinterview.portal.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.testmyinterview.portal.bean.InterViewBean;
import com.testmyinterview.portal.bean.ProfileBean;
import com.testmyinterview.portal.util.Constants;
import com.testmyinterview.portal.util.FileUploader;
import com.testmyinterview.portal.util.TmiUtil;


public class ExternalUserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TmiUtil tmiUtil;

	/**
	 * This Method is used to get the count of interviews of the user.
	 * 
	 * @param pb
	 * @param status
	 * @return count
	 */
	public int getInterviewCount(ProfileBean pb, String status) {
		String interviewCountQuery = "SELECT count(*) FROM interview WHERE userid=? and find_in_set(status,?) ";
		int count = jdbcTemplate.queryForObject(interviewCountQuery,
				Integer.class, pb.getUserId(), status);
		return count;
	}

	/**
	 * This Method is used to get open interviews  count 
	 * 
	 * @param pb
	 * @return : count
	 */

	public int getOpenInterviewCount(ProfileBean pb) {
		String openInterviewCountQuery = "SELECT count(*) FROM interview WHERE userid=? and status=0";
		int count = jdbcTemplate.queryForObject(openInterviewCountQuery,
				Integer.class, pb.getUserId());
		return count;
	}

	/**
	 * This Method is used to get open interviewid based on userid 
	 * 
	 * @param pb
	 * @return interviewid
	 */

	public int getOpenInterviewId(ProfileBean pb) {
		String InterviewIdQuery = "SELECT interviewid FROM interview WHERE userid=? and status=0";
		int count = jdbcTemplate.queryForObject(InterviewIdQuery,
				Integer.class, pb.getUserId());
		return count;
	}

	/**
	 * this method is used to get user id based on mailAddress
	 * 
	 * @param ib
	 * @return userid
	 */
	public int getUserId(InterViewBean ib) {
		String userIdQuery = "SELECT userid FROM profile WHERE emailid=?";
		int userId = jdbcTemplate.queryForObject(userIdQuery, Integer.class,
				ib.getEmailaddress());
		return userId;
	}

	/**
	 * This Method is used to add user into interviews table and get interviewid
	 * 
	 * @param pb
	 * @return  interviewid
	 */
	@Transactional
	public int addUsertInterview(final ProfileBean pb) {
		final String adduserQuery = "insert into interview (userid,createddate,interviewtmiid) VALUES(?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					Connection connection) throws SQLException {
				long timestamp = new Timestamp(new Date().getTime()).getTime();
				PreparedStatement ps = (PreparedStatement) connection
						.prepareStatement(adduserQuery,
								new String[] { "interviewid" });
				ps.setInt(1, pb.getUserId());
				ps.setString(2, tmiUtil.getCurrentGmtTime());
				ps.setString(3, "INP" + String.valueOf(timestamp));
				return ps;
			}
		}, keyHolder);
		return Integer.parseInt(keyHolder.getKey().toString());
	}

	/**
	 * This Method is used to get  interviewid based on userid
	 * 
	 * @param ib
	 * @return interviewid
	 */
	public int getInterViewID(InterViewBean ib) {
		String intrerviewQuery = "SELECT interviewid FROM interview WHERE userid=? and status=0";
		return jdbcTemplate.queryForObject(intrerviewQuery, Integer.class,
				ib.getUserid());
	}

	/**
	 * This method is to add interviewskills Details  
	 * 
	 * @param ib
	 */
	@Transactional
	public void addInterViewSkills(final InterViewBean ib) {
		final String addEducationQuery = "INSERT INTO interviewskills(skilltypeid,skillnameid,"
				+ "skillrating,year,month,currenttimestamp) VALUES(?,?,?,?,?,?) ";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(addEducationQuery,
								new String[] { "interviewskillid" });
				ps.setInt(1, ib.getSkillTypeId());
				ps.setInt(2, ib.getSkillName());
				ps.setInt(3, ib.getSkillRating());
				ps.setInt(4, ib.getYearsOfExperience());
				ps.setInt(5, ib.getMonthsOfExperience());
				ps.setString(6, tmiUtil.getCurrentGmtTime());
				return ps;
			}
		}, keyHolder);
		String interviewskillid = keyHolder.getKey().toString();
		String interviewProfileQuery = "INSERT INTO interview_profile_skills(interviewid,skillid,pageid)"
				+ " VALUES ('"
				+ ib.getInterviewid()
				+ "','"
				+ interviewskillid
				+ "','" + Constants.interviewPageId + "')";
		jdbcTemplate.update(interviewProfileQuery);
		if (ib.getSkillTypeId() == 0) {
			addOtherCategory(Constants.interviewPageId,
					Constants.skillTypeCategoryId, ib.getUserid(),
					ib.getSkillTypeOther(), keyHolder.getKey().toString());
		}
		if (ib.getSkillName() == 0) {
			addOtherCategory(Constants.interviewPageId,
					Constants.skillNameCategoryId, ib.getUserid(),
					ib.getSkillNameOther(), keyHolder.getKey().toString());
		}
	}

	/**
	 * this method is used to add OtherCategory
	 * @param otherPageId
	 * @param otherCategoryType
	 * @param otherCategoryCreatorId
	 * @param otherCategoryName
	 * @param skillId
	 */
	@Transactional
	public void addOtherCategory(int otherPageId, int otherCategoryType,
			int otherCategoryCreatorId, String otherCategoryName, String skillId) {
		String deleteExistingOtherCategoryQuery = "DELETE FROM otherCategories WHERE otherpageid=? and "
				+ "otherCategoryType=? and otherCategoryCreatorId=?";
		String addOtherCategoryQuery = "INSERT INTO otherCategories(otherCategoryType,"
				+ "otherpageid,otherCategoryName,otherCategoryCreatorId,otherskillid,otherCategoryCreatedDate) VALUES "
				+ "(?, ?, ?, ?,?,?)";
		deleteExistingOtherCategoryQuery += " and otherskillid=" + skillId;

		jdbcTemplate.update(deleteExistingOtherCategoryQuery, otherPageId,
				otherCategoryType, otherCategoryCreatorId);

		jdbcTemplate.update(addOtherCategoryQuery, otherCategoryType,
				otherPageId, otherCategoryName, otherCategoryCreatorId,
				skillId, tmiUtil.getCurrentGmtTime());

	}
	/**
	 * this method is used to get both interview skills and profile skills
	 * @param ib
	 * @return  InterviewSkills
	 */
	public List getInterviewSkills(InterViewBean ib) {
		String InterviewQuery = "call getInterviewSkills("
				+ ib.getInterviewid() + ");";
		List intervierwlist = jdbcTemplate.queryForList(InterviewQuery);
		return intervierwlist;
	}

	/**
	 * This method is used to get Skill Details based on interviewskillid
	 * 
	 * @param skillId
	 * @return Skill  list 
	 */

	public List getInterViewSkillDetailsonId(String skillId) {
		List educationList = null;
		try {
			String educationQuery = "SELECT *,(SELECT If(count(*)=0,'',otherCategoryName) FROM"
					+ " otherCategories WHERE otherCategoryType=3 and otherpageid=2 and otherskillid=interviewskillid)as"
					+ " otherSkillType,(SELECT If(count(*)=0,'',otherCategoryName) FROM otherCategories"
					+ " WHERE otherCategoryType=4 and otherpageid=2 and otherskillid=interviewskillid)as otherSkillName FROM"
					+ " interviewskills WHERE interviewskillid=?";
			educationList = jdbcTemplate.queryForList(educationQuery, skillId);

		} catch (EmptyResultDataAccessException ed) {
			educationList = null;
		}
		return educationList;
	}

	/**
	 * This method is to updateSkills Details  
	 * 
	 * @param ib
	 */
	@Transactional
	public void updateInterViewSkills(InterViewBean ib) {
		String updateEducationQuery = "UPDATE interviewskills SET skilltypeid=?,skillnameid=?"
				+ ",skillrating=?,year=?,month=? WHERE interviewskillid=?";
		jdbcTemplate.update(updateEducationQuery, ib.getSkillTypeId(),
				ib.getSkillName(), ib.getSkillRating(),
				ib.getYearsOfExperience(), ib.getMonthsOfExperience(),
				ib.getInterviewskillid());
		if (ib.getSkillTypeId() != 0) {
			deleteOtherCategory(Constants.interviewPageId,
					Constants.skillTypeCategoryId, ib.getUserid(),
					Integer.toString(ib.getInterviewskillid()));
		}
		if (ib.getSkillName() != 0) {
			deleteOtherCategory(Constants.interviewPageId,
					Constants.skillNameCategoryId, ib.getUserid(),
					Integer.toString(ib.getInterviewskillid()));
		}
		if (ib.getSkillTypeId() == 0) {
			addOtherCategory(Constants.interviewPageId,
					Constants.skillTypeCategoryId, ib.getUserid(),
					ib.getSkillTypeOther(),
					Integer.toString(ib.getInterviewskillid()));
		}
		if (ib.getSkillName() == 0) {
			addOtherCategory(Constants.interviewPageId,
					Constants.skillNameCategoryId, ib.getUserid(),
					ib.getSkillNameOther(),
					Integer.toString(ib.getInterviewskillid()));
		}

	}
	/**
	 * this method is used to delete OtherCategory
	 * 
	 * @param otherPageId
	 * @param otherCategoryType
	 * @param otherCategoryCreatorId
	 * @param skillId
	 */
	@Transactional
	public void deleteOtherCategory(int otherPageId, int otherCategoryType,
			int otherCategoryCreatorId, String skillId) {

		String deleteExistingOtherCategoryQuery = "DELETE FROM otherCategories WHERE otherpageid=? and "
				+ "otherCategoryType=? and otherCategoryCreatorId=?";
		if (skillId == null) {
			deleteExistingOtherCategoryQuery += " and otherskillid is null";
		} else {
			deleteExistingOtherCategoryQuery += " and otherskillid=" + skillId;
		}
		jdbcTemplate.update(deleteExistingOtherCategoryQuery, otherPageId,
				otherCategoryType, otherCategoryCreatorId);
	}

	/**
	 * This method is used to get delete InterViewSkill based on InterviewSkillid
	 * 
	 * @param interviewskillId
	 */
	@Transactional
	public void deleteInterViewSkill(int interviewskillId) {
		String skillDeleteQuery = "DELETE FROM interviewskills WHERE interviewskillid=?";
		jdbcTemplate.update(skillDeleteQuery, interviewskillId);
		String otherSkillDeleteQuery = "DELETE FROM otherCategories WHERE otherskillid=? and otherpageid=2";
		jdbcTemplate.update(otherSkillDeleteQuery, interviewskillId);
	}

	/**
	 * This method is to update Skills   
	 * 
	 * @param ib
	 */
	@Transactional
	public void updateSkills(InterViewBean ib) {
		String updateEducationQuery = "UPDATE skills SET skilltypeid=?,skillnameid= ?,skillrating=?"
				+ ",year=?,month=? WHERE skillid=?";
		jdbcTemplate.update(updateEducationQuery, ib.getSkillTypeId(),
				ib.getSkillName(), ib.getSkillRating(),
				ib.getYearsOfExperience(), ib.getMonthsOfExperience(),
				ib.getSkillid());
		if (ib.getSkillTypeId() == 0) {
			addOtherCategory(Constants.profilePageId,
					Constants.skillTypeCategoryId, ib.getUserid(),
					ib.getSkillTypeOther(), Integer.toString(ib.getSkillid()));
		}
		if (ib.getSkillName() == 0) {
			addOtherCategory(Constants.profilePageId,
					Constants.skillNameCategoryId, ib.getUserid(),
					ib.getSkillNameOther(), Integer.toString(ib.getSkillid()));
		}
		if (ib.getSkillTypeId() != 0) {
			deleteOtherCategory(Constants.profilePageId,
					Constants.skillTypeCategoryId, ib.getUserid(),
					Integer.toString(ib.getSkillid()));
		}
		if (ib.getSkillName() != 0) {
			deleteOtherCategory(Constants.profilePageId,
					Constants.skillNameCategoryId, ib.getUserid(),
					Integer.toString(ib.getSkillid()));
		}
	}

	/**
	 *  This method is to update skill to interview
	 * @param userid
	 * @param skillid
	 * @param interviewid
	 */
	@Transactional
	public void updateskilltointerview(final int userid, final String skillid,
			final int interviewid) {
		String updateInterviewSkills = "UPDATE interview_profile_skills SET updateskills=1 WHERE skillid=? and pageid=2 and interviewid=?";
		jdbcTemplate.update(updateInterviewSkills, skillid, interviewid);
		final String updatefrominterviewtoprofileQuery = "INSERT INTO skills (skillid,userid,skilltypeid,skillnameid,"
				+ "skillrating,year,month,currenttimestamp) select null,?,skilltypeid,skillnameid,skillrating,year,month,'"
				+ tmiUtil.getCurrentGmtTime()
				+ "' from "
				+ "interviewskills where interviewskillid=?";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(updatefrominterviewtoprofileQuery,
								new String[] { "skillid" });
				ps.setInt(1, userid);
				ps.setString(2, skillid);

				return ps;
			}
		}, keyHolder);
	}

	/**
	 * This method is used to check count from interview_prfile_skills table
	 * 
	 * @param interviewBean
	 * @return count
	 */
	public int countCheck(InterViewBean interviewBean) {
		int count = 0;
		try {
			String countCheckQuery = "SELECT count(*) FROM interview_profile_skills WHERE interviewid=? and pageid=?";
			count = jdbcTemplate.queryForObject(countCheckQuery, Integer.class,
					interviewBean.getInterviewid(), Constants.profilePageId);
		} catch (EmptyResultDataAccessException ed) {
			count = 0;
			return count;
		}

		return count;
	}

	/**
	 * This method is used to add Profile Skills to the interview_prfile_skills
	 * 
	 * @param interviewBean
	 */
	@Transactional
	public void addProfileSkills(InterViewBean interviewBean) {
		try {
			String addProfileQuery = "INSERT INTO interview_profile_skills "
					+ "(interviewid,pageid,skillid) select ?, 1,skillid from skills where userid=? and skilltypeid!=0 and status=1 and skillnameid!=0";
			jdbcTemplate.update(addProfileQuery,
					interviewBean.getInterviewid(), interviewBean.getUserid());
		} catch (EmptyResultDataAccessException ed) {
		}

	}

	/**
	 * This method is used to delete Interview profileId to the interview_prfile_skills
	 *
	 * @param interviewskillId
	 * @param pagetype
	 */
	@Transactional
	public void deleteInterviewprofileId(int interviewskillId, int pagetype) {
		String deleteQuery = "DELETE FROM interview_profile_skills WHERE skillid='"
				+ interviewskillId + "'" + " and pageid='" + pagetype + "'";
		jdbcTemplate.update(deleteQuery);

	}

	/**
	 * This method is used to get skill names based on interviewid
	 * 
	 * @param interviewid
	 * @return skill names 
	 */
	public List getSkillsName(int interviewid) {
		List skillNames = null;
		try {
			String skillNameQuery = "SELECT ips.skillspriority,ips.pageid,sk.skillid,if(sk.skillnameid=0,(SELECT If(count(*)=0,'',"
					+ "otherCategoryName)FROM otherCategories WHERE otherCategoryType=4 and otherpageid=1 and otherskillid=sk.skillid),"
					+ "(select if(sk.skilltypeid=1,(SELECT skillname FROM nontechskillnamelkp WHERE skillnameid=sk.skillnameid),"
					+ "(SELECT skillname FROM skillnamelkp WHERE skillnameid=sk.skillnameid))))as skillname FROM skills as "
					+ "sk,skilltypelkp as st ,skillnamelkp as sn,"
					+ "interview_profile_skills as ips WHERE st.skilltypeid=sk.skilltypeid and "
					+ "sn.skillnameid=sk.skillnameid and sk.skillid=ips.skillid and ips.pageid=1 and sk.status=1 "
					+ "and ips.interviewid='"
					+ interviewid
					+ "'"
					+ "union "
					+ "SELECT ips.skillspriority,ips.pageid,ik.interviewskillid,if(ik.skillnameid=0,(SELECT If(count(*)=0,'',otherCategoryName)"
					+ "FROM otherCategories WHERE otherCategoryType=4 and otherpageid=2 and otherskillid=ik.interviewskillid),"
					+ "(select if(ik.skilltypeid=1,(SELECT skillname FROM nontechskillnamelkp WHERE skillnameid=ik.skillnameid),"
					+ "(SELECT skillname FROM skillnamelkp WHERE skillnameid=ik.skillnameid))))as skillname FROM "
					+ "interviewskills as ik,skilltypelkp as st ,skillnamelkp as sn ,"
					+ "interview_profile_skills as ips WHERE st.skilltypeid=ik.skilltypeid and sn.skillnameid=ik.skillnameid "
					+ "and ik.interviewskillid=ips.skillid and ips.pageid=2 and ips.interviewid='"
					+ interviewid + "'";
			skillNames = jdbcTemplate.queryForList(skillNameQuery);

		} catch (EmptyResultDataAccessException ed) {
			return null;
		}

		return skillNames;
	}

	/**
	 * This method is used to updateInterView
	 * @param request
	 * @param ib
	 * @param otherDomain
	 * @throws Exception
	 */
	@Transactional
	public void updateInterView(HttpServletRequest request, InterViewBean ib,
			int otherDomain) throws Exception {
		try {
			FileUploader interviewResumeUploader = new FileUploader();
			interviewResumeUploader.fileUploadFromController(request, "resume");
			String updateInterviewProfileQuery = "UPDATE interview SET industryid=?,domainid= ?"
					+ ",designation=?,interviewerroleid=?,interviewerlocationid=?,interviewmodeid=?,interviewtypeid=?,"
					+ "careerstatus=?,companyName=?,iplocation=? ";

			if (interviewResumeUploader.getFileName() != null
					&& !interviewResumeUploader.getFileName().isEmpty()) {
				updateInterviewProfileQuery += ",resumeid='"
						+ interviewResumeUploader.getFileName() + "'";
			} else if (ib.getResumeName() != null
					&& !ib.getResumeName().isEmpty()) {
				updateInterviewProfileQuery += ",resumeid='"
						+ ib.getResumeName() + "'";
			}
			updateInterviewProfileQuery += " WHERE interviewid="
					+ ib.getInterviewid();
			jdbcTemplate.update(updateInterviewProfileQuery,
					ib.getIndustryid(), ib.getDomainid(), ib.getPosition(),
					ib.getInterviewrole(), ib.getInterviewlocation(),
					ib.getInterviewmode(), ib.getInterviewtype(),
					ib.getCareer(), ib.getCompanyId(),ib.getIpLocation());

			if (ib.getIndustryid() == 0) {
				addOtherCategory(Constants.interviewPageId,
						Constants.industryCategoryId, ib.getUserid(),
						ib.getOtherIndustry(),
						Integer.toString(ib.getInterviewid()));
			}
			if (otherDomain == 0) {
				addOtherCategory(Constants.interviewPageId,
						Constants.domainCategoryId, ib.getUserid(),
						ib.getOtherDomain(),
						Integer.toString(ib.getInterviewid()));
			}
			if (ib.getIndustryid() != 0) {
				deleteOtherCategory(Constants.interviewPageId,
						Constants.industryCategoryId, ib.getUserid(),
						Integer.toString(ib.getInterviewid()));
			}
			if (otherDomain != 0) {
				deleteOtherCategory(Constants.interviewPageId,
						Constants.domainCategoryId, ib.getUserid(),
						Integer.toString(ib.getInterviewid()));
			}
			if (ib.getCompanyId() == 0) {

				addOtherCategory(Constants.interviewPageId,
						Constants.companyCategoryId, ib.getUserid(),
						ib.getCompanyName(),
						Integer.toString(ib.getInterviewid()));
			}
			if (ib.getCompanyId() != 0) {

				deleteOtherCategory(Constants.interviewPageId,
						Constants.companyCategoryId, ib.getUserid(),
						Integer.toString(ib.getInterviewid()));

			}
		} catch (org.springframework.web.multipart.MaxUploadSizeExceededException e) {
			throw e;
		}

	}

	/**
	 * This method is used to update SkillPriorty
	 * 
	 * @param ib
	 */
	@Transactional
	public void updateSkillsPriority(InterViewBean ib) {
		String updatePriortyQuery = "UPDATE interview_profile_skills SET skillspriority='"
				+ ib.getSkillPriority()
				+ "'"
				+ " WHERE interviewid='"
				+ ib.getInterviewid()
				+ "'and skillid='"
				+ ib.getSkillid()
				+ "'and pageid='" + ib.getPageid() + "'";
		jdbcTemplate.update(updatePriortyQuery);

	}

	/**
	 *  This method is used to check Industry
	 * @param ib
	 * @return count
	 */
	public int checkIndustry(InterViewBean ib) {
		String industryQuery = "SELECT count(*) FROM interview WHERE"
				+ " interviewid='" + ib.getInterviewid() + "' and industryid='"
				+ ib.getIndustryid() + "'";
		int count = jdbcTemplate.queryForObject(industryQuery, Integer.class);
		return count;
	}

	/**
	 * This method is used to delete Interview skills
	 * @param ib
	 */
	@Transactional
	public void deleteInterviewskills(InterViewBean ib) {
		String deleteOtherCategoriesSkillTypeQuery = "DELETE FROM otherCategories WHERE otherskillid in(SELECT skillid "
				+ "FROM interview_profile_skills WHERE interviewid='"
				+ ib.getInterviewid()
				+ "' and pageid=2) "
				+ "and otherpageid=2 and otherCategoryType=3";
		jdbcTemplate.update(deleteOtherCategoriesSkillTypeQuery);
		String deleteOtherCategoriesSkillNameQuery = "DELETE FROM otherCategories WHERE otherskillid in(SELECT skillid "
				+ "FROM interview_profile_skills WHERE interviewid='"
				+ ib.getInterviewid()
				+ "' and pageid=2) "
				+ "and otherpageid=2 and otherCategoryType=4";
		jdbcTemplate.update(deleteOtherCategoriesSkillNameQuery);
		String deleteinterviewSkillQuery = "DELETE FROM interviewskills WHERE interviewskillid in"
				+ "(SELECT skillid FROM interview_profile_skills WHERE interviewid='"
				+ ib.getInterviewid() + "' and pageid=2)";
		jdbcTemplate.update(deleteinterviewSkillQuery);
		String deleteinterviewQuery = "DELETE FROM interview_profile_skills WHERE interviewid='"
				+ ib.getInterviewid() + "'";
		jdbcTemplate.update(deleteinterviewQuery);
	}

	/**
	 * This method is used to Interview Details
	 * @param interviewid
	 * @return  Interview Details list
	 */
	public List getInterviewDetails(int interviewid) {
		List userList = null;
		String applicatentQuery = "SELECT *,IF(i.resumeid<>0,i.resumeid,(SELECT p.resumeid FROM profile p WHERE p.userid=i.userid)) as resume,IF(i.companyName<>0, "
				+ "(SELECT cl.companyname FROM companyNamelkp cl WHERE cl.companyId=i.companyName ), "
				+ "(select otherCategoryName from otherCategories where  otherskillid = '"
				+ interviewid
				+ "' and "
				+ "otherpageid=2 and otherCategoryType=6 )) as companynametxt,(SELECT If(count(*)=0,'',"
				+ "otherCategoryName) FROM otherCategories  WHERE otherCategoryType=2 and otherpageid=2 and "
				+ "otherskillid='"
				+ interviewid
				+ "') as otherIndustry,(SELECT If(count(*)=0,'',otherCategoryName) "
				+ "FROM otherCategories WHERE otherCategoryType=1 and otherpageid=2 and otherskillid='"
				+ interviewid
				+ "')as "
				+ "otherdomain  FROM interview i WHERE i.interviewid='"
				+ interviewid + "'";
		userList = jdbcTemplate.queryForList(applicatentQuery);
		return userList;
	}

	/**
	 * This method is used to check Industry InProfile
	 * @param ib
	 * @return count 
	 */
	public int checkIndustryInProfile(InterViewBean ib) {
		String industryQuery = "SELECT count(*) FROM profile WHERE userid='"
				+ ib.getUserid() + "' and industryid='" + ib.getIndustryid()
				+ "'";
		int count = jdbcTemplate.queryForObject(industryQuery, Integer.class);
		return count;
	}

	/**
	 * This method is used to get slot book details based on interview id 
	 *
	 * @param ib
	 * @return   slot book details list
	 */
	public List getBookedSlotDetailsOninterviewID(InterViewBean ib) {
		List bookedSlotsDetails = null;
		try {

			String bookedSlotsDetailsQuery = "SELECT i.resumeid,i.careerstatus,ivp.typeofinterview,"
					+ "i.interviewtmiid,concat(p.firstname,' ', p.lastname) as UserName, "
					+ "ip.industryname,(IF(i.companyName<>0, (SELECT cl.companyname FROM "
					+ "companyNamelkp cl WHERE cl.companyId=i.companyName ), (select otherCategoryName "
					+ "from otherCategories where otherskillid = i.interviewid and otherpageid=2 "
					+ "and otherCategoryType=6 ))) as companyName,i.designation,irp.interviewerrolename,"
					+ "ilp.locationname,imp.interviewtypename,concat(DATE_FORMAT(CONVERT_TZ(scd.starttime,"
					+ "'+00:00',p.timezone),'%h:%i %p'),'-',DATE_FORMAT(CONVERT_TZ(scd.endtime,'+00:00',"
					+ "p.timezone),'%h:%i %p'),' ',p.timezone) as timeslot,DATE_FORMAT(CONVERT_TZ(scd.starttime,"
					+ "'+00:00',p.timezone),'%d %b,%Y') as date,concat(slt.slotfrom,'-',slt.slotto,' ',"
					+ "p.timezone)as evaltime, (SELECT If(count(*)=0,'',otherCategoryName) FROM "
					+ "otherCategories WHERE otherCategoryType=2 and otherpageid=2 and "
					+ "otherskillid=i.interviewid)as otherIndustry, ia.actualamount as amount,p.emailid,p.firstname,"
					+ "p.mobileno,p.countrycode,p2.emailid as evalemailid,p2.firstname as evalfirstname,"
					+ "p2.mobileno as evalmobile,p2.countrycode as evalcountrycode  FROM industrylkp as ip,"
					+ "interviewerrolelkp as irp,interviewlocationlkp as ilp,inteviewmodelkp as imp,"
					+ "slotschedule scd,slotlkp slt,profile as p,profile p2,interviewtypelkp as ivp,"
					+ "interview as i ,interviewamount ia WHERE  p2.userid=scd.InterviewerId and "
					+ "i.evaluatorscheduleid = scd.slotscheduleid AND slt.slotid = scd.slotid and "
					+ "imp.interviewtypeid=i.interviewmodeid and  ilp.locationid=i.interviewerlocationid "
					+ "and  irp.interviewerroleid=i.interviewerroleid and ip.industryid=i.industryid and "
					+ "p.userid=i.userid and ivp.interviewtypeid=i.interviewtypeid and  "
					+ "ia.interviewid=i.interviewid and i.interviewid=?";
			bookedSlotsDetails = jdbcTemplate.queryForList(
					bookedSlotsDetailsQuery, ib.getInterviewid());
		} catch (EmptyResultDataAccessException ed) {
			return null;
		}

		return bookedSlotsDetails;
	}
	/**
	 * This method is used to get slot book details based on interviewid
	 *
	 * @param ib
	 * @return booked slot details list
	 */
	public List getBookingSlotDetailsOninterviewID(InterViewBean ib) {
		List bookedSlotsDetails = null;
		try {

			String bookedSlotsDetailsQuery = "call getBookingSlotDetailsOninterviewID(?)";
			bookedSlotsDetails = jdbcTemplate.queryForList(
					bookedSlotsDetailsQuery, ib.getInterviewid());
		} catch (EmptyResultDataAccessException ed) {
			return null;
		}

		return bookedSlotsDetails;
	}

	/**
	 * This method is used to get interview details based on userId
	 * 
	 * @param userId
	 * @return interview details list
	 */
	public List getInterViewDetails(int userId) {
		String inteviewQuery = "SELECT iv.applicantfeedbackstatus,iv.interviewid,iv.interviewtmiid,iv.evaluatorfeedbackstatus,iv.designation,iv.status,ip.industryname,"
				+ "ik.interviewerrolename,iv.status,concat(DATE_FORMAT(CONVERT_TZ(scd.starttime,'+00:00',p.timezone),'%h:%i %p'),' - ',"
				+ "DATE_FORMAT(CONVERT_TZ(scd.endtime,'+00:00',p.timezone),'%h:%i %p')) as timeslot,"
				+ "DATE_FORMAT(CONVERT_TZ(scd.starttime,'+00:00',p.timezone),'%d %b,%Y') as date FROM interview as iv,industrylkp "
				+ "as ip,interviewerrolelkp as ik ,slotschedule scd,slotlkp slt,profile p  WHERE  "
				+ "iv.evaluatorscheduleid = scd.slotscheduleid AND slt.slotid = scd.slotid and ik.interviewerroleid="
				+ "iv.interviewerroleid and ip.industryid= iv.industryid and iv.status in(1,2,3) and  iv.userid =p.userid and iv.userid ='"
				+ userId + "' order by scd.starttime desc";
		List interviewList = jdbcTemplate.queryForList(inteviewQuery);
		return interviewList;
	}

	/**
	 * This method is used to update SkillPriorty to null
	 * 
	 * @param ib
	 */
	@Transactional
	public void changeSkillPriorty(InterViewBean ib) {
		String changeSkillPriortyQuery = "UPDATE interview_profile_skills SET skillspriority =null WHERE interviewid=?";
		jdbcTemplate.update(changeSkillPriortyQuery, ib.getInterviewid());

	}

	/**
	 * This method is used to get Domain List
	 * @param ib
	 * @return Domain List
	 */
	public List getDomainLst(InterViewBean ib) {
		String domainQuery = "SELECT domainid FROM interview WHERE interviewid=? ";
		String domains = jdbcTemplate.queryForObject(domainQuery, String.class,
				ib.getInterviewid());
		String domainnamesQuery = "SELECT case domainname when 'Others' then "
				+ "(SELECT If(count(*)=0,domainname,otherCategoryName) FROM otherCategories "
				+ "WHERE otherCategoryType=1 and otherpageid=2 and otherskillid='"
				+ ib.getInterviewid()
				+ "') "
				+ "else   domainname end as domainname FROM domainlkp WHERE domainid in("
				+ domains + ") ";
		List domainList = jdbcTemplate.queryForList(domainnamesQuery);
		return domainList;
	}

	/**
	 * This method is used to update interview status
	 * 
	 * @param interviewId
	 */
	@Transactional
	public void updateInterviewStatus(String interviewId) {
		String updatePaymentStatusQuery = "update interview set status=1 where interviewtmiid='"
				+ interviewId + "'";
		jdbcTemplate.update(updatePaymentStatusQuery);
		updatePaymentStatusQuery = "UPDATE slotschedule set status=2,statustimestamp=utc_timestamp() WHERE slotscheduleid=(SELECT evaluatorscheduleid"
				+ " FROM interview WHERE interviewtmiid='" + interviewId + "')";
		jdbcTemplate.update(updatePaymentStatusQuery);

	}

	/**
	 * This method is used to update interview status to 5(cancel)
	 * 
	 * @param interviewId
	 */
	@Transactional
	public void updateInterviewStatusOnCancel(String interviewId) {

		String updatePaymentStatusQuery = "UPDATE slotschedule set status=0 WHERE slotscheduleid=(SELECT evaluatorscheduleid"
				+ " FROM interview WHERE interviewtmiid='" + interviewId + "')";
		jdbcTemplate.update(updatePaymentStatusQuery);
		updatePaymentStatusQuery = "update interview set status=4 where interviewtmiid='"
				+ interviewId + "'";
		jdbcTemplate.update(updatePaymentStatusQuery);

	}

	/**
	 * This method is used to update interview status to 5(cancel) and  schedule status to 0
	 * 
	 * @param interviewId
	 */
	@Transactional
	public void updateInterviewStatusOnFailure(String interviewId) {

		String updatePaymentStatusQuery = "UPDATE slotschedule set status=0 WHERE slotscheduleid=(SELECT evaluatorscheduleid"
				+ " FROM interview WHERE interviewtmiid='" + interviewId + "')";
		jdbcTemplate.update(updatePaymentStatusQuery);
		updatePaymentStatusQuery = "update interview set status=5 where interviewtmiid='"
				+ interviewId + "'";
		jdbcTemplate.update(updatePaymentStatusQuery);
	}

	/**
	 * This method is used to update interview status to 0 and evaluatorscheduleid to empty
	 * 
	 * @param interviewId
	 */
	@Transactional
	public void updateInterviewStatusOnTime(int interviewId) {

		String updatePaymentStatusQuery = "UPDATE slotschedule set statustimestamp=null,status=0 WHERE slotscheduleid=(SELECT evaluatorscheduleid"
				+ " FROM interview WHERE interviewid=" + interviewId + ")";
		jdbcTemplate.update(updatePaymentStatusQuery);
		updatePaymentStatusQuery = "update interview set evaluatorscheduleid=null,status=0 where interviewid="
				+ interviewId;
		jdbcTemplate.update(updatePaymentStatusQuery);

	}

	/**
	 * This method is used to update interview status to 2 (Success)
	 * 
	 * @param interviewTmiId
	 */
	@Transactional
	public void updateInterviewStatusOnSuccess(String interviewTmiId) {
		String updateInterviewStatusOnSuccessQuery = "update interview set status=2 where interviewtmiid=?";
		jdbcTemplate
				.update(updateInterviewStatusOnSuccessQuery, interviewTmiId);
	}

	/**
	 * This method is used to update payment details
	 *
	 * @param paymenttransactionid
	 * @param interviewId
	 * @param amount
	 * @param status
	 */
	@Transactional
	public void updatePaymentStatus(String paymenttransactionid,
			int interviewId, String amount, String status) {	
		String updatePaymentStatusQuery = "INSERT INTO payment(paymentid,paymenttransactionid,interviewid,"
				+ "amount,status,currenttimestamp)VALUES (NULL,?,?,?,?,?);";
		jdbcTemplate.update(updatePaymentStatusQuery, paymenttransactionid,
				interviewId, amount, status, tmiUtil.getCurrentGmtTime());

	}

	/**
	 * This method is used to get InterViewid 
	 * @param interviewId
	 * @return  interviewid
	 */
	public int getInterViewIDByTMI(String interviewId) {
		String getInterViewIDByTMIQuery = "SELECT interviewid FROM interview WHERE interviewtmiid=?";
		return jdbcTemplate.queryForObject(getInterViewIDByTMIQuery,
				Integer.class, interviewId);

	}

	/**
	 * This method is used to  update Industry To Profile
	 * 
	 * @param userid
	 * @param interviewid
	 * @return
	 */
	@Transactional
	public int updateIndustryToProfile(int userid, int interviewid) {
		String checkbothTablesQuery = "SELECT count(*) FROM profile as pf ,interview as iw "
				+ "WHERE pf.industryid=iw.industryid and iw.userid =pf.userid and pf.userid=? and iw.interviewid=?";
		int count = jdbcTemplate.queryForObject(checkbothTablesQuery,
				Integer.class, userid, interviewid);
		if (count == 0) {
			String updateIndustry = "UPDATE profile as pf,interview as iw SET pf.industryid=iw.industryid, "
					+ "pf.domainid=iw.domainid WHERE iw.userid=pf.userid and pf.userid=? and interviewid=? ";
			String deleteothers = "DELETE FROM otherCategories WHERE otherCategoryCreatorId =? and otherCategoryType in (1,2)";
			jdbcTemplate.update(updateIndustry, userid, interviewid);
			jdbcTemplate.update(deleteothers, userid);
			String updateskills = "UPDATE skills SET status=0 WHERE userid=?";
			jdbcTemplate.update(updateskills, userid);
		}
		return count;
	}

	/**
	 * This method is used to check Industry and both are profile and industry
	 * 
	 * @param ib
	 * @return count
	 */
	public int checkIndustryProfilecount(InterViewBean ib) {
		String checkbothTablesQuery = "SELECT count(*) FROM profile as pf ,interview as iw "
				+ "WHERE pf.industryid=iw.industryid and iw.userid =pf.userid and pf.userid=? and iw.interviewid=?";
		return jdbcTemplate.queryForObject(checkbothTablesQuery, Integer.class,
				ib.getUserid(), ib.getInterviewid());
	}
	/**
	 * this method is used to add OtherCategory SkillsTypes
	 * @param otherType
	 * @param otherName
	 * @param userId
	 */
	public void addOtherCategoryTypesInterview(String otherType,
			String otherName, int userId) {
		addOtherCategoryByTypesInteriview(Constants.interviewPageId,
				Constants.skillTypeCategoryId, userId, otherType, null);
		addOtherCategoryByTypesInteriview(Constants.interviewPageId,
				Constants.skillNameCategoryId, userId, otherName, null);

	}
	/**
	 * this method is used to add OtherCategory Skill Names
	 * @param otherName
	 * @param userId
	 */
	public void addOtherCategoryNameInteriview(String otherName, int userId) {
		addOtherCategoryByTypesInteriview(Constants.interviewPageId,
				Constants.skillNameCategoryId, userId, otherName, null);

	}
	/**
	 * this method is used to add OtherCategory other Industry
	 * @param otherIndustry
	 * @param otherDomain
	 * @param otherinterviewroles
	 * @param userId
	 */
	public void addIndustryotherInterview(String otherIndustry,
			String otherDomain, String otherinterviewroles, int userId) {
		addOtherCategoryByTypesInteriview(Constants.interviewPageId,
				Constants.industryCategoryId, userId, otherIndustry, null);
		addOtherCategoryByTypesInteriview(Constants.interviewPageId,
				Constants.domainCategoryId, userId, otherDomain, null);
		addOtherCategoryByTypesInteriview(Constants.interviewPageId,
				Constants.interviewCategoryId, userId, otherinterviewroles,
				null);

	}
	/**
	 * this method is used to  add Domain to otherCategory
	 * @param otherDomain
	 * @param userId
	 */
	public void addDomainotherInterview(String otherDomain, int userId) {
		addOtherCategoryByTypesInteriview(Constants.interviewPageId,
				Constants.domainCategoryId, userId, otherDomain, null);

	}
	/**
	 * this method is used to add  otherInterview to OtherCategory
	 * @param otherInterview
	 * @param userId
	 */
	public void addinterviewotherInterview(String otherInterview, int userId) {
		addOtherCategoryByTypesInteriview(Constants.interviewPageId,
				Constants.interviewCategoryId, userId, otherInterview, null);
	}

	/**
	 * this method is used to add OtherCompany to OtherCategory
	 * @param othercompanyName
	 * @param userId
	 */
	public void addOtherCompany(String othercompanyName, int userId) {
		addOtherCategoryByTypesInteriview(Constants.interviewPageId,
				Constants.companyCategoryId, userId, othercompanyName, null);
	}
	/**
	 * This method is used to add othercompanyName Career OtherCategory
	 * @param othercompanyName
	 * @param userId
	 */
	public void addOtherCompanyCareer(String othercompanyName, int userId) {
		addOtherCategoryByTypesInteriview(Constants.profilePageId,
				Constants.companyCategoryId, userId, othercompanyName, null);
	}
	/**
	 * this method is used to add Industry to OtherCategory  
	 * @param otherIndustry
	 * @param otherDomain
	 * @param userId
	 */
	public void addIndustryotherProfile(String otherIndustry,
			String otherDomain, int userId) {
		addOtherCategoryByTypesInteriview(Constants.profilePageId,
				Constants.industryCategoryId, userId, otherIndustry, null);
		addOtherCategoryByTypesInteriview(Constants.profilePageId,
				Constants.domainCategoryId, userId, otherDomain, null);
	}
	/**
	 * this method is used to add Domain to OtherCategory
	 * @param otherDomain
	 * @param userId
	 */
	public void addDomainotherProfile(String otherDomain, int userId) {
		addOtherCategoryByTypesInteriview(Constants.profilePageId,
				Constants.domainCategoryId, userId, otherDomain, null);

	}
	/**
	 * This method is used to add OtherCategory details
	 * @param otherPageId
	 * @param otherCategoryType
	 * @param otherCategoryCreatorId
	 * @param otherCategoryName
	 * @param skillId
	 */
	@Transactional
	public void addOtherCategoryByTypesInteriview(int otherPageId,
			int otherCategoryType, int otherCategoryCreatorId,
			String otherCategoryName, String skillId) {
		String addOtherCategoryQuery = "INSERT INTO otherCategories(otherCategoryType,"
				+ "otherpageid,otherCategoryName,otherCategoryCreatorId,otherskillid,otherCategoryCreatedDate) VALUES "
				+ "(?, ?, ?, ?,?,?)";
		jdbcTemplate.update(addOtherCategoryQuery, otherCategoryType,
				otherPageId, otherCategoryName, otherCategoryCreatorId,
				skillId, tmiUtil.getCurrentGmtTime());

	}

	/**
	 * This method is used to get Profile Report
	 * @param userId
	 * @return 
	 */
	public int getProfileReport(int userId) {
		String getProfileReportQuery = "select if((SELECT profilestatus FROM profile WHERE userid=?)"
				+ ",10,0)+IFNULL(if((SELECT photoid FROM profile WHERE userid=?),5,0),0)+ifnull"
				+ "(if((SELECT videoid FROM profile WHERE userid=?),5,0),0)+ifnull("
				+ "if((SELECT resumeid FROM profile WHERE userid=?),5,0),0)+if((SELECT count(*) "
				+ "FROM skills WHERE status=1 and userid=?)>0,25,0)+if((SELECT count(*) FROM education WHERE userid=?"
				+ ")>0,25,0)+if((SELECT careerstatus FROM profile WHERE userid=?)=1,25,if((SELECT "
				+ "count(*) FROM career WHERE userid=?)>0,25,0))as profilereport";
		return jdbcTemplate.queryForObject(getProfileReportQuery,
				Integer.class, userId, userId, userId, userId, userId, userId,
				userId, userId);
	}

	/**
	 * This method is used to get Evaluator Profile Report
	 * @param userId
	 * @return
	 */
	public int getEvalProfileReport(int userId) {
		String getProfileReportQuery = "select if((SELECT profilestatus FROM profile WHERE userid=?),10,0)"
				+ "+IFNULL(if((SELECT photoid FROM profile WHERE userid=?),5,0),0)+ifnull(if((SELECT "
				+ "resumeid FROM profile WHERE userid=?),5,0),0)+if((SELECT count(*) FROM skills WHERE"
				+ " status=1 and userid=?)>0,15,0)+if((SELECT count(*) FROM education WHERE userid=?)>0,15,0)+if(("
				+ "SELECT count(*) FROM career WHERE userid=?)>0,15,0)+if((SELECT count(*) FROM "
				+ "evaluatorpreferences WHERE userid=?)>0,15,0)+if((SELECT count(*) FROM "
				+ "evalapprovedpreferences WHERE evaluatorid=? and evalstatus=1 and "
				+ "trainingstatus=2)>0,20,0)as profilereport";
		return jdbcTemplate.queryForObject(getProfileReportQuery,
				Integer.class, userId, userId, userId, userId, userId, userId,
				userId, userId);
	}

	/**
	 * This method is used to get Referral Count
	 * @param pb
	 * @return count
	 */
	public Object getReferralCount(ProfileBean pb) {
		String getProfileReportQuery = "SELECT count(*) FROM profile WHERE referralid=?";
		return jdbcTemplate.queryForObject(getProfileReportQuery,
				Integer.class, pb.getUserId());
	}

	/**
	 * This method is used to get MockTest Status
	 * @param userId
	 * @return status
	 */
	public Object getMyMockTestStatus(int userId) {
		int status = 0;
		try {
			String myMockTestStatusQuery = "SELECT status FROM mocktest WHERE userid=?";
			status = jdbcTemplate.queryForObject(myMockTestStatusQuery,
					Integer.class, userId);
		} catch (Exception e) {
			status = 0;
		}
		return status;
	}

	/**
	 * This method is used to get training status
	 * @param userId
	 * @return trainingstatus
	 */
	public Object getELearningStatus(int userId) {
		int status = 0;
		try {
			String eLearningStatusQuery = "SELECT trainingstatus FROM evalapprovedpreferences WHERE evaluatorid=? and evalstatus=1";
			status = jdbcTemplate.queryForObject(eLearningStatusQuery,
					Integer.class, userId);
		} catch (Exception e) {
			status = 0;
		}
		return status;
	}

	/**
	 *  This method is used to add Interview Amount
	 * @param interviewId
	 * @param actualAmount
	 * @param evalAmount
	 * @param paidAmount
	 */
	@Transactional
	public void addInterviewAmount(int interviewId, String actualAmount, String evalAmount,String paidAmount) {
		String interviewAmountCountQuery ="select count(*) from interviewamount where interviewid=?";
		int count=jdbcTemplate.queryForObject(interviewAmountCountQuery, Integer.class,interviewId);
		if(count==0){		
		String addInterviewAmountQuery = "INSERT INTO interviewamount (interviewid, actualamount, "
				+ "currencytype, appamount, evalamount, evalpaymentstatus) SELECT "
				+ "interviewid,?, if(i.iplocation='IN',1,2)  ,?,?,0 FROM interview i,"
				+ "interviewerrolelkp irl WHERE irl.interviewerroleid=i.interviewerroleid"
				+ " and i.interviewid=?";
		jdbcTemplate.update(addInterviewAmountQuery, actualAmount,paidAmount,evalAmount,interviewId);
		}else{
			String updateInterviewAmountQuery = "update interviewamount ia,interview i set "
					+ "actualamount=?,currencytype=if(i.iplocation='IN',1,2),appamount=?,"
					+ "evalamount=?, evalpaymentstatus=0 where ia.interviewid=i.interviewid "
					+ "and i.interviewid=?";
			jdbcTemplate.update(updateInterviewAmountQuery,actualAmount,paidAmount,evalAmount,interviewId);
		}
	}
	/**
	 * This method is used to add BulkInterview Amount
	 * @param interviewId
	 */
	@Transactional
	public void addBulkInterviewAmount(String interviewId) {
			
		String addInterviewAmountQuery = "INSERT INTO interviewamount(interviewid, actualamount,"
				+ " currencytype, appamount, evalamount, evalpaymentstatus)SELECT interviewid,"
				+ "irc.amount, if(i.iplocation='IN',1,2) ,'0',irc.evalamount,0 FROM interview i,"
				+ "interviewratecardlkp irc WHERE irc.interviewerroleid=i.interviewerroleid and "
				+ "if(i.iplocation='IN',1,2)=irc.currencytypeid and i.interviewid=?";
		jdbcTemplate.update(addInterviewAmountQuery,interviewId);
		
	}
	/**
	 * This method is used to update Applicant feedback status
	 * @param interviewId
	 */
	@Transactional
	public void updateApplicantfeedbackstatus(int interviewId) {
		String interviewStatusQuey = "UPDATE interview SET applicantfeedbackstatus=1 WHERE interviewid=?";
		jdbcTemplate.update(interviewStatusQuey, interviewId);

	}

	/*
	 * this method is used to get Wallet History of user
	 */
	public List getWalletHistory(int userId) {
		String timezoneQuery="SELECT timezone FROM profile WHERE userid=?";
		 String timezone=jdbcTemplate.queryForObject(timezoneQuery,String.class,userId);
		String walletHistoryQuery = "SELECT amount,reference, DATE_FORMAT(CONVERT_TZ(currenttimestamp,'+00:00',?), '%d %b,%Y %H:%i %p') as datetime FROM wallet WHERE userid=?";
		return jdbcTemplate.queryForList(walletHistoryQuery, timezone,userId);
	}

	/*
	 * this method is used to get Wallet History of user
	 */
	public float getWalletTotal(int userId) {
		String walletHistoryQuery = "SELECT ifnull(sum(round(amount,2)),0) FROM wallet WHERE userid=?";
		return jdbcTemplate.queryForObject(walletHistoryQuery, Float.class,
				userId);
	}

	/**
	 *  this method is used to add Wallet Amount
	 * @param userId
	 * @param amount
	 * @param reference
	 * @param status
	 */
	@Transactional
	public void addWalletAmount(int userId, String amount, String reference,
			int status) {
		String addWalletAmountQuery = "INSERT INTO wallet(userid,amount,reference,status,currenttimestamp)VALUES(?,?,?,?,utc_timestamp());";
		jdbcTemplate.update(addWalletAmountQuery, userId, amount, reference,
				status);
	}

	/**
	 *   this method is used to get Wallet total amount 
	 * @param userId
	 * @param reference
	 * @return
	 */
	@Transactional
	public float getWalletSumByInterview(int userId, String reference) {
		String getWalletSumByInterviewQuery = "SELECT ifnull(sum(amount),0) FROM wallet WHERE reference=? and userid=?";
		return jdbcTemplate.queryForObject(getWalletSumByInterviewQuery,
				Float.class, reference, userId);
	}
	/**
	 *   this method is used to add MockTest Result
	 * @param userId
	 * @param testRollNo
	 * @param testResultList
	 */
	@Transactional
	public void addMockTestResult(int userId,String testRollNo, String testResultList) {
		String addMockTestResultQuery="insert into tmimockresult(testrollno,userid,tmimockresultList,currenttimestamp)values(?,?,?,?)";
		jdbcTemplate.update(addMockTestResultQuery,testRollNo,userId,testResultList,tmiUtil.getCurrentGmtTime());
		
	}
	/**
	 *   this method is used to get MockTest Result List
	 * @param userId
	 * @return
	 */
	public List getMockTestResultList(int userId) {
		String timezoneQuery="SELECT timezone FROM profile WHERE userid=?";
		 String timezone=jdbcTemplate.queryForObject(timezoneQuery,String.class,userId);
		String getMockTestResultQuery="select testrollno,  DATE_FORMAT(CONVERT_TZ(currenttimestamp,'+00:00',?), '%d %b,%Y %H:%i %p') as datetime  from tmimockresult where userid=?";
		return jdbcTemplate.queryForList(getMockTestResultQuery,timezone,userId);		 
	}
	/**
	 *   this method is used to get MockTest Result 
	 * @param testRollNo
	 * @return
	 */
	public String getMockTestResultById(String testRollNo) {
		String getMockTestResultQuery="select tmimockresultList from tmimockresult where "
				+ " testrollno=?";
		return jdbcTemplate.queryForObject(getMockTestResultQuery,String.class,testRollNo);		 
	}
	/**
	 *   this method is used to get MockTest Result by date
	 * @param testRollNo
	 * @return
	 */
	public List getMockTestResultList(String testRollNo) {
		String getMockTestResultQuery="select tmr.tmimockresultList,concat(firstname,"
				+ "' ',lastname)fullname,p.emailid from tmimockresult tmr,profile p where "
				+ "p.userid=tmr.userid and tmr.testrollno=?";
		return jdbcTemplate.queryForList(getMockTestResultQuery,testRollNo);		 
	}
	/**
	 *  this method is used to get MockTest status 
	 * @param userId
	 * @return
	 */
	public int getMocktestStatus(int userId) {
		String getMockTestResultQuery="select count(*) from tmimockresult where userid=?";
		return jdbcTemplate.queryForObject(getMockTestResultQuery,Integer.class,userId);	
	}
	/**
	 *  this method is used to get MockTest result list by date
	 * @param testEndDate 
	 * @param testStartDate 
	 * @return
	 */
	public List<String> getMockTestResultByList(String testStartDate, String testEndDate) {
		String getMockTestResultQuery="select testrollno from tmimockresult where date_format(currenttimestamp,'%Y-%m-%d') between ? and ?";
		return (List<String>)jdbcTemplate.queryForList(getMockTestResultQuery,String.class,testStartDate,testEndDate);		 
	}
	/**
	 * This method is used to update the applicant interview booking location	
	 * @return
	 */
	@Transactional
	public void updateInterviewBookingLocation(String interviewLocation,
			int interviewId) {
		String interviewBookingLocationQuery="update interview set iplocation=? where interviewid=?";
		jdbcTemplate.update(interviewBookingLocationQuery,interviewLocation,interviewId);
		
	}
	
	/**
	 * This Method is used to get  domainid based on interviewid
	 * 
	 * @param interviewid
	 * @return domainid
	 */
	public int getRoleID(int interviewid) {
		String intrerviewQuery = "SELECT interviewerroleid FROM interview WHERE interviewid=?";
		return jdbcTemplate.queryForObject(intrerviewQuery, Integer.class,
				interviewid);
	}
	
}