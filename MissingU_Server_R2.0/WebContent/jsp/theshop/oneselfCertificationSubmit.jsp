<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<meta http-equiv="Cache-Control" content="no-cache"/> 
<meta http-equiv="Expires" content="0"/> 
<meta http-equiv="Pragma" content="no-cache"/>
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/missingu.css">
<link rel="stylesheet" type="text/css" href="/css/common.css" />
<style type="text/css">
	body { background: url(/img/bg_gray_nopad.png); }
</style>
<title>본인인증</title>

</head>
<body>

<form id="linkForm" action="https://nice.checkplus.co.kr/CheckPlusSafeModel/checkplus.cb" method="post">
	<input type="hidden" name="m" value="checkplusSerivce" />
	<input type="hidden" name="EncodeData" value="${sEncData}" />
	<input type="hidden" name="param_r1" value="${paramMap.gMemberId}" />
</form>

	<div class="page">
		
		<div class="ui-box ui-box-white ui-box-shadow">
			
			<h2 class="icon-arrow-green">본인인증 처리중</h2>
			
			<table class="tbl_nor" cellspacing="0" summary="본인인증 정보 입력">
				<caption>본인인증 정보 입력</caption>
				<colgroup>
					<col width="140">
				</colgroup>
				<tr>
					<th>설명</th>
					<td>※ 본인인증 작업 중입니다. 페이지가 멈춰 있는 경우 아래 확인 버튼을 클릭해 주세요..<br>
				</tr>
			</table>
			
			<div class="ui-btns">
				<button id="btn_confirm">확인</button>
				<button id="btn_cancel">취소</button>
			</div>
			
		</div>
		
	</div>

<script type="text/javascript">

	$("#btn_confirm").click(function(){
		$("#linkForm").submit();
	});
	
</script>
<script type="text/javascript">
		$("#linkForm").submit();
</script>
</body>
</html>