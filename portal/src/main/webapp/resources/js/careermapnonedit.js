/**
 * This function is used to check inputs fields empty or not based on that it validate input fields
 * @returns true or false
 */
var myFormCheck = function() {
	return 0 != $.trim($("#companyname").val()).length ? !1 : 0 != $.trim($(
			"#position").val()).length ? !1
			: 0 != $.trim($("#location").val()).length ? !1 : -1 != $("#month")
					.val() ? !1 : -1 != $("#year").val() ? !1 : -1 != $(
					"#expmonth").val() ? !1 : -1 != $("#expryear").val() ? !1
					: !0
};
/**
 * This function is used to bootstrap validation input fields
 * 
 */
$(document)
		.ready(
				function() {					
					var myflag =0;					
							$("#myprofile")
									.bootstrapValidator(
											{
												feedbackIcons : {
													valid : "glyphicon glyphicon-ok",
													invalid : "glyphicon glyphicon-remove",
													validating : "glyphicon glyphicon-refresh"
												},
												fields : {
													 companyname : {
														enabled : !1,
														validators : {
															notEmpty : {
																message : "Company name is required."
															}
														}
													},													
													position : {
														enabled : !1,
														validators : {
															notEmpty : {
																message : "Position is required."
															}
														}
													},
													location : {
														enabled : !1,
														validators : {
															notEmpty : {
																message : "Location is required."
															}
														}
													},
													month : {
														enabled : !1,
														validators : {
															callback : {
																message : "From month is required.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"month")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													year : {
														enabled : !1,
														validators : {
															callback : {
																message : "From year is required.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"year")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													expmonth : {
														enabled : !1,
														validators : {
															callback : {
																message : "To month is required.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"expmonth")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													expryear : {
														enabled : !1,
														validators : {
															callback : {
																message : "To year is required.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"expryear")
																			.val();
																	return -1 != d
																}
															}
														}
													}
												}
											})
									.on(
											"keyup",
											'[name="companyname"],[name="position"],[name="location"]',
											function() {
												var a = myFormCheck();
														$("#myprofile")
																.bootstrapValidator(
																		"enableFieldValidators",
																		"companyname",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"position",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"location",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"month",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"year",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"expmonth",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"expryear",
																		!a),
														a
																|| $(
																		"#myprofile")
																		.bootstrapValidator(
																				"validateField",
																				"companyname")
																		.bootstrapValidator(
																				"validateField",
																				"position")
																		.bootstrapValidator(
																				"validateField",
																				"location")
																		.bootstrapValidator(
																				"validateField",
																				"month")
																		.bootstrapValidator(
																				"validateField",
																				"year")
																		.bootstrapValidator(
																				"validateField",
																				"expmonth")
																		.bootstrapValidator(
																				"validateField",
																				"expryear")
											})
									.on(
											"change",
											'[name="month"],[name="year"],[name="expmonth"],[name="expryear"]',
											function() {
												var a = myFormCheck();
														$("#myprofile")
																.bootstrapValidator(
																		"enableFieldValidators",
																		"companyname",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"position",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"location",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"month",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"year",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"expmonth",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"expryear",
																		!a),
														a
																|| $(
																		"#myprofile")
																		.bootstrapValidator(
																				"validateField",
																				"companyname")
																		.bootstrapValidator(
																				"validateField",
																				"position")
																		.bootstrapValidator(
																				"validateField",
																				"location")
																		.bootstrapValidator(
																				"validateField",
																				"month")
																		.bootstrapValidator(
																				"validateField",
																				"year")
																		.bootstrapValidator(
																				"validateField",
																				"expmonth")
																		.bootstrapValidator(
																				"validateField",
																				"expryear")
											}), $("#myprofile").on(
									"success.form.bv", function(a) {
										a.preventDefault(),
										0 == myflag && myajaxform()
											
									})
				});