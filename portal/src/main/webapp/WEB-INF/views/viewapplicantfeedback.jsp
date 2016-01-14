<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.text-knowledge {
	font-family: robotoregular;
	font-size: 13px;
	color: #3E454C;
	text-align: justify;
}
</style>
<body>

	<div class="container">
		<div class="row">
			<div class="col-md-9">

				<c:if test="${not empty interviewDetailsKnowledge}">
					<c:set var="i" value="test" />
					<c:forEach var="feedLst" items="${interviewDetailsKnowledge}">
						<c:if test="${feedLst.feedbacksectionname ne i }">
							<div class="row">
								<div class="col-xs-10 col-sm-11 col-md-11">
									<div class="form-group">
										<span class="header-belowtext">${feedLst.feedbacksectionname}</span>
									</div>
								</div>
							</div>
						</c:if>
						<div class="row">
							<div class="col-xs-12 col-sm-9 col-md-9">
								<div class="form-group text-instruction">${feedLst.feedbacksubsectionname}</div>
							</div>
							<div class="col-xs-12 col-sm-2 col-md-2 pull-right">
								<div class="form-group text-instruction">
									<c:forEach var="i" begin="1" end="5">
										<c:if test="${feedLst.rating < i}">
											<img src="resources/images/off.png">
										</c:if>
										<c:if test="${feedLst.rating >= i}">
											<img src="resources/images/on.png">
										</c:if>
									</c:forEach>
								</div>
							</div>
							<div class="col-xs-12 col-sm-12 col-md-12">
								<div class="form-group text-knowledge">${feedLst.notes}</div>
							</div>
						</div>
						<c:set var="i" value="${feedLst.feedbacksectionname}" />
					</c:forEach>
				</c:if>
				<hr>
			</div>
		</div>
	</div>

</body>