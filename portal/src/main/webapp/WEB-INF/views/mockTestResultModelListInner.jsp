<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	$('.pageClick').click(function(e) {
		//$("#processImg").show();
		var a = this.getAttribute('href');
		$('.mockTestResultInner').load("tmiAdmMockTestResultModelList", {
			'testStartDate' : $('#testStartDate').val(),
			'testEndDate' : $('#testEndDate').val(),
			'pageNo' : a.replace("#", ""),
			'pageLimit' : '${pageLimit}'
		});
		e.preventDefault();
		window.scrollTo(0, 0);
	});	
	$('#testStartDate').val('${testStartDate}');
	$('#testEndDate').val('${testEndDate}');
	var myPaginationLimit = function() {		
		$('.mockTestResultInner').load("tmiAdmMockTestResultModelList", {
			'testStartDate' : $('#testStartDate').val(),
			'testEndDate' : $('#testEndDate').val(),
			'pageLimit' : $('#pageLimitId').val()
		});
		window.scrollTo(0, 0);

	}
</script>
<hr>
<c:if test="${not empty testResultList}">
	<div class="row">
		<div class="col-xs-10 col-sm-6 col-md-3 form-group"
			style="text-align: left">Total No.of
			Records:${mockTestResultCount}</div>

		<div class="col-xs-10 col-sm-6 col-md-3 form-group">
			Show <select id="pageLimitId" onchange="myPaginationLimit();">
				<c:forEach var="pagLst" items="${paginationList}">
					<option
						<c:if test="${pagLst.paginationlimit==pageLimit}">selected="selected"</c:if>
						value="${pagLst.paginationlimit}">${pagLst.paginationlimit}</option>
				</c:forEach>
			</select>
		</div>
		<div id="" class="col-xs-10 col-sm-6 col-md-4">
			<nav>
				<ul class="pagination" id="pagination-bottom"
					style="margin-top: -2px">${pageNav}
				</ul>
			</nav>

		</div>
		<div class="col-xs-10 col-sm-6 col-md-2">
			<img src="resources/images/printer.png" style="width:40px;" onclick="PrintElem('#mockTestResultByList');"/>
		</div>
	</div>
	<div id="mockTestResultByList">
	<c:forEach var="testResultLst" items="${testResultList}">
		<div class="col-md-12 ">
			<div class="row">
				<div class="form-group">
					<div class="col-md-4">
						<label>Name : </label>${testResultLst[0].fullname}</div>
					<div class="col-md-4">
						<label>Test Paper Name : </label>${testResultLst[0].testPaperName}
					</div>
					<div class="col-md-4">
						<label>Test RollNo : </label>${testResultLst[0].testRollNo}
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-6">
					<label>Email Id : </label>${testResultLst[0].emailid}
				</div>
			</div>
			<br>
		</div>
		<div class="row">
			<div class="col-md-12">
				<table>
					<tr>
						<th class="table-hd" style="text-align: left;">Section Name</th>
						<th class="table-hd">Total Questions</th>
						<th class="table-hd">Attended Questions</th>
						<th class="table-hd">Correct Answers</th>
					</tr>

					<c:forEach var="resultList"
						items="${testResultLst[0].exam_results}">

						<tr>
							<td style="text-align: left;">${resultList.sectionName}</td>
							<td>${resultList.totalNoOfQuestions}</td>
							<td>${resultList.noOfQuestionsAttended}</td>
							<td>${resultList.noOfCorrectAnswers}</td>

						</tr>
					</c:forEach>
					<tr style="background: #f2f2f2;">
						<td style="font-weight: bold; text-align: left;">Grand Total</td>
						<td style="font-weight: bold;">
							${testResultLst[0].totalQuestions}</td>
						<td style="font-weight: bold;">
							${testResultLst[0].totalQuestionsAttended}</td>
						<td style="font-weight: bold;">
							${testResultLst[0].totalCorrectAnswers}</td>

					</tr>
				</table>

			</div>
		</div>
		<br>
		<br>
		<hr>
	</c:forEach>
	</div>
</c:if>
<c:if test="${empty testResultList}">
<div class="row">
			<div class="col-xs-8 col-sm-8 col-md-12" style="text-align: center;">
				<div class="form-group">
					<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;${nodata}
				</div>
			</div>
		</div>
</c:if>
