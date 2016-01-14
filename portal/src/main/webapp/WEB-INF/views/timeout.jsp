<style>
body {
	background-color: #0094DA;
}
</style>
<body>
	<div class="container center" style="margin-top: 100px; width: 350px;">

		<h4 style="color: #ffffff;">Session Timed Out. Reloading Page ...
		</h4>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#processImg").hide();			
			function simulate_ajax_call() {
				$.ajax({
					url : "home.do",
					success : function() {
						window.location.href = "home.do";
					}
				});
			}

			window.onload = function() {
				setTimeout(function() {					
					$.ajax({
						url : "home.do",
						success : function() {
							window.location.href = "home.do";
						}
					});
				}, 2000);
			};
		});
	</script>
</body>
