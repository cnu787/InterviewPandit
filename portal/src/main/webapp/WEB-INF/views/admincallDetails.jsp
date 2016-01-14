<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
jQuery(document).ready(function($) {
	$('.pageClick').click(function(e) {	
		var a = this.getAttribute('href');
		$('.connectdetails').load("tmiAdmAdmincallDetails", {
			'pageNo' : a.replace("#", ""),
			'connectstatus' : '${connectstatus}',
			'evaluatorName' : '${enterString}',
			'dashboard' : '${dashboard}'
		});
		e.preventDefault();
		window.scrollTo(0, 0);
	});

});
var myPaginationFun = function() {		
	$('.connectdetails').load("tmiAdmAdmincallDetails", {
		'connectstatus' : '${connectstatus}',
		'pageLimit' : $("#pageLimitId").val(),
		'evaluatorName' : '${enterString}',
		'dashboard' : '${dashboard}'
	});
	window.scrollTo(0, 0);

}
</script>
<body>
	<hr>
	<c:if test="${not empty adminDetailsList}">
	<div class="row">
			<div class="col-md-4" style="text-align: left">Total No.of
				Records:${adminLstCount}</div>
			<div class="col-md-4" style="margin-top: -4px">
				Show <select id="pageLimitId" onchange="myPaginationFun('#');">
					<c:forEach var="pagLst" items="${paginationList}">
						<option
							<c:if test="${pagLst.paginationlimit==pageLimit}">selected="selected"</c:if>
							value="${pagLst.paginationlimit}">${pagLst.paginationlimit}</option>
					</c:forEach>
				</select>
			</div>
			<div id="" class="col-md-4" style="text-align: right">
				<nav>
					<ul class="pagination" style="margin-top: -2px">${pageNav}</ul>
				</nav>
			</div>
		</div>		
		<table>
			<tr>
				<th Style="color: #FFF">Call<br>Status
				</th>
				<th Style="color: #FFF">Interview ID</th>
				<th Style="color: #FFF">Evaluator<br>Name
				</th>
				<th Style="color: #FFF">Evaluator<br>Phone Number
				</th>
				<th Style="color: #FFF">Applicant<br>Name
				</th>
				<th Style="color: #FFF">Applicant<br>Phone Number
				</th>
				<th Style="color: #FFF">Date</th>
				<th Style="color: #FFF">Time Slot</th>
				<th Style="color: #FFF">Actions</th>
			</tr>

			<c:forEach var="details" items="${adminDetailsList}">
				<tr>
					<td style="padding-left: 18px; padding-right: 10px;"><c:if
							test="${details.call_status==5 || details.call_status==6}">
							<button type="button" class="form-control btn btn-default"
								style="background: #FF3333;"></button>
						</c:if> <c:if test="${details.call_status==9}">
							<button type="button" class="form-control btn btn-default"
								style="background: #79B221;"></button>

						</c:if>
					<td style="padding-left: 18px; padding-right: 10px;" class="myTmi"
						id="my${details.intId}_${details.InterviewID}"><a href="#">${details.InterviewID}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;" class="appid"
						id="user${details.EvaluatorUserId}"><a href="#">${details.EvaluatorName}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;">${details.EvaluatorNumber}</td>
					<td style="padding-left: 18px; padding-right: 10px;" class="appid"
						id="user${details.applicantUserId}"><a href="#">${details.ApplicantName}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;">${details.ApplicantNumber}</td>
					<td style="padding-left: 18px; padding-right: 10px;">${details.Date}</td>
					<td style="padding-left: 18px; padding-right: 10px;">${details.Time}</td>
					<td style="padding-left: 18px; padding-right: 10px;"><c:if
							test="${(details.call_status!=1) && (details.call_status!=2)}">
							<a href="#"
								onclick="callAppEval('${details.EvaluatorNumber}','${details.ApplicantNumber}','${details.InterviewID}');return false;"><img
								src="resources/images/icon_phone.png" height="30px" width="30px"></a>
						</c:if></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${empty adminDetailsList}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png">&nbsp;
			No records available.
		</div>
	</c:if>
	<hr>
	<script>
		jQuery(document).ready(
				function() {
					$("#interviewlandLink").parent().addClass("active"), $(
							".myTmi").click(
							function() {

								var a = $(this).attr("id");
								var res = a.split("_");
								a = res[0];
								var profile = "Interview Pandit ID : ";
								var profileName = profile.concat(res[1]);
								$(".nameOf").text(profileName);

								$(".modal-bodyTmi").load(
										"tmiAdmGetApplicantDetailsApplicant", {
											'interviewId' : a.replace("my", "")
										}), $("#myModal1").modal({
									show : !0
								})

							})
				});
	</script>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$(".appid").click(function() {
				var a = $(this).attr("id");
				$(".modal-bodyprofile").load("usersCompleteProfile", {
					'userId' : a.replace("user", "")
				}), $("#myModalprofile").modal({
					show : !0
				})
			});
			$(".evalid").click(function() {
				var a = $(this).attr("id");
				$(".modal-bodyprofile").load("usersCompleteProfile", {
					'userId' : a.replace("user", "")
				}), $("#myModalprofile").modal({
					show : !0
				})
			});

		});
		function callAppEval(evaluatorPhone, applicantPhone, tmiId) {
			$.ajax({
				url : "AdminKookooConnect",
				data : {
					"evaluatorPhone" : evaluatorPhone,
					"applicantPhone" : applicantPhone,
					"tmiId" : tmiId
				}
			});
		}
	</script>
</body>
</html>