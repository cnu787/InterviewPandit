jQuery(document).ready(function() {
	/**
	 * This function is used to show interview skills view
	 */
	$("#back").click(function() {
		$("#interviewBookingContainer").load("interviewTechSkills")
	})
});
/**
 * This function is used to  add skill priority List
 */
var myajaxform = function() {
	$("#processImg").show();
	var a = $("form").serialize();
	$.ajax({
		url : "addskillpriorityList",
		type : "get",
		success : completeHandler,
		error : errorHandler,
		data : a,
		cache : !1,
		contentType : !1,
		processData : !1
	})
}, completeHandler = function() {
	/**
	 * this is used to shows slot booking view
	 */
	nextstep(), $("#interviewBookingContainer").load("slotBooking")
}, errorHandler = function() {
}, progressHandlingFunction = function() {
};