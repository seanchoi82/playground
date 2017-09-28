<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<%@ include file="/jsp/common/header.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<style type="text/css">
	body { background: url(/img/bg_gray_nopad.png); }
</style>
<title>본인인증 결과</title>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn_confirm").click(function(){
			var msg = $("#resultMsg").html();
			window.onselfcertification.onFail(msg);
			location.href="/missingu/theshop/onselfCert.html";
		});
	});
	
</script>

</head>
<body>
	<div class="page">
		
		<div class="ui-box ui-box-white ui-box-shadow">
			
			<h2 class="icon-arrow-green">본인인증 결과</h2>
			
			<table class="tbl_nor" cellspacing="0" summary="본인인증 결과">
				<caption>본인인증 결과</caption>
				<colgroup>
					<col width="140">
				</colgroup>
				<tr>
					<th>메세지</th>
					<td><span id="resultMsg">${resultMsg}</span></td>
				</tr>
			</table>
			
			<div class="ui-btns">
				<button id="btn_confirm">확인</button>
			</div>
			
		</div>
		
	</div>
</body>
</html>