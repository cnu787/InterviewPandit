 /**
 * This function is used to check inputs fields empty or not based on that it validate input fields
 * @returns true or false
 */

var myFormCheck = function() {
	return 0 != 
		    	  $("#graduateyears").val() ? !1 : -1 != $("#graduatemonth").val() ? !1
			: 0 != $("#degreename").val() ? !1
					: 0 != $.trim($("#study").val()).length ? !1 : 0 != $("#universityname").val() ? !1 : !0
};
/**
 * This function is used to bootstrap validation input fields
 * 
 */
$(document)
		.ready(
				function() {
							$("#myedu")
									.bootstrapValidator(
											{
												feedbackIcons : {
													valid : "glyphicon glyphicon-ok",
													invalid : "glyphicon glyphicon-remove",
													validating : "glyphicon glyphicon-refresh"
												},
												fields : {
													graduateyears : {
														enabled : !1,
														validators : {
															callback : {
																message : "Please select listed Graduated year.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"graduateyears")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													graduatemonth : {
														enabled : !1,
														validators : {
															callback : {
																message : "Please select listed Graduated month.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"graduatemonth")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													degreename : {
														enabled : !1,
														validators : {
															notEmpty : {
																message : "Degree name is required."
															}
														}
													},
													study : {
														enabled : !1,
														validators : {
															notEmpty : {
																message : "Field of study is required."
															}
														}
													},
													universityname : {
														enabled : !1,
														validators : {
															notEmpty : {
																message : "university name is required."
															}
														}
													}
												}
											})
									.on(
											"keyup",
											'[name="universityname"],[name="study"],[name="degreename"]',
											function() {
												var a = myFormCheck();
														$("#myedu")
																.bootstrapValidator(
																		"enableFieldValidators",
																		"graduateyears",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"graduatemonth",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"degreename",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"study",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"universityname",
																		!a),
														a
																|| $("#myedu")
																		.bootstrapValidator(
																				"validateField",
																				"graduateyears")
																		.bootstrapValidator(
																				"validateField",
																				"graduatemonth")
																		.bootstrapValidator(
																				"validateField",
																				"degreename")
																		.bootstrapValidator(
																				"validateField",
																				"study")
																		.bootstrapValidator(
																				"validateField",
																				"universityname")
											})
									.on(
											"change",
											'[name="graduateyears"],[name="graduatemonth"],[name="degreename"],[name="universityname"]',
											function() {
												var a = myFormCheck();
														$("#myedu")
																.bootstrapValidator(
																		"enableFieldValidators",
																		"graduateyears",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"graduatemonth",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"degreename",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"study",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"universityname",
																		!a),
														a
																|| $("#myedu")
																		.bootstrapValidator(
																				"validateField",
																				"graduateyears")
																		.bootstrapValidator(
																				"validateField",
																				"graduatemonth")
																		.bootstrapValidator(
																				"validateField",
																				"degreename")
																		.bootstrapValidator(
																				"validateField",
																				"study")
																		.bootstrapValidator(
																				"validateField",
																				"universityname")
											}), $("#myedu").on(
									"success.form.bv", function(a) {
										a.preventDefault(), myajaxform()
									})
				});