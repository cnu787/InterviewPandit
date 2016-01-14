/**
 * This function is used to validating empty fields 
 */
$(document)
		.ready(
				function() {
							$("#myinterview")
									.bootstrapValidator(
											{
												feedbackIcons : {
													valid : "glyphicon glyphicon-ok",
													invalid : "glyphicon glyphicon-remove",
													validating : "glyphicon glyphicon-refresh"
												},
												fields : {
													industry : {
														validators : {
															callback : {
																message : "Please select listed industry.",
																callback : function(
																		a, b) {
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
																		a, b) {
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
													interviewtype : {
														validators : {
															callback : {
																message : "Please select listed interview type.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"interviewtype")
																			.val();
																	return -1 != d
																}
															}
														}
													},													
													interviewrole : {
														validators : {
															callback : {
																message : "Please select listed interviewer role.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"interviewrole")
																			.val();
																	return -1 != d
																}
															}
														}
													},
													/*uncomment below code when location select is needed*/
													/*location : {
														validators : {
															callback : {
																message : "Please select listed interviewer location.",
																callback : function(
																		a, b) {
																	var d = b
																			.getFieldElements(
																					"location")
																			.val();
																	return -1 != d
																}
															}
														}
													},*/
													resume : {
														validators : {
															notEmpty : {
																message : "Resume is required."
															},
															file : {
																extension : "pdf,doc,docx",
																type : "application/pdf,application/msword,application/vnd.openxmlformats-officedocument.wordprocessingml.document",
																maxSize : 2097152,
																message : "The selected file is not valid, it should be (pdf,doc,docx) and 2 MB at maximum."
															}
														}
													}
												}
											}), $("#myinterview").on(
									"success.form.bv",
									function(a) {
										a.preventDefault(), 0 == myflag
												&& myajaxform()
									})
				});