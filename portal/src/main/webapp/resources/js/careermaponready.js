/**
 * This function is used to call after bootstrap validation and call to applicantCareer url 
 * 
 */
var myajaxform = function() {
	myflag=1;	
	$("#processImg").show();
	var a = $("form").serialize();
	$.ajax({
		url : "applicantCareer",
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