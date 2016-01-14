<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Welcome Iframe Test</title>
</head>
<body>
	<iframe
		src="${testUrl}tmiProcessor.php?id=${emailId}"
		id="tmitestframe" width="1200" height="645"></iframe>
	<script type="text/javascript">
		function receiveMessage(event) {
			var result= event.data;
			if(result.trim().substring(0,1)=="{"){
				document.getElementById('resultData').value = '[' + event.data
				+ ']';
				document.forms['testForm'].submit();
			}
			else{
				//alert("no  "+result);
			}
			
		
		}
		addEventListener("message", receiveMessage, false);
	</script>

	<form name="testForm" action="mockTestResult.do" method="post">
		<input type="hidden" id="resultData" name="resultData" value="" />
	</form>
</body>
</html>