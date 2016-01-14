/**
 *  This function is used to Validating empty fields
 */		
jQuery(document).ready(function() {
			$("#profilelink").parent().addClass("active")
		}),
		$(document)
				.ready(
						function() {
									$("#myprofile")
											.find('[name="industry"]')
											.change(
													function() {
														$("#myprofile")
																.bootstrapValidator(
																		"revalidateField",
																		"industry")
													})
											.end()
											.find('[name="domain"]')
											.change(
													function() {
														$("#myprofile")
																.bootstrapValidator(
																		"revalidateField",
																		"domain")
													})
											.end()
											.bootstrapValidator(
													{
														feedbackIcons : {
															valid : "glyphicon glyphicon-ok",
															invalid : "glyphicon glyphicon-remove",
															validating : "glyphicon glyphicon-refresh"
														},
														fields : {
															firstName : {
																validators : {
																	notEmpty : {
																		message : "First name is required."
																	}
																}
															},
															lastName : {
																validators : {
																	notEmpty : {
																		message : "Last name is required."
																	}
																}
															},
														phoneNumber : {
															 validators: {
																 notEmpty : {
																		message : "Mobile number is required."
																	},
												                    integer: {
												                    	message : "Enter only numbers."
												                    }
												                }
															},
															screenname : {
																validators : {
																	notEmpty : {
																		message : "Screen name is required."
																	}
																}
															},
															industry : {
																validators : {
																	callback : {
																		message : "Please select listed industry.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"industry")
																					.val();
																			return -1 != d
																		}
																	}
																}
															},
															domain : {
																validators : {
																	callback : {
																		message : "Please select listed domain.",
																		callback : function(
																				a,
																				b) {
																			var d = b
																					.getFieldElements(
																							"domain")
																					.val();
																			return -1 != d
																					&& null != d
																		}
																	}
																}
															},
															profileVideo : {
																validators : {
																	file : {
																		extension : "3gpp,3gp,mp4,mpeg",
																		type : "video/3gpp,video/mp4,video/mpeg",
																		maxSize : 2097152,
																		message : "The selected file is not valid, it should be (3gpp,3gp,mp4,mpeg) and 2 MB at maximum."
																	}
																}
															},
															profileImage : {
																validators : {
																	file : {
																		extension : "jpeg,jpg,png",
																		type : "image/jpeg,image/png",
																		maxSize : 153600,
																		message : "The selected file is not valid, it should be (jpeg,jpg,png) and 150 KB at maximum."
																	}
																}
															},
															profileResume : {
																validators : {
																	file : {
																		extension : "pdf,doc,docx",
																		type : "application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document",
																		maxSize : 2097152,
																		message : "The selected file is not valid, it should be (pdf,doc,docx) and 2 MB at maximum."
																	},
															notEmpty : {
																message : "The resume is required."
															}
																}
															},
															otherIndustry : {
																validators : {
																	notEmpty : {
																		message : 'The field-others in industry is required'
																	}
																}
															},
															otherDomain : {
																validators : {
																	notEmpty : {
																		message : 'The field-others in domain is required'
																	}
																}
															},
														}
													}), $("#myprofile").on(
											"success.form.bv",
											function(a) {
												a.preventDefault(),
														myajaxform()
											});									
						}), $(":file").change(function() {
			{
				var a = this.files[0];
				a.name, a.size, a.type
			}
		});
/**
 *  This function is used to get applicant profile
 */
var myajaxform = function() {
	$("#processImg").show();
	var a = new FormData($("form")[0]);
	$.ajax({
		url : "applicantProfile",
		type : "POST",
		xhr : function() {
			var a = $.ajaxSettings.xhr();
			return a.upload
					&& a.addEventListener("progress", progressHandlingFunction,
							!1), a
		},
		success : completeHandler,
		error : errorHandler,
		data : a,
		cache : !1,
		contentType : !1,
		processData : !1
	})
}, errorHandler = function() {
}, progressHandlingFunction = function() {
};

/**
 *  This function is used to Validating  drop down values selected or not 
 */
var myDropdownCheck = function() {
	$("#myprofile").bootstrapValidator("enableFieldValidators", "domain", !1),
			$("#myprofile").bootstrapValidator("enableFieldValidators",
					"domain", !0), $("#myprofile").bootstrapValidator(
					"validateField", "domain")
};