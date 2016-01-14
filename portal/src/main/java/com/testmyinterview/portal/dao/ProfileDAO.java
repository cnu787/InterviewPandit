package com.testmyinterview.portal.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.sql.PreparedStatement;
import javax.servlet.http.HttpServletRequest;

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
import com.testmyinterview.portal.util.FileUploader;
import com.testmyinterview.portal.util.TmiUtil;

public class ProfileDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TmiUtil tmiUtil;

	/**
	 * This Method is used to user Registration
	 * 
	 * @param pb
	 * @param securityCode
	 * @return
	 */
	@Transactional
	public int userRegister(ProfileBean pb,String securityCode) {
		String roleName = null;
		int careerstatus = 0;
		if (pb.getChooseyourRole() == 1) {
			roleName = Constants.ROLE_EXTERNAL;
			careerstatus = 1;
		} else {
			careerstatus = 0;
			roleName = Constants.ROLE_INTERNAL;
		}

		String updateQuery = "INSERT INTO profile(createddate,firstname,lastname,screenName,usertypeid,"
				+ "emailid,careerstatus,timezone,countrycode,mobileno";
		if (pb.getReferralId() != 0) {
			updateQuery += ",referralid";
		}
		updateQuery += " )VALUES (?,?,?,?,?,?,?,?,?,?";
		if (pb.getReferralId() != 0) {
			updateQuery += ",'" + pb.getReferralId() + "'";
		}
		updateQuery += ")";
		jdbcTemplate.update(updateQuery, tmiUtil.getCurrentGmtTime(),
				pb.getFirstName(), pb.getLastName(), pb.getScreenName(),
				pb.getChooseyourRole(), pb.getEmailaddress(), careerstatus,
				pb.getTimeZone(), pb.getCountryCode(), pb.getPhoneNumber());
		String usersQuery = "INSERT INTO users(USERNAME,PASSWORD,ENABLED,USER_KEY)VALUES(?,md5(?),0,'')";
		jdbcTemplate.update(usersQuery, pb.getEmailaddress(), pb.getPassword());
		String emailverificationQuery = "INSERT INTO emailverification(emailid,securitycode,currentdatetime)VALUES(?,?,?)";
		jdbcTemplate.update(emailverificationQuery,pb.getEmailaddress(),securityCode,tmiUtil.getCurrentGmtTime());
		String updateAuthoritiesQuery = "INSERT INTO authorities (USERNAME,AUTHORITY) VALUES (?,?)";
		int count = jdbcTemplate.update(updateAuthoritiesQuery,
				pb.getEmailaddress(), roleName);
		return count;
	}

	/**
	 * This Method is used to check email already exits or not  in DataBase
	 * 
	 * @param pb
	 * @return
	 */
	public int getEmailcount(ProfileBean pb) {
		String emailQuery = "SELECT count(*) FROM users WHERE USERNAME=?";
		int count = jdbcTemplate.queryForObject(emailQuery, Integer.class,
				pb.getEmailaddress());
		return count;
	}

	/**
	 * This Method is used to get email count
	 * 
	 * @param Emailid
	 * @return
	 */
	public int getEmail(String Emailid) {
		String emailQuery = "SELECT count(*) FROM profile WHERE emailid=? and usertypeid=1";
		int count = jdbcTemplate.queryForObject(emailQuery, Integer.class,
				Emailid);
		return count;
	}

	/**
	 * This Method is used to update Profile Details in to DataBase
	 *	 
	 * @param request
	 * @param pb
	 * @param otherDomain
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public int updateProfile(HttpServletRequest request, ProfileBean pb,
			int otherDomain) throws Exception {
		try {
			FileUploader profileImageUploader = new FileUploader();
			FileUploader profileVideoUploader = new FileUploader();
			FileUploader profileResumeUploader = new FileUploader();
			try {
				String updateProfileQuery = "UPDATE profile SET firstname=?,lastname=?,"
						+ "timezone=?,mobileno=?,countrycode=?,screenName=?,industryid=?,"
						+ "domainid=?";
				profileImageUploader.fileUploadFromController(request,
						"profileImage");
				if (profileImageUploader.getFileName() != null
						&& !profileImageUploader.getFileName().isEmpty()) {
					updateProfileQuery += ",photoid='"
							+ profileImageUploader.getFileName() + "'";
				}
				/*-------------for video upload------------*/
				profileVideoUploader.fileUploadFromController(request,
						"profileVideo");
				if (profileVideoUploader.getFileName() != null
						&& !profileVideoUploader.getFileName().isEmpty()) {
					updateProfileQuery += ",videoid='"
							+ profileVideoUploader.getFileName() + "'";
				}
				/*-------------for resume upload------------*/
				profileResumeUploader.fileUploadFromController(request,
						"profileResume");
				if (profileResumeUploader.getFileName() != null
						&& !profileResumeUploader.getFileName().isEmpty()) {
					updateProfileQuery += ",resumeid='"
							+ profileResumeUploader.getFileName() + "'";
				}
				updateProfileQuery += ",profilestatus=1 WHERE emailid='"
						+ pb.getEmailaddress() + "'";
				jdbcTemplate.update(updateProfileQuery, pb.getFirstName(),
						pb.getLastName(), pb.getTimeZone(),
						pb.getPhoneNumber(), pb.getCountryCode(),
						pb.getScreenName(), pb.getIndustry(), pb.getDomain());
				List<Map<String, String>> userDet = getUserDetails(pb);

				int existingIndustryid = Integer.parseInt(String
						.valueOf(userDet.get(0).get("industryid")));
				String existingDomainid = (String.valueOf(userDet.get(0).get(
						"domainid")));
				String existingOtherIndustryName = String.valueOf(userDet
						.get(0).get("otherIndustry"));
				String existingOtherDomainName = String.valueOf(userDet.get(0)
						.get("otherDomain"));
				if (pb.getIndustry() == 0
						&& (!existingOtherIndustryName.equals(pb
								.getIndustryOther()))) {
					addOtherCategory(Constants.profilePageId,
							Constants.industryCategoryId, pb.getUserId(),
							pb.getIndustryOther(), null);
				}
				if (pb.getIndustry() != 0) {
					deleteOtherCategory(Constants.profilePageId,
							Constants.industryCategoryId, pb.getUserId(), null);

				}
				if (otherDomain == 0
						&& (!existingOtherDomainName
								.equals(pb.getDomainOther()))) {
					addOtherCategory(Constants.profilePageId,
							Constants.domainCategoryId, pb.getUserId(),
							pb.getDomainOther(), null);
				}
				if (otherDomain != 0) {
					deleteOtherCategory(Constants.profilePageId,
							Constants.domainCategoryId, pb.getUserId(), null);

				}

			} catch (org.springframework.web.multipart.MaxUploadSizeExceededException e) {
				throw e;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
			throw exception;
		}
		return 0;
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
				+ "otherpageid,otherCategoryName,otherCategoryCreatorId,otherskillid) VALUES "
				+ "(?, ?, ?, ?,?)";
		if (skillId == null) {
			deleteExistingOtherCategoryQuery += " and otherskillid is null";
		} else {
			deleteExistingOtherCategoryQuery += " and otherskillid=" + skillId;
		}
		jdbcTemplate.update(deleteExistingOtherCategoryQuery, otherPageId,
				otherCategoryType, otherCategoryCreatorId);

		jdbcTemplate
				.update(addOtherCategoryQuery, otherCategoryType, otherPageId,
						otherCategoryName, otherCategoryCreatorId, skillId);

	}
	/**
	 * this method is used to add OtherCareerCategory 
	 * @param otherPageId
	 * @param otherCategoryType
	 * @param otherCategoryCreatorId
	 * @param otherCategoryName
	 * @param otherskillid
	 * @param companyId
	 * @param previousCompanyId
	 */
	@Transactional
	public void addUpdateOtherCategory(int otherPageId, int otherCategoryType,
			int otherCategoryCreatorId, String otherCategoryName,
			String otherskillid, int companyId, String previousCompanyId) {

		if (previousCompanyId.equals("0") && companyId == 0) {
			String updateExistingOtherCategoryQuery = "update otherCategories set otherCategoryName =? WHERE  otherskillid= ? and otherCategoryType = ? and otherPageId =? and otherCategoryCreatorId=?";
			jdbcTemplate.update(updateExistingOtherCategoryQuery,
					otherCategoryName, otherskillid, otherCategoryType,
					otherPageId, otherCategoryCreatorId);

		} else if (previousCompanyId.equals("0") && companyId != 0) {
			String deleteExistingOtherCategoryQuery = "DELETE FROM otherCategories WHERE otherpageid=? and "
					+ "otherCategoryType=? and otherCategoryCreatorId=? and otherskillid= ?";

			jdbcTemplate.update(deleteExistingOtherCategoryQuery, otherPageId,
					otherCategoryType, otherCategoryCreatorId, otherskillid);

		} else if (!previousCompanyId.equals("0") && companyId == 0) {
			String addOtherCategoryQuery = "INSERT INTO otherCategories(otherCategoryType,"
					+ "otherpageid,otherCategoryName,otherCategoryCreatorId,otherskillid) VALUES "
					+ "(?, ?, ?, ?,?)";
			jdbcTemplate.update(addOtherCategoryQuery, otherCategoryType,
					otherPageId, otherCategoryName, otherCategoryCreatorId,
					otherskillid);

		}

	}
	/**
	 * this method is used to delete OtherCategory
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
	 * this method is used to get user id based on mailAddress
	 * @param pb
	 * @return
	 */
	public int getUserId(ProfileBean pb) {
		String userIdQuery = "SELECT userid FROM profile WHERE emailid=?";
		int userId = jdbcTemplate.queryForObject(userIdQuery, Integer.class,
				pb.getEmailaddress());
		return userId;
	}

	/**
	 * this method is used to get emailAddress based on user id
	 * 
	 * @param userId
	 * @return
	 */
	public String getEmailIdByUserId(int userId) {
		String getEmailIdQuery = "SELECT emailid FROM profile WHERE userid=?";
		return jdbcTemplate.queryForObject(getEmailIdQuery, String.class,
				userId);

	}

	/**
	 * this method is used to get user details based on user id
	 * 
	 * @param pb
	 * @return
	 */
	public List getUserDetails(ProfileBean pb) {
		String userDetailsQuery = "SELECT p.*,if(p.industryid=null,'null',(select ind.industryname from "
				+ "industrylkp ind where ind.industryid=p.industryid)) as industryname,"
				+ "(SELECT If(count(*)=0,'',otherCategoryName) FROM otherCategories WHERE "
				+ "otherCategoryType=2 and otherpageid=1 and otherCategoryCreatorId=p.userid)as "
				+ "otherIndustry,(SELECT If(count(*)=0,'',otherCategoryName) FROM otherCategories "
				+ "WHERE otherCategoryType=1 and otherpageid=1 and otherCategoryCreatorId=p.userid)as "
				+ "otherdomain FROM profile p WHERE p.userid=?";
		List userList = jdbcTemplate.queryForList(userDetailsQuery,
				pb.getUserId());
		return userList;
	}

	/**
	 * this method is used to add user log
	 * 	
	 * @param userId
	 */
	@Transactional
	public void addUserLog(int userId) {
		try {
			String addUserLogQuery = "INSERT INTO logger(userid,currenttimestamp)values(?,?)";
			jdbcTemplate.update(addUserLogQuery, userId,
					tmiUtil.getCurrentGmtTime());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * this method is used to get user last session
	 * @param userId
	 * @return
	 */
	public String getUserLastSession(int userId) {
		try {
			String userIdQuery = "SELECT date_format(currenttimestamp,'%M %d, %Y %H:%i:%s') FROM logger where userid=? order by currenttimestamp desc limit 1,1";
			return jdbcTemplate.queryForObject(userIdQuery, String.class,
					userId);
		} catch (Exception ed) {
			// ed.printStackTrace();
			return "";
		}
	}

	/**
	 * this method is used to get profile status
	 * @param userId
	 * @return
	 */
	public int getProfileStatus(int userId) {
		String userIdQuery = "SELECT profilestatus FROM profile WHERE userid=?";
		return jdbcTemplate.queryForObject(userIdQuery, Integer.class, userId);
	}

	/**
	 * this method is used to update email verification
	 * @param type
	 * @return
	 */
	@Transactional
	public String updateEmailVerification(String type) {
		try {
			String updateEmailVerificationQuery = "UPDATE users SET ENABLED=1 where USERNAME=("
					+ "SELECT emailid FROM emailverification WHERE securitycode=?)";
			int count =jdbcTemplate.update(updateEmailVerificationQuery, type);
			if(count>0){
				String getEmailVerificationQuery = "SELECT emailid FROM emailverification WHERE securitycode=?";
				return jdbcTemplate.queryForObject(getEmailVerificationQuery,String.class, type);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return "";
	}

	/**
	 * This method is to update the user password
	 * 	
	 * @param username
	 * @param password
	 */
	@Transactional
	public void updatePassword(String username, String password) {
		String updateProfileQuery = "UPDATE users SET password=MD5(?) WHERE username=?";
		jdbcTemplate.update(updateProfileQuery, password, username);

	}

	/**
	 * This method is to reset the user password
	 * 
	 * @param username
	 * @param password
	 */
	@Transactional
	public void resetPassword(String username, String password) {
		String updateProfileQuery = "UPDATE users SET password=MD5(MD5(concat(?,?))) WHERE username=?";
		jdbcTemplate.update(updateProfileQuery, username, password, username);

	}

	/**
	 * This method is to get the full name in user registration based on userid
	 * 	
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public String getFullNameByUserId(int userId) throws Exception {
		String fullName = null;
		try {
			String registrationIdQuery = "SELECT CONCAT(firstname,' ',lastname) as fullname FROM profile WHERE userid=?";
			fullName = jdbcTemplate.queryForObject(registrationIdQuery,
					String.class, userId);
		} catch (EmptyResultDataAccessException ed) {
			fullName = null;
		}
		return fullName;
	}

	/**
	 * This method is to get the full name based on emailId
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public String getFullNameByEmailId(String emailId) throws Exception {
		String fullName = null;
		try {
			String registrationIdQuery = "SELECT CONCAT(firstname,' ',lastname) as fullname FROM profile WHERE emailid=?";
			fullName = jdbcTemplate.queryForObject(registrationIdQuery,
					String.class, emailId);
		} catch (EmptyResultDataAccessException ed) {
			fullName = null;
		}
		return fullName;
	}

	/**
	 * This method is to get the firstname based on  emailId
	 * 
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public String getFirstNameByEmailId(String emailId) throws Exception {
		String firstName = null;
		try {
			String registrationIdQuery = "SELECT firstname FROM profile WHERE emailid=?";
			firstName = jdbcTemplate.queryForObject(registrationIdQuery,
					String.class, emailId);
		} catch (EmptyResultDataAccessException ed) {
			firstName = "";
		}
		return firstName;
	}

	/**
	 * This method is used to get the userid based on emailid
	 * 
	 * @param emailId
	 * @return
	 * @throws Exception
	 */
	public Integer getUserIdByEmailId(String emailId) throws Exception {
		int userId = 0;
		try {
			String getUserIdByEmailIdQuery = "SELECT userid FROM profile WHERE usertypeid=2 and emailid=?";
			userId = jdbcTemplate.queryForObject(getUserIdByEmailIdQuery,
					Integer.class, emailId);
		} catch (EmptyResultDataAccessException ed) {
			userId = 0;
		}
		return userId;
	}

	/**
	 * This method is used to add Education Details based on emailid
	 * 
	 * @param profileBean
	 */
	@Transactional
	public void addEducation(final ProfileBean profileBean) {
		final String addEducationQuery = "INSERT INTO education(userid,institutename,graduatedyear,"
				+ "graduatedmonth,degreeid,fieldofstudy,universityid,currenttimestamp) VALUES (?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(addEducationQuery,
								new String[] { "educationid" });

				ps.setInt(1, profileBean.getUserId());
				ps.setString(2, profileBean.getSchool());
				ps.setInt(3, profileBean.getGraduatedYear());
				ps.setInt(4, profileBean.getGraduatedMonth());
				ps.setInt(5, profileBean.getDegreeId());
				ps.setString(6, profileBean.getFieldofStudy());
				ps.setInt(7, profileBean.getUniversityid());
				ps.setString(8, tmiUtil.getCurrentGmtTime());
				return ps;
			}
		}, keyHolder);

		if (profileBean.getDegreeId() == 0) {
			addOtherCategory(Constants.profilePageId,
					Constants.degreeCategoryId, profileBean.getUserId(),
					profileBean.getDegreeName(), keyHolder.getKey().toString());
		}

		if (profileBean.getUniversityid() == 0) {
			addOtherCategory(Constants.profilePageId,
					Constants.universityCategoryId, profileBean.getUserId(),
					profileBean.getUniversityName(), keyHolder.getKey()
							.toString());
		}

	}

	/**
	 * This method is to update Education Details based on email id
	 * 
	 * @param profileBean
	 * @param previousdegreeid
	 * @param previousuniversityid
	 */
	@Transactional
	public void updateEducation(ProfileBean profileBean,
			String previousdegreeid, String previousuniversityid) {
		String updateEducationQuery = "UPDATE education SET institutename=?,graduatedyear=?,graduatedmonth=?,degreeid=?,fieldofstudy=?"
				+ ",universityid=? WHERE educationid=?";
		jdbcTemplate.update(updateEducationQuery, profileBean.getSchool(),
				profileBean.getGraduatedYear(),
				profileBean.getGraduatedMonth(), profileBean.getDegreeId(),
				profileBean.getFieldofStudy(), profileBean.getUniversityid(),
				profileBean.getEducationid());

		if (profileBean.getDegreeId() == 0 || previousdegreeid.equals("0")) {
			addUpdateOtherCategory(Constants.profilePageId,
					Constants.degreeCategoryId, profileBean.getUserId(),
					profileBean.getDegreeName(),
					Integer.toString(profileBean.getEducationid()),
					profileBean.getDegreeId(), previousdegreeid);
		}
		if (profileBean.getUniversityid() == 0
				|| previousuniversityid.equals("0")) {
			addUpdateOtherCategory(Constants.profilePageId,
					Constants.universityCategoryId, profileBean.getUserId(),
					profileBean.getUniversityName(),
					Integer.toString(profileBean.getEducationid()),
					profileBean.getUniversityid(), previousuniversityid);
		}

	}

	/**
	 * This method is used to get Education Details based on userId
	 * 
	 * @param profileBean
	 * @return
	 */
	public List getEducationDetails(ProfileBean profileBean) {

		List educationList = null;
		try {
			String educationQuery = "SELECT ed.educationid,IF(ed.universityid!=0, (SELECT u.universityname"
					+ " FROM universitylkp u WHERE u.universityid=ed.universityid),"
					+ " (select otherCategoryName  from otherCategories where otherskillid = ed.educationid"
					+ " and otherpageid=1 and otherCategoryType=8)) as universityname,ed.institutename,gd.graduateyear,"
					+ " IF(ed.degreeid!=0, (SELECT d.degreename FROM degreelkp d WHERE "
					+ "d.degreeid=ed.degreeid), (select otherCategoryName from otherCategories "
					+ "where otherskillid = ed.educationid and otherpageid=1 and "
					+ "otherCategoryType=7)) as degreename,ed.fieldofstudy FROM education as ed,"
					+ "degreelkp as dg ,graduatelkp as gd WHERE gd.graduateId=ed.graduatedyear"
					+ " and dg.degreeid=ed.degreeid and userid=?";
			educationList = jdbcTemplate.queryForList(educationQuery,
					profileBean.getUserId());
		} catch (EmptyResultDataAccessException ed) {
			educationList = null;
		}
		return educationList;
	}

	/**
	 * This method is used to get Education Details based on educationid
	 * 
	 * @param eduId
	 * @return
	 */
	public List getEducationDetailsonId(String eduId) {
		List educationList = null;
		try {
			String educationQuery = "SELECT *,IF(e.degreeid!=0, (SELECT d.degreename"
					+ " FROM degreelkp d WHERE d.degreeid=e.degreeid), (select otherCategoryName"
					+ " from otherCategories where otherskillid = e.educationid and otherpageid=1"
					+ " and otherCategoryType=7)) as degreename,IF(e.universityid!=0, "
					+ "(SELECT u.universityname FROM universitylkp u WHERE u.universityid=e.universityid),"
					+ " (select otherCategoryName  from otherCategories where otherskillid = e.educationid and "
					+ "otherpageid=1 and otherCategoryType=8)) as universityname  FROM education e WHERE "
					+ "e.educationid=?";
			educationList = jdbcTemplate.queryForList(educationQuery, eduId);

		} catch (EmptyResultDataAccessException ed) {
			educationList = null;
		}
		return educationList;
	}

	/**
	 * This method is used to delete education details based on educationid
	 * 
	 * @param studyId
	 * @param userId
	 */
	@Transactional
	public void deleteEducation(String studyId, int userId) {
		String educationQuery = "SELECT degreeid FROM education WHERE educationid =?";
		int degreeId = jdbcTemplate.queryForObject(educationQuery,
				Integer.class, studyId);
		String universityidQuery = "SELECT universityid FROM education WHERE educationid =?";
		int universityid = jdbcTemplate.queryForObject(universityidQuery,
				Integer.class, studyId);
		String educationDeleteQuery = "DELETE FROM education WHERE educationid=?";
		jdbcTemplate.update(educationDeleteQuery, studyId);
		if (degreeId == 0) {
			deleteOtherCategory(Constants.profilePageId,
					Constants.degreeCategoryId, userId, studyId);
		}
		if (universityid == 0) {
			deleteOtherCategory(Constants.profilePageId,
					Constants.universityCategoryId, userId, studyId);
		}
	}

	/**
	 * This method is used to add Skill Details based on user id
	 * 
	 * @param pb
	 * @return
	 */
	@Transactional
	public void addSkills(final ProfileBean pb) {
		final String addEducationQuery = "INSERT INTO skills(userid,skilltypeid,skillnameid,"
				+ "skillrating,year,month,currenttimestamp) VALUES(?,?,?,?,?,?,?) ";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(addEducationQuery,
								new String[] { "skillid" });
				ps.setInt(1, pb.getUserId());
				ps.setInt(2, pb.getSkillTypeId());
				ps.setInt(3, pb.getSkillName());
				ps.setInt(4, pb.getSkillRating());
				ps.setInt(5, pb.getYearsOfExperience());
				ps.setInt(6, pb.getMonthsOfExperience());
				ps.setString(7, tmiUtil.getCurrentGmtTime());
				return ps;
			}
		}, keyHolder);

		if (pb.getSkillTypeId() == 0) {
			addOtherCategory(Constants.profilePageId,
					Constants.skillTypeCategoryId, pb.getUserId(),
					pb.getSkillTypeOther(), keyHolder.getKey().toString());
		}
		if (pb.getSkillName() == 0) {
			addOtherCategory(Constants.profilePageId,
					Constants.skillNameCategoryId, pb.getUserId(),
					pb.getSkillNameOther(), keyHolder.getKey().toString());
		}
	}

	/**
	 * This method is used to update Skills Details based on  emailid
	 * 
	 * @param pb
	 * @return
	 */
	@Transactional
	public void updateSkills(ProfileBean pb) {
		String updateEducationQuery = "UPDATE skills SET skilltypeid=?,skillnameid=?,skillrating=?,year=?,month=? WHERE skillid=?";
		jdbcTemplate.update(updateEducationQuery, pb.getSkillTypeId(),
				pb.getSkillName(), pb.getSkillRating(),
				pb.getYearsOfExperience(), pb.getMonthsOfExperience(),
				pb.getSkillid());
		if (pb.getSkillTypeId() == 0) {
			addOtherCategory(Constants.profilePageId,
					Constants.skillTypeCategoryId, pb.getUserId(),
					pb.getSkillTypeOther(), Integer.toString(pb.getSkillid()));
		}
		if (pb.getSkillName() == 0) {
			addOtherCategory(Constants.profilePageId,
					Constants.skillNameCategoryId, pb.getUserId(),
					pb.getSkillNameOther(), Integer.toString(pb.getSkillid()));
		}
		if (pb.getSkillTypeId() != 0) {
			deleteOtherCategory(Constants.profilePageId,
					Constants.skillTypeCategoryId, pb.getUserId(),
					Integer.toString(pb.getSkillid()));
		}
		if (pb.getSkillName() != 0) {
			deleteOtherCategory(Constants.profilePageId,
					Constants.skillNameCategoryId, pb.getUserId(),
					Integer.toString(pb.getSkillid()));
		}
	}

	/**
	 * This method is used to get Skill Details based on userId
	 * 
	 * @param profileBean
	 * @return
	 */
	public List getSkillDetails(ProfileBean profileBean) {
		List skillsList = null;
		try {
			String skillsQuery = "call  getSkillDetails(?);";
			skillsList = jdbcTemplate.queryForList(skillsQuery,
					profileBean.getUserId());
		} catch (EmptyResultDataAccessException ed) {
			skillsList = null;
		}
		return skillsList;
	}

	/**
	 * This method is used to get Skill Details based on skillId
	 * 
	 * @param skillId
	 * @return
	 */

	public List getSkillDetailsonId(String skillId) {
		List educationList = null;
		try {
			String educationQuery = "SELECT *,(SELECT If(count(*)=0,'',otherCategoryName) FROM"
					+ " otherCategories WHERE otherCategoryType=3 and otherpageid=1 and otherskillid=skillid)as"
					+ " otherSkillType,(SELECT If(count(*)=0,'',otherCategoryName) FROM otherCategories"
					+ " WHERE otherCategoryType=4 and otherpageid=1 and otherskillid=skillid)as otherSkillName FROM"
					+ " skills WHERE skillid=?";
			educationList = jdbcTemplate.queryForList(educationQuery, skillId);

		} catch (EmptyResultDataAccessException ed) {
			educationList = null;
		}
		return educationList;
	}

	/**
	 * This method is used to delete Skill Details based on  SkillId 
	 * 
	 * @param skillId
	 * @return
	 */
	@Transactional
	public void deleteSkill(int skillId) {
		String skillDeleteQuery = "UPDATE skills SET status=0 WHERE skillid=?";
		jdbcTemplate.update(skillDeleteQuery, skillId);
	}

	/**
	 * This method is used to get career States based on mailAddress
	 * 
	 * @param pb
	 * @return
	 */
	public int getCareerId(ProfileBean pb) {
		String careerIdQuery = "SELECT careerstatus FROM profile WHERE emailid=?";
		int careerId = jdbcTemplate.queryForObject(careerIdQuery,
				Integer.class, pb.getEmailaddress());
		return careerId;
	}

	/**
	 * This method is used to add career Details
	 * 
	 * @param pb
	 * @return
	 */
	@Transactional
	public void addCareer(final ProfileBean pb) {
		final String addCareerQuery = "INSERT INTO career(userid,companyname,designation,location,"
				+ "frommonthid,fromyearid,tomonthid,toyearid,currenttimestamp) VALUES (?,?,?,?,?,?,?,?,?)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public java.sql.PreparedStatement createPreparedStatement(
					java.sql.Connection arg0) throws SQLException {
				PreparedStatement ps = (PreparedStatement) arg0
						.prepareStatement(addCareerQuery,
								new String[] { "careerid" });

				ps.setInt(1, pb.getUserId());
				ps.setInt(2, pb.getCompanyId());
				ps.setString(3, pb.getPositionDesignation());
				ps.setString(4, pb.getLocation());
				ps.setInt(5, pb.getStartMonth());
				ps.setInt(6, pb.getStartYear());
				ps.setInt(7, pb.getExpmonth());
				ps.setInt(8, pb.getExpryear());
				ps.setString(9, tmiUtil.getCurrentGmtTime());
				return ps;
			}
		}, keyHolder);
		if (pb.getCompanyId() == 0) {
			addOtherCategory(Constants.profilePageId,
					Constants.companyCategoryId, pb.getUserId(),
					pb.getCompanyName(), keyHolder.getKey().toString());
		}
	}

	/**
	 * This method is used to add career Details
	 * 
	 * @param profileBean
	 */
	@Transactional
	public void updateCareer(ProfileBean profileBean) {
		String careerQuery = "UPDATE profile SET careerstatus=? WHERE userid=?";
		jdbcTemplate.update(careerQuery, profileBean.getCareer(),
				profileBean.getUserId());

	}

	/**
	 * This method is used to get Career Details
	 * @param profileBean
	 * @return
	 */
	public List getCareerDetails(ProfileBean profileBean) {
		List carrerList = null;
		try {
			String careerQuery = "SELECT cr.careerid,IF(cr.companyname!=0, (select cn.companyname from "
					+ "companyNamelkp as cn where cn.companyId=cr.companyname), (select otherCategoryName "
					+ "from otherCategories where otherskillid = cr.careerid and otherpageid=1 and "
					+ "otherCategoryType=6) ) as companyname,cr.designation,cr.location,ms.months,"
					+ "gp.graduateyear,(select months from monthslkp where monthid=tomonthid) as "
					+ "tomonth,(select graduateyear from graduatelkp where graduateId=toyearid) as "
					+ "toyear FROM career as cr ,monthslkp as ms,graduatelkp as gp where    "
					+ "ms.monthid=cr.frommonthid and gp.graduateId=cr.fromyearid and userid=?";
			carrerList = jdbcTemplate.queryForList(careerQuery,
					profileBean.getUserId());
		} catch (Exception e) {
			return carrerList;
		}
		return carrerList;
	}

	/**
	 * This method is used to get Career Details based on careerid
	 * @param careerId
	 * @return
	 */
	public List getCareerDetailsonId(String careerId) {
		List carrerList = null;
		try {
			String careerQuery = "Select *,IF(cr.companyname<>0, (SELECT cl.companyname "
					+ "FROM companyNamelkp cl WHERE cl.companyId=cr.companyname ), "
					+ "(select otherCategoryName from otherCategories where otherskillid =? "
					+ "and otherpageid=1 and otherCategoryType=6) ) as companynametxt from "
					+ "career cr where cr.careerid=?";
			carrerList = jdbcTemplate.queryForList(careerQuery, careerId,
					careerId);
		} catch (Exception e) {
			return carrerList;
		}
		return carrerList;
	}

	/**
	 * This method is used to update Career Details
	 * @param profileBean
	 * @param previousCompanyId
	 */
	@Transactional
	public void updateCareers(ProfileBean profileBean, String previousCompanyId) {
		String updateCareerQuery = "UPDATE career SET companyname=?,designation=?,"
				+ "location=?,frommonthid=?,fromyearid=?,tomonthid=?,toyearid=? WHERE careerid=?";
		jdbcTemplate.update(updateCareerQuery, profileBean.getCompanyId(),
				profileBean.getPositionDesignation(),
				profileBean.getLocation(), profileBean.getStartMonth(),
				profileBean.getStartYear(), profileBean.getExpmonth(),
				profileBean.getExpryear(), profileBean.getCareerId());
		if (profileBean.getCompanyId() == 0 || previousCompanyId.equals("0")) {
			addUpdateOtherCategory(Constants.profilePageId,
					Constants.companyCategoryId, profileBean.getUserId(),
					profileBean.getCompanyName(),
					Integer.toString(profileBean.getCareerId()),
					profileBean.getCompanyId(), previousCompanyId);
		}

	}

	/**
	 * This method is used to  delete career  Details 
	 * 
	 * @param careerId
	 * @param userId
	 */
	@Transactional
	public void deletecareer(int careerId, int userId) {
		String careerQuery = "SELECT companyname FROM career WHERE careerid =?";
		int companyId = jdbcTemplate.queryForObject(careerQuery, Integer.class,
				careerId);
		String skillDeleteQuery = "DELETE FROM career WHERE careerid=?";
		jdbcTemplate.update(skillDeleteQuery, careerId);
		if (companyId == 0) {
			deleteOtherCategory(Constants.profilePageId,
					Constants.companyCategoryId, userId,
					Integer.toString(careerId));
		}

	}

	/**
	 * This method is used to add preferences
	 * 
	 * @param userId
	 * @param interviewtype
	 * @param interviewmode
	 * @param interviewlocality
	 */
	@Transactional
	public void addPreferences(int userId, String interviewtype,
			String interviewmode, String interviewlocality) {
		String deleteprefencesQuery = "DELETE FROM evaluatorpreferences WHERE userid=?";
		jdbcTemplate.update(deleteprefencesQuery, userId);
		String prefencesQuery = "INSERT INTO evaluatorpreferences(userid,interviewtype,interviewmode,"
				+ "interviewlocality) VALUES (?,?,?,?)";
		jdbcTemplate.update(prefencesQuery, userId, interviewtype,
				interviewmode, interviewlocality);
	}

	/**
	 * This method is used to get user preferences
	 * @param userId
	 * @return
	 */
	public Object getUserPreference(int userId) {
		String getUserPreferenceQuery = "Select * from evaluatorpreferences where userid=?";
		return jdbcTemplate.queryForList(getUserPreferenceQuery, userId);
	}

	/**
	 * This Method is used to get user industryid
	 * 
	 * @param userId
	 * @return
	 */
	public int getUserIndustryId(int userId) {
		try {
			String userIndustryIdQuery = "SELECT industryid FROM profile WHERE userid=?";
			return jdbcTemplate.queryForObject(userIndustryIdQuery,
					Integer.class, userId);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * This Method is used  to get interview industryid 
	 * 
	 * @param interviewid
	 * @return
	 */
	public int getInterviewIndustryId(int interviewid) {
		String userIndustryIdQuery = "SELECT industryid FROM interview WHERE interviewid=?";
		return jdbcTemplate.queryForObject(userIndustryIdQuery, Integer.class,
				interviewid);
	}

	/**
	 * This Method is used to get domain Names
	 * @param profileBean
	 * @return
	 */
	public List getDomainNames(ProfileBean profileBean) {
		String domainlistQuery = "SELECT domainid FROM profile WHERE userid=?";
		String domainids = jdbcTemplate.queryForObject(domainlistQuery,
				String.class, profileBean.getUserId());

		String domainNameQuery = "SELECT case domainname when 'Others' then (SELECT If(count(*)=0,domainname,"
				+ "otherCategoryName)  FROM otherCategories WHERE otherCategoryType=1 and "
				+ "otherpageid=1 and otherCategoryCreatorId="
				+ profileBean.getUserId()
				+ ") else   domainname end as domainname"
				+ " FROM domainlkp WHERE domainid in(" + domainids + ")";
		List domainList = jdbcTemplate.queryForList(domainNameQuery);
		return domainList;
	}

	/**
	 * This method is used to check industry is already exists or not if not
	 * exists skills Status change to 0;
	 * 
	 * @param pb
	 */
	@Transactional
	public void checkCount(ProfileBean pb) {
		String checkIndustry = "SELECT count(*) FROM profile WHERE userid=? and industryid=?";
		int count = jdbcTemplate.queryForObject(checkIndustry, Integer.class,
				pb.getUserId(), pb.getIndustry());
		if (count == 0) {
			String updateskills = "UPDATE skills SET status=0 WHERE userid=?";
			jdbcTemplate.update(updateskills, pb.getUserId());
		}
	}
	/**
	 * this method is used to add OtherCategory
	 * @param otherType
	 * @param otherName
	 * @param userId
	 */
	public void addOtherCategoryTypes(String otherType, String otherName,
			int userId) {
		addOtherCategoryByTypes(Constants.profilePageId,
				Constants.skillTypeCategoryId, userId, otherType, null);
		addOtherCategoryByTypes(Constants.profilePageId,
				Constants.skillNameCategoryId, userId, otherName, null);

	}

	/**
	 * this method is used to add OtherCategoryName
	 * @param otherName
	 * @param userId
	 */
	public void addOtherCategoryName(String otherName, int userId) {
		addOtherCategoryByTypes(Constants.profilePageId,
				Constants.skillNameCategoryId, userId, otherName, null);

	}

	/**
	 * this method is used to add OtherCategory Types
	 * @param otherPageId
	 * @param otherCategoryType
	 * @param otherCategoryCreatorId
	 * @param otherCategoryName
	 * @param skillId
	 */
	@Transactional
	public void addOtherCategoryByTypes(int otherPageId, int otherCategoryType,
			int otherCategoryCreatorId, String otherCategoryName, String skillId) {
		String addOtherCategoryQuery = "INSERT INTO otherCategories(otherCategoryType,"
				+ "otherpageid,otherCategoryName,otherCategoryCreatorId,otherskillid,otherCategoryCreatedDate) VALUES "
				+ "(?, ?, ?, ?,?,?)";
		jdbcTemplate.update(addOtherCategoryQuery, otherCategoryType,
				otherPageId, otherCategoryName, otherCategoryCreatorId,
				skillId, tmiUtil.getCurrentGmtTime());

	}

	/**
	 * this method is used to get email count
	 * @param pb
	 * @return
	 */
	public int getEmailcount(AdminProfileBean pb) {
		String emailQuery = "SELECT count(*) FROM users WHERE USERNAME=?";
		int count = jdbcTemplate.queryForObject(emailQuery, Integer.class,
				pb.getEmailaddress());
		return count;
	}

	/**
	 * this method is used to sub-admin user registration 
	 * @param pb
	 * @return
	 */
	public void adminUserRegister(AdminProfileBean pb) {
		String subadminprofileQuery = "INSERT INTO subadminprofile(firstname,lastname,roleid,emailid,"
				+ "mobileno,currenttimestamp,timezone )VALUES (?,?,?,?,?,?,?)";
		jdbcTemplate.update(subadminprofileQuery, pb.getFirstName(),
				pb.getLastName(), pb.getChooseyourRole(), pb.getEmailaddress(),
				pb.getPhoneNumber(), tmiUtil.getCurrentGmtTime(),
				pb.getTimezone());
		String usersQuery = "INSERT INTO users(USERNAME,PASSWORD,ENABLED)VALUES (?,md5(md5(?)),1)";
		jdbcTemplate.update(usersQuery,pb.getEmailaddress(),pb.getEmailaddress()+pb.getSecurityCode());
		String updateAuthoritiesQuery = "INSERT INTO authorities (USERNAME,AUTHORITY) VALUES (?, (SELECT userrolename FROM adminuserrolelkp WHERE roleid = ?))";
		jdbcTemplate.update(updateAuthoritiesQuery,
				pb.getEmailaddress(), pb.getChooseyourRole());		
	}

	/**
	 * this method is used to get answer call count
	 * @param callstatus
	 * @return
	 */
	public String getanswercount(String callstatus) {
		String countQuery = "SELECT count(*) FROM interviewhistory WHERE DATE(interview_start_time)=date(UTC_TIMESTAMP()) and find_in_set(call_status,?)";
		return jdbcTemplate
				.queryForObject(countQuery, String.class, callstatus);
	}

	/**
	 * this method is used to check log details
	 * @param username
	 * @param password
	 * @return
	 */
	public String checkLoginDetails(String username, String password) {
		int count = 0;
		String userRole = null;
		String query = "SELECT COUNT(*) FROM users WHERE USERNAME='" + username
				+ "' AND PASSWORD=(MD5('" + password + "'))";
		count = jdbcTemplate.queryForObject(query, Integer.class);
		if (count == 1) {
			userRole = getUserAuthority(username);
		} else {
			userRole = "Invalid User";
		}
		return userRole;
	}

	/**
	 * this method is used to get user authorities
	 * @param emailId
	 * @return
	 */
	public String getUserAuthority(String emailId) {
		String queryForRole = "SELECT AUTHORITY FROM authorities WHERE USERNAME=?";
		return jdbcTemplate.queryForObject(queryForRole, String.class, emailId);
	}

	/**
	 * this method is used to get password count
	 * @param currentPassword
	 * @param userName
	 * @return
	 */
	public int getPasswordCount(String currentPassword, String userName) {
		String getPasswordCountQuery = "SELECT count(*) FROM users WHERE USERNAME=? and PASSWORD=md5(?)";
		return jdbcTemplate.queryForObject(getPasswordCountQuery,
				Integer.class, userName, currentPassword);
	}

	/**
	 * This method is to update the user profile details
	 * 
	 * @param request :resume
	 * @param ib
	 */
	@Transactional
	public void updateProfileResume(HttpServletRequest request, InterViewBean ib) {
		FileUploader interviewResumeUploader = new FileUploader();
		interviewResumeUploader.fileUploadFromController(request, "resume");
		String updateResumeQuery = "UPDATE profile SET resumeid =? WHERE userid=?";
		jdbcTemplate.update(updateResumeQuery,
				interviewResumeUploader.getFileName(), ib.getUserid());
	}

	/**
	 * this method is used to get mobile no based on emailid
	 * @param emailId
	 * @return
	 */
	public String getMobileNoByEmailId(String emailId) {
		try {
			String getMobileNoByEmailIdQuery = "SELECT mobileno FROM profile WHERE emailid=?";
			return jdbcTemplate.queryForObject(getMobileNoByEmailIdQuery,
					String.class, emailId);
		} catch (Exception e) {
			return "";
		}
	}

}