<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
body {
	background-color: #0094DA;
}

.top-banner {
	border-bottom-color: #fff;
	border-bottom: 5px solid #FFF;
}

.carousel-inner img {
	margin: auto;
}
/* .carousel-control {
width:30%;
} */
</style>
<body>
	<div class="top-banner" style="background-color: #3E454C">
		<div id="carousel-example-generic" class="carousel slide"
			data-ride="carousel">
			<!-- data-interval="false" -->
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#carousel-example-generic" data-slide-to="0"
					class="active"></li>
				<li data-target="#carousel-example-generic" data-slide-to="1"></li>
				<li data-target="#carousel-example-generic" data-slide-to="2"></li>
				<li data-target="#carousel-example-generic" data-slide-to="3"></li>
			</ol>

			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">
				<div class="item active">
					<a href="registerView.do"><img class="hidden-xs hidden-sm"
						src="resources/images/Banner-1.jpg" style="width: 75%;"> <img
						class="visible-xs visible-sm"
						src="resources/images/Banner-sm-1.jpg" style="width: 100%;"></a>
					<div class="carousel-caption"></div>
				</div>
				<div class="item">
					<a href="http://blogs.interviewpandit.com/"><img
						class="hidden-xs hidden-sm" src="resources/images/Banner-2.jpg"
						style="width: 75%;"> <img class="visible-xs visible-sm"
						src="resources/images/Banner-sm-2.jpg" style="width: 100%;"></a>
					<div class="carousel-caption"></div>
				</div>
				<div class="item">
					<a href="registerView.do"><img class="hidden-xs hidden-sm"
						src="resources/images/Banner-3.jpg" style="width: 75%;"> <img
						class="visible-xs visible-sm"
						src="resources/images/Banner-sm-3.jpg" style="width: 100%;"></a>
					<div class="carousel-caption"></div>
				</div>
				<div class="item">
					<a href="registerView.do"><img class="hidden-xs hidden-sm"
						src="resources/images/Banner-4.jpg" style="width: 75%;"> <img
						class="visible-xs visible-sm"
						src="resources/images/Banner-sm-4.jpg" style="width: 100%;"></a>
					<div class="carousel-caption"></div>
				</div>
			</div>

			<!-- Controls -->
			<a class="left carousel-control" href="#carousel-example-generic"
				role="button" data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#carousel-example-generic"
				role="button" data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true">
			</span> <span class="sr-only">Next</span>
			</a>
		</div>
	</div>
	<div class="home-middle">
		<div class="container">
			<div class="row" style="padding: 30px 0px 60px;">
				<div class="col-xs-12 col-sm-12 col-md-12">
					<div class="row">
						<div class="col-xs-12 col-sm-12 col-md-9 testmyinterviewblog">
							<div class="row">
								<c:set var="blogCount" value="0" />
								<c:forEach var="blgData" items="${blogData}">
									<c:set var="blogCount" value="${blogCount+1}" />
									<div class="col-xs-6 col-sm-6 col-md-6">
										<div class="form-group">
											<div class="row">
												<div class="col-xs-12  col-sm-4 col-md-5 ">
													<img class="thumbnailimg" src="${blgData.img}"
														style="width: 100%;">
												</div>
												<div class="col-xs-12  col-sm-8 col-md-7 ">
													<h2 style="margin-top: 0px;">
														<a style="color: #ffffff;" href="${blgData.titlelink}">${blgData.title}</a>
													</h2>
													<p style="font-family: robotolight; line-height: 1.65;">
														${blgData.category} <br>${blgData.date}
													</p>
												</div>
											</div>
										</div>
									</div>
									<c:if test="${blogCount % 2 ==0 }">
										<div
											class="clearfix visible-xs-block visible-sm-block visible-md-block visible-lg-block"></div>
									</c:if>
								</c:forEach>
							</div>
						</div>					
					</div>
				</div>
			</div>
		</div>
	</div>	
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$("#homelink").parent().addClass("active");
		});
	</script>
</body>