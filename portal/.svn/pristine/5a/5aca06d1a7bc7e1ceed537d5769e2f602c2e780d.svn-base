<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	var appinterviewid = null;
	var  myreschedule = '${reschedule}';
	var evalArray = {};
	<c:forEach var="evalLst" items="${evalUnavailableList}">
	evalArray["${evalLst.interviewid}"] = "   Applicant Name : ${evalLst.ApplicantName}  mobile : ${evalLst.appMobile}  TimeSlot : ${evalLst.date} | ${evalLst.applTime}";
	</c:forEach>
	$(document).ready(function() {
		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');		
			$('.eval_unavailable').load("evalUnavailableDetailsbasedonName", {
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimit}',
				'dashboard' :'${dashboard}',
				'evaluatorName' : '${enterString}',
				'mydays':'${mydays}',
				'reschedule':'${reschedule}'
			});
			e.preventDefault();
			window.scrollTo(0, 0);
		});
		$('#assignEval').click(function() {
			$('#myModal').modal('hide');
			var str = $("input[name='evalatorRad']:checked").val();
			var arry = str.split('-');
			var slotscheduleid = arry[0];
			var evalid = arry[1];
			assignEvalSlots(appinterviewid, slotscheduleid, evalid);

		});

	});

	function searchEvalAvailability(interviewid, uid, date) {		
		$("#processImg").show();		
		appinterviewid = interviewid;
		var title = "Assign Evaluators : ";
		var titleName = title.concat(evalArray[interviewid]);
		$(".my-modal-title").text(titleName);
		var apdate = date.slice(0, 10).split('-').reverse().join("-");
		if(myreschedule==0){
		$(".modal-bodyreschedule").load("admApplicantSlotReschedule", {
			'interviewId' : interviewid,
			'userid' : uid,
			'startTimestamp' : date,
			'startDate' : apdate,
			'reschedule':'${reschedule}'
		}), $("#myModal").modal({
			show : !0
		});
		}else if(myreschedule==1){
			$(".modal-bodyreschedule").load("admRescheduleApplicantInterview", {
				'interviewId' : interviewid,
				'userid' : uid,
				'startTimestamp' : date,
				'startDate' : apdate,
				'reschedule':'${reschedule}'
			}), $("#myModal").modal({
				show : !0
			});
		}
		
	}
	var myPaginationFun = function() {		
		$('.eval_unavailable').load("evalUnavailableDetailsbasedonName", {
			'pageLimit' : $("#pageLimitId").val(),				
			'dashboard' :'${dashboard}',
			'evaluatorName' : '${enterString}',
			'mydays':'${mydays}',
			'reschedule':'${reschedule}'
		});
		window.scrollTo(0, 0);

	}
	function reload(){
		location.reload();
	}

</script>
<style>
.tmilejend {
	height: 16px;
	width: 16px;
	margin-right: 10px;
}

.table-hd {
	height: 61px;
	color: #FFF;
	background: #59574B;
}

.ev-btn {
	padding: 8px 8px;
	margin-bottom: 5px;
	border: 1px solid #999393;
}

.interview-reschedule {
	width: 90%;
}

.emergency-slots {
	background-color: #FF6A6A !important;
	color: #ffffff !important;
}
</style>
<body>
	<hr>
	<c:if test="${not empty evalUnavailableList}">
		<div class="row">
			<div class="col-md-4" style="text-align: left">Total No.of
				Records:${evalLstCount}</div>
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
				<th class="table-hd"></th>
				<th class="table-hd" Style="color: #FFF">Interview ID </th>
				<th class="table-hd" Style="color: #FFF">Applicant Name </th>
				<th class="table-hd" Style="color: #FFF">Evaluator Name </th>
				<th class="table-hd" Style="color: #FFF">Interviewers Role</th>
				<th class="table-hd" Style="color: #FFF">Time Slot </th>
				<th class="table-hd" Style="color: #FFF">Status </th>
			</tr>

			<c:forEach var="evalList" items="${evalUnavailableList}">
				<tr
					<c:if test='${evalList.timestatus==1}'>class ="emergency-slots"</c:if>>
					<td style="padding-left: 12px; padding-right: 10px;">
						<button type="button" class="ev-btn" style="background: #FFF;"></button>
					<td style="padding-left: 18px; padding-right: 10px;" class="myTmi"
						id="my${evalList.interviewid}_${evalList.interviewtmiid}"><a
						<c:if test='${evalList.timestatus==1}'>style='color:#Ffffff !important' </c:if>
						href="#">${evalList.interviewtmiid}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;" class="appid"
						id="user${evalList.userid}"><a
						<c:if test='${evalList.timestatus==1}'>style='color:#Ffffff !important' </c:if>
						href="#">${evalList.ApplicantName}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;">Unavailable<img
						class="myEvalTmi"
						onclick="searchEvalAvailability('${evalList.interviewid}','${evalList.userid}','${evalList.starttime}');"
						id="${evalList.interviewid}"
						<c:if test='${evalList.timestatus==1}'>src="resources/images/icon_evaluator_search_white.png"</c:if>
						src="resources/images/icon_evaluator_search.png" height="16px"
						width="16px" style="margin-left: 6px;"></td>
					<td style="padding-left: 18px; padding-right: 10px;">${evalList.interviewerrolename}</td>
					<td style="padding-left: 18px; padding-right: 10px;">${evalList.date}
						<br> ${evalList.applTime}
					</td>
					<td style="padding-left: 18px; padding-right: 10px;">${evalList.status}</td>

				</tr>
			</c:forEach>
		</table>
		<br>
		<div class="row">
			<div class="form-group ">
				<div class="col-md-3">
					<img class="tmilejend"
						src="resources/images/icon_eval_reject_slot.png">Emergency
					Slots
				</div>
			</div>
		</div>
	</c:if>
	<c:if test="${empty evalUnavailableList}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
			are no Unavailable Evaluators List.
		</div>
	</c:if>
	<hr>
	<script type="text/javascript">
		jQuery(document).ready(
				function() {
					$("#interviewlandLink").parent().addClass("active"), $(
							".myTmi").click(
							function() {
								var a = $(this).attr("id");
								var res = a.split("_");
								a = res[0];
								var profile = "TMI ID : ";
								var profileName = profile.concat(res[1]);
								$(".nameOf").text(profileName);
								$(".modal-bodyTmi").load(
										"tmiAdmGetApplicantDetailsApplicant", {
											'interviewId' : a.replace("my", "")
										}), $("#myModal1").modal({
									show : !0
								})
							})

					$(".appid").click(function() {
						var a = $(this).attr("id");
						$(".modal-bodyprofile").load("usersCompleteProfile", {
							'userId' : a.replace("user", "")
						}), $("#myModalprofile").modal({
							show : !0
						})
					});
				});
	</script>
	<form id="assignEvaluator" action="#" method="post" autocomplete="off">
		<div class="modal" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog interview-reschedule">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" onclick="reload()"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title my-modal-title" id="myModalLabel"></h4>
					</div>
					<div class="modal-body modal-bodyreschedule"></div>
					<div class="modal-footer">
						<hr>
						<div class="col-md-12 ">
							<div class="col-md-2 col-md-push-8">
								<button type="button" class="form-control mybackbtn" onclick="reload()"
									data-dismiss="modal">Cancel</button>
							</div>
							<div class="col-md-2 col-md-push-8">
								<button type="submit" class="form-control mycontinuebtn"
									id="assignEval">Assign</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
