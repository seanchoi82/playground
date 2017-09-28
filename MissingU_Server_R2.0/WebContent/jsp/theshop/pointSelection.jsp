<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/header.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<style type="text/css">
	body { background: url(/img/bg_gray_nopad.png); }
</style>
<title>포인트 결제</title>
</head>
<body>
<script type="text/javascript">
	function order(prodCode) {
		//alert("베타 테스트 기간에는 포인트를 구매 하실 수 없습니다.");
		//return;
		$("#prodCode").val(prodCode);
		$("#orderForm").submit();
	}
	
</script>
<div class="page">
	<p class="tiparea">TIP : 포인트 결제를 하시면, 미싱유 모든 유료서비스에 포인트를 활용할 수 있습니다.</p>
	<ul class="item">
		<li><a href="javascript:order('P01001')"><img src="/img/btn_10000_n.png" /></a></li>
		<li><a href="javascript:order('P01002')"><img src="/img/btn_20000_n.png" /></a></li>
		<li><a href="javascript:order('P01003')"><img src="/img/btn_30000_n.png" /></a></li>
	</ul>
</div>

<div>
	<form:form id="orderForm" modelAttribute="orderReqVO" action="/missingu/theshop/orderPhone.html" method="POST">
		<input id="gMemberId" name="gMemberId" type="hidden" value="${paramMap.gMemberId}">
		<input id="gLang" name="gLang" type="hidden" value="${paramMap.gLang}">
		<input id="prodCode" name="prodCode" type="hidden" value="">
	</form:form>
</div>

</body>
</html>