package com.testmyinterview.portal.util;

/**
 * This class is used to declare Constants variables,This Constants are used in side the project
 * 
 */
public interface Constants {	
	
	
	String ROLE_ADMIN =     "ROLE_ADMIN";
	String ROLE_INTERNAL =  "ROLE_INTERNAL";
	String ROLE_EXTERNAL =  "ROLE_EXTERNAL";
	String ROLE_CCR =       "ROLE_CCR";	
	String ROLE_EVAL_EVAL = "ROLE_EVAL_EVAL";
	
	int careerStausYes=0;
	int careerStausNo=1;
	
	int domainCategoryId=1;
	int industryCategoryId=2;
	int skillTypeCategoryId=3;
	int skillNameCategoryId=4;
	int interviewCategoryId=5;
	int companyCategoryId=6;
	int degreeCategoryId=7;
	int universityCategoryId=8;
	
	int profilePageId=1;
	int interviewPageId=2;
	
	int interviewOpenId=0;
	int interviewBookedId=1;
	int interviewClosedId=2;
	int interviewCancelledId=3;  
	int interviewPaymentCancelledId=4;
	int interviewPaymentFailureId=5;
	int interviewAdminBooking=6;
	
	int slotStatusOpenId=0;
	int slotStatusWaitingId=1;
	int slotStatusPaymentId=2;
	int slotStatusRejectId=3;
	int slotStatusAcceptId=4;

	int feedbackKnowledgeId=1;
	int feedbackSkillsId=2;
	int feedbackAttitudesId=3;
	int feedbackOverallImpressionId=4;
	int feebbcakApplicantId=5;
	
	int requestPaymentId=0;
	int requestPaymentSendId=1;
	int requestPaymentDoneId=2;
	
	int applicantUserTypeId=1;
	int internalUserTypeId=2;
	int adminUsertype=3;
	
	int walletStatusTrue=1;
	
	String 	AdminReject="Reject Interview";
	String 	AdminClosing="Closing Interview";
	String 	AdminSlotAccept="Slot Accept";
	String 	AdminFeedback="Feedback";
	String 	AdminAddUser="Add User";
	String 	AdminSlotStatus="Slot Schedule";
	String 	AdminSlotCreate="Slot Create";
	String 	AdminCancel="Cancel Interview";
	String 	AdminReassigned="Reassigned slot";
	String 	AdminCreateInterview="Create Interview";
	String AdminEvalMapping="Evaluator Mapped";
	
	String KookooNoAnswer = "5,6";
	String KookooDTMF = "11,12,13";
	String KookooSuccess = "9";
	int countFirst=1;
}