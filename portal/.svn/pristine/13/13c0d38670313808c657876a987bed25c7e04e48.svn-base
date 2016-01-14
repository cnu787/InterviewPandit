<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(document).ready(function() {

		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');
			$('.eval_interviews').load("getDashboardReport", {
				'dashboardId' : '${dashboardId}',
				'enterString' : '${enterString}',
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimit}'
			});
			e.preventDefault();
			window.scrollTo(0, 0);
		});

		$(".myTmi").click(function() {
			var a = $(this).attr("id");
			var res = a.split("_");
			a = res[0];
			a = a.replace("my", "")
			var profile = "TMI ID : ";
			var profileName = profile.concat(res[1]);
			$(".nameOf").text(profileName);
			$(".modal-bodyTmi").load("tmiAdmGetApplicantDetails", {
				'interviewId' : a
			}), $("#myModal").modal({
				show : !0
			})
		})
	});
	var myPaginationFun = function() {
		$('.eval_interviews').load("getDashboardReport", {
			'dashboardId' : '${dashboardId}',
			'enterString' : '${enterString}',
			'pageLimit' : $("#pageLimitId").val()
		});
		window.scrollTo(0, 0);

	}
</script>
<style>
.table-hd {
	height: 61px;
	color: #FFF;
	background: #59574B;
}
</style>
<body>
	<h1 class="header-file">
		<span style="color: #F26F21">Interview Results:</span>
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
				<th Style="color: #FFF">Applicant Name</th>
				<th Style="color: #FFF">Evaluator Name</th>
				<th Style="color: #FFF">Interviewers Role</th>
				<th Style="color: #FFF">Time Slot</th>
				<th Style="color: #FFF">Status</th>
				<th Style="color: #FFF">Meeting Request</th>
			</tr>
			<c:forEach var="evalList" items="${userDetails}">
				<tr>
					<c:if test="${evalList.slotStatus eq 2 && evalList.interviewStatus ne 3}">
						<td class="myTmi"
							id="my${evalList.interviewid}_${evalList.interviewtmiid}"><a
							href="#">${evalList.interviewtmiid}</a></td>
					</c:if>
					<c:if test="${evalList.slotStatus ne 2 || evalList.interviewStatus eq 3}">
						<td>${evalList.interviewtmiid}</td>
					</c:if>
					<td class="appid" id="user${evalList.appuserid}"><a href="#">${evalList.appname}</a></td>
					<td class="evalid" id="user${evalList.evauserid}"><a href="#">${evalList.evaname}</a></td>
					<td>${evalList.interviewerrolename}</td>
					<td>${evalList.slotTime}</td>
					<td><c:if test="${evalList.interviewStatus eq 1}">Booked</c:if>
						<c:if test="${evalList.interviewStatus eq 2}">Closed</c:if> <c:if
							test="${evalList.interviewStatus eq 3}">Cancelled</c:if></td>
					<td><c:if test="${evalList.slotStatus eq 4 && evalList.interviewStatus ne 3}">Accepted</c:if> <c:if
							test="${evalList.slotStatus eq 3 && evalList.interviewStatus ne 3}">Rejected</c:if> <c:if
							test="${evalList.slotStatus eq 2 && evalList.interviewStatus ne 3}">Scheduled</c:if>
							</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>

	<c:if test="${ empty userDetails && not empty enterString}">
		<hr>
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;
			No records available.
		</div>
	</c:if>
	<hr>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$(".appid").click(function() {
				var a = $(this).attr("id");
				//alert('a='+a);
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
	</script>
</body>
