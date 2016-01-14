/**
 *  This function is used to validating form
 */
var myFormCheck = function() {
	return -1 != $("#skilltype").val() ? !1 : -1 != $("#skillname").val() ? !1
			: -1 != $("#skillrating").val() ? !1 : -1 != $("#exprienceyear")
					.val() ? !1 : -1 != $("#expriencemonth").val() ? !1 : !0
};
/**
 * validating empty fields
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
													skilltype : {
														enabled : !1,
														validators : {
															callback : {
																message : "Please select listed skill type.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"skilltype")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													skillname : {
														enabled : !1,
														validators : {
															callback : {
																message : "Please select listed skill name.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"skillname")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													skillrating : {
														enabled : !1,
														validators : {
															callback : {
																message : "Please select listed skill rating.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"skillrating")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													exprienceyear : {
														enabled : !1,
														validators : {
															callback : {
																message : "Please select listed experience year.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"exprienceyear")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													expriencemonth : {
														enabled : !1,
														validators : {
															callback : {
																message : "Please select listed experience month.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"expriencemonth")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													otherSkillType : {
														validators : {
															notEmpty : {
																message : 'Field-others in skilltype is required'
															}
														}
													},
													otherSkillName : {
														validators : {
															notEmpty : {
																message : 'Field-others in skillname is required'
															}
														}
													}
												}
											})
									.on(
											"change",
											'[name="skilltype"],[name="skillname"],[name="expriencemonth"],[name="exprienceyear"],[name="skillrating"]',
											function() {
												var a = myFormCheck();
														$("#myedu")
																.bootstrapValidator(
																		"enableFieldValidators",
																		"skilltype",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"skillname",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"skillrating",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"expriencemonth",
																		!a)
																.bootstrapValidator(
																		"enableFieldValidators",
																		"exprienceyear",
																		!a),
														a
																|| $("#myedu")
																		.bootstrapValidator(
																				"validateField",
																				"skilltype")
																		.bootstrapValidator(
																				"validateField",
																				"skillname")
																		.bootstrapValidator(
																				"validateField",
																				"skillrating")
																		.bootstrapValidator(
																				"validateField",
																				"expriencemonth")
																		.bootstrapValidator(
																				"validateField",
																				"exprienceyear")
											}), $("#myedu").on(
									"success.form.bv", function(a) {
										a.preventDefault(), myajaxform()
									})
				});