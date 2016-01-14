<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<body>

	<div class="" style="margin-top: 20px; margin-bottom: 60px;">

		<div class="container">
			<form id="feedBack" action="#" autocomplete="off">
				<h1 class="header-file">Overall impression of the Interview</h1>
				<hr>
				<input type="hidden" name="interviewId" id="interviewId"
					value="${interviewId}">
				<div class="row">
					<div class="col-md-12">
						<div class="radio">
							<label> <input type="radio" name="impression"
								id="evaluatorImpress1" value="15" checked="checked"
								<c:if test="${interviewDetails eq 15}"> checked="true"</c:if>>
								You're hired!
							</label>
						</div>
						<div class="radio">
							<label> <input type="radio" name="impression"
								id="evaluatorImpress2" value="16"
								<c:if test="${interviewDetails eq 16}"> checked="true"</c:if>>
								You may get the job, but you haven't edged out the competition
								yet!
							</label>
						</div>
						<div class="radio">
							<label> <input type="radio" name="impression"
								id="evaluatorImpress3" value="17"
								<c:if test="${interviewDetails eq 17}"> checked="true"</c:if>>
								You need more practice and preparation before going on job
								interviews.
							</label>
						</div>
					</div>
				</div>
				<input type="hidden" name="hiddenId" id="hiddenId">

				<hr>
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
					<div class="form-group">
						<button type="button" class="form-control mybackbtn" id="back">Back</button>
					</div>
				</div>
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-8">
					<div class="form-group">
						<button id="continue1" type="button"
							class="form-control mycontinuebtn">Continue</button>

					</div>
				</div>
			</form>
		</div>

	</div>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$("#back").click(function() {
				var interviewId = $("#interviewId").val();
				$("#interviewFeedBackContainer").load("evaluatorsAttitudes", {
					'interviewId' : interviewId
				})
			});
			$("#continue1").click(function() {
				myajaxform();
			});
		});
		function myajaxform() {
			var a = $("form").serialize();
			$.ajax({
				url : "evaluatorFinalUpdate",
				type : "GET",
				success : completeHandler,
				error : errorHandler,
				data : a,
				cache : !1,
				contentType : !1,
				processData : !1
			})
		}
		jQuery(document).ready(function() {

		});
		var errorHandler = function() {
		}, progressHandlingFunction = function() {
		}, completeHandler = function() {
			var interviewId = $("#interviewId").val();
			$("#interviewFeedBackContainer").load("evaluatorSummaryi", {
				'interviewId' : interviewId
			})
		};
	</script>
</body>