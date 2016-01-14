<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" href="resources/css/mywallet.css" />
<script type="text/javascript">
jQuery(document).ready(function() {
	$("#profilelink").parent().addClass("active");
});
function walletBack(){
		window.location.href = 'myCompleteProfile.do';
}
	
</script>
<body>
	<div
		style="padding: 20px 0%; background-color: #0094DA; color: #ffffff; font-size: 22px; font-family: robotolight;">
		<div class="container">
			<img src="resources/images/icon_profile.png">&nbsp;&nbsp;MyWallet
		</div>
	</div>
	<div class="" style="margin-top: 20px;">
		<div class="container">			
			<hr>
			<form id="myprofile" action="" method="post" autocomplete="off">
				<c:if test="${not empty myWalletHistory}">				
					<table>
						<thead>
							<tr>
								<th id="width1" Style="color: #FFF">Reference Id</th>
								<th id="width2" Style="color: #FFF">Amount</th>
								<th id="width3" Style="color: #FFF">Date</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="maxid" value="0" />
							<c:set var="i" value="0" />
							<c:forEach var="walletLst" items="${myWalletHistory}">						
								<tr>
									<td>${walletLst.reference}</td>
									<td>${walletLst.amount}</td>
									<td>${walletLst.datetime}</td>
									<c:set var="i" value="${i + walletLst.amount}"></c:set>								
								</tr>											 	
							</c:forEach>							
						</tbody>
					</table>	
					<br>
						<div class="text-right profile-text" >
						<b style="color:#0094DA;">Total Wallet amount:${i}</b>
					</div>			
				</c:if>
				<%-- <c:out value="${i}"/> --%>
				<c:if test="${empty myWalletHistory}">
					<div style="text-align: center;">
						<img style="width: 30px;" src="resources/images/warning.png" />&nbsp;There
									are no records available.
					</div>
				</c:if>
				<br>				
				<hr>
				<div class="row">				
						<div
							class="col-xs-4 col-xs-push-8 col-sm-2 col-sm-push-10 col-md-2 col-md-push-10">
							<div class="form-group">
								<button type="button" onclick="walletBack()"
									class="form-control mybackbtn" >Back</button>
							</div>
						</div>		
				</div>
			</form>
		</div>
	</div>

	
</body>