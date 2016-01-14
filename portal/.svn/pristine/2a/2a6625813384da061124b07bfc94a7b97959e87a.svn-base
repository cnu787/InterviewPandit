<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	$(document).ready(function() {
		$('.pageClick').click(function(e) {
			var a = this.getAttribute('href');
			$('.eval_monthSlot').load("getEvalMonthSlotCount", {
				'pageNo' : a.replace("#", ""),
				'pageLimit' : '${pageLimit}'
			});
			e.preventDefault();
			window.scrollTo(0, 0);
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
		$('.eval_monthSlot').load("getEvalMonthSlotCount", {
			'pageLimit' : $("#pageLimitId").val()
		});
		window.scrollTo(0, 0);

	}
</script>
<body>
	<c:if test="${not empty evalSlotCountList}">
		<div class="row">
			<div class="col-md-4" style="text-align: left">Total No.of
				Records:${evalSlotCount}</div>
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
				<th Style="color: #FFF">Count</th>


			</tr>

			<c:forEach var="evalList" items="${evalSlotCountList}">
				<tr>
					<td class="appid" id="user${evalList.userid}"><a href="#">${evalList.evalname}</a></td>
					<td>${evalList.evalcount}</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	<c:if test="${ empty evalSlotCountList}">
		<div style="text-align: center;">
			<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;
			No records available.
		</div>
	</c:if>
</body>