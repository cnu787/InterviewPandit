package com.testmyinterview.portal.bean;

import java.io.Serializable;
import java.util.List;

public class CustomTemplateBean implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String senderEmailId;
	private String templateName;
	private String mailSubject;
	private String userName;
	private int userType;
	private int referralId;
	private String[] recepients;
	private String securityCode;
	private String siteUrl;
	private String loginUrl;
	private String password;
	private String phoneNumber;
	private List userDetails;
	private List skillsDetails;
	private List domainList;
	
	
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public int getReferralId() {
		return referralId;
	}
	public void setReferralId(int referralId) {
		this.referralId = referralId;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getSenderEmailId() {
		return senderEmailId;
	}
	public void setSenderEmailId(String senderEmailId) {
		this.senderEmailId = senderEmailId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getMailSubject() {
		return mailSubject;
	}
	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String[] getRecepients() {
		return recepients;
	}
	public void setRecepients(String[] recepients) {
		this.recepients = recepients;
	}
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public List getUserDetails() {
		return userDetails;
	}
	public void setUserDetails(List userDetails) {
		this.userDetails = userDetails;
	}
	public List getSkillsDetails() {
		return skillsDetails;
	}
	public void setSkillsDetails(List skillsDetails) {
		this.skillsDetails = skillsDetails;
	}
	public List getDomainList() {
		return domainList;
	}
	public void setDomainList(List domainList) {
		this.domainList = domainList;
	}

}