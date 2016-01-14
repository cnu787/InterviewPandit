/**
 * This function is used to call after bootstrap validation and call to applicantStudy url 
 * 
 */
var myajaxform = function() {
	$("#processImg").show();
	var a = $("form").serialize();
	$.ajax({
		url : "applicantStudy",
		type : "GET",
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