<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
/* Generic Styling, for Desktops/Laptops */
.tmimock-rollno {
	width: 1000px;
}

table {
	width: 100%;
	/*border-collapse: collapse;*/
}

th {
	/* 	background: #59574B;
	color: white; */
	font-weight: bold;
}

.table-hd {
	color: #FFF;
	background: #59574B;
}

td, th {
	padding: 10px 18px;
	border: 0px solid #ccc;
	text-align: center;
	width: -1em;
	text-wrap: none;
	white-space: -moz-pre-wrap !important; /* Mozilla, since 1999 */
	white-space: -webkit-pre-wrap; /*Chrome & Safari */
	white-space: -pre-wrap; /* Opera 4-6 */
	white-space: -o-pre-wrap; /* Opera 7 */
	white-space: pre-wrap; /* css-3 */
	word-wrap: break-word; /* Internet Explorer 5.5+ */
	/*word-break: break-all;*/
	white-space: normal;
}

tr {
	border-bottom: 1px solid #cccccc;
}

label {
	font-weight: bold;
}
</style>
<body>

	<c:if test="${not empty myMockType}">
		<div
			style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
			<div class="container">Test Results</div>
		</div>
		
	</c:if>
	<div class="container">
	<c:if test="${not empty myMockType}">
	<br>
		<div style="border: 5px solid #0db14b; padding: 2%; text-align: justify;">
					<div class="row">

						<div class="col-xs-2 col-sm-1 col-md-1">
							<img class="visible-lg" src="resources/images/icon_success.png">
							<img class="visible-sm" src="resources/images/icon_success.png">
							<img class="visible-xs" src="resources/images/icon_successsmall.png">
						</div>
						<div class="col-xs-10 col-sm-11 col-md-11  ">
							<span class="successMsg" >Thank You For Taking The Mock Test</span>
						</div>
					</div>
				</div>
				</c:if>
		<br>
		<div class="col-md-12 ">
			<div class="row">
				<div class="form-group">
					<div class="col-xs-10 col-sm-11 col-md-4 ">
						<label>Test Category : </label>${testResultList[0].testCategory}</div>
					<div class="col-xs-10 col-sm-11 col-md-4 ">
						<label>Test Paper Name : </label>${testResultList[0].testPaperName}
					</div>
					<div class=" col-xs-10 col-sm-11 	col-md-4 ">
						<label>Test RollNo : </label>${testResultList[0].testRollNo}
					</div>
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
					<c:forEach var="resultList" items="${examresultsList}">
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
							${testResultList[0].totalQuestions}</td>
						<td style="font-weight: bold;">
							${testResultList[0].totalQuestionsAttended}</td>
						<td style="font-weight: bold;">
							${testResultList[0].totalCorrectAnswers}</td>

					</tr>
				</table>

			</div>
		</div>
		<hr>
	</div>
</body>