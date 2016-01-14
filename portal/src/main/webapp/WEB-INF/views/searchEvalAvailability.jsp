<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
</style>
<body>
	<div class="" style="margin-top: 20px;">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-12">
					<c:if test="${ not empty evalAvailableList}">
						<div class="row">
							<div class="col-md-8">
								<label style="font-family: robotoregular; font-size: 13px;"><b>Interview Pandit
										Role of Evaluator
										:${applicantNameAndPhone.interviewerrolename} </b></label>
							</div>
							<br>
							<br>
						</div>
						<div class="row">
							<div class="col-md-8 ">
								<label style="color: #37B2EB; font-size: 16px">
									Applicant Data: </label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-10 ">
								<label> ${applicantNameAndPhone.applicantname}</label>
								&nbsp;&nbsp;<label id="${applicantNameAndPhone.mobileno}"
									style="color: #37B2EB;">
									(+91)${applicantNameAndPhone.mobileno}</label>
							</div>
						</div>
						<br>
						<div class="row">
							<div class="col-md-8 ">
								<label style="color: #37B2EB; font-size: 16px">Recommended
									Evaluators: </label>
							</div>
						</div>
						<div class="row">
							<div class="col-md-10 ">

								<c:set var="i" value="0" />
								<c:forEach var="evllst" items="${evalAvailableList}">
									<input name="evalatorRad" type="radio" value="${evllst.userid}"
										<c:if test="${i == '0'}">checked="true"</c:if>>&nbsp;&nbsp;<label
										style="color: #F36F21;"> ${evllst.name}</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label
										style="color: #37B2EB;"> (+91)${evllst.mobileno}</label>
									<br>
									<c:set var="i" value="${i+1}" />
								</c:forEach>
							</div>
						</div>
					</c:if>
					<c:if test="${empty evalAvailableList}">
						<div style="text-align: center;">
							<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
							are no Evaluators Available
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
</body>