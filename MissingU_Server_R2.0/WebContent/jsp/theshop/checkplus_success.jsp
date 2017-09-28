<%@ page contentType="text/html;charset=UTF-8"%>
<html lang="ko">
<head>
<meta charset="utf-8">
<%@ include file="/jsp/common/header.jsp"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<style type="text/css">
	body { background: url(/img/bg_gray_nopad.png); }
</style>
<title>본인인증 결과</title>



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
			
			<input type="hidden" id="sName" name="sName"  value="${ sName }" />
			<input type="hidden" id="sBirthDate" name="sBirthDate" value="${ sBirthDate }" />
			<input type="hidden" id="sGender" name="sGender" value="${ sGender }" />
			<input type="hidden" id="sNationalInfo" name="sNationalInfo" value="${ sNationalInfo }" />
			
			<div class="ui-btns">
				<button id="btn_confirm">확인</button>
			</div>
			
		</div>
		
	</div>
	<c:if test="${ iReturn == 0 }">
	<script type="text/javascript">
			$("#btn_confirm").click(function(){
				
				var msg = $("#resultMsg").html();
				var sName = $("#sName").val();
				var sBirthDate = $("#sBirthDate").val();
				var sGender = $("#sGender").val();
				var sNationalInfo = $("#sNationalInfo").val();
				
				
				window.onselfcertification.onSuccess(msg, sName, sBirthDate, sGender, sNationalInfo);
			});
			
			$("#btn_confirm").trigger("click");
	</script>
	</c:if>
	
</body>
</html>