/**
 * This function is used to call after bootstrap validation and call to updateinterviewSkills url 
 * 
 */
var myajaxform = function() {
	var a = $("form").serialize();
	$.ajax({
		url : "updateinterviewSkills",
		type : "GET",
		success : completeHandler,
		error : errorHandler,
		data : a,
		cache : !1,
		contentType : !1,
		processData : !1
	})
};