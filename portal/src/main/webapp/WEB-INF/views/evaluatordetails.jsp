<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	jQuery(document).ready(function($) {
		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');
			$('.evaldetails').load("evalDetails", {
				'pageNo' : a.replace("#", ""),
				'status' : '${evalstatus}',
				'pageLimit' : '${pageLimit}',
				'selectType' : '${selectType}',
				'enterName' : $('#enterString').val()
			});
			e.preventDefault();
			window.scrollTo(0, 0);
		});

	});
	var myPaginationFun = function() {
		$('.evaldetails').load("evalDetails", {
			'status' : '${evalstatus}',
			'pageLimit' : $("#pageLimitId").val(),
			'selectType' : '${selectType}',
			'enterName' : $('#enterString').val()
		});
		window.scrollTo(0, 0);

	}
</script>
<body>
	<c:if test="${not empty evaluatorList}">
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
					<ul class="pagination" style="margin-top: -2px">${pageNav}
					</ul>
				</nav>
			</div>
		</div>
		<table>
			<tr>

				<th Style="color: #FFF">Evaluator Name</th>
				<th Style="color: #FFF">Industry</th>
				<th Style="color: #FFF">Phone Number</th>
				<th Style="color: #FFF">Evaluators Role</th>
				<th Style="color: #FFF">Training</th>

			</tr>

			<c:forEach var="details" items="${evaluatorList}">
				<tr>
					<td style="padding-left: 18px; padding-right: 10px;" class="appid"
						id="user${details.userid}_${details.evlname}"><a href="#">${details.evlname}</a></td>
					<td style="padding-left: 18px; padding-right: 10px;">${details.industryname}</td>
					<td style="padding-left: 18px; padding-right: 10px;">${details.countrycode}${details.mobileno}</td>
					<c:if test="${details.evaluatorsRole ==  0}">
						<td style="padding-left: 18px; padding-right: 10px;" class="appid"
							id="user${details.userid}_${details.evlname}"><a href="#">
								<c:if test="${details.Training == 1 || details.Training == 2}">Mapped</c:if>
								<c:if test="${empty details.Training || details.Training == 0}">Yet
								to Map</c:if>
						</a></td>
					</c:if>
					<td style="padding-left: 18px; padding-right: 10px;"><c:if
							test="${details.Training ==  0}">To be approved by Interview Pandit</c:if> <c:if
							test="${details.Training ==  1}">Training Pending</c:if>
						<c:if test="${details.Training == 2}">Training Completed</c:if> <c:if
							test="${empty details.Training}">To be approved by Interview Pandit </c:if></td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${empty evaluatorList}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;
			No records available.
		</div>
	</c:if>
	<script type="text/javascript">
		$(".appid").click(function() {
			var a = $(this).attr("id");
			var res = a.split("_");
			a = res[0];
			var profile = "Profile : ";
			var profileName = profile.concat(res[1]);
			$(".nameOf").text(profileName);
			$(".modal-bodyprofile").load("evalCompleteProfile", {
				'userId' : a.replace("user", "")
			}), $("#myModalprofile").modal({
				show : !0
			})
		})
	</script>
</body>
</html>