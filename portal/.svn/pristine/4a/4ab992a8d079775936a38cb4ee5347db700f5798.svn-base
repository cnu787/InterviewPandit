/**
 * this method is used to edit skills
 * @param a
 * @param b
 */
function editSkill(a, b) {
	$("#interviewBookingContainer").load("interviewMySkills", {
		skillId : a,
		pagetype : b
	})
}
/**
 * this method is used to load interviewRole in interviewBookingContainer id div
 * 
 */
function backbutton() {
	backstep(), $("#interviewBookingContainer").load("interviewRole")
}
/**
 * this method is used to load interviewMySkills in interviewBookingContainer id div for add skills
 * 
 */
function addSkills() {
	var a = "addmoreskills";
	$("#interviewBookingContainer").load("interviewMySkills", {
		interviewsparam : a
	})
}
function deleterecord(a) {
	recordnumber = a
}
var errorHandler = function() {
}, progressHandlingFunction = function() {
}, recordnumber = 0;
/**
 * This function is used to bootstrap validation input fields
 * 
 */
jQuery(document)
		.ready(
				function() {/* $.ajaxSetup({cache:!1}), */
					$("#myModal")
							.on(
									"show.bs.modal",
									function(a) {
										var b = $(a.relatedTarget);
												$("#deleteItem")
														.click(
																function() {
																	$(
																			"#myModal")
																			.modal(
																					"hide");
																	var a = b
																			.data("skillid"
																					+ recordnumber), c = b
																			.data("pagetype"
																					+ recordnumber);
																	"undefined" !== typeof a
																			&& "undefined" !== typeof c
																			&& $(
																					"#interviewBookingContainer")
																					.load(
																							"interViewSkilldelete",
																							{
																								interviewskillId : a,
																								pagetype : c
																							})
																}),
												$("#closeItem")
														.click(
																function() {
																	$(
																			"#myModal")
																			.modal(
																					"hide")
																})
									})
				});
var completeHandler = function() {
	$("#processImg").hide();
	$("#interviewBookingContainer").load("skillspriority")
};