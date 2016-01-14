<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.starimg{
  margin      : 0;
  padding     : 1.5em;
  font-family : sans-serif;
  line-height : 1.5;
} 
p{
  margin  : 0 0 1.0em;
  padding : 0;
}
.starRating:not(old){
  display        : inline-block;
  width          : 7.5em;
  height         : 1.5em;
  overflow       : hidden;
  vertical-align : bottom;
}

.starRating:not(old) > input{
  margin-right : -100%;
  opacity      : 0;
}

.starRating:not(old) > label{
  display         : block;
  float           : right;
  position        : relative;
  background      : url('resources/images/star-off.svg');
  background-size : contain;
}
.starRating:not(old) > label:before{
  content         : '';
  display         : block;
  width           : 1.5em;
  height          :1.5em;
  background      : url('resources/images/23 x 22.png');
  background-size : contain;
  opacity         : 0;
  transition      : opacity 0.2s linear;
}

.starRating:not(old) > label:hover:before,
.starRating:not(old) > label:hover ~ label:before,
.starRating:not(:hover) > :checked ~ label:before{
  opacity : 1;
}
</style>
<script>
function feedbackRating(rating, ratingName,id) {
	 document.getElementById(id+'educationalRating' + rating).checked = true;
	 
 }
</script>
<body>
	<div class="" style="margin-top: 20px; margin-bottom: 60px;">
		<div class="container">
			<form id="feedBackform" action="#" autocomplete="off">
				<input type="hidden" name="feedbackDone" id="feedbackDone">
				<input type="hidden" name="interviewId" id="interviewId"
					value="${interviewId}">
				<div class="row">
					<div class="col-md-9">
						<script>var qIdArr = [];</script>
						<c:set var="i" value="test" />
						<c:set var="j" value="0" />
						<c:forEach var="knowLst" items="${interviewDetailsKnowledge}">
							<c:if test="${knowLst.feedbacksectionname ne i }">
								<div class="row">
									<div class="col-xs-10 col-sm-11 col-md-11">
										<div class="form-group">
											<span class="header-belowtext">${knowLst.feedbacksectionname}</span>
										</div>
									</div>
								</div>
							</c:if>
							<c:if
								test="${knowLst.feedbacksectionname ne 'Overall Impression of the Interview' }">
								<div class="row">
									<div class="col-xs-12 col-sm-8 col-md-9">
										<div class="form-group text-instruction">${knowLst.feedbacksubsectionname}</div>
									</div>
									<input type="hidden" name="educationalId_${j}"
										id="educationalId_${j}" value="${knowLst.subsectionid}">
									<div class="col-xs-12 col-sm-4 col-md-3 ">
										<div class="form-group text-instruction pull-right">
											<div>
												<p>
													<span class="starRating "> <input
														id="${j}educationalRating5" type="radio"
														name="educationalRating_${j}" value="5"
														<c:if test="${knowLst.rating eq 5}" >checked="checked"</c:if>
														onclick="feedbackRating(5,'edurating',${j})"> <label
														for="${j}educationalRating5">5</label> <input
														id="${j}educationalRating4" type="radio"
														name="educationalRating_${j}" value="4"
														<c:if test="${knowLst.rating eq 4}" >checked="checked"</c:if>
														onclick="feedbackRating(4,'edurating',${j})">&nbsp;
														<label for="${j}educationalRating4">4</label> <input
														id="${j}educationalRating3" type="radio"
														name="educationalRating_${j}" value="3"
														<c:if test="${knowLst.rating eq 3}" >checked="checked"</c:if>
														onclick="feedbackRating(3,'edurating',${j})">&nbsp;
														<label for="${j}educationalRating3">3</label> <input
														id="${j}educationalRating2" type="radio"
														name="educationalRating_${j}" value="2"
														<c:if test="${knowLst.rating eq 2}" >checked="checked"</c:if>
														onclick="feedbackRating(2,'edurating',${j})">&nbsp;
														<label for="${j}educationalRating2">2</label> <input
														id="${j}educationalRating1" type="radio"
														name="educationalRating_${j}" value="1"
														<c:if test="${(knowLst.rating eq 1)||(empty knowLst.rating)}" >checked="checked"</c:if>
														onclick="feedbackRating(1,'edurating',${j})">&nbsp;
														<label for="${j}educationalRating1">1</label> <script>qIdArr.push('${j}');</script>
													</span>
												</p>
											</div>
										</div>
									</div>
									<div class="col-xs-12 col-sm-12 col-md-12">
										<div class="form-group text-knowledge">
											<textarea class="form-control" rows="2" maxlength="150"
												style="resize: none" name="educationalNotes_${j}"
												id="educationalNotes_${j}">${knowLst.notes}</textarea>
										</div>
										<p
											style="font-size: 12px; font-family: robotoregular; color: #3e454c"
											class="pull-right">Max 150 characters</p>
									</div>
								</div>
							</c:if>
							<c:if
								test="${knowLst.feedbacksectionname eq 'Overall Impression of the Interview' }">
								<div class="row">
									<div class="col-md-12">
										<div class="radio">
											<label> <input type="radio" name="overallNotes"
												id="educationalNotes_${j}"
												<c:if test="${knowLst.feedbacksubsectionid eq knowLst.subsectionid || knowLst.subsectionid eq 15}"> checked="true"</c:if>
												value="${knowLst.subsectionid}">
												${knowLst.feedbacksubsectionname}
											</label>
										</div>

									</div>
								</div>
							</c:if>
							<c:set var="j" value="${j+1}" />
							<c:set var="i" value="${knowLst.feedbacksectionname}" />
						</c:forEach>
						<hr>
					</div>
					<input type="hidden" name="interviewId" id="interviewId"
						value="${interviewId}">
				</div>
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-3">
					<div class="form-group">
						<button type="button" data-dismiss="modal"
							class="form-control mybackbtn" id="back">Close</button>
					</div>
				</div>
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-3">
					<div class="form-group">
						<button id="continue1" type="submit"
							class="form-control mycontinuebtn">Save</button>
					</div>
				</div>
				<div
					class="col-xs-4 col-xs-push-4 col-sm-2 col-sm-push-8 col-md-2 col-md-push-3">
					<div class="form-group">
						<button id="continue1" type="submit"
							onclick="feedbackStatusChange()"
							class="form-control mycontinuebtn">Done</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">	
 	var myflag = 0;
	function feedbackStatusChange(){		
		document.getElementById('feedbackDone').value='1';
	}
			$(document).ready(function() {			
				$("#feedBackform").bootstrapValidator({
					feedbackIcons : {
						valid : "glyphicon glyphicon-ok",
						invalid : "glyphicon glyphicon-remove",
						validating : "glyphicon glyphicon-refresh"
					},					
					
				}), $("#feedBackform").on("success.form.bv", function(a) {
					a.preventDefault(),  0 == myflag
					myajaxform()
				})
			});	
		function myajaxform() {
			myflag = 1;
			var a = $("form").serialize();
			$.ajax({
				url : "adminupdateFeedback",
				type : "GET",
				success : completeHandler,
				error : errorHandler,
				data : a,
				cache : !1,
				contentType : !1,
				processData : !1
			})
		}
		var errorHandler = function() {
		}, progressHandlingFunction = function() {
		}, completeHandler = function(a) {
			$(".modal-bodyInterview").html(a);
			//location.reload(); 
		};
		$.each(qIdArr, function( index, value ) {				
			$("#feedBackform").bootstrapValidator('addField', 'educationalNotes_'+qIdArr[index],{
	            validators: {
	                notEmpty: {
	                    message: 'Please enter valid feedback.'
	                }
	            }
	        });
			});
	</script>
</body>