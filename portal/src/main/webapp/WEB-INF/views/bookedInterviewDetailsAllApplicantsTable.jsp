<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	var myinterviewid = null;
	var mytmiid = null;
	var appuserid = null;
	$(document).ready(function() {

		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');			
			$('.allapplicant_interviews').load("tmiAdmAllAplicantBookedInterviewsBasedonSearch", {
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimit}',
				'dashboard':'${dashboard}',
				'evaluatorName':'${enterstring}'
			});
			e.preventDefault();
			window.scrollTo(0, 0);
		});
		$(".myTmi").click(function() {
			var a = $(this).attr("id");
			var res = a.split("_");
			a = res[0];
			var profile = "Interview Pandit ID : ";
			var profileName = profile.concat(res[1]);
			$(".nameOf").text(profileName);

			$(".modal-bodyTmi").load("tmiAdmGetApplicantDetailsApplicant", {
				'interviewId' : a.replace("my", "")
			}), $("#myModal").modal({
				show : !0
			});
		});
		$(".appid").click(function() {
			var a = $(this).attr("id");
			$(".modal-bodyprofile").load("usersCompleteProfile", {
				'userId' : a.replace("user", "")
			}), $("#myModalprofile").modal({
				show : !0
			})
		});
	});
	var myPaginationFun = function() {		
		$('.allapplicant_interviews').load("tmiAdmAllAplicantBookedInterviewsBasedonSearch", {
			'dashboard':'${dashboard}',
			'evaluatorName':'${enterstring}',
			'pageLimit' : $("#pageLimitId").val()
		});
		window.scrollTo(0, 0);

	}
	function cancelApplicantInterview(intViewId, tmiID, userid) {
		myinterviewid = intViewId;
		mytmiid = tmiID;
		appuserid = userid;

		$('#mycancelInterViewModel').modal({
			keyboard : false,
			show : true
		});
	}
	function cancelInterview() {
		var cancelslotReson = $('#cancelslot').val();
		$("#mycancelInterViewModel").hide();
		$("#processImg").show();

		$.ajax({
			url : "cancelApplicantInterView.do/" + myinterviewid + "/"
					+ mytmiid + "/" + appuserid + "/" + cancelslotReson,
			async : false,
			success : function(result) {
				$('#cancelslot').val('');
				completeHandler(result);
			}
		});

	}
	function completeHandler(result) {
		$("#processImg").hide();
		location.reload();
	}
</script>
<style>
.mymodal-dialog {
	width: 550px;
}

.table-hd {
	height: 61px;
	color: #FFF;
	background: #59574B;
}
</style>
<body>
	<h1 class="header-file">
		<span style="color: #F26F21">Cancel Interview:</span>
	</h1>
	<c:if test="${not empty userDetails}">

		<hr>
		<div class="row">
			<div class="col-md-4" style="text-align: left">Total No.of
				Records:${userDetailsListCount}</div>
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
				<th Style="color: #FFF">Interview ID</th>
				<th Style="color: #FFF">Name</th>
				<th Style="color: #FFF">Industry</th>
				<th Style="color: #FFF">Applied Position</th>
				<th Style="color: #FFF">Interviewers Role</th>
				<th Style="color: #FFF">Time Slot</th>
				<th Style="color: #FFF">Status</th>
				<th Style="color: #FFF"></th>

			</tr>

			<c:forEach var="appList" items="${userDetails}">
				<tr>
					<td class="myTmi"
						id="my${appList.interviewid}_${appList.interviewtmiid}"><a
						href="#">${appList.interviewtmiid}</a></td>
					<td class="appid" id="user${appList.userid}"><a href="#">${appList.UserName}</a></td>
					<td>${appList.industryname}</td>
					<td>${appList.designation}</td>
					<td>${appList.interviewerrolename}</td>
					<td>${appList.timeslot}</td>
					<td>Booked</td>
					<td><a href="javascript:cancelApplicantInterview('${appList.interviewid}','${appList.interviewtmiid}','${appList.userid}');">Cancel
								Booking</a>
						</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${ empty userDetails}">
		<hr>
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;
			No records available.
		</div>
	</c:if>
	<hr>
	<form id="mycancelInterviewForm" action="#" method="post"
		autocomplete="off">
		<div class="modal" id="mycancelInterViewModel" tabindex="-1"
			role="dialog" data-backdrop="static" aria-labelledby="myModalLabel"
			aria-hidden="true">
			<div class="modal-dialog mymodal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">Cancel Interview
							Confirmation</h4>
					</div>
					<div class="modal-body">
						<div class="row">
							<label>Are you sure you wish to Cancel Interview?</label>
							<div class="col-md-12">
								<div class="form-group">
									<textarea class="form-control" rows="4" name="cancelslot"
										id="cancelslot" maxlength="100" style="resize: none"></textarea>

								</div>
								<hr>
							</div>

						</div>
					</div>
					<div class="modal-footer">
						<div class="col-md-4 col-md-push-2">
							<button type="button" class="form-control mybackbtn"
								data-dismiss="modal">No</button>
						</div>
						<div class="col-md-4 col-md-push-3">
							<button type="submit" id="cancelItem"
								class="form-control mycontinuebtn">Yes</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>

	<script>
		$(document).ready(function() {
			$("#mycancelInterviewForm").bootstrapValidator({
				feedbackIcons : {
					valid : "glyphicon glyphicon-ok",
					invalid : "glyphicon glyphicon-remove",
					validating : "glyphicon glyphicon-refresh"
				},
				fields : {
					cancelslot : {
						validators : {
							notEmpty : {
								message : "reason  is required."
							}
						}
					}
				}
			})
		}), $("#mycancelInterviewForm").on("success.form.bv", function(a) {
			cancelInterview();

		});
	</script>
</body>
