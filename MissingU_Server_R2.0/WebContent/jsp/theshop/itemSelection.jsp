<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/header.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=high-dpi" />
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<style type="text/css">
	body { background: url(/img/bg_gray_nopad.png); }
	.item-btn {background:url(/img/btn_empty_bg.png); width:359px; height:112px; padding:3px 10px; border:0px;}
	.item .item-btn h3 {font-weight:bold; font-size:25px; color:#fff; text-shadow: 0px -2px 1px #333}
	.item .item-btn p {font-weight:bold; font-size:25px; font-style:italic; text-shadow: 0px -2px 1px #333;}
</style>
<title><spring:message code="theshop.itemSelection.title" /></title>


</head>
<body>
<div class="page">
	<p class="tiparea"><spring:message code="theshop.itemSelection.tip" /><br/><spring:message code="theshop.itemSelection.tip.msg1" /><br />
	<spring:message code="theshop.itemSelection.tip.msg2" /><br />
	 &nbsp; <spring:message code="theshop.itemSelection.tip.msg3" /></p>
	<div class="item">
		<button id="btnWeekTicket" class="item-btn" >
			<h3><spring:message code="theshop.itemSelection.ticket.week" /></h3>
			<p>7,000<spring:message code="theshop.itemSelection.ticket.point" /></p>
		</button><br><br>
		<button id="btnMonthTicket" class="item-btn">
			<h3><spring:message code="theshop.itemSelection.ticket.month" /></h3>
			<p>21,000<spring:message code="theshop.itemSelection.ticket.point" /></p>
		</button>
	</div>
</div>

<div>
	<!-- 아이템 구매 처리를 위한 form -->
	<form:form id="buyItemForm" modelAttribute="buyItemReqVO" action="/missingu/theshop/buyItemSubmit.html" method="POST">
		<input id="gMemberId" name="gMemberId" type="hidden" value="${paramMap.gMemberId}">
		<input id="gLang" name="gLang" type="hidden" value="${paramMap.gLang}">
		<input id="itemCode" name="itemCode" type="hidden" value="">
	</form:form>
</div>

<script type="text/javascript">
	function buyItem(itemCode) {
		// alert("베타 테스트 기간에는 정액권을 구매 하실 수 없습니다.");
		// return;
		
		if(confirm("<spring:message code="theshop.itemSelection.confirmPurchase" />")) {
			$("#itemCode").val(itemCode);
			//$("#buyItemForm").submit();
			$.ajax({
				url : "/missingu/theshop/buyItemSubmit.html",
				type : "POST",
				cache : false,
				data : $("#buyItemForm").serialize(),
				dataType : "json",
				success : function(data) {
					alert(data.result.rsltMsg);
					window.shop.onOrderCompleted();
				},
				error : function(data) {
					alert('<spring:message code="comm.systemError.msgText" />');
				}
			});
		}
	}
	
	$("#btnWeekTicket").click(function(){
		buyItem('T01001');
	});
	$("#btnMonthTicket").click(function(){
		buyItem('T01002');
	});
	
</script>
</body>
</html>