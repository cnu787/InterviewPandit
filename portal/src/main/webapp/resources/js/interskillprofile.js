/**
 * This function is used to  update the interview skills in to profile skills 
 * based on condition and call updateinterviewSkills url
 * 
 */
var myajaxform = function() {
	if (document.getElementById("skillspage").checked)
		$("#myprofileUpdate").modal("show");
	else {
		document.getElementById("skillspage").checked = !1;
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
	}
	$("#updateProfile").click(
			
			function() {
				$("#processImg").show();
				document.getElementById("skillspage").checked = !0, $(
						"#myprofileUpdate").modal("hide");
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
			}), $("#noupdate").click(
			function() {
				document.getElementById("skillspage").checked = !1, $(
						"#myprofileUpdate").modal("hide");
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
			})
};