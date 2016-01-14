

<body>
	<style>
@media ( min-width : 768px) {
	.interview-dialog {
		width: 750px
	}
}
</style>
	Evaluator Home Page
	<a href="j_spring_security_logout" />Log Out
	</a>
	<p>User Role : ROLE_EVAL_EVAL</p>
	<button type="button" class="btn btn-primary btn-lg" id="appid">
		Launch demo modal</button>
	<a href=tmiAdmevaluatorMap.do>evaluatorRoles</a>
	<div class="modal" id="myModalprofile" tabindex="-1" role="dialog"
		data-backdrop="static" aria-labelledby="myModalLabel"
		aria-hidden="true">
		<div class="modal-dialog interview-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Profile:</h4>

				</div>
				<div class="modal-bodyprofile"></div>
				<div class="modal-footer"></div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#tmiRoles").bootstrapValidator({
				feedbackIcons : {
					valid : "glyphicon glyphicon-ok",
					invalid : "glyphicon glyphicon-remove",
					validating : "glyphicon glyphicon-refresh"
				},
				fields : {
					roles : {
						validators : {
							callback : {
								message : "Please select listed industry.",
								callback : function(a, b) {
									var d = b.getFieldElements("roles").val();
									return -1 != d
								}
							}
						}
					}
				}
			}), $("#tmiRoles").on("success.form.bv", function(a) {
				a.preventDefault(), myotherajaxform()
			})
		});
		var myotherajaxform = function() {
			var a = $("input[name=roles]").val(), b = $("input[name=comment]")
					.val();

		};
	</script>

</body>
