/**
 * This function is used to call after bootstrap validation and call to careermap url 
 * 
 */
function myajaxform() {
	$("#processImg").show();
	var a = $("form").serialize();
	$.ajax({
		url : "careermap",
		type : "GET",
		success : completeHandler,
		error : errorHandler,
		data : a,
		cache : !1,
		contentType : !1,
		processData : !1
	})
}
/**
 * This function is used to add class to profilelink id
 * 
 */
jQuery(document).ready(function() {
	$("#profilelink").parent().addClass("active"), $("#back").click(function() {
		$("#mainProfileContainer").load("studyPage")
	})
});
var errorHandler = function() {
}, progressHandlingFunction = function() {
}, completeHandler = function() {
	$("#processImg").hide();
	0 == $("input[name=career]:checked", "#myprofile").val() ? $(
			"#mainProfileContainer").load("careerpage") : 1 == $(
			"input[name=career]:checked", "#myprofile").val()
			&& $("#mainProfileContainer").load("skillspage")
};