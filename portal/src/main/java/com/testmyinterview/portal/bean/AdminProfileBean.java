package com.testmyinterview.portal.bean;

public class AdminProfileBean {

	private int adminId;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private int chooseyourRole;
	private String emailaddress;
	private String securityCode;	
	private int referralId;
	private String timezone;
	
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}	
	public int getAdminId() {
		return adminId;
	}

	public void setAdminId(int adminId) {
		this.adminId = adminId;
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

	public int getChooseyourRole() {
		return chooseyourRole;
	}

	public void setChooseyourRole(int chooseyourRole) {
		this.chooseyourRole = chooseyourRole;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getReferralId() {
		return referralId;
	}

	public void setReferralId(int referralId) {
		this.referralId = referralId;
	}

	
	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}


}